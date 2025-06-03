package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.GTValues;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.BlockFusionCasing;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockCompressedFusionReactor;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockGlass;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.ZPM;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.wireGtSingle;
import static gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_COIL;
import static gregtech.common.blocks.BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL;
import static gregtech.common.blocks.MetaBlocks.FUSION_CASING;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.items.MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT;
import static gregtech.common.metatileentities.MetaTileEntities.FUSION_REACTOR;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.KaptonK;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.swarm;
import static keqing.gtqtcore.common.block.GTQTMetaBlocks.blockCompressedFusionReactor;
import static keqing.gtqtcore.common.block.blocks.BlockCompressedFusionReactor.CasingType.FUSION_COIL_MKII;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.CIRCUIT_GOOD_III;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.ADVANCED_FUSION_REACTOR;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.COMPRESSED_FUSION_REACTOR;

public class FusionReactorRecipes {
    public static void init() {
        //控制器
        Control();
        //外壳
        Shell();
        //线圈
        Coils();
        //聚变玻璃
        Glass();
    }

    private static void Glass() {
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(MetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockGlassCasing.CasingType.FUSION_GLASS))
                .input(frameGt, Tritanium, 1)
                .input(screw, Neutronium, 4)
                .inputs(MetaItems.FIELD_GENERATOR_UHV.getStackForm(2))
                .input(OrePrefix.circuit, MarkerMaterials.Tier.UV, 2)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.ZPM, 4)
                .fluidInputs(KaptonE.getFluid(L * 2))
                .fluidInputs(Cinobite.getFluid(L * 1))
                .fluidInputs(Octahedrite.getFluid(L * 1))
                .fluidInputs(AstralTitanium.getFluid(L * 1))
                .outputs(GTQTMetaBlocks.blockMultiblockGlass.getItemVariant(BlockMultiblockGlass.CasingType.TECH_FUSION_GLASS_IV))
                .stationResearch(b -> b
                        .researchStack(MetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockGlassCasing.CasingType.FUSION_GLASS))
                        .EUt(VA[UHV])
                        .CWUt(CWT[UV]))
                .duration(200).EUt(VA[UHV]).buildAndRegister();
    }

    private static void Coils() {
        //超导线圈
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

        //聚变线圈
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

        //聚变线圈
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.FUSION_COIL))
                .inputs(MetaItems.FIELD_GENERATOR_UHV.getStackForm(2))
                .inputs(MetaItems.ELECTRIC_PUMP_UHV.getStackForm())
                .inputs(MetaItems.NEUTRON_REFLECTOR.getStackForm(4))
                .input(OrePrefix.circuit, MarkerMaterials.Tier.UV, 8)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.ZPM, 16)
                .input(OrePrefix.pipeSmallFluid, Neutronium, 4)
                .input(OrePrefix.plate, Tritanium, 4)
                .fluidInputs(KaptonE.getFluid(L * 32))
                .fluidInputs(Cinobite.getFluid(L * 16))
                .fluidInputs(Octahedrite.getFluid(L * 16))
                .fluidInputs(AstralTitanium.getFluid(L * 16))
                .outputs(GTQTMetaBlocks.blockCompressedFusionReactor.getItemVariant(BlockCompressedFusionReactor.CasingType.FUSION_COIL_MKII))
                .stationResearch(b -> b
                        .researchStack(MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.FUSION_COIL))
                        .EUt(VA[UHV])
                        .CWUt(CWT[UV]))
                .duration(800).EUt(VA[UHV]).buildAndRegister();
    }

    private static void Shell() {
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[UHV])
                .inputs(blockCompressedFusionReactor.getItemVariant(FUSION_COIL_MKII))
                .input(VOLTAGE_COIL_UHV,2)
                .input(FIELD_GENERATOR_UHV,2)
                .input(ELECTRIC_PISTON_UHV)
                .input(circuit, MarkerMaterials.Tier.UHV,8)
                .input(circuit, MarkerMaterials.Tier.UV,16)
                .input(plate, Neutronium, 8)
                .input(screw,EnrichedNaqAlloy,4)
                .input(rotor,Americium,2)
                .fluidInputs(KaptonE.getFluid(L * 32))
                .fluidInputs(Cinobite.getFluid(L * 4))
                .fluidInputs(Octahedrite.getFluid(L * 4))
                .fluidInputs(AstralTitanium.getFluid(L * 4))
                .outputs(blockCompressedFusionReactor.getItemVariant(BlockCompressedFusionReactor.CasingType.CASING_FUSION_MKIV))
                .stationResearch(b -> b
                        .researchStack(MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.FUSION_CASING_MK3))
                        .EUt(VA[UHV])
                        .CWUt(CWT[UV]))
                .duration(300)
                .EUt(VA[UHV])
                .buildAndRegister();
    }

    public static void Control()
    {
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 8)
                .inputs(FUSION_CASING.getItemVariant(SUPERCONDUCTOR_COIL))
                .input(circuit, MarkerMaterials.Tier.ZPM, 64)
                .input(plateDouble, Plutonium241, 6)
                .input(plateDouble, NaquadahAlloy, 6)
                .input(FIELD_GENERATOR_IV, 32)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, IVSuperconductor, 64)
                .input(wireGtSingle, IVSuperconductor, 64)
                .fluidInputs(PbB.getFluid(L * 16))
                .fluidInputs(NiobiumTitanium.getFluid(L * 16))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .outputs(FUSION_REACTOR[0].getStackForm())
                .scannerResearch(b -> b
                        .researchStack(DISK_6.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .duration(2000).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II, 8)
                .inputs(FUSION_CASING.getItemVariant(FUSION_COIL))
                .input(circuit, MarkerMaterials.Tier.UV, 64)
                .input(plateDouble, Naquadria, 6)
                .input(plateDouble, Duranium, 6)
                .input(FIELD_GENERATOR_LuV, 32)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .fluidInputs(PbB.getFluid(L * 16))
                .fluidInputs(VanadiumGallium.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .fluidInputs(Kevlar.getFluid(L * 16))
                .outputs(FUSION_REACTOR[1].getStackForm())
                .stationResearch(b -> b
                        .researchStack(FUSION_REACTOR[0].getStackForm())
                        .CWUt(CWT[LuV])
                        .EUt(VA[ZPM]))
                .duration(2000).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III, 8)
                .inputs(FUSION_CASING.getItemVariant(FUSION_COIL))
                .input(circuit, MarkerMaterials.Tier.UHV, 64)
                .input(plateDouble, Darmstadtium, 6)
                .input(plateDouble, Americium, 6)
                .input(FIELD_GENERATOR_ZPM, 32)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, ZPMSuperconductor, 64)
                .input(wireGtSingle, ZPMSuperconductor, 64)
                .fluidInputs(PbB.getFluid(L * 16))
                .fluidInputs(YttriumBariumCuprate.getFluid(L * 16))
                .fluidInputs(Kevlar.getFluid(L * 16))
                .fluidInputs(KaptonK.getFluid(L * 16))
                .outputs(FUSION_REACTOR[2].getStackForm())
                .stationResearch(b -> b
                        .researchStack(FUSION_REACTOR[1].getStackForm())
                        .CWUt(CWT[ZPM])
                        .EUt(VA[UV]))
                .duration(2000).EUt(VA[ZPM]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_IV, 8)
                .inputs(blockCompressedFusionReactor.getItemVariant(FUSION_COIL_MKII))
                .input(circuit, MarkerMaterials.Tier.UEV, 64)
                .input(plateDouble, Neutronium, 6)
                .input(plateDouble, Tritanium, 6)
                .input(FIELD_GENERATOR_UV, 32)
                .input(NANO_POWER_IC, 64)
                .input(NANO_POWER_IC, 64)
                .input(wireGtSingle, UVSuperconductor, 64)
                .input(wireGtSingle, UVSuperconductor, 64)
                .fluidInputs(PbB.getFluid(L * 16))
                .fluidInputs(Cinobite.getFluid(L * 16))
                .fluidInputs(KaptonK.getFluid(L * 16))
                .fluidInputs(KaptonE.getFluid(L * 16))
                .outputs(ADVANCED_FUSION_REACTOR[0].getStackForm())
                .stationResearch(b -> b
                        .researchStack(FUSION_REACTOR[2].getStackForm())
                        .CWUt(CWT[UV])
                        .EUt(VA[UHV]))
                .duration(2000).EUt(VA[UHV]).buildAndRegister();

        //压缩聚变
        //  Compressed Fusion Reactor Mk I
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I, 16)
                .input(FUSION_REACTOR[0], 48)
                .input(swarm, Osmium, 1)
                .input(circuit, MarkerMaterials.Tier.ZPM, 64)
                .input(ELECTRIC_PUMP_LuV, 32)
                .input(FIELD_GENERATOR_LuV, 32)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .fluidInputs(PbB.getFluid(L * 48))
                .fluidInputs(NiobiumTitanium.getFluid(L * 48))
                .fluidInputs(Kevlar.getFluid(L * 40))
                .fluidInputs(MARM200Steel.getFluid(L * 16))
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
                .input(FUSION_REACTOR[1], 48)
                .input(swarm, Americium, 1)
                .input(circuit, MarkerMaterials.Tier.UV, 64)
                .input(ELECTRIC_PUMP_ZPM, 32)
                .input(FIELD_GENERATOR_ZPM, 32)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, ZPMSuperconductor, 64)
                .input(wireGtSingle, ZPMSuperconductor, 64)
                .fluidInputs(PbB.getFluid(L * 48))
                .fluidInputs(VanadiumGallium.getFluid(L * 48))
                .fluidInputs(KaptonK.getFluid(L * 40))
                .fluidInputs(Hdcs.getFluid(L * 16))
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
                .input(FUSION_REACTOR[2], 48)
                .input(swarm, Darmstadtium, 1)
                .input(circuit, MarkerMaterials.Tier.UHV, 64)
                .input(ELECTRIC_PUMP_UV, 32)
                .input(FIELD_GENERATOR_UV, 32)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(wireGtSingle, UVSuperconductor, 64)
                .input(wireGtSingle, UVSuperconductor, 64)
                .fluidInputs(PbB.getFluid(L * 48))
                .fluidInputs(YttriumBariumCuprate.getFluid(L * 48))
                .fluidInputs(KaptonK.getFluid(L * 40))
                .fluidInputs(TanmolyiumBetaC.getFluid(L*16))
                .output(COMPRESSED_FUSION_REACTOR[2])
                .stationResearch(b -> b
                        .researchStack(FUSION_REACTOR[2].getStackForm())
                        .EUt(VA[UV])
                        .CWUt(CWT[UV]))
                .EUt(VA[UV])
                .duration(1200)
                .buildAndRegister();

        //  Compressed Fusion Reactor Mk IV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_IV, 16)
                .input(ADVANCED_FUSION_REACTOR[0], 48)
                .input(swarm, Neutronium, 1)
                .input(circuit, MarkerMaterials.Tier.UEV, 64)
                .input(ELECTRIC_PUMP_UHV, 32)
                .input(FIELD_GENERATOR_UHV, 32)
                .input(NANO_POWER_IC, 64)
                .input(NANO_POWER_IC, 64)
                .input(wireGtSingle, UHVSuperconductor, 64)
                .input(wireGtSingle, UHVSuperconductor, 64)
                .fluidInputs(PbB.getFluid(L * 48))
                .fluidInputs(Cinobite.getFluid(L * 48))
                .fluidInputs(KaptonE.getFluid(L * 40))
                .fluidInputs(Teflon.getFluid(L*16))
                .output(COMPRESSED_FUSION_REACTOR[3])
                .stationResearch(b -> b
                        .researchStack(ADVANCED_FUSION_REACTOR[0].getStackForm())
                        .EUt(VA[UHV])
                        .CWUt(CWT[UHV]))
                .EUt(VA[UHV])
                .duration(1200)
                .buildAndRegister();
    }
}
