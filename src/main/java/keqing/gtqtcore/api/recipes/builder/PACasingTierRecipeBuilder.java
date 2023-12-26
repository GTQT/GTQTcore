package keqing.gtqtcore.api.recipes.builder;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.ComputationRecipeBuilder;
import gregtech.api.recipes.recipeproperties.ComputationProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import keqing.gtqtcore.api.recipes.properties.PACasingTierProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;

public class PACasingTierRecipeBuilder extends RecipeBuilder<PACasingTierRecipeBuilder> {
    public PACasingTierRecipeBuilder() {}

    public PACasingTierRecipeBuilder(Recipe recipe, RecipeMap<PACasingTierRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public PACasingTierRecipeBuilder(RecipeBuilder<PACasingTierRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public PACasingTierRecipeBuilder copy() {
        return new PACasingTierRecipeBuilder(this);
    }

    public int getPATier() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(PACasingTierProperty.getInstance(), 0);
    }

    @Override
    public boolean applyProperty(@Nonnull String key, Object value) {
        if (key.equals(PACasingTierProperty.KEY)) {
            this.CasingTier(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    public PACasingTierRecipeBuilder CasingTier(int Tier) {
        if (Tier <= 0) {
            GTQTLog.logger.error("Casing Tier cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(PACasingTierProperty.getInstance(), Tier);
        return this;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(PACasingTierProperty.getInstance().getKey(), getPATier())
                .toString();
    }
}