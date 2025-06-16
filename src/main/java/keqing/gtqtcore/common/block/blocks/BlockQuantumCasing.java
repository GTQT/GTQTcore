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

public class BlockQuantumCasing extends VariantBlock<BlockQuantumCasing.CasingType> {


    public BlockQuantumCasing() {
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

    public enum CasingType implements IStringSerializable {

        //维度注入机械方块
        DIMENSIONAL_INJECTION_CASING("dimensional_injection_casing"),
        //超能机械方块
        HIGH_ENERGY_CASING("high_energy_casing"),
        //进阶超能机械方块
        ADVANCED_HIGH_ENERGY_CASING("advanced_high_energy_casing"),
        //终极超能机械方块
        ULTIMATE_HIGH_ENERGY_CASING("ultimate_high_energy_casing"),
        ANNIHILATION_CASING("annihilation_casing"),
        FIELD_GENERATOR_CASING("field_generator_casing"),
        QUANTUM_COMPUTER_CASING("quantum_computer_casing"),
        MOLECULAR_COIL("molecular_coil"),
        HOLLOW_CASING("hollow_casing"),
        SPACETIME_CASING("spacetime_casing"),
        //维度桥接机械方块
        DIMENSIONAL_BRIDGE_CASING("dimensional_bridge_casing"),
        DIMENSIONAL_PRESERVE_CASING("dimensional_preserve_casing"),
        //强化时间结构方块
        REINFORCED_SPACETIME_CASING("reinforced_temporal_structure_casing"),
        //强化空间结构方块
        REINFORCED_SPACE_CASING("reinforced_spatial_structure_casing"),
        //无尽时空能量
        INFINITE_SPACETIME_ENERGY_CASING("infinite_spacetime_energy_casing");


        /*
        SINGULARITY_REINFORCED_STELLAR_SHIELDING_CASING("singularity_reinforced_stellar_shielding_casing"),
        CELESTIAL_MATTER_GUIDANCE_CASING("celestial_matter_guidance_casing"),
        BOUNDLESS_GRAVITATIONALLY_SEVERED_STRUCTURE_CASING("boundless_gravitationally_severed_structure_casing"),
        TRANSCENDENTALLY_AMPLIFIED_MAGNETIC_CONFINEMENT_CASING("transcendentally_amplified_magnetic_confinement_casing");

         */




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