package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;


import gregicality.multiblocks.api.metatileentity.GCYMMultiblockAbility;
import gregicality.science.common.block.blocks.BlockTransparentCasing;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.BlockBoilerCasing.BoilerCasingType;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;

import gregicality.science.common.block.GCYSMetaBlocks;
import gregicality.science.common.block.blocks.BlockGCYSMultiblockCasing;

import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityIntegratedMiningDivision extends GCYMRecipeMapMultiblockController {

    public MetaTileEntityIntegratedMiningDivision(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[] {
                GTQTcoreRecipeMaps.INTEGRATED_MINING_DIVISION,
                RecipeMaps.ORE_WASHER_RECIPES,
                RecipeMaps.THERMAL_CENTRIFUGE_RECIPES,
                RecipeMaps.SIFTER_RECIPES,
                RecipeMaps.MACERATOR_RECIPES
        });

    }
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityIntegratedMiningDivision(this.metaTileEntityId);
    }

    @Nonnull
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCCC", "CGGGC", "CGCGC", "CGGGC", "CCCCC")
                .aisle("CBCBC", "BGGGB", "CGAGC", "BGGGB", "CBCBC")
                .aisle("CBCBC", "BGGGB", "CGAGC", "BGGGB", "CBCBC")
                .aisle("CBCBC", "BGGGB", "CGAGC", "BGGGB", "CBCBC")
                .aisle("CBCBC", "BGGGB", "CGAGC", "BGGGB", "CBCBC")
                .aisle("CBCBC", "BGGGB", "CGAGC", "BGGGB", "CBCBC")
                .aisle("CBCBC", "BGGGB", "CGAGC", "BGGGB", "CBCBC")
                .aisle("CCCCC", "CGGGC", "CGOGC", "CGGGC", "CCCCC")
                .where('O', this.selfPredicate())
                .where('C', states(this.getCasingState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                        .or(abilities(GCYMMultiblockAbility.PARALLEL_HATCH).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                )
                .where('A', states(this.getSecondCasingState()))
                .where('B', states(this.getPipeCasingState()))
                .where('G', states(this.getGlassState()))
                .build();
    }
    private IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PTFE_INERT_CASING);
    }
    private IBlockState getSecondCasingState() {
        return GCYSMetaBlocks.MULTIBLOCK_CASING.getState(BlockGCYSMultiblockCasing.CasingType.ADVANCED_SUBSTRATE);
    }
    private IBlockState getPipeCasingState() {
        return MetaBlocks.BOILER_CASING.getState(BoilerCasingType.POLYTETRAFLUOROETHYLENE_PIPE);
    }
    private IBlockState getGlassState() {
        return GCYSMetaBlocks.TRANSPARENT_CASING.getState(BlockTransparentCasing.CasingType.PMMA);
    }
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("矿石所需要的唯一", new Object[0]));
    }

    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.INERT_PTFE_CASING;
    }
    @Nonnull
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.LARGE_CHEMICAL_REACTOR_OVERLAY;
    }
}

