package keqing.gtqtcore.api.recipes.builder;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import keqing.gtqtcore.api.recipes.properties.BioReactorProperty;
import keqing.gtqtcore.api.recipes.properties.ElectronBathProperties;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;

public class BioRecipeRecipeBuilder extends RecipeBuilder<BioRecipeRecipeBuilder> {

    public BioRecipeRecipeBuilder() {}

    public BioRecipeRecipeBuilder(Recipe recipe, RecipeMap<BioRecipeRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public BioRecipeRecipeBuilder(RecipeBuilder<BioRecipeRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public BioRecipeRecipeBuilder copy() {
        return new BioRecipeRecipeBuilder(this);
    }

    public int getRate() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.get(BioReactorProperty.getInstance(), 0);
    }
    @Override
    public boolean applyPropertyCT(String key,Object value) {
        if (key.equals(BioReactorProperty.KEY)) {
            this.rate(((Number) value).intValue());
            return true;
        }
        return super.applyPropertyCT(key, value);
    }
    public BioRecipeRecipeBuilder rate(int Tire) {
        if (Tire <= 0) {
            GTQTLog.logger.error("Casing Tier cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(BioReactorProperty.getInstance(), Tire);
        return this;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(BioReactorProperty.getInstance().getKey(), getRate())
                .toString();
    }
}
