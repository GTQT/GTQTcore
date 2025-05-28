package keqing.gtqtcore.api.recipes.builder;


import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import keqing.gtqtcore.api.recipes.properties.CatalystProperties;
import keqing.gtqtcore.api.recipes.properties.QFTCasingTierProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class QFTRecipeBuilder extends RecipeBuilder<QFTRecipeBuilder> {
    public QFTRecipeBuilder() {}

    public QFTRecipeBuilder(Recipe recipe, RecipeMap<QFTRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public QFTRecipeBuilder(RecipeBuilder<QFTRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public QFTRecipeBuilder copy() {
        return new QFTRecipeBuilder(this);
    }

    public int getTier() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.get(QFTCasingTierProperty.getInstance(), 0);
    }
    public ItemStack getCatalyst() {
        return this.recipePropertyStorage == null ? ItemStack.EMPTY :
                this.recipePropertyStorage.get(CatalystProperties.getInstance(), ItemStack.EMPTY);
    }
    @Override
    public boolean applyPropertyCT(String key,Object value) {
        if (key.equals(QFTCasingTierProperty.KEY)) {
            this.CasingTier(((Number) value).intValue());
            return true;
        }
        if (key.equals(CatalystProperties.KEY)) {
            this.Catalyst((ItemStack) value);
            return true;
        }
        return super.applyPropertyCT(key, value);
    }

    public QFTRecipeBuilder CasingTier(int Tier) {
        if (Tier <= 0) {
            GTQTLog.logger.error("Casing Tier cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(QFTCasingTierProperty.getInstance(), Tier);
        return this;
    }
    public QFTRecipeBuilder Catalyst(ItemStack catalyst)
    {
        if (catalyst==ItemStack.EMPTY) {
            GTLog.logger.error("Catalyst can not be empty!", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(CatalystProperties.getInstance(), catalyst);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(QFTCasingTierProperty.getInstance().getKey(), getTier())
                .append(CatalystProperties.getInstance().getKey(), getCatalyst())
                .toString();
    }
}
