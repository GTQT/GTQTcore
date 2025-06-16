package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;

public class SpacetimeCompressionProperty extends RecipeProperty<Integer> {

    public static final String KEY = "spacetime_compression";
    private static SpacetimeCompressionProperty INSTANCE;

    private SpacetimeCompressionProperty() {
        super(KEY, Integer.class);
    }

    public static SpacetimeCompressionProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SpacetimeCompressionProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("压缩时空发生器等级：%s",
                castValue(value)), x, y, color);
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
