package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.unification.OreDictUnifier;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.recipes.RecipeMaps.MIXER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.utils.GTQTUniversUtil.*;

public class RubbersChain {
    public static void init() {
        VanillaRubberProcess();
        PPFRubberProcess();
        PTMEGRubberProcess();
    }

    private static void PPFRubberProcess() {
        //  Acrylonitrile (EV): Acetylene + Hydrogen Cyanide -> Acrylonitrile
        LOW_TEMP_ACTIVATOR_RECIPES.recipeBuilder()
                .fluidInputs(Acetylene.getFluid(1000))
                .fluidInputs(HydrogenCyanide.getFluid(1000))
                .fluidOutputs(Acrylonitrile.getFluid(1000))
                .EUt(VA[HV])
                .duration(120)
                .buildAndRegister();

        //  Acrylonitrile (LuV): Propene + Ammonia + Air + Water -> Acrylonitrile + Hydrogen
        CHEMICAL_PLANT.recipeBuilder()
                .notConsumable(dust, SodiumPhosphomolybdate)
                .fluidInputs(Ammonia.getFluid(6000))
                .fluidInputs(Propene.getFluid(6000))
                .fluidInputs(Oxygen.getFluid(18000))
                .fluidOutputs(Water.getFluid(18000))
                .fluidOutputs(Acrylonitrile.getFluid(6000))
                .EUt(VA[LuV])
                .duration(120)
                .recipeLevel(6)
                .buildAndRegister();

        //  Ferric Catalyst
        MIXER_RECIPES.recipeBuilder()
                .input(dust, FerricOxide, 1)
                .fluidInputs(HydrogenPeroxide.getFluid(1000))
                .output(dust, FerricCatalyst, 2)
                .EUt(VA[MV])
                .duration(200)
                .buildAndRegister();

        //  Butadiene + Acrylonitrile -> Nitrile Butadiene Rubber
        CRYOGENIC_REACTOR_RECIPES.recipeBuilder()
                .notConsumable(dust, FerricCatalyst)
                .fluidInputs(Butadiene.getFluid(1000))
                .fluidInputs(Acrylonitrile.getFluid(1000))
                .fluidOutputs(NitrileButadieneRubber.getFluid(1000))
                .EUt(VA[LuV])
                .duration(200)
                .temperature(286)
                .buildAndRegister();

        //  Carbon + Phosphoryl Chloride + Ammonium Chloride -> Phosphonitrilic Chloride Trimer + Steam + Diluted Hydrochloric Acid
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, Carbon, 6)
                .fluidInputs(PhosphorylChloride.getFluid(3000))
                .fluidInputs(AmmoniumChloride.getFluid(3*144))
                .fluidOutputs(PhosphonitrilicChlorideTrimer.getFluid(1000))
                .fluidOutputs(Steam.getFluid(3000))
                .fluidOutputs(DilutedHydrochloricAcid.getFluid(6000))
                .EUt(VA[HV])
                .duration(140)
                .blastFurnaceTemp(2700)
                .buildAndRegister();

        //  Sodium Fluoride + Ethanol + Fluorine -> Sodium Trifluoroethanolate + Hydrogen
        CRYOGENIC_REACTOR_RECIPES.recipeBuilder()
                .input(dust, SodiumFluoride, 6)
                .fluidInputs(Ethanol.getFluid(3000))
                .fluidInputs(Fluorine.getFluid(3000))
                .output(dust, SodiumTrifluoroethanolate, 3)
                .fluidOutputs(Hydrogen.getFluid(2000))
                .temperature(344)
                .EUt(VA[EV])
                .duration(140)
                .buildAndRegister();

        //  Fluorobenzene + Hydrofluoric Acid + Oxygen -> Octafluoro Pentanol + Propene (Cycle)
        ULTRAVIOLET_LAMP_CHAMBER_RECIPES.recipeBuilder()
                .notConsumable(lens, NetherStar)
                .fluidInputs(Fluorobenzene.getFluid(2000))
                .fluidInputs(HydrofluoricAcid.getFluid(6000))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(OctafluoroPentanol.getFluid(1000))
                .fluidOutputs(Propene.getFluid(2000))
                .EUt(VA[ZPM])
                .duration(340)
                .buildAndRegister();

        // NaC2H4OF3 + Cl6N3P3 + 4C5H4F8O -> (CH2CF3)6(CH2C3F7)2(C2F4)2(NPO)4O4 + NaF (cycle) + 3POCl3 (cycle)
        POLYMERIZATION_RECIPES.recipeBuilder()
                .input(dust, SodiumTrifluoroethanolate, 11)
                .fluidInputs(PhosphonitrilicChlorideTrimer.getFluid(1000))
                .fluidInputs(OctafluoroPentanol.getFluid(4000))
                .output(dust, SodiumFluoride, 2)
                .fluidOutputs(PolyPhosphonitrileFluoroRubber.getFluid(1000))
                .fluidOutputs(PhosphorylChloride.getFluid(3000))
                .EUt(VA[UV])
                .duration(20 * SECOND)
                .buildAndRegister();

        // Na + F -> NaF
        CHEMICAL_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .input(dust, Sodium, 1)
                .fluidInputs(Fluorine.getFluid(1000))
                .output(dust, SodiumFluoride, 2)
                .EUt(VA[MV])
                .duration(5 * SECOND)
                .buildAndRegister();
    }

    private static void VanillaRubberProcess() {
        // Raw Rubber
        removeChemicalRecipe(
                OreDictUnifier.get(dust, RawRubber, 9),
                OreDictUnifier.get(dust, Sulfur, 1)
        );

        POLYMERIZATION_RECIPES.recipeBuilder()
                .input(dust, RawRubber, 9)
                .fluidInputs(Sulfur.getFluid(250))
                .fluidOutputs(Rubber.getFluid(L * 9))
                .EUt(VA[LV])
                .duration(10 * SECOND)
                .buildAndRegister();

        // Silicone Rubber
        removeChemicalRecipe(
                OreDictUnifier.get(dust, Polydimethylsiloxane, 9),
                OreDictUnifier.get(dust, Sulfur, 1));

        POLYMERIZATION_RECIPES.recipeBuilder()
                .input(dust, Polydimethylsiloxane, 9)
                .fluidInputs(Sulfur.getFluid(250))
                .fluidOutputs(SiliconeRubber.getFluid(L * 9))
                .EUt(VA[HV])
                .duration(10 * SECOND)
                .buildAndRegister();

        // Styrene Butadiene Rubber
        removeChemicalRecipe(
                OreDictUnifier.get(dust, RawStyreneButadieneRubber, 9),
                OreDictUnifier.get(dust, Sulfur, 1)
        );

        POLYMERIZATION_RECIPES.recipeBuilder()
                .input(dust, RawStyreneButadieneRubber, 9)
                .fluidInputs(Sulfur.getFluid(250))
                .fluidOutputs(StyreneButadieneRubber.getFluid(L * 9))
                .EUt(VA[HV])
                .duration(10 * SECOND)
                .buildAndRegister();
    }
    private static void PTMEGRubberProcess() {
        // C7H8 + 2HNO3 -> C7H6N2O4
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Toluene.getFluid(1000))
                .fluidInputs(NitricAcid.getFluid(2000))
                .fluidOutputs(Dinitrotoluene.getFluid(1000))
                .EUt(VA[HV])
                .duration(25 * SECOND)
                .buildAndRegister();

        // C7H6N2O4 + 4H -> C6H3(NH2)2CH3
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(Dinitrotoluene.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(4000))
                .fluidOutputs(Diaminotoluene.getFluid(1000))
                .EUt(VA[EV])
                .duration(5 * SECOND)
                .buildAndRegister();

        // C6H3(NH2)2CH3 + 2COCl2 -> CH3C6H3(NCO)2
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(Diaminotoluene.getFluid(1000))
                .fluidInputs(Phosgene.getFluid(2000))
                .fluidOutputs(TolueneDiisocyanate.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(4000))
                .blastFurnaceTemp(3600)
                .EUt(VA[HV])
                .duration(45 * SECOND)
                .buildAndRegister();

        // (CH2)4O + H2O -> (C4H8O)OH2
        POLYMERIZATION_RECIPES.recipeBuilder()
                .notConsumable(dust, SodiumBisulfate, 1) // as Initiator.
                .fluidInputs(Tetrahydrofuran.getFluid(L))
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidOutputs(Polytetrahydrofuran.getFluid(L + L / 2))
                .EUt(VA[MV])
                .duration(50 * SECOND)
                .buildAndRegister();

        // (C4H8O)OH2 + 3CH3C6H3(NCO)2 + 2H -> (CONH)2(C6H4)2CH2(C4O)
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Polytetrahydrofuran.getFluid(1000))
                .fluidInputs(TolueneDiisocyanate.getFluid(3000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(TolueneTetramethylDiisocyanate.getFluid(2000))
                .EUt(VA[IV])
                .duration(MINUTE)
                .buildAndRegister();

        // (CONH)2(C6H4)2CH2(C4O) + C4H8(OH)2 -> (CONH)2(C6H4)2CH2(C4O)HO(CH2)4OH
        POLYMERIZATION_RECIPES.recipeBuilder()
                .fluidInputs(TolueneTetramethylDiisocyanate.getFluid(4000))
                .fluidInputs(Butanediol.getFluid(1000))
                .fluidOutputs(PolytetramethyleneGlycolRubber.getFluid(4000))
                .EUt(VA[UV])
                .duration(30 * SECOND)
                .buildAndRegister();

    }
}