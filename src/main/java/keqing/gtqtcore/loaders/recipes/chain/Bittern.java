package keqing.gtqtcore.loaders.recipes.chain;

import keqing.gtqtcore.api.unification.GTQTMaterials;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class Bittern {
    public static void init() {
        MSF();
    }

    private static void MSF() {
        SFM.recipeBuilder()
                .fluidInputs(GTQTMaterials.Bittern.getFluid(8000))
                .output(dust, Salt,10)
                .fluidOutputs(Bitterncl.getFluid(300))
                .fluidOutputs(Bitterncl.getFluid(200))
                .fluidOutputs(Bitterncl.getFluid(100))
                .fluidOutputs(Bitternso.getFluid(300))
                .fluidOutputs(Bitternso.getFluid(200))
                .fluidOutputs(Bitternso.getFluid(100))
                .fluidOutputs(Bitternco.getFluid(300))
                .fluidOutputs(Bitternco.getFluid(200))
                .fluidOutputs(Bitternco.getFluid(100))
                .fluidOutputs(SaltWater.getFluid(300))
                .fluidOutputs(SaltWater.getFluid(200))
                .fluidOutputs(SaltWater.getFluid(100))
                .duration(20).EUt(120).buildAndRegister();

        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(Bitterncl.getFluid(4000))
                .output(dust, LithiumChloride, 4)
                .output(dust, CalciumChloride, 4)
                .output(dust, MagnesiumChloride, 4)
                .fluidOutputs(AmmoniumChloride.getFluid(576))
                .blastFurnaceTemp(2700)
                .duration(200).EUt(120).buildAndRegister();

        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(Bitternso.getFluid(4000))
                .output(dust, PotassiumSulfate, 4)
                .fluidOutputs(SodiumBisulfate.getFluid(576))
                .fluidOutputs(AmmoniumSulfate.getFluid(3000))
                .blastFurnaceTemp(2700)
                .duration(200).EUt(120).buildAndRegister();

        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(Bitternco.getFluid(4000))
                .output(dust, SodiumBicarbonate, 4)
                .output(dust, BariumCarbonate, 4)
                .blastFurnaceTemp(2700)
                .duration(200).EUt(120).buildAndRegister();

        SFM.recipeBuilder()
                .fluidInputs(SeaWater.getFluid(8000))
                .output(dust, Salt,10)
                .fluidOutputs(SaltWater.getFluid(300))
                .fluidOutputs(SaltWater.getFluid(200))
                .fluidOutputs(SaltWater.getFluid(100))
                .fluidOutputs(SaltWater.getFluid(300))
                .fluidOutputs(SaltWater.getFluid(200))
                .fluidOutputs(SaltWater.getFluid(100))
                .fluidOutputs(Water.getFluid(500))
                .fluidOutputs(Water.getFluid(400))
                .fluidOutputs(Water.getFluid(300))
                .fluidOutputs(Bitterncl.getFluid(200))
                .fluidOutputs(Bitternso.getFluid(200))
                .fluidOutputs(Bitternco.getFluid(200))
                .duration(20).EUt(120).buildAndRegister();

        SFM.recipeBuilder()
                .fluidInputs(SaltWater.getFluid(8000))
                .output(dust, Salt,10)
                .fluidOutputs(Bitterncl.getFluid(300))
                .fluidOutputs(Bitterncl.getFluid(200))
                .fluidOutputs(Bitternso.getFluid(300))
                .fluidOutputs(Bitternso.getFluid(200))
                .fluidOutputs(Bitternco.getFluid(300))
                .fluidOutputs(Bitternco.getFluid(200))
                .fluidOutputs(Water.getFluid(400))
                .fluidOutputs(Water.getFluid(400))
                .fluidOutputs(Water.getFluid(300))
                .fluidOutputs(Water.getFluid(300))
                .fluidOutputs(Water.getFluid(200))
                .fluidOutputs(Water.getFluid(100))
                .duration(20).EUt(120).buildAndRegister();

        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(Ulexite.getFluid(144*11))
                .output(dust, SodiumHydroxide, 1)
                .output(dust, CalciumHydroxide, 1)
                .output(dust, Borax, 1)
                .fluidOutputs(Water.getFluid(6000))
                .blastFurnaceTemp(2700)
                .duration(200).EUt(120).buildAndRegister();

    }
}
