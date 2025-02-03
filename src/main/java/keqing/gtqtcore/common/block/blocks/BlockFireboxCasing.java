package keqing.gtqtcore.common.block.blocks;

import gregtech.api.block.IStateHarvestLevel;
import gregtech.api.block.VariantActiveBlock;
import gregtech.api.items.toolitem.ToolClasses;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;

public class BlockFireboxCasing extends VariantActiveBlock<BlockFireboxCasing.FireboxCasingType> {

    public BlockFireboxCasing() {
        super(Material.IRON);
        this.setTranslationKey("firebox_casing");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setDefaultState(this.getState(FireboxCasingType.RHODIUM_PLATED_PALLADIUM_FIREBOX));
    }

    @Override
    public boolean canCreatureSpawn( IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public enum FireboxCasingType implements IStringSerializable, IStateHarvestLevel {

        STAINLESS_STEEL_FIREBOX("stainless_steel_firebox", 3),
        RHODIUM_PLATED_PALLADIUM_FIREBOX("rhodium_plated_palladium_firebox", 4),
        NAQUADAH_ALLOY_FIREBOX("naquadah_alloy_firebox", 4),
        DARMSTADTIUM_FIREBOX("darmstadtium_firebox", 5),
        NEUTRONIUM_FIREBOX("neutronium_firebox", 5);

        private final String name;
        private final int harvestLevel;

        FireboxCasingType(String name, int harvestLevel) {
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

