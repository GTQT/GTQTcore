package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.huge;

import gregicality.multiblocks.api.capability.IParallelMultiblock;
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
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiFluidHatch;
import gregtech.core.sound.GTSoundEvents;
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

import static gregtech.api.util.RelativeDirection.*;

public class MetaTileEntityHugeDistillationTower extends RecipeMapMultiblockController {

    protected int heatingCoilLevel;
    protected int heatingCoilDiscount;
    public MetaTileEntityHugeDistillationTower(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.DISTILLATION_RECIPES);
        this.recipeMapWorkable = new MetaTileEntityHugeDistillationTower.MetaTileEntityHugeDistillationTowerWorkable(this);
    }
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityHugeDistillationTower(this.metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.md.level", heatingCoilLevel));
        }
        super.addDisplayText(textList);
    }
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.1"));
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("你们不能在一起", new Object[0]));
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
            this.heatingCoilDiscount = ((IHeatingCoilBlockStats) coilType).getEnergyDiscount();
        } else {
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
            this.heatingCoilDiscount = BlockWireCoil.CoilType.CUPRONICKEL.getEnergyDiscount();
        }
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.heatingCoilLevel = 0;
        this.heatingCoilDiscount = 0;
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("MMMMMMMMMMMMMMM","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMSMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","MMMMMMMMMMMMMMM")
                .where('S', selfPredicate())
                .where('M', states(getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                )
                .where('A', states(getCasingState())
                        .or(metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.EXPORT_FLUIDS).stream()
                                .filter(mte->!(mte instanceof MetaTileEntityMultiFluidHatch))
                                .toArray(MetaTileEntity[]::new))
                                .setMinLayerLimited(1).setMaxLayerLimited(1))
                        .or(autoAbilities(true, false)))
                .where(' ', air())
                .where('B', heatingCoils())
                .build();
    }

    @Override
    protected boolean allowSameFluidFillForOutputs() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.CLEAN_STAINLESS_STEEL_CASING;
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(MetalCasingType.STAINLESS_CLEAN);
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.DISTILLATION_TOWER_OVERLAY;
    }

    @Override
    public int getFluidOutputLimit() {
        return getOutputFluidInventory().getTanks();
    }

    public static int getMaxParallel(int heatingCoilLevel) {
        return  16 * heatingCoilLevel;
    }
    protected class MetaTileEntityHugeDistillationTowerWorkable extends MultiblockRecipeLogic {

        public MetaTileEntityHugeDistillationTowerWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }
        @Override
        public int getParallelLimit() {
            return getMaxParallel(heatingCoilLevel);
        }
        @Override
        public void setMaxProgress(int maxProgress)
        {
            this.maxProgressTime = maxProgress/getParallelLimit();
        }
    }
}