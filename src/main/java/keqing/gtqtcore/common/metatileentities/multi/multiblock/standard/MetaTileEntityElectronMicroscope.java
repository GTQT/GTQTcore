package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.PAProperty;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTADVBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static gregtech.api.GTValues.EV;

public class MetaTileEntityElectronMicroscope extends RecipeMapMultiblockController {

    int LENS;
    int SOURCE;

    public MetaTileEntityElectronMicroscope(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.MOLECULAR_TRANSFORMER_RECIPES);
        this.recipeMapWorkable = new ElectronMicroscopeRecipeLogic(this);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.ADV_BLOCK.getState(GTQTADVBlock.CasingType.MaragingSteel250);
    }

    private static IBlockState getUniqueCasingState() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.HEAT_VENT);
    }

    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(GTQTMaterials.DarkSteel).getBlock(GTQTMaterials.DarkSteel);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityElectronMicroscope(metaTileEntityId);
    }

    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return super.checkRecipe(recipe, consumeIfSuccess) && recipe.getProperty(PAProperty.getInstance(), 0) <= SOURCE;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object LENS = context.get("LENSTieredStats");
        Object SOURSE = context.get("SOURSETieredStats");
        this.LENS = GTQTUtil.getOrDefault(() -> LENS instanceof WrappedIntTired,
                () -> ((WrappedIntTired) LENS).getIntTier(),
                0);

        this.SOURCE = GTQTUtil.getOrDefault(() -> SOURSE instanceof WrappedIntTired,
                () -> ((WrappedIntTired) SOURSE).getIntTier(),
                0);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("       X       ", "     XXEXX     ", "       X       ")
                .aisle("     XPXPX     ", "   XXLLLLLXX   ", "     XPXPX     ")
                .aisle("   XXF s FXX   ", "  XOCCXEXCCOX  ", "   XXF s FXX   ")
                .aisle("  XF       FX  ", " XOOXX   XXOOX ", "  XF       FX  ")
                .aisle("  X         X  ", " XCX       XCX ", "  X         X  ")
                .aisle(" XF         FX ", "XLCX       XCLX", " XF         FX ")
                .aisle(" P           P ", "XLX         XLX", " P           P ")
                .aisle("XXs         sXX", "ELE         ELE", "XXs         sXX")
                .aisle(" P           P ", "XLX         XLX", " P           P ")
                .aisle(" XF         FX ", "XLCX       XCLX", " XF         FX ")
                .aisle("  X         X  ", " XCX       XCX ", "  X         X  ")
                .aisle("  XF       FX  ", " XOOXX   XXOOX ", "  XF       FX  ")
                .aisle("   XXF s FXX   ", "  XOCCXEXCCOX  ", "   XXF s FXX   ")
                .aisle("     XPXPX     ", "   XXLLLLLXX   ", "     XPXPX     ")
                .aisle("       X       ", "     XXSXX     ", "       X       ")
                .where('S', this.selfPredicate())
                .where('X', states(getCasingState()))
                .where('E', states(getUniqueCasingState()))
                .where('C', states(getFrameState()))

                .where('L', TiredTraceabilityPredicate.CP_LENS.get())
                .where('O', TiredTraceabilityPredicate.CP_SOURSE.get())
                .where('F', states(getCasingState())
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS)
                                .setMaxGlobalLimited(12)
                                .setPreviewCount(6))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS)
                                .setMaxGlobalLimited(12)
                                .setPreviewCount(6)))
                .where('P', states(getCasingState())
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS)
                                .setMaxGlobalLimited(8)
                                .setPreviewCount(4))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS)
                                .setMaxGlobalLimited(8)
                                .setPreviewCount(4)))
                .where('s', states(getCasingState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH)
                                .setExactLimit(1)
                                .setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY)
                                .setMinGlobalLimited(1)
                                .setMaxGlobalLimited(3)
                                .setPreviewCount(3)))
                .where(' ', any())
                .build();
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gtqtcore.machine.molecular_transformer.tooltip.1", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.molecular_transformer.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.molecular_transformer.tooltip.3"));
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.MaragingSteel250;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.CRYOGENIC_REACTOR_OVERLAY;
    }


    @SuppressWarnings("InnerClassMayBeStatic")
    private class ElectronMicroscopeRecipeLogic extends MultiblockRecipeLogic {

        public ElectronMicroscopeRecipeLogic(MetaTileEntityElectronMicroscope tileEntity) {
            super(tileEntity);
        }

        private int ParallelTier(int tier) {
            if (tier - EV <= 0) {
                return SOURCE;
            } else {
                return SOURCE * (tier - EV);
            }
        }

        @Override
        public int getParallelLimit() {
            int tier = GTUtility.getTierByVoltage(getMaxVoltage());
            return ParallelTier(tier);
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = maxProgress * (10 - LENS) / 10;
        }
    }
}
