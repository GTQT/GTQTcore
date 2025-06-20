package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.giantEquipment;

import com.cleanroommc.modularui.api.drawable.IDrawable;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.value.sync.LongSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.layout.Column;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.AtomicDouble;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.ImageCycleButtonWidget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.IndicatorImageWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIFactory;
import gregtech.api.metatileentity.multiblock.ui.TemplateBarBuilder;
import gregtech.api.mui.GTGuiTextures;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.logic.OCParams;
import gregtech.api.recipes.properties.RecipePropertyStorage;
import gregtech.api.recipes.properties.impl.FusionEUToStartProperty;
import gregtech.api.util.KeyUtil;
import gregtech.api.util.RelativeDirection;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.api.util.interpolate.Eases;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.IRenderSetup;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.shader.postprocessing.BloomEffect;
import gregtech.client.shader.postprocessing.BloomType;
import gregtech.client.utils.*;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityFusionReactor;
import keqing.gtqtcore.api.gui.GTQTGuiTextures;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockCompressedFusionReactor;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleSupplier;
import java.util.function.UnaryOperator;

import static gregtech.api.GTValues.UEV;
import static gregtech.api.GTValues.UHV;
import static gregtech.api.recipes.logic.OverclockingLogic.PERFECT_HALF_DURATION_FACTOR;
import static gregtech.api.recipes.logic.OverclockingLogic.PERFECT_HALF_VOLTAGE_FACTOR;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.ADVANCED_FUSION_REACTOR;

public class MetaTileEntityAdvancedFusionReactor extends RecipeMapMultiblockController
        implements IFastRenderMetaTileEntity, IBloomEffect, ProgressBarMultiblock {

    protected static final int NO_COLOR = 0;

    private final int tier;
    private EnergyContainerList inputEnergyContainers;
    private long heat = 0; // defined in TileEntityFusionReactor but serialized in FusionRecipeLogic
    private int fusionRingColor = NO_COLOR;

    @SideOnly(Side.CLIENT)
    private boolean registeredBloomRenderTicket;

    public MetaTileEntityAdvancedFusionReactor(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, RecipeMaps.FUSION_RECIPES);
        this.recipeMapWorkable = new FusionRecipeLogic(this);
        this.tier = tier;
        this.energyContainer = new EnergyContainerHandler(this, 0, 0, 0, 0, 0) {

            @NotNull
            @Override
            public String getName() {
                return GregtechDataCodes.FUSION_REACTOR_ENERGY_CONTAINER_TRAIT;
            }
        };
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAdvancedFusionReactor(metaTileEntityId, tier);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("###############", "######OGO######", "###############")
                .aisle("######ICI######", "####GGAAAGG####", "######ICI######")
                .aisle("####CC###CC####", "###EAAOGOAAE###", "####CC###CC####")
                .aisle("###C#######C###", "##EKEG###GEKE##", "###C#######C###")
                .aisle("##C#########C##", "#GAE#######EAG#", "##C#########C##")
                .aisle("##C#########C##", "#GAG#######GAG#", "##C#########C##")
                .aisle("#I###########I#", "OAO#########OAO", "#I###########I#")
                .aisle("#C###########C#", "GAG#########GAG", "#C###########C#")
                .aisle("#I###########I#", "OAO#########OAO", "#I###########I#")
                .aisle("##C#########C##", "#GAG#######GAG#", "##C#########C##")
                .aisle("##C#########C##", "#GAE#######EAG#", "##C#########C##")
                .aisle("###C#######C###", "##EKEG###GEKE##", "###C#######C###")
                .aisle("####CC###CC####", "###EAAOGOAAE###", "####CC###CC####")
                .aisle("######ICI######", "####GGAAAGG####", "######ICI######")
                .aisle("###############", "######OSO######", "###############")
                .where('S', selfPredicate())
                .where('G', states(getCasingState(), getGlassState()))
                .where('E',
                        states(getCasingState(), getGlassState()).or(metaTileEntities(Arrays
                                .stream(MetaTileEntities.ENERGY_INPUT_HATCH)
                                .filter(mte -> mte != null && tier <= mte.getTier() && mte.getTier() <= GTValues.UIV)
                                .toArray(MetaTileEntity[]::new))
                                .setMinGlobalLimited(1).setPreviewCount(16)))
                .where('C', states(getCasingState()))
                .where('K', states(getCoilState()))
                .where('O', states(getCasingState(), getGlassState()).or(abilities(MultiblockAbility.EXPORT_FLUIDS)))
                .where('A', air())
                .where('I',
                        states(getCasingState()).or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(2)))
                .where('#', any())
                .build();
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        List<MultiblockShapeInfo> shapeInfos = new ArrayList<>();

        MultiblockShapeInfo.Builder baseBuilder = MultiblockShapeInfo.builder()
                .aisle("###############", "######WGW######", "###############")
                .aisle("######DCD######", "####GG###GG####", "######UCU######")
                .aisle("####CC###CC####", "###w##EGE##s###", "####CC###CC####")
                .aisle("###C#######C###", "##nKeG###GeKn##", "###C#######C###")
                .aisle("##C#########C##", "#G#s#######w#G#", "##C#########C##")
                .aisle("##C#########C##", "#G#G#######G#G#", "##C#########C##")
                .aisle("#D###########D#", "N#S#########N#S", "#U###########U#")
                .aisle("#C###########C#", "G#G#########G#G", "#C###########C#")
                .aisle("#D###########D#", "N#S#########N#S", "#U###########U#")
                .aisle("##C#########C##", "#G#G#######G#G#", "##C#########C##")
                .aisle("##C#########C##", "#G#s#######w#G#", "##C#########C##")
                .aisle("###C#######C###", "##eKnG###GnKe##", "###C#######C###")
                .aisle("####CC###CC####", "###w##WGW##s###", "####CC###CC####")
                .aisle("######DCD######", "####GG###GG####", "######UCU######")
                .aisle("###############", "######EME######", "###############")
                .where('M', ADVANCED_FUSION_REACTOR[tier - GTValues.UHV], EnumFacing.SOUTH)
                .where('C', getCasingState())
                .where('G', getGlassState())
                .where('K', getCoilState())
                .where('W', MetaTileEntities.FLUID_EXPORT_HATCH[tier], EnumFacing.NORTH)
                .where('E', MetaTileEntities.FLUID_EXPORT_HATCH[tier], EnumFacing.SOUTH)
                .where('S', MetaTileEntities.FLUID_EXPORT_HATCH[tier], EnumFacing.EAST)
                .where('N', MetaTileEntities.FLUID_EXPORT_HATCH[tier], EnumFacing.WEST)
                .where('w', MetaTileEntities.ENERGY_INPUT_HATCH[tier], EnumFacing.WEST)
                .where('e', MetaTileEntities.ENERGY_INPUT_HATCH[tier], EnumFacing.SOUTH)
                .where('s', MetaTileEntities.ENERGY_INPUT_HATCH[tier], EnumFacing.EAST)
                .where('n', MetaTileEntities.ENERGY_INPUT_HATCH[tier], EnumFacing.NORTH)
                .where('U', MetaTileEntities.FLUID_IMPORT_HATCH[tier], EnumFacing.UP)
                .where('D', MetaTileEntities.FLUID_IMPORT_HATCH[tier], EnumFacing.DOWN)
                .where('#', Blocks.AIR.getDefaultState());

        shapeInfos.add(baseBuilder.shallowCopy()
                .where('G', getCasingState())
                .build());
        shapeInfos.add(baseBuilder.build());
        return shapeInfos;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        if (this.recipeMapWorkable.isActive()) {
            if (tier == UHV) {
                return GTQTTextures.ADVANCED_ACTIVE_FUSION_TEXTURE;
            } else if (tier == UEV) {
                return GTQTTextures.ULTIMATE_ACTIVE_FUSION_TEXTURE;
            } else {
                return GTQTTextures.END_FUSION_TEXTURE;
            }
        } else {
            if (tier == UHV) {
                return GTQTTextures.ADVANCED_FUSION_TEXTURE;
            } else if (tier == UEV) {
                return GTQTTextures.ULTIMATE_FUSION_TEXTURE;
            } else {
                return GTQTTextures.END_FUSION_TEXTURE;
            }
        }
    }

    private IBlockState getGlassState() {
        if (tier == GTValues.UHV)
            return GTQTMetaBlocks.blockMultiblockGlass.getState(BlockMultiblockGlass.CasingType.TECH_FUSION_GLASS_IV);
        if (tier == UEV)
            return GTQTMetaBlocks.blockMultiblockGlass.getState(BlockMultiblockGlass.CasingType.TECH_FUSION_GLASS_V);
        return GTQTMetaBlocks.blockMultiblockGlass.getState(BlockMultiblockGlass.CasingType.TECH_FUSION_GLASS_VI);
    }

    private IBlockState getCasingState() {
        if (tier == GTValues.UHV)
            return GTQTMetaBlocks.blockCompressedFusionReactor.getState(BlockCompressedFusionReactor.CasingType.CASING_FUSION_MKIV);
        if (tier == GTValues.UEV)
            return GTQTMetaBlocks.blockCompressedFusionReactor.getState(BlockCompressedFusionReactor.CasingType.CASING_FUSION_MKV);
        return GTQTMetaBlocks.blockCompressedFusionReactor.getState(BlockCompressedFusionReactor.CasingType.CASING_FUSION_MKVI);
    }

    private IBlockState getCoilState() {
        if (tier == GTValues.UHV)
            return GTQTMetaBlocks.blockCompressedFusionReactor.getState(BlockCompressedFusionReactor.CasingType.FUSION_COIL_MKII);
        if (tier == GTValues.UEV)
            return GTQTMetaBlocks.blockCompressedFusionReactor.getState(BlockCompressedFusionReactor.CasingType.FUSION_COIL_MKIII);
        return GTQTMetaBlocks.blockCompressedFusionReactor.getState(BlockCompressedFusionReactor.CasingType.FUSION_COIL_MKIV);
    }

    protected int getFusionRingColor() {
        return this.fusionRingColor;
    }

    protected void setFusionRingColor(int fusionRingColor) {
        if (this.fusionRingColor != fusionRingColor) {
            this.fusionRingColor = fusionRingColor;
            writeCustomData(GregtechDataCodes.UPDATE_COLOR, buf -> buf.writeVarInt(fusionRingColor));
        }
    }

    protected boolean hasFusionRingColor() {
        return this.fusionRingColor != NO_COLOR;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        long energyStored = this.energyContainer.getEnergyStored();
        super.formStructure(context);
        this.initializeAbilities();
        ((EnergyContainerHandler) this.energyContainer).setEnergyStored(energyStored);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.energyContainer = new EnergyContainerHandler(this, 0, 0, 0, 0, 0) {
            @Override
            public String getName() {
                return GregtechDataCodes.FUSION_REACTOR_ENERGY_CONTAINER_TRAIT;
            }
        };
        this.inputEnergyContainers = new EnergyContainerList(Lists.newArrayList());
        this.heat = 0;
        this.setFusionRingColor(NO_COLOR);
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        List<IEnergyContainer> energyInputs = getAbilities(MultiblockAbility.INPUT_ENERGY);
        this.inputEnergyContainers = new EnergyContainerList(energyInputs);
        long euCapacity = calculateEnergyStorageFactor(energyInputs.size());
        this.energyContainer = new EnergyContainerHandler(this, euCapacity, GTValues.V[tier], 0, 0, 0) {

            @Override
            public String getName() {
                return GregtechDataCodes.FUSION_REACTOR_ENERGY_CONTAINER_TRAIT;
            }
        };
    }

    private long calculateEnergyStorageFactor(int energyInputAmount) {
        return energyInputAmount * (long) Math.pow(2, tier - 6) * 10000000L;
    }

    @Override
    protected void updateFormedValid() {
        if (this.inputEnergyContainers.getEnergyStored() > 0) {
            long energyAdded = this.energyContainer.addEnergy(this.inputEnergyContainers.getEnergyStored());
            if (energyAdded > 0) this.inputEnergyContainers.removeEnergy(energyAdded);
        }
        super.updateFormedValid();
        if (recipeMapWorkable.isWorking() && fusionRingColor == NO_COLOR) {
            if (recipeMapWorkable.getPreviousRecipe() != null &&
                    !recipeMapWorkable.getPreviousRecipe().getFluidOutputs().isEmpty()) {
                setFusionRingColor(0xFF000000 |
                        recipeMapWorkable.getPreviousRecipe().getFluidOutputs().get(0).getFluid().getColor());
            }
        } else if (!recipeMapWorkable.isWorking() && isStructureFormed()) {
            setFusionRingColor(NO_COLOR);
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeVarInt(this.fusionRingColor);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.fusionRingColor = buf.readVarInt();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        if (dataId == GregtechDataCodes.UPDATE_COLOR) {
            this.fusionRingColor = buf.readVarInt();
        } else {
            super.receiveCustomData(dataId, buf);
        }
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(
                I18n.format("gregtech.machine.fusion_reactor.capacity", calculateEnergyStorageFactor(16) / 1000000L));
        tooltip.add(I18n.format("gregtech.machine.fusion_reactor.overclocking"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    public long getHeat() {
        return heat;
    }











    @Override
    protected MultiblockUIFactory createUIFactory() {
        IDrawable title;
        if (tier == GTValues.UHV) {
            // MK1
            title = GTQTGuiTextures.FUSION_REACTOR_MK4_TITLE;
        } else if (tier == GTValues.UEV) {
            // MK2
            title = GTQTGuiTextures.FUSION_REACTOR_MK5_TITLE;
        } else {
            // MK3
            title = GTQTGuiTextures.FUSION_REACTOR_MK6_TITLE;
        }

        DoubleSyncValue progress = new DoubleSyncValue(recipeMapWorkable::getProgressPercent);
        return new MultiblockUIFactory(this)
                .setScreenHeight(138)
                .disableDisplayText()
                .addScreenChildren((parent, syncManager) -> {
                    var status = MultiblockUIFactory.builder("status", syncManager);
                    status.setAction(b -> b.structureFormed(true)
                            .setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                            .addWorkingStatusLine());
                    parent.child(new Column()
                            .padding(4)
                            .expanded()
                            .child(title.asWidget()
                                    .marginBottom(8)
                                    .size(69, 12))
                            .child(new com.cleanroommc.modularui.widgets.ProgressWidget()
                                    .size(77, 77)
                                    .tooltipAutoUpdate(true)
                                    .tooltipBuilder(status::build)
                                    .background(GTGuiTextures.FUSION_DIAGRAM.asIcon()
                                            .size(89, 101)
                                            .marginTop(11))
                                    .direction(com.cleanroommc.modularui.widgets.ProgressWidget.Direction.CIRCULAR_CW)
                                    .value(progress)
                                    .texture(null, GTGuiTextures.FUSION_PROGRESS, 77))
                            .child(GTGuiTextures.FUSION_LEGEND.asWidget()
                                    .left(4)
                                    .bottom(4)
                                    .size(108, 41)));
                });
    }

    @Override
    public int getProgressBarCount() {
        return 2;
    }

    @Override
    public void registerBars(List<UnaryOperator<TemplateBarBuilder>> bars, PanelSyncManager syncManager) {
        LongSyncValue capacity = new LongSyncValue(energyContainer::getEnergyCapacity);
        syncManager.syncValue("capacity", capacity);
        LongSyncValue stored = new LongSyncValue(energyContainer::getEnergyStored);
        syncManager.syncValue("stored", stored);
        LongSyncValue heat = new LongSyncValue(this::getHeat);
        syncManager.syncValue("heat", heat);

        bars.add(barTest -> barTest
                .progress(() -> capacity.getLongValue() > 0 ?
                        1.0 * stored.getLongValue() / capacity.getLongValue() : 0)
                .texture(GTGuiTextures.PROGRESS_BAR_FUSION_ENERGY)
                .tooltipBuilder(tooltip -> tooltip
                        .add(KeyUtil.lang(TextFormatting.GRAY,
                                "gregtech.multiblock.energy_stored",
                                stored.getLongValue(), capacity.getLongValue()))));

        bars.add(barTest -> barTest
                .texture(GTGuiTextures.PROGRESS_BAR_FUSION_HEAT)
                .tooltipBuilder(tooltip -> {
                    IKey heatInfo = KeyUtil.string(TextFormatting.AQUA,
                            "%,d / %,d EU",
                            heat.getLongValue(), capacity.getLongValue());
                    tooltip.add(KeyUtil.lang(TextFormatting.GRAY,
                            "gregtech.multiblock.fusion_reactor.heat",
                            heatInfo));
                })
                .progress(() -> capacity.getLongValue() > 0 ?
                        1.0 * heat.getLongValue() / capacity.getLongValue() : 0));
    }


    private class FusionRecipeLogic extends MultiblockRecipeLogic {

        public FusionRecipeLogic(MetaTileEntityAdvancedFusionReactor tileEntity) {
            super(tileEntity);
        }

        @Override
        protected double getOverclockingDurationFactor() {
            return PERFECT_HALF_DURATION_FACTOR;
        }

        @Override
        protected double getOverclockingVoltageFactor() {
            return PERFECT_HALF_VOLTAGE_FACTOR;
        }

        @Override
        public long getMaxVoltage() {
            return Math.min(GTValues.V[tier], super.getMaxVoltage());
        }

        @Override
        public void updateWorkable() {
            super.updateWorkable();
            // Drain heat when the reactor is not active, is paused via soft mallet, or does not have enough energy and
            // has fully wiped recipe progress
            // Don't drain heat when there is not enough energy and there is still some recipe progress, as that makes
            // it doubly hard to complete the recipe
            // (Will have to recover heat and recipe progress)
            if (heat > 0) {
                if (!isActive || !workingEnabled || (hasNotEnoughEnergy && progressTime == 0)) {
                    heat = heat <= 10000 ? 0 : (heat - 10000);
                }
            }
        }

        @Override
        public boolean checkRecipe(@NotNull Recipe recipe) {
            if (!super.checkRecipe(recipe))
                return false;

            // if the reactor is not able to hold enough energy for it, do not run the recipe
            if (recipe.getProperty(FusionEUToStartProperty.getInstance(), 0L) > energyContainer.getEnergyCapacity())
                return false;

            long heatDiff = recipe.getProperty(FusionEUToStartProperty.getInstance(), 0L) - heat;
            // if the stored heat is >= required energy, recipe is okay to run
            if (heatDiff <= 0)
                return true;

            // if the remaining energy needed is more than stored, do not run
            if (energyContainer.getEnergyStored() < heatDiff)
                return false;

            // remove the energy needed
            energyContainer.removeEnergy(heatDiff);
            // increase the stored heat
            heat += heatDiff;
            return true;
        }

        @Override
        protected void modifyOverclockPre(@NotNull OCParams ocParams, @NotNull RecipePropertyStorage storage) {
            super.modifyOverclockPre(ocParams, storage);

            // Limit the number of OCs to the difference in fusion reactor MK.
            // I.e., a MK2 reactor can overclock a MK1 recipe once, and a
            // MK3 reactor can overclock a MK2 recipe once, or a MK1 recipe twice.
            long euToStart = storage.get(FusionEUToStartProperty.getInstance(), 0L);
            int fusionTier = FusionEUToStartProperty.getFusionTier(euToStart);
            if (fusionTier != 0) fusionTier = MetaTileEntityAdvancedFusionReactor.this.tier - fusionTier;
            ocParams.setOcAmount(Math.min(fusionTier, ocParams.ocAmount()));
        }

        @NotNull
        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound tag = super.serializeNBT();
            tag.setLong("Heat", heat);
            return tag;
        }

        @Override
        public void deserializeNBT(@NotNull NBTTagCompound compound) {
            super.deserializeNBT(compound);
            heat = compound.getLong("Heat");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(double x, double y, double z, float partialTicks) {
        if (this.hasFusionRingColor() && !this.registeredBloomRenderTicket) {
            this.registeredBloomRenderTicket = true;
            BloomEffectUtil.registerBloomRender(MetaTileEntityAdvancedFusionReactor.FusionBloomSetup.INSTANCE, getBloomType(), this, this);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderBloomEffect(@NotNull BufferBuilder buffer, @NotNull EffectRenderContext context) {
        if (!this.hasFusionRingColor()) return;
        int color = RenderUtil.interpolateColor(this.getFusionRingColor(), -1, Eases.QUAD_IN.getInterpolation(
                Math.abs((Math.abs(getOffsetTimer() % 50) + context.partialTicks()) - 25) / 25));
        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        EnumFacing relativeBack = RelativeDirection.BACK.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());
        EnumFacing.Axis axis = RelativeDirection.UP.getRelativeFacing(getFrontFacing(), getUpwardsFacing(), isFlipped())
                .getAxis();

        buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
        RenderBufferHelper.renderRing(buffer,
                getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 7 + 0.5,
                getPos().getY() - context.cameraY() + relativeBack.getYOffset() * 7 + 0.5,
                getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 7 + 0.5,
                6, 0.2, 10, 20,
                r, g, b, a, axis);
        Tessellator.getInstance().draw();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRenderBloomEffect(@NotNull EffectRenderContext context) {
        return this.hasFusionRingColor() && context.camera().isBoundingBoxInFrustum(getRenderBoundingBox());
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        EnumFacing relativeRight = RelativeDirection.RIGHT.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());
        EnumFacing relativeBack = RelativeDirection.BACK.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());

        return new AxisAlignedBB(
                this.getPos().offset(relativeBack).offset(relativeRight, 6),
                this.getPos().offset(relativeBack, 13).offset(relativeRight.getOpposite(), 6));
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 0;
    }

    @Override
    public boolean isGlobalRenderer() {
        return true;
    }

    private static BloomType getBloomType() {
        ConfigHolder.FusionBloom fusionBloom = ConfigHolder.client.shader.fusionBloom;
        return BloomType.fromValue(fusionBloom.useShader ? fusionBloom.bloomStyle : -1);
    }

    @SideOnly(Side.CLIENT)
    private static final class FusionBloomSetup implements IRenderSetup {

        private static final MetaTileEntityAdvancedFusionReactor.FusionBloomSetup INSTANCE = new MetaTileEntityAdvancedFusionReactor.FusionBloomSetup();

        float lastBrightnessX;
        float lastBrightnessY;

        @Override
        public void preDraw(@NotNull BufferBuilder buffer) {
            BloomEffect.strength = (float) ConfigHolder.client.shader.fusionBloom.strength;
            BloomEffect.baseBrightness = (float) ConfigHolder.client.shader.fusionBloom.baseBrightness;
            BloomEffect.highBrightnessThreshold = (float) ConfigHolder.client.shader.fusionBloom.highBrightnessThreshold;
            BloomEffect.lowBrightnessThreshold = (float) ConfigHolder.client.shader.fusionBloom.lowBrightnessThreshold;
            BloomEffect.step = 1;

            lastBrightnessX = OpenGlHelper.lastBrightnessX;
            lastBrightnessY = OpenGlHelper.lastBrightnessY;
            GlStateManager.color(1, 1, 1, 1);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GlStateManager.disableTexture2D();
        }

        @Override
        public void postDraw(@NotNull BufferBuilder buffer) {
            GlStateManager.enableTexture2D();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
        }
    }
}


