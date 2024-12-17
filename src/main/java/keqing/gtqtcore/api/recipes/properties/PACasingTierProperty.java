package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.ComputationProperty;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.TreeMap;

public class PACasingTierProperty extends RecipeProperty<Integer> {

    public static final String KEY = "machineLevel";
    private static PACasingTierProperty INSTANCE;

    private PACasingTierProperty() {
        super(KEY, Integer.class);
    }

    public static PACasingTierProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PACasingTierProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("外壳等级：%s",
                castValue(value)), x, y, color);
    }


}
