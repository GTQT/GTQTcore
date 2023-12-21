package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.TreeMap;

public class CACasingTierProperty extends RecipeProperty<Integer> {

    public static final String KEY = "machineLevel";
    private static final TreeMap<Integer, String> registeredCACasingTier = new TreeMap<>();
    private static CACasingTierProperty INSTANCE;

    private CACasingTierProperty() {
        super(KEY, Integer.class);
    }

    public static CACasingTierProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CACasingTierProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("gtqtcore.machine.component_assembly_line.tier",
                castValue(value).toString()) + getCACasingTier(castValue(value)), x, y, color);
    }

    private static String getCACasingTier(Integer casingTier) {
        Map.Entry<Integer, String> mapEntry = registeredCACasingTier.ceilingEntry(casingTier);

        if (mapEntry == null) {
            throw new IllegalArgumentException("Tier is above registered maximum Casing Tier.");
        }

        return String.format("%s", mapEntry.getValue());
    }

    public static void registerCACasingTier(int tier, String shortName) {
        Validate.notNull(shortName);
        registeredCACasingTier.put(tier, shortName);
    }
}
