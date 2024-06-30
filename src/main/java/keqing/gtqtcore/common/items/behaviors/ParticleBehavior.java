package keqing.gtqtcore.common.items.behaviors;

import gregtech.api.items.metaitem.stats.IItemBehaviour;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 15, true);
        
        return EnumActionResult.SUCCESS;

    }

}
