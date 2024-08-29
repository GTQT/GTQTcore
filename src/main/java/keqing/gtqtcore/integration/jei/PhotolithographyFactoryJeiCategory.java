package keqing.gtqtcore.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static keqing.gtqtcore.GTQTCore.MODID;

public class PhotolithographyFactoryJeiCategory implements IRecipeCategory<PhotolithographyFactoryJei> {
    @Nonnull
    public String getModName() {
        return MODID;
    }
    @Nonnull
    private final IDrawable background;
    public PhotolithographyFactoryJeiCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(144, 120);
    }
    @Nonnull
    public String getUid() {
        return MODID+":PhotolithographyFactory";
    }

    @Nonnull
    public String getTitle() {
        return "PhotolithographyFactory";
    }

    @Nonnull
    public IDrawable getBackground() {
        return this.background;
    }

    @Nullable
    public IDrawable getIcon() {
        return null;
    }

    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull PhotolithographyFactoryJei recipeWrapper, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStackGroup = recipeLayout.getFluidStacks();

        for(int i = 0; i < recipeWrapper.getInputCount(); ++i) {
            int xPos = 18*i;
            itemStackGroup.init(i, true, xPos, 0);
        }

        //Fluid Inputs
        fluidStackGroup.init(0, true, 0, 24, 18, 18, 1, false, null);

        for(int i = 0; i < recipeWrapper.getOutputCount(); ++i) {

            int yPos = 48 + 18 * (i / 8);
            int xPos = i % 8 * 18;

            itemStackGroup.init(i+recipeWrapper.getInputCount(), false, xPos, yPos);
        }

        itemStackGroup.set(ingredients);
        fluidStackGroup.set(ingredients);
    }
}
