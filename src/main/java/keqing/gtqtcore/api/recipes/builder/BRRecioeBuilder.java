package keqing.gtqtcore.api.recipes.builder;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import keqing.gtqtcore.api.recipes.properties.BRProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BRRecioeBuilder  extends RecipeBuilder<BRRecioeBuilder> {

    public BRRecioeBuilder() {}

    public BRRecioeBuilder(Recipe recipe, RecipeMap<BRRecioeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public BRRecioeBuilder(RecipeBuilder<BRRecioeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public BRRecioeBuilder copy() {
        return new BRRecioeBuilder(this);
    }

    public int getRate() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(BRProperty.getInstance(), 0);
    }

    public BRRecioeBuilder rate(int Tire) {
        if (Tire <= 0) {
            GTQTLog.logger.error("Casing Tier cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(BRProperty.getInstance(), Tire);
        return this;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(BRProperty.getInstance().getKey(), getRate())
                .toString();
    }
}
