package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.huge;

import gregicality.multiblocks.api.recipes.GCYMRecipeMaps;
import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockFusionCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.utils.GTQTUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.GTValues.VA;

public class MetaTileEntityHugeAlloyBlastSmelter extends RecipeMapMultiblockController {

    private int coilTier;
    protected int heatingCoilLevel;
    protected int heatingCoilDiscount;
    private int glassTire;
    public MetaTileEntityHugeAlloyBlastSmelter(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GCYMRecipeMaps.ALLOY_BLAST_RECIPES);
        this.recipeMapWorkable = new MetaTileEntityHugeAlloyBlastSmelterWorkable(this);
    }



    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityHugeAlloyBlastSmelter(this.metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.md.level", heatingCoilLevel));
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.md.glass", glassTire));
            textList.add(new TextComponentTranslation("gregtech.multiblock.cracking_unit.energy", 100 - 10 * this.coilTier));
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.fu.level", 100-10*glassTire));
        }
        super.addDisplayText(textList);
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.multiblock.hb.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.multiblock.hb.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.multiblock.hb.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.1"));
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("最强合金王", new Object[0]));
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoilType");
        Object coilType = context.get("CoilType");
        Object glassTire = context.get("GlassTieredStats");
        this.glassTire = GTQTUtil.getOrDefault(() -> glassTire instanceof WrappedIntTired,
                () -> ((WrappedIntTired)glassTire).getIntTier(),
                0);
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

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(
                        "   BBBBB   ",
                        "   CCCCC   ",
                        "   CCCCC   ",
                        "   CCCCC   ",
                        "   BBBBB   ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ")
                .aisle(
                        "  BDDDDDB  ",
                        "  G     G  ",
                        "  G     G  ",
                        "  G     G  ",
                        "  BDDDDDB  ",
                        "   DDDDD   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   DDDDD   ",
                        "   DDDDD   ",
                        "           ")
                .aisle(
                        " BDDSSSDDB ",
                        " G       G ",
                        " G       G ",
                        " G       G ",
                        " BDDSSSDDB ",
                        "  DVVVVVD  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  DDDDDDD  ",
                        "  DDDDDDD  ",
                        "   DDDDD   ")
                .aisle(
                        "BDDDDDDDDDB",
                        "C  AWWWA  C",
                        "C  ABBBA  C",
                        "C  ABBBA  C",
                        "BDDDDDDDDDB",
                        " DVVVVVVVD ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " DDDDDDDDD ",
                        " DDDDDDDDD ",
                        "  DDDDDDD  ")
                .aisle(
                        "BDSDDDDDSDB",
                        "C  W   W  C",
                        "C  B   B  C",
                        "C  B   B  C",
                        "BDSDDDDDSDB",
                        " DVVVVVVVD ",
                        " GW     WG ",
                        " GW     WG ",
                        " GW     WG ",
                        " GW     WG ",
                        " GW     WG ",
                        " GW     WG ",
                        " GW     WG ",
                        " GW     WG ",
                        " GW     WG ",
                        " GW     WG ",
                        " GW     WG ",
                        " DDDDDDDDD ",
                        " DDDDDDDDD ",
                        "  DDDDDDD  ")
                .aisle(
                        "BDSDDDDDSDB",
                        "C  W A W  C",
                        "C  B A B  C",
                        "C  B A B  C",
                        "BDSDDADDSDB",
                        " DVVVAVVVD ",
                        " GW  A  WG ",
                        " GW  A  WG ",
                        " GW  A  WG ",
                        " GW  A  WG ",
                        " GW  A  WG ",
                        " GW  A  WG ",
                        " GW  A  WG ",
                        " GW  A  WG ",
                        " GW  A  WG ",
                        " GW  A  WG ",
                        " GW  A  WG ",
                        " DDDDDDDDD ",
                        " DDDDDDDDD ",
                        "  DDDDDDD  ")
                .aisle(
                        "BDSDDDDDSDB",
                        "C  W   W  C",
                        "C  B   B  C",
                        "C  B   B  C",
                        "BDSDDDDDSDB",
                        " DVVVVVVVD ",
                        " GW     WG ",
                        " GW     WG ",
                        " GW     WG ",
                        " GW     WG ",
                        " GW     WG ",
                        " GW     WG ",
                        " GW     WG ",
                        " GW     WG ",
                        " GW     WG ",
                        " GW     WG ",
                        " GW     WG ",
                        " DDDDDDDDD ",
                        " DDDDDDDDD ",
                        "  DDDDDDD  ")
                .aisle(
                        "BDDDDDDDDDB",
                        "C  AWWWA  C",
                        "C  ABBBA  C",
                        "C  ABBBA  C",
                        "BDDDDDDDDDB",
                        " DVVVVVVVD ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " GWW   WWG ",
                        " DDDDDDDDD ",
                        " DDDDDDDDD ",
                        "  DDDDDDD  ")
                .aisle(
                        " BDDSSSDDB ",
                        " G       G ",
                        " G       G ",
                        " G       G ",
                        " BDDSSSDDB ",
                        "  DVVVVVD  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  GWWWWWG  ",
                        "  DDDDDDD  ",
                        "  DDDDDDD  ",
                        "   DDDDD   ")
                .aisle(
                        "  BDDDDDB  ",
                        "  G     G  ",
                        "  G     G  ",
                        "  G     G  ",
                        "  BDDDDDB  ",
                        "   DDDDD   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   GGGGG   ",
                        "   DDDDD   ",
                        "   DDDDD   ",
                        "           ")
                .aisle(
                        "   BBPBB   ",
                        "   CCCCC   ",
                        "   CCOCC   ",
                        "   CCCCC   ",
                        "   BBBBB   ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ",
                        "           ")
                .where('O', selfPredicate())
                .where('A', states(getCasingState1()))
                .where('B', states(getCasingState2()))
                .where('D', states(getCasingState3()))
                .where('S', states(getCasingState4()))
                .where('V', states(getCasingState5()))
                .where('C', states(getCasingState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                )
                .where('W', heatingCoils())
                .where('G', TiredTraceabilityPredicate.CP_GLASS.get())
                .where('P', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where(' ', air())
                .build();
    }

    private static IBlockState getCasingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.HIGH_TEMPERATURE_CASING);
    }
    private static IBlockState getCasingState1() {
        return MetaBlocks.FUSION_CASING.getState(BlockFusionCasing.CasingType.FUSION_CASING);
    }
    private static IBlockState getCasingState2() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.HIGH_TEMPERATURE_CASING);
    }
    private static IBlockState getCasingState3() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.HIGH_TEMPERATURE_CASING);
    }
    private static IBlockState getCasingState4() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.ATOMIC_CASING);
    }
    private static IBlockState getCasingState5() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.HIGH_TEMPERATURE_CASING);
    }


    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GCYMTextures.BLAST_CASING;
    }

    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return GCYMTextures.ALLOY_BLAST_SMELTER_OVERLAY;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    /**
     * @param heatingCoilLevel the level to get the parallel for
     * @return the max parallel for the heating coil level
     */
    public static int getMaxParallel(int heatingCoilLevel) {
        return VA[heatingCoilLevel];
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.coilTier = 0;
        heatingCoilLevel = 0;
        heatingCoilDiscount = 0;
    }

    protected int getCoilTier() {
        return this.coilTier;
    }
    protected class MetaTileEntityHugeAlloyBlastSmelterWorkable extends MultiblockRecipeLogic {


        public MetaTileEntityHugeAlloyBlastSmelterWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = maxProgress*(100-glassTire*5)/100;

        }
        @Override
        public int getParallelLimit() {
            return getMaxParallel(heatingCoilLevel);
        }

        protected void modifyOverclockPost(int[] resultOverclock, @Nonnull IRecipePropertyStorage storage) {
            super.modifyOverclockPost(resultOverclock, storage);
            int coilTier = ((MetaTileEntityHugeAlloyBlastSmelter)this.metaTileEntity).getCoilTier();
            if (coilTier > 0) {
                resultOverclock[0] = (int)((double)resultOverclock[0] * (100.0 - (double)coilTier)/100);
                resultOverclock[0] = Math.max(1, resultOverclock[0]);
            }
        }
    }
}