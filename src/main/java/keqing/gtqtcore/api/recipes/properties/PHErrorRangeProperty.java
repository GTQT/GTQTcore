package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nonnull;

public class PHErrorRangeProperty extends RecipeProperty<Double> {
    public static final String KEY = "ph_error_range";

    private static PHErrorRangeProperty INSTANCE;

    private PHErrorRangeProperty() {
        super("ph_error_range", Double.class);
    }

    public static PHErrorRangeProperty getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PHErrorRangeProperty();
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("gtqtcore.recipe.ph_error_range", String.format("%, .2f", castValue(value))), x, y, color);
    }
}
