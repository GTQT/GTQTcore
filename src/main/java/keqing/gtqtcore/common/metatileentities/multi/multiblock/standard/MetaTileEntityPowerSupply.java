package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import keqing.gtqtcore.api.capability.IPowerSupply;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.POWER_SUPPLY_ABILITY;

public class MetaTileEntityPowerSupply extends MultiblockWithDisplayBase {
    @Override
    public boolean usesMui2() {
        return false;
    }
    int HatchLength;
    long maxEuStore;
    long EuStore = 0;
    int maxTier;

    int chargeHeight = 1;
    int updateTime = 1;

    boolean work = true;
    boolean charge = true;//供能模式
    boolean fastCharge = false;//闪充模式

    private IEnergyContainer inenergyContainer;
    private IEnergyContainer outenergyContainer;

    public MetaTileEntityPowerSupply(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    public void changeEu(long eu) {
        if ((this.EuStore += eu) >= 0 && (this.EuStore += eu) <= maxEuStore) this.EuStore += eu;
    }

    public int getChargeHeight() {
        return chargeHeight;
    }

    public long getEuStore() {
        return EuStore;
    }

    public long getMaxEuStore() {
        return maxEuStore;
    }

    public boolean getFastCharge() {
        return fastCharge;
    }

    public boolean getCharge() {
        return charge;
    }

    public boolean getWork() {
        return work;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setLong("EuStore", EuStore);
        data.setInteger("maxTier", maxTier);
        data.setInteger("updateTime", updateTime);
        data.setInteger("chargeHeight", chargeHeight);

        data.setBoolean("work", work);

        data.setBoolean("charge", charge);
        data.setBoolean("fastCharge", fastCharge);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        EuStore = data.getLong("EuStore");
        maxTier = data.getInteger("maxTier");
        updateTime = data.getInteger("updateTime");
        chargeHeight = data.getInteger("chargeHeight");

        work = data.getBoolean("work");

        charge = data.getBoolean("charge");
        fastCharge = data.getBoolean("fastCharge");
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentTranslation("供能/发电模式：%s 闪充模式：%s", charge, fastCharge));
        textList.add(new TextComponentTranslation("状态：%s 检测频率：%s 供能范围：%s", work, updateTime, chargeHeight));
        textList.add(new TextComponentTranslation("蓄能：%s / %s EU", EuStore, maxEuStore));
    }

    protected void ventInfo(List<ITextComponent> textList) {
        if (inenergyContainer != null)
            textList.add(new TextComponentTranslation("能源仓缓存：%s  EU", inenergyContainer.getEnergyStored()));
        if (inenergyContainer != null)
            textList.add(new TextComponentTranslation("能源仓蓄能：%s  EU", inenergyContainer.getEnergyCapacity()));

        if (outenergyContainer != null)
            textList.add(new TextComponentTranslation("动力仓缓存：%s  EU", outenergyContainer.getEnergyStored()));
        if (outenergyContainer != null)
            textList.add(new TextComponentTranslation("动力仓蓄能：%s  EU", outenergyContainer.getEnergyCapacity()));
        if (!work)
            textList.add(TextComponentUtil.translationWithColor(TextFormatting.RED, "工作已暂停！"));

    }


    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 260, 240);


        builder.image(92, 4, 162, 50, GuiTextures.DISPLAY);
        builder.dynamicLabel(95, 8, () -> "超导矩阵管理中心", 0xFFFFFF);
        builder.widget((new AdvancedTextWidget(95, 20, this::addDisplayText, 16777215)).setMaxWidthLimit(162).setClickHandler(this::handleDisplayClick));

        //UI
        builder.image(92, 54, 162, 62, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(95, 58, this::ventInfo, 16777215)).setMaxWidthLimit(162).setClickHandler(this::handleDisplayClick));

        builder.widget(new ClickButtonWidget(132 - 32, 120, 48, 18, "充能模式", data -> charge = !charge).setTooltipText("切换供能模式与发电模式"));
        builder.widget(new ClickButtonWidget(180 - 32, 120, 48, 18, "检测刷新", data -> Refresh()).setTooltipText("重载所有模块信息"));
        builder.widget(new ClickButtonWidget(228 - 32, 120, 48, 18, "闪充模式", data -> fastCharge = !fastCharge).setTooltipText("开启后对低于模块等级的设备瞬间完成供能"));

        builder.widget(new ClickButtonWidget(132 - 32, 140, 24, 18, "+1", data -> this.updateTime = MathHelper.clamp(updateTime + 1, 1, 20)));
        builder.widget(new ClickButtonWidget(156 - 32, 140, 24, 18, "-1", data -> this.updateTime = MathHelper.clamp(updateTime - 1, 1, 20)));
        builder.widget(new ClickButtonWidget(180 - 32, 140, 24, 18, "+1", data -> this.chargeHeight = MathHelper.clamp(chargeHeight + 1, 1, 6)));
        builder.widget(new ClickButtonWidget(204 - 32, 140, 24, 18, "-1", data -> this.chargeHeight = MathHelper.clamp(chargeHeight - 1, 1, 6)));


        builder.widget(new ClickButtonWidget(228 - 32, 140, 48, 18, "设备开关", data -> work = !work).setTooltipText("开关设备"));

        builder.widget((new ProgressWidget(() -> (double) EuStore / maxEuStore, 92, 53, 162, 4, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, EuStore, maxEuStore)));

        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 92, 160);
        return builder;
    }

    private void Refresh() {
        long currentMaxEuStore = 0;
        for (int x = 0; x <= HatchLength; x++) {
            currentMaxEuStore += this.getAbilities(POWER_SUPPLY_ABILITY).get(x).getBatteryStore();
        }
        if (currentMaxEuStore != maxEuStore) maxEuStore = currentMaxEuStore;
        EuStore = Math.min(EuStore, maxEuStore);
    }

    public void addBarHoverText(List<ITextComponent> hoverList, long a, long b) {
        ITextComponent Info = TextComponentUtil.stringWithColor(
                TextFormatting.AQUA,
                a + " / " + b + " EU");
        hoverList.add(TextComponentUtil.translationWithColor(
                TextFormatting.GRAY,
                "存储电量: %s",
                Info));
    }

    @Override
    protected void updateFormedValid() {

        // 更新频率
        if (getOffsetTimer() % updateTime == 0) {
            if (charge) {
                if (this.inenergyContainer != null && this.inenergyContainer.getEnergyStored() > 0 && EuStore < maxEuStore) {
                    long energyToAdd = Math.min(this.inenergyContainer.getEnergyStored(), maxEuStore - EuStore);
                    EuStore += (int) energyToAdd;
                    this.inenergyContainer.removeEnergy(energyToAdd);
                }
            } else {
                if (this.outenergyContainer != null && this.outenergyContainer.getEnergyStored() < this.outenergyContainer.getEnergyCapacity()) {
                    long energyToTransfer = Math.min(EuStore, this.outenergyContainer.getEnergyCapacity() - this.outenergyContainer.getEnergyStored());
                    EuStore -= (int) energyToTransfer;
                    this.outenergyContainer.addEnergy(energyToTransfer);
                }
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("谁还需要地板", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.4"));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.5"));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.6"));
    }

    @Override
    public boolean isMultiblockPartWeatherResistant(@Nonnull IMultiblockPart part) {
        return true;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPowerSupply(metaTileEntityId);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCCC")
                .aisle("CCCCCCCCCCCCCCCC")
                .aisle("SCCCCCCCCCCCCCCC")
                .where('S', selfPredicate())
                .where('C', abilities(POWER_SUPPLY_ABILITY)
                        .or(abilities(MultiblockAbility.OUTPUT_ENERGY).setMaxLayerLimited(8).setMinGlobalLimited(0))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMaxLayerLimited(8).setMinGlobalLimited(0)))
                .build();
    }


    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.POWER_SUPPLY_COMMON;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public boolean shouldShowVoidingModeButton() {
        return false;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        List<IPowerSupply> i = getAbilities(POWER_SUPPLY_ABILITY);
        this.HatchLength = i.size();

        long currentMaxEuStore = 0;
        for (int x = 0; x < HatchLength; x++) {
            currentMaxEuStore += this.getAbilities(POWER_SUPPLY_ABILITY).get(x).getBatteryStore();
            maxTier = Math.max(maxTier, GTUtility.getTierByVoltage(this.getAbilities(POWER_SUPPLY_ABILITY).get(x).getLevel()));
        }
        if (currentMaxEuStore != maxEuStore) maxEuStore = currentMaxEuStore;
        EuStore = Math.min(EuStore, maxEuStore);
        initializeAbilities();
    }

    private void initializeAbilities() {
        List<IEnergyContainer> inenergyContainer = new ArrayList<>(this.getAbilities(MultiblockAbility.INPUT_ENERGY));
        inenergyContainer.addAll(this.getAbilities(MultiblockAbility.INPUT_LASER));
        this.inenergyContainer = new EnergyContainerList(inenergyContainer);

        List<IEnergyContainer> outenergyContainer = new ArrayList<>(this.getAbilities(MultiblockAbility.OUTPUT_ENERGY));
        outenergyContainer.addAll(this.getAbilities(MultiblockAbility.OUTPUT_LASER));
        this.outenergyContainer = new EnergyContainerList(outenergyContainer);
    }

    private void resetTileAbilities() {
        this.inenergyContainer = new EnergyContainerList(new ArrayList<>());
        this.outenergyContainer = new EnergyContainerList(new ArrayList<>());
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();

        resetTileAbilities();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), true,
                isStructureFormed());
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }
}
