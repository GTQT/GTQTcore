package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

import static gregtech.api.GTValues.MV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_BATH_RECIPES;
import static gregtech.api.unification.material.Materials.Kanthal;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.ingotHot;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Polysilicon;
import static keqing.gtqtcore.api.unification.GTQTMaterials.VibrantAlloy;

public class ChemicalBath {
    public static void init()
    {
        ChemicalBathRecipes(StainlessSteel);
        ChemicalBathRecipes(Polysilicon);
        ChemicalBathRecipes(VibrantAlloy);
    }
    public static void ChemicalBathRecipes(Material material)
    {
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(ingotHot, material)
                .fluidInputs(Water.getFluid(100))
                .output(OrePrefix.ingot, material)
                .duration(400).EUt(VA[MV]).buildAndRegister();

        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(ingotHot, material)
                .fluidInputs(DistilledWater.getFluid(100))
                .output(OrePrefix.ingot, material)
                .duration(250).EUt(VA[MV]).buildAndRegister();
    }
}
