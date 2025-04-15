package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagDouble;

import javax.annotation.Nonnull;

public class PHChangeProperty extends RecipeProperty<Double> {
    public static final String KEY = "ph_change";

    private static PHChangeProperty INSTANCE;

    private PHChangeProperty() {
        super("ph_change", Double.class);
    }

    public static PHChangeProperty getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PHChangeProperty();
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("gtqtcore.recipe.ph_change", String.format("%, .2f", castValue(value))), x, y, color);
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
