package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.recipes.GTRecipeHandler;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Hydrazine;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class RocketEngineRecipes {
    public static void init() {

        //  Remove rocket fuel Combustion generator recipe
        GTRecipeHandler.removeRecipesByInputs(COMBUSTION_GENERATOR_FUELS, RocketFuel.getFluid(16));

        //  Rocket Fuel
        ROCKET_RECIPES.recipeBuilder()
                .fluidInputs(RocketFuel.getFluid(16))
                .fluidOutputs(OverheatedGas.getFluid(2 * 1000))
                .EUt(512)
                .duration(8 * SECOND)
                .buildAndRegister();

        //  RP-1 Rocket Fuel
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(CoalTar.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(RP1RocketFuel.getFluid(1000))
                .EUt(VA[HV])
                .duration(SECOND - 4)
                .buildAndRegister();

        ROCKET_RECIPES.recipeBuilder()
                .fluidInputs(RP1RocketFuel.getFluid(12))
                .fluidOutputs(OverheatedGas.getFluid(1000))
                .EUt(512)
                .duration(6 * SECOND)
                .buildAndRegister();

        //  Dense Hydrazine Mixture Fuel
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(Dimethylhydrazine.getFluid(1000))
                .fluidInputs(Methanol.getFluid(1000))
                .fluidOutputs(DenseHydrazineMixtureFuel.getFluid(1000))
                .EUt(VH[EV])
                .duration(6 * SECOND)
                .buildAndRegister();

        ROCKET_RECIPES.recipeBuilder()
                .fluidInputs(DenseHydrazineMixtureFuel.getFluid(9))
                .fluidOutputs(OverheatedGas.getFluid(4 * 1000))
                .EUt(2048)
                .duration(4 * SECOND)
                .buildAndRegister();

        //  C + N2H4 + H2 -> CH6N2
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Carbon)
                .fluidInputs(Hydrazine.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(Methylhydrazine.getFluid(1000))
                .EUt(VA[EV])
                .duration(24 * SECOND)
                .buildAndRegister();

        //  Methylhydrazine Nitrate Rocket Fuel
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(Methylhydrazine.getFluid(1000))
                .fluidInputs(Tetranitromethane.getFluid(1000))
                .fluidOutputs(MethylhydrazineNitrateRocketFuel.getFluid(1000))
                .EUt(VA[EV])
                .duration(8 * SECOND)
                .buildAndRegister();

        ROCKET_RECIPES.recipeBuilder()
                .fluidInputs(MethylhydrazineNitrateRocketFuel.getFluid(6))
                .fluidOutputs(OverheatedGas.getFluid(6 * 1000))
                .EUt(2048)
                .duration(8 * SECOND)
                .buildAndRegister();

        // HydrazineFluorideFuel
        LOW_TEMP_ACTIVATOR_RECIPES.recipeBuilder()
                .fluidInputs(Methylhydrazine.getFluid(1000))
                .fluidInputs(HydrofluoricAcid.getFluid(1000))
                .fluidOutputs(UnstableHydrazineFluoride.getFluid(1000))
                .EUt(VA[IV])
                .duration(10 * SECOND)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(UnstableHydrazineFluoride.getFluid(1000))
                .fluidInputs(MTBE.getFluid(1000))
                .fluidOutputs(HydrazineFluorideFuel.getFluid(1000))
                .EUt(VA[IV])
                .duration(10 * SECOND)
                .buildAndRegister();

        ROCKET_RECIPES.recipeBuilder()
                .fluidInputs(HydrazineFluorideFuel.getFluid(8))
                .fluidOutputs(OverheatedGas.getFluid(3 * 1000))
                .EUt(8192)
                .duration(4 * SECOND)
                .buildAndRegister();

        //HydraziniumChloride
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(AmmoniumChloride.getFluid(2000))
                .fluidInputs(Chlorine.getFluid(2000))
                .fluidOutputs(HydraziniumChloride.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(1000)) // 需中和处理
                .EUt(VA[IV])
                .duration(10 * SECOND)
                .buildAndRegister();

        //DinitrogenPentoxide
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Nitrogen.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(5000))
                .circuitMeta(6)
                .fluidOutputs(DinitrogenPentoxide.getFluid(1000))
                .EUt(VA[IV])
                .duration(10 * SECOND)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(NitricAcid.getFluid(1000))
                .fluidInputs(DinitrogenPentoxide.getFluid(100))
                .fluidOutputs(SodiumNitrateSulfuricAcid.getFluid(1000)) // 含过量NO₂
                .EUt(VA[IV])
                .duration(80)
                .buildAndRegister();

        //硝化反应（需低温逐滴添加）
        LOW_TEMP_ACTIVATOR_RECIPES.recipeBuilder()
                .fluidInputs(HydraziniumChloride.getFluid(4000))
                .fluidInputs(SodiumNitrateSulfuricAcid.getFluid(1000))
                .output(dust, HydraziniumDinitramide, 6) // N₂H₅N(NO₂)₂
                .fluidOutputs(NitrogenDioxide.getFluid(500)) // 剧毒红烟
                .EUt(VA[IV])
                .duration(300)
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .input(dust, HydraziniumDinitramide, 6)
                .fluidInputs(MTBE.getFluid(2000))
                .fluidInputs(Ozone.getFluid(FluidStorageKeys.LIQUID, 2000))
                .fluidOutputs(TrihydraziniumGel.getFluid(1000))
                .EUt(VA[IV])
                .duration(240)
                .buildAndRegister();

        ROCKET_RECIPES.recipeBuilder()
                .fluidInputs(TrihydraziniumGel.getFluid(8))
                .fluidOutputs(OverheatedGas.getFluid(5 * 1000))
                .EUt(8192)
                .duration(6 * SECOND)
                .buildAndRegister();
    }
}
