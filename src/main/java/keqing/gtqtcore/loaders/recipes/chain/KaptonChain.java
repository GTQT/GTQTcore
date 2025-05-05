package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.ingredients.IntCircuitIngredient;

import static gregtech.api.recipes.RecipeMaps.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;



public class KaptonChain {

    public static void init() {
        pmda();
        oda();
        bpda();
        ppd();
        kapton();
    }

    private static void pmda() {
        // C6H4(CH3)2 + 2CH3Cl -> C6H2(CH3)4 + 2HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(ParaXylene.getFluid(1000))
                .fluidInputs(Chloromethane.getFluid(2000))
                .output(dust, Durene, 24)
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .duration(120).EUt(VA[MV]).buildAndRegister();

        // C6H2(CH3)4 + 12O → C6H2(C2O3)2 + 6H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Durene, 4)
                .fluidInputs(Oxygen.getFluid(2000))
                .output(dust, PyromelliticDianhydride, 3)
                .fluidOutputs(Water.getFluid(1000))
                .duration(30).EUt(VA[HV]).buildAndRegister();
    }

    private static void oda() {
        // C6H5NO2 + 4H -> HOC6H4NH2 + H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Nitrobenzene.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(4000))
                .notConsumable(new IntCircuitIngredient(1))
                .fluidOutputs(Aminophenol.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .duration(300).EUt(VA[LV]).buildAndRegister();

        //  Tin dust + Aniline + Phenol + Hydrochloric Acid -> Oxydianiline + Methane
        CHEMICAL_PLANT.recipeBuilder()
                .notConsumable(dust, Tin)
                .fluidInputs(Aniline.getFluid(2000))
                .fluidInputs(Phenol.getFluid(1000))
                .notConsumable(HydrochloricAcid.getFluid(1))
                .output(dust, Oxydianiline, 27)
                .fluidOutputs(Methane.getFluid(2000))
                .duration(100)
                .EUt(VA[ZPM])
                .recipeLevel(4)
                .buildAndRegister();

        //  Saltpeter + Aminonphenol + Nitrochlorobenzene + Water + Dimethylformamide -> Oxydianiline + Oxygen + Hydrochloric Acid
        CHEMICAL_PLANT.recipeBuilder()
                .notConsumable(dust, Saltpeter)
                .fluidInputs(Aminophenol.getFluid(1000))
                .fluidInputs(Nitrochlorobenzene.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .notConsumable(Dimethylformamide.getFluid(1))
                .output(dust, Oxydianiline, 27)
                .fluidOutputs(Oxygen.getFluid(3000))
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .duration(200)
                .EUt(VA[HV])
                .recipeLevel(2)
                .buildAndRegister();
    }

    private static void bpda() {
        //  Phthalic Anhydride + Palladium -> Biphenyl Tetracarboxylic Acid Dianhydride + Hydrogen
        CHEMICAL_PLANT.recipeBuilder()
                .input(dust, PhthalicAnhydride, 13)
                .notConsumable(dust, Palladium)
                .output(dust, BiphenylTetracarboxylicAcidDianhydride, 28)
                .fluidOutputs(Hydrogen.getFluid(2000))
                .duration(200)
                .EUt(VA[HV])
                .recipeLevel(3)
                .buildAndRegister();
    }

    private static void ppd() {
        // ClC6H4NO2 + 2NH3 -> H2NC6H4NO2 + NH4Cl
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Nitrochlorobenzene.getFluid(1000))
                .fluidInputs(Ammonia.getFluid(2000))
                .fluidOutputs(Nitroaniline.getFluid(1000))
                .output(dust, AmmoniumChloride, 2)
                .duration(100).EUt(VA[HV]).buildAndRegister();

        // H2NC6H4NO2 + 6H -> H2NC6H4NH2 + 2H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Nitroaniline.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(6000))
                .output(dust, ParaPhenylenediamine, 16)
                .fluidOutputs(Water.getFluid(2000))
                .duration(200).EUt(VA[IV]).buildAndRegister();
    }

    private static void kapton() {
        // 2C6H2(C2O3)2 + C12H12N2O -> C22H10N2O5 + 10C + 6H + 2O (loss)
        POLYMERIZATION_RECIPES.recipeBuilder()
                .input(dust, PyromelliticDianhydride, 2)
                .input(dust, Oxydianiline, 3)
                .fluidOutputs(KaptonK.getFluid(L))
                .EUt(VA[IV])
                .duration(SECOND + 10 * TICK)
                .buildAndRegister();

        //  2C6H2(C2O3)2 + C12H12N2O + C16H6O6 + H2NC6H4NH2 -> C24H18N2O5 + 30C + 12H + 14O + 2N (loss)
        POLYMERIZATION_RECIPES.recipeBuilder()
                .input(dust, BiphenylTetracarboxylicAcidDianhydride, 2)
                .input(dust, ParaPhenylenediamine)
                .fluidInputs(KaptonK.getFluid(L))
                .fluidOutputs(KaptonE.getFluid(L))
                .EUt(VA[ZPM])
                .duration(SECOND + 10 * TICK)
                .buildAndRegister();

        //  Dimethylamine + Hydrochloric Acid -> Dimethylamine Hydrochloride
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(Dimethylamine.getFluid(1000))
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .fluidOutputs(DimethylamineHydrochloride.getFluid(1000))
                .EUt(VA[EV])
                .duration(60)
                .buildAndRegister();

        //  Dimethylformamide (EV): Potassium Formate + Dimethylamine Hydrochloride -> Rock Salt + Dimethylformamide + Hydrogen
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, PotassiumFormate, 1)
                .fluidInputs(DimethylamineHydrochloride.getFluid(1000))
                .output(dust, RockSalt, 2)
                .fluidOutputs(Dimethylformamide.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .EUt(VA[EV])
                .duration(120)
                .blastFurnaceTemp(4500)
                .buildAndRegister();

        //  Rock Salt + Methanol -> Potassium Formate + Chlorine
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, RockSalt, 2)
                .fluidInputs(Methanol.getFluid(1000))
                .output(dust, PotassiumFormate)
                .fluidOutputs(DilutedHydrochloricAcid.getFluid(1000))
                .EUt(VA[HV])
                .duration(240)
                .blastFurnaceTemp(4500)
                .buildAndRegister();
    }
}
