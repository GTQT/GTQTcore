package keqing.gtqtcore.common.block.wood;

import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import java.util.Random;

import static gregtech.common.blocks.wood.BlockRubberLog.NATURAL;

public class WorldGenPine extends WorldGenerator {

    public static final WorldGenPine TREE_GROW_INSTANCE = new WorldGenPine();

    protected WorldGenPine() {
        super();
    }


    @Override
    public boolean generate(@Nonnull World world, @Nonnull Random random, @Nonnull BlockPos pos) {
        return generateImpl(world, random, new BlockPos.MutableBlockPos(pos));
    }

    public boolean generateImpl(@Nonnull World world, @Nonnull Random random, BlockPos.MutableBlockPos pos) {
        pos.setPos(pos.getX() + 8, world.getHeight() - 1, pos.getZ() + 8);
        while (pos.getY() > 0 && world.isAirBlock(pos)) {
            pos.setY(pos.getY() - 1);
        }
        pos.setY(pos.getY() + 1);
        return grow(world, pos, random);
    }

    public boolean grow(World world, BlockPos pos, Random random) {
        if (world == null) {
            return false;
        }
        SaplingGrowTreeEvent event = new SaplingGrowTreeEvent(world, random, pos);
        MinecraftForge.TERRAIN_GEN_BUS.post(event);
        if (event.getResult() == Event.Result.DENY) {
            return false;
        }
        IBlockState woodBlock = GTQTMetaBlocks.BLOCK_PINE_LOG.getDefaultState().withProperty(NATURAL, true);
        IBlockState leaves = GTQTMetaBlocks.BLOCK_PINE_LEAVES.getDefaultState();
        int height = getGrowHeight(world, pos);
        if (height < 2)
            return false;
        height -= random.nextInt(height / 2 + 1);
        height = Math.max(5, height);
        BlockPos.MutableBlockPos tmpPos = new BlockPos.MutableBlockPos();
        for (int cHeight = 0; cHeight < height; cHeight++) {
            BlockPos cPos = pos.up(cHeight);
            setBlockAndNotifyAdequately(world, cPos, woodBlock);
            if ((height < 7 && cHeight > 1) || cHeight > 2) {
                for (int cx = pos.getX() - 2; cx <= pos.getX() + 2; cx++) {
                    for (int cz = pos.getZ() - 2; cz <= pos.getZ() + 2; cz++) {
                        int chance = Math.max(1, cHeight + 4 - height);
                        int dx = Math.abs(cx - pos.getX());
                        int dz = Math.abs(cz - pos.getZ());
                        if ((dx <= 1 && dz <= 1) || (dx <= 1 && random
                                .nextInt(chance) == 0) || (dz <= 1 && random
                                .nextInt(chance) == 0)) {
                            tmpPos.setPos(cx, pos.getY() + cHeight, cz);
                            if (world.isAirBlock(tmpPos)) {
                                setBlockAndNotifyAdequately(world, new BlockPos(tmpPos), leaves);
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i <= height / 4 + random.nextInt(2); i++) {
            tmpPos.setPos(pos.getX(), pos.getY() + height + i, pos.getZ());
            if (world.isAirBlock(tmpPos))
                setBlockAndNotifyAdequately(world, new BlockPos(tmpPos), leaves);
        }
        return true;
    }

    public int getGrowHeight(@Nonnull World world, @Nonnull BlockPos pos) {
        BlockPos below = pos.down();
        IBlockState baseState = world.getBlockState(below);
        Block baseBlock = baseState.getBlock();
        if (baseBlock.isAir(baseState, world, below) ||
                !baseBlock.canSustainPlant(baseState, world, below, EnumFacing.UP, GTQTMetaBlocks.BLOCK_PINE_SAPLING) || (
                !world.isAirBlock(pos.up()) && world.getBlockState(pos.up()).getBlock() != GTQTMetaBlocks.BLOCK_PINE_SAPLING))
            return 0;
        int height = 1;
        pos = pos.up();
        while (world.isAirBlock(pos) && height < 7) {
            pos = pos.up();
            height++;
        }
        return height;
    }
}