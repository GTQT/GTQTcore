package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.TreeMap;

public class EnzymesReactionProperty extends RecipeProperty<Integer> {

    public static final String KEY = "enzymes_reaction";

    private static final TreeMap<Integer, String> registeredTire = new TreeMap<>();
    private static EnzymesReactionProperty INSTANCE;

    private EnzymesReactionProperty() {
        super(KEY, Integer.class);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("性状因子：%s",
                castValue(value)), x, y, color);
    }
    public static EnzymesReactionProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EnzymesReactionProperty();
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
