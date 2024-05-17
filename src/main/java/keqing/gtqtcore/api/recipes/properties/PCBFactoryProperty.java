package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.TreeMap;

public class PCBFactoryProperty extends RecipeProperty<Integer> {

    public static final String KEY = "pcb_factory";
    private static final TreeMap<Integer, String> registeredPCBFactoryTier = new TreeMap<>();
    private static PCBFactoryProperty INSTANCE;

    private PCBFactoryProperty () {
        super(KEY, Integer.class);
    }

    public static PCBFactoryProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PCBFactoryProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("gtqtcore.machine.pcb_factory.tier", castValue(value).toString()) + getPCBFactoryTier(castValue(value)), x, y, color);
    }

    private static String getPCBFactoryTier(Integer tier) {
        Map.Entry<Integer, String> mapEntry = registeredPCBFactoryTier.ceilingEntry(tier);
        if (mapEntry == null) {
            throw new IllegalArgumentException("Tier is above registered maximum tier.");
        }
        return String.format("%s", mapEntry.getValue());
    }

    public static void registerPCBFactoryTier(int tier, String shortName) {
        Validate.notNull(shortName);
        registeredPCBFactoryTier.put(tier, shortName);
    }
}
