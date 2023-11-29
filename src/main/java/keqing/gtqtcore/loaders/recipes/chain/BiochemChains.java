package keqing.gtqtcore.loaders.recipes.chain;

import static gregicality.science.api.unification.materials.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.unification.TJMaterials.*;

public class BiochemChains {

    /* chem template
    CHEMICAL_RECIPES.recipeBuilder()
        .duration(145)
        .EUt(VA[EV])

    */
    public static void init() {
        sterilizedGrowthMedium();
    }

    private static void sterilizedGrowthMedium() {
        cyanocobalamin();
    }

    private static void cyanocobalamin() {
        H_E_ringPrecursors();
        dehydrogenationCatalyst();
        camphor();
        acetoin();
        anisidine();
    }

    private static void H_E_ringPrecursors() {

    }

    private static void dehydrogenationCatalyst() {

    }

    private static void camphor() {
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, Potassium)
                .fluidInputs(Hydrogen.getFluid(1000))
                .output(dust, PotassiumHydride, 2)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .fluidInputs(Acrylonitrile.getFluid(1000))
                .fluidInputs(Ammonia.getFluid(1000))
                .fluidOutputs(Aminopropionitrile.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .fluidInputs(Aminopropionitrile.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(4000))
                .notConsumable(dust, Nickel)
                .notConsumable(dust, Aluminium)
                .fluidOutputs(Aminopropylamine.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, PotassiumHydride, 2)
                .fluidInputs(Aminopropylamine.getFluid(1000))
                .fluidOutputs(KAPA.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(2000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, BetaPinene, 26)
                .notConsumable(KAPA.getFluid(1))
                .output(dust, AlphaPinene, 26)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, AlphaPinene, 26)
                .notConsumable(SulfuricAcid.getFluid(1))
                .output(dust, Camphene, 26)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, Camphene, 26)
                .fluidInputs(AceticAcid.getFluid(1000))
                .notConsumable(HydrochloricAcid.getFluid(1))
                .output(dust, IsobornylAcetate, 34)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, IsobornylAcetate, 34)
                .input(dust, SodiumHydroxide, 3)
                .output(dust, Isoborneol, 29)
                .fluidOutputs(SodiumAcetate.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, Isoborneol, 29)
                .notConsumable(dust, DehydrogenationCatalyst)
                .output(dust, Isoborneol, 26)
                .fluidOutputs(Hydrogen.getFluid(2000))
                .buildAndRegister();
    }

    private static void acetoin() {
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, SodiumChlorate)
                .fluidInputs(Water.getFluid(1000))
                .output(dust, SodiumPerchlorate)
                .fluidOutputs(Hydrogen.getFluid(2000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, SodiumPerchlorate, 6)
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .output(dust, Salt, 2)
                .fluidOutputs(PerchloricAcid.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, SodiumNitrate, 4)
                .fluidInputs(Aniline.getFluid(1000))
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .notConsumable(dust, SodiumSulfide)
                .output(dust, Salt, 2)
                .fluidOutputs(Phenylhydrazine.getFluid(1000))
                .fluidOutputs(Oxygen.getFluid(1000))
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .duration(265)
                .EUt(VA[LuV])
                .fluidInputs(FormicAcid.getFluid(1000))
                .fluidInputs(PerchloricAcid.getFluid(1000)) //TODO: RECIPE
                .fluidInputs(BenzoylChloride.getFluid(1000)) //TODO: RECIPE
                .fluidInputs(Aniline.getFluid(1000))
                .fluidInputs(Phenylhydrazine.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .output(dust, TriphenylMethoxytriazoylPerchlorate, 44)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, TriphenylMethoxytriazoylPerchlorate, 44)
                .input(dust, SodiumMethoxide, 6)
                .output(dust, TriphenylMethoxytriazole, 44)
                .output(dust, SodiumPerchlorate, 6)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .fluidInputs(Acetaldehyde.getFluid(1000)) //TODO: RECIPE
                .notConsumable(dust, TriphenylMethoxytriazole)
                .fluidOutputs(Acetoin.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, Sodium)
                .fluidInputs(Methanol.getFluid(1000))
                .output(dust, SodiumMethoxide, 6)
                .fluidOutputs(Hydrogen.getFluid(1000))
                .buildAndRegister();
    }

    private static void anisidine() {
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .fluidInputs(Nitrobenzene.getFluid(1000))
                .fluidInputs(Chlorine.getFluid(2000))
                .fluidOutputs(MetaNitrochlorobenzine.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, SodiumMethoxide, 6)
                .fluidInputs(MetaNitrochlorobenzine.getFluid(1000))
                .output(dust, Salt, 2)
                .fluidOutputs(Nitroanisole.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .fluidInputs(Nitroanisole.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(6000))
                .notConsumable(dust, Nickel)
                .notConsumable(dust, Aluminium)
                .fluidOutputs(Anisidine.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .buildAndRegister();
    }


}
