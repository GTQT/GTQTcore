package keqing.gtqtcore.common.pipelike.pressure.net;


import gregtech.api.pipenet.PipeNetWalker;
import keqing.gtqtcore.common.pipelike.pressure.tile.TileEntityPressurePipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PressureNetWalker extends PipeNetWalker<TileEntityPressurePipe> {

    private double pressure = -1;

    protected PressureNetWalker(World world, BlockPos sourcePipe, int walkedBlocks) {
        super(world, sourcePipe, walkedBlocks);
    }

    public static void checkPressure(World world, BlockPos start, double pressure) {
        PressureNetWalker walker = new PressureNetWalker(world, start, 0);
        walker.pressure = pressure;
        walker.traversePipeNet();
    }

    @Override
    protected PipeNetWalker<TileEntityPressurePipe> createSubWalker(World world, EnumFacing enumFacing, BlockPos blockPos, int i) {
        PressureNetWalker walker = new PressureNetWalker(world, blockPos, i);
        walker.pressure = pressure;
        return walker;
    }

    @Override
    protected void checkPipe(TileEntityPressurePipe pipeTile, BlockPos blockPos) {
        TileEntityPressurePipe pipe = pipeTile;
        pipe.checkPressure(pressure);
    }

    @Override
    protected void checkNeighbour(TileEntityPressurePipe pipeTile, BlockPos blockPos, EnumFacing enumFacing, @Nullable TileEntity tileEntity) {
    }

    @Override
    protected Class<TileEntityPressurePipe> getBasePipeClass() {
        return TileEntityPressurePipe.class;
    }
}