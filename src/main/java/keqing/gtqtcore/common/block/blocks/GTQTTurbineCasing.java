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

public class GTQTTurbineCasing extends VariantBlock<GTQTTurbineCasing.TurbineCasingType> {

    public GTQTTurbineCasing() {
        super(Material.IRON);
        setTranslationKey("turbine_casing");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setDefaultState(getState(TurbineCasingType.PD_MACHINE_CASING));
    }

    @Override
    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public enum TurbineCasingType implements IStringSerializable, IStateHarvestLevel {
        MACERATOR_CASING("macerator_casing", 4),
        BRICK("brick", 4),
        ADVANCED_INVAR_CASING("advanced_invar_casing", 4),
        HYPER_CIRE_MK1("hyper_core_mk1", 4),
        HYPER_CIRE_MK2("hyper_core_mk2", 4),
        HYPER_CIRE_MK3("hyper_core_mk3", 4),
        HYPER_CASING("hyper_casing", 4),
        IRIDIUM_CASING("iridium_casing", 4),
        ADVANCED_FILTER_CASING("advanced_filter_casing", 4),
        POLYBENZIMIDAZOLE_PIPE("polybenzimidazole_pipe", 4),
        TALONITE_CASING("talonite_casing", 4),
        PD_MACHINE_CASING("pd_machine_casing", 4),
        PD_TURBINE_CASING("pd_turbine_casing", 4),
        NQ_MACHINE_CASING("nq_machine_casing", 4),
        NQ_TURBINE_CASING("nq_turbine_casing", 4);

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

