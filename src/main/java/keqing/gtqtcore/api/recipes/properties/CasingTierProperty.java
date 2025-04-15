package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;

public class CasingTierProperty extends RecipeProperty<Integer> {

    public static final String KEY = "casing_tier";
    private static CasingTierProperty INSTANCE;

    private CasingTierProperty() {
        super(KEY, Integer.class);
    }

    public static CasingTierProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CasingTierProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("外壳等级：%s",
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
