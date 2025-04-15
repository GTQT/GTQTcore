package keqing.gtqtcore.api.recipes.builder;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.properties.impl.ComputationProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import keqing.gtqtcore.api.recipes.properties.LaserNetProperty;
import keqing.gtqtcore.api.utils.GTQTLog;


public class LaserComputationRecipeBuilder extends RecipeBuilder<LaserComputationRecipeBuilder> {

    public LaserComputationRecipeBuilder() {/**/}

    public LaserComputationRecipeBuilder(Recipe recipe, RecipeMap<LaserComputationRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public LaserComputationRecipeBuilder(RecipeBuilder<LaserComputationRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public LaserComputationRecipeBuilder copy() {
        return new LaserComputationRecipeBuilder(this);
    }

    @Override
    public boolean applyPropertyCT(String key,Object value) {
        if (key.equals(ComputationProperty.KEY)) {
            this.CWUt(((Number) value).intValue());
            return true;
        }
        if (key.equals(LaserNetProperty.KEY)) {
            this.Laser(((Number) value).intValue());
            return true;
        }
        return super.applyPropertyCT(key, value);
    }

    public LaserComputationRecipeBuilder Laser(int Laser) {
        if (Laser <= 0) {
            GTQTLog.logger.error("NULL Laser", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(LaserNetProperty.getInstance(), Laser);
        return this;
    }

    public LaserComputationRecipeBuilder CWUt(int cwut) {
        if (cwut < 0) {
            GTLog.logger.error("CWU/t cannot be less than 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(ComputationProperty.getInstance(), cwut);
        return this;
    }

}