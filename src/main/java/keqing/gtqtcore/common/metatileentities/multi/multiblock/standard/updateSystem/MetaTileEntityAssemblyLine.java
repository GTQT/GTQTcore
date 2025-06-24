package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.updateSystem;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import codechicken.lib.vec.Vector3;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.IDataAccessHatch;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.recipes.properties.impl.ResearchProperty;
import gregtech.api.util.GTUtility;
import gregtech.api.util.RelativeDirection;
import gregtech.client.particle.GTLaserBeamParticle;
import gregtech.client.particle.GTParticleManager;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiFluidHatch;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metaileentity.GTQTRecipeMapMultiblockController;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.List;
import java.util.function.Function;

import static gregtech.api.util.RelativeDirection.*;

public class MetaTileEntityAssemblyLine extends GTQTRecipeMapMultiblockController {
    private static final ResourceLocation LASER_LOCATION = GTUtility.gregtechId("textures/fx/laser/laser.png");
    private static final ResourceLocation LASER_HEAD_LOCATION = GTUtility
            .gregtechId("textures/fx/laser/laser_start.png");
    private int glass_tier;
    private int laser_tier;
    private int casing_tier;
    @SideOnly(Side.CLIENT)
    private GTLaserBeamParticle[][] beamParticles;
    private int beamCount;

    public MetaTileEntityAssemblyLine(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.ASSEMBLY_LINE_RECIPES
        });
        setTierFlag(true);
        //setTier(auto);
        setMaxParallel(64);
        setMaxParallelFlag(true);
        //setMaxVoltage(auto);
        setMaxVoltageFlag(true);
        //setTimeReduce(none);
        setTimeReduceFlag(false);
        setOverclocking(3);
    }

    protected static TraceabilityPredicate fluidInputPredicate() {
        // block multi-fluid hatches if ordered fluids is enabled
        if (ConfigHolder.machines.orderedFluidAssembly) {
            return metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.IMPORT_FLUIDS).stream()
                    .filter(mte -> !(mte instanceof MetaTileEntityMultiFluidHatch))
                    .toArray(MetaTileEntity[]::new))
                    .setMaxGlobalLimited(4);
        }
        return abilities(MultiblockAbility.IMPORT_FLUIDS);
    }

    protected static TraceabilityPredicate dataHatchPredicate() {
        // if research is enabled, require the data hatch, otherwise use a grate instead
        if (ConfigHolder.machines.enableResearch) {
            return abilities(MultiblockAbility.DATA_ACCESS_HATCH, MultiblockAbility.OPTICAL_DATA_RECEPTION)
                    .setExactLimit(1)
                    .or(TiredTraceabilityPredicate.CP_CASING.get());
        }
        return TiredTraceabilityPredicate.CP_CASING.get();
    }

    private static boolean isRecipeAvailable(Iterable<? extends IDataAccessHatch> hatches,
                                             Recipe recipe) {
        for (IDataAccessHatch hatch : hatches) {
            // creative hatches do not need to check, they always have the recipe
            if (hatch.isCreative()) return true;

            // hatches need to have the recipe available
            if (hatch.isRecipeAvailable(recipe)) return true;
        }
        return false;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAssemblyLine(metaTileEntityId);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object laser_tier = context.get("ZWTieredStats");
        Object casing_tier = context.get("ChemicalPlantCasingTieredStats");
        Object glass_tier = context.get("LGLTieredStats");

        this.laser_tier = GTQTUtil.getOrDefault(() -> laser_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) laser_tier).getIntTier(),
                0);
        this.casing_tier = GTQTUtil.getOrDefault(() -> casing_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) casing_tier).getIntTier(),
                0);
        this.glass_tier = GTQTUtil.getOrDefault(() -> glass_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) glass_tier).getIntTier(),
                0);

        setTier(Math.min(this.casing_tier, this.laser_tier * 2));
        setMaxVoltage(Math.min(Math.min(this.casing_tier, this.laser_tier * 2), 6));
        setTimeReduce((100 - Math.min(this.glass_tier, 10) * 5.0) / 100);

        this.writeCustomData(GTQTValue.UPDATE_TIER14, buf -> buf.writeInt(this.casing_tier));
    }

    @Override
    protected BlockPattern createStructurePattern() {
        FactoryBlockPattern pattern = FactoryBlockPattern.start(FRONT, UP, RIGHT)
                .aisle("FIF", "RTR", "SAG", " Y ")
                .aisle("FIF", "RTR", "DAG", " Y ").setRepeatable(4, 15)
                .aisle("FOF", "RTR", "DAG", " Y ")
                .where('S', selfPredicate())
                .where('F', TiredTraceabilityPredicate.CP_CASING.get()
                        .or(autoAbilities(false, true, false, false, false, false, false))
                        .or(fluidInputPredicate()))
                .where('O', abilities(MultiblockAbility.EXPORT_ITEMS)
                        .addTooltips("gregtech.multiblock.pattern.location_end"))
                .where('Y', TiredTraceabilityPredicate.CP_CASING.get()
                        .or(abilities(MultiblockAbility.INPUT_ENERGY)
                                .setMinGlobalLimited(1)
                                .setMaxGlobalLimited(3)))
                .where('I', metaTileEntities(MetaTileEntities.ITEM_IMPORT_BUS[GTValues.ULV]))
                .where('G', TiredTraceabilityPredicate.CP_CASING.get())
                .where('A', TiredTraceabilityPredicate.CP_ZW_CASING.get())
                .where('R', TiredTraceabilityPredicate.CP_LGLASS.get())
                .where('T', TiredTraceabilityPredicate.CP_CASING.get())
                .where('D', dataHatchPredicate())
                .where(' ', any());
        return pattern.build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("gtqtcore.atier", tier, glass_tier, laser_tier, casing_tier));

    }

    @Override
    protected Function<BlockPos, Integer> multiblockPartSorter() {
        // player's right when looking at the controller, but the controller's left
        return RelativeDirection.LEFT.getSorter(getFrontFacing(), getUpwardsFacing(), isFlipped());
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("casingTier", casing_tier);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        casing_tier = data.getInteger("casingTier");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        switch (this.casing_tier) {
            case (2) -> {
                return Textures.SOLID_STEEL_CASING;
            }
            case (3) -> {
                return Textures.FROST_PROOF_CASING;
            }
            case (4) -> {
                return Textures.CLEAN_STAINLESS_STEEL_CASING;
            }
            case (5) -> {
                return Textures.STABLE_TITANIUM_CASING;
            }
            case (6) -> {
                return Textures.ROBUST_TUNGSTENSTEEL_CASING;
            }
            case (7) -> {
                return GTQTTextures.PD_CASING;
            }
            case (8) -> {
                return GTQTTextures.NQ_CASING;
            }
            case (9) -> {
                return GTQTTextures.ST_CASING;
            }
            case (10) -> {
                return GTQTTextures.AD_CASING;
            }
            default -> {
                return Textures.BRONZE_PLATED_BRICKS;
            }
        }
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(),
                recipeMapWorkable.isActive(), recipeMapWorkable.isWorkingEnabled());
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_MECHANICAL;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public void update() {
        super.update();
        if (ConfigHolder.client.shader.assemblyLineParticles) {
            if (getRecipeMapWorkable().isWorking()) {
                int maxBeams = getAbilities(MultiblockAbility.IMPORT_ITEMS).size() + 1;
                int maxProgress = getRecipeMapWorkable().getMaxProgress();

                // Each beam should be visible for an equal amount of time, which is derived from the maximum number of
                // beams and the maximum progress in the recipe.
                int beamTime = Math.max(1, maxProgress / maxBeams);

                int currentBeamCount = Math.min(maxBeams, getRecipeMapWorkable().getProgress() / beamTime);

                if (currentBeamCount != beamCount) {
                    beamCount = currentBeamCount;
                    writeCustomData(GregtechDataCodes.UPDATE_PARTICLE, this::writeParticles);
                }
            } else if (beamCount != 0) {
                beamCount = 0;
                writeCustomData(GregtechDataCodes.UPDATE_PARTICLE, this::writeParticles);
            }
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.casing_tier);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.casing_tier = buf.readInt();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        if (dataId == GregtechDataCodes.UPDATE_PARTICLE) {
            readParticles(buf);
        } else {
            super.receiveCustomData(dataId, buf);
        }

        if (dataId == GTQTValue.UPDATE_TIER14) {
            this.casing_tier = buf.readInt();
        }
        if (dataId == GTQTValue.REQUIRE_DATA_UPDATE14) {
            this.writeCustomData(GTQTValue.UPDATE_TIER14, buf1 -> buf1.writeInt(this.casing_tier));
        }
    }

    @Override
    public void onRemoval() {
        super.onRemoval();
        if (getWorld().isRemote && beamParticles != null) {
            for (GTLaserBeamParticle[] particle : beamParticles) {
                if (particle[0] != null) {
                    particle[0].setExpired();
                    particle[1].setExpired();
                }
            }
            beamParticles = null;
        }
    }

    private void writeParticles(PacketBuffer buf) {
        buf.writeVarInt(beamCount);
    }

    @SideOnly(Side.CLIENT)
    private void readParticles(PacketBuffer buf) {
        beamCount = buf.readVarInt();
        if (beamParticles == null) {
            beamParticles = new GTLaserBeamParticle[17][2];
        }
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(getPos());

        EnumFacing relativeUp = RelativeDirection.UP.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());
        EnumFacing relativeLeft = RelativeDirection.LEFT.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());
        boolean negativeUp = relativeUp.getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE;

        for (int i = 0; i < beamParticles.length; i++) {
            GTLaserBeamParticle particle = beamParticles[i][0];
            if (i < beamCount && particle == null) {
                pos.setPos(getPos());
                if (negativeUp) {
                    // correct for the position of the block corresponding to its negative side
                    pos.move(relativeUp.getOpposite());
                }
                Vector3 startPos = new Vector3()
                        .add(pos.move(relativeLeft, i))
                        .add( // offset by 0.5 in both non-"upwards" directions
                                relativeUp.getAxis() == EnumFacing.Axis.X ? 0 : 0.5,
                                relativeUp.getAxis() == EnumFacing.Axis.Y ? 0 : 0.5,
                                relativeUp.getAxis() == EnumFacing.Axis.Z ? 0 : 0.5);
                Vector3 endPos = startPos.copy()
                        .subtract(relativeUp.getXOffset(), relativeUp.getYOffset(), relativeUp.getZOffset());

                beamParticles[i][0] = createALParticles(startPos, endPos);

                pos.setPos(getPos());
                if (negativeUp) {
                    pos.move(relativeUp.getOpposite());
                }
                startPos = new Vector3()
                        .add(pos.move(relativeLeft, i)
                                .move(getFrontFacing().getOpposite(), 2))
                        .add( // offset by 0.5 in both non-"upwards" directions
                                relativeUp.getAxis() == EnumFacing.Axis.X ? 0 : 0.5,
                                relativeUp.getAxis() == EnumFacing.Axis.Y ? 0 : 0.5,
                                relativeUp.getAxis() == EnumFacing.Axis.Z ? 0 : 0.5);
                endPos = startPos.copy()
                        .subtract(relativeUp.getXOffset(), relativeUp.getYOffset(), relativeUp.getZOffset());

                beamParticles[i][1] = createALParticles(startPos, endPos);

                // Don't forget to add particles
                GTParticleManager.INSTANCE.addEffect(beamParticles[i][0]);
                GTParticleManager.INSTANCE.addEffect(beamParticles[i][1]);

            } else if (i >= beamCount && particle != null) {
                particle.setExpired();
                beamParticles[i][0] = null;
                beamParticles[i][1].setExpired();
                beamParticles[i][1] = null;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private GTLaserBeamParticle createALParticles(Vector3 startPos, Vector3 endPos) {
        return new GTLaserBeamParticle(this, startPos, endPos)
                .setBody(LASER_LOCATION)
                .setBeamHeight(0.125f)
                // Try commenting or adjusting on the next four lines to see what happens
                .setDoubleVertical(true)
                .setHead(LASER_HEAD_LOCATION)
                .setHeadWidth(0.1f)
                .setEmit(0.2f);
    }

    @Override
    public boolean checkRecipe(Recipe recipe, boolean consumeIfSuccess) {
        if (consumeIfSuccess) return true; // don't check twice
        // check ordered items
        if (ConfigHolder.machines.orderedAssembly) {
            List<GTRecipeInput> inputs = recipe.getInputs();
            List<IItemHandlerModifiable> itemInputInventory = getAbilities(MultiblockAbility.IMPORT_ITEMS);

            // slot count is not enough, so don't try to match it
            if (itemInputInventory.size() < inputs.size()) return false;

            for (int i = 0; i < inputs.size(); i++) {
                if (!inputs.get(i).acceptsStack(itemInputInventory.get(i).getStackInSlot(0))) {
                    return false;
                }
            }

            // check ordered fluids
            if (ConfigHolder.machines.orderedFluidAssembly) {
                inputs = recipe.getFluidInputs();
                List<IFluidTank> fluidInputInventory = getAbilities(MultiblockAbility.IMPORT_FLUIDS);

                // slot count is not enough, so don't try to match it
                if (fluidInputInventory.size() < inputs.size()) return false;

                for (int i = 0; i < inputs.size(); i++) {
                    if (!inputs.get(i).acceptsFluid(fluidInputInventory.get(i).getFluid())) {
                        return false;
                    }
                }
            }
        }

        if (!ConfigHolder.machines.enableResearch || !recipe.hasProperty(ResearchProperty.getInstance())) {
            return super.checkRecipe(recipe, consumeIfSuccess);
        }

        return isRecipeAvailable(getAbilities(MultiblockAbility.DATA_ACCESS_HATCH), recipe) ||
                isRecipeAvailable(getAbilities(MultiblockAbility.OPTICAL_DATA_RECEPTION), recipe);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.machine.assembly_line.tooltip_ordered_both"));
        tooltip.add(I18n.format("gregtech.machine.assembly_line.tooltip_ordered_items"));
        tooltip.add(I18n.format("gregtech.machine.assembly_line.tooltip_ordered_fluids"));
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("gtqt.machine.assembly_line.1"));
        tooltip.add(I18n.format("gtqt.machine.assembly_line.2"));
        tooltip.add(I18n.format("友情提醒，我只能使用ULV级的总线仓口"));
    }
}