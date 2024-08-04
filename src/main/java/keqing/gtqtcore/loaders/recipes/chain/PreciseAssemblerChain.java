package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.unification.material.MarkerMaterials;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTPowerSupply;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing1;

import static gregicality.multiblocks.api.unification.GCYMMaterials.Zeron100;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.PRECISE_ASSEMBLER_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.*;

public class PreciseAssemblerChain {
    public static void init() {
        MachineRecipes();//
        WorkStation();//工作站
        Common();
    }

    private static void Common() {
        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I,2)
                .input(circuit,MarkerMaterials.Tier.LuV,8)
                .input(CHEMICAL_PLANT,2)
                .input(PARTICLE_ACCELERATOR_IO[0],2)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Zylon.getFluid(L * 4))
                .output(NEUTRON_ACTIVATOR)
                .EUt(VA[IV])
                .duration(600)
                .Tier(1)
                .CWUt(120)
                .buildAndRegister();

    }


    private static void WorkStation() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(ELITE_CIRCUIT_BOARD)
                .input(circuit,MarkerMaterials.Tier.IV,8)
                .input(ADVANCED_SYSTEM_ON_CHIP,4)
                .input(POWER_INTEGRATED_CIRCUIT,8)
                .input(screw,NanometerBariumTitanate,32)
                .input(cableGtSingle,Platinum,8)
                .input(rotor,TungstenSteel,8)
                .fluidInputs(Lead.getFluid(L * 8))
                .output(CIRCUIT_GOOD_I)
                .EUt(VA[IV])
                .duration(600)
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I,2)
                .input(circuit,MarkerMaterials.Tier.LuV,8)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT,32)
                .input(rotor,RhodiumPlatedPalladium,2)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 8))
                .fluidInputs(Zylon.getFluid(L * 4))
                .output(CIRCUIT_GOOD_II)
                .EUt(VA[LuV])
                .duration(600)
                .Tier(2)
                .CWUt(120)
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II,2)
                .input(circuit,MarkerMaterials.Tier.ZPM,8)
                .input(NANO_POWER_IC,32)
                .input(rotor,NaquadahAlloy,2)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .fluidInputs(Zylon.getFluid(L * 8))
                .fluidInputs(KaptonK.getFluid(L * 4))
                .output(CIRCUIT_GOOD_III)
                .EUt(VA[ZPM])
                .duration(600)
                .Tier(3)
                .CWUt(480)
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III,2)
                .input(circuit,MarkerMaterials.Tier.UV,8)
                .input(PICO_POWER_IC,32)
                .input(rotor,Adamantium,2)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .fluidInputs(KaptonK.getFluid(L * 8))
                .fluidInputs(KaptonE.getFluid(L * 4))
                .output(CIRCUIT_GOOD_IV)
                .EUt(VA[UV])
                .duration(600)
                .Tier(4)
                .CWUt(1920)
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_IV,2)
                .input(circuit,MarkerMaterials.Tier.UHV,8)
                .input(FEMTO_POWER_IC,32)
                .input(rotor,Neutronium,2)
                .fluidInputs(SolderingAlloy.getFluid(L * 8))
                .fluidInputs(Tin.getFluid(L * 8))
                .fluidInputs(KaptonE.getFluid(L * 8))
                .fluidInputs(Kevlar.getFluid(L * 4))
                .output(CIRCUIT_GOOD_V)
                .EUt(VA[UHV])
                .duration(600)
                .Tier(5)
                .CWUt(7680)
                .buildAndRegister();
    }

    private static void MachineRecipes() {
        //  Precise Assembler Casing Mk I
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LuV))
                .inputs(GTQTMetaBlocks.POWER.getItemVariant(GTQTPowerSupply.SupplyType.POWER_SUPPLY_BASIC))
                .input(ROBOT_ARM_EV, 2)
                .input(plateDouble, MARM200CeSteel, 2)
                .input(circuit, MarkerMaterials.Tier.LuV)
                .input(gearSmall, Stellite, 4)
                .input(cableGtQuadruple, Naquadah, 2)
                .input(screw, HSSG, 32)
                .fluidInputs(BlackSteel.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK1,4))
                .EUt(VA[IV])
                .duration(400)
                .buildAndRegister();

        //  Precise Assembler Casing Mk II
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ZPM))
                .inputs(GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK1))
                .input(ROBOT_ARM_IV, 2)
                .input(plateDouble, HastelloyC59, 2)
                .input(CIRCUIT_GOOD_I)
                .input(gearSmall, TanmolyiumBetaC, 8)
                .input(cableGtQuadruple, Tritanium, 2)
                .input(screw, HSSE, 32)
                .fluidInputs(Zeron100.getFluid(576))
                .outputs(GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK2,4))
                .EUt(VA[LuV])
                .duration(400)
                .buildAndRegister();

        //  Precise Assembler Casing Mk III
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.UV))
                .inputs(GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK2))
                .input(ROBOT_ARM_LuV, 2)
                .input(plateDouble, HMS1J79Alloy, 2)
                .input(CIRCUIT_GOOD_II)
                .input(gearSmall, HY1301, 8)
                .input(cableGtQuadruple, SiliconCarbide, 2)
                .input(screw, HSSS, 32)
                .fluidInputs(IncoloyMA813.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK3,4))
                .EUt(VA[ZPM])
                .duration(400)
                .buildAndRegister();

        //  Precise Assembler Casing Mk IV
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.UHV))
                .inputs(GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK3))
                .input(ROBOT_ARM_ZPM, 2)
                .input(plateDouble, PlatinumGroupAlloy, 2)
                .input(CIRCUIT_GOOD_III)
                .input(gearSmall, Cinobite, 8)
                .input(screw, Orichalcum, 32)
                .fluidInputs(Neutronium.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK4,4))
                .EUt(VA[UV])
                .duration(400)
                .buildAndRegister();

        //  Precise Assembler Casing Mk V
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.UEV))
                .inputs(GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK4))
                .input(ROBOT_ARM_UV, 2)
                .input(plateDouble, BlackTitanium, 2)
                .input(CIRCUIT_GOOD_IV)
                .input(gearSmall, TitanSteel, 8)
                .input(screw, SuperheavyLAlloy, 32)
                .fluidInputs(Adamantium.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK5,4))
                .EUt(VA[UHV])
                .duration(400)
                .buildAndRegister();

        //  Precise Assembler Casing Mk VI
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.UIV))
                .inputs(GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK5))
                .input(ROBOT_ARM_UHV, 2)
                .input(plateDouble, BlackPlutonium, 2)
                .input(CIRCUIT_GOOD_V)
                .input(gearSmall, Infinity, 8)
                .input(screw, LanthanumFullereneNanotube, 32)
                .fluidInputs(RutheniumTriniumAmericiumNeutronate.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(GTQTTurbineCasing1.TurbineCasingType.PRECISE_ASSEMBLER_CASING_MK6,4))
                .EUt(VA[UEV])
                .duration(400)
                .buildAndRegister();

    }

}
