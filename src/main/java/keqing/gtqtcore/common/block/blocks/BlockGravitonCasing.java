package keqing.gtqtcore.common.block.blocks;


import gregtech.api.block.VariantBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BlockGravitonCasing extends VariantBlock<BlockGravitonCasing.GravitonCasingType> {

    public BlockGravitonCasing() {
        super(Material.IRON);
        this.setTranslationKey("graviton_casing");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 1);
        this.setDefaultState(this.getState(GravitonCasingType.REMOTE_GRAVITON_FLOW_MODULATOR));
    }

    @Override
    public boolean canCreatureSpawn( IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public enum GravitonCasingType implements IStringSerializable {
        REMOTE_GRAVITON_FLOW_MODULATOR("remote_graviton_flow_modulator"),
        MEDIAL_GRAVITON_FLOW_MODULATOR("medial_graviton_flow_modulator"),
        CENTRAL_GRAVITON_FLOW_MODULATOR("central_graviton_flow_modulator");

        private final String name;

        GravitonCasingType(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
