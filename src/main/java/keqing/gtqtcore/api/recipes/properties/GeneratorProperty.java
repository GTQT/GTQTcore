package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagLong;

public class GeneratorProperty extends RecipeProperty<Long> {

    public static final String KEY = "generator";
    private static GeneratorProperty INSTANCE;

    private GeneratorProperty() {
        super(KEY, Long.class);
    }

    public static GeneratorProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GeneratorProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("发电量：%s",
                castValue(value)), x, y, color);
    }
    @Override
    public NBTBase serialize(Object value) {
        return new NBTTagLong(castValue(value));
    }

    @Override
    public Object deserialize( NBTBase nbt) {
        return ((NBTTagLong) nbt).getLong();
    }
}
