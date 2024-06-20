package keqing.gtqtcore.common.items.behaviors;

import gregtech.api.items.metaitem.stats.IItemBehaviour;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class PosBindingCardBehaviors implements IItemBehaviour {
    public PosBindingCardBehaviors() {}

    public void addInformation(ItemStack stack, List<String> lines) {
        if (stack.hasTagCompound()) {
            NBTTagCompound compound = stack.getTagCompound();

            int x = compound.getInteger("x");
            int y = compound.getInteger("x");
            int z = compound.getInteger("x");

            lines.add(I18n.format("右键下蹲获取对应方块坐标，右键清空物品缓存坐标"));
            lines.add(I18n.format("-------------------"));
            lines.add(I18n.format("x：%s y：%s z：%s", x, y, z));
        }
    }
    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            if (stack.hasTagCompound()) {
                NBTTagCompound compound = stack.getTagCompound();
                compound.setBoolean("hasPos", true);
                compound.setInteger("x", pos.getX());
                compound.setInteger("y", pos.getY());
                compound.setInteger("z", pos.getZ());
            } else {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setBoolean("hasPos", false);
                compound.setInteger("x", pos.getX());
                compound.setInteger("y", pos.getY());
                compound.setInteger("z", pos.getZ());
                stack.setTagCompound(compound);
            }
        } else {
            if (stack.hasTagCompound()) {
                stack.getTagCompound().setBoolean("hasPos", false);
            } else {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setBoolean("hasPos", false);
                compound.setInteger("x", Integer.MIN_VALUE);
                compound.setInteger("y", Integer.MAX_VALUE);
                compound.setInteger("z", Integer.MAX_VALUE);
                stack.setTagCompound(compound);
            }
        }
        return EnumActionResult.SUCCESS;
    }
}
