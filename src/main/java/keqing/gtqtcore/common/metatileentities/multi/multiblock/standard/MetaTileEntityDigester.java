package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityDigester extends RecipeMapMultiblockController {
    private int coilTier;
    protected int heatingCoilLevel;
    protected int heatingCoilDiscount;
    public MetaTileEntityDigester(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.DIGESTER_RECIPES);
        this.recipeMapWorkable = new MetaTileEntityDigesterWorkable(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityDigester(metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.md.level", heatingCoilLevel));
            textList.add(new TextComponentTranslation("gregtech.multiblock.cracking_unit.energy", 100 - this.coilTier));
        }
        super.addDisplayText(textList);
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.multiblock.hb.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.2",24));
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("这是什么，塞进去煮煮", new Object[0]));
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" CCCCC ", " COOOC ", "  CCC  ", "       ")
                .aisle("CHHHHHC", "COAAAOC", " CAAAC ", " CCCCC ")
                .aisle("CHLLLHC", "OAAAAAO", "CAAAAAC", " CAAAC ")
                .aisle("CHLLLHC", "OAAAAAO", "CAAAAAC", " CAAAC ")
                .aisle("CHLLLHC", "OAAAAAO", "CAAAAAC", " CAAAC ")
                .aisle("CHHHHHC", "COAAAOC", " CAAAC ", " CCCCC ")
                .aisle(" CCSCC ", " COOOC ", "  CCC  ", "       ")
                .where('S', selfPredicate())
                .where('C', states(getCasingAState()).setMinGlobalLimited(52).or(autoAbilities()))
                .where('H', states(getHeatState()))
                .where('O', heatingCoils())
                .where('L', states(getCasingBState()))
                .where('A', air())
                .where(' ', any())
                .build();
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoilType");
        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.coilTier = ((IHeatingCoilBlockStats)type).getTier();
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
            this.heatingCoilDiscount = ((IHeatingCoilBlockStats) coilType).getEnergyDiscount();
        } else {
            this.coilTier = 0;
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
            this.heatingCoilDiscount = BlockWireCoil.CoilType.CUPRONICKEL.getEnergyDiscount();
        }
    }
    private static IBlockState getCasingAState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST);
    }
    private static IBlockState getCasingBState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);
    }
    private static IBlockState getHeatState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF);
    }

    public static int getMaxParallel(int heatingCoilLevel) {
        return   heatingCoilLevel;
    }
    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.coilTier = -1;
        heatingCoilLevel = 0;
        heatingCoilDiscount = 0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.ROBUST_TUNGSTENSTEEL_CASING;
    }
    protected int getCoilTier() {
        return this.coilTier;
    }

    protected class MetaTileEntityDigesterWorkable extends MultiblockRecipeLogic {


        public MetaTileEntityDigesterWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public int getParallelLimit() {
            return getMaxParallel(heatingCoilLevel);
        }
        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = maxProgress*(100-heatingCoilLevel)/100;
        }

        protected void modifyOverclockPost(int[] resultOverclock, @Nonnull IRecipePropertyStorage storage) {
            super.modifyOverclockPost(resultOverclock, storage);
            int coilTier = ((MetaTileEntityDigester)this.metaTileEntity).getCoilTier();
            if (coilTier > 0) {
                resultOverclock[0] = (int)((double)resultOverclock[0] * (100.0 - (double)coilTier)/100);
                resultOverclock[0] = Math.max(1, resultOverclock[0]);
            }
        }
    }
}
