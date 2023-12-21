package keqing.gtqtcore.api.recipes.builder;


import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import keqing.gtqtcore.api.recipes.properties.CACasingTierProperty;
import keqing.gtqtcore.api.recipes.properties.ELEProperties;

import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;

public class ELERecipeBuilder  extends RecipeBuilder<ELERecipeBuilder> {

    public ELERecipeBuilder() {}

    public ELERecipeBuilder(Recipe recipe, RecipeMap<ELERecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public ELERecipeBuilder(RecipeBuilder<ELERecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public ELERecipeBuilder copy() {
        return new ELERecipeBuilder(this);
    }

    public int getTire() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(ELEProperties.getInstance(), 0);
    }

    public ELERecipeBuilder tier(int Tire) {
        if (Tire <= 0) {
            GTQTLog.logger.error("Casing Tier cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(ELEProperties.getInstance(), Tire);
        return this;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(ELEProperties.getInstance().getKey(), getTire())
                .toString();
    }
}
