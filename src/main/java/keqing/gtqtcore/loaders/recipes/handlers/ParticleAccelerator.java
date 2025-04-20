package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.material.MarkerMaterials;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;

import static gregicality.multiblocks.api.unification.GCYMMaterials.TitaniumTungstenCarbide;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.PRECISE_ASSEMBLER_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

import static keqing.gtqtcore.common.block.blocks.BlockParticleAcceleratorCasing.MachineType.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.ANTIPROTON;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.PARTICLE_ACCELERATOR;
import static supercritical.api.unification.material.SCMaterials.*;


public class ParticleAccelerator {

    public static void init() {
        common();
        TARGET_CHAMBER();
        PARTICLE_ACCELERATOR_RECIPES();
        NUCLEOSYNTHESIS();

    }
    public static void common()
    {
        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I,1)
                .input(pipeHugeFluid,TungstenSteel,1)
                .input(wireGtSingle,IVSuperconductor,4)
                .input(rotor,RhodiumPlatedPalladium,2)
                .fluidInputs(Helium.getFluid(FluidStorageKeys.LIQUID, 8*L))
                .fluidInputs(UltraGlue.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 4))
                .fluidInputs(Zylon.getFluid(L * 2))
                .output(ADV_HEAT_EXCHANGE,4)
                .EUt(VA[IV])
                .duration(800)
                .Tier(1)
                .CWUt(120)
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I,1)
                .input(ENERGY_LAPOTRONIC_ORB,1)
                .input(wireGtSingle,IVSuperconductor,4)
                .input(rotor,RhodiumPlatedPalladium,2)
                .fluidInputs(Helium.getFluid(FluidStorageKeys.LIQUID, 8*L))
                .fluidInputs(UltraGlue.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 4))
                .fluidInputs(Zylon.getFluid(L * 2))
                .output(ADV_ENERGY_STORAGE,4)
                .EUt(VA[IV])
                .duration(800)
                .Tier(1)
                .CWUt(120)
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I,1)
                .input(ELECTRIC_PUMP_IV,1)
                .input(wireGtSingle,IVSuperconductor,4)
                .input(VOLTAGE_COIL_IV,4)
                .fluidInputs(Helium.getFluid(FluidStorageKeys.LIQUID, 8*L))
                .fluidInputs(UltraGlue.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 4))
                .fluidInputs(Zylon.getFluid(L * 2))
                .output(ADV_COMPONENT_VENT,4)
                .EUt(VA[IV])
                .duration(800)
                .Tier(1)
                .CWUt(120)
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I,1)
                .input(circuit, MarkerMaterials.Tier.LuV,1)
                .input(wireGtSingle,IVSuperconductor,4)
                .input(rotor,RhodiumPlatedPalladium,2)
                .fluidInputs(Helium.getFluid(FluidStorageKeys.LIQUID, 8*L))
                .fluidInputs(UltraGlue.getFluid(L * 8))
                .fluidInputs(Polybenzimidazole.getFluid(L * 4))
                .fluidInputs(Zylon.getFluid(L * 2))
                .output(ADV_CAPACITOR,4)
                .EUt(VA[IV])
                .duration(800)
                .Tier(1)
                .CWUt(120)
                .buildAndRegister();

        //控制器
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I,64)
                .inputs(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(ACCELERATOR_CASING,32))
                .input(EMITTER_IV, 64)
                .input(SENSOR_IV, 64)
                .input(frameGt, Naquadah, 64)
                .input(frameGt, Naquadah, 64)
                .input(plate, HSSS, 64)
                .input(ring, HSSG, 64)
                .input(gearSmall,PPB,64)
                .input(stickLong,Samarium,64)
                .input(screw,RhodiumPlatedPalladium,64)
                .input(foil,NiobiumTitanium,64)
                .input(wireFine,Ruridit,64)
                .input(wireFine,Ruridit,64)
                .input(wireGtSingle, IVSuperconductor, 64)
                .input(wireGtSingle, IVSuperconductor, 64)
                .output(PARTICLE_ACCELERATOR)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .scannerResearch(b -> b
                        .researchStack(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(ACCELERATOR_CASING))
                        .EUt(VA[IV]))
                .duration(2000).EUt(VA[IV]).buildAndRegister();

        //
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I)
                .input(ADV_HEAT_EXCHANGE)
                .input(frameGt, HSSS, 2)
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(gearSmall,HSSG,4)
                .input(screw,NanometerBariumTitanate,4)
                .input(foil,Ruridit,8)
                .input(wireFine, Platinum, 16)
                .outputs(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(ACCELERATOR_CASING,8))
                .fluidInputs(Polybenzimidazole.getFluid(L*2))
                .fluidInputs(Zylon.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .duration(800).EUt(VA[IV]).buildAndRegister();

        //部件I
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[5])
                .input(ADV_ENERGY_STORAGE)
                .input(CIRCUIT_GOOD_I)
                .input(VOLTAGE_COIL_IV,2)
                .input(FIELD_GENERATOR_IV, 2)
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(gearSmall,NanometerBariumTitanate,2)
                .input(wireFine,Platinum,4)
                .input(wireGtSingle, IVSuperconductor, 2)
                .outputs(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(ACCELERATOR_FIRM_MKI,4))
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .fluidInputs(Zylon.getFluid(L * 2))
                .fluidInputs(NiobiumTitanium.getFluid(L * 2))
                .duration(1000).EUt(VA[EV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[5])
                .input(ADV_CAPACITOR)
                .input(CIRCUIT_GOOD_I)
                .input(VOLTAGE_COIL_IV,4)
                .input(EMITTER_IV, 2)
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(gearSmall,NanometerBariumTitanate,2)
                .input(wireFine,Platinum,16)
                .input(wireGtSingle, IVSuperconductor, 2)
                .outputs(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(ACCELERATOR_ELECTROMAGNET_MKI,4))
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .fluidInputs(Zylon.getFluid(L * 2))
                .fluidInputs(NiobiumTitanium.getFluid(L * 2))
                .duration(1000).EUt(VA[EV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[5])
                .input(ADV_COMPONENT_VENT)
                .input(CIRCUIT_GOOD_I)
                .input(VOLTAGE_COIL_IV,4)
                .input(SENSOR_IV, 2)
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(gearSmall,NanometerBariumTitanate,2)
                .input(wireFine,Platinum,16)
                .input(wireGtSingle, IVSuperconductor, 2)
                .outputs(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(ACCELERATOR_ELECTROMAGNETV_MKI,4))
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .fluidInputs(Zylon.getFluid(L * 2))
                .fluidInputs(NiobiumTitanium.getFluid(L * 2))
                .duration(1000).EUt(VA[EV]).buildAndRegister();

        //部件II
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[6])
                .input(ADV_ENERGY_STORAGE,2)
                .input(CIRCUIT_GOOD_II)
                .input(VOLTAGE_COIL_LuV,4)
                .input(SENSOR_LuV, 2)
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(gearSmall,PPB,2)
                .input(wireFine,Platinum,16)
                .input(wireGtSingle, LuVSuperconductor, 2)
                .outputs(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(ACCELERATOR_FIRM_MKII,4))
                .stationResearch(b -> b
                        .researchStack(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(ACCELERATOR_FIRM_MKI))
                        .CWUt(CWT[LuV])
                        .EUt(VA[LuV]))
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .fluidInputs(Zylon.getFluid(L * 2))
                .fluidInputs(NiobiumTitanium.getFluid(L * 2))
                .duration(1000).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[6])
                .input(ADV_CAPACITOR,2)
                .input(CIRCUIT_GOOD_II)
                .input(VOLTAGE_COIL_LuV,4)
                .input(EMITTER_LuV, 2)
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(gearSmall,PPB,2)
                .input(wireFine,Platinum,16)
                .input(wireGtSingle, LuVSuperconductor, 2)
                .outputs(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(ACCELERATOR_ELECTROMAGNET_MKII,4))
                .stationResearch(b -> b
                        .researchStack(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(ACCELERATOR_ELECTROMAGNET_MKI))
                        .CWUt(CWT[LuV])
                        .EUt(VA[LuV]))
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .fluidInputs(Zylon.getFluid(L * 2))
                .fluidInputs(NiobiumTitanium.getFluid(L * 2))
                .duration(1000).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[6])
                .input(ADV_COMPONENT_VENT,2)
                .input(CIRCUIT_GOOD_II)
                .input(VOLTAGE_COIL_LuV,4)
                .input(SENSOR_LuV, 2)
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(gearSmall,PPB,2)
                .input(wireFine,Platinum,16)
                .input(wireGtSingle, LuVSuperconductor, 2)
                .outputs(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(ACCELERATOR_ELECTROMAGNETV_MKII,4))
                .stationResearch(b -> b
                        .researchStack(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(ACCELERATOR_ELECTROMAGNETV_MKI))
                        .CWUt(CWT[LuV])
                        .EUt(VA[LuV]))
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .fluidInputs(Zylon.getFluid(L * 2))
                .fluidInputs(NiobiumTitanium.getFluid(L * 2))
                .duration(1000).EUt(VA[LuV]).buildAndRegister();

        //部件III
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[7])
                .input(ADV_ENERGY_STORAGE,2)
                .input(CIRCUIT_GOOD_III)
                .input(VOLTAGE_COIL_ZPM,4)
                .input(SENSOR_ZPM, 2)
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(gearSmall,NiobiumTitanium,2)
                .input(wireFine,Platinum,16)
                .input(wireGtSingle, ZPMSuperconductor, 2)
                .outputs(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(ACCELERATOR_FIRM_MKIII,4))
                .stationResearch(b -> b
                        .researchStack(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(ACCELERATOR_FIRM_MKII))
                        .CWUt(CWT[ZPM])
                        .EUt(VA[ZPM]))
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .fluidInputs(Zylon.getFluid(L * 2))
                .duration(1000).EUt(VA[ZPM]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[7])
                .input(ADV_CAPACITOR,2)
                .input(CIRCUIT_GOOD_III)
                .input(VOLTAGE_COIL_ZPM,4)
                .input(EMITTER_ZPM, 2)
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(gearSmall,NiobiumTitanium,2)
                .input(wireFine,Platinum,16)
                .input(wireGtSingle, ZPMSuperconductor, 2)
                .outputs(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(ACCELERATOR_ELECTROMAGNET_MKIII,4))
                .stationResearch(b -> b
                        .researchStack(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(ACCELERATOR_ELECTROMAGNET_MKII))
                        .CWUt(CWT[ZPM])
                        .EUt(VA[ZPM]))
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .fluidInputs(Zylon.getFluid(L * 2))
                .duration(1000).EUt(VA[ZPM]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[7])
                .input(ADV_COMPONENT_VENT,2)
                .input(CIRCUIT_GOOD_III)
                .input(VOLTAGE_COIL_ZPM,4)
                .input(SENSOR_ZPM, 2)
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(gearSmall,NiobiumTitanium,2)
                .input(wireFine,Platinum,16)
                .input(wireGtSingle, ZPMSuperconductor, 2)
                .outputs(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(ACCELERATOR_ELECTROMAGNETV_MKIII,4))
                .stationResearch(b -> b
                        .researchStack(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(ACCELERATOR_ELECTROMAGNETV_MKII))
                        .CWUt(CWT[ZPM])
                        .EUt(VA[ZPM]))
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .fluidInputs(Zylon.getFluid(L * 2))
                .duration(1000).EUt(VA[ZPM]).buildAndRegister();

    }
    public static void NUCLEOSYNTHESIS()
    {
        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidOutputs(Deuterium.getFluid(1000))
                .fluidOutputs()
                .EUToStart(1000)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidInputs(Deuterium.getFluid(1000))
                .fluidOutputs(Helium3.getFluid(1000))
                .fluidOutputs()
                .EUToStart(1000)
                .Scattering(8)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Helium3.getFluid(1000))
                .fluidInputs(Helium3.getFluid(1000))
                .fluidOutputs(Helium.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .fluidOutputs()
                .EUToStart(1000)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Deuterium.getFluid(1000))
                .fluidInputs(Tritium.getFluid(1000))
                .fluidOutputs(Helium.getFluid(1000))
                .fluidOutputs()
                .EUToStart(2000)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Tritium.getFluid(1000))
                .fluidInputs(Tritium.getFluid(1000))
                .fluidOutputs(Helium.getFluid(1000))
                .fluidOutputs()
                .EUToStart(3000)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Deuterium.getFluid(1000))
                .fluidInputs(Helium3.getFluid(1000))
                .fluidOutputs(Helium.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .EUToStart(4000)
                .Scattering(7)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidInputs(Lithium7.getFluid(1000))
                .fluidOutputs(Helium.getFluid(1000))
                .EUToStart(5000)
                .Scattering(5)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Deuterium.getFluid(1000))
                .fluidInputs(Lithium7.getFluid(1000))
                .fluidOutputs(Helium.getFluid(1000))
                .EUToStart(6000)
                .Scattering(3)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Deuterium.getFluid(1000))
                .fluidInputs(Deuterium.getFluid(1000))
                .fluidOutputs(Tritium.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .EUToStart(7000)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Helium3.getFluid(1000))
                .fluidInputs(Lithium6.getFluid(1000))
                .fluidOutputs(Helium.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .EUToStart(8000)
                .Scattering(8)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidInputs(Lithium6.getFluid(1000))
                .fluidOutputs(Helium.getFluid(1000))
                .fluidOutputs(Helium3.getFluid(1000))
                .EUToStart(9000)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidInputs(Boron.getFluid(1000))
                .fluidOutputs(Helium.getFluid(1000))
                .EUToStart(10000)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidInputs(Carbon.getFluid(1000))
                .fluidOutputs(Nitrogen.getFluid(1000))
                .EUToStart(11000)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidInputs(Nitrogen.getFluid(1000))
                .fluidOutputs(Carbon.getFluid(1000))
                .fluidOutputs(Helium.getFluid(1000))
                .EUToStart(12000)
                .Scattering(7)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Helium.getFluid(1000))
                .fluidInputs(Helium.getFluid(1000))
                .fluidOutputs(Carbon.getFluid(1000))
                .EUToStart(13000)
                .Scattering(5)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Helium.getFluid(1000))
                .fluidInputs(Carbon.getFluid(1000))
                .fluidOutputs(Oxygen.getFluid(1000))
                .EUToStart(14000)
                .Scattering(3)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Helium.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(Neon.getFluid(1000))
                .EUToStart(15000)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Helium.getFluid(1000))
                .fluidInputs(Neon.getFluid(1000))
                .fluidOutputs(Magnesium.getFluid(1000))
                .EUToStart(16000)
                .Scattering(8)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Helium.getFluid(1000))
                .fluidInputs(Magnesium.getFluid(1000))
                .fluidOutputs(Silicon.getFluid(1000))
                .EUToStart(17000)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Helium.getFluid(1000))
                .fluidInputs(Silicon.getFluid(1000))
                .fluidOutputs(Sulfur.getFluid(1000))
                .EUToStart(18000)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Helium.getFluid(1000))
                .fluidInputs(Sulfur.getFluid(1000))
                .fluidOutputs(Argon.getFluid(1000))
                .EUToStart(19000)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Helium.getFluid(1000))
                .fluidInputs(Argon.getFluid(1000))
                .fluidOutputs(Calcium.getFluid(1000))
                .EUToStart(20000)
                .Scattering(7)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Helium.getFluid(1000))
                .fluidInputs(Calcium.getFluid(1000))
                .fluidOutputs(Titanium.getFluid(1000))
                .EUToStart(21000)
                .Scattering(5)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Helium.getFluid(1000))
                .fluidInputs(Titanium.getFluid(1000))
                .fluidOutputs(Chrome.getFluid(1000))
                .EUToStart(22000)
                .Scattering(3)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Helium.getFluid(1000))
                .fluidInputs(Chrome.getFluid(1000))
                .fluidOutputs(Iron.getFluid(1000))
                .EUToStart(23000)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
            .input(MUON)
            .fluidInputs(Helium.getFluid(1000))
            .fluidInputs(Iron.getFluid(1000))
            .fluidOutputs(Nickel.getFluid(1000))
            .EUToStart(24000)
            .Scattering(8)
            .duration(100)
            .EUt(7680)
            .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(Silicon.getFluid(1000))
                .fluidOutputs(Helium.getFluid(1000))
                .EUToStart(25000)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(MUON)
                .fluidInputs(Carbon.getFluid(1000))
                .fluidInputs(Carbon.getFluid(1000))
                .fluidOutputs(Sodium.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .EUToStart(26000)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        //////////////////////////
        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Iron.getFluid(1000))
                .fluidOutputs(Cobalt.getFluid(1000))
                .EUToStart(30000)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Cobalt.getFluid(1000))
                .fluidOutputs(Copper.getFluid(1000))
                .EUToStart(31000)
                .Scattering(7)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Nickel.getFluid(1000))
                .fluidOutputs(Copper.getFluid(1000))
                .EUToStart(32000)
                .Scattering(5)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Copper.getFluid(1000))
                .fluidOutputs(Zinc.getFluid(1000))
                .EUToStart(33000)
                .Scattering(3)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Zinc.getFluid(1000))
                .fluidOutputs(Arsenic.getFluid(1000))
                .EUToStart(34000)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Arsenic.getFluid(1000))
                .fluidOutputs(Strontium.getFluid(1000))
                .EUToStart(35000)
                .Scattering(8)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Zirconium.getFluid(1000))
                .fluidOutputs(Molybdenum.getFluid(1000))
                .EUToStart(36000)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Molybdenum.getFluid(1000))
                .fluidOutputs(Ruthenium.getFluid(1000))
                .EUToStart(37000)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Ruthenium.getFluid(1000))
                .fluidOutputs(Silver.getFluid(1000))
                .EUToStart(38000)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Silver.getFluid(1000))
                .fluidOutputs(Tin.getFluid(1000))
                .EUToStart(39000)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Tin.getFluid(1000))
                .fluidOutputs(Iodine.getFluid(1000))
                .EUToStart(40000)
                .Scattering(8)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Iodine.getFluid(1000))
                .fluidOutputs(Caesium.getFluid(1000))
                .EUToStart(41000)
                .Scattering(7)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Caesium.getFluid(1000))
                .fluidOutputs(Neodymium.getFluid(1000))
                .EUToStart(42000)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        // 镧系前半部分元素合成
        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Lanthanum.getFluid(1000))  // 镧（La,57）
                .fluidOutputs(Cerium.getFluid(1000))    // 铈（Ce,58）
                .EUToStart(40000)
                .Scattering(3)  // 更高的散射强度
                .duration(100)
                .EUt(6144)      // 电压等级逐步提升
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Cerium.getFluid(1000))      // 铈（Ce,58）
                .fluidOutputs(Praseodymium.getFluid(1000)) // 镨（Pr,59）
                .EUToStart(41000)
                .Scattering(2)
                .duration(100)
                .EUt(6144)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Praseodymium.getFluid(1000)) // 镨（Pr,59）
                .fluidOutputs(Neodymium.getFluid(1000))  // 钕（Nd,60）（已存在下游配方）
                .EUToStart(42000)
                .Scattering(5)
                .duration(100)
                .EUt(6144)
                .buildAndRegister();

        // 钕（Nd,60）→ 钷（Pm,61）→ 钐（Sm,62）独立路径
        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Neodymium.getFluid(1000))
                .fluidOutputs(Promethium.getFluid(1000)) // 钷（Pm,61）
                .EUToStart(42500)
                .Scattering(3)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Promethium.getFluid(1000))
                .fluidOutputs(Samarium.getFluid(1000))   // 钐（Sm,62）（已存在下游）
                .EUToStart(43000)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        // 钆（Gd,64）相关合成路径
        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Europium.getFluid(1000))    // 铕（Eu,63）
                .fluidOutputs(Gadolinium.getFluid(1000)) // 钆（Gd,64）
                .EUToStart(44500)
                .Scattering(5)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Gadolinium.getFluid(1000))
                .fluidOutputs(Terbium.getFluid(1000))    // 铽（Tb,65）（已有其他输入路径）
                .EUToStart(45000)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        // 镝（Dy,66）→ 钬（Ho,67）→ 铒（Er,68）完整链
        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Terbium.getFluid(1000))     // 铽（Tb,65）
                .fluidOutputs(Dysprosium.getFluid(1000))// 镝（Dy,66）
                .EUToStart(45500)
                .Scattering(7)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Dysprosium.getFluid(1000))
                .fluidOutputs(Holmium.getFluid(1000))   // 钬（Ho,67）
                .EUToStart(46000)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Holmium.getFluid(1000))
                .fluidOutputs(Erbium.getFluid(1000))    // 铒（Er,68）（已有下游）
                .EUToStart(46500)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        // 铒（Er,68）→ 铥（Tm,69）→ 镱（Yb,70）补充路径
        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Erbium.getFluid(1000))
                .fluidOutputs(Thulium.getFluid(1000))   // 铥（Tm,69）
                .EUToStart(47000)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Thulium.getFluid(1000))
                .fluidOutputs(Ytterbium.getFluid(1000)) // 镱（Yb,70）（已有下游）
                .EUToStart(47500)
                .Scattering(3)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        // 镱（Yb,70）→ 镥（Lu,71）最终合成
        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Ytterbium.getFluid(1000))
                .fluidOutputs(Lutetium.getFluid(1000))  // 镥（Lu,71）
                .EUToStart(48000)
                .Scattering(8)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Ytterbium.getFluid(1000))
                .fluidOutputs(Hafnium.getFluid(1000))
                .EUToStart(48000)
                .Scattering(7)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Hafnium.getFluid(1000))
                .fluidOutputs(Tungsten.getFluid(1000))
                .EUToStart(49000)
                .Scattering(5)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Tungsten.getFluid(1000))
                .fluidOutputs(Osmium.getFluid(1000))
                .EUToStart(50000)
                .Scattering(3)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Osmium.getFluid(1000))
                .fluidOutputs(Iridium.getFluid(1000))
                .EUToStart(51000)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Iridium.getFluid(1000))
                .fluidOutputs(Platinum.getFluid(1000))
                .EUToStart(52000)
                .Scattering(8)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Platinum.getFluid(1000))
                .fluidOutputs(Gold.getFluid(1000))
                .EUToStart(53000)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Gold.getFluid(1000))
                .fluidOutputs(Mercury.getFluid(1000))
                .EUToStart(54000)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Mercury.getFluid(1000))
                .fluidOutputs(Lead.getFluid(1000))
                .EUToStart(55000)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Lead.getFluid(1000))
                .fluidOutputs(Bismuth.getFluid(1000))
                .EUToStart(56000)
                .Scattering(8)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Bismuth.getFluid(1000))
                .fluidOutputs(Polonium.getFluid(1000))
                .EUToStart(57000)
                .Scattering(7)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Polonium.getFluid(1000))
                .fluidOutputs(Astatine.getFluid(1000))
                .EUToStart(57000)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Astatine.getFluid(1000))
                .fluidOutputs(Radium.getFluid(1000))
                .EUToStart(58000)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Radium.getFluid(1000))
                .fluidOutputs(Thorium.getFluid(1000))
                .EUToStart(59000)
                .Scattering(5)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Thorium.getFluid(1000))
                .fluidOutputs(Uranium238.getFluid(1000))
                .EUToStart(60000)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Uranium238.getFluid(1000))
                .fluidOutputs(Plutonium242.getFluid(1000))
                .EUToStart(61000)
                .Scattering(3)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Plutonium242.getFluid(1000))
                .fluidOutputs(Curium.getFluid(1000))
                .EUToStart(62000)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.NUCLEOSYNTHESIS.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Curium.getFluid(1000))
                .fluidOutputs(Californium.getFluid(1000))
                .EUToStart(63000)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();


    }
    public static void PARTICLE_ACCELERATOR_RECIPES()
    {
        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(NEUTRON)
                .output(PROTON)
                .output(ELECTRON_ANTINEUTRINO)
                .output(ELECTRON)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(ANTINEUTRON)
                .output(POSITRON)
                .output(ELECTRON_ANTINEUTRINO)
                .output(ANTIPROTON)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(PION_NAUGHT)
                .output(PHOTON)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(PION_PLUS)
                .output(ANTIMUON)
                .output(MUON_NEUTRINO)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(PION_MINUS)
                .output(MUON_ANTINEUTRINO)
                .output(MUON)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(MUON)
                .output(ELECTRON_ANTINEUTRINO)
                .output(MUON_NEUTRINO)
                .output(ELECTRON)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(ANTIMUON)
                .output(POSITRON)
                .output(MUON_ANTINEUTRINO)
                .output(ELECTRON_NEUTRINO)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(TAU)
                .output(PION_NAUGHT)
                .output(TAU_NEUTRINO)
                .output(PION_MINUS)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(ANTITAU)
                .output(PION_PLUS)
                .output(TAU_ANTINEUTRINO)
                .output(PION_NAUGHT)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(KAON_PLUS)
                .output(ANTIMUON)
                .output(MUON_NEUTRINO)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(KAON_MINUS)
                .output(MUON_ANTINEUTRINO)
                .output(MUON)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(KAON_NAUGHT)
                .output(PION_PLUS)
                .output(PION_MINUS)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(ANTIKAON_NAUGHT)
                .output(PION_PLUS)
                .output(PION_MINUS)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(W_MINUS_BOSON)
                .output(PION_MINUS)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(W_PLUS_BOSON)
                .output(PION_PLUS)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(Z_BOSON)
                .output(ELECTRON_NEUTRINO)
                .output(ELECTRON_ANTINEUTRINO)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(HIGGS_BOSON)
                .output(BOTTOM_ETA)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(ETA)
                .output(PION_PLUS)
                .output(PION_NAUGHT)
                .output(PION_MINUS)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(ETA_PRIME)
                .output(PION_PLUS)
                .output(ETA)
                .output(PION_MINUS)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(CHARMED_ETA)
                .output(KAON_NAUGHT)
                .output(ANTIKAON_NAUGHT)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(BOTTOM_ETA)
                .output(ANTITAU)
                .output(TAU)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(TRITON)
                .output(HELION)
                .output(ELECTRON_ANTINEUTRINO)
                .output(ELECTRON)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(ANTITRITON)
                .output(POSITRON)
                .output(ELECTRON_NEUTRINO)
                .output(ANTIHELION)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(GLUEBALL)
                .output(KAON_PLUS)
                .output(KAON_MINUS)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(SIGMA_PLUS)
                .output(PROTON)
                .output(PION_NAUGHT)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(ANTISIGMA_PLUS)
                .output(PION_NAUGHT)
                .output(ANTIPROTON)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(SIGMA_MINUS)
                .output(NEUTRON)
                .output(PION_MINUS)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(ANTISIGMA_MINUS)
                .output(PION_PLUS)
                .output(ANTINEUTRON)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(DELTA_PLUS_PLUS)
                .output(PROTON)
                .output(PION_PLUS)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(ANTIDELTA_PLUS_PLUS)
                .output(ANTIPROTON)
                .output(PION_MINUS)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(DELTA_MINUS)
                .output(NEUTRON)
                .output(PION_MINUS)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(ANTIDELTA_MINUS)
                .output(ANTINEUTRON)
                .output(PION_PLUS)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(PHOTON)
                .circuitMeta(1)
                .output(ELECTRON)
                .output(POSITRON)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(PHOTON)
                .circuitMeta(2)
                .output(MUON)
                .output(ANTIMUON)
                .CWUt(512)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();
    }
    public static void TARGET_CHAMBER()
    {
        //0-100000
        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Boron)
                .output(ALPHA)
                .EUToStart(400)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Beryllium)
                .output(dust,Lithium7)
                .output(ALPHA)
                .EUToStart(1000)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Aluminium)
                .output(dust,Silicon)
                .output(PHOTON)
                .EUToStart(1500)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .fluidInputs(Tritium.getFluid(1000))
                .output(HELION)
                .output(NEUTRON)
                .EUToStart(2400)
                .Scattering(5)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .fluidInputs(Fluorine.getFluid(1000))
                .fluidOutputs(Oxygen.getFluid(1000))
                .output(ALPHA)
                .EUToStart(4000)
                .Scattering(5)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Copper)
                .output(dust,Zinc)
                .output(PHOTON)
                .circuitMeta(1)
                .EUToStart(4500)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Cobalt)
                .output(dust,Nickel)
                .output(PHOTON)
                .EUToStart(4500)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .fluidInputs(Deuterium.getFluid(1000))
                .circuitMeta(1)
                .output(HELION)
                .output(PHOTON)
                .EUToStart(6000)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Osmium)
                .output(dust,Iridium)
                .output(NEUTRON)
                .EUToStart(1500)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Manganese)
                .output(dust,Iron)
                .output(PHOTON)
                .EUToStart(9700)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .fluidInputs(Deuterium.getFluid(1000))
                .output(PROTON)
                .output(NEUTRON)
                .circuitMeta(2)
                .EUToStart(6000)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Thorium)
                .output(dust,Protactinium)
                .output(NEUTRON)
                .EUToStart(10000)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Uranium238)
                .output(dust,Neptunium)
                .output(NEUTRON)
                .EUToStart(10000)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Gold)
                .fluidOutputs(Mercury.getFluid(1000))
                .output(NEUTRON)
                .EUToStart(10000)
                .Scattering(5)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Plutonium242)
                .output(dust,Americium241)
                .output(NEUTRON)
                .EUToStart(10000)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Bismuth)
                .output(dust,Polonium)
                .output(PHOTON)
                .EUToStart(4700)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Boron)
                .output(dust,Carbon)
                .output(PHOTON)
                .EUToStart(150)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Calcium)
                .output(dust,Potassium)
                .output(PROTON)
                .EUToStart(7850)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .fluidInputs(Nitrogen.getFluid(1000))
                .output(dust,Beryllium)
                .output(ALPHA)
                .circuitMeta(1)
                .EUToStart(8930)
                .Scattering(3)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();


        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust,Carbon)
                .output(ALPHA)
                .output(PROTON)
                .circuitMeta(1)
                .EUToStart(6140)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .output(dust,Aluminium)
                .input(dust,Silicon)
                .output(PROTON)
                .EUToStart(1500)
                .Scattering(8)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();


        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Uranium238)
                .output(dust,Neptunium236)
                .output(NEUTRON)
                .EUToStart(10000)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Bismuth)
                .output(dust,Lead)
                .output(NEUTRON)
                .EUToStart(10900)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Gold)
                .output(dust,Platinum)
                .output(ALPHA)
                .EUToStart(9900)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Calcium)
                .fluidOutputs(Argon.getFluid(1000))
                .output(PROTON)
                .EUToStart(18900)
                .Scattering(5)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .fluidInputs(Nitrogen.getFluid(1000))
                .output(dust,Carbon)
                .output(PROTON)
                .output(ALPHA)
                .circuitMeta(2)
                .EUToStart(9700)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(Nitrogen.getFluid(1000))
                .output(ALPHA)
                .output(PROTON)
                .circuitMeta(2)
                .EUToStart(20240)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Copper)
                .output(dust,Nickel)
                .output(ALPHA)
                .output(PROTON)
                .EUToStart(4240)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(dust,Aluminium)
                .output(dust,Sodium)
                .output(PROTON)
                .output(NEUTRON)
                .circuitMeta(2)
                .EUToStart(37440)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        //////
        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Helium3.getFluid(1000))
                .output(PROTON)
                .output(TRITON)
                .EUToStart(7640)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .input(dust,Beryllium)
                .output(dust,Lithium7)
                .output(PROTON)
                .EUToStart(2150)
                .Scattering(3)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .input(dust,Sulfur)
                .output(dust,Silicon)
                .output(ALPHA)
                .EUToStart(2250)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .input(dust,Zinc)
                .output(dust,Nickel)
                .output(ALPHA)
                .EUToStart(4880)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Nitrogen.getFluid(1000))
                .output(dust,Copper)
                .output(ALPHA)
                .EUToStart(4880)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .input(dust,Iron)
                .output(dust,Chrome)
                .output(ALPHA)
                .EUToStart(1350)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .input(dust,Chrome)
                .output(dust,Titanium)
                .output(ALPHA)
                .EUToStart(1350)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .input(dust,Copper)
                .output(dust,Nickel)
                .output(ALPHA)
                .output(NEUTRON)
                .EUToStart(1350)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .input(dust,Potassium)
                .fluidOutputs(Chlorine.getFluid(1000))
                .output(ALPHA)
                .output(NEUTRON)
                .EUToStart(1350)
                .Scattering(3)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .input(dust,Zircon)
                .output(dust,Strontium)
                .output(ALPHA)
                .output(NEUTRON)
                .EUToStart(13050)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .input(dust,Platinum)
                .output(dust,Osmium)
                .output(ALPHA)
                .output(NEUTRON)
                .EUToStart(5760)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .input(dust,Terbium)
                .output(dust,Europium)
                .output(ALPHA)
                .output(NEUTRON)
                .EUToStart(57600)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .input(dust,Zircon)
                .output(dust,Yttrium)
                .output(DEUTERON)
                .EUToStart(5620)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust,Beryllium)
                .output(ALPHA)
                .EUToStart(10800)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .input(dust,Niobium)
                .output(dust,Yttrium)
                .output(NEUTRON)
                .output(ALPHA)
                .EUToStart(64804)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .input(dust,Manganese)
                .output(dust,Chrome)
                .output(TRITON)
                .EUToStart(12604)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .input(dust,Cobalt)
                .output(dust,Iron)
                .output(TRITON)
                .EUToStart(24560)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .input(dust,Yttrium)
                .output(dust,Strontium)
                .output(DEUTERON)
                .EUToStart(13840)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .input(dust,Sodium)
                .fluidOutputs(Neon.getFluid(1000))
                .output(DEUTERON)
                .EUToStart(4860)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .input(dust,Gold)
                .output(dust,Iridium)
                .output(ALPHA)
                .output(NEUTRON)
                .EUToStart(19900)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .fluidInputs(Helium3.getFluid(1000))
                .output(HELION)
                .output(NEUTRON)
                .EUToStart(19900)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(NEUTRON)
                .input(dust,Bismuth)
                .output(dust,Lead)
                .output(TRITON)
                .EUToStart(19900)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        //////////
        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PHOTON)
                .input(dust,Tungsten)
                .output(dust,Hafnium)
                .output(ALPHA)
                .EUToStart(19900)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PHOTON)
                .input(dust,Zircon)
                .output(dust,Yttrium)
                .output(PROTON)
                .EUToStart(19900)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PHOTON)
                .input(dust,Iron)
                .output(dust,Manganese)
                .output(PROTON)
                .EUToStart(19900)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PHOTON)
                .input(dust,Yttrium)
                .output(dust,Strontium)
                .output(PROTON)
                .EUToStart(19900)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PHOTON)
                .input(dust,Aluminium)
                .output(dust,Magnesium)
                .output(PROTON)
                .EUToStart(19900)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PHOTON)
                .input(dust,Silicon)
                .output(dust,Aluminium)
                .output(PROTON)
                .EUToStart(19900)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PHOTON)
                .input(dust,Calcium)
                .output(dust,Potassium)
                .output(PROTON)
                .EUToStart(19900)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PHOTON)
                .input(dust,Copper)
                .output(dust,Nickel)
                .output(PROTON)
                .output(NEUTRON)
                .EUToStart(19900)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PHOTON)
                .fluidInputs(Nitrogen.getFluid(1000))
                .output(dust,Carbon)
                .output(PROTON)
                .output(NEUTRON)
                .EUToStart(19900)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PHOTON)
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidInputs(Nitrogen.getFluid(1000))
                .output(PROTON)
                .output(NEUTRON)
                .EUToStart(19900)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        //////////////////////////////////////////////////////////////////
        //补
        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(DEUTERON)
                .input(PROTON)
                .output(PHOTON)
                .output(NEUTRON)
                .EUToStart(12000)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ANTIDEUTERON)
                .input(ANTIPROTON)
                .output(PHOTON)
                .output(ANTINEUTRON)
                .EUToStart(12000)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(TRITON)
                .input(DEUTERON)
                .output(PHOTON)
                .output(NEUTRON)
                .EUToStart(12000)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ANTITRITON)
                .input(ANTIDEUTERON)
                .output(PHOTON)
                .output(ANTINEUTRON)
                .EUToStart(12000)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ALPHA)
                .input(HELION)
                .output(PHOTON)
                .output(NEUTRON)
                .EUToStart(12000)
                .Scattering(3)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ANTIALPHA)
                .input(ANTIHELION)
                .output(PHOTON)
                .output(ANTINEUTRON)
                .EUToStart(12000)
                .Scattering(3)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(DEUTERON)
                .input(PROTON)
                .input(ELECTRON_NEUTRINO)
                .output(POSITRON)
                .output(PROTON)
                .EUToStart(12000)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ANTIDEUTERON)
                .input(ANTIPROTON)
                .input(ELECTRON_ANTINEUTRINO)
                .output(POSITRON)
                .output(ANTIPROTON)
                .EUToStart(12000)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(HELION)
                .input(PROTON)
                .output(PHOTON)
                .output(DEUTERON)
                .EUToStart(12000)
                .Scattering(5)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ANTIHELION)
                .input(ANTIPROTON)
                .output(PHOTON)
                .output(ANTIDEUTERON)
                .EUToStart(12000)
                .Scattering(5)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ALPHA)
                .input(HELION)
                .output(PROTON)
                .output(HELION)
                .EUToStart(12000)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ANTIALPHA)
                .input(ANTIHELION)
                .output(ANTIPROTON)
                .output(ANTIHELION)
                .EUToStart(12000)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ALPHA)
                .input(DEUTERON)
                .output(NEUTRON)
                .output(TRITON)
                .EUToStart(12000)
                .Scattering(7)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ANTIALPHA)
                .input(ANTIDEUTERON)
                .output(ANTINEUTRON)
                .output(ANTITRITON)
                .EUToStart(12000)
                .Scattering(7)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ALPHA)
                .input(TRITON)
                .output(NEUTRON)
                .output(TRITON)
                .EUToStart(12000)
                .Scattering(8)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ANTIALPHA)
                .input(ANTITRITON)
                .output(ANTINEUTRON)
                .output(ANTITRITON)
                .EUToStart(12000)
                .Scattering(8)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ALPHA)
                .input(DEUTERON)
                .output(PROTON)
                .output(HELION)
                .EUToStart(12000)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ANTIALPHA)
                .input(ANTIDEUTERON)
                .output(ANTIPROTON)
                .output(ANTIHELION)
                .EUToStart(12000)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ALPHA)
                .input(HELION)
                .output(NEUTRON)
                .output(PROTON)
                .output(TRITON)
                .EUToStart(12000)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ANTIALPHA)
                .input(ANTIHELION)
                .output(ANTINEUTRON)
                .output(ANTIPROTON)
                .output(ANTITRITON)
                .EUToStart(12000)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(TRITON)
                .input(DEUTERON)
                .output(PROTON)
                .output(DEUTERON)
                .EUToStart(12000)
                .Scattering(3)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ANTITRITON)
                .input(ANTIDEUTERON)
                .output(ANTIPROTON)
                .output(ANTIDEUTERON)
                .EUToStart(12000)
                .Scattering(3)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PION_PLUS)
                .input(PROTON)
                .input(PION_MINUS)
                .output(PION_NAUGHT)
                .output(ANTIPROTON)
                .EUToStart(12000)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PION_PLUS)
                .input(NEUTRON)
                .input(PION_MINUS)
                .output(PION_NAUGHT)
                .output(ANTINEUTRON)
                .EUToStart(12000)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PION_PLUS)
                .input(ANTIPROTON)
                .input(PION_MINUS)
                .output(PION_NAUGHT)
                .output(PROTON)
                .EUToStart(12000)
                .Scattering(5)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PION_PLUS)
                .input(ANTINEUTRON)
                .input(PION_MINUS)
                .output(PION_NAUGHT)
                .output(NEUTRON)
                .EUToStart(12000)
                .Scattering(5)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ELECTRON_NEUTRINO)
                .input(ELECTRON)
                .input(MUON_ANTINEUTRINO)
                .output(MUON)
                .output(ELECTRON)
                .EUToStart(12000)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ELECTRON_ANTINEUTRINO)
                .input(POSITRON)
                .input(MUON_ANTINEUTRINO)
                .output(MUON)
                .output(POSITRON)
                .EUToStart(12000)
                .Scattering(6)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(MUON)
                .input(ELECTRON)
                .output(ANTIMUON)
                .output(POSITRON)
                .EUToStart(12000)
                .Scattering(7)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(PROTON)
                .input(PROTON)
                .input(PION_MINUS)
                .output(PION_PLUS)
                .output(PROTON)
                .output(PION_NAUGHT)
                .EUToStart(12000)
                .Scattering(7)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ANTIPROTON)
                .input(ANTIPROTON)
                .input(PION_PLUS)
                .output(PION_MINUS)
                .output(ANTIPROTON)
                .output(PION_NAUGHT)
                .EUToStart(12000)
                .Scattering(8)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(DELTA_MINUS)
                .input(PROTON)
                .input(KAON_PLUS)
                .output(SIGMA_PLUS)
                .output(PROTON)
                .output(PION_PLUS)
                .EUToStart(12000)
                .Scattering(8)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(ANTIDELTA_MINUS)
                .input(ANTIPROTON)
                .input(KAON_MINUS)
                .output(ANTISIGMA_PLUS)
                .output(ANTIPROTON)
                .output(PION_MINUS)
                .EUToStart(14000)
                .Scattering(1)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(TAU)
                .input(ELECTRON)
                .input(Z_BOSON)
                .output(ANTITAU)
                .output(POSITRON)
                .EUToStart(14000)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(GLUEBALL)
                .input(PROTON)
                .input(W_MINUS_BOSON)
                .output(W_PLUS_BOSON)
                .output(ANTIPROTON)
                .output(CHARMED_ETA)
                .EUToStart(14000)
                .Scattering(3)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(HIGGS_BOSON)
                .input(PROTON)
                .input(KAON_PLUS)
                .output(DELTA_PLUS_PLUS)
                .output(PROTON)
                .output(SIGMA_MINUS)
                .EUToStart(14000)
                .Scattering(4)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(HIGGS_BOSON)
                .input(ANTIPROTON)
                .input(KAON_MINUS)
                .output(ANTIDELTA_MINUS)
                .output(ANTIPROTON)
                .output(ANTISIGMA_MINUS)
                .EUToStart(14000)
                .Scattering(5)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();
    }


}
