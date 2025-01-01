package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockBoilerCasing.BoilerCasingType;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityFluidHatch;
import keqing.gtqtcore.api.capability.IHeat;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.metaileentity.multiblock.RecipeMapHeatMultiblockController;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

import static gregtech.api.unification.material.Materials.Steam;

public class MetaTileEntityDistillationKettle extends RecipeMapHeatMultiblockController {

    FluidStack STEAM = Steam.getFluid(1000);

    public MetaTileEntityDistillationKettle(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.DISTILLATION_KETTLE);
        this.recipeMapWorkable = new DistillationKettleWorkableHandler(this, GTQTcoreRecipeMaps.DISTILLATION_KETTLE);

    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (getInputFluidInventory() != null) {
            FluidStack SteamStack = getInputFluidInventory().drain(Steam.getFluid(Integer.MAX_VALUE), false);
            int SteamAmount = SteamStack == null ? 0 : SteamStack.amount;
            textList.add(new TextComponentTranslation("蒸汽缓存：%s", TextFormattingUtil.formatNumbers(SteamAmount)));
        }
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityDistillationKettle(this.metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" F F ", " F F ", " CMC ", " COC ", " COC ", " COC ", " COC ", " COC ", " COC ", " FCF ")
                .aisle("F   F", "F   F", "CCCCC", "CPPPC", "C   C", "CPPPC", "C   C", "CPPPC", "CCCCC", "FCCCF")
                .aisle("     ", "     ", "CCCCC", "CPPPC", "C E C", "CPEPC", "C E C", "CPPPC", "CCECC", "CCCCC")
                .aisle("F   F", "F   F", "CCCCC", "CPPPC", "C   C", "CPPPC", "C   C", "CPPPC", "CCCCC", "FCCCF")
                .aisle(" F F ", " F F ", " CCC ", " CSC ", " CCC ", " CCC ", " CCC ", " CCC ", " CCC ", " FCF ")
                .where('S', selfPredicate())
                .where('C', states(MetaBlocks.METAL_CASING.getState(MetalCasingType.STEEL_SOLID)).setMinGlobalLimited(96)
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(GTQTMultiblockAbility.HEAT_MULTIBLOCK_ABILITY).setExactLimit(1))
                )
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('F', states(MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel)))
                .where('P', states(MetaBlocks.BOILER_CASING.getState(BoilerCasingType.STEEL_PIPE)))
                .where('E', states(MetaBlocks.METAL_CASING.getState(MetalCasingType.STEEL_SOLID)))
                .where('O', metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.EXPORT_FLUIDS).stream()
                        .filter(mte -> (mte instanceof MetaTileEntityFluidHatch))
                        .toArray(MetaTileEntity[]::new)))
                .where(' ', any())
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("谁家LV做石化", new Object[0]));
        tooltip.add(I18n.format("gtqt.machine.distillation_kettle.1"));
        tooltip.add(I18n.format("当输入仓内额外拥有1000mb蒸汽时，并行*4，耗时*0.8"));
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        setTier(getHeatHatch().getTier());
    }

    @Override
    public void updateFormedValid() {
        super.updateFormedValid();
        setHeat((int) getHeatHatch().getHeat());
        setTier(getHeatHatch().getTier());
    }

    public IHeat getHeatHatch() {
        List<IHeat> abilities = getAbilities(GTQTMultiblockAbility.HEAT_MULTIBLOCK_ABILITY);
        if (abilities.isEmpty())
            return null;
        return abilities.get(0);
    }

    protected ICubeRenderer getFrontOverlay() {
        return Textures.BLAST_FURNACE_OVERLAY;
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }

    private class DistillationKettleWorkableHandler extends HeatRecipeLogic {
        public DistillationKettleWorkableHandler(RecipeMapHeatMultiblockController tileEntity, RecipeMap<?> recipeMap) {
            super(tileEntity, recipeMap);
        }

        @Override
        public int getParallelLimit() {
            if (STEAM.isFluidStackIdentical(getInputFluidInventory().drain(STEAM, false))) {
                return 4;
            }
            return 1;
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            if (STEAM.isFluidStackIdentical(getInputFluidInventory().drain(STEAM, false))) {
                maxProgressTime = (int) (maxProgress * 0.8);
            } else maxProgressTime = (int) (maxProgress * 1.2);

        }

        protected void updateRecipeProgress() {
            if (canRecipeProgress && drawEnergy(recipeEUt, true)) {
                this.drawEnergy(this.recipeEUt, false);
                if (++progressTime > maxProgressTime) {
                    if (STEAM.isFluidStackIdentical(getInputFluidInventory().drain(STEAM, false)))
                        getInputFluidInventory().drain(STEAM, true);
                    completeRecipe();
                }
            }
        }
    }
}
