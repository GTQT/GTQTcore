package keqing.gtqtcore.api.recipes.properties;


import gregtech.api.recipes.recipeproperties.RecipeProperty;
import keqing.gtqtcore.api.utils.GTQTKQnetHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.TreeMap;

public class KQKindProperty extends RecipeProperty<Integer> {

    public static final String KEY = "KI";

    private static KQKindProperty INSTANCE;

    private KQKindProperty() {
        super(KEY, Integer.class);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("设备"+
                GTQTKQnetHelper.getInfo(castValue(value))), x, y, color);
    }
    public static KQKindProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new KQKindProperty();
        }
        return INSTANCE;
    }
}
