package keqing.gtqtcore.common.items.behaviors;

import gregtech.api.items.metaitem.stats.IItemBehaviour;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class WaterClearBehavior  implements IItemBehaviour {
    public WaterClearBehavior() {}
    int range=1;
    boolean work=true;
    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (!world.isRemote) return EnumActionResult.SUCCESS;
        if (player.isSneaking()) {
            work=!work;
            if(work) {
                if (range < 3) range++;
                else range = 1;
                player.sendMessage(new TextComponentTranslation("清理半径：%s", range));
            }
        }
        else {
            for(int i=-range;i<range;i++) for(int j=-range;j<range;j++) for(int k=-range;k<range;k++)
            if(world.getBlockState(pos.add(i,j,k))==Blocks.WATER.getDefaultState())
            {
                world.setBlockState(pos.add(i,j,k),Blocks.SPONGE.getDefaultState());
                world.setBlockState(pos.add(i,j,k),Blocks.AIR.getDefaultState());
            }
        }
        return EnumActionResult.SUCCESS;
    }
}
