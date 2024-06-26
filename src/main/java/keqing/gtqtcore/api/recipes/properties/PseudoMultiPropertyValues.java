package keqing.gtqtcore.api.recipes.properties;

import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;

public class PseudoMultiPropertyValues {
    public ArrayList<IBlockState> validBlockStates;

    public final String blockGroupName;

    public PseudoMultiPropertyValues(@Nonnull String blockGroupName, @Nonnull IBlockState... validBlockStates) {
        this.validBlockStates = new ArrayList<>(Arrays.asList(validBlockStates));
        this.blockGroupName = blockGroupName;
    }

    public PseudoMultiPropertyValues(@Nonnull String blockGroupName, @Nonnull ArrayList<IBlockState> validBlocks) {
        this.validBlockStates = validBlocks;
        this.blockGroupName = blockGroupName;
    }

    @Nonnull
    public ArrayList<IBlockState> getValidBlockStates() {
        return validBlockStates;
    }

    @Nonnull
    public String getBlockGroupName() {
        return blockGroupName;
    }
}