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

public class GTQTADVBlock extends VariantBlock<GTQTADVBlock.CasingType> {


    public GTQTADVBlock() {
        super(Material.IRON);
        this.setTranslationKey("multiblock_casing");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 2);
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public static enum CasingType implements IStringSerializable {

        ADV_MACHINE_TECH("adv_machine_tech"),
        ADV_MACHINE_BASIC("adv_machine_basic"),
        ADV_DIMENSIONAL_CASING_B("adv_machine_dimensional_cover_blue"),
        ADV_DIMENSIONAL_CASING_O("adv_machine_dimensional_cover_orange"),
        ADV_MACHINE_LESU("adv_machine_lesu"),
        ADV_MACHINE_MATTERFAB("adv_machine_matterfab"),
        ADV_MACHINE_MATTERFAB_ACTIVE("adv_machine_matterfab_active"),
        ADV_MACHINE_MATTERFAB_ACTIVE_ANIMATED("adv_machine_matterfab_active_animated"),
        ADV_MACHINE_MATTERFAB_ANIMATED("adv_machine_matterfab_animated"),
        ADV_MACHINE_VENT_ROTARING("adv_machine_vent_rotating"),
        ADV_MACHINE_TUBBINE("adv_machine_tubbine");
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