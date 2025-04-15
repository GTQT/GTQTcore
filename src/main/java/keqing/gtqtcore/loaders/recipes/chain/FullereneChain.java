package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import keqing.gtqtcore.api.unification.GTQTMaterials;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.SHAPE_EXTRUDER_INGOT;

public class FullereneChain {

    public static void init() {
        truxene();
        naphthalene();
        butyllitium();
        palladiumAcetate();
        fullerene();
    }

    private static void truxene() {
        BREWING_RECIPES.recipeBuilder()
                .input(dust, Sodium)
                .fluidInputs(CoalTar.getFluid(1000))
                .fluidOutputs(GTQTMaterials.SodioIndene.getFluid(1000))
                .duration(100).EUt(VA[IV]).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.SodioIndene.getFluid(1000))
                .fluidInputs(Steam.getFluid(1000)) //TODO Superheated steam?
                .notConsumable(new IntCircuitIngredient(1))
                .fluidOutputs(GTQTMaterials.SteamCrackedSodioIndene.getFluid(1000))
                .duration(160).EUt(VA[IV]).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.SteamCrackedSodioIndene.getFluid(1000))
                .output(dust, Sodium)
                .fluidOutputs(GTQTMaterials.Indene.getFluid(1000))
                .duration(240).EUt(VA[IV]).buildAndRegister();

        // C9H8 + O -> C9H8O
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.Indene.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust, GTQTMaterials.Indanone, 3)
                .duration(80).EUt(VA[IV]).buildAndRegister();

        // 3C9H8O -> C27H18 + 3H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.Indanone, 9)
                .notConsumable(HydrochloricAcid.getFluid())
                .notConsumable(AceticAcid.getFluid())
                .fluidOutputs(GTQTMaterials.Truxene.getFluid(1000))
                .fluidOutputs(Water.getFluid(3000))
                .duration(480).EUt(VA[EV]).buildAndRegister();
    }

    private static void naphthalene() {
        // CH4 + 2Br -> CH3Br + HBr
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Methane.getFluid(1000))
                .fluidInputs(Bromine.getFluid(2000))
                .fluidOutputs(GTQTMaterials.Bromomethane.getFluid(1000))
                .fluidOutputs(GTQTMaterials.HydrobromicAcid.getFluid(1000))
                .duration(80).EUt(VA[LV]).buildAndRegister();

        // CH3OH + HBr -> CH3Br + H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Methanol.getFluid(1000))
                .fluidInputs(GTQTMaterials.HydrobromicAcid.getFluid(1000))
                .fluidOutputs(GTQTMaterials.Bromomethane.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .duration(160).EUt(VA[LV]).buildAndRegister();

        // C10H8 + 2CH3Br -> C11H8Br2 + CH4 + 2H (H lost)
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Naphthalene.getFluid(1000))
                .fluidInputs(GTQTMaterials.Bromomethane.getFluid(2000))
                .fluidOutputs(GTQTMaterials.BromoBromomethylNaphthalene.getFluid(1000))
                .fluidOutputs(Methane.getFluid(1000))
                .duration(320).EUt(VA[IV]).buildAndRegister();
    }

    private static void butyllitium() {
        // C4H8O + 2H -> C4H10O
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Butyraldehyde.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(GTQTMaterials.Butanol.getFluid(1000))
                .duration(100).EUt(VA[HV]).buildAndRegister();

        // Anti-Markovnikov Reaction

        // C4H10O + HBr -> C4H9Br + H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.Butanol.getFluid(1000))
                .fluidInputs(GTQTMaterials.HydrobromicAcid.getFluid(1000))
                .fluidOutputs(GTQTMaterials.Bromobutane.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .duration(200).EUt(VA[EV]).buildAndRegister();

        // Li + C4H9Br -> C4H9Li + Br
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Lithium)
                .notConsumable(dust, Sodium)
                .fluidInputs(GTQTMaterials.Bromobutane.getFluid(1000))
                .fluidOutputs(GTQTMaterials.Butyllithium.getFluid(1000))
                .fluidOutputs(Bromine.getFluid(1000))
                .duration(200).EUt(VA[EV]).buildAndRegister();
    }

    private static void palladiumAcetate() {
        // Pd + 2HNO3 -> Pd(NO3)2 + 2H
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Palladium)
                .fluidInputs(NitricAcid.getFluid(2000))
                .output(dust, GTQTMaterials.PalladiumNitrate, 9)
                .fluidOutputs(Hydrogen.getFluid(2000))
                .duration(200).EUt(VA[MV]).buildAndRegister();

        // Pd(NO3)2 + 2CH3COOH -> Pd(CH3COOH)2 + 2HNO3
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.PalladiumNitrate, 9)
                .fluidInputs(AceticAcid.getFluid(2000))
                .output(dust, GTQTMaterials.PalladiumAcetate, 5)
                .fluidOutputs(NitricAcid.getFluid(2000))
                .duration(50).EUt(VA[EV]).buildAndRegister();
    }


    private static void fullerene() {

        /*
         * The full version of this reaction uses MANY more catalysts.
         * For those interested, here's the full catalyst list:
         * - n-Butyllithium
         * - Palladium (II) Acetate
         * - Tert-Butyl Alcohol
         * - Potassium Tert-Butoxide
         * - Caesium Carbonate
         * - Dimethylacetamide
         * - Benzyltrimethylammonium Bromide
         */

        // C27H18 + 3C11H8Br2 -> C60H30 + 2HBr + 10H
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.PalladiumAcetate)
                .fluidInputs(GTQTMaterials.Truxene.getFluid(1000))
                .fluidInputs(GTQTMaterials.BromoBromomethylNaphthalene.getFluid(3000))
                .fluidInputs(GTQTMaterials.Butyllithium.getFluid(10))
                .output(dust, GTQTMaterials.GeodesicPolyarene, 60)
                .fluidOutputs(GTQTMaterials.HydrobromicAcid.getFluid(2000))
                .fluidOutputs(Hydrogen.getFluid(10000))
                .duration(600).EUt(VA[UHV]).buildAndRegister();

        // C60H30 -> C60 + 30H
        PYROLYSE_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.GeodesicPolyarene)
                .input(foil, Platinum)
                .output(dust, GTQTMaterials.Fullerene)
                .fluidOutputs(Hydrogen.getFluid(500))
                .duration(10).EUt(VA[UHV]).buildAndRegister();

        EXTRUDER_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.Fullerene)
                .notConsumable(SHAPE_EXTRUDER_INGOT)
                .output(ingot, GTQTMaterials.Fullerene)
                .duration(400).EUt(240).buildAndRegister();
    }
}
