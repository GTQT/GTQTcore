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

public class BlockMultiblockCasing3 extends VariantBlock<BlockMultiblockCasing3.CasingType> {


    public BlockMultiblockCasing3() {
        super(Material.IRON);
        this.setTranslationKey("multiblock_casing3");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 1);
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public static enum CasingType implements IStringSerializable {
        eglin_steel("eglin_steel"),
        grisium("grisium"),
        potin("potin"),
        black_steel("black_steel"),
        blue_steel("blue_steel"),
        red_steel("red_steel"),
        tumbaga("tumbaga"),
        NITINOL_MACHINE_CASING("nitinol_machine_casing"),
        HC_ALLOY_CASING("hc_alloy_casing"),
        SFTC("supercritical_fuel_trans_casing"),
        SFTS("supercritical_fuel_trans_score"),
        NAQUADRIA_CASING("naquadria_casing"),
        NAQUADAH_ALLOY_CASING("naquadah_alloy_casing"),
        ALLOY_MELTING("alloy_melting");

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