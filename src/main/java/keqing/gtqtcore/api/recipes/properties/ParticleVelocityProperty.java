package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import gregtech.api.util.TextFormattingUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import org.apache.commons.lang3.tuple.Pair;

import java.util.TreeMap;

public class ParticleVelocityProperty extends RecipeProperty<Integer> {
    public static final String KEY = "ParticleVelocity";
    private static final TreeMap<Long, Pair<Integer, String>> registeredFusionTiers = new TreeMap();
    private static ParticleVelocityProperty INSTANCE;

    protected ParticleVelocityProperty() {
        super("ParticleVelocity", Integer.class);
    }

    public static ParticleVelocityProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ParticleVelocityProperty();
        }

        return INSTANCE;
    }

    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("粒子速度：%s", TextFormattingUtil.formatLongToCompactString(this.castValue(value))) , x, y, color);
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
