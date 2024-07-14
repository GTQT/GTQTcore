package keqing.gtqtcore.api.recipes.builder;


import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import keqing.gtqtcore.api.recipes.properties.CACasingTierProperty;
import keqing.gtqtcore.api.recipes.properties.ELEProperties;

import keqing.gtqtcore.api.recipes.properties.MDProperties;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;

public class MDRecipeBuilder  extends RecipeBuilder<MDRecipeBuilder> {

    public MDRecipeBuilder() {}

    public MDRecipeBuilder(Recipe recipe, RecipeMap<MDRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public MDRecipeBuilder(RecipeBuilder<MDRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public MDRecipeBuilder copy() {
        return new MDRecipeBuilder(this);
    }

    public int getTire() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(MDProperties.getInstance(), 0);
    }

    public MDRecipeBuilder tier(int Tire) {
        this.applyProperty(MDProperties.getInstance(), Tire);
        return this;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(MDProperties.getInstance().getKey(), getTire())
                .toString();
    }
}
