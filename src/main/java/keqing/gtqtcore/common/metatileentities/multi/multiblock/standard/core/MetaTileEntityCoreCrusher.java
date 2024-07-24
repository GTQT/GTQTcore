package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.core;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.GTTransferUtils;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockCore;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.MetaTileEntityStewStoolStove;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityBaseWithControl;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public class MetaTileEntityCoreCrusher extends GTQTMultiblockCore {
    public MetaTileEntityCoreCrusher(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {
        super.updateFormedValid();

            GTTransferUtils.moveInventoryItems(this.inputInventory, this.itemInventory);
            GTTransferUtils.transferFluids(this.inputFluidInventory, this.fluidInventory);

            Recipe machineRecipe = RecipeMaps.MACERATOR_RECIPES.findRecipe(this.currentEU, this.importItems, this.importFluids);
            if (machineRecipe != null && machineRecipe.matches(false, this.importItems, this.importFluids)) {
                currentRecipeEU = (long) machineRecipe.getEUt() * machineRecipe.getDuration();
                if (currentRecipeEU <= this.currentEU) {
                    this.currentEU -= currentRecipeEU;
                    this.currentRecipeEU = 0;
                    machineRecipe.matches(true, this.importItems, this.importFluids);

                    GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false, machineRecipe.getFluidOutputs());
                    GTTransferUtils.addItemsToItemHandler(this.outputInventory, false, machineRecipe.getOutputs());
                }
            }
    }

    @Nonnull
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CMC", "CCC", "CCC", "CCC", "CCC", "CCC")
                .aisle("CCC", "CXC", "CXC", "CXC", "CXC", "CCC")
                .aisle("COC", "CCC", "CCC", "CCC", "CCC", "CCC")
                .where('O', this.selfPredicate())
                .where('C', states(this.getCasingState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                )
                .where('X', heatingCoils())
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .build();
    }

    private IBlockState getCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.MACERATOR_CASING);
    }

    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.MACERATOR_CASING;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCoreCrusher(this.metaTileEntityId);
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        return new LinkedList<>();
    }
}
