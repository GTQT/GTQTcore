package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;

import javax.annotation.Nonnull;

public class NoCoilTemperatureProperty extends RecipeProperty<Integer> {

    public static final String KEY = "temperature";

    private static NoCoilTemperatureProperty INSTANCE;

    private NoCoilTemperatureProperty() {
        super(KEY, Integer.class);
    }

    public static NoCoilTemperatureProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NoCoilTemperatureProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("gtqtcore.recipe.temperature", castValue(value)), x, y, color);
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
