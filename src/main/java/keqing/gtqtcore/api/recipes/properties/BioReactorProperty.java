package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class BioReactorProperty extends RecipeProperty<Integer> {

    public static final String KEY = "Rate";

    private static BioReactorProperty INSTANCE;

    private BioReactorProperty() {
        super(KEY, Integer.class);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("最小反应浓度：%s",
                castValue(value)
                )
                , x, y, color);
    }

    public static BioReactorProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BioReactorProperty();
        }
        return INSTANCE;
    }
}
