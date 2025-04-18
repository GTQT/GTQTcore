package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import codechicken.lib.vec.Vector3;
import gregtech.api.capability.*;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.recipes.properties.impl.ResearchProperty;
import gregtech.api.util.RelativeDirection;
import gregtech.client.particle.GTLaserBeamParticle;
import gregtech.client.particle.GTParticleManager;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiFluidHatch;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.metaileentity.GTQTNoTierMultiblockController;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static gregtech.api.util.GTUtility.getTierByVoltage;
import static gregtech.api.util.GTUtility.gregtechId;
import static keqing.gtqtcore.api.utils.GTQTUtil.getAccelerateByCWU;
import static keqing.gtqtcore.common.block.blocks.BlockActiveUniqueCasing.ActiveCasingType.ADVANCED_ASSEMBLY_CONTROL_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockActiveUniqueCasing.ActiveCasingType.ADVANCED_ASSEMBLY_LINE_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4.TurbineCasingType.ADVANCED_FILTER_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4.TurbineCasingType.IRIDIUM_CASING;

public class MetaTileEntityAdvancedAssemblyLine extends GTQTNoTierMultiblockController implements IOpticalComputationReceiver {
    private static final ResourceLocation LASER_LOCATION = gregtechId("textures/fx/laser/laser.png");
    private static final ResourceLocation LASER_HEAD_LOCATION = gregtechId("textures/fx/laser/laser_start.png");
    int requestCWUt;
    private IOpticalComputationProvider computationProvider;
    @SideOnly(Side.CLIENT)
    private GTLaserBeamParticle[][] beamParticles;
    private int beamCount;

    public MetaTileEntityAdvancedAssemblyLine(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{RecipeMaps.ASSEMBLY_LINE_RECIPES});
        this.recipeMapWorkable = new AdvancedAssemblyLineRecipeLogic(this);

        //setMaxParallel(auto);
        setMaxParallelFlag(true);

        //setTimeReduce(auto);
        setTimeReduceFlag(true);

        setOverclocking(3.0);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(IRIDIUM_CASING);
    }

    private static IBlockState getSecondCasingState() {
        return GTQTMetaBlocks.blockActiveUniqueCasing.getState(ADVANCED_ASSEMBLY_LINE_CASING);
    }

    private static IBlockState getUniqueCasingState() {
        return GTQTMetaBlocks.blockActiveUniqueCasing.getState(ADVANCED_ASSEMBLY_CONTROL_CASING);
    }

    private static IBlockState getGrateState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(ADVANCED_FILTER_CASING);
    }

    private static IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS);
    }

    @SuppressWarnings("unchecked")
    protected static TraceabilityPredicate fluidInputPredicate() {
        return ConfigHolder.machines.orderedFluidAssembly ? metaTileEntities((MetaTileEntity[]) ((List) MultiblockAbility.REGISTRY.get(MultiblockAbility.IMPORT_FLUIDS)).stream()
                .filter((mte) -> !(mte instanceof MetaTileEntityMultiFluidHatch))
                .toArray(MetaTileEntity[]::new))
                .setMaxGlobalLimited(4) : abilities(MultiblockAbility.IMPORT_FLUIDS);
    }

    protected static TraceabilityPredicate dataHatchPredicate() {
        return ConfigHolder.machines.enableResearch ? abilities(MultiblockAbility.DATA_ACCESS_HATCH, MultiblockAbility.OPTICAL_DATA_RECEPTION)
                .setExactLimit(1)
                .or(states(getGrateState())) : states(getGrateState());
    }

    private static boolean isRecipeAvailable(Iterable<? extends IDataAccessHatch> hatches, Recipe recipe) {
        Iterator<? extends IDataAccessHatch> var2 = hatches.iterator();

        IDataAccessHatch hatch;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            hatch = var2.next();
            if (hatch.isCreative()) {
                return true;
            }
        } while (!hatch.isRecipeAvailable(recipe));

        return true;
    }

    @Override
    public void updateFormedValid() {
        super.updateFormedValid();
        if (isStructureFormed() && isActive()) {
            requestCWUt = computationProvider.requestCWUt(2048, false);
        }
    }

    @Override
    public IOpticalComputationProvider getComputationProvider() {
        return this.computationProvider;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAdvancedAssemblyLine(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.FRONT, RelativeDirection.UP, RelativeDirection.RIGHT)
                .aisle("FIF", "RTR", "SAG", " Y ")
                .aisle("FIF", "RTR", "DAG", " Y ").setRepeatable(3, 15)
                .aisle("FOF", "RTR", "DAG", " Y ")
                .where('S', this.selfPredicate())
                .where('F', states(getCasingState())
                        .or(autoAbilities(false, true, false, false, false, false, false))
                        .or(fluidInputPredicate()))
                .where('O', abilities(MultiblockAbility.EXPORT_ITEMS)
                        .addTooltips("gregtech.multiblock.pattern.location_end"))
                .where('Y', states(getCasingState())
                        .or(abilities(MultiblockAbility.INPUT_ENERGY)
                                .setMinGlobalLimited(1)
                                .setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.INPUT_LASER)
                                .setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1))
                )
                .where('I', abilities(MultiblockAbility.IMPORT_ITEMS))
                .where('G', states(getGrateState()))
                .where('A', states(getUniqueCasingState()))
                .where('R', states(getGlassState()))
                .where('T', states(getSecondCasingState()))
                .where('D', dataHatchPredicate())
                .where(' ', any())
                .build();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);

        List<IOpticalComputationHatch> providers = this.getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
        if (providers != null && !providers.isEmpty()) {
            this.computationProvider = providers.get(0);
        }

        setMaxParallel(4 * getTierByVoltage(recipeMapWorkable.getMaxVoltage()));
        setTimeReduce(0.8);
    }

    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    protected Function<BlockPos, Integer> multiblockPartSorter() {
        return RelativeDirection.LEFT.getSorter(this.getFrontFacing(), this.getUpwardsFacing(), this.isFlipped());
    }

    @Override
    protected void initializeAbilities() {
        this.inputInventory = new ItemHandlerList(this.getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.inputFluidInventory = new FluidTankList(this.allowSameFluidFillForOutputs(), this.getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputInventory = new ItemHandlerList(this.getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.outputFluidInventory = new FluidTankList(this.allowSameFluidFillForOutputs(), this.getAbilities(MultiblockAbility.EXPORT_FLUIDS));
        List<IEnergyContainer> energyContainer = new ArrayList<>(this.getAbilities(MultiblockAbility.INPUT_ENERGY));
        energyContainer.addAll(this.getAbilities(MultiblockAbility.INPUT_LASER));
        this.energyContainer = new EnergyContainerList(energyContainer);
    }

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        if (iMultiblockPart != null) {
            return iMultiblockPart instanceof IDataInfoProvider ? GTQTTextures.ADVANCED_FILTER_IRIDIUM_FRONT : GTQTTextures.IRIDIUM_CASING;
        } else {
            return this.isStructureFormed() ? GTQTTextures.ADVANCED_FILTER_IRIDIUM_FRONT : GTQTTextures.IRIDIUM_CASING;
        }
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState,
                                     Matrix4 translation,
                                     IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, this.getFrontFacing(), this.recipeMapWorkable.isActive(), this.recipeMapWorkable.isWorkingEnabled());
    }

    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_MECHANICAL;
    }

    public void update() {
        super.update();
        if (ConfigHolder.client.shader.assemblyLineParticles) {
            if (this.getRecipeMapWorkable().isWorking()) {
                int maxBeams = this.getAbilities(MultiblockAbility.IMPORT_ITEMS).size() + 1;
                int maxProgress = this.getRecipeMapWorkable().getMaxProgress();
                int beamTime = Math.max(1, maxProgress / maxBeams);
                int currentBeamCount = Math.min(maxBeams, this.getRecipeMapWorkable().getProgress() / beamTime);
                if (currentBeamCount != this.beamCount) {
                    this.beamCount = currentBeamCount;
                    this.writeCustomData(GregtechDataCodes.UPDATE_PARTICLE, this::writeParticles);
                }
            } else if (this.beamCount != 0) {
                this.beamCount = 0;
                this.writeCustomData(GregtechDataCodes.UPDATE_PARTICLE, this::writeParticles);
            }
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        this.writeParticles(buf);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.readParticles(buf);
    }

    @Override
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

            for (GTLaserBeamParticle[] particle : var1) {
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
    }

    @SideOnly(Side.CLIENT)
    private void readParticles(PacketBuffer buf) {
        this.beamCount = buf.readVarInt();
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
        return (new GTLaserBeamParticle(this, startPos, endPos)).setBody(LASER_LOCATION).setBeamHeight(0.125F).setDoubleVertical(true).setHead(LASER_HEAD_LOCATION).setHeadWidth(0.1F).setEmit(0.2F);
    }

    @Override
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

                for (int i = 0; i < inputs.size(); i++) {
                    IItemHandlerModifiable inputHatch = itemInputInventory.get(i);
                    boolean isEmpty = true;
                    for (int j = 0; j < inputHatch.getSlots(); j++) {
                        ItemStack item = inputHatch.getStackInSlot(j);
                        if (item.isEmpty())
                            continue;
                        isEmpty = false;
                        if (!(inputs.get(i)).acceptsStack(item)) {
                            return false;
                        }
                    }

                    if (isEmpty) {
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
                        if (!(inputs.get(i)).acceptsFluid((fluidInputInventory.get(i)).getFluid())) {
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

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.machine.advanced_assembly_line.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.advanced_assembly_line.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.advanced_assembly_line.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.machine.advanced_assembly_line.tooltip.4"));
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.acc.tooltip"));
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.acc.tooltip"));
        if (ConfigHolder.machines.orderedAssembly && ConfigHolder.machines.orderedFluidAssembly) {
            tooltip.add(I18n.format("gregtech.machine.assembly_line.tooltip_ordered_both"));
        } else if (ConfigHolder.machines.orderedAssembly) {
            tooltip.add(I18n.format("gregtech.machine.assembly_line.tooltip_ordered_items"));
        } else if (ConfigHolder.machines.orderedFluidAssembly) {
            tooltip.add(I18n.format("gregtech.machine.assembly_line.tooltip_ordered_fluids"));
        }
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class AdvancedAssemblyLineRecipeLogic extends MultiblockRecipeLogic {

        public AdvancedAssemblyLineRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            super.setMaxProgress((int) (maxProgress * getAccelerateByCWU(requestCWUt)));
        }
    }
}