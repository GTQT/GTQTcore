package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.material.Materials;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.ZincOxide;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.wrap;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class FluidizedBedChain {
    public static void init() {
        recipes();
    }

    private static void recipes() {
        GTQTcoreRecipeMaps.FLUIDIZED_BED.recipeBuilder()
                .fluidInputs(Materials.OilHeavy.getFluid(2000))
                .fluidOutputs(Materials.OilLight.getFluid(1600))
                .fluidOutputs(Materials.Ethylene.getFluid(800))
                .recipeLevel(2)
                .Catalyst(CATALYST_GAS.getStackForm())
                .EUt(180)
                .duration(120)
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLUIDIZED_BED.recipeBuilder()
                .input(ore, Materials.Oilsands)
                .fluidInputs(Materials.OilHeavy.getFluid(2000))
                .output(dust, Materials.Stone)
                .recipeLevel(2)
                .Catalyst(CATALYST_GAS.getStackForm())
                .EUt(180)
                .duration(120)
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLUIDIZED_BED.recipeBuilder()
                .fluidInputs(Materials.RawOil.getFluid(3000))
                .fluidOutputs(Materials.CarbonMonoxide.getFluid(4800))
                .fluidOutputs(Materials.Hydrogen.getFluid(4800))
                .recipeLevel(2)
                .Catalyst(CATALYST_CO.getStackForm())
                .EUt(180)
                .duration(120)
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLUIDIZED_BED.recipeBuilder()
                .fluidInputs(AtmosphericResidue.getFluid(1000))
                .fluidInputs(WaxOil.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(7000))
                .fluidOutputs(OilGas.getFluid(1000))
                .duration(600)
                .EUt(480)
                .recipeLevel(2)
                .Catalyst(CATALYST_CO.getStackForm())
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLUIDIZED_BED.recipeBuilder()
                .fluidInputs(VacuumResidue.getFluid(1000))
                .fluidInputs(WaxOil.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(7000))
                .fluidOutputs(OilGas.getFluid(3000))
                .duration(600)
                .EUt(480)
                .recipeLevel(2)
                .Catalyst(CATALYST_CO.getStackForm())
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLUIDIZED_BED.recipeBuilder()
                .fluidInputs(Materials.CarbonMonoxide.getFluid(4000))
                .fluidInputs(Materials.Hydrogen.getFluid(4000))
                .circuitMeta(1)
                .fluidOutputs(DieselLight.getFluid(1200))
                .fluidOutputs(Lubricant.getFluid(400))
                .recipeLevel(3)
                .Catalyst(CATALYST_CU.getStackForm())
                .EUt(180)
                .duration(120)
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLUIDIZED_BED.recipeBuilder()
                .fluidInputs(WaxOil.getFluid(2000))
                .circuitMeta(1)
                .fluidOutputs(DieselLight.getFluid(1200))
                .fluidOutputs(Lubricant.getFluid(800))
                .recipeLevel(2)
                .Catalyst(CATALYST_CU.getStackForm())
                .EUt(180)
                .duration(120)
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLUIDIZED_BED.recipeBuilder()
                .fluidInputs(CoalTar.getFluid(2000))
                .circuitMeta(1)
                .fluidOutputs(DieselLight.getFluid(1200))
                .fluidOutputs(Lubricant.getFluid(800))
                .recipeLevel(2)
                .Catalyst(CATALYST_CU.getStackForm())
                .EUt(180)
                .duration(120)
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLUIDIZED_BED.recipeBuilder()
                .fluidInputs(Materials.CarbonMonoxide.getFluid(1000))
                .fluidInputs(Materials.Hydrogen.getFluid(2000))
                .circuitMeta(2)
                .fluidOutputs(Methanol.getFluid(1000))
                .recipeLevel(3)
                .Catalyst(CATALYST_ZN.getStackForm())
                .EUt(180)
                .duration(120)
                .buildAndRegister();


        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{IntCircuitIngredient.getIntegratedCircuit(1)}, new FluidStack[]{Nitrogen.getFluid(1000), Hydrogen.getFluid(3000)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{IntCircuitIngredient.getIntegratedCircuit(1)}, new FluidStack[]{Nitrogen.getFluid(1000), Hydrogen.getFluid(3000)});

        GTQTcoreRecipeMaps.FLUIDIZED_BED.recipeBuilder()
                .fluidInputs(Materials.Nitrogen.getFluid(1000))
                .fluidInputs(Materials.Hydrogen.getFluid(3000))
                .fluidOutputs(Ammonia.getFluid(1000))
                .recipeLevel(3)
                .Catalyst(CATALYST_CR.getStackForm())
                .EUt(180)
                .duration(120)
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLUIDIZED_BED.recipeBuilder()
                .fluidInputs(Materials.Ethane.getFluid(1000))
                .fluidOutputs(Propene.getFluid(900))
                .fluidOutputs(Materials.Hydrogen.getFluid(300))
                .recipeLevel(3)
                .Catalyst(CATALYST_NB.getStackForm())
                .EUt(180)
                .duration(120)
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLUIDIZED_BED.recipeBuilder()
                .fluidInputs(Materials.Ethylene.getFluid(2000))
                .fluidOutputs(Polyethylene.getFluid(2000))
                .recipeLevel(3)
                .Catalyst(CATALYST_ZR.getStackForm())
                .EUt(180)
                .duration(120)
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLUIDIZED_BED.recipeBuilder()
                .fluidInputs(Biomass.getFluid(2000))
                .fluidOutputs(Methane.getFluid(1200))
                .fluidOutputs(CarbonDioxide.getFluid(400))
                .recipeLevel(4)
                .Catalyst(CATALYST_RH.getStackForm())
                .EUt(180)
                .duration(120)
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLUIDIZED_BED.recipeBuilder()
                .fluidInputs(BioOil.getFluid(2000))
                .fluidOutputs(BioDiesel.getFluid(1200))
                .recipeLevel(4)
                .Catalyst(CATALYST_RH.getStackForm())
                .EUt(180)
                .duration(120)
                .buildAndRegister();
    }


}
