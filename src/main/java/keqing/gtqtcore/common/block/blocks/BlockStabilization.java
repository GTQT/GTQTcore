package keqing.gtqtcore.common.block.blocks;

import gregtech.api.block.VariantBlock;
import gregtech.api.util.TextFormattingUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockStabilization extends VariantBlock<BlockStabilization.CasingType> {


    public BlockStabilization() {
        super(Material.IRON);
        this.setTranslationKey("stabilization_field_generator");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 5);
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World world, List<String> tooltip, @Nonnull ITooltipFlag advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("设备等级：%s",
                TextFormatting.RED + TextFormattingUtil.formatNumbers(getState(stack).getTier())));
    }
    public static enum CasingType implements IStringSerializable {

        CRUDE("crude",1),
        PRIMITIVE("primitive",2),
        STABLE("stable",3),
        ADVANCED("advanced",4),
        SUPERB("superb",5),
        EXOTIC("exotic",6),
        PERFECT("perfect",7),
        TIPLER("tipler",8),
        GALLIFREYAN("gallifreyan",9);

        private final String name;
        private final int tier;

        CasingType(String name,int  tier) {
            this.name = name;
            this.tier = tier;
        }

        @Override
        public String getName() {
            return name;
        }

        public int getTier() {
            return tier;
        }
    }
}