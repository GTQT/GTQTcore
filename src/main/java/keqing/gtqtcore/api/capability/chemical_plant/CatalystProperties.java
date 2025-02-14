package keqing.gtqtcore.api.capability.chemical_plant;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CatalystProperties extends RecipeProperty<ItemStack> {

    public static final String KEY = "catalyst";

    private static CatalystProperties INSTANCE;

    private CatalystProperties() {
        super(KEY, ItemStack.class);
    }

    public static CatalystProperties getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CatalystProperties();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("所需催化剂：%s",
                ((ItemStack) value).getDisplayName()), x, y, color);
    }
}