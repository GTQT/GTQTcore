package keqing.gtqtcore.api.recipes.builder;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import keqing.gtqtcore.api.recipes.properties.ParticleVelocityProperty;
import keqing.gtqtcore.api.recipes.properties.ScatteringProperty;


public class TargetRecipeBuilder extends RecipeBuilder<TargetRecipeBuilder> {

    public TargetRecipeBuilder() {/**/}

    public TargetRecipeBuilder(Recipe recipe, RecipeMap<TargetRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public TargetRecipeBuilder(RecipeBuilder<TargetRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public TargetRecipeBuilder copy() {
        return new TargetRecipeBuilder(this);
    }

    @Override
    public boolean applyPropertyCT(String key, Object value) {
        if (key.equals(ParticleVelocityProperty.KEY)) {
            this.EUToStart(((Number)value).intValue());
            return true;
        }
        if (key.equals(ScatteringProperty.KEY)) {
            this.Scattering(((Number) value).intValue());
            return true;
        }
        return super.applyPropertyCT(key, value);

    }

    //eu to start

    //散射截面
    public TargetRecipeBuilder EUToStart(int EUToStart) {
        if (EUToStart <= 0L) {
            GTLog.logger.error("EU to start cannot be less than or equal to 0", new IllegalArgumentException());
            this.recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(ParticleVelocityProperty.getInstance(), EUToStart);
        return this;
    }

    public TargetRecipeBuilder Scattering(int Scattering) {
        if (Scattering < 0) {
            GTLog.logger.error("Scattering cannot be less than 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(ScatteringProperty.getInstance(), Scattering);
        return this;
    }


}