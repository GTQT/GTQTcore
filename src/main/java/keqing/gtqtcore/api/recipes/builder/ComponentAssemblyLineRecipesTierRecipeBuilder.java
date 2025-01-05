package keqing.gtqtcore.api.recipes.builder;


import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import keqing.gtqtcore.api.recipes.properties.ComponentAssemblyLineRecipesTierProperty;
import keqing.gtqtcore.api.recipes.properties.CasingTierProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;

public class ComponentAssemblyLineRecipesTierRecipeBuilder extends RecipeBuilder<ComponentAssemblyLineRecipesTierRecipeBuilder> {
    public ComponentAssemblyLineRecipesTierRecipeBuilder() {}

    public ComponentAssemblyLineRecipesTierRecipeBuilder(Recipe recipe, RecipeMap<ComponentAssemblyLineRecipesTierRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public ComponentAssemblyLineRecipesTierRecipeBuilder(RecipeBuilder<ComponentAssemblyLineRecipesTierRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public ComponentAssemblyLineRecipesTierRecipeBuilder copy() {
        return new ComponentAssemblyLineRecipesTierRecipeBuilder(this);
    }

    public int getCATier() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(ComponentAssemblyLineRecipesTierProperty.getInstance(), 0);
    }

    @Override
    public boolean applyProperty(@Nonnull String key, Object value) {
        if (key.equals(CasingTierProperty.KEY)) {
            this.CasingTier(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    public ComponentAssemblyLineRecipesTierRecipeBuilder CasingTier(int Tier) {
        if (Tier <= 0) {
            GTQTLog.logger.error("Casing Tier cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(ComponentAssemblyLineRecipesTierProperty.getInstance(), Tier);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(ComponentAssemblyLineRecipesTierProperty.getInstance().getKey(), getCATier())
                .toString();
    }
}
