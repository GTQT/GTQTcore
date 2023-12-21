package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.TreeMap;

public class ELEProperties extends RecipeProperty<Integer> {

    public static final String KEY = "Tier";

    private static final TreeMap<Integer, String> registeredTire = new TreeMap<>();
    private static ELEProperties INSTANCE;

    private ELEProperties() {
        super(KEY, Integer.class);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("电解器等级：",
                castValue(value).toString()) + getTire(castValue(value)), x, y, color);
    }

    public static ELEProperties getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ELEProperties();
        }
        return INSTANCE;
    }

    private static String getTire(Integer Tire) {
        Map.Entry<Integer, String> mapEntry = registeredTire.ceilingEntry(Tire);

        if (mapEntry == null) {
            throw new IllegalArgumentException("Tier is above registered maximum Casing Tier.");
        }

        return String.format("%s", mapEntry.getValue());
    }

    public static void registeredTier(int tier, String shortName) {
        Validate.notNull(shortName);
        registeredTire.put(tier, shortName);
    }
}
