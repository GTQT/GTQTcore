package keqing.gtqtcore.common.block.blocks;

import gregtech.api.block.VariantBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;

public class GTQTMultiblockCasing extends VariantBlock<GTQTMultiblockCasing.CasingType> {


    public GTQTMultiblockCasing() {
        super(Material.IRON);
        this.setTranslationKey("multiblock_casing");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 2);
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public static enum CasingType implements IStringSerializable {

        NITINOL_MACHINE_CASING("nitinol_machine_casing"),
        HC_ALLOY_CASING("hc_alloy_casing"),
        SFTC("supercritical_fuel_trans_casing"),
        SFTS("supercritical_fuel_trans_score"),
        NAQUADRIA_CASING("naquadria_casing"),
        NAQUADAH_ALLOY_CASING("naquadah_alloy_casing");

        private final String name;

        CasingType(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

    }
}