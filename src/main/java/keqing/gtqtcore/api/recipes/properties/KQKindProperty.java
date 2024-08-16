package keqing.gtqtcore.api.recipes.properties;


import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.TreeMap;

public class KQKindProperty extends RecipeProperty<Integer> {

    public static final String KEY = "KI";

    private static final TreeMap<Integer, String> registeredKI = new TreeMap<>();
    private static KQKindProperty INSTANCE;

    private KQKindProperty() {
        super(KEY, Integer.class);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("研究设备：",
                castValue(value).toString()) + getKI(castValue(value)), x, y, color);
    }

    public static KQKindProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new KQKindProperty();
        }
        return INSTANCE;
    }

    private static String getKI(Integer Part) {
        Map.Entry<Integer, String> mapEntry = registeredKI.ceilingEntry(Part);

        if (mapEntry == null) {
            throw new IllegalArgumentException("NULL KI");
        }

        return String.format("%s", mapEntry.getValue());
    }

    public static void registeredKI(int tier, String shortName) {
        Validate.notNull(shortName);
        registeredKI.put(tier, shortName);
    }
}
