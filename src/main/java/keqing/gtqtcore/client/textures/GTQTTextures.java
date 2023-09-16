package keqing.gtqtcore.client.textures;

import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

public class GTQTTextures {
    //怎么写？请看
    //https://github.com/Darknight123MC/Gregica-Sharp/blob/master/src/main/java/me/oganesson/gregicas/client/textures/GSTextures.java

    public static OrientedOverlayRenderer LIGHTNING_ROD_OVERLAY;
    public static OrientedOverlayRenderer CHEMICAL_PLANT;
    public static SimpleOverlayRenderer CATALYST_HATCH;
    public static SimpleOverlayRenderer INF_WATER;
    public static void init() {
        LIGHTNING_ROD_OVERLAY = new OrientedOverlayRenderer("generators/lightning_rod");
        INF_WATER = new SimpleOverlayRenderer("multipart/overlay_water");
        CATALYST_HATCH = new SimpleOverlayRenderer("multipart/overlay_catalysts");
        CHEMICAL_PLANT = new OrientedOverlayRenderer("multiblock/chemical_plant");
        }



}
