package keqing.gtqtcore.common.block.blocks;

import gregtech.api.block.VariantBlock;
import keqing.gtqtcore.api.metaileentity.multiblock.ICellData;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class BlockEnergyCell extends VariantBlock<BlockEnergyCell.CellTier> {

    public BlockEnergyCell() {
        super(Material.IRON);
        this.setTranslationKey("energy_cell");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 1);
        this.setDefaultState(this.getState(BlockEnergyCell.CellTier.HV));
    }

    @Override
    public boolean canCreatureSpawn( IBlockState state,
                                     IBlockAccess world,
                                     BlockPos pos,
                                     EntityLiving.SpawnPlacementType type) {
        return false;
    }
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        CellTier cellTier = this.getState(stack);
        if (cellTier.getCapacity() != 0L) {
            tooltip.add(I18n.format("gregtech.universal.tooltip.energy_storage_capacity", cellTier.getCapacity()));
        }
    }

    public static long cellCapacityBase = 400000L;

    public enum CellTier implements IStringSerializable, ICellData {
        ULV(1, cellCapacityBase),
        LV(2, (long) (cellCapacityBase * Math.pow(4, 1))),
        MV(3, (long) (cellCapacityBase * Math.pow(4, 2))),
        HV(4, (long) (cellCapacityBase * Math.pow(4, 3))),
        EV(5, (long) (cellCapacityBase * Math.pow(4, 4)));

        private final int tier;
        private final long capacity;

        CellTier(int tier, long capacity) {
            this.tier = tier;
            this.capacity = capacity;
        }

        @Override
        public int getTier() {
            return this.tier;
        }

        @Override
        public long getCapacity() {
            return this.capacity;
        }


        @Override
        public String getCellName() {
            return this.name().toLowerCase();
        }


        @Override
        public String getName() {
            return this.getCellName();
        }
    }
}
