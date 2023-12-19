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

public class GTQTIsaCasing extends VariantBlock<GTQTIsaCasing.CasingType> {


    public GTQTIsaCasing() {
        super(Material.IRON);
        this.setTranslationKey("isa_casing");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 2);
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public static enum CasingType implements IStringSerializable {

        ISA_MILL_CASING("isa_mill_casing"),
        ISA_MILL_CASING_GEARBOX("isa_mill_casing_gearbox"),
        ISA_MILL_CASING_PIPE("isa_mill_casing_pipe"),
        FLOTATION_CASING("flotation_casing"),
        FLOTATION_CASING_GEARBOX("flotation_casing_gearbox"),
        FLOTATION_CASING_PIPE("flotation_casing_pipe"),
        FLOTATION_INTAKE_CASING("flotation_intake_casing"),
        VACUUM_CASING("vacuum_casing"),
        ASEPTIC_FARM_CASING("aseptic_farm_machine_casing"),
        PROCESS("process_casing"),
        ROUND("round");

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