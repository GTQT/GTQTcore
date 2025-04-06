package keqing.gtqtcore.common.pipelike.pressure.tile;



import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.pipenet.tile.TileEntityPipeBase;
import gregtech.api.util.TaskScheduler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import keqing.gtqtcore.api.GCYSValues;
import keqing.gtqtcore.api.capability.GTQTTileCapabilities;
import keqing.gtqtcore.api.capability.IPressureContainer;
import keqing.gtqtcore.api.utils.NumberFormattingUtil;
import keqing.gtqtcore.common.pipelike.pressure.PressurePipeData;
import keqing.gtqtcore.common.pipelike.pressure.PressurePipeType;
import keqing.gtqtcore.common.pipelike.pressure.net.PressurePipeNet;
import keqing.gtqtcore.common.pipelike.pressure.net.WorldPressureNet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

public class TileEntityPressurePipe extends TileEntityPipeBase<PressurePipeType, PressurePipeData> implements IDataInfoProvider {

    private WeakReference<PressurePipeNet> currentPipeNet = new WeakReference<>(null);

    public TileEntityPressurePipe() {/**/}

    public double getPressure() {
        PressurePipeNet net = getPipeNet();
        if (net != null) {
            return net.getPressure();
        }
        return GCYSValues.EARTH_PRESSURE;
    }
    public double getMinPressure() {
        PressurePipeNet net = getPipeNet();
        if (net != null) {
            return net.getMinPressure();
        }
        return 0;
    }
    public double getMaxPressure() {
        PressurePipeNet net = getPipeNet();
        if (net != null) {
            return net.getMaxPressure();
        }
        return 0;
    }



    @Override
    public <T> T getCapabilityInternal(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == GTQTTileCapabilities.CAPABILITY_PRESSURE_CONTAINER) {
            if (world == null || world.isRemote) {
                return GTQTTileCapabilities.CAPABILITY_PRESSURE_CONTAINER.cast(IPressureContainer.EMPTY);
            }
            return GTQTTileCapabilities.CAPABILITY_PRESSURE_CONTAINER.cast(getPipeNet());
        }
        return super.getCapabilityInternal(capability, facing);
    }

    public void checkPressure(double pressure) {
        if (pressure > getNodeData().getMaxPressure()*1.25) {
            causePressureExplosion();
        } else if (pressure < getNodeData().getMinPressure()*0.8) {
            causePressureExplosion();
        }
    }

    public void causePressureExplosion() {
        PressurePipeNet net = getPipeNet();
        if (net != null) net.causePressureExplosion(getWorld(), getPos());
    }

    @Override
    public void setConnection(EnumFacing side, boolean connected, boolean fromNeighbor) {
        super.setConnection(side, connected, fromNeighbor);
        if (!world.isRemote) {
            BlockPos blockPos = pos.offset(side);
            IBlockState neighbour = world.getBlockState(blockPos);
            if (!neighbour.isFullBlock() || !neighbour.isOpaqueCube()) {
                // check the pipes for unconnected things
                TaskScheduler.scheduleTask(getWorld(), this::updateLeakage);
                return;
            }
            TileEntity te = world.getTileEntity(blockPos);
            if (te instanceof IPipeTile && ((IPipeTile<?, ?>) te).getPipeBlock().getPipeTypeClass() == this.getPipeTypeClass() &&
                    ((IPipeTile<?, ?>) te).getPipeType().getThickness() != getPipeType().getThickness() &&
                    ((IPipeTile<?, ?>) te).isConnected(side.getOpposite())) {
                // mismatched connected pipe sizes leak
                TaskScheduler.scheduleTask(getWorld(), this::updateLeakage);
            }
        }
    }

    @Override
    public Class<PressurePipeType> getPipeTypeClass() {
        return PressurePipeType.class;
    }

    @Nullable
    public PressurePipeNet getPipeNet() {
        if (world == null || world.isRemote) return null;
        PressurePipeNet currentPipeNet = this.currentPipeNet.get();
        if (currentPipeNet != null && currentPipeNet.isValid() && currentPipeNet.containsNode(getPipePos())) {
            return currentPipeNet; //if current net is valid and does contain position, return it
        }
        WorldPressureNet worldFluidPipeNet = (WorldPressureNet) getPipeBlock().getWorldPipeNet(getPipeWorld());
        currentPipeNet = worldFluidPipeNet.getNetFromPos(getPipePos());
        if (currentPipeNet != null) {
            this.currentPipeNet = new WeakReference<>(currentPipeNet);
        }
        return currentPipeNet;
    }

    public boolean updateLeakage() {
        PressurePipeNet net = getPipeNet();
        if (net != null) {
            net.onLeak();
            if (!net.isNormalPressure()) causePressureExplosion();
            return !net.isNormalPressure();
        }
        return true;
    }

    @Override
    public boolean supportsTicking() {
        //return true so adding pump covers doesn't log an exception
        return true;
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        if (getPipeNet() == null) return Collections.emptyList();
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.current_pressure", new TextComponentString(NumberFormattingUtil.formatDoubleToCompactString(getPipeNet().getPressure())).setStyle(new Style().setColor(TextFormatting.AQUA))));
        list.add(new TextComponentTranslation("behavior.tricorder.min_pressure", new TextComponentString(NumberFormattingUtil.formatDoubleToCompactString(getPipeNet().getMinPressure())).setStyle(new Style().setColor(TextFormatting.GREEN))));
        list.add(new TextComponentTranslation("behavior.tricorder.max_pressure", new TextComponentString(NumberFormattingUtil.formatDoubleToCompactString(getPipeNet().getMaxPressure())).setStyle(new Style().setColor(TextFormatting.GREEN))));
        return list;
    }
}