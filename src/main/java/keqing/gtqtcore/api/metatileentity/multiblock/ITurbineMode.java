package keqing.gtqtcore.api.metatileentity.multiblock;

import keqing.gtqtcore.api.capability.IReinforcedRotorHolder;

import java.util.List;

public interface ITurbineMode {

    List<IReinforcedRotorHolder> getRotorHolders();

    int getMode();
}
