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

public class BlockMultiblockCasing6 extends VariantBlock<BlockMultiblockCasing6.CasingType> {

    public BlockMultiblockCasing6() {
        super(Material.IRON);
        setTranslationKey("multiblock_casing6");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 1);
        setDefaultState(getState(CasingType.RHODIUM));
    }

    @Override
    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public enum CasingType implements IStringSerializable, IStateHarvestLevel {
        RHODIUM("rhodium",4),
        OSMIRIDIUM("osmiridium",4),
        NEUTRONIUM("neutronium",4),
        REINFORCED_TREATED_WOOD_WALL("reinforced_treated_wood_wall",1),
        REINFORCED_TREATED_WOOD_BOTTOM("reinforced_treated_wood_bottom",1),;

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

