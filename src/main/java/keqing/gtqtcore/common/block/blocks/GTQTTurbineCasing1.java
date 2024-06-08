package keqing.gtqtcore.common.block.blocks;


import gregtech.api.block.IStateHarvestLevel;
import gregtech.api.block.VariantBlock;
import gregtech.api.items.toolitem.ToolClasses;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;

public class GTQTTurbineCasing1 extends VariantBlock<GTQTTurbineCasing1.TurbineCasingType> {

    public GTQTTurbineCasing1() {
        super(Material.IRON);
        setTranslationKey("turbine_casing1");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setDefaultState(getState(TurbineCasingType.ST_MACHINE_CASING));
    }

    @Override
    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public enum TurbineCasingType implements IStringSerializable, IStateHarvestLevel {
        AL_TURBINE_CASING("al_turbine_casing", 2),
        SA_TURBINE_CASING("sa_turbine_casing", 3),
        ST_MACHINE_CASING("st_machine_casing", 4),
        ST_TURBINE_CASING("st_turbine_casing", 4),
        AD_MACHINE_CASING("ad_machine_casing", 5),
        AD_TURBINE_CASING("ad_turbine_casing", 5),
        PRECISE_ASSEMBLER_CASING_MK1("precise_assembler_casing_mk1",2),
        PRECISE_ASSEMBLER_CASING_MK2("precise_assembler_casing_mk2",3),
        PRECISE_ASSEMBLER_CASING_MK3("precise_assembler_casing_mk3",4),
        PRECISE_ASSEMBLER_CASING_MK4("precise_assembler_casing_mk4",5),
        PRECISE_ASSEMBLER_CASING_MK5("precise_assembler_casing_mk5",6),
        PRECISE_ASSEMBLER_CASING_MK6("precise_assembler_casing_mk6",7),
        GALVANIZE_STEEL_CASING("galvanize_steel_casing",4),
        ADVANCED_COLD_CASING("advanced_cold_casing", 4),
        CLARIFIER_CASING("clarifier_casing", 4),
        FLOATING_CASING("floating_casing", 4);

        private final String name;
        private final int harvestLevel;

        TurbineCasingType(String name, int harvestLevel) {
            this.name = name;
            this.harvestLevel = harvestLevel;
        }

        @Override
        @Nonnull
        public String getName() {
            return this.name;
        }

        @Override
        public int getHarvestLevel(IBlockState state) {
            return harvestLevel;
        }

        @Override
        public String getHarvestTool(IBlockState state) {
            return ToolClasses.WRENCH;
        }
    }

}

