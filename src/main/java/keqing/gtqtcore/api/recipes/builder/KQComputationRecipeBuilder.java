package keqing.gtqtcore.api.recipes.builder;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.ComputationProperty;
import gregtech.api.recipes.recipeproperties.TotalComputationProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import keqing.gtqtcore.api.recipes.properties.ELEProperties;
import keqing.gtqtcore.api.recipes.properties.KQKindProperty;
import keqing.gtqtcore.api.recipes.properties.KQNetProperty;
import keqing.gtqtcore.api.utils.GTQTLog;


public class KQComputationRecipeBuilder extends RecipeBuilder<KQComputationRecipeBuilder> {

    public KQComputationRecipeBuilder() {/**/}

    public KQComputationRecipeBuilder(Recipe recipe, RecipeMap<KQComputationRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public KQComputationRecipeBuilder(RecipeBuilder<KQComputationRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public KQComputationRecipeBuilder copy() {
        return new KQComputationRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(String key, Object value) {
        if (key.equals(ComputationProperty.KEY)) {
            this.CWUt(((Number) value).intValue());
            return true;
        }
        if (key.equals(TotalComputationProperty.KEY)) {
            this.totalCWU(((Number) value).intValue());
            return true;
        }
        if (key.equals(KQNetProperty.KEY)) {
            this.NB(((Number) value).intValue());
            return true;
        }
        if (key.equals(ELEProperties.KEY)) {
            this.Tier(((Number) value).intValue());
            return true;
        }
        if (key.equals(KQKindProperty.KEY)) {
            this.KI(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    public KQComputationRecipeBuilder KI(int KI) {
        if (KI < 0) {
            GTQTLog.logger.error("NULL KI", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(KQKindProperty.getInstance(), KI);
        return this;
    }

    public KQComputationRecipeBuilder Tier(int Tier) {
        if (Tier <= 0) {
            GTQTLog.logger.error("NULL Tier", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(ELEProperties.getInstance(), Tier);
        return this;
    }

    public KQComputationRecipeBuilder NB(int NB) {
        if (NB <= 0) {
            GTQTLog.logger.error("NULL NB", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(KQNetProperty.getInstance(), NB);
        return this;
    }

    public KQComputationRecipeBuilder CWUt(int cwut) {
        if (cwut < 0) {
            GTLog.logger.error("CWU/t cannot be less than 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(ComputationProperty.getInstance(), cwut);
        return this;
    }

    public KQComputationRecipeBuilder totalCWU(int totalCWU) {
        if (totalCWU < 0) {
            GTLog.logger.error("Total CWU cannot be less than 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(TotalComputationProperty.getInstance(), totalCWU);
        return duration(totalCWU);
    }
}