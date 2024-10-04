package keqing.gtqtcore.loaders.recipes.handlers;

import gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities;
import gregtech.api.GTValues;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.ingredients.nbtmatch.NBTCondition;
import gregtech.api.recipes.ingredients.nbtmatch.NBTMatcher;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.BlockCleanroomCasing;
import gregtech.common.blocks.BlockComputerCasing;
import gregtech.common.blocks.BlockFusionCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static gregicality.multiblocks.api.unification.GCYMMaterials.*;
import static gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.MarkerMaterials.Tier;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_COIL;
import static gregtech.common.blocks.BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL;
import static gregtech.common.blocks.MetaBlocks.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.ASSEMBLY_LINE;
import static gregtech.common.metatileentities.MetaTileEntities.LARGE_CHEMICAL_REACTOR;
import static gregtech.common.metatileentities.MetaTileEntities.ROTOR_HOLDER;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static gregtechfoodoption.machines.GTFOTileEntities.GREENHOUSE;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.MaterialHelper.Superconductor;
import static keqing.gtqtcore.api.unification.TJMaterials.Polyetheretherketone;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.common.block.GTQTMetaBlocks.POWER;
import static keqing.gtqtcore.common.block.blocks.GTQTIsaCasing.CasingType.FLOTATION_CASING_GEARBOX;
import static keqing.gtqtcore.common.block.blocks.GTQTIsaCasing.CasingType.ISA_MILL_CASING_GEARBOX;
import static keqing.gtqtcore.common.block.blocks.GTQTKQCC.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.GTQTPowerSupply.SupplyType.POWER_SUPPLY_BASIC;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.COMBUSTION_GENERATOR;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.CRACKER;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.GAS_TURBINE;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.STEAM_TURBINE;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.*;

public class KeQingNET {

    public static void init() {
        Pre();
        I_VV();
        VVI_VVVV();
        //#GTQTKQnetHelper

        //辅助计算项目 科研计算机等级 序号
        //1 涡轮效率升级I 大型涡轮
        KQ_NET(0, 1, 1, DISK_1, MetaTileEntities.COMBUSTION_GENERATOR[2].getStackForm());
        KQ_NET(0, 1, 1, DISK_1, MetaTileEntities.GAS_TURBINE[2].getStackForm());
        KQ_NET(0, 1, 1, DISK_1, MetaTileEntities.STEAM_TURBINE[2].getStackForm());
        //2 涡轮效率升级II 小火箭
        KQ_NET(0, 2, 2, DISK_2, COMBUSTION_GENERATOR[0].getStackForm());
        KQ_NET(0, 2, 2, DISK_2, GAS_TURBINE[0].getStackForm());
        KQ_NET(0, 2, 2, DISK_2, STEAM_TURBINE[0].getStackForm());
        //3 涡轮效率升级III 特大轮机
        KQ_NET(0, 3, 3, DISK_3, EXTREME_COMBUSTION_ENGINE.getStackForm());
        KQ_NET(0, 3, 3, DISK_3, LARGE_COMBUSTION_ENGINE.getStackForm());
        //4 蒸燃联合体系 爆燃 大火箭
        KQ_NET(0, 2, 4, DISK_4, COMBUSTION_GENERATOR[1].getStackForm());
        KQ_NET(0, 2, 4, DISK_4, GAS_TURBINE[1].getStackForm());
        KQ_NET(0, 2, 4, DISK_4, STEAM_TURBINE[1].getStackForm());
        //5 室温超导设计
        KQ_NET(0, 2, 5, DISK_5, wireGtSingle, UraniumTriplatinum);
        //6 可控核聚变-环流器设计
        KQ_NET(2, 3, 6, DISK_6, PROTON);
        //7 可控核聚变-聚变超导线圈
        KQ_NET(2, 3, 7, DISK_7, MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL));
        //8 资源勘探I
        KQ_NET(0, 1, 8, DISK_8, SENSOR_HV);
        //9 资源勘探II
        KQ_NET(0, 2, 9, DISK_9, SENSOR_IV);
        //10 资源勘探III
        KQ_NET(3, 3, 10, DISK_10, SENSOR_ZPM);
        //11 网道行者-元素存储
        KQ_NET(21, 1, 11, DISK_11, gem, Fluix);
        //12 网道行者-管网系统

        //13 网道行者-无线传输

        //14 人工智能I
        KQ_NET(0, 1, 14, DISK_14, circuit, Tier.EV);
        //15 人工智能II
        KQ_NET(0, 2, 15, DISK_15, circuit, Tier.LuV);
        //16 人工智能III
        KQ_NET(3, 3, 16, DISK_16, circuit, Tier.UV);

        //17 生物工程I
        KQ_NET(0, 1, 17, DISK_17, SEPTIC_TANK.getStackForm());
        //18 生物工程II
        KQ_NET(11, 2, 18, DISK_18, WETWARE_CIRCUIT_BOARD);
        //19 生物工程III
        KQ_NET(12, 3, 19, DISK_19, INTRAVITAL_SOC);

        //20 标准化作业
        KQ_NET(0, 1, 20, DISK_20, ASSEMBLER[2].getStackForm());
        //21 高精加工
        KQ_NET(0, 2, 21, DISK_21, ASSEMBLY_LINE.getStackForm());
        //22 自动化管理
        KQ_NET(0, 3, 22, DISK_22, PRECISE_ASSEMBLER.getStackForm());
        //23 物联网集成控制
        KQ_NET(3, 4, 23, DISK_23, ADVANCED_ASSEMBLY_LINE.getStackForm());
        //24 计算科学
        KQ_NET(3, 3, 24, DISK_24, HIGH_PERFORMANCE_COMPUTING_ARRAY.getStackForm());
        //25 云数据存储
        KQ_NET(0, 2, 25, DISK_25, MINI_DATE_BANK.getStackForm());
        //26 硅岩燃料
        KQ_NET(3, 4, 26, DISK_26, screw, Naquadria);
        //27 奇异燃料
        KQ_NET(3, 4, 27, DISK_27, LARGE_NAQUADAH_REACTOR.getStackForm());
        //28 超高能激光器
        KQ_NET(4, 4, 28, DISK_28, POWER_SUBSTATION.getStackForm());
        //29 天基折射棱镜
        KQ_NET(4, 4, 29, DISK_29, LASER_SWITCH.getStackForm());
    }


    private static void VVI_VVVV() {
        //29 天基棱镜
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Duranium, 16)
                .input(LASER_OUTPUT_HATCH_1024[2], 16)
                .input(EMITTER_ZPM, 64)
                .input(FIELD_GENERATOR_ZPM, 64)
                .input(plate, Lutetium, 32)
                .input(circuit, MarkerMaterials.Tier.UHV, 16)
                .input(circuit, MarkerMaterials.Tier.UV, 32)
                .input(circuit, Tier.ZPM, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, Superconductor[ZPM], 64)
                .input(wireGtSingle, Superconductor[ZPM], 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .stationResearch(b -> b
                        .researchStack(DISK_29.getStackForm())
                        .CWUt(CWT[ZPM])
                        .EUt(VA[ZPM]))
                .output(SBPRI)
                .EUt(VA[ZPM])
                .duration(1000)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Duranium, 16)
                .input(LASER_INPUT_HATCH_1024[2], 16)
                .input(SENSOR_ZPM, 64)
                .input(FIELD_GENERATOR_ZPM, 64)
                .input(plate, Lutetium, 32)
                .input(circuit, MarkerMaterials.Tier.UHV, 16)
                .input(circuit, MarkerMaterials.Tier.UV, 32)
                .input(circuit, Tier.ZPM, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, Superconductor[ZPM], 64)
                .input(wireGtSingle, Superconductor[ZPM], 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .stationResearch(b -> b
                        .researchStack(DISK_29.getStackForm())
                        .CWUt(CWT[ZPM])
                        .EUt(VA[ZPM]))
                .output(SBPRO)
                .EUt(VA[ZPM])
                .duration(1000)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Duranium, 16)
                .input(CIRCUIT_GOOD_IV, 64)
                .input(FIELD_GENERATOR_ZPM, 64)
                .input(FIELD_GENERATOR_ZPM, 64)
                .input(plate, Lutetium, 32)
                .input(circuit, MarkerMaterials.Tier.UHV, 16)
                .input(circuit, MarkerMaterials.Tier.UV, 32)
                .input(circuit, Tier.ZPM, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, Superconductor[ZPM], 64)
                .input(wireGtSingle, Superconductor[ZPM], 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .stationResearch(b -> b
                        .researchStack(DISK_29.getStackForm())
                        .CWUt(CWT[ZPM])
                        .EUt(VA[ZPM]))
                .output(SBPRC)
                .EUt(VA[ZPM])
                .duration(1000)
                .buildAndRegister();
        //28 激光发射
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, NaquadahAlloy, 16)
                .input(LASER_OUTPUT_HATCH_1024[1], 16)
                .input(EMITTER_LuV, 64)
                .input(FIELD_GENERATOR_LuV, 64)
                .input(plate, Duranium, 32)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(circuit, MarkerMaterials.Tier.LuV, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, Superconductor[LuV], 64)
                .input(wireGtSingle, Superconductor[LuV], 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .stationResearch(b -> b
                        .researchStack(DISK_28.getStackForm())
                        .CWUt(CWT[LuV])
                        .EUt(VA[LuV]))
                .output(LASER_EMITTER)
                .EUt(VA[LuV])
                .duration(1000)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, NaquadahAlloy, 16)
                .input(LASER_INPUT_HATCH_1024[1], 16)
                .input(SENSOR_LuV, 64)
                .input(FIELD_GENERATOR_LuV, 64)
                .input(plate, Duranium, 32)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(circuit, MarkerMaterials.Tier.LuV, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, Superconductor[LuV], 64)
                .input(wireGtSingle, Superconductor[LuV], 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .stationResearch(b -> b
                        .researchStack(DISK_28.getStackForm())
                        .CWUt(CWT[LuV])
                        .EUt(VA[LuV]))
                .output(LASER_TRANSLATION)
                .EUt(VA[LuV])
                .duration(1000)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, NaquadahAlloy, 16)
                .input(CIRCUIT_GOOD_III, 64)
                .input(FIELD_GENERATOR_LuV, 64)
                .input(FIELD_GENERATOR_LuV, 64)
                .input(plate, Duranium, 32)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(circuit, MarkerMaterials.Tier.LuV, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, Superconductor[LuV], 64)
                .input(wireGtSingle, Superconductor[LuV], 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .stationResearch(b -> b
                        .researchStack(DISK_28.getStackForm())
                        .CWUt(CWT[LuV])
                        .EUt(VA[LuV]))
                .output(LASER_SWITCH)
                .EUt(VA[LuV])
                .duration(1000)
                .buildAndRegister();
        //设备
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(BLAZING_BLAST_FURNACE, 64)
                .input(BLAZING_BLAST_FURNACE, 64)
                .input(frameGt, NaquadahAlloy, 16)
                .input(LASER_OUTPUT_HATCH_1024[2], 8)
                .input(VOLTAGE_COIL_LuV, 64)
                .input(plate, Duranium, 32)
                .input(screw, PPB, 64)
                .input(gear, HG1223, 6)
                .input(stick, Staballoy, 32)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(circuit, MarkerMaterials.Tier.LuV, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, Superconductor[LuV], 64)
                .input(wireGtSingle, Superconductor[LuV], 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .stationResearch(b -> b
                        .researchStack(DISK_28.getStackForm())
                        .CWUt(CWT[LuV])
                        .EUt(VA[LuV]))
                .output(LASER_BLAST_FURNACE)
                .EUt(VA[LuV])
                .duration(1000)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(ALLOY_BLAST_SMELTER, 64)
                .input(ALLOY_BLAST_SMELTER, 64)
                .input(frameGt, NaquadahAlloy, 16)
                .input(LASER_OUTPUT_HATCH_1024[2], 8)
                .input(VOLTAGE_COIL_LuV, 64)
                .input(plate, Duranium, 32)
                .input(screw, PPB, 64)
                .input(gear, HG1223, 6)
                .input(stick, Staballoy, 32)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(circuit, MarkerMaterials.Tier.LuV, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, Superconductor[LuV], 64)
                .input(wireGtSingle, Superconductor[LuV], 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .stationResearch(b -> b
                        .researchStack(DISK_28.getStackForm())
                        .CWUt(CWT[LuV])
                        .EUt(VA[LuV]))
                .output(LASER_ALLOY_FURNACE)
                .EUt(VA[LuV])
                .duration(1000)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(LASER_CUTTER, 64)
                .input(LASER_CUTTER, 64)
                .input(frameGt, NaquadahAlloy, 16)
                .input(LASER_OUTPUT_HATCH_1024[2], 8)
                .input(toolHeadBuzzSaw,NaquadahAlloy, 64)
                .input(plate, Duranium, 32)
                .input(screw, PPB, 64)
                .input(gear, HG1223, 6)
                .input(stick, Staballoy, 32)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(circuit, MarkerMaterials.Tier.LuV, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, Superconductor[LuV], 64)
                .input(wireGtSingle, Superconductor[LuV], 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .stationResearch(b -> b
                        .researchStack(DISK_28.getStackForm())
                        .CWUt(CWT[LuV])
                        .EUt(VA[LuV]))
                .output(LASER_CUTTER)
                .EUt(VA[LuV])
                .duration(1000)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(LASER_ENGRAVING, 64)
                .input(LASER_ENGRAVING, 64)
                .input(frameGt, NaquadahAlloy, 16)
                .input(LASER_OUTPUT_HATCH_1024[2], 8)
                .input(EMITTER_LuV, 64)
                .input(plate, Duranium, 32)
                .input(screw, PPB, 64)
                .input(gear, HG1223, 6)
                .input(stick, Staballoy, 32)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(circuit, MarkerMaterials.Tier.LuV, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, Superconductor[LuV], 64)
                .input(wireGtSingle, Superconductor[LuV], 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .stationResearch(b -> b
                        .researchStack(DISK_28.getStackForm())
                        .CWUt(CWT[LuV])
                        .EUt(VA[LuV]))
                .output(LASER_ENV)
                .EUt(VA[LuV])
                .duration(1000)
                .buildAndRegister();
        //26
        //  Large Naquadah Reactor
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Naquadria, 16)
                .input(GTQTMetaTileEntities.NAQUADAH_REACTOR[2], 4)
                .input(ELECTRIC_PUMP_UV, 16)
                .input(FIELD_GENERATOR_UV, 16)
                .input(plate, Moscovium, 32)
                .input(circuit, MarkerMaterials.Tier.UHV, 16)
                .input(circuit, MarkerMaterials.Tier.UV, 32)
                .input(circuit, MarkerMaterials.Tier.ZPM, 64)
                .input(NANO_POWER_IC, 64)
                .input(NANO_POWER_IC, 64)
                .input(cableGtQuadruple, YttriumBariumCuprate, 64)
                .input(cableGtQuadruple, YttriumBariumCuprate, 64)
                .fluidInputs(Nihonium.getFluid(L * 4))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .fluidInputs(Kevlar.getFluid(L * 16))
                .fluidInputs(IncoloyMA813.getFluid(L * 16))
                .stationResearch(b -> b
                        .researchStack(DISK_26.getStackForm())
                        .CWUt(CWT[UHV])
                        .EUt(VA[UV]))
                .output(LARGE_NAQUADAH_REACTOR)
                .EUt(VA[UHV])
                .duration(600)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();


        //27
        //  Fuel Refine Factory
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, HastelloyC276, 16)
                .input(HUGE_CHEMICAL_REACTOR, 8)
                .input(pipeHugeFluid, Duranium, 32)
                .input(rotor, Orichalcum, 32)
                .input(circuit, MarkerMaterials.Tier.UHV, 16)
                .input(circuit, MarkerMaterials.Tier.UV, 32)
                .input(circuit, Tier.ZPM, 64)
                .input(ELECTRIC_PUMP_UV, 16)
                .input(FIELD_GENERATOR_UV, 16)
                .input(NANO_POWER_IC, 64)
                .input(NANO_POWER_IC, 64)
                .input(gear, Roentgenium, 8)
                .input(screw, Dubnium, 32)
                .input(cableGtQuadruple, Europium, 64)
                .input(cableGtQuadruple, Europium, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 40))
                .fluidInputs(BlackTitanium.getFluid(L * 20))
                .fluidInputs(Kevlar.getFluid(L * 10))
                .fluidInputs(KaptonK.getFluid(L * 5))
                .output(FUEL_REFINE_FACTORY)
                .stationResearch(b -> b
                        .researchStack(DISK_27.getStackForm())
                        .CWUt(CWT[UHV])
                        .EUt(VA[UV]))
                .EUt(VA[UHV])
                .duration(600)
                .buildAndRegister();

        //  Hyper Reactor Mk I
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, HastelloyC276, 16)
                .input(LARGE_NAQUADAH_REACTOR, 8)
                .input(plate, Meitnerium, 32)
                .input(plate, Vibranium, 32)
                .input(circuit, MarkerMaterials.Tier.UEV, 4)
                .input(circuit, MarkerMaterials.Tier.UHV, 16)
                .input(circuit, MarkerMaterials.Tier.UV, 32)
                .input(ELECTRIC_PUMP_UHV, 16)
                .input(FIELD_GENERATOR_UHV, 16)
                .input(NANO_POWER_IC, 64)
                .input(NANO_POWER_IC, 64)
                .input(gear, Roentgenium, 8)
                .input(screw, Dubnium, 32)
                .input(cableGtQuadruple, Europium, 64)
                .input(cableGtQuadruple, Europium, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 40))
                .fluidInputs(BlackTitanium.getFluid(L * 20))
                .fluidInputs(Kevlar.getFluid(L * 10))
                .fluidInputs(KaptonK.getFluid(L * 5))
                .output(HYPER_REACTOR_MKI)
                .stationResearch(b -> b
                        .researchStack(DISK_27.getStackForm())
                        .CWUt(CWT[UHV])
                        .EUt(VA[UV]))
                .EUt(VA[UHV])
                .duration(600)
                .buildAndRegister();

        //  Hyper Reactor Mk II
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, HastelloyX78, 16)
                .input(HYPER_REACTOR_MKI, 8)
                .input(plate, Nobelium, 32)
                .input(plate, Rhugnor, 32)
                .input(circuit, MarkerMaterials.Tier.UIV, 4)
                .input(circuit, MarkerMaterials.Tier.UEV, 16)
                .input(circuit, MarkerMaterials.Tier.UHV, 32)
                .input(ELECTRIC_PUMP_UEV, 16)
                .input(FIELD_GENERATOR_UEV, 16)
                .input(PICO_POWER_IC, 64)
                .input(PICO_POWER_IC, 64)
                .input(gear, Lawrencium, 8)
                .input(screw, Livermorium, 32)
                .input(cableGtQuadruple, PedotTMA, 64)
                .input(cableGtQuadruple, PedotTMA, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 40))
                .fluidInputs(BlackTitanium.getFluid(L * 20))
                .fluidInputs(Kevlar.getFluid(L * 10))
                .fluidInputs(KaptonE.getFluid(L * 5))
                .output(HYPER_REACTOR_MKII)
                .stationResearch(b -> b
                        .researchStack(DISK_27.getStackForm())
                        .CWUt(CWT[UHV])
                        .EUt(VA[UV]))
                .EUt(VA[UEV])
                .duration(600)
                .buildAndRegister();

        //  Hyper Reactor Mk III
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, HastelloyK243, 16)
                .input(HYPER_REACTOR_MKII, 8)
                .input(plate, MetastableHassium, 32)
                .input(plate, Hypogen, 32)
                .input(circuit, MarkerMaterials.Tier.UXV, 4)
                .input(circuit, MarkerMaterials.Tier.UIV, 16)
                .input(circuit, MarkerMaterials.Tier.UEV, 32)
                .input(ELECTRIC_PUMP_UIV, 16)
                .input(FIELD_GENERATOR_UIV, 16)
                .input(FEMTO_POWER_IC, 64)
                .input(FEMTO_POWER_IC, 64)
                .input(gear, MetastableOganesson, 8)
                .input(screw, MetastableFlerovium, 32)
                .input(cableGtQuadruple, Solarium, 64)
                .input(cableGtQuadruple, Solarium, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 40))
                .fluidInputs(BlackTitanium.getFluid(L * 20))
                .fluidInputs(Kevlar.getFluid(L * 10))
                .fluidInputs(KaptonE.getFluid(L * 5))
                .output(HYPER_REACTOR_MKIII)
                .stationResearch(b -> b
                        .researchStack(DISK_27.getStackForm())
                        .CWUt(CWT[UHV])
                        .EUt(VA[UV]))
                .EUt(VA[UIV])
                .duration(600)
                .buildAndRegister();

        //24
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 8)
                .input(frameGt, NaquadahAlloy, 4)
                .input(plate, Naquadria, 8)
                .input(HPCA_COMPUTATION_COMPONENT, 8)
                .output(HPCA_SUPER_COMPUTATION_COMPONENT)
                .fluidInputs(VanadiumGallium.getFluid(L * 16))
                .fluidInputs(KaptonK.getFluid(L * 16))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .stationResearch(b -> b
                        .researchStack(DISK_24.getStackForm())
                        .CWUt(CWT[LuV])
                        .EUt(VA[ZPM]))
                .EUt(VA[ZPM])
                .duration(1000).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 8)
                .input(frameGt, NaquadahAlloy, 4)
                .input(plate, Naquadria, 8)
                .input(HPCA_ADVANCED_COMPUTATION_COMPONENT, 8)
                .output(HPCA_ULTIMATE_COMPUTATION_COMPONENT)
                .fluidInputs(VanadiumGallium.getFluid(L * 16))
                .fluidInputs(KaptonK.getFluid(L * 16))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .stationResearch(b -> b
                        .researchStack(DISK_24.getStackForm())
                        .CWUt(CWT[LuV])
                        .EUt(VA[ZPM]))
                .EUt(VA[ZPM])
                .duration(1000).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 8)
                .input(frameGt, NaquadahAlloy, 4)
                .input(plate, Naquadria, 8)
                .input(HPCA_HEAT_SINK_COMPONENT, 8)
                .output(HPCA_SUPER_COOLER_COMPONENT)
                .fluidInputs(VanadiumGallium.getFluid(L * 16))
                .fluidInputs(KaptonK.getFluid(L * 16))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .stationResearch(b -> b
                        .researchStack(DISK_24.getStackForm())
                        .CWUt(CWT[LuV])
                        .EUt(VA[ZPM]))
                .EUt(VA[ZPM])
                .duration(1000).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 8)
                .input(frameGt, NaquadahAlloy, 4)
                .input(plate, Naquadria, 8)
                .input(HPCA_ACTIVE_COOLER_COMPONENT, 8)
                .output(HPCA_ULTIMATE_COOLER_COMPONENT)
                .fluidInputs(VanadiumGallium.getFluid(L * 16))
                .fluidInputs(KaptonK.getFluid(L * 16))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .stationResearch(b -> b
                        .researchStack(DISK_24.getStackForm())
                        .CWUt(CWT[LuV])
                        .EUt(VA[ZPM]))
                .EUt(VA[ZPM])
                .duration(1000).buildAndRegister();


        OreDictionary.registerOre("crystalFluix", OreDictUnifier.get(OrePrefix.gem, Fluix));

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[HV])
                .input(gem, Fluix)
                .input(EMITTER_HV)
                .input(plate, StainlessSteel, 4)
                .fluidInputs(Polybenzimidazole.getFluid(GTValues.L * 4))
                .output(AE_FLUIX_FIRM)
                .scannerResearch(b -> b.researchStack(DISK_11.getStackForm()).duration(1200).EUt(VA[HV]))
                .duration(100).buildAndRegister();


        //
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(circuit, Tier.EV, 2)
                .input(ELECTRIC_PISTON_HV, 8)
                .input(HULL[HV], 8)
                .input(MACERATOR[3], 4)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .output(CORE_MACERATOR)
                .scannerResearch(b -> b
                        .researchStack(DISK_14.getStackForm())
                        .EUt(VA[HV]))
                .EUt(VA[HV])
                .duration(600)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(circuit, Tier.EV, 2)
                .input(ELECTRIC_MOTOR_HV, 8)
                .input(HULL[HV], 8)
                .input(CENTRIFUGE[3], 4)
                .input(frameGt, Aluminium, 8)
                .input(plate, StainlessSteel, 32)
                .input(wireGtSingle, MercuryBariumCalciumCuprate, 16)
                .input(wireGtSingle, MercuryBariumCalciumCuprate, 16)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .output(CORE_CENTRIFUGE)
                .scannerResearch(b -> b
                        .researchStack(DISK_14.getStackForm())
                        .EUt(VA[HV]))
                .EUt(VA[HV])
                .duration(600)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(circuit, Tier.EV, 2)
                .input(ROBOT_ARM_HV, 8)
                .input(HULL[HV], 8)
                .input(MIXER[3], 4)
                .input(frameGt, Aluminium, 8)
                .input(plate, StainlessSteel, 32)
                .input(wireGtSingle, MercuryBariumCalciumCuprate, 16)
                .input(wireGtSingle, MercuryBariumCalciumCuprate, 16)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .output(CORE_MIX)
                .scannerResearch(b -> b
                        .researchStack(DISK_14.getStackForm())
                        .EUt(VA[HV]))
                .EUt(VA[HV])
                .duration(600)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(circuit, Tier.EV, 2)
                .input(ELECTRIC_PUMP_HV, 8)
                .input(HULL[HV], 8)
                .input(ORE_WASHER[3], 4)
                .input(frameGt, Aluminium, 8)
                .input(plate, StainlessSteel, 32)
                .input(wireGtSingle, MercuryBariumCalciumCuprate, 16)
                .input(wireGtSingle, MercuryBariumCalciumCuprate, 16)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .output(CORE_WASHER)
                .scannerResearch(b -> b
                        .researchStack(DISK_14.getStackForm())
                        .EUt(VA[HV]))
                .EUt(VA[HV])
                .duration(600)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(circuit, Tier.EV, 2)
                .input(CONVEYOR_MODULE_HV, 8)
                .input(HULL[HV], 8)
                .input(FORGE_HAMMER[3], 4)
                .input(frameGt, Aluminium, 8)
                .input(plate, StainlessSteel, 32)
                .input(wireGtSingle, MercuryBariumCalciumCuprate, 16)
                .input(wireGtSingle, MercuryBariumCalciumCuprate, 16)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .output(CORE_HAMMER)
                .scannerResearch(b -> b
                        .researchStack(DISK_14.getStackForm())
                        .EUt(VA[HV]))
                .EUt(VA[HV])
                .duration(600)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(circuit, Tier.EV, 2)
                .input(VOLTAGE_COIL_HV, 8)
                .input(HULL[HV], 8)
                .input(ARC_FURNACE[3], 4)
                .input(frameGt, Aluminium, 8)
                .input(plate, StainlessSteel, 32)
                .input(wireGtSingle, MercuryBariumCalciumCuprate, 16)
                .input(wireGtSingle, MercuryBariumCalciumCuprate, 16)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .output(CORE_FURNACE)
                .scannerResearch(b -> b
                        .researchStack(DISK_14.getStackForm())
                        .EUt(VA[HV]))
                .EUt(VA[HV])
                .duration(600)
                .buildAndRegister();

        //多线程设备
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 2)
                .input(ELECTRIC_PISTON_IV, 8)
                .input(HULL[IV], 8)
                .input(MACERATOR[5], 4)
                .input(frameGt, TungstenSteel, 8)
                .input(plate, PPB, 32)
                .input(wireGtSingle, IVSuperconductor, 64)
                .input(wireGtSingle, IVSuperconductor, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Zylon.getFluid(L * 4))
                .output(INDUSTRIAL_MACERATOR)
                .EUt(VA[IV])
                .duration(600)
                .scannerResearch(b -> b
                        .researchStack(DISK_15.getStackForm()))
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 2)
                .input(ELECTRIC_MOTOR_IV, 8)
                .input(HULL[IV], 8)
                .input(CENTRIFUGE[5], 4)
                .input(frameGt, TungstenSteel, 8)
                .input(plate, PPB, 32)
                .input(wireGtSingle, IVSuperconductor, 64)
                .input(wireGtSingle, IVSuperconductor, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Zylon.getFluid(L * 4))
                .output(INDUSTRIAL_CENTRIFUGE)
                .EUt(VA[IV])
                .duration(600)
                .scannerResearch(b -> b
                        .researchStack(DISK_15.getStackForm()))
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 2)
                .input(ROBOT_ARM_IV, 8)
                .input(HULL[IV], 8)
                .input(MIXER[5], 4)
                .input(frameGt, TungstenSteel, 8)
                .input(plate, PPB, 32)
                .input(wireGtSingle, IVSuperconductor, 64)
                .input(wireGtSingle, IVSuperconductor, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Zylon.getFluid(L * 4))
                .output(INDUSTRIAL_MIX)
                .EUt(VA[IV])
                .duration(600)
                .scannerResearch(b -> b
                        .researchStack(DISK_15.getStackForm()))
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 2)
                .input(ELECTRIC_PUMP_IV)
                .input(HULL[IV], 8)
                .input(ORE_WASHER[5], 4)
                .input(frameGt, TungstenSteel, 8)
                .input(plate, PPB, 32)
                .input(wireGtSingle, IVSuperconductor, 64)
                .input(wireGtSingle, IVSuperconductor, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Zylon.getFluid(L * 4))
                .output(INDUSTRIAL_WASHER)
                .EUt(VA[IV])
                .duration(600)
                .scannerResearch(b -> b
                        .researchStack(DISK_15.getStackForm()))
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 2)
                .input(CONVEYOR_MODULE_IV, 8)
                .input(HULL[IV], 8)
                .input(FORGE_HAMMER[5], 4)
                .input(frameGt, TungstenSteel, 8)
                .input(plate, PPB, 32)
                .input(wireGtSingle, IVSuperconductor, 64)
                .input(wireGtSingle, IVSuperconductor, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Zylon.getFluid(L * 4))
                .output(INDUSTRIAL_HAMMER)
                .EUt(VA[IV])
                .duration(600)
                .scannerResearch(b -> b
                        .researchStack(DISK_15.getStackForm()))
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 2)
                .input(VOLTAGE_COIL_IV, 8)
                .input(HULL[IV], 8)
                .input(ARC_FURNACE[5], 4)
                .input(frameGt, TungstenSteel, 8)
                .input(plate, PPB, 32)
                .input(wireGtSingle, IVSuperconductor, 64)
                .input(wireGtSingle, IVSuperconductor, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Zylon.getFluid(L * 4))
                .output(INDUSTRIAL_FURNACE)
                .EUt(VA[IV])
                .duration(600)
                .scannerResearch(b -> b
                        .researchStack(DISK_15.getStackForm()))
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 2)
                .input(ELECTRIC_PISTON_LUV, 8)
                .input(HULL[LuV], 8)
                .input(BENDER[6], 4)
                .input(frameGt, NaquadahAlloy, 8)
                .input(plate, PPB, 32)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Polyetheretherketone.getFluid(L * 4))
                .output(INDUSTRIAL_BENDER)
                .EUt(VA[LuV])
                .duration(600)
                .scannerResearch(b -> b
                        .researchStack(DISK_15.getStackForm()))
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 2)
                .input(ELECTRIC_MOTOR_LuV, 8)
                .input(HULL[LuV], 8)
                .input(WIREMILL[6], 4)
                .input(frameGt, NaquadahAlloy, 8)
                .input(plate, PPB, 32)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Polyetheretherketone.getFluid(L * 4))
                .output(INDUSTRIAL_WIREMILL)
                .EUt(VA[LuV])
                .duration(600)
                .scannerResearch(b -> b
                        .researchStack(DISK_15.getStackForm()))
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 2)
                .input(ELECTRIC_PUMP_LuV, 8)
                .input(HULL[LuV], 8)
                .input(EXTRUDER[6], 4)
                .input(frameGt, NaquadahAlloy, 8)
                .input(plate, PPB, 32)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Polyetheretherketone.getFluid(L * 4))
                .output(INDUSTRIAL_EXTRUDER)
                .EUt(VA[LuV])
                .duration(600)
                .scannerResearch(b -> b
                        .researchStack(DISK_15.getStackForm()))
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 2)
                .input(ROBOT_ARM_LuV, 16)
                .input(CONVEYOR_MODULE_LuV, 16)
                .input(HULL[LuV], 8)
                .input(ASSEMBLY_LINE, 4)
                .input(ASSEMBLER[6], 4)
                .input(frameGt, NaquadahAlloy, 8)
                .input(plate, PPB, 32)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Polyetheretherketone.getFluid(L * 4))
                .output(INDUSTRIAL_ASSEMBLY_LINE)
                .EUt(VA[LuV])
                .duration(600)
                .scannerResearch(b -> b
                        .researchStack(DISK_15.getStackForm()))
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 1)
                .input(WETWARE_CIRCUIT_BOARD, 16)
                .input(foil, NaquadahAlloy, 8)
                .input(OPTICAL_PIPES[0], 64)
                .input(OPTICAL_PIPES[0], 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Polyetheretherketone.getFluid(L * 4))
                .output(BIO_0)
                .EUt(VA[LuV])
                .duration(600)
                .scannerResearch(b -> b
                        .researchStack(DISK_18.getStackForm()))
                .buildAndRegister();
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
                .output(OPTICAL_PIPES[0], 2)
                .duration(100).EUt(VA[MV]).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(wireFine, BorosilicateGlass, 8)
                .input(foil, Silver, 8)
                .input(plateDense, Aluminium, 1)
                .fluidInputs(Polytetrafluoroethylene.getFluid(L))
                .output(OPTICAL_PIPES[0], 4)
                .duration(100).EUt(VA[MV]).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(wireFine, BorosilicateGlass, 8)
                .input(foil, Silver, 8)
                .input(plateDense, Aluminium, 1)
                .fluidInputs(KaptonK.getFluid(L))
                .output(OPTICAL_PIPES[0], 8)
                .duration(100).EUt(VA[MV]).buildAndRegister();

        //物品支架
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(GOOD_CIRCUIT_BOARD)
                .input(circuit, Tier.MV, 2)
                .input(wireFine, Aluminium, 2)
                .input(gearSmall, Copper, 4)
                .input(plateDense, Aluminium, 4)
                .input(OPTICAL_PIPES[0], 2)
                .fluidInputs(Polyethylene.getFluid(L))
                .output(DISK_0)
                .duration(1000).EUt(120).buildAndRegister();

        //计算框架
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt, Steel, 4)
                .input(plate, Aluminium, 4)
                .input(OPTICAL_PIPES[0], 2)
                .input(plateDense, Aluminium, 4)
                .fluidInputs(Polytetrafluoroethylene.getFluid(L * 8))
                .outputs(GTQTMetaBlocks.KQCC.getItemVariant(COMPUTER_VENT))
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
                .stationResearch(b -> b
                        .researchStack(ADVANCED_DATA_ACCESS_HATCH.getStackForm())
                        .CWUt(CWT[UV])
                        .EUt(VA[UV]))
                .duration(4000000).EUt(6000).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(GREENHOUSE, 4)
                .input(circuit, Tier.HV, 4)
                .input(ELECTRIC_PUMP_HV, 4)
                .input(ROBOT_ARM_HV, 4)
                .input(plateDense, StainlessSteel, 4)
                .input(foil, StainlessSteel, 64)
                .output(GREEN_HOUSE_PLUS)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .scannerResearch(b -> b
                        .researchStack(DISK_17.getStackForm())
                        .EUt(VA[HV]))
                .duration(4000).EUt(1920).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CENTRIFUGE[3], 4)
                .input(circuit, Tier.HV, 4)
                .input(ELECTRIC_MOTOR_HV, 4)
                .input(ROBOT_ARM_HV, 4)
                .input(plateDense, NanometerBariumTitanate, 4)
                .input(gear, TungstenSteel, 16)
                .input(screw, Titanium, 8)
                .input(foil, Tcfour, 64)
                .output(BIO_CENTRIFUGE)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Platinum.getFluid(L * 8))
                .scannerResearch(b -> b
                        .researchStack(DISK_17.getStackForm())
                        .EUt(VA[HV]))
                .duration(4000).EUt(1920).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(BIOLOGICAL_REACTION, 4)
                .input(circuit, Tier.HV, 4)
                .input(ELECTRIC_PUMP_HV, 4)
                .input(ROBOT_ARM_HV, 4)
                .input(plateDense, NanometerBariumTitanate, 4)
                .input(foil, Tcfour, 64)
                .output(ENZYMES_REACTOR)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Brominatedepoxyresins.getFluid(4000))
                .fluidInputs(Polyethylene.getFluid(4000))
                .fluidInputs(Palladium.getFluid(L * 8))
                .scannerResearch(b -> b
                        .researchStack(DISK_17.getStackForm())
                        .EUt(VA[HV]))
                .duration(4000).EUt(1920).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(ENZYMES_REACTOR, 4)
                .input(circuit, Tier.UV, 4)
                .input(circuit, Tier.ZPM, 16)
                .input(ELECTRIC_PUMP_LuV, 4)
                .input(ROBOT_ARM_LuV, 4)
                .input(frameGt, Europium, 8)
                .input(plateDense, NaquadahAlloy, 4)
                .input(foil, PPB, 64)
                .input(wireGtSingle, IVSuperconductor, 16)
                .output(GTQTMetaTileEntities.GENE_MUTAGENESIS)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .stationResearch(b -> b
                        .researchStack(DISK_18.getStackForm())
                        .CWUt(CWT[IV])
                        .EUt(VA[LuV]))
                .duration(4000).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(circuit, Tier.IV, 4)
                .input(EMITTER_IV, 4)
                .input(ELECTRIC_MOTOR_IV, 4)
                .input(HULL[IV], 1)
                .input(frameGt, Naquadah, 1)
                .inputs(ITEM_FILTER.getStackForm(4))
                .inputs(VOLTAGE_COIL_IV.getStackForm(16))
                .input(pipeLargeFluid, Polybenzimidazole, 16)
                .input(wireGtSingle, EVSuperconductor, 8)
                .input(wireGtSingle, EVSuperconductor, 8)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Zylon.getFluid(L * 4))
                .outputs(MetaBlocks.CLEANROOM_CASING.getItemVariant(BlockCleanroomCasing.CasingType.FILTER_CASING_STERILE))
                .EUt(VA[IV])
                .duration(600)
                .scannerResearch(b -> b
                        .researchStack(MetaBlocks.CLEANROOM_CASING.getItemVariant(BlockCleanroomCasing.CasingType.FILTER_CASING)))
                .buildAndRegister();

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
                .input(DISK_0, 4)
                .input(COVER_SCREEN)
                .input(wireFine, Aluminium, 16)
                .input(OPTICAL_PIPES[0], 8)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .output(KeQing_NET)
                .duration(200).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm())
                .input(EMITTER_LV, 8)
                .input(DISK_0, 4)
                .input(COVER_SCREEN)
                .input(wireFine, Aluminium, 16)
                .input(OPTICAL_PIPES[0], 8)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .output(KQCC)
                .duration(200).EUt(120).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[4].getStackForm(16))
                .input(circuit, Tier.EV, 8)
                .input(EMITTER_EV, 8)
                .input(DISK_0, 16)
                .input(COVER_SCREEN)
                .input(OPTICAL_PIPES[0], 16)
                .input(frameGt, TungstenSteel, 8)
                .input(plate, NanometerBariumTitanate, 8)
                .input(stickLong, NeodymiumMagnetic, 64)
                .input(stickLong, NeodymiumMagnetic, 64)
                .input(wireGtHex, Platinum, 8)
                .input(foil, Palladium, 8)
                .fluidInputs(Epoxy.getFluid(4000))
                .fluidInputs(Zylon.getFluid(4000))
                .fluidInputs(Brominatedepoxyresins.getFluid(4000))
                .fluidInputs(Polytetrafluoroethylene.getFluid(4000))
                .scannerResearch(b -> b
                        .researchStack(KQCC.getStackForm())
                        .duration(1200)
                        .EUt(VA[HV]))
                .output(ADV_KQCC)
                .duration(2000).EUt(480).buildAndRegister();

        //  Component Assembly Line
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III, 8)
                .input(ASSEMBLY_LINE, 8)
                .input(frameGt, Cinobite, 16)
                .input(PRECISE_ASSEMBLER, 16)
                .input(COMPONENT_ASSEMBLER[IV], 16)
                .input(ROBOT_ARM_LuV, 64)
                .input(CONVEYOR_MODULE_LuV, 64)
                .input(plateDouble, Naquadria, 6)
                .input(plateDouble, Pikyonium64B, 6)
                .input(gear, TungstenSteel, 6)
                .input(gearSmall, TitanSteel, 32)
                .input(wireFine, Ruridit, 64)
                .input(wireFine, Ruridit, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(COMPONENT_ASSEMBLY_LINE)
                .EUt(VA[LuV])
                .duration(1200)
                .stationResearch(b -> b
                        .researchStack(DISK_22.getStackForm())
                        .EUt(VA[LuV])
                        .CWUt(CWT[LuV]))
                .buildAndRegister();


        //大型光刻厂
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GCYMMetaTileEntities.LARGE_ENGRAVER.getStackForm(64))
                .inputs(GCYMMetaTileEntities.LARGE_ENGRAVER.getStackForm(64))
                .inputs(GCYMMetaTileEntities.LARGE_ENGRAVER.getStackForm(64))
                .inputs(GCYMMetaTileEntities.LARGE_ENGRAVER.getStackForm(64))
                .input(CIRCUIT_GOOD_IV, 8)
                .input(circuit, MarkerMaterials.Tier.UHV, 4)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(frameGt, HMS1J22Alloy, 16)
                .input(plateDouble, HG1223, 4)
                .input(plateDouble, Staballoy, 4)
                .input(gear, MaragingSteel250, 4)
                .input(gearSmall, Stellite, 16)
                .input(cableGtQuadruple, VanadiumGallium, 16)
                .input(wireGtSingle, ZPMSuperconductor, 64)
                .input(wireGtSingle, ZPMSuperconductor, 64)
                .output(PHOTOLITHOGRAPHY_FACTORY)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .stationResearch(b -> b
                        .researchStack(DISK_23.getStackForm())
                        .CWUt(CWT[IV])
                        .EUt(VA[LuV]))
                .EUt(VA[LuV])
                .duration(400)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
        //  Mega Oil Cracking Unit
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(CRACKER.getStackForm(64))
                .inputs(CRACKER.getStackForm(64))
                .inputs(CRACKER.getStackForm(64))
                .inputs(CRACKER.getStackForm(64))
                .input(CIRCUIT_GOOD_IV, 8)
                .input(frameGt, HMS1J22Alloy, 16)
                .input(circuit, MarkerMaterials.Tier.UHV, 4)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(plateDouble, HG1223, 4)
                .input(plateDouble, Staballoy, 4)
                .input(gear, MaragingSteel250, 4)
                .input(gearSmall, Stellite, 16)
                .input(cableGtQuadruple, VanadiumGallium, 64)
                .input(cableGtQuadruple, VanadiumGallium, 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .outputs(HUGE_CRACKING_UNIT.getStackForm())
                .stationResearch(b -> b
                        .researchStack(DISK_23.getStackForm())
                        .CWUt(CWT[IV])
                        .EUt(VA[LuV]))
                .EUt(VA[LuV])
                .duration(400)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Mega Chemical Reactor
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(LARGE_CHEMICAL_REACTOR.getStackForm(64))
                .inputs(LARGE_CHEMICAL_REACTOR.getStackForm(64))
                .inputs(LARGE_CHEMICAL_REACTOR.getStackForm(64))
                .inputs(LARGE_CHEMICAL_REACTOR.getStackForm(64))
                .input(CIRCUIT_GOOD_IV, 8)
                .input(ELECTRIC_PUMP_UV, 32)
                .input(frameGt, MARM200Steel, 16)
                .input(circuit, MarkerMaterials.Tier.UHV, 4)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(plateDouble, HMS1J79Alloy, 4)
                .input(plateDouble, IncoloyDS, 4)
                .input(gear, Inconel625, 4)
                .input(gearSmall, Tantalloy61, 16)
                .input(cableGtQuadruple, VanadiumGallium, 64)
                .input(cableGtQuadruple, VanadiumGallium, 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .outputs(HUGE_CHEMICAL_REACTOR.getStackForm())
                .stationResearch(b -> b
                        .researchStack(DISK_23.getStackForm())
                        .CWUt(CWT[IV])
                        .EUt(VA[LuV]))
                .EUt(VA[IV])
                .duration(800)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Mega Alloy Blast Smelter
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(ALLOY_BLAST_SMELTER.getStackForm(64))
                .inputs(ALLOY_BLAST_SMELTER.getStackForm(64))
                .inputs(ALLOY_BLAST_SMELTER.getStackForm(64))
                .inputs(ALLOY_BLAST_SMELTER.getStackForm(64))
                .input(CIRCUIT_GOOD_IV, 8)
                .input(frameGt, AusteniticStainlessSteel904L, 16)
                .input(circuit, MarkerMaterials.Tier.UHV, 4)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(plateDouble, HSLASteel, 4)
                .input(plateDouble, HastelloyC59, 4)
                .input(gear, HY1301, 4)
                .input(gearSmall, TanmolyiumBetaC, 16)
                .input(cableGtQuadruple, VanadiumGallium, 64)
                .input(cableGtQuadruple, VanadiumGallium, 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .outputs(HUGE_ALLOY_BLAST_FURANCE.getStackForm())
                .stationResearch(b -> b
                        .researchStack(DISK_23.getStackForm())
                        .CWUt(CWT[IV])
                        .EUt(VA[LuV]))
                .EUt(VA[LuV])
                .duration(1200)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Mega Alloy Blast Smelter
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(BLAZING_BLAST_FURNACE.getStackForm(64))
                .inputs(BLAZING_BLAST_FURNACE.getStackForm(64))
                .inputs(BLAZING_BLAST_FURNACE.getStackForm(64))
                .inputs(BLAZING_BLAST_FURNACE.getStackForm(64))
                .input(frameGt, AusteniticStainlessSteel904L, 16)
                .input(circuit, MarkerMaterials.Tier.UHV, 4)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(CIRCUIT_GOOD_IV, 8)
                .input(plateDouble, HSLASteel, 4)
                .input(plateDouble, HastelloyC59, 4)
                .input(gear, HY1301, 4)
                .input(gearSmall, TanmolyiumBetaC, 16)
                .input(cableGtQuadruple, VanadiumGallium, 64)
                .input(cableGtQuadruple, VanadiumGallium, 64)
                .fluidInputs(KaptonK.getFluid(L * 32))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .outputs(HUGE_BLAST_FURANCE.getStackForm())
                .stationResearch(b -> b
                        .researchStack(DISK_23.getStackForm())
                        .CWUt(CWT[IV])
                        .EUt(VA[LuV]))
                .EUt(VA[LuV])
                .duration(1200)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();


        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Adamantium, 8)
                .input(plate, Neutronium, 16)
                .input(circuit, MarkerMaterials.Tier.UHV, 4)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(CIRCUIT_GOOD_IV, 8)
                .input(stick, Orichalcum, 32)
                .input(wireFine, Tritanium, 64)
                .input(wireGtSingle, UVSuperconductor, 16)
                .input(wireGtSingle, UVSuperconductor, 16)
                .outputs(GTQTMetaBlocks.KQCC.getItemVariant(ULTRA_POWER_CASING))
                .fluidInputs(Kevlar.getFluid(L * 8))
                .fluidInputs(KaptonE.getFluid(L * 16))
                .fluidInputs(Duranium.getFluid(L * 32))
                .fluidInputs(Tritanium.getFluid(L * 32))
                .stationResearch(b -> b
                        .researchStack(COMPUTER_CASING.getItemVariant(BlockComputerCasing.CasingType.HIGH_POWER_CASING))
                        .CWUt(CWT[ZPM])
                        .EUt(VA[UV]))
                .EUt(VA[UV])
                .duration(2000)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Tritanium, 8)
                .input(plate, Neutronium, 16)
                .input(circuit, MarkerMaterials.Tier.UHV, 4)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(CIRCUIT_GOOD_IV, 8)
                .input(stick, Orichalcum, 32)
                .input(gearSmall, Duranium, 64)
                .input(wireGtSingle, UVSuperconductor, 16)
                .input(wireGtSingle, UVSuperconductor, 16)
                .outputs(GTQTMetaBlocks.KQCC.getItemVariant(ADV_COMPUTER_CASING))
                .fluidInputs(Kevlar.getFluid(L * 8))
                .fluidInputs(KaptonE.getFluid(L * 16))
                .fluidInputs(Duranium.getFluid(L * 32))
                .fluidInputs(Tritanium.getFluid(L * 32))
                .stationResearch(b -> b
                        .researchStack(COMPUTER_CASING.getItemVariant(BlockComputerCasing.CasingType.COMPUTER_CASING))
                        .CWUt(CWT[ZPM])
                        .EUt(VA[UV]))
                .EUt(VA[UV])
                .duration(2000)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.KQCC.getItemVariant(ADV_COMPUTER_CASING))
                .input(circuit, MarkerMaterials.Tier.UHV, 4)
                .input(circuit, MarkerMaterials.Tier.UV, 16)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(CIRCUIT_GOOD_IV, 8)
                .input(stick, Orichalcum, 32)
                .input(ring, Tritanium, 64)
                .input(wireGtSingle, UVSuperconductor, 16)
                .input(wireGtSingle, UVSuperconductor, 16)
                .outputs(GTQTMetaBlocks.KQCC.getItemVariant(ULTRA_COMPUTER_CASING))
                .fluidInputs(Kevlar.getFluid(L * 8))
                .fluidInputs(KaptonE.getFluid(L * 16))
                .fluidInputs(Duranium.getFluid(L * 32))
                .fluidInputs(Tritanium.getFluid(L * 32))
                .stationResearch(b -> b
                        .researchStack(COMPUTER_CASING.getItemVariant(BlockComputerCasing.CasingType.ADVANCED_COMPUTER_CASING))
                        .CWUt(CWT[ZPM])
                        .EUt(VA[UV]))
                .EUt(VA[UV])
                .duration(2000)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Tritanium, 16)
                .input(ELECTRIC_MOTOR_UHV, 32)
                .input(rotor, Adamantium, 64)
                .input(rotor, Adamantium, 64)
                .input(pipeTinyFluid, Neutronium, 16)
                .input(plate, Orichalcum, 32)
                .input(wireGtSingle, UVSuperconductor, 16)
                .input(wireGtSingle, UVSuperconductor, 16)
                .outputs(GTQTMetaBlocks.KQCC.getItemVariant(ADV_COMPUTER_HEAT_VENT))
                .fluidInputs(Kevlar.getFluid(L * 8))
                .fluidInputs(KaptonE.getFluid(L * 16))
                .fluidInputs(Duranium.getFluid(L * 32))
                .fluidInputs(Tritanium.getFluid(L * 32))
                .stationResearch(b -> b
                        .researchStack(COMPUTER_CASING.getItemVariant(BlockComputerCasing.CasingType.COMPUTER_HEAT_VENT))
                        .CWUt(CWT[ZPM])
                        .EUt(VA[UV]))
                .EUt(VA[UV])
                .duration(2000)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();


        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.KQCC.getItemVariant(ADV_COMPUTER_CASING))
                .input(EMITTER_UV, 16)
                .input(SENSOR_UV, 16)
                .input(circuit, Tier.UHV, 4)
                .input(wireGtDouble, RutheniumTriniumAmericiumNeutronate, 32)
                .input(foil, Vibranium, 64)
                .input(foil, Vibranium, 64)
                .input(OPTICAL_PIPES[0], 64)
                .input(OPTICAL_PIPES[0], 64)
                .input(wireGtSingle, UVSuperconductor, 64)
                .input(wireGtSingle, UVSuperconductor, 64)
                .output(ADV_NETWORK_SWITCH)
                .fluidInputs(Kevlar.getFluid(L * 16))
                .fluidInputs(KaptonE.getFluid(L * 32))
                .fluidInputs(Polyetheretherketone.getFluid(L * 64))
                .fluidInputs(RutheniumTriniumAmericiumNeutronate.getFluid(L * 16))
                .stationResearch(b -> b
                        .researchStack(NETWORK_SWITCH.getStackForm())
                        .CWUt(CWT[ZPM])
                        .EUt(VA[UV]))
                .EUt(VA[UV])
                .duration(2000)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();


        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.KQCC.getItemVariant(ADV_COMPUTER_CASING))
                .input(GENERAL_CIRCUIT_UV, 16)
                .input(circuit, Tier.UHV, 8)
                .inputNBT(TOOL_DATA_MODULE, NBTMatcher.ANY, NBTCondition.ANY)
                .input(frameGt, Tritanium, 16)
                .input(wireFine, Orichalcum, 64)
                .input(OPTICAL_PIPES[0], 64)
                .input(wireGtDouble, RutheniumTriniumAmericiumNeutronate, 32)
                .input(wireGtSingle, UVSuperconductor, 64)
                .input(wireGtSingle, UVSuperconductor, 64)
                .fluidInputs(Kevlar.getFluid(L * 16))
                .fluidInputs(KaptonE.getFluid(L * 32))
                .fluidInputs(Polyetheretherketone.getFluid(L * 64))
                .fluidInputs(RutheniumTriniumAmericiumNeutronate.getFluid(L * 16))
                .output(ADV_DATE_BANK)
                .stationResearch(b -> b
                        .researchStack(DATA_BANK.getStackForm())
                        .CWUt(CWT[ZPM])
                        .EUt(VA[UV]))
                .EUt(VA[UV])
                .duration(2000)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Advanced Assembly Line
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 8)
                .input(ASSEMBLY_LINE, 8)
                .input(frameGt, Pikyonium64B, 4)
                .input(ASSEMBLER[IV], 8)
                .input(CIRCUIT_ASSEMBLER[LuV], 8)
                .input(ROBOT_ARM_LuV, 4)
                .input(EMITTER_LuV, 2)
                .input(CONVEYOR_MODULE_LuV, 2)
                .input(plateDouble, HY1301, 6)
                .input(plateDouble, Naquadria, 6)
                .input(wireFine, Ruridit, 64)
                .input(wireFine, Ruridit, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(ADVANCED_ASSEMBLY_LINE)
                .EUt(VA[LuV])
                .duration(1200)
                .stationResearch(b -> b
                        .researchStack(DISK_22.getStackForm())
                        .EUt(VA[LuV])
                        .CWUt(CWT[LuV]))
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 8)
                .input(POWER_TRANSFORMER[LuV], 4)
                .input(circuit, Tier.LuV, 16)
                .input(wireGtSingle, IndiumTinBariumTitaniumCuprate, 32)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 8)
                .fluidInputs(PCBCoolant.getFluid(1000))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Zylon.getFluid(L * 16))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 16))
                .stationResearch(b -> b
                        .researchStack(DISK_22.getStackForm())
                        .EUt(VA[LuV])
                        .CWUt(CWT[LuV]))
                .output(ACTIVE_TRANSFORMER)
                .duration(600).EUt(VA[LuV]).buildAndRegister();

        //  Large Circuit Assembly Line
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 8)
                .input(ASSEMBLY_LINE)
                .input(LARGE_CIRCUIT_ASSEMBLER, 4)
                .input(CIRCUIT_ASSEMBLER[LuV], 16)
                .input(frameGt, HastelloyC59, 16)
                .input(ROBOT_ARM_LuV, 64)
                .input(CONVEYOR_MODULE_LuV, 64)
                .input(plateDouble, Tantalloy61, 6)
                .input(plateDouble, MARM200CeSteel, 6)
                .input(gear, HSSE, 6)
                .input(gearSmall, Osmiridium, 3)
                .input(wireFine, Ruridit, 64)
                .input(wireFine, Ruridit, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .output(LARGE_CIRCUIT_ASSEMBLY_LINE)
                .EUt(VA[LuV])
                .duration(1200)
                .stationResearch(b -> b
                        .researchStack(DISK_22.getStackForm())
                        .EUt(VA[LuV])
                        .CWUt(CWT[LuV]))
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(POWER.getItemVariant(POWER_SUPPLY_BASIC))
                .input(circuit, Tier.IV, 8)
                .inputNBT(TOOL_DATA_ORB, NBTMatcher.ANY, NBTCondition.ANY)
                .input(frameGt, TungstenSteel, 32)
                .input(plate, NanometerBariumTitanate, 32)
                .input(wireFine, Ruridit, 64)
                .input(wireFine, NiobiumTitanium, 64)
                .input(OPTICAL_PIPES[0], 64)
                .input(wireGtDouble, IVSuperconductor, 16)
                .fluidInputs(SolderingAlloy.getFluid(L * 2))
                .fluidInputs(Lubricant.getFluid(500))
                .output(MINI_DATE_BANK)
                .scannerResearch(b -> b
                        .researchStack(DATA_ACCESS_HATCH.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .duration(1200).EUt(4000).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(COMPUTER_CASING.getItemVariant(BlockComputerCasing.CasingType.COMPUTER_CASING))
                .input(MINI_DATE_BANK, 8)
                .input(circuit, Tier.ZPM, 8)
                .inputNBT(TOOL_DATA_ORB, NBTMatcher.ANY, NBTCondition.ANY)
                .input(frameGt, HSSS, 32)
                .input(plate, PPB, 32)
                .input(wireFine, YttriumBariumCuprate, 64)
                .input(wireFine, Europium, 64)
                .input(OPTICAL_PIPES[0], 64)
                .input(wireGtDouble, IVSuperconductor, 16)
                .fluidInputs(SolderingAlloy.getFluid(L * 2))
                .fluidInputs(Lubricant.getFluid(500))
                .output(DATA_BANK)
                .scannerResearch(b -> b
                        .researchStack(DISK_25.getStackForm())
                        .duration(1200)
                        .EUt(VA[LuV]))
                .duration(1200).EUt(6000).buildAndRegister();


        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .input(HULL[IV], 8)
                .input(ROBOT_ARM_IV, 32)
                .input(TOOL_DATA_STICK, 1)
                .input(frameGt, Naquadah, 16)
                .input(plate, Staballoy, 16)
                .input(ring, HSSS, 32)
                .input(gearSmall, PPB, 32)
                .input(cableGtSingle, NiobiumTitanium, 64)
                .input(cableGtSingle, NiobiumTitanium, 64)
                .output(LAGER_PROCESSING_FACTORY)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .scannerResearch(b -> b
                        .researchStack(ASSEMBLER[5].getStackForm())
                        .EUt(VA[IV]))
                .duration(2000).EUt(VA[IV]).buildAndRegister();


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
                .input(ELECTRIC_MOTOR_MV, 16)
                .input(ELECTRIC_PUMP_MV, 16)
                .input(rotor, Steel, 8)
                .input(QUANTUM_TANK[1], 4)
                .input(pipeHugeFluid, Steel, 4)
                .input(plate, Aluminium, 8)
                .fluidInputs(Polytetrafluoroethylene.getFluid(4000))
                .fluidInputs(Aluminium.getFluid(4000))
                .fluidInputs(Brominatedepoxyresins.getFluid(4000))
                .fluidInputs(Polyethylene.getFluid(4000))
                .scannerResearch(b -> b
                        .researchStack(DISK_17.getStackForm())
                        .duration(1200)
                        .EUt(VA[MV]))
                .output(BIOLOGICAL_REACTION)
                .duration(200).EUt(120).buildAndRegister();

        //DIGESTER
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[3].getStackForm(16))
                .input(circuit, Tier.HV, 8)
                .input(ELECTRIC_MOTOR_HV, 16)
                .input(VOLTAGE_COIL_HV, 16)
                .input(rotor, StainlessSteel, 8)
                .input(QUANTUM_TANK[2], 4)
                .input(pipeHugeFluid, Polytetrafluoroethylene, 4)
                .input(plate, NanometerBariumTitanate, 8)
                .fluidInputs(Polytetrafluoroethylene.getFluid(4000))
                .fluidInputs(TungstenSteel.getFluid(4000))
                .scannerResearch(b -> b
                        .researchStack(DISK_20.getStackForm())
                        .duration(1200)
                        .EUt(VA[MV]))
                .output(DIGESTER)
                .duration(2000).EUt(480).buildAndRegister();
        //ALGAE_FARM
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[3].getStackForm(16))
                .input(circuit, Tier.HV, 8)
                .input(ELECTRIC_PUMP_HV, 16)
                .input(VOLTAGE_COIL_MV, 16)
                .input(gear, StainlessSteel, 8)
                .input(RANDOM_ACCESS_MEMORY, 4)
                .input(wireFine, Aluminium, 8)
                .input(foil, Aluminium, 8)
                .fluidInputs(Polytetrafluoroethylene.getFluid(4000))
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Brominatedepoxyresins.getFluid(4000))
                .fluidInputs(Polyethylene.getFluid(4000))
                .scannerResearch(b -> b
                        .researchStack(DISK_20.getStackForm())
                        .duration(1200)
                        .EUt(VA[HV]))
                .output(ALGAE_FARM)
                .duration(2000).EUt(480).buildAndRegister();


        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[4].getStackForm(16))
                .input(circuit, Tier.EV, 8)
                .input(ELECTRIC_PUMP_EV, 16)
                .input(VOLTAGE_COIL_EV, 16)
                .input(gear, HSSE, 16)
                .input(NANO_CENTRAL_PROCESSING_UNIT, 4)
                .input(wireFine, Platinum, 64)
                .input(wireFine, TungstenSteel, 64)
                .input(foil, Palladium, 32)
                .fluidInputs(Zylon.getFluid(4000))
                .scannerResearch(b -> b
                        .researchStack(DISK_20.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .output(COKING_TOWER)
                .duration(2000).EUt(480).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[4].getStackForm(16))
                .input(circuit, Tier.EV, 8)
                .input(ELECTRIC_PUMP_EV, 16)
                .input(VOLTAGE_COIL_EV, 16)
                .input(gear, HSSG, 16)
                .input(NANO_CENTRAL_PROCESSING_UNIT, 4)
                .input(wireFine, TungstenSteel, 64)
                .input(wireFine, TungstenSteel, 64)
                .input(foil, Palladium, 32)
                .fluidInputs(Zylon.getFluid(4000))
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .output(SMSF)
                .duration(2000).EUt(480).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[4].getStackForm(16))
                .input(circuit, Tier.IV, 8)
                .input(ELECTRIC_PUMP_EV, 16)
                .input(VOLTAGE_COIL_EV, 16)
                .input(gear, TungstenSteel, 8)
                .input(NANO_CENTRAL_PROCESSING_UNIT, 4)
                .input(pipeHugeFluid, TungstenSteel, 8)
                .input(wireFine, Platinum, 64)
                .input(wireFine, TungstenSteel, 64)
                .input(foil, HSSE, 32)
                .fluidInputs(Zylon.getFluid(4000))
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .output(VACUUM_DISTILLATION_TOWER)
                .duration(2000).EUt(480).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(ARC_FURNACE[4].getStackForm(16))
                .input(circuit, Tier.IV, 8)
                .input(CONVEYOR_MODULE_EV, 16)
                .input(VOLTAGE_COIL_EV, 32)
                .input(foil, HSSG, 8)
                .input(gear, TungstenSteel, 8)
                .input(NANO_CENTRAL_PROCESSING_UNIT, 4)
                .input(wireFine, Platinum, 64)
                .input(wireFine, TungstenSteel, 64)
                .input(foil, Palladium, 32)
                .fluidInputs(Zylon.getFluid(4000))
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .output(ADV_ARC_FURNACE)
                .duration(2000).EUt(480).buildAndRegister();
        //op
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[3].getStackForm(16))
                .input(circuit, Tier.HV, 8)
                .input(ELECTRIC_MOTOR_HV, 16)
                .input(ELECTRIC_PUMP_HV, 16)
                .input(gear, StainlessSteel, 8)
                .input(RANDOM_ACCESS_MEMORY, 4)
                .input(wireFine, Aluminium, 8)
                .input(foil, Aluminium, 8)
                .fluidInputs(Polytetrafluoroethylene.getFluid(4000))
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Brominatedepoxyresins.getFluid(4000))
                .fluidInputs(Polyethylene.getFluid(4000))
                .scannerResearch(b -> b
                        .researchStack(DISK_20.getStackForm())
                        .duration(1200)
                        .EUt(VA[MV]))
                .output(INTEGRATED_MINING_DIVISION)
                .duration(2000).EUt(480).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[EV])
                .input(frameGt, TungstenSteel, 4)
                .input(plate, TitaniumTungstenCarbide, 4)
                .input(circuit, MarkerMaterials.Tier.EV, 4)
                .input(ELECTRIC_PISTON_EV, 2)
                .input(gear, TanmolyiumBetaC, 2)
                .input(gearSmall, EglinSteel, 4)
                .input(screw, NanometerBariumTitanate, 16)
                .input(foil, StainlessSteel, 8)
                .input(cableGtQuadruple, Platinum, 4)
                .fluidInputs(SolderingAlloy.getFluid(5760))
                .fluidInputs(Lubricant.getFluid(3000))
                .fluidInputs(CobaltBrass.getFluid(L * 4))
                .output(VACUUM_DRYING_FURNACE)
                .scannerResearch(b -> b
                        .researchStack(DISK_20.getStackForm())
                        .EUt(VA[HV])
                        .duration(600))
                .EUt(VA[EV])
                .duration(1200)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[EV])
                .input(frameGt, StainlessSteel, 4)
                .input(plate, Iridium, 4)
                .input(circuit, MarkerMaterials.Tier.EV, 4)
                .input(ELECTRIC_PUMP_EV, 2)
                .input(screw, TanmolyiumBetaC, 2)
                .input(plate, TitaniumTungstenCarbide, 16)
                .input(foil, TungstenSteel, 8)
                .input(wireFine, Platinum, 32)
                .fluidInputs(SolderingAlloy.getFluid(5760))
                .fluidInputs(Zylon.getFluid(5760))
                .fluidInputs(CobaltBrass.getFluid(L * 4))
                .output(FROTH_FLOTATION_TANK)
                .scannerResearch(b -> b
                        .researchStack(DISK_20.getStackForm())
                        .EUt(VA[HV])
                        .duration(600))
                .EUt(VA[EV])
                .duration(1200)
                .buildAndRegister();
        //  Isa Mill
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, IncoloyMA956)
                .inputs(GTQTMetaBlocks.ISA_CASING.getItemVariant(ISA_MILL_CASING_GEARBOX, 4))
                .input(COMPONENT_GRINDER_TUNGSTEN, 16)
                .input(circuit, MarkerMaterials.Tier.LuV, 8)
                .input(gear, Inconel625, 8)
                .input(plate, Inconel625, 32)
                .input(plateDouble, HSSE, 8)
                .input(plate, Stellite100, 8)
                .input(screw, HSSG, 64)
                .input(wireFine, NiobiumTitanium, 64)
                .input(wireFine, NiobiumTitanium, 64)
                .input(foil, Titanium, 32)
                .fluidInputs(Zeron100.getFluid(2304))
                .fluidInputs(Trinium.getFluid(4608))
                .fluidInputs(HastelloyC276.getFluid(4608))
                .output(ISA_MILL)
                .EUt(VA[LuV])
                .duration(3200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .EUt(VA[IV])
                        .duration(1200))
                .buildAndRegister();

        //  Grindball Hatch
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(15)
                .input(TIERED_HATCH[EV])
                .input(ITEM_IMPORT_BUS[EV])
                .input(gear, Titanium, 4)
                .input(COMPONENT_GRINDER_TUNGSTEN)
                .input(wireFine, Tungsten, 16)
                .fluidInputs(TinAlloy.getFluid(L * 8))
                .output(MULTIPART_BALL_HATCH)
                .EUt(VA[EV])
                .duration(200)
                .buildAndRegister();

        //  Soapstone Grindball
        VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .input(dust, Soapstone, 4)
                .notConsumable(SHAPE_MOLD_BALL)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .output(GRINDBALL_SOAPSTONE)
                .EUt(VA[EV])
                .duration(300)
                .buildAndRegister();

        //  Aluminium Grindball
        VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .input(dust, Aluminium, 4)
                .notConsumable(SHAPE_MOLD_BALL)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .output(GRINDBALL_ALUMINIUM)
                .EUt(VA[EV])
                .duration(300)
                .buildAndRegister();

        //  Flotation Factory
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, HastelloyX)
                .inputs(GTQTMetaBlocks.ISA_CASING.getItemVariant(FLOTATION_CASING_GEARBOX, 4))
                .input(CONVEYOR_MODULE_LuV, 8)
                .input(ELECTRIC_PUMP_LuV, 8)
                .input(circuit, MarkerMaterials.Tier.LuV, 8)
                .input(gear, HastelloyN, 8)
                .input(plate, HastelloyN, 32)
                .input(plateDouble, Osmiridium, 8)
                .input(plate, MaragingSteel300, 8)
                .input(screw, Trinium, 32)
                .input(wireFine, YttriumBariumCuprate, 64)
                .input(wireFine, YttriumBariumCuprate, 64)
                .input(foil, NiobiumNitride, 32)
                .fluidInputs(WatertightSteel.getFluid(2304))
                .fluidInputs(NaquadahEnriched.getFluid(4608))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(4608))
                .output(FLOTATION_FACTORY)
                .EUt(VA[LuV])
                .duration(3200)
                .scannerResearch(b -> b
                        .researchStack(DISK_21.getStackForm())
                        .EUt(VA[IV])
                        .duration(1200))
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, HastelloyX, 16)
                .inputs(POWER.getItemVariant(POWER_SUPPLY_BASIC, 4))
                .input(EMITTER_EV, 8)
                .input(SENSOR_EV, 8)
                .input(circuit, MarkerMaterials.Tier.EV, 16)
                .input(gear, TungstenSteel, 8)
                .input(plate, Platinum, 32)
                .input(plate, Palladium, 8)
                .input(plate, MaragingSteel300, 8)
                .input(screw, HSSG, 32)
                .input(stickLong, NanometerBariumTitanate, 64)
                .input(stickLong, NanometerBariumTitanate, 64)
                .input(foil, NiobiumNitride, 32)
                .fluidInputs(Epoxy.getFluid(9216))
                .fluidInputs(Zylon.getFluid(4608))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(4608))
                .output(POWER_SUPPLY)
                .EUt(VA[EV])
                .duration(3200)
                .scannerResearch(b -> b
                        .researchStack(LAPOTRON_CRYSTAL.getStackForm())
                        .EUt(VA[EV])
                        .duration(1200))
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, HastelloyX, 64)
                .inputs(POWER.getItemVariant(POWER_SUPPLY_BASIC, 64))
                .input(EMITTER_IV, 32)
                .input(SENSOR_IV, 32)
                .input(circuit, MarkerMaterials.Tier.IV, 64)
                .input(gear, NaquadahAlloy, 8)
                .input(plate, RhodiumPlatedPalladium, 32)
                .input(plate, Ruridit, 32)
                .input(screw, HSSS, 32)
                .input(stickLong, NanometerBariumTitanate, 64)
                .input(stickLong, NanometerBariumTitanate, 64)
                .input(foil, NiobiumNitride, 32)
                .input(wireGtSingle, EVSuperconductor, 16)
                .fluidInputs(Zylon.getFluid(9216))
                .fluidInputs(Polybenzimidazole.getFluid(4608))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(4608))
                .output(MICROWAVE_ENERGY_RECEIVER_CONTROL)
                .EUt(VA[IV])
                .duration(3200)
                .scannerResearch(b -> b
                        .researchStack(POWER_SUPPLY.getStackForm())
                        .EUt(VA[EV])
                        .duration(1200))
                .buildAndRegister();

        //Arc
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm(16))
                .input(circuit, Tier.HV, 8)
                .input(ELECTRIC_MOTOR_MV, 16)
                .input(ELECTRIC_PUMP_MV, 16)
                .input(gear, Aluminium, 16)
                .input(wireFine, Gold, 64)
                .input(wireFine, Gold, 64)
                .input(foil, Aluminium, 64)
                .fluidInputs(Polystyrene.getFluid(4000))
                .output(ELECTRIC_ARC_FURNACE)
                .duration(400).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm(8))
                .input(circuit, Tier.HV, 8)
                .input(ELECTRIC_PISTON_MV, 16)
                .input(ELECTRIC_PUMP_MV, 16)
                .input(pipeHugeFluid, Steel, 8)
                .input(gear, Aluminium, 8)
                .input(rotor, Aluminium, 8)
                .fluidInputs(Epoxy.getFluid(4000))
                .output(FLUIDIZED_BED)
                .duration(200).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[3].getStackForm(8))
                .input(circuit, Tier.HV, 8)
                .input(ELECTRIC_PISTON_HV, 16)
                .input(ELECTRIC_PUMP_HV, 16)
                .input(pipeHugeFluid, StainlessSteel, 8)
                .input(gear, Aluminium, 8)
                .input(rotor, Aluminium, 8)
                .fluidInputs(Epoxy.getFluid(4000))
                .output(FIX_BED)
                .duration(200).EUt(480).buildAndRegister();

    }

    private static void I_VV() {
        //
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[EV])
                .input(ELECTRIC_PISTON_EV, 4)
                .input(ELECTRIC_MOTOR_EV, 4)
                .input(circuit, Tier.EV, 2)
                .input(HULL[EV])
                .input(cableGtSingle, Aluminium, 2)
                .input(gear, Titanium, 2)
                .fluidInputs(Polytetrafluoroethylene.getFluid(GTValues.L * 24))
                .outputs(COMBUSTION_GENERATOR[0].getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_1.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .duration(100).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[IV])
                .input(ELECTRIC_PISTON_IV, 4)
                .input(ELECTRIC_MOTOR_IV, 4)
                .input(circuit, Tier.IV, 2)
                .input(HULL[IV])
                .input(cableGtSingle, Platinum, 2)
                .input(gear, TungstenSteel, 2)
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
                .input(ELECTRIC_PISTON_EV, 4)
                .input(ELECTRIC_MOTOR_EV, 4)
                .input(circuit, Tier.EV, 2)
                .input(HULL[EV])
                .input(cableGtSingle, Aluminium, 2)
                .input(rotor, Titanium, 2)
                .fluidInputs(Polytetrafluoroethylene.getFluid(GTValues.L * 24))
                .outputs(GAS_TURBINE[0].getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_1.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .duration(100).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[IV])
                .input(ELECTRIC_PISTON_IV, 4)
                .input(ELECTRIC_MOTOR_IV, 4)
                .input(circuit, Tier.IV, 2)
                .input(HULL[IV])
                .input(cableGtSingle, Platinum, 2)
                .input(rotor, TungstenSteel, 2)
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
                .input(ELECTRIC_PISTON_EV, 4)
                .input(ELECTRIC_MOTOR_EV, 4)
                .input(circuit, Tier.EV, 2)
                .input(HULL[EV])
                .input(cableGtSingle, Aluminium, 2)
                .input(pipeNormalFluid, Titanium, 2)
                .fluidInputs(Polytetrafluoroethylene.getFluid(GTValues.L * 24))
                .outputs(STEAM_TURBINE[0].getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_1.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .duration(100).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[IV])
                .input(ELECTRIC_PISTON_IV, 4)
                .input(ELECTRIC_MOTOR_IV, 4)
                .input(circuit, Tier.IV, 2)
                .input(HULL[IV])
                .input(cableGtSingle, Platinum, 2)
                .input(pipeNormalFluid, TungstenSteel, 2)
                .fluidInputs(Polybenzimidazole.getFluid(GTValues.L * 24))
                .outputs(STEAM_TURBINE[1].getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_1.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(100).buildAndRegister();

        //火箭
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[EV])
                .input(ELECTRIC_PISTON_EV, 8)
                .input(ELECTRIC_MOTOR_EV, 8)
                .input(circuit, Tier.EV, 4)
                .input(HULL[EV])
                .input(wireGtDouble, UraniumTriplatinum, 8)
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
                .input(ELECTRIC_PISTON_IV, 8)
                .input(ELECTRIC_MOTOR_IV, 8)
                .input(circuit, Tier.IV, 4)
                .input(HULL[IV])
                .input(wireGtDouble, SamariumIronArsenicOxide, 8)
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
                .input(ELECTRIC_PISTON_LUV, 8)
                .input(ELECTRIC_MOTOR_LuV, 8)
                .input(circuit, Tier.LuV, 4)
                .input(HULL[LuV])
                .input(wireGtDouble, IndiumTinBariumTitaniumCuprate, 8)
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
                .input(ELECTRIC_PISTON_EV, 8)
                .input(ELECTRIC_MOTOR_EV, 8)
                .input(circuit, Tier.EV, 8)
                .input(HULL[EV], 4)
                .input(wireGtDouble, UraniumTriplatinum, 16)
                .input(gear, Titanium, 2)
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
                .input(ELECTRIC_PISTON_IV, 8)
                .input(ELECTRIC_MOTOR_IV, 8)
                .input(circuit, Tier.IV, 8)
                .input(HULL[IV], 4)
                .input(wireGtDouble, SamariumIronArsenicOxide, 16)
                .input(gear, TungstenSteel, 2)
                .input(plateDouble, TungstenSteel, 32)
                .fluidInputs(Polybenzimidazole.getFluid(GTValues.L * 24))
                .outputs(EXTREME_COMBUSTION_ENGINE.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_2.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(100).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(LARGE_STEAM_TURBINE, 6)
                .input(plate, WatertightSteel, 8)
                .input(circuit, MarkerMaterials.Tier.LuV, 32)
                .input(ELECTRIC_PUMP_IV, 4)
                .input(FLUID_REGULATOR_IV, 4)
                .input(gear, TanmolyiumBetaC, 8)
                .input(screw, MARM200Steel, 16)
                .fluidInputs(BlueSteel.getFluid(L * 16))
                .stationResearch(b -> b
                        .researchStack(DISK_3.getStackForm())
                        .CWUt(CWT[IV])
                        .EUt(VA[IV]))
                .output(MEGA_STEAM_TURBINE)
                .EUt(VA[IV])
                .duration(1200)
                .buildAndRegister();

        //  Mega Gas Turbine
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(LARGE_GAS_TURBINE, 6)
                .input(plate, TantalumCarbide, 8)
                .input(circuit, MarkerMaterials.Tier.ZPM, 32)
                .input(ELECTRIC_PUMP_LuV, 4)
                .input(FLUID_REGULATOR_LUV, 4)
                .input(rotor, Staballoy, 8)
                .input(screw, IncoloyMA813, 16)
                .fluidInputs(Naquadah.getFluid(L * 16))
                .stationResearch(b -> b
                        .researchStack(DISK_3.getStackForm())
                        .CWUt(CWT[IV])
                        .EUt(VA[LuV]))
                .output(MEGA_GAS_TURBINE)
                .EUt(VA[LuV])
                .duration(1200)
                .buildAndRegister();

        //  Mega Plasma Turbine
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(LARGE_PLASMA_TURBINE, 6)
                .input(plate, HMS1J79Alloy, 8)
                .input(circuit, MarkerMaterials.Tier.UV, 32)
                .input(ELECTRIC_PUMP_ZPM, 4)
                .input(FLUID_REGULATOR_ZPM, 4)
                .input(spring, Pikyonium64B, 8)
                .input(screw, Trinium, 16)
                .fluidInputs(NaquadahAlloy.getFluid(L * 16))
                .stationResearch(b -> b
                        .researchStack(DISK_3.getStackForm())
                        .CWUt(CWT[IV])
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
                        .CWUt(CWT[IV])
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
                        .CWUt(CWT[IV])
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
                        .CWUt(CWT[IV])
                        .EUt(VA[ZPM]))
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[IV])
                .input(ELECTRIC_PISTON_IV, 16)
                .input(ELECTRIC_MOTOR_IV, 16)
                .input(circuit, Tier.IV, 8)
                .input(HULL[IV], 4)
                .input(wireGtDouble, SamariumIronArsenicOxide, 8)
                .input(plate, TungstenSteel, 32)
                .fluidInputs(Materials.Polybenzimidazole.getFluid(GTValues.L * 24))
                .outputs(HUGE_TURBINE_COMBUSTION_CHAMBER.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_4.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(100).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[IV])
                .input(ELECTRIC_PISTON_IV, 16)
                .input(ELECTRIC_MOTOR_IV, 16)
                .input(circuit, Tier.IV, 8)
                .input(HULL[IV], 4)
                .input(wireGtDouble, TungstenSteel, 8)
                .input(plate, NanometerBariumTitanate, 32)
                .fluidInputs(Zylon.getFluid(GTValues.L * 24))
                .outputs(LARGE_GAS_TURBINE.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_4.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(100).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .EUt(VA[LuV])
                .input(ELECTRIC_PISTON_LUV, 16)
                .input(ELECTRIC_MOTOR_LuV, 16)
                .input(circuit, Tier.LuV, 4)
                .input(HULL[LuV], 4)
                .input(wireGtDouble, IndiumTinBariumTitaniumCuprate, 8)
                .input(plate, Materials.NiobiumTitanium, 32)
                .fluidInputs(Materials.Trinium.getFluid(GTValues.L * 24))
                .outputs(ROCKET.getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_4.getStackForm())
                        .duration(1200)
                        .EUt(VA[LuV]))
                .duration(100).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(OrePrefix.wireGtDouble, IVSuperconductor, 64)
                .input(OrePrefix.foil, Materials.NiobiumTitanium, 64)
                .fluidInputs(Materials.Trinium.getFluid(4608))
                .outputs(MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL))
                .scannerResearch(b -> b
                        .researchStack(DISK_5.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .duration(800).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(OrePrefix.wireGtDouble, LuVSuperconductor, 32)
                .input(OrePrefix.foil, Materials.NiobiumTitanium, 32)
                .fluidInputs(Materials.Trinium.getFluid(3456))
                .outputs(MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL))
                .scannerResearch(b -> b
                        .researchStack(DISK_5.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .duration(800).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(OrePrefix.wireGtDouble, ZPMSuperconductor, 16)
                .input(OrePrefix.foil, Materials.NiobiumTitanium, 16)
                .fluidInputs(Materials.Trinium.getFluid(2304))
                .outputs(MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL))
                .scannerResearch(b -> b
                        .researchStack(DISK_5.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .duration(800).EUt(VA[ZPM]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(OrePrefix.wireGtDouble, UVSuperconductor, 8)
                .input(OrePrefix.foil, Materials.NiobiumTitanium, 8)
                .fluidInputs(Materials.Trinium.getFluid(1152))
                .outputs(MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL))
                .scannerResearch(b -> b
                        .researchStack(DISK_5.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .duration(800).EUt(VA[UV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(OrePrefix.wireGtDouble, UHVSuperconductor, 4)
                .input(OrePrefix.foil, Materials.NiobiumTitanium, 4)
                .fluidInputs(Materials.Trinium.getFluid(576))
                .outputs(MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL))
                .scannerResearch(b -> b
                        .researchStack(DISK_5.getStackForm())
                        .duration(1200)
                        .EUt(VA[EV]))
                .duration(800).EUt(VA[UHV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .inputs(FUSION_CASING.getItemVariant(SUPERCONDUCTOR_COIL))
                .input(circuit, Tier.ZPM, 64)
                .input(plateDouble, Plutonium241, 6)
                .input(plateDouble, NaquadahAlloy, 6)
                .input(FIELD_GENERATOR_IV, 32)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 16))
                .fluidInputs(NiobiumTitanium.getFluid(L * 16))
                .fluidInputs(Zylon.getFluid(L * 16))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .outputs(FUSION_REACTOR[0].getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_6.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(2000).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 8)
                .inputs(FUSION_CASING.getItemVariant(FUSION_COIL))
                .input(circuit, Tier.UV, 64)
                .input(plateDouble, Naquadria, 6)
                .input(plateDouble, Duranium, 6)
                .input(FIELD_GENERATOR_LuV, 32)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, ZPMSuperconductor, 64)
                .input(wireGtSingle, ZPMSuperconductor, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 16))
                .fluidInputs(VanadiumGallium.getFluid(L * 16))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .outputs(FUSION_REACTOR[1].getStackForm())
                .stationResearch(b -> b
                        .researchStack(FUSION_REACTOR[0].getStackForm())
                        .CWUt(CWT[LuV])
                        .EUt(VA[ZPM]))
                .duration(2000).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III, 8)
                .inputs(FUSION_CASING.getItemVariant(FUSION_COIL))
                .input(circuit, Tier.UHV, 64)
                .input(plateDouble, Darmstadtium, 6)
                .input(plateDouble, Americium, 6)
                .input(FIELD_GENERATOR_ZPM, 32)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, UHVSuperconductor, 64)
                .input(wireGtSingle, UHVSuperconductor, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 16))
                .fluidInputs(YttriumBariumCuprate.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(KaptonK.getFluid(L * 16))
                .outputs(FUSION_REACTOR[2].getStackForm())
                .stationResearch(b -> b
                        .researchStack(FUSION_REACTOR[1].getStackForm())
                        .CWUt(CWT[ZPM])
                        .EUt(VA[UV]))
                .duration(2000).EUt(VA[ZPM]).buildAndRegister();

        //压缩聚变
        //  Compressed Fusion Reactor Mk I
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 16)
                .input(HULL[LuV], 16)
                .input(FUSION_REACTOR[0], 16)
                .input(plate, Europium, 32)
                .input(circuit, MarkerMaterials.Tier.ZPM, 64)
                .input(ELECTRIC_PUMP_LuV, 32)
                .input(FIELD_GENERATOR_LuV, 32)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 16))
                .fluidInputs(NiobiumTitanium.getFluid(L * 16))
                .fluidInputs(Zylon.getFluid(L * 16))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .output(COMPRESSED_FUSION_REACTOR[0])
                .stationResearch(b -> b
                        .researchStack(FUSION_REACTOR[0].getStackForm())
                        .EUt(VA[LuV])
                        .CWUt(CWT[LuV]))
                .EUt(VA[LuV])
                .duration(1200)
                .buildAndRegister();

        //  Compressed Fusion Reactor Mk II
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 16)
                .input(HULL[ZPM], 16)
                .input(FUSION_REACTOR[1], 16)
                .input(plate, Americium, 32)
                .input(circuit, MarkerMaterials.Tier.UV, 64)
                .input(ELECTRIC_PUMP_ZPM, 32)
                .input(FIELD_GENERATOR_ZPM, 32)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, ZPMSuperconductor, 64)
                .input(wireGtSingle, ZPMSuperconductor, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 16))
                .fluidInputs(VanadiumGallium.getFluid(L * 16))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .output(COMPRESSED_FUSION_REACTOR[1])
                .stationResearch(b -> b
                        .researchStack(FUSION_REACTOR[1].getStackForm())
                        .EUt(VA[ZPM])
                        .CWUt(CWT[ZPM]))
                .EUt(VA[ZPM])
                .duration(1200)
                .buildAndRegister();

        //  Compressed Fusion Reactor Mk III
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III, 16)
                .input(HULL[UV], 16)
                .input(FUSION_REACTOR[2], 16)
                .input(plate, Darmstadtium, 32)
                .input(circuit, MarkerMaterials.Tier.UHV, 64)
                .input(ELECTRIC_PUMP_UV, 32)
                .input(FIELD_GENERATOR_UV, 32)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, UVSuperconductor, 64)
                .input(wireGtSingle, UVSuperconductor, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 16))
                .fluidInputs(YttriumBariumCuprate.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(KaptonK.getFluid(L * 16))
                .output(COMPRESSED_FUSION_REACTOR[2])
                .stationResearch(b -> b
                        .researchStack(FUSION_REACTOR[2].getStackForm())
                        .EUt(VA[UV])
                        .CWUt(CWT[UV]))
                .EUt(VA[UV])
                .duration(1200)
                .buildAndRegister();
        //线圈
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
                        .EUt(VA[HV]))
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
                        .EUt(VA[HV]))
                .duration(400).EUt(VA[EV]).buildAndRegister();

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
                        .CWUt(CWT[IV])
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
                        .CWUt(CWT[IV])
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
                        .CWUt(CWT[IV])
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
                        .CWUt(CWT[IV])
                        .EUt(VA[LuV]))
                .duration(400).EUt(VA[LuV]).buildAndRegister();

    }

    public static void KQ_NET(int kind, int tier, int number, MetaItem<?>.MetaValueItem item, OrePrefix prefix, Material material) {
        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .Tier(tier)
                .KI(kind)
                .input(DISK_0)
                .notConsumable(prefix, material)
                .output(item)
                .EUt(VA[2 * tier])
                .CWUt(CWT[2 * tier])
                .duration(20000)
                .NB(number)
                .buildAndRegister();
    }

    public static void KQ_NET(int kind, int tier, int number, MetaItem<?>.MetaValueItem item1, MetaItem<?>.MetaValueItem item2) {
        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .Tier(tier)
                .KI(kind)
                .input(DISK_0)
                .notConsumable(item2)
                .output(item1)
                .EUt(VA[2 * tier])
                .CWUt(CWT[2 * tier])
                .duration(20000)
                .NB(number)
                .buildAndRegister();
    }

    public static void KQ_NET(int kind, int tier, int number, MetaItem<?>.MetaValueItem item, ItemStack itemStack) {
        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .Tier(tier)
                .KI(kind)
                .input(DISK_0)
                .notConsumable(itemStack)
                .output(item)
                .EUt(VA[2 * tier])
                .CWUt(CWT[2 * tier])
                .duration(20000)
                .NB(number)
                .buildAndRegister();
    }

}
