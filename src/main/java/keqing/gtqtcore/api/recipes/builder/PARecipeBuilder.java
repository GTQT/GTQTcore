package keqing.gtqtcore.api.recipes.builder;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import keqing.gtqtcore.api.recipes.properties.PAPartProperty;
import keqing.gtqtcore.api.recipes.properties.PCBPartProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PARecipeBuilder extends RecipeBuilder<PARecipeBuilder> {
    public PARecipeBuilder() {}

    public PARecipeBuilder(Recipe recipe, RecipeMap<PARecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public PARecipeBuilder(RecipeBuilder<PARecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public PARecipeBuilder copy() {
        return new PARecipeBuilder(this);
    }

    public int getPart() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(PAPartProperty.getInstance(), 0);
    }

    public PARecipeBuilder part(int Tier) {
        if (Tier <= 0) {
            GTQTLog.logger.error("Casing Tier cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(PAPartProperty.getInstance(), Tier);
        return this;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(PAPartProperty.getInstance().getKey(), getPart())
                .toString();
    }
}
