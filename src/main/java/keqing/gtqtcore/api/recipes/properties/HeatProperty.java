package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class HeatProperty extends RecipeProperty<Integer> {

    public static final String KEY = "Heat";

    private static HeatProperty INSTANCE;

    private HeatProperty() {
        super(KEY, Integer.class);
    }

    public static HeatProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HeatProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("热源温度：%s H",
                castValue(value)), x, y, color);
    }
}