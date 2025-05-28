package keqing.gtqtcore.loaders.recipes;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockSteamCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.loaders.recipe.CraftingComponent;
import keqing.gtqtcore.api.unification.MaterialHelper;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockElectrolyticBath;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing6;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import supercritical.common.metatileentities.SCMetaTileEntities;

import static gregicality.multiblocks.api.unification.GCYMMaterials.MolybdenumDisilicide;
import static gregicality.multiblocks.api.unification.GCYMMaterials.Stellite100;
import static gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities.LARGE_ASSEMBLER;
import static gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities.LARGE_DISTILLERY;
import static gregtech.api.GTValues.LuV;
import static gregtech.api.GTValues.UV;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.MarkerMaterials.Tier.HV;
import static gregtech.api.unification.material.MarkerMaterials.Tier.LV;
import static gregtech.api.unification.material.MarkerMaterials.Tier.MV;
import static gregtech.api.unification.material.MarkerMaterials.Tier.ULV;
import static gregtech.api.unification.material.MarkerMaterials.Tier.ZPM;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.blocks.BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS;
import static gregtech.common.blocks.BlockWireCoil.CoilType.NICHROME;
import static gregtech.common.blocks.MetaBlocks.MACHINE_CASING;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static gregtech.loaders.recipe.CraftingComponent.GLASS;
import static gregtech.loaders.recipe.CraftingComponent.PIPE_NORMAL;
import static gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.*;
import static keqing.gtqtcore.common.block.blocks.BlockCrucible.CrucibleType.QUARTZ_CRUCIBLE;
import static keqing.gtqtcore.common.block.blocks.BlockIsaCasing.CasingType.ASEPTIC_FARM_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4.TurbineCasingType.ADVANCED_FILTER_CASING;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.DISTILLATION_TOWER;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.GAS_COLLECTOR;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.PYROLYSE_OVEN;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.*;
import static net.minecraft.init.Blocks.FURNACE;
import static net.minecraft.init.Blocks.IRON_BARS;
import static supercritical.common.metatileentities.SCMetaTileEntities.DECAY_CHAMBER;


public class MetaTileEntityLoader {
    public static void init() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate_big, Steel, 32)
                .input(stick, Steel, 16)
                .input(ring, Steel, 8)
                .input(rotor, Steel, 4)
                .output(WIND_ROTOR_STEEL)
                .circuitMeta(11)
                .duration(600).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate_big, Aluminium, 32)
                .input(stick, Aluminium, 16)
                .input(ring, Aluminium, 8)
                .input(rotor, Aluminium, 4)
                .output(WIND_ROTOR_ALUMINIUM)
                .circuitMeta(11)
                .duration(600).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate_big, StainlessSteel, 32)
                .input(stick, StainlessSteel, 16)
                .input(ring, StainlessSteel, 8)
                .input(rotor, StainlessSteel, 4)
                .output(WIND_ROTOR_STAINLESSSTEEL)
                .circuitMeta(11)
                .duration(600).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate_big, Titanium, 32)
                .input(stick, Titanium, 16)
                .input(ring, Titanium, 8)
                .input(rotor, Titanium, 4)
                .output(WIND_ROTOR_TITANIUM)
                .circuitMeta(11)
                .duration(600).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate_big, TungstenSteel, 32)
                .input(stick, TungstenSteel, 16)
                .input(ring, TungstenSteel, 8)
                .input(rotor, TungstenSteel, 4)
                .output(WIND_ROTOR_TUNGSTENSTEEL)
                .circuitMeta(11)
                .duration(600).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate_big, RhodiumPlatedPalladium, 32)
                .input(stick, RhodiumPlatedPalladium, 16)
                .input(ring, RhodiumPlatedPalladium, 8)
                .input(rotor, RhodiumPlatedPalladium, 4)
                .output(WIND_ROTOR_RHODIUMPLATEDPALLADIUM)
                .circuitMeta(11)
                .duration(600).EUt(120).buildAndRegister();
        // Minecart wheels
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(stick, Iron)
                .input(ring, Iron, 2)
                .output(IRON_MINECART_WHEELS)
                .circuitMeta(1)
                .duration(100).EUt(20).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(stick, Steel)
                .input(ring, Steel, 2)
                .output(STEEL_MINECART_WHEELS)
                .circuitMeta(1)
                .duration(60).EUt(20).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(120)
                .inputs(HULL[2].getStackForm(4))
                .input(ELECTRIC_PUMP_MV, 8)
                .input(circuit, MV, 8)
                .input(rotor, Aluminium, 32)
                .input(OrePrefix.cableGtSingle, Materials.Tin, 32)
                .fluidInputs(Polyethylene.getFluid(L * 12))
                .outputs(GAS_COLLECTOR.getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(500).EUt(120)
                .inputs(HULL[2].getStackForm(4))
                .input(ELECTRIC_MOTOR_MV, 8)
                .input(ELECTRIC_PUMP_MV, 8)
                .input(circuit, MV, 8)
                .input(gear, Aluminium, 8)
                .input(OrePrefix.cableGtSingle, Materials.Tin, 32)
                .fluidInputs(Polyethylene.getFluid(L * 12))
                .outputs(WATER_POWER_STATION[0].getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(500).EUt(1920)
                .inputs(HULL[4].getStackForm(4))
                .input(ELECTRIC_MOTOR_EV, 8)
                .input(ELECTRIC_PUMP_EV, 8)
                .input(circuit, MarkerMaterials.Tier.EV, 8)
                .input(gear, Titanium, 8)
                .input(OrePrefix.cableGtSingle, Platinum, 32)
                .fluidInputs(Epoxy.getFluid(L * 12))
                .outputs(WATER_POWER_STATION[1].getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(500).EUt(30720)
                .inputs(HULL[6].getStackForm(4))
                .input(ELECTRIC_MOTOR_LuV, 8)
                .input(ELECTRIC_PUMP_LuV, 8)
                .input(circuit, MarkerMaterials.Tier.LuV, 8)
                .input(gear, RhodiumPlatedPalladium, 8)
                .input(OrePrefix.cableGtSingle, Naquadah, 32)
                .fluidInputs(Zylon.getFluid(L * 12))
                .outputs(WATER_POWER_STATION[2].getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(500).EUt(480)
                .inputs(HULL[3].getStackForm(4))
                .input(circuit, MarkerMaterials.Tier.EV, 8)
                .input(ELECTRIC_MOTOR_HV, 4)
                .input(ROBOT_ARM_HV, 8)
                .input(gear, StainlessSteel, 8)
                .input(stick, Talonite, 8)
                .input(OrePrefix.cableGtSingle, Aluminium, 32)
                .circuitMeta(10)
                .fluidInputs(Epoxy.getFluid(L * 12))
                .outputs(WIND_GENERATOR.getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(500).EUt(480)
                .inputs(HULL[3].getStackForm(4))
                .input(circuit, MarkerMaterials.Tier.EV, 8)
                .input(EMITTER_HV, 4)
                .input(ELECTRIC_PUMP_HV, 8)
                .input(gear, StainlessSteel, 8)
                .input(stick, Talonite, 8)
                .input(OrePrefix.cableGtSingle, Aluminium, 32)
                .circuitMeta(10)
                .fluidInputs(Epoxy.getFluid(L * 12))
                .outputs(TIDE_CONTROL.getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(500).EUt(480)
                .input(frameGt, StainlessSteel, 8)
                .input(circuit, MarkerMaterials.Tier.EV, 8)
                .input(SENSOR_HV, 2)
                .input(ELECTRIC_PUMP_EV, 4)
                .input(VOLTAGE_COIL_HV, 8)
                .input(gear, Steel, 8)
                .input(OrePrefix.cableGtSingle, Aluminium, 32)
                .circuitMeta(10)
                .fluidInputs(Epoxy.getFluid(L * 12))
                .outputs(TIDE_UNIT.getStackForm()).buildAndRegister();

        ModHandler.addShapedRecipe(true, "industry_pump", INDUSTRY_WATER_PUMP.getStackForm(),
                "ABA", "CHC", "ABA",
                'H', MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LV),
                'C', new UnificationEntry(pipeLargeFluid, Steel),
                'B', new UnificationEntry(plate, Iron),
                'A', new UnificationEntry(plate, Invar));

        ModHandler.addShapedRecipe(true, "ulv_casing", HULL[0].getStackForm(),
                "ABA", "CHC", "ABA",
                'H', MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ULV),
                'C', new UnificationEntry(wireGtSingle, Lead),
                'B', new UnificationEntry(plate, Iron),
                'A', new UnificationEntry(plate, RedAlloy));

        ModHandler.addShapedRecipe(true, "lv_casing", HULL[1].getStackForm(),
                "ABA", "CHC", "ABA",
                'H', MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LV),
                'C', new UnificationEntry(cableGtSingle, Tin),
                'B', new UnificationEntry(plate, GalvanizedSteel),
                'A', new UnificationEntry(plate, Rubber));

        ModHandler.addShapedRecipe(true, "mv_casing", HULL[2].getStackForm(),
                "ABA", "CHC", "ABA",
                'H', MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.MV),
                'C', new UnificationEntry(cableGtSingle, Copper),
                'B', new UnificationEntry(plate, Invar),
                'A', new UnificationEntry(plate, Polyethylene));

        ModHandler.addShapedRecipe(true, "hv_casing", HULL[3].getStackForm(),
                "ABA", "CHC", "ABA",
                'H', MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.HV),
                'C', new UnificationEntry(cableGtSingle, Aluminium),
                'B', new UnificationEntry(plate, Talonite),
                'A', new UnificationEntry(plate, Epoxy));

        ModHandler.addShapedRecipe(true, "ev_casing", HULL[4].getStackForm(),
                "ABA", "CHC", "ABA",
                'H', MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.EV),
                'C', new UnificationEntry(cableGtSingle, Gold),
                'B', new UnificationEntry(plate, Palladium),
                'A', new UnificationEntry(plate, Polytetrafluoroethylene));

        ModHandler.addShapedRecipe(true, "item_input_hatch", ITEM_IMPORT_BUS[0].getStackForm(),
                "MC", "hc",
                'M', MetaTileEntities.HULL[GTValues.ULV].getStackForm(),
                'C', new ItemStack(Blocks.CHEST));

        ModHandler.addShapedRecipe(true, "item_output_hatch", ITEM_EXPORT_BUS[0].getStackForm(),
                "MC", "ch",
                'M', MetaTileEntities.HULL[GTValues.ULV].getStackForm(),
                'C', new ItemStack(Blocks.CHEST));

        ModHandler.addShapedRecipe(true, "fluid_input_hatch", FLUID_IMPORT_HATCH[0].getStackForm(),
                "MC", "hc",
                'M', MetaTileEntities.HULL[GTValues.ULV].getStackForm(),
                'C', new ItemStack(Blocks.GLASS));

        ModHandler.addShapedRecipe(true, "fluid_output_hatch", FLUID_EXPORT_HATCH[0].getStackForm(),
                "MC", "ch",
                'M', MetaTileEntities.HULL[GTValues.ULV].getStackForm(),
                'C', new ItemStack(Blocks.GLASS));

        ModHandler.addShapedRecipe(true, "mold_gas", MOLD_GAS.getStackForm(),
                "MC", "ch",
                'M', new UnificationEntry(block, Steel),
                'C', new UnificationEntry(cylinder, Aluminium));

        ModHandler.addShapedRecipe(true, "mold_motor", MOLD_MOTOR.getStackForm(),
                "MC", "ch",
                'M', new UnificationEntry(block, Steel),
                'C', new UnificationEntry(motor_stick, Aluminium));

        ModHandler.addShapedRecipe(true, "mold_value", MOLD_VALUE.getStackForm(),
                "MC", "ch",
                'M', new UnificationEntry(block, Steel),
                'C', new UnificationEntry(valve, Aluminium));

        ModHandler.addShapedRecipe(true, "mte_copy_card", MTE_COPY_CARD.getStackForm(),
                "SF ", "DPR", "SF ",
                'R', new UnificationEntry(circuit, MarkerMaterials.Tier.LV),
                'P', MetaItems.VOLTAGE_COIL_LV,
                'D', new UnificationEntry(plate, Steel),
                'S', new UnificationEntry(screw, Bronze),
                'F', new UnificationEntry(foil, Nickel));
        //3d
        ModHandler.addShapedRecipe(true, "three_d", THREE_DIM_PRINT.getStackForm(),
                "MCM", "HHH", "PPP",
                'M', new UnificationEntry(pipeNormalFluid, Polyethylene),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.MV),
                'P', ELECTRIC_PUMP_MV.getStackForm(),
                'H', FLUID_SOLIDIFIER[2].getStackForm()
        );

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[7])
                .input(ELECTRIC_MOTOR_ZPM, 32)
                .input(ELECTRIC_PISTON_ZPM, 8)
                .input(ELECTRIC_PUMP_ZPM, 16)
                .input(CONVEYOR_MODULE_ZPM, 8)
                .input(ROBOT_ARM_ZPM, 8)
                .input(plateDouble, StainlessSteel, 32)
                .input(rotor, Chrome, 16)
                .input(circuit, MarkerMaterials.Tier.UV, 4)
                .input(pipeNormalFluid, Polybenzimidazole, 32)
                .input(COMPONENT_GRINDER_TUNGSTEN, 64)
                .input(wireGtDouble, UraniumRhodiumDinaquadide, 16)
                .fluidInputs(SolderingAlloy.getFluid(2880))
                .fluidInputs(Naquadria.getFluid(1440))
                .output(INTEGRATED_ORE_PROCESSOR)
                .EUt(VA[7])
                .duration(1600)
                .stationResearch(b -> b
                        .researchStack(DISK_22.getStackForm())
                        .EUt(VA[LuV])
                        .CWUt(CWT[LuV]))
                .buildAndRegister();

        //处理阵列
        ModHandler.addShapedRecipe(true, "lv_processing_array", LV_PROCESSING_ARRAY.getStackForm(),
                "RCR", "SPE", "HNH",
                'R', MetaItems.ROBOT_ARM_MV,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.MV),
                'S', MetaItems.SENSOR_MV,
                'P', ASSEMBLER[1].getStackForm(),
                'E', MetaItems.EMITTER_MV,
                'H', new UnificationEntry(OrePrefix.plate, Invar),
                'N', new UnificationEntry(OrePrefix.pipeLargeFluid, Aluminium));

        ModHandler.addShapedRecipe(true, "mv_processing_array", MV_PROCESSING_ARRAY.getStackForm(),
                "RCR", "SPE", "HNH",
                'R', MetaItems.ROBOT_ARM_HV,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.HV),
                'S', MetaItems.SENSOR_HV,
                'P', GTQTMetaTileEntities.LV_PROCESSING_ARRAY.getStackForm(),
                'E', MetaItems.EMITTER_HV,
                'H', new UnificationEntry(OrePrefix.plate, Talonite),
                'N', new UnificationEntry(OrePrefix.pipeLargeFluid, StainlessSteel));

        ModHandler.addShapedRecipe(true, "hv_processing_array", HV_PROCESSING_ARRAY.getStackForm(),
                "RCR", "SPE", "HNH",
                'R', MetaItems.ROBOT_ARM_EV,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.EV),
                'S', MetaItems.SENSOR_EV,
                'P', GTQTMetaTileEntities.MV_PROCESSING_ARRAY.getStackForm(),
                'E', MetaItems.EMITTER_EV,
                'H', new UnificationEntry(OrePrefix.plate, Titanium),
                'N', new UnificationEntry(OrePrefix.pipeLargeFluid, Polyethylene));

        //发电阵列
        ModHandler.addShapedRecipe(true, "lv_generator_array", LV_GENERATOR_ARRAY.getStackForm(),
                "RCR", "SPE", "HNH",
                'R', MetaItems.ROBOT_ARM_MV,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.MV),
                'E', MetaItems.SENSOR_MV,
                'P', ASSEMBLER[1].getStackForm(),
                'S', MetaItems.EMITTER_MV,
                'H', new UnificationEntry(OrePrefix.plate, Invar),
                'N', new UnificationEntry(OrePrefix.pipeLargeFluid, Aluminium));

        ModHandler.addShapedRecipe(true, "mv_generator_array", MV_GENERATOR_ARRAY.getStackForm(),
                "RCR", "SPE", "HNH",
                'R', MetaItems.ROBOT_ARM_HV,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.HV),
                'E', MetaItems.SENSOR_HV,
                'P', GTQTMetaTileEntities.LV_GENERATOR_ARRAY.getStackForm(),
                'S', MetaItems.EMITTER_HV,
                'H', new UnificationEntry(OrePrefix.plate, Talonite),
                'N', new UnificationEntry(OrePrefix.pipeLargeFluid, StainlessSteel));

        ModHandler.addShapedRecipe(true, "hv_generator_array", HV_GENERATOR_ARRAY.getStackForm(),
                "RCR", "SPE", "HNH",
                'R', MetaItems.ROBOT_ARM_EV,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.EV),
                'E', MetaItems.SENSOR_EV,
                'P', GTQTMetaTileEntities.MV_GENERATOR_ARRAY.getStackForm(),
                'S', MetaItems.EMITTER_EV,
                'H', new UnificationEntry(OrePrefix.plate, Titanium),
                'N', new UnificationEntry(OrePrefix.pipeLargeFluid, Polyethylene));

        ModHandler.addShapedRecipe(true, "ev_generator_array", EV_GENERATOR_ARRAY.getStackForm(),
                "RCR", "SPE", "HNH",
                'R', MetaItems.ROBOT_ARM_IV,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.IV),
                'E', MetaItems.SENSOR_IV,
                'P', MetaTileEntities.PROCESSING_ARRAY.getStackForm(),
                'S', MetaItems.EMITTER_IV,
                'H', new UnificationEntry(OrePrefix.plate, TungstenSteel),
                'N', new UnificationEntry(OrePrefix.pipeLargeFluid, Materials.Titanium));

        ModHandler.addShapedRecipe(true, "iv_generator_array",
                IV_GENERATOR_ARRAY.getStackForm(),
                "RCR", "SPE", "HNH",
                'R', MetaItems.ROBOT_ARM_LuV,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.ZPM),
                'E', MetaItems.SENSOR_LuV,
                'P', MetaTileEntities.PROCESSING_ARRAY.getStackForm(),
                'S', MetaItems.EMITTER_LuV,
                'H', new UnificationEntry(OrePrefix.plate, Materials.HSSE),
                'N', new UnificationEntry(OrePrefix.pipeLargeFluid, Materials.Naquadah));

        ModHandler.addShapedRecipe(true, "generator_access_interface", GENERATOR_HATCH.getStackForm(),
                "CHS",
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.MV),
                'H', MetaTileEntities.HULL[GTValues.MV].getStackForm(),
                'S', EMITTER_MV.getStackForm());

        //耐火砖快乐配方
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(MetaItems.FIRECLAY_BRICK.getStackForm(6))
                .input(dust, Gypsum, 2)
                .fluidInputs(Concrete.getFluid(1000))
                .outputs(MetaBlocks.METAL_CASING.getItemVariant(BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS))
                .duration(20).EUt(30).buildAndRegister();

        //垃圾桶
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Steel, 16)
                .input(stickLong, Steel, 2)
                .input(circuit, LV, 4)
                .input(pipeHugeItem, Nickel, 1)
                .circuitMeta(10)
                .output(RUBBISH_BIN)
                .duration(20).EUt(30).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Steel, 16)
                .input(stickLong, Steel, 2)
                .input(circuit, LV, 4)
                .input(pipeLargeFluid, Steel, 1)
                .circuitMeta(10)
                .output(FLUID_RUBBISH_BIN)
                .duration(20).EUt(30).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(FLUID_RUBBISH_BIN)
                .input(RUBBISH_BIN)
                .output(COMMON_RUBBISH_BIN)
                .duration(20).EUt(30).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Steel, 32)
                .input(stickLong, Steel, 4)
                .input(circuit, LV, 8)
                .input(pipeHugeItem, Nickel, 1)
                .input(pipeLargeFluid, Steel, 1)
                .circuitMeta(11)
                .output(COMMON_RUBBISH_BIN)
                .duration(20).EUt(30).buildAndRegister();

        //海藻方块
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(MetaTileEntities.HULL[GTValues.HV].getStackForm())
                .input(screw, NanometerBariumTitanate, 1)
                .input(plate, Nickel, 4)
                .fluidInputs(Bps.getFluid(1440))
                .circuitMeta(1)
                .outputs(GTQTMetaBlocks.blockIsaCasing.getItemVariant(ASEPTIC_FARM_CASING))
                .duration(20).EUt(30).buildAndRegister();
        //太阳能
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[4].getStackForm())
                .input(EMITTER_MV, 8)
                .input(plate, StainlessSteel, 16)
                .fluidInputs(Polybenzimidazole.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockElectrolyticBath.getItemVariant(BlockElectrolyticBath.CasingType.SOLAR_PLATE_CASING))
                .duration(2000).EUt(1920).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[4].getStackForm())
                .input(circuit, MarkerMaterials.Tier.HV, 8)
                .input(COVER_SCREEN)
                .input(wireFine, Platinum, 16)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .output(SOLAR_PLATE)
                .duration(2000).EUt(1920).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust, GalliumArsenide, 16)
                .input(plate, Polysilicon, 16)
                .fluidInputs(Nitrogen.getFluid(4000))
                .output(SOLAR_PLATE_MKI, 1)
                .blastFurnaceTemp(2700)
                .duration(2000).EUt(480).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust, IndiumGalliumPhosphide, 16)
                .input(plate, Polysilicon, 16)
                .fluidInputs(Nitrogen.getFluid(4000))
                .output(SOLAR_PLATE_MKII, 1)
                .blastFurnaceTemp(3600)
                .duration(2000).EUt(1920).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust, NaquadriaGalliumIndium, 16)
                .input(plate, Polysilicon, 16)
                .fluidInputs(Nitrogen.getFluid(4000))
                .output(SOLAR_PLATE_MKIII, 1)
                .blastFurnaceTemp(4500)
                .duration(2000).EUt(7680).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockElectrolyticBath.getItemVariant(BlockElectrolyticBath.CasingType.SOLAR_PLATE_CASING))
                .input(EMITTER_HV, 1)
                .input(circuit, MarkerMaterials.Tier.HV, 8)
                .input(SOLAR_PLATE_MKI, 4)
                .fluidInputs(Polybenzimidazole.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockElectrolyticBath.getItemVariant(BlockElectrolyticBath.CasingType.SOLAR_PLATE_LV))
                .duration(2000).EUt(1920).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockElectrolyticBath.getItemVariant(BlockElectrolyticBath.CasingType.SOLAR_PLATE_CASING))
                .input(EMITTER_EV, 1)
                .input(circuit, MarkerMaterials.Tier.EV, 8)
                .input(SOLAR_PLATE_MKII, 4)
                .fluidInputs(Polybenzimidazole.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockElectrolyticBath.getItemVariant(BlockElectrolyticBath.CasingType.SOLAR_PLATE_MV))
                .duration(2000).EUt(1920).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockElectrolyticBath.getItemVariant(BlockElectrolyticBath.CasingType.SOLAR_PLATE_CASING))
                .input(EMITTER_IV, 1)
                .input(circuit, MarkerMaterials.Tier.IV, 8)
                .input(SOLAR_PLATE_MKIII, 4)
                .fluidInputs(Polybenzimidazole.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockElectrolyticBath.getItemVariant(BlockElectrolyticBath.CasingType.SOLAR_PLATE_HV))
                .duration(2000).EUt(1920).buildAndRegister();

        //化工厂
        ModHandler.addShapedRecipe(true, "chemical_plant", CHEMICAL_PLANT.getStackForm(),
                "MCM", "PHP", "MKM",
                'M', new UnificationEntry(pipeNormalFluid, StainlessSteel),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.HV),
                'P', ELECTRIC_PUMP_HV.getStackForm(),
                'H', CHEMICAL_REACTOR[3].getStackForm(),
                'K', new UnificationEntry(cableGtDouble, Aluminium)
        );

        ModHandler.addShapedRecipe(true, "catalyst_hatch.0", CATALYST_HATCH[0].getStackForm(),
                "MhM", "PHP", "McM",
                'M', new UnificationEntry(pipeNormalFluid, Aluminium),
                'P', ROBOT_ARM_HV.getStackForm(),
                'H', MetaTileEntities.HULL[3].getStackForm()
        );
        ModHandler.addShapedRecipe(true, "catalyst_hatch.1", CATALYST_HATCH[1].getStackForm(),
                "MhM", "PHP", "McM",
                'M', new UnificationEntry(pipeNormalFluid, StainlessSteel),
                'P', ROBOT_ARM_EV.getStackForm(),
                'H', MetaTileEntities.HULL[4].getStackForm()
        );
        ModHandler.addShapedRecipe(true, "catalyst_hatch.2", CATALYST_HATCH[2].getStackForm(),
                "MhM", "PHP", "McM",
                'M', new UnificationEntry(pipeNormalFluid, TungstenSteel),
                'P', ROBOT_ARM_IV.getStackForm(),
                'H', MetaTileEntities.HULL[5].getStackForm()
        );
        ModHandler.addShapedRecipe(true, "catalyst_hatch.3", CATALYST_HATCH[3].getStackForm(),
                "MhM", "PHP", "McM",
                'M', new UnificationEntry(pipeNormalFluid, TungstenSteel),
                'P', ROBOT_ARM_LuV.getStackForm(),
                'H', MetaTileEntities.HULL[6].getStackForm()
        );

        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .input(ITEM_IMPORT_BUS[GTValues.ULV])
                .input(gear, Steel, 4)
                .input(plate, Aluminium, 16)
                .input(wireFine, Tin, 8)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .output(SINGLE_ITEM_INPUT_BUS)
                .EUt(VA[GTValues.LV])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  Single Input Bus
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .input(ITEM_IMPORT_BUS[GTValues.LV])
                .input(gear, Steel, 4)
                .input(plate, Aluminium, 16)
                .input(wireFine, Tin, 8)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .output(SINGLE_INPUT_BUS)
                .EUt(VA[GTValues.LV])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  Super Input Bus
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .input(ITEM_IMPORT_BUS[GTValues.HV])
                .input(gear, StainlessSteel, 4)
                .input(plate, Titanium, 16)
                .input(wireFine, Platinum, 8)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .output(SUPER_INPUT_BUS)
                .EUt(VA[GTValues.HV])
                .duration(15 * SECOND)
                .buildAndRegister();

        ModHandler.addShapedRecipe(true, "bio_hatch", BIO_HATCH.getStackForm(),
                "MhM", "PHP", "McM",
                'M', new UnificationEntry(pipeNormalFluid, StainlessSteel),
                'P', ELECTRIC_PUMP_HV.getStackForm(),
                'H', MetaTileEntities.HULL[3].getStackForm()
        );

        ModHandler.addShapedRecipe(true, "multipart_buffer_hatch", MULTIPART_BUFFER_HATCH.getStackForm(),
                "MhM", "PHP", "McM",
                'M', new UnificationEntry(pipeNormalFluid, StainlessSteel),
                'P', ELECTRIC_MOTOR_HV.getStackForm(),
                'H', MetaTileEntities.HULL[3].getStackForm()
        );
        //  Cryogenic Freezer
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(MetaTileEntities.VACUUM_FREEZER.getStackForm(4))
                .input(CIRCUIT_GOOD_I)
                .input(circuit, MarkerMaterials.Tier.LuV, 16)
                .input(frameGt, HSSG, 16)
                .input(ROBOT_ARM_IV, 8)
                .input(ELECTRIC_PUMP_IV, 8)
                .input(plate, TanmolyiumBetaC, 4)
                .input(plate, Stellite100, 4)
                .input(wireFine, Platinum, 64)
                .input(stickLong, SamariumMagnetic, 64)
                .input(stickLong, SamariumMagnetic, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 6))
                .fluidInputs(AdvancedLubricant.getFluid(3000))
                .fluidInputs(HastelloyN.getFluid(L * 16))
                .fluidInputs(Zylon.getFluid(L * 16))
                .outputs(CRYOGENIC_FREEZER.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .EUt(VA[GTValues.IV])
                        .duration(1200))
                .EUt(VA[GTValues.LuV])
                .duration(1200)
                .buildAndRegister();

        //BLAZING_BLAST_FURNACE
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(ELECTRIC_BLAST_FURNACE.getStackForm(16))
                .input(circuit, MarkerMaterials.Tier.IV, 16)
                .input(frameGt, HSSG, 16)
                .input(ROBOT_ARM_IV, 8)
                .input(VOLTAGE_COIL_IV, 16)
                .input(plate, TanmolyiumBetaC, 4)
                .input(plate, AusteniticStainlessSteel904L, 4)
                .input(ring, Iridium, 4)
                .input(rotor, RhodiumPlatedPalladium, 4)
                .input(stickLong, SamariumMagnetic, 32)
                .input(wireFine, Palladium, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 6))
                .fluidInputs(AdvancedLubricant.getFluid(3000))
                .fluidInputs(HastelloyN.getFluid(L * 16))
                .fluidInputs(Zylon.getFluid(L * 16))
                .outputs(BLAZING_BLAST_FURNACE.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .EUt(VA[GTValues.IV])
                        .duration(1200))
                .EUt(VA[GTValues.IV])
                .duration(1200)
                .buildAndRegister();

        //  Plasma Condenser
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, HSSS)
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(circuit, MarkerMaterials.Tier.UV, 2)
                .input(ELECTRIC_MOTOR_UV, 2)
                .input(ELECTRIC_PUMP_UV, 2)
                .input(SENSOR_UV, 2)
                .input(gear, TungstenCarbide, 4)
                .input(screw, Inconel792, 16)
                .fluidInputs(SolderingAlloy.getFluid(1440))
                .fluidInputs(BlueSteel.getFluid(2880))
                .fluidInputs(CobaltBrass.getFluid(2880))
                .outputs(PLASMA_CONDENSER.getStackForm())
                .EUt(VA[8])
                .duration(600)
                .stationResearch(b -> b
                        .researchStack(MetaTileEntities.VACUUM_FREEZER.getStackForm())
                        .CWUt(30)
                        .EUt(VA[7]))
                .buildAndRegister();

        //炽焰
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(ELECTRIC_FURNACE[4].getStackForm(16))
                .input(CIRCUIT_GOOD_I)
                .input(circuit, MarkerMaterials.Tier.LuV, 16)
                .input(frameGt, HSSG, 16)
                .input(CONVEYOR_MODULE_IV, 8)
                .input(VOLTAGE_COIL_IV, 16)
                .input(plate, TanmolyiumBetaC, 4)
                .input(plate, AusteniticStainlessSteel904L, 4)
                .input(wireFine, Palladium, 64)
                .input(stickLong, SamariumMagnetic, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 6))
                .fluidInputs(AdvancedLubricant.getFluid(3000))
                .fluidInputs(HastelloyN.getFluid(L * 16))
                .fluidInputs(Zylon.getFluid(L * 16))
                .outputs(INDUSTRIAL_INDUCTION_FURNACE.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .EUt(VA[GTValues.IV])
                        .duration(1200))
                .EUt(VA[GTValues.IV])
                .duration(1200)
                .buildAndRegister();
        //  Precise Assembler
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(LARGE_ASSEMBLER.getStackForm(4))
                .input(circuit, MarkerMaterials.Tier.LuV, 16)
                .input(frameGt, MARM200Steel, 16)
                .input(ROBOT_ARM_IV, 8)
                .input(CONVEYOR_MODULE_IV, 8)
                .input(plate, Stellite100, 4)
                .input(gear, TanmolyiumBetaC, 4)
                .input(wireFine, Platinum, 64)
                .input(stickLong, SamariumMagnetic, 64)
                .input(stickLong, SamariumMagnetic, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 6))
                .fluidInputs(AdvancedLubricant.getFluid(3000))
                .fluidInputs(HastelloyN.getFluid(L * 16))
                .fluidInputs(Zylon.getFluid(L * 16))
                .outputs(PRECISE_ASSEMBLER.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .EUt(VA[GTValues.IV])
                        .duration(1200))
                .EUt(VA[GTValues.LuV])
                .duration(1200)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(THREE_DIM_PRINT.getStackForm())
                .inputs(CHEMICAL_PLANT.getStackForm())
                .input(frameGt, TungstenSteel, 4)
                .input(circuit, MarkerMaterials.Tier.EV, 16)
                .input(ROBOT_ARM_EV, 8)
                .input(ELECTRIC_MOTOR_EV, 8)
                .input(CONVEYOR_MODULE_EV, 8)
                .input(screw, Titanium, 16)
                .input(plate, Palladium, 16)
                .input(gear, NanometerBariumTitanate, 16)
                .input(wireFine, Platinum, 32)
                .fluidInputs(SolderingAlloy.getFluid(L * 6))
                .fluidInputs(Lubricant.getFluid(3000))
                .fluidInputs(Zylon.getFluid(L * 2))
                .outputs(NANO_COATING.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_20.getStackForm())
                        .EUt(VA[GTValues.HV])
                        .duration(1200))
                .EUt(VA[GTValues.EV])
                .duration(1200)
                .buildAndRegister();

        //  Advanced Filter Casing
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt, Iridium, 4)
                .inputs(MetaBlocks.CLEANROOM_CASING.getItemVariant(gregtech.common.blocks.BlockCleanroomCasing.CasingType.FILTER_CASING))
                .input(ELECTRIC_MOTOR_LuV)
                .input(rotor, Iridium, 4)
                .input(ITEM_FILTER, 4)
                .input(FLUID_FILTER, 4)
                .input(stickLong, Iridium, 8)
                .fluidInputs(SolderingAlloy.getFluid(L * 2))
                .outputs(GTQTMetaBlocks.blockMultiblockCasing4.getItemVariant(ADVANCED_FILTER_CASING))
                .EUt(VA[6])
                .duration(200)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  ZhuHai Fishing Pond
        ModHandler.addShapedRecipe(true, "zhuhai_fishing_pond", INDUSTRIAL_FISHING_POND.getStackForm(),
                "FRF", "PHP", "WXW",
                'H', FISHER[3].getStackForm(),
                'R', Items.FISHING_ROD,
                'P', ELECTRIC_PUMP_EV,
                'F', FLUID_FILTER,
                'W', new UnificationEntry(cableGtSingle, Aluminium),
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.EV));
        //溶解罐
        ModHandler.addShapedRecipe(true, "dissolution_tank", DISSOLUTION_TANK.getStackForm(),
                "MCM", "PHP", "MKM",
                'M', new UnificationEntry(pipeNormalFluid, Polytetrafluoroethylene),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.HV),
                'P', ELECTRIC_PUMP_HV.getStackForm(),
                'H', QUANTUM_TANK[1].getStackForm(),
                'K', new UnificationEntry(cableGtDouble, Aluminium)
        );
        //  Large Biomass Generator
        ModHandler.addShapedRecipe(true, "large_biomass_generator", LARGE_BIOMASS_GENERATOR.getStackForm(),
                "SAS", "PBP", "WFW",
                'B', BIOMASS_GENERATOR[2].getStackForm(),
                'P', new UnificationEntry(plateDouble, Plutonium),
                'W', new UnificationEntry(cableGtDouble, NiobiumTitanium),
                'F', FIELD_GENERATOR_LuV,
                'S', new UnificationEntry(spring, RTMAlloy),
                'A', FLUID_CELL_LARGE_TUNGSTEN_STEEL);

        ModHandler.addShapedRecipe(true, "primitive_reactor", PRIMITIVE_REACTOR.getStackForm(),
                "PRP", "sQh", "PSP",
                'P', new UnificationEntry(plate, TreatedWood),
                'Q', new UnificationEntry(pipeLargeFluid, TreatedWood),
                'R', new UnificationEntry(rotor, Steel),
                'S', new UnificationEntry(screw, Steel));

        ModHandler.addShapedRecipe(true, "reinforced_treated_wood_wall", GTQTMetaBlocks.blockMultiblockCasing6.getItemVariant(BlockMultiblockCasing6.CasingType.REINFORCED_TREATED_WOOD_WALL),
                "PhP", "QFQ", "PwP",
                'P', new UnificationEntry(plate, TreatedWood),
                'Q', new UnificationEntry(plate, Steel),
                'F', new UnificationEntry(frameGt, TreatedWood));

        ModHandler.addShapedRecipe(true, "reinforced_treated_wood_bottom", GTQTMetaBlocks.blockMultiblockCasing6.getItemVariant(BlockMultiblockCasing6.CasingType.REINFORCED_TREATED_WOOD_BOTTOM),
                "PhP", "QFQ", "PwP",
                'P', new UnificationEntry(plate, TreatedWood),
                'Q', new UnificationEntry(stick, Iron),
                'F', new UnificationEntry(frameGt, Steel));

        ModHandler.addShapedRecipe(true, "heat_furnace",
                GTQTMetaTileEntities.HEAT_FURNACE.getStackForm(),
                "BPB", "PCP", "BPB",
                'C', new UnificationEntry(circuit, LV),
                'P', new UnificationEntry(OrePrefix.plateDouble, Steel),
                'B', FURNACE);

        ModHandler.addShapedRecipe("primitive_roaster", PRIMITIVE_ROASTER.getStackForm(),
                "BhB", "BIB", "BPB",
                'I', new ItemStack(IRON_BARS),
                'B', MetaBlocks.METAL_CASING.getItemVariant(PRIMITIVE_BRICKS),
                'P', OreDictUnifier.get(plate, Iron));

        ModHandler.addShapedRecipe(true, "tree_farmer",
                GTQTMetaTileEntities.PRIMITIVE_TREE_FARMER.getStackForm(),
                "BPB", "PCP", "BPB",
                'C', new UnificationEntry(circuit, ULV),
                'P', new UnificationEntry(plate, Wood),
                'B', new UnificationEntry(frameGt, Iron));

        ModHandler.addShapedRecipe(true, "gravity_separator",
                GTQTMetaTileEntities.GRAVITY_SEPARATOR.getStackForm(),
                "BPB", "PCP", "BPB",
                'C', new UnificationEntry(circuit, MV),
                'P', new UnificationEntry(OrePrefix.plateDouble, Aluminium),
                'B', ELECTRIC_MOTOR_MV);

        ModHandler.addShapedRecipe(true, "seismic_detector",
                GTQTMetaTileEntities.SEISMIC_DETECTOR.getStackForm(),
                "BPB", "PCP", "BPB",
                'C', new UnificationEntry(circuit, MV),
                'P', new UnificationEntry(OrePrefix.plateDouble, Aluminium),
                'B', SENSOR_MV);

        ModHandler.addShapedRecipe(true, "cz_puller",
                GTQTMetaTileEntities.LARGE_CZ_PULLER.getStackForm(),
                "FFF", "CHC", "WCW",
                'F', MetaTileEntities.ELECTRIC_FURNACE[2].getStackForm(),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.MV),
                'H', GTQTMetaBlocks.blockCrucible.getItemVariant(QUARTZ_CRUCIBLE),
                'W', new UnificationEntry(OrePrefix.cableGtSingle, Aluminium));

        ModHandler.addShapedRecipe(true, "distillation_tower", DISTILLATION_TOWER.getStackForm(),
                "CBC", "FMF", "CBC",
                'M', MetaTileEntities.DISTILLATION_TOWER.getStackForm(),
                'B', new UnificationEntry(OrePrefix.pipeLargeFluid, Titanium),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.IV),
                'F', MetaItems.ELECTRIC_PUMP_EV);

        ModHandler.addShapedRecipe(true, "msf", GTQTMetaTileEntities.MSF.getStackForm(),
                "CBC", "FMF", "CBC",
                'M', MetaTileEntities.DISTILLATION_TOWER.getStackForm(),
                'B', new UnificationEntry(OrePrefix.pipeLargeFluid, Aluminium),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.EV),
                'F', MetaItems.ELECTRIC_PUMP_HV);

        ModHandler.addShapedRecipe(true, "evaporation_pool", GTQTMetaTileEntities.EVAPORATION_POOL.getStackForm(),
                "FFF", "CMC", "BBB",
                'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(),
                'B', new UnificationEntry(OrePrefix.pipeLargeFluid, Steel),
                'C', new UnificationEntry(OrePrefix.circuit, LV),
                'F', MetaItems.ELECTRIC_PUMP_LV);

        ModHandler.addShapedRecipe(true, "pyrolysis_tower", GTQTMetaTileEntities.PYROLYSIS_TOWER.getStackForm(),
                "FFF", "CMC", "BBB",
                'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(),
                'B', new UnificationEntry(OrePrefix.pipeLargeFluid, Steel),
                'C', new UnificationEntry(OrePrefix.circuit, LV),
                'F', PRIMITIVE_BLAST_FURNACE.getStackForm());

        ModHandler.addShapedRecipe(true, "distillation_kettle", GTQTMetaTileEntities.DISTILLATION_KETTLE.getStackForm(),
                "BCB", "FMF", "BCB",
                'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(),
                'B', new UnificationEntry(OrePrefix.pipeLargeFluid, Steel),
                'C', new UnificationEntry(OrePrefix.circuit, LV),
                'F', MetaItems.ELECTRIC_PUMP_LV);

        //  Heat Exchanger
        ModHandler.addShapedRecipe(true, "heat_exchanger", SMALL_HEAT_EXCHANGER.getStackForm(),
                "XRX", "PHP", "pSp",
                'H', HULL[GTValues.HV].getStackForm(),
                'R', new UnificationEntry(rotor, Aluminium),
                'P', ELECTRIC_PUMP_EV,
                'X', new UnificationEntry(cableGtQuadruple, Gold),
                'p', new UnificationEntry(plate, StainlessSteel),
                'S', new UnificationEntry(spring, Invar));

        ModHandler.addShapedRecipe(true, "septic_tank", GTQTMetaTileEntities.SEPTIC_TANK.getStackForm(),
                "BFB", "CMC", "BFB",
                'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(),
                'B', new UnificationEntry(OrePrefix.pipeLargeFluid, Steel),
                'C', new UnificationEntry(OrePrefix.circuit, LV),
                'F', MetaItems.ELECTRIC_PUMP_LV);

        /*
        ModHandler.addShapedRecipe(true, "kinetic_energy_battery", GTQTMetaTileEntities.KINETIC_ENERGY_BATTERY.getStackForm(),
                "FFF", "BMB", "CCC", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'B',
                new UnificationEntry(OrePrefix.pipeLargeFluid, Steel), 'C',
                new UnificationEntry(OrePrefix.circuit, LV), 'F', ELECTRIC_MOTOR_LV);

         */
        ModHandler.addShapedRecipe(true, "oil_poll", GTQTMetaTileEntities.OIL_POOL.getStackForm(),
                "FFF", "BMB", "CCC", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'B',
                new UnificationEntry(OrePrefix.pipeLargeFluid, Steel), 'C',
                new UnificationEntry(OrePrefix.circuit, LV), 'F', MetaItems.ELECTRIC_PUMP_LV);

        ModHandler.addShapedRecipe(true, "salt_flied", GTQTMetaTileEntities.SALT_FLIED.getStackForm(),
                "BBB", "CMC", "FFF", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'C',
                new UnificationEntry(OrePrefix.pipeLargeFluid, Steel), 'B',
                new UnificationEntry(OrePrefix.circuit, LV), 'F', MetaItems.ELECTRIC_PUMP_LV);

        //  Dangote Distillery
        ModHandler.addShapedRecipe(true, "dangote_distillery", DANGOTE_DISTILLERY.getStackForm(),
                "SWS", "PHP", "pGp",
                'H', LARGE_DISTILLERY.getStackForm(),
                'P', ELECTRIC_PUMP_ZPM,
                'p', new UnificationEntry(plate, HG1223),
                'G', new UnificationEntry(gear, Osmiridium),
                'S', new UnificationEntry(spring, MolybdenumDisilicide),
                'W', new UnificationEntry(cableGtSingle, NiobiumTitanium));

        ModHandler.addShapedRecipe(true, "mining_drill", GTQTMetaTileEntities.MINING_DRILL.getStackForm(),
                "FFF", "BMB", "CCC",
                'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(),
                'B', new UnificationEntry(gear, Steel),
                'C', new UnificationEntry(OrePrefix.circuit, LV),
                'F', ELECTRIC_MOTOR_LV);

        ModHandler.addShapedRecipe(true, "ele_oil", GTQTMetaTileEntities.ELE_OIL.getStackForm(),
                "FFF", "BMB", "CCC",
                'M', MetaTileEntities.HULL[GTValues.HV].getStackForm(),
                'B', new UnificationEntry(gear, Titanium),
                'C', new UnificationEntry(OrePrefix.circuit, HV),
                'F', EMITTER_HV);

        ModHandler.addShapedRecipe(true, "fluid_heat_exchange", GTQTMetaTileEntities.HEAT_HATCH_EXCHANGE.getStackForm(),
                "BCB", "FMF", "BCB",
                'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(),
                'B', VOLTAGE_COIL_LV,
                'C', new UnificationEntry(OrePrefix.circuit, LV),
                'F', ELECTRIC_PUMP_LV);

        ModHandler.addShapedRecipe(true, "clarifier", GTQTMetaTileEntities.CLARIFIER.getStackForm(),
                "FFF", "BMB", "CCC",
                'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(),
                'B', new UnificationEntry(plateDouble, Steel),
                'C', new UnificationEntry(OrePrefix.circuit, LV),
                'F', ELECTRIC_PUMP_LV);

        ModHandler.addShapedRecipe(true, "ocean_pumper", GTQTMetaTileEntities.OCEAN_PUMPER.getStackForm(),
                "FFF", "CMC", "FFF",
                'M', MetaTileEntities.HULL[GTValues.MV].getStackForm(),
                'C', new UnificationEntry(OrePrefix.circuit, MV),
                'F', ELECTRIC_PUMP_MV);

        ModHandler.addShapedRecipe(true, "cracking_unit", GTQTMetaTileEntities.CRACKER.getStackForm(),
                "CEC", "PHP", "CEC",
                'C', MetaBlocks.WIRE_COIL.getItemVariant(NICHROME),
                'E', MetaItems.ELECTRIC_PUMP_EV,
                'P', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.IV),
                'H', MetaTileEntities.CRACKER.getStackForm());

        ModHandler.addShapedRecipe(true, "pyrolyse_oven", PYROLYSE_OVEN.getStackForm(),
                "WEP", "EME", "WCP",
                'M', MetaTileEntities.PYROLYSE_OVEN.getStackForm(),
                'W', MetaItems.ELECTRIC_PISTON_HV,
                'P', new UnificationEntry(OrePrefix.wireGtQuadruple, Gold),
                'E', new UnificationEntry(OrePrefix.circuit, HV),
                'C', MetaItems.ELECTRIC_PUMP_HV);

        ModHandler.addShapedRecipe(true, "vacuum_freezer", GTQTMetaTileEntities.VACUUM_FREEZER.getStackForm(),
                "PPP", "CMC", "WCW",
                'M', MetaTileEntities.VACUUM_FREEZER.getStackForm(),
                'P', MetaItems.ELECTRIC_PUMP_EV,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.EV),
                'W', new UnificationEntry(OrePrefix.cableGtSingle, Aluminium));

        ModHandler.addShapedRecipe(true, "large_ore_washer", GTQTMetaTileEntities.LARGE_ORE_WASHER.getStackForm(),
                "PPP", "CMC", "WCW",
                'M', ORE_WASHER[2].getStackForm(),
                'P', ROBOT_ARM_MV,
                'C', new UnificationEntry(OrePrefix.circuit, MV),
                'W', ELECTRIC_MOTOR_MV);

        ModHandler.addShapedRecipe(true, "large_thermal_centrifuge", GTQTMetaTileEntities.LARGE_THERMAL_CENTRIFUGE.getStackForm(),
                "WPW", "CMC", "WPW",
                'M', THERMAL_CENTRIFUGE[2].getStackForm(),
                'P', MetaItems.ELECTRIC_PUMP_MV,
                'C', new UnificationEntry(OrePrefix.circuit, MV),
                'W', VOLTAGE_COIL_MV);

        ModHandler.addShapedRecipe(true, "large_grind", GTQTMetaTileEntities.LARGE_GRIND.getStackForm(),
                "WPW", "CMC", "WPW",
                'M', MACERATOR[2].getStackForm(),
                'P', MetaItems.ELECTRIC_PUMP_MV, 'C', new UnificationEntry(OrePrefix.circuit, MV),
                'W', ELECTRIC_PISTON_MV);

        ModHandler.addShapedRecipe(true, "large_forging", LARGE_FORGING.getStackForm(),
                "WPW", "CMC", "WPW",
                'M', FORGE_HAMMER[2].getStackForm(),
                'P', MetaItems.ELECTRIC_PUMP_MV, 'C', new UnificationEntry(OrePrefix.circuit, MV),
                'W', ELECTRIC_MOTOR_MV);

        ModHandler.addShapedRecipe(true, "large_extractor", LARGE_EXTRACTOR.getStackForm(),
                "WPW", "CMC", "WPW",
                'M', EXTRACTOR[2].getStackForm(),
                'P', MetaItems.ELECTRIC_PUMP_MV, 'C', new UnificationEntry(OrePrefix.circuit, MV),
                'W', ELECTRIC_MOTOR_MV);

        ModHandler.addShapedRecipe(true, "large_mixer", LARGE_MIXER.getStackForm(),
                "WPW", "CMC", "WPW",
                'M', MIXER[2].getStackForm(),
                'P', ELECTRIC_PISTON_MV,
                'C', new UnificationEntry(OrePrefix.circuit, MV),
                'W', ELECTRIC_MOTOR_MV);

        ModHandler.addShapedRecipe(true, "assembler_line", GTQTMetaTileEntities.ASSEMBLY_LINE.getStackForm(),
                "PPP", "CMC", "WCW",
                'M', ASSEMBLER[3].getStackForm(),
                'P', EMITTER_HV,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.HV),
                'W', new UnificationEntry(OrePrefix.cableGtSingle, Gold));

        ModHandler.addShapedRecipe(true, "large_chemical_reactor",
                GTQTMetaTileEntities.LARGE_CHEMICAL_REACTOR.getStackForm(),
                "CRC", "PMP", "CHC",
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.EV),
                'R', OreDictUnifier.get(OrePrefix.rotor, StainlessSteel),
                'P', OreDictUnifier.get(OrePrefix.pipeLargeFluid, Materials.Polyethylene),
                'M', CHEMICAL_REACTOR[3].getStackForm(),
                'H', MetaTileEntities.HULL[GTValues.HV].getStackForm());

        ModHandler.addShapedRecipe(true, "desulphurization_recipes",
                GTQTMetaTileEntities.LARGE_DESULPHURIZATION.getStackForm(),
                "CRC", "PMP", "CHC",
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.MV),
                'R', OreDictUnifier.get(OrePrefix.rotor, Aluminium),
                'P', OreDictUnifier.get(OrePrefix.pipeLargeFluid, Materials.Steel),
                'M', CHEMICAL_REACTOR[2].getStackForm(),
                'H', MetaTileEntities.HULL[GTValues.MV].getStackForm());

        ModHandler.addShapedRecipe(true, "fluidized_bed_recipes",
                GTQTMetaTileEntities.LARGE_FLUIDIZED_BED.getStackForm(),
                "CRC", "PMP", "CHC",
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.HV),
                'R', OreDictUnifier.get(OrePrefix.rotor, StainlessSteel),
                'P', OreDictUnifier.get(OrePrefix.pipeLargeFluid, Aluminium),
                'M', CHEMICAL_REACTOR[3].getStackForm(),
                'H', MetaTileEntities.HULL[GTValues.HV].getStackForm());

        ModHandler.addShapedRecipe(true, "large_wiremill",
                GTQTMetaTileEntities.LARGE_WIREMILL.getStackForm(),
                "CRC", "PMP", "CHC",
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.HV),
                'R', OreDictUnifier.get(stick, StainlessSteel),
                'P', OreDictUnifier.get(frameGt, Aluminium),
                'M', WIREMILL[3].getStackForm(),
                'H', MetaTileEntities.HULL[GTValues.HV].getStackForm());

        ModHandler.addShapedRecipe(true, "large_extruder",
                GTQTMetaTileEntities.LARGE_EXTRUDER.getStackForm(),
                "CRC", "PMP", "CHC",
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.HV),
                'R', OreDictUnifier.get(plate, StainlessSteel),
                'P', OreDictUnifier.get(screw, Aluminium),
                'M', EXTRUDER[3].getStackForm(),
                'H', MetaTileEntities.HULL[GTValues.HV].getStackForm());

        ModHandler.addShapedRecipe(true, "large_cutter",
                GTQTMetaTileEntities.LARGE_CUTTER.getStackForm(),
                "CRC", "PMP", "CHC",
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.HV),
                'R', OreDictUnifier.get(frameGt, StainlessSteel),
                'P', OreDictUnifier.get(plate, Aluminium),
                'M', CUTTER[3].getStackForm(),
                'H', MetaTileEntities.HULL[GTValues.HV].getStackForm());

        ModHandler.addShapedRecipe(true, "large_bender",
                LARGE_BENDER.getStackForm(),
                "CRC", "PMP", "CHC",
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.HV),
                'R', OreDictUnifier.get(plate, StainlessSteel),
                'P', OreDictUnifier.get(screw, Aluminium),
                'M', BENDER[3].getStackForm(),
                'H', MetaTileEntities.HULL[GTValues.HV].getStackForm());

        ModHandler.addShapedRecipe(true, "blazing_cz_puller", BLAZING_CZ_PULLER.getStackForm(),
                "GXG", "RHR", "PWP",
                'G', new UnificationEntry(gear, HSSS),
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'H', LARGE_CZ_PULLER.getStackForm(),
                'R', ROBOT_ARM_IV.getStackForm(),
                'P', new UnificationEntry(plate, AusteniticStainlessSteel904L),
                'W', VOLTAGE_COIL_IV.getStackForm());

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Electrotine)
                .input(Items.SNOWBALL)
                .fluidInputs(Ice.getFluid(2000))
                .fluidOutputs(GelidCryotheum.getFluid(4000))
                .EUt(120)
                .duration(120)
                .buildAndRegister();

        //  Blazing Pyrotheum
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Redstone)
                .input(dust, Sulfur)
                .fluidInputs(Blaze.getFluid(L * 2))
                .fluidOutputs(Pyrotheum.getFluid(4000))
                .EUt(120)
                .duration(120)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Steel, 16)
                .input(stick, Steel, 2)
                .input(ring, Steel, 4)
                .input(LAPOTRON_CRYSTAL, 1)
                .input(circuit, MarkerMaterials.Tier.EV, 1)
                .input(SENSOR_HV, 1)
                .output(POS_BINDING_CARD)
                .circuitMeta(2)
                .duration(600).EUt(VA[3]).buildAndRegister();

        ModHandler.addShapedRecipe(true, "industrial_drill", INDUSTRIAL_DRILL.getStackForm(),
                "PKP", "CHC", "MMM",
                'P', ELECTRIC_PISTON_UV.getStackForm(),
                'K', new UnificationEntry(cableGtQuadruple, YttriumBariumCuprate),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UHV),
                'H', MetaTileEntities.HULL[7].getStackForm(),
                'M', ELECTRIC_MOTOR_UV.getStackForm()
        );

        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, MICROWAVE_ENERGY_RECEIVER,
                "PCP", "EHE", "PCP",
                'E', CraftingComponent.EMITTER,
                'H', CraftingComponent.HULL,
                'P', CraftingComponent.SENSOR,
                'C', CraftingComponent.CIRCUIT);

        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, ULTRAVIOLET_LAMP_CHAMBER,
                "GEG", "PHP", "WXW",
                'E', CraftingComponent.EMITTER,
                'H', CraftingComponent.HULL,
                'P', CraftingComponent.PLATE,
                'G', CraftingComponent.GLASS,
                'W', CraftingComponent.CABLE,
                'X', CraftingComponent.CIRCUIT);

        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, VACUUM_CHAMBER,
                "GCG", "PHP", "GWG",
                'W', CraftingComponent.CABLE,
                'C', CraftingComponent.CIRCUIT,
                'P', CraftingComponent.PUMP,
                'G', CraftingComponent.GLASS,
                'H', CraftingComponent.HULL);

        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, DECAY_CHAMBER,
                "CFC", "RHR", "WFW",
                'H', CraftingComponent.HULL,
                'R', CraftingComponent.DOUBLE_PLATE,
                'F', CraftingComponent.FIELD_GENERATOR,
                'C', CraftingComponent.CIRCUIT,
                'W', CraftingComponent.CABLE);


        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, COMPONENT_ASSEMBLER,
                "PPP", "CHR", "WXW",
                'P', CraftingComponent.PLATE,
                'H', CraftingComponent.HULL,
                'C', CraftingComponent.CONVEYOR,
                'R', CraftingComponent.ROBOT_ARM,
                'W', CraftingComponent.CABLE,
                'X', CraftingComponent.CIRCUIT);

        ModHandler.addShapedRecipe(true, "particle_accelerator_iv", PARTICLE_ACCELERATOR_IO[0].getStackForm(),
                "PPP", "CFC", "EME", 'M', HULL[GTValues.IV].getStackForm(),
                'P', SENSOR_IV,
                'E', ULTRAVIOLET_LAMP_CHAMBER[GTValues.IV].getStackForm(),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.IV),
                'F', MetaItems.ELECTRIC_PUMP_IV);

        ModHandler.addShapedRecipe(true, "particle_accelerator_luv", GTQTMetaTileEntities.PARTICLE_ACCELERATOR_IO[1].getStackForm(),
                "PPP", "CFC", "EME", 'M', HULL[GTValues.LuV].getStackForm(),
                'P', SENSOR_LuV,
                'E', ULTRAVIOLET_LAMP_CHAMBER[GTValues.LuV].getStackForm(),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.LuV),
                'F', MetaItems.ELECTRIC_PUMP_LuV);

        ModHandler.addShapedRecipe(true, "particle_accelerator_zpm", GTQTMetaTileEntities.PARTICLE_ACCELERATOR_IO[2].getStackForm(),
                "PPP", "CFC", "EME", 'M', HULL[GTValues.ZPM].getStackForm(),
                'P', SENSOR_ZPM,
                'E', ULTRAVIOLET_LAMP_CHAMBER[GTValues.ZPM].getStackForm(),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.ZPM),
                'F', MetaItems.ELECTRIC_PUMP_ZPM);

        ModHandler.addShapedRecipe(true, "particle_accelerator_uv", GTQTMetaTileEntities.PARTICLE_ACCELERATOR_IO[3].getStackForm(),
                "PPP", "CFC", "EME", 'M', HULL[GTValues.UV].getStackForm(),
                'P', SENSOR_UV,
                'E', ULTRAVIOLET_LAMP_CHAMBER[GTValues.UV].getStackForm(),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.UV),
                'F', MetaItems.ELECTRIC_PUMP_UV);

        //  Naquadah Reactor
        ModHandler.addShapedRecipe(true, "naquadah_reactor_iv", GTQTMetaTileEntities.NAQUADAH_REACTOR[0].getStackForm(),
                "PPP", "CFC", "EME",
                'M', HULL[GTValues.IV].getStackForm(),
                'P', FIELD_GENERATOR_IV,
                'E', MetaTileEntities.CHEMICAL_REACTOR[GTValues.IV].getStackForm(),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.IV),
                'F', MetaItems.ELECTRIC_PUMP_IV);

        ModHandler.addShapedRecipe(true, "naquadah_reactor_luv", GTQTMetaTileEntities.NAQUADAH_REACTOR[1].getStackForm(),
                "PPP", "CFC", "EME",
                'M', HULL[GTValues.LuV].getStackForm(),
                'P', FIELD_GENERATOR_LuV,
                'E', MetaTileEntities.CHEMICAL_REACTOR[GTValues.LuV].getStackForm(),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.LuV),
                'F', MetaItems.ELECTRIC_PUMP_LuV);

        ModHandler.addShapedRecipe(true, "naquadah_reactor_zpm", GTQTMetaTileEntities.NAQUADAH_REACTOR[2].getStackForm(),
                "PPP", "CFC", "EME",
                'M', HULL[GTValues.ZPM].getStackForm(),
                'P', FIELD_GENERATOR_ZPM,
                'E', MetaTileEntities.CHEMICAL_REACTOR[GTValues.ZPM].getStackForm(),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.ZPM),
                'F', MetaItems.ELECTRIC_PUMP_ZPM);

        ModHandler.addShapedRecipe(true, "fuel_cell.lv", GTQTMetaTileEntities.FUEL_CELL_TURBINE[0].getStackForm(),
                "PCP", "AMB", "ECE",
                'M', HULL[GTValues.LV].getStackForm(),
                'A', MetaTileEntities.COMBUSTION_GENERATOR[0].getStackForm(),
                'B', MetaTileEntities.GAS_TURBINE[0].getStackForm(),
                'P', ELECTRIC_MOTOR_LV,
                'E', ELECTRIC_PISTON_LV,
                'C', new UnificationEntry(OrePrefix.circuit, LV));

        ModHandler.addShapedRecipe(true, "fuel_cell.mv", GTQTMetaTileEntities.FUEL_CELL_TURBINE[1].getStackForm(),
                "PCP", "AMB", "ECE",
                'M', HULL[GTValues.MV].getStackForm(),
                'A', MetaTileEntities.COMBUSTION_GENERATOR[1].getStackForm(),
                'B', MetaTileEntities.GAS_TURBINE[1].getStackForm(),
                'P', ELECTRIC_MOTOR_MV,
                'E', ELECTRIC_PISTON_MV,
                'C', new UnificationEntry(OrePrefix.circuit, MV));

        ModHandler.addShapedRecipe(true, "fuel_cell.hv", GTQTMetaTileEntities.FUEL_CELL_TURBINE[2].getStackForm(),
                "PCP", "AMB", "ECE",
                'M', HULL[GTValues.HV].getStackForm(),
                'A', MetaTileEntities.COMBUSTION_GENERATOR[2].getStackForm(),
                'B', MetaTileEntities.GAS_TURBINE[2].getStackForm(),
                'P', ELECTRIC_MOTOR_HV,
                'E', ELECTRIC_PISTON_HV,
                'C', new UnificationEntry(OrePrefix.circuit, HV));

        ModHandler.addShapedRecipe(true, "fuel_cell.ev", GTQTMetaTileEntities.FUEL_CELL_TURBINE[3].getStackForm(),
                "PCP", "AMB", "ECE",
                'M', HULL[GTValues.EV].getStackForm(),
                'A', COMBUSTION_GENERATOR[3].getStackForm(),
                'B', GAS_TURBINE[3].getStackForm(),
                'P', ELECTRIC_MOTOR_EV,
                'E', ELECTRIC_PISTON_EV,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.EV));

        ModHandler.addShapedRecipe(true, "fuel_cell.iv", GTQTMetaTileEntities.FUEL_CELL_TURBINE[4].getStackForm(),
                "PCP", "AMB", "ECE",
                'M', HULL[GTValues.IV].getStackForm(),
                'A', COMBUSTION_GENERATOR[4].getStackForm(),
                'B', GAS_TURBINE[4].getStackForm(),
                'P', ELECTRIC_MOTOR_IV,
                'E', ELECTRIC_PISTON_IV,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.IV));


        ModHandler.addShapedRecipe(true, "rtg.hv", SCMetaTileEntities.RTG[0].getStackForm(),
                "PCP", "AMA", "ECE",
                'M', HULL[GTValues.HV].getStackForm(),
                'A', new UnificationEntry(OrePrefix.spring, Gold),
                'P', ELECTRIC_MOTOR_HV,
                'E', SENSOR_HV,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.HV));

        ModHandler.addShapedRecipe(true, "rtg.ev", SCMetaTileEntities.RTG[1].getStackForm(),
                "PCP", "AMA", "ECE",
                'M', HULL[GTValues.EV].getStackForm(),
                'A', new UnificationEntry(OrePrefix.spring, TungstenSteel),
                'P', ELECTRIC_MOTOR_EV,
                'E', SENSOR_EV,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.EV));

        ModHandler.addShapedRecipe(true, "rtg.iv", SCMetaTileEntities.RTG[2].getStackForm(),
                "PCP", "AMA", "ECE",
                'M', HULL[GTValues.IV].getStackForm(),
                'A', new UnificationEntry(OrePrefix.spring, RhodiumPlatedPalladium),
                'P', ELECTRIC_MOTOR_IV,
                'E', SENSOR_IV,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.IV));

        ModHandler.addShapedRecipe(true, "rtg.luv", SCMetaTileEntities.RTG[3].getStackForm(),
                "PCP", "AMA", "ECE",
                'M', HULL[GTValues.LuV].getStackForm(),
                'A', new UnificationEntry(OrePrefix.spring, NaquadahAlloy),
                'P', ELECTRIC_MOTOR_LuV,
                'E', SENSOR_LuV,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.LuV));

        ModHandler.addShapedRecipe(true, "rtg.zpm", SCMetaTileEntities.RTG[4].getStackForm(),
                "PCP", "AMA", "ECE",
                'M', HULL[GTValues.ZPM].getStackForm(),
                'A', new UnificationEntry(OrePrefix.spring, Iridium),
                'P', ELECTRIC_MOTOR_ZPM,
                'E', SENSOR_ZPM,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.ZPM));

        ModHandler.addShapedRecipe(true, "lightning_rod.iv", GTQTMetaTileEntities.LIGHTNING_ROD[0].getStackForm(),
                "AMA", "MCM", "AMA",
                'M', HULL[GTValues.IV].getStackForm(),
                'A', ENERGY_LAPOTRONIC_ORB,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.IV));

        ModHandler.addShapedRecipe(true, "lightning_rod.luv", GTQTMetaTileEntities.LIGHTNING_ROD[1].getStackForm(),
                "AMA", "MCM", "AMA",
                'M', HULL[GTValues.LuV].getStackForm(),
                'A', ENERGY_LAPOTRONIC_ORB_CLUSTER,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.LuV));

        ModHandler.addShapedRecipe(true, "lightning_rod.zpm", GTQTMetaTileEntities.LIGHTNING_ROD[2].getStackForm(),
                "AMA", "MCM", "AMA",
                'M', HULL[GTValues.ZPM].getStackForm(),
                'A', ENERGY_MODULE,
                'C', new UnificationEntry(OrePrefix.circuit, ZPM));

        ModHandler.addShapedRecipe("one_stack_filter", COVER_ONE_STACK_FILTER.getStackForm(),
                "XXX", "XYX", "XXX",
                'X', new UnificationEntry(OrePrefix.foil, Materials.Zinc),
                'Y', new UnificationEntry(OrePrefix.plate, Materials.Iron));

        registerMachineRecipe(GTQTMetaTileEntities.LATEX_COLLECTOR,
                "PCP", "AMA", "PCP",
                'M', CraftingComponent.HULL,
                'A', PIPE_NORMAL,
                'C', GLASS,
                'P', CraftingComponent.PUMP);

        ModHandler.addShapedRecipe(true, "latex_collector.bronze",
                STEAM_LATEX_COLLECTOR[0].getStackForm(), "XCX", "PMP", "XCX",
                'M', STEAM_EXTRACTOR_BRONZE.getStackForm(),
                'C', new UnificationEntry(OrePrefix.pipeSmallFluid, Bronze),
                'X', new UnificationEntry(OrePrefix.plate, Rubber),
                'P', new UnificationEntry(gear, Bronze));

        ModHandler.addShapedRecipe(true, "latex_collector.steel",
                STEAM_LATEX_COLLECTOR[1].getStackForm(), "XCX", "PMP", "XCX",
                'M', STEAM_EXTRACTOR_STEEL.getStackForm(),
                'C', new UnificationEntry(OrePrefix.pipeSmallFluid, Steel),
                'X', new UnificationEntry(OrePrefix.plate, Rubber),
                'P', new UnificationEntry(gear, Steel));

        //  Biomass Generator
        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, BIOMASS_GENERATOR,
                "SOS", "IHP", "WTW",
                'H', CraftingComponent.HULL,
                'I', CraftingComponent.PISTON,
                'P', CraftingComponent.PUMP,
                'S', CraftingComponent.SPRING,
                'T', CraftingComponent.SAWBLADE,
                'W', CraftingComponent.CABLE,
                'O', CraftingComponent.DOUBLE_PLATE);

        //  Biomass Generator
        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, ACID_GENERATOR,
                "SOS", "IHP", "WTW",
                'H', CraftingComponent.HULL,
                'I', CraftingComponent.PUMP,
                'P', CraftingComponent.GLASS,
                'S', CraftingComponent.SPRING,
                'T', CraftingComponent.SAWBLADE,
                'W', CraftingComponent.CABLE,
                'O', CraftingComponent.DOUBLE_PLATE);

        //小等离子
        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, PLASMA_GENERATOR,
                "TPT", "IHI", "SPS",
                'H', CraftingComponent.HULL,
                'I', CraftingComponent.FIELD_GENERATOR,
                'P', CraftingComponent.CIRCUIT,
                'S', CraftingComponent.CABLE_HEX,
                'T', CraftingComponent.STICK_DISTILLATION);

        //热源仓
        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, HEAT_HATCH,
                "TPT", "PHP", "TPT",
                'H', CraftingComponent.HULL,
                'P', CraftingComponent.VOLTAGE_COIL,
                'T', CraftingComponent.SPRING);

        //电加热器
        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, ELECTRIC_HEATER,
                "TPT", "PHP", "TPT",
                'H', CraftingComponent.HULL,
                'P', CraftingComponent.VOLTAGE_COIL,
                'T', CraftingComponent.CABLE_HEX);

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(ENERGY_INPUT_HATCH_16A[LuV - 5], 4)
                .input(LASER_INPUT_HATCH_256[LuV - 5], 1)
                .input(circuit, MarkerMaterials.Tier.LuV, 8)
                .outputs(LASER_BOOSTER[0].getStackForm())
                .EUt(VA[GTValues.LuV])
                .duration(300)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(ENERGY_INPUT_HATCH_16A[GTValues.ZPM - 5], 4)
                .input(LASER_INPUT_HATCH_1024[GTValues.ZPM - 5], 1)
                .input(circuit, MarkerMaterials.Tier.ZPM, 8)
                .outputs(LASER_BOOSTER[1].getStackForm())
                .EUt(VA[GTValues.ZPM])
                .duration(300)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(ENERGY_INPUT_HATCH_16A[GTValues.UV - 5], 4)
                .input(LASER_INPUT_HATCH_1024[GTValues.UV - 5], 1)
                .input(circuit, MarkerMaterials.Tier.UV, 8)
                .outputs(LASER_BOOSTER[2].getStackForm())
                .EUt(VA[UV])
                .duration(300)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, MaterialHelper.Plate[1], 4)
                .input(pipeNormalFluid, MaterialHelper.Pipe[1], 1)
                .input(stick, MaterialHelper.Wire[1], 2)
                .output(HEAT_SHIELD_MKI)
                .circuitMeta(6)
                .EUt(VA[GTValues.LV])
                .duration(300)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, MaterialHelper.Plate[2], 4)
                .input(pipeNormalFluid, MaterialHelper.Pipe[2], 1)
                .input(stick, MaterialHelper.Wire[2], 2)
                .output(HEAT_SHIELD_MKII)
                .circuitMeta(6)
                .EUt(VA[GTValues.MV])
                .duration(300)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, MaterialHelper.Plate[3], 4)
                .input(pipeNormalFluid, MaterialHelper.Pipe[3], 1)
                .input(stick, MaterialHelper.Wire[3], 2)
                .output(HEAT_SHIELD_MKIII)
                .circuitMeta(6)
                .EUt(VA[GTValues.HV])
                .duration(300)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, MaterialHelper.Plate[4], 4)
                .input(pipeNormalFluid, MaterialHelper.Pipe[4], 1)
                .input(stick, MaterialHelper.Wire[4], 2)
                .output(HEAT_SHIELD_MKIV)
                .circuitMeta(6)
                .EUt(VA[GTValues.EV])
                .duration(300)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, MaterialHelper.Plate[5], 4)
                .input(pipeNormalFluid, MaterialHelper.Pipe[5], 1)
                .input(stick, MaterialHelper.Wire[5], 2)
                .output(HEAT_SHIELD_MKV)
                .circuitMeta(6)
                .EUt(VA[GTValues.IV])
                .duration(300)
                .buildAndRegister();

        // Steam Pressure Machines
        ModHandler.addShapedRecipe(true, "steam_ejector.bronze", STEAM_EJECTOR[0].getStackForm(),
                "MGM", "LHL", "MEM",
                'M', new UnificationEntry(pipeNormalFluid, TinAlloy),
                'G', new UnificationEntry(block, Glass),
                'L', new UnificationEntry(pipeLargeFluid, TinAlloy),
                'H', MetaBlocks.STEAM_CASING.getItemVariant(BlockSteamCasing.SteamCasingType.STEEL_HULL),
                'E', new UnificationEntry(gear, Iron));

        ModHandler.addShapedRecipe(true, "steam_ejector.steel", STEAM_EJECTOR[1].getStackForm(),
                "MGM", "LHL", "MEM",
                'M', new UnificationEntry(pipeNormalFluid, TinAlloy),
                'G', new UnificationEntry(block, Glass),
                'L', new UnificationEntry(pipeLargeFluid, TinAlloy),
                'H', MetaBlocks.STEAM_CASING.getItemVariant(BlockSteamCasing.SteamCasingType.STEEL_HULL),
                'E', new UnificationEntry(gear, Steel));

        ModHandler.addShapedRecipe(true, "subsonic_axial_compressor", SUBSONIC_AXIAL_COMPRESSOR.getStackForm(),
                "PMP", "MHM", "KCK",
                'P', new UnificationEntry(pipeNormalFluid, StainlessSteel),
                'M', ELECTRIC_MOTOR_EV.getStackForm(),
                'H', MetaTileEntities.HULL[3].getStackForm(),
                'K', new UnificationEntry(cableGtSingle, BlackSteel),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.LuV)
        );

        ModHandler.addShapedRecipe(true, "supersonic_axial_compressor", SUPERSONIC_AXIAL_COMPRESSOR.getStackForm(),
                "PMP", "MHM", "KCK",
                'P', new UnificationEntry(pipeNormalFluid, NiobiumTitanium),
                'M', ELECTRIC_MOTOR_IV.getStackForm(),
                'H', MetaTileEntities.HULL[4].getStackForm(),
                'K', new UnificationEntry(cableGtSingle, Osmium),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.ZPM)
        );

        ModHandler.addShapedRecipe(true, "low_power_turbomolecular_pump", LOW_POWER_TURBOMOLECULAR_PUMP.getStackForm(),
                "PMP", "MHM", "KCK",
                'P', new UnificationEntry(pipeNormalFluid, Polytetrafluoroethylene),
                'M', ELECTRIC_MOTOR_EV.getStackForm(),
                'H', MetaTileEntities.HULL[3].getStackForm(),
                'K', new UnificationEntry(cableGtSingle, BlackSteel),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.LuV)
        );

        ModHandler.addShapedRecipe(true, "high_power_turbomolecular_pump", HIGH_POWER_TURBOMOLECULAR_PUMP.getStackForm(),
                "PMP", "MHM", "KCK",
                'P', new UnificationEntry(pipeNormalFluid, Polybenzimidazole),
                'M', ELECTRIC_MOTOR_IV.getStackForm(),
                'H', MetaTileEntities.HULL[4].getStackForm(),
                'K', new UnificationEntry(cableGtSingle, Osmium),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.ZPM)
        );

        registerMachineRecipe(true, PRESSURE_HATCH,
                "TPT", "PHP", "TPT",
                'H', CraftingComponent.HULL,
                'P', GLASS,
                'T', CraftingComponent.PLATE);

        registerMachineRecipe(true, PRESSURE_COMPRESSOR,
                "TPT", "CHC", "TPT",
                'H', CraftingComponent.HULL,
                'C', CraftingComponent.CIRCUIT,
                'P', CraftingComponent.MOTOR,
                'T', CraftingComponent.ROTOR);

        registerMachineRecipe(true, PRESSURE_PUMP,
                "TPT", "CHC", "TPT",
                'H', CraftingComponent.HULL,
                'C', CraftingComponent.CIRCUIT,
                'T', CraftingComponent.MOTOR,
                'P', CraftingComponent.ROTOR);

        for (int i = 0; i < GAS_TANK.length; i++) {
            ModHandler.addShapedRecipe(true, "gas_tank" + i, GAS_TANK[i].getStackForm(),
                    "PMP", "MHM", "PMP",
                    'P', new UnificationEntry(pipeNormalFluid, MaterialHelper.Pipe[i]),
                    'H', HULL[i].getStackForm(),
                    'M', new UnificationEntry(screw, MaterialHelper.Plate[i])
            );

        }
        ModHandler.addShapedRecipe(true, "pressure_tank.i", PRESSURE_TANK[0].getStackForm(),
                "PMP", "MHM", "PMP",
                'P', new UnificationEntry(pipeLargeFluid, MaterialHelper.Pipe[1]),
                'H', STEEL_DRUM.getStackForm(),
                'M', new UnificationEntry(screw, MaterialHelper.Plate[1])
        );
        ModHandler.addShapedRecipe(true, "pressure_tank.ii", PRESSURE_TANK[1].getStackForm(),
                "PMP", "MHM", "PMP",
                'P', new UnificationEntry(pipeLargeFluid, MaterialHelper.Pipe[3]),
                'H', STAINLESS_STEEL_DRUM.getStackForm(),
                'M', new UnificationEntry(screw, MaterialHelper.Plate[3])
        );
        ModHandler.addShapedRecipe(true, "pressure_tank.iii", PRESSURE_TANK[2].getStackForm(),
                "PMP", "MHM", "PMP",
                'P', new UnificationEntry(pipeLargeFluid, MaterialHelper.Pipe[5]),
                'H', TUNGSTENSTEEL_DRUM.getStackForm(),
                'M', new UnificationEntry(screw, MaterialHelper.Plate[5])
        );


        // Inventory Bridge
        ModHandler.addShapedRecipe(true, "inventory_bridge", INV_BRIDGE.getStackForm(),
                "hP ", " H ", " Pw",
                'H', HULL[1].getStackForm(),
                'P', new UnificationEntry(pipeNormalItem, Nickel));

        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input(HULL[1])
                .input(pipeNormalItem, Nickel, 2)
                .output(INV_BRIDGE)
                .EUt(VA[1])
                .duration(10 * SECOND)
                .buildAndRegister();

        // Tank Bridge
        ModHandler.addShapedRecipe(true, "tank_bridge", TANK_BRIDGE.getStackForm(),
                "h  ", "PHP", "  w",
                'H', HULL[1].getStackForm(),
                'P', new UnificationEntry(pipeNormalFluid, Steel));

        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input(HULL[1])
                .input(pipeNormalFluid, Steel, 2)
                .output(TANK_BRIDGE)
                .EUt(VA[1])
                .duration(10 * SECOND)
                .buildAndRegister();

        // Inventory Tank Bridge
        ModHandler.addShapedRecipe(true, "inventory_tank_bridge", INV_TANK_BRIDGE.getStackForm(),
                "hP ", "QHQ", " Pw",
                'H', HULL[1].getStackForm(),
                'P', new UnificationEntry(pipeNormalItem, Nickel),
                'Q', new UnificationEntry(pipeNormalFluid, Steel));

        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(5)
                .input(HULL[1])
                .input(pipeNormalFluid, Steel, 2)
                .input(pipeNormalItem, Nickel, 2)
                .output(INV_TANK_BRIDGE)
                .EUt(VA[1])
                .duration(10 * SECOND)
                .buildAndRegister();

        // Universal Bridge
        ModHandler.addShapedRecipe(true, "universal_bridge", UNIVERSAL_BRIDGE.getStackForm(),
                "SPR", "QHQ", "XPG",
                'H', HULL[2].getStackForm(),
                'P', new UnificationEntry(pipeNormalItem, Electrum),
                'Q', new UnificationEntry(pipeNormalFluid, Aluminium),
                'S', new UnificationEntry(spring, Aluminium),
                'R', new UnificationEntry(rotor, Aluminium),
                'G', new UnificationEntry(gear, Aluminium),
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.LV));

        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(5)
                .input(HULL[2])
                .input(circuit, MarkerMaterials.Tier.LV)
                .input(rotor, Aluminium)
                .input(gear, Aluminium)
                .input(spring, Aluminium)
                .input(pipeNormalFluid, Aluminium, 2)
                .input(pipeNormalItem, Electrum, 2)
                .output(UNIVERSAL_BRIDGE)
                .EUt(VH[2])
                .duration(10 * SECOND)
                .buildAndRegister();

        // Inventory Extender
        ModHandler.addShapedRecipe(true, "inventory_extender", INV_EXTENDER.getStackForm(),
                " hP", " H ", "Pw ",
                'H', HULL[1].getStackForm(),
                'P', new UnificationEntry(pipeNormalItem, Nickel));

        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .input(HULL[1])
                .input(pipeNormalItem, Nickel, 2)
                .output(INV_EXTENDER)
                .EUt(VA[1])
                .duration(10 * SECOND)
                .buildAndRegister();

        // Tank Extender
        ModHandler.addShapedRecipe(true, "tank_extender", TANK_EXTENDER.getStackForm(),
                "Ph ", " H ", " wP",
                'H', HULL[1].getStackForm(),
                'P', new UnificationEntry(pipeNormalFluid, Steel));

        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .input(HULL[1])
                .input(pipeNormalFluid, Steel, 2)
                .output(TANK_EXTENDER)
                .EUt(VA[1])
                .duration(10 * SECOND)
                .buildAndRegister();

        // Inventory Tank Extender
        ModHandler.addShapedRecipe(true, "inventory_tank_extender", INV_TANK_EXTENDER.getStackForm(),
                "PhQ", " H ", "QwP",
                'H', HULL[1].getStackForm(),
                'P', new UnificationEntry(pipeNormalFluid, Steel),
                'Q', new UnificationEntry(pipeNormalItem, Nickel));

        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(3)
                .input(HULL[1])
                .input(pipeNormalFluid, Steel, 2)
                .input(pipeNormalItem, Nickel, 2)
                .output(INV_TANK_EXTENDER)
                .EUt(VA[1])
                .duration(10 * SECOND)
                .buildAndRegister();

        // Universal Extender
        ModHandler.addShapedRecipe(true, "universal_extender", UNIVERSAL_EXTENDER.getStackForm(),
                "PRQ", "XHG", "QSP",
                'H', HULL[2].getStackForm(),
                'P', new UnificationEntry(pipeNormalFluid, Aluminium),
                'Q', new UnificationEntry(pipeNormalItem, Electrum),
                'R', new UnificationEntry(rotor, Aluminium),
                'G', new UnificationEntry(gear, Aluminium),
                'S', new UnificationEntry(spring, Aluminium),
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.LV));

        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(3)
                .input(HULL[2])
                .input(circuit, MarkerMaterials.Tier.LV)
                .input(rotor, Aluminium)
                .input(gear, Aluminium)
                .input(spring, Aluminium)
                .input(pipeNormalFluid, Aluminium, 2)
                .input(pipeNormalItem, Electrum, 2)
                .output(UNIVERSAL_EXTENDER)
                .EUt(VA[2])
                .duration(10 * SECOND)
                .buildAndRegister();
    }
}
