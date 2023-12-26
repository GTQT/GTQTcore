package keqing.gtqtcore.loaders.recipes.chain;

import static gregicality.science.api.unification.materials.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.unification.TJMaterials.*;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;

import static gregicality.science.api.recipes.GCYSRecipeMaps.*;
import static gregicality.science.api.unification.materials.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.blocks.BlockWireCoil.CoilType.CUPRONICKEL;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.CRACKER;
import static gregtech.common.metatileentities.MetaTileEntities.DISTILLATION_TOWER;


public class PetrochemRecipes {

    public static void init() {
        removeOvercostRecipes();
        addLowCostRecipes();
        renewablePropene();
        catalysts();
    }

    public static void removeOvercostRecipes() {
        //TODO cannot remove this GCYS recipe
//        GTRecipeHandler.removeRecipesByInputs(DISTILLATION_RECIPES, RareEarthChloridesSolution.getFluid(1000));
        GTRecipeHandler.removeRecipesByInputs(DISTILLATION_RECIPES, CharcoalByproducts.getFluid(1000));
        GTRecipeHandler.removeRecipesByInputs(DISTILLATION_RECIPES, WoodGas.getFluid(1000));
        GTRecipeHandler.removeRecipesByInputs(DISTILLATION_RECIPES, FermentedBiomass.getFluid(1000));
        GTRecipeHandler.removeRecipesByInputs(DISTILLATION_RECIPES, WoodVinegar.getFluid(1000));
        GTRecipeHandler.removeRecipesByInputs(DISTILLATION_RECIPES, WoodTar.getFluid(1000));
    }

    public static void addLowCostRecipes() {
        DISTILLATION_RECIPES.recipeBuilder()
                .duration(150)
                .EUt(VA[EV])
                .fluidInputs(RareEarthChloridesSolution.getFluid(1000))
                .fluidOutputs(ErTmYbLuOxidesSolution.getFluid(250))
                .fluidOutputs(LaPrNdCeOxidesSolution.getFluid(250))
                .fluidOutputs(ScEuGdSmOxidesSolution.getFluid(250))
                .fluidOutputs(YTbDyHoOxidesSolution.getFluid(250))
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .chancedOutput(dust, Thorium, 2500, 1000)
                .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .duration(175)
                .EUt(VA[MV])
                .fluidInputs(CharcoalByproducts.getFluid(1000))
                .fluidOutputs(WoodVinegar.getFluid(450))
                .fluidOutputs(WoodTar.getFluid(250))
                .fluidOutputs(WoodGas.getFluid(250))
                .fluidOutputs(Dimethylbenzene.getFluid(100))
                .chancedOutput(dust, Charcoal, 5000, 1000)
                .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .duration(125)
                .EUt(VA[MV])
                .fluidInputs(WoodTar.getFluid(1000))
                .fluidOutputs(Benzene.getFluid(350))
                .fluidOutputs(Creosote.getFluid(300))
                .fluidOutputs(Dimethylbenzene.getFluid(200))
                .fluidOutputs(Phenol.getFluid(75))
                .fluidOutputs(Toluene.getFluid(75))
                .chancedOutput(STICKY_RESIN.getStackForm(), 5000, 1000)
                .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .duration(150)
                .EUt(VA[MV])
                .fluidInputs(WoodVinegar.getFluid(1000))
                .fluidOutputs(Water.getFluid(400))
                .fluidOutputs(Methanol.getFluid(300))
                .fluidOutputs(AceticAcid.getFluid(200))
                .fluidOutputs(Acetone.getFluid(50))
                .fluidOutputs(MethylAcetate.getFluid(10))
                .fluidOutputs(Ethanol.getFluid(10))
                .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .duration(150)
                .EUt(VA[MV])
                .fluidInputs(WoodGas.getFluid(1000))
                .fluidOutputs(CarbonDioxide.getFluid(500))
                .fluidOutputs(CarbonMonoxide.getFluid(350))
                .fluidOutputs(Methane.getFluid(150))
                .fluidOutputs(Hydrogen.getFluid(50))
                .fluidOutputs(Ethylene.getFluid(50))
                .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(VA[MV])
                .fluidInputs(FermentedBiomass.getFluid(1000))
                .fluidOutputs(Water.getFluid(500))
                .fluidOutputs(Methane.getFluid(400))
                .fluidOutputs(CarbonDioxide.getFluid(350))
                .fluidOutputs(Ethanol.getFluid(200))
                .fluidOutputs(Methanol.getFluid(150))
                .fluidOutputs(Ammonia.getFluid(100))
                .fluidOutputs(AceticAcid.getFluid(50))
                .output(FERTILIZER)
                .buildAndRegister();

    }

    public static void catalysts() {
        CVD_RECIPES.recipeBuilder()
                .duration(550)
                .EUt(VA[EV])
                .input(dust, CubicZirconia)
                .input(dust, Iridium)
                .output(dust, IridiumOnCubicZirconia)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(VA[MV])
                .input(dust, SodiumHydroxide)
                .input(dust, SiliconDioxide)
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidOutputs(SodiumHydroxideSilica.getFluid(1000))
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .duration(85)
                .EUt(VA[MV])
                .input(dust, SodiumHydroxide, 2)
                .input(dust, Alumina)
                .notConsumable(plate, Nickel)
                .output(dust, SodiumAluminate, 2)
                .fluidOutputs(Water.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(70)
                .EUt(VA[MV])
                .input(dust, SodiumAluminate)
                .fluidInputs(SodiumHydroxideSilica.getFluid(1000))
                .fluidOutputs(SodiumAluminumSilicaSolution.getFluid(1000))
                .fluidOutputs(Oxygen.getFluid(2000))
                .output(dust, SodiumHydroxide)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(55)
                .EUt(VA[MV])
                .fluidInputs(CarbonMonoxide.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidInputs(Methanol.getFluid(2000))
                .fluidOutputs(DimethylCarbonate.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(80)
                .EUt(VA[HV])
                .fluidInputs(Ethylene.getFluid(1000))
                .fluidInputs(DimethylCarbonate.getFluid(2000))
                .input(dust, AmmoniumChloride, 2)
                .output(dust, TetramethylammoniumChloride, 2)
                .fluidOutputs(Oxygen.getFluid(6000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[HV])
                .fluidInputs(TetramethylammoniumHydroxide.getFluid(1000))
                .fluidInputs(HydrobromicAcid.getFluid(1000))
                .output(dust, TetramethylammoniumBromide)
                .fluidOutputs(Water.getFluid(1000))
                .buildAndRegister();

        CRYSTALLIZER_RECIPES.recipeBuilder()
                .duration(120)
                .EUt(VA[EV])
                .fluidInputs(SodiumAluminumSilicaSolution.getFluid(1000))
                .notConsumable(dust, TetramethylammoniumBromide)
                .output(dust, ZSM_5_ZEOLITE)
                .buildAndRegister();
    }

    public static void renewablePropene() {
        BLAST_RECIPES.recipeBuilder()
                .duration(185)
                .EUt(VA[HV])
                .notConsumable(dust, IridiumOnCubicZirconia)
                .fluidInputs(Glycerol.getFluid(1000))
                .fluidOutputs(OnePropanol.getFluid(1000))
                .buildAndRegister();

        DRYER_RECIPES.recipeBuilder()
                .duration(120)
                .EUt(VA[HV])
                .notConsumable(dust, ZSM_5_ZEOLITE)
                .fluidInputs(OnePropanol.getFluid(1000))
                .fluidOutputs(Propene.getFluid(1000))
                .buildAndRegister();
    }

}
