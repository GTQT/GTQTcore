package keqing.gtqtcore.common.items.behaviors;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.items.gui.ItemUIFactory;
import gregtech.api.items.gui.PlayerInventoryHolder;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.util.GTUtility;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static net.minecraft.util.EnumFacing.*;

public class MTECopyCardBehaviors implements IItemBehaviour, ItemUIFactory {
    boolean isAutoOutputItems;
    boolean isAutoOutputFluids;
    boolean isAllowInputFromOutputSideItems;
    boolean isAllowInputFromOutputSideFluids;
    private EnumFacing frontFacing;
    private EnumFacing outputFacingItems;
    private EnumFacing outputFacingFluids;
    private int circuit;
    private Boolean ItemModel = true;

    public MTECopyCardBehaviors() {
    }

    private static int getCircuitValue(MetaTileEntity mte) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Field circuitInventoryField = SimpleMachineMetaTileEntity.class.getDeclaredField("circuitInventory");
        circuitInventoryField.setAccessible(true);
        Object circuitInventory = circuitInventoryField.get(mte);

        // 使用反射调用 getCircuitValue 方法
        Method getCircuitValueMethod = circuitInventory.getClass().getDeclaredMethod("getCircuitValue");
        getCircuitValueMethod.setAccessible(true);
        int circuitValue = (int) getCircuitValueMethod.invoke(circuitInventory);
        return circuitValue;
    }

    public void writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("isAutoOutputItems", isAutoOutputItems);
        compound.setBoolean("isAutoOutputFluids", isAutoOutputFluids);
        compound.setBoolean("isAllowInputFromOutputSideItems", isAllowInputFromOutputSideItems);
        compound.setBoolean("isAllowInputFromOutputSideFluids", isAllowInputFromOutputSideFluids);
        compound.setInteger("frontFacing", frontFacing.ordinal());
        compound.setInteger("outputFacingItems", outputFacingItems.ordinal());
        compound.setInteger("outputFacingFluids", outputFacingFluids.ordinal());
        compound.setInteger("circuit", circuit);
        compound.setBoolean("ItemModel", ItemModel);
    }

    public void readFromNBT(NBTTagCompound compound) {
        isAutoOutputItems = compound.getBoolean("isAutoOutputItems");
        isAutoOutputFluids = compound.getBoolean("isAutoOutputFluids");
        isAllowInputFromOutputSideItems = compound.getBoolean("isAllowInputFromOutputSideItems");
        isAllowInputFromOutputSideFluids = compound.getBoolean("isAllowInputFromOutputSideFluids");
        frontFacing = EnumFacing.byIndex(compound.getInteger("frontFacing"));
        outputFacingItems = EnumFacing.byIndex(compound.getInteger("outputFacingItems"));
        outputFacingFluids = EnumFacing.byIndex(compound.getInteger("outputFacingFluids"));
        circuit = compound.getInteger("circuit");
        ItemModel = compound.getBoolean("ItemModel");
    }

    public void addInformation(ItemStack stack, List<String> lines) {
        lines.add("潜行右键机器可复制其IO参数，对非设备方块潜行右键可打开UI调整机器参数");
        lines.add("右键机器可粘贴已经存储的IO参数");
        lines.add("电路: " + circuit);
        lines.add("自动输出物品: " + (isAutoOutputItems ? "是" : "否"));
        lines.add("自动输出流体: " + (isAutoOutputFluids ? "是" : "否"));
        lines.add("允许从输出侧输入物品: " + (isAllowInputFromOutputSideItems ? "是" : "否"));
        lines.add("允许从输出侧输入流体: " + (isAllowInputFromOutputSideFluids ? "是" : "否"));
        if (frontFacing != null) {
            lines.add("前方朝向: " + frontFacing.getName());
        }
        if (outputFacingItems != null) {
            lines.add("物品输出朝向: " + outputFacingItems.getName());
        }
        if (outputFacingFluids != null) {
            lines.add("流体输出朝向: " + outputFacingFluids.getName());
        }
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (player.isSneaking()) {
            if (GTUtility.getMetaTileEntity(world, pos) instanceof MetaTileEntity) {

                MetaTileEntity mte = GTUtility.getMetaTileEntity(world, pos);

                frontFacing = mte.getFrontFacing();
                if (mte instanceof SimpleMachineMetaTileEntity) {
                    isAutoOutputItems = ((SimpleMachineMetaTileEntity) mte).isAutoOutputItems();
                    isAutoOutputFluids = ((SimpleMachineMetaTileEntity) mte).isAutoOutputFluids();

                    isAllowInputFromOutputSideItems = ((SimpleMachineMetaTileEntity) mte).isAllowInputFromOutputSideItems();
                    isAllowInputFromOutputSideFluids = ((SimpleMachineMetaTileEntity) mte).isAllowInputFromOutputSideFluids();

                    outputFacingItems = ((SimpleMachineMetaTileEntity) mte).getOutputFacingItems();
                    outputFacingFluids = ((SimpleMachineMetaTileEntity) mte).getOutputFacingFluids();


                    // 使用反射获取 circuitInventory 字段
                    try {
                        circuit = getCircuitValue(mte);
                    } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException |
                             InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    player.sendStatusMessage(new TextComponentTranslation("复制"), true);

                    String machineName = mte.getMetaFullName();
                    int x = pos.getX();
                    int y = pos.getY();
                    int z = pos.getZ();
                    if (!world.isRemote) {
                        TextComponentString detailedInfo = new TextComponentString("\n======================================\n");
                        detailedInfo.appendSibling(new TextComponentString("已复制：").appendSibling(new TextComponentTranslation(machineName)));
                        detailedInfo.appendSibling(new TextComponentString("\n坐标：X=")
                                .appendSibling(new TextComponentString(String.valueOf(x)))
                                .appendSibling(new TextComponentString(", Y="))
                                .appendSibling(new TextComponentString(String.valueOf(y)))
                                .appendSibling(new TextComponentString(", Z="))
                                .appendSibling(new TextComponentString(String.valueOf(z))));

                        detailedInfo.appendSibling(new TextComponentString("\n内部参数 ："));

                        detailedInfo.appendSibling(new TextComponentString("\n自动输出物品：").appendSibling(new TextComponentString(String.valueOf(isAutoOutputItems))));
                        detailedInfo.appendSibling(new TextComponentString("\n自动输出流体：").appendSibling(new TextComponentString(String.valueOf(isAutoOutputFluids))));
                        detailedInfo.appendSibling(new TextComponentString("\n允许从输出侧输入物品：").appendSibling(new TextComponentString(String.valueOf(isAllowInputFromOutputSideItems))));
                        detailedInfo.appendSibling(new TextComponentString("\n允许从输出侧输入流体：").appendSibling(new TextComponentString(String.valueOf(isAllowInputFromOutputSideFluids))));
                        detailedInfo.appendSibling(new TextComponentString("\n输出物品方向：").appendSibling(new TextComponentString(String.valueOf(outputFacingItems))));
                        detailedInfo.appendSibling(new TextComponentString("\n输出流体方向：").appendSibling(new TextComponentString(String.valueOf(outputFacingFluids))));
                        detailedInfo.appendSibling(new TextComponentString("\n电路配置：").appendSibling(new TextComponentString(String.valueOf(circuit))));
                        detailedInfo.appendSibling(new TextComponentString("\n======================================"));

                        player.sendMessage(detailedInfo);
                    }
                }
            } else {
                if (!world.isRemote) {
                    PlayerInventoryHolder holder = new PlayerInventoryHolder(player, hand);
                    holder.openUI();
                }
            }
        } else {
            if (GTUtility.getMetaTileEntity(world, pos) instanceof MetaTileEntity) {

                MetaTileEntity mte = GTUtility.getMetaTileEntity(world, pos);

                if (frontFacing != null) mte.setFrontFacing(frontFacing);
                if (mte instanceof SimpleMachineMetaTileEntity) {
                    ((SimpleMachineMetaTileEntity) mte).setAutoOutputItems(isAutoOutputItems);
                    ((SimpleMachineMetaTileEntity) mte).setAutoOutputFluids(isAutoOutputFluids);

                    ((SimpleMachineMetaTileEntity) mte).setAllowInputFromOutputSideItems(isAllowInputFromOutputSideItems);
                    ((SimpleMachineMetaTileEntity) mte).setAllowInputFromOutputSideFluids(isAllowInputFromOutputSideFluids);

                    if (outputFacingItems != null)
                        ((SimpleMachineMetaTileEntity) mte).setOutputFacingItems(outputFacingItems);
                    if (outputFacingFluids != null)
                        ((SimpleMachineMetaTileEntity) mte).setOutputFacingFluids(outputFacingFluids);

                    ((SimpleMachineMetaTileEntity) mte).setGhostCircuitConfig(circuit);
                    player.sendStatusMessage(new TextComponentTranslation("粘贴"), true);
                }
            }
        }
        return EnumActionResult.SUCCESS;
    }

    public ModularUI createUI(PlayerInventoryHolder playerInventoryHolder, EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 265, 150)
                .image(10, 8, 154, 65, GuiTextures.DISPLAY)
                .dynamicLabel(15, 13, () -> I18n.format("编程电路：%s", circuit), 0xFAF9F6)
                .dynamicLabel(15, 23, () -> I18n.format("物品自动输出：%s", isAutoOutputItems), 0xFAF9F6)
                .dynamicLabel(15, 33, () -> I18n.format("流体自动输出：%s", isAutoOutputFluids), 0xFAF9F6)
                .dynamicLabel(15, 43, () -> I18n.format("物品输出面输入：%s", isAllowInputFromOutputSideItems), 0xFAF9F6)
                .dynamicLabel(15, 53, () -> I18n.format("流体输出面输入：%s", isAllowInputFromOutputSideFluids), 0xFAF9F6)

                .dynamicLabel(100, 13, () -> I18n.format("设备面：%s", frontFacing), 0xFAF9F6)
                .dynamicLabel(100, 23, () -> I18n.format("物品面：%s", outputFacingItems), 0xFAF9F6)
                .dynamicLabel(100, 33, () -> I18n.format("流体面：%s", outputFacingFluids), 0xFAF9F6)


                .widget(new ClickButtonWidget(10, 78, 38, 20, I18n.format("+1"), clickData -> addCircuit(1)))
                .widget(new ClickButtonWidget(90, 78, 38, 20, I18n.format("-1"), clickData -> addCircuit(-1)))

                .widget(new ClickButtonWidget(48, 78, 38, 20, I18n.format("+5"), clickData -> addCircuit(5)))
                .widget(new ClickButtonWidget(128, 78, 38, 20, I18n.format("-5"), clickData -> addCircuit(-5)))

                .widget(new ClickButtonWidget(10, 101, 76, 20, I18n.format("物品自动输出"), clickData -> isAutoOutputItems = !isAutoOutputItems))
                .widget(new ClickButtonWidget(90, 101, 76, 20, I18n.format("流体自动输出"), clickData -> isAutoOutputFluids = !isAutoOutputFluids))

                .widget(new ClickButtonWidget(10, 124, 76, 20, I18n.format("物品输出面输入"), clickData -> isAllowInputFromOutputSideItems = !isAllowInputFromOutputSideItems))
                .widget(new ClickButtonWidget(90, 124, 76, 20, I18n.format("流体输出面输入"), clickData -> isAllowInputFromOutputSideFluids = !isAllowInputFromOutputSideFluids))

                //上
                .widget(new ClickButtonWidget(195, 10, 15, 15, I18n.format("上"), clickData -> frontFacing = UP))
                //前
                .widget(new ClickButtonWidget(195, 25, 15, 15, I18n.format("前"), clickData -> frontFacing = entityPlayer.getHorizontalFacing().getOpposite()))
                //下
                .widget(new ClickButtonWidget(195, 40, 15, 15, I18n.format("下"), clickData -> frontFacing = DOWN))
                //左
                .widget(new ClickButtonWidget(180, 25, 15, 15, I18n.format("左"), clickData -> frontFacing = entityPlayer.getHorizontalFacing().getOpposite().rotateY()))
                //右
                .widget(new ClickButtonWidget(210, 25, 15, 15, I18n.format("右"), clickData -> frontFacing = entityPlayer.getHorizontalFacing().rotateY()))
                //后
                .widget(new ClickButtonWidget(180, 40, 15, 15, I18n.format("后"), clickData -> frontFacing = entityPlayer.getHorizontalFacing()))

                .image(178, 56, 70, 16, GuiTextures.DISPLAY)
                .dynamicLabel(180, 60, () -> {
                    String mode = (ItemModel != null) ? (ItemModel ? "物品模式" : "流体模式") : "未初始化";
                    return I18n.format("模式：%s", mode);
                }, 0xFAF9F6)

                .widget(new ClickButtonWidget(180, 80, 35, 15, I18n.format("I/F"), clickData -> ItemModel = !ItemModel))
                .widget(new ClickButtonWidget(220, 80, 35, 15, I18n.format("推荐值"), clickData ->
                {
                    frontFacing = entityPlayer.getHorizontalFacing().getOpposite();
                    outputFacingItems = entityPlayer.getHorizontalFacing().rotateAround(Axis.X);
                    outputFacingFluids = entityPlayer.getHorizontalFacing().rotateAround(Axis.Z);
                }
                ))

                //上
                .widget(new ClickButtonWidget(195, 95, 15, 15, I18n.format("上"), clickData ->
                {
                    if (ItemModel)
                        outputFacingItems = UP;
                    else outputFacingFluids = UP;
                }))
                //前
                .widget(new ClickButtonWidget(195, 110, 15, 15, I18n.format("前"), clickData ->
                {
                    if (ItemModel)
                        outputFacingItems = frontFacing;
                    else outputFacingFluids = frontFacing;
                }))
                //下
                .widget(new ClickButtonWidget(195, 125, 15, 15, I18n.format("下"), clickData ->
                {
                    if (ItemModel)
                        outputFacingItems = DOWN;
                    else outputFacingFluids = DOWN;
                }))
                //左
                .widget(new ClickButtonWidget(180, 110, 15, 15, I18n.format("左"), clickData ->
                {
                    if (ItemModel)
                        outputFacingItems = frontFacing.rotateY();
                    else outputFacingFluids = frontFacing.rotateY();
                }))
                //右
                .widget(new ClickButtonWidget(210, 110, 15, 15, I18n.format("右"), clickData ->
                {
                    if (ItemModel)
                        outputFacingItems = frontFacing.rotateY().getOpposite();
                    else outputFacingFluids = frontFacing.rotateY().getOpposite();
                }))
                //后
                .widget(new ClickButtonWidget(180, 125, 15, 15, I18n.format("后"), clickData ->
                {
                    if (ItemModel)
                        outputFacingItems = frontFacing.getOpposite();
                    else outputFacingFluids = frontFacing.getOpposite();
                }))
                .build(playerInventoryHolder, entityPlayer);
    }

    public void addCircuit(int i) {
        if (i > 0 && circuit + i > 32) {
            circuit = 32;
            return;
        }
        if (i < 0 && circuit + i < 0) {
            circuit = 0;
            return;
        }

        circuit += i;
    }
}
