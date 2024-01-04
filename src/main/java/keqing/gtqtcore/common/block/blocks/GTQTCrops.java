package keqing.gtqtcore.common.block.blocks;

import gregtechfoodoption.block.GTFOCrop;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class GTQTCrops extends BlockCrops {

    public static GTQTcrop COPPER_CROP = GTQTcrop.create("copper");
    public static GTQTcrop IRON_CROP = GTQTcrop.create("iron");
    public static GTQTcrop TIN_CROP = GTQTcrop.create("tin");
    public static GTQTcrop GOLD_CROP = GTQTcrop.create("gold");
    public static GTQTcrop BRONZE_CROP = GTQTcrop.create("bronze");

    public static GTQTcrop CARBON_CROP = GTQTcrop.create("carbon");
}