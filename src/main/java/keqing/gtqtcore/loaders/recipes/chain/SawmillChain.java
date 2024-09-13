package keqing.gtqtcore.loaders.recipes.chain;

import net.minecraft.init.Items;

import static gregtech.api.unification.material.Materials.Water;
import static gregtech.api.unification.material.Materials.Wood;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.SAW_MILL;

public class SawmillChain {
    public static void init() {
        SAW_MILL.recipeBuilder()
                .input(log, Wood)
                .output(plank, Wood,4)
                .fluidInputs(Water.getFluid(100))
                .duration(200).EUt(8).buildAndRegister();

        SAW_MILL.recipeBuilder()
                .input(plank, Wood)
                .output(plate,Wood,1)
                .fluidInputs(Water.getFluid(100))
                .duration(100).EUt(8).buildAndRegister();

        SAW_MILL.recipeBuilder()
                .input(plate,Wood,1)
                .output(Items.STICK, 2)
                .fluidInputs(Water.getFluid(100))
                .duration(100).EUt(8).buildAndRegister();
    }
}
