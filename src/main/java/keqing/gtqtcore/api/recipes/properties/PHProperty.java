package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagInt;

import javax.annotation.Nonnull;

public class PHProperty extends RecipeProperty<Double> {
    public static final String KEY = "ph";

    private static PHProperty INSTANCE;

    private PHProperty() {
        super("ph", Double.class);
    }

    public static PHProperty getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PHProperty();
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("gtqtcore.recipe.ph", String.format("%, .2f", castValue(value))), x, y, color);
    }
    @Override
    public NBTBase serialize(Object value) {
        return new NBTTagDouble(castValue(value));
    }

    @Override
    public Object deserialize( NBTBase nbt) {
        return ((NBTTagDouble) nbt).getDouble();
    }
}
