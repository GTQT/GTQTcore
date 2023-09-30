package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregicality.multiblocks.api.recipes.GCYMRecipeMaps;
import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregicality.science.common.block.GCYSMetaBlocks;
import gregicality.science.common.block.blocks.BlockTransparentCasing;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.ParallelLogicType;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.unification.material.Materials.Lubricant;

public class MetaTileEntityHugeAlloyBlastSmelter extends GCYMRecipeMapMultiblockController {

    protected int heatingCoilLevel;
    protected int heatingCoilDiscount;
    public MetaTileEntityHugeAlloyBlastSmelter(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[] {
                GCYMRecipeMaps.ALLOY_BLAST_RECIPES
        });
        this.recipeMapWorkable = new MetaTileEntityHugeAlloyBlastSmelterWorkable(this);
    }



    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityHugeAlloyBlastSmelter(this.metaTileEntityId);
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
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.2"));
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("最强合金王", new Object[0]));
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

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("MMMMMMMSMMMMMMM","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMXMMMMMMM","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","MMMMMMMMMMMMMMM")
                .where('X', selfPredicate())
                .where('M', states(getCasingState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                )
                .where('B', heatingCoils())
                .where('A', states(this.getGlassState()))
                .where('S', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where(' ', air())
                .build();
    }
    private IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS);
    }
    private static IBlockState getCasingState() {
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
        return 32 * heatingCoilLevel;
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        heatingCoilLevel = 0;
        heatingCoilDiscount = 0;
    }
    protected class MetaTileEntityHugeAlloyBlastSmelterWorkable extends MultiblockRecipeLogic {


        public MetaTileEntityHugeAlloyBlastSmelterWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public int getParallelLimit() {
            return getMaxParallel(heatingCoilLevel);
        }
    }
}