package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.TreeMap;

public class PCBFactoryBioUpgradeProperty extends RecipeProperty<Integer> {

    public static final String KEY = "pcb_factory_bio_upgrade";
    private static final TreeMap<Integer, String> registeredPCBFactoryBioUpgradeTiers = new TreeMap<>();
    private static PCBFactoryBioUpgradeProperty INSTANCE;

    private PCBFactoryBioUpgradeProperty() {
        super(KEY, Integer.class);
    }

    public static PCBFactoryBioUpgradeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PCBFactoryBioUpgradeProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft,
                         int x,
                         int y,
                         int color,
                         Object value) {
        minecraft.fontRenderer.drawString(I18n.format("gtqtcore.machine.pcb_factory.bio_upgrade"), x, y, color);
    }

    private static String getPCBFactoryBioUpgradeTier(Integer tier) {
        Map.Entry<Integer, String> mapEntry = registeredPCBFactoryBioUpgradeTiers.ceilingEntry(tier);
        if (mapEntry == null) {
            throw new IllegalArgumentException("Tier is above registerd maximum tier.");
        }
        return String.format("%s", mapEntry.getValue());
    }

    public static void registerPCBFactoryBioUpgradeTier(int tier,
                                                        String shortName) {
        Validate.notNull(shortName);
        registeredPCBFactoryBioUpgradeTiers.put(tier, shortName);
    }
}
