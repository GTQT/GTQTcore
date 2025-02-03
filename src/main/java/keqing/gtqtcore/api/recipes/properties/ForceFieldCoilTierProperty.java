package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nonnull;

public class ForceFieldCoilTierProperty extends RecipeProperty<Integer> {

    public static final String PROPERTY_KEY = "force_field_coil_tier";

    private static ForceFieldCoilTierProperty INSTANCE;

    private ForceFieldCoilTierProperty() {
        super(PROPERTY_KEY, Integer.class);
    }

    public static ForceFieldCoilTierProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ForceFieldCoilTierProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("线圈等级：%s", castValue(value)), x, y, color);
    }

}
