package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.TreeMap;

public class EnzymesReactionProperty extends RecipeProperty<Integer> {

    public static final String KEY = "Rate";

    private static final TreeMap<Integer, String> registeredTire = new TreeMap<>();
    private static EnzymesReactionProperty INSTANCE;

    private EnzymesReactionProperty() {
        super(KEY, Integer.class);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("性状因子：",
                castValue(value).toString()) + getTire(castValue(value)), x, y, color);
    }

    public static EnzymesReactionProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EnzymesReactionProperty();
        }
        return INSTANCE;
    }

    private static String getTire(Integer Rate) {
        Map.Entry<Integer, String> mapEntry = registeredTire.ceilingEntry(Rate);

        if (mapEntry == null) {
            throw new IllegalArgumentException("Tier is above registered maximum Casing Tier.");
        }

        return String.format("%s", mapEntry.getValue());
    }

    public static void registeredRate(int Rate, String shortName) {
        Validate.notNull(shortName);
        registeredTire.put(Rate, shortName);
    }
}
