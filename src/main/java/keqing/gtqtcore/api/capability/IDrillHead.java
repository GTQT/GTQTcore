package keqing.gtqtcore.api.capability;

import gregtech.api.unification.material.Material;

public interface IDrillHead {
    int getDrillHeadTier();
    boolean isAvailable();
    int getTier();
    long getWorkTime();
    long getTotalTick();
    void setWork(boolean w);
    Material getMaterial();
}
