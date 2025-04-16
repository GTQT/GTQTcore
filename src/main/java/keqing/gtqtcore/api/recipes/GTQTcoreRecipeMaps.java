package keqing.gtqtcore.api.recipes;
import keqing.gtqtcore.api.recipes.ui.*;
import gregtech.api.GTValues;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMapBuilder;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.builders.*;
import gregtech.api.recipes.ui.impl.DistillationTowerUI;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.gui.GTQTGuiTextures;
import keqing.gtqtcore.api.recipes.builder.*;
import keqing.gtqtcore.api.recipes.machine.RecipeMapPseudoGroup;
import keqing.gtqtcore.api.recipes.ui.ComponentAssemblyLineUI;

import static gregtech.api.gui.widgets.ProgressWidget.MoveType.CIRCULAR;
import static gregtech.api.gui.widgets.ProgressWidget.MoveType.HORIZONTAL;
import static gregtech.api.recipes.RecipeMaps.*;
import static keqing.gtqtcore.api.gui.GTQTGuiTextures.*;

public class GTQTcoreRecipeMaps {

    public static final RecipeMapPseudoGroup<SimpleRecipeBuilder> PROCESSING_MODE_A = new RecipeMapPseudoGroup<>("processing_mode_a", 1, 2, 1, 1, new SimpleRecipeBuilder(), RecipeMaps.COMPRESSOR_RECIPES, RecipeMaps.LATHE_RECIPES, RecipeMaps.POLARIZER_RECIPES, false);
    public static final RecipeMapPseudoGroup<SimpleRecipeBuilder> PROCESSING_MODE_B = new RecipeMapPseudoGroup<>("processing_mode_b", 2, 2, 1, 1, new SimpleRecipeBuilder(), RecipeMaps.FERMENTING_RECIPES, RecipeMaps.EXTRACTOR_RECIPES, RecipeMaps.CANNER_RECIPES, false);
    public static final RecipeMapPseudoGroup<SimpleRecipeBuilder> PROCESSING_MODE_C = new RecipeMapPseudoGroup<>("processing_mode_c", 2, 2, 1, 1, new SimpleRecipeBuilder(), RecipeMaps.LASER_ENGRAVER_RECIPES, RecipeMaps.AUTOCLAVE_RECIPES, RecipeMaps.FLUID_SOLIDFICATION_RECIPES, false);

    public static final RecipeMap<SimpleRecipeBuilder> SIMULATOR_RECIPES = new RecipeMapBuilder<>("simulator",
            new SimpleRecipeBuilder())
            .itemInputs(2)
            .itemOutputs(2)
            .progressBar(GuiTextures.PROGRESS_BAR_ARC_FURNACE, HORIZONTAL)
            .sound(GTSoundEvents.SCIENCE)
            .build();

    public static final RecipeMap<FuelRecipeBuilder> NAQUADAH_REACTOR_RECIPES = new RecipeMapBuilder<>(
            "naquadah_reactor", new FuelRecipeBuilder())
            .fluidInputs(1)
            .fluidOutputs(1)
            .fluidSlotOverlay(GuiTextures.FURNACE_OVERLAY_2, false)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW, HORIZONTAL)
            .sound(GTSoundEvents.COMBUSTION)
            .allowEmptyOutputs()
            .generator()
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> SPINNER_RECIPES = new RecipeMapBuilder<>("spinner",
            new SimpleRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(1)
            .fluidInputs(1)
            .itemSlotOverlay(GuiTextures.VIAL_OVERLAY_1, false)
            .progressBar(GuiTextures.PROGRESS_BAR_COMPRESS, HORIZONTAL)
            .sound(GTSoundEvents.WIRECUTTER_TOOL)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> LARGE_MIXER_RECIPES = new RecipeMapBuilder<>("large_mixer",
            new SimpleRecipeBuilder())
            .itemInputs(9)
            .itemOutputs(1)
            .fluidInputs(6)
            .fluidInputs(1)
            .itemSlotOverlay(GuiTextures.DUST_OVERLAY, false)
            .progressBar(GuiTextures.PROGRESS_BAR_MIXER, CIRCULAR)
            .sound(GTSoundEvents.MIXER)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> REFINER_MACERATOR_RECIPES = new RecipeMapBuilder<>("refiner_macerator",
            new SimpleRecipeBuilder())
            .itemInputs(2)
            .itemOutputs(2)
            .fluidInputs(1)
            .fluidInputs(1)
            .progressBar(GuiTextures.PROGRESS_BAR_CRACKING, HORIZONTAL)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> POLYMERIZATION_RECIPES = new RecipeMapBuilder<>("polymerization_tank",
            new SimpleRecipeBuilder())
            .itemInputs(2)
            .itemOutputs(2)
            .fluidInputs(2)
            .fluidInputs(2)
            .itemSlotOverlay(GuiTextures.VIAL_OVERLAY_1, false)
            .fluidSlotOverlay(GuiTextures.VIAL_OVERLAY_1, false)
            .fluidSlotOverlay(GuiTextures.VIAL_OVERLAY_2, true)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, CIRCULAR)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> LOW_TEMP_ACTIVATOR_RECIPES = new RecipeMapBuilder<>("low_temperature_activator", new SimpleRecipeBuilder())
            .itemInputs(2)
            .itemOutputs(4)
            .fluidInputs(2)
            .fluidOutputs(1)
            .itemSlotOverlay(GuiTextures.DUST_OVERLAY, false)
            .itemSlotOverlay(GuiTextures.DUST_OVERLAY, true)
            .fluidSlotOverlay(GuiTextures.VIAL_OVERLAY_2, false)
            .fluidSlotOverlay(GuiTextures.VIAL_OVERLAY_2, true)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTSoundEvents.COOLING)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> FLOTATION_RECIPES = new RecipeMapBuilder<>("flotation_cell", new SimpleRecipeBuilder())
            .itemInputs(6)
            .fluidInputs(1)
            .fluidOutputs(1)
            .itemSlotOverlay(GuiTextures.DUST_OVERLAY, false)
            .fluidSlotOverlay(GuiTextures.VIAL_OVERLAY_2, false)
            .fluidSlotOverlay(GuiTextures.HEATING_OVERLAY_2, true)
            .progressBar(GuiTextures.PROGRESS_BAR_BATH, HORIZONTAL)
            .sound(GTSoundEvents.BATH)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> PLASMA_CONDENSER_RECIPES = new RecipeMapBuilder<>("plasma_condenser_recipes", new SimpleRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_REPLICATOR, HORIZONTAL)
            .sound(GTSoundEvents.COOLING)
            .build();

    public static final RecipeMap<FuelRecipeBuilder> HIGH_PRESSURE_STEAM_TURBINE_RECIPES = new RecipeMapBuilder<>("high_pressure_steam_turbine", new FuelRecipeBuilder())
            .fluidInputs(1)
            .fluidOutputs(1)
            .fluidSlotOverlay(GuiTextures.DARK_CANISTER_OVERLAY, false, true)
            .progressBar(GuiTextures.PROGRESS_BAR_GAS_COLLECTOR, HORIZONTAL)
            .allowEmptyOutputs()
            .sound(GTSoundEvents.TURBINE)
            .generator()
            .build();

    public static final RecipeMap<FuelRecipeBuilder> SUPERCRITICAL_STEAM_TURBINE_RECIPES = new RecipeMapBuilder<>("supercritical_steam_turbine", new FuelRecipeBuilder())
            .fluidInputs(1)
            .fluidOutputs(1)
            .fluidSlotOverlay(GuiTextures.DARK_CANISTER_OVERLAY, false, true)
            .progressBar(GuiTextures.PROGRESS_BAR_GAS_COLLECTOR, HORIZONTAL)
            .allowEmptyOutputs()
            .sound(GTSoundEvents.TURBINE)
            .generator()
            .build();

    public static final RecipeMap<SwarmTierRecipeBuilder> NEUTRAL_NETWORK_NEXUS_BREEDING_MODE = new RecipeMapBuilder<>("neutral_network_nexus_breeding_mode", new SwarmTierRecipeBuilder())
            .itemInputs(6)
            .itemOutputs(1)
            .fluidInputs(3)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false)
            .itemSlotOverlay(GuiTextures.MOLECULAR_OVERLAY_3, true, false)
            .itemSlotOverlay(GuiTextures.MOLECULAR_OVERLAY_4, true, true)
            .fluidSlotOverlay(GuiTextures.EXTRACTOR_OVERLAY, false)
            .progressBar(GuiTextures.PROGRESS_BAR_CIRCUIT, HORIZONTAL)
            .sound(GTSoundEvents.ASSEMBLER)
            .build();

    public static final RecipeMap<PrimitiveRecipeBuilder> PRIMITIVE_ROASTING_RECIPES = new RecipeMapBuilder<>("primitive_roaster", new PrimitiveRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(1)
            .fluidOutputs(1)
            .itemSlotOverlay(GuiTextures.FURNACE_OVERLAY_1, false)
            .itemSlotOverlay(GuiTextures.OUT_SLOT_OVERLAY, true)
            .progressBar(GuiTextures.PROGRESS_BAR_COKE_OVEN, HORIZONTAL)
            .sound(GTSoundEvents.FURNACE)
            .build();

    public static final RecipeMap<EvaporationPoolRecipeBuilder> EVAPORATION_POOL = new RecipeMapBuilder<>("evaporation_pool", new EvaporationPoolRecipeBuilder())
            .itemInputs(2)
            .itemOutputs(4)
            .fluidInputs(1)
            .fluidOutputs(1)
            .progressBar(GuiTextures.PROGRESS_BAR_SIFT, ProgressWidget.MoveType.VERTICAL)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> GRAVITY_SEPARATOR_RECIPES = new RecipeMapBuilder<>("gravity_separator", new SimpleRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(6)
            .fluidInputs(3)
            .fluidOutputs(3)
            .itemSlotOverlay(GuiTextures.CRUSHED_ORE_OVERLAY, false)
            .itemSlotOverlay(GuiTextures.CRUSHED_ORE_OVERLAY, true)
            .progressBar(GuiTextures.PROGRESS_BAR_MACERATE, ProgressWidget.MoveType.VERTICAL)
            .sound(GTSoundEvents.MACERATOR)
            .build();

    public static final RecipeMap<ComputationRecipeBuilder> LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES = new RecipeMapBuilder<>("large_circuit_assembly_line", new ComputationRecipeBuilder())
            .itemInputs(7)
            .itemOutputs(1)
            .fluidInputs(1)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false)
            .itemSlotOverlay(GuiTextures.DATA_ORB_OVERLAY, true)
            .progressBar(GuiTextures.PROGRESS_BAR_CIRCUIT_ASSEMBLER, HORIZONTAL)
            .ui(LargeCircuitAssemblyLineUI::new)
            .sound(GTSoundEvents.ASSEMBLER)
            .build();

    public static final RecipeMap<FuelRecipeBuilder> BIOMASS_GENERATOR_RECIPES = new RecipeMapBuilder<>("biomass_generator", new FuelRecipeBuilder())
            .fluidInputs(1)
            .allowEmptyOutputs()
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW, HORIZONTAL)
            .sound(GTSoundEvents.BATH)
            .generator()
            .build();

    public static final RecipeMap<FuelRecipeBuilder> ANTIMATTER_GENERATOR = new RecipeMapBuilder<>("antimatter_generator", new FuelRecipeBuilder())
            .itemInputs(2)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW, HORIZONTAL)
            .sound(GTSoundEvents.ARC)
            .allowEmptyOutputs()
            .generator()
            .build();

    public static final RecipeMap<FuelRecipeBuilder> ACID_GENERATOR_RECIPES = new RecipeMapBuilder<>("acid_generator", new FuelRecipeBuilder())
            .fluidInputs(1)
            .allowEmptyOutputs()
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW, HORIZONTAL)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .generator()
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> AUTO_CHISEL_RECIPES = new RecipeMapBuilder<>("auto_chisel", new SimpleRecipeBuilder())
            .itemInputs(2)
            .itemOutputs(1)
            .itemSlotOverlay(GuiTextures.BOXED_BACKGROUND, false)
            .progressBar(GuiTextures.PROGRESS_BAR_CRACKING, HORIZONTAL)
            .sound(GTSoundEvents.FILE_TOOL)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> LAMINATOR_RECIPES = new RecipeMapBuilder<>("laminator", new SimpleRecipeBuilder())
            .itemInputs(6)
            .itemOutputs(1)
            .fluidInputs(2)
            .itemSlotOverlay(GuiTextures.BOXED_BACKGROUND, false) // 示例中未明确指定具体纹理，这里假设使用默认背景
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTSoundEvents.COOLING)
            .build();

    public static final RecipeMap<PseudoMultiRecipeBuilder> LATEX_COLLECTOR_RECIPES = new RecipeMapBuilder<>("latex_collector", new PseudoMultiRecipeBuilder())
            .itemInputs(0)
            .itemOutputs(2)
            .fluidInputs(1)
            .fluidOutputs(2)
            .progressBar(GuiTextures.PROGRESS_BAR_CRACKING, ProgressWidget.MoveType.VERTICAL_DOWNWARDS)
            .sound(GTSoundEvents.DRILL_TOOL)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> DRYER_RECIPES = new RecipeMapBuilder<>("dryer_recipes", new SimpleRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .itemSlotOverlay(GuiTextures.FURNACE_OVERLAY_1, false, false)
            .itemSlotOverlay(GuiTextures.FURNACE_OVERLAY_2, false, true)
            .itemSlotOverlay(GuiTextures.DUST_OVERLAY, true, false)
            .itemSlotOverlay(GuiTextures.DUST_OVERLAY, true, true)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTSoundEvents.FURNACE)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> CATALYTIC_REFORMER_RECIPES = new RecipeMapBuilder<>("catalytic_reformer_recipes", new SimpleRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(6)
            .progressBar(GuiTextures.PROGRESS_BAR_CRACKING, HORIZONTAL)
            .sound(GTSoundEvents.FURNACE)
            .build();

    public static final RecipeMap<BlastRecipeBuilder> BURNER_REACTOR_RECIPES = new RecipeMapBuilder<>("burner_reactor_recipes", new BlastRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_ARC_FURNACE, HORIZONTAL)
            .sound(GTSoundEvents.ARC)
            .build();

    public static final RecipeMap<NoCoilTemperatureRecipeBuilder> CRYOGENIC_REACTOR_RECIPES = new RecipeMapBuilder<>("cryogenic_reactor_recipes", new NoCoilTemperatureRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(2)
            .fluidInputs(2)
            .fluidOutputs(2)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW, HORIZONTAL)
            .sound(GTSoundEvents.COOLING)
            .build();

    public static final RecipeMap<PressureRecipeBuilder> CVD_RECIPES = new RecipeMapBuilder<>("cvd_recipes", new PressureRecipeBuilder())
            .itemInputs(2)
            .itemOutputs(2)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTSoundEvents.COOLING)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> SAW_MILL = new RecipeMapBuilder<>("saw_mill", new SimpleRecipeBuilder())
            .itemInputs(2)
            .itemOutputs(2)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTSoundEvents.CUT)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> PLASMA_CVD_RECIPES = new RecipeMapBuilder<>("plasma_cvd_recipes", new SimpleRecipeBuilder())
            .itemInputs(4)
            .itemOutputs(4)
            .fluidInputs(4)
            .fluidOutputs(4)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTSoundEvents.COOLING)
            .build();

    public static final RecipeMap<NoCoilTemperatureRecipeBuilder> MOLECULAR_BEAM_RECIPES = new RecipeMapBuilder<>("molecular_beam_recipes", new NoCoilTemperatureRecipeBuilder())
            .itemInputs(5)
            .itemOutputs(5)
            .fluidInputs(2)
            .fluidOutputs(1)
            .itemSlotOverlay(NANOSCALE_OVERLAY_1, false, false)
            .itemSlotOverlay(NANOSCALE_OVERLAY_1, false, true)
            .itemSlotOverlay(NANOSCALE_OVERLAY_2, false, true)
            .itemSlotOverlay(NANOSCALE_OVERLAY_1, true, false)
            .itemSlotOverlay(NANOSCALE_OVERLAY_2, true, true)
            .progressBar(PROGRESS_BAR_NANOSCALE, HORIZONTAL)
            .sound(GTSoundEvents.ELECTROLYZER)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> SONICATION_RECIPES = new RecipeMapBuilder<>("sonication_recipes", new SimpleRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(2)
            .fluidInputs(2)
            .fluidOutputs(2)
            .itemSlotOverlay(GuiTextures.BREWER_OVERLAY, false, true)
            .itemSlotOverlay(GuiTextures.MOLECULAR_OVERLAY_3, false, true)
            .itemSlotOverlay(GuiTextures.MOLECULAR_OVERLAY_4, true, true)
            .itemSlotOverlay(FOIL_OVERLAY, true, false)
            .progressBar(GuiTextures.PROGRESS_BAR_EXTRACT, HORIZONTAL)
            .sound(GTSoundEvents.CENTRIFUGE)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> ION_IMPLANTATOR_RECIPES = new RecipeMapBuilder<>("ion_implanter_recipes", new SimpleRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(1)
            .fluidOutputs(1)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTSoundEvents.ELECTROLYZER)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> DRILLING_RECIPES = new RecipeMapBuilder<>("drill_recipes", new SimpleRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(1)
            .fluidInputs(1)
            .fluidOutputs(1)
            .itemSlotOverlay(GuiTextures.CRUSHED_ORE_OVERLAY, false, false)
            .itemSlotOverlay(GuiTextures.DUST_OVERLAY, true, false)
            .progressBar(GuiTextures.PROGRESS_BAR_MACERATE, HORIZONTAL)
            .sound(GTSoundEvents.MACERATOR)
            .build();

    public static final RecipeMap<BlastRecipeBuilder> CRYSTALLIZER_RECIPES = new RecipeMapBuilder<>("crystallization_recipes", new BlastRecipeBuilder())
            .itemInputs(6)
            .itemOutputs(1)
            .fluidInputs(2)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_CRYSTALLIZATION, HORIZONTAL)
            .sound(GTSoundEvents.FURNACE)
            .build();

    public static final RecipeMap<TemperaturePressureRecipeBuilder> CZPULLER_RECIPES = new RecipeMapBuilder<>("czpuller_recipes", new TemperaturePressureRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(1)
            .fluidInputs(1)
            .progressBar(GuiTextures.PROGRESS_BAR_CRYSTALLIZATION, HORIZONTAL)
            .sound(GTSoundEvents.FURNACE)
            .build();

    public static final RecipeMap<NoCoilTemperatureRecipeBuilder> ROASTER_RECIPES = new RecipeMapBuilder<>("roaster_recipes", new NoCoilTemperatureRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW, HORIZONTAL)
            .sound(GTSoundEvents.FURNACE)
            .build();

    public static final RecipeMap<PCBFactoryRecipeBuilder> PCB_FACTORY_RECIPES = new RecipeMapBuilder<>("pcb_factory", new PCBFactoryRecipeBuilder())
            .itemInputs(6)
            .itemOutputs(9)
            .fluidInputs(3)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false, false)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false, true)
            .progressBar(GuiTextures.PROGRESS_BAR_CIRCUIT_ASSEMBLER, HORIZONTAL)
            .sound(GTSoundEvents.ASSEMBLER)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> COMPONENT_ASSEMBLER_RECIPES = new RecipeMapBuilder<>("component_assembler_recipes", new SimpleRecipeBuilder())
            .itemInputs(6)
            .itemOutputs(1)
            .fluidInputs(1)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false, false)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false, true)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL) // 示例中未明确指定进度条，假设使用默认样式
            .sound(GTSoundEvents.ASSEMBLER)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> COPY_RECIPES = new RecipeMapBuilder<>("uu_copy", new SimpleRecipeBuilder())
            .itemInputs(2)
            .itemOutputs(1)
            .fluidInputs(3)
            .fluidOutputs(1)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> ULTRAVIOLET_LAMP_CHAMBER_RECIPES = new RecipeMapBuilder<>("ultraviolet_lamp_chamber_recipes", new SimpleRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(2)
            .fluidInputs(3)
            .fluidOutputs(2)
            .itemSlotOverlay(GuiTextures.LENS_OVERLAY, false, false)
            .itemSlotOverlay(GuiTextures.LENS_OVERLAY, false, true)
            .itemSlotOverlay(GuiTextures.MOLECULAR_OVERLAY_3, false, true)
            .itemSlotOverlay(GuiTextures.MOLECULAR_OVERLAY_4, false, true)
            .itemSlotOverlay(GuiTextures.VIAL_OVERLAY_1, true, false)
            .itemSlotOverlay(GuiTextures.VIAL_OVERLAY_2, true, true)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> PRESSURE_LAMINATOR_RECIPES = new RecipeMapBuilder<>("pressure_laminator_recipes", new SimpleRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(1)
            .fluidInputs(3)
            .sound(GTSoundEvents.COMPRESSOR)
            .build();

    public static final RecipeMap<PressureRecipeBuilder> VACUUM_CHAMBER_RECIPES = new RecipeMapBuilder<>("vacuum_chamber_recipes", new PressureRecipeBuilder())
            .itemInputs(6)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false)
            .progressBar(GuiTextures.PROGRESS_BAR_COMPRESS, HORIZONTAL)
            .sound(GTSoundEvents.ASSEMBLER)
            .build();

    public static final RecipeMap<ComponentAssemblyLineRecipesTierRecipeBuilder> COMPONENT_ASSEMBLY_LINE_RECIPES = new RecipeMapBuilder<>("component_assembly_line_recipes", new ComponentAssemblyLineRecipesTierRecipeBuilder())
            .itemInputs(12)
            .itemOutputs(1)
            .fluidInputs(12)
            .fluidOutputs(0)
            .sound(GTSoundEvents.ASSEMBLER)
            .ui(ComponentAssemblyLineUI::new)
            .build();

    public static final RecipeMap<ElectronRecipeBuilder> ELECTROBATH = new RecipeMapBuilder<>("electrobath", new ElectronRecipeBuilder())
            .itemInputs(6)
            .itemOutputs(6)
            .fluidInputs(6)
            .fluidOutputs(6)
            .progressBar(GuiTextures.PROGRESS_BAR_BATH, CIRCULAR)
            .sound(GTSoundEvents.ELECTROLYZER)
            .build();

    public static final RecipeMap<SwarmTierRecipeBuilder> SWARM_GROWTH = new RecipeMapBuilder<>("swarm_growth", new SwarmTierRecipeBuilder())
            .itemInputs(16)
            .itemOutputs(1)
            .progressBar(GuiTextures.PROGRESS_BAR_HAMMER, ProgressWidget.MoveType.VERTICAL_DOWNWARDS)
            .sound(GTSoundEvents.SCIENCE)
            .build();

    public static final RecipeMap<SwarmTierRecipeBuilder> SWARM_ASSEMBLER = new RecipeMapBuilder<>("swarm_assembler", new SwarmTierRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(1)
            .fluidInputs(9)
            .fluidOutputs(1)
            .progressBar(GuiTextures.PROGRESS_BAR_HAMMER, ProgressWidget.MoveType.VERTICAL_DOWNWARDS)
            .sound(GTSoundEvents.SCIENCE)
            .build();

    public static final RecipeMap<ComputationRecipeBuilder> PARTICLE_ACCELERATOR_RECIPES = new RecipeMapBuilder<>("particle_accelerator", new ComputationRecipeBuilder())
            .itemInputs(1)
            .itemOutputs(3)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> DIGESTER_RECIPES = new RecipeMapBuilder<>("digester_recipes", new SimpleRecipeBuilder())
            .itemInputs(4)
            .itemOutputs(4)
            .fluidInputs(4)
            .fluidOutputs(4)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<BioRecipeRecipeBuilder> BIOLOGICAL_REACTION_RECIPES = new RecipeMapBuilder<>("biological_reaction_recipes", new BioRecipeRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<BioRecipeRecipeBuilder> GENE_MUTAGENESIS = new RecipeMapBuilder<>("gene_mutagenesis", new BioRecipeRecipeBuilder())
            .itemInputs(6)
            .itemOutputs(6)
            .fluidInputs(6)
            .fluidOutputs(6)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<EnzymesReactionRecipeBuilder> ENZYMES_REACTION_RECIPES = new RecipeMapBuilder<>("enzymes_reaction_recipes", new EnzymesReactionRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<PHRecipeBuilder> FERMENTATION_TANK_RECIPES = new RecipeMapBuilder<>("fermentation_tank_recipes", new PHRecipeBuilder())
            .itemInputs(4)
            .itemOutputs(4)
            .fluidInputs(4)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_EXTRACT, HORIZONTAL)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> FUEL_REFINE_FACTORY_RECIPES = new RecipeMapBuilder<>("fuel_refine_factory_recipes", new SimpleRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(6)
            .fluidInputs(6)
            .fluidOutputs(3)
            .itemSlotOverlay(GuiTextures.MOLECULAR_OVERLAY_1, false, false)
            .itemSlotOverlay(GuiTextures.MOLECULAR_OVERLAY_2, false, true)
            .itemSlotOverlay(GuiTextures.MOLECULAR_OVERLAY_3, false, true)
            .itemSlotOverlay(GuiTextures.MOLECULAR_OVERLAY_4, true, false)
            .itemSlotOverlay(GuiTextures.VIAL_OVERLAY_1, true, true)
            .itemSlotOverlay(GuiTextures.VIAL_OVERLAY_2, true, true)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTValues.FOOLS.get() ? GTSoundEvents.SCIENCE : GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<ForceFieldCoilRecipeBuilder> NAQUADAH_REFINE_FACTORY_RECIPES = new RecipeMapBuilder<>("naquadah_refine_factory_recipes", new ForceFieldCoilRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(6)
            .fluidInputs(6)
            .fluidOutputs(3)
            .itemSlotOverlay(GuiTextures.MOLECULAR_OVERLAY_1, false, false)
            .itemSlotOverlay(GuiTextures.MOLECULAR_OVERLAY_2, false, true)
            .itemSlotOverlay(GuiTextures.MOLECULAR_OVERLAY_3, false, true)
            .itemSlotOverlay(GuiTextures.MOLECULAR_OVERLAY_4, true, false)
            .itemSlotOverlay(GuiTextures.VIAL_OVERLAY_1, true, true)
            .itemSlotOverlay(GuiTextures.VIAL_OVERLAY_2, true, true)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTValues.FOOLS.get() ? GTSoundEvents.SCIENCE : GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<FuelRecipeBuilder> ROCKET_RECIPES = new RecipeMapBuilder<>("rocket_engine_recipes", new FuelRecipeBuilder())
            .itemInputs(0)
            .itemOutputs(0)
            .fluidInputs(1)
            .fluidOutputs(1)
            .itemSlotOverlay(GuiTextures.CENTRIFUGE_OVERLAY, false, true)
            .progressBar(GuiTextures.PROGRESS_BAR_GAS_COLLECTOR, HORIZONTAL)
            .sound(GTSoundEvents.TURBINE)
            .allowEmptyOutputs()
            .build();

    public static final RecipeMap<FuelRecipeBuilder> NAQUADAH_REACTOR = new RecipeMapBuilder<>("naquadah_reactor", new FuelRecipeBuilder())
            .itemInputs(1)
            .itemOutputs(1)
            .fluidInputs(0)
            .fluidOutputs(0)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW, HORIZONTAL)
            .sound(GTSoundEvents.COMBUSTION)
            .allowEmptyOutputs()
            .generator()
            .build();

    public static final RecipeMap<ChemicalPlantBuilder> CHEMICAL_PLANT = new RecipeMapBuilder<>("chemical_plant", new ChemicalPlantBuilder())
            .itemInputs(6)
            .itemOutputs(6)
            .fluidInputs(6)
            .fluidOutputs(6)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> INTEGRATED_MINING_DIVISION = new RecipeMapBuilder<>("integrated_mining_division", new SimpleRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> UU_RECIPES = new RecipeMapBuilder<>("uu_producter", new SimpleRecipeBuilder())
            .itemInputs(2)
            .itemOutputs(0)
            .fluidInputs(1)
            .fluidOutputs(1)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<QFTCasingTierRecipeBuilder> QUANTUM_FORCE_TRANSFORMER_RECIPES = new RecipeMapBuilder<>("quantum_force_transformer_recipes", new QFTCasingTierRecipeBuilder())
            .itemInputs(6)
            .itemOutputs(6)
            .fluidInputs(6)
            .fluidOutputs(6)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTSoundEvents.SCIENCE)
            .build();

    public static final RecipeMap<FlowRateRecipeBuilder> HEAT_EXCHANGE_RECIPES = new RecipeMapBuilder<>("heat_exchanger_recipes", new FlowRateRecipeBuilder())
            .itemInputs(0)
            .itemOutputs(0)
            .fluidInputs(2)
            .fluidOutputs(3)
            .progressBar(GTQTGuiTextures.PROGRESS_BAR_HEAT_EXCHANGE, HORIZONTAL)
            .sound(GTSoundEvents.BATH)
            .ui(HeatExchangerUI::new)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> STAR_MIXER = new RecipeMapBuilder<>("star_mixer", new SimpleRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(9)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> RECYCLE_RECIPE = new RecipeMapBuilder<>("recycle_items", new SimpleRecipeBuilder())
            .itemInputs(1)
            .itemOutputs(1)
            .fluidInputs(0)
            .fluidOutputs(0)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> POLISHER_RECIPES = new RecipeMapBuilder<>("polisher", new SimpleRecipeBuilder())
            .itemInputs(1)
            .itemOutputs(2)
            .fluidInputs(1)
            .fluidOutputs(0)
            .itemSlotOverlay(GuiTextures.SAWBLADE_OVERLAY, false, false)
            .itemSlotOverlay(GuiTextures.CUTTER_OVERLAY, true, false)
            .progressBar(GuiTextures.PROGRESS_BAR_BATH, CIRCULAR)
            .sound(GTSoundEvents.CUT)
            .build();

    public static final RecipeMap<NeutronActivatorIORecipeBuilder> PAC_RECIPES = new RecipeMapBuilder<>("pac_recipes", new NeutronActivatorIORecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTSoundEvents.ARC)
            .allowEmptyOutputs()
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> PLASMA_FORGE = new RecipeMapBuilder<>("plasma_forge", new SimpleRecipeBuilder())
            .itemInputs(9)
            .itemOutputs(9)
            .fluidInputs(9)
            .fluidOutputs(9)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> STAR_BIOMIMETIC_FACTORY = new RecipeMapBuilder<>("star_biomimetic_factory", new SimpleRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(32)
            .fluidInputs(1)
            .fluidOutputs(16)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .build();

    public static final RecipeMap<UniversalDistillationRecipeBuilder> MOLECULAR_DISTILLATION_RECIPES = new RecipeMapBuilder<>(
            "molecular_distillation_recipes", new UniversalDistillationRecipeBuilder())
            .itemOutputs(1)
            .fluidInputs(1)
            .fluidOutputs(12)
            .ui(DistillationTowerUI::new)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<PrimitiveRecipeBuilder> PR_MIX = new RecipeMapBuilder<>("pr_mix", new PrimitiveRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_MACERATE, HORIZONTAL)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<HeatRecipeBuilder> SALT_FLIED = new RecipeMapBuilder<>("salt_fish", new HeatRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_MACERATE, HORIZONTAL)
            .sound(GTSoundEvents.MACERATOR)
            .build();

    public static final RecipeMap<KQComputationRecipeBuilder> RESEARCH_SYSTEM_RECIPES = new RecipeMapBuilder<>("research_system_network", new KQComputationRecipeBuilder())
            .itemInputs(2)
            .itemOutputs(1)
            .fluidInputs(1)
            .fluidOutputs(0)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW, HORIZONTAL)
            .itemSlotOverlay(GuiTextures.SCANNER_OVERLAY,false, false)
            .itemSlotOverlay( GuiTextures.RESEARCH_STATION_OVERLAY,true, false)
            .sound(GTValues.FOOLS.get() ? GTSoundEvents.SCIENCE : GTSoundEvents.COMPUTATION)
            .build();

    public static final RecipeMap<FuelRecipeBuilder> HYPER_REACTOR_MK1_RECIPES = new RecipeMapBuilder<>("hyper_reactor_mk1_recipes", new FuelRecipeBuilder())
            .itemInputs(0)
            .itemOutputs(0)
            .fluidInputs(1)
            .fluidOutputs(0)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTSoundEvents.ARC)
            .allowEmptyOutputs()
            .build();

    public static final RecipeMap<FuelRecipeBuilder> HYPER_REACTOR_MK2_RECIPES = new RecipeMapBuilder<>("hyper_reactor_mk2_recipes", new FuelRecipeBuilder())
            .itemInputs(0)
            .itemOutputs(0)
            .fluidInputs(1)
            .fluidOutputs(0)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTSoundEvents.ARC)
            .allowEmptyOutputs()
            .build();

    public static final RecipeMap<FuelRecipeBuilder> HYPER_REACTOR_MK3_RECIPES = new RecipeMapBuilder<>("hyper_reactor_mk3_recipes", new FuelRecipeBuilder())
            .itemInputs(0)
            .itemOutputs(0)
            .fluidInputs(1)
            .fluidOutputs(0)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTSoundEvents.ARC)
            .allowEmptyOutputs()
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> DISSOLUTION_TANK_RECIPES = new RecipeMapBuilder<>("dissolution_tank_recipes", new SimpleRecipeBuilder())
            .itemInputs(4)
            .itemOutputs(4)
            .fluidInputs(4)
            .fluidOutputs(4)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> FLUID_EXTRACTOR_RECIPES = new RecipeMapBuilder<>("fluid_extractor_recipes", new SimpleRecipeBuilder())
            .itemInputs(2)
            .itemOutputs(2)
            .fluidInputs(2)
            .fluidOutputs(2)
            .itemSlotOverlay(GuiTextures.EXTRACTOR_OVERLAY, false, false)
            .progressBar(GuiTextures.PROGRESS_BAR_COMPRESS, HORIZONTAL)
            .sound(GTSoundEvents.ASSEMBLER)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> FLUID_CANNER_RECIPES = new RecipeMapBuilder<>("fluid_canner_recipes", new SimpleRecipeBuilder())
            .itemInputs(2)
            .itemOutputs(2)
            .fluidInputs(2)
            .fluidOutputs(2)
            .itemSlotOverlay(GuiTextures.CANNER_OVERLAY, false, false)
            .progressBar(GuiTextures.PROGRESS_BAR_COMPRESS, HORIZONTAL)
            .sound(GTSoundEvents.ASSEMBLER)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> BIO_CENTRIFUGE = new RecipeMapBuilder<>("bio_centrifuge", new SimpleRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_MACERATE, HORIZONTAL)
            .sound(GTSoundEvents.MACERATOR)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> FLOTATION_FACTORY_RECIPES = new RecipeMapBuilder<>("flotation_factory_recipes", new SimpleRecipeBuilder())
            .itemInputs(6)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_BATH, CIRCULAR)
            .sound(GTSoundEvents.BATH)
            .build();

    public static final RecipeMap<BlastRecipeBuilder> VACUUM_DRYING_FURNACE_RECIPES = new RecipeMapBuilder<>("vacuum_drying_furnace_recipes", new BlastRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(9)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_SIFT, HORIZONTAL)
            .itemSlotOverlay(GuiTextures.FURNACE_OVERLAY_1, false, false)
            .itemSlotOverlay(GuiTextures.FURNACE_OVERLAY_1, false, true)
            .itemSlotOverlay(GuiTextures.FURNACE_OVERLAY_2, true, false)
            .itemSlotOverlay(GuiTextures.FURNACE_OVERLAY_2, true, true)
            .sound(GTSoundEvents.FURNACE)
            .build();

    public static final RecipeMap<GrindBallTierRecipeBuilder> ISA_MILL_GRINDER = new RecipeMapBuilder<>("isa_mill_recipes", new GrindBallTierRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_MACERATE, HORIZONTAL)
            .sound(GTSoundEvents.MACERATOR)
            .build();

    public static final RecipeMap<PrimitiveRecipeBuilder> OIL_POOL = new RecipeMapBuilder<>("oil_pool", new PrimitiveRecipeBuilder())
            .itemInputs(0)
            .itemOutputs(0)
            .fluidInputs(2)
            .fluidOutputs(2)
            .progressBar(GuiTextures.PROGRESS_BAR_MACERATE, HORIZONTAL)
            .sound(GTSoundEvents.MACERATOR)
            .build();

    public static final RecipeMap<CasingComputationRecipeBuilder> PRECISE_ASSEMBLER_RECIPES = new RecipeMapBuilder<>("precise_assembler_recipes", new CasingComputationRecipeBuilder())
            .itemInputs(4)
            .itemOutputs(1)
            .fluidInputs(1)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false, false)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false, true)
            .sound(GTSoundEvents.ASSEMBLER)
            .build();

    public static final RecipeMap<CasingComputationRecipeBuilder> MOLECULAR_TRANSFORMER_RECIPES = new RecipeMapBuilder<>("molecular_transformer", new CasingComputationRecipeBuilder())
            .itemInputs(1)
            .itemOutputs(1)
            .progressBar(GuiTextures.PROGRESS_BAR_COMPRESS, HORIZONTAL)
            .itemSlotOverlay(GuiTextures.MOLECULAR_OVERLAY_1, false, false)
            .itemSlotOverlay(GuiTextures.MOLECULAR_OVERLAY_2, true, false)
            .sound(GTValues.FOOLS.get() ? GTSoundEvents.SCIENCE : GTSoundEvents.ARC)
            .build();

    public static final RecipeMap<MiningDrillRecipeBuilder> MINING_DRILL_RECIPES = new RecipeMapBuilder<>("mining_drill", new MiningDrillRecipeBuilder())
            .itemInputs(1)
            .itemOutputs(9)
            .progressBar(GuiTextures.PROGRESS_BAR_MACERATE, HORIZONTAL)
            .itemSlotOverlay(GuiTextures.CRUSHED_ORE_OVERLAY, false, true)
            .itemSlotOverlay(GuiTextures.DUST_OVERLAY, true, true)
            .sound(GTSoundEvents.MACERATOR)
            .build();

    public static final RecipeMap<HeatRecipeBuilder> DISTILLATION_KETTLE = new RecipeMapBuilder<>("distillation_kettle", new HeatRecipeBuilder())
            .itemInputs(0)
            .itemOutputs(0)
            .fluidInputs(1)
            .fluidOutputs(9)
            .progressBar(GuiTextures.PROGRESS_BAR_SIFT, HORIZONTAL)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> DESULPHURIZATION_RECIPES = new RecipeMapBuilder<>("desulphurization_recipes", new SimpleRecipeBuilder())
            .itemInputs(0)
            .itemOutputs(0)
            .fluidInputs(2)
            .fluidOutputs(2)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<ChemicalPlantBuilder> FLUIDIZED_BED = new RecipeMapBuilder<>("fluidized_bed_recipes", new ChemicalPlantBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .build();

    public static final RecipeMap<HeatRecipeBuilder> PYROLYSIS_TOWER = new RecipeMapBuilder<>("pyrolysis_tower", new HeatRecipeBuilder())
            .itemInputs(1)
            .itemOutputs(12)
            .fluidInputs(1)
            .fluidOutputs(1)
            .progressBar(GuiTextures.PROGRESS_BAR_SIFT, HORIZONTAL)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<ComputationRecipeBuilder> LASER_ENGRAVING = new RecipeMapBuilder<>("laser_engraving", new ComputationRecipeBuilder())
            .itemInputs(2)
            .itemOutputs(6)
            .fluidInputs(1)
            .fluidOutputs(0)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false, false)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false, true)
            .sound(GTSoundEvents.ASSEMBLER)
            .build();

    public static final RecipeMap<ComputationRecipeBuilder> TD_PRINT_RECIPES = new RecipeMapBuilder<>("threed_print", new ComputationRecipeBuilder())
            .itemInputs(1)
            .itemOutputs(1)
            .fluidInputs(1)
            .fluidOutputs(0)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false, false)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false, true)
            .sound(GTSoundEvents.ASSEMBLER)
            .build();

    public static final RecipeMap<ComputationRecipeBuilder> PRECISION_SPRAYING = new RecipeMapBuilder<>("precision_spraying", new ComputationRecipeBuilder())
            .itemInputs(2)
            .itemOutputs(2)
            .fluidInputs(1)
            .fluidOutputs(0)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false, false)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false, true)
            .sound(GTSoundEvents.CENTRIFUGE)
            .build();

    public static final RecipeMap<ComputationRecipeBuilder> PRECISION_SPINNING = new RecipeMapBuilder<>("precision_spinning", new ComputationRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(1)
            .fluidOutputs(0)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false, false)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false, true)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> CW_LASER_ENGRAVER_RECIPES = new RecipeMapBuilder<>("cw_laser_engraver", new SimpleRecipeBuilder())
            .itemInputs(2)
            .itemOutputs(6)
            .fluidInputs(1)
            .fluidOutputs(0)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false, false)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false, true)
            .sound(GTSoundEvents.ASSEMBLER)
            .build();

    public static final RecipeMap<BlastRecipeBuilder> CW_LASER_ALLOY_RECIPES = new RecipeMapBuilder<>("cw_laser_alloy", new BlastRecipeBuilder())
            .itemInputs(9)
            .itemOutputs(0)
            .fluidInputs(3)
            .fluidOutputs(1)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .itemSlotOverlay(GuiTextures.FURNACE_OVERLAY_1, false, false)
            .itemSlotOverlay(GuiTextures.FURNACE_OVERLAY_1, false, true)
            .itemSlotOverlay(GuiTextures.FURNACE_OVERLAY_2, true, false)
            .itemSlotOverlay(GuiTextures.FURNACE_OVERLAY_2, true, true)
            .sound(GTSoundEvents.FURNACE)
            .build();

    public static final RecipeMap<BlastRecipeBuilder> ANTIMATTER_FORGE = new RecipeMapBuilder<>("antimatter_forge", new BlastRecipeBuilder())
            .itemInputs(9)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .itemSlotOverlay(GuiTextures.FURNACE_OVERLAY_1, false, false)
            .itemSlotOverlay(GuiTextures.FURNACE_OVERLAY_1, false, true)
            .itemSlotOverlay(GuiTextures.FURNACE_OVERLAY_2, true, false)
            .itemSlotOverlay(GuiTextures.FURNACE_OVERLAY_2, true, true)
            .sound(GTSoundEvents.ARC)
            .build();

    public static final RecipeMap<LaserComputationRecipeBuilder> STEPPER_RECIPES = new RecipeMapBuilder<>("stepper_recipes", new LaserComputationRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false, false)
            .itemSlotOverlay(GuiTextures.CIRCUIT_OVERLAY, false, true)
            .sound(GTSoundEvents.ASSEMBLER)
            .build();

    public static final RecipeMap<PrimitiveRecipeBuilder> CLARIFIER = new RecipeMapBuilder<>("clarifire", new PrimitiveRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_MACERATE, HORIZONTAL)
            .sound(GTSoundEvents.MACERATOR)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> ELEOIL = new RecipeMapBuilder<>("ele_oil", new SimpleRecipeBuilder())
            .itemInputs(0)
            .itemOutputs(0)
            .fluidInputs(1)
            .fluidOutputs(2)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .build();

    public static final RecipeMap<NeutronActivatorRecipeBuilder> NEUTRON_ACTIVATOR = new RecipeMapBuilder<>("neutron_activator", new NeutronActivatorRecipeBuilder())
            .itemInputs(6)
            .itemOutputs(6)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .build();

    public static final RecipeMap<SimpleRecipeBuilder> SFM = new RecipeMapBuilder<>("sfm", new SimpleRecipeBuilder())
            .itemInputs(1)
            .itemOutputs(12)
            .fluidInputs(1)
            .fluidOutputs(1)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL)
            .sound(GTSoundEvents.CHEMICAL_REACTOR)
            .ui(HeatExchangerUI::new)
            .build();

    public static final RecipeMap<PrimitiveRecipeBuilder> COAGULATION_RECIPES = new RecipeMapBuilder<>("coagulation_tank", new PrimitiveRecipeBuilder())
            .itemInputs(2)
            .itemOutputs(1)
            .fluidInputs(2)
            .fluidOutputs(0)
            .build();

    public static final RecipeMap<TargetRecipeBuilder> TARGET_CHAMBER = new RecipeMapBuilder<>("target_chamber", new TargetRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_COMPRESS, HORIZONTAL)
            .sound(GTSoundEvents.ARC)
            .build();
    ;
    public static final RecipeMap<TargetRecipeBuilder> NUCLEOSYNTHESIS = new RecipeMapBuilder<>("nucleosynthesis", new TargetRecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_COMPRESS, HORIZONTAL)
            .sound(GTSoundEvents.ARC)
            .build();;

    public static final RecipeMap<NeutronActivatorIORecipeBuilder> BEAM_COLLECTION = new RecipeMapBuilder<>("beam_collection", new NeutronActivatorIORecipeBuilder())
            .itemInputs(3)
            .itemOutputs(3)
            .fluidInputs(3)
            .fluidOutputs(3)
            .progressBar(GuiTextures.PROGRESS_BAR_COMPRESS, HORIZONTAL)
            .sound(GTSoundEvents.ARC)
            .build();;

    public static RecipeMap<FuelRecipeBuilder> FUEL_CELL = new RecipeMapBuilder<>("fuel_cell", new FuelRecipeBuilder())
            .itemInputs(0)
            .itemOutputs(0)
            .fluidInputs(2)
            .fluidOutputs(1)
            .progressBar(GuiTextures.PROGRESS_BAR_ARROW, HORIZONTAL)
            .sound(GTSoundEvents.COMBUSTION)
            .allowEmptyOutputs()
            .generator()
            .build();


    private GTQTcoreRecipeMaps() {
    }

    public static void init() {
        RecipeMaps.BLAST_RECIPES.setMaxFluidInputs(3);
        RecipeMaps.BLAST_RECIPES.setMaxFluidOutputs(3);
        CUTTER_RECIPES.setMaxOutputs(4);
        ELECTROLYZER_RECIPES.setMaxFluidInputs(2);
        LASER_ENGRAVER_RECIPES.setMaxOutputs(2);
        REPLICATOR_RECIPES.setMaxFluidInputs(3);
        COMBUSTION_GENERATOR_FUELS.setMaxFluidOutputs(1);
        CANNER_RECIPES.setMaxFluidInputs(1);
    }

}