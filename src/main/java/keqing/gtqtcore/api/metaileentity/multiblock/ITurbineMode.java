package keqing.gtqtcore.api.metaileentity.multiblock;

import keqing.gtqtcore.api.capability.IReinforcedRotorHolder;

import java.util.List;

public interface ITurbineMode {

    List<IReinforcedRotorHolder> getRotorHolders();
    int getMode();
}
