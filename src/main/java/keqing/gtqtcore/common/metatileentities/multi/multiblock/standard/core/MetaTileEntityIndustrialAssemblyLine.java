package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.core;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import codechicken.lib.vec.Vector3;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.IDataAccessHatch;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.recipes.recipeproperties.ResearchProperty;
import gregtech.api.util.GTUtility;
import gregtech.api.util.RelativeDirection;
import gregtech.client.particle.GTLaserBeamParticle;
import gregtech.client.particle.GTParticleManager;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiFluidHatch;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockCore;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static gregtech.api.GTValues.UV;
import static gregtech.api.GTValues.VA;
import static keqing.gtqtcore.common.block.blocks.BlockActiveUniqueCasing.ActiveCasingType.ADVANCED_ASSEMBLY_CONTROL_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockActiveUniqueCasing.ActiveCasingType.ADVANCED_ASSEMBLY_LINE_CASING;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing.TurbineCasingType.ADVANCED_FILTER_CASING;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing.TurbineCasingType.IRIDIUM_CASING;

public class MetaTileEntityIndustrialAssemblyLine extends GTQTMultiblockCore {

    private static final ResourceLocation LASER_LOCATION = GTUtility.gregtechId("textures/fx/laser/laser.png");
    private static final ResourceLocation LASER_HEAD_LOCATION = GTUtility.gregtechId("textures/fx/laser/laser_start.png");
    @SideOnly(Side.CLIENT)
    private GTLaserBeamParticle[][] beamParticles;
    private int beamCount;
    private int beamTime;

    public MetaTileEntityIndustrialAssemblyLine(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.ASSEMBLY_LINE_RECIPES,
                RecipeMaps.ASSEMBLER_RECIPES
        });
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(IRIDIUM_CASING);
    }

    private static IBlockState getSecondCasingState() {
        return GTQTMetaBlocks.ACTIVE_UNIQUE_CASING.getState(ADVANCED_ASSEMBLY_LINE_CASING);
    }

    private static IBlockState getUniqueCasingState() {
        return GTQTMetaBlocks.ACTIVE_UNIQUE_CASING.getState(ADVANCED_ASSEMBLY_CONTROL_CASING);
    }

    private static IBlockState getGrateState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(ADVANCED_FILTER_CASING);
    }

    private static IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS);
    }

    protected static TraceabilityPredicate fluidInputPredicate() {
        return ConfigHolder.machines.orderedFluidAssembly ? metaTileEntities((MetaTileEntity[]) ((List) MultiblockAbility.REGISTRY.get(MultiblockAbility.IMPORT_FLUIDS)).stream().filter((mte) -> !(mte instanceof MetaTileEntityMultiFluidHatch)).toArray((x$0) -> new MetaTileEntity[x$0])).setMaxGlobalLimited(4) : abilities(MultiblockAbility.IMPORT_FLUIDS);
    }

    protected static TraceabilityPredicate dataHatchPredicate() {
        return ConfigHolder.machines.enableResearch ? abilities(MultiblockAbility.DATA_ACCESS_HATCH, MultiblockAbility.OPTICAL_DATA_RECEPTION).setExactLimit(1).or(states(getGrateState())) : states(getGrateState());
    }

    private static boolean isRecipeAvailable(Iterable<? extends IDataAccessHatch> hatches, Recipe recipe) {
        Iterator var2 = hatches.iterator();

        IDataAccessHatch hatch;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            hatch = (IDataAccessHatch) var2.next();
            if (hatch.isCreative()) {
                return true;
            }
        } while (!hatch.isRecipeAvailable(recipe));

        return true;
    }

    @Override
    public int getMinVa() {
        return VA[UV];
    }

    @Override
    public int getCoreNum() {
        return 4;
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityIndustrialAssemblyLine(this.metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.FRONT, RelativeDirection.UP, RelativeDirection.RIGHT)
                .aisle("FIF", "RTR", "SAG")
                .aisle("FIF", "RTR", "DAG").setRepeatable(3, 15)
                .aisle("FOF", "RTR", "DAG")
                .where('S', this.selfPredicate())
                .where('F', states(getCasingState())
                        .or(autoAbilities(false, true, false, false, false, false, false))
                        .or(fluidInputPredicate())
                        .or(abilities(MultiblockAbility.INPUT_ENERGY)
                                .setMinGlobalLimited(1)
                                .setMaxGlobalLimited(3)))
                .where('O', abilities(MultiblockAbility.EXPORT_ITEMS)
                        .addTooltips("gregtech.multiblock.pattern.location_end"))
                .where('I', abilities(MultiblockAbility.IMPORT_ITEMS))
                .where('G', states(getGrateState()))
                .where('A', states(getUniqueCasingState()))
                .where('R', states(getGlassState()))
                .where('T', states(getSecondCasingState()))
                .where('D', dataHatchPredicate())
                .where(' ', any())
                .build();
    }

    protected Function<BlockPos, Integer> multiblockPartSorter() {
        return RelativeDirection.LEFT.getSorter(this.getFrontFacing(), this.getUpwardsFacing(), this.isFlipped());
    }

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        if (iMultiblockPart != null) {
            return iMultiblockPart instanceof IDataInfoProvider ? GTQTTextures.ADVANCED_FILTER_IRIDIUM_FRONT : GTQTTextures.IRIDIUM_CASING;
        } else {
            return this.isStructureFormed() ? GTQTTextures.ADVANCED_FILTER_IRIDIUM_FRONT : GTQTTextures.IRIDIUM_CASING;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.recipeMapWorkable.isWorkingEnabled(),
                isActive());
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    public void update() {
        super.update();
        if (ConfigHolder.client.shader.assemblyLineParticles) {
            if (this.getRecipeMapWorkable().isWorking()) {
                int maxBeams = this.getAbilities(MultiblockAbility.IMPORT_ITEMS).size() + 1;
                int maxProgress = this.getRecipeMapWorkable().getMaxProgress();
                int beamTime = Math.max(1, maxProgress / maxBeams);
                int beamCount = Math.min(maxBeams, this.getRecipeMapWorkable().getProgress() / beamTime + 1);
                if (beamCount != this.beamCount) {
                    if (beamCount < this.beamCount) {
                        this.beamCount = Math.max(0, beamCount - 1);
                        this.writeCustomData(GregtechDataCodes.UPDATE_PARTICLE, this::writeParticles);
                    }

                    this.beamTime = beamTime;
                    this.beamCount = beamCount;
                    this.writeCustomData(GregtechDataCodes.UPDATE_PARTICLE, this::writeParticles);
                }
            } else if (this.beamCount != 0) {
                this.beamTime = 0;
                this.beamCount = 0;
                this.writeCustomData(GregtechDataCodes.UPDATE_PARTICLE, this::writeParticles);
            }
        }

    }

    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        this.writeParticles(buf);
    }

    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.readParticles(buf);
    }

    public void receiveCustomData(int dataId, PacketBuffer buf) {
        if (dataId == GregtechDataCodes.UPDATE_PARTICLE) {
            this.readParticles(buf);
        } else {
            super.receiveCustomData(dataId, buf);
        }

    }

    public void onRemoval() {
        super.onRemoval();
        if (this.getWorld().isRemote && this.beamParticles != null) {
            GTLaserBeamParticle[][] var1 = this.beamParticles;
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                GTLaserBeamParticle[] particle = var1[var3];
                if (particle[0] != null) {
                    particle[0].setExpired();
                    particle[1].setExpired();
                }
            }

            this.beamParticles = null;
        }

    }

    private void writeParticles(PacketBuffer buf) {
        buf.writeVarInt(this.beamCount);
        buf.writeVarInt(this.beamTime);
    }

    @SideOnly(Side.CLIENT)
    private void readParticles(PacketBuffer buf) {
        this.beamCount = buf.readVarInt();
        this.beamTime = buf.readVarInt();
        if (this.beamParticles == null) {
            this.beamParticles = new GTLaserBeamParticle[17][2];
        }

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(this.getPos());
        EnumFacing relativeUp = RelativeDirection.UP.getRelativeFacing(this.getFrontFacing(), this.getUpwardsFacing(), this.isFlipped());
        EnumFacing relativeLeft = RelativeDirection.LEFT.getRelativeFacing(this.getFrontFacing(), this.getUpwardsFacing(), this.isFlipped());
        boolean negativeUp = relativeUp.getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE;

        for (int i = 0; i < this.beamParticles.length; ++i) {
            GTLaserBeamParticle particle = this.beamParticles[i][0];
            if (i < this.beamCount && particle == null) {
                pos.setPos(this.getPos());
                if (negativeUp) {
                    pos.move(relativeUp.getOpposite());
                }

                Vector3 startPos = (new Vector3()).add(pos.move(relativeLeft, i)).add(relativeUp.getAxis() == EnumFacing.Axis.X ? 0.0 : 0.5, relativeUp.getAxis() == EnumFacing.Axis.Y ? 0.0 : 0.5, relativeUp.getAxis() == EnumFacing.Axis.Z ? 0.0 : 0.5);
                Vector3 endPos = startPos.copy().subtract(relativeUp.getXOffset(), relativeUp.getYOffset(), relativeUp.getZOffset());
                this.beamParticles[i][0] = this.createALParticles(startPos, endPos);
                pos.setPos(this.getPos());
                if (negativeUp) {
                    pos.move(relativeUp.getOpposite());
                }

                startPos = (new Vector3()).add(pos.move(relativeLeft, i).move(this.getFrontFacing().getOpposite(), 2)).add(relativeUp.getAxis() == EnumFacing.Axis.X ? 0.0 : 0.5, relativeUp.getAxis() == EnumFacing.Axis.Y ? 0.0 : 0.5, relativeUp.getAxis() == EnumFacing.Axis.Z ? 0.0 : 0.5);
                endPos = startPos.copy().subtract(relativeUp.getXOffset(), relativeUp.getYOffset(), relativeUp.getZOffset());
                this.beamParticles[i][1] = this.createALParticles(startPos, endPos);
                GTParticleManager.INSTANCE.addEffect(this.beamParticles[i][0]);
                GTParticleManager.INSTANCE.addEffect(this.beamParticles[i][1]);
            } else if (i >= this.beamCount && particle != null) {
                particle.setExpired();
                this.beamParticles[i][0] = null;
                this.beamParticles[i][1].setExpired();
                this.beamParticles[i][1] = null;
            }
        }

    }

    @SideOnly(Side.CLIENT)
    private GTLaserBeamParticle createALParticles(Vector3 startPos, Vector3 endPos) {
        return (new GTLaserBeamParticle(this, startPos, endPos, this.beamTime)).setBody(LASER_LOCATION).setBeamHeight(0.125F).setDoubleVertical(true).setHead(LASER_HEAD_LOCATION).setHeadWidth(0.1F).setEmit(0.2F);
    }

    public boolean checkRecipe(Recipe recipe, boolean consumeIfSuccess) {
        if (consumeIfSuccess) {
            return true;
        } else {
            if (ConfigHolder.machines.orderedAssembly) {
                List<GTRecipeInput> inputs = recipe.getInputs();
                List<IItemHandlerModifiable> itemInputInventory = this.getAbilities(MultiblockAbility.IMPORT_ITEMS);
                if (itemInputInventory.size() < inputs.size()) {
                    return false;
                }

                for (int i = 0; i < inputs.size(); ++i) {
                    if (!inputs.get(i).acceptsStack(itemInputInventory.get(i).getStackInSlot(0))) {
                        return false;
                    }
                }

                if (ConfigHolder.machines.orderedFluidAssembly) {
                    inputs = recipe.getFluidInputs();
                    List<IFluidTank> fluidInputInventory = this.getAbilities(MultiblockAbility.IMPORT_FLUIDS);
                    if (fluidInputInventory.size() < inputs.size()) {
                        return false;
                    }

                    for (int i = 0; i < inputs.size(); ++i) {
                        if (!inputs.get(i).acceptsFluid(fluidInputInventory.get(i).getFluid())) {
                            return false;
                        }
                    }
                }
            }

            if (ConfigHolder.machines.enableResearch && recipe.hasProperty(ResearchProperty.getInstance())) {
                return isRecipeAvailable(this.getAbilities(MultiblockAbility.DATA_ACCESS_HATCH), recipe) || isRecipeAvailable(this.getAbilities(MultiblockAbility.OPTICAL_DATA_RECEPTION), recipe);
            } else {
                return super.checkRecipe(recipe, consumeIfSuccess);
            }
        }
    }

    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("地铁猫猫看手机", new Object[0]));
        super.addInformation(stack, player, tooltip, advanced);
        if (ConfigHolder.machines.orderedAssembly && ConfigHolder.machines.orderedFluidAssembly) {
            tooltip.add(I18n.format("gregtech.machine.assembly_line.tooltip_ordered_both"));
        } else if (ConfigHolder.machines.orderedAssembly) {
            tooltip.add(I18n.format("gregtech.machine.assembly_line.tooltip_ordered_items"));
        } else if (ConfigHolder.machines.orderedFluidAssembly) {
            tooltip.add(I18n.format("gregtech.machine.assembly_line.tooltip_ordered_fluids"));
        }

    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }
}


