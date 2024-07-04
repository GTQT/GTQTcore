package keqing.gtqtcore.loaders.recipes.handlers;

import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;


public class ParticleAccelerator {

    public static void init() {
        TARGET_CHAMBER();
        PARTICLE_ACCELERATOR_RECIPES();

    }
    public static void PARTICLE_ACCELERATOR_RECIPES()
    {
        //0-100000
        GTQTcoreRecipeMaps.PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                .input(dust,Iron)
                .output(ingot,Iron)
                .CWUt(114)
                .totalCWU(114514)
                .EUt(7680)
                .buildAndRegister();
    }
    public static void TARGET_CHAMBER()
    {
        //0-100000
        GTQTcoreRecipeMaps.TARGET_CHAMBER.recipeBuilder()
                .input(dust,Iron)
                .output(ingot,Iron)
                .EUToStart(20000)
                .Scattering(2)
                .duration(100)
                .EUt(7680)
                .buildAndRegister();
    }


}
