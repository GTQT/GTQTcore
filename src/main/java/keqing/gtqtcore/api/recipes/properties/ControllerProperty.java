package keqing.gtqtcore.api.recipes.properties;


import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class ControllerProperty extends RecipeProperty<Integer> {

    public static final String KEY = "ControllerTier";

    private static ControllerProperty INSTANCE;

    private ControllerProperty() {
        super(KEY, Integer.class);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("设备等级：%s",
                castValue(value)), x, y, color);
    }

    public static ControllerProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ControllerProperty();
        }
        return INSTANCE;
    }
}
