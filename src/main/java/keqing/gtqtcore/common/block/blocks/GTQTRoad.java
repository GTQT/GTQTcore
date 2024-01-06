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

public class GTQTRoad extends VariantBlock<GTQTRoad.RoadType> {

    public GTQTRoad() {
        super(Material.IRON);
        setTranslationKey("road");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setDefaultState(getState(RoadType.ASPHALT_STRAGHT));
    }

    @Override
    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public enum RoadType implements IStringSerializable, IStateHarvestLevel {
        ASPHALT_STRAGHT("asphalt_straght",3),
        ASPHALT_LINE("asphalt_line",3),
        ASPHALT_RIGHT("asphalt_right",3),
        ASPHALT_LEFT("asphalt_left",3);

        private final String name;
        private final int harvestLevel;

        RoadType(String name, int harvestLevel) {
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

