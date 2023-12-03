package keqing.gtqtcore.api.recipes.builder;


import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import keqing.gtqtcore.api.recipes.properties.CACasingTierProperty;
import keqing.gtqtcore.api.recipes.properties.PACasingTierProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;

public class CACasingTierRecipeBuilder extends RecipeBuilder<CACasingTierRecipeBuilder> {
    public CACasingTierRecipeBuilder() {}

    public CACasingTierRecipeBuilder(Recipe recipe, RecipeMap<CACasingTierRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public CACasingTierRecipeBuilder(RecipeBuilder<CACasingTierRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public CACasingTierRecipeBuilder copy() {
        return new CACasingTierRecipeBuilder(this);
    }

    public int getCATier() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(CACasingTierProperty.getInstance(), 0);
    }

    @Override
    public boolean applyProperty(@Nonnull String key, Object value) {
        if (key.equals(PACasingTierProperty.KEY)) {
            this.CasingTier(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    public CACasingTierRecipeBuilder CasingTier(int Tier) {
        if (Tier <= 0) {
            GTQTLog.logger.error("Casing Tier cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(CACasingTierProperty.getInstance(), Tier);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(CACasingTierProperty.getInstance().getKey(), getCATier())
                .toString();
    }
}
