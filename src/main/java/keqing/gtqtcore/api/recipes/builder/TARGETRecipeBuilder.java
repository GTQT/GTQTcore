package keqing.gtqtcore.api.recipes.builder;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.FusionRecipeBuilder;
import gregtech.api.recipes.recipeproperties.ComputationProperty;
import gregtech.api.recipes.recipeproperties.FusionEUToStartProperty;
import gregtech.api.recipes.recipeproperties.TotalComputationProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import keqing.gtqtcore.api.recipes.properties.EUToStartProperty;
import keqing.gtqtcore.api.recipes.properties.KQNetProperty;
import keqing.gtqtcore.api.recipes.properties.PAProperty;
import keqing.gtqtcore.api.recipes.properties.ScatteringProperty;
import keqing.gtqtcore.api.utils.GTQTLog;


public class TARGETRecipeBuilder extends RecipeBuilder<TARGETRecipeBuilder> {

    public TARGETRecipeBuilder() {/**/}

    public TARGETRecipeBuilder(Recipe recipe, RecipeMap<TARGETRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public TARGETRecipeBuilder(RecipeBuilder<TARGETRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public TARGETRecipeBuilder copy() {
        return new TARGETRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(String key, Object value) {

        if (key.equals(EUToStartProperty.KEY)) {
            this.EUToStart(((Number)value).intValue());
            return true;
        }
        if (key.equals(ScatteringProperty.KEY)) {
            this.Scattering(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);

    }

    //eu to start

    //散射截面
    public TARGETRecipeBuilder EUToStart(int EUToStart) {
        if (EUToStart <= 0L) {
            GTLog.logger.error("EU to start cannot be less than or equal to 0", new IllegalArgumentException());
            this.recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(EUToStartProperty.getInstance(), EUToStart);
        return this;
    }

    public TARGETRecipeBuilder Scattering(int Scattering) {
        if (Scattering < 0) {
            GTLog.logger.error("Scattering cannot be less than 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(ScatteringProperty.getInstance(), Scattering);
        return this;
    }


}