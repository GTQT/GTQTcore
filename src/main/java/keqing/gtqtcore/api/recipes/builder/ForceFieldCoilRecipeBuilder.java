package keqing.gtqtcore.api.recipes.builder;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import keqing.gtqtcore.api.recipes.properties.ForceFieldCoilTierProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ForceFieldCoilRecipeBuilder extends RecipeBuilder<ForceFieldCoilRecipeBuilder> {
    public ForceFieldCoilRecipeBuilder() {}
    public ForceFieldCoilRecipeBuilder(Recipe recipe,
                                       RecipeMap<ForceFieldCoilRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public ForceFieldCoilRecipeBuilder(RecipeBuilder<ForceFieldCoilRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public ForceFieldCoilRecipeBuilder copy() {
        return new ForceFieldCoilRecipeBuilder(this);
    }

    public int getTier() {
        return this.recipePropertyStorage == null ? 0 : this.recipePropertyStorage.getRecipePropertyValue(
                ForceFieldCoilTierProperty.getInstance(), 0);
    }

    @Override
    public boolean applyProperty( String key,
                                  Object value) {
        if (key.equals(ForceFieldCoilTierProperty.PROPERTY_KEY)) {
            this.tier(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    public ForceFieldCoilRecipeBuilder tier(int coilTier) {
        if (coilTier <= 0) {
            GTQTLog.logger.error("Force Field Coil Tier cannot be less than or equal to 0", new IllegalArgumentException());
            this.recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(ForceFieldCoilTierProperty.getInstance(), coilTier);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(ForceFieldCoilTierProperty.getInstance().getKey(), this.getTier())
                .toString();
    }
}