package keqing.gtqtcore.api.recipes.properties;


import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.TreeMap;

public class SEProperty extends RecipeProperty<Integer> {

    public static final String KEY = "Motor";

    private static final TreeMap<Integer, String> registeredLaser = new TreeMap<>();
    private static SEProperty INSTANCE;

    private SEProperty() {
        super(KEY, Integer.class);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("轨道加速等级：",
                castValue(value).toString()) + getBN(castValue(value)), x, y, color);
    }

    public static SEProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SEProperty();
        }
        return INSTANCE;
    }

    private static String getBN(Integer Part) {
        Map.Entry<Integer, String> mapEntry = registeredLaser.ceilingEntry(Part);

        if (mapEntry == null) {
            throw new IllegalArgumentException("NULL Motor");
        }

        return String.format("%s", mapEntry.getValue());
    }

    public static void registeredMotor(int tier, String shortName) {
        Validate.notNull(shortName);
        registeredLaser.put(tier, shortName);
    }
}
