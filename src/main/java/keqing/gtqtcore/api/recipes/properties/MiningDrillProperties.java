package keqing.gtqtcore.api.recipes.properties;


import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class MiningDrillProperties extends RecipeProperty<Integer> {

    public static final String KEY = "MiningDrillTier";

    private static MiningDrillProperties INSTANCE;

    private MiningDrillProperties() {
        super(KEY, Integer.class);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("%s",
                castValue(value)), x, y, color);
    }

    public static MiningDrillProperties getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MiningDrillProperties();
        }
        return INSTANCE;
    }
}
