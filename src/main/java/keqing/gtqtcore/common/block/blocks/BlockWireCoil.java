package keqing.gtqtcore.common.block.blocks;


import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.block.VariantBlock;
import gregtech.api.block.VariantItemBlock;
import gregtech.api.items.toolitem.ToolClasses;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityMultiSmelter;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;


    public class BlockWireCoil extends VariantBlock<BlockWireCoil.CoilType> {

        public BlockWireCoil() {
            super(net.minecraft.block.material.Material.IRON);
            setTranslationKey("wire_coil");
            setHardness(5.0f);
            setResistance(10.0f);
            setSoundType(SoundType.METAL);
            setHarvestLevel(ToolClasses.WRENCH, 2);
            setDefaultState(getState(CoilType.NEUTRONIUM));
        }

        @Nonnull
        @Override
        public BlockRenderLayer getRenderLayer() {
            return BlockRenderLayer.SOLID;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void addInformation(@Nonnull ItemStack itemStack, @Nullable World worldIn, List<String> lines, @Nonnull ITooltipFlag tooltipFlag) {
            super.addInformation(itemStack, worldIn, lines, tooltipFlag);

            // noinspection rawtypes, unchecked
            VariantItemBlock itemBlock = (VariantItemBlock<CoilType, BlockWireCoil>) itemStack.getItem();
            IBlockState stackState = itemBlock.getBlockState(itemStack);
            CoilType coilType = getState(stackState);

            lines.add(I18n.format("tile.wire_coil.tooltip_heat", coilType.coilTemperature));

            if (TooltipHelper.isShiftDown()) {
                int coilTier = coilType.ordinal();
                lines.add(I18n.format("tile.wire_coil.tooltip_smelter"));
                lines.add(I18n.format("tile.wire_coil.tooltip_parallel_smelter", coilType.level * 32));
                int EUt = MetaTileEntityMultiSmelter.getEUtForParallel(MetaTileEntityMultiSmelter.getMaxParallel(coilType.getLevel()), coilType.getEnergyDiscount());
                lines.add(I18n.format("tile.wire_coil.tooltip_energy_smelter", EUt));
                lines.add(I18n.format("tile.wire_coil.tooltip_pyro"));
                lines.add(I18n.format("tile.wire_coil.tooltip_speed_pyro", coilTier == 0 ? 75 : 50 * (coilTier + 1)));
                lines.add(I18n.format("tile.wire_coil.tooltip_cracking"));
                lines.add(I18n.format("tile.wire_coil.tooltip_energy_cracking", 100 - 10 * coilTier));
            } else {
                lines.add(I18n.format("tile.wire_coil.tooltip_extended_info"));
            }
        }

        @Override
        public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull SpawnPlacementType type) {
            return false;
        }

        public enum CoilType implements IStringSerializable, IHeatingCoilBlockStats {

            NEUTRONIUM("neutronium_coil_block", 12600, 32, 16, Materials.Neutronium),
            INFINITY("infinity_coil_block", 14400, 64, 32,  GTQTMaterials.Infinity),
            HYPOGEN("hypogen_coil_block", 16200, 128, 64,  GTQTMaterials.Hypogen),
            SPACE_TIME("space_time_coil_block", 18000, 256, 128,  GTQTMaterials.Spacetime),
            ETERNITY("eternity_coil_block", 21600, 512, 256,  GTQTMaterials.Eternity),
            CUPAR_PROTON_PAIR_COIL("cupar_proton_pair_coil_block", 25200, 1024, 256, Materials.Neutronium);

            /*
            DRACONIC("draconic", 12600, 18, 10, GTQTMaterials.Draconium),
            WAKE_DRACONIC("wake_draconic", 14400, 20, 12, GTQTMaterials.AwakenedDraconium),
            END("end", 18000, 24, 20,  GTQTMaterials.Infinity),
            SUPERCONDUCTING_METALLIC_HYDROGEN_COIL("superconducting_metallic_hydrogen_coil", 25200, 32, 18, Materials.Neutronium),
            CUPARA_PROTON_PAIR_COIL("cupara_proton_pair_coil", 28800, 36, 20, Materials.Neutronium),
            CUPARB_PROTON_PAIR_COIL("cuparb_proton_pair_coil", 30000, 38, 10, Materials.Neutronium);
             */
            private final String name;
            //electric blast furnace properties
            private final int coilTemperature;
            //multi smelter properties
            private final int level;
            private final int energyDiscount;
            private final Material material;

            CoilType(String name, int coilTemperature, int level, int energyDiscount, Material material) {
                this.name = name;
                this.coilTemperature = coilTemperature;
                this.level = level;
                this.energyDiscount = energyDiscount;
                this.material = material;
            }

            @Nonnull
            @Override
            public String getName() {
                return this.name;
            }

            @Override
            public int getCoilTemperature() {
                return coilTemperature;
            }

            @Override
            public int getLevel() {
                return level;
            }

            @Override
            public int getEnergyDiscount() {
                return energyDiscount*2+8;
            }

            @Override
            public int getTier() {
                return this.ordinal()*2+8;
            }

            @Nullable
            @Override
            public Material getMaterial() {
                return material;
            }

            @Nonnull
            @Override
            public String toString() {
                return getName();
            }
        }
    }
