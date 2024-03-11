package keqing.gtqtcore.loaders.recipes.chain;

import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.unification.TJMaterials.*;

public class PhotoresistChains {

    public static void init() {
        SU8();//3
        HSQ();//1
    }

    private static void SU8() {
        //fixing GCYS gamma-Butyrolactone
        CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[HV])
                .duration(60)
                .fluidInputs(Acetylene.getFluid(1000))
                .fluidInputs(Formaldehyde.getFluid(2000))
                .fluidOutputs(Butynediol.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[HV])
                .duration(85)
                .fluidInputs(Butynediol.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(4000))
                .fluidOutputs(Butanediol.getFluid(1000))
                .buildAndRegister();

        //Cyclopentanone

        CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[HV])
                .duration(140)
                .fluidInputs(Cyclohexane.getFluid(2000))
                .fluidInputs(Oxygen.getFluid(1500))
                .fluidOutputs(KAOil.getFluid(2000))
                .fluidOutputs(Water.getFluid(1000))
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[IV])
                .duration(90)
                .fluidInputs(KAOil.getFluid(2000))
                .fluidInputs(NitricAcid.getFluid(7000))
                .fluidOutputs(NitrousAcid.getFluid(7000))
                .fluidOutputs(Water.getFluid(1000))
                .output(dust, AdipicAcid, 2)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[MV])
                .duration(120)
                .input(dust, Barium)
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust, BariumOxide)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[MV])
                .duration(80)
                .input(dust, BariumOxide)
                .fluidInputs(Water.getFluid(1000))
                .output(dust, BariumHydroxide)
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[HV])
                .duration(80)
                .input(dust, AdipicAcid)
                .notConsumable(dust, BariumHydroxide)
                .fluidOutputs(Cyclopentanone.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .buildAndRegister();

        //Mixed Triarylsulfonium Hexafluoroantimonate Salts

        CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[HV])
                .duration(160)
                .fluidInputs(Benzene.getFluid(1000))
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .output(dust, BenzenesulfonicAcid)
                .fluidOutputs(Water.getFluid(1000))
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[EV])
                .duration(120)
                .input(dust, BenzenesulfonicAcid)
                .fluidInputs(Benzene.getFluid(2000))
                .fluidInputs(FluoroantimonicAcid.getFluid(1000))
                .fluidOutputs(TriphenylsulfoniumHexafluoroantimonate.getFluid(1000))
                .fluidOutputs(HypofluorousAcid.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[EV])
                .duration(210)
                .fluidInputs(TriphenylsulfoniumHexafluoroantimonate.getFluid(3000))
                .fluidInputs(Hydrogen.getFluid(1000))
                .circuitMeta(1)
                .fluidOutputs(HexafluoroantimonateSalt1.getFluid(1000))
                .fluidOutputs(Benzene.getFluid(3000))
                .fluidOutputs(DiluteFluoroantimonicAcid.getFluid(1000))
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[EV])
                .duration(210)
                .fluidInputs(TriphenylsulfoniumHexafluoroantimonate.getFluid(2000))
                .fluidInputs(Hydrogen.getFluid(1000))
                .circuitMeta(2)
                .fluidOutputs(HexafluoroantimonateSalt2.getFluid(1000))
                .fluidOutputs(Benzene.getFluid(2000))
                .fluidOutputs(DiluteFluoroantimonicAcid.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[MV])
                .duration(85)
                .fluidInputs(DiluteFluoroantimonicAcid.getFluid(1000))
                .fluidInputs(HydrofluoricAcid.getFluid(1000))
                .fluidOutputs(FluoroantimonicAcid.getFluid(1000))
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .EUt(VA[EV])
                .duration(135)
                .fluidInputs(HexafluoroantimonateSalt1.getFluid(1000))
                .fluidInputs(HexafluoroantimonateSalt2.getFluid(1000))
                .fluidOutputs(MixedHexafluoroantimonateSalts.getFluid(2000))
                .buildAndRegister();

        //SU-8

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[IV])
                .duration(340)
                .fluidInputs(Epoxy.getFluid(1500))
                .fluidInputs(MixedHexafluoroantimonateSalts.getFluid(500))
                .fluidInputs(GammaButyrolactone.getFluid(500))
                .fluidInputs(Cyclopentanone.getFluid(500))
                .fluidOutputs(SU8_Photoresist.getFluid(3000))
                .buildAndRegister();

    }

    private static void HSQ() {
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(45)
                .EUt(VA[LV])
                .fluidInputs(HydrochloricAcid.getFluid(2000))
                .fluidInputs(Chloromethane.getFluid(1000))
                .input(dust, Silicon)
                .notConsumable(dust, Copper)
                .fluidOutputs(Hydrogen.getFluid(2000))
                .fluidOutputs(Methyltrichlorosilane.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(60)
                .EUt(VA[LV])
                .fluidInputs(Methanol.getFluid(3000))
                .fluidInputs(Methyltrichlorosilane.getFluid(1000))
                .fluidOutputs(Methyltrimethoxysilane.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(3000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(45)
                .EUt(VA[LV])
                .fluidInputs(Methyltrimethoxysilane.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(Polymethylsilesquioxane.getFluid(1000))
                .fluidOutputs(Methanol.getFluid(3000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(15)
                .EUt(VA[MV])
                .fluidInputs(Polymethylsilesquioxane.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(1000))
                .circuitMeta(1)
                .fluidOutputs(HydrogenSilsesquioxane.getFluid(1000))
                .buildAndRegister();

    }
}
