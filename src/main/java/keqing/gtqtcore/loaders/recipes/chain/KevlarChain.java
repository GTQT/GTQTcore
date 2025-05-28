package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;

import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtechfoodoption.GTFOMaterialHandler.Acetaldehyde;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.LARGE_MIXER_RECIPES;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.SPINNER_RECIPES;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.wrap;

public class KevlarChain {

    public static void init() {
        Kevlar();
        PolyurethaneResinProcess();
    }

    private static void Kevlar() {
        // C6H4(CH3)2 + 6O -> C6H4(CO2H)2 + 2H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(ParaXylene.getFluid(1000))
                .fluidInputs(Chlorine.getFluid(6000))
                .fluidOutputs(Bistrichloromethylbenzene.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(6000))
                .duration(200)
                .EUt(VA[EV])
                .buildAndRegister();

        // Amoco Process for Terephthalic Acid

        // 4Br + C2H2 -> C2H2Br4
        VACUUM_RECIPES.recipeBuilder()
                .fluidInputs(Bromine.getFluid(4000))
                .fluidInputs(Acetylene.getFluid(1000))
                .fluidOutputs(Tetrabromoethane.getFluid(1000))
                .duration(80)
                .EUt(VA[LV])
                .buildAndRegister();

        // C6H4(CH3)2 + 6O -> C6H4(CO2H)2 + 2H2O
        LARGE_CHEMICAL_RECIPES.recipeBuilder() // TODO corrosion proof reactor
                .notConsumable(foil, Titanium, 10)
                .notConsumable(dust, Manganese)
                .notConsumable(dust, Cobalt)
                .notConsumable(Acetone.getFluid())
                .fluidInputs(ParaXylene.getFluid(1000))
                .fluidInputs(Tetrabromoethane.getFluid(50))
                .fluidInputs(Air.getFluid(12000)) // TODO compressed air
                .output(dust, TerephthalicAcid, 3)
                .fluidOutputs(Water.getFluid(2000))
                .duration(240)
                .EUt(VA[LuV])
                .buildAndRegister();

        // C6H4(CCl3)2 + C6H4(CO2H)2 -> 2 C6H4(COCl)2 + 2 HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, TerephthalicAcid, 3)
                .fluidInputs(Bistrichloromethylbenzene.getFluid(1000))
                .output(dust, TerephthaloylChloride, 6)
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .duration(100)
                .EUt(VA[HV])
                .buildAndRegister();

        // C4H8 + HClO + H2O -> C4H10O2 + HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Butene.getFluid(1000))
                .fluidInputs(HypochlorousAcid.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(Butanediol.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .duration(60)
                .EUt(VA[IV])
                .buildAndRegister();

        // C4H10O2 -> C4H6O2 + 4H (4H lost)
        BREWING_RECIPES.recipeBuilder()
                .input(dust, Copper)
                .fluidInputs(Butanediol.getFluid(1000))
                .fluidOutputs(GammaButyrolactone.getFluid(1000))
                .duration(120)
                .EUt(VA[EV])
                .buildAndRegister();

        // CH3NH2 + C4H6O2 -> C5H9NO + H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Methylamine.getFluid(1000))
                .fluidInputs(GammaButyrolactone.getFluid(1000))
                .fluidOutputs(NMethylPyrrolidone.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .duration(240)
                .EUt(VA[IV])
                .buildAndRegister();

        // Ca + 2Cl -> CaCl2
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Calcium)
                .fluidInputs(Chlorine.getFluid(2000))
                .output(dust, CalciumChloride, 3)
                .duration(80).EUt(VA[LV]).buildAndRegister();

        // C6H4(NH2)2 + C6H4(COCl)2 -> [-CO-C6H4-CO-NH-C6H4-NH-]n + 2HCl
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, CalciumChloride) // as Catalyst
                .input(dust, ParaPhenylenediamine, 8) // Output from Kapton Chain.
                .input(dust, TerephthaloylChloride, 3)
                .fluidInputs(NMethylPyrrolidone.getFluid(100))
                .fluidInputs(SulfuricAcid.getFluid(500))
                .fluidOutputs(CrystalKevlar.getFluid(4000))
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .EUt(VA[IV])
                .duration(20 * SECOND)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(CrystalKevlar.getFluid(1000))
                .fluidInputs(PolyurethaneResin.getFluid(1000))
                .fluidOutputs(Kevlar.getFluid(1440))
                .EUt(VA[ZPM])
                .duration(100)
                .buildAndRegister();
    }
    private static void PolyurethaneResinProcess() {

        // Subchain of Polyurethane Catalyst A.

        // Ni + 4CO -> C4NiO4
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Nickel, 1)
                .fluidInputs(CarbonMonoxide.getFluid(4000))
                .fluidOutputs(NickelTetracarbonyl.getFluid(1000))
                .EUt(VA[EV])
                .duration(20 * SECOND)
                .buildAndRegister();

        // C2H4 + CO + H2O -> CH3CH2COOH
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Ethylene.getFluid(1000))
                .fluidInputs(CarbonMonoxide.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .fluidInputs(NickelTetracarbonyl.getFluid(100)) // as Catalyst
                .fluidOutputs(PropionicAcid.getFluid(1000))
                .EUt(VA[EV])
                .duration(10 * SECOND)
                .buildAndRegister();

        // Sn + 2C4H9OH + CH3CH2COOH -> (C4H9OH)2(CH3CH2COOH)Sn
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Tin)
                .fluidInputs(Butanol.getFluid(2000))
                .fluidInputs(PropionicAcid.getFluid(1000))
                .fluidInputs(Iron3Chloride.getFluid(100)) // as Catalyst
                .output(dust, PolyurethaneCatalystA, 1)
                .EUt(VA[IV])
                .duration(5 * SECOND)
                .buildAndRegister();

        // Subchain of 4,4'-Diphenylmethane Diisocyanate.

        // CO + 2Cl -> COCl2
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(CarbonMonoxide.getFluid(1000))
                .fluidInputs(Chlorine.getFluid(2000))
                .fluidOutputs(Phosgene.getFluid(1000))
                .EUt(VA[HV])
                .duration(2 * SECOND + 10 * TICK)
                .buildAndRegister();

        // Two choice of C13H14N2, used H2NC6H4NO2 or C6H5NH2.

        // CH2O + 2C6H5NH2 + HCl -> C13H14N2
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Formaldehyde.getFluid(1000))
                .fluidInputs(Aniline.getFluid(2000)) // Output from Kapton Chain.
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .fluidOutputs(DiaminodiphenylmethaneMixture.getFluid(1000))
                .EUt(VA[EV])
                .duration(MINUTE)
                .buildAndRegister();

        // CH2O + 2H2NC6H4NO2 + HCl -> C13H14N2 + N2O4 + O (drop)
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Formaldehyde.getFluid(1000))
                .fluidInputs(Nitroaniline.getFluid(2000)) // Output from Kapton Chain.
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .fluidOutputs(DiaminodiphenylmethaneMixture.getFluid(1000))
                .fluidOutputs(DinitrogenTetroxide.getFluid(1000))
                .EUt(VA[EV])
                .duration(30 * SECOND)
                .buildAndRegister();

        // C13H14N2 + 2COCl2 -> C15H10N2O2?
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(DiaminodiphenylmethaneMixture.getFluid(1000))
                .fluidInputs(Phosgene.getFluid(2000))
                .fluidOutputs(DiphenylmethaneDiisocyanateMixture.getFluid(1000))
                .EUt(VA[IV])
                .duration(10 * SECOND)
                .buildAndRegister();

        // C15H10N2O2? -> C15H10N2O2 + 5HCl
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(DiphenylmethaneDiisocyanateMixture.getFluid(1000))
                .output(dust, DiphenylmethaneDiisocyanate, 29)
                .fluidOutputs(HydrochloricAcid.getFluid(5000))
                .EUt(VA[LuV])
                .duration(15 * SECOND)
                .disableDistilleryRecipes()
                .buildAndRegister();

        // Processing Polyurethane.

        // C2H4O + 4C2H6Cl2Si + 5H2O -> (C2H4O)(C2H6Cl2Si)4(H2O)5
        LARGE_MIXER_RECIPES.recipeBuilder()
                .circuitMeta(3)
                .fluidInputs(EthyleneOxide.getFluid(1000))
                .fluidInputs(Dimethyldichlorosilane.getFluid(4000))
                .fluidInputs(Water.getFluid(5000))
                .fluidOutputs(SiliconOil.getFluid(5000))
                .EUt(VA[EV])
                .duration(15 * TICK)
                .buildAndRegister();

        LARGE_MIXER_RECIPES.recipeBuilder()
                .circuitMeta(3)
                .fluidInputs(EthyleneOxide.getFluid(1000))
                .fluidInputs(Dimethyldichlorosilane.getFluid(4000))
                .fluidInputs(DistilledWater.getFluid(5000))
                .fluidOutputs(SiliconOil.getFluid(5000))
                .EUt(VA[EV])
                .duration(15 * TICK)
                .buildAndRegister();

        // Hg + 2Br -> HgBr2
        GTQTcoreRecipeMaps.DRYER_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .fluidInputs(Mercury.getFluid(1000))
                .fluidInputs(Bromine.getFluid(2000))
                .output(dust, MercuryDibromide, 3)
                .EUt(VA[LV])
                .duration(5 * SECOND)
                .buildAndRegister();

        // C2H2 + H2O -> CH3CHO
        // The first recipe of CH3CHO, the original method to get CH3CHO at 1774 (Scheele).
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust, MercuryDibromide, 1)
                .fluidInputs(Acetylene.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(Acetaldehyde.getFluid(1000))
                .EUt(VA[MV])
                .duration(5 * SECOND)
                .buildAndRegister();

        // C2H4 + O -> CH3CHO
        // Advanced recipe of CH3CHO, required Cu/PdCl2 catalyst, and you can use C2H4 to get CH3CHO.
        // CuCl2 Catalyst Reaction is from EDTAChain.
        CHEMICAL_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .notConsumable(dust, CopperChloride, 1) // CuCl2 Catalyst
                .fluidInputs(Ethylene.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(Acetaldehyde.getFluid(1000))
                .EUt(VA[MV])
                .duration(5 * SECOND)
                .buildAndRegister();

        // C2H4 + H2O -> CH3CHO + 2H
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust, PalladiumRaw, 1) // PdCl2 Catalyst
                .fluidInputs(Ethylene.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(Acetaldehyde.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(2000))
                .EUt(VA[HV])
                .duration(5 * SECOND)
                .buildAndRegister();

        // 2Ag + O -> Ag2O
        GTQTcoreRecipeMaps.BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, Silver, 2)
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust, SilverOxide, 3)
                .EUt(VA[LV])
                .duration(5 * SECOND)
                .blastFurnaceTemp(1800)
                .buildAndRegister();

        // CH3CH2OH -> 2O -> CH3CHO + 2H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust, SilverOxide, 1) // Ag2O Catalyst.
                .fluidInputs(Ethanol.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(2000))
                .fluidOutputs(Acetaldehyde.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .EUt(VA[HV])
                .duration(10 * SECOND)
                .buildAndRegister();

        // NaOH + 4CH2O + CH3CHO -> C5H12O4 + CO
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SodiumHydroxide, 1) // as Catalyst
                .fluidInputs(Formaldehyde.getFluid(4000))
                .fluidInputs(Acetaldehyde.getFluid(1000))
                .output(dust, Pentaerythritol, 21)
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .EUt(VA[HV])
                .duration(10 * SECOND)
                .buildAndRegister();

        // C5H12O4 + C15H10N2O2 + C2H6O2 -> C17H16N2O4
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, PolyurethaneCatalystA, 1) // as Catalyst
                .input(dust, DiphenylmethaneDiisocyanate, 5)
                .input(dust, Pentaerythritol, 1)
                .fluidInputs(EthyleneGlycol.getFluid(4000))
                .fluidInputs(SiliconOil.getFluid(1000))
                .fluidOutputs(PolyurethaneResin.getFluid(1000))
                .EUt(VA[IV])
                .duration(10 * SECOND)
                .buildAndRegister();
    }
}
