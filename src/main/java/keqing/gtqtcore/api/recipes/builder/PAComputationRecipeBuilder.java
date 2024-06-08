package keqing.gtqtcore.api.recipes.builder;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.ComputationProperty;
import gregtech.api.recipes.recipeproperties.TotalComputationProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import keqing.gtqtcore.api.recipes.properties.KQNetProperty;
import keqing.gtqtcore.api.recipes.properties.PAProperty;
import keqing.gtqtcore.api.utils.GTQTLog;


public class PAComputationRecipeBuilder extends RecipeBuilder<PAComputationRecipeBuilder> {

    public PAComputationRecipeBuilder() {/**/}

    public PAComputationRecipeBuilder(Recipe recipe, RecipeMap<PAComputationRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public PAComputationRecipeBuilder(RecipeBuilder<PAComputationRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public PAComputationRecipeBuilder copy() {
        return new PAComputationRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(String key, Object value) {
        if (key.equals(ComputationProperty.KEY)) {
            this.CWUt(((Number) value).intValue());
            return true;
        }
        if (key.equals(PAProperty.KEY)) {
            this.Tier(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    public PAComputationRecipeBuilder Tier(int tier) {
        if (tier <= 0) {
            GTQTLog.logger.error("NULL tier", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(PAProperty.getInstance(), tier);
        return this;
    }

    public PAComputationRecipeBuilder CWUt(int cwut) {
        if (cwut < 0) {
            GTLog.logger.error("CWU/t cannot be less than 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(ComputationProperty.getInstance(), cwut);
        return this;
    }


}