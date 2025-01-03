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

public class BlockElectrolyticMicroscope extends VariantBlock<BlockElectrolyticMicroscope.CasingType> {


    public BlockElectrolyticMicroscope() {
        super(Material.IRON);
        this.setTranslationKey("electron_microscope");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 2);
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public static enum CasingType implements IStringSerializable {


        //电子透镜 五级
        LENS_I("lens_i"),
        LENS_II("lens_ii"),
        LENS_III("lens_iii"),
        LENS_IV("lens_iv"),
        LENS_V("lens_v"),
        //电子源 五级
        SOURSE_I("sourse_i"),
        SOURSE_II("sourse_ii"),
        SOURSE_III("sourse_iii"),
        SOURSE_IV("sourse_iv"),
        SOURSE_V("sourse_v");


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