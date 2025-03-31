package keqing.gtqtcore.common.block.blocks;


import gregtech.api.block.IStateHarvestLevel;
import gregtech.api.block.VariantBlock;
import gregtech.api.items.toolitem.ToolClasses;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;

public class BlockMultiblockCasing7 extends VariantBlock<BlockMultiblockCasing7.CasingType> {

    public BlockMultiblockCasing7() {
        super(Material.IRON);
        setTranslationKey("multiblock_casing7");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 1);
        setDefaultState(getState(CasingType.MAGNETIC_FLUX_CASING));
    }

    @Override
    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public enum CasingType implements IStringSerializable, IStateHarvestLevel {
        MAGNETIC_FLUX_CASING("magnetic_flux_casing",5),
        MAGNETIC_FIELD_CASING("magnetic_field_casing",5),
        GRAVITY_STABILIZATION_CASING("gravity_stabilization_casing",5),
        PROTOMATTER_ACTIVATION_COIL("protomatter_activation_coil",5),
        ANTIMATTER_ANNIHILATION_MATRIX("antimatter_annihilation_matrix",5);

        private final String name;
        private final int harvestLevel;

        CasingType(String name, int harvestLevel) {
            this.name = name;
            this.harvestLevel = harvestLevel;
        }

        @Override
        @Nonnull
        public String getName() {
            return this.name;
        }

        @Override
        public int getHarvestLevel(IBlockState state) {
            return harvestLevel;
        }

        @Override
        public String getHarvestTool(IBlockState state) {
            return ToolClasses.WRENCH;
        }
    }

}

