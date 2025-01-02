package keqing.gtqtcore.client.textures;

import codechicken.lib.texture.TextureUtils;
import gregtech.api.gui.resources.SteamTexture;
import gregtech.api.gui.resources.TextureArea;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.renderer.texture.cube.SimpleOrientedCubeRenderer;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import keqing.gtqtcore.GTQTCore;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.renderer.textures.OverlayRenderer;
import keqing.gtqtcore.client.textures.custom.IsaMillRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

import static keqing.gtqtcore.api.utils.GTQTUtil.gtqtId;


public class GTQTTextures {
    public static final SteamTexture FLUID_SLOT_STEAM = SteamTexture.fullImage("textures/gui/base/fluid_slot_%s.png");
    public static final SteamTexture INT_CIRCUIT_OVERLAY_STEAM = SteamTexture.fullImage("textures/gui/overlay/int_circuit_overlay_%s.png");

    public static OrientedOverlayRenderer RTG_OVERLAY= new OrientedOverlayRenderer("overlay/rtg");
    public static OrientedOverlayRenderer PARTICLE_ACCELERATOR= new OrientedOverlayRenderer("overlay/particle_accelerator");
    public static TextureAtlasSprite PRESSURE_PIPE_SIDE;
    public static TextureAtlasSprite PRESSURE_PIPE_OPEN;
    public static SimpleOverlayRenderer WIRELESS_HATCH_HATCH;
    public static SimpleOverlayRenderer FISHING_CASING;
    public static SimpleOverlayRenderer BASIC_PHOTOLITHOGRAPHIC_FRAMEWORK_CASING = new SimpleOverlayRenderer("multiblock/casing/basic_photolithographic_framework_casing");
    public static OverlayRenderer MULTIPART_BUFFER_HATCH = new OverlayRenderer("multipart/overlay_buffer_hatch");
    public static OrientedOverlayRenderer ROCKET_ENGINE_OVERLAY = new OrientedOverlayRenderer("machines/rocket_engine");
    public static OrientedOverlayRenderer NAQUADAH_REACTOR_OVERLAY = new OrientedOverlayRenderer("machines/naquadah_reactor");
    public static final SimpleOverlayRenderer ADVANCED_FUSION_TEXTURE = new SimpleOverlayRenderer("casings/fusion/machine_casing_advanced_fusion_hatch");
    public static final SimpleOverlayRenderer ULTIMATE_FUSION_TEXTURE = new SimpleOverlayRenderer("casings/fusion/machine_casing_ultimate_fusion_hatch");
    public static final SimpleOverlayRenderer END_FUSION_TEXTURE = new SimpleOverlayRenderer("casings/fusion/machine_casing_end_fusion_hatch");
    public static final SimpleOverlayRenderer ADVANCED_ACTIVE_FUSION_TEXTURE = new SimpleOverlayRenderer("casings/fusion/machine_casing_advanced_fusion_hatch_blue");
    public static final SimpleOverlayRenderer ULTIMATE_ACTIVE_FUSION_TEXTURE = new SimpleOverlayRenderer("casings/fusion/machine_casing_ultimate_fusion_hatch_blue");
    public static final SimpleOverlayRenderer END_ACTIVE_FUSION_TEXTURE = new SimpleOverlayRenderer("casings/fusion/machine_casing_end_fusion_hatch_blue");
    public static final SimpleOrientedCubeRenderer ADVANCED_FILTER_IRIDIUM_FRONT = new SimpleOrientedCubeRenderer("casings/advanced_filter_iridium_front");
    public static SimpleOverlayRenderer CIRCUIT_ASSEMBLY_CONTROL_CASING = new SimpleOverlayRenderer("multiblock/advblock/osmiridium_casing");
    public static SimpleOverlayRenderer Inconel625 = new SimpleOverlayRenderer("multiblock/advblock/inconel_625");
    public static SimpleOverlayRenderer HastelloyN = new SimpleOverlayRenderer("multiblock/advblock/hastelloy_n");
    public static SimpleOverlayRenderer Stellite = new SimpleOverlayRenderer("multiblock/advblock/stellite");
    public static SimpleOverlayRenderer Hdcs = new SimpleOverlayRenderer("multiblock/advblock/hdcs");
    public static SimpleOverlayRenderer Lafium = new SimpleOverlayRenderer("multiblock/advblock/lafium");
    public static SimpleOverlayRenderer BlackTitanium = new SimpleOverlayRenderer("multiblock/advblock/black_titanium");
    public static SimpleOverlayRenderer Talonite = new SimpleOverlayRenderer("multiblock/advblock/talonite");
    public static SimpleOverlayRenderer BlackPlutonium = new SimpleOverlayRenderer("multiblock/advblock/black_plutonium");
    public static SimpleOverlayRenderer MaragingSteel250 = new SimpleOverlayRenderer("multiblock/advblock/maraging_steel_250");
    public static SimpleOverlayRenderer Staballoy = new SimpleOverlayRenderer("multiblock/advblock/staballoy");
    public static SimpleOverlayRenderer BabbittAlloy = new SimpleOverlayRenderer("multiblock/advblock/babbitt_alloy");
    public static SimpleOverlayRenderer ZirconiumCarbide = new SimpleOverlayRenderer("multiblock/advblock/zirconium_carbide");
    public static SimpleOverlayRenderer Inconel792 = new SimpleOverlayRenderer("multiblock/advblock/inconel_792");
    public static SimpleOverlayRenderer IncoloyMA813 = new SimpleOverlayRenderer("multiblock/advblock/incoloy_ma_813");
    public static SimpleOverlayRenderer HastelloyX78 = new SimpleOverlayRenderer("multiblock/advblock/hastelloy_x_78");
    public static SimpleOverlayRenderer HastelloyK243 = new SimpleOverlayRenderer("multiblock/advblock/hastelloy_k_243");
    public static SimpleOverlayRenderer DIMENSIONAL_BRIDGE_CASING = new SimpleOverlayRenderer("multiblock/quantumcasing/dimensional_bridge_casing");
    public static SimpleOverlayRenderer ULTIMATE_HIGH_ENERGY_CASING = new SimpleOverlayRenderer("multiblock/quantumcasing/ultimate_high_energy_casing");
    public static SimpleOverlayRenderer TRANSCENDENTALLY_AMPLIFIED_MAGNETIC_CONFINEMENT_CASING = new SimpleOverlayRenderer("casings/transcendentally_amplified_magnetic_confinement_casing");
    public static SimpleOverlayRenderer SINGULARITY_REINFORCED_STELLAR_SHIELDING_CASING = new SimpleOverlayRenderer("casings/singularity_reinforced_stellar_shielding_casing");
    public static SimpleOverlayRenderer DIRT = new SimpleOverlayRenderer("stones/concrete_light/concrete_light_smooth");

    public static SimpleOverlayRenderer ADV_COMPUTER_HEAT_VENT_CASING = new SimpleOverlayRenderer("multiblock/computer/computer_heat_vent_side");
    public static SimpleOverlayRenderer ADV_COMPUTER_CASING = new SimpleOverlayRenderer("multiblock/computer/computer_casing/front");
    public static SimpleOverlayRenderer ULTRA_POWER_CASING = new SimpleOverlayRenderer("multiblock/computer/high_power_casing");

    public static final OrientedOverlayRenderer POLYMERIZATION_TANK_OVERLAY
            = new OrientedOverlayRenderer("overlay/polymerization_tank");

    public static final OrientedOverlayRenderer LOW_TEMP_ACTIVATOR_OVERLAY
            = new OrientedOverlayRenderer("overlay/low_temp_activator");

    public static SimpleOverlayRenderer HEAT = new SimpleOverlayRenderer("overlay/heat/overlay_front");
    public static SimpleOverlayRenderer HEAT_HATCH = new SimpleOverlayRenderer("overlay/heat_hatch/overlay_front");

    public static SimpleOverlayRenderer ELECTRODE_HATCH = new SimpleOverlayRenderer("overlay/electrode_hatch/overlay_front");
    public static SimpleOverlayRenderer DRILL = new SimpleOverlayRenderer("casings/solid/drill_head_bottom");

    public static SimpleOverlayRenderer FUSION_MKI = new SimpleOverlayRenderer("multiblock/casings/machine_casing_fusion");
    public static SimpleOverlayRenderer FUSION_MKII = new SimpleOverlayRenderer("multiblock/casings/machine_casing_fusion_2");
    public static SimpleOverlayRenderer FUSION_MKIII = new SimpleOverlayRenderer("multiblock/casings/machine_casing_fusion_3");
    public static SimpleOverlayRenderer ENERGY_CELL = new SimpleOverlayRenderer("casings/energy_cell_side");

    public static SimpleOverlayRenderer KQCC_COMMON = new SimpleOverlayRenderer("multiblock/computer/computer_top");
    public static SimpleOverlayRenderer KQCC_RAM_1 = new SimpleOverlayRenderer("multiblock/computer/rami");
    public static SimpleOverlayRenderer KQCC_CPU_1 = new SimpleOverlayRenderer("multiblock/computer/cpui");
    public static SimpleOverlayRenderer KQCC_GPU_1 = new SimpleOverlayRenderer("multiblock/computer/gpui");

    public static SimpleOverlayRenderer KQCC_RAM_2 = new SimpleOverlayRenderer("multiblock/computer/ramii");
    public static SimpleOverlayRenderer KQCC_CPU_2 = new SimpleOverlayRenderer("multiblock/computer/cpuii");
    public static SimpleOverlayRenderer KQCC_GPU_2 = new SimpleOverlayRenderer("multiblock/computer/gpuii");

    public static SimpleOverlayRenderer KQCC_RAM_3 = new SimpleOverlayRenderer("multiblock/computer/ramiii");
    public static SimpleOverlayRenderer KQCC_CPU_3 = new SimpleOverlayRenderer("multiblock/computer/cpuiii");
    public static SimpleOverlayRenderer KQCC_GPU_3 = new SimpleOverlayRenderer("multiblock/computer/gpuiii");

    public static SimpleOverlayRenderer POWER_SUPPLY_COMMON = new SimpleOverlayRenderer("multiblock/power_supply/power_supply_basic");

    public static SimpleOverlayRenderer POWER_SUPPLY_B1 = new SimpleOverlayRenderer("multiblock/power_supply/power_supply_battery_i");
    public static SimpleOverlayRenderer POWER_SUPPLY_B2 = new SimpleOverlayRenderer("multiblock/power_supply/power_supply_battery_ii");
    public static SimpleOverlayRenderer POWER_SUPPLY_B3 = new SimpleOverlayRenderer("multiblock/power_supply/power_supply_battery_iii");
    public static SimpleOverlayRenderer POWER_SUPPLY_B4 = new SimpleOverlayRenderer("multiblock/power_supply/power_supply_battery_iv");
    public static SimpleOverlayRenderer POWER_SUPPLY_B5 = new SimpleOverlayRenderer("multiblock/power_supply/power_supply_battery_v");

    public static SimpleOverlayRenderer POWER_SUPPLY_S1 = new SimpleOverlayRenderer("multiblock/power_supply/power_supply_i");
    public static SimpleOverlayRenderer POWER_SUPPLY_S2 = new SimpleOverlayRenderer("multiblock/power_supply/power_supply_ii");
    public static SimpleOverlayRenderer POWER_SUPPLY_S3 = new SimpleOverlayRenderer("multiblock/power_supply/power_supply_iii");
    public static SimpleOverlayRenderer POWER_SUPPLY_S4 = new SimpleOverlayRenderer("multiblock/power_supply/power_supply_iv");
    public static SimpleOverlayRenderer POWER_SUPPLY_S5 = new SimpleOverlayRenderer("multiblock/power_supply/power_supply_v");
    public static SimpleOverlayRenderer POWER_SUPPLY_S6 = new SimpleOverlayRenderer("multiblock/power_supply/power_supply_vi");
    public static SimpleOverlayRenderer POWER_SUPPLY_S7 = new SimpleOverlayRenderer("multiblock/power_supply/power_supply_vii");
    public static SimpleOverlayRenderer POWER_SUPPLY_S8 = new SimpleOverlayRenderer("multiblock/power_supply/power_supply_viii");
    public static SimpleOverlayRenderer POWER_SUPPLY_S9 = new SimpleOverlayRenderer("multiblock/power_supply/power_supply_iv");
    public static SimpleOverlayRenderer POWER_SUPPLY_S10 = new SimpleOverlayRenderer("multiblock/power_supply/power_supply_vv");

    //怎么写？请看
    public static OrientedOverlayRenderer LIGHTNING_ROD_OVERLAY;
    public static OrientedOverlayRenderer CHEMICAL_PLANT;
    public static SimpleOverlayRenderer CATALYST_HATCH;
    public static SimpleOverlayRenderer HYPER_CASING;
    public static SimpleOverlayRenderer POLYBENZIMIDAZOLE_PIPE;
    public static SimpleOverlayRenderer QUANTUM_CONSTRAINT_CASING;
    public static SimpleOverlayRenderer PD_CASING;
    public static SimpleOverlayRenderer TALONITE;
    public static SimpleOverlayRenderer PRECISE_ASSEMBLER_CASING_MK1;
    public static SimpleOverlayRenderer PRECISE_ASSEMBLER_CASING_MK2;
    public static SimpleOverlayRenderer PRECISE_ASSEMBLER_CASING_MK3;
    public static SimpleOverlayRenderer PRECISE_ASSEMBLER_CASING_MK4;
    public static SimpleOverlayRenderer PRECISE_ASSEMBLER_CASING_MK5;
    public static SimpleOverlayRenderer PRECISE_ASSEMBLER_CASING_MK6;
    public static SimpleOverlayRenderer AD_CASING;
    public static SimpleOverlayRenderer GALVANIZE_STEEL_CASING;
    public static SimpleOverlayRenderer ST_CASING;
    public static SimpleOverlayRenderer ASEPTIC_FARM_CASING;
    public static SimpleOverlayRenderer FLOTATION_CASING;
    public static SimpleOverlayRenderer BRICK;
    public static SimpleOverlayRenderer SOLAR_PLATE_CASING;
    public static SimpleOverlayRenderer ADVANCED_INVAR_CASING;
    public static SimpleOverlayRenderer MACERATOR_CASING;
    public static SimpleOverlayRenderer NQ_CASING;
    public static SimpleOverlayRenderer VACUUM_CASING;
    public static SimpleOverlayRenderer IRIDIUM_CASING;
    public static SimpleOverlayRenderer INF_WATER;
    public static SimpleOverlayRenderer PROCESS;
    public static SimpleOverlayRenderer ADV_COLD_CASING;
    public static SimpleOverlayRenderer NITINOL_CASING;
    public static SimpleOverlayRenderer ISA_CASING;
    public static SimpleOverlayRenderer NUCLEAR_FUSION_CASING;
    public static SimpleOverlayRenderer NUCLEAR_FUSION_COOLING;
    public static SimpleOverlayRenderer NEUTRON_ACTIVATOR_CASING;
    public static SimpleOverlayRenderer NUCLEAR_FUSION_FRAME;
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
    public static TextureArea ITEM_FLUID_OVERLAY;
    public static IsaMillRenderer ISA_MILL = new IsaMillRenderer();

    public static OrientedOverlayRenderer CATALYTIC_REFORMER_OVERLAY = new OrientedOverlayRenderer("overlay/catalytic_reformer");
    public static OrientedOverlayRenderer CRYSTALLIZATION_CRUCIBLE_OVERLAY = new OrientedOverlayRenderer("overlay/crystallization_crucible");
    public static OrientedOverlayRenderer CVD_UNIT_OVERLAY = new OrientedOverlayRenderer("overlay/cvd_unit");
    public static OrientedOverlayRenderer NANOSCALE_FABRICATOR_OVERLAY = new OrientedOverlayRenderer("overlay/nanoscale_fabricator");
    public static OrientedOverlayRenderer SONICATOR_OVERLAY = new OrientedOverlayRenderer("overlay/sonicator");
    public static final OrientedOverlayRenderer ION_IMPLANTER_OVERLAY
            = new OrientedOverlayRenderer("overlay/ion_implanter");

    public static final OrientedOverlayRenderer FLOTATION_CELL_OVERLAY
            = new OrientedOverlayRenderer("overlay/flotation_cell");

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
    public static OrientedOverlayRenderer CHEMICAL_DRYER_OVERLAY = new OrientedOverlayRenderer("overlay/chemical_dryer");
    public static OrientedOverlayRenderer BIO_REACTOR_OVERLAY = new OrientedOverlayRenderer("overlay/bio_reactor");
    public static OrientedOverlayRenderer CONDENSER_OVERLAY = new OrientedOverlayRenderer("overlay/condenser");
    public static OrientedOverlayRenderer SIMULATOR_OVERLAY = new OrientedOverlayRenderer("overlay/simulator");
    public static OrientedOverlayRenderer BIOMASS_GENERATOR_OVERLAY = new OrientedOverlayRenderer("overlay/biomass_generator");
    public static OrientedOverlayRenderer HYDRAULIC_FRACKER_OVERLAY = new OrientedOverlayRenderer("overlay/hydraulic_fracker");
    public static OrientedOverlayRenderer INDUSTRIAL_ROASTER_OVERLAY = new OrientedOverlayRenderer("overlay/industrial_roaster");
    public static OrientedOverlayRenderer FARM_OVERLAY = new OrientedOverlayRenderer("overlay/tree_growth_factory");
    public static OrientedOverlayRenderer VIRTUAL_COSMOS_SIMULATOR_OVERLAY = new OrientedOverlayRenderer("overlay/virtual_cosmos_simulator");
    public static OrientedOverlayRenderer COLLIDER_OVERLAY = new OrientedOverlayRenderer("overlay/collider");

    public static SimpleOverlayRenderer WIRELESS_OVERLAY = new SimpleOverlayRenderer("overlay/wireless/overlay_front");

    public static OrientedOverlayRenderer DIMENSIONAL_OSCILLATOR_OVERLAY = new OrientedOverlayRenderer("overlay/dimensional_oscillator");
    public static OrientedOverlayRenderer DECAY_GENERATOR_OVERLAY = new OrientedOverlayRenderer("overlay/decay_generator");
    public static OrientedOverlayRenderer SUPRACHRONAL_ASSEMBLY_LINE_OVERLAY = new OrientedOverlayRenderer("overlay/suprachronal_assembly_line");
    public static OrientedOverlayRenderer SPACE_ELEVATOR_OVERLAY = new OrientedOverlayRenderer("overlay/space_elevator");
    public static OrientedOverlayRenderer LARGE_PROCESSING_FACTORY_OVERLAY = new OrientedOverlayRenderer("overlay/large_processing_factory");
    public static OrientedOverlayRenderer NEUTRAL_NETWORK_NEXUS_OVERLAY = new OrientedOverlayRenderer("overlay/neutral_network_nexus");
    public static OrientedOverlayRenderer QUANTUM_FORCE_TRANSFORMER_OVERLAY = new OrientedOverlayRenderer("overlay/quantum_force_transformer");
    public static OrientedOverlayRenderer TURBINE_MIXER_OVERLAY = new OrientedOverlayRenderer("overlay/turbine_mixer");
    public static OrientedOverlayRenderer INDUSTRIAL_CENTRIFUGE_OVERLAY = new OrientedOverlayRenderer("overlay/industrial_centrifuge");
    public static OrientedOverlayRenderer COKING_TOWER_OVERLAY = new OrientedOverlayRenderer("overlay/coking_tower");
    public static OrientedOverlayRenderer BIOWARE_SIMULATOR_OVERLAY = new OrientedOverlayRenderer("overlay/bioware_simulator");
    public static OrientedOverlayRenderer LARGE_ROCKET_ENGINE_OVERLAY = new OrientedOverlayRenderer("overlay/large_rocket_engine");
    public static OrientedOverlayRenderer ALGAE_CULTURE_TANK_OVERLAY = new OrientedOverlayRenderer("overlay/algae_culture_tank");
    public static OrientedOverlayRenderer LARGE_BIO_REACTOR_OVERLAY = new OrientedOverlayRenderer("overlay/large_bio_reactor");
    public static OrientedOverlayRenderer ELECTROMAGNETIC_SEPARATION_PLANT_OVERLAY = new OrientedOverlayRenderer("overlay/electromagnetic_separation_plant");
    public static OrientedOverlayRenderer DIMENSIONALLY_TRANSCENDENT_PLASMA_FORGE_OVERLAY = new OrientedOverlayRenderer("overlay/dimensionally_transcendent_plasma_forge");
    public static OrientedOverlayRenderer DYSON_SWARM_OVERLAY = new OrientedOverlayRenderer("overlay/dyson_swarm");
    public static OrientedOverlayRenderer LARGE_FLUID_PHASE_TRANSFORMER_OVERLAY = new OrientedOverlayRenderer("overlay/large_fluid_phase_transformer");
    public static OrientedOverlayRenderer PRECISE_ASSEMBLER_OVERLAY = new OrientedOverlayRenderer("overlay/precise_assembler");
    public static OrientedOverlayRenderer QUANTUM_COMPUTER_OVERLAY = new OrientedOverlayRenderer("overlay/quantum_computer");
    public static OrientedOverlayRenderer PLANETARY_GAS_SIPHON_OVERLAY = new OrientedOverlayRenderer("overlay/planetary_gas_siphon");
    public static OrientedOverlayRenderer NICOLL_DYSON_BEAMER_OVERLAY = new OrientedOverlayRenderer("overlay/nicoll_dyson_beamer");
    public static OrientedOverlayRenderer SUPRACHRONAL_NEUTRONIUM_FORGE_OVERLAY = new OrientedOverlayRenderer("overlay/suprachronal_neutronium_forge");
    public static OrientedOverlayRenderer MASS_FABRICATOR_OVERLAY = new OrientedOverlayRenderer("overlay/mass_fabricator");
    public static OrientedOverlayRenderer ELEMENT_REPLICATOR_OVERLAY = new OrientedOverlayRenderer("overlay/element_replicator");
    public static SimpleOverlayRenderer MAINTENANCE_OVERLAY_STERILE_CLEANING;
    public static SimpleOverlayRenderer MAINTENANCE_OVERLAY_ISO3_CLEANING;
    public static SimpleOverlayRenderer MAINTENANCE_OVERLAY_ISO2_CLEANING;
    public static SimpleOverlayRenderer MAINTENANCE_OVERLAY_ISO1_CLEANING;
    public static TextureArea AUTO_PULL;
    public static void init() {
        AUTO_PULL = TextureArea.fullImage("textures/gui/widget/auto_pull.png");
        ITEM_FLUID_OVERLAY = TextureArea.fullImage("textures/gui/widget/item_fluid.png");
        MAINTENANCE_OVERLAY_STERILE_CLEANING = new SimpleOverlayRenderer("overlay/machine/overlay_maintenance_sterile_cleaning");
        MAINTENANCE_OVERLAY_ISO3_CLEANING = new SimpleOverlayRenderer("overlay/machine/overlay_maintenance_iso_3_cleaning");
        MAINTENANCE_OVERLAY_ISO2_CLEANING = new SimpleOverlayRenderer("overlay/machine/overlay_maintenance_iso_2_cleaning");
        MAINTENANCE_OVERLAY_ISO1_CLEANING = new SimpleOverlayRenderer("overlay/machine/overlay_maintenance_iso_1_cleaning");

        WIRELESS_HATCH_HATCH = new SimpleOverlayRenderer("overlay/wireless_hatch/overlay_front");
        FLOTATION_CASING = new SimpleOverlayRenderer("multiblock/isa_casing/flotation_casing");
        ISA_CASING = new SimpleOverlayRenderer("multiblock/isa_casing/isa_mill_casing");
        ASEPTIC_FARM_CASING = new SimpleOverlayRenderer("multiblock/isa_casing/aseptic_farm_machine_casing");
        PROCESS = new SimpleOverlayRenderer("multiblock/isa_casing/process_casing");
        VACUUM_CASING = new SimpleOverlayRenderer("multiblock/isa_casing/vacuum_casing");
        NITINOL_CASING = new SimpleOverlayRenderer("multiblock/casings/nitinol_machine_casing");
        MACERATOR_CASING = new SimpleOverlayRenderer("multiblock/casing/macerator_casing");
        NUCLEAR_FUSION_CASING = new SimpleOverlayRenderer("multiblock/casings/nuclear_fusion_casing");
        NUCLEAR_FUSION_COOLING = new SimpleOverlayRenderer("multiblock/casings/nuclear_fusion_cooling");
        NEUTRON_ACTIVATOR_CASING = new SimpleOverlayRenderer("multiblock/casings/neutron_activator_casing");
        NUCLEAR_FUSION_FRAME = new SimpleOverlayRenderer("multiblock/casings/nuclear_fusion_frame");
        PRECISE_ASSEMBLER_CASING_MK1= new SimpleOverlayRenderer("multiblock/casing/precise_assembler_casing_mk1");
        PRECISE_ASSEMBLER_CASING_MK2= new SimpleOverlayRenderer("multiblock/casing/precise_assembler_casing_mk2");
        PRECISE_ASSEMBLER_CASING_MK3 = new SimpleOverlayRenderer("multiblock/casing/precise_assembler_casing_mk3");
        PRECISE_ASSEMBLER_CASING_MK4 = new SimpleOverlayRenderer("multiblock/casing/precise_assembler_casing_mk4");
        PRECISE_ASSEMBLER_CASING_MK5 = new SimpleOverlayRenderer("multiblock/casing/precise_assembler_casing_mk5");
        PRECISE_ASSEMBLER_CASING_MK6 = new SimpleOverlayRenderer("multiblock/casing/precise_assembler_casing_mk6");
        GALVANIZE_STEEL_CASING = new SimpleOverlayRenderer("multiblock/casing/galvanize_steel_casing");
        PD_CASING = new SimpleOverlayRenderer("multiblock/casing/pd_turbine_casing");
        ST_CASING = new SimpleOverlayRenderer("multiblock/casing/st_turbine_casing");
        AD_CASING = new SimpleOverlayRenderer("multiblock/casing/ad_turbine_casing");
        ADV_COLD_CASING = new SimpleOverlayRenderer("multiblock/casing/advanced_cold_casing");
        TALONITE = new SimpleOverlayRenderer("multiblock/advblock/talonite");
        HYPER_CASING = new SimpleOverlayRenderer("multiblock/casing/hyper_casing");
        BRICK = new SimpleOverlayRenderer("multiblock/casing/brick");
        FISHING_CASING = new SimpleOverlayRenderer("multiblock/casing/fishing_casing");
        POLYBENZIMIDAZOLE_PIPE = new SimpleOverlayRenderer("multiblock/casing/talonite_casing");
        QUANTUM_CONSTRAINT_CASING = new SimpleOverlayRenderer("multiblock/quantum_force_transformer_casing/quantum_constraint_casing");
        ADVANCED_INVAR_CASING = new SimpleOverlayRenderer("multiblock/casing/advanced_invar_casing");
        NQ_CASING = new SimpleOverlayRenderer("multiblock/casing/nq_turbine_casing");
        IRIDIUM_CASING = new SimpleOverlayRenderer("multiblock/casing/iridium_casing");
        HC_ALLOY_CASING = new SimpleOverlayRenderer("multiblock/casings/hc_alloy_casing");
        SOLAR_PLATE_CASING = new SimpleOverlayRenderer("multiblock/electrobath/solar_generator_bottom");
        SFTC = new SimpleOverlayRenderer("multiblock/casings/supercritical_fuel_trans_casing");
        SFTS = new SimpleOverlayRenderer("multiblock/casings/supercritical_fuel_trans_score");
        LIGHTNING_ROD_OVERLAY = new OrientedOverlayRenderer("generators/lightning_rod");
        INF_WATER = new SimpleOverlayRenderer("multipart/overlay_water");
        CATALYST_HATCH = new SimpleOverlayRenderer("multipart/overlay_catalysts");
        CHEMICAL_PLANT = new OrientedOverlayRenderer("multiblock/chemical_plant");
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

    //  Multi Renderer
    public static TextureAtlasSprite HALO_NOISE;
    public static TextureAtlasSprite HALO;
    public static TextureAtlasSprite[] COSMIC;
    public static TextureAtlasSprite COSMIC_0;
    public static TextureAtlasSprite COSMIC_1;
    public static TextureAtlasSprite COSMIC_2;
    public static TextureAtlasSprite COSMIC_3;
    public static TextureAtlasSprite COSMIC_4;
    public static TextureAtlasSprite COSMIC_5;
    public static TextureAtlasSprite COSMIC_6;
    public static TextureAtlasSprite COSMIC_7;
    public static TextureAtlasSprite COSMIC_8;
    public static TextureAtlasSprite COSMIC_9;

    public static void register(TextureMap textureMap) {
        FORCE_FIELD = textureMap.registerSprite(gtqtId("blocks/force_field"));
        HALO = textureMap.registerSprite(gtqtId("items/halo"));
        PRESSURE_PIPE_SIDE = textureMap.registerSprite(new ResourceLocation(GTQTCore.MODID, "blocks/pipe/pressure_pipe_side"));
        PRESSURE_PIPE_OPEN = textureMap.registerSprite(new ResourceLocation(GTQTCore.MODID, "blocks/pipe/pressure_pipe_open"));
        HALO_NOISE = textureMap.registerSprite(gtqtId("items/halo_noise"));
        COSMIC_0 = textureMap.registerSprite(gtqtId("shader/cosmic_0"));
        COSMIC_1 = textureMap.registerSprite(gtqtId("shader/cosmic_1"));
        COSMIC_2 = textureMap.registerSprite(gtqtId("shader/cosmic_2"));
        COSMIC_3 = textureMap.registerSprite(gtqtId("shader/cosmic_3"));
        COSMIC_4 = textureMap.registerSprite(gtqtId("shader/cosmic_4"));
        COSMIC_5 = textureMap.registerSprite(gtqtId("shader/cosmic_5"));
        COSMIC_6 = textureMap.registerSprite(gtqtId("shader/cosmic_6"));
        COSMIC_7 = textureMap.registerSprite(gtqtId("shader/cosmic_7"));
        COSMIC_8 = textureMap.registerSprite(gtqtId("shader/cosmic_8"));
        COSMIC_9 = textureMap.registerSprite(gtqtId("shader/cosmic_9"));
        COSMIC = new TextureAtlasSprite[] {
                COSMIC_0,
                COSMIC_1,
                COSMIC_2,
                COSMIC_3,
                COSMIC_4,
                COSMIC_5,
                COSMIC_6,
                COSMIC_7,
                COSMIC_8,
                COSMIC_9

        };
        FORCE_FIELD = textureMap.registerSprite(gtqtId("blocks/multiblocks/quantum_force_transformer/force_field"));
    }

    public static void preInit() {
        TextureUtils.addIconRegister(GTQTTextures::register);
    }

}
