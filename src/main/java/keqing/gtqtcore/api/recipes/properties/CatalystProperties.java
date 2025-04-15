package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class CatalystProperties extends RecipeProperty<ItemStack> {

    public static final String KEY = "catalyst";

    private static CatalystProperties INSTANCE;

    private CatalystProperties() {
        super(KEY, ItemStack.class);
    }

    public static CatalystProperties getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CatalystProperties();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("所需催化剂：%s",
                ((ItemStack) value).getDisplayName()), x, y, color);
    }

    @Override
    public NBTBase serialize(Object value) {
        if (!(value instanceof ItemStack stack)) {
            return null;
        }
        NBTTagCompound nbt = new NBTTagCompound();
        stack.writeToNBT(nbt);
        return nbt;
    }

    @Override
    public Object deserialize(NBTBase nbt) {
        if (!(nbt instanceof NBTTagCompound compound)) {
            return null;
        }
        return new ItemStack(compound);
    }

}