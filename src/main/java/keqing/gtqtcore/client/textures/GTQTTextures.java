package keqing.gtqtcore.client.textures;

import codechicken.lib.texture.TextureUtils;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.renderer.textures.OverlayRenderer;
import keqing.gtqtcore.client.textures.custom.IsaMillRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;


public class GTQTTextures {
    public static SimpleOverlayRenderer FISHING_CASING;
    public static OverlayRenderer MULTIPART_BUFFER_HATCH = new OverlayRenderer("multipart/overlay_buffer_hatch");
    public static OrientedOverlayRenderer ROCKET_ENGINE_OVERLAY = new OrientedOverlayRenderer("machines/rocket_engine");
    public static OrientedOverlayRenderer NAQUADAH_REACTOR_OVERLAY = new OrientedOverlayRenderer("machines/naquadah_reactor");
    //怎么写？请看
    //https://github.com/Darknight123MC/Gregica-Sharp/blob/master/src/main/java/me/oganesson/gregicas/client/textures/GSTextures.java
    public static TextureAtlasSprite HALO;
    public static OrientedOverlayRenderer LIGHTNING_ROD_OVERLAY;
    public static OrientedOverlayRenderer CHEMICAL_PLANT;
    public static SimpleOverlayRenderer CATALYST_HATCH;
    public static SimpleOverlayRenderer HYPER_CASING;
    public static SimpleOverlayRenderer POLYBENZIMIDAZOLE_PIPE;
    public static SimpleOverlayRenderer QUANTUM_CONSTRAINT_CASING;
    public static SimpleOverlayRenderer PD_CASING;
    public static SimpleOverlayRenderer FLOTATION_CASING;
    public static SimpleOverlayRenderer BRICK;
    public static SimpleOverlayRenderer ADVANCED_INVAR_CASING;
    public static SimpleOverlayRenderer MACERATOR_CASING;
    public static SimpleOverlayRenderer NQ_CASING;
    public static SimpleOverlayRenderer VACUUM_CASING;
    public static SimpleOverlayRenderer IRIDIUM_CASING;
    public static SimpleOverlayRenderer INF_WATER;
    public static SimpleOverlayRenderer NITINOL_CASING;
    public static SimpleOverlayRenderer ISA_CASING;
    public static SimpleOverlayRenderer COMPRESSED_FUSION_REACTOR_MKI_CASING;
    public static SimpleOverlayRenderer COMPRESSED_FUSION_REACTOR_MKII_CASING;
    public static SimpleOverlayRenderer COMPRESSED_FUSION_REACTOR_MKIII_CASING;
    public static SimpleOverlayRenderer ADV_DIMENSIONAL_CASING_B;
    public static SimpleOverlayRenderer ADV_DIMENSIONAL_CASING_O;
    public static SimpleOverlayRenderer ADV_MACHINE_LESU;
    public static SimpleOverlayRenderer ADV_MACHINE_TECH;
    public static SimpleOverlayRenderer ADV_MACHINE_BASIC;
    public static SimpleOverlayRenderer ADV_MACHINE_MATTERFAB;
    public static SimpleOverlayRenderer ADV_MACHINE_MATTERFAB_ACTIVE;
    public static SimpleOverlayRenderer ADV_MACHINE_MATTERFAB_ACTIVE_ANIMATED;
    public static SimpleOverlayRenderer ADV_MACHINE_MATTERFAB_ANIMATED;
    public static SimpleOverlayRenderer ADV_MACHINE_VENT_ROTARING;
    public static SimpleOverlayRenderer ADV_MACHINE_TUBBINE;
    public static SimpleOverlayRenderer QUANTUM_CASING;
    public static SimpleOverlayRenderer HC_ALLOY_CASING;
    public static SimpleOverlayRenderer SFTC;
    public static SimpleOverlayRenderer SFTS;
    public static TextureAtlasSprite FORCE_FIELD;

    public static IsaMillRenderer ISA_MILL = new IsaMillRenderer();

    public static OrientedOverlayRenderer CATALYTIC_REFORMER_OVERLAY = new OrientedOverlayRenderer("overlay/catalytic_reformer");
    public static OrientedOverlayRenderer CRYSTALLIZATION_CRUCIBLE_OVERLAY = new OrientedOverlayRenderer("overlay/crystallization_crucible");
    public static OrientedOverlayRenderer CVD_UNIT_OVERLAY = new OrientedOverlayRenderer("overlay/cvd_unit");
    public static OrientedOverlayRenderer NANOSCALE_FABRICATOR_OVERLAY = new OrientedOverlayRenderer("overlay/nanoscale_fabricator");
    public static OrientedOverlayRenderer SONICATOR_OVERLAY = new OrientedOverlayRenderer("overlay/sonicator");
    public static OrientedOverlayRenderer ISA_MILL_OVERLAY = new OrientedOverlayRenderer("overlay/isa_mill");
    public static OrientedOverlayRenderer BURNER_REACTOR_OVERLAY = new OrientedOverlayRenderer("overlay/burner_reactor");
    public static OrientedOverlayRenderer CRYOGENIC_REACTOR_OVERLAY = new OrientedOverlayRenderer("overlay/cryogenic_reactor");
    public static OrientedOverlayRenderer VACUUM_DRYING_FURNACE_OVERLAY = new OrientedOverlayRenderer("overlay/vacuum_drying_furnace");
    public static OrientedOverlayRenderer CHEMICAL_PLANT_OVERLAY = new OrientedOverlayRenderer("overlay/ep_chemical_plant");
    public static OrientedOverlayRenderer INDUSTRIAL_DRILL_OVERLAY = new OrientedOverlayRenderer("overlay/industrial_drill");
    public static OrientedOverlayRenderer FRACKER_OVERLAY = new OrientedOverlayRenderer("overlay/fracker");
    public static OrientedOverlayRenderer ROASTER_OVERLAY = new OrientedOverlayRenderer("overlay/roaster");
    public static OrientedOverlayRenderer ALGAE_FARM_OVERLAY = new OrientedOverlayRenderer("overlay/algae_farm");
    public static OrientedOverlayRenderer MEGA_TURBINE_OVERLAY = new OrientedOverlayRenderer("overlay/mega_turbine");
    public static OrientedOverlayRenderer DRYER_OVERLAY = new OrientedOverlayRenderer("machines/dryer");
    public static OrientedOverlayRenderer STELLAR_FURNACE_OVERLAY = new OrientedOverlayRenderer("overlay/stellar_furnace");
    public static OrientedOverlayRenderer CYCLOTRON_OVERLAY = new OrientedOverlayRenderer("overlay/cyclotron");
    public static OrientedOverlayRenderer DRAGON_FUSION_OVERLAY = new OrientedOverlayRenderer("overlay/dragon_fusion");
    public static OrientedOverlayRenderer MULTIPART_BALL_HATCH = new OrientedOverlayRenderer("multiparts/overlay_ball_hatch");

    public static void init() {
        FLOTATION_CASING = new SimpleOverlayRenderer("multiblock/isa_casing/flotation_casing");
        ISA_CASING = new SimpleOverlayRenderer("multiblock/isa_casing/isa_mill_casing");
        VACUUM_CASING = new SimpleOverlayRenderer("multiblock/isa_casing/vacuum_casing");
        NITINOL_CASING = new SimpleOverlayRenderer("multiblock/casings/nitinol_machine_casing");
        MACERATOR_CASING = new SimpleOverlayRenderer("multiblock/casing/macerator_casing");
        PD_CASING = new SimpleOverlayRenderer("multiblock/casing/pd_turbine_casing");
        HYPER_CASING = new SimpleOverlayRenderer("multiblock/casing/hyper_casing");
        BRICK = new SimpleOverlayRenderer("multiblock/casing/brick");
        FISHING_CASING = new SimpleOverlayRenderer("multiblock/casing/fishing_casing");
        POLYBENZIMIDAZOLE_PIPE = new SimpleOverlayRenderer("multiblock/casing/talonite_casing");
        QUANTUM_CONSTRAINT_CASING = new SimpleOverlayRenderer("multiblock/quantum_force_transformer_casing/quantum_constraint_casing");
        ADVANCED_INVAR_CASING = new SimpleOverlayRenderer("multiblock/casing/advanced_invar_casing");
        NQ_CASING = new SimpleOverlayRenderer("multiblock/casing/nq_turbine_casing");
        IRIDIUM_CASING = new SimpleOverlayRenderer("multiblock/casing/iridium_casing");
        HC_ALLOY_CASING = new SimpleOverlayRenderer("multiblock/casings/hc_alloy_casing");
        SFTC = new SimpleOverlayRenderer("multiblock/casings/supercritical_fluid_turbine_casing");
        SFTS = new SimpleOverlayRenderer("multiblock/casings/supercritical_fluid_turbine_shaft");
        LIGHTNING_ROD_OVERLAY = new OrientedOverlayRenderer("generators/lightning_rod");
        INF_WATER = new SimpleOverlayRenderer("multipart/overlay_water");
        CATALYST_HATCH = new SimpleOverlayRenderer("multipart/overlay_catalysts");
        CHEMICAL_PLANT = new OrientedOverlayRenderer("multiblock/chemical_plant");
        COMPRESSED_FUSION_REACTOR_MKI_CASING = new SimpleOverlayRenderer("multiblock/casings/compressed_fusion_reactor_mki_casing");
        COMPRESSED_FUSION_REACTOR_MKII_CASING = new SimpleOverlayRenderer("multiblock/casings/compressed_fusion_reactor_mkii_casing");
        COMPRESSED_FUSION_REACTOR_MKIII_CASING = new SimpleOverlayRenderer("multiblock/casings/compressed_fusion_reactor_mkiii_casing");
        ADV_DIMENSIONAL_CASING_B = new SimpleOverlayRenderer("multiblock/advblock/adv_machine_dimensional_cover_blue");
        ADV_DIMENSIONAL_CASING_O = new SimpleOverlayRenderer("multiblock/advblock/adv_machine_dimensional_cover_orange");
        ADV_MACHINE_LESU = new SimpleOverlayRenderer("multiblock/advblock/adv_machine_lesu");
        ADV_MACHINE_TECH = new SimpleOverlayRenderer("multiblock/advblock/adv_machine_tech");
        ADV_MACHINE_BASIC = new SimpleOverlayRenderer("multiblock/advblock/adv_machine_basic");
        ADV_MACHINE_MATTERFAB = new SimpleOverlayRenderer("multiblock/advblock/adv_machine_matterfab");
        ADV_MACHINE_MATTERFAB_ACTIVE = new SimpleOverlayRenderer("multiblock/advblock/adv_machine_matterfab_active");
        ADV_MACHINE_MATTERFAB_ACTIVE_ANIMATED = new SimpleOverlayRenderer("multiblock/advblock/adv_machine_matterfab_active_animated");
        ADV_MACHINE_MATTERFAB_ANIMATED = new SimpleOverlayRenderer("multiblock/advblock/adv_machine_matterfab_animated");
        ADV_MACHINE_VENT_ROTARING = new SimpleOverlayRenderer("multiblock/advblock/adv_machine_vent_rotating");
        ADV_MACHINE_TUBBINE = new SimpleOverlayRenderer("multiblock/advblock/adv_machine_tubbine");
        QUANTUM_CASING = new SimpleOverlayRenderer("multiblock/quantumcasing/quantumcasing");

        }

        public static void register(TextureMap textureMap) {
            FORCE_FIELD = textureMap.registerSprite(GTQTUtil.gtqtId("blocks/force_field"));
            HALO = textureMap.registerSprite(GTQTUtil.gtqtId("items/halo"));
        }
    public static void preInit() {
        TextureUtils.addIconRegister(GTQTTextures::register);
    }

}
