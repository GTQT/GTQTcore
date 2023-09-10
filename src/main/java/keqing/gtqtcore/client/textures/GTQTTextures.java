package keqing.gtqtcore.client.textures;

import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;

public class GTQTTextures {
    //怎么写？请看
    //https://github.com/Darknight123MC/Gregica-Sharp/blob/master/src/main/java/me/oganesson/gregicas/client/textures/GSTextures.java

    public static OrientedOverlayRenderer LIGHTNING_ROD_OVERLAY;

    public static void init() {
        LIGHTNING_ROD_OVERLAY = new OrientedOverlayRenderer("generators/lightning_rod");
        }

}
