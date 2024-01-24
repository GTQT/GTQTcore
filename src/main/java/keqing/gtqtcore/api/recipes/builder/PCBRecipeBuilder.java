package keqing.gtqtcore.api.recipes.builder;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ingredients.GTRecipeItemInput;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.ValidationResult;
import keqing.gtqtcore.api.recipes.properties.CACasingTierProperty;
import keqing.gtqtcore.api.recipes.properties.PACasingTierProperty;
import keqing.gtqtcore.api.recipes.properties.PCBPartProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;

public class PCBRecipeBuilder  extends RecipeBuilder<PCBRecipeBuilder> {

    public PCBRecipeBuilder() {}

    public PCBRecipeBuilder(Recipe recipe, RecipeMap<PCBRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public PCBRecipeBuilder(RecipeBuilder<PCBRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public PCBRecipeBuilder copy() {
        return new PCBRecipeBuilder(this);
    }

    public int getPart() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(PCBPartProperty.getInstance(), 0);
    }

    public PCBRecipeBuilder part(int Tier) {
        if (Tier <= 0) {
            GTQTLog.logger.error("Casing Tier cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(PCBPartProperty.getInstance(), Tier);
        return this;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(PCBPartProperty.getInstance().getKey(), getPart())
                .toString();
    }

}
