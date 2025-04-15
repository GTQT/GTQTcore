package keqing.gtqtcore.api.recipes.builder;


import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import keqing.gtqtcore.api.recipes.properties.ElectronBathProperties;

import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ElectronRecipeBuilder extends RecipeBuilder<ElectronRecipeBuilder> {

    public ElectronRecipeBuilder() {}

    public ElectronRecipeBuilder(Recipe recipe, RecipeMap<ElectronRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public ElectronRecipeBuilder(RecipeBuilder<ElectronRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public boolean applyPropertyCT(String key,Object value) {
        if (key.equals(ElectronBathProperties.KEY)) {
            this.tier(((Number) value).intValue());
            return true;
        }
        return super.applyPropertyCT(key, value);
    }

    @Override
    public ElectronRecipeBuilder copy() {
        return new ElectronRecipeBuilder(this);
    }

    public int getTire() {
        return (this.recipePropertyStorage == null) ? 0 :
                this.recipePropertyStorage.get(ElectronBathProperties.getInstance(), 0);
    }

    public ElectronRecipeBuilder tier(int Tire) {
        if (Tire <= 0) {
            GTQTLog.logger.error("Casing Tier cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(ElectronBathProperties.getInstance(), Tire);
        return this;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(ElectronBathProperties.getInstance().getKey(), getTire())
                .toString();
    }
}
