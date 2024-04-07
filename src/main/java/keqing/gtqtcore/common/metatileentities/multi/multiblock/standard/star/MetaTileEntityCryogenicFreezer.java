package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.star;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTRecipeMapMultiblockOverwrite;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing1;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static gregtech.api.GTValues.VA;
import static keqing.gtqtcore.api.unification.GTQTMaterials.GelidCryotheum;

//冰箱
public class MetaTileEntityCryogenicFreezer extends RecipeMapMultiblockController {

    public MetaTileEntityCryogenicFreezer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.VACUUM_RECIPES);
        this.recipeMapWorkable = new CryogenicFreezerRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCryogenicFreezer(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "X X", "XXX")
                .aisle("XXX", "XSX", "XXX")
                .where('S', this.selfPredicate())
                .where('X', states(getCasingState())
                        .setMinGlobalLimited(14)
                        .or(autoAbilities()))
                .where(' ', air())
                .build();
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.ADVANCED_COLD_CASING);
    }

    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.ADV_COLD_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.VACUUM_FREEZER_OVERLAY;
    }

    @Override
    public void addInformation(ItemStack stack,
                               World player,
                               List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.machine.cryogenic_freezer.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.cryogenic_freezer.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.cryogenic_freezer.tooltip.3"));
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {

            if (getInputFluidInventory() != null) {
                FluidStack LubricantStack = getInputFluidInventory().drain(GelidCryotheum.getFluid(Integer.MAX_VALUE), false);
                int liquidOxygenAmount = LubricantStack == null ? 0 : LubricantStack.amount;
                textList.add(new TextComponentTranslation("gtqtcore.machine.cryogenic_freezer.amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));

                if (isActive()) {
                    textList.add(new TextComponentTranslation("gtqtcore.machine.cryogenic_freezer.subcooled"));
                }
            }
        }
        super.addDisplayText(textList);
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (isStructureFormed()) {
            FluidStack lubricantStack = getInputFluidInventory().drain(GelidCryotheum.getFluid(Integer.MAX_VALUE), false);
            if (lubricantStack == null || lubricantStack.amount == 0) {
                textList.add(new TextComponentTranslation("gtqtcore.machine.cryogenic_freezer.warning"));
            }
        }
    }

    private final FluidStack GELID_STACK = GelidCryotheum.getFluid(2);

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }

    protected class CryogenicFreezerRecipeLogic extends MultiblockRecipeLogic {

        private final MetaTileEntityCryogenicFreezer freezer;

        public CryogenicFreezerRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
            this.freezer = (MetaTileEntityCryogenicFreezer) tileEntity;
        }


        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = maxProgressTime/16;
        }

        @Override
        public int getParallelLimit() {
            return 64;
        }

        @Override
        protected void updateRecipeProgress() {
            if (canRecipeProgress && drawEnergy(recipeEUt/64, true)) {
                IMultipleTankHandler inputTank = freezer.getInputFluidInventory();
                if (GELID_STACK.isFluidStackIdentical(inputTank.drain(GELID_STACK, false))) {
                    inputTank.drain(GELID_STACK, true);
                    if (++progressTime > maxProgressTime) {
                        completeRecipe();
                    }
                }
                else return;
                drawEnergy(recipeEUt/64, false);
            }
        }
    }
}
