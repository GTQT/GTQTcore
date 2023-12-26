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
    private static final TreeMap<Integer, String> registeredPACasingTiers = new TreeMap<>();
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
        minecraft.fontRenderer.drawString(I18n.format("gtqtcore.machine.precise_assembler.tier",
                castValue(value).toString()) + getPACasingTier(castValue(value)), x, y, color);
    }

    private static String getPACasingTier(Integer casingTier) {
        Map.Entry<Integer, String> mapEntry = registeredPACasingTiers.ceilingEntry(casingTier);

        if (mapEntry == null) {
            throw new IllegalArgumentException("Tier is above registered maximum Casing Tier.");
        }

        return String.format("%s", mapEntry.getValue());
    }

    public static void registerPACasingTier(int tier, String shortName) {
        Validate.notNull(shortName);
        registeredPACasingTiers.put(tier, shortName);
    }

}
