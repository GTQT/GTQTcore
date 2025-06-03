package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.unification.material.MarkerMaterials;
import gregtech.common.blocks.BlockFusionCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.api.utils.ParticleHelper;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockCompressedFusionReactor;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockGlass;
import keqing.gtqtcore.common.items.GTQTMetaItems;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.LASER_FUSION_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.swarm;
import static keqing.gtqtcore.api.utils.ParticleHelper.metaItemArray;
import static keqing.gtqtcore.common.block.GTQTMetaBlocks.blockCompressedFusionReactor;
import static keqing.gtqtcore.common.block.GTQTMetaBlocks.blockMultiblockCasing7;
import static keqing.gtqtcore.common.block.blocks.BlockCompressedFusionReactor.CasingType.FUSION_COIL_MKII;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing7.CasingType.MAGNETIC_FIELD_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing7.CasingType.PROTOMATTER_ACTIVATION_COIL;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockGlass.CasingType.ANTIMATTER_CONTAINMENT_CASING;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.*;

public class AntimatterRecipes {
    /* 在UHV建造 高能激光注入单元 高能激光聚合核心 以生产 量子反常，作为UEV多方块的核心耗材
     * 高能激光聚合核心 还用于 合成 反粒子
     * 造价参考压缩MKIV
     */
    public static void init() {
        //控制器
        Control();
        //外壳
        Casing();
        //反物质收集
        AntimatterCollection();
        //炖反粒子
        LaserFusion();
        //反物质发电
        AntimatterGenerator();
    }
    private static void Casing() {
        //虚空力场通量外壳 UHV-UEV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt,Neutronium,1)
                .input(FIELD_GENERATOR_UHV,1)
                .input(circuit, MarkerMaterials.Tier.UHV,2)
                .input(circuit, MarkerMaterials.Tier.UV,4)
                .input(plate, Duranium, 8)
                .input(screw,EnrichedNaqAlloy,4)
                .input(rotor,Americium,2)
                .fluidInputs(KaptonE.getFluid(L * 32))
                .fluidInputs(Cinobite.getFluid(L * 4))
                .fluidInputs(Octahedrite.getFluid(L * 4))
                .fluidInputs(AstralTitanium.getFluid(L * 4))
                .outputs(blockMultiblockCasing7.getItemVariant(MAGNETIC_FIELD_CASING))
                .stationResearch(b -> b
                        .researchStack(MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.FUSION_CASING_MK3))
                        .EUt(VA[UHV])
                        .CWUt(CWT[UV]))
                .duration(300)
                .EUt(VA[UHV])
                .buildAndRegister();

        //反物质磁约束链接玻璃 UHV-UEV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockMultiblockGlass.getItemVariant(BlockMultiblockGlass.CasingType.TECH_FUSION_GLASS_IV))
                .input(circuit, MarkerMaterials.Tier.UHV,2)
                .input(circuit, MarkerMaterials.Tier.UV,4)
                .input(plate, Duranium, 8)
                .input(screw,EnrichedNaqAlloy,4)
                .fluidInputs(KaptonE.getFluid(L * 32))
                .fluidInputs(Cinobite.getFluid(L * 4))
                .fluidInputs(Octahedrite.getFluid(L * 4))
                .fluidInputs(AstralTitanium.getFluid(L * 4))
                .outputs(blockMultiblockCasing7.getItemVariant(PROTOMATTER_ACTIVATION_COIL))
                .stationResearch(b -> b
                        .researchStack(GTQTMetaBlocks.blockMultiblockGlass.getItemVariant(ANTIMATTER_CONTAINMENT_CASING))
                        .EUt(VA[UHV])
                        .CWUt(CWT[UV]))
                .duration(300)
                .EUt(VA[UHV])
                .buildAndRegister();

        //超活性源能共振线圈 UHV-UEV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockCompressedFusionReactor.getItemVariant(BlockCompressedFusionReactor.CasingType.FUSION_COIL_MKII))
                .input(VOLTAGE_COIL_UHV,2)
                .input(circuit, MarkerMaterials.Tier.UHV,2)
                .input(circuit, MarkerMaterials.Tier.UV,4)
                .input(FIELD_GENERATOR_UHV,2)
                .input(plate, Duranium, 8)
                .input(screw,EnrichedNaqAlloy,4)
                .input(NANO_POWER_IC, 4)
                .input(wireGtSingle, UHVSuperconductor, 8)
                .fluidInputs(KaptonE.getFluid(L * 32))
                .fluidInputs(Cinobite.getFluid(L * 4))
                .fluidInputs(Octahedrite.getFluid(L * 4))
                .fluidInputs(AstralTitanium.getFluid(L * 4))
                .outputs(blockMultiblockCasing7.getItemVariant(PROTOMATTER_ACTIVATION_COIL))
                .stationResearch(b -> b
                        .researchStack(GTQTMetaBlocks.blockCompressedFusionReactor.getItemVariant(BlockCompressedFusionReactor.CasingType.FUSION_COIL_MKII))
                        .EUt(VA[UHV])
                        .CWUt(CWT[UV]))
                .duration(300)
                .EUt(VA[UHV])
                .buildAndRegister();
    }
    private static void Control() {
        //高能激光注入单元 UHV-UEV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_IV, 16)
                .input(LASER_EMITTER, 64)
                .input(plate, Meitnerium, 32)
                .input(plate, Vibranium, 32)
                .input(swarm, Neutronium, 8)
                .input(circuit, MarkerMaterials.Tier.UEV, 16)
                .input(circuit, MarkerMaterials.Tier.UHV, 32)
                .input(circuit, MarkerMaterials.Tier.UV, 64)
                .input(EMITTER_UHV, 64)
                .input(FIELD_GENERATOR_UHV, 64)
                .input(NANO_POWER_IC, 64)
                .input(NANO_POWER_IC, 64)
                .input(wireGtSingle, UHVSuperconductor, 64)
                .input(wireGtSingle, UHVSuperconductor, 64)
                .fluidInputs(PbB.getFluid(L * 48))
                .fluidInputs(Cinobite.getFluid(L * 48))
                .fluidInputs(KaptonE.getFluid(L * 40))
                .fluidInputs(Teflon.getFluid(L*16))
                .output(LASER_FBT)
                .stationResearch(b -> b
                        .researchStack(DISK_33.getStackForm())
                        .EUt(VA[UHV])
                        .CWUt(CWT[UHV]))
                .EUt(VA[UHV])
                .duration(1200)
                .buildAndRegister();

        //高能激光聚合核心 UHV-UEV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_IV, 16)
                .input(LASER_TRANSLATION, 64)
                .input(plate, Meitnerium, 32)
                .input(plate, Vibranium, 32)
                .input(swarm, Neutronium, 8)
                .input(circuit, MarkerMaterials.Tier.UEV, 16)
                .input(circuit, MarkerMaterials.Tier.UHV, 32)
                .input(circuit, MarkerMaterials.Tier.UV, 64)
                .input(EMITTER_UHV, 64)
                .input(FIELD_GENERATOR_UHV, 64)
                .input(NANO_POWER_IC, 64)
                .input(NANO_POWER_IC, 64)
                .input(wireGtSingle, UHVSuperconductor, 64)
                .input(wireGtSingle, UHVSuperconductor, 64)
                .fluidInputs(PbB.getFluid(L * 48))
                .fluidInputs(Cinobite.getFluid(L * 48))
                .fluidInputs(KaptonE.getFluid(L * 40))
                .fluidInputs(Teflon.getFluid(L*16))
                .output(LASER_FBC)
                .stationResearch(b -> b
                        .researchStack(DISK_33.getStackForm())
                        .EUt(VA[UHV])
                        .CWUt(CWT[UHV]))
                .EUt(VA[UHV])
                .duration(1200)
                .buildAndRegister();

        //半稳定反物质脉冲序列器 UIV

        //屏蔽拉格朗日湮灭超核心矩阵 UIV
    }
    private static void AntimatterCollection() {
        //应该不使用配方 暂时
    }

    public static void AntimatterGenerator()
    {
        ParticleHelper.init();
        for (ParticleHelper.MetaItemWithFloat metaItemWithFloat : metaItemArray) {
            GTQTcoreRecipeMaps.ANTIMATTER_GENERATOR.recipeBuilder()
                    .input(metaItemWithFloat.item1)
                    .input(metaItemWithFloat.item2)
                    .EUt((int) V[UEV])
                    .duration(metaItemWithFloat.getDurationByTicket())
                    .buildAndRegister();
        }
    }
    public static void LaserFusion()
    {
        //量子反常是MKV（UEV）阶段的核心材料
        //使用基本粒子+
        LASER_FUSION_RECIPES.recipeBuilder()
                .input(PROTON,64)
                .input(NEUTRON,64)
                .fluidInputs(Duranium.getFluid(40))
                .chancedOutput(QUANTUM_ANOMALY,100,0)
                .EUt(VA[UEV])
                .duration(500)
                .buildAndRegister();

        //此外 强激光聚变也是获取反物质的重要途径
        //普通粒子+反物质 激光聚变 -》反物质
    }
}
