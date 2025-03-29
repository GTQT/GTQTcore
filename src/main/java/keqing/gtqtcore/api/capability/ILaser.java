package keqing.gtqtcore.api.capability;

import gregtech.api.capability.IEnergyContainer;
import net.minecraft.util.math.BlockPos;

public interface ILaser{
    long Laser();
    long SetLaser();
    long MaxLaser();

    void setLaser(long laser);
    int tier();
    boolean isExport();

    int Voltage();
    int Amperage();

    void addVoltage(int i);
    void addAmperage(int i);

    void setVoltage(int i);
    void setAmperage(int i);

    void setMachinePos(BlockPos pos);
}
