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
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MTECopyCardBehaviors implements IItemBehaviour, ItemUIFactory {
    boolean isAutoOutputItems;
    boolean isAutoOutputFluids;
    boolean isAllowInputFromOutputSideItems;
    boolean isAllowInputFromOutputSideFluids;
    private EnumFacing frontFacing;
    private EnumFacing outputFacingItems;
    private EnumFacing outputFacingFluids;
    private int circuit;

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

    public void addInformation(ItemStack stack, List<String> lines) {
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
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 120)
                .image(10, 8, 156, 50, GuiTextures.DISPLAY)
                .dynamicLabel(15, 13, () -> I18n.format("%s", circuit), 0xFAF9F6)
                .widget(new ClickButtonWidget(10, 68, 77, 20, I18n.format("+1"), clickData -> addCircuit(1)))
                .widget(new ClickButtonWidget(90, 68, 77, 20, I18n.format("+5"), clickData -> addCircuit(5)))
                .widget(new ClickButtonWidget(10, 91, 77, 20, I18n.format("-1"), clickData -> addCircuit(-1)))
                .widget(new ClickButtonWidget(90, 91, 77, 20, I18n.format("-5"), clickData -> addCircuit(-5)))
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
