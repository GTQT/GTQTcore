package keqing.gtqtcore.api.capability;

import gregtech.api.capability.IMultipleTankHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface IVoidMinerProvider {

    boolean drainEnergy(boolean simulate);

    IMultipleTankHandler getImportFluidHandler();

    IMultipleTankHandler getExportFluidHandler();

    IItemHandlerModifiable getOutputInventory();

}