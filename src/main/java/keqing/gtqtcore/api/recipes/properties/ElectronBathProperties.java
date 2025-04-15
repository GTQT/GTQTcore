package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;

public class ElectronBathProperties extends RecipeProperty<Integer> {

    public static final String KEY = "electron_bath_tier";

    private static ElectronBathProperties INSTANCE;

    private ElectronBathProperties() {
        super(KEY, Integer.class);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("电极等级：%s",
                castValue(value)), x, y, color);
    }
    public static ElectronBathProperties getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ElectronBathProperties();
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
