package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.HV;
import static gregtech.api.recipes.RecipeMaps.BLAST_RECIPES;
import static gregtech.api.recipes.RecipeMaps.WIREMILL_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.ingot;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.BURNER_REACTOR_RECIPES;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.POLYMERIZATION_RECIPES;
import static keqing.gtqtcore.api.utils.GTQTUniversUtil.*;

public class PlasticChain {

    public static void init() {
        // init (PE)
        removeChemicalRecipe(
                new ItemStack[] {
                        IntCircuitIngredient.getIntegratedCircuit(1)
                },
                new FluidStack[] {
                        Ethylene.getFluid(L),
                        Air.getFluid(1000)
                }
        );

        removeChemicalRecipe(
                new ItemStack[] {
                        IntCircuitIngredient.getIntegratedCircuit(1)
                },
                new FluidStack[] {
                        Ethylene.getFluid(L),
                        Oxygen.getFluid(1000)
                }
        );

        POLYMERIZATION_RECIPES.recipeBuilder()
                .fluidInputs(Ethylene.getFluid(L))
                .fluidInputs(Air.getFluid(1000))
                .fluidOutputs(Polyethylene.getFluid(L))
                .EUt(VA[LV])
                .duration(8 * SECOND)
                .buildAndRegister();

        POLYMERIZATION_RECIPES.recipeBuilder()
                .fluidInputs(Ethylene.getFluid(L))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(Polyethylene.getFluid(L + L / 2))
                .EUt(VA[LV])
                .duration(8 * SECOND)
                .buildAndRegister();

        // Polyvinyl Chloride (PVC)
        removeChemicalRecipe(
                new ItemStack[] {
                        IntCircuitIngredient.getIntegratedCircuit(1)
                },
                new FluidStack[] {
                        VinylChloride.getFluid(L),
                        Air.getFluid(1000)
                }
        );

        removeChemicalRecipe(
                new ItemStack[] {
                        IntCircuitIngredient.getIntegratedCircuit(1)
                },
                new FluidStack[] {
                        VinylChloride.getFluid(L),
                        Oxygen.getFluid(1000)
                }
        );

        POLYMERIZATION_RECIPES.recipeBuilder()
                .fluidInputs(VinylChloride.getFluid(L))
                .fluidInputs(Air.getFluid(1000))
                .fluidOutputs(PolyvinylChloride.getFluid(L))
                .EUt(VA[LV])
                .duration(8 * SECOND)
                .buildAndRegister();

        POLYMERIZATION_RECIPES.recipeBuilder()
                .fluidInputs(VinylChloride.getFluid(L))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(PolyvinylChloride.getFluid(L + L / 2))
                .EUt(VA[LV])
                .duration(8 * SECOND)
                .buildAndRegister();

        // PTFE
        removeChemicalRecipe(
                new ItemStack[] {
                        IntCircuitIngredient.getIntegratedCircuit(1)
                },
                new FluidStack[] {
                        Tetrafluoroethylene.getFluid(L),
                        Air.getFluid(1000)
                });

        removeChemicalRecipe(
                new ItemStack[] {
                        IntCircuitIngredient.getIntegratedCircuit(1)
                },
                new FluidStack[] {
                        Tetrafluoroethylene.getFluid(L),
                        Oxygen.getFluid(1000)
                });

        POLYMERIZATION_RECIPES.recipeBuilder()
                .fluidInputs(Tetrafluoroethylene.getFluid(L))
                .fluidInputs(Air.getFluid(1000))
                .fluidOutputs(Polytetrafluoroethylene.getFluid(144))
                .EUt(VA[HV])
                .duration(8 * SECOND)
                .buildAndRegister();

        POLYMERIZATION_RECIPES.recipeBuilder()
                .fluidInputs(Tetrafluoroethylene.getFluid(L))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(Polytetrafluoroethylene.getFluid(216))
                .EUt(VA[HV])
                .duration(8 * SECOND)
                .buildAndRegister();

        // Polycaprolactam
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES,
                new ItemStack[] {
                        OreDictUnifier.get(dust, Caprolactam, 1)
                },
                new FluidStack[] {
                        Nitrogen.getFluid(1000)
                });

        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, Caprolactam, 1)
                .fluidInputs(Nitrogen.getFluid(100))
                .output(ingot, Polycaprolactam, 1)
                .EUt(VA[MV])
                .blastFurnaceTemp(2700)
                .duration(7 * SECOND + 10 * TICK)
                .buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(WIREMILL_RECIPES,
                OreDictUnifier.get(ingot, Polycaprolactam, 1));

        WIREMILL_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .input(ingot, Polycaprolactam, 1)
                .outputs(new ItemStack(Items.STRING, 32))
                .EUt(48) // MV
                .duration(4 * SECOND)
                .buildAndRegister();

        // Polyphenylene Sulfide
        removeChemicalRecipe(
                new ItemStack[] {
                        OreDictUnifier.get(dust, SodiumSulfide, 3)
                },
                new FluidStack[] {
                        Dichlorobenzene.getFluid(1000),
                        Air.getFluid(16000)
                }
        );

        removeChemicalRecipe(
                new ItemStack[] {
                        OreDictUnifier.get(dust, SodiumSulfide, 3)
                },
                new FluidStack[] {
                        Dichlorobenzene.getFluid(1000),
                        Oxygen.getFluid(8000)
                }
        );

        // Na2S + C6H4Cl2 -> C6H4S + 2NaCl
        POLYMERIZATION_RECIPES.recipeBuilder()
                .input(dust, SodiumSulfide, 3)
                .fluidInputs(Dichlorobenzene.getFluid(1000))
                .fluidInputs(Air.getFluid(1000))
                .output(dust, Salt, 4)
                .fluidOutputs(PolyphenyleneSulfide.getFluid(1000))
                .EUt(VA[HV])
                .duration(12 * SECOND)
                .buildAndRegister();

        POLYMERIZATION_RECIPES.recipeBuilder()
                .input(dust, SodiumSulfide, 3)
                .fluidInputs(Dichlorobenzene.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust, Salt, 4)
                .fluidOutputs(PolyphenyleneSulfide.getFluid(1500))
                .EUt(VA[HV])
                .duration(12 * SECOND)
                .buildAndRegister();

        // Polybenzimidazole (PBI)
        removeChemicalRecipe(
                DiphenylIsophtalate.getFluid(1000),
                Diaminobenzidine.getFluid(1000)
        );

        // C20H14O4 + (C6H3(NH2)2)2 -> C20H12N4 + C6H6O
        POLYMERIZATION_RECIPES.recipeBuilder()
                .fluidInputs(DiphenylIsophtalate.getFluid(1000))
                .fluidInputs(Diaminobenzidine.getFluid(1000))
                .fluidOutputs(Polybenzimidazole.getFluid(1000))
                .fluidOutputs(Phenol.getFluid(1000))
                .EUt(VA[IV])
                .duration(5 * SECOND)
                .buildAndRegister();

        // Polyvinyl Butyral
        removeChemicalRecipe(
                PolyvinylAcetate.getFluid(L),
                Butyraldehyde.getFluid(250)
        );

        POLYMERIZATION_RECIPES.recipeBuilder()
                .fluidInputs(PolyvinylAcetate.getFluid(L))
                .fluidInputs(Butyraldehyde.getFluid(250))
                .fluidOutputs(PolyvinylButyral.getFluid(L))
                .EUt(VA[HV])
                .duration(20 * SECOND)
                .buildAndRegister();

    }

}
