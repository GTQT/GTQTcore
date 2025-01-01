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

public class GTQTElectrobath extends VariantBlock<GTQTElectrobath.CasingType> {


    public GTQTElectrobath() {
        super(Material.IRON);
        this.setTranslationKey("electrobath");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 2);
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public static enum CasingType implements IStringSerializable {
        DRILL_HEAD_LV("drill_head_lv"),
        DRILL_HEAD_MV("drill_head_mv"),
        DRILL_HEAD_HV("drill_head_hv"),
        DRILL_HEAD_EV("drill_head_ev"),
        SOLAR_PLATE_LV("solar_plate_lv"),
        SOLAR_PLATE_MV("solar_plate_mv"),
        SOLAR_PLATE_HV("solar_plate_hv"),
        SOLAR_PLATE_CASING("solar_plate_casing");

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