package keqing.gtqtcore.common.block.wood;

import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.wood.BlockRubberLog;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

public class WorldGenBigPine extends WorldGenerator {
    public static final WorldGenBigPine TREE_GROW_INSTANCE = new WorldGenBigPine();
    IBlockState TRUNK = GTQTMetaBlocks.PINE_LOG.getDefaultState().withProperty(BlockRubberLog.NATURAL, true);
    IBlockState LEAF = GTQTMetaBlocks.PINE_LEAVES.getDefaultState();

    public boolean generate( World world,  Random random,  BlockPos pos) {
        return this.generateImpl(world, random, new BlockPos.MutableBlockPos(pos));
    }

    public boolean generateImpl( World world,  Random random, BlockPos.MutableBlockPos pos) {
        pos.setPos(pos.getX() + 8, world.getHeight() - 1, pos.getZ() + 8);

        while(pos.getY() > 0 && world.isAirBlock(pos)) {
            pos.setY(pos.getY() - 1);
        }

        pos.setY(pos.getY() + 1);
        return this.grow(world, pos, random);
    }

    public boolean grow(World worldIn, BlockPos position, Random rand) {
        {
            int i = rand.nextInt(4) + 10;
            int j = 1 + rand.nextInt(4);
            int k = i - j;
            int l = 2 + rand.nextInt(3);


            BlockPos down = position.down();
            IBlockState state = worldIn.getBlockState(down);

            if (state.getBlock().canSustainPlant(state, worldIn, down, net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling) Blocks.SAPLING) && position.getY() < worldIn.getHeight() - i - 1) {
                state.getBlock().onPlantGrow(state, worldIn, down, position);
                int i3 = rand.nextInt(2);
                int j3 = 1;
                int k3 = 0;

                for (int l3 = 0; l3 <= k; ++l3) {
                    int j4 = position.getY() - 1 + i - l3;

                    for (int i2 = position.getX() - i3; i2 <= position.getX() + i3; ++i2) {
                        int j2 = i2 - position.getX();

                        for (int k2 = position.getZ() - i3; k2 <= position.getZ() + i3; ++k2) {
                            int l2 = k2 - position.getZ();

                            if (Math.abs(j2) != i3 || Math.abs(l2) != i3 || i3 <= 0) {
                                BlockPos blockpos = new BlockPos(i2, j4, k2);
                                state = worldIn.getBlockState(blockpos);

                                if (state.getBlock().canBeReplacedByLeaves(state, worldIn, blockpos)) {
                                    this.setBlockAndNotifyAdequately(worldIn, blockpos, LEAF);
                                }
                            }
                        }
                    }

                    if (i3 >= j3) {
                        i3 = k3;
                        k3 = 1;
                        ++j3;

                        if (j3 > l) {
                            j3 = l;
                        }
                    } else {
                        ++i3;
                    }
                }

                int i4 = rand.nextInt(3);

                for (int k4 = 0; k4 < i - i4; ++k4) {

                    this.setBlockAndNotifyAdequately(worldIn, position.up(k4), TRUNK);

                }

                return true;
            } else {
                return false;
            }

        }

    }
}