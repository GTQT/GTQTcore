package keqing.gtqtcore.api.recipes;

import gregtech.api.GTValues;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.RecyclingHandler;
import gregtech.api.recipes.builders.*;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.recipes.machines.RecipeMapAssemblyLine;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.util.AssemblyLineManager;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.capability.chemical_plant.ChemicalPlantBuilder;
import keqing.gtqtcore.api.recipes.builder.*;
import keqing.gtqtcore.api.recipes.machine.*;
import keqing.gtqtcore.client.textures.GCYSGuiTextures;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Consumer;


//怎么写请看
//https://github.com/Darknight123MC/Gregica-Sharp/blob/master/src/main/java/me/oganesson/gregicas/api/recipe/GSRecipeMaps.java
public class GTQTcoreRecipeMaps {
    public static final RecipeMap<SimpleRecipeBuilder> AUTO_CHISEL_RECIPES = new RecipeMap<>("auto_chisel", 2, 1, 0, 0, new SimpleRecipeBuilder(), false)
            .setSlotOverlay(false, false, false, GuiTextures.BOXED_BACKGROUND)
            .setProgressBar(GuiTextures.PROGRESS_BAR_CRACKING, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.FILE_TOOL);

    public static RecipeMap<FuelRecipeBuilder> FUEL_CELL = new RecipeMap<>("fuel_cell", 0, 0, 2, 0, new FuelRecipeBuilder(), false)
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

    public static final RecipeMap<SimpleRecipeBuilder> CATALYTIC_REFORMER_RECIPES = new RecipeMap<>("catalytic_reformer_recipes", 3,1, 1, 4, new SimpleRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_CRACKING, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.FURNACE);

    public static final RecipeMap<BlastRecipeBuilder> BURNER_REACTOR_RECIPES = new RecipeMap<>("burner_reactor_recipes",  3,  2,  3,  3, new BlastRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARC_FURNACE, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.ARC);

    public static final RecipeMap<NoCoilTemperatureRecipeBuilder> CRYOGENIC_REACTOR_RECIPES = new RecipeMap<>("cryogenic_reactor_recipes",  3,  2,  2,  2, new NoCoilTemperatureRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.COOLING);

    public static final RecipeMap<SimpleRecipeBuilder> CVD_RECIPES = new RecipeMap<>("cvd_recipes",  2,  2,  3,  3, new SimpleRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.COOLING);

    public static final RecipeMap<SimpleRecipeBuilder> PLASMA_CVD_RECIPES = new RecipeMap<>("plasma_cvd_recipes",  4,  4,  4,  4, new SimpleRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.COOLING);

    public static final RecipeMap<NoCoilTemperatureRecipeBuilder> MOLECULAR_BEAM_RECIPES = new RecipeMap<>("molecular_beam_recipes", 5, 5,  2,  1, new NoCoilTemperatureRecipeBuilder(), false)
            .setSlotOverlay(false, false, false, GCYSGuiTextures.NANOSCALE_OVERLAY_1)
            .setSlotOverlay(false, false, true, GCYSGuiTextures.NANOSCALE_OVERLAY_1)
            .setSlotOverlay(false, true, false, GCYSGuiTextures.NANOSCALE_OVERLAY_2)
            .setSlotOverlay(false, true, true, GCYSGuiTextures.NANOSCALE_OVERLAY_2)
            .setSlotOverlay(true, false, true, GCYSGuiTextures.NANOSCALE_OVERLAY_1)
            .setSlotOverlay(true, true, true, GCYSGuiTextures.NANOSCALE_OVERLAY_2)
            .setProgressBar(GCYSGuiTextures.PROGRESS_BAR_NANOSCALE, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.ELECTROLYZER);

    public static final RecipeMap<SimpleRecipeBuilder> SONICATION_RECIPES = new RecipeMap<>("sonication_recipes",    3, 2, 2, 1, new SimpleRecipeBuilder(), false)
            .setSlotOverlay(false, true, false, GuiTextures.BREWER_OVERLAY)
            .setSlotOverlay(false, true, true, GuiTextures.MOLECULAR_OVERLAY_3)
            .setSlotOverlay(true, true, true, GuiTextures.MOLECULAR_OVERLAY_4)
            .setSlotOverlay(true, false, true, GCYSGuiTextures.FOIL_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_EXTRACT, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.CENTRIFUGE);

    public static final RecipeMap<SimpleRecipeBuilder> ION_IMPLANTATOR_RECIPES = new RecipeMap<>("ion_implanter_recipes", 3, 3, 1,  1,   new SimpleRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.ELECTROLYZER);

    public static final RecipeMap<SimpleRecipeBuilder> DRILLING_RECIPES = new RecipeMap<>("drill_recipes", 3, 1,  1,    1, new SimpleRecipeBuilder(), false)
            .setSlotOverlay(false, false, true, GuiTextures.CRUSHED_ORE_OVERLAY)
            .setSlotOverlay(true, false, true, GuiTextures.DUST_OVERLAY)
            .setSound(GTSoundEvents.MACERATOR);

    public static final RecipeMap<BlastRecipeBuilder> CRYSTALLIZER_RECIPES = new RecipeMap<>("crystallization_recipes",  6, 1, 1,  3,   new BlastRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_CRYSTALLIZATION, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.FURNACE);

    public static final RecipeMap<BlastRecipeBuilder> CZPULLER_RECIPES = new RecipeMap<>("czpuller_recipes", 3, 12, 1,3,   new BlastRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_CRYSTALLIZATION, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.FURNACE);

    public static final RecipeMap<NoCoilTemperatureRecipeBuilder> ROASTER_RECIPES = new RecipeMap<>("roaster_recipes",  3,  3,  3,  3, new NoCoilTemperatureRecipeBuilder(), false)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL)
            .setSound(GTSoundEvents.FURNACE);

    public static final RecipeMap<SimpleRecipeBuilder> COMPONENT_ASSEMBLER_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> SEPTIC_TANK;
    public static final RecipeMap<SimpleRecipeBuilder> ULTRAVIOLET_LAMP_CHAMBER_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> VACUUM_CHAMBER_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> DECAY_CHAMBER_RECIPES;
    public static final RecipeMap<CACasingTierRecipeBuilder> COMPONENT_ASSEMBLY_LINE_RECIPES;
    public static final RecipeMap<ELERecipeBuilder> ELECTROBATH;
    public static final RecipeMap<PCBRecipeBuilder> PCB;
    public static final RecipeMap<PARecipeBuilder> PARTICLE_ACCELERATOR_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> DIGESTER_RECIPES;
    public static final RecipeMap<BRRecioeBuilder> BIOLOGICAL_REACTION_RECIPES;
    public static final RecipeMap<ERRecioeBuilder> ENZYMES_REACTION_RECIPES;
    public static final RecipeMap<PHRecipeBuilder> FERMENTATION_TANK_RECIPES;
    public static final RecipeMap<FuelRecipeBuilder> NAQUADAH_REACTOR_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> FUEL_REFINE_FACTORY_RECIPES;
    public static final RecipeMap<FuelRecipeBuilder> ROCKET;
    public static final RecipeMap<FuelRecipeBuilder> NAQUADAH_REACTOR;
    public static final RecipeMap<FuelRecipeBuilder> I_MODULAR_FISSION_REACTOR;
    public static final RecipeMap<ChemicalPlantBuilder> CHEMICAL_PLANT;
    public static final RecipeMap<SimpleRecipeBuilder> INTEGRATED_MINING_DIVISION;
    public static final RecipeMap<FuelRecipeBuilder> STEAM_BLAST_FURNACE_RECIPES;
    public static final RecipeMap<FuelRecipeBuilder> STEAM_ORE_WASHER_RECIPES;
    public static final RecipeMap<QFTCasingTierRecipeBuilder> QUANTUM_FORCE_TRANSFORMER_RECIPES;
    public static final RecipeMap<FlowRateRecipeBuilder> HEAT_EXCHANGE_RECIPES;
    public static final RecipeMap<PrimitiveRecipeBuilder> HEAT_EXCHANGE;
    public static final RecipeMap<FuelRecipeBuilder> STAR_MIXER;
    public static final RecipeMap<FuelRecipeBuilder> RTG_RECIPES;
    public static final RecipeMap<FuelRecipeBuilder> NUCLEAR_RECIPES;
    public static final RecipeMap<NeutronActivatorIORecipeBuilder> PAC_RECIPES;
    public static final RecipeMap<FuelRecipeBuilder> PLASMA_FORGE;
    public static final RecipeMap<FuelRecipeBuilder> STAR_BIOMIMETIC_FACTORY;
    public static final RecipeMap<NoCoilTemperatureRecipeBuilder> MOLECULAR_DISTILLATION_RECIPES;
    public static final RecipeMap<PrimitiveRecipeBuilder> ALLOY_kILN;
    public static final RecipeMap<PrimitiveRecipeBuilder> PR_MIX;
    public static final RecipeMap<PrimitiveRecipeBuilder> SALT_FLIED;
    public static final RecipeMap<FuelRecipeBuilder> HIGH_PRESSURE_STEAM_TURBINE_FUELS;
    public static final RecipeMap<KQComputationRecipeBuilder> KEQING_NET_RECIES;
    public static final RecipeMap<FuelRecipeBuilder> SUPERCRITICAL_STEAM_TURBINE_FUELS;
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
    public static final RecipeMap<PACasingTierRecipeBuilder> PRECISE_ASSEMBLER_RECIPES;
    public static final RecipeMap<ComputationRecipeBuilder> CW_PRECISE_ASSEMBLER_RECIPES;
    public static final RecipeMap<MDRecipeBuilder> MINING_DRILL_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> DISTILLATION_KETTLE;
    public static final RecipeMap<SimpleRecipeBuilder> NANOHYBRID;
    public static final RecipeMap<SimpleRecipeBuilder> PYROLYSIS_TOWER;
    public static final RecipeMap<FusionRecipeBuilder> EFUSION_RECIPES;
    public static final RecipeMap<ComputationRecipeBuilder> LASER_ENGRAVING;
    public static final RecipeMap<ComputationRecipeBuilder> TD_PRINT_RECIPES;
    public static final RecipeMap<ComputationRecipeBuilder> PRECISION_SPRAYING;
    public static final RecipeMap<LASERComputationRecipeBuilder> PRECISION_SPINNING;
    public static final RecipeMap<SimpleRecipeBuilder> CW_LASER_ENGRAVER_RECIPES;
    public static final RecipeMap<LASERComputationRecipeBuilder> STEPPER_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> GANTRY_CRANE;
    public static final RecipeMap<SimpleRecipeBuilder> CLARIFIER;
    public static final RecipeMap<SimpleRecipeBuilder>  ELEOIL;
    public static final RecipeMap<SimpleRecipeBuilder>  REACTION_FURNACE_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder>  BLAST_ARC_RECIPES;
    public static final RecipeMap<NeutronActivatorRecipeBuilder> NEUTRON_ACTIVATOR ;
    public static final RecipeMap<SimpleRecipeBuilder> SFM;
    public static final RecipeMap<PrimitiveRecipeBuilder>  COAGULATION_RECIPES ;
    public static final RecipeMap<SimpleRecipeBuilder> FLUIDIZED_BED;

    private GTQTcoreRecipeMaps() {}
    static {

        PROCESSING_MODE_A = new RecipeMapPseudoGroup<>("processing_mode_a", 1, 2, 1, 1, new SimpleRecipeBuilder(), RecipeMaps.COMPRESSOR_RECIPES, RecipeMaps.LATHE_RECIPES, RecipeMaps.POLARIZER_RECIPES, true);
        PROCESSING_MODE_B = new RecipeMapPseudoGroup<>("processing_mode_b", 2, 2, 1, 1, new SimpleRecipeBuilder(), RecipeMaps.FERMENTING_RECIPES, RecipeMaps.EXTRACTOR_RECIPES, RecipeMaps.CANNER_RECIPES, true);
        PROCESSING_MODE_C = new RecipeMapPseudoGroup<>("processing_mode_c", 2, 2, 1, 1, new SimpleRecipeBuilder(), RecipeMaps.LASER_ENGRAVER_RECIPES, RecipeMaps.AUTOCLAVE_RECIPES, RecipeMaps.FLUID_SOLIDFICATION_RECIPES, true);
        COAGULATION_RECIPES = new RecipeMap<>("coagulation_tank", 2, 1, 2, 0, new PrimitiveRecipeBuilder(), false);

        EFUSION_RECIPES = new RecipeMap<>("efusion_reactor",0,0,2,1,new FusionRecipeBuilder(),false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_COMPRESS, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.ARC);

        MINING_DRILL_RECIPES= new RecipeMap<>("mining_drill", 2, 9, 0, 0, new MDRecipeBuilder(), false)
                .setSlotOverlay(false, false, true, GuiTextures.CRUSHED_ORE_OVERLAY)
                .setSlotOverlay(true, false, true, GuiTextures.DUST_OVERLAY)
                .setSound(GTSoundEvents.MACERATOR);

        PARTICLE_ACCELERATOR_RECIPES=new RecipeMapParticleAccelerator<>("pa", 2, 1, 2, 1, new PARecipeBuilder(), false)
                .setSound(GTSoundEvents.CHEMICAL_REACTOR);

        NEUTRON_ACTIVATOR=new RecipeMap<>("neutron_activator", 6, 6, 1, 1, new NeutronActivatorRecipeBuilder(), false)
                .setSound(GTSoundEvents.CHEMICAL_REACTOR);

        VACUUM_CHAMBER_RECIPES = new RecipeMap<>("vacuum_chamber_recipes", 1, 4,  2,  1, new SimpleRecipeBuilder(), false)
                .setSlotOverlay(false, false, GuiTextures.CIRCUIT_OVERLAY)
                .setProgressBar(GuiTextures.PROGRESS_BAR_COMPRESS, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.ASSEMBLER);

        ULTRAVIOLET_LAMP_CHAMBER_RECIPES = new RecipeMap<>("ultraviolet_lamp_chamber_recipes",  2,  2,  3, 2, new SimpleRecipeBuilder(), false)
                .setSlotOverlay(false, false, false, GuiTextures.LENS_OVERLAY)
                .setSlotOverlay(false, false, true, GuiTextures.LENS_OVERLAY)
                .setSlotOverlay(false, true, false, GuiTextures.MOLECULAR_OVERLAY_3)
                .setSlotOverlay(false, true, true, GuiTextures.MOLECULAR_OVERLAY_4)
                .setSlotOverlay(true, false, GuiTextures.VIAL_OVERLAY_1)
                .setSlotOverlay(true, true, GuiTextures.VIAL_OVERLAY_2)
                .setSound(GTSoundEvents.CHEMICAL_REACTOR);

        DECAY_CHAMBER_RECIPES = new RecipeMap<>("decay_chamber_recipes",  1,  1,  1,  1, new SimpleRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_HAMMER, ProgressWidget.MoveType.VERTICAL_DOWNWARDS)
                .setSound(GTSoundEvents.SCIENCE);

        COMPONENT_ASSEMBLER_RECIPES = new RecipeMap<>("component_assembler_recipes", 6,  1,  1,  0, new SimpleRecipeBuilder(), false)
                .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
                .setSlotOverlay(false, false, true, GuiTextures.CIRCUIT_OVERLAY)
                .setSound(GTSoundEvents.ASSEMBLER);

        PRECISE_ASSEMBLER_RECIPES = new RecipeMapPreciseAssembler<>("precise_assembler_recipes", 4, 1, 4, 0, new PACasingTierRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
                .setSlotOverlay(false, false, true, GuiTextures.CIRCUIT_OVERLAY)
                .setSound(GTSoundEvents.ASSEMBLER);

        CW_PRECISE_ASSEMBLER_RECIPES = new RecipeMapPreciseAssembler<>("cwt_precise_assembler_recipes", 4, 1, 4, 0, new ComputationRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
                .setSlotOverlay(false, false, true, GuiTextures.CIRCUIT_OVERLAY)
                .setSound(GTSoundEvents.ASSEMBLER);

        LASER_ENGRAVING = new RecipeMap<>("laser_engraving", 2, 1, 1, 0, new ComputationRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
                .setSlotOverlay(false, false, true, GuiTextures.CIRCUIT_OVERLAY)
                .setSound(GTSoundEvents.ASSEMBLER);

        STEPPER_RECIPES = new RecipeMap<>("stepper_recipes", 2, 1, 1, 0, new LASERComputationRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
                .setSlotOverlay(false, false, true, GuiTextures.CIRCUIT_OVERLAY)
                .setSound(GTSoundEvents.ASSEMBLER);


        TD_PRINT_RECIPES = new RecipeMap<>("threed_print", 2, 1, 2, 0, new ComputationRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
                .setSlotOverlay(false, false, true, GuiTextures.CIRCUIT_OVERLAY)
                .setSound(GTSoundEvents.ASSEMBLER);

        PRECISION_SPRAYING = new RecipeMap<>("precision_spraying", 2, 1, 2, 0, new ComputationRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
                .setSlotOverlay(false, false, true, GuiTextures.CIRCUIT_OVERLAY)
                .setSound(GTSoundEvents.CENTRIFUGE);

        PRECISION_SPINNING = new RecipeMap<>("precision_spinning", 3, 1, 3, 0, new LASERComputationRecipeBuilder(), false)
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

        ELECTROBATH = new RecipeMap<>("electrobath", 6, 6, 6, 6, new ELERecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_BATH, ProgressWidget.MoveType.CIRCULAR)
                .setSound(GTSoundEvents.ELECTROLYZER);

        FLOTATION_FACTORY_RECIPES = new RecipeMap<>("flotation_factory_recipes", 6, 3, 3, 3, new SimpleRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_BATH, ProgressWidget.MoveType.CIRCULAR)
                .setSound(GTSoundEvents.BATH);

        ALLOY_kILN = new RecipeMap<>("alloy_klin", 2, 2, 0, 0, new PrimitiveRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.MACERATOR);

        PR_MIX = new RecipeMap<>("pr_mix", 3, 3, 3, 3, new PrimitiveRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.CHEMICAL_REACTOR);

        OIL_POOL = new RecipeMap<>("oil_pool", 0, 0, 1, 2, new PrimitiveRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.MACERATOR);

        SALT_FLIED = new RecipeMap<>("salt_fish", 3, 3, 3, 3, new PrimitiveRecipeBuilder(), false)
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

        PCB=new  RecipeMap<>("pcb", 6, 6, 6, 6, new PCBRecipeBuilder(), false);
        COMPONENT_ASSEMBLY_LINE_RECIPES = new RecipeMapComponentAssemblyLine<>("component_assembly_line_recipes", 12, 1,  12, 0, new CACasingTierRecipeBuilder(), false)
                .setSound(GTSoundEvents.ASSEMBLER);

        DIGESTER_RECIPES = new RecipeMap<>("digester_recipes", 4, 4, 4, 4, new SimpleRecipeBuilder(), false);

        FERMENTATION_TANK_RECIPES = new RecipeMap<>("fermentation_tank_recipes",  4, 4, 4,  3, new PHRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_EXTRACT, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.CHEMICAL_REACTOR);
        BIOLOGICAL_REACTION_RECIPES = new RecipeMap<>("biological_reaction_recipes", 3, 3, 3, 3, new BRRecioeBuilder(), false);
        ENZYMES_REACTION_RECIPES = new RecipeMap<>("enzymes_reaction_recipes", 3, 3, 3, 3, new ERRecioeBuilder(), false);

        DISSOLUTION_TANK_RECIPES = new RecipeMap<>("dissolution_tank_recipes", 4, 4, 4, 4, new SimpleRecipeBuilder(), false);
        //  Dangote Distillery RecipeMap
        MOLECULAR_DISTILLATION_RECIPES = new RecipeMapDangoteDistillery<>("molecular_distillation_recipes", 0, true, 1, true, 1, true, 12, false, new NoCoilTemperatureRecipeBuilder(), false)
                .setSound(GTSoundEvents.CHEMICAL_REACTOR);

        //  Large Heat Exchanger Recipemap
        HEAT_EXCHANGE_RECIPES = new RecipeMap<>("heat_exchanger_recipes", 0, 0, 2, 3, new FlowRateRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARC_FURNACE, ProgressWidget.MoveType.HORIZONTAL);

        HEAT_EXCHANGE = new RecipeMap<>("heat_exchanger", 0, 0, 1, 1, new PrimitiveRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARC_FURNACE, ProgressWidget.MoveType.HORIZONTAL);

        ROCKET = new RecipeMap<>("rocket",
                0, 0, 1, 0, new FuelRecipeBuilder(), false)
                .setSlotOverlay(false, true, true, GuiTextures.CENTRIFUGE_OVERLAY)
                .setProgressBar(GuiTextures.PROGRESS_BAR_GAS_COLLECTOR, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.TURBINE)
                .allowEmptyOutput();

        CW_LASER_ENGRAVER_RECIPES = new RecipeMap<>("cw_laser_engraver", 2, 2, 1, 0, new SimpleRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSlotOverlay(false, false, false, GuiTextures.CIRCUIT_OVERLAY)
                .setSlotOverlay(false, false, true, GuiTextures.CIRCUIT_OVERLAY)
                .setSound(GTSoundEvents.ASSEMBLER);

        NAQUADAH_REACTOR = new RecipeMap<>("naquadah_reactor",
                3, 1, 1, 1, new FuelRecipeBuilder(), false)
                .allowEmptyOutput();

        I_MODULAR_FISSION_REACTOR = new RecipeMap<>("i_modular_fission_reactor",
                3, 1, 1, 1, new FuelRecipeBuilder(), false);

        CHEMICAL_PLANT = new RecipeMap<>("chemical_plant",
                6, 6, 6, 6, new ChemicalPlantBuilder(), false);

        INTEGRATED_MINING_DIVISION = new RecipeMap<>("integrated_mining_division",
                3, 3, 3, 3, new SimpleRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.CHEMICAL_REACTOR);

        GANTRY_CRANE = new RecipeMap<>("gantry_crane",
                3, 3, 3, 3, new SimpleRecipeBuilder(), false);

        CLARIFIER = new RecipeMap<>("clarifire",
                3, 3, 3, 3, new SimpleRecipeBuilder(), false);

        ELEOIL = new RecipeMap<>("ele_oil",
                0, 0, 2, 2, new SimpleRecipeBuilder(), false);

        REACTION_FURNACE_RECIPES = new RecipeMap<>("reaction_furnace",
                3, 3, 3, 3, new SimpleRecipeBuilder(), false);

        BLAST_ARC_RECIPES = new RecipeMap<>("blast_arc_furnace",
                6, 3, 6, 3, new SimpleRecipeBuilder(), false);

        SEPTIC_TANK = new RecipeMap<>("septic_tank",
                2, 2, 2, 2, new SimpleRecipeBuilder(), false);

        FLUIDIZED_BED = new RecipeMap<>("fluidized_bed",
                3, 3, 3, 3, new SimpleRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.SCIENCE);

        DISTILLATION_KETTLE = new RecipeMapGTQTDistillationTower<>("distillation_kettle",
                1,false, 1,false, 1,false, 12,false, new SimpleRecipeBuilder(), false).setSound(GTSoundEvents.CHEMICAL_REACTOR);

        SFM = new RecipeMapGTQTDistillationTower<>("sfm",
                1, false, 1,false,  1,false,  12,false,  new SimpleRecipeBuilder(), false).setSound(GTSoundEvents.CHEMICAL_REACTOR);

        PYROLYSIS_TOWER = new RecipeMap<>("pyrolysis_tower",
                1, 1, 1, 8, new SimpleRecipeBuilder(), false);

        STEAM_BLAST_FURNACE_RECIPES = new RecipeMap<>("steam_blast_furnace",
                3, 1, 0, 0, new FuelRecipeBuilder(), false);

        STEAM_ORE_WASHER_RECIPES = new RecipeMap<>("steam_ore_washer",
                3, 1, 0, 0, new FuelRecipeBuilder(), false);

        QUANTUM_FORCE_TRANSFORMER_RECIPES = new RecipeMap<>("quantum_force_transformer_recipes", 6,  6,  6,  6, new QFTCasingTierRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.SCIENCE);

        STAR_MIXER = new RecipeMap<>("star_mixer",
                3, 3, 9, 3, new FuelRecipeBuilder(), false);

        STAR_BIOMIMETIC_FACTORY = new RecipeMap<>("star_biomimetic_factory",
                3, 32, 1, 16, new FuelRecipeBuilder(), false);

        PLASMA_FORGE = new RecipeMap<>("plasma_forge",
                9, 9, 9, 9, new FuelRecipeBuilder(), false);

        NANOHYBRID = new RecipeMap<>("nanohybrid",
                9, 1, 3, 0, new SimpleRecipeBuilder(), false);

        //  High Pressure Steam Turbine Recipemap
        HIGH_PRESSURE_STEAM_TURBINE_FUELS = new RecipeMap<>("high_pressure_steam_turbine_fuels", 0, 0, 1, 1, new FuelRecipeBuilder(), false);

        //  Supercritical Steam Turbine Recipemap
        SUPERCRITICAL_STEAM_TURBINE_FUELS = new RecipeMap<>("supercritical_steam_turbine_fuels",  0, 0, 1, 1, new FuelRecipeBuilder(), false);

        KEQING_NET_RECIES = new RecipeMap<>("keqing_net_recipes", 2, 1, 0, 0, new KQComputationRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL)
                .setSlotOverlay(false, false, GuiTextures.SCANNER_OVERLAY)
                .setSlotOverlay(true, false, GuiTextures.RESEARCH_STATION_OVERLAY)
                .setSound(GTValues.FOOLS.get() ? GTSoundEvents.SCIENCE : GTSoundEvents.COMPUTATION);


        NAQUADAH_REACTOR_RECIPES = new RecipeMap<>("naquadah_reactor_recipes", 0,  0,   1,  0, new FuelRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.COMBUSTION)
                .allowEmptyOutput();

        RTG_RECIPES = new RecipeMap<>("rtg_recipes", 1,  1,  1, 0, new FuelRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.ARC);

        NUCLEAR_RECIPES = new RecipeMap<>("nuclear_recipes", 1,  1,  1, 1, new FuelRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.ARC);

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

        FUEL_REFINE_FACTORY_RECIPES = new RecipeMap<>("fuel_refine_factory_recipes", 3,  4,  4, 2, new SimpleRecipeBuilder(), false)
                .setSlotOverlay(false, false, false, GuiTextures.MOLECULAR_OVERLAY_1)
                .setSlotOverlay(false, false, true, GuiTextures.MOLECULAR_OVERLAY_2)
                .setSlotOverlay(false, true, false, GuiTextures.MOLECULAR_OVERLAY_3)
                .setSlotOverlay(false, true, true, GuiTextures.MOLECULAR_OVERLAY_4)
                .setSlotOverlay(true, false, GuiTextures.VIAL_OVERLAY_1)
                .setSlotOverlay(true, true, GuiTextures.VIAL_OVERLAY_2)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTValues.FOOLS.get() ? GTSoundEvents.SCIENCE : GTSoundEvents.CHEMICAL_REACTOR);

    }

}