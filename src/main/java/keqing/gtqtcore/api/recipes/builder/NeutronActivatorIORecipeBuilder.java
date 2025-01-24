package keqing.gtqtcore.api.recipes.builder;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import keqing.gtqtcore.api.recipes.properties.MiningDrillProperties;
import keqing.gtqtcore.api.recipes.properties.NeutronActivatorIOPartProperty;
import keqing.gtqtcore.api.recipes.properties.NeutronActivatorPartProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;

public class NeutronActivatorIORecipeBuilder extends RecipeBuilder<NeutronActivatorIORecipeBuilder> {

    public NeutronActivatorIORecipeBuilder() {}

    public NeutronActivatorIORecipeBuilder(Recipe recipe, RecipeMap<NeutronActivatorIORecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public NeutronActivatorIORecipeBuilder(RecipeBuilder<NeutronActivatorIORecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public NeutronActivatorIORecipeBuilder copy() {
        return new NeutronActivatorIORecipeBuilder(this);
    }

    public int getPart() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(NeutronActivatorIOPartProperty.getInstance(), 0);
    }

    public NeutronActivatorIORecipeBuilder part(int Tier) {
        if (Tier <= 0) {
            GTQTLog.logger.error("Casing Tier cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(NeutronActivatorIOPartProperty.getInstance(), Tier);
        return this;
    }
    @Override
    public boolean applyProperty(@Nonnull String key, Object value) {
        if (key.equals(NeutronActivatorIOPartProperty.KEY)) {
            this.part(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(NeutronActivatorIOPartProperty.getInstance().getKey(), getPart())
                .toString();
    }

}
