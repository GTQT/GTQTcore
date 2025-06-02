package keqing.gtqtcore.common.block.blocks;

import gregtech.api.block.VariantBlock;
import gregtech.api.items.toolitem.ToolClasses;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class BlockTransparentCasing extends VariantBlock<BlockTransparentCasing.CasingType> {

    public BlockTransparentCasing() {
        super(Material.GLASS);
        setTranslationKey("transparent_casing");
        setHardness(5.0F);
        setResistance(5.0F);
        setSoundType(SoundType.GLASS);
        setHarvestLevel(ToolClasses.PICKAXE, 3);
        setDefaultState(this.getState(CasingType.BPA_POLYCARBONATE_GLASS));
        useNeighborBrightness = true;
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state,
                                    IBlockAccess world,
                                    BlockPos pos,
                                    EntityLiving.SpawnPlacementType type) {
        return false;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canRenderInLayer(IBlockState state,
                                    BlockRenderLayer layer) {
        return layer == BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public boolean shouldSideBeRendered(IBlockState state,
                                        IBlockAccess world,
                                        BlockPos pos,
                                        EnumFacing side) {
        IBlockState sideState = world.getBlockState(pos.offset(side));
        return sideState.getBlock() == this ?
                getState(sideState) != getState(state) :
                super.shouldSideBeRendered(state, world, pos, side);
    }

    public enum CasingType implements IStringSerializable {

        BPA_POLYCARBONATE_GLASS("bpa_polycarbonate_glass"),
        PMMA_GLASS("pmma_glass"),
        CBDO_POLYCARBONATE_GLASS("cbdo_polycarbonate_glass"),
        INFINITY_GLASS("infinity_glass"),
        QUANTUM_GLASS("quantum_glass"),
        SPATIALLY_TRANSCENDENT_GRAVITATIONAL_LENS("spatially_transcendent_gravitational_lens");


        private final String name;

        CasingType(String name) {
            this.name = name;
        }

        @Override
        @Nonnull
        public String getName() {
            return this.name;
        }

    }
}
