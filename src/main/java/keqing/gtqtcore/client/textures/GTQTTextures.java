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
    public static SimpleOverlayRenderer NITINOL_CASING;
    public static SimpleOverlayRenderer COMPRESSED_FUSION_REACTOR_MKI_CASING;
    public static SimpleOverlayRenderer COMPRESSED_FUSION_REACTOR_MKII_CASING;
    public static SimpleOverlayRenderer COMPRESSED_FUSION_REACTOR_MKIII_CASING;
    public static void init() {
        NITINOL_CASING = new SimpleOverlayRenderer("multiblock/casings/nitinol_machine_casing");
        LIGHTNING_ROD_OVERLAY = new OrientedOverlayRenderer("generators/lightning_rod");
        INF_WATER = new SimpleOverlayRenderer("multipart/overlay_water");
        CATALYST_HATCH = new SimpleOverlayRenderer("multipart/overlay_catalysts");
        CHEMICAL_PLANT = new OrientedOverlayRenderer("multiblock/chemical_plant");
        COMPRESSED_FUSION_REACTOR_MKI_CASING = new SimpleOverlayRenderer("multiblock/casings/compressed_fusion_reactor_mki_casing");
        COMPRESSED_FUSION_REACTOR_MKII_CASING = new SimpleOverlayRenderer("multiblock/casings/compressed_fusion_reactor_mkii_casing");
        COMPRESSED_FUSION_REACTOR_MKIII_CASING = new SimpleOverlayRenderer("multiblock/casings/compressed_fusion_reactor_mkiii_casing");
        }



}
