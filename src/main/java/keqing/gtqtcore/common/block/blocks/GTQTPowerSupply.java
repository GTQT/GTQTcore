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

public class GTQTPowerSupply extends VariantBlock<GTQTPowerSupply.SupplyType> {

    public GTQTPowerSupply() {
        super(Material.IRON);
        setTranslationKey("power_supply");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setDefaultState(getState(SupplyType.POWER_SUPPLY_BASIC));
    }

    @Override
    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public enum SupplyType implements IStringSerializable, IStateHarvestLevel {

        POWER_SUPPLY_BASIC("power_supply_basic",1),
        POWER_SUPPLY_I("power_supply_i",1),
        POWER_SUPPLY_II("power_supply_ii",1),
        POWER_SUPPLY_III("power_supply_iii",2),
        POWER_SUPPLY_IV("power_supply_iv",2),
        POWER_SUPPLY_V("power_supply_v",3),
        POWER_SUPPLY_VI("power_supply_vi",3),
        POWER_SUPPLY_VII("power_supply_vii",4),
        POWER_SUPPLY_VIII("power_supply_viii",4),
        POWER_SUPPLY_IVV("power_supply_ivv",5),
        POWER_SUPPLY_VV("power_supply_vv",5),
        POWER_SUPPLY_BATTERY_I("power_supply_battery_i",1),
        POWER_SUPPLY_BATTERY_II("power_supply_battery_ii",2),
        POWER_SUPPLY_BATTERY_III("power_supply_battery_iii",3),
        POWER_SUPPLY_BATTERY_IV("power_supply_battery_iv",4),
        POWER_SUPPLY_BATTERY_V("power_supply_battery_v",5);

        private final String name;
        private final int harvestLevel;

        SupplyType(String name, int harvestLevel) {
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

