package keqing.gtqtcore.api.capability.impl;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import keqing.gtqtcore.api.GCYSValues;
import keqing.gtqtcore.api.capability.GTQTTileCapabilities;
import keqing.gtqtcore.api.capability.IPressureContainer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PressureContainer extends MTETrait implements IPressureContainer {

    private final double minPressure;
    private final double maxPressure;
    private final double volume;

    private double particles;

    /**
     * Default pressure container
     * {@link IPressureContainer}
     *
     * @param volume the volume of the container, must be nonzero
     */
    public PressureContainer(MetaTileEntity metaTileEntity, double minPressure, double maxPressure, double volume) {
        super(metaTileEntity);
        this.minPressure = minPressure;
        this.maxPressure = maxPressure;
        this.volume = volume;
        this.particles = volume * GCYSValues.EARTH_PRESSURE;
    }
    public PressureContainer(MetaTileEntity metaTileEntity, double minPressure, double maxPressure,  double volume,double particles) {
        super(metaTileEntity);
        this.minPressure = minPressure;
        this.maxPressure = maxPressure;
        this.volume = volume;
        this.particles = particles;
    }

    @Override
    public double getMaxPressure() {
        return this.maxPressure;
    }

    @Override
    public double getParticles() {
        return this.particles;
    }

    @Override
    public void setParticles(double amount) {
        this.particles = amount;
        this.metaTileEntity.markDirty();
    }

    @Override
    public double getVolume() {
        return this.volume;
    }

    @Override
    public double getMinPressure() {
        return this.minPressure;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setDouble("particles", this.particles);
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        this.particles = compound.getDouble("particles");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buffer) {
        super.writeInitialSyncData(buffer);
        buffer.writeDouble(this.particles);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buffer) {
        super.receiveInitialSyncData(buffer);
        this.particles = buffer.readDouble();
    }

    @Override
    public String getName() {
        return "PressureContainer";
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == GTQTTileCapabilities.CAPABILITY_PRESSURE_CONTAINER) {
            return GTQTTileCapabilities.CAPABILITY_PRESSURE_CONTAINER.cast(this);
        }
        return null;
    }
}