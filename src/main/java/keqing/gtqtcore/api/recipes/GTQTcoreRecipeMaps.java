package keqing.gtqtcore.api.recipes;

import gregtech.api.GTValues;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.builders.*;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.capability.chemical_plant.ChemicalPlantBuilder;
import keqing.gtqtcore.api.gui.GTQTGuiTextures;
import keqing.gtqtcore.api.recipes.builder.*;
import keqing.gtqtcore.api.recipes.machine.*;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.recipes.RecipeMaps.REPLICATOR_RECIPES;
import static keqing.gtqtcore.api.gui.GTQTGuiTextures.*;

public class GTQTcoreRecipeMaps {
    public static final RecipeMap<FuelRecipeBuilder> NAQUADAH_REACTOR_RECIPES = new RecipeMap<>("naquadah_reactor", 0, 0, 1, 1, new FuelRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.COMBUSTION)
            .allowEmptyOutput();

    public static final RecipeMap<SimpleRecipeBuilder> SPINNER_RECIPES = new RecipeMap<>("spinner",
            3, 1, 1, 0, new SimpleRecipeBuilder(), false)
            .setSlotOverlay(false, false, false, GuiTextures.VIAL_OVERLAY_1)
            .setSlotOverlay(false, false, true, GuiTextures.INT_CIRCUIT_OVERLAY)
            .setSlotOverlay(false, true, GuiTextures.VIAL_OVERLAY_1)
            .setSlotOverlay(true, false, GuiTextures.VIAL_OVERLAY_1)
            .setProgressBar(GuiTextures.PROGRESS_BAR_COMPRESS, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.WIRECUTTER_TOOL);

    public static final RecipeMap<SimpleRecipeBuilder> LARGE_MIXER_RECIPES = new RecipeMapLargeMixer<>("large_mixer",
            9, 1, 6, 1, new SimpleRecipeBuilder(), false)
            .setSlotOverlay(false, false, GuiTextures.DUST_OVERLAY)
            .setSlotOverlay(true, false, GuiTextures.DUST_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MIXER, ProgressWidget.MoveType.CIRCULAR)
            .setSound(GTSoundEvents.MIXER);

    public static final RecipeMap<SimpleRecipeBuilder> REFINER_MACERATOR_RECIPES = new RecipeMap<>("refiner_macerator",
            2, 2, 1, 1, new SimpleRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_CRACKING, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.MACERATOR);

    public static final RecipeMap<SimpleRecipeBuilder> POLYMERIZATION_RECIPES = new RecipeMap<>("polymerization_tank",
            2, 2, 2, 2, new SimpleRecipeBuilder(), false)
            .setSlotOverlay(false, false, GuiTextures.VIAL_OVERLAY_1)
            .setSlotOverlay(false, true, GuiTextures.VIAL_OVERLAY_2)
            .setSlotOverlay(true, false, GuiTextures.VIAL_OVERLAY_1)
            .setSlotOverlay(true, true, GuiTextures.VIAL_OVERLAY_2)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.CIRCULAR)
            .setSound(GTSoundEvents.CHEMICAL_REACTOR);

    public static final RecipeMap<SimpleRecipeBuilder> LOW_TEMP_ACTIVATOR_RECIPES = new RecipeMap<>("low_temperature_activator",
            2, 4, 2, 1, new SimpleRecipeBuilder(), false)
            .setSlotOverlay(false, false, GuiTextures.DUST_OVERLAY)
            .setSlotOverlay(true, false, GuiTextures.DUST_OVERLAY)
            .setSlotOverlay(false, true, GuiTextures.VIAL_OVERLAY_2)
            .setSlotOverlay(true, true, GuiTextures.VIAL_OVERLAY_2)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.COOLING);

    public static final RecipeMap<SimpleRecipeBuilder> FLOTATION_RECIPES = new RecipeMap<>("flotation_cell",
            6, 0, 1, 1, new SimpleRecipeBuilder(), false)
            .setSlotOverlay(false, false, GuiTextures.DUST_OVERLAY)
            .setSlotOverlay(false, true, GuiTextures.VIAL_OVERLAY_2)
            .setSlotOverlay(true, true, GuiTextures.HEATING_OVERLAY_2)
            .setProgressBar(GuiTextures.PROGRESS_BAR_BATH, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.BATH);

    public static final RecipeMap<SimpleRecipeBuilder> PLASMA_CONDENSER_RECIPES = new RecipeMap<>("plasma_condenser_recipes", 3, 3, 3, 3, new SimpleRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_REPLICATOR, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.COOLING);

    public static final RecipeMap<FuelRecipeBuilder> HIGH_PRESSURE_STEAM_TURBINE_RECIPES = new RecipeMap<>("high_pressure_steam_turbine", 0, 0, 1, 1, new FuelRecipeBuilder(), false)
            .setSlotOverlay(false, true, true, GuiTextures.DARK_CANISTER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_GAS_COLLECTOR, ProgressWidget.MoveType.HORIZONTAL)
            .allowEmptyOutput()
            .setSound(GTSoundEvents.TURBINE);

    public static final RecipeMap<FuelRecipeBuilder> SUPERCRITICAL_STEAM_TURBINE_RECIPES = new RecipeMap<>("supercritical_steam_turbine", 0, 0, 1, 1, new FuelRecipeBuilder(), false)
            .setSlotOverlay(false, true, true, GuiTextures.DARK_CANISTER_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_GAS_COLLECTOR, ProgressWidget.MoveType.HORIZONTAL)
            .allowEmptyOutput()
            .setSound(GTSoundEvents.TURBINE);

    public static final RecipeMap<SwarmTierRecipeBuilder> NEUTRAL_NETWORK_NEXUS_BREEDING_MODE = new RecipeMap<>("neutral_network_nexus_breeding_mode", 6, 1, 3, 0, new SwarmTierRecipeBuilder(), false)
            .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
            .setSlotOverlay(false, false, true, GuiTextures.CIRCUIT_OVERLAY)
            .setSlotOverlay(false, true, false, GuiTextures.MOLECULAR_OVERLAY_3)
            .setSlotOverlay(false, true, true, GuiTextures.MOLECULAR_OVERLAY_4)
            .setSlotOverlay(true, false, true, GuiTextures.EXTRACTOR_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_CIRCUIT, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.ASSEMBLER);

    public static final RecipeMap<PrimitiveRecipeBuilder> PRIMITIVE_ROASTING_RECIPES = new RecipeMap<>("primitive_roaster", 3, 3,  1, 1, new PrimitiveRecipeBuilder(), false)
            .setSlotOverlay(false, false, GuiTextures.FURNACE_OVERLAY_1)
            .setSlotOverlay(true, false, GuiTextures.OUT_SLOT_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_COKE_OVEN, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.FURNACE);

    public static final RecipeMap<EvaporationPoolRecipeBuilder> EVAPORATION_POOL = new RecipeMap<>("evaporation_pool", 2, 4, 1, 1, new EvaporationPoolRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_SIFT, ProgressWidget.MoveType.VERTICAL)
            .setSound(GTSoundEvents.CHEMICAL_REACTOR);

    public static final RecipeMap<SimpleRecipeBuilder> GRAVITY_SEPARATOR_RECIPES = new RecipeMap<>("gravity_separator", 3, 6, 3, 3, new SimpleRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, ProgressWidget.MoveType.VERTICAL)
            .setSlotOverlay(true, false, false, GuiTextures.CRUSHED_ORE_OVERLAY)
            .setSlotOverlay(true, false, true, GuiTextures.CRUSHED_ORE_OVERLAY)
            .setSound(GTSoundEvents.MACERATOR);

    public static final RecipeMap<ComputationRecipeBuilder> LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES = new RecipeMapLargeCircuitAssemblyLine<>("large_circuit_assembly_line", 7, 1, 1, 0, new ComputationRecipeBuilder(), false)
            .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
            .setSlotOverlay(false, false, true, GuiTextures.DATA_ORB_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_CIRCUIT_ASSEMBLER, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.ASSEMBLER);

    public static final RecipeMap<FuelRecipeBuilder> BIOMASS_GENERATOR_RECIPES = new RecipeMap<>("biomass_generator", 0, 0, 1, 0, new FuelRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.BATH)
            .allowEmptyOutput();

    public static final RecipeMap<FuelRecipeBuilder> ACID_GENERATOR_RECIPES = new RecipeMap<>("acid_generator", 0, 0, 1, 0, new FuelRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.CHEMICAL_REACTOR)
            .allowEmptyOutput();

    public static final RecipeMap<SimpleRecipeBuilder> AUTO_CHISEL_RECIPES = new RecipeMap<>("auto_chisel", 2, 1, 0, 0, new SimpleRecipeBuilder(), false)
            .setSlotOverlay(false, false, false, GuiTextures.BOXED_BACKGROUND)
            .setProgressBar(GuiTextures.PROGRESS_BAR_CRACKING, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.FILE_TOOL);

    public static final RecipeMap<SimpleRecipeBuilder> LAMINATOR_RECIPES = new RecipeMap<>("laminator", 6, 1, 2, 0, new SimpleRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.COOLING);

    public static RecipeMap<FuelRecipeBuilder> FUEL_CELL = new RecipeMap<>("fuel_cell", 0, 0, 2, 1, new FuelRecipeBuilder(), false)
            .allowEmptyOutput();

    public static final RecipeMap<PseudoMultiRecipeBuilder> LATEX_COLLECTOR_RECIPES = new RecipeMap<>("latex_collector", 0, 2, 1, 2, new PseudoMultiRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_CRACKING, ProgressWidget.MoveType.VERTICAL_DOWNWARDS)
            .setSound(GTSoundEvents.DRILL_TOOL);

    public static final RecipeMap<SimpleRecipeBuilder> DRYER_RECIPES = new RecipeMap<>("dryer_recipes",  3,  3, 3,  3, new SimpleRecipeBuilder(), false)
            .setSlotOverlay(false, false, true, GuiTextures.FURNACE_OVERLAY_1)
            .setSlotOverlay(false, true, true, GuiTextures.FURNACE_OVERLAY_2)
            .setSlotOverlay(true, false, false, GuiTextures.DUST_OVERLAY)
            .setSlotOverlay(true, false, true, GuiTextures.DUST_OVERLAY)
            .setSound(GTSoundEvents.FURNACE);

    public static final RecipeMap<SimpleRecipeBuilder> CATALYTIC_REFORMER_RECIPES = new RecipeMap<>("catalytic_reformer_recipes", 3,3, 3, 6, new SimpleRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_CRACKING, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.FURNACE);

    public static final RecipeMap<BlastRecipeBuilder> BURNER_REACTOR_RECIPES = new RecipeMap<>("burner_reactor_recipes",  3,  3  ,  3,  3, new BlastRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARC_FURNACE, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.ARC);

    public static final RecipeMap<NoCoilTemperatureRecipeBuilder> CRYOGENIC_REACTOR_RECIPES = new RecipeMap<>("cryogenic_reactor_recipes",  3,  2,  2,  2, new NoCoilTemperatureRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.COOLING);

    public static final RecipeMap<SimpleRecipeBuilder> CVD_RECIPES = new RecipeMap<>("cvd_recipes",  2,  2,  3,  3, new SimpleRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.COOLING);

    public static final RecipeMap<SimpleRecipeBuilder> SAW_MILL = new RecipeMap<>("saw_mill",  2,  2,  0,  0, new SimpleRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.CUT);

    public static final RecipeMap<SimpleRecipeBuilder> PLASMA_CVD_RECIPES = new RecipeMap<>("plasma_cvd_recipes",  4,  4,  4,  4, new SimpleRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.COOLING);

    public static final RecipeMap<NoCoilTemperatureRecipeBuilder> MOLECULAR_BEAM_RECIPES = new RecipeMap<>("molecular_beam_recipes", 5, 5,  2,  1, new NoCoilTemperatureRecipeBuilder(), false)
            .setSlotOverlay(false, false, false, NANOSCALE_OVERLAY_1)
            .setSlotOverlay(false, false, true, NANOSCALE_OVERLAY_1)
            .setSlotOverlay(false, true, false, NANOSCALE_OVERLAY_2)
            .setSlotOverlay(false, true, true, NANOSCALE_OVERLAY_2)
            .setSlotOverlay(true, false, true, NANOSCALE_OVERLAY_1)
            .setSlotOverlay(true, true, true, NANOSCALE_OVERLAY_2)
            .setProgressBar(PROGRESS_BAR_NANOSCALE, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.ELECTROLYZER);

    public static final RecipeMap<SimpleRecipeBuilder> SONICATION_RECIPES = new RecipeMap<>("sonication_recipes",    3, 2, 2, 2, new SimpleRecipeBuilder(), false)
            .setSlotOverlay(false, true, false, GuiTextures.BREWER_OVERLAY)
            .setSlotOverlay(false, true, true, GuiTextures.MOLECULAR_OVERLAY_3)
            .setSlotOverlay(true, true, true, GuiTextures.MOLECULAR_OVERLAY_4)
            .setSlotOverlay(true, false, true, FOIL_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_EXTRACT, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.CENTRIFUGE);

    public static final RecipeMap<SimpleRecipeBuilder> ION_IMPLANTATOR_RECIPES = new RecipeMap<>("ion_implanter_recipes", 3, 3, 1,  1,   new SimpleRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.ELECTROLYZER);

    public static final RecipeMap<SimpleRecipeBuilder> DRILLING_RECIPES = new RecipeMap<>("drill_recipes", 3, 1,  1,    1, new SimpleRecipeBuilder(), false)
            .setSlotOverlay(false, false, true, GuiTextures.CRUSHED_ORE_OVERLAY)
            .setSlotOverlay(true, false, true, GuiTextures.DUST_OVERLAY)
            .setSound(GTSoundEvents.MACERATOR);

    public static final RecipeMap<BlastRecipeBuilder> CRYSTALLIZER_RECIPES = new RecipeMap<>("crystallization_recipes",  6, 1, 2,  3,   new BlastRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_CRYSTALLIZATION, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.FURNACE);

    public static final RecipeMap<BlastRecipeBuilder> CZPULLER_RECIPES = new RecipeMap<>("czpuller_recipes", 3, 1, 1,0,   new BlastRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_CRYSTALLIZATION, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.FURNACE);

    public static final RecipeMap<NoCoilTemperatureRecipeBuilder> ROASTER_RECIPES = new RecipeMap<>("roaster_recipes",  3,  3,  3,  3, new NoCoilTemperatureRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.FURNACE);

    public static final RecipeMap<PCBFactoryRecipeBuilder> PCB_FACTORY_RECIPES = new RecipeMap<>("pcb_factory", 6, 9, 3, 0, new PCBFactoryRecipeBuilder(), false)
            .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
            .setSlotOverlay(false, false, true, GuiTextures.CIRCUIT_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_CIRCUIT_ASSEMBLER, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.ASSEMBLER);

    public static final RecipeMap<SimpleRecipeBuilder> COMPONENT_ASSEMBLER_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> SEPTIC_TANK;
    public static final RecipeMap<SimpleRecipeBuilder> COPY_RECIPES ;
    public static final RecipeMap<SimpleRecipeBuilder> ULTRAVIOLET_LAMP_CHAMBER_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> PRESSURE_LAMINATOR_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> VACUUM_CHAMBER_RECIPES;
    public static final RecipeMap<ComponentAssemblyLineRecipesTierRecipeBuilder> COMPONENT_ASSEMBLY_LINE_RECIPES;
    public static final RecipeMap<ElectronRecipeBuilder> ELECTROBATH;
    public static final RecipeMap<SwarmTierRecipeBuilder> SWARM_GROWTH;
    public static final RecipeMap<SwarmTierRecipeBuilder> SWARM_ASSEMBLER;
    public static final RecipeMap<ComputationRecipeBuilder> PARTICLE_ACCELERATOR_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> DIGESTER_RECIPES;
    public static final RecipeMap<BioRecipeRecipeBuilder> BIOLOGICAL_REACTION_RECIPES;
    public static final RecipeMap<BioRecipeRecipeBuilder> GENE_MUTAGENESIS;
    public static final RecipeMap<EnzymesReactionRecipeBuilder> ENZYMES_REACTION_RECIPES;
    public static final RecipeMap<PHRecipeBuilder> FERMENTATION_TANK_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> FUEL_REFINE_FACTORY_RECIPES;
    public static final RecipeMap<ForceFieldCoilRecipeBuilder> NAQUADAH_REFINE_FACTORY_RECIPES;
    public static final RecipeMap<FuelRecipeBuilder> ROCKET_RECIPES;
    public static final RecipeMap<FuelRecipeBuilder> NAQUADAH_REACTOR;
    public static final RecipeMap<ChemicalPlantBuilder> CHEMICAL_PLANT;
    public static final RecipeMap<SimpleRecipeBuilder> INTEGRATED_MINING_DIVISION;
    public static final RecipeMap<SimpleRecipeBuilder> UU_RECIPES ;
    public static final RecipeMap<QFTCasingTierRecipeBuilder> QUANTUM_FORCE_TRANSFORMER_RECIPES;
    public static final RecipeMap<FlowRateRecipeBuilder> HEAT_EXCHANGE_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> STAR_MIXER;
    public static final RecipeMap<SimpleRecipeBuilder> RECYCLE_RECIPE ;
    public static final RecipeMap<NeutronActivatorIORecipeBuilder> PAC_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> PLASMA_FORGE;
    public static final RecipeMap<SimpleRecipeBuilder> STAR_BIOMIMETIC_FACTORY;
    public static final RecipeMap<NoCoilTemperatureRecipeBuilder> MOLECULAR_DISTILLATION_RECIPES;
    public static final RecipeMap<PrimitiveRecipeBuilder> PR_MIX;
    public static final RecipeMap<HeatRecipeBuilder> SALT_FLIED;
    public static final RecipeMap<KQComputationRecipeBuilder> KEQING_NET_RECIES;
    public static final RecipeMap<FuelRecipeBuilder> HYPER_REACTOR_MK1_RECIPES;
    public static final RecipeMap<FuelRecipeBuilder> HYPER_REACTOR_MK2_RECIPES;
    public static final RecipeMap<FuelRecipeBuilder> HYPER_REACTOR_MK3_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> DISSOLUTION_TANK_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> FLUID_EXTRACTOR_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> FLUID_CANNER_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> BIO_CENTRIFUGE;
    public static final RecipeMap<SimpleRecipeBuilder> FLOTATION_FACTORY_RECIPES;
    public static final RecipeMap<BlastRecipeBuilder> VACUUM_DRYING_FURNACE_RECIPES;
    public static final RecipeMap<GrindBallTierRecipeBuilder> ISA_MILL_GRINDER;
    public static final RecipeMap<PrimitiveRecipeBuilder> OIL_POOL;
    public static final RecipeMapPseudoGroup<SimpleRecipeBuilder> PROCESSING_MODE_A;
    public static final RecipeMapPseudoGroup<SimpleRecipeBuilder> PROCESSING_MODE_B;
    public static final RecipeMapPseudoGroup<SimpleRecipeBuilder> PROCESSING_MODE_C;
    public static final RecipeMap<CasingComputationRecipeBuilder> PRECISE_ASSEMBLER_RECIPES;
    public static final RecipeMap<CasingComputationRecipeBuilder> MOLECULAR_TRANSFORMER_RECIPES;
    public static final RecipeMap<MiningDrillRecipeBuilder> MINING_DRILL_RECIPES;
    public static final RecipeMap<HeatRecipeBuilder> DISTILLATION_KETTLE;
    public static final RecipeMap<SimpleRecipeBuilder> DESULPHURIZATION_RECIPES;
    public static final RecipeMap<ChemicalPlantBuilder>  FLUIDIZED_BED;
    public static final RecipeMap<HeatRecipeBuilder> PYROLYSIS_TOWER;
    public static final RecipeMap<ComputationRecipeBuilder> LASER_ENGRAVING;
    public static final RecipeMap<ComputationRecipeBuilder> TD_PRINT_RECIPES;
    public static final RecipeMap<ComputationRecipeBuilder> PRECISION_SPRAYING;
    public static final RecipeMap<ComputationRecipeBuilder> PRECISION_SPINNING;
    public static final RecipeMap<SimpleRecipeBuilder> CW_LASER_ENGRAVER_RECIPES;
    public static final RecipeMap<BlastRecipeBuilder>  CW_LASER_ALLOY_RECIPES;
    public static final RecipeMap<LaserComputationRecipeBuilder> STEPPER_RECIPES;
    public static final RecipeMap<PrimitiveRecipeBuilder> CLARIFIER;
    public static final RecipeMap<SimpleRecipeBuilder>  ELEOIL;
    public static final RecipeMap<NeutronActivatorRecipeBuilder> NEUTRON_ACTIVATOR ;
    public static final RecipeMap<SimpleRecipeBuilder> SFM;
    public static final RecipeMap<PrimitiveRecipeBuilder>  COAGULATION_RECIPES ;
    public static final RecipeMap<TargetRecipeBuilder> TARGET_CHAMBER;
    public static final RecipeMap<TargetRecipeBuilder> NUCLEOSYNTHESIS;
    public static final RecipeMap<NeutronActivatorIORecipeBuilder> BEAM_COLLECTION;
    //靶室（启动耗能+散射截面）

    private GTQTcoreRecipeMaps() {}
    static {
        RECYCLE_RECIPE = new RecipeMap<>("recycle_items",1,1,0,0,(new SimpleRecipeBuilder()),false);

        COPY_RECIPES = new RecipeMap<>("uu_copy",2,1,3,1,(new SimpleRecipeBuilder()),false);

        UU_RECIPES = new RecipeMap<>("uu_producter",2,0,1,1,(new SimpleRecipeBuilder()),false);

        PROCESSING_MODE_A = new RecipeMapPseudoGroup<>("processing_mode_a", 1, 2, 1, 1, new SimpleRecipeBuilder(), RecipeMaps.COMPRESSOR_RECIPES, RecipeMaps.LATHE_RECIPES, RecipeMaps.POLARIZER_RECIPES, false);
        PROCESSING_MODE_B = new RecipeMapPseudoGroup<>("processing_mode_b", 2, 2, 1, 1, new SimpleRecipeBuilder(), RecipeMaps.FERMENTING_RECIPES, RecipeMaps.EXTRACTOR_RECIPES, RecipeMaps.CANNER_RECIPES, false);
        PROCESSING_MODE_C = new RecipeMapPseudoGroup<>("processing_mode_c", 2, 2, 1, 1, new SimpleRecipeBuilder(), RecipeMaps.LASER_ENGRAVER_RECIPES, RecipeMaps.AUTOCLAVE_RECIPES, RecipeMaps.FLUID_SOLIDFICATION_RECIPES, false);
        COAGULATION_RECIPES = new RecipeMap<>("coagulation_tank", 2, 1, 2, 0, new PrimitiveRecipeBuilder(), false);

        BEAM_COLLECTION = new RecipeMap<>("beam_collection",3,3,3,3,new NeutronActivatorIORecipeBuilder(),false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_COMPRESS, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.ARC);

        NUCLEOSYNTHESIS = new RecipeMap<>("nucleosynthesis",3,3,3,3,new TargetRecipeBuilder(),false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_COMPRESS, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.ARC);

        TARGET_CHAMBER = new RecipeMap<>("target_chamber",3,3,3,3,new TargetRecipeBuilder(),false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_COMPRESS, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.ARC);

        MINING_DRILL_RECIPES= new RecipeMap<>("mining_drill", 1, 9, 0, 0, new MiningDrillRecipeBuilder(), false)
                .setSlotOverlay(false, false, true, GuiTextures.CRUSHED_ORE_OVERLAY)
                .setSlotOverlay(true, false, true, GuiTextures.DUST_OVERLAY)
                .setSound(GTSoundEvents.MACERATOR);

        PARTICLE_ACCELERATOR_RECIPES=new RecipeMap<>("particle_accelerator", 1, 3, 0, 0, new ComputationRecipeBuilder(), false)
                .setSound(GTSoundEvents.CHEMICAL_REACTOR);

        NEUTRON_ACTIVATOR=new RecipeMap<>("neutron_activator", 6, 6, 3, 3, new NeutronActivatorRecipeBuilder(), false)
                .setSound(GTSoundEvents.CHEMICAL_REACTOR);

        VACUUM_CHAMBER_RECIPES = new RecipeMap<>("vacuum_chamber_recipes", 6, 3,  3,  3, new SimpleRecipeBuilder(), false)
                .setSlotOverlay(false, false, GuiTextures.CIRCUIT_OVERLAY)
                .setProgressBar(GuiTextures.PROGRESS_BAR_COMPRESS, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.ASSEMBLER);

        ULTRAVIOLET_LAMP_CHAMBER_RECIPES = new RecipeMap<>("ultraviolet_lamp_chamber_recipes",  3,  2,  3, 2, new SimpleRecipeBuilder(), false)
                .setSlotOverlay(false, false, false, GuiTextures.LENS_OVERLAY)
                .setSlotOverlay(false, false, true, GuiTextures.LENS_OVERLAY)
                .setSlotOverlay(false, true, false, GuiTextures.MOLECULAR_OVERLAY_3)
                .setSlotOverlay(false, true, true, GuiTextures.MOLECULAR_OVERLAY_4)
                .setSlotOverlay(true, false, GuiTextures.VIAL_OVERLAY_1)
                .setSlotOverlay(true, true, GuiTextures.VIAL_OVERLAY_2)
                .setSound(GTSoundEvents.CHEMICAL_REACTOR);

        PRESSURE_LAMINATOR_RECIPES = new RecipeMap<>("pressure_laminator_recipes",  3,  1,  3, 0, new SimpleRecipeBuilder(), false)
                .setSound(GTSoundEvents.COMPRESSOR);

        SWARM_GROWTH = new RecipeMap<>("swarm_growth", 16, 1, 0, 0, new SwarmTierRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_HAMMER, ProgressWidget.MoveType.VERTICAL_DOWNWARDS)
                .setSound(GTSoundEvents.SCIENCE);

        SWARM_ASSEMBLER = new RecipeMap<>("swarm_assembler", 3, 1, 9, 1, new SwarmTierRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_HAMMER, ProgressWidget.MoveType.VERTICAL_DOWNWARDS)
                .setSound(GTSoundEvents.SCIENCE);

        COMPONENT_ASSEMBLER_RECIPES = new RecipeMap<>("component_assembler_recipes", 6,  1,  1,  0, new SimpleRecipeBuilder(), false)
                .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
                .setSlotOverlay(false, false, true, GuiTextures.CIRCUIT_OVERLAY)
                .setSound(GTSoundEvents.ASSEMBLER);

        PRECISE_ASSEMBLER_RECIPES = new RecipeMapPreciseAssembler<>("precise_assembler_recipes", 4, 1, 4, 0, new CasingComputationRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
                .setSlotOverlay(false, false, true, GuiTextures.CIRCUIT_OVERLAY)
                .setSound(GTSoundEvents.ASSEMBLER);

        MOLECULAR_TRANSFORMER_RECIPES = new RecipeMap<>("molecular_transformer", 1, 1, 0, 0, new CasingComputationRecipeBuilder(), false)
                .setSlotOverlay(false, false, true, GuiTextures.MOLECULAR_OVERLAY_1)
                .setSlotOverlay(true, false, true, GuiTextures.MOLECULAR_OVERLAY_2)
                .setProgressBar(GuiTextures.PROGRESS_BAR_COMPRESS, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTValues.FOOLS.get() ? GTSoundEvents.SCIENCE : GTSoundEvents.ARC);

        LASER_ENGRAVING = new RecipeMap<>("laser_engraving", 2, 1, 1, 1, new ComputationRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
                .setSlotOverlay(false, false, true, GuiTextures.CIRCUIT_OVERLAY)
                .setSound(GTSoundEvents.ASSEMBLER);

        STEPPER_RECIPES = new RecipeMap<>("stepper_recipes", 3, 3, 3, 3, new LaserComputationRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
                .setSlotOverlay(false, false, true, GuiTextures.CIRCUIT_OVERLAY)
                .setSound(GTSoundEvents.ASSEMBLER);


        TD_PRINT_RECIPES = new RecipeMap<>("threed_print", 1, 1, 1, 0, new ComputationRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
                .setSlotOverlay(false, false, true, GuiTextures.CIRCUIT_OVERLAY)
                .setSound(GTSoundEvents.ASSEMBLER);

        PRECISION_SPRAYING = new RecipeMap<>("precision_spraying", 2, 1, 2, 0, new ComputationRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
                .setSlotOverlay(false, false, true, GuiTextures.CIRCUIT_OVERLAY)
                .setSound(GTSoundEvents.CENTRIFUGE);

        PRECISION_SPINNING = new RecipeMap<>("precision_spinning", 3, 1, 3, 0, new ComputationRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
                .setSlotOverlay(false, false, true, GuiTextures.CIRCUIT_OVERLAY)
                .setSound(GTSoundEvents.CHEMICAL_REACTOR);

        VACUUM_DRYING_FURNACE_RECIPES = new RecipeMap<>("vacuum_drying_furnace_recipes", 3, 9, 3, 3, new BlastRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_SIFT, ProgressWidget.MoveType.HORIZONTAL)
                .setSlotOverlay(false, false, false, GuiTextures.FURNACE_OVERLAY_1)
                .setSlotOverlay(false, false, true, GuiTextures.FURNACE_OVERLAY_1)
                .setSlotOverlay(false, true, false, GuiTextures.FURNACE_OVERLAY_2)
                .setSlotOverlay(false, true, true, GuiTextures.FURNACE_OVERLAY_2)
                .setSlotOverlay(true, true, false, GuiTextures.FURNACE_OVERLAY_2)
                .setSlotOverlay(true, true, true, GuiTextures.FURNACE_OVERLAY_2)
                .setSound(GTSoundEvents.FURNACE);

        ELECTROBATH = new RecipeMap<>("electrobath", 6, 6, 6, 6, new ElectronRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_BATH, ProgressWidget.MoveType.CIRCULAR)
                .setSound(GTSoundEvents.ELECTROLYZER);

        FLOTATION_FACTORY_RECIPES = new RecipeMap<>("flotation_factory_recipes", 6, 3, 3, 3, new SimpleRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_BATH, ProgressWidget.MoveType.CIRCULAR)
                .setSound(GTSoundEvents.BATH);

        PR_MIX = new RecipeMap<>("pr_mix", 3, 3, 3, 3, new PrimitiveRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.CHEMICAL_REACTOR);

        OIL_POOL = new RecipeMap<>("oil_pool", 0, 0, 2, 2, new PrimitiveRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.MACERATOR);

        SALT_FLIED = new RecipeMap<>("salt_fish", 3, 3, 3, 3, new HeatRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.MACERATOR);

        ISA_MILL_GRINDER = new RecipeMap<>("isa_mill_recipes", 3, 3, 0, 0, new GrindBallTierRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.MACERATOR);

        BIO_CENTRIFUGE = new RecipeMap<>("bio_centrifuge", 3, 3, 3, 3, new SimpleRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.MACERATOR);

        FLUID_EXTRACTOR_RECIPES = new RecipeMap<>("fluid_extractor_recipes", 2,2,2,2, new SimpleRecipeBuilder(), false)
                .setSlotOverlay(false, false, GuiTextures.EXTRACTOR_OVERLAY)
                .setProgressBar(GuiTextures.PROGRESS_BAR_COMPRESS, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.ASSEMBLER);

        FLUID_CANNER_RECIPES = new RecipeMap<>("fluid_canner_recipes", 2,2,2,2, new SimpleRecipeBuilder(), false)
                .setSlotOverlay(false, false, GuiTextures.CANNER_OVERLAY)
                .setProgressBar(GuiTextures.PROGRESS_BAR_COMPRESS, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.ASSEMBLER);

        DESULPHURIZATION_RECIPES = new RecipeMap<>("desulphurization_recipes", 0,0,2,2, new SimpleRecipeBuilder(), false)
                .setSound(GTSoundEvents.CHEMICAL_REACTOR);

        COMPONENT_ASSEMBLY_LINE_RECIPES = new RecipeMapComponentAssemblyLine<>("component_assembly_line_recipes", 12, 1,  12, 0, new ComponentAssemblyLineRecipesTierRecipeBuilder(), false)
                .setSound(GTSoundEvents.ASSEMBLER);

        DIGESTER_RECIPES = new RecipeMap<>("digester_recipes", 4, 4, 4, 4, new SimpleRecipeBuilder(), false);

        FERMENTATION_TANK_RECIPES = new RecipeMap<>("fermentation_tank_recipes",  4, 4, 4,  3, new PHRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_EXTRACT, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.CHEMICAL_REACTOR);

        BIOLOGICAL_REACTION_RECIPES = new RecipeMap<>("biological_reaction_recipes", 3, 3, 3, 3, new BioRecipeRecipeBuilder(), false);

        GENE_MUTAGENESIS = new RecipeMap<>("gene_mutagenesis", 6, 6, 6, 6, new BioRecipeRecipeBuilder(), false);

        ENZYMES_REACTION_RECIPES = new RecipeMap<>("enzymes_reaction_recipes", 3, 3, 3, 3, new EnzymesReactionRecipeBuilder(), false);

        DISSOLUTION_TANK_RECIPES = new RecipeMap<>("dissolution_tank_recipes", 4, 4, 4, 4, new SimpleRecipeBuilder(), false);
        //  Dangote Distillery RecipeMap
        MOLECULAR_DISTILLATION_RECIPES = new RecipeMapDangoteDistillery<>("molecular_distillation_recipes", 0, true, 1, true, 1, true, 12, false, new NoCoilTemperatureRecipeBuilder(), false)
                .setSound(GTSoundEvents.CHEMICAL_REACTOR);

        //  Large Heat Exchanger Recipemap
        HEAT_EXCHANGE_RECIPES = new RecipeMapHeatExchanger<>("heat_exchanger_recipes", 0, 0, 2, 3, new FlowRateRecipeBuilder(), false)
                .setProgressBar(GTQTGuiTextures.PROGRESS_BAR_HEAT_EXCHANGE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.BATH);

        ROCKET_RECIPES = new RecipeMap<>("rocket_engine_recipes",
                0, 0, 1, 1, new FuelRecipeBuilder(), false)
                .setSlotOverlay(false, true, true, GuiTextures.CENTRIFUGE_OVERLAY)
                .setProgressBar(GuiTextures.PROGRESS_BAR_GAS_COLLECTOR, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.TURBINE)
                .allowEmptyOutput();

        CW_LASER_ENGRAVER_RECIPES = new RecipeMap<>("cw_laser_engraver", 2, 6, 1, 0, new SimpleRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
                .setSlotOverlay(false, false, true, GuiTextures.CIRCUIT_OVERLAY)
                .setSound(GTSoundEvents.ASSEMBLER);

        CW_LASER_ALLOY_RECIPES =  new RecipeMap<>("cw_laser_alloy", 9, 0, 3, 1, new BlastRecipeBuilder(), false)
                .setSlotOverlay(false, false, false, GuiTextures.FURNACE_OVERLAY_1)
                .setSlotOverlay(false, false, true, GuiTextures.FURNACE_OVERLAY_1)
                .setSlotOverlay(false, true, false, GuiTextures.FURNACE_OVERLAY_2)
                .setSlotOverlay(false, true, true, GuiTextures.FURNACE_OVERLAY_2)
                .setSlotOverlay(true, true, false, GuiTextures.FURNACE_OVERLAY_2)
                .setSlotOverlay(true, true, true, GuiTextures.FURNACE_OVERLAY_2)
                .setSound(GTSoundEvents.FURNACE);

        NAQUADAH_REACTOR = new RecipeMap<>("naquadah_reactor",
                1, 1, 0, 0, new FuelRecipeBuilder(), false)
                .allowEmptyOutput();

        CHEMICAL_PLANT = new RecipeMap<>("chemical_plant",
                6, 6, 6, 6, new ChemicalPlantBuilder(), false);

        FLUIDIZED_BED = new RecipeMap<>("fluidized_bed_recipes",
                3, 3, 3, 3, new ChemicalPlantBuilder(), false);

        INTEGRATED_MINING_DIVISION = new RecipeMap<>("integrated_mining_division",
                3, 3, 3, 3, new SimpleRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.CHEMICAL_REACTOR);

        CLARIFIER = new RecipeMap<>("clarifire",
                3, 3, 3, 3, new PrimitiveRecipeBuilder(), false);

        ELEOIL = new RecipeMap<>("ele_oil",
                0, 0, 1, 2, new SimpleRecipeBuilder(), false);

        SEPTIC_TANK = new RecipeMap<>("septic_tank",
                2, 2, 2, 2, new SimpleRecipeBuilder(), false);

        SFM = new RecipeMapGTQTDistillationTower<>("sfm",
                1, false, 1,false,  1,false,  12,false,  new SimpleRecipeBuilder(), false).setSound(GTSoundEvents.CHEMICAL_REACTOR);

        PYROLYSIS_TOWER = new RecipeMapGTQTDistillationTower<>("pyrolysis_tower",
                1, false,1, false,1,false, 12,false, new HeatRecipeBuilder(), false);

        DISTILLATION_KETTLE = new RecipeMap<>("distillation_kettle",
                0,false, 0,false, 1,false, 9,false, new HeatRecipeBuilder(), false).setSound(GTSoundEvents.CHEMICAL_REACTOR);


        QUANTUM_FORCE_TRANSFORMER_RECIPES = new RecipeMap<>("quantum_force_transformer_recipes", 6,  6,  6,  6, new QFTCasingTierRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.SCIENCE);

        STAR_MIXER = new RecipeMap<>("star_mixer",
                3, 3, 9, 3, new SimpleRecipeBuilder(), false);

        STAR_BIOMIMETIC_FACTORY = new RecipeMap<>("star_biomimetic_factory",
                3, 32, 1, 16, new SimpleRecipeBuilder(), false);

        PLASMA_FORGE = new RecipeMap<>("plasma_forge",
                9, 9, 9, 9, new SimpleRecipeBuilder(), false);

        KEQING_NET_RECIES = new RecipeMap<>("keqing_net_recipes", 2, 1, 1, 0, new KQComputationRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL)
                .setSlotOverlay(false, false, GuiTextures.SCANNER_OVERLAY)
                .setSlotOverlay(true, false, GuiTextures.RESEARCH_STATION_OVERLAY)
                .setSound(GTValues.FOOLS.get() ? GTSoundEvents.SCIENCE : GTSoundEvents.COMPUTATION);

        PAC_RECIPES = new RecipeMap<>("pac_recipes", 3,  3,  3, 3, new NeutronActivatorIORecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.ARC)
                .allowEmptyOutput();

        //  Hyper Reactor Mk I Recipemap
        HYPER_REACTOR_MK1_RECIPES = new RecipeMap<>("hyper_reactor_mk1_recipes", 0,  0,  1, 0, new FuelRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.ARC)
                .allowEmptyOutput();

        //  Hyper Reactor Mk II Recipemap
        HYPER_REACTOR_MK2_RECIPES = new RecipeMap<>("hyper_reactor_mk2_recipes", 0,  0, 1,0, new FuelRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.ARC)
                .allowEmptyOutput();

        //  Hyper Reactor Mk III Recipemap
        HYPER_REACTOR_MK3_RECIPES = new RecipeMap<>("hyper_reactor_mk3_recipes",  0, 0,  1,  0, new FuelRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.ARC)
                .allowEmptyOutput();

        FUEL_REFINE_FACTORY_RECIPES = new RecipeMap<>("fuel_refine_factory_recipes", 3,  6,  6, 3, new SimpleRecipeBuilder(), false)
                .setSlotOverlay(false, false, false, GuiTextures.MOLECULAR_OVERLAY_1)
                .setSlotOverlay(false, false, true, GuiTextures.MOLECULAR_OVERLAY_2)
                .setSlotOverlay(false, true, false, GuiTextures.MOLECULAR_OVERLAY_3)
                .setSlotOverlay(false, true, true, GuiTextures.MOLECULAR_OVERLAY_4)
                .setSlotOverlay(true, false, GuiTextures.VIAL_OVERLAY_1)
                .setSlotOverlay(true, true, GuiTextures.VIAL_OVERLAY_2)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTValues.FOOLS.get() ? GTSoundEvents.SCIENCE : GTSoundEvents.CHEMICAL_REACTOR);

        NAQUADAH_REFINE_FACTORY_RECIPES = new RecipeMap<>("naquadah_refine_factory_recipes", 3,  6,  6, 3, new ForceFieldCoilRecipeBuilder(), false)
                .setSlotOverlay(false, false, false, GuiTextures.MOLECULAR_OVERLAY_1)
                .setSlotOverlay(false, false, true, GuiTextures.MOLECULAR_OVERLAY_2)
                .setSlotOverlay(false, true, false, GuiTextures.MOLECULAR_OVERLAY_3)
                .setSlotOverlay(false, true, true, GuiTextures.MOLECULAR_OVERLAY_4)
                .setSlotOverlay(true, false, GuiTextures.VIAL_OVERLAY_1)
                .setSlotOverlay(true, true, GuiTextures.VIAL_OVERLAY_2)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTValues.FOOLS.get() ? GTSoundEvents.SCIENCE : GTSoundEvents.CHEMICAL_REACTOR);
    }
    public static void init() {
        RecipeMaps.BLAST_RECIPES.setMaxFluidInputs(3);
        RecipeMaps.BLAST_RECIPES.setMaxFluidOutputs(3);
        CUTTER_RECIPES.setMaxOutputs(4);
        ELECTROLYZER_RECIPES.setMaxFluidInputs(2);
        ELECTROLYZER_RECIPES.setSlotOverlay(false, true, false, GuiTextures.LIGHTNING_OVERLAY_2);
        LASER_ENGRAVER_RECIPES.setMaxOutputs(2);
        MASS_FABRICATOR_RECIPES.setSlotOverlay(false, false, GuiTextures.DUST_OVERLAY);
        REPLICATOR_RECIPES.setMaxFluidInputs(3);
        REPLICATOR_RECIPES.setSlotOverlay(false, false, GuiTextures.DUST_OVERLAY);
        REPLICATOR_RECIPES.setSlotOverlay(true, false, GuiTextures.DUST_OVERLAY);
        COMBUSTION_GENERATOR_FUELS.setMaxFluidOutputs(1);
    }

}