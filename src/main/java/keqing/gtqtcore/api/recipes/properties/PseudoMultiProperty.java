package keqing.gtqtcore.api.recipes.properties;

import gregtech.api.recipes.properties.RecipeProperty;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;


public class PseudoMultiProperty extends RecipeProperty<PseudoMultiPropertyValues> {
    public static final String KEY = "blocks";

    private static PseudoMultiProperty INSTANCE;

    private PseudoMultiProperty() {
        super(KEY, PseudoMultiPropertyValues.class);
    }

    public static PseudoMultiProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PseudoMultiProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        PseudoMultiPropertyValues propertyValue = castValue(value);
        String localisedBlockGroupMembers = I18n.format("gregtech.block_group_members." + propertyValue.getBlockGroupName() + ".name");
        minecraft.fontRenderer.drawString(I18n.format("gregtech.recipe.blocks", localisedBlockGroupMembers), x, y, color);
    }

    @Override
    public NBTBase serialize(Object value) {
        if (!(value instanceof PseudoMultiPropertyValues propertyValue)) {
            return null;
        }
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("blockGroupName", propertyValue.getBlockGroupName());
        NBTTagList blockStatesList = new NBTTagList();
        for (IBlockState blockState : propertyValue.getValidBlockStates()) {
            NBTTagCompound blockStateNBT = new NBTTagCompound();
            blockStateNBT.setString("blockState", Block.REGISTRY.getNameForObject(blockState.getBlock()).toString());
            blockStateNBT.setInteger("meta", blockState.getBlock().getMetaFromState(blockState));
            blockStatesList.appendTag(blockStateNBT);
        }
        nbt.setTag("validBlockStates", blockStatesList);
        return nbt;
    }

    @Override
    public Object deserialize(NBTBase nbt) {
        if (!(nbt instanceof NBTTagCompound compound)) {
            return null;
        }
        String blockGroupName = compound.getString("blockGroupName");
        NBTTagList blockStatesList = compound.getTagList("validBlockStates", 10);
        ArrayList<IBlockState> validBlockStates = new ArrayList<>();
        for (int i = 0; i < blockStatesList.tagCount(); i++) {
            NBTTagCompound blockStateNBT = blockStatesList.getCompoundTagAt(i);
            Block block = Block.getBlockFromName(blockStateNBT.getString("blockState"));
            int meta = blockStateNBT.getInteger("meta");
            validBlockStates.add(block.getStateFromMeta(meta));
        }
        return new PseudoMultiPropertyValues(blockGroupName, validBlockStates);
    }

}