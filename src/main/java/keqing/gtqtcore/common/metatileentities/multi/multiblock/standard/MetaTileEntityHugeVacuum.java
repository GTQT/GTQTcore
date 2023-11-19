package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

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
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.capability.GTQTCapabilities;
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

public class MetaTileEntityHugeVacuum extends RecipeMapMultiblockController {

    protected int heatingCoilLevel;
    protected int heatingCoilDiscount;
    public MetaTileEntityHugeVacuum(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.VACUUM_RECIPES);
        this.recipeMapWorkable = new MetaTileEntityHugeVacuum.MetaTileEntityHugeVaccumWorkable(this);
    }
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityHugeVacuum(this.metaTileEntityId);
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
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.2", 384));
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.1"));
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("极度冰寒", new Object[0]));
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
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2).setPreviewCount(1)))
                .where('A', states(getCasingState())
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
        return Textures.FROST_PROOF_CASING;
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(MetalCasingType.ALUMINIUM_FROSTPROOF);
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.VACUUM_FREEZER_OVERLAY;
    }

    @Override
    public int getFluidOutputLimit() {
        return getOutputFluidInventory().getTanks();
    }

    public static int getMaxParallel(int heatingCoilLevel) {
        return  16 * heatingCoilLevel;
    }
    protected class MetaTileEntityHugeVaccumWorkable extends MultiblockRecipeLogic {

        public MetaTileEntityHugeVaccumWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);

        }
        @Override
        public int getParallelLimit() {
            return getMaxParallel(heatingCoilLevel);
        }
    }
}