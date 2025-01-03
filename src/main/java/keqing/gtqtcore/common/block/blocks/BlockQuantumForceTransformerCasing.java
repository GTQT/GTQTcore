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
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BlockQuantumForceTransformerCasing extends VariantBlock<BlockQuantumForceTransformerCasing.CasingType> {

    public BlockQuantumForceTransformerCasing() {
        super(Material.IRON);
        this.setTranslationKey("quantum_force_transformer_casing");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 3);
        this.setDefaultState(this.getState(CasingType.QUANTUM_CONSTRAINT_CASING));
    }
    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public static enum CasingType implements IStringSerializable {
        QUANTUM_CONSTRAINT_CASING("quantum_constraint_casing"),
        NEUTRON_PULSE_MANIPULATOR_CASING("neutron_pulse_manipulator_casing"),
        NEUTRON_SHIELDING_CORE_CASING("neutron_shielding_core_casing"),
        COSMIC_FABRIC_MANIPULATOR_CASING("cosmic_fabric_manipulator_casing"),
        COSMIC_FABRIC_SHIELDING_CORE_CASING("cosmic_fabric_shielding_core_casing"),
        INFINITY_INFUSED_MANIPULATOR_CASING("infinity_infused_manipulator_casing"),
        INFINITY_INFUSED_SHIELDING_CORE_CASING("infinity_infused_shielding_core_casing"),
        SUPRACAUSAL_CONTINUUM_MANIPULATOR_CASING("supracausal_continuum_manipulator_casing"),
        SUPRACAUSAL_CONTINUUM_SHIELDING_CORE_CASING("supracausal_continuum_shielding_core_casing"),
        QUANTUM_FORCE_TRANSFORMER_COIL("quantum_force_transformer_coil");

        private final String name;

        CasingType(String name) {
            this.name = name;
        }

        @Nonnull
        @Override
        public String getName() {
            return name;
        }
    }

}
