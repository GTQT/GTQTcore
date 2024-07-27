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

public class GTQTQuantumCasing extends VariantBlock<GTQTQuantumCasing.CasingType> {


    public GTQTQuantumCasing() {
        super(Material.IRON);
        this.setTranslationKey("quantum_casing");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 2);
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public static enum CasingType implements IStringSerializable {


        HIGH_ENERGY_CASING("high_energy_casing"),
        ADVANCED_HIGH_ENERGY_CASING("advanced_high_energy_casing"),
        ULTIMATE_HIGH_ENERGY_CASING("ultimate_high_energy_casing"),
        ANNIHILATION_CASING("annihilation_casing"),
        FIELD_GENERATOR_CASING("field_generator_casing"),
        QUANTUM_COMPUTER_CASING("quantum_computer_casing"),
        MOLECULAR_COIL("molecular_coil"),
        HOLLOW_CASING("hollow_casing"),
        SPACETIME_CASING("spacetime_casing"),
        DIMENSIONAL_BRIDGE_CASING("dimensional_bridge_casing"),
        DIMENSIONAL_PRESERVE_CASING("dimensional_preserve_casing"),
        SINGULARITY_REINFORCED_STELLAR_SHIELDING_CASING("singularity_reinforced_stellar_shielding_casing"),
        CELESTIAL_MATTER_GUIDANCE_CASING("celestial_matter_guidance_casing"),
        BOUNDLESS_GRAVITATIONALLY_SEVERED_STRUCTURE_CASING("boundless_gravitationally_severed_structure_casing"),
        TRANSCENDENTALLY_AMPLIFIED_MAGNETIC_CONFINEMENT_CASING("transcendentally_amplified_magnetic_confinement_casing");


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