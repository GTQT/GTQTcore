package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.common.items.MetaItems.STICKY_RESIN;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.ULTRAVIOLET_LAMP_CHAMBER_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class CleanWaterChain {
    public static void init() {
        SIFTER_RECIPES.recipeBuilder()
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(CleanWater_1.getFluid(900))
                .chancedOutput(dust,Stone,5000,0)
                .chancedOutput(dust,Clay,2000,0)
                .chancedOutput(dust,Calcite,1000,0)
                .chancedOutput(dust,Salt,1000,0)
                .duration(1000)
                .EUt(VA[IV])
                .buildAndRegister();

        FLUID_HEATER_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .fluidInputs(CleanWater_1.getFluid(1000))
                .fluidOutputs(CleanWater_2.getFluid(900))
                .duration(1000)
                .EUt(VA[IV])
                .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .duration(1000)
                .EUt(VA[IV+1])
                .fluidInputs(CleanWater_2.getFluid(1000))
                .fluidOutputs(CleanWater_1.getFluid(100))
                .fluidOutputs(CleanWater_2.getFluid(100))
                .fluidOutputs(CleanWater_3.getFluid(900))
                .fluidOutputs(Oil.getFluid(50))
                .fluidOutputs(Ammonia.getFluid(100))
                .buildAndRegister();

        ULTRAVIOLET_LAMP_CHAMBER_RECIPES.recipeBuilder()
                .duration(1000)
                .EUt(VA[IV+1])
                .notConsumable(CATALYST_FRAMEWORK_III)
                .fluidInputs(CleanWater_3.getFluid(1000))
                .fluidOutputs(CleanWater_4.getFluid(900))
                .fluidOutputs(Biomass.getFluid(100))
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .duration(1000)
                .EUt(VA[IV+2])
                .circuitMeta(1)
                .notConsumable(dust,Fluorite)
                .fluidInputs(CleanWater_4.getFluid(1000))
                .fluidOutputs(CleanWater_5.getFluid(900))
                .buildAndRegister();

        ULTRAVIOLET_LAMP_CHAMBER_RECIPES.recipeBuilder()
                .duration(1000)
                .EUt(VA[IV+2])
                .notConsumable(CATALYST_FRAMEWORK_III)
                .fluidInputs(CleanWater_5.getFluid(1000))
                .fluidOutputs(CleanWater_6.getFluid(900))
                .fluidOutputs(Steam.getFluid(1000))
                .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .duration(1000)
                .EUt(VA[IV+3])
                .fluidInputs(CleanWater_6.getFluid(1000))
                .fluidOutputs(CleanWater_5.getFluid(100))
                .fluidOutputs(CleanWater_6.getFluid(100))
                .fluidOutputs(CleanWater_7.getFluid(900))
                .fluidOutputs(Oxygen.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .buildAndRegister();

        LASER_ENGRAVER_RECIPES.recipeBuilder()
                .duration(1000)
                .EUt(VA[IV+3])
                .notConsumable(CATALYST_FRAMEWORK_IV)
                .fluidInputs(CleanWater_7.getFluid(1000))
                .fluidOutputs(CleanWater_8.getFluid(900))
                .fluidOutputs(Steam.getFluid(1000))
                .buildAndRegister();

    }

}
