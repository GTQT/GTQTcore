package keqing.gtqtcore.api.capability.chemical_plant;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.util.TextFormattingUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.TreeMap;

public class ChemicalPlantProperties extends RecipeProperty<Integer> {

    public static final String KEY = "machineLevel";

    private static ChemicalPlantProperties INSTANCE;

    private ChemicalPlantProperties() {
        super(KEY, Integer.class);
    }

    public static ChemicalPlantProperties getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChemicalPlantProperties();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("设备等级：%s",
                castValue(value)), x, y, color);
    }
}