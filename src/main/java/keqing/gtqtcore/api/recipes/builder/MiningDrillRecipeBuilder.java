package keqing.gtqtcore.api.recipes.builder;


import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;

import keqing.gtqtcore.api.recipes.properties.MiningDrillProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class MiningDrillRecipeBuilder extends RecipeBuilder<MiningDrillRecipeBuilder> {

    public MiningDrillRecipeBuilder() {}

    public MiningDrillRecipeBuilder(Recipe recipe, RecipeMap<MiningDrillRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public MiningDrillRecipeBuilder(RecipeBuilder<MiningDrillRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public MiningDrillRecipeBuilder copy() {
        return new MiningDrillRecipeBuilder(this);
    }

    public int getTire() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(MiningDrillProperties.getInstance(), 0);
    }

    public MiningDrillRecipeBuilder tier(int Tire) {
        this.applyProperty(MiningDrillProperties.getInstance(), Tire);
        return this;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(MiningDrillProperties.getInstance().getKey(), getTire())
                .toString();
    }
}