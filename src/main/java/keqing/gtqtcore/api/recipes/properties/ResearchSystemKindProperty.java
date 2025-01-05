package keqing.gtqtcore.api.recipes.properties;


import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class ResearchSystemKindProperty extends RecipeProperty<Integer> {

    public static final String KEY = "BN";

    private static ResearchSystemKindProperty INSTANCE;

    private ResearchSystemKindProperty() {
        super(KEY, Integer.class);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("项目序号：%s",
                castValue(value)), x, y, color);
    }

    public static ResearchSystemKindProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ResearchSystemKindProperty();
        }
        return INSTANCE;
    }

}
