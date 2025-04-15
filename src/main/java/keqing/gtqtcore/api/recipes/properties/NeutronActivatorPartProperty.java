package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;


import java.util.TreeMap;

public class NeutronActivatorPartProperty extends RecipeProperty<Integer> {

    public static final String KEY = "NeutronActivatorPart";

    private static final TreeMap<Integer, String> registeredPart = new TreeMap<>();
    private static NeutronActivatorPartProperty INSTANCE;

    private NeutronActivatorPartProperty() {
        super(KEY, Integer.class);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("中子动能：%s",
                castValue(value)*200), x, y, color);
    }

    public static NeutronActivatorPartProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NeutronActivatorPartProperty();
        }
        return INSTANCE;
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
