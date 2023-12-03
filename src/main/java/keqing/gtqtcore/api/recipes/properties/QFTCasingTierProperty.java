package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.TreeMap;

public class QFTCasingTierProperty extends RecipeProperty<Integer> {

    public static final String KEY = "machineLevel";
    private static final TreeMap<Integer, String> registeredQFTCasingTiers = new TreeMap<>();
    private static QFTCasingTierProperty INSTANCE;

    private QFTCasingTierProperty() {
        super(KEY, Integer.class);
    }

    public static QFTCasingTierProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new QFTCasingTierProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("epimorphism.machine.quantum_force_transformer.tier",
                castValue(value).toString()) + getQFTCasingTier(castValue(value)), x, y, color);
    }

    private static String getQFTCasingTier(Integer casingTier) {
        Map.Entry<Integer, String> mapEntry = registeredQFTCasingTiers.ceilingEntry(casingTier);

        if (mapEntry == null) {
            throw new IllegalArgumentException("Tier is above registered maximum Casing Tier.");
        }

        return String.format("%s", mapEntry.getValue());
    }

    public static void registerQFTCasingTier(int tier, String shortName) {
        Validate.notNull(shortName);
        registeredQFTCasingTiers.put(tier, shortName);
    }
}
