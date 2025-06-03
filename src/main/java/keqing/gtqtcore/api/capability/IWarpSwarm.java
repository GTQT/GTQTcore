package keqing.gtqtcore.api.capability;

import gregtech.api.unification.material.Material;

public interface IWarpSwarm {
    int getWarpSwarmTier();
    Material getMaterial();
    boolean isAvailable();
    boolean applyDamage(int i);
    int getParallel();
}
