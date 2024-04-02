package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.ingredients.nbtmatch.NBTCondition;
import gregtech.api.recipes.ingredients.nbtmatch.NBTMatcher;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.BlockFusionCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraftforge.oredict.OreDictionary;

import static gregicality.multiblocks.api.unification.GCYMMaterials.TantalumCarbide;
import static gregicality.multiblocks.api.unification.GCYMMaterials.WatertightSteel;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.MarkerMaterials.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_COIL;
import static gregtech.common.blocks.BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL;
import static gregtech.common.blocks.BlockMetalCasing.MetalCasingType.BRONZE_BRICKS;
import static gregtech.common.blocks.MetaBlocks.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.ADVANCED_FLUID_DRILLING_RIG;
import static gregtech.common.metatileentities.MetaTileEntities.FLUID_DRILLING_RIG;
import static gregtech.common.metatileentities.MetaTileEntities.FUSION_REACTOR;

import static gregtech.common.blocks.BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static gregtech.common.metatileentities.MetaTileEntities.ROTOR_HOLDER;
import static gregtechfoodoption.machines.GTFOTileEntities.GREENHOUSE;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.block.blocks.GTQTKQCC.CasingType.COMPUTER_VENT;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.COMBUSTION_GENERATOR;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.GAS_TURBINE;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.STEAM_TURBINE;

public class KeQingNET {

    public static void init() {
        Pre();
        I_VV();
        VVI_VVVV();

    }

    private static void VVI_VVVV() {
        //11 网道行者-元素存储
        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .input(DISK_0)
                .notConsumable(gem,Fluix)
                .output(DISK_11)
                .EUt(7680)
                .CWUt(128)
                .NB(11)
                .totalCWU(100000)
                .buildAndRegister();

        OreDictionary.registerOre("crystalFluix", OreDictUnifier.get(OrePrefix.gem, Fluix));

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[HV])
                .input(gem,Fluix)
                .input(EMITTER_HV)
                .input(plate,StainlessSteel,4)
                .fluidInputs(Polybenzimidazole.getFluid(GTValues.L * 4))
                .output(AE_FLUIX_FIRM)
                .scannerResearch(b -> b.researchStack(DISK_11.getStackForm()).duration(1200).EUt(VA[HV]))
                .duration(100).buildAndRegister();
        //12 网道行者-管网系统

        //13 网道行者-无线传输
    }

    private static void Pre() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(wireFine, BorosilicateGlass, 8)
                .input(foil, Silver, 8)
                .input(plateDense, Aluminium, 1)
                .fluidInputs(Polyethylene.getFluid(L))
                .output(OPTICAL_PIPES[0])
                .duration(100).EUt(VA[MV]).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(wireFine, BorosilicateGlass, 8)
                .input(foil, Silver, 8)
                .input(plateDense, Aluminium, 1)
                .fluidInputs(Epoxy.getFluid(L))
                .output(OPTICAL_PIPES[0],2)
                .duration(100).EUt(VA[MV]).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(wireFine, BorosilicateGlass, 8)
                .input(foil, Silver, 8)
                .input(plateDense, Aluminium, 1)
                .fluidInputs(Polytetrafluoroethylene.getFluid(L))
                .output(OPTICAL_PIPES[0],4)
                .duration(100).EUt(VA[MV]).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(wireFine, BorosilicateGlass, 8)
                .input(foil, Silver, 8)
                .input(plateDense, Aluminium, 1)
                .fluidInputs(KaptonK.getFluid(L))
                .output(OPTICAL_PIPES[0],8)
                .duration(100).EUt(VA[MV]).buildAndRegister();

        //物品支架
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(GOOD_CIRCUIT_BOARD)
                .input(circuit, Tier.MV,2)
                .input(wireFine, Aluminium, 2)
                .input(gearSmall, Copper, 4)
                .input(plateDense, Aluminium, 4)
                .input(OPTICAL_PIPES[0], 2)
                .fluidInputs(Polyethylene.getFluid(L))
                .output(DISK_0)
                .duration(1000).EUt(120).buildAndRegister();

        //软盘
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(ITEM_IMPORT_BUS[MV])
                .input(EMITTER_MV, 8)
                .input(circuit, Tier.MV)
                .input(ROBOT_ARM_MV, 2)
                .input(ELECTRIC_MOTOR_MV, 2)
                .input(wireGtDouble, Aluminium, 16)
                .input(OPTICAL_PIPES[0], 2)
                .fluidInputs(Polyethylene.getFluid(L * 4))
                .output(OBJECT_HOLDER)
                .duration(1000).EUt(120).buildAndRegister();

        //计算框架
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt,Steel, 4)
                .input(plate, Aluminium, 4)
                .input(OPTICAL_PIPES[0], 2)
                .input(plateDense, Aluminium, 4)
                .fluidInputs(Polytetrafluoroethylene.getFluid(L*8))
                .outputs( GTQTMetaBlocks.KQCC.getItemVariant(COMPUTER_VENT))
                .duration(1000).EUt(120).buildAndRegister();
        //数据仓
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(ITEM_IMPORT_BUS[MV])
                .input(DISK_0)
                .input(circuit, Tier.MV, 4)
                .input(plateDense, Aluminium, 4)
                .output(EDATA_ACCESS_HATCH)
                .fluidInputs(Polytetrafluoroethylene.getFluid(L * 2))
                .duration(200).EUt(VA[HV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(ITEM_IMPORT_BUS[UV])
                .inputNBT(TOOL_DATA_ORB, 4, NBTMatcher.ANY, NBTCondition.ANY)
                .input(circuit, Tier.UHV, 4)
                .input(plateDense, Aluminium, 4)
                .output(FDATA_ACCESS_HATCH)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .stationResearch(b -> b.researchStack(ADVANCED_DATA_ACCESS_HATCH.getStackForm()).CWUt(2048))
                .duration(4000000).EUt(6000).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(GREENHOUSE,4)
                .input(circuit, Tier.HV, 4)
                .input(ELECTRIC_PUMP_HV, 4)
                .input(ROBOT_ARM_HV, 4)
                .input(plateDense, StainlessSteel, 4)
                .input(foil, StainlessSteel, 64)
                .output(GREEN_HOUSE_PLUS)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .scannerResearch(b -> b
                        .researchStack(GREENHOUSE.getStackForm())
                        .EUt(VA[HV]))
                .duration(4000).EUt(1960).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm())
                .input(EMITTER_MV, 4)
                .input(SENSOR_MV, 4)
                .input(circuit, Tier.MV, 4)
                .input(wireFine, Copper, 32)
                .input(foil, Aluminium, 64)
                .input(OPTICAL_PIPES[0], 8)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(Polyethylene.getFluid(L * 4))
                .output(KQNS)
                .scannerResearch(b -> b
                        .researchStack(DISK_0.getStackForm())
                        .EUt(VA[MV]))
                .duration(200).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm())
                .input(FIELD_GENERATOR_LV, 8)
                .input(DISK_0,4)
                .input(COVER_SCREEN)
                .input(wireFine, Aluminium, 16)
                .input(OPTICAL_PIPES[0], 8)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .output(KeQing_NET)
                .duration(200).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm())
                .input(EMITTER_LV, 8)
                .input(DISK_0,4)
                .input(COVER_SCREEN)
                .input(wireFine, Aluminium, 16)
                .input(OPTICAL_PIPES[0], 8)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .output(KQCC)
                .duration(200).EUt(120).buildAndRegister();

        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(PLASTIC_CIRCUIT_BOARD)
                .input(circuit, Tier.MV, 2)
                .input(CENTRAL_PROCESSING_UNIT, 4)
                .input(RANDOM_ACCESS_MEMORY, 4)
                .input(wireFine, Aluminium, 32)
                .output(TOOL_DATA_STICK)
                .solderMultiplier(2)
                .duration(1000).EUt(120).buildAndRegister();
        //BIOLOGICAL_REACTION
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[3].getStackForm(16))
                .input(circuit, Tier.MV, 8)
                .input(ELECTRIC_MOTOR_MV,16)
                .input(ELECTRIC_PUMP_MV,16)
                .input(rotor,Steel,8)
                .input(QUANTUM_TANK[1], 4)
                .input(pipeHugeFluid, Steel, 4)
                .input(plate, Aluminium, 8)
                .fluidInputs(Polytetrafluoroethylene.getFluid(4000))
                .fluidInputs(Aluminium.getFluid(4000))
                .scannerResearch(b -> b
                        .researchStack(SEPTIC_TANK.getStackForm())
                        .duration(1200)
                        .EUt(VA[MV]))
                .output(BIOLOGICAL_REACTION)
                .duration(200).EUt(120).buildAndRegister();
        //DIGESTER
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[3].getStackForm(16))
                .input(circuit, Tier.HV, 8)
                .input(ELECTRIC_MOTOR_HV,16)
                .input(VOLTAGE_COIL_HV,16)
                .input(rotor,StainlessSteel,8)
                .input(QUANTUM_TANK[2], 4)
                .input(pipeHugeFluid, Polytetrafluoroethylene, 4)
                .input(plate, NanometerBariumTitanate, 8)
                .fluidInputs(Polytetrafluoroethylene.getFluid(4000))
                .fluidInputs(TungstenSteel.getFluid(4000))
                .scannerResearch(b -> b
                        .researchStack(REACTION_FURNACE.getStackForm())
                        .duration(1200)
                        .EUt(VA[MV]))
                .output(DIGESTER)
                .duration(2000).EUt(480).buildAndRegister();
        //ALGAE_FARM
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[3].getStackForm(16))
                .input(circuit, Tier.HV, 8)
                .input(ELECTRIC_PUMP_HV,16)
                .input(VOLTAGE_COIL_MV,16)
                .input(gear,StainlessSteel,8)
                .input(RANDOM_ACCESS_MEMORY, 4)
                .input(wireFine, Aluminium, 8)
                .input(foil, Aluminium, 8)
                .fluidInputs(Polytetrafluoroethylene.getFluid(4000))
                .scannerResearch(b -> b
                        .researchStack(BIOLOGICAL_REACTION.getStackForm())
                        .duration(1200)
                        .EUt(VA[HV]))
                .output(ALGAE_FARM)
                .duration(2000).EUt(480).buildAndRegister();
        //op
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[3].getStackForm(16))
                .input(circuit, Tier.HV, 8)
                .input(ELECTRIC_MOTOR_HV,16)
                .input(ELECTRIC_PUMP_HV,16)
                .input(gear,StainlessSteel,8)
                .input(RANDOM_ACCESS_MEMORY, 4)
                .input(wireFine, Aluminium, 8)
                .input(foil, Aluminium, 8)
                .fluidInputs(Polytetrafluoroethylene.getFluid(4000))
                .scannerResearch(b -> b
                        .researchStack(MACERATOR[3].getStackForm())
                        .duration(1200)
                        .EUt(VA[MV]))
                .output(INTEGRATED_MINING_DIVISION)
                .duration(2000).EUt(480).buildAndRegister();

        //Arc
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm(16))
                .input(circuit, Tier.HV, 8)
                .input(ELECTRIC_MOTOR_MV,16)
                .input(ELECTRIC_PUMP_MV,16)
                .input(gear,Aluminium,8)
                .input(wireFine, Aluminium, 8)
                .input(foil, Aluminium, 8)
                .fluidInputs(Bps.getFluid(4000))
                .output(ELECTRIC_ARC_FURNACE)
                .duration(400).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm(8))
                .input(circuit, Tier.HV, 8)
                .input(ELECTRIC_PISTON_MV,16)
                .input(ELECTRIC_PUMP_MV,16)
                .input(pipeHugeFluid,Steel,8)
                .input(gear,Aluminium,8)
                .input(rotor, Aluminium, 8)
                .fluidInputs(Epoxy.getFluid(4000))
                .output(FLUIDIZED_BED)
                .duration(200).EUt(120).buildAndRegister();

    }
    private static void I_VV() {

        //1 涡轮效率升级I 大型涡轮
        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .input(DISK_0)
                .notConsumable(MetaTileEntities.COMBUSTION_GENERATOR[2].getStackForm())
                .output(DISK_1)
                .EUt(7680)
                .CWUt(32)
                .NB(1)
                .totalCWU(100000)
                .buildAndRegister();

        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .input(DISK_0)
                .notConsumable(MetaTileEntities.GAS_TURBINE[2].getStackForm())
                .output(DISK_1)
                .EUt(7680)
                .CWUt(32)
                .NB(1)
                .totalCWU(100000)
                .buildAndRegister();

        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .input(DISK_0)
                .notConsumable(MetaTileEntities.STEAM_TURBINE[2].getStackForm())
                .output(DISK_1)
                .EUt(7680)
                .CWUt(32)
                .NB(1)
                .totalCWU(100000)
                .buildAndRegister();
        //
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[EV])
                .input(ELECTRIC_PISTON_EV,4)
                .input(ELECTRIC_MOTOR_EV,4)
                .input(circuit, Tier.EV, 2)
                .input(HULL[EV])
                .input(cableGtSingle,Aluminium,2)
                .input(gear,Titanium,2)
                .fluidInputs(Polytetrafluoroethylene.getFluid(GTValues.L * 24))
                .outputs(COMBUSTION_GENERATOR[0].getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_1.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .duration(100).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[IV])
                .input(ELECTRIC_PISTON_IV,4)
                .input(ELECTRIC_MOTOR_IV,4)
                .input(circuit, Tier.IV, 2)
                .input(HULL[IV])
                .input(cableGtSingle,Platinum,2)
                .input(gear,TungstenSteel,2)
                .fluidInputs(Polybenzimidazole.getFluid(GTValues.L * 24))
                .outputs(COMBUSTION_GENERATOR[1].getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_1.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(100).buildAndRegister();
        //
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[EV])
                .input(ELECTRIC_PISTON_EV,4)
                .input(ELECTRIC_MOTOR_EV,4)
                .input(circuit, Tier.EV, 2)
                .input(HULL[EV])
                .input(cableGtSingle,Aluminium,2)
                .input(rotor,Titanium,2)
                .fluidInputs(Polytetrafluoroethylene.getFluid(GTValues.L * 24))
                .outputs(GAS_TURBINE[0].getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_1.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .duration(100).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[IV])
                .input(ELECTRIC_PISTON_IV,4)
                .input(ELECTRIC_MOTOR_IV,4)
                .input(circuit, Tier.IV, 2)
                .input(HULL[IV])
                .input(cableGtSingle,Platinum,2)
                .input(rotor,TungstenSteel,2)
                .fluidInputs(Polybenzimidazole.getFluid(GTValues.L * 24))
                .outputs(GAS_TURBINE[1].getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_1.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(100).buildAndRegister();
        //
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[EV])
                .input(ELECTRIC_PISTON_EV,4)
                .input(ELECTRIC_MOTOR_EV,4)
                .input(circuit, Tier.EV, 2)
                .input(HULL[EV])
                .input(cableGtSingle,Aluminium,2)
                .input(pipeNormalFluid,Titanium,2)
                .fluidInputs(Polytetrafluoroethylene.getFluid(GTValues.L * 24))
                .outputs(STEAM_TURBINE[0].getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_1.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .duration(100).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[IV])
                .input(ELECTRIC_PISTON_IV,4)
                .input(ELECTRIC_MOTOR_IV,4)
                .input(circuit, Tier.IV, 2)
                .input(HULL[IV])
                .input(cableGtSingle,Platinum,2)
                .input(pipeNormalFluid,TungstenSteel,2)
                .fluidInputs(Polybenzimidazole.getFluid(GTValues.L * 24))
                .outputs(STEAM_TURBINE[1].getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_1.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(100).buildAndRegister();



        //2 涡轮效率升级II 小火箭
        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .input(DISK_0)
                .notConsumable(COMBUSTION_GENERATOR[1].getStackForm())
                .output(DISK_2)
                .EUt(7680)
                .CWUt(96)
                .NB(2)
                .totalCWU(100000)
                .buildAndRegister();

        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .input(DISK_0)
                .notConsumable(GAS_TURBINE[1].getStackForm())
                .output(DISK_2)
                .EUt(7680)
                .CWUt(96)
                .NB(2)
                .totalCWU(100000)
                .buildAndRegister();

        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .input(DISK_0)
                .notConsumable(STEAM_TURBINE[1].getStackForm())
                .output(DISK_2)
                .EUt(7680)
                .CWUt(96)
                .NB(2)
                .totalCWU(100000)
                .buildAndRegister();

        //火箭
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[EV])
                .input(ELECTRIC_PISTON_EV,8)
                .input(ELECTRIC_MOTOR_EV,8)
                .input(circuit, Tier.EV, 4)
                .input(HULL[EV])
                .input(wireGtDouble,UraniumTriplatinum,8)
                .input(OrePrefix.foil, Titanium, 32)
                .fluidInputs(Materials.Polytetrafluoroethylene.getFluid(GTValues.L * 24))
                .outputs(ROCKET_ENGINE[0].getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_2.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(100).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[IV])
                .input(ELECTRIC_PISTON_IV,8)
                .input(ELECTRIC_MOTOR_IV,8)
                .input(circuit, Tier.IV, 4)
                .input(HULL[IV])
                .input(wireGtDouble,SamariumIronArsenicOxide,8)
                .input(OrePrefix.foil, TungstenSteel, 32)
                .fluidInputs(Materials.Polybenzimidazole.getFluid(GTValues.L * 24))
                .outputs(ROCKET_ENGINE[1].getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_2.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(100).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[LuV])
                .input(ELECTRIC_PISTON_LUV,8)
                .input(ELECTRIC_MOTOR_LuV,8)
                .input(circuit, Tier.LuV, 4)
                .input(HULL[LuV])
                .input(wireGtDouble,IndiumTinBariumTitaniumCuprate,8)
                .input(OrePrefix.foil, Materials.NiobiumTitanium, 32)
                .fluidInputs(Materials.Trinium.getFluid(GTValues.L * 24))
                .outputs(ROCKET_ENGINE[2].getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_2.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(100).buildAndRegister();

        //大柴
        ModHandler.removeRecipeByName("gregtech:large_combustion_engine");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[EV])
                .input(ELECTRIC_PISTON_EV,8)
                .input(ELECTRIC_MOTOR_EV,8)
                .input(circuit, Tier.EV, 8)
                .input(HULL[EV],4)
                .input(wireGtDouble,UraniumTriplatinum,16)
                .input(gear,Titanium,2)
                .input(plateDouble, Titanium, 32)
                .fluidInputs(Polytetrafluoroethylene.getFluid(GTValues.L * 24))
                .outputs(LARGE_COMBUSTION_ENGINE.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_2.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .duration(100).buildAndRegister();

        ModHandler.removeRecipeByName("gregtech:extreme_combustion_engine");
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[IV])
                .input(ELECTRIC_PISTON_IV,8)
                .input(ELECTRIC_MOTOR_IV,8)
                .input(circuit, Tier.IV, 8)
                .input(HULL[IV],4)
                .input(wireGtDouble,SamariumIronArsenicOxide,16)
                .input(gear,TungstenSteel,2)
                .input(plateDouble, TungstenSteel, 32)
                .fluidInputs(Polybenzimidazole.getFluid(GTValues.L * 24))
                .outputs(EXTREME_COMBUSTION_ENGINE.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_2.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(100).buildAndRegister();

        //3 涡轮效率升级III 特大轮机
        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .input(DISK_0)
                .notConsumable(ROCKET_ENGINE[2].getStackForm())
                .output(DISK_3)
                .EUt(7680)
                .CWUt(1024)
                .NB(3)
                .totalCWU(100000)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(LARGE_STEAM_TURBINE,6)
                .input(plate, WatertightSteel, 8)
                .input(circuit, MarkerMaterials.Tier.LuV, 32)
                .input(ELECTRIC_PUMP_IV, 4)
                .input(FLUID_REGULATOR_IV, 4)
                .input(gear, TanmolyiumBetaC, 8)
                .input(screw, MARM200Steel, 16)
                .fluidInputs(BlueSteel.getFluid(L * 16))
                .stationResearch(b -> b
                        .researchStack(DISK_3.getStackForm())
                        .CWUt(1000,1000000)
                        .EUt(VA[IV]))
                .output(MEGA_STEAM_TURBINE)
                .EUt(VA[IV])
                .duration(1200)
                .buildAndRegister();

        //  Mega Gas Turbine
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(LARGE_GAS_TURBINE,6)
                .input(plate, TantalumCarbide, 8)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(ELECTRIC_PUMP_LuV, 4)
                .input(FLUID_REGULATOR_LUV, 4)
                .input(rotor, Staballoy, 8)
                .input(screw, IncoloyMA813, 16)
                .fluidInputs(Naquadah.getFluid(L * 16))
                .stationResearch(b -> b
                        .researchStack(DISK_3.getStackForm())
                        .CWUt(4000,1000000)
                        .EUt(VA[LuV]))
                .output(MEGA_GAS_TURBINE)
                .EUt(VA[LuV])
                .duration(1200)
                .buildAndRegister();

        //  Mega Plasma Turbine
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(LARGE_PLASMA_TURBINE,6)
                .input(plate, HMS1J79Alloy, 8)
                .input(circuit, MarkerMaterials.Tier.UV, 32)
                .input(ELECTRIC_PUMP_ZPM, 4)
                .input(FLUID_REGULATOR_ZPM, 4)
                .input(spring, Pikyonium64B, 8)
                .input(screw, Trinium, 16)
                .fluidInputs(NaquadahAlloy.getFluid(L * 16))
                .stationResearch(b -> b
                        .researchStack(DISK_3.getStackForm())
                        .CWUt(16000,1000000)
                        .EUt(VA[ZPM]))
                .output(MEGA_PLASMA_TURBINE)
                .EUt(VA[ZPM])
                .duration(1200)
                .buildAndRegister();

        //  ZPM
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, NaquadahAlloy)
                .input(ROTOR_HOLDER[5])
                .input(ELECTRIC_MOTOR_ZPM, 2)
                .input(rotor, Inconel792, 4)
                .input(stickLong, TungstenSteel, 2)
                .input(wireFine, NiobiumTitanium, 16)
                .fluidInputs(SolderingAlloy.getFluid(L * 2))
                .output(MULTIPART_REINFORCED_ROTOR_HOLDER[1])
                .stationResearch(b -> b
                        .researchStack(DISK_3.getStackForm())
                        .CWUt(1000,1000000)
                        .EUt(VA[ZPM]))
                .EUt(VA[ZPM])
                .duration(1200)
                .buildAndRegister();

        //  UV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Darmstadtium)
                .input(MULTIPART_REINFORCED_ROTOR_HOLDER[1])
                .input(ELECTRIC_MOTOR_UV, 2)
                .input(rotor, Inconel625, 4)
                .input(stickLong, RhodiumPlatedPalladium, 2)
                .input(wireFine, VanadiumGallium, 16)
                .fluidInputs(SolderingAlloy.getFluid(L * 2))
                .output(MULTIPART_REINFORCED_ROTOR_HOLDER[2])
                .stationResearch(b -> b
                        .researchStack(DISK_3.getStackForm())
                        .CWUt(1000,1000000)
                        .EUt(VA[ZPM]))
                .EUt(VA[UV])
                .duration(1200)
                .buildAndRegister();

        //  UHV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Orichalcum)
                .input(MULTIPART_REINFORCED_ROTOR_HOLDER[2])
                .input(ELECTRIC_MOTOR_UHV, 2)
                .input(CONVEYOR_MODULE_UHV, 2)
                .input(rotor, Adamantium, 4)
                .input(stickLong, HSSS, 2)
                .input(wireFine, YttriumBariumCuprate, 32)
                .fluidInputs(SolderingAlloy.getFluid(L * 10))
                .output(MULTIPART_REINFORCED_ROTOR_HOLDER[3])
                .EUt(VA[UHV])
                .duration(1200)
                .stationResearch(b -> b
                        .researchStack(DISK_3.getStackForm())
                        .CWUt(1000,1000000)
                        .EUt(VA[ZPM]))
                .buildAndRegister();
        //4 蒸燃联合体系 爆燃 大火箭
        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .input(DISK_0)
                .notConsumable(ROCKET_ENGINE[0].getStackForm())
                .output(DISK_4)
                .EUt(7680)
                .CWUt(128)
                .NB(4)
                .totalCWU(100000)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[IV])
                .input(ELECTRIC_PISTON_IV,16)
                .input(ELECTRIC_MOTOR_IV,16)
                .input(circuit, Tier.IV, 8)
                .input(HULL[IV],4)
                .input(wireGtDouble,SamariumIronArsenicOxide,8)
                .input(plate, TungstenSteel, 32)
                .fluidInputs(Materials.Polybenzimidazole.getFluid(GTValues.L * 24))
                .outputs(HUGE_TURBINE_COMBUSTION_CHAMBER.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_4.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(100).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[LuV])
                .input(ELECTRIC_PISTON_LUV,16)
                .input(ELECTRIC_MOTOR_LuV,16)
                .input(circuit, Tier.LuV, 4)
                .input(HULL[LuV],4)
                .input(wireGtDouble,IndiumTinBariumTitaniumCuprate,8)
                .input(plate, Materials.NiobiumTitanium, 32)
                .fluidInputs(Materials.Trinium.getFluid(GTValues.L * 24))
                .outputs(ROCKET.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_4.getStackForm())
                        .duration(1200)
                        .EUt(VA[LuV]))
                .duration(100).buildAndRegister();
        //5 室温超导设计
        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .input(DISK_0)
                .notConsumable(wireGtSingle,IndiumTinBariumTitaniumCuprate)
                .output(DISK_5)
                .EUt(7680)
                .CWUt(256)
                .NB(5)
                .totalCWU(100000)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[LuV])
                .input(OrePrefix.wireGtDouble, Materials.IndiumTinBariumTitaniumCuprate, 32)
                .input(OrePrefix.foil, Materials.NiobiumTitanium, 32)
                .fluidInputs(Materials.Trinium.getFluid(GTValues.L * 24))
                .outputs(MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL))
                .scannerResearch(b -> b
                        .researchStack(DISK_5.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(100).buildAndRegister();

        //6 可控核聚变-环流器设计
        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .input(DISK_0)
                .notConsumable(FUSION_REACTOR[0].getStackForm())
                .output(DISK_6)
                .EUt(7680)
                .CWUt(2)
                .NB(6)
                .totalCWU(100000)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(FUSION_CASING.getItemVariant(SUPERCONDUCTOR_COIL))
                .input(circuit, Tier.ZPM, 8)
                .input(plateDouble, Plutonium241,4)
                .input(plateDouble, Osmiridium,4)
                .input(FIELD_GENERATOR_IV, 2)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, IndiumTinBariumTitaniumCuprate, 32)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(NiobiumTitanium.getFluid(L * 8))
                .outputs(EFUSION_REACTOR[0].getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_6.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(800).EUt(VA[LuV]).buildAndRegister();

        //7 可控核聚变-聚变超导线圈
        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .input(DISK_0)
                .notConsumable(wireGtSingle,SamariumIronArsenicOxide)
                .output(DISK_7)
                .EUt(7680)
                .CWUt(256)
                .NB(7)
                .totalCWU(100000)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL))
                .inputs(MetaItems.FIELD_GENERATOR_IV.getStackForm(2)).inputs(MetaItems.ELECTRIC_PUMP_IV.getStackForm())
                .inputs(MetaItems.NEUTRON_REFLECTOR.getStackForm(2))
                .input(OrePrefix.circuit, MarkerMaterials.Tier.LuV, 4)
                .input(OrePrefix.pipeSmallFluid, Materials.Naquadah, 4).input(OrePrefix.plate, Materials.Europium, 4)
                .fluidInputs(Materials.VanadiumGallium.getFluid(GTValues.L * 4))
                .outputs(MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.FUSION_COIL))
                .scannerResearch(b -> b
                        .researchStack(DISK_7.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(800).EUt(VA[ZPM]).buildAndRegister();
        //8 资源勘探I
        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .input(DISK_0)
                .notConsumable(SENSOR_HV)
                .output(DISK_8)
                .EUt(480)
                .CWUt(30)
                .NB(8)
                .totalCWU(100000)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[EV])
                .input(frameGt, Titanium, 4)
                .input(circuit, Tier.EV, 4)
                .input(ELECTRIC_MOTOR_EV, 4)
                .input(ELECTRIC_PUMP_EV, 4)
                .input(CONVEYOR_MODULE_EV, 4)
                .input(gear, Tungsten, 4)
                .circuitMeta(2)
                .fluidInputs(Polyethylene.getFluid(GTValues.L * 4))
                .output(BASIC_LARGE_MINER)
                .scannerResearch(b -> b
                        .researchStack(DISK_8.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .duration(400).EUt(VA[EV]).buildAndRegister();


        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[EV])
                .input(frameGt, Titanium, 4)
                .input(circuit, Tier.EV, 4)
                .input(ELECTRIC_MOTOR_EV, 4)
                .input(ELECTRIC_PUMP_EV, 4)
                .input(gear, TungstenCarbide, 4)
                .circuitMeta(2)
                .fluidInputs(Polyethylene.getFluid(GTValues.L * 4))
                .output(FLUID_DRILLING_RIG)
                .scannerResearch(b -> b
                        .researchStack(DISK_8.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .duration(400).EUt(VA[EV]).buildAndRegister();

        //9 资源勘探II
        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .input(DISK_0)
                .notConsumable(SENSOR_IV)
                .output(DISK_9)
                .EUt(7680)
                .CWUt(256)
                .NB(8)
                .totalCWU(100000)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[IV])
                .input(frameGt, TungstenSteel, 4)
                .input(circuit, Tier.IV, 4)
                .input(ELECTRIC_MOTOR_IV, 4)
                .input(ELECTRIC_PUMP_IV, 4)
                .input(CONVEYOR_MODULE_IV, 4)
                .input(gear, Iridium, 4)
                .circuitMeta(2)
                .fluidInputs(ReinforcedEpoxyResin.getFluid(GTValues.L * 4))
                .fluidInputs(Polytetrafluoroethylene.getFluid(GTValues.L * 4))
                .fluidInputs(Polybenzimidazole.getFluid(GTValues.L * 4))
                .output(LARGE_MINER)
                .stationResearch(b -> b
                        .researchStack(DISK_9.getStackForm())
                        .CWUt(1000,1000000)
                        .EUt(VA[IV]))
                .duration(400).EUt(VA[IV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[LuV])
                .input(frameGt, TungstenSteel, 4)
                .input(circuit, Tier.LuV, 4)
                .input(ELECTRIC_MOTOR_LuV, 4)
                .input(ELECTRIC_PUMP_LuV, 4)
                .input(gear, Osmiridium, 4)
                .fluidInputs(ReinforcedEpoxyResin.getFluid(GTValues.L * 4))
                .fluidInputs(Polytetrafluoroethylene.getFluid(GTValues.L * 4))
                .fluidInputs(Polybenzimidazole.getFluid(GTValues.L * 4))
                .circuitMeta(2)
                .output(ADVANCED_FLUID_DRILLING_RIG)
                .stationResearch(b -> b
                        .researchStack(DISK_9.getStackForm())
                        .CWUt(1000,1000000)
                        .EUt(VA[LuV]))
                .duration(400).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[LuV])
                .input(frameGt, HSSS, 4)
                .input(circuit, Tier.LuV, 4)
                .input(ELECTRIC_MOTOR_LuV, 4)
                .input(ELECTRIC_PUMP_LuV, 4)
                .input(CONVEYOR_MODULE_LuV, 4)
                .input(gear, Ruridit, 4)
                .fluidInputs(ReinforcedEpoxyResin.getFluid(GTValues.L * 4))
                .fluidInputs(Polytetrafluoroethylene.getFluid(GTValues.L * 4))
                .fluidInputs(Polybenzimidazole.getFluid(GTValues.L * 4))
                .circuitMeta(2)
                .output(ADVANCED_LARGE_MINER)
                .stationResearch(b -> b
                        .researchStack(DISK_9.getStackForm())
                        .CWUt(1000,4000000)
                        .EUt(VA[LuV]))
                .duration(400).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[LuV])
                .input(frameGt, TungstenSteel, 4)
                .input(circuit, Tier.LuV, 4)
                .input(ELECTRIC_MOTOR_LuV, 4)
                .input(ELECTRIC_PUMP_LuV, 4)
                .input(gear, Osmiridium, 4)
                .fluidInputs(ReinforcedEpoxyResin.getFluid(GTValues.L * 4))
                .fluidInputs(Polytetrafluoroethylene.getFluid(GTValues.L * 4))
                .fluidInputs(Polybenzimidazole.getFluid(GTValues.L * 4))
                .circuitMeta(2)
                .output(ADVANCED_FLUID_DRILLING_RIG)
                .stationResearch(b -> b
                        .researchStack(DISK_9.getStackForm())
                        .CWUt(1000,4000000)
                        .EUt(VA[LuV]))
                .duration(400).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[ZPM])
                .input(frameGt, RhodiumPlatedPalladium, 4)
                .input(circuit, Tier.ZPM, 4)
                .input(ELECTRIC_MOTOR_ZPM, 4)
                .input(ELECTRIC_PUMP_ZPM, 4)
                .input(gear, RhodiumPlatedPalladium, 4)
                .fluidInputs(ReinforcedEpoxyResin.getFluid(GTValues.L * 4))
                .fluidInputs(Polytetrafluoroethylene.getFluid(GTValues.L * 4))
                .fluidInputs(Polybenzimidazole.getFluid(GTValues.L * 4))
                .circuitMeta(2)
                .output(GTQTMetaTileEntities.BASIC_FLUID_DRILLING_RIG)
                .stationResearch(b -> b
                        .researchStack(DISK_9.getStackForm())
                        .CWUt(1000,4000000)
                        .EUt(VA[LuV]))
                .duration(400).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[UV])
                .input(frameGt, NaquadahAlloy, 4)
                .input(circuit, Tier.UV, 4)
                .input(ELECTRIC_MOTOR_UV, 4)
                .input(ELECTRIC_PUMP_UV, 4)
                .input(gear, NaquadahAlloy, 4)
                .circuitMeta(2)
                .fluidInputs(ReinforcedEpoxyResin.getFluid(GTValues.L * 4))
                .fluidInputs(Polytetrafluoroethylene.getFluid(GTValues.L * 4))
                .fluidInputs(Polybenzimidazole.getFluid(GTValues.L * 4))
                .output(GTQTMetaTileEntities.FLUID_DRILLING_RIG)
                .stationResearch(b -> b
                        .researchStack(DISK_9.getStackForm())
                        .CWUt(1000,4000000)
                        .EUt(VA[LuV]))
                .duration(400).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[UHV])
                .input(frameGt, Orichalcum, 4)
                .input(circuit, Tier.UHV, 4)
                .input(ELECTRIC_MOTOR_UHV, 4)
                .input(ELECTRIC_PUMP_UHV, 4)
                .input(gear, Orichalcum, 4)
                .circuitMeta(2)
                .fluidInputs(ReinforcedEpoxyResin.getFluid(GTValues.L * 4))
                .fluidInputs(Polytetrafluoroethylene.getFluid(GTValues.L * 4))
                .fluidInputs(Polybenzimidazole.getFluid(GTValues.L * 4))
                .output(GTQTMetaTileEntities.ADVANCED_FLUID_DRILLING_RIG)
                .stationResearch(b -> b
                        .researchStack(DISK_9.getStackForm())
                        .CWUt(1000,4000000)
                        .EUt(VA[LuV]))
                .duration(400).EUt(VA[LuV]).buildAndRegister();

        //10 资源勘探III
        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .input(DISK_0)
                .notConsumable(SENSOR_ZPM)
                .output(DISK_10)
                .EUt(7680)
                .CWUt(256)
                .NB(8)
                .totalCWU(100000)
                .buildAndRegister();
    }


}
