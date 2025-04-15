package keqing.gtqtcore.loaders.recipes.chain;

import keqing.gtqtcore.api.unification.GTQTMaterials;

import static gregtechfoodoption.GTFOMaterialHandler.PerchloricAcid;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.PLANT_BALL;
import static gregtech.common.items.MetaItems.STICKY_RESIN;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.PR_MIX;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Latex;

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
        latex();
        H_E_ringPrecursors();
        dehydrogenationCatalyst();
        camphor();
        acetoin();
        Aniline();
    }

    private static void latex()
    {
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(20)
                .EUt(30)
                .input(STICKY_RESIN,2)
                .input(dust,Salt,1)
                .fluidInputs(Water.getFluid(800))
                .fluidOutputs(Latex.getFluid(400))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(20)
                .EUt(30)
                .input(STICKY_RESIN,2)
                .input(dust,RockSalt,1)
                .fluidInputs(Water.getFluid(800))
                .fluidOutputs(Latex.getFluid(400))
                .buildAndRegister();

        //
        //
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(200)
                .EUt(120)
                .input(dust,Salt,1)
                .fluidInputs(Ammonia.getFluid(1000))
                .fluidInputs(CarbonDioxide.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(AmmoniumChloride.getFluid(144))
                .output(dust,SodiumBicarbonate,1)
                .buildAndRegister();

        //原始混凝土
        PR_MIX.recipeBuilder()
                .input(dust, Calcite,2)
                .input(dust, Clay)
                .input(dust, Stone, 1)
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(Concrete.getFluid(1000))
                .duration(400).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .duration(1000)
                .EUt(30)
                .input(dust,Salt,40)
                .fluidInputs(Ammonia.getFluid(40000))
                .fluidInputs(CarbonDioxide.getFluid(40000))
                .fluidInputs(Water.getFluid(40000))
                .fluidOutputs(AmmoniumChloride.getFluid(40*144))
                .output(dust,SodiumBicarbonate,40)
                .circuitMeta(3)
                .buildAndRegister();

        PR_MIX.recipeBuilder()
                .duration(1200)
                .input(STICKY_RESIN,20)
                .input(dust,Salt,10)
                .fluidInputs(Water.getFluid(4000))
                .fluidOutputs(Latex.getFluid(2000))
                .buildAndRegister();

        PR_MIX.recipeBuilder()
                .duration(1200)
                .input(STICKY_RESIN,20)
                .input(dust,RockSalt,10)
                .fluidInputs(Water.getFluid(4000))
                .fluidOutputs(Latex.getFluid(2000))
                .buildAndRegister();


        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Mercury.getFluid(1000))
                .fluidInputs(Water.getFluid(10000))
                .fluidInputs(Chlorine.getFluid(10000))
                .fluidOutputs(HypochlorousAcid.getFluid(10000))
                .duration(4800).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder().duration(400).EUt(5)
                .fluidInputs(Latex.getFluid(400))
                .input(dust,Trona)
                .output(dust, RawRubber, 4)
                .chancedOutput(PLANT_BALL, 2000, 400)
                .fluidOutputs(Glue.getFluid(200))
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder().duration(200).EUt(5)
                .fluidInputs(Latex.getFluid(200))
                .input(dust,SodaAsh)
                .output(dust, RawRubber, 4)
                .chancedOutput(PLANT_BALL, 2000, 400)
                .fluidOutputs(Glue.getFluid(200))
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder().duration(200).EUt(120)
                .input(STICKY_RESIN)
                .output(dust, RawRubber, 3)
                .chancedOutput(PLANT_BALL, 1000, 850)
                .fluidOutputs(Glue.getFluid(100))
                .buildAndRegister();


        PR_MIX.recipeBuilder().duration(200)
                .fluidInputs(Latex.getFluid(200))
                .input(dust,Trona)
                .output(dust, RawRubber, 1)
                .fluidOutputs(Glue.getFluid(200))
                .buildAndRegister();

        PR_MIX.recipeBuilder().duration(200)
                .fluidInputs(Latex.getFluid(200))
                .input(dust,SodaAsh)
                .output(dust, RawRubber, 1)
                .fluidOutputs(Glue.getFluid(200))
                .buildAndRegister();

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
                .output(dust, GTQTMaterials.PotassiumHydride, 2)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .fluidInputs(GTQTMaterials.Acrylonitrile.getFluid(1000))
                .fluidInputs(Ammonia.getFluid(1000))
                .fluidOutputs(GTQTMaterials.Aminopropionitrile.getFluid(1000))
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .circuitMeta(1)
                .fluidInputs(GTQTMaterials.Aminopropionitrile.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(4000))
                .notConsumable(dust, Nickel)
                .notConsumable(dust, Aluminium)
                .fluidOutputs(GTQTMaterials.Aminopropylamine.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, GTQTMaterials.PotassiumHydride, 2)
                .fluidInputs(GTQTMaterials.Aminopropylamine.getFluid(1000))
                .fluidOutputs(GTQTMaterials.KAPA.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(2000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, GTQTMaterials.BetaPinene, 26)
                .notConsumable(GTQTMaterials.KAPA.getFluid(1))
                .output(dust, GTQTMaterials.AlphaPinene, 26)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, GTQTMaterials.AlphaPinene, 26)
                .notConsumable(SulfuricAcid.getFluid(1))
                .output(dust, GTQTMaterials.Camphene, 26)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, GTQTMaterials.Camphene, 26)
                .fluidInputs(AceticAcid.getFluid(1000))
                .notConsumable(HydrochloricAcid.getFluid(1))
                .output(dust, GTQTMaterials.IsobornylAcetate, 34)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, GTQTMaterials.IsobornylAcetate, 34)
                .input(dust, SodiumHydroxide, 3)
                .output(dust, GTQTMaterials.Isoborneol, 29)
                .fluidOutputs(GTQTMaterials.SodiumAcetate.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, GTQTMaterials.Isoborneol, 29)
                .notConsumable(dust, GTQTMaterials.DehydrogenationCatalyst)
                .output(dust, GTQTMaterials.Isoborneol, 26)
                .fluidOutputs(Hydrogen.getFluid(2000))
                .buildAndRegister();
    }

    private static void acetoin() {
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, GTQTMaterials.SodiumChlorate)
                .fluidInputs(Water.getFluid(1000))
                .output(dust, GTQTMaterials.SodiumPerchlorate)
                .fluidOutputs(Hydrogen.getFluid(2000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, GTQTMaterials.SodiumPerchlorate, 6)
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .output(dust, Salt, 2)
                .fluidOutputs(PerchloricAcid.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, GTQTMaterials.SodiumNitrate, 4)
                .fluidInputs(GTQTMaterials.Aniline.getFluid(1000))
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .notConsumable(dust, SodiumSulfide)
                .output(dust, Salt, 2)
                .fluidOutputs(GTQTMaterials.Phenylhydrazine.getFluid(1000))
                .fluidOutputs(Oxygen.getFluid(1000))
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .duration(265)
                .EUt(VA[LuV])
                .fluidInputs(GTQTMaterials.FormicAcid.getFluid(1000))
                .fluidInputs(PerchloricAcid.getFluid(1000)) //TODO: RECIPE
                .fluidInputs(GTQTMaterials.BenzoylChloride.getFluid(1000)) //TODO: RECIPE
                .fluidInputs(GTQTMaterials.Aniline.getFluid(1000))
                .fluidInputs(GTQTMaterials.Phenylhydrazine.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .output(dust, GTQTMaterials.TriphenylMethoxytriazoylPerchlorate, 44)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, GTQTMaterials.TriphenylMethoxytriazoylPerchlorate, 44)
                .input(dust, GTQTMaterials.SodiumMethoxide, 6)
                .output(dust, GTQTMaterials.TriphenylMethoxytriazole, 44)
                .output(dust, GTQTMaterials.SodiumPerchlorate, 6)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, Sodium)
                .fluidInputs(Methanol.getFluid(1000))
                .output(dust, GTQTMaterials.SodiumMethoxide, 6)
                .fluidOutputs(Hydrogen.getFluid(1000))
                .buildAndRegister();
    }

    private static void Aniline() {
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .fluidInputs(Nitrobenzene.getFluid(1000))
                .fluidInputs(Chlorine.getFluid(2000))
                .fluidOutputs(GTQTMaterials.MetaNitrochlorobenzine.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .input(dust, GTQTMaterials.SodiumMethoxide, 6)
                .fluidInputs(GTQTMaterials.MetaNitrochlorobenzine.getFluid(1000))
                .output(dust, Salt, 2)
                .fluidOutputs(GTQTMaterials.Nitroanisole.getFluid(1000))
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .duration(145)
                .EUt(VA[EV])
                .circuitMeta(2)
                .fluidInputs(GTQTMaterials.Nitroanisole.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(6000))
                .notConsumable(dust, Nickel)
                .notConsumable(dust, Aluminium)
                .fluidOutputs(GTQTMaterials.Aniline.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .buildAndRegister();

        // 6H + C6H5NO2 -> C6H5NH2 + 2H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Hydrogen.getFluid(6000))
                .fluidInputs(Nitrobenzene.getFluid(1000))
                .notConsumable(dust, Zinc)
                .fluidOutputs(GTQTMaterials.Aniline.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .EUt(30)
                .duration(100)
                .buildAndRegister();
    }


}
