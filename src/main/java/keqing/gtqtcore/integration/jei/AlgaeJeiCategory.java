package keqing.gtqtcore.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static keqing.gtqtcore.GTQTCore.MODID;

public class AlgaeJeiCategory implements IRecipeCategory<AlgaeJei> {
    @Nonnull
    private final IDrawable background;

    public AlgaeJeiCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(144, 120);
    }

    @Nonnull
    public String getModName() {
        return MODID;
    }

    @Nonnull
    public String getUid() {
        return MODID + ":Algae";
    }

    @Nonnull
    public String getTitle() {
        return "Algae";
    }

    @Nonnull
    public IDrawable getBackground() {
        return this.background;
    }

    @Nullable
    public IDrawable getIcon() {
        return null;
    }

    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull AlgaeJei recipeWrapper, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();

        int count = 0;
        for (int i = 0; i < recipeWrapper.getInputCount(); ++i) {
            int yPos = 18 * i;
            itemStackGroup.init(i, true, 0, yPos);
            count++;
        }

        itemStackGroup.init(count, false, 18, 0);
        itemStackGroup.init(count + 1, false, 36, 18);
        itemStackGroup.init(count + 2, false, 54, 36);
        itemStackGroup.init(count + 3, false, 72, 54);
        itemStackGroup.init(count + 4, false, 90, 72);

        itemStackGroup.set(ingredients);
    }
}
