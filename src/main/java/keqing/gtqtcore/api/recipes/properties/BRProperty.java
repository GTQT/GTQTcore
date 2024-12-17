package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.TreeMap;

public class BRProperty extends RecipeProperty<Integer> {

    public static final String KEY = "Rate";

    private static BRProperty INSTANCE;

    private BRProperty() {
        super(KEY, Integer.class);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("最小反应浓度：%s",
                castValue(value)
                )
                , x, y, color);
    }

    public static BRProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BRProperty();
        }
        return INSTANCE;
    }
}
