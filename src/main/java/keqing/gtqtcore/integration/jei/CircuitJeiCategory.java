package keqing.gtqtcore.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static keqing.gtqtcore.GTQTCore.MODID;

public class CircuitJeiCategory implements IRecipeCategory<CircuitJei> {
    @Nonnull
    public String getModName() {
        return MODID;
    }
    @Nonnull
    private final IDrawable background;
    public CircuitJeiCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(144, 120);
    }
    @Nonnull
    public String getUid() {
        return MODID+":Circuit";
    }

    @Nonnull
    public String getTitle() {
        return "Circuit";
    }

    @Nonnull
    public IDrawable getBackground() {
        return this.background;
    }

    @Nullable
    public IDrawable getIcon() {
        return null;
    }

    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull CircuitJei recipeWrapper, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        int count=0;
        for(int i = 0; i < recipeWrapper.getInputCount(); ++i) {
            int xPos = 18*i;
            itemStackGroup.init(i, true, xPos, 10);
            count++;
        }

        for(int i = 0; i < recipeWrapper.getOutputCount(); ++i) {

            int xPos = i % 8 * 18;
            itemStackGroup.init(i+count, false, xPos, 50);
        }

        itemStackGroup.set(ingredients);
    }
    public void drawExtras(Minecraft minecraft) {
        minecraft.fontRenderer.drawString("同系列电路：", 0, 0, 0x111111);
        minecraft.fontRenderer.drawString("同等级电路：", 0, 40, 0x111111);
    }
}
