package keqing.gtqtcore.api.recipes.builder;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.properties.impl.ComputationProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import keqing.gtqtcore.api.recipes.properties.ControllerProperty;
import keqing.gtqtcore.api.utils.GTQTLog;


public class CasingComputationRecipeBuilder extends RecipeBuilder<CasingComputationRecipeBuilder> {

    public CasingComputationRecipeBuilder() {/**/}

    public CasingComputationRecipeBuilder(Recipe recipe, RecipeMap<CasingComputationRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public CasingComputationRecipeBuilder(RecipeBuilder<CasingComputationRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public CasingComputationRecipeBuilder copy() {
        return new CasingComputationRecipeBuilder(this);
    }

    @Override
    public boolean applyPropertyCT(String key,Object value) {
        if (key.equals(ComputationProperty.KEY)) {
            this.CWUt(((Number) value).intValue());
            return true;
        }
        if (key.equals(ControllerProperty.KEY)) {
            this.Tier(((Number) value).intValue());
            return true;
        }
        return super.applyPropertyCT(key, value);
    }

    public CasingComputationRecipeBuilder Tier(int tier) {
        if (tier <= 0) {
            GTQTLog.logger.error("NULL tier", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(ControllerProperty.getInstance(), tier);
        return this;
    }

    public CasingComputationRecipeBuilder CWUt(int cwut) {
        if (cwut < 0) {
            GTLog.logger.error("CWU/t cannot be less than 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(ComputationProperty.getInstance(), cwut);
        return this;
    }


}