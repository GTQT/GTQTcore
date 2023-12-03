package keqing.gtqtcore.api.recipes.builder;


import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import keqing.gtqtcore.api.recipes.properties.QFTCasingTierProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;

public class QFTCasingTierRecipeBuilder extends RecipeBuilder<QFTCasingTierRecipeBuilder> {
    public QFTCasingTierRecipeBuilder() {}

    public QFTCasingTierRecipeBuilder(Recipe recipe, RecipeMap<QFTCasingTierRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public QFTCasingTierRecipeBuilder(RecipeBuilder<QFTCasingTierRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public QFTCasingTierRecipeBuilder copy() {
        return new QFTCasingTierRecipeBuilder(this);
    }

    public int getTier() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(QFTCasingTierProperty.getInstance(), 0);
    }

    @Override
    public boolean applyProperty(@Nonnull String key, Object value) {
        if (key.equals(QFTCasingTierProperty.KEY)) {
            this.CasingTier(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    public QFTCasingTierRecipeBuilder CasingTier(int Tier) {
        if (Tier <= 0) {
            GTQTLog.logger.error("Casing Tier cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(QFTCasingTierProperty.getInstance(), Tier);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(QFTCasingTierProperty.getInstance().getKey(), getTier())
                .toString();
    }
}
