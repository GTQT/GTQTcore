package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.unification.ore.OrePrefix;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.SPACE_ELEVATOR_DRILLING_MODULE;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.SPACE_ELEVATOR_MINING_MODULE;

public class SEloader {
    public static void init() {
        casing();
        mining();
        drilling();
    }

    private static void casing() {
    }

    private static void mining() {
        SPACE_ELEVATOR_MINING_MODULE.recipeBuilder()
                .input(dust,Iron)
                .fluidInputs(Water.getFluid(14))
                .output(ore,Naquadah)
                .Motor(3)
                .CWUt(10)
                .totalCWU(114514)
                .EUt(32)
                .duration(20*64)
                .buildAndRegister();
    }

    private static void drilling() {
        SPACE_ELEVATOR_DRILLING_MODULE.recipeBuilder()
                .input(dust,Iron)
                .fluidInputs(Water.getFluid(14))
                .fluidOutputs(OilHeavy.getFluid(114))
                .Motor(3)
                .CWUt(10)
                .totalCWU(114514)
                .EUt(32)
                .duration(20*64)
                .buildAndRegister();
    }
}
