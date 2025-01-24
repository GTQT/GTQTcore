package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class MachineTierProperty extends RecipeProperty<Integer> {

    public static final String KEY = "MachineTier";
    private static MachineTierProperty INSTANCE;

    private MachineTierProperty() {
        super(KEY, Integer.class);
    }

    public static MachineTierProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MachineTierProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("设备等级：%s",
                castValue(value)), x, y, color);
    }


}
