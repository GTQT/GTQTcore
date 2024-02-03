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
public class GTQTSpaceElevator extends VariantBlock<GTQTSpaceElevator.ElevatorCasingType> {
    public GTQTSpaceElevator() {
        super(Material.IRON);
        this.setTranslationKey("space_elevator_casing");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 4);
        this.setDefaultState(this.getState(ElevatorCasingType.BASIC_CASING));
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state,
                                    @Nonnull IBlockAccess world,
                                    @Nonnull BlockPos pos,
                                    @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public enum ElevatorCasingType implements IStringSerializable {
        BASIC_CASING("basic_casing"),
        INTERNAL_STRUCTURE("internal_structure"),
        SUPPORT_STRUCTURE("support_structure"),
        FLOOR("floor"),
        CABLE_CASING("cable_casing"),
        MOTOR_CASING_MK1("motor_casing_mk1"),
        MOTOR_CASING_MK2("motor_casing_mk2"),
        MOTOR_CASING_MK3("motor_casing_mk3"),
        MOTOR_CASING_MK4("motor_casing_mk4"),
        MOTOR_CASING_MK5("motor_casing_mk5"),
        UPDATE_CASING_MK1("update_casing_mk1"),
        UPDATE_CASING_MK2("update_casing_mk2"),
        UPDATE_CASING_MK3("update_casing_mk3");

        private final String name;

        ElevatorCasingType(String name) {
            this.name = name;
        }

        @Nonnull
        @Override
        public String getName() {
            return name;
        }
    }
}
