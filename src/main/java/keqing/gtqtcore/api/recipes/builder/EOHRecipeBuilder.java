package keqing.gtqtcore.api.recipes.builder;


import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import keqing.gtqtcore.api.recipes.properties.GeneratorProperty;
import keqing.gtqtcore.api.recipes.properties.SpacetimeCompressionProperty;
import keqing.gtqtcore.api.recipes.properties.SuccessChanceProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class EOHRecipeBuilder extends RecipeBuilder<EOHRecipeBuilder> {
    public EOHRecipeBuilder() {
    }

    public EOHRecipeBuilder(Recipe recipe, RecipeMap<EOHRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public EOHRecipeBuilder(RecipeBuilder<EOHRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public EOHRecipeBuilder copy() {
        return new EOHRecipeBuilder(this);
    }

    public int getSpacetimeCompressionTier() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.get(SpacetimeCompressionProperty.getInstance(), 0);
    }

    public int getSuccessChance() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.get(SuccessChanceProperty.getInstance(), 0);
    }

    public long getGenerator() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.get(GeneratorProperty.getInstance(), 0L);
    }

    @Override
    public boolean applyPropertyCT(String key, Object value) {
        if (key.equals(SpacetimeCompressionProperty.KEY)) {
            this.SpacetimeCompressionTier(((Number) value).intValue());
            return true;
        }
        if (key.equals(SuccessChanceProperty.KEY)) {
            this.SuccessChance(((Number) value).intValue());
            return true;
        }
        if (key.equals(GeneratorProperty.KEY)) {
            this.Generator(((Number) value).longValue());
            return true;
        }
        return super.applyPropertyCT(key, value);
    }

    public EOHRecipeBuilder SpacetimeCompressionTier(int Tier) {
        if (Tier <= 0) {
            GTQTLog.logger.error("Casing Tier cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(SpacetimeCompressionProperty.getInstance(), Tier);
        return this;
    }

    //SuccessChanceProperty
    public EOHRecipeBuilder SuccessChance(int SuccessChance) {
        if (SuccessChance <= 0) {
            GTQTLog.logger.error("Success Chance cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(SuccessChanceProperty.getInstance(), SuccessChance);
        return this;
    }
    //GeneratorProperty
    public EOHRecipeBuilder Generator(long EUt) {
        if (EUt <= 0) {
            GTQTLog.logger.error("EUt cannot be less than or equal to 0", new IllegalArgumentException());
        }
        this.applyProperty(GeneratorProperty.getInstance(), EUt);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(SpacetimeCompressionProperty.getInstance().getKey(), getSpacetimeCompressionTier())
                .append(SuccessChanceProperty.getInstance().getKey(), getSuccessChance())
                .append(GeneratorProperty.getInstance().getKey(), getGenerator())
                .toString();
    }
}
