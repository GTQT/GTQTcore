package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.IV;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Lubricant;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.dustTiny;
import static gregtechfoodoption.GTFOMaterialHandler.Guaiacol;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CHEMICAL_PLANT;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.POLYMERIZATION_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.utils.GTQTUniversUtil.SECOND;

public class AdvancedLubricantChain {

    public static void register() {
        CreosoteDistilleryProcess();
        TricresylPhosphateProcess();
        PEGProcess();
        AdvancedLubricantProcess();
    }

    private static void CreosoteDistilleryProcess() {
        // Remove vanilla Creosote Distillation recipe.
        GTRecipeHandler.removeRecipesByInputs(DISTILLATION_RECIPES, Creosote.getFluid(24));
        GTRecipeHandler.removeRecipesByInputs(DISTILLERY_RECIPES,
                new ItemStack[]{
                        IntCircuitIngredient.getIntegratedCircuit(1)
                },
                new FluidStack[] {
                        Creosote.getFluid(24)
                });
        GTRecipeHandler.removeRecipesByInputs(DISTILLERY_RECIPES,
                new ItemStack[]{
                        IntCircuitIngredient.getIntegratedCircuit(2)
                },
                new FluidStack[] {
                        Creosote.getFluid(1)
                });
        // Creosote -> (C8H10O)(C7H8O)(C6H6O), C7H8O, C8H10O2
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(Creosote.getFluid(2000))
                .chancedOutput(dustTiny, Wood, 1, 1500, 0)
                .fluidOutputs(CreosolMixture.getFluid(1400))
                .fluidOutputs(Methoxycreosol.getFluid(350))
                .fluidOutputs(Guaiacol.getFluid(150))
                .fluidOutputs(Lubricant.getFluid(100))
                .EUt(VA[MV])
                .duration(4 * SECOND)
                .buildAndRegister();

        // (C8H10O)(C7H8O)(C6H6O) -> C8H10O + C7H8O + C6H6O
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(CreosolMixture.getFluid(1200))
                .fluidOutputs(Xylenol.getFluid(600))
                .fluidOutputs(Creosol.getFluid(400))
                .fluidOutputs(Phenol.getFluid(200))
                .EUt(VA[LV])
                .duration(2 * SECOND)
                .buildAndRegister();

    }

    private static void TricresylPhosphateProcess() {
        // 3C7H8O + POCl3 -> C21H21O4P + 3HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Creosol.getFluid(3000))
                .fluidInputs(PhosphorylChloride.getFluid(1000))
                .fluidOutputs(TricresylPhosphate.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(3000))
                .EUt(VA[EV])
                .duration(10 * SECOND)
                .buildAndRegister();
    }

    private static void PEGProcess() {
        // C2H4O (Ethylene Oxide) -> C2H4O (PEG)
        POLYMERIZATION_RECIPES.recipeBuilder()
                .fluidInputs(EthyleneOxide.getFluid(1000))
                .notConsumable(BoronTrifluoride.getFluid(1)) // as Catalyst
                .fluidOutputs(PolyethyleneGlycol.getFluid(1000))
                .EUt(VA[MV])
                .duration(10 * SECOND)
                .buildAndRegister();
    }

    private static void AdvancedLubricantProcess() {
        // MoS2 + Cr -> Cr:MoS2
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, Molybdenite)
                .fluidInputs(Chrome.getFluid(L / 2))
                .output(dust, ChromiumDopedMolybdenite, 1)
                .EUt(VA[EV])
                .duration(6 * SECOND)
                .buildAndRegister();

        // Cr:MoS2 + C + C2H6OSi + C21H21O4P + C2H4O (and Lubricant) -> Advanced Lubricant
        CHEMICAL_PLANT.recipeBuilder()
                .circuitMeta(6)
                .input(dust, ChromiumDopedMolybdenite, 1)
                .input(dust, Graphite, 1)
                .input(dust, Polydimethylsiloxane, 1)
                .fluidInputs(Lubricant.getFluid(1000))
                .fluidInputs(TricresylPhosphate.getFluid(100))
                .fluidInputs(PolyethyleneGlycol.getFluid(100))
                .fluidOutputs(AdvancedLubricant.getFluid(6000))
                .EUt(VA[IV])
                .duration(10 * SECOND)
                .buildAndRegister();
    }

}
