package keqing.gtqtcore.api.recipes.builder;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import keqing.gtqtcore.api.recipes.properties.NeutronActivatorIOPartProperty;
import keqing.gtqtcore.api.recipes.properties.NeutronActivatorPartProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;

public class NeutronActivatorRecipeBuilder extends RecipeBuilder<NeutronActivatorRecipeBuilder> {

    public NeutronActivatorRecipeBuilder() {}

    public NeutronActivatorRecipeBuilder(Recipe recipe, RecipeMap<NeutronActivatorRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public NeutronActivatorRecipeBuilder(RecipeBuilder<NeutronActivatorRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public NeutronActivatorRecipeBuilder copy() {
        return new NeutronActivatorRecipeBuilder(this);
    }

    public int getPart() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.get(NeutronActivatorPartProperty.getInstance(), 0);
    }
    @Override
    public boolean applyPropertyCT(String key,Object value) {
        if (key.equals(NeutronActivatorPartProperty.KEY)) {
            this.part(((Number) value).intValue());
            return true;
        }
        return super.applyPropertyCT(key, value);
    }
    public NeutronActivatorRecipeBuilder part(int Tier) {
        if (Tier <= 0) {
            GTQTLog.logger.error("Casing Tier cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(NeutronActivatorPartProperty.getInstance(), Tier);
        return this;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(NeutronActivatorPartProperty.getInstance().getKey(), getPart())
                .toString();
    }

}
