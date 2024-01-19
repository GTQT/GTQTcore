package keqing.gtqtcore.mixin.gregtech;

import gregtech.api.block.VariantActiveBlock;
import gregtech.api.block.VariantItemBlock;
import gregtech.common.blocks.BlockGlassCasing;
import keqing.gtqtcore.api.blocks.ITierGlassBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Mixin(BlockGlassCasing.class)
public abstract class MixinBlockGlassCasing extends VariantActiveBlock<BlockGlassCasing.CasingType> {
    public MixinBlockGlassCasing(Material materialIn) {
        super(materialIn);
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        VariantItemBlock itemBlock = (VariantItemBlock<BlockGlassCasing.CasingType, BlockGlassCasing>) stack.getItem();
        IBlockState stackState = itemBlock.getBlockState(stack);
        Object casingType =  this.getState(stackState);
        tooltip.add(I18n.format("epimorphism.glass_tier.tooltip", ((ITierGlassBlockState) casingType).getTireNameColored()));
        if (((ITierGlassBlockState) casingType).isOpticalGlass()) {
            tooltip.add(((ITierGlassBlockState) casingType).getOpticalTierName());
        }
    }
}