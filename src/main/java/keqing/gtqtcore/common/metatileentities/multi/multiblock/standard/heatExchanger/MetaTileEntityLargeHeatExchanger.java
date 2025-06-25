package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.heatExchanger;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.capability.IHeatExchanger;
import keqing.gtqtcore.api.capability.impl.HeatExchangerRecipeLogic;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtsteam.api.metatileentity.multiblock.NoEnergyMultiblockController;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.gui.widgets.AdvancedTextWidget.withHoverTextTranslate;
import static net.minecraft.util.text.TextFormatting.*;

public class MetaTileEntityLargeHeatExchanger extends NoEnergyMultiblockController implements IHeatExchanger {
    @Override
    public boolean usesMui2() {
        return false;
    }
    private final int heatTime = 300;
    private int thresholdPercentage = 100;
    protected HeatExchangerRecipeLogic recipeMapWorkable;

    public MetaTileEntityLargeHeatExchanger(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.HEAT_EXCHANGE_RECIPES);
        this.recipeMapWorkable = new HeatExchangerRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityLargeHeatExchanger(metaTileEntityId);
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return GTQTTextures.LARGE_ROCKET_ENGINE_OVERLAY;
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("N#N#N", "NNNNN", "NNNNN", "NNNNN")
                .aisle("N#N#N", "NNNNN", "IAAAE", "NNNNN")
                .aisle("N#N#N", "NNNNN", "NCNNN", "NNNNN")
                .where('C', selfPredicate())
                .where('N', states(getCasingState()).setMinGlobalLimited(48)
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS)))
                .where('A', states(getBoilerState()))
                .where('I', abilities(MultiblockAbility.IMPORT_FLUIDS))
                .where('E', abilities(MultiblockAbility.EXPORT_FLUIDS))
                .where('#', any())
                .build();
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST);
    }

    protected IBlockState getBoilerState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.ROBUST_TUNGSTENSTEEL_CASING;
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
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.multiblock.large_heat_exchanger.heat_time_tooltip", heatTime));
        tooltip.add(TooltipHelper.BLINKING_RED + I18n.format("gtqtcore.multiblock.large_heat_exchanger.explosion_tooltip"));
    }


    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("ThresholdPercentage", thresholdPercentage);
        return super.writeToNBT(data);
    }


    public void readFromNBT(NBTTagCompound data) {
        thresholdPercentage = data.getInteger("ThresholdPercentage");
        super.readFromNBT(data);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeVarInt(thresholdPercentage);
        this.recipeMapWorkable.writeInitialData(buf);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        thresholdPercentage = buf.readVarInt();
        this.recipeMapWorkable.receiveInitialData(buf);
    }

    @Override
    public int getThrottle() {
        return thresholdPercentage;
    }

    @Override
    public int getHeatTime() {
        return heatTime;
    }

    @Override
    public int getParallel() {
        return 4;
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
    public boolean shouldShowVoidingModeButton() {
        return false;
    }
}
