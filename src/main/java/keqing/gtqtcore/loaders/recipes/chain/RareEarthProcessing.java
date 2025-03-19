package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.unification.material.Material;
import keqing.gtqtcore.api.unification.GTQTMaterials;

import static gregtech.api.unification.material.Materials.Hafnium;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.SodiumFluoride;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.swarm;
import static keqing.gtqtcore.api.utils.GTQTUniversUtil.SECOND;


public class RareEarthProcessing {

    public static void init() {
        EP();
        bastnasite();
        monazite();
        HafniumZirconium();
        Cerium();
        Oxalate();
        SodiumFluorosilicate();
        p507();
        NanoSwarm();
    }

    private static void NanoSwarm() {
        //  Nano Resin Processing is an Advanced Rare Earth Processing for player which beyond ZPM tier,
        //  required Mysterious Crystal lens and Nanoscale Mask Aligner (Mega Laser Engraver) to start.
        BLAST_RECIPES.recipeBuilder()
                .input(dust, RareEarth, 8)
                .fluidInputs(Chlorine.getFluid(6000))
                .output(dust, SiliconDioxide)
                .fluidOutputs(RareEarthChloridesConcentrate.getFluid(1000))
                .EUt(VA[ZPM])
                .duration(2 * SECOND)
                .blastFurnaceTemp(1600) // Cupronickel
                .buildAndRegister();

        //  Lanthanum (La)-Praseodymium (Pr)-Neodymium (Nd)-Cerium (Ce)
        addNanoExtractingRecipe(
                Lanthanum,
                LanthanumExtractingNanoResin,
                FilledLanthanumExtractingNanoResin);

        addNanoExtractingRecipe(
                Praseodymium,
                PraseodymiumExtractingNanoResin,
                FilledPraseodymiumExtractingNanoResin);

        addNanoExtractingRecipe(
                Neodymium,
                NeodymiumExtractingNanoResin,
                FilledNeodymiumExtractingNanoResin);

        addNanoExtractingRecipe(
                Cerium,
                CeriumExtractingNanoResin,
                FilledCeriumExtractingNanoResin);

        //  Scandium (Sc)-Europium (Eu)-Gadolinium (Gd)-Samarium(Sm)
        addNanoExtractingRecipe(
                Scandium,
                ScandiumExtractingNanoResin,
                FilledScandiumExtractingNanoResin);

        addNanoExtractingRecipe(
                Europium,
                EuropiumExtractingNanoResin,
                FilledEuropiumExtractingNanoResin);

        addNanoExtractingRecipe(
                Gadolinium,
                GadoliniumExtractingNanoResin,
                FilledGadoliniumExtractingNanoResin);

        addNanoExtractingRecipe(
                Samarium,
                SamariumExtractingNanoResin,
                FilledSamariumExtractingNanoResin);

        //  Yttrium (Y)-Terbium (Tb)-Dysprosium (Dy)-Holmium (Ho)
        addNanoExtractingRecipe(
                Yttrium,
                YttriumExtractingNanoResin,
                FilledYttriumExtractingNanoResin);

        addNanoExtractingRecipe(
                Terbium,
                TerbiumExtractingNanoResin,
                FilledTerbiumExtractingNanoResin);

        addNanoExtractingRecipe(
                Dysprosium,
                DysprosiumExtractingNanoResin,
                FilledDysprosiumExtractingNanoResin);

        addNanoExtractingRecipe(
                Holmium,
                HolmiumExtractingNanoResin,
                FilledHolmiumExtractingNanoResin);

        //  Erbium (Er)-Thulium (Tm)-Ytterbium (Yb)-Lutetium (Lu)
        addNanoExtractingRecipe(
                Erbium,
                ErbiumExtractingNanoResin,
                FilledErbiumExtractingNanoResin);

        addNanoExtractingRecipe(
                Thulium,
                ThuliumExtractingNanoResin,
                FilledThuliumExtractingNanoResin);

        addNanoExtractingRecipe(
                Ytterbium,
                YtterbiumExtractingNanoResin,
                FilledYtterbiumExtractingNanoResin);

        addNanoExtractingRecipe(
                Lutetium,
                LutetiumExtractingNanoResin,
                FilledLutetiumExtractingNanoResin);
    }

    private static void addNanoExtractingRecipe(Material material,
                                                Material resinMaterial,
                                                Material filledResinMaterial) {
        //  Step 1: {@code material} -> {@code resinMaterial}
        //  For example: Lanthanum -> Lanthanum Extracting Nano Resin
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(lens, MysteriousCrystal)
                .input(dust, material)
                .input(swarm, Carbon)
                .fluidInputs(DiethylhexylPhosphoricAcid.getFluid(4000))
                .fluidOutputs(resinMaterial.getFluid(1000))
                .EUt(VA[UV])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  Step 2: {@code resinMaterial} -> {@code filledResinMaterial}.
        //  Catalyst Liquid Decay Chain: Concentrate -> Enriched Solution -> Diluted Solution -> Waste Fluid.
        //  For example: Lanthanum Extracting Nano Resin -> Filled Lanthanum Extracting Nano Resin
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(resinMaterial.getFluid(1000))
                .fluidInputs(RareEarthChloridesConcentrate.getFluid(1000))
                .fluidOutputs(filledResinMaterial.getFluid(1000))
                .fluidOutputs(RareEarthChloridesEnrichedSolution.getFluid(1000))
                .EUt(VA[UV])
                .duration(SECOND)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(resinMaterial.getFluid(1000))
                .fluidInputs(RareEarthChloridesEnrichedSolution.getFluid(1000))
                .fluidOutputs(filledResinMaterial.getFluid(1000))
                .fluidOutputs(RareEarthChloridesDilutedSolution.getFluid(1000))
                .EUt(VA[UV])
                .duration(SECOND)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(resinMaterial.getFluid(1000))
                .fluidInputs(RareEarthChloridesDilutedSolution.getFluid(1000))
                .fluidOutputs(filledResinMaterial.getFluid(1000))
                .fluidOutputs(ChlorinatedRareEarthWasteFluid.getFluid(1000))
                .EUt(VA[UV])
                .duration(SECOND)
                .buildAndRegister();

        //  Step 3: {@code filledResinMaterial} -> {@code material} + {@code resinMaterial}.
        //  For example: Filled Lanthanum Extracting Nano Resin -> Lanthanum + Lanthanum Extracting Nano Resin
        ELECTROLYZER_RECIPES.recipeBuilder()
                .fluidInputs(filledResinMaterial.getFluid(1000))
                .fluidOutputs(material.getFluid(L))
                .fluidOutputs(resinMaterial.getFluid(1000))
                .fluidOutputs(Chlorine.getFluid(3000))
                .EUt(VA[UV])
                .duration(5 * SECOND)
                .buildAndRegister();

        //  Chlorinated Rare Earth Waste Fluid recycling
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(ChlorinatedRareEarthWasteFluid.getFluid(10000))
                .output(dust, Chrome, 3)
                .fluidOutputs(SaltWater.getFluid(3000))
                .fluidOutputs(Phenol.getFluid(2000))
                .fluidOutputs(HydrochloricAcid.getFluid(5000))
                .EUt(VA[HV])
                .duration(15 * SECOND)
                .buildAndRegister();

    }

    private static void EP() {
        //  Rare Earth -> Crude Rare Earth Turbid Solution
        MIXER_RECIPES.recipeBuilder()
                .input(dust, RareEarth)
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidOutputs(CrudeRareEarthTurbidSolution.getFluid(2000))
                .EUt(VA[EV])
                .duration(120)
                .buildAndRegister();

        //  Crude Rare Earth Turbid Solution + Nitric Acid -> Nitrated Rare Earth Turbid Solution
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(CrudeRareEarthTurbidSolution.getFluid(2000))
                .fluidInputs(NitricAcid.getFluid(2000))
                .fluidOutputs(NitratedRareEarthTurbidSolution.getFluid(4000))
                .EUt(VA[IV])
                .duration(350)
                .buildAndRegister();

        //  Sodium Hydroxide + Di-(2-ethylhexyl)phosphoric Acid + Nitrated Rare Earth Turbid Solution -> Sodium Nitrate + Rare Earth Hydroxides Solution
        DIGESTER_RECIPES.recipeBuilder()
                .input(dust, SodiumHydroxide, 3)
                .fluidInputs(DiethylhexylPhosphoricAcid.getFluid(1000))
                .fluidInputs(NitratedRareEarthTurbidSolution.getFluid(2000))
                .output(dust, SodiumNitrate, 3)
                .fluidOutputs(RareEarthHydroxidesSolution.getFluid(1000))
                .duration(240)
                .EUt(VA[IV])
                .buildAndRegister();

        //  Rare Earth Hydroxides Solution + Hydrochloric Acid -> Rare Earth Chorides Slurry + Steam
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(RareEarthHydroxidesSolution.getFluid(1000))
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .output(dust, RareEarthChloridesSlurry, 4)
                .fluidOutputs(Steam.getFluid(600))
                .blastFurnaceTemp(4500)
                .duration(120)
                .EUt(VA[EV])
                .buildAndRegister();

        //  Rare Earth Chorides Slurry (EV)
        ROASTER_RECIPES.recipeBuilder()
                .input(dust, RareEarthChloridesSlurry, 4)
                .input(dust, SodiumBicarbonate, 8)
                .fluidInputs(DistilledWater.getFluid(1000))
                .output(dust, Sodium, 4)
                .fluidOutputs(LowPurityRareEarthChloridesSolution.getFluid(1000))
                .fluidOutputs(CarbonDioxide.getFluid(2000))
                .temperature(4500)
                .EUt(VA[EV])
                .duration(1200)
                .buildAndRegister();

        //  Rare Earth Chorides Slurry (EV)
        ROASTER_RECIPES.recipeBuilder()
                .input(dust, RareEarthChloridesSlurry, 2)
                .input(dust, BariumCarbonate, 4)
                .fluidInputs(DistilledWater.getFluid(500))
                .output(dust, Barium, 2)
                .fluidOutputs(LowPurityRareEarthChloridesSolution.getFluid(2000))
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .temperature(4500)
                .EUt(VA[EV])
                .duration(600)
                .buildAndRegister();

        //  Low Purity Rare Earth Chlorides Solution + AquaRegia -> Roughly Purified Rare Earth Chlorides Solution
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(LowPurityRareEarthChloridesSolution.getFluid(4000))
                .fluidInputs(AquaRegia.getFluid(2000))
                .fluidOutputs(RoughlyPurifiedRareEarthChloridesSolution.getFluid(6000))
                .EUt(VA[IV])
                .duration(1200)
                .buildAndRegister();

        //  Roughly Purified Rare Earth Chlorides Solution -> High Purity Rare Earth Chlorides Slurry + Diluted Hydrochloric Acid
        DRYER_RECIPES.recipeBuilder()
                .fluidInputs(RoughlyPurifiedRareEarthChloridesSolution.getFluid(6000))
                .output(dust, HighPurityRareEarthChloridesSlurry, 3)
                .output(dust, LowPurityRareEarthChloridesSlag, 2)
                .fluidOutputs(DilutedHydrochloricAcid.getFluid(1000))
                .EUt(VA[EV])
                .duration(300)
                .buildAndRegister();

        //  High Purity Rare Earth Chlorides Slurry + Hydrochloric Acid -> High Purity Rare Earth Chlorides Solution
        DISSOLUTION_TANK_RECIPES.recipeBuilder()
                .input(dust, HighPurityRareEarthChloridesSlurry)
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .fluidOutputs(HighPurityRareEarthChloridesSolution.getFluid(1000))
                .EUt(VA[IV])
                .duration(600)
                .buildAndRegister();

        //  High Purity Rare Earth Chlorides Solution -> Neodymium Oxide
        BLAST_RECIPES.recipeBuilder()
                .fluidInputs(HighPurityRareEarthChloridesSolution.getFluid(1000))
                .circuitMeta(1)
                .output(dust, NeodymiumOxide)
                .fluidOutputs(DilutedHydrochloricAcid.getFluid(500))
                .duration(600)
                .blastFurnaceTemp(4500)
                .EUt(VA[IV])
                .buildAndRegister();

        //  High Purity Rare Earth Chlorides Solution -> Cerium Oxide
        BLAST_RECIPES.recipeBuilder()
                .fluidInputs(HighPurityRareEarthChloridesSolution.getFluid(1000))
                .circuitMeta(2)
                .output(dust, CeriumOxide)
                .fluidOutputs(DilutedHydrochloricAcid.getFluid(300))
                .duration(600)
                .blastFurnaceTemp(4500)
                .EUt(VA[IV])
                .buildAndRegister();

        //  High Purity Rare Earth Chlorides Solution -> Samarium Oxide
        BLAST_RECIPES.recipeBuilder()
                .fluidInputs(HighPurityRareEarthChloridesSolution.getFluid(1000))
                .circuitMeta(3)
                .output(dust, SamariumOxide)
                .fluidOutputs(HydrochloricAcid.getFluid(800))
                .duration(600)
                .blastFurnaceTemp(4500)
                .EUt(VA[IV])
                .buildAndRegister();

        //  Low Purity Rare Earth Chlorides Slag + Cobaltite -> Yttrium Oxide, Lanthanum Oxide
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, LowPurityRareEarthChloridesSlag, 4)
                .input(dust, Cobaltite, 2)
                .output(dust, YttriumOxide)
                .output(dust, Sulfur, 2)
                .duration(600)
                .blastFurnaceTemp(4500)
                .EUt(VA[IV])
                .buildAndRegister();

        //  Low Purity Rare Earth Chlorides Slag + Ferric Oxide -> Lanthanum Oxide + Oxygen
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, LowPurityRareEarthChloridesSlag, 4)
                .input(dust, FerricOxide, 2)
                .output(dust, LanthanumOxide)
                .fluidOutputs(Oxygen.getFluid(3000))
                .duration(600)
                .EUt(VA[LuV])
                .blastFurnaceTemp(4500)
                .buildAndRegister();
    }

    private static void bastnasite() {

        DIGESTER_RECIPES.recipeBuilder()
                .fluidInputs(NitricAcid.getFluid(700))
                .input(crushed,Bastnasite,1)
                .fluidOutputs(FluorocarbonLanthanumCeriumRareEarthTurbidity.getFluid(400))
                .output(dust,SiliconDioxide)
                .circuitMeta(1)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        DIGESTER_RECIPES.recipeBuilder()
                .fluidInputs(NitricAcid.getFluid(700))
                .input(dust,Bastnasite,2)
                .fluidOutputs(FluorocarbonLanthanumCeriumRareEarthTurbidity.getFluid(400))
                .output(dust,SiliconDioxide)
                .circuitMeta(2)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
                .fluidInputs(FluorocarbonLanthanumCeriumRareEarthTurbidity.getFluid(1000))
                .fluidInputs(Steam.getFluid(1000))
                .fluidOutputs(SteamCrackingFluorocarbonLanthanumCeriumSlurry.getFluid(1000))
                .duration(600).EUt(VA[EV]).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(SteamCrackingFluorocarbonLanthanumCeriumSlurry.getFluid(1000))
                .fluidInputs(SodiumSilicofluoride.getFluid(320))
                .fluidOutputs(ModulationFluorocarbonLanthanumCeriumSlurry.getFluid(1320))
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(ModulationFluorocarbonLanthanumCeriumSlurry.getFluid(1000))
                .output(dust, FilterFluorocarbonLanthanumCeriumSlurry)
                .chancedOutput(dust,SodiumNitrate,8000,0)
                .chancedOutput(dust,Rutile,2000,0)
                .chancedOutput(dust,Ilmenite,2000,0)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust, FilterFluorocarbonLanthanumCeriumSlurry)
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust, FluorocarbonLanthanumCeriumRareEarthOxide,1)
                .fluidOutputs(Fluorine.getFluid(36))
                .blastFurnaceTemp(3500)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, FluorocarbonLanthanumCeriumRareEarthOxide)
                .fluidInputs(NitricAcid.getFluid(1000))
                .fluidOutputs(AcidFluorocarbonLanthanumCeriumRareEarthOxide.getFluid(1000))
                .fluidOutputs(NitricAcid.getFluid(500))
                .duration(600).EUt(VA[EV]).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust,Calcite)
                .fluidInputs(AcidFluorocarbonLanthanumCeriumRareEarthOxide.getFluid(1000))
                .fluidOutputs(NitricAcid.getFluid(500))
                .output(dust, RoastedRareEarthOxide,1)
                .output(dust,Calcium)
                .blastFurnaceTemp(3500)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, RoastedRareEarthOxide)
                .fluidInputs(Fluorine.getFluid(4000))
                .fluidOutputs(CeriumOxideRareEarthOxide.getFluid(1000))
                .fluidOutputs(HydrofluoricAcid.getFluid(4000))
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(CeriumOxideRareEarthOxide.getFluid(4000))
                .fluidOutputs(NitricAcid.getFluid(200))
                .output(dust, FluorocarbonLanthanumCeriumRareSoilOxide)
                .output(dust,CeriumOxide)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, FluorocarbonLanthanumCeriumRareSoilOxide)
                .fluidInputs(Acetone.getFluid(1000))
                .fluidOutputs(FluorocarbonLanthanumCeriumRareSoilOxideSuspension.getFluid(1000))
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(FluorocarbonLanthanumCeriumRareSoilOxideSuspension.getFluid(4000))
                .output(dust, SamariumRareEarth)
                .output(dust,LanthanumOxide)
                .output(dust,NeodymiumOxide)
                .fluidOutputs(Acetone.getFluid(4000))
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SamariumRareEarth)
                .fluidInputs(HydrofluoricAcid.getFluid(2000))
                .fluidOutputs(SamariumFluorideRareEarthRefined.getFluid(2000))
                .duration(600).EUt(VA[EV]).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust,Sodium,2)
                .fluidInputs(SamariumFluorideRareEarthRefined.getFluid(2000))
                .fluidOutputs(SamariumTerbiumMixture.getFluid(2000))
                .output(dust,HolmiumOxide)
                .output(dust,SodiumFluoride,4)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(SamariumTerbiumMixture.getFluid(3000))
                .output(dust,TerbiumOxide)
                .output(dust,GadoliniumOxide)
                .output(dust, SamariumEssence)
                .duration(600).EUt(VA[EV]).buildAndRegister();
    }
    private static void monazite() {
        //快乐独居石处理
        DIGESTER_RECIPES.recipeBuilder()
                .fluidInputs(NitricAcid.getFluid(700))
                .input(crushed,Monazite,1)
                .fluidOutputs(MonaziteRareEarthTurbid.getFluid(400))
                .output(dust,SiliconDioxide)
                .circuitMeta(1)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        DIGESTER_RECIPES.recipeBuilder()
                .fluidInputs(NitricAcid.getFluid(700))
                .input(dust,Monazite,2)
                .fluidOutputs(MonaziteRareEarthTurbid.getFluid(400))
                .output(dust,SiliconDioxide)
                .circuitMeta(2)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        DISSOLUTION_TANK_RECIPES.recipeBuilder()
                .fluidInputs(MonaziteRareEarthTurbid.getFluid(9000))
                .fluidInputs(Water.getFluid(90000))
                .input(dust,Saltpeter,9)
                .fluidOutputs(DiluteMonaziteRareEarthMud.getFluid(99000))
                .output(dust, HafniumOxideZirconiumOxideMixture,4)
                .output(dust,Thorianite,9)
                .output(dust,Monazite,2)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(DiluteMonaziteRareEarthMud.getFluid(1000))
                .fluidOutputs(SulfuricAcidMonazite.getFluid(1000))
                .output(dust,Rutile,3)
                .chancedOutput(dust,SodiumNitrate,8000,0)
                .chancedOutput(dust,Rutile,2000,0)
                .chancedOutput(dust,Ilmenite,2000,0)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(SulfuricAcidMonazite.getFluid(1000))
                .chancedOutput(dust, MonaziteRareEarthFilterResidue,9000,0)
                .chancedOutput(dust, ThoriumPhosphateFilterCake,9000,0)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,SodiumHydroxide)
                .fluidInputs(NitricAcid.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .output(dust,SodiumNitrate)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust, ThoriumPhosphateFilterCake,1)
                .output(dust, ThoriumPhosphateConcentrate,1)
                .chancedOutput(dust,SodiumNitrate,8000,0)
                .blastFurnaceTemp(3500)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, ThoriumPhosphateConcentrate,1)
                .output(dust,Thorium)
                .output(dust,Phosphate)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, MonaziteRareEarthFilterResidue)
                .fluidInputs(AmmoniumNitrate.getFluid(1000))
                .output(dust, NeutralizationMonaziteRareEarthFilterResidue)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, NeutralizationMonaziteRareEarthFilterResidue)
                .output(dust, ConcentratedMonaziteRareEarthHydroxide)
                .output(dust, UraniumFilterResidue)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, UraniumFilterResidue)
                .output(dust,Uranium235)
                .output(dust,Uranium238)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust, ConcentratedMonaziteRareEarthHydroxide,1)
                .output(dust, DryConcentratedNitrateMonaziteRareEarth,1)
                .chancedOutput(dust,SodiumNitrate,2000,0)
                .blastFurnaceTemp(3500)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, DryConcentratedNitrateMonaziteRareEarth)
                .output(dust, SolitaryStoneRareSoilSedimentation)
                .output(dust,EuropiumOxide)
                .output(dust,CeriumOxide)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SolitaryStoneRareSoilSedimentation)
                .fluidInputs(Chlorine.getFluid(1000))
                .output(dust, HeterogeneousHalogenatedMonaziteRareEarthMixture)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, HeterogeneousHalogenatedMonaziteRareEarthMixture)
                .fluidInputs(Acetone.getFluid(2000))
                .output(dust, SaturatedMonaziteDopedWithRareEarth,4)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, SaturatedMonaziteDopedWithRareEarth)
                .output(dust, SamariumEssence)
                .fluidOutputs(Acetone.getFluid(500))
                .fluidOutputs()
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, SamariumEssence)
                .output(dust,SamariumOxide)
                .output(dust,GadoliniumOxide)
                .duration(600).EUt(VA[EV]).buildAndRegister();
    }
    private static void HafniumZirconium() {
        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, HafniumOxideZirconiumOxideMixture,1)
                .output(dust, HafniumOxide,3)
                .output(dust,CubicZirconia,1)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(HydrofluoricAcid.getFluid(4000))
                .input(dust, HafniumOxide,3)
                .fluidOutputs(HafniumZirconiumTetrachloride.getFluid(4000))
                .fluidOutputs(Water.getFluid(2000))
                .duration(600).EUt(VA[EV]).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .fluidInputs(HafniumZirconiumTetrachloride.getFluid(1000))
                .input(dust,Magnesium,4)
                .output(dust, LowPurityHafniumZirconiumResidue,1)
                .output(dust,MagnesiumChloride,12)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Iodine.getFluid(5000))
                .input(dust, LowPurityHafniumZirconiumResidue,1)
                .fluidOutputs(LowPurityHafniumZirconiumIodideMixture.getFluid(5000))
                .duration(600).EUt(VA[EV]).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .fluidInputs(LowPurityHafniumZirconiumIodideMixture.getFluid(10000))
                .output(dust,Hafnium,1)
                .output(dust,Zirconium,1)
                .fluidOutputs(Iodine.getFluid(10000))
                .blastFurnaceTemp(3500)
                .duration(600).EUt(VA[EV]).buildAndRegister();
    }
    private static void Cerium() {
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,AmmoniumChloride,3)
                .fluidInputs(Hydrogen.getFluid(1000))
                .input(dust,CeriumOxide,3)
                .output(dust, CeriumChloride,4)
                .fluidOutputs(Ammonia.getFluid(3000))
                .fluidOutputs(Steam.getFluid(1000))
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, CeriumChloride,8)
                .fluidInputs(EthanedioicAcid.getFluid(5000))
                .output(dust, CeriumOxalate,5)
                .fluidOutputs(HydrochloricAcid.getFluid(6000))
                .duration(600).EUt(VA[EV]).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust, CeriumOxalate,5)
                .input(dust,Carbon,3)
                .output(dust, GTQTMaterials.CeriumOxide,5)
                .fluidOutputs(CarbonMonoxide.getFluid(3000))
                .blastFurnaceTemp(3500)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.CeriumOxide,5)
                .output(dust,Cerium,2)
                .fluidOutputs(Oxygen.getFluid(3000))
                .duration(600).EUt(VA[EV]).buildAndRegister();
    }
    private static void SodiumFluorosilicate() {
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(HydrofluoricAcid.getFluid(6000))
                .input(dust,Polysilicon,1)
                .fluidOutputs(Hydrogen.getFluid(4000))
                .fluidOutputs(HexafluorosilicicAcid.getFluid(1000))
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(HexafluorosilicicAcid.getFluid(1000))
                .input(dust,Salt,4)
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .fluidOutputs(SodiumSilicofluoride.getFluid(1000))
                .duration(600).EUt(VA[EV]).buildAndRegister();
    }

    private static void Oxalate() {
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Oxygen.getFluid(5000))
                .input(dust,Vanadium,2)
                .output(dust,VanadiumOxide)
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(NitricAcid.getFluid(1000))
                .input(dust,Sugar,24)
                .fluidOutputs(EthanedioicAcid.getFluid(3000))
                .fluidOutputs(NitricOxide.getFluid(6000))
                .notConsumable(dust,VanadiumOxide)
                .duration(600).EUt(VA[EV]).buildAndRegister();
    }
    private static void p507() {
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Ethanol.getFluid(2000))
                .notConsumable(dust, LithiumHydroxide, 1)
                .fluidOutputs(Crotonaldehyde.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Crotonaldehyde.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(Butyraldehyde.getFluid(1000))
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Butyraldehyde.getFluid(2000))
                .notConsumable(dust, SodiumHydroxide, 1)
                .fluidOutputs(Ethylhexenal.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .duration(600).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Ethylhexenal.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(Ethylhexanol.getFluid(1000))
                .duration(600).EUt(VA[EV]).buildAndRegister();
        // 5C8H18O + 0.5P4O10 -> 2C16H35O4P + 2C4H10 + 2O (lost)
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Ethylhexanol.getFluid(5000))
                .input(dust, PhosphorusPentoxide, 7)
                .fluidOutputs(DiethylhexylPhosphoricAcid.getFluid(2000))
                .fluidOutputs(Butane.getFluid(2000))
                .duration(600).EUt(VA[EV]).buildAndRegister();
    }
}
