package keqing.gtqtcore.api.recipes.builder;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.properties.impl.PrimitiveProperty;
import gregtech.api.util.ValidationResult;
import keqing.gtqtcore.api.recipes.properties.HeatProperty;

public class HeatRecipeBuilder extends RecipeBuilder<HeatRecipeBuilder> {
    public HeatRecipeBuilder() {
    }

    public HeatRecipeBuilder(Recipe recipe, RecipeMap<HeatRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public HeatRecipeBuilder(RecipeBuilder<HeatRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    public HeatRecipeBuilder copy() {
        return new HeatRecipeBuilder(this);
    }

    @Override
    public boolean applyPropertyCT(String key,Object value) {
        if (key.equals(HeatProperty.KEY)) {
            this.Heat(((Number) value).intValue());
            return true;
        }
        return super.applyPropertyCT(key, value);
    }

    public HeatRecipeBuilder Heat(int Heat) {
        this.applyProperty(HeatProperty.getInstance(), Heat);
        return this;
    }

    public ValidationResult<Recipe> build() {
        this.EUt(1);
        this.applyProperty(PrimitiveProperty.getInstance(), true);
        return super.build();
    }
}
