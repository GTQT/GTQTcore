package keqing.gtqtcore.loaders.recipes.chain;

import static gregicality.science.api.unification.materials.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.unification.TJMaterials.*;

public class CircuitryMaterialChains {

    public static void init() {
        SiliconNanocrystals();
    }

    private static void SiliconNanocrystals() {

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(110)
                .EUt(VA[MV])
                .input(dust, Silicon)
                .circuitMeta(0)
                .fluidInputs(HydrochloricAcid.getFluid(3000))
                .fluidOutputs(Trichlorosilane.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(160)
                .EUt(VA[MV])
                .notConsumable(dust, RutheniumChloride)
                .fluidInputs(Trichlorosilane.getFluid(4000))
                .fluidOutputs(Silane.getFluid(1000))
                .fluidOutputs(SiliconTetrachloride.getFluid(3000))
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .duration(140)
                .EUt(VA[EV])
                .fluidInputs(Argon.getFluid(1000))
                .fluidInputs(Silane.getFluid(1000))
                .fluidOutputs(ArgonSilane.getFluid(2000))
                .buildAndRegister();

        ARC_FURNACE_RECIPES.recipeBuilder()
                .duration(530)
                .EUt(VA[IV])
                .fluidInputs(ArgonSilane.getFluid(1000))
                .notConsumable(plate, AluminoSilicateGlass)
                .fluidOutputs(ArgonSilane.getPlasma(1000))
                .output(dustTiny, LuminescentSiliconNanocrystals)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .duration(220)
                .EUt(VA[LuV])
                .fluidInputs(ArgonSilane.getPlasma(1000))
                .chancedOutput(dustSmall, LuminescentSiliconNanocrystals, 7500, 500)
                .chancedOutput(dustSmall, LuminescentSiliconNanocrystals, 7500, 500)
                .chancedOutput(dustSmall, LuminescentSiliconNanocrystals, 7500, 500)
                .chancedOutput(dustSmall, LuminescentSiliconNanocrystals, 7500, 500)
                .chancedOutput(dustSmall, LuminescentSiliconNanocrystals, 7500, 500)
                .chancedOutput(dustSmall, LuminescentSiliconNanocrystals, 7500, 500)
                .fluidOutputs(Silane.getFluid(400))
                .fluidOutputs(Argon.getFluid(400))
                .buildAndRegister();
    }
}
