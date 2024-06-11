package keqing.gtqtcore.api.recipes.builder;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.ComputationProperty;
import gregtech.api.recipes.recipeproperties.TotalComputationProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import keqing.gtqtcore.api.recipes.properties.LASERNetProperty;
import keqing.gtqtcore.api.utils.GTQTLog;


public class LASERComputationRecipeBuilder extends RecipeBuilder<LASERComputationRecipeBuilder> {

    public LASERComputationRecipeBuilder() {/**/}

    public LASERComputationRecipeBuilder(Recipe recipe, RecipeMap<LASERComputationRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public LASERComputationRecipeBuilder(RecipeBuilder<LASERComputationRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public LASERComputationRecipeBuilder copy() {
        return new LASERComputationRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(String key, Object value) {
        if (key.equals(ComputationProperty.KEY)) {
            this.CWUt(((Number) value).intValue());
            return true;
        }
        if (key.equals(LASERNetProperty.KEY)) {
            this.Laser(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    public LASERComputationRecipeBuilder Laser(int Laser) {
        if (Laser <= 0) {
            GTQTLog.logger.error("NULL Laser", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(LASERNetProperty.getInstance(), Laser);
        return this;
    }

    public LASERComputationRecipeBuilder CWUt(int cwut) {
        if (cwut < 0) {
            GTLog.logger.error("CWU/t cannot be less than 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(ComputationProperty.getInstance(), cwut);
        return this;
    }

}