package keqing.gtqtcore.api.recipes.builder;


import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import keqing.gtqtcore.api.recipes.properties.PHChangeProperty;
import keqing.gtqtcore.api.recipes.properties.PHErrorRangeProperty;
import keqing.gtqtcore.api.recipes.properties.PHProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;

public class PHRecipeBuilder extends RecipeBuilder<PHRecipeBuilder> {
    public PHRecipeBuilder() {/**/}

    public PHRecipeBuilder(Recipe recipe, RecipeMap<PHRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public PHRecipeBuilder(PHRecipeBuilder builder) {
        super(builder);
    }

    public PHRecipeBuilder copy() {
        return new PHRecipeBuilder(this);
    }

    public boolean applyProperty(@Nonnull String key, Object value) {
        if (key.equals(PHProperty.KEY)) {
            pH(((Number)value).doubleValue());
            return true;
        }
        if (key.equals(PHChangeProperty.KEY)) {
            pHChange(((Number) value).doubleValue());
            return true;
        }
        if (key.equals(PHErrorRangeProperty.KEY)) {
            pHErrorRange(((Number) value).doubleValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    public PHRecipeBuilder pH(double ph) {
        if (ph < 0 || ph > 14) {
            GTQTLog.logger.error("The pH value must be between 0 and 14.", new IllegalArgumentException());
            this.recipeStatus = EnumValidationResult.INVALID;
        } else {
            int i = 0;
            do {
                i++;
            } while (ph * Math.pow(10, i) % 1 != 0);
            if (i > 2) {
                GTQTLog.logger.error("The maximum number of decimal places for pH is two.", new IllegalArgumentException());
                this.recipeStatus = EnumValidationResult.INVALID;
            }
        }
        applyProperty(PHProperty.getInstance(), ph);
        return this;
    }

    public PHRecipeBuilder pHChange(double ph_change) {
        if (ph_change < -14 || ph_change > 14) {
            GTQTLog.logger.error("The pH change value must be between -14 and 14.", new IllegalArgumentException());
            this.recipeStatus = EnumValidationResult.INVALID;
        } else {
            int i = 0;
            do {
                i++;
            } while (Math.abs(ph_change) * Math.pow(10, i) % 1 != 0);
            if (i > 2) {
                GTQTLog.logger.error("The maximum number of decimal places for pH change is two.", new IllegalArgumentException());
                this.recipeStatus = EnumValidationResult.INVALID;
            }
        }
        applyProperty(PHChangeProperty.getInstance(), ph_change);
        return this;
    }

    public PHRecipeBuilder pHErrorRange(double ph_error_range) {
        if (ph_error_range < 0 || ph_error_range > 14) {
            GTQTLog.logger.error("The pH error range value must be between 0 and 14.", new IllegalArgumentException());
            this.recipeStatus = EnumValidationResult.INVALID;
        } else {
            int i = 0;
            do {
                i++;
            } while (ph_error_range * Math.pow(10, i) % 1 != 0);
            if (i > 2) {
                GTQTLog.logger.error("The maximum number of decimal places for pH error range is two.", new IllegalArgumentException());
                this.recipeStatus = EnumValidationResult.INVALID;
            }
        }
        applyProperty(PHErrorRangeProperty.getInstance(), ph_error_range);
        return this;
    }


    public double getPH() {
        return this.recipePropertyStorage == null ? 7D : this.recipePropertyStorage
                .getRecipePropertyValue(PHProperty.getInstance(), 7D);
    }

    public double getPHChange() {
        return this.recipePropertyStorage == null ? 0D : this.recipePropertyStorage
                .getRecipePropertyValue(PHChangeProperty.getInstance(), 0D);
    }

    public double getPHErrorRange() {
        return this.recipePropertyStorage == null ? 0D : this.recipePropertyStorage
                .getRecipePropertyValue(PHErrorRangeProperty.getInstance(), 0D);
    }

    public String toString() {
        return (new ToStringBuilder(this))
                .appendSuper(super.toString())
                .append(PHProperty.getInstance().getKey(), getPH())
                .append(PHChangeProperty.getInstance().getKey(), getPHChange())
                .append(PHErrorRangeProperty.getInstance().getKey(), getPHErrorRange())
                .toString();
    }
}
