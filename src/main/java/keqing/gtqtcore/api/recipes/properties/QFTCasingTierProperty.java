package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;

import java.util.Map;
import java.util.TreeMap;

public class QFTCasingTierProperty extends RecipeProperty<Integer> {

    public static final String KEY = "QFTCasingTier";
    private static final TreeMap<Integer, String> registeredQFTCasingTiers = new TreeMap<>();
    private static QFTCasingTierProperty INSTANCE;

    private QFTCasingTierProperty() {
        super(KEY, Integer.class);
    }

    public static QFTCasingTierProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new QFTCasingTierProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("脉冲控制器等级+：%s",
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
