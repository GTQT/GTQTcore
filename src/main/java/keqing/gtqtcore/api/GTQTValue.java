package keqing.gtqtcore.api;

import keqing.gtqtcore.GTQTCore;
import net.minecraft.util.ResourceLocation;

public class GTQTValue {
    public static ResourceLocation gtqtcoreId(String id) {
        return new ResourceLocation(GTQTCore.MODID, id);
    }
}
