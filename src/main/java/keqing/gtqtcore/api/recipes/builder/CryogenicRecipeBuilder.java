package keqing.gtqtcore.api.recipes.builder;

import gregtech.api.recipes.RecipeBuilder;
import keqing.gtqtcore.api.recipes.properties.CryogenicEnvironmentProperty;

public class CryogenicRecipeBuilder<R extends RecipeBuilder<R>> extends RecipeBuilder<R> {

    public CryogenicRecipeBuilder() {
    }

    public CryogenicRecipeBuilder(CryogenicRecipeBuilder<R> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public boolean applyProperty( String key, Object value) {
        if (key.equals(CryogenicEnvironmentProperty.KEY)) {
            if (value == null) {
                this.cryogenicEnvironment(false);
            } else {
                this.cryogenicEnvironment((Boolean) value);
            }
            return true;
        }
        return super.applyProperty(key, value);
    }

    public CryogenicRecipeBuilder<R> cryogenicEnvironment() {
        return cryogenicEnvironment(true);
    }

    public CryogenicRecipeBuilder<R> cryogenicEnvironment(boolean requiresCryogenic) {
        this.applyProperty(CryogenicEnvironmentProperty.getInstance(), requiresCryogenic);
        return this;
    }
}