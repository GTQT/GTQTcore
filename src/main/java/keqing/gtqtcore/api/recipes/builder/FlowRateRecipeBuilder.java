package keqing.gtqtcore.api.recipes.builder;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.PrimitiveProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.TextFormattingUtil;
import gregtech.api.util.ValidationResult;
import keqing.gtqtcore.api.recipes.properties.FlowRateProperty;
import keqing.gtqtcore.api.recipes.properties.MaxRateProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;

public class FlowRateRecipeBuilder extends RecipeBuilder<FlowRateRecipeBuilder> {
    public FlowRateRecipeBuilder() {/**/}

    public FlowRateRecipeBuilder(Recipe recipe, RecipeMap<FlowRateRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public FlowRateRecipeBuilder(FlowRateRecipeBuilder builder) {
        super(builder);
    }

    public FlowRateRecipeBuilder copy() {
        return new FlowRateRecipeBuilder(this);
    }

    public boolean applyProperty(@Nonnull String key, Object value) {
        if (key.equals("max_rate")) {
            // 检查 max_rate 是否已经赋值
            if (getMaxRate() == 0) { // 如果 max_rate 未赋值
                flowRate(((Number)value).intValue());
            }
            return true;
        }
        if (key.equals("flow_rate")) {
            // 检查 flow_rate 是否已经赋值
            if (getFlowRate() == 0) { // 如果 flow_rate 未赋值
                flowRate(((Number)value).intValue());
            }
            return true;
        }
        return super.applyProperty(key, value);
    }

    public FlowRateRecipeBuilder maxRate(int heat_max_rate) {
        if (heat_max_rate < 0) {
            GTQTLog.logger.error("Max Rate cannot be less than 0", new IllegalArgumentException());
            this.recipeStatus = EnumValidationResult.INVALID;
        }
        applyProperty(MaxRateProperty.getInstance(), heat_max_rate);
        return this;
    }

    public FlowRateRecipeBuilder flowRate(int heat_flow_rate) {
        if (heat_flow_rate < 0) {
            GTQTLog.logger.error("Flow Rate cannot be less than 0", new IllegalArgumentException());
            this.recipeStatus = EnumValidationResult.INVALID;
        }
        applyProperty(FlowRateProperty.getInstance(), heat_flow_rate);
        return this;
    }

    public int getMaxRate() {
        return this.recipePropertyStorage == null ? 0 : this.recipePropertyStorage
                .getRecipePropertyValue(MaxRateProperty.getInstance(), 0);
    }

    public int getFlowRate() {
        return this.recipePropertyStorage == null ? 0 : this.recipePropertyStorage
                .getRecipePropertyValue(FlowRateProperty.getInstance(), 0);
    }

    public ValidationResult<Recipe> build() {
        this.EUt(1);
        this.applyProperty(PrimitiveProperty.getInstance(), true);
        return super.build();
    }

    public String toString() {
        return (new ToStringBuilder(this))
                .appendSuper(super.toString())
                .append(MaxRateProperty.getInstance().getKey(), TextFormattingUtil.formatNumbers(getMaxRate()))
                .append(FlowRateProperty.getInstance().getKey(), TextFormattingUtil.formatNumbers(getFlowRate()))
                .toString();
    }
}
