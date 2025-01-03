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

public class BlockCompressedFusionReactor extends VariantBlock<BlockCompressedFusionReactor.CasingType> {


    public BlockCompressedFusionReactor() {
        super(Material.IRON);
        this.setTranslationKey("compressed_fusion_reactor");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 2);
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public static enum CasingType implements IStringSerializable {

        CASING_FUSION_MKIV("machine_casing_fusion_4"),
        CASING_FUSION_MKV("machine_casing_fusion_5"),
        CASING_FUSION_MKVI("machine_casing_fusion_6"),
        FUSION_COIL_MKII("fusion_coil_mkii"),
        FUSION_COIL_MKIII("fusion_coil_mkiii"),
        FUSION_COIL_MKIV("fusion_coil_mkiv");

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