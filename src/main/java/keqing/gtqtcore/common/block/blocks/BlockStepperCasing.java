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

public class BlockStepperCasing extends VariantBlock<BlockStepperCasing.CasingType> {


    public BlockStepperCasing() {
        super(Material.IRON);
        this.setTranslationKey("stepper");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 1);
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public static enum CasingType implements IStringSerializable {


        LASER_MKI("laser_mki"),
        LASER_MKII("laser_mkii"),
        LASER_MKIII("laser_mkiii"),
        LASER_MKIV("laser_mkiv"),
        LASER_MKV("laser_mkv"),
        F_LASER_MKI("f_laser_mki"),
        F_LASER_MKII("f_laser_mkii"),
        F_LASER_MKIII("f_laser_mkiii"),
        F_LASER_MKIV("f_laser_mkiv"),
        F_LASER_MKV("f_laser_mkv"),
        CLEAN_MKI("clean_mki"),
        CLEAN_MKII("clean_mkii"),
        CLEAN_MKIII("clean_mkiii"),
        CLEAN_MKIV("clean_mkiv"),
        CLEAN_MKV("clean_mkv");

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