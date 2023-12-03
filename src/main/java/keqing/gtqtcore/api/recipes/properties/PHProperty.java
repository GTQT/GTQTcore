package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nonnull;

public class PHProperty extends RecipeProperty<Double> {
    public static final String KEY = "ph";

    private static PHProperty INSTANCE;

    private PHProperty() {
        super("ph", Double.class);
    }

    public static PHProperty getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PHProperty();
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("epimorphism.recipe.ph", String.format("%, .2f", castValue(value))), x, y, color);
    }
}
