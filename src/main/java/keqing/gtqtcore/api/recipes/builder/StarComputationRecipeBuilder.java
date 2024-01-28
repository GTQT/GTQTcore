package keqing.gtqtcore.api.recipes.builder;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.ComputationProperty;
import gregtech.api.recipes.recipeproperties.TotalComputationProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import keqing.gtqtcore.api.recipes.properties.KQNetProperty;
import keqing.gtqtcore.api.recipes.properties.StarProperty;
import keqing.gtqtcore.api.utils.GTQTLog;


public class StarComputationRecipeBuilder extends RecipeBuilder<StarComputationRecipeBuilder> {

    public StarComputationRecipeBuilder() {/**/}

    public StarComputationRecipeBuilder(Recipe recipe, RecipeMap<StarComputationRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public StarComputationRecipeBuilder(RecipeBuilder<StarComputationRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public StarComputationRecipeBuilder copy() {
        return new StarComputationRecipeBuilder(this);
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
        if (key.equals(KQNetProperty.KEY)) {
            this.totalCWU(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    public StarComputationRecipeBuilder NB(int NB) {
        if (NB <= 0) {
            GTQTLog.logger.error("NULL NB", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(StarProperty.getInstance(), NB);
        return this;
    }

    public StarComputationRecipeBuilder CWUt(int cwut) {
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
    public StarComputationRecipeBuilder totalCWU(int totalCWU) {
        if (totalCWU < 0) {
            GTLog.logger.error("Total CWU cannot be less than 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(TotalComputationProperty.getInstance(), totalCWU);
        return duration(totalCWU);
    }
}