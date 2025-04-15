package keqing.gtqtcore.api.recipes.builder;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import keqing.gtqtcore.api.recipes.properties.CatalystProperties;
import keqing.gtqtcore.api.recipes.properties.ChemicalPlantProperties;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ChemicalPlantBuilder extends RecipeBuilder<ChemicalPlantBuilder> {

    public ChemicalPlantBuilder() {}
    public ChemicalPlantBuilder(Recipe recipe, RecipeMap<ChemicalPlantBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public ChemicalPlantBuilder(RecipeBuilder<ChemicalPlantBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public ChemicalPlantBuilder copy() {
        return new ChemicalPlantBuilder(this);
    }

    public int getLevel() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.get(ChemicalPlantProperties.getInstance(), 0);
    }
    public ItemStack getCatalyst() {
        return this.recipePropertyStorage == null ? ItemStack.EMPTY :
                this.recipePropertyStorage.get(CatalystProperties.getInstance(), ItemStack.EMPTY);
    }
    @Override
    public boolean applyPropertyCT(String key,Object value) {
        if (key.equals(ChemicalPlantProperties.KEY)) {
            this.recipeLevel(((Number) value).intValue());
            return true;
        }
        if (key.equals(CatalystProperties.KEY)) {
            this.Catalyst((ItemStack) value);
            return true;
        }
        return super.applyPropertyCT(key, value);
    }

    public ChemicalPlantBuilder recipeLevel(int level) {
        if (level <= 0) {
            GTLog.logger.error("Blast Furnace Temperature cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(ChemicalPlantProperties.getInstance(), level);
        return this;
    }

    public ChemicalPlantBuilder Catalyst(ItemStack catalyst)
    {
        if (catalyst==ItemStack.EMPTY) {
            GTLog.logger.error("Blast Furnace Temperature cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(CatalystProperties.getInstance(), catalyst);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(ChemicalPlantProperties.getInstance().getKey(), getLevel())
                .append(CatalystProperties.getInstance().getKey(), getCatalyst())
                .toString();
    }
}