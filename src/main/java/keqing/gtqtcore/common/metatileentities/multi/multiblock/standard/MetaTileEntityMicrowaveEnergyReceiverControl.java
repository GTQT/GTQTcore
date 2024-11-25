package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.cover.CoverableView;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.ProgressWidget;
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
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.covers.CoverMicrowaveEnergyReceiver;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.metatileentities.multi.multiblockpart.MetaTileEntityMicrowaveEnergyReceiver;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

import static gregtech.api.GTValues.V;
import static gregtech.api.util.RelativeDirection.*;
import static keqing.gtqtcore.common.block.blocks.GTQTPowerSupply.SupplyType.POWER_SUPPLY_BASIC;

public class MetaTileEntityMicrowaveEnergyReceiverControl extends MetaTileEntityBaseWithControl {
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
    }

    @Override
    protected void initializeAbilities() {
        this.inputInventory = new ItemHandlerList(this.getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.outputInventory = new ItemHandlerList(this.getAbilities(MultiblockAbility.EXPORT_ITEMS));
        List<IEnergyContainer> energyContainer = new ArrayList<>(this.getAbilities(MultiblockAbility.INPUT_ENERGY));
        energyContainer.addAll(this.getAbilities(MultiblockAbility.INPUT_LASER));
        this.energyContainer=new EnergyContainerList(energyContainer);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("本多方块允许使用能源仓，激光仓；最多可放置4个能源仓。"));
        tooltip.add(I18n.format("在输入总线放置绑定微波仓（覆盖板）的数据卡来将其存入系统对其供能，绑定的微波仓需要在多方块的供能范围内，否则不会存入系统"));
        tooltip.add(I18n.format("升级结构来获得更大的供能范围与缓存电量,最大容量为 V[Math.min(heatingCoilLevel,9)]*16*coilHeight EU"));
        tooltip.add(I18n.format("最多管理：%s 个设备,升级线圈获得更多的管理容量", 64));
        tooltip.add(I18n.format("使用操作按钮配合加减按钮完成不同功能操作"));
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setLong("euStore", euStore);
        data.setInteger("op", op);
        data.setInteger("circuit", circuit);
        data.setInteger("range", range);
        for (int i = 0; i < 64; i++) data.setIntArray("io" + i, io[i]);

        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        euStore = data.getLong("euStore");
        op = data.getInteger("op");
        circuit = data.getInteger("circuit");
        range = data.getInteger("range");
        for (int i = 0; i < 64; i++) io[i] = data.getIntArray("io" + i);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "存储电量：%s/%s", euStore, maxStore()));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "设备链接上限：%s", maxLength));
                        if (op == 0) tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "序号操作"));
                        if (op == 1) tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "电压操作"));
                        if (op == 2) tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "电流操作"));
                        if (op == 3) tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "供电操作"));
                        if (op == 4) tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "终端开关"));
                        if (op == 5) tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "全清操作"));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "高度 %s/等级 %s/范围半径 %s", coilHeight, heatingCoilLevel, range));
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
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        for (int i = -3; i <= 3; i++) {
                            if (i == 0) {
                                {
                                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "N：%s | E：%s/O：%s", circuit + 1, this.io[circuit][0] == 1, getStatue(circuit)));
                                }
                            } else if (circuit + i >= 0 && circuit + i < maxLength) {
                                {
                                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "N：%s | E：%s/O：%s", circuit + i + 1, this.io[circuit + i][0] == 1, getStatue(circuit + i)));
                                }
                            }
                        }
                    }
                });
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
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 260, 240);

        // Display
        builder.image(92, 4, 162, 62, GuiTextures.DISPLAY);
        builder.dynamicLabel(95, 8, () -> "微波无线能量控制塔", 0xFFFFFF);
        builder.widget((new AdvancedTextWidget(95, 20, this::addDisplayText, 16777215)).setMaxWidthLimit(162).setClickHandler(this::handleDisplayClick));

        // Display
        builder.image(92, 66, 162, 52, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(95, 70, this::addDisplayText1, 16777215)).setMaxWidthLimit(162).setClickHandler(this::handleDisplayClick));

        // Display

        int j = 0;
        //1号
        builder.image(3, 4 + j * 30, 90, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 30, this::addInfo1, 16777215)).setMaxWidthLimit(70).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(77, 4 + j * 30, 15, 30, "-2", data -> this.circuit = MathHelper.clamp(circuit - 2, 0, maxLength - 1)));
        j++;
        //2号
        builder.image(3, 4 + j * 30, 90, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 30, this::addInfo2, 16777215)).setMaxWidthLimit(70).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(77, 4 + j * 30, 15, 30, "-1", data -> this.circuit = MathHelper.clamp(circuit - 1, 0, maxLength - 1)));
        j++;
        //3号
        builder.image(3, 4 + j * 30, 90, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 30, this::addInfo3, 16777215)).setMaxWidthLimit(70).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(77, 4 + j * 30, 15, 30, "->", data -> setStatue(!getStatue(circuit), circuit)));
        j++;
        //4号
        builder.image(3, 4 + j * 30, 90, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 30, this::addInfo4, 16777215)).setMaxWidthLimit(70).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(77, 4 + j * 30, 15, 30, "+1", data -> this.circuit = MathHelper.clamp(circuit + 1, 0, maxLength - 1)));
        j++;
        //5号
        builder.image(3, 4 + j * 30, 90, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 30, this::addInfo5, 16777215)).setMaxWidthLimit(70).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(77, 4 + j * 30, 15, 30, "+2", data -> this.circuit = MathHelper.clamp(circuit + 2, 0, maxLength - 1)));


        // Display
        builder.image(3, 154, 90, 82, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 156, this::addTotal, 16777215)).setMaxWidthLimit(76).setClickHandler(this::handleDisplayClick));


        //
        builder.widget(new ClickButtonWidget(92, 120, 48, 18, "操作 -1", this::decrementThreshold));
        builder.widget(new ClickButtonWidget(140, 120, 48, 18, "操作 +1", this::incrementThreshold));
        builder.widget(new ClickButtonWidget(188, 120, 48, 18, "清空单位", this::stop));

        //op
        builder.widget(new ClickButtonWidget(92, 140, 24, 18, "序号", data -> op = 0));
        builder.widget(new ClickButtonWidget(116, 140, 24, 18, "电压", data -> op = 1));
        builder.widget(new ClickButtonWidget(140, 140, 24, 18, "电流", data -> op = 2));
        builder.widget(new ClickButtonWidget(164, 140, 24, 18, "供电", data -> {
            op = 3;
            if (io[circuit][0] == 1) io[circuit][0] = 0;
            else io[circuit][0] = 1;
        }
        ));
        builder.widget(new ClickButtonWidget(188, 140, 24, 18, "终端", data ->
        {
            op = 4;
            setStatue(!getStatue(circuit), circuit);
        }
        ));
        builder.widget(new ClickButtonWidget(212, 140, 24, 18, "全清", data -> op = 5).setTooltipText("请点击 清空单位 按钮确认你的操作"));


        builder.widget((new ProgressWidget(() -> (double) euStore / maxStore(), 92, 65, 162, 4, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, euStore, maxStore())));

        builder.widget((new ProgressWidget(() -> getRate(0), 92, 115, 162, 4, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, 0)));

        builder.widget((new ProgressWidget(() -> getRate(-2), 3, 30, 88, 3, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, -2)));
        builder.widget((new ProgressWidget(() -> getRate(-1), 3, 60, 88, 3, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, -1)));
        builder.widget((new ProgressWidget(() -> getRate(0), 3, 90, 88, 3, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, 0)));
        builder.widget((new ProgressWidget(() -> getRate(1), 3, 120, 88, 3, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, 1)));
        builder.widget((new ProgressWidget(() -> getRate(2), 3, 150, 88, 3, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, 2)));


        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 92, 160);
        return builder;
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

    @Override
    protected void updateFormedValid() {
        if(euStore<0)euStore=0;

        if (this.energyContainer != null && this.energyContainer.getEnergyStored() > 0 && euStore < maxStore()) {
            if (euStore + this.energyContainer.getEnergyStored() > maxStore()) {
                this.energyContainer.removeEnergy(maxStore() - euStore);
                euStore = maxStore();
            } else {
                euStore = euStore + Math.max((int) this.energyContainer.getEnergyStored(), 0);
                this.energyContainer.removeEnergy(this.energyContainer.getEnergyStored());
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
                GTTransferUtils.insertItem(this.outputInventory, setCard(), false);
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
                if (energyNeeded < voltage * amperage && euStore>energyNeeded) {
                    container.addEnergy(energyNeeded);
                    euStore -= energyNeeded;
                    return;
                } else if (euStore > voltage * amperage) {
                    container.addEnergy(voltage * amperage);
                    euStore -= voltage * amperage;
                    return;
                } else {
                    container.addEnergy(euStore);
                    euStore = 0;
                    return;
                }
            }
        }
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
        var slots = this.getInputInventory().getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack item = this.getInputInventory().getStackInSlot(i);
            if (item.getItem() == GTQTMetaItems.GTQT_META_ITEM && item.getMetadata() == GTQTMetaItems.POS_BINDING_CARD.getMetaValue()) {
                NBTTagCompound compound = item.getTagCompound();
                if (compound != null && compound.hasKey("x") && compound.hasKey("y") && compound.hasKey("z")) {
                    x = compound.getInteger("x");
                    y = compound.getInteger("y");
                    z = compound.getInteger("z");

                    if (sim) for (int j = 0; j < maxLength; j++) {
                        if (io[i][0] == 1) {
                            if (x == io[i][1] && y == io[i][2] && z == io[i][3]) return false;
                        }
                    }

                    MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(x, y, z));
                    if (mte instanceof MetaTileEntityMicrowaveEnergyReceiver) {
                        this.getInputInventory().extractItem(i, 1, sim);
                        return true;
                    }
                    if (hasCover(mte)) {
                        this.getInputInventory().extractItem(i, 1, sim);
                        return true;
                    }
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
                .aisle("CCSCC", "CCCCC", "CCCCC", "CCCCC", "CCCCC")
                .aisle("C   C", "  H  ", " HHH ", "  H  ", "C   C").setRepeatable(5, 25)
                .aisle("CCCCC", "CCCCC", "CCCCC", "CCCCC", "CCCCC")
                .where('S', selfPredicate())
                .where('C', states(getCasingAState())
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY)
                                .setMaxGlobalLimited(3))
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
        return GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BASIC);
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
