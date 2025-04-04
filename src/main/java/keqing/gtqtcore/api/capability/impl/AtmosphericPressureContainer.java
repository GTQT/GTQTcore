package keqing.gtqtcore.api.capability.impl;

import gregtech.api.metatileentity.MetaTileEntity;
import keqing.gtqtcore.api.GCYSValues;

public class AtmosphericPressureContainer extends PressureContainer {

    /**
     * Atmopsheric pressure container which always remains at atmospheric
     *
     * @param volume the volume of the container, must be nonzero
     */
    public AtmosphericPressureContainer(MetaTileEntity metaTileEntity, double volume) {
        super(metaTileEntity, GCYSValues.EARTH_PRESSURE * 0.9, GCYSValues.EARTH_PRESSURE * 1.1, volume);
    }

    @Override
    public void setParticles(double amount) {/**/}
}