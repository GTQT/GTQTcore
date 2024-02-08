package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
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

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.MarkerMaterials.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_COIL;
import static gregtech.common.blocks.BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL;
import static gregtech.common.blocks.BlockMetalCasing.MetalCasingType.BRONZE_BRICKS;
import static gregtech.common.blocks.MetaBlocks.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.FUSION_REACTOR;

import static gregtech.common.blocks.BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.KaptonK;
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
        Assembler();

    }
    private static void Pre() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(wireFine, BorosilicateGlass, 8)
                .input(foil, Silver, 8)
                .fluidInputs(Polyethylene.getFluid(L))
                .output(OPTICAL_PIPES[0])
                .duration(100).EUt(VA[MV]).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(wireFine, BorosilicateGlass, 8)
                .input(foil, Silver, 8)
                .fluidInputs(Epoxy.getFluid(L))
                .output(OPTICAL_PIPES[0],2)
                .duration(100).EUt(VA[MV]).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(wireFine, BorosilicateGlass, 8)
                .input(foil, Silver, 8)
                .fluidInputs(Polytetrafluoroethylene.getFluid(L))
                .output(OPTICAL_PIPES[0],4)
                .duration(100).EUt(VA[MV]).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(wireFine, BorosilicateGlass, 8)
                .input(foil, Silver, 8)
                .fluidInputs(KaptonK.getFluid(L))
                .output(OPTICAL_PIPES[0],8)
                .duration(100).EUt(VA[MV]).buildAndRegister();

        //物品支架
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(PLASTIC_CIRCUIT_BOARD)
                .input(circuit, Tier.MV)
                .input(CENTRAL_PROCESSING_UNIT, 4)
                .input(RANDOM_ACCESS_MEMORY, 4)
                .input(wireFine, Aluminium, 2)
                .input(gear, Copper, 1)
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
                .fluidInputs(Polytetrafluoroethylene.getFluid(L*8))
                .outputs( GTQTMetaBlocks.KQCC.getItemVariant(COMPUTER_VENT))
                .duration(1000).EUt(120).buildAndRegister();
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
    }
    private static void Assembler() {




    }

}
