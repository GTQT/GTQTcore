package keqing.gtqtcore.common.items.behaviors;

import gregtech.api.items.gui.ItemUIFactory;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.pipenet.tile.TileEntityPipeBase;
import gregtech.api.unification.material.Material;
import gregtech.common.pipelike.cable.BlockCable;
import gregtech.common.pipelike.cable.tile.TileEntityCable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.util.List;

import static net.minecraft.init.Blocks.AIR;

public class WireTransBehavior implements IItemBehaviour, ItemUIFactory {

    int connections;

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (world.isRemote) return EnumActionResult.SUCCESS;
        TileEntity tileEntity = world.getTileEntity(pos);
        IBlockState state = world.getBlockState(pos);

        if (tileEntity instanceof TileEntityCable cable) {
            connections = cable.getConnections(); // 保存原始连接状态
            BlockCable cableBlock = (BlockCable) state.getBlock();
            Material material = cable.getPipeMaterial();
            ItemStack originalCableItem = cableBlock.getItem(material);

            // 遍历玩家背包寻找可替换的电缆
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack slotStack = player.inventory.getStackInSlot(i);
                if (slotStack.isEmpty()) continue;

                Block targetBlock = Block.getBlockFromItem(slotStack.getItem());

                if (targetBlock instanceof BlockCable) {
                    // 消耗玩家手中的电缆物品
                    slotStack.shrink(1);

                    // 放置新电缆方块
                    if (slotStack.getItem() instanceof ItemBlock itemBlock) {
                        world.setBlockState(pos, AIR.getDefaultState());

                        Block block = itemBlock.getBlock();
                        state = block.getStateFromMeta(itemBlock.getMetadata(slotStack.copy().splitStack(1).getMetadata()));
                        itemBlock.placeBlockAt(slotStack.copy().splitStack(1), player, world, pos, EnumFacing.NORTH, pos.getX(), pos.getY(), pos.getZ(), state);
                    }

                    // 处理旧电缆物品
                    if (!player.inventory.addItemStackToInventory(originalCableItem.copy())) {
                        EntityItem entityItem = new EntityItem(
                                world,
                                player.posX,
                                player.posY + 0.5,
                                player.posZ,
                                originalCableItem.copy()
                        );
                        entityItem.setNoPickupDelay();
                        world.spawnEntity(entityItem);
                    }

                    // 设置新电缆的连接状态
                    TileEntity newCable = world.getTileEntity(pos);
                    if (newCable instanceof TileEntityCable) {
                        Material newMaterial = ((TileEntityCable) newCable).getPipeMaterial();
                        try {
                            // 使用反射保留连接状态（建议后续改用公共方法）
                            Field connectionsField = TileEntityPipeBase.class.getDeclaredField("connections");
                            connectionsField.setAccessible(true);
                            connectionsField.setInt(newCable, connections);

                            // 强制同步数据
                            newCable.markDirty();
                            world.notifyBlockUpdate(
                                    pos,
                                    world.getBlockState(pos),
                                    world.getBlockState(pos),
                                    3
                            );
                        } catch (Exception e) {
                            player.sendMessage(new TextComponentString("正在尝试替换" + pos + "位置的线缆故障！：电缆连接状态同步失败").setStyle(new Style().setColor(TextFormatting.YELLOW)));
                        }
                        player.sendMessage(new TextComponentString("正在尝试替换" + pos + "位置的线缆成功").setStyle(new Style().setColor(TextFormatting.GREEN)));
                        player.sendMessage(new TextComponentString("参数："+ newMaterial.getLocalizedName() +" "+((TileEntityCable) newCable).getMaxVoltage()+" V/ "+((TileEntityCable) newCable).getMaxAmperage()+" A").setStyle(new Style().setColor(TextFormatting.GREEN)));
                    }


                    // 更新客户端显示
                    world.playSound(
                            player,
                            pos,
                            SoundEvents.BLOCK_METAL_PLACE,
                            SoundCategory.BLOCKS,
                            1.0F,
                            1.0F
                    );
                    return EnumActionResult.SUCCESS;
                }
                player.sendMessage(new TextComponentString("正在尝试替换" + pos + "位置的线缆成功！：背包数量不可用").setStyle(new Style().setColor(TextFormatting.RED)));
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        lines.add(I18n.format("替换所选线缆为背包内位置靠前的线缆"));
    }
}
