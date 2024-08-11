package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.huge;



import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMetalCasing;
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

public class MetaTileEntityHugeCrackingUnit extends RecipeMapMultiblockController {
    private int coilTier;
    protected int heatingCoilLevel;
    protected int heatingCoilDiscount;
    private int glassTire;

    public MetaTileEntityHugeCrackingUnit(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.CRACKING_RECIPES);
        this.recipeMapWorkable = new CrackingUnitWorkableHandler(this);
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityHugeCrackingUnit(this.metaTileEntityId);
    }

    @Nonnull
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("HHHHHHHHHHHHH"," H         H "," H         H "," H         H "," H         H "," H         H "," H         H ")
                .aisle("HHHHHHHHHHHHH","HHGGGGGGGGGHH","HHGGGGGGGGGHH","HHGGGGGGGGGHH","HHGGGGGGGGGHH","HHGGGGGGGGGHH","HHGGGGGGGGGHH")
                .aisle("HHHHHHHHHHHHH"," G C C C C G "," G C C C C G "," G C C C C G "," G C C C C G "," G C C C C G "," HGGGGGGGGGH ")
                .aisle("HHHHHHHHHHHHH"," G C C C C G "," H C C C C H "," H C C C C H "," H C C C C H "," G C C C C G "," HGGGHHHGGGH ")
                .aisle("HHHHHHHHHHHHH"," G C C C C G "," H C C C C H "," H C C C C H "," H C C C C H "," G C C C C G "," HGGGHHHGGGH ")
                .aisle("HHHHHHHHHHHHH"," G C C C C G "," H C C C C H "," H C C C C H "," H C C C C H "," G C C C C G "," HGGGHHHGGGH ")
                .aisle("HHHHHHHHHHHHH"," G C C C C G "," G C C C C G "," G C C C C G "," G C C C C G "," G C C C C G "," HGGGGGGGGGH ")
                .aisle("HHHHHHHHHHHHH","HHGGGGGGGGGHH","HHGGGGGGGGGHH","HHGGGGGGGGGHH","HHGGGGGGGGGHH","HHGGGGGGGGGHH","HHGGGGGGGGGHH")
                .aisle("HHHHHHOHHHHHH"," H         H "," H         H "," H         H "," H         H "," H         H "," H         H ")
                .where('O', this.selfPredicate())
                .where('H', states(this.getCasingState()).setMinGlobalLimited(215).or(this.autoAbilities()))
                .where('G', TiredTraceabilityPredicate.CP_GLASS.get())
                .where('#', air()).where('C', heatingCoils()).build();
    }

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.CLEAN_STAINLESS_STEEL_CASING;
    }


    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);
    }

    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (this.isStructureFormed()) {
            textList.add(new TextComponentTranslation("gregtech.multiblock.cracking_unit.energy", 100 - 10 * this.coilTier));
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.md.level", heatingCoilLevel));
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.md.glass", glassTire));
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.fu.level", 100-10*glassTire));
        }

    }


    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.cracker.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.multiblock.hb.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.multiblock.hb.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.multiblock.hb.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.2", 768));
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("超级裂化王", new Object[0]));
    }



    @SideOnly(Side.CLIENT)
    @Nonnull
    protected ICubeRenderer getFrontOverlay() {
        return Textures.CRACKING_UNIT_OVERLAY;
    }

    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoilType");
        Object coilType = context.get("CoilType");
        Object glassTire = context.get("GlassTieredStats");
        this.glassTire = GTQTUtil.getOrDefault(() -> glassTire instanceof WrappedIntTired,
                () -> ((WrappedIntTired)glassTire).getIntTier(),
                0);
        if (type instanceof IHeatingCoilBlockStats) {
            this.coilTier = ((IHeatingCoilBlockStats)type).getTier();
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
        } else {
            this.coilTier = 0;
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
        }

    }

    public void invalidateStructure() {
        super.invalidateStructure();
        this.coilTier = 0;
        this.heatingCoilLevel = 0;
        this.heatingCoilDiscount = 0;
    }

    protected int getCoilTier() {
        return this.coilTier;
    }
    public static int getMaxParallel(int heatingCoilLevel) {
        return  8 * heatingCoilLevel;
    }
    private class CrackingUnitWorkableHandler extends MultiblockRecipeLogic {

        public CrackingUnitWorkableHandler(RecipeMapMultiblockController tileEntity) {
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
            int coilTier = ((MetaTileEntityHugeCrackingUnit)this.metaTileEntity).getCoilTier();
            if (coilTier > 0) {
                resultOverclock[0] = (int)((double)resultOverclock[0] * (100 - (double)coilTier*3)/100);
                resultOverclock[0] = Math.max(1, resultOverclock[0]);
            }
        }
    }
}
