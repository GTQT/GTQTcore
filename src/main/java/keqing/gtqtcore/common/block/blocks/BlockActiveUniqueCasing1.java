package keqing.gtqtcore.common.block.blocks;

import gregtech.api.block.IStateHarvestLevel;
import gregtech.api.block.VariantActiveBlock;
import gregtech.api.block.VariantBlock;
import gregtech.api.items.toolitem.ToolClasses;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockActiveUniqueCasing1 extends VariantActiveBlock<BlockActiveUniqueCasing1.ActiveCasingType> {

    public BlockActiveUniqueCasing1() {
        super(Material.IRON);
        setTranslationKey("active_unique_casing1");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel(ToolClasses.WRENCH, 2);
        setDefaultState(this.getState(ActiveCasingType.NITINOL_INTAKE_CASING));
    }
    @Override
    public boolean canCreatureSpawn( IBlockState state,
                                     IBlockAccess world,
                                     BlockPos pos,
                                     EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public enum ActiveCasingType implements IStringSerializable {
        NITINOL_INTAKE_CASING("nitinol_intake_casing"),
        HG1223_INTAKE_CASING("hg1223_intake_casing");

        private final String name;

        ActiveCasingType(String name) {
            this.name = name;
        }
        @Override
        public String getName() {
            return this.name;
        }
    }
}
