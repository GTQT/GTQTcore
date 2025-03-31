package keqing.gtqtcore.api.capability;

import gregtech.api.items.metaitem.MetaItem;
import net.minecraft.item.ItemStack;

public interface IParticle {
    ItemStack getParticle();
    ItemStack getAntiStaticParticle();
    boolean isAvailable();
    boolean consumeParticle(boolean sim);

    long getEUAdd();
}
