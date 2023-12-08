package keqing.gtqtcore.api.recipes;

import gregicality.science.api.recipes.builders.NoCoilTemperatureRecipeBuilder;
import gregtech.api.GTValues;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.ComputationRecipeBuilder;
import gregtech.api.recipes.builders.FuelRecipeBuilder;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.capability.chemical_plant.ChemicalPlantBuilder;
import keqing.gtqtcore.api.recipes.builder.CACasingTierRecipeBuilder;
import keqing.gtqtcore.api.recipes.builder.FlowRateRecipeBuilder;
import keqing.gtqtcore.api.recipes.builder.PHRecipeBuilder;
import keqing.gtqtcore.api.recipes.builder.QFTCasingTierRecipeBuilder;
import keqing.gtqtcore.api.recipes.machine.RecipeMapComponentAssemblyLine;
import keqing.gtqtcore.api.recipes.machine.RecipeMapDangoteDistillery;


//怎么写请看
//https://github.com/Darknight123MC/Gregica-Sharp/blob/master/src/main/java/me/oganesson/gregicas/api/recipe/GSRecipeMaps.java
public class GTQTcoreRecipeMaps {

    public static final RecipeMap<CACasingTierRecipeBuilder> COMPONENT_ASSEMBLY_LINE_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> DIGESTER_RECIPES;
    public static final RecipeMap<PHRecipeBuilder> FERMENTATION_TANK_RECIPES;
    public static final RecipeMap<FuelRecipeBuilder> NAQUADAH_REACTOR_RECIPES;
    public static final RecipeMap<FuelRecipeBuilder> TURBINE_COMBUSTION_CHAMBER;
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
    public static final RecipeMap<FuelRecipeBuilder> STAR_MIXER;
    public static final RecipeMap<FuelRecipeBuilder> PLASMA_FORGE;
    public static final RecipeMap<FuelRecipeBuilder> STAR_BIOMIMETIC_FACTORY;
    public static final RecipeMap<NoCoilTemperatureRecipeBuilder> MOLECULAR_DISTILLATION_RECIPES;

    public static final RecipeMap<FuelRecipeBuilder> HIGH_PRESSURE_STEAM_TURBINE_FUELS;
    public static final RecipeMap<ComputationRecipeBuilder> KEQING_NET_RECIES;
    public static final RecipeMap<FuelRecipeBuilder> SUPERCRITICAL_STEAM_TURBINE_FUELS;

    public static final RecipeMap<FuelRecipeBuilder> HYPER_REACTOR_MK1_RECIPES;
    public static final RecipeMap<FuelRecipeBuilder> HYPER_REACTOR_MK2_RECIPES;
    public static final RecipeMap<FuelRecipeBuilder> HYPER_REACTOR_MK3_RECIPES;
    public static final RecipeMap<SimpleRecipeBuilder> DISSOLUTION_TANK_RECIPES;

    private GTQTcoreRecipeMaps() {}
    static {
        COMPONENT_ASSEMBLY_LINE_RECIPES = new RecipeMapComponentAssemblyLine<>("component_assembly_line_recipes", 12, 1,  12, 0, new CACasingTierRecipeBuilder(), false)
                .setSound(GTSoundEvents.ASSEMBLER);

        DIGESTER_RECIPES = new RecipeMap<>("digester_recipes", 4, 4, 4, 4, new SimpleRecipeBuilder(), false);

        FERMENTATION_TANK_RECIPES = new RecipeMap<>("fermentation_tank_recipes",  4, 4, 4,  3, new PHRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_EXTRACT, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.CHEMICAL_REACTOR);

        DISSOLUTION_TANK_RECIPES = new RecipeMap<>("dissolution_tank_recipes", 4, 4, 4, 4, new SimpleRecipeBuilder(), false);
        //  Dangote Distillery RecipeMap
        MOLECULAR_DISTILLATION_RECIPES = new RecipeMapDangoteDistillery<>("molecular_distillation_recipes", 0, true, 1, true, 1, true, 12, false, new NoCoilTemperatureRecipeBuilder(), false)
                .setSound(GTSoundEvents.CHEMICAL_REACTOR);

        //  Large Heat Exchanger Recipemap
        HEAT_EXCHANGE_RECIPES = new RecipeMap<>("heat_exchanger_recipes", 0, 0, 2, 3, new FlowRateRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARC_FURNACE, ProgressWidget.MoveType.HORIZONTAL);

        TURBINE_COMBUSTION_CHAMBER = new RecipeMap<>("turbine_combustion_chamber",
                0, 0, 1, 1, new FuelRecipeBuilder(), false);

        ROCKET = new RecipeMap<>("rocket",
                0, 0, 1, 1, new FuelRecipeBuilder(), false);

        NAQUADAH_REACTOR = new RecipeMap<>("naquadah_reactor",
                3, 1, 1, 1, new FuelRecipeBuilder(), false);

        I_MODULAR_FISSION_REACTOR = new RecipeMap<>("i_modular_fission_reactor",
                3, 1, 1, 1, new FuelRecipeBuilder(), false);

        CHEMICAL_PLANT = new RecipeMap<>("chemical_plant",
                6, 6, 6, 6, new ChemicalPlantBuilder(), false);

        INTEGRATED_MINING_DIVISION = new RecipeMap<>("integrated_mining_division",
                3, 3, 3, 0, new SimpleRecipeBuilder(), false);

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

        //  High Pressure Steam Turbine Recipemap
        HIGH_PRESSURE_STEAM_TURBINE_FUELS = new RecipeMap<>("high_pressure_steam_turbine_fuels", 0, 0, 1, 1, new FuelRecipeBuilder(), false);

        //  Supercritical Steam Turbine Recipemap
        SUPERCRITICAL_STEAM_TURBINE_FUELS = new RecipeMap<>("supercritical_steam_turbine_fuels",  0, 0, 1, 1, new FuelRecipeBuilder(), false);

        KEQING_NET_RECIES = new RecipeMap<>("keqing_net_recipes", 2, 1, 0, 0, new ComputationRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL)
                .setSlotOverlay(false, false, GuiTextures.SCANNER_OVERLAY)
                .setSlotOverlay(true, false, GuiTextures.RESEARCH_STATION_OVERLAY)
                .setSound(GTValues.FOOLS.get() ? GTSoundEvents.SCIENCE : GTSoundEvents.COMPUTATION);

        NAQUADAH_REACTOR_RECIPES = new RecipeMap<>("naquadah_reactor_recipes", 0,  0,   1,  0, new FuelRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.COMBUSTION);

        //  Hyper Reactor Mk I Recipemap
        HYPER_REACTOR_MK1_RECIPES = new RecipeMap<>("hyper_reactor_mk1_recipes", 0,  0,  1, 0, new FuelRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.ARC);

        //  Hyper Reactor Mk II Recipemap
        HYPER_REACTOR_MK2_RECIPES = new RecipeMap<>("hyper_reactor_mk2_recipes", 0,  0, 1,0, new FuelRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.ARC);

        //  Hyper Reactor Mk III Recipemap
        HYPER_REACTOR_MK3_RECIPES = new RecipeMap<>("hyper_reactor_mk3_recipes",  0, 0,  1,  0, new FuelRecipeBuilder(), false)
                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressWidget.MoveType.HORIZONTAL)
                .setSound(GTSoundEvents.ARC);

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