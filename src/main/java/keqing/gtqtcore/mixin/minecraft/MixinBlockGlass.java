package keqing.gtqtcore.mixin.minecraft;

import gregtech.api.GTValues;
import keqing.gtqtcore.api.blocks.ITierGlassBlockState;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockStainedGlass;
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

@Mixin({BlockGlass.class, BlockStainedGlass.class})
public abstract class MixinBlockGlass extends BlockBreakable implements ITierGlassBlockState {
    protected MixinBlockGlass(Material materialIn, boolean ignoreSimilarityIn) {
        super(materialIn, ignoreSimilarityIn);
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("epimorphism.glass_tier.tooltip", getTireNameColored()));
        if (isOpticalGlass()) {
            tooltip.add(getOpticalTierName());
        }
    }

    @Override
    public String getName() {
        return getRegistryName().toString();
    }

    @Override
    public int getGlassTier() {
        return GTValues.LV;
    }

    @Override
    public boolean isOpticalGlass() {
        return false;
    }
}