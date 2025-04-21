package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.heatExchanger;

import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapPrimitiveMultiblockController;
import gregtech.api.pattern.*;
import gregtech.api.util.BlockInfo;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.IHeatExchanger;
import keqing.gtqtcore.api.capability.impl.HeatExchangerRecipeLogic;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtsteam.api.metatileentity.multiblock.RecipeMapNoEnergyMultiblockController;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static gregtech.api.gui.widgets.AdvancedTextWidget.withHoverTextTranslate;
import static gregtech.api.util.RelativeDirection.*;
import static net.minecraft.util.text.TextFormatting.*;

public class MetaTileEntityHeatExchanger extends RecipeMapNoEnergyMultiblockController implements IHeatExchanger {
    private final int heatTime = 200;
    private int coilLevel;
    private int number;
    private int casingTier;
    private int tubeTier;
    private int glassTier;
    private int tier;
    private int thresholdPercentage = 100;

    public MetaTileEntityHeatExchanger(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.HEAT_EXCHANGE_RECIPES);
        this.recipeMapWorkable = new HeatExchangerRecipeLogic(this, GTQTcoreRecipeMaps.HEAT_EXCHANGE_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityHeatExchanger(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        FactoryBlockPattern pattern = FactoryBlockPattern.start(FRONT, UP, RIGHT)
                .aisle(" XXX ", " XIX ", " XXX ", " XXX ", " XIX ", " XXX ")
                .aisle("XXXXX", "XTTTX", "STTTX", "XTTTX", "XTTTX", "XXXXX")
                .aisle("XXXXX", "GTTTG", "GTTTG", "GTTTG", "GTTTG", "XXXXX")
                .aisle("XXXXX", "GCTCG", "GCTCG", "GCTCG", "GCTCG", "XXXXX").setRepeatable(1, 10)
                .aisle("XXXXX", "GTTTG", "GTTTG", "GTTTG", "GTTTG", "XXXXX")
                .aisle("XXXXX", "XTTTX", "XTTTX", "XTTTX", "XTTTX", "XXXXX")
                .aisle(" XHX ", " XOX ", " XXX ", " XXX ", " XOX ", " XXX ")
                .where('S', selfPredicate())
                .where('H', (abilities(MultiblockAbility.MAINTENANCE_HATCH)))
                .where('O', (abilities(MultiblockAbility.EXPORT_FLUIDS)))
                .where('I', (abilities(MultiblockAbility.IMPORT_FLUIDS)))
                .where('C', coilPredicate())
                .where('X', TiredTraceabilityPredicate.CP_CASING.get())
                .where('T', TiredTraceabilityPredicate.CP_TUBE.get())
                .where('G', TiredTraceabilityPredicate.CP_LGLASS.get())
                .where(' ', any());
        return pattern.build();
    }

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        switch (this.casingTier) {
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
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public SoundEvent getSound() {
        return GTSoundEvents.ELECTROLYZER;
    }

    private TraceabilityPredicate coilPredicate() {
        return new TraceabilityPredicate((blockWorldState) -> {
            IBlockState blockState = blockWorldState.getBlockState();
            if (blockState.getBlock() instanceof BlockWireCoil blockWireCoil) {
                BlockWireCoil.CoilType coilType = blockWireCoil.getState(blockState);
                Object currentCoilType = blockWorldState.getMatchContext().getOrPut("CoilType", coilType);
                if (!currentCoilType.toString().equals(coilType.getName())) {
                    blockWorldState.setError(new PatternStringError("gregtech.multiblock.pattern.error.coils"));
                    return false;
                } else {
                    blockWorldState.getMatchContext().increment("Count", 1);
                    blockWorldState.getMatchContext().getOrPut("VABlock", new LinkedList<>()).add(blockWorldState.getPos());
                    return true;
                }
            } else {
                return false;
            }
        }, () -> Arrays.stream(BlockWireCoil.CoilType.values()).map((type) -> new BlockInfo(MetaBlocks.WIRE_COIL.getState(type), null)).toArray(BlockInfo[]::new)).addTooltips("gregtech.multiblock.pattern.error.coils");
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GTQTValue.UPDATE_TIER6) {
            this.casingTier = buf.readInt();
        }
        if (dataId == GTQTValue.REQUIRE_DATA_UPDATE6) {
            this.writeCustomData(GTQTValue.UPDATE_TIER6, buf1 -> buf1.writeInt(this.casingTier));
        }
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        number = context.getInt("Count") / 8;
        Object casingTier = context.get("ChemicalPlantCasingTieredStats");
        Object tubeTier = context.get("ChemicalPlantTubeTieredStats");
        Object glassTier = context.get("LGLTieredStats");
        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.coilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
        } else {
            this.coilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
        }
        this.casingTier = GTQTUtil.getOrDefault(() -> casingTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) casingTier).getIntTier(),
                0);
        this.tubeTier = GTQTUtil.getOrDefault(() -> tubeTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) tubeTier).getIntTier(),
                0);
        this.glassTier = GTQTUtil.getOrDefault(() -> glassTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) glassTier).getIntTier(),
                0);

        this.tier = Math.min(Math.min(this.casingTier, this.tubeTier), this.glassTier);

        this.writeCustomData(GTQTValue.UPDATE_TIER6, buf -> buf.writeInt(this.casingTier));
    }


    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed()) {
            HeatExchangerRecipeLogic logic = (HeatExchangerRecipeLogic) recipeMapWorkable;
            textList.add(new TextComponentTranslation("gtqtcore.machine.heat_exchanger.rate." + logic.isSuperheat(), logic.getRate()));
            int efficiency = (int) Math.ceil(logic.getHeatEfficiency() * (40 + 0.6 * thresholdPercentage));
            textList.add(new TextComponentTranslation("gtqtcore.machine.heat_exchanger.efficiency",
                    (efficiency == 0 ? DARK_RED : efficiency <= 40 ? RED : efficiency == 100 ? GREEN : YELLOW).toString() + efficiency + "%"));
            ITextComponent throttleText = new TextComponentTranslation("gtqtcore.machine.heat_exchanger.threshold",
                    AQUA.toString() + getThrottle() + "%");
            withHoverTextTranslate(throttleText, "gtqtcore.machine.heat_exchanger.threshold.tooltip");
            textList.add(throttleText);
        }
    }

    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 18, "", this::decrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("gtqtcore.multiblock.large_heat_exchanger.threshold_decrement"));
        group.addWidget(new ClickButtonWidget(9, 0, 9, 18, "", this::incrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("gtqtcore.multiblock.large_heat_exchanger.threshold_increment"));
        return group;
    }

    private void incrementThreshold(Widget.ClickData clickData) {
        this.thresholdPercentage = MathHelper.clamp(thresholdPercentage + 5, 25, 200);
    }

    private void decrementThreshold(Widget.ClickData clickData) {
        this.thresholdPercentage = MathHelper.clamp(thresholdPercentage - 5, 25, 200);
    }

    @Override
    public String[] getDescription() {
        return new String[]{I18n.format("gtqtcore.multiblock.large_heat_exchanger.description")};
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.multiblock.large_heat_exchanger.heat_time_tooltip", heatTime));
        tooltip.add(TooltipHelper.BLINKING_RED + I18n.format("gtqtcore.multiblock.large_heat_exchanger.explosion_tooltip"));
    }


    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("ThresholdPercentage", thresholdPercentage);
        data.setInteger("casingTier", casingTier);
        return super.writeToNBT(data);
    }


    public void readFromNBT(NBTTagCompound data) {
        thresholdPercentage = data.getInteger("ThresholdPercentage");
        casingTier = data.getInteger("casingTier");
        super.readFromNBT(data);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeVarInt(thresholdPercentage);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        thresholdPercentage = buf.readVarInt();
    }

    @Override
    public int getThrottle() {
        return thresholdPercentage;
    }

    @Override
    public int getHeatTime() {
        return (int) (heatTime * 1.0 / tier);
    }

    @Override
    public int getParallel() {
        return number * coilLevel;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public boolean canVoidRecipeItemOutputs() {
        return true;
    }

    @Override
    public boolean canVoidRecipeFluidOutputs() {
        return true;
    }

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }
}
