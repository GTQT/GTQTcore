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

public class GTQTKQCC extends VariantBlock<GTQTKQCC.CasingType> {


    public GTQTKQCC() {
        super(Material.IRON);
        this.setTranslationKey("computer_competent");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 2);
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public static enum CasingType implements IStringSerializable {

        COMPUTER_VENT("computer_vent"),
        COMPUTER_RAMI("computer_rami"),
        COMPUTER_RAMII("computer_ramii"),
        COMPUTER_RAMIII("computer_ramiii"),
        COMPUTER_CPUI("computer_cpui"),
        COMPUTER_CPUII("computer_cpuii"),
        COMPUTER_CPUIII("computer_cpuiii"),
        COMPUTER_GPUI("computer_gpui"),
        COMPUTER_GPUII("computer_gpuii"),
        COMPUTER_GPUIII("computer_gpuiii"),

        ADV_COMPUTER_CASING("adv_computer_casing"),
        ADV_COMPUTER_HEAT_VENT("adv_computer_heat_vent"),
        ULTRA_POWER_CASING("ultra_power_casing"),
        ULTRA_COMPUTER_CASING("ultra_computer_casing");

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