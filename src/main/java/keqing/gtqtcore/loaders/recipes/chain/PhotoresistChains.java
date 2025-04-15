package keqing.gtqtcore.loaders.recipes.chain;

import keqing.gtqtcore.api.unification.GTQTMaterials;

import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CHEMICAL_PLANT;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

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
                .circuitMeta(1)
                .fluidInputs(GTQTMaterials.Acetylene.getFluid(1000))
                .fluidInputs(GTQTMaterials.Formaldehyde.getFluid(2000))
                .fluidOutputs(GTQTMaterials.Butynediol.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[HV])
                .duration(85)
                .fluidInputs(GTQTMaterials.Butynediol.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(4000))
                .fluidOutputs(GTQTMaterials.Butanediol.getFluid(1000))
                .buildAndRegister();

        //Cyclopentanone

        CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[HV])
                .duration(140)
                .fluidInputs(Cyclohexane.getFluid(2000))
                .fluidInputs(Oxygen.getFluid(1500))
                .fluidOutputs(GTQTMaterials.KAOil.getFluid(2000))
                .fluidOutputs(Water.getFluid(1000))
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[IV])
                .duration(90)
                .fluidInputs(GTQTMaterials.KAOil.getFluid(2000))
                .fluidInputs(NitricAcid.getFluid(7000))
                .fluidOutputs(GTQTMaterials.NitrousAcid.getFluid(7000))
                .fluidOutputs(Water.getFluid(1000))
                .output(dust, GTQTMaterials.AdipicAcid, 2)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[MV])
                .duration(120)
                .input(dust, Barium)
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust, GTQTMaterials.BariumOxide)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[MV])
                .duration(80)
                .input(dust, GTQTMaterials.BariumOxide)
                .fluidInputs(Water.getFluid(1000))
                .output(dust, GTQTMaterials.BariumHydroxide)
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[HV])
                .duration(80)
                .input(dust, GTQTMaterials.AdipicAcid)
                .notConsumable(dust, GTQTMaterials.BariumHydroxide)
                .fluidOutputs(GTQTMaterials.Cyclopentanone.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .buildAndRegister();

        //Mixed Triarylsulfonium Hexafluoroantimonate Salts

        CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[HV])
                .duration(160)
                .fluidInputs(Benzene.getFluid(1000))
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .output(dust, GTQTMaterials.BenzenesulfonicAcid)
                .fluidOutputs(Water.getFluid(1000))
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[EV])
                .duration(120)
                .input(dust, GTQTMaterials.BenzenesulfonicAcid)
                .fluidInputs(Benzene.getFluid(2000))
                .fluidInputs(FluoroantimonicAcid.getFluid(1000))
                .fluidOutputs(GTQTMaterials.TriphenylsulfoniumHexafluoroantimonate.getFluid(1000))
                .fluidOutputs(GTQTMaterials.HypofluorousAcid.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[EV])
                .duration(210)
                .fluidInputs(GTQTMaterials.TriphenylsulfoniumHexafluoroantimonate.getFluid(3000))
                .fluidInputs(Hydrogen.getFluid(1000))
                .circuitMeta(1)
                .fluidOutputs(GTQTMaterials.HexafluoroantimonateSalt1.getFluid(1000))
                .fluidOutputs(Benzene.getFluid(3000))
                .fluidOutputs(GTQTMaterials.DiluteFluoroantimonicAcid.getFluid(1000))
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[EV])
                .duration(210)
                .fluidInputs(GTQTMaterials.TriphenylsulfoniumHexafluoroantimonate.getFluid(2000))
                .fluidInputs(Hydrogen.getFluid(1000))
                .circuitMeta(2)
                .fluidOutputs(GTQTMaterials.HexafluoroantimonateSalt2.getFluid(1000))
                .fluidOutputs(Benzene.getFluid(2000))
                .fluidOutputs(GTQTMaterials.DiluteFluoroantimonicAcid.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .EUt(VA[MV])
                .duration(85)
                .fluidInputs(GTQTMaterials.DiluteFluoroantimonicAcid.getFluid(1000))
                .fluidInputs(HydrofluoricAcid.getFluid(1000))
                .fluidOutputs(FluoroantimonicAcid.getFluid(1000))
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .EUt(VA[EV])
                .duration(135)
                .fluidInputs(GTQTMaterials.HexafluoroantimonateSalt1.getFluid(1000))
                .fluidInputs(GTQTMaterials.HexafluoroantimonateSalt2.getFluid(1000))
                .fluidOutputs(GTQTMaterials.MixedHexafluoroantimonateSalts.getFluid(2000))
                .buildAndRegister();

        //SU-8

        CHEMICAL_PLANT.recipeBuilder()
                .EUt(VA[IV])
                .duration(340)
                .recipeLevel(5)
                .fluidInputs(Epoxy.getFluid(1500))
                .fluidInputs(GTQTMaterials.MixedHexafluoroantimonateSalts.getFluid(500))
                .fluidInputs(GTQTMaterials.GammaButyrolactone.getFluid(500))
                .fluidInputs(GTQTMaterials.Cyclopentanone.getFluid(500))
                .fluidOutputs(GTQTMaterials.SU8_Photoresist.getFluid(3000))
                .buildAndRegister();

    }

    private static void HSQ() {
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(45)
                .EUt(VA[LV])
                .fluidInputs(HydrochloricAcid.getFluid(2000))
                .fluidInputs(Chloromethane.getFluid(1000))
                .input(dustSmall, Silicon,9)
                .circuitMeta(2)
                .fluidOutputs(Hydrogen.getFluid(2000))
                .fluidOutputs(GTQTMaterials.Methyltrichlorosilane.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(60)
                .EUt(VA[LV])
                .fluidInputs(Methanol.getFluid(3000))
                .fluidInputs(GTQTMaterials.Methyltrichlorosilane.getFluid(1000))
                .fluidOutputs(GTQTMaterials.Methyltrimethoxysilane.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(3000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(45)
                .EUt(VA[LV])
                .fluidInputs(GTQTMaterials.Methyltrimethoxysilane.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(GTQTMaterials.Polymethylsilesquioxane.getFluid(1000))
                .fluidOutputs(Methanol.getFluid(3000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(15)
                .EUt(VA[MV])
                .fluidInputs(GTQTMaterials.Polymethylsilesquioxane.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(1000))
                .circuitMeta(1)
                .fluidOutputs(GTQTMaterials.HydrogenSilsesquioxane.getFluid(1000))
                .buildAndRegister();

    }
}
