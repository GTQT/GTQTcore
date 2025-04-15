package keqing.gtqtcore.api.recipes.builder;


import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import keqing.gtqtcore.api.recipes.properties.PCBFactoryBioUpgradeProperty;
import keqing.gtqtcore.api.recipes.properties.PCBFactoryProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;


public class PCBFactoryRecipeBuilder extends RecipeBuilder<PCBFactoryRecipeBuilder> {

    public PCBFactoryRecipeBuilder() {}

    public PCBFactoryRecipeBuilder(Recipe recipe,
                                   RecipeMap<PCBFactoryRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public PCBFactoryRecipeBuilder(PCBFactoryRecipeBuilder builder) {
        super(builder);
    }

    @Override
    public PCBFactoryRecipeBuilder copy() {
        return new PCBFactoryRecipeBuilder(this);
    }

    @Override
    public boolean applyPropertyCT(String key,Object value) {
        if (key.equals(PCBFactoryProperty.KEY)) {
            tier(((Number) value).intValue());
            return true;
        }

        if (key.equals(PCBFactoryBioUpgradeProperty.KEY)) {
            isBioUpgrade(((Number) value).intValue());
            return true;
        }
        return super.applyPropertyCT(key, value);
    }

    public PCBFactoryRecipeBuilder tier(int tier) {
        if (tier < 0) {
            GTQTLog.logger.error("PCB Factory Tier cannot be less than 0", new IllegalArgumentException());
            this.recipeStatus = EnumValidationResult.INVALID;
        }
        applyProperty(PCBFactoryProperty.getInstance(), tier);
        return this;
    }

    public PCBFactoryRecipeBuilder isBioUpgrade(int isBioUpgrade) {
        applyProperty(PCBFactoryBioUpgradeProperty.getInstance(), isBioUpgrade);
        return this;
    }

    public int getPCBFactoryTier() {
        return this.recipePropertyStorage == null ? 0 : this.recipePropertyStorage
                .get(PCBFactoryProperty.getInstance(), 0);
    }

    public int getPCBFactoryBioUpgradeTier() {
        return this.recipePropertyStorage == null ? 0 : this.recipePropertyStorage
                .get(PCBFactoryBioUpgradeProperty.getInstance(), 0);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(PCBFactoryProperty.getInstance().getKey(), getPCBFactoryTier())
                .append(PCBFactoryBioUpgradeProperty.getInstance().getKey(), getPCBFactoryBioUpgradeTier())
                .toString();
    }

}
