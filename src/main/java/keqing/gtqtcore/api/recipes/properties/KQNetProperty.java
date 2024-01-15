package keqing.gtqtcore.api.recipes.properties;


import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.TreeMap;

public class KQNetProperty extends RecipeProperty<Integer> {

    public static final String KEY = "BN";

    private static final TreeMap<Integer, String> registeredNB = new TreeMap<>();
    private static KQNetProperty INSTANCE;

    private KQNetProperty() {
        super(KEY, Integer.class);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("项目序号：",
                castValue(value).toString()) + getBN(castValue(value)), x, y, color);
    }

    public static KQNetProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new KQNetProperty();
        }
        return INSTANCE;
    }

    private static String getBN(Integer Part) {
        Map.Entry<Integer, String> mapEntry = registeredNB.ceilingEntry(Part);

        if (mapEntry == null) {
            throw new IllegalArgumentException("NULL NB");
        }

        return String.format("%s", mapEntry.getValue());
    }

    public static void registeredNB(int tier, String shortName) {
        Validate.notNull(shortName);
        registeredNB.put(tier, shortName);
    }
}
