package keqing.gtqtcore.loaders.recipes.handlers;

import gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;

import static gregicality.multiblocks.api.unification.GCYMMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.LuV;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.circuit;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static gregtech.common.metatileentities.MetaTileEntities.DISTILLATION_TOWER;
import static gregtech.loaders.recipe.CraftingComponent.*;
import static gregtech.loaders.recipe.CraftingComponent.HULL;
import static gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.Polyetheretherketone;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.DISK_22;

public class GCYMMultiblockRecipesOverwrite {
    public static void init() {
        multiblocks();
    }
    private static void multiblocks() {
        /////////////////////////Fuck Machine
        ModHandler.removeRecipeByName("gcym:steam_engine");
        /////////////////////////Common Machine
        ModHandler.removeRecipeByName("gcym:large_circuit_assembler");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III, 8)
                .input(WIREMILL[IV], 16)
                .input(ROBOT_ARM_LuV, 16)
                .input(EMITTER_LuV, 16)
                .input(CONVEYOR_MODULE_LuV, 16)
                .input(plateDouble, HY1301, 6)
                .input(plateDouble, Naquadria, 6)
                .input(wireFine, Ruridit, 64)
                .input(wireFine, Osmium, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_CIRCUIT_ASSEMBLER)
                .EUt(VA[EV])
                .duration(1200)
                .stationResearch(b -> b
                        .researchStack(DISK_22.getStackForm())
                        .EUt(VA[LuV])
                        .CWUt(CWT[LuV]))
                .EUt(VA[UV])
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:electric_implosion_compressor");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 8)
                .input(IMPLOSION_COMPRESSOR, 4)
                .input(ELECTRIC_PISTON_IV, 8)
                .input(FIELD_GENERATOR_IV, 8)
                .input(plateDense, TantalumCarbide, 4)
                .input(spring, HSLASteel, 32)
                .input(plate, HSLASteel, 16)
                .input(plate, Rhodium, 16)
                .input(wireFine, Osmium, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.ELECTRIC_IMPLOSION_COMPRESSOR)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_wiremill");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(WIREMILL[IV], 16)
                .input(ELECTRIC_PISTON_IV, 8)
                .input(CONVEYOR_MODULE_IV, 8)
                .input(plateDense, TantalumCarbide, 4)
                .input(spring, HSLASteel, 8)
                .input(plate, HSLASteel, 16)
                .input(plate, Rhodium, 16)
                .input(wireFine, Osmium, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_WIREMILL)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_sifter");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(SIFTER[IV], 16)
                .input(ELECTRIC_PISTON_IV, 8)
                .input(CONVEYOR_MODULE_IV, 8)
                .input(plateDense, TantalumCarbide, 4)
                .input(screw, TungstenSteel, 8)
                .input(plate, HSLASteel, 16)
                .input(plate, Rhodium, 16)
                .input(wireFine, Osmium, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_SIFTER)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_engraver");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(LASER_ENGRAVER[IV], 16)
                .input(EMITTER_IV, 16)
                .input(ELECTRIC_PISTON_IV, 16)
                .input(plateDense, TantalumCarbide, 4)
                .input(screw, TungstenSteel, 8)
                .input(plate, Ruridit, 16)
                .input(plate, Rhodium, 16)
                .input(wireFine, Osmium, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_ENGRAVER)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_packager");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.EV, 8)
                .input(PACKER[HV], 16)
                .input(ROBOT_ARM_HV, 8)
                .input(CONVEYOR_MODULE_HV, 8)
                .input(pipeNormalFluid, Iridium, 4)
                .input(screw, Steel, 8)
                .input(plate, StainlessSteel, 16)
                .input(plate, Aluminium, 16)
                .input(wireFine, Osmium, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_PACKAGER)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_mixer");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(MIXER[IV], 16)
                .input(ELECTRIC_PUMP_IV, 8)
                .input(ELECTRIC_MOTOR_IV, 8)
                .input(pipeNormalFluid, Iridium, 4)
                .input(rotor, TungstenSteel, 2)
                .input(plate, Ruridit, 16)
                .input(plate, BlackSteel, 16)
                .input(wireFine, Osmium, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_MIXER)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_solidifier");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(FLUID_SOLIDIFIER[IV], 16)
                .input(ELECTRIC_PUMP_IV, 64)
                .input(CONVEYOR_MODULE_IV, 64)
                .input(pipeNormalFluid, Polyethylene, 4)
                .input(spring, MolybdenumDisilicide, 12)
                .input(plate, Ruridit, 6)
                .input(plate, BlackSteel, 16)
                .input(wireFine, Osmium, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_SOLIDIFIER)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_extruder");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(EXTRUDER[IV], 8)
                .input(ELECTROMAGNETIC_SEPARATOR[IV], 8)
                .input(ELECTRIC_PISTON_IV, 64)
                .input(CONVEYOR_MODULE_IV, 64)
                .input(pipeLargeItem, Ultimet, 4)
                .input(spring, MolybdenumDisilicide, 8)
                .input(plate, Ruridit, 16)
                .input(plate, BlackSteel, 16)
                .input(wireFine, Osmium, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_EXTRUDER)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_polarizer");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(POLARIZER[IV], 8)
                .input(ELECTROMAGNETIC_SEPARATOR[IV], 8)
                .input(pipeLargeFluid, Iridium, 4)
                .input(screw, TungstenSteel, 8)
                .input(plate, Ruridit, 16)
                .input(plate, BlackSteel, 16)
                .input(wireFine, Osmium, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_POLARIZER)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_electrolyzer");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(ELECTROLYZER[IV], 16)
                .input(pipeLargeFluid, Iridium, 4)
                .input(screw, TungstenSteel, 8)
                .input(plate, Ruridit, 16)
                .input(plate, BlackSteel, 16)
                .input(wireFine, Osmium, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_ELECTROLYZER)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_distillery");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(DISTILLATION_TOWER, 16)
                .input(CONVEYOR_MODULE_IV, 8)
                .input(ELECTRIC_PUMP_IV, 8)
                .input(pipeLargeFluid, Iridium, 4)
                .input(screw, TungstenSteel, 8)
                .input(plate, Ruridit, 16)
                .input(plate, Rhodium, 16)
                .input(wireFine, Ruridit, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_DISTILLERY)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_cutter");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(CUTTER[IV], 4)
                .input(LATHE[IV],4)
                .input(CONVEYOR_MODULE_IV, 8)
                .input(ELECTRIC_MOTOR_IV, 8)
                .input(toolHeadBuzzSaw, TungstenCarbide, 4)
                .input(screw, TungstenSteel, 8)
                .input(plate, Ruridit, 16)
                .input(plate, Rhodium, 16)
                .input(wireFine, Ruridit, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_CUTTER)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_extractor");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(EXTRACTOR[IV], 4)
                .input(CANNER[IV],4)
                .input(ELECTRIC_PUMP_IV, 8)
                .input(ELECTRIC_PISTON_IV, 8)
                .input(spring, Iridium, 8)
                .input(screw, TungstenSteel, 8)
                .input(plate, Ruridit, 16)
                .input(plate, Rhodium, 16)
                .input(wireFine, Ruridit, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_EXTRACTOR)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_chemical_bath");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(CHEMICAL_BATH[IV], 4)
                .input(ORE_WASHER[IV],4)
                .input(ELECTRIC_PUMP_IV, 8)
                .input(CONVEYOR_MODULE_IV, 8)
                .input(spring, MolybdenumDisilicide, 8)
                .input(pipeHugeFluid, StainlessSteel, 4)
                .input(plate, Ruridit, 16)
                .input(plate, Rhodium, 16)
                .input(wireFine, Ruridit, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_CHEMICAL_BATH)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_centrifuge");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(CENTRIFUGE[IV], 4)
                .input(THERMAL_CENTRIFUGE[IV],4)
                .input(ELECTRIC_MOTOR_IV, 8)
                .input(spring, MolybdenumDisilicide, 8)
                .input(pipeHugeFluid, StainlessSteel, 8)
                .input(plate, Ruridit, 16)
                .input(plate, Rhodium, 16)
                .input(wireFine, Ruridit, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_CENTRIFUGE)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_brewer");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(BREWERY[IV], 2)
                .input(FERMENTER[IV], 2)
                .input(FLUID_HEATER[IV], 2)
                .input(ELECTRIC_PUMP_IV,8)
                .input(CONVEYOR_MODULE_IV, 8)
                .input(spring, Iridium, 8)
                .input(screw, TungstenSteel, 8)
                .input(plateDouble, HSLASteel, 16)
                .input(plate, Rhodium, 16)
                .input(wireFine, Ruridit, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_BREWERY)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_bender");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(BENDER[IV], 2)
                .input(COMPRESSOR[IV], 2)
                .input(FORMING_PRESS[IV], 2)
                .input(FORGE_HAMMER[IV], 2)
                .input(CIRCUIT_GOOD_I, 8)
                .input(ELECTRIC_PISTON_IV,4)
                .input(CONVEYOR_MODULE_IV, 4)
                .input(spring, Iridium, 8)
                .input(screw, TungstenSteel, 8)
                .input(plateDouble, HSLASteel, 16)
                .input(plate, Rhodium, 16)
                .input(wireFine, Ruridit, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_BENDER)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_autoclave");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(AUTOCLAVE[IV], 8)
                .input(ELECTRIC_PUMP_IV,4)
                .input(CONVEYOR_MODULE_IV, 4)
                .input(spring, Iridium, 8)
                .input(screw, TungstenSteel, 8)
                .input(plateDouble, HSLASteel, 16)
                .input(plate, Rhodium, 16)
                .input(wireFine, Ruridit, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_AUTOCLAVE)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_assembler");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(ASSEMBLER[IV], 8)
                .input(ROBOT_ARM_IV,16)
                .input(CONVEYOR_MODULE_IV, 8)
                .input(spring, Iridium, 8)
                .input(screw, TungstenSteel, 8)
                .input(plate, Ruridit, 16)
                .input(plate, Rhodium, 16)
                .input(wireFine, Ruridit, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_ASSEMBLER)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_arc_furnace");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(ALLOY_SMELTER[EV], 8)
                .input(VOLTAGE_COIL_EV,16)
                .input(CONVEYOR_MODULE_EV, 4)
                .input(ELECTRODE_GRAPHITE, 8)
                .input(plate, TantalumCarbide, 16)
                .input(plate, Rhodium, 16)
                .input(wireFine, Ruridit, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_ARC_FURNACE)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:alloy_blast_smelter");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.IV,8)
                .input(ALLOY_SMELTER[EV], 8)
                .input(ELECTRIC_FURNACE[EV], 8)
                .input(VOLTAGE_COIL_EV,4)
                .input(plate, TantalumCarbide, 6)
                .input(plateDouble, TungstenSteel, 6)
                .input(wireFine, Ruridit, 32)
                .input(cableGtSingle, Aluminium, 64)
                .fluidInputs(Polytetrafluoroethylene.getFluid(L * 64))
                .fluidInputs(Zylon.getFluid(L * 32))
                .fluidInputs(SolderingAlloy.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.ALLOY_BLAST_SMELTER)
                .EUt(VA[EV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        ModHandler.removeRecipeByName("gcym:large_macerator");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(MACERATOR[IV], 8)
                .input(MetaItems.ELECTRIC_PISTON_IV,8)
                .input(MetaItems.ELECTRIC_MOTOR_IV, 8)
                .input(plate, TantalumCarbide, 6)
                .input(plateDouble, TungstenSteel, 6)
                .input(wireFine, Ruridit, 32)
                .input(cableGtSingle, Platinum, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(GCYMMetaTileEntities.LARGE_MACERATOR)
                .EUt(VA[IV])
                .duration(1200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .buildAndRegister();

        /////////////////////////MEGA Machine
        ModHandler.removeRecipeByName("gcym:mega_blast_furnace");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(ELECTRIC_BLAST_FURNACE.getStackForm(64))
                .inputs(ELECTRIC_BLAST_FURNACE.getStackForm(64))
                .inputs(FIELD_GENERATOR_UHV.getStackForm(64))
                .inputs(FIELD_GENERATOR_UHV.getStackForm(64))
                .input(CIRCUIT_GOOD_III, 8)
                .input(frameGt, HMS1J22Alloy, 16)
                .input(circuit, MarkerMaterials.Tier.UHV, 4)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(plate, SiliconCarbide, 16)
                .input(plateDouble, Staballoy, 4)
                .input(spring, Europium, 4)
                .input(gearSmall, Stellite, 16)
                .input(gearSmall, Neutronium, 16)
                .input(cableGtHex, SiliconCarbide, 64)
                .input(cableGtHex, SiliconCarbide, 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .outputs(GCYMMetaTileEntities.MEGA_BLAST_FURNACE.getStackForm())
                .stationResearch(b -> b
                        .researchStack(DISK_23.getStackForm())
                        .CWUt(CWT[IV])
                        .EUt(VA[LuV]))
                .EUt(VA[UV])
                .duration(1200)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();


        ModHandler.removeRecipeByName("gcym:mega_vacuum_freezer");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(MetaTileEntities.VACUUM_FREEZER.getStackForm(64))
                .inputs(MetaTileEntities.VACUUM_FREEZER.getStackForm(64))
                .inputs(FIELD_GENERATOR_UHV.getStackForm(64))
                .inputs(FIELD_GENERATOR_UHV.getStackForm(64))
                .input(CIRCUIT_GOOD_III, 8)
                .input(frameGt, HMS1J22Alloy, 16)
                .input(circuit, MarkerMaterials.Tier.UHV, 4)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(plate, Vibranium, 16)
                .input(plateDouble, Staballoy, 4)
                .input(pipeHugeFluid, Duranium, 4)
                .input(gearSmall, Stellite, 16)
                .input(gearSmall, Europium, 16)
                .input(cableGtHex, SiliconCarbide, 64)
                .input(cableGtHex, SiliconCarbide, 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .outputs(GCYMMetaTileEntities.MEGA_VACUUM_FREEZER.getStackForm())
                .stationResearch(b -> b
                        .researchStack(DISK_23.getStackForm())
                        .CWUt(CWT[IV])
                        .EUt(VA[LuV]))
                .EUt(VA[UV])
                .duration(1200)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
        ModHandler.removeRecipeByName("gcym:parallel_hatch_iv");
        ModHandler.removeRecipeByName("gcym:parallel_hatch_luv");
        ModHandler.removeRecipeByName("gcym:parallel_hatch_zpm");
        ModHandler.removeRecipeByName("gcym:parallel_hatch_uv");

        registerMachineRecipe(GTQTMetaTileEntities.PARALLEL_HATCH,
                "SCE", "CHC", "WCW",
                'C', CIRCUIT,
                'H', HULL,
                'S', SENSOR,
                'E', EMITTER,
                'W', WIRE_QUAD);
    }
}
