package keqing.gtqtcore.integration.jei;

import gregtech.api.gui.GuiTextures;
import gregtech.api.util.GTStringUtils;
import gregtech.integration.jei.basic.BasicRecipeCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import static keqing.gtqtcore.GTQTCore.MODID;


public class VoidMinerRecipeCategory extends BasicRecipeCategory<VoidMinerRecipeWrapper, VoidMinerRecipeWrapper> {

    private static final int SLOT_WIDTH = 18;
    private static final int SLOT_HEIGHT = 18;
    protected final IDrawable slot;
    protected int outputCount;
    protected int tier;

    public VoidMinerRecipeCategory(IGuiHelper guiHelper) {
        super("void_miner",
                "recipemap.void_miner.name",
                guiHelper.createBlankDrawable(176+18*6+4, 166+18*3+9),
                guiHelper);

        this.slot = guiHelper.drawableBuilder(GuiTextures.SLOT.imageLocation,
                        0, 0, SLOT_WIDTH, SLOT_HEIGHT)
                .setTextureSize(SLOT_WIDTH, SLOT_HEIGHT)
                .build();
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout,
                          VoidMinerRecipeWrapper recipeWrapper,
                          IIngredients ingredients) {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        for (int i = 0; i < recipeWrapper.getOutputCount(); ++i) {
            int xPos = 18 * (i % 16);
            itemStackGroup.init(i, true, xPos, 10 + i / 16 * 18);
        }

        itemStackGroup.set(ingredients);
        this.outputCount = recipeWrapper.getOutputCount();
        this.tier = recipeWrapper.getTier();

    }

    @Override
    public IRecipeWrapper getRecipeWrapper(VoidMinerRecipeWrapper recipeWrapper) {
        return recipeWrapper;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        GTStringUtils.drawCenteredStringWithCutoff(getNameByTier(this.tier), minecraft.fontRenderer, 176);
    }


    @Override
    public String getModName() {
        return MODID;
    }

    private String getNameByTier(int tier) {
        if (tier == 0) {
            return I18n.format("gtqtcore.universal.recipe_vm_tier.1");
        } else if (tier == 1) {
            return I18n.format("gtqtcore.universal.recipe_vm_tier.2");
        } else if (tier == 2) {
            return I18n.format("gtqtcore.universal.recipe_vm_tier.3");
        } else {
            return null;
        }
    }

}