package keqing.gtqtcore.loaders.recipes;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
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
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTElectrobath;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static gregicality.multiblocks.api.unification.GCYMMaterials.*;
import static gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities.LARGE_ASSEMBLER;
import static gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities.LARGE_DISTILLERY;
import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.LuV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.MarkerMaterials.Tier.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.blocks.BlockMetalCasing.MetalCasingType.*;
import static gregtech.common.blocks.BlockSteamCasing.SteamCasingType.BRONZE_HULL;
import static gregtech.common.blocks.BlockWireCoil.CoilType.CUPRONICKEL;
import static gregtech.common.blocks.MetaBlocks.MACHINE_CASING;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static gregtech.loaders.recipe.CraftingComponent.GLASS;
import static gregtech.loaders.recipe.CraftingComponent.PIPE_NORMAL;
import static gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.GENE_MUTAGENESIS;
import static keqing.gtqtcore.api.unification.GCYSMaterials.Adamantite;
import static keqing.gtqtcore.api.unification.GCYSMaterials.Orichalcum;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.*;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.common.block.blocks.BlockCrucible.CrucibleType.QUARTZ_CRUCIBLE;
import static keqing.gtqtcore.common.block.blocks.GTQTIsaCasing.CasingType.ASEPTIC_FARM_CASING;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing.TurbineCasingType.*;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing1.TurbineCasingType.AD_TURBINE_CASING;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing1.TurbineCasingType.ST_TURBINE_CASING;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.DISTILLATION_TOWER;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.GAS_COLLECTOR;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.PYROLYSE_OVEN;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.VACUUM_FREEZER;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.*;
import static net.minecraft.init.Blocks.IRON_BARS;


public class MetaTileEntityLoader {
    public static CraftingComponent.Component SXA_HULL;

    public static void init() {

        SXA_HULL = new CraftingComponent.Component((Map) Stream.of(new Object[]{0, HULL[0].getStackForm()},new Object[]{1, OUTPUT_ENERGY_HATCH_16A[0].getStackForm()}, new Object[]{2, OUTPUT_ENERGY_HATCH_16A[1].getStackForm()}, new Object[]{3, OUTPUT_ENERGY_HATCH_16A[2].getStackForm()}, new Object[]{4, OUTPUT_ENERGY_HATCH_16A[3].getStackForm()}, new Object[]{5, ENERGY_OUTPUT_HATCH_16A[0].getStackForm()}, new Object[]{6, MetaTileEntities.ENERGY_OUTPUT_HATCH_16A[1].getStackForm()}, new Object[]{7, MetaTileEntities.ENERGY_OUTPUT_HATCH_16A[2].getStackForm()}, new Object[]{8, MetaTileEntities.ENERGY_OUTPUT_HATCH_16A[3].getStackForm()}, new Object[]{9, MetaTileEntities.ENERGY_OUTPUT_HATCH_16A[4].getStackForm()}, new Object[]{10, OUTPUT_ENERGY_HATCH_16A[4].getStackForm()}, new Object[]{11, OUTPUT_ENERGY_HATCH_16A[5].getStackForm()}, new Object[]{12, OUTPUT_ENERGY_HATCH_16A[6].getStackForm()}, new Object[]{13, OUTPUT_ENERGY_HATCH_16A[7].getStackForm()}).collect(Collectors.toMap((data) -> (Integer)data[0], (data) -> data[1])));


        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate_big, Steel,32)
                .input(stick, Steel, 16)
                .input(ring, Steel, 8)
                .input(rotor, Steel, 4)
                .output(WIND_ROTOR_STEEL)
                .circuitMeta(11)
                .duration(600).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate_big, Aluminium,32)
                .input(stick, Aluminium, 16)
                .input(ring, Aluminium, 8)
                .input(rotor, Aluminium, 4)
                .output(WIND_ROTOR_ALUMINIUM)
                .circuitMeta(11)
                .duration(600).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate_big, StainlessSteel,32)
                .input(stick, StainlessSteel, 16)
                .input(ring, StainlessSteel, 8)
                .input(rotor, StainlessSteel, 4)
                .output(WIND_ROTOR_STAINLESSSTEEL)
                .circuitMeta(11)
                .duration(600).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate_big, Titanium,32)
                .input(stick, Titanium, 16)
                .input(ring, Titanium, 8)
                .input(rotor, Titanium, 4)
                .output(WIND_ROTOR_TITANIUM)
                .circuitMeta(11)
                .duration(600).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate_big, TungstenSteel,32)
                .input(stick, TungstenSteel, 16)
                .input(ring, TungstenSteel, 8)
                .input(rotor, TungstenSteel, 4)
                .output(WIND_ROTOR_TUNGSTENSTEEL)
                .circuitMeta(11)
                .duration(600).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate_big, RhodiumPlatedPalladium,32)
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
                .input(ELECTRIC_PUMP_MV,8)
                .input(circuit,MV,8)
                .input(rotor,Aluminium,32)
                .input(OrePrefix.cableGtSingle, Materials.Tin, 32)
                .fluidInputs(Polyethylene.getFluid(L * 12))
                .outputs(GAS_COLLECTOR.getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(500).EUt(120)
                .inputs(HULL[2].getStackForm(4))
                .input(ELECTRIC_MOTOR_MV,8)
                .input(ELECTRIC_PUMP_MV,8)
                .input(circuit,MV,8)
                .input(gear,Aluminium,8)
                .input(OrePrefix.cableGtSingle, Materials.Tin, 32)
                .fluidInputs(Polyethylene.getFluid(L * 12))
                .outputs(WATER_POWER_STATION[0].getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(500).EUt(1920)
                .inputs(HULL[4].getStackForm(4))
                .input(ELECTRIC_MOTOR_EV,8)
                .input(ELECTRIC_PUMP_EV,8)
                .input(circuit,EV,8)
                .input(gear,Titanium,8)
                .input(OrePrefix.cableGtSingle, Platinum, 32)
                .fluidInputs(Epoxy.getFluid(L * 12))
                .outputs(WATER_POWER_STATION[1].getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(500).EUt(30720)
                .inputs(HULL[6].getStackForm(4))
                .input(ELECTRIC_MOTOR_LuV,8)
                .input(ELECTRIC_PUMP_LuV,8)
                .input(circuit, MarkerMaterials.Tier.LuV,8)
                .input(gear,RhodiumPlatedPalladium,8)
                .input(OrePrefix.cableGtSingle, Naquadah, 32)
                .fluidInputs(Zylon.getFluid(L * 12))
                .outputs(WATER_POWER_STATION[2].getStackForm()).buildAndRegister();


        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(500).EUt(480)
                .inputs(HULL[3].getStackForm(4))
                .input(circuit,EV,8)
                .input(EMITTER_HV,4)
                .input(ELECTRIC_PUMP_HV,8)
                .input(gear,StainlessSteel,8)
                .input(stick,Talonite,8)
                .input(OrePrefix.cableGtSingle, Aluminium, 32)
                .circuitMeta(10)
                .fluidInputs(Epoxy.getFluid(L * 12))
                .outputs(TIDE_CONTROL.getStackForm()).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(500).EUt(480)
                .input(frameGt,StainlessSteel,8)
                .input(circuit,EV,8)
                .input(SENSOR_HV,2)
                .input(ELECTRIC_PUMP_EV,4)
                .input(VOLTAGE_COIL_HV,8)
                .input(gear,Steel,8)
                .input(OrePrefix.cableGtSingle, Aluminium, 32)
                .circuitMeta(10)
                .fluidInputs(Epoxy.getFluid(L * 12))
                .outputs(TIDE_UNIT.getStackForm()).buildAndRegister();

        ModHandler.addShapedRecipe(true, "industry_pump",INDUSTRY_WATER_PUMP.getStackForm(),
                "ABA", "CHC","ABA",
                'H', MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LV),
                'C', new UnificationEntry(pipeLargeFluid, Steel),
                'B', new UnificationEntry(plate, Iron),
                'A', new UnificationEntry(plate, Invar));

        ModHandler.addShapedRecipe(true, "ulv_casing", HULL[0].getStackForm(),
                "ABA", "CHC","ABA",
                'H', MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ULV),
                'C', new UnificationEntry(wireGtSingle, Lead),
                'B', new UnificationEntry(plate, Iron),
                'A', new UnificationEntry(plate, RedAlloy));

        ModHandler.addShapedRecipe(true, "lv_casing", HULL[1].getStackForm(),
                "ABA", "CHC","ABA",
                'H', MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LV),
                'C', new UnificationEntry(cableGtSingle, Tin),
                'B', new UnificationEntry(plate, GalvanizedSteel),
                'A', new UnificationEntry(plate, Rubber));

        ModHandler.addShapedRecipe(true, "mv_casing", HULL[2].getStackForm(),
                "ABA", "CHC","ABA",
                'H', MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.MV),
                'C', new UnificationEntry(cableGtSingle, Copper),
                'B', new UnificationEntry(plate, Invar),
                'A', new UnificationEntry(plate, Polyethylene));

        ModHandler.addShapedRecipe(true, "hv_casing", HULL[3].getStackForm(),
                "ABA", "CHC","ABA",
                'H', MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.HV),
                'C', new UnificationEntry(cableGtSingle, Aluminium),
                'B', new UnificationEntry(plate, Talonite),
                'A', new UnificationEntry(plate, Epoxy));

        ModHandler.addShapedRecipe(true, "ev_casing", HULL[4].getStackForm(),
                "ABA", "CHC","ABA",
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
                'C', new UnificationEntry(cylinder, Steel));

        ModHandler.addShapedRecipe(true, "mold_motor", MOLD_MOTOR.getStackForm(),
                "MC", "ch",
                'M', new UnificationEntry(block, Steel),
                'C', new UnificationEntry(motor_stick, Steel));

        //3d
        ModHandler.addShapedRecipe(true, "three_d", THREE_DIM_PRINT.getStackForm(),
                "MCM", "HHH", "PPP",
                'M', new UnificationEntry(pipeNormalFluid, Polytetrafluoroethylene),
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
        ModHandler.addShapedRecipe(true, "lv_processing_array", LV_PROCESSING_ARRAY.getStackForm(), "RCR", "SPE", "HNH", 'R',
                MetaItems.ROBOT_ARM_MV, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.MV), 'S',
                MetaItems.SENSOR_MV, 'P', GTQTMetaTileEntities.ASSEMBLY_LINE.getStackForm(), 'E', MetaItems.EMITTER_MV,
                'H', new UnificationEntry(OrePrefix.plate, Invar), 'N',
                new UnificationEntry(OrePrefix.pipeLargeFluid, Aluminium));

        ModHandler.addShapedRecipe(true, "mv_processing_array", MV_PROCESSING_ARRAY.getStackForm(),  "RCR", "SPE", "HNH", 'R',
                MetaItems.ROBOT_ARM_HV, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.HV), 'S',
                MetaItems.SENSOR_HV, 'P', GTQTMetaTileEntities.LV_PROCESSING_ARRAY.getStackForm(), 'E', MetaItems.EMITTER_HV,
                'H', new UnificationEntry(OrePrefix.plate, Talonite), 'N',
                new UnificationEntry(OrePrefix.pipeLargeFluid, StainlessSteel));

        ModHandler.addShapedRecipe(true, "hv_processing_array", HV_PROCESSING_ARRAY.getStackForm(), "RCR", "SPE", "HNH", 'R',
                MetaItems.ROBOT_ARM_EV, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.EV), 'S',
                MetaItems.SENSOR_EV, 'P', GTQTMetaTileEntities.MV_PROCESSING_ARRAY.getStackForm(), 'E', MetaItems.EMITTER_EV,
                'H', new UnificationEntry(OrePrefix.plate, Titanium), 'N',
                new UnificationEntry(OrePrefix.pipeLargeFluid, Polyethylene));

        ModHandler.addShapedRecipe(true, "hv_machine_access_interface", HV_MACHINE_HATCH.getStackForm(),
                "CHS", 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.HV), 'H',
                MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'S', MetaItems.SENSOR_HV.getStackForm());
        //耐火砖快乐配方
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(MetaItems.FIRECLAY_BRICK.getStackForm(6))
                .input(dust, Gypsum, 2)
                .fluidInputs(Concrete.getFluid(1000))
                .outputs(MetaBlocks.METAL_CASING.getItemVariant(BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS))
                .duration(20).EUt(30).buildAndRegister();

        //垃圾桶
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate,Steel,16)
                .input(stickLong, Steel, 2)
                .input(circuit, LV, 4)
                .input(pipeHugeItem,Nickel,1)
                .circuitMeta(10)
                .output(RUBBISH_BIN)
                .duration(20).EUt(30).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate,Steel,16)
                .input(stickLong, Steel, 2)
                .input(circuit, LV, 4)
                .input(pipeLargeFluid,Steel,1)
                .circuitMeta(10)
                .output(FLUID_RUBBISH_BIN)
                .duration(20).EUt(30).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(FLUID_RUBBISH_BIN)
                .input(RUBBISH_BIN)
                .output(COMMON_RUBBISH_BIN)
                .duration(20).EUt(30).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate,Steel,32)
                .input(stickLong, Steel, 4)
                .input(circuit, LV, 8)
                .input(pipeHugeItem,Nickel,1)
                .input(pipeLargeFluid,Steel,1)
                .circuitMeta(11)
                .output(COMMON_RUBBISH_BIN)
                .duration(20).EUt(30).buildAndRegister();

        //海藻方块
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(MetaTileEntities.HULL[GTValues.HV].getStackForm())
                .input(plate, NanometerBariumTitanate, 6)
                .input(frameGt, StainlessSteel, 1)
                .fluidInputs(Bps.getFluid(1440))
                .circuitMeta(1)
                .outputs(GTQTMetaBlocks.ISA_CASING.getItemVariant(ASEPTIC_FARM_CASING))
                .duration(20).EUt(30).buildAndRegister();
        //太阳能
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[4].getStackForm())
                .input(EMITTER_MV, 8)
                .input(plate, StainlessSteel, 16)
                .fluidInputs(Polybenzimidazole.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.SOLAR_PLATE_CASING))
                .duration(2000).EUt(1920).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[4].getStackForm())
                .input(circuit, MarkerMaterials.Tier.HV,8)
                .input(COVER_SCREEN)
                .input(wireFine, Platinum, 16)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .output(SOLAR_PLATE)
                .duration(2000).EUt(1920).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust,GalliumArsenide,16)
                .input(plate,Polysilicon,16)
                .fluidInputs(Nitrogen.getFluid(4000))
                .output(SOLAR_PLATE_MKI,1)
                .blastFurnaceTemp(2700)
                .duration(2000).EUt(480).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust,Germanium,16)
                .input(plate,Polysilicon,16)
                .fluidInputs(Nitrogen.getFluid(4000))
                .output(SOLAR_PLATE_MKII,1)
                .blastFurnaceTemp(3600)
                .duration(2000).EUt(1920).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust,IndiumGalliumPhosphide,16)
                .input(plate,Polysilicon,16)
                .fluidInputs(Nitrogen.getFluid(4000))
                .output(SOLAR_PLATE_MKIII,1)
                .blastFurnaceTemp(4500)
                .duration(2000).EUt(7680).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.SOLAR_PLATE_CASING))
                .input(EMITTER_HV, 1)
                .input(circuit, MarkerMaterials.Tier.HV,8)
                .input(SOLAR_PLATE_MKI,4)
                .fluidInputs(Polybenzimidazole.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.SOLAR_PLATE_LV))
                .duration(2000).EUt(1920).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.SOLAR_PLATE_CASING))
                .input(EMITTER_EV, 1)
                .input(circuit, MarkerMaterials.Tier.EV,8)
                .input(SOLAR_PLATE_MKII,4)
                .fluidInputs(Polybenzimidazole.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.SOLAR_PLATE_MV))
                .duration(2000).EUt(1920).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.SOLAR_PLATE_CASING))
                .input(EMITTER_IV, 1)
                .input(circuit, MarkerMaterials.Tier.IV,8)
                .input(SOLAR_PLATE_MKIII,4)
                .fluidInputs(Polybenzimidazole.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.SOLAR_PLATE_HV))
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

        ModHandler.addShapedRecipe(true, "catalyst_hatch", CATALYST_HATCH.getStackForm(),
                "MhM", "PHP", "McM",
                'M', new UnificationEntry(pipeNormalFluid, Aluminium),
                'P', ROBOT_ARM_HV.getStackForm(),
                'H', MetaTileEntities.HULL[3].getStackForm()
        );
        //  Cryogenic Freezer
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(VACUUM_FREEZER.getStackForm(4))
                .input(CIRCUIT_GOOD_I)
                .input(circuit, MarkerMaterials.Tier.LuV,16)
                .input(frameGt, HSSG, 16)
                .input(ROBOT_ARM_IV, 8)
                .input(ELECTRIC_PUMP_IV, 8)
                .input(plate, TanmolyiumBetaC, 4)
                .input(plate, Stellite100, 4)
                .input(wireFine, Platinum, 64)
                .input(stickLong, SamariumMagnetic, 64)
                .input(stickLong, SamariumMagnetic, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 6))
                .fluidInputs(Lubricant.getFluid(3000))
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
                .input(circuit, MarkerMaterials.Tier.IV,16)
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
                .fluidInputs(Lubricant.getFluid(3000))
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
        //炽焰
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(ELECTRIC_FURNACE[4].getStackForm(16))
                .input(CIRCUIT_GOOD_I)
                .input(circuit, MarkerMaterials.Tier.LuV,16)
                .input(frameGt, HSSG, 16)
                .input(CONVEYOR_MODULE_IV, 8)
                .input(VOLTAGE_COIL_IV, 16)
                .input(plate, TanmolyiumBetaC, 4)
                .input(plate, AusteniticStainlessSteel904L, 4)
                .input(wireFine, Palladium, 64)
                .input(stickLong, SamariumMagnetic, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 6))
                .fluidInputs(Lubricant.getFluid(3000))
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
                .input(circuit, MarkerMaterials.Tier.LuV,16)
                .input(frameGt, MARM200Steel, 16)
                .input(ROBOT_ARM_IV, 8)
                .input(CONVEYOR_MODULE_IV, 8)
                .input(plate, Stellite100, 4)
                .input(gear, TanmolyiumBetaC, 4)
                .input(wireFine, Platinum, 64)
                .input(stickLong, SamariumMagnetic, 64)
                .input(stickLong, SamariumMagnetic, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 6))
                .fluidInputs(Lubricant.getFluid(3000))
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
                .inputs(CHEMICAL_PLANT.getStackForm())
                .input(frameGt, TungstenSteel, 4)
                .input(circuit, EV, 16)
                .input(ROBOT_ARM_EV, 8)
                .input(ELECTRIC_MOTOR_EV, 8)
                .input(CONVEYOR_MODULE_EV, 8)
                .input(screw, Titanium, 16)
                .input(plate, Palladium, 16)
                .input(gear, NanometerBariumTitanate, 16)
                .input(wireFine, Platinum, 64)
                .input(wireFine, Platinum, 64)
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
                .input(frameGt, Iridium,4)
                .inputs(MetaBlocks.CLEANROOM_CASING.getItemVariant(gregtech.common.blocks.BlockCleanroomCasing.CasingType.FILTER_CASING))
                .input(ELECTRIC_MOTOR_LuV)
                .input(rotor, Iridium,4)
                .input(ITEM_FILTER,4)
                .input(FLUID_FILTER,4)
                .input(stickLong, Iridium, 8)
                .fluidInputs(SolderingAlloy.getFluid(L * 2))
                .outputs(GTQTMetaBlocks.TURBINE_CASING.getItemVariant(ADVANCED_FILTER_CASING))
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
                'P', new UnificationEntry(plateDouble, Plutonium239),
                'W', new UnificationEntry(cableGtDouble, NiobiumTitanium),
                'F', FIELD_GENERATOR_LuV,
                'S', new UnificationEntry(spring, RTMAlloy),
                'A', FLUID_CELL_LARGE_TUNGSTEN_STEEL);

        ModHandler.addShapedRecipe(true, "large_fuel_generator", LARGE_FUEL_TURBINE.getStackForm(),
                "SAS", "PBP", "WFW",
                'B', FUEL_CELL_TURBINE[4].getStackForm(),
                'P', new UnificationEntry(plateDouble, Plutonium239),
                'W', new UnificationEntry(cableGtDouble, NiobiumTitanium),
                'F', ELECTRIC_PISTON_LUV,
                'S', new UnificationEntry(spring, RTMAlloy),
                'A', FLUID_CELL_LARGE_TUNGSTEN_STEEL);

        ModHandler.addShapedRecipe(true, "p_reactor", P_REACTOR.getStackForm(),
                "CGC", "ChC", "CGC",
                'G', new UnificationEntry(OrePrefix.gear, Wood),
                'C', MetaBlocks.STEAM_CASING.getItemVariant(BlockSteamCasing.SteamCasingType.WOOD_WALL));

        ModHandler.addShapedRecipe(true, "b_reactor", B_REACTOR.getStackForm(),
                "CGC", "ChC", "CGC",
                'G', new UnificationEntry(OrePrefix.gear, StainlessSteel),
                'C', MetaBlocks.METAL_CASING.getItemVariant(STAINLESS_CLEAN));

        ModHandler.addShapedRecipe(true, "bronze_industrial_blast_furnace",
                GTQTMetaTileEntities.INDUSTRIAL_PRIMITIVE_BLAST_FURNACE.getStackForm(),
                "BPB", "PCP", "BPB",
                'C',  new UnificationEntry(circuit, LV),
                'P', new UnificationEntry(OrePrefix.plateDouble, Steel),
                'B', PRIMITIVE_BLAST_FURNACE.getStackForm());

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

        ModHandler.addShapedRecipe(true, "saw_mill",
                GTQTMetaTileEntities.SAW_MILL.getStackForm(),
                "BPB", "PCP", "BPB",
                'C', new UnificationEntry(circuit, ULV),
                'P', new UnificationEntry(plate, Wood),
                'B', new UnificationEntry(frameGt, Bronze));

        ModHandler.addShapedRecipe(true, "steam_vat",
                GTQTMetaTileEntities.STEAM_VAT.getStackForm(),
                "BPB", "PCP", "BPB",
                'C',  new UnificationEntry(circuit, LV),
                'P', new UnificationEntry(OrePrefix.plateDouble, Invar),
                'B', ELECTRIC_PUMP_LV);

        ModHandler.addShapedRecipe(true, "gravity_separator",
                GTQTMetaTileEntities.GRAVITY_SEPARATOR.getStackForm(),
                "BPB", "PCP", "BPB",
                'C',  new UnificationEntry(circuit, MV),
                'P', new UnificationEntry(OrePrefix.plateDouble, Aluminium),
                'B', ELECTRIC_MOTOR_MV);

        ModHandler.addShapedRecipe(true, "seismic_detector",
                GTQTMetaTileEntities.SEISMIC_DETECTOR.getStackForm(),
                "BPB", "PCP", "BPB",
                'C',  new UnificationEntry(circuit, MV),
                'P', new UnificationEntry(OrePrefix.plateDouble, Aluminium),
                'B', SENSOR_MV);

        ModHandler.addShapedRecipe(true, "crystallization_crucible", CRYSTALLIZATION_CRUCIBLE.getStackForm(),
                "CMC", "LHL", "PCP",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.LuV),
                'M', new UnificationEntry(plateDouble, MolybdenumDisilicide),
                'L', new UnificationEntry(pipeNormalFluid, Titanium),
                'H', MetaTileEntities.HULL[5].getStackForm(),
                'P', new UnificationEntry(plate, TungstenSteel)
        );

        ModHandler.addShapedRecipe(true, "cz_puller",
                GTQTMetaTileEntities.CZ_PULLER.getStackForm(),
                "FFF", "CHC", "WCW",
                'F', MetaTileEntities.ELECTRIC_FURNACE[2].getStackForm(),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.MV),
                'H', GTQTMetaBlocks.CRUCIBLE.getItemVariant(QUARTZ_CRUCIBLE),
                'W', new UnificationEntry(OrePrefix.cableGtSingle, Aluminium));

        ModHandler.addShapedRecipe(true, "distillation_tower", DISTILLATION_TOWER.getStackForm(),
                "CBC", "FMF", "CBC", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'B',
                new UnificationEntry(OrePrefix.pipeLargeFluid, Steel), 'C',
                new UnificationEntry(OrePrefix.circuit, MV), 'F', MetaItems.ELECTRIC_PUMP_LV);

        ModHandler.addShapedRecipe(true, "msf", GTQTMetaTileEntities.MSF.getStackForm(),
                "CBC", "FMF", "CBC", 'M', MetaTileEntities.HULL[GTValues.MV].getStackForm(), 'B',
                new UnificationEntry(OrePrefix.pipeLargeFluid, Aluminium), 'C',
                new UnificationEntry(OrePrefix.circuit, MV), 'F', DISTILLATION_TOWER.getStackForm());

        ModHandler.addShapedRecipe(true, "evaporation_pool", GTQTMetaTileEntities.EVAPORATION_POOL.getStackForm(),
                "FFF", "CMC", "BBB", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'B',
                new UnificationEntry(OrePrefix.pipeLargeFluid, Steel), 'C',
                new UnificationEntry(OrePrefix.circuit, LV), 'F', MetaItems.ELECTRIC_PUMP_LV);

        ModHandler.addShapedRecipe(true, "pyrolysis_tower", GTQTMetaTileEntities.PYROLYSIS_TOWER.getStackForm(),
                "FFF", "CMC", "BBB", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'B',
                new UnificationEntry(OrePrefix.pipeLargeFluid, Steel), 'C',
                new UnificationEntry(OrePrefix.circuit, LV), 'F', PRIMITIVE_BLAST_FURNACE.getStackForm());

        ModHandler.addShapedRecipe(true, "distillation_kettle", GTQTMetaTileEntities.DISTILLATION_KETTLE.getStackForm(),
                "BCB", "FMF", "BCB", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'B',
                new UnificationEntry(OrePrefix.pipeLargeFluid, Steel), 'C',
                new UnificationEntry(OrePrefix.circuit, LV), 'F', MetaItems.ELECTRIC_PUMP_LV);


        ModHandler.addShapedRecipe(true, "septic_tank", GTQTMetaTileEntities.SEPTIC_TANK.getStackForm(),
                "BFB", "CMC", "BFB", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'B',
                new UnificationEntry(OrePrefix.pipeLargeFluid, Steel), 'C',
                new UnificationEntry(OrePrefix.circuit, LV), 'F', MetaItems.ELECTRIC_PUMP_LV);

        ModHandler.addShapedRecipe(true, "kinetic_energy_battery", GTQTMetaTileEntities.KINETIC_ENERGY_BATTERY.getStackForm(),
                "FFF", "BMB", "CCC", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'B',
                new UnificationEntry(OrePrefix.pipeLargeFluid, Steel), 'C',
                new UnificationEntry(OrePrefix.circuit, LV), 'F', ELECTRIC_MOTOR_LV);

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
                'P', ELECTRIC_PUMP_LuV,
                'p', new UnificationEntry(plate, HG1223),
                'G', new UnificationEntry(gear, Osmiridium),
                'S', new UnificationEntry(spring, MolybdenumDisilicide),
                'W', new UnificationEntry(cableGtSingle, NiobiumTitanium));

        ModHandler.addShapedRecipe(true, "mining_drill", GTQTMetaTileEntities.MINING_DRILL.getStackForm(),
                "FFF", "BMB", "CCC", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'B',
                new UnificationEntry(gear, Steel), 'C',
                new UnificationEntry(OrePrefix.circuit, LV), 'F', ELECTRIC_MOTOR_LV);

        ModHandler.addShapedRecipe(true, "ele_oil", GTQTMetaTileEntities.ELE_OIL.getStackForm(),
                "FFF", "BMB", "CCC", 'M', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'B',
                new UnificationEntry(gear, Titanium), 'C',
                new UnificationEntry(OrePrefix.circuit, HV), 'F', EMITTER_HV);

        ModHandler.addShapedRecipe(true, "clarifier", GTQTMetaTileEntities.CLARIFIER.getStackForm(),
                "FFF", "BMB", "CCC", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'B',
                new UnificationEntry(plateDouble, Steel), 'C',
                new UnificationEntry(OrePrefix.circuit, LV), 'F', ELECTRIC_PUMP_LV);

        ModHandler.addShapedRecipe(true, "ocean_pumper", GTQTMetaTileEntities.OCEAN_PUMPER.getStackForm(),
                "FFF", "CMC", "FFF", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'C',
                new UnificationEntry(OrePrefix.circuit, LV), 'F', ELECTRIC_PUMP_LV);

        ModHandler.addShapedRecipe(true, "gantry_crane", GTQTMetaTileEntities.GANTRY_CRANE.getStackForm(),
                "FFF", "BMB", "CCC", 'M', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'B',
                new UnificationEntry(plateDouble, StainlessSteel), 'C',
                new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.HV), 'F', ELECTRIC_MOTOR_HV);

        ModHandler.addShapedRecipe(true, "cracking_unit", GTQTMetaTileEntities.CRACKER.getStackForm(), "CEC", "PHP", "CEC",
                'C', MetaBlocks.WIRE_COIL.getItemVariant(CUPRONICKEL), 'E', MetaItems.ELECTRIC_PUMP_LV, 'P',
                new UnificationEntry(OrePrefix.circuit, LV), 'H',
                MetaTileEntities.HULL[GTValues.LV].getStackForm());

        ModHandler.addShapedRecipe(true, "pyrolyse_oven", PYROLYSE_OVEN.getStackForm(), "WEP", "EME",
                "WCP", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'W', MetaItems.ELECTRIC_PISTON_LV, 'P',
                new UnificationEntry(OrePrefix.wireGtQuadruple, Aluminium), 'E',
                new UnificationEntry(OrePrefix.circuit, LV), 'C', MetaItems.ELECTRIC_PUMP_LV);

        ModHandler.addShapedRecipe(true, "vacuum_freezer", VACUUM_FREEZER.getStackForm(), "PPP", "CMC",
                "WCW", 'M', MetaTileEntities.HULL[GTValues.MV].getStackForm(), 'P',
                MetaItems.ELECTRIC_PUMP_MV, 'C', new UnificationEntry(OrePrefix.circuit, MV), 'W',
                new UnificationEntry(OrePrefix.cableGtSingle, Aluminium));

        ModHandler.addShapedRecipe(true, "lager_ore_washer", GTQTMetaTileEntities.LAGER_ORE_WASHER.getStackForm(), "PPP", "CMC",
                "WCW",
                'M', ORE_WASHER[1].getStackForm(),
                'P', ROBOT_ARM_LV, 'C', new UnificationEntry(OrePrefix.circuit, LV),
                'W', ELECTRIC_MOTOR_LV);

        ModHandler.addShapedRecipe(true, "lager_thermal_centrifuge", GTQTMetaTileEntities.LAGER_THERMAL_CENTRIFUGE.getStackForm(), "WPW", "CMC",
                "WPW",
                'M', THERMAL_CENTRIFUGE[1].getStackForm(),
                'P', MetaItems.ELECTRIC_PUMP_LV, 'C', new UnificationEntry(OrePrefix.circuit, LV),
                'W', VOLTAGE_COIL_MV);

        ModHandler.addShapedRecipe(true, "lager_grind", GTQTMetaTileEntities.LAGER_GRIND.getStackForm(), "WPW", "CMC",
                "WPW",
                'M', MACERATOR[1].getStackForm(),
                'P', MetaItems.ELECTRIC_PUMP_LV, 'C', new UnificationEntry(OrePrefix.circuit, LV),
                'W', ELECTRIC_PISTON_LV);

        ModHandler.addShapedRecipe(true, "lager_forging", LAGER_FORGING.getStackForm(), "WPW", "CMC",
                "WPW",
                'M', FORGE_HAMMER[1].getStackForm(),
                'P', MetaItems.ELECTRIC_PUMP_LV, 'C', new UnificationEntry(OrePrefix.circuit, LV),
                'W', ELECTRIC_MOTOR_LV);

        ModHandler.addShapedRecipe(true, "lager_extractor", LAGER_EXTRACTOR.getStackForm(), "WPW", "CMC",
                "WPW",
                'M', EXTRACTOR[1].getStackForm(),
                'P', MetaItems.ELECTRIC_PUMP_LV, 'C', new UnificationEntry(OrePrefix.circuit, LV),
                'W', ELECTRIC_MOTOR_LV);

        ModHandler.addShapedRecipe(true, "assembler_line", GTQTMetaTileEntities.ASSEMBLY_LINE.getStackForm(), "PPP", "CMC",
                "WCW", 'M', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'P',
                EMITTER_HV, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.HV), 'W',
                new UnificationEntry(OrePrefix.cableGtSingle, Gold));

        ModHandler.addShapedRecipe(true, "large_chemical_reactor",
                GTQTMetaTileEntities.LARGE_CHEMICAL_REACTOR.getStackForm(), "CRC", "PMP", "CHC", 'C',
                new UnificationEntry(OrePrefix.circuit, LV), 'R',
                OreDictUnifier.get(OrePrefix.rotor, Steel), 'P',
                OreDictUnifier.get(OrePrefix.pipeLargeFluid, Materials.Polyethylene), 'M',
                MetaItems.ELECTRIC_MOTOR_LV.getStackForm(), 'H', MetaTileEntities.HULL[GTValues.LV].getStackForm());

        ModHandler.addShapedRecipe(true, "reaction_furnace", REACTION_FURNACE.getStackForm(),
                "KSK", "CHC", "PPP",
                'K', new UnificationEntry(cableGtQuadruple, Aluminium),
                'S', new UnificationEntry(spring, Steel),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.MV),
                'H', MetaTileEntities.HULL[2].getStackForm(),
                'P', new UnificationEntry(plate, Invar)
        );

        ModHandler.addShapedRecipe(true, "blazing_cz_puller", BLAZING_CZ_PULLER.getStackForm(),
                "GXG", "RHR", "PWP",
                'G', new UnificationEntry(gear, HSSS),
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'H', CZ_PULLER.getStackForm(),
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
                .input(plate, Steel,16)
                .input(stick, Steel,2)
                .input(ring, Steel, 4)
                .input(LAPOTRON_CRYSTAL,1)
                .input(circuit, MarkerMaterials.Tier.EV, 1)
                .input(SENSOR_HV,1)
                .output(POS_BINDING_CARD)
                .circuitMeta(2)
                .duration(600).EUt(VA[3]).buildAndRegister();


        ModHandler.addShapedRecipe(true, "roaster", ROASTER.getStackForm(),
                "KSK", "CHC", "PPP",
                'K', new UnificationEntry(cableGtQuadruple, Platinum),
                'S', new UnificationEntry(spring, Titanium),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.EV),
                'H', MetaTileEntities.HULL[3].getStackForm(),
                'P', new UnificationEntry(plate, TitaniumCarbide)
        );

        ModHandler.addShapedRecipe(true, "nanoscale_fabricator", NANOSCALE_FABRICATOR.getStackForm(),
                "KSK", "EHE", "CFC",
                'K', new UnificationEntry(cableGtSingle, Europium),
                'S', SENSOR_UHV.getStackForm(),
                'E', EMITTER_UHV.getStackForm(),
                'H', MetaTileEntities.HULL[8].getStackForm(),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UEV),
                'F', FIELD_GENERATOR_UHV.getStackForm()
        );

        ModHandler.addShapedRecipe(true, "cvd_unit", CVD_UNIT.getStackForm(),
                "PKP", "CHC", "ESE",
                'P', new UnificationEntry(plate, HSSS),
                'K', new UnificationEntry(gear, PPB),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'H', MetaTileEntities.HULL[5].getStackForm(),
                'S', SENSOR_IV.getStackForm(),
                'E', EMITTER_IV.getStackForm()
        );

        ModHandler.addShapedRecipe(true, "burner_reactor", BURNER_REACTOR.getStackForm(),
                "KRK", "IHI", "CMC",
                'K', new UnificationEntry(cableGtSingle, Platinum),
                'R', new UnificationEntry(frameGt, Polytetrafluoroethylene),
                'I', new UnificationEntry(plate, ReinforcedEpoxyResin),
                'H', MetaTileEntities.HULL[4].getStackForm(),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.EV),
                'M', ELECTRIC_MOTOR_EV.getStackForm()
        );

        ModHandler.addShapedRecipe(true, "cryo_reactor", CRYOGENIC_REACTOR.getStackForm(),
                "KRK", "IHI", "CWC",
                'K', new UnificationEntry(cableGtSingle, Platinum),
                'R', new UnificationEntry(rotor, Titanium),
                'I', new UnificationEntry(pipeSmallFluid, StainlessSteel),
                'H', MetaTileEntities.HULL[4].getStackForm(),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'W', ELECTRIC_PUMP_IV.getStackForm()
        );

        ModHandler.addShapedRecipe(true, "fracker", HYDRAULIC_FRACKER.getStackForm(),
                "CLC", "GHG", "PPP",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UV),
                'L', new UnificationEntry(pipeLargeFluid, Naquadah),
                'G', new UnificationEntry(gear, NaquadahAlloy),
                'H', MetaTileEntities.HULL[6].getStackForm(),
                'P', ELECTRIC_PUMP_ZPM.getStackForm()
        );

        ModHandler.addShapedRecipe(true, "sonicator", SONICATOR.getStackForm(),
                "LFL", "PHP", "CPC",
                'L', new UnificationEntry(pipeLargeFluid, Naquadah),
                'F', FIELD_GENERATOR_UV.getStackForm(),
                'P', ELECTRIC_PUMP_UV.getStackForm(),
                'H', MetaTileEntities.HULL[7].getStackForm(),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UV)
        );

        ModHandler.addShapedRecipe(true, "catalytic_reformer", CATALYTIC_REFORMER.getStackForm(),
                "MCM", "PHP", "MKM",
                'M', new UnificationEntry(pipeNormalFluid, StainlessSteel),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'P', ELECTRIC_PUMP_EV.getStackForm(),
                'H', MetaTileEntities.HULL[3].getStackForm(),
                'K', new UnificationEntry(cableGtDouble, Aluminium)
        );

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
                'H', SXA_HULL,
                'P', CraftingComponent.SENSOR,
                'C', CraftingComponent.CIRCUIT);



        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, KQCC_COMPUTATION_HATCH_RECEIVER,
                "GEG", "PHP", "WXW",
                'E', CraftingComponent.EMITTER,
                'H', CraftingComponent.HULL,
                'P', CraftingComponent.PLATE,
                'G', CraftingComponent.SENSOR,
                'W', CraftingComponent.CABLE,
                'X', CraftingComponent.CIRCUIT);

        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, KQCC_COMPUTATION_HATCH_TRANSMITTER,
                "EGE", "PHP", "WXW",
                'E', CraftingComponent.EMITTER,
                'H', CraftingComponent.HULL,
                'P', CraftingComponent.PLATE,
                'G', CraftingComponent.SENSOR,
                'W', CraftingComponent.CABLE,
                'X', CraftingComponent.CIRCUIT);

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

        //  General Circuits

        //  ULV
        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.ULV)
                .circuitMeta(5)
                .output(GENERAL_CIRCUIT_ULV)
                .EUt(VA[2])
                .duration(20)
                .buildAndRegister();

        //  LV
        ModHandler.addShapedRecipe("general_circuit.lv", GENERAL_CIRCUIT_LV.getStackForm(),
                " X ",
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.LV));

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.LV)
                .circuitMeta(5)
                .output(GENERAL_CIRCUIT_LV)
                .EUt(VA[2])
                .duration(20)
                .buildAndRegister();

        //  MV
        ModHandler.addShapedRecipe("general_circuit.mv", GENERAL_CIRCUIT_MV.getStackForm(),
                " X ",
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.MV));

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.MV)
                .circuitMeta(5)
                .output(GENERAL_CIRCUIT_MV)
                .EUt(VA[2])
                .duration(20)
                .buildAndRegister();

        //  HV
        ModHandler.addShapedRecipe("general_circuit.hv", GENERAL_CIRCUIT_HV.getStackForm(),
                " X ",
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.HV));

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.HV)
                .circuitMeta(5)
                .output(GENERAL_CIRCUIT_HV)
                .EUt(VA[2])
                .duration(20)
                .buildAndRegister();

        //  EV
        ModHandler.addShapedRecipe("general_circuit.ev", GENERAL_CIRCUIT_EV.getStackForm(),
                " X ",
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.EV));

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.EV)
                .circuitMeta(5)
                .output(GENERAL_CIRCUIT_EV)
                .EUt(VA[2])
                .duration(20)
                .buildAndRegister();

        //  IV
        ModHandler.addShapedRecipe("general_circuit.iv", GENERAL_CIRCUIT_IV.getStackForm(),
                " X ",
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.IV));

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.IV)
                .circuitMeta(5)
                .output(GENERAL_CIRCUIT_IV)
                .EUt(VA[2])
                .duration(20)
                .buildAndRegister();

        //  LuV
        ModHandler.addShapedRecipe("general_circuit.luv", GENERAL_CIRCUIT_LuV.getStackForm(),
                " X ",
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.LuV));

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.LuV)
                .circuitMeta(5)
                .output(GENERAL_CIRCUIT_LuV)
                .EUt(VA[2])
                .duration(20)
                .buildAndRegister();

        //  ZPM
        ModHandler.addShapedRecipe("general_circuit.zpm", GENERAL_CIRCUIT_ZPM.getStackForm(),
                " X ",
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.ZPM));

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.ZPM)
                .circuitMeta(5)
                .output(GENERAL_CIRCUIT_ZPM)
                .EUt(VA[2])
                .duration(20)
                .buildAndRegister();

        //  UV
        ModHandler.addShapedRecipe("general_circuit.uv", GENERAL_CIRCUIT_UV.getStackForm(),
                " X ",
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.UV));

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.UV)
                .circuitMeta(5)
                .output(GENERAL_CIRCUIT_UV)
                .EUt(VA[2])
                .duration(20)
                .buildAndRegister();

        //  UHV
        ModHandler.addShapedRecipe("general_circuit.uhv", GENERAL_CIRCUIT_UHV.getStackForm(),
                " X ",
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.UHV));

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.UHV)
                .circuitMeta(5)
                .output(GENERAL_CIRCUIT_UHV)
                .EUt(VA[2])
                .duration(20)
                .buildAndRegister();

        //  UEV
        ModHandler.addShapedRecipe("general_circuit.uev", GENERAL_CIRCUIT_UEV.getStackForm(),
                " X ",
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.UEV));

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.UEV)
                .circuitMeta(5)
                .output(GENERAL_CIRCUIT_UEV)
                .EUt(VA[2])
                .duration(20)
                .buildAndRegister();

        //  UIV
        ModHandler.addShapedRecipe("general_circuit.uiv", GENERAL_CIRCUIT_UIV.getStackForm(),
                " X ",
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.UIV));

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.UIV)
                .circuitMeta(5)
                .output(GENERAL_CIRCUIT_UIV)
                .EUt(VA[2])
                .duration(20)
                .buildAndRegister();

        //  UXV
        ModHandler.addShapedRecipe("general_circuit.uxv", GENERAL_CIRCUIT_UXV.getStackForm(),
                " X ",
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.UXV));

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.UXV)
                .circuitMeta(5)
                .output(GENERAL_CIRCUIT_UXV)
                .EUt(VA[2])
                .duration(20)
                .buildAndRegister();

        //  OpV
        ModHandler.addShapedRecipe("general_circuit.opv", GENERAL_CIRCUIT_OpV.getStackForm(),
                " X ",
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.OpV));

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.OpV)
                .circuitMeta(5)
                .output(GENERAL_CIRCUIT_OpV)
                .EUt(VA[2])
                .duration(20)
                .buildAndRegister();

        //  MAX
        ModHandler.addShapedRecipe("general_circuit.max", GENERAL_CIRCUIT_MAX.getStackForm(),
                " X ",
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.MAX));

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.MAX)
                .circuitMeta(5)
                .output(GENERAL_CIRCUIT_MAX)
                .EUt(VA[2])
                .duration(20)
                .buildAndRegister();

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
                'P', EMITTER_LV,
                'E', SENSOR_LV,
                'C', new UnificationEntry(OrePrefix.circuit, LV));

        ModHandler.addShapedRecipe(true, "fuel_cell.mv", GTQTMetaTileEntities.FUEL_CELL_TURBINE[1].getStackForm(),
                "PCP", "AMB", "ECE",
                'M', HULL[GTValues.MV].getStackForm(),
                'A', MetaTileEntities.COMBUSTION_GENERATOR[1].getStackForm(),
                'B', MetaTileEntities.GAS_TURBINE[1].getStackForm(),
                'P', EMITTER_MV,
                'E', SENSOR_MV,
                'C', new UnificationEntry(OrePrefix.circuit, MV));

        ModHandler.addShapedRecipe(true, "fuel_cell.hv", GTQTMetaTileEntities.FUEL_CELL_TURBINE[2].getStackForm(),
                "PCP", "AMB", "ECE",
                'M', HULL[GTValues.HV].getStackForm(),
                'A', MetaTileEntities.COMBUSTION_GENERATOR[2].getStackForm(),
                'B', MetaTileEntities.GAS_TURBINE[2].getStackForm(),
                'P', EMITTER_HV,
                'E', SENSOR_HV,
                'C', new UnificationEntry(OrePrefix.circuit, HV));

        ModHandler.addShapedRecipe(true, "fuel_cell.ev", GTQTMetaTileEntities.FUEL_CELL_TURBINE[3].getStackForm(),
                "PCP", "AMB", "ECE",
                'M', HULL[GTValues.EV].getStackForm(),
                'A', GTQTMetaTileEntities.COMBUSTION_GENERATOR[0].getStackForm(),
                'B', GTQTMetaTileEntities.GAS_TURBINE[0].getStackForm(),
                'P', EMITTER_EV,
                'E', SENSOR_EV,
                'C', new UnificationEntry(OrePrefix.circuit, EV));

        ModHandler.addShapedRecipe(true, "fuel_cell.iv", GTQTMetaTileEntities.FUEL_CELL_TURBINE[4].getStackForm(),
                "PCP", "AMB", "ECE",
                'M', HULL[GTValues.IV].getStackForm(),
                'A', GTQTMetaTileEntities.COMBUSTION_GENERATOR[1].getStackForm(),
                'B', GTQTMetaTileEntities.GAS_TURBINE[1].getStackForm(),
                'P', EMITTER_IV,
                'E', SENSOR_IV,
                'C', new UnificationEntry(OrePrefix.circuit, IV));


        ModHandler.addShapedRecipe(true, "rtg.ev", GTQTMetaTileEntities.RTG[0].getStackForm(),
                "PCP", "AMA", "ECE",
                'M', HULL[GTValues.EV].getStackForm(),
                'A', new UnificationEntry(OrePrefix.spring, TungstenSteel),
                'P', ELECTRIC_MOTOR_EV,
                'E', SENSOR_EV,
                'C', new UnificationEntry(OrePrefix.circuit, EV));

        ModHandler.addShapedRecipe(true, "rtg.iv", GTQTMetaTileEntities.RTG[1].getStackForm(),
                "PCP", "AMA", "ECE",
                'M', HULL[GTValues.IV].getStackForm(),
                'A', new UnificationEntry(OrePrefix.spring, RhodiumPlatedPalladium),
                'P', ELECTRIC_MOTOR_IV,
                'E', SENSOR_IV,
                'C', new UnificationEntry(OrePrefix.circuit, IV));

        ModHandler.addShapedRecipe(true, "rtg.luv", GTQTMetaTileEntities.RTG[2].getStackForm(),
                "PCP", "AMA", "ECE",
                'M', HULL[GTValues.LuV].getStackForm(),
                'A', new UnificationEntry(OrePrefix.spring, NaquadahAlloy),
                'P', ELECTRIC_MOTOR_LuV,
                'E', SENSOR_LuV,
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.LuV));


        ModHandler.addShapedRecipe(true, "lightning_rod.iv", GTQTMetaTileEntities.LIGHTNING_ROD[0].getStackForm(),
                "AMA", "MCM", "AMA",
                'M', HULL[GTValues.IV].getStackForm(),
                'A', ENERGY_LAPOTRONIC_ORB,
                'C', new UnificationEntry(OrePrefix.circuit, IV));

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

        registerMachineRecipe(GTQTMetaTileEntities.LATEX_COLLECTOR,
                "PCP", "AMA", "PCP",
                'M', CraftingComponent.HULL,
                'A', PIPE_NORMAL,
                'C', GLASS,
                'P', CraftingComponent.PUMP);

        ModHandler.addShapedRecipe(true, "latex_collector.bronze",
                STEAM_LATEX_COLLECTOR[0].getStackForm(), "XCX", "PMP", "XCX",
                'M', MetaBlocks.STEAM_CASING.getItemVariant(BRONZE_HULL),
                'C', new UnificationEntry(OrePrefix.pipeSmallFluid, Bronze),
                'X', new UnificationEntry(OrePrefix.plate, Rubber),
                'P', new UnificationEntry(gear, Bronze));

        ModHandler.addShapedRecipe(true, "latex_collector.steel",
                STEAM_LATEX_COLLECTOR[1].getStackForm(), "XCX", "PMP", "XCX",
                'M', MetaTileEntities.STEAM_COMPRESSOR_BRONZE.getStackForm(),
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
    }
}
