package keqing.gtqtcore.api.recipes.builder;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.ComputationProperty;
import gregtech.api.recipes.recipeproperties.TotalComputationProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import keqing.gtqtcore.api.recipes.properties.SEProperty;
import keqing.gtqtcore.api.utils.GTQTLog;


public class SEComputationRecipeBuilder extends RecipeBuilder<SEComputationRecipeBuilder> {

    public SEComputationRecipeBuilder() {/**/}

    public SEComputationRecipeBuilder(Recipe recipe, RecipeMap<SEComputationRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public SEComputationRecipeBuilder(RecipeBuilder<SEComputationRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public SEComputationRecipeBuilder copy() {
        return new SEComputationRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(String key, Object value) {
        if (key.equals(ComputationProperty.KEY)) {
            this.CWUt(((Number) value).intValue());
            return true;
        }
        if (key.equals(TotalComputationProperty.KEY)) {
            this.totalCWU(((Number) value).intValue());
            return true;
        }
        if (key.equals(SEProperty.KEY)) {
            this.totalCWU(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    public SEComputationRecipeBuilder Motor(int Motor) {
        if (Motor <= 0) {
            GTQTLog.logger.error("NULL Motor", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(SEProperty.getInstance(), Motor);
        return this;
    }

    public SEComputationRecipeBuilder CWUt(int cwut) {
        if (cwut < 0) {
            GTLog.logger.error("CWU/t cannot be less than 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(ComputationProperty.getInstance(), cwut);
        return this;
    }

    /**
     * The total computation for this recipe. If desired, this should be used instead of a call to duration().
     */
    public SEComputationRecipeBuilder totalCWU(int totalCWU) {
        if (totalCWU < 0) {
            GTLog.logger.error("Total CWU cannot be less than 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(TotalComputationProperty.getInstance(), totalCWU);
        return duration(totalCWU);
    }
}