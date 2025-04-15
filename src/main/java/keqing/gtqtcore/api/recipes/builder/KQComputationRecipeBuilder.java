package keqing.gtqtcore.api.recipes.builder;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.ComputationProperty;
import gregtech.api.recipes.recipeproperties.TotalComputationProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import keqing.gtqtcore.api.recipes.properties.*;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;


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
        if (key.equals(ResearchSystemKindProperty.KEY)) {
            this.NB(((Number) value).intValue());
            return true;
        }
        if (key.equals(MachineTierProperty.KEY)) {
            this.Tier(((Number) value).intValue());
            return true;
        }
        if (key.equals(ResearchSystemMachineProperty.KEY)) {
            this.KI(((Number) value).intValue());
            return true;
        }
        return super.applyPropertyCT(key, value);
    }

    public KQComputationRecipeBuilder KI(int KI) {
        if (KI < 0) {
            GTQTLog.logger.error("NULL KI", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(ResearchSystemMachineProperty.getInstance(), KI);
        return this;
    }

    public KQComputationRecipeBuilder Tier(int Tier) {
        if (Tier <= 0) {
            GTQTLog.logger.error("NULL Tier", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(MachineTierProperty.getInstance(), Tier);
        return this;
    }

    public KQComputationRecipeBuilder NB(int NB) {
        if (NB <= 0) {
            GTQTLog.logger.error("NULL NB", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(ResearchSystemKindProperty.getInstance(), NB);
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

    public int getKI() {
        return this.recipePropertyStorage == null ? 0 : this.recipePropertyStorage
                .getRecipePropertyValue(ResearchSystemMachineProperty.getInstance(), 0);
    }

    public int getTier() {
        return this.recipePropertyStorage == null ? 0 : this.recipePropertyStorage
                .getRecipePropertyValue(MachineTierProperty.getInstance(), 0);
    }
    public int getCWUt() {
        return this.recipePropertyStorage == null ? 0 : this.recipePropertyStorage
                .getRecipePropertyValue(ComputationProperty.getInstance(), 0);
    }
    public int gettotalCWU() {
        return this.recipePropertyStorage == null ? 0 : this.recipePropertyStorage
                .getRecipePropertyValue(TotalComputationProperty.getInstance(), 0);
    }
    public String toString() {
        return (new ToStringBuilder(this))
                .appendSuper(super.toString())
                .append(ResearchSystemMachineProperty.getInstance().getKey(), getKI())
                .append(MachineTierProperty.getInstance().getKey(), getTier())
                .append(ComputationProperty.getInstance().getKey(), getCWUt())
                .append(TotalComputationProperty.getInstance().getKey(), gettotalCWU())
                .toString();
    }
}