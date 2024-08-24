package keqing.gtqtcore.loaders.recipes.chain;

import static gregtechfoodoption.GTFOMaterialHandler.PerchloricAcid;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.PLANT_BALL;
import static gregtech.common.items.MetaItems.STICKY_RESIN;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.PR_MIX;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Latex;
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
                .fluidOutputs(AmmoniumChloride.getFluid(1000))
                .output(dust,SodiumBicarbonate,1)
                .buildAndRegister();

        PR_MIX.recipeBuilder()
                .duration(4000)
                .input(dust,Salt,40)
                .fluidInputs(Ammonia.getFluid(40000))
                .fluidInputs(CarbonDioxide.getFluid(40000))
                .fluidInputs(Water.getFluid(40000))
                .fluidOutputs(AmmoniumChloride.getFluid(40000))
                .output(dust,SodiumBicarbonate,40)
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .duration(1000)
                .EUt(30)
                .input(dust,Salt,40)
                .fluidInputs(Ammonia.getFluid(40000))
                .fluidInputs(CarbonDioxide.getFluid(40000))
                .fluidInputs(Water.getFluid(40000))
                .fluidOutputs(AmmoniumChloride.getFluid(40000))
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

        PR_MIX.recipeBuilder()
                .input(dust, Sulfur)
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(HydrogenSulfide.getFluid(1000))
                .duration(480).buildAndRegister();

        PR_MIX.recipeBuilder()
                .input(dust, Sodium)
                .fluidInputs(Chlorine.getFluid(1000))
                .output(dust, Salt, 2)
                .duration(1200).buildAndRegister();


        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Mercury.getFluid(1000))
                .fluidInputs(Water.getFluid(10000))
                .fluidInputs(Chlorine.getFluid(10000))
                .fluidOutputs(HypochlorousAcid.getFluid(10000))
                .duration(4800).buildAndRegister();

        PR_MIX.recipeBuilder()
                .circuitMeta(2)
                .input(dust, Sulfur)
                .fluidInputs(Oxygen.getFluid(2000))
                .fluidOutputs(SulfurDioxide.getFluid(1000))
                .duration(300).buildAndRegister();

        PR_MIX.recipeBuilder()
                .fluidInputs(SulfurDioxide.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(SulfurTrioxide.getFluid(1000))
                .duration(400).buildAndRegister();

        PR_MIX.recipeBuilder()
                .fluidInputs(SulfurTrioxide.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .duration(320).buildAndRegister();

        PR_MIX.recipeBuilder()
                .input(dust, Carbon)
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .duration(320).buildAndRegister();

        PR_MIX.recipeBuilder()
                .input(gem, Charcoal)
                .fluidInputs(Oxygen.getFluid(1000))
                .chancedOutput(dust, Ash, 1111, 0)
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .duration(640).buildAndRegister();

        PR_MIX.recipeBuilder()
                .input(gem, Coal)
                .fluidInputs(Oxygen.getFluid(1000))
                .chancedOutput(dust, Ash, 1111, 0)
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .duration(640).buildAndRegister();

        PR_MIX.recipeBuilder()
                .input(dust, Charcoal)
                .fluidInputs(Oxygen.getFluid(1000))
                .chancedOutput(dust, Ash, 1111, 0)
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .duration(640).buildAndRegister();

        PR_MIX.recipeBuilder()
                .duration(640)
                .input(dust, Coal)
                .fluidInputs(Oxygen.getFluid(1000))
                .chancedOutput(dust, Ash, 1111, 0)
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .buildAndRegister();

        PR_MIX.recipeBuilder()
                .input(dust, Carbon)
                .fluidInputs(CarbonDioxide.getFluid(1000))
                .fluidOutputs(CarbonMonoxide.getFluid(2000))
                .duration(800).buildAndRegister();

        PR_MIX.recipeBuilder()
                .fluidInputs(Chlorine.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .duration(480).buildAndRegister();

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
                .input(dust, Sodium)
                .fluidInputs(Methanol.getFluid(1000))
                .output(dust, SodiumMethoxide, 6)
                .fluidOutputs(Hydrogen.getFluid(1000))
                .buildAndRegister();
    }

    private static void Aniline() {
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
                .fluidOutputs(Aniline.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .buildAndRegister();
    }


}
