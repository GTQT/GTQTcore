package keqing.gtqtcore.common.block.blocks;

import gregtech.api.GTValues;
import gregtech.api.block.VariantActiveBlock;
import gregtech.api.block.VariantItemBlock;
import gregtech.api.items.toolitem.ToolClasses;
import keqing.gtqtcore.api.blocks.ITierGlassBlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static keqing.gtqtcore.common.block.blocks.GTQTBlockGlassCasing.CasingType.TI_BORON_SILICATE_GLASS;

@ParametersAreNonnullByDefault
public class GTQTBlockGlassCasing extends VariantActiveBlock<GTQTBlockGlassCasing.CasingType> {

    public GTQTBlockGlassCasing() {
        super(Material.IRON);
        this.setTranslationKey("glass_casing");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.GLASS);
        this.setHarvestLevel(ToolClasses.PICKAXE, 1);
        this.setDefaultState(this.getState(TI_BORON_SILICATE_GLASS));
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state,
                                    @Nonnull IBlockAccess world,
                                    @Nonnull BlockPos pos,
                                    @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    @Override
    @Nonnull
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canRenderInLayer(@Nonnull IBlockState state, @Nonnull BlockRenderLayer layer) {
        return super.canRenderInLayer(state, layer);
    }
    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(@Nonnull IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(@Nonnull IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public boolean shouldSideBeRendered(@Nonnull IBlockState state, IBlockAccess world, BlockPos pos,
                                        @Nonnull EnumFacing side) {
        IBlockState sideState = world.getBlockState(pos.offset(side));

        return sideState.getBlock() == this ?
                getState(sideState) != getState(state) :
                super.shouldSideBeRendered(state, world, pos, side);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        VariantItemBlock itemBlock = (VariantItemBlock<GTQTBlockGlassCasing.CasingType, GTQTBlockGlassCasing>) stack.getItem();
        IBlockState stackState = itemBlock.getBlockState(stack);
        GTQTBlockGlassCasing.CasingType casingType =  this.getState(stackState);
        tooltip.add(I18n.format("gtqtcore.glass_tier.tooltip", casingType.getTireNameColored()));
        if (casingType.isOpticalGlass) {
            tooltip.add(casingType.getOpticalTierName());
        }
    }

    public enum CasingType implements IStringSerializable, ITierGlassBlockState {


        TI_BORON_SILICATE_GLASS("ti_boron_silicate_glass", GTValues.EV, true),
        W_BORON_SILICATE_GLASS("w_boron_silicate_glass", GTValues.IV, true),
        OSMIR_BORON_SILICATE_GLASS("osmir_boron_silicate_glass",GTValues.LuV, true),
        NAQ_BORON_SILICATE_GLASS("naq_boron_silicate_glass", GTValues.ZPM, true),
        FORCE_FIELD_CONSTRAINED_GLASS("force_field_constrained_glass", GTValues.UV, true),
        COSMIC_MICROWAVE_BACKGROUND_RADIATION_ABSORPTION_GLASS("cosmic_microwave_background_radiation_absorption_glass", GTValues.UHV, true),
        SPACETIME_SUPERCONDENSER_GLASS("spacetime_supercondenser_glass", GTValues.UEV, true),
        SUPRACAUSAL_LIGHT_CONE_GLASS("supracausal_light_cone_glass", GTValues.UIV, true);

        private final String name;
        private final int tier;
        private final boolean isOpticalGlass;

        CasingType(String name, int tier, boolean isOpticalGlass) {
            this.name = name;
            this.tier = tier;
            this.isOpticalGlass = isOpticalGlass;
        }

        @Nonnull
        @Override
        public String getName() {return this.name;}

        @Override
        public int getGlassTier() {return this.tier;}

        public boolean isOpticalGlass() {return isOpticalGlass;}

        @Nonnull
        @Override
        public String toString() {return getName();}
    }
}