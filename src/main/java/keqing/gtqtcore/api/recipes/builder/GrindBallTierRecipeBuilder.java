package keqing.gtqtcore.api.recipes.builder;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import keqing.gtqtcore.api.recipes.properties.GrindBallTierProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;

public class GrindBallTierRecipeBuilder extends RecipeBuilder<GrindBallTierRecipeBuilder> {
    public GrindBallTierRecipeBuilder() {/**/}

    public GrindBallTierRecipeBuilder(Recipe recipe, RecipeMap<GrindBallTierRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public GrindBallTierRecipeBuilder(RecipeBuilder<GrindBallTierRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public GrindBallTierRecipeBuilder copy() {
        return new GrindBallTierRecipeBuilder(this);
    }

    public int getLevel() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.get(GrindBallTierProperty.getInstance(), 0);
    }

    @Override
    public boolean applyPropertyCT(String key,Object value) {
        if (key.equals(GrindBallTierProperty.KEY)) {
            this.grindBallTier(((Number) value).intValue());
            return true;
        }
        return super.applyPropertyCT(key, value);
    }

    public GrindBallTierRecipeBuilder grindBallTier(int level) {
        if (level <= 0) {
            GTQTLog.logger.error("Grind Ball Tier cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(GrindBallTierProperty.getInstance(), level);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(GrindBallTierProperty.getInstance().getKey(), getLevel())
                .toString();
    }
}