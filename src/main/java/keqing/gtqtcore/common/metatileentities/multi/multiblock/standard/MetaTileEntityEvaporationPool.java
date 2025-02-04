package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.*;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.StoneVariantBlock;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.capability.impl.EvapRecipeLogic;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.EvaporationEnergyProperty;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockEvaporationBed;
import keqing.gtqtcore.integration.theoneprobe.EvaporationPoolInfoProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Supplier;

import static gregtech.api.GregTechAPI.HEATING_COILS;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.EVAPORATION_POOL;

public class MetaTileEntityEvaporationPool extends RecipeMapMultiblockController {

    /*
        For future reference: "((IGregTechTileEntity)world.getTileEntity(pos)).getMetaTileEntity() instanceof IMultiblockAbilityPart"
        is the way ceu gets mte from te. You might also try using MetaTileEntityHolder.
     */

    public static final int MAX_SQUARE_SIDE_LENGTH = 12; //two edge layers on either side, shouldn't exceed chunk boundary at max size

    public static final int structuralDimensionsID = 1051354;
    public static final int coilDataID = 10142156;
    public static final int energyValuesID = 10868607;
    public static final ArrayList<IBlockState> validContainerStates = new ArrayList<>();

    static {
        validContainerStates.add(GTQTMetaBlocks.blockEvaporationBed.getState(BlockEvaporationBed.EvaporationBedType.DIRT));
        validContainerStates.addAll(MetaBlocks.WIRE_COIL.getBlockState().getValidStates()); //add all coils as valid container blocks
    }

    public boolean isHeated = false;
    public int[] rollingAverage = new int[20];
    public boolean areCoilsHeating = false;
    public int coilStateMeta = -1; //order is last in order dependent ops because I'm lazy
    public boolean isRecipeStalled = false;
    //just initialized on formation
    public IHeatingCoilBlockStats coilStats;
    int columnCount = 1; //number of columns in row of controller (1 -> EDGEself(controller)EDGE, 2 -> EsCOLUMNe)
    int rowCount = 1; //number of rows where controller is placed on "edge" row
    int controllerPosition = 0; //column placement from left to right, where 0 = one from edge [ESCCCCE]
    int exposedBlocks = 0;
    byte[] wasExposed; //indexed with row*col + col with row = 0 being furthest and col 0 being leftmost when looking at controller
    int kiloJoules = 0; //about 1000J/s on a sunny day for 1/m^2 of area
    int joulesBuffer = 0;
    int tickTimer = 0;

    public MetaTileEntityEvaporationPool(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.EVAPORATION_POOL);
        this.recipeMapWorkable = new EvapRecipeLogic(this);

        columnCount = 1; //minimum of one column for controller to be placed on
        controllerPosition = 0; //controller starts off furthest to left
    }

    public static String repeat(String s, int count) {
        if (s.length() == 0 || count < 1) {
            return "";
        }
        if (count == 1) {
            return s;
        }

        //create empty char array, convert to string which places null terminators in all its positions, then replace all
        return new String(new char[count * s.length()]).replace("\0", s);
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getControllerPosition() {
        return controllerPosition;
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityEvaporationPool(this.metaTileEntityId);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.isHeated = false;
        this.areCoilsHeating = false;
        this.coilStateMeta = -1;

        this.exposedBlocks = 0;
        this.wasExposed = new byte[0];
        this.kiloJoules = 0;
        this.tickTimer = 0;
        this.isRecipeStalled = false;

        this.writeCustomData(coilDataID, (buf) -> {
            buf.writeBoolean(isHeated);
            buf.writeBoolean(areCoilsHeating);
            buf.writeInt(coilStateMeta);
        });

        this.writeCustomData(energyValuesID, buf -> {
            buf.writeInt(exposedBlocks);
            buf.writeByteArray(wasExposed);
            buf.writeInt(kiloJoules);
            buf.writeInt(tickTimer);
            buf.writeBoolean(isRecipeStalled);
        });
    }

    public boolean updateStructureDimensions() {
        try {
            World world = getWorld();
            if (world == null) {
                throw new IllegalStateException("World is null");
            }

            BlockPos controllerPos = getPos();
            if (controllerPos == null) {
                throw new IllegalStateException("Controller position is null");
            }

            EnumFacing front = this.getFrontFacing();
            EnumFacing back = front.getOpposite();
            EnumFacing right = front.rotateYCCW();
            EnumFacing left = right.getOpposite(); //left as if you were looking at it, not controller's left

            //distance to use when located edges of structure, moved inwards to container block portion for detection purposes
            BlockPos.MutableBlockPos leftPosition = new BlockPos.MutableBlockPos(controllerPos.offset(back, 2)); //start in container section
            BlockPos.MutableBlockPos rightPosition = new BlockPos.MutableBlockPos(controllerPos.offset(back, 2)); //start in container section
            BlockPos.MutableBlockPos backPosition = new BlockPos.MutableBlockPos(controllerPos.offset(back)); //start one before container section

            //zero on both lDist and rDist indicates 1 column
            int leftDistance = -1;
            int rightDistance = -1;
            int backDistance = -1;

            //find when container block section is exited left, right, and back
            for (int i = 1; i <= MAX_SQUARE_SIDE_LENGTH; i++) {
                if (leftDistance == -1 && !isContainerBlock(world, leftPosition, left)) leftDistance = i;
                if (rightDistance == -1 && !isContainerBlock(world, rightPosition, right)) rightDistance = i;
                if (backDistance == -1 && !isContainerBlock(world, backPosition, back)) backDistance = i;
                if (leftDistance != -1 && rightDistance != -1 && backDistance != -1) break;

                leftPosition.move(left);
                rightPosition.move(right);
                backPosition.move(back);
            }

            // Validate distances
            if (leftDistance < 0 || rightDistance < 0 || backDistance < 1 || leftDistance + rightDistance + 1 > MAX_SQUARE_SIDE_LENGTH) {
                invalidateStructure();
                return false;
            }

            // Calculate dimensions
            columnCount = leftDistance + rightDistance + 1;
            rowCount = backDistance; //"Depth" of container blocks
            controllerPosition = leftDistance; //if there are no blocks to the left controller is left most spot

            //store the known dimensions for structure check
            this.writeCustomData(structuralDimensionsID, (buf) -> {
                buf.writeInt(columnCount);
                buf.writeInt(rowCount);
                buf.writeInt(controllerPosition);
            });

            return true; //successful formation
        } catch (Exception e) {
            e.printStackTrace();
            invalidateStructure();
            return false;
        }
    }


    public boolean isContainerBlock(@Nonnull World world, @Nonnull BlockPos.MutableBlockPos pos, @Nonnull EnumFacing direction) {
        return validContainerStates.contains(world.getBlockState(pos.move(direction)));
    }

    public String[] centerRowsPattern() {
        if (rowCount <= 0 || columnCount < 0) {
            throw new IllegalArgumentException("rowCount must be positive and columnCount must be non-negative");
        }

        String[] containerRows = new String[rowCount];
        final String[] flooring = {"G", "C"};

        for (int i = 0; i < rowCount; ++i) {
            StringBuilder containerRowBuilder = new StringBuilder();

            // Add edge blocks at the start
            containerRowBuilder.append("EE");

            for (int j = 2; j < columnCount + 2; ++j) {
                if (j % 2 == 0) {
                    // Coil columns always have coils at their position
                    containerRowBuilder.append("C");
                } else if (j % 4 == 1) {
                    // Further crossover column
                    containerRowBuilder.append(i == rowCount - 1 ? "C" : "G");
                } else if (j % 4 == 3) {
                    // Closer crossover column
                    containerRowBuilder.append(i == 0 ? "C" : "G");
                }
            }

            // Add edge blocks at the end
            containerRowBuilder.append("EE");

            containerRows[i] = containerRowBuilder.toString();
        }

        return containerRows;
    }


    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.variantActiveBlocks = context.getOrDefault("VABlock", new LinkedList<>());
        this.replaceVariantBlocksActive(isHeated());
    }

    @Override
    protected BlockPattern createStructurePattern() {
    /*
        'E' for "edge" block, that is to say essentially the blocks that contain the water.
        '#' for air block where hypothetical water would be.
        'S' for self, or controller.
        'C' for "container blocks" that can be coils, with coils required for powered heating.
        'G' for ground blocks which must be some non coil block
     */

        int currColCount = columnCount;
        int currRowCount = rowCount;
        int currContPos = controllerPosition;

        // Ensure structure dimensions are updated if world is not null
        if (getWorld() != null) {
            updateStructureDimensions();
        }

        initializeCoilStats();

        // Ensure minimum values for columnCount and rowCount
        ensureMinimumSize();

        // Initialize wasExposed array if necessary
        if (wasExposed == null || wasExposed.length != columnCount * rowCount) {
            wasExposed = new byte[columnCount * rowCount]; // all set to 0
            this.exposedBlocks = 0; // ensure exposedBlocks don't go higher than expected
        }

        // Initialize rollingAverage if necessary
        if (rollingAverage == null) {
            rollingAverage = new int[20];
        }
        isRecipeStalled = false;

        // Return existing pattern if dimensions haven't changed
        if (structurePattern != null && currColCount == columnCount && currRowCount == rowCount && currContPos == controllerPosition) {
            return structurePattern;
        }

        // Abstracted away construction of center rows for later use
        String[] containerRows = centerRowsPattern();

        FactoryBlockPattern pattern = FactoryBlockPattern.start()
                .aisle(repeat("E", columnCount + 4), repeat(" ", columnCount + 4))
                .aisle(repeat("E", columnCount + 4), " ".concat(repeat("E", columnCount + 2)).concat(" "));

        // Place all generated aisles (rows stored closer to further, this wants them further to closer)
        for (int i = 0; i < rowCount; ++i) {
            pattern = pattern.aisle(containerRows[rowCount - 1 - i], " E".concat(repeat("#", columnCount).concat("E ")));
        }

        // Place last two aisles
        pattern = pattern.aisle(repeat("E", columnCount + 4), " ".concat(repeat("E", columnCount + 2)).concat(" "))
                .aisle(repeat("E", controllerPosition + 2).concat("S").concat(repeat("E", columnCount + 1 - controllerPosition)), repeat(" ", columnCount + 4))

                // Begin predicates
                .where('S', selfPredicate())
                .where('E', isEdge().or(autoAbilities(false, false, true, true, true, true, false).setMaxGlobalLimited(8))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(0).setMaxGlobalLimited(2).setPreviewCount(0)))
                .where('G', isGround())
                .where('C', isContainer())
                .where('#', air())
                .where(' ', any());

        return pattern.build();
    }

    private void ensureMinimumSize() {
        if (columnCount < 1) {
            columnCount = 1;
        }
        if (rowCount < 1) {
            rowCount = 1;
        }
    }

    protected TraceabilityPredicate isEdge() {
        Supplier<BlockInfo[]> supplier = () -> new BlockInfo[]{new BlockInfo(MetaBlocks.STONE_BLOCKS.get(StoneVariantBlock.StoneVariant.SMOOTH).getState(StoneVariantBlock.StoneType.CONCRETE_LIGHT))};
        return new TraceabilityPredicate(blockWorldState -> {
            IBlockState state = blockWorldState.getBlockState();
            return state == MetaBlocks.STONE_BLOCKS.get(StoneVariantBlock.StoneVariant.SMOOTH).getState(StoneVariantBlock.StoneType.CONCRETE_LIGHT);
        }, supplier);
    }

    protected TraceabilityPredicate isGround() {
        Supplier<BlockInfo[]> supplier = () -> new BlockInfo[]{new BlockInfo(GTQTMetaBlocks.blockEvaporationBed.getState(BlockEvaporationBed.EvaporationBedType.DIRT))};
        return new TraceabilityPredicate(blockWorldState -> {
            IBlockState state = blockWorldState.getBlockState();
            return isValidGround(state);
        }, supplier);
    }

    protected TraceabilityPredicate isContainer() {
        Supplier<BlockInfo[]> supplier = () -> {
            List<BlockInfo> containerInfo = Collections.synchronizedList(new ArrayList<>());

            try {
                // Add evap bed types
                for (BlockEvaporationBed.EvaporationBedType type : BlockEvaporationBed.EvaporationBedType.values()) {
                    containerInfo.add(new BlockInfo(GTQTMetaBlocks.blockEvaporationBed.getState(type)));
                }

                // Add coil types
                GregTechAPI.HEATING_COILS.entrySet().stream()
                        .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
                        .forEach(entry -> containerInfo.add(new BlockInfo(entry.getKey())));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return containerInfo.toArray(new BlockInfo[0]);
        };

        return new TraceabilityPredicate(blockWorldState -> {
            IBlockState state = blockWorldState.getBlockState();

            int containerStateResult = isValidCoil(state);
            if (containerStateResult == -1) {
                return false;
            } else if (containerStateResult == 1) {
                blockWorldState.getMatchContext().getOrPut("VABlock", new LinkedList<>()).add(blockWorldState.getPos());
            }

            return true;
        }, supplier);
    }


    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("efFIEEEEEE", "          ")
                .aisle("EEEEEEEEEE", " EEEEEEEE ").aisle("EECCCGCCEE", " E######E ")
                .aisle("EECGCGCGEE", " E######E ").aisle("EECGCGCGEE", " E######E ")
                .aisle("EECGCGCGEE", " E######E ").aisle("EECGCGCGEE", " E######E ")
                .aisle("EECGCCCGEE", " E######E ").aisle("EEEEEEEEEE", " EEEEEEEE ")
                .aisle("EEESEEEEEE", "          ")
                .where('S', EVAPORATION_POOL, EnumFacing.SOUTH)
                .where('E', MetaBlocks.STONE_BLOCKS.get(StoneVariantBlock.StoneVariant.SMOOTH).getState(StoneVariantBlock.StoneType.CONCRETE_LIGHT))
                .where('G', GTQTMetaBlocks.blockEvaporationBed.getState(BlockEvaporationBed.EvaporationBedType.DIRT)).where('#', Blocks.AIR.getDefaultState())
                .where(' ', Blocks.AIR.getDefaultState()) //supposed to be any
                .where('e', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.LV], EnumFacing.NORTH).where('f', MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.LV], EnumFacing.NORTH)
                .where('F', MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.LV], EnumFacing.NORTH).where('I', MetaTileEntities.ITEM_EXPORT_BUS[GTValues.LV], EnumFacing.NORTH);

        GregTechAPI.HEATING_COILS.entrySet().stream().sorted(Comparator.comparingInt(entry -> entry.getValue().getTier())).forEach(entry -> shapeInfo.add(builder.where('C', entry.getKey()).build()));

        return shapeInfo;
    }

    public int coilPatternCheck(boolean checkGround) {
        String[] centerPattern = centerRowsPattern();
        IBlockState targetState = (coilStateMeta == -1) ? null : MetaBlocks.WIRE_COIL.getStateFromMeta(coilStateMeta);
        EnumFacing right = getFrontFacing().rotateYCCW();
        EnumFacing back = getFrontFacing().getOpposite();

        BlockPos.MutableBlockPos targetPos = new BlockPos.MutableBlockPos(getPos().offset(back, 2));
        targetPos.move(right.getOpposite(), controllerPosition + 1);

        for (int i = 0; i < rowCount; ++i) {
            for (int j = 2; j < columnCount + 2; ++j) {
                targetPos.move(right);

                if (j >= centerPattern[i].length()) {
                    return ((i * columnCount + j - 2) * -1) - 2;
                }

                char patternChar = centerPattern[i].charAt(j);
                if (patternChar != 'C') {
                    if (!checkGround) continue;

                    if (getWorld().getBlockState(targetPos) == GTQTMetaBlocks.blockEvaporationBed.getState(BlockEvaporationBed.EvaporationBedType.DIRT))
                        continue;

                    return ((i * columnCount + j - 2) * -1) - 2;
                }

                IBlockState currentState = getWorld().getBlockState(targetPos);
                if (targetState == null) {
                    targetState = currentState;
                    if (MetaBlocks.WIRE_COIL.getBlockState().getValidStates().contains(targetState))
                        continue;
                } else if (currentState.equals(targetState)) {
                    continue;
                }

                return i * columnCount + j - 2;
            }

            targetPos.move(back);
            targetPos.move(right.getOpposite(), columnCount);
        }

        if (!checkGround && targetState != null) {
            coilStateMeta = targetState.getBlock().getMetaFromState(targetState);
        }
        return -1;
    }


    public boolean handleCoilCheckResult(int result) {
        if (result != -1) {
            coilStateMeta = -1;
            isHeated = false;
            initializeCoilStats();
            return false;
        }
        isHeated = true;
        initializeCoilStats();

        this.writeCustomData(coilDataID, (buf) -> {
            buf.writeBoolean(isHeated);
            buf.writeBoolean(areCoilsHeating);
            buf.writeInt(coilStateMeta);
        });

        return true;
    }

    public void initializeCoilStats() {
        coilStats = coilStateMeta > -1 ? HEATING_COILS.get(MetaBlocks.WIRE_COIL.getStateFromMeta(coilStateMeta)) : null;
    }

    public int isValidCoil(IBlockState state) {
        if (!MetaBlocks.WIRE_COIL.getBlockState().getValidStates().contains(state))
            return this.isValidGround(state) ? 0 : -1;
        if (coilStateMeta == -1) return 1;
        return state.toString().equals(MetaBlocks.WIRE_COIL.getStateFromMeta(coilStateMeta).toString()) ? 1 : 0;
    }

    public boolean isValidGround(IBlockState state) {
        return state == GTQTMetaBlocks.blockEvaporationBed.getState(BlockEvaporationBed.EvaporationBedType.DIRT);
    }

    @Override
    public void checkStructurePattern() {
        if (!isStructureFormed() || structurePattern == null) {
            structurePattern = null; // should erase any faulty errors picked up from reloading world
            reinitializeStructurePattern(); // creates new structure pattern again
        }
        super.checkStructurePattern();
        if (((tickTimer / 20) & 1) == 0 && structurePattern != null && structurePattern.getError() == null) {
            handleCoilCheckResult(coilPatternCheck(false));
        }
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.machine.evaporation_pool.tooltip.info", MAX_SQUARE_SIDE_LENGTH, MAX_SQUARE_SIDE_LENGTH));
        if (TooltipHelper.isShiftDown()) {
            tooltip.add(I18n.format("gregtech.machine.evaporation_pool.tooltip.structure_info", MAX_SQUARE_SIDE_LENGTH, MAX_SQUARE_SIDE_LENGTH) + "\n");
        }
    }

    @Override
    public void update() {
        super.update(); // Recipe logic happens before heating is added

        if (this.getWorld().isRemote) {
            if (this.isActive() && !isRecipeStalled) {
                evaporationParticles(); // Custom rendering on client side
            }
            return; // Don't do logic on client
        }

        setCoilActivity(false); // Solve world reload issue
        if (structurePattern.getError() != null) return; // Skip processing for unformed multis

        // Ensure tickTimer is non-negative
        tickTimer = Math.max(0, tickTimer);

        checkCoilActivity(); // Make coils active if they should be
        rollingAverage[tickTimer % 20] = 0; // Reset rolling average for this tick index

        // Perform checks every 10 ticks, except the first tick
        if (tickTimer % 10 == 0 && tickTimer != 0) {
            if (getWorld().isRainingAt(getPos().offset(getFrontFacing().getOpposite(), 2)) || !getWorld().isDaytime()) {
                exposedBlocks = 0;
            } else if (getWorld().provider.getDimension() == 0) {
                int row = ((tickTimer / 20) / columnCount) % rowCount;
                int col = ((tickTimer / 20) % columnCount);
                BlockPos.MutableBlockPos skyCheckPos = new BlockPos.MutableBlockPos(getPos().offset(EnumFacing.UP, 2));
                skyCheckPos.move(getFrontFacing().getOpposite(), rowCount - row + 1);
                skyCheckPos.move(getFrontFacing().rotateY(), controllerPosition);
                skyCheckPos.move(getFrontFacing().rotateYCCW(), col);

                if (wasExposed == null || wasExposed.length != rowCount * columnCount) {
                    wasExposed = new byte[rowCount * columnCount];
                    exposedBlocks = 0;
                }

                if (!getWorld().canBlockSeeSky(skyCheckPos)) {
                    if (wasExposed[(row * columnCount) + col] != 0 && tickTimer / 20 > rowCount * columnCount) {
                        exposedBlocks = Math.max(0, exposedBlocks - 1);
                        wasExposed[(row * columnCount) + col] = 0;
                    }
                } else {
                    if (wasExposed[(row * columnCount) + col] == 0) {
                        if (exposedBlocks < rowCount * columnCount) ++exposedBlocks;
                        wasExposed[(row * columnCount) + col] = 1;
                    }
                }
            }
        }

        inputEnergy(exposedBlocks * 50); // 1kJ/s/m^2 -> 50J/t

        // Convert joules in buffer to kJ
        if (joulesBuffer >= 1000) {
            int tempBuffer = joulesBuffer;
            joulesBuffer = 0;
            if (!inputEnergy(tempBuffer)) joulesBuffer = tempBuffer;
        }

        tickTimer++;

        try {
            this.writeCustomData(energyValuesID, buf -> {
                buf.writeInt(exposedBlocks);
                buf.writeByteArray(wasExposed);
                buf.writeInt(kiloJoules);
                buf.writeInt(tickTimer);
                buf.writeBoolean(isRecipeStalled);
            });
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
        }
    }


    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive()).addEnergyUsageLine(getEnergyContainer()).addCustom(tl -> {
            // coil coefficient
            if (isStructureFormed()) {
                // handle heating contributions
                if (isHeated()) {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.WHITE, "gregtech.top.evaporation_pool_heated_preface").appendText(" ").appendSibling(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "gregtech.top.evaporation_pool_is_heated")));

                } else {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.WHITE, "gregtech.top.evaporation_pool_heated_preface").appendText(" ").appendSibling(TextComponentUtil.translationWithColor(TextFormatting.RED, "gregtech.top.evaporation_pool_not_heated")));
                }

                tl.add(TextComponentUtil.translationWithColor(TextFormatting.WHITE, "gregtech.multiblock.evaporation_pool.exposed_blocks").appendText(" ").appendSibling(TextComponentUtil.translationWithColor(TextFormatting.GREEN, TextFormattingUtil.formatNumbers(exposedBlocks))));

                tl.add(TextComponentUtil.translationWithColor(TextFormatting.WHITE, "gregtech.top.evaporation_pool.energy_transferred").appendText(" ").appendSibling(TextComponentUtil.stringWithColor(TextFormatting.YELLOW, TextFormattingUtil.formatNumbers(this.getKiloJoules())).appendText(".").appendSibling(TextComponentUtil.stringWithColor(TextFormatting.YELLOW, EvaporationPoolInfoProvider.constLengthToString(this.getJoulesBuffer()))).appendText(" ").appendSibling(TextComponentUtil.translationWithColor(TextFormatting.WHITE, "gregtech.top.evaporation_pool.kilojoules"))));

                tl.add(TextComponentUtil.translationWithColor(TextFormatting.WHITE, "gregtech.multiblock.evaporation_pool.rolling_average").appendText(" ").appendSibling(TextComponentUtil.stringWithColor(TextFormatting.YELLOW, TextFormattingUtil.formatNumbers(getRollingAverageJt()))).appendText(" ").appendSibling(TextComponentUtil.translationWithColor(TextFormatting.WHITE, "gregtech.multiblock.evaporation_pool.joules_per_tick")));

                tl.add(TextComponentUtil.translationWithColor(TextFormatting.WHITE, "gregtech.multiblock.evaporation_pool.average_speed").appendText(" ").appendSibling(TextComponentUtil.stringWithColor(TextFormatting.GREEN, getAverageRecipeSpeedString())).appendSibling(TextComponentUtil.stringWithColor(TextFormatting.WHITE, "x"))); // add empty space to visually separate evap pool custom stats
            }
        }).addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage())).addParallelsLine(recipeMapWorkable.getParallelLimit()).addWorkingStatusLine().addProgressLine(recipeMapWorkable.getProgressPercent());
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("columnCount", this.columnCount);
        data.setInteger("rowCount", this.rowCount);
        data.setInteger("controllerPosition", this.controllerPosition);
        data.setBoolean("isHeated", this.isHeated);
        data.setBoolean("areCoilsHeating", this.areCoilsHeating);
        data.setInteger("exposedBlocks", this.exposedBlocks);
        data.setByteArray("wasExposed", this.wasExposed == null ? new byte[this.rowCount * this.columnCount] : this.wasExposed);
        data.setInteger("kiloJoules", this.kiloJoules);
        data.setInteger("tickTimer", this.tickTimer);
        data.setBoolean("isRecipeStalled", this.isRecipeStalled);
        data.setInteger("coilStateMeta", this.coilStateMeta);
        return data;
    }


    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        if (data.hasKey("columnCount")) {
            this.columnCount = data.getInteger("columnCount");
        }
        if (data.hasKey("rowCount")) {
            this.rowCount = data.getInteger("rowCount");
        }
        if (data.hasKey("controllerPosition")) {
            this.controllerPosition = data.getInteger("controllerPosition");
        }
        if (data.hasKey("isHeated")) {
            this.isHeated = data.getBoolean("isHeated");
        }
        if (data.hasKey("areCoilsHeating")) {
            this.areCoilsHeating = data.getBoolean("areCoilsHeating");
        }
        if (data.hasKey("exposedBlocks")) {
            this.exposedBlocks = data.getInteger("exposedBlocks");
        }
        if (data.hasKey("wasExposed")) {
            this.wasExposed = data.getByteArray("wasExposed");
        }
        if (data.hasKey("kiloJoules")) {
            this.kiloJoules = data.getInteger("kiloJoules");
        }
        if (data.hasKey("tickTimer")) {
            this.tickTimer = data.getInteger("tickTimer");
        }
        if (data.hasKey("isRecipeStalled")) {
            this.isRecipeStalled = data.getBoolean("isRecipeStalled");
        }
        if (data.hasKey("coilStateMeta")) {
            this.coilStateMeta = data.getInteger("coilStateMeta");
            initializeCoilStats();
        }
        if (rollingAverage == null) rollingAverage = new int[20];
        reinitializeStructurePattern();
    }

    //order matters for these
    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.columnCount);
        buf.writeInt(this.rowCount);
        buf.writeInt(this.controllerPosition);
        buf.writeBoolean(this.isHeated);
        buf.writeBoolean(this.areCoilsHeating);
        buf.writeInt(this.exposedBlocks);
        buf.writeByteArray(this.wasExposed == null ? new byte[this.rowCount * this.columnCount] : this.wasExposed);
        buf.writeInt(this.kiloJoules);
        buf.writeInt(this.tickTimer);
        buf.writeBoolean(this.isRecipeStalled);
        buf.writeInt(this.coilStateMeta);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.columnCount = buf.readInt();
        this.rowCount = buf.readInt();
        this.controllerPosition = buf.readInt();
        this.isHeated = buf.readBoolean();
        this.areCoilsHeating = buf.readBoolean();
        this.exposedBlocks = buf.readInt();
        this.wasExposed = buf.readByteArray();
        this.kiloJoules = buf.readInt();
        this.tickTimer = buf.readInt();
        this.isRecipeStalled = buf.readBoolean();
        this.coilStateMeta = buf.readInt();

        if (rollingAverage == null) rollingAverage = new int[20];
        reinitializeStructurePattern();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == structuralDimensionsID) {
            this.columnCount = buf.readInt();
            this.rowCount = buf.readInt();
            this.controllerPosition = buf.readInt();
        } else if (dataId == coilDataID) {
            this.isHeated = buf.readBoolean();
            this.areCoilsHeating = buf.readBoolean();
            this.coilStateMeta = buf.readInt();
            initializeCoilStats();
        } else if (dataId == energyValuesID) {
            this.exposedBlocks = buf.readInt();
            this.wasExposed = buf.readByteArray();
            this.kiloJoules = buf.readInt();
            this.tickTimer = buf.readInt();
            this.isRecipeStalled = buf.readBoolean();
        }
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    private void evaporationParticles() {
        final EnumFacing back = this.getFrontFacing().getOpposite();
        final EnumFacing left = back.rotateYCCW();

        //conversion from blockpos to in world pos places particle position at corner of block such that relative direction must be accounted for
        //add 1 if x pos or z pos (offsets mutually exclusively non-zero for given facing) as conversion to float pos rounds down in both coords
        int leftOffset = ((left.getXOffset() * -1) >>> 31) + ((left.getZOffset() * -1) >>> 31);
        int backOffset = ((back.getXOffset() * -1) >>> 31) + ((back.getZOffset() * -1) >>> 31);

        //place pos in closest leftmost corner
        final BlockPos pos = this.getPos().offset(left, controllerPosition + leftOffset).offset(back, 1 + backOffset);

        //Spawn number of particles on range [1, 3 * colCount * rowCount/9] (~3 particles for every 3x3 = 9m^2)
        for (int i = 0; i < Math.max(1, columnCount * rowCount / 3); i++) {
            //either single line along x axis intersects all rows, or it intersects all columns. Same applies to z axis. getXOffset indicates +, -, or "0" direction of facing for given axis
            float xLength = (back.getXOffset() * rowCount) + (left.getXOffset() * columnCount * -1); //we start on leftmost closest corner and want to go back and to the right
            float zLength = (back.getZOffset() * rowCount) + (left.getZOffset() * columnCount * -1); //so we invert the sign of the left offsets to effectively get right displacement

            //if (tickTimer % 100 == 0) SusyLog.logger.atError().log("xLength: " + xLength + ", zLength: " + zLength + ", leftOffset: " + leftOffset + ", backOffset: " + backOffset + ", pos: " + pos + ", controller pos: " + getPos());

            float xPos = pos.getX() + (xLength * GTValues.RNG.nextFloat()); //scale x length by random amount to get output coord
            float yPos = pos.getY() + 0.75F; //shit out particles one quarter of a block below the surface of the interior to give effect of gases rising from bottom
            float zPos = pos.getZ() + (zLength * GTValues.RNG.nextFloat());

            float ySpd = 0.4F + 0.2F * GTValues.RNG.nextFloat();
            getWorld().spawnParticle(EnumParticleTypes.CLOUD, xPos, yPos, zPos, 0, ySpd, 0);
        }
    }

    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.DIRT;
    }

    @Nonnull
    protected ICubeRenderer getFrontOverlay() {
        return Textures.BLAST_FURNACE_OVERLAY;
    }

    @Override
    public boolean getIsWeatherOrTerrainResistant() {
        return true;
    }

    public int getKiloJoules() {
        return kiloJoules;
    }

    public void setKiloJoules(int kiloJoules) {
        this.kiloJoules = kiloJoules;
    }

    public int getJoulesBuffer() {
        return joulesBuffer;
    }

    public void setJoulesBuffer(int joulesBuffer) {
        this.joulesBuffer = joulesBuffer;
    }

    public int getTickTimer() {
        return tickTimer;
    }

    public int getRollingAverageJt() {
        // sunlight => 1kJ/s/m^2 -> 50J/t/m^2
        return exposedBlocks * 50 + Arrays.stream(rollingAverage).sum() / 20;
    }

    public float getAverageRecipeSpeed() {
        if (!recipeMapWorkable.isActive() || recipeMapWorkable.getPreviousRecipe() == null) return 0;
        float recipeJt = recipeMapWorkable.getPreviousRecipe().getProperty(EvaporationEnergyProperty.getInstance(), -1);
        return (exposedBlocks * 50 + Arrays.stream(rollingAverage).sum() / 20F) / recipeJt;
    }

    public String getAverageRecipeSpeedString() {
        return Float.toString(((int) (getAverageRecipeSpeed() * 100) / 100F));
    }

    public void checkCoilActivity(String source) {
        boolean isRunningHeated = isRunningHeated();
        if (lastActive ^ isRunningHeated) {
            this.setLastActive(isRunningHeated);
            this.markDirty();
            this.replaceVariantBlocksActive(isRunningHeated);
        }
    }

    public void checkCoilActivity() {
        boolean isRunningHeated = isRunningHeated();
        if (lastActive ^ isRunningHeated) {
            this.setLastActive(isRunningHeated);
            this.markDirty();
            this.replaceVariantBlocksActive(isRunningHeated);
        }
    }

    // controller disappears if you set variant blocks to same state as they already were
    public void setCoilActivity(boolean state) {
        if (state == lastActive) {
            this.setLastActive(!state);
            this.markDirty();
            this.replaceVariantBlocksActive(!state);
        }

        this.setLastActive(state);
        this.markDirty();
        this.replaceVariantBlocksActive(state);

    }

    public boolean isHeated() {
        return isHeated;
    }

    public boolean isRunningHeated() {
        return isHeated && isActive() && areCoilsHeating;
    }

    public void setIsHeated(boolean isHeated) {
        this.isHeated = isHeated;
    }

    public boolean inputEnergy(int joules) {
        //limit amount of energy stored
        final int CUBE_HEAT_CAPACITY = 100; //kJ/m^3
        if (getKiloJoules() > CUBE_HEAT_CAPACITY * columnCount * rowCount) {
            return false;
        }

        int kJ = joules / 1000;
        joules -= kJ * 1000;
        joulesBuffer += joules;

        //store kJ
        setKiloJoules(getKiloJoules() + kJ);
        rollingAverage[tickTimer % 20] += joules + 1000 * kJ;
        return true;
    }

    public int calcMaxSteps(int jStepSize) {
        int stepCount = (getKiloJoules() * 1000) / jStepSize; //max number of times jStepSize can cleanly be deducted from kiloJoules
        int remainder = (stepCount + 1) * jStepSize - getKiloJoules() * 1000; //remaining joules needed to not waste partial kJ

        if (joulesBuffer >= remainder) ++stepCount;
        else remainder = 0;

        stepCount += (joulesBuffer - remainder) / jStepSize; //number of jSteps which can come entirely from joulesBuffer
        return stepCount;
    }

    @Override
    public boolean allowsExtendedFacing() {
        return false;
    }
}