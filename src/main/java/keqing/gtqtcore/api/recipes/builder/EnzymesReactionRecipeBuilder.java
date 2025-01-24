package keqing.gtqtcore.api.recipes.builder;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import keqing.gtqtcore.api.recipes.properties.BioReactorProperty;
import keqing.gtqtcore.api.recipes.properties.EnzymesReactionProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;

public class EnzymesReactionRecipeBuilder extends RecipeBuilder<EnzymesReactionRecipeBuilder> {

    public EnzymesReactionRecipeBuilder() {}

    public EnzymesReactionRecipeBuilder(Recipe recipe, RecipeMap<EnzymesReactionRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public EnzymesReactionRecipeBuilder(RecipeBuilder<EnzymesReactionRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }
    @Override
    public boolean applyProperty(@Nonnull String key, Object value) {
        if (key.equals(EnzymesReactionProperty.KEY)) {
            this.rate(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }
    @Override
    public EnzymesReactionRecipeBuilder copy() {
        return new EnzymesReactionRecipeBuilder(this);
    }

    public int getRate() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(EnzymesReactionProperty.getInstance(), 0);
    }

    public EnzymesReactionRecipeBuilder rate(int Tire) {
        if (Tire <= 0) {
            GTQTLog.logger.error("Casing Tier cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(EnzymesReactionProperty.getInstance(), Tire);
        return this;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(EnzymesReactionProperty.getInstance().getKey(), getRate())
                .toString();
    }
}
