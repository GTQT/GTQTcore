package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.TreeMap;

public class NuclearProperties extends RecipeProperty<Integer> {

    public static final String KEY = "minTemp";

    private static final TreeMap<Integer, String> registeredTire = new TreeMap<>();
    private static NuclearProperties INSTANCE;

    private NuclearProperties() {
        super(KEY, Integer.class);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("产出温度：",
                castValue(value).toString()) + getminTemp(castValue(value)), x, y, color);
    }

    public static NuclearProperties getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NuclearProperties();
        }
        return INSTANCE;
    }

    private static String getminTemp(Integer minTemp) {
        Map.Entry<Integer, String> mapEntry = registeredTire.ceilingEntry(minTemp);

        if (mapEntry == null) {
            throw new IllegalArgumentException("minTemp is above registered maximum minTemp.");
        }

        return String.format("%s", mapEntry.getValue());
    }

    public static void registeredminTemp(int minTemp, String shortName) {
        Validate.notNull(shortName);
        registeredTire.put(minTemp, shortName);
    }
}
