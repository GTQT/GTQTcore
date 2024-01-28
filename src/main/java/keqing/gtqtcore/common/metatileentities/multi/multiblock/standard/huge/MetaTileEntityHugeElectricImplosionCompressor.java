package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.huge;

import gregicality.multiblocks.api.render.GCYMTextures;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IMufflerHatch;
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
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeItemInput;
import gregtech.api.recipes.recipeproperties.ImplosionExplosiveProperty;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.ValidationResult;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.*;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.PCBPartProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityHugeElectricImplosionCompressor extends RecipeMapMultiblockController {

    protected int heatingCoilLevel;
    protected int heatingCoilDiscount;
    protected int number;
    public MetaTileEntityHugeElectricImplosionCompressor(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.IMPLOSION_RECIPES);
        this.recipeMapWorkable = new MetaTileEntityHugeElectricImplosionCompressorWorkable(this);
    }
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityHugeElectricImplosionCompressor(this.metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.md.level", heatingCoilLevel));
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.md.number", number));
        }
        super.addDisplayText(textList);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.2", 192));
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.3",1,12));
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.1"));
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("恒星级压缩机", new Object[0]));
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object coilType = context.get("CoilType");
        List<IMufflerHatch> i = getAbilities(MultiblockAbility.MUFFLER_HATCH);
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
            this.heatingCoilDiscount = ((IHeatingCoilBlockStats) coilType).getEnergyDiscount();
        } else {
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
            this.heatingCoilDiscount = BlockWireCoil.CoilType.CUPRONICKEL.getEnergyDiscount();
        }
        this.number=i.size();
    }

    @Nonnull
    @Override
    protected  BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXX", "F###F", "F###F", "F###F", "F###F", "XXXXX")
                .aisle("XXXXX", "#PGP#", "#PGP#", "#PGP#", "#PGP#", "XXXXX")
                .aisle("XXXXX", "#BAB#", "#BAB#", "#BAB#", "#BAB#", "XXMXX").setRepeatable(1,12)
                .aisle("XXXXX", "#PGP#", "#PGP#", "#PGP#", "#PGP#", "XXXXX")
                .aisle("XXSXX", "F###F", "F###F", "F###F", "F###F", "XXXXX")
                .where('S', selfPredicate())
                .where('X',
                        states(getCasingState()).setMinGlobalLimited(40)
                                .or(autoAbilities(true, true, true, true, true, true, false)))
                .where('P', states(getCasingState2()))
                .where('G', states(getCasingState3()))
                .where('F', frames(Materials.TungstenSteel))
                .where('A', air())
                .where('#', any())
                .where('B', heatingCoils())
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .build();
    }



    private static IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST);
    }

    private static IBlockState getCasingState2() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE);
    }

    private static IBlockState getCasingState3() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.ROBUST_TUNGSTENSTEEL_CASING;
    }

    @Nonnull
    @Override
    protected  OrientedOverlayRenderer getFrontOverlay() {
        return GCYMTextures.ELECTRIC_IMPLOSION_OVERLAY;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    /**
     * @param heatingCoilLevel the level to get the parallel for
     * @return the max parallel for the heating coil level
     */
    public int getMaxParallel(int heatingCoilLevel,int number) {
        return 2 * heatingCoilLevel * number ;
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        number=1;
        heatingCoilLevel = 0;
        heatingCoilDiscount = 0;
    }
    protected class MetaTileEntityHugeElectricImplosionCompressorWorkable extends MultiblockRecipeLogic {


        public MetaTileEntityHugeElectricImplosionCompressorWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public int getParallelLimit() {
            return getMaxParallel(heatingCoilLevel,number);
        }
    }
}