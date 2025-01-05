package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class CasingTierProperty extends RecipeProperty<Integer> {

    public static final String KEY = "CasingTier";
    private static CasingTierProperty INSTANCE;

    private CasingTierProperty() {
        super(KEY, Integer.class);
    }

    public static CasingTierProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CasingTierProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("外壳等级：%s",
                castValue(value)), x, y, color);
    }


}
