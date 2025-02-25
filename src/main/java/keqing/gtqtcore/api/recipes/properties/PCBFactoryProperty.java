package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.TreeMap;

public class PCBFactoryProperty extends RecipeProperty<Integer> {

    public static final String KEY = "pcb_factory";
    private static PCBFactoryProperty INSTANCE;

    private PCBFactoryProperty () {
        super(KEY, Integer.class);
    }

    public static PCBFactoryProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PCBFactoryProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("结构等级：%s",
                castValue(value)), x, y, color);
    }
}
