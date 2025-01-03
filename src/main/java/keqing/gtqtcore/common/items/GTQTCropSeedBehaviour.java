package keqing.gtqtcore.common.items;

import gregtech.api.items.metaitem.stats.IItemBehaviour;
import keqing.gtqtcore.common.block.blocks.BlockCrops;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class GTQTCropSeedBehaviour implements IItemBehaviour {
    protected final BlockCrops crop;

    public GTQTCropSeedBehaviour(BlockCrops cropBlock, ItemStack seed, ItemStack crop) {
        cropBlock.setSeed(seed);
        cropBlock.setCrop(crop);
        this.crop = cropBlock;
    }

    @Override
    public ActionResult<ItemStack> onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isAirBlock(pos.up()) && this.crop.getDefaultState().getBlock().canPlaceBlockAt(world, pos.up())) {
            world.setBlockState(pos.up(), this.crop.getDefaultState());
            ItemStack heldItem = player.getHeldItem(hand);
            heldItem.shrink(1);
            return new ActionResult<>(EnumActionResult.SUCCESS, heldItem);
        }
        return new ActionResult<>(EnumActionResult.FAIL, player.getHeldItem(hand));
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        lines.add(I18n.format("gtqtcore.seed.0"));
    }
}