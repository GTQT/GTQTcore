package keqing.gtqtcore.api.capability;

import net.minecraftforge.items.IItemHandlerModifiable;

public interface ICatalystHatch extends IItemHandlerModifiable {

    boolean hasCatalyst();
    default void catalystConsumed(int amount) {}
}
