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

public class GTQTNuclearFusion extends VariantBlock<GTQTNuclearFusion.CasingType> {


    public GTQTNuclearFusion() {
        super(Material.IRON);
        this.setTranslationKey("nuclear_fusion");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 2);
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public static enum CasingType implements IStringSerializable {

        NUCLEAR_FUSION_CASING("nuclear_fusion_casing"),
        NUCLEAR_FUSION_FRAME("nuclear_fusion_frame"),
        NUCLEAR_FUSION_COOLING("nuclear_fusion_cooling"),
        ENERGY_CELL("energy_cell"),
        NEUTRON_ACTIVATOR_CASING("neutron_activator_casing"),
        NEUTRON_ACTIVATOR_FRAME("neutron_activator_frame");

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