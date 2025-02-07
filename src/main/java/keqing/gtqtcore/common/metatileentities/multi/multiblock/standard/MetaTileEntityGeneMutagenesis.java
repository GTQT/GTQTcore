package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.IRadiation;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.properties.BioReactorProperty;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.api.utils.GTQTDateHelper;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.RADIATION_MULTIBLOCK_ABILITY;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.BIOLOGICAL_REACTION_RECIPES;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.GENE_MUTAGENESIS;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4.TurbineCasingType.NQ_TURBINE_CASING;


public class MetaTileEntityGeneMutagenesis extends MultiMapMultiblockController {
    private int glass_tier;

    public MetaTileEntityGeneMutagenesis(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GENE_MUTAGENESIS,
                BIOLOGICAL_REACTION_RECIPES,
                RecipeMaps.FERMENTING_RECIPES,
                RecipeMaps.BREWING_RECIPES
        });
        this.recipeMapWorkable = new BiologicalReactionLogic(this);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(NQ_TURBINE_CASING);
    }

    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(GTQTMaterials.MaragingSteel250).getBlock(GTQTMaterials.MaragingSteel250);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityGeneMutagenesis(metaTileEntityId);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object glassTire = context.get("GlassTieredStats");
        this.glass_tier = GTQTUtil.getOrDefault(() -> glassTire instanceof WrappedIntTired,
                () -> ((WrappedIntTired) glassTire).getIntTier(),
                0);

    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("JCCCJ", "JCCCJ", "GGGGG", "GGGGG", "CCCCC")
                .aisle("JCCCJ", "JPPPJ", "G   G", "G   G", "CCCCC")
                .aisle("JCCCJ", "JPPPJ", "G   G", "G   G", "CCCCC")
                .aisle("JCCCJ", "JPPPJ", "G   G", "G   G", "CCCCC")
                .aisle("JCCCJ", "JCSCJ", "GGGGG", "GGGGG", "CCCCC")
                .where('S', selfPredicate())
                .where('J', states(getCasingState()))
                .where('G', TiredTraceabilityPredicate.CP_GLASS.get())
                .where('C', states(getCasingState()).setMinGlobalLimited(38)
                        .or(autoAbilities())
                        .or(abilities(RADIATION_MULTIBLOCK_ABILITY).setExactLimit(1))
                )
                .where('P', states(getFrameState()))
                .where(' ', any())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.NQ_CASING;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (!isStructureFormed()) return;
        super.addDisplayText(textList);
        if (getRadiationHatch().isAvailable()) {
            textList.add(new TextComponentTranslation("放射仓等级：%s 辐射：%s Sv", getRadiationHatch().getTier(), getRadiationHatch().getRadiation()));
            textList.add(new TextComponentString("辐射物质: " + getRadiationHatch().getMaterial().getLocalizedName()));
            textList.add(new TextComponentString("已经工作: " + GTQTDateHelper.getTimeFromTicks(getRadiationHatch().getWorkTime())));
            textList.add(new TextComponentString("距离损坏: " + GTQTDateHelper.getTimeFromTicks(getRadiationHatch().getTotalTick() - getRadiationHatch().getWorkTime())));
        } else textList.add(new TextComponentTranslation("放射仓等级：%s", getRadiationHatch().getTier()));
        textList.add(new TextComponentTranslation("玻璃等级：%s 耗时减免：%s", glass_tier, (10 - glass_tier) / 10.0));
    }

    public IRadiation getRadiationHatch() {
        List<IRadiation> abilities = getAbilities(RADIATION_MULTIBLOCK_ABILITY);
        if (abilities.isEmpty())
            return null;
        return abilities.get(0);
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        if (this.getRecipeMap() != GENE_MUTAGENESIS) return true;
        int number = recipe.getProperty(BioReactorProperty.getInstance(), 0);
        if (getRadiationHatch().getRadiation() >= number)
            return super.checkRecipe(recipe, consumeIfSuccess);
        else return false;
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("轰击基因与DNA", new Object[0]));
        tooltip.add(I18n.format("gregtech.machine.biorea.gtqtupdate.1"));
        tooltip.add(I18n.format("gregtech.machine.biorea.gtqtupdate.2"));
        tooltip.add(I18n.format("gregtech.machine.biorea.gtqtupdate.3"));
        tooltip.add(I18n.format("gregtech.machine.biorea.gtqtupdate.4"));
    }

    protected class BiologicalReactionLogic extends MultiblockRecipeLogic {

        public BiologicalReactionLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity, true);
        }

        @Override
        public int getParallelLimit() {
            if (getRadiationHatch().isAvailable()) return (int) Math.pow(2, getRadiationHatch().getTier());
            return 1;
        }

        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = (int) (maxProgress * (10 - glass_tier) / 10.0);
        }
        boolean work=false;
        protected void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)) {
                this.drawEnergy(this.recipeEUt, false);
                if(!work) {
                    getRadiationHatch().setWork(true);
                    work=true;
                }
                if (++progressTime > maxProgressTime) {
                    completeRecipe();
                    getRadiationHatch().setWork(false);
                    work=false;
                }
            }
        }
    }

}