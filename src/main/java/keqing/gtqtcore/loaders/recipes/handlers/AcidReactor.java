package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.ACID_GENERATOR_RECIPES;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.BIOMASS_GENERATOR_RECIPES;

public class AcidReactor {

    public static void init() {
        AcidReactorRecipes(AceticAcid,1);
        AcidReactorRecipes(HydrochloricAcid,1);
        AcidReactorRecipes(DilutedHydrochloricAcid,1);

        AcidReactorRecipes(SulfuricAcid,2);
        AcidReactorRecipes(NitricAcid,2);
        AcidReactorRecipes(HypochlorousAcid,2);

        AcidReactorRecipes(HydrofluoricAcid,3);
        AcidReactorRecipes(PhosphoricAcid,3);
        AcidReactorRecipes(FluoroantimonicAcid,3);
    }

    public static void AcidReactorRecipes(Material material, int tier)
    {
        ACID_GENERATOR_RECIPES.recipeBuilder()
                .fluidInputs(material.getFluid(200))
                .EUt(VA[tier])
                .duration((int) (10*Math.pow(2,tier)))
                .buildAndRegister();
    }

}
