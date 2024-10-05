package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.util.TextFormattingUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.TreeMap;

public class EUToStartProperty extends RecipeProperty<Integer> {
    public static final String KEY = "eu_to_start";
    private static final TreeMap<Long, Pair<Integer, String>> registeredFusionTiers = new TreeMap();
    private static EUToStartProperty INSTANCE;

    protected EUToStartProperty() {
        super("eu_to_start", Integer.class);
    }

    public static EUToStartProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EUToStartProperty();
        }

        return INSTANCE;
    }

    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("粒子速度：%s", TextFormattingUtil.formatLongToCompactString(this.castValue(value))) , x, y, color);
    }
}
