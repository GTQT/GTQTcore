package keqing.gtqtcore.api.metaileentity;

import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.capability.IDistinctBusController;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.IntSupplier;

import static gregtech.api.GTValues.V;

public abstract class GTQTRecipeMapMultiblockController extends MultiMapMultiblockController {
    private int actualMaxParallel;

    protected boolean setTier;
    protected int tier;
    protected boolean setMaxParallel;
    protected int maxParallel;
    protected boolean setMaxVoltage;
    protected int maxVoltage;
    protected boolean setTimeReduce;
    protected int timeReduce;

    protected int autoParallel;
    protected int customParallel;
    protected boolean autoParallelModel;

    public GTQTRecipeMapMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMaps) {
        super(metaTileEntityId, recipeMaps);
        this.recipeMapWorkable = new GTQTMultiblockLogic(this);

    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        if (setMaxParallel) actualMaxParallel= (int) Math.min(maxParallel, Math.pow(2, tier));
        else actualMaxParallel= (int) Math.pow(2, tier);
    }
    public static Function<String, String> getTextFieldValidator(IntSupplier maxSupplier) {
        return val -> {
            if (val.isEmpty())
                return String.valueOf(1);
            int max = maxSupplier.getAsInt();
            int num;
            try {
                num = Integer.parseInt(val);
            } catch (NumberFormatException ignored) {
                return String.valueOf(max);
            }
            if (num < 1)
                return String.valueOf(1);
            if (num > max)
                return String.valueOf(max);
            return val;
        };
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setBoolean("autoParallelModel", autoParallelModel);
        data.setInteger("customParallel", customParallel);
        data.setInteger("autoParallel", autoParallel);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        autoParallelModel = data.getBoolean("autoParallelModel");
        customParallel = data.getInteger("customParallel");
        autoParallel = data.getInteger("autoParallel");
    }


    protected void setTier(int tier) {
        this.tier = tier;
    }

    protected void setMaxParallel(int maxParallel) {
        this.maxParallel = maxParallel;
    }

    protected void setMaxVoltage(int maxVoltage) {
        this.maxVoltage = maxVoltage;
    }

    protected void setTimeReduce(int timeReduce) {
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
        tooltip.add(I18n.format("gregtech.machine.gtqt.update.1"));
        if (setTier) tooltip.add(I18n.format("gregtech.machine.gtqt.update.2"));
        if (setMaxVoltage) tooltip.add(I18n.format("gregtech.machine.gtqt.update.3"));
        if (setMaxParallel) tooltip.add(I18n.format("gtqtcore.machine.parallel.pow.machineTier", 2, 32));
        if (setMaxVoltage) tooltip.add(I18n.format("gtqtcore.machine.voltage.num", V[maxVoltage]));
    }

    @Override
    public String[] getDescription() {
        if(setTier)return new String[]{I18n.format("gtqt.tooltip.update")};
        return super.getDescription();
    }
    
    public void setCurrentParallel(int parallelAmount) {
        this.customParallel = MathHelper.clamp(this.customParallel + parallelAmount, 1, actualMaxParallel);
    }

    public String getParallelAmountToString() {
        return Integer.toString(this.customParallel);
    }

    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder;

        builder = ModularUI.builder(GuiTextures.BACKGROUND, 288, 208);

        builder.image(4, 4, 190, 117, GuiTextures.DISPLAY);
        builder.widget((new IndicatorImageWidget(174, 101, 17, 17, this.getLogo())).setWarningStatus(this.getWarningLogo(), this::addWarningText).setErrorStatus(this.getErrorLogo(), this::addErrorText));

        ///////////////////////////智能并行组件
        builder.image(200, 4, 80, 20, GuiTextures.DISPLAY)
                .dynamicLabel(202, 8, () -> I18n.format("模式：%s", autoParallelModel ? "自动模式" : "手动模式")
                        , 0xFAF9F6);

        builder.widget(new ClickButtonWidget(200, 28, 80, 20, I18n.format("模式切换"),
                clickData -> autoParallelModel = !autoParallelModel));

        builder.image(200, 52, 80, 20, GuiTextures.DISPLAY)
                .widget(new TextFieldWidget2(200, 52, 40, 20, this::getParallelAmountToString, val -> {
                    if (val != null && !val.isEmpty()) {
                        setCurrentParallel(Integer.parseInt(val));
                    }
                })
                        .setCentered(true)
                        .setNumbersOnly(1, actualMaxParallel)
                        .setMaxLength(6)
                        .setValidator(getTextFieldValidator(this::getMaxParallel)));

        builder.dynamicLabel(240, 52, () -> "/" + actualMaxParallel, 0xFFFFFF);

        builder.widget(new IncrementButtonWidget(200, 76, 40, 20, actualMaxParallel >= 64 ? actualMaxParallel / 64 : 1, actualMaxParallel >= 32 ? actualMaxParallel / 32 : 1, actualMaxParallel >= 16 ? actualMaxParallel / 16 : 1, actualMaxParallel / 4, this::setCurrentParallel)
                .setDefaultTooltip()
                .setShouldClientCallback(false));

        builder.widget(new IncrementButtonWidget(240, 76, 40, 20, actualMaxParallel >= 64 ? -actualMaxParallel / 64 : -1, actualMaxParallel >= 32 ? -actualMaxParallel / 32 : -1, actualMaxParallel >= 16 ? -actualMaxParallel / 16 : -1, -actualMaxParallel / 4, this::setCurrentParallel)
                .setDefaultTooltip()
                .setShouldClientCallback(false));
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

    private int getMaxParallel() {
        return actualMaxParallel;
    }

    protected class GTQTMultiblockLogic extends MultiblockRecipeLogic {
        public GTQTMultiblockLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity, true);
        }

        public long getMaxVoltage() {
            if (setMaxVoltage) return V[maxVoltage];
            else return super.getMaxVoltage();
        }

        @Override
        public void update() {
            super.update();
            if (autoParallelModel) {
                autoParallel = (int) ((this.getEnergyStored() + energyContainer.getInputPerSec()) / (getMinVoltage() == 0 ? 1 : getMinVoltage()));
                autoParallel = Math.min(autoParallel, actualMaxParallel);
            }
        }

        public int getMinVoltage() {
            if ((Math.min(this.getEnergyCapacity() / 32, this.getMaxVoltage()) * 20) == 0) return 1;
            return (int) (Math.min(this.getEnergyCapacity() / 32, this.getMaxVoltage()));

        }

        @Override
        public int getParallelLimit() {
            int actualMaxParallel;

            if (setMaxParallel) actualMaxParallel = (int) Math.min(maxParallel, Math.pow(2, tier));
            else actualMaxParallel = (int) Math.pow(2, tier);

            if (autoParallelModel) {
                return Math.min(autoParallel, actualMaxParallel);
            } else {
                return Math.min(customParallel, actualMaxParallel);
            }
        }

        @Override
        protected long getMaxParallelVoltage() {
            return super.getMaxVoltage();
        }
        @Override
        public void setMaxProgress(int maxProgress) {
            if(setTimeReduce)this.maxProgressTime = maxProgress/timeReduce;
            else super.setMaxProgress(maxProgress);
        }
    }
}
