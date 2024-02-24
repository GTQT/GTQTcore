package keqing.gtqtcore.api.capability;

import gregtech.api.capability.IRotorHolder;
import gregtech.api.unification.material.Material;
import net.minecraft.item.ItemStack;

public interface IReinforcedRotorHolder extends IRotorHolder {

    void setRotor(ItemStack itemStack);
    void setCurrentSpeed(int speed);
    Material getRotorMaterial();
}
