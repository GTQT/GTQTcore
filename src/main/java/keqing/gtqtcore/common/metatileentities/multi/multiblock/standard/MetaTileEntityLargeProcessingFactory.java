package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.utils.TooltipHelper;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTIsaCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityLargeProcessingFactory extends MultiMapMultiblockController {


    public MetaTileEntityLargeProcessingFactory(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.PROCESSING_MODE_A,
                GTQTcoreRecipeMaps.PROCESSING_MODE_B,
                GTQTcoreRecipeMaps.PROCESSING_MODE_C
        });
        this.recipeMapWorkable = new ProcessingFactoryRecipeLogic(this);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.ISA_CASING.getState(GTQTIsaCasing.CasingType.PROCESS);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLargeProcessingFactory(metaTileEntityId);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCC", "CCC", "CCC")
                .aisle("CCC", "C#C", "CMC")
                .aisle("CCC", "CSC", "CCC")
                .where('S', this.selfPredicate())
                .where('C', states(getCasingState())
                        .setMinGlobalLimited(6)
                        .or(autoAbilities()))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('#', air())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.PROCESS;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack,
                               @Nullable World player,
                               List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("把你的小机器丢掉", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.large_processing_factory.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.large_processing_factory.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.large_processing_factory.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.machine.large_processing_factory.tooltip.4"));
        tooltip.add(I18n.format("gtqtcore.machine.large_processing_factory.tooltip.5"));
        tooltip.add(I18n.format("gtqtcore.machine.large_processing_factory.tooltip.6"));
        tooltip.add(I18n.format("gtqtcore.machine.large_processing_factory.tooltip.7"));
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    protected class ProcessingFactoryRecipeLogic extends MultiblockRecipeLogic {

        public ProcessingFactoryRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        protected void modifyOverclockPre(@Nonnull int[] values,
                                          @Nonnull IRecipePropertyStorage storage) {
            super.modifyOverclockPre(values, storage);
            values[0] *= 0.8;
            values[1] *= 0.4;
        }

        @Override
        public int getParallelLimit() {
            return GTUtility.getFloorTierByVoltage(getMaxVoltage()) * 2;
        }
    }
}

