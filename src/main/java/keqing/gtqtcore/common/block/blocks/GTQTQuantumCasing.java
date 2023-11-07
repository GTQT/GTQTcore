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

        MOTOR_BLOCK("motorblock"),
        ANNIHILATION_CASING("annihilationcasing"),
        DIMENSIONAL_CASING("dimensionalcasing"),
        FIELD_GENERATOR_CASING("fieldgeneratorcasing"),
        QUANTUM_CASING("quantumcasing"),
        QUANTUM_COMPUTER_CASING("quantumcomputercasing");

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