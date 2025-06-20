package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.cover.CoverableView;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.*;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityPowerSubstation;
import keqing.gtqtcore.api.metaileentity.MetaTileEntityBaseWithControl;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4;
import keqing.gtqtcore.common.covers.CoverMicrowaveEnergyReceiver;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.metatileentities.multi.multiblockpart.MetaTileEntityMicrowaveEnergyReceiver;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

import static gregtech.api.GTValues.V;
import static gregtech.api.util.RelativeDirection.*;
import static keqing.gtqtcore.api.gui.GTQTGuiTextures.PSS_POWER;

public class MetaTileEntityMicrowaveEnergyReceiverControl extends MetaTileEntityBaseWithControl {
    @Override
    public boolean usesMui2() {
        return false;
    }
    private final ItemStackHandler inputCardInventory;
    private final ItemStackHandler outputCardInventory;
    private final ItemStackHandler pssInventory;
    MetaTileEntityPowerSubstation PSSmte;
    int[] pssPos = new int[3];
    boolean pssModel;
    int coilHeight;
    int heatingCoilLevel;
    int x;
    int y;
    int z;
    long euStore;
    int op = 0;
    int range;
    int maxLength = 10;
    int[][] io = new int[64][5];
    int circuit;

    //分别为 启动？ 坐标（三位） 等级
    public MetaTileEntityMicrowaveEnergyReceiverControl(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.inputCardInventory = new NotifiableItemStackHandler(this, 1, null, false);
        this.outputCardInventory = new NotifiableItemStackHandler(this, 1, null, false);
        this.pssInventory = new NotifiableItemStackHandler(this, 1, null, false);
    }

    @Override
    public void onRemoval() {
        super.onRemoval();

        var pos = getPos();
        if (!inputCardInventory.getStackInSlot(0).isEmpty()) {
            getWorld().spawnEntity(new EntityItem(getWorld(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, inputCardInventory.getStackInSlot(0)));
            inputCardInventory.extractItem(0, 1, false);
        }
        if (!outputCardInventory.getStackInSlot(0).isEmpty()) {
            getWorld().spawnEntity(new EntityItem(getWorld(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, outputCardInventory.getStackInSlot(0)));
            outputCardInventory.extractItem(0, 1, false);
        }
        if (!pssInventory.getStackInSlot(0).isEmpty()) {
            getWorld().spawnEntity(new EntityItem(getWorld(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, pssInventory.getStackInSlot(0)));
            pssInventory.extractItem(0, 1, false);
        }
    }

    @Override
    protected void initializeAbilities() {
        List<IEnergyContainer> energyContainer = new ArrayList<>(this.getAbilities(MultiblockAbility.INPUT_ENERGY));
        energyContainer.addAll(this.getAbilities(MultiblockAbility.INPUT_LASER));
        this.energyContainer = new EnergyContainerList(energyContainer);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("本多方块允许使用能源仓，激光仓；最多可放置4个能源仓与一个激光仓。"));
        tooltip.add(I18n.format("本多方块允许使用数据卡绑定蓄能变电站（PSS）"));
        tooltip.add(I18n.format("在输入总线放置绑定微波仓（覆盖板）的数据卡来将其存入系统对其供能，绑定的微波仓需要在多方块的供能范围内，否则不会存入系统"));
        tooltip.add(I18n.format("升级结构来获得更大的供能范围与缓存电量,最大容量为 V[Math.min(heatingCoilLevel,9)]*16*coilHeight EU"));
        tooltip.add(I18n.format("最多管理：%s 个设备,升级线圈获得更多的管理容量", 64));
        tooltip.add(I18n.format("使用操作按钮配合加减按钮完成不同功能操作"));
        tooltip.add(I18n.format("不要共用！"));
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setTag("inputCardInventory", this.inputCardInventory.serializeNBT());
        data.setTag("outputCardInventory", this.outputCardInventory.serializeNBT());
        data.setTag("pssInventory", this.pssInventory.serializeNBT());
        data.setLong("euStore", euStore);
        data.setInteger("op", op);
        data.setInteger("circuit", circuit);
        data.setInteger("range", range);
        data.setBoolean("pssModel", pssModel);
        for (int i = 0; i < 64; i++) data.setIntArray("io" + i, io[i]);

        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.inputCardInventory.deserializeNBT(data.getCompoundTag("inputCardInventory"));
        this.outputCardInventory.deserializeNBT(data.getCompoundTag("outputCardInventory"));
        this.pssInventory.deserializeNBT(data.getCompoundTag("pssInventory"));
        euStore = data.getLong("euStore");
        op = data.getInteger("op");
        circuit = data.getInteger("circuit");
        range = data.getInteger("range");
        pssModel = data.getBoolean("pssModel");
        for (int i = 0; i < 64; i++) io[i] = data.getIntArray("io" + i);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        if (pssModel&&PSSmte!=null) {
                            tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "缓存电量：%s", PSSmte.getStoredLong()));
                            tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "存储上限：%s", PSSmte.getCapacityLong()));
                            tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "设备链接上限：%s 范围半径：%s", maxLength, range));
                        } else {
                            tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "存储电量：%s/%s", euStore, maxStore()));
                            tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "高度 %s/等级 %s/范围半径 %s", coilHeight, heatingCoilLevel, range));
                            tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "设备链接上限：%s", maxLength));
                        }
                        if (op == 0) tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "序号操作"));
                        if (op == 1) tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "电压操作"));
                        if (op == 2) tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "电流操作"));
                        if (op == 3) tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "供电操作"));
                        if (op == 4) tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "终端开关"));
                        if (op == 5) tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "全清操作"));
                    }
                });
    }


    protected void addDisplayText1(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "序号：%s|供电：%s/开机：%s", circuit + 1, this.io[circuit][0] == 1, getStatue(circuit)));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "坐标：x：%s y：%s z：%s", this.io[circuit][1], this.io[circuit][2], this.io[circuit][3]));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "电压：%s/电流：%s", getAmperageVoltage(1, circuit), getAmperageVoltage(0, circuit)));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "设备等级：%s|存储电量：%s/%s", this.io[circuit][4], getEU(circuit), getMaxEU(circuit)));

                    }
                });
    }

    protected void addTotal(List<ITextComponent> textList) {
        if (!isStructureFormed()) return;
        if (pssModel && PSSmte != null) {
            textList.add(new TextComponentTranslation(">>蓄能塔"));
            textList.add(new TextComponentTranslation("坐标：%s,%s,%s", pssPos[0], pssPos[1], pssPos[2]));
            textList.add(new TextComponentTranslation("平均输入：%s", PSSmte.getAverageInLastSec()));
            textList.add(new TextComponentTranslation("平均输出：%s", PSSmte.getAverageOutLastSec()));
        } else {
            textList.add(new TextComponentTranslation(">>能源仓"));
            textList.add(new TextComponentTranslation("平均输入：%s", this.energyContainer.getInputVoltage()));
            textList.add(new TextComponentTranslation("平均输出：%s", this.energyContainer.getOutputVoltage()));
        }
    }


    protected void addInfo1(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed() && circuit - 2 >= 0) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "序号：%s", circuit - 1));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "V：%s/A：%s", getAmperageVoltage(1, circuit - 2), getAmperageVoltage(0, circuit - 2)));
                    }
                });
    }

    protected void addInfo2(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed() && circuit - 1 >= 0) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "序号：%s", circuit));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "V：%s/A：%s", getAmperageVoltage(1, circuit - 1), getAmperageVoltage(0, circuit - 1)));
                    }
                });
    }

    protected void addInfo3(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "序号：%s", circuit + 1));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "V：%s/A：%s", getAmperageVoltage(1, circuit), getAmperageVoltage(0, circuit)));
                    }
                });
    }

    protected void addInfo4(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed() && circuit + 1 < maxLength) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "序号：%s", circuit + 2));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "V：%s/A：%s", getAmperageVoltage(1, circuit + 1), getAmperageVoltage(0, circuit + 1)));
                    }
                });
    }


    protected void addInfo5(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed() && circuit + 2 < maxLength) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "序号：%s", circuit + 3));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "V：%s/A：%s", getAmperageVoltage(1, circuit + 2), getAmperageVoltage(0, circuit + 2)));
                    }
                });
    }

    public void addBarHoverText(List<ITextComponent> hoverList, int a) {
        ITextComponent cwutInfo = TextComponentUtil.stringWithColor(
                TextFormatting.AQUA,
                getEU(circuit + a) + " / " + getMaxEU(circuit + a) + " EU");
        hoverList.add(TextComponentUtil.translationWithColor(
                TextFormatting.GRAY,
                "存储电量: %s",
                cwutInfo));
    }

    public void addBarHoverText(List<ITextComponent> hoverList, long a, long b) {
        if (pssModel && PSSmte != null) {
            a = PSSmte.getStoredLong();
            b = PSSmte.getCapacityLong();
        }
        ITextComponent cwutInfo = TextComponentUtil.stringWithColor(
                TextFormatting.AQUA,
                a + " / " + b + " EU");
        hoverList.add(TextComponentUtil.translationWithColor(
                TextFormatting.GRAY,
                "存储电量: %s",
                cwutInfo));
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 300, 240);

        // Display
        builder.image(112, 4, 182, 62, GuiTextures.DISPLAY);
        builder.dynamicLabel(115, 8, () -> "微波无线能量控制塔", 0xFFFFFF);
        builder.widget((new AdvancedTextWidget(115, 20, this::addDisplayText, 16777215)).setMaxWidthLimit(202).setClickHandler(this::handleDisplayClick));

        // Display
        builder.image(112, 66, 182, 52, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(115, 70, this::addDisplayText1, 16777215)).setMaxWidthLimit(202).setClickHandler(this::handleDisplayClick));

        // Display

        int j = 0;
        //1号
        builder.image(3, 4 + j * 30, 108, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 30, this::addInfo1, 16777215)).setMaxWidthLimit(70).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(95, 4 + j * 30, 15, 30, "-2", data -> this.circuit = MathHelper.clamp(circuit - 2, 0, maxLength - 1)));
        j++;
        //2号
        builder.image(3, 4 + j * 30, 108, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 30, this::addInfo2, 16777215)).setMaxWidthLimit(70).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(95, 4 + j * 30, 15, 30, "-1", data -> this.circuit = MathHelper.clamp(circuit - 1, 0, maxLength - 1)));
        j++;
        //3号
        builder.image(3, 4 + j * 30, 108, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 30, this::addInfo3, 16777215)).setMaxWidthLimit(70).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(95, 4 + j * 30, 15, 30, "->", data -> setStatue(!getStatue(circuit), circuit)));
        j++;
        //4号
        builder.image(3, 4 + j * 30, 108, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 30, this::addInfo4, 16777215)).setMaxWidthLimit(70).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(95, 4 + j * 30, 15, 30, "+1", data -> this.circuit = MathHelper.clamp(circuit + 1, 0, maxLength - 1)));
        j++;
        //5号
        builder.image(3, 4 + j * 30, 108, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 30, this::addInfo5, 16777215)).setMaxWidthLimit(70).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(95, 4 + j * 30, 15, 30, "+2", data -> this.circuit = MathHelper.clamp(circuit + 2, 0, maxLength - 1)));


        // Display
        builder.image(3, 154, 108, 82, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 158, this::addTotal, 16777215)).setMaxWidthLimit(76).setClickHandler(this::handleDisplayClick));


        //
        builder.widget(new ClickButtonWidget(110, 120, 60, 18, "操作 -1", this::decrementThreshold));
        builder.widget(new ClickButtonWidget(170, 120, 60, 18, "操作 +1", this::incrementThreshold));
        builder.widget(new ClickButtonWidget(230, 120, 60, 18, "清空单位", this::stop));

        //op
        builder.widget(new ClickButtonWidget(110, 140, 30, 18, "序号", data -> op = 0));
        builder.widget(new ClickButtonWidget(140, 140, 30, 18, "电压", data -> op = 1));
        builder.widget(new ClickButtonWidget(170, 140, 30, 18, "电流", data -> op = 2));
        builder.widget(new ClickButtonWidget(200, 140, 30, 18, "供电", data -> {
            op = 3;
            if (io[circuit][0] == 1) io[circuit][0] = 0;
            else io[circuit][0] = 1;
        }
        ));
        builder.widget(new ClickButtonWidget(230, 140, 30, 18, "终端", data ->
        {
            op = 4;
            setStatue(!getStatue(circuit), circuit);
        }
        ));
        builder.widget(new ClickButtonWidget(260, 140, 30, 18, "全清", data -> op = 5).setTooltipText("请点击 清空单位 按钮确认你的操作"));


        builder.widget((new ProgressWidget(this::getEnergy, 112, 65, 182, 4, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, euStore, maxStore())));

        builder.widget((new ProgressWidget(() -> getRate(0), 112, 115, 182, 4, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, 0)));

        builder.widget((new ProgressWidget(() -> getRate(-2), 3, 30, 106, 3, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, -2)));
        builder.widget((new ProgressWidget(() -> getRate(-1), 3, 60, 106, 3, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, -1)));
        builder.widget((new ProgressWidget(() -> getRate(0), 3, 90, 106, 3, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, 0)));
        builder.widget((new ProgressWidget(() -> getRate(1), 3, 120, 106, 3, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, 1)));
        builder.widget((new ProgressWidget(() -> getRate(2), 3, 150, 106, 3, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, 2)));

        builder.widget(new SlotWidget(this.inputCardInventory, 0, 274, 160, true, true, true)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setChangeListener(this::markDirty)
                .setTooltipText("请放入坐标卡(MTE)"));

        builder.widget(new SlotWidget(this.outputCardInventory, 0, 274, 178, true, true, true)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setChangeListener(this::markDirty));

        builder.widget(new SlotWidget(this.pssInventory, 0, 274, 196, true, true, true)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setChangeListener(this::markDirty)
                .setTooltipText("请放入坐标卡(PSS)"));

        builder.widget(new ImageCycleButtonWidget(274, 218, 18, 18, PSS_POWER, () -> pssModel, data ->
        {
            if (pssPos != null) {
                pssModel = !pssModel;
                euStore = 0;
            }
        }).setTooltipHoverString((i) -> "gtqtcore.multiblock.universal.pss_" + (i == 0 ? "disabled" : "enabled")));
        builder.widget(new ImageWidget(274, 236, 18, 6, GuiTextures.BUTTON_POWER_DETAIL));

        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 112, 160);
        return builder;
    }

    public double getEnergy() {
        if (pssModel && PSSmte != null) return (double) PSSmte.getStoredLong() /PSSmte.getCapacityLong();
        else return (double) euStore / maxStore();
    }

    public double getRate(int x) {
        if (getMaxEU(circuit + x) == 0) return 0;
        return (double) getEU(circuit + x) / getMaxEU(circuit + x);
    }

    private void incrementThreshold(Widget.ClickData clickData) {
        if (op == 0) this.circuit = MathHelper.clamp(circuit + 1, 0, maxLength - 1);
        if (op == 1) setAmperageVoltage(1, 0, circuit);
        if (op == 2) setAmperageVoltage(0, 1, circuit);
        if (op == 3) io[circuit][0] = 1;
        if (op == 4) setStatue(true, circuit);
    }

    private void decrementThreshold(Widget.ClickData clickData) {
        if (op == 0) this.circuit = MathHelper.clamp(circuit - 1, 0, maxLength - 1);
        if (op == 1) setAmperageVoltage(-1, 0, circuit);
        if (op == 2) setAmperageVoltage(0, -1, circuit);
        if (op == 3) io[circuit][0] = 0;
        if (op == 4) setStatue(false, circuit);
    }

    private void stop(Widget.ClickData clickData) {
        if (op == 5) clean(true, 0);
        else clean(false, circuit);
    }

    public long maxStore() {
        return V[Math.min(heatingCoilLevel, 9)] * 20 * coilHeight;
    }

    public void checkPSS() {
        if (PSSmte == null) {
            ItemStack item = pssInventory.getStackInSlot(0);
            if (item.getItem() == GTQTMetaItems.GTQT_META_ITEM && item.getMetadata() == GTQTMetaItems.POS_BINDING_CARD.getMetaValue()) {
                NBTTagCompound compound = item.getTagCompound();
                if (compound != null && compound.hasKey("x") && compound.hasKey("y") && compound.hasKey("z")) {
                    x = compound.getInteger("x");
                    y = compound.getInteger("y");
                    z = compound.getInteger("z");

                    if (((x - this.getPos().getX()) * (x - this.getPos().getX())
                            + (y - this.getPos().getY()) * (y - this.getPos().getY())
                            + (z - this.getPos().getZ()) * (z - this.getPos().getZ())) <= 100) {
                        MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(x, y, z));
                        if (mte instanceof MetaTileEntityPowerSubstation) {
                            PSSmte = (MetaTileEntityPowerSubstation) mte;
                            pssPos[0] = x;
                            pssPos[1] = y;
                            pssPos[2] = z;
                        }
                    }
                }
            }
        } else {
            MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(pssPos[0], pssPos[1], pssPos[2]));
            if (mte instanceof MetaTileEntityPowerSubstation) {
                PSSmte = (MetaTileEntityPowerSubstation) mte;
            } else {
                PSSmte = null;
                pssPos = new int[3];
                pssModel = false;
            }
        }
    }

    @Override
    protected void updateFormedValid() {
        if (pssInventory.getStackInSlot(0) != ItemStack.EMPTY) checkPSS();
        else pssModel = false;
        if (euStore < 0) euStore = 0;
        if (PSSmte == null) pssModel = false;

        if (pssModel) euStore = PSSmte.getStoredLong();

        else {
            euStore = Math.min(euStore, maxStore());
            if (this.energyContainer != null && this.energyContainer.getEnergyStored() > 0 && euStore < maxStore()) {
                if (euStore + this.energyContainer.getEnergyStored() > maxStore()) {
                    this.energyContainer.removeEnergy(maxStore() - euStore);
                    euStore = maxStore();
                } else {
                    euStore = euStore + Math.max((int) this.energyContainer.getEnergyStored(), 0);
                    this.energyContainer.removeEnergy(this.energyContainer.getEnergyStored());
                }
            }
        }

        //更新
        if (checkLoacl(true)) for (int i = 0; i < maxLength; i++) {
            if (io[i][0] == 0 && getDistance(x, y, z) < range) {
                io[i][0] = 1;
                io[i][1] = x;
                io[i][2] = y;
                io[i][3] = z;
                io[i][4] = getTier(i);
                checkLoacl(false);
                GTTransferUtils.insertItem(outputCardInventory, setCard(), false);
                x = 0;
                y = 0;
                z = 0;
            }

        }
        for (int i = 0; i < maxLength; i++) {
            if (io[i][0] == 1) {
                if (!checkLoacl(i)) {
                    clean(false, i);
                }
            }
        }
        //供电
        //if电多
        for (int i = 0; i < maxLength; i++) {
            if (io[i][0] == 1) {
                addEnergy(i, V[io[i][4]]);
            }
        }
    }

    //对 x y z +eu
    public void addEnergy(int point, long eu) {
        if (io[circuit][0] == 0) return;
        // 定义 euStore 为局部变量
        MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(io[point][1], io[point][2], io[point][3]));
        if (mte == null)
            return; // 避免 NullPointerException

        if (hasCover(mte) && getStatue(point))
            addEnergyToContainer(mte, getAmperageVoltage(1, point), getAmperageVoltage(0, point));

        if (mte instanceof MetaTileEntityMicrowaveEnergyReceiver)
            addEnergyToContainer(mte, eu * 16L, 16); // 使用常量代替硬编码

    }

    private void addEnergyToContainer(MetaTileEntity mte, long voltage, int amperage) {
        for (EnumFacing facing : EnumFacing.VALUES) {
            if (mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing) instanceof IEnergyContainer) {
                IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);

                assert container != null;
                long energyNeeded = container.getEnergyCapacity() - container.getEnergyStored();
                if (energyNeeded < voltage * amperage && euStore > energyNeeded) {
                    container.addEnergy(energyNeeded);
                    if (pssModel) getEnergyBankFromPowerSubstation(PSSmte).drain(energyNeeded);
                    else euStore -= energyNeeded;
                    return;
                } else if (euStore > voltage * amperage) {
                    container.addEnergy(voltage * amperage);
                    if (pssModel) getEnergyBankFromPowerSubstation(PSSmte).drain(voltage * amperage);
                    else euStore -= voltage * amperage;
                    return;
                } else {
                    container.addEnergy(euStore);
                    if (pssModel) getEnergyBankFromPowerSubstation(PSSmte).drain(euStore);
                    else euStore = 0;
                    return;
                }
            }
        }
    }

    private MetaTileEntityPowerSubstation.PowerStationEnergyBank getEnergyBankFromPowerSubstation(MetaTileEntityPowerSubstation powerSubstation) {
        return powerSubstation.getEnergyBank();
    }

    //1为+1 -1为-1
    public void setAmperageVoltage(int v, int a, int point) {
        if (point < 0 || point > maxLength) return;

        MetaTileEntity mte = getMetaTileEntity(point);
        if (mte instanceof MetaTileEntityMicrowaveEnergyReceiver) {
            ((MetaTileEntityMicrowaveEnergyReceiver) mte).setAmperageVoltage(v, a);
            return;
        }
        if (hasCover(mte)) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                CoverableView coverable = mte.getCapability(GregtechTileCapabilities.CAPABILITY_COVER_HOLDER, facing);
                if (coverable.getCoverAtSide(facing) instanceof CoverMicrowaveEnergyReceiver) {
                    ((CoverMicrowaveEnergyReceiver) coverable.getCoverAtSide(facing)).setAmperageVoltage(v, a);
                    return;
                }
            }
        }
    }

    public int getEU(int point) {
        if (point < 0 || point > maxLength) return 0;

        MetaTileEntity mte = getMetaTileEntity(point);
        if (mte instanceof MetaTileEntity) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                if (mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing) instanceof IEnergyContainer) {
                    IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);
                    return (int) container.getEnergyStored();
                }
            }
        }
        return 0;
    }

    public int getMaxEU(int point) {
        if (point < 0 || point > maxLength) return 0;

        MetaTileEntity mte = getMetaTileEntity(point);
        if (mte instanceof MetaTileEntity) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                if (mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing) instanceof IEnergyContainer) {
                    IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);
                    return (int) container.getEnergyCapacity();
                }
            }
        }
        return 0;
    }

    public void setStatue(boolean statue, int point) {
        if (point < 0 || point > maxLength) return;

        MetaTileEntity mte = getMetaTileEntity(point);
        if (mte instanceof MetaTileEntityMicrowaveEnergyReceiver) {
            ((MetaTileEntityMicrowaveEnergyReceiver) mte).setActive(statue);
            return;
        }
        if (hasCover(mte)) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                CoverableView coverable = mte.getCapability(GregtechTileCapabilities.CAPABILITY_COVER_HOLDER, facing);
                if (coverable.getCoverAtSide(facing) instanceof CoverMicrowaveEnergyReceiver) {
                    ((CoverMicrowaveEnergyReceiver) coverable.getCoverAtSide(facing)).setActive(statue);
                    return;
                }
            }
        }
    }

    public boolean getStatue(int point) {
        if (point < 0 || point > maxLength) return false;

        MetaTileEntity mte = getMetaTileEntity(point);
        if (mte instanceof MetaTileEntityMicrowaveEnergyReceiver) {
            return ((MetaTileEntityMicrowaveEnergyReceiver) mte).active;
        }
        if (hasCover(mte)) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                CoverableView coverable = mte.getCapability(GregtechTileCapabilities.CAPABILITY_COVER_HOLDER, facing);
                if (coverable.getCoverAtSide(facing) instanceof CoverMicrowaveEnergyReceiver) {
                    return ((CoverMicrowaveEnergyReceiver) coverable.getCoverAtSide(facing)).active;
                }
            }
        }
        return false;
    }

    public int getAmperageVoltage(int num, int point) {
        if (point < 0 || point > maxLength) return 0;

        MetaTileEntity mte = getMetaTileEntity(point);
        if (mte instanceof MetaTileEntityMicrowaveEnergyReceiver) {
            if (num == 1) return ((MetaTileEntityMicrowaveEnergyReceiver) mte).Voltage;
            else return ((MetaTileEntityMicrowaveEnergyReceiver) mte).Amperage;
        }
        if (hasCover(mte)) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                CoverableView coverable = mte.getCapability(GregtechTileCapabilities.CAPABILITY_COVER_HOLDER, facing);
                if (coverable.getCoverAtSide(facing) instanceof CoverMicrowaveEnergyReceiver) {
                    if (num == 1) return ((CoverMicrowaveEnergyReceiver) coverable.getCoverAtSide(facing)).Voltage;
                    else return ((CoverMicrowaveEnergyReceiver) coverable.getCoverAtSide(facing)).Amperage;
                }
            }
        }
        return 0;
    }

    public int getTier(int point) {
        if (point < 0 || point > maxLength) return 0;

        MetaTileEntity mte = getMetaTileEntity(point);
        if (mte instanceof MetaTileEntityMicrowaveEnergyReceiver) {
            return ((MetaTileEntityMicrowaveEnergyReceiver) mte).getTier();
        }
        if (hasCover(mte)) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                CoverableView coverable = mte.getCapability(GregtechTileCapabilities.CAPABILITY_COVER_HOLDER, facing);
                if (coverable.getCoverAtSide(facing) instanceof CoverMicrowaveEnergyReceiver) {
                    return ((CoverMicrowaveEnergyReceiver) coverable.getCoverAtSide(facing)).tier;
                }
            }
        }
        return 0;
    }

    private MetaTileEntity getMetaTileEntity(int point) {
        try {
            return GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(io[point][1], io[point][2], io[point][3]));
        } catch (Exception ignored) {

        }
        return null;
    }

    //距离计算
    public int getDistance(int x, int y, int z) {
        double dx = x - this.getPos().getX();
        double dy = y - this.getPos().getY();
        double dz = z - this.getPos().getZ();
        return (int) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public ItemStack setCard() {
        ItemStack card = new ItemStack(GTQTMetaItems.POS_BINDING_CARD.getMetaItem(), 1, 417);
        NBTTagCompound nodeTagCompound = new NBTTagCompound();
        nodeTagCompound.setInteger("x", x);
        nodeTagCompound.setInteger("y", y);
        nodeTagCompound.setInteger("z", z);
        card.setTagCompound(nodeTagCompound);
        return card;
    }

    //清空 all为所有 否则需要指定point
    public void clean(boolean all, int point) {
        if (all) for (int i = 0; i < maxLength; i++) {
            io[i][0] = 1;
            io[i][1] = 0;
            io[i][2] = 0;
            io[i][3] = 0;
            io[i][4] = 0;
            x = 0;
            y = 0;
            z = 0;
        }
        else {
            io[point][0] = 0;
            io[point][1] = 0;
            io[point][2] = 0;
            io[point][3] = 0;
            io[point][4] = 0;
            x = 0;
            y = 0;
            z = 0;
        }
    }

    public boolean checkLoacl(int point) {
        MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(io[point][1], io[point][2], io[point][3]));
        if (mte instanceof MetaTileEntityMicrowaveEnergyReceiver) {
            return true;
        }
        return hasCover(mte);
    }

    //通过物品获取坐标
    public boolean checkLoacl(boolean sim) {
        ItemStack item = inputCardInventory.getStackInSlot(0);
        if (item.getItem() == GTQTMetaItems.GTQT_META_ITEM && item.getMetadata() == GTQTMetaItems.POS_BINDING_CARD.getMetaValue()) {
            NBTTagCompound compound = item.getTagCompound();
            if (compound != null && compound.hasKey("x") && compound.hasKey("y") && compound.hasKey("z")) {
                x = compound.getInteger("x");
                y = compound.getInteger("y");
                z = compound.getInteger("z");

                if (sim) for (int j = 0; j < maxLength; j++) {
                    if (io[j][0] == 1) {
                        if (x == io[j][1] && y == io[j][2] && z == io[j][3]) return false;
                    }
                }

                MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(x, y, z));
                if (mte instanceof MetaTileEntityMicrowaveEnergyReceiver) {
                    inputCardInventory.extractItem(0, 1, sim);
                    return true;
                }
                if (hasCover(mte)) {
                    inputCardInventory.extractItem(0, 1, sim);
                    return true;
                }
            }

        }

        return false;
    }

    private boolean hasCover(MetaTileEntity te) {
        if (te == null) return false;
        for (EnumFacing facing : EnumFacing.VALUES) {
            CoverableView coverable = te.getCapability(GregtechTileCapabilities.CAPABILITY_COVER_HOLDER, facing);
            if (coverable.getCoverAtSide(facing) instanceof CoverMicrowaveEnergyReceiver)
                return true;
        }
        return false;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityMicrowaveEnergyReceiverControl(metaTileEntityId);
    }

    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("CCCCC", "CCCCC", "CCCCC", "CCCCC", "CCCCC")
                .aisle("CCSCC", "CCCCC", "CCCCC", "CCCCC", "CCCCC")
                .aisle("CCCCC", "CCCCC", "CCCCC", "CCCCC", "CCCCC")
                .aisle("C   C", "  H  ", " HHH ", "  H  ", "C   C").setRepeatable(5, 25)
                .aisle("CCCCC", "CCCCC", "CCCCC", "CCCCC", "CCCCC")
                .where('S', selfPredicate())
                .where('C', states(getCasingAState())
                        .or(abilities(MultiblockAbility.INPUT_ENERGY)
                                .setMaxGlobalLimited(4))
                        .or(abilities(MultiblockAbility.INPUT_LASER)
                                .setMaxGlobalLimited(1))
                )
                .where('H', coilPredicate())
                .build();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        coilHeight = context.getInt("Count") / 5;
        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
        } else {
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
        }
        range = coilHeight * heatingCoilLevel;
        maxLength = Math.min(heatingCoilLevel, 16) * 4;
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

    private IBlockState getCasingAState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.IRIDIUM_CASING);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.IRIDIUM_CASING;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
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
