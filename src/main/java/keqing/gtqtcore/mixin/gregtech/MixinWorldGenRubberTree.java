package keqing.gtqtcore.mixin.gregtech;

import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.wood.BlockRubberLog;
import gregtech.common.metatileentities.steam.boiler.SteamSolarBoiler;
import gregtech.common.worldgen.WorldGenRubberTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(WorldGenRubberTree.class)
public abstract class MixinWorldGenRubberTree extends WorldGenerator {

    IBlockState TRUNK = MetaBlocks.RUBBER_LOG.getDefaultState().withProperty(BlockRubberLog.NATURAL, true);
    IBlockState LEAF = MetaBlocks.RUBBER_LEAVES.getDefaultState();

    public boolean grow(World worldIn, BlockPos position, Random rand)
    {
        int i = rand.nextInt(4) + 10;
        int j = 1 + rand.nextInt(4);
        int k = i - j;
        int l = 2 + rand.nextInt(3);


            BlockPos down = position.down();
            IBlockState state = worldIn.getBlockState(down);

            if (state.getBlock().canSustainPlant(state, worldIn, down, net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling)Blocks.SAPLING) && position.getY() < worldIn.getHeight() - i - 1)
            {
                state.getBlock().onPlantGrow(state, worldIn, down, position);
                int i3 = rand.nextInt(2);
                int j3 = 1;
                int k3 = 0;

                for (int l3 = 0; l3 <= k; ++l3)
                {
                    int j4 = position.getY() + i - l3;

                    for (int i2 = position.getX() - i3; i2 <= position.getX() + i3; ++i2)
                    {
                        int j2 = i2 - position.getX();

                        for (int k2 = position.getZ() - i3; k2 <= position.getZ() + i3; ++k2)
                        {
                            int l2 = k2 - position.getZ();

                            if (Math.abs(j2) != i3 || Math.abs(l2) != i3 || i3 <= 0)
                            {
                                BlockPos blockpos = new BlockPos(i2, j4, k2);
                                state = worldIn.getBlockState(blockpos);

                                if (state.getBlock().canBeReplacedByLeaves(state, worldIn, blockpos))
                                {
                                    this.setBlockAndNotifyAdequately(worldIn, blockpos, LEAF);
                                }
                            }
                        }
                    }

                    if (i3 >= j3)
                    {
                        i3 = k3;
                        k3 = 1;
                        ++j3;

                        if (j3 > l)
                        {
                            j3 = l;
                        }
                    }
                    else
                    {
                        ++i3;
                    }
                }

                int i4 = rand.nextInt(3);

                for (int k4 = 0; k4 < i - i4; ++k4)
                {
                    this.setBlockAndNotifyAdequately(worldIn, position.up(k4), TRUNK);
                }

                return true;
            }
            else
            {
                return false;
            }

    }
}