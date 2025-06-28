package keqing.gtqtcore.api.metatileentity;

import com.cleanroommc.modularui.api.GuiAxis;
import com.cleanroommc.modularui.api.IPanelHandler;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.ItemDrawable;
import com.cleanroommc.modularui.drawable.Rectangle;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.utils.Color;
import com.cleanroommc.modularui.utils.MouseData;
import com.cleanroommc.modularui.value.sync.*;
import com.cleanroommc.modularui.widgets.ButtonWidget;
import com.cleanroommc.modularui.widgets.layout.Flow;
import com.cleanroommc.modularui.widgets.textfield.TextFieldWidget;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.capability.IDistinctBusController;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.metatileentity.multiblock.ui.KeyManager;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIBuilder;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIFactory;
import gregtech.api.metatileentity.multiblock.ui.UISyncer;
import gregtech.api.mui.GTGuiTextures;
import gregtech.api.mui.GTGuis;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.KeyUtil;
import gregtech.client.utils.TooltipHelper;
import keqing.gtqtcore.api.capability.impl.ComputationRecipeLogic;
import keqing.gtqtcore.api.utils.GTQTUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
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
    protected int Overclocking;

    public GTQTOCMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMaps) {
        super(metaTileEntityId, recipeMaps);
        this.recipeMapWorkable = new GTQTOCMultiblockLogic(this);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setBoolean("setTier", this.setTier);
        data.setInteger("tier", this.tier);
        data.setBoolean("setMaxParallel", this.setMaxParallel);
        data.setInteger("maxParallel", this.maxParallel);
        data.setBoolean("setMaxVoltage", this.setMaxVoltage);
        data.setInteger("maxVoltage", this.maxVoltage);
        data.setBoolean("setTimeReduce", this.setTimeReduce);
        data.setDouble("timeReduce", this.timeReduce);

        data.setInteger("autoParallel", this.autoParallel);
        data.setInteger("customParallel", this.customParallel);
        data.setBoolean("autoParallelModel", this.autoParallelModel);
        data.setBoolean("OCFirst", this.OCFirst);
        data.setInteger("limitAutoParallel", this.limitAutoParallel);
        data.setInteger("energyHatchMaxWork", this.energyHatchMaxWork);
        data.setInteger("Overclocking", this.Overclocking);

        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        this.setTier = data.getBoolean("setTier");
        this.tier = data.getInteger("tier");
        this.setMaxParallel = data.getBoolean("setMaxParallel");
        this.maxParallel = data.getInteger("maxParallel");
        this.setMaxVoltage = data.getBoolean("setMaxVoltage");
        this.maxVoltage = data.getInteger("maxVoltage");
        this.setTimeReduce = data.getBoolean("setTimeReduce");
        this.timeReduce = data.getDouble("timeReduce");

        this.autoParallel = data.getInteger("autoParallel");
        this.customParallel = data.getInteger("customParallel");
        this.autoParallelModel = data.getBoolean("autoParallelModel");
        this.OCFirst = data.getBoolean("OCFirst");
        this.limitAutoParallel = data.getInteger("limitAutoParallel");
        this.energyHatchMaxWork = data.getInteger("energyHatchMaxWork");
        this.Overclocking = data.getInteger("Overclocking");

        super.readFromNBT(data);
    }

    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(this.setTier);
        buf.writeInt(this.tier);
        buf.writeBoolean(this.setMaxParallel);
        buf.writeInt(this.maxParallel);
        buf.writeBoolean(this.setMaxVoltage);
        buf.writeInt(this.maxVoltage);
        buf.writeBoolean(this.setTimeReduce);
        buf.writeDouble(this.timeReduce);

        buf.writeInt(this.autoParallel);
        buf.writeInt(this.customParallel);
        buf.writeBoolean(this.autoParallelModel);
        buf.writeBoolean(this.OCFirst);
        buf.writeInt(this.limitAutoParallel);
        buf.writeInt(this.energyHatchMaxWork);
        buf.writeInt(this.Overclocking);
    }

    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.setTier = buf.readBoolean();
        this.tier = buf.readInt();
        this.setMaxParallel = buf.readBoolean();
        this.maxParallel = buf.readInt();
        this.setMaxVoltage = buf.readBoolean();
        this.maxVoltage = buf.readInt();
        this.setTimeReduce = buf.readBoolean();
        this.timeReduce = buf.readDouble();

        this.autoParallel = buf.readInt();
        this.customParallel = buf.readInt();
        this.autoParallelModel = buf.readBoolean();
        this.OCFirst = buf.readBoolean();
        this.limitAutoParallel = buf.readInt();
        this.energyHatchMaxWork = buf.readInt();
        this.Overclocking = buf.readInt();
    }

    protected void setOverclocking(int Overclocking) {
        this.Overclocking = Overclocking;
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
        if(Overclocking==4)tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gregtech.machine.perfect_oc"));
        tooltip.add(I18n.format("gregtech.machine.gtqt.oc",Overclocking));
        tooltip.add(I18n.format("gregtech.machine.gtqt.update.1"));
        if (setTier) tooltip.add(I18n.format("gregtech.machine.gtqt.update.2"));
        if (setTimeReduce) tooltip.add(I18n.format("gregtech.machine.time.reduce","详见机器内部UI"));
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


    protected class GTQTOCMultiblockLogic extends ComputationRecipeLogic {
        public GTQTOCMultiblockLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity, ComputationType.SPORADIC);
            this.hasPerfectOC = true;
        }

        @Override
        protected double getOverclockingDurationFactor() {
            return OCFirst ? (double) 1 /Overclocking : 0.5;
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
                autoParallel = (int) ((this.getEnergyStored() + energyContainer.getInputPerSec() / 19L) / (getMinVoltage() == 0 ? 1 : getMinVoltage()));
                autoParallel = Math.max(autoParallel, 0);
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
        public long getMaxParallelVoltage() {
            if (OCFirst) return super.getMaxParallelVoltage();
            return super.getMaxVoltage()* getParallelLimit();
        }
        @Override
        public long getMaximumOverclockVoltage() {
            if (OCFirst)return energyContainer.getInputVoltage();
            return super.getMaximumOverclockVoltage();
        }
        @Override
        public void setMaxProgress(int maxProgress) {
            super.setMaxProgress((int) (maxProgress*timeReduce));
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void configureDisplayText(MultiblockUIBuilder builder) {
        builder.setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addCustom(this::addCustomData)
                .addWorkingStatusLine()
                .addRecipeOutputLine(recipeMapWorkable);
    }

    //这里是警告
    @Override
    protected void configureWarningText(MultiblockUIBuilder builder) {
        super.configureWarningText(builder);

    }


    @Override
    protected MultiblockUIFactory createUIFactory() {
        return super.createUIFactory()
                .createFlexButton((guiData, syncManager) -> {
                    var throttle = syncManager.panel("throttle_panel", this::makeThrottlePanel, true);
                    //配置按钮 打开新UI
                    return new ButtonWidget<>()
                            .size(18)
                            .overlay(GTGuiTextures.FILTER_SETTINGS_OVERLAY.asIcon().size(16))
                            .addTooltipLine(IKey.lang("设备性能调整"))
                            .onMousePressed(i -> {
                                if (throttle.isPanelOpen()) {
                                    throttle.closePanel();
                                } else {
                                    throttle.openPanel();
                                }
                                return true;
                            });
                });
    }

    //留给外部自定义
    public void addCustomData(KeyManager keyManager, UISyncer syncer) {
        keyManager.add(KeyUtil.lang(TextFormatting.GRAY, "gui.time_reduction" , syncer.syncDouble(timeReduce)));
    }
    //新UI
    protected ModularPanel makeThrottlePanel(PanelSyncManager syncManager, IPanelHandler syncHandler) {
        StringSyncValue throttleValue = new StringSyncValue(() -> "并行限制："+limitAutoParallel, str -> {
            try {
                if (str.charAt(str.length() - 1) == '%') {
                    str = str.substring(0, str.length() - 1);
                }

                this.limitAutoParallel = Integer.parseInt(str);
            } catch (NumberFormatException ignored) {

            }
        });

        DoubleSyncValue sliderValue = new DoubleSyncValue(
                () -> (double) getLimitAutoParallel() / getMaxParallel(),
                d -> setLimitAutoParallel((int) (d * getMaxParallel())));

        StringSyncValue parallelValue = new StringSyncValue(
                // 获取值的方法
                () -> {
                    if (autoParallelModel) {
                        return "自动并行："+autoParallel + "/" + limitAutoParallel + "/" + getMaxParallel();
                    } else {
                        return "手动并行："+customParallel + "/" + getMaxParallel();
                    }
                },
                str -> {}
        );
        StringSyncValue ovValue = new StringSyncValue(
                // 获取值的方法
                () -> {
                    if (OCFirst) {
                        return "超频优先："+Overclocking;
                    } else {
                        return "并行优先";
                    }
                },
                str -> {}
        );
        StringSyncValue maxWorkTimeValue = new StringSyncValue(
                // 获取值的方法
                () -> "最大自持："+energyHatchMaxWork+"(tick)",
                str -> {}
        );
        StringSyncValue customParallelValue = new StringSyncValue(
                // 获取值的方法
                () -> "手动并行："+customParallel,
                str -> {}
        );
        BooleanSyncValue autoParallel = new BooleanSyncValue(this::getAutoParallel, this::setAutoParallel);
        syncManager.syncValue("autoParallel", autoParallel);

        BooleanSyncValue ocFirstModel = new BooleanSyncValue(this::getOCFirstModel, this::setOCFirstModel);
        syncManager.syncValue("ocFirstModel", ocFirstModel);

        IntSyncValue customParallel = new IntSyncValue(this::getCustomParallel, this::setCustomParallel);
        syncManager.syncValue("customParallel", customParallel);

        IntSyncValue maxParallelLimit = new IntSyncValue(this::getMaxParallelLimit, this::setMaxParallelLimit);
        syncManager.syncValue("maxParallelLimit", maxParallelLimit);

        IntSyncValue energyHatchWorkTime = new IntSyncValue(this::getWorkTime, this::setWorkTime);
        syncManager.syncValue("energyHatchWorkTime", energyHatchWorkTime);

        return GTGuis.createPopupPanel("boiler_throttle", 200, 200)
                //名字
                .child(Flow.row()
                        .pos(4, 4)
                        .height(16)
                        .coverChildrenWidth()
                        .child(new ItemDrawable(getStackForm())
                                .asWidget()
                                .size(16)
                                .marginRight(4))
                        .child(IKey.lang("机器性能设置")
                                .asWidget()
                                .heightRel(1.0f)))

                //第一行 调整自动并行上限
                .child(Flow.row()
                        .top(20)
                        .margin(4, 0)
                        .coverChildrenHeight()
                        .child(new com.cleanroommc.modularui.widgets.SliderWidget()
                                .left(3)
                                .background(new Rectangle().setColor(Color.BLACK.brighter(2)).asIcon().height(8))
                                .bounds(0, 1)
                                .setAxis(GuiAxis.X)
                                .value(sliderValue)
                                .widthRel(0.45f)
                                .height(22))
                        .child(new TextFieldWidget()
                                .left(95)
                                .widthRel(0.50f)
                                .height(20)
                                .setTextColor(Color.WHITE.darker(1))
                                .setValidator(str -> {
                                    if (str.charAt(str.length() - 1) == '%') {
                                        str = str.substring(0, str.length() - 1);
                                    }

                                    try {
                                        long l = Long.parseLong(str);
                                        if (l < 0) l = 0;
                                        else if (l > getMaxParallel()) l = getMaxParallel();
                                        return String.valueOf(l);
                                    } catch (NumberFormatException ignored) {
                                        return throttleValue.getValue();
                                    }
                                })
                                .value(throttleValue)
                                .background(GTGuiTextures.DISPLAY)
                        )
                )
                //第二行 调整手动并行上限
                .child(Flow.row()
                        .top(55)
                        .child(new ButtonWidget<>()
                                .left(5).widthRel(0.20f)
                                .height(22)
                                .tooltip(tooltip -> tooltip
                                        .addLine(IKey.lang("减小手动并行")))
                                .onMousePressed(mouseButton -> {
                                    customParallel.setValue(MathHelper.clamp(
                                            customParallel.getValue() - GTQTUtil.getIncrementValue(MouseData.create(mouseButton)), 1,
                                            maxParallelLimit.getValue()));
                                    return true;
                                })
                                .onUpdateListener(w -> w.overlay(GTQTUtil.createAdjustOverlay(false)))
                        )
                        .child(new ButtonWidget<>()
                                .left(55).widthRel(0.20f)
                                .height(22)
                                .tooltip(tooltip -> tooltip
                                        .addLine(IKey.lang("增大手动并行")))
                                .onMousePressed(mouseButton -> {
                                    customParallel.setValue(MathHelper.clamp(
                                            customParallel.getValue() + GTQTUtil.getIncrementValue(MouseData.create(mouseButton)), 1,
                                            maxParallelLimit.getValue()));
                                    return true;
                                })
                                .onUpdateListener(w -> w.overlay(GTQTUtil.createAdjustOverlay(true))))

                        .child(new TextFieldWidget()
                                .left(100)
                                .widthRel(0.45f)
                                .height(20)
                                .setValidator(str -> customParallelValue.getValue())
                                .value(customParallelValue)
                                .background(GTGuiTextures.DISPLAY)
                        )
                )
                //超频优先 并行优先
                .child(Flow.row()
                        .top(90)
                        .child(new ButtonWidget<>()
                                .left(5).widthRel(0.45f)
                                .height(22)
                                .tooltip(tooltip -> tooltip
                                        .addLine(IKey.lang("gui.mode_switch")))
                                .onMousePressed(mouseButton ->{
                                    autoParallel.setValue(!autoParallel.getValue());
                                    return true;
                                })
                                .onUpdateListener((w -> w.overlay(IKey.str("并行模式切换").color(Color.WHITE.main))))
                        )
                        .child(new TextFieldWidget()
                                .left(100)
                                .widthRel(0.45f)
                                .height(20)
                                .setValidator(str -> parallelValue.getValue())
                                .value(parallelValue)
                                .background(GTGuiTextures.DISPLAY)
                        )
                )
                //调整是否开启自动并行
                .child(Flow.row()
                        .top(125)
                        .child(new ButtonWidget<>()
                                .left(5).widthRel(0.45f)
                                .height(22)
                                .tooltip(tooltip -> tooltip
                                        .addLine(IKey.lang("gui.oc_parallel_mode")))
                                .onMousePressed(mouseButton ->{
                                    ocFirstModel.setValue(!ocFirstModel.getValue());
                                    return true;
                                })
                                .onUpdateListener((w -> w.overlay(IKey.str("超频/并行模式优先").color(Color.WHITE.main))))
                        )
                        .child(new TextFieldWidget()
                                .left(100)
                                .widthRel(0.45f)
                                .height(20)
                                .setValidator(str -> ovValue.getValue())
                                .value(ovValue)
                                .background(GTGuiTextures.DISPLAY)
                        )
                )
                //最大自持
                .child(Flow.row()
                        .top(160)
                        .child(new ButtonWidget<>()
                                .left(5).widthRel(0.20f)
                                .height(22)
                                .tooltip(tooltip -> tooltip
                                        .addLine(IKey.lang("gui.reset")))
                                .onMousePressed(mouseButton ->{
                                    energyHatchWorkTime.setValue(32);
                                    return true;
                                })
                                .onUpdateListener((w -> w.overlay(IKey.str("重置").color(Color.WHITE.main))))
                        )
                        .child(new ButtonWidget<>()
                                .left(55).widthRel(0.20f)
                                .height(22)
                                .tooltip(tooltip -> tooltip
                                        .addLine(IKey.lang("gui.recommend")))
                                .onMousePressed(mouseButton ->{
                                    int i;
                                    if(this.energyContainer==null)return true;
                                    if(this.energyContainer.getInputVoltage()==0)return true;
                                    i = (int) (this.energyContainer.getEnergyStored() / this.energyContainer.getInputVoltage());
                                    i = Math.max(1, i);
                                    i = Math.min(i, 128);
                                    energyHatchWorkTime.setValue(i);
                                    return true;
                                })
                                .onUpdateListener((w -> w.overlay(IKey.str("推荐").color(Color.WHITE.main))))
                        )
                        .child(new TextFieldWidget()
                                .left(100)
                                .widthRel(0.45f)
                                .height(20)
                                .setValidator(str -> maxWorkTimeValue.getValue())
                                .value(maxWorkTimeValue)
                                .background(GTGuiTextures.DISPLAY)
                        )
                );
    }

    //控件
    private void setMaxParallelLimit(int i) {

    }

    public int getMaxParallelLimit() {
        if (autoParallelModel)return limitAutoParallel;
        return getMaxParallel();
    }

    private void setCustomParallel(int i) {
        customParallel = i;
    }

    private int getCustomParallel() {
        return customParallel;
    }

    private void setWorkTime(int time) {
        energyHatchMaxWork=time;
    }

    private int getWorkTime() {
        return energyHatchMaxWork;
    }

    private void setOCFirstModel(boolean ocFirst) {
        OCFirst=ocFirst;
    }

    private boolean getOCFirstModel() {
        return OCFirst;
    }

    private void setAutoParallel(boolean autoParallel) {
        autoParallelModel = autoParallel;
    }

    private boolean getAutoParallel() {
        return autoParallelModel;
    }

    private void setLimitAutoParallel(int amount) {
        this.limitAutoParallel = amount;
    }

    private int getLimitAutoParallel() {
        return this.limitAutoParallel;
    }

    //文字显示
    public String getParallel() {
        if (autoParallelModel)return autoParallel+"/"+limitAutoParallel+"/"+getMaxParallel();
        else return customParallel+"/"+getMaxParallel();
    }
}
