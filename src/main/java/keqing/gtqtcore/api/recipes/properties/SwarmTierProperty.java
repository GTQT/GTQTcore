package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.TreeMap;

public class SwarmTierProperty extends RecipeProperty<Integer> {

    public static final String KEY = "SwarmTier";
    private static final TreeMap<Integer, String> registeredSwarmTiers = new TreeMap<>();
    private static SwarmTierProperty INSTANCE;

    private SwarmTierProperty() {
        super(KEY, Integer.class);
    }

    public static SwarmTierProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SwarmTierProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft,
                         int x,
                         int y,
                         int color,
                         Object value) {
        minecraft.fontRenderer.drawString(I18n.format("gtqtcore.machine.neutral_network_nexus.tier",
                castValue(value).toString()) + getSwarmTier(castValue(value)), x, y, color);
    }

    private static String getSwarmTier(Integer swarmTier) {
        Map.Entry<Integer, String> mapEntry = registeredSwarmTiers.ceilingEntry(swarmTier);

        if (mapEntry == null) {
            throw new IllegalArgumentException("Tier is above registered maximum Swarm Tier.");
        }

        return String.format("%s", mapEntry.getValue());
    }

    public static void registerSwarmTier(int tier,
                                         String shortName) {
        Validate.notNull(shortName);
        registeredSwarmTiers.put(tier, shortName);
    }
    @Override
    public NBTBase serialize(Object value) {
        return new NBTTagInt(castValue(value));
    }

    @Override
    public Object deserialize( NBTBase nbt) {
        return ((NBTTagInt) nbt).getInt();
    }
}
