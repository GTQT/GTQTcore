package keqing.gtqtcore.api.metaileentity;

import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.capability.IDistinctBusController;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.utils.TooltipHelper;
import keqing.gtqtcore.api.capability.impl.ComputationRecipeLogic;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import supercritical.api.gui.SCGuiTextures;

import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;

import static gregtech.api.GTValues.V;
import static gregtech.api.GTValues.VA;

public abstract class GTQTOCMultiblockController extends MultiMapMultiblockController implements IOpticalComputationReceiver {

    protected boolean setTier;
    protected int tier;
    protected boolean setMaxParallel;
    protected int maxParallel;
    protected boolean setMaxVoltage;
    protected int maxVoltage;
    protected boolean setTimeReduce;
    protected double timeReduce;

    protected int autoParallel;
    protected int customParallel;
    protected boolean autoParallelModel;
    protected boolean OCFirst;
    protected int limitAutoParallel;
    protected int energyHatchMaxWork = 32;

    public GTQTOCMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMaps) {
        super(metaTileEntityId, recipeMaps);
        this.recipeMapWorkable = new GTQTOCMultiblockLogic(this);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setBoolean("autoParallelModel", autoParallelModel);
        data.setBoolean("OCFirst", OCFirst);
        data.setInteger("customParallel", customParallel);
        data.setInteger("autoParallel", autoParallel);
        data.setInteger("limitAutoParallel", limitAutoParallel);
        data.setInteger("energyHatchMaxWork", energyHatchMaxWork);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        autoParallelModel = data.getBoolean("autoParallelModel");
        OCFirst = data.getBoolean("OCFirst");
        customParallel = data.getInteger("customParallel");
        autoParallel = data.getInteger("autoParallel");
        limitAutoParallel = data.getInteger("limitAutoParallel");
        energyHatchMaxWork = data.getInteger("energyHatchMaxWork");
    }


    protected void setTier(int tier) {
        this.tier = tier;
    }

    protected void setMaxVoltage(int maxVoltage) {
        this.maxVoltage = maxVoltage;
    }

    protected void setTimeReduce(double timeReduce) {
        this.timeReduce = timeReduce;
    }

    protected void setTierFlag(boolean setTier) {
        this.setTier = setTier;
    }

    protected void setMaxParallelFlag(boolean setMaxParallel) {
        this.setMaxParallel = setMaxParallel;
    }

    protected void setMaxVoltageFlag(boolean setMaxVoltage) {
        this.setMaxVoltage = setMaxVoltage;
    }

    protected void setTimeReduceFlag(boolean setTimeReduce) {
        this.setTimeReduce = setTimeReduce;
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gregtech.machine.perfect_oc"));
        tooltip.add(I18n.format("gregtech.machine.gtqt.update.1"));
        if (setTier) tooltip.add(I18n.format("gregtech.machine.gtqt.update.2"));
        if (setMaxVoltage) tooltip.add(I18n.format("gregtech.machine.gtqt.update.3"));
        if (setMaxParallel) tooltip.add(I18n.format("gtqtcore.machine.parallel.pow.machineTier", 2, "详见机器内部UI"));
        if (setMaxVoltage) tooltip.add(I18n.format("gtqtcore.machine.voltage.num", "外壳等级对应的电压"));
    }

    @Override
    public String[] getDescription() {
        if (setTier) return new String[]{I18n.format("gtqt.tooltip.update")};
        return super.getDescription();
    }

    public int getMaxParallel() {
        if (setMaxParallel) return (int) Math.min(maxParallel, Math.pow(2, tier));
        else return (int) Math.pow(2, tier);
    }

    protected void setMaxParallel(int maxParallel) {
        this.maxParallel = maxParallel;
    }

    public void setCurrentParallel(int parallelAmount) {
        this.customParallel = MathHelper.clamp(this.customParallel + parallelAmount, 1, getMaxParallel());
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if(setTimeReduce)textList.add(new TextComponentTranslation("耗时减免:%s", timeReduce));
    }
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder;

        builder = ModularUI.builder(GuiTextures.BACKGROUND, 288, 208);

        builder.image(4, 4, 190, 117, GuiTextures.DISPLAY);
        builder.widget((new IndicatorImageWidget(174, 101, 17, 17, this.getLogo())).setWarningStatus(this.getWarningLogo(), this::addWarningText).setErrorStatus(this.getErrorLogo(), this::addErrorText));

        ///////////////////////////智能并行组件
        builder.image(200, 4, 80, 20, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(204, 10, this::addModel, 16777215)).setMaxWidthLimit(110).setClickHandler(this::handleDisplayClick));

        builder.widget(new ClickButtonWidget(200, 28, 80, 20, I18n.format("模式切换"),
                clickData -> autoParallelModel = !autoParallelModel));

        builder.image(200, 52, 80, 20, GuiTextures.DISPLAY);

        builder.widget((new AdvancedTextWidget(204, 58, this::addInfo, 16777215)).setMaxWidthLimit(110).setClickHandler(this::handleDisplayClick));

        builder.widget(new IncrementButtonWidget(200, 76, 40, 20, 1, 4, 16, 64, this::setCurrentParallel)
                .setDefaultTooltip()
                .setShouldClientCallback(false));

        builder.widget(new IncrementButtonWidget(240, 76, 40, 20, -1, -4, -16, -64, this::setCurrentParallel)
                .setDefaultTooltip()
                .setShouldClientCallback(false));

        builder.widget(
                new SliderWidget("自动并行上限", 200, 100, 80, 20, 1, maxParallel, limitAutoParallel,
                        this::setLimitAutoParallel).setBackground(SCGuiTextures.DARK_SLIDER_BACKGROUND)
                        .setSliderIcon(SCGuiTextures.DARK_SLIDER_ICON));

        builder.image(200, 124, 80, 20, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(204, 129, this::addEnergyHatch, 16777215)).setMaxWidthLimit(110).setClickHandler(this::handleDisplayClick));

        builder.image(200, 142, 80, 18, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(204, 146, this::addOC, 16777215)).setMaxWidthLimit(110).setClickHandler(this::handleDisplayClick));

        builder.widget(new ClickButtonWidget(200, 160, 40, 20, "重置", data ->
                energyHatchMaxWork = 32).setTooltipText("重置到默认的最大自持(自动并行可用)"));
        builder.widget(new ClickButtonWidget(240, 160, 40, 20, "推荐", data ->
        {
            energyHatchMaxWork = (int) (this.energyContainer.getEnergyStored() / VA[maxVoltage]);
            energyHatchMaxWork = Math.max(1, energyHatchMaxWork);
            energyHatchMaxWork = Math.min(energyHatchMaxWork, 128);

        }).setTooltipText("根据能源仓实际情况推荐最大自持(自动并行可用)"));

        builder.widget(new ClickButtonWidget(200, 184, 80, 20, I18n.format("超频/并行模式"),
                clickData -> OCFirst = !OCFirst).setTooltipText("设置并行/无损超频算法优先度"));

        ///////////////////////////Main GUI

        builder.label(9, 9, this.getMetaFullName(), 16777215);
        builder.widget((new AdvancedTextWidget(9, 20, this::addDisplayText, 16777215)).setMaxWidthLimit(181).setClickHandler(this::handleDisplayClick));
        IControllable controllable = this.getCapability(GregtechTileCapabilities.CAPABILITY_CONTROLLABLE, null);
        TextureArea var10007;
        BooleanSupplier var10008;
        if (controllable != null) {
            var10007 = GuiTextures.BUTTON_POWER;
            Objects.requireNonNull(controllable);
            var10008 = controllable::isWorkingEnabled;
            Objects.requireNonNull(controllable);
            builder.widget(new ImageCycleButtonWidget(173, 183, 18, 18, var10007, var10008, controllable::setWorkingEnabled));
            builder.widget(new ImageWidget(173, 201, 18, 6, GuiTextures.BUTTON_POWER_DETAIL));
        }

        if (this.shouldShowVoidingModeButton()) {
            builder.widget((new ImageCycleButtonWidget(173, 161, 18, 18, GuiTextures.BUTTON_VOID_MULTIBLOCK, 4, this::getVoidingMode, this::setVoidingMode)).setTooltipHoverString(MultiblockWithDisplayBase::getVoidingModeTooltip));
        } else {
            builder.widget((new ImageWidget(173, 161, 18, 18, GuiTextures.BUTTON_VOID_NONE)).setTooltip("gregtech.gui.multiblock_voiding_not_supported"));
        }

        label30:
        {
            IDistinctBusController distinct = this;
            if (distinct.canBeDistinct()) {
                var10007 = GuiTextures.BUTTON_DISTINCT_BUSES;
                Objects.requireNonNull(distinct);
                var10008 = distinct::isDistinct;
                Objects.requireNonNull(distinct);
                builder.widget((new ImageCycleButtonWidget(173, 143, 18, 18, var10007, var10008, distinct::setDistinct)).setTooltipHoverString((i) -> "gregtech.multiblock.universal.distinct_" + (i == 0 ? "disabled" : "enabled")));
                break label30;
            }

            builder.widget((new ImageWidget(173, 143, 18, 18, GuiTextures.BUTTON_NO_DISTINCT_BUSES)).setTooltip("gregtech.multiblock.universal.distinct_not_supported"));
        }

        builder.widget(this.getFlexButton(173, 125, 18, 18));
        builder.bindPlayerInventory(entityPlayer.inventory, 125);
        return builder;
    }

    public void setLimitAutoParallel(float value) {
        limitAutoParallel = (int) value;
    }

    protected void addInfo(List<ITextComponent> textList) {
        if(autoParallelModel)textList.add(new TextComponentTranslation("%s / %s / %s", autoParallel, limitAutoParallel, getMaxParallel()));
        else textList.add(new TextComponentTranslation("%s / %s", customParallel, getMaxParallel()));
    }

    protected void addModel(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .addCustom(tl -> textList.add(new TextComponentTranslation("%s", autoParallelModel ? "自动并行模式" : "手动并行模式")));
    }

    protected void addEnergyHatch(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .addCustom(tl -> textList.add(new TextComponentTranslation("最大自持：%s（tick）", energyHatchMaxWork)));
    }

    protected void addOC(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .addCustom(tl -> textList.add(new TextComponentTranslation("%s", OCFirst ? "超频优先模式" : "并行优先模式")));
    }

    protected class GTQTOCMultiblockLogic extends ComputationRecipeLogic {
        public GTQTOCMultiblockLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity, ComputationType.SPORADIC);
            this.hasPerfectOC=true;
        }

        @Override
        protected double getOverclockingDurationDivisor() {
            return OCFirst ? 4.0 : 2.0;
        }
        @Override
        public long getMaxVoltage() {
            if (setMaxVoltage) return V[maxVoltage];
            else return super.getMaxVoltage();
        }

        @Override
        public void update() {
            super.update();
            if (autoParallelModel) {
                autoParallel = (int) ((this.getEnergyStored() + energyContainer.getInputPerSec() * 19L) / (getMinVoltage() == 0 ? 1 : getMinVoltage()));
                autoParallel = Math.min(autoParallel, limitAutoParallel);
                autoParallel = Math.min(autoParallel, getMaxParallel());
            }
        }

        public int getMinVoltage() {
            if ((Math.min(this.getEnergyCapacity() / (energyHatchMaxWork == 0 ? 1 : energyHatchMaxWork), this.getMaxVoltage())) == 0)
                return 1;
            return (int) (Math.min(this.getEnergyCapacity() / (energyHatchMaxWork == 0 ? 1 : energyHatchMaxWork), this.getMaxVoltage()));
        }

        @Override
        public int getParallelLimit() {
            return autoParallelModel ? autoParallel : customParallel;
        }

        @Override
        protected long getMaxParallelVoltage() {
            if (OCFirst) return super.getMaxVoltage();
            return super.getMaxParallelVoltage();
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            if (setTimeReduce) this.maxProgressTime = (int) (maxProgress * timeReduce);
            else super.setMaxProgress(maxProgress);
        }

    }
}
