package keqing.gtqtcore.api.recipes.builder;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.properties.RecipePropertyStorage;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.TextFormattingUtil;
import gregtech.api.util.ValidationResult;
import keqing.gtqtcore.api.GCYSValues;
import keqing.gtqtcore.api.recipes.properties.NoCoilTemperatureProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;

public class NoCoilTemperatureRecipeBuilder extends RecipeBuilder<NoCoilTemperatureRecipeBuilder> {

    public NoCoilTemperatureRecipeBuilder() {

    }

    @SuppressWarnings("unused")
    public NoCoilTemperatureRecipeBuilder(Recipe recipe, RecipeMap<NoCoilTemperatureRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public NoCoilTemperatureRecipeBuilder(NoCoilTemperatureRecipeBuilder builder) {
        super(builder);
    }

    @Override
    public NoCoilTemperatureRecipeBuilder copy() {
        return new NoCoilTemperatureRecipeBuilder(this);
    }

    @Override
    public boolean applyPropertyCT(String key,Object value) {
        if (key.equals(NoCoilTemperatureProperty.KEY)) {
            this.temperature(((Number) value).intValue());
            return true;
        }
        return super.applyPropertyCT(key, value);
    }

    public NoCoilTemperatureRecipeBuilder temperature(int temperature) {
        if (temperature <= 0) {
            GTLog.logger.error("Temperature cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(NoCoilTemperatureProperty.getInstance(), temperature);
        return this;
    }

    @Override
    public ValidationResult<Recipe> build() {
        if (this.recipePropertyStorage.contains(NoCoilTemperatureProperty.getInstance())) {
            if (this.recipePropertyStorage.get(NoCoilTemperatureProperty.getInstance(), -1) <= 0) {
                this.recipePropertyStorage.store(NoCoilTemperatureProperty.getInstance(), GCYSValues.EARTH_TEMPERATURE);
            }
        } else {
            this.temperature(GCYSValues.EARTH_TEMPERATURE);
        }

        return super.build();
    }

    public int getTemperature() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.get(NoCoilTemperatureProperty.getInstance(), 0);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(NoCoilTemperatureProperty.getInstance().getKey(), TextFormattingUtil.formatNumbers(getTemperature()))
                .toString();
    }
}
