package keqing.gtqtcore.api.recipes.builder;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import keqing.gtqtcore.api.recipes.properties.BRProperty;
import keqing.gtqtcore.api.recipes.properties.ERProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ERRecioeBuilder extends RecipeBuilder<ERRecioeBuilder> {

    public ERRecioeBuilder() {}

    public ERRecioeBuilder(Recipe recipe, RecipeMap<ERRecioeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public ERRecioeBuilder(RecipeBuilder<ERRecioeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public ERRecioeBuilder copy() {
        return new ERRecioeBuilder(this);
    }

    public int getRate() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(ERProperty.getInstance(), 0);
    }

    public ERRecioeBuilder rate(int Tire) {
        if (Tire <= 0) {
            GTQTLog.logger.error("Casing Tier cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(ERProperty.getInstance(), Tire);
        return this;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(ERProperty.getInstance().getKey(), getRate())
                .toString();
    }
}
