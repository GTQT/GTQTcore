package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import keqing.gtqtcore.api.utils.NumberFormattingUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagInt;

import javax.annotation.Nonnull;

public class PressureProperty extends RecipeProperty<Double> {

    public static final String KEY = "pressure";

    private static PressureProperty INSTANCE;

    private PressureProperty() {
        super(KEY, Double.class);
    }

    public static PressureProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PressureProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        Double casted = castValue(value);
        minecraft.fontRenderer.drawString(I18n.format(casted > 1 ? "gtqtcore.recipe.pressure" : "gtqtcore.recipe.vacuum",
                NumberFormattingUtil.formatDoubleToCompactString(casted)), x, y, color);
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