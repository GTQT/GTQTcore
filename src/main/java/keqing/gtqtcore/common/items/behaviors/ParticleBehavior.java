package keqing.gtqtcore.common.items.behaviors;

import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.common.entities.DynamiteEntity;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ParticleBehavior implements IItemBehaviour {
    public void addInformation(ItemStack stack, List<String> lines) {
        lines.add(I18n.format("我是可爱的高能粒子"));
    }
    public ParticleBehavior() {}
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!player.capabilities.isCreativeMode) {
            itemstack.shrink(1);
        }

        if (world.isRemote) {
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }

        DynamiteEntity entity = new DynamiteEntity(world, player);
        entity.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.7F, 1.0F);

        world.spawnEntity(entity);

        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

}
