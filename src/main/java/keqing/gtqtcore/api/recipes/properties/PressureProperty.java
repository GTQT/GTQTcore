package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import keqing.gtqtcore.api.utils.NumberFormattingUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nonnull;

public class PressureProperty extends RecipeProperty<Double> {

    public static final String KEY = "pressure";

    private static PressureProperty INSTANCE;

    private PressureProperty() {
        super(KEY, Double.class);
    }

    public static PressureProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PressureProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        Double casted = castValue(value);
        minecraft.fontRenderer.drawString(I18n.format(casted > 1 ? "gtqtcore.recipe.pressure" : "gtqtcore.recipe.vacuum",
                NumberFormattingUtil.formatDoubleToCompactString(casted)), x, y, color);
    }
}