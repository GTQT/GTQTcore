package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;

public class SuccessChanceProperty extends RecipeProperty<Integer> {

    public static final String KEY = "success_chance";
    private static SuccessChanceProperty INSTANCE;

    private SuccessChanceProperty() {
        super(KEY, Integer.class);
    }

    public static SuccessChanceProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SuccessChanceProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("成功率：%s",
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
