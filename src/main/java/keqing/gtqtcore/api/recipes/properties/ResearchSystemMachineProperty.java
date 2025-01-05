package keqing.gtqtcore.api.recipes.properties;


import gregtech.api.recipes.recipeproperties.RecipeProperty;
import keqing.gtqtcore.api.utils.GTQTKQnetHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class ResearchSystemMachineProperty extends RecipeProperty<Integer> {

    public static final String KEY = "ResearchSystemMachine";

    private static ResearchSystemMachineProperty INSTANCE;

    private ResearchSystemMachineProperty() {
        super(KEY, Integer.class);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("设备"+
                GTQTKQnetHelper.getInfo(castValue(value))), x, y, color);
    }
    public static ResearchSystemMachineProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ResearchSystemMachineProperty();
        }
        return INSTANCE;
    }
}
