package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;

import javax.annotation.Nonnull;

public class FlowRateProperty extends RecipeProperty<Integer> {
    public static final String KEY = "flow_rate";

    private static FlowRateProperty INSTANCE;

    private FlowRateProperty() {
        super("flow_rate", Integer.class);
    }

    public static FlowRateProperty getInstance() {
        if (INSTANCE == null)
            INSTANCE = new FlowRateProperty();
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("gtqtcore.recipe.flow_rate", castValue(value)), x, y, color);
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
