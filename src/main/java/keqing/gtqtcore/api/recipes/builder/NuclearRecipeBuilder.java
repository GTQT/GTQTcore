package keqing.gtqtcore.api.recipes.builder;


import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import keqing.gtqtcore.api.recipes.properties.NuclearProperties;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;

public class NuclearRecipeBuilder extends RecipeBuilder<NuclearRecipeBuilder> {

    public NuclearRecipeBuilder() {}

    public NuclearRecipeBuilder(Recipe recipe, RecipeMap<NuclearRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public NuclearRecipeBuilder(RecipeBuilder<NuclearRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public NuclearRecipeBuilder copy() {
        return new NuclearRecipeBuilder(this);
    }

    public int getminTemp() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(NuclearProperties.getInstance(), 0);
    }

    public NuclearRecipeBuilder minTemp(int minTemp) {
        if (minTemp <= 0) {
            GTQTLog.logger.error("minTemp cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(NuclearProperties.getInstance(), minTemp);
        return this;
    }
    @Override
    public boolean applyProperty(@Nonnull String key, Object value) {
        if (key.equals(NuclearProperties.KEY)) {
            this.minTemp(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(NuclearProperties.getInstance().getKey(), getminTemp())
                .toString();
    }
}
