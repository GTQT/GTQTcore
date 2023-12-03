package keqing.gtqtcore.common;

import gregtech.api.unification.material.event.MaterialEvent;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.api.unification.TJMaterials;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(
        modid = "gtqtcore"
)

public class GTQTEventHandler {

    public GTQTEventHandler() {}

    @SubscribeEvent(
            priority = EventPriority.HIGH
    )
    public static void registerMaterials(MaterialEvent event)
    {
        GTQTMaterials.register();
        TJMaterials.register();
        //在此处注册材料
    }

}
