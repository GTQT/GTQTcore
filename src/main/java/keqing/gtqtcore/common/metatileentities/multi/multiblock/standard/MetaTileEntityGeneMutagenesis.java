package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.metatileentity.multiblock.ui.KeyManager;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIBuilder;
import gregtech.api.metatileentity.multiblock.ui.UISyncer;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.GTUtility;
import gregtech.api.util.KeyUtil;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility.RADIATION_MULTIBLOCK_ABILITY;
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
                .aisle("CCCCC", "CCCCC", "GGGGG", "GGGGG", "CCCCC")
                .aisle("CCCCC", "CPPPC", "G   G", "G   G", "CCCCC")
                .aisle("CCCCC", "CPPPC", "G   G", "G   G", "CCCCC")
                .aisle("CCCCC", "CPPPC", "G   G", "G   G", "CCCCC")
                .aisle("CCCCC", "CCSCC", "GGGGG", "GGGGG", "CCCCC")
                .where('S', selfPredicate())
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
    protected void configureDisplayText(MultiblockUIBuilder builder) {
        builder.setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(this.getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addCustom(this::addCustomCapacity)
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgress(), recipeMapWorkable.getMaxProgress())
                .addRecipeOutputLine(recipeMapWorkable);
    }

private void addCustomCapacity(KeyManager keyManager, UISyncer syncer) {
    if (!isStructureFormed()) return;

    IRadiation radiationHatch = getRadiationHatch();
    if (radiationHatch == null) return;

    // 第一步：同步所有数值数据
    Integer syncedTier = syncer.syncInt(radiationHatch.getTier());
    Integer syncedRadiation = radiationHatch.isAvailable() ?
        syncer.syncInt(radiationHatch.getRadiation()) : null;
    String syncedMaterialName = radiationHatch.isAvailable() ?
        syncer.syncString(radiationHatch.getMaterial().getLocalizedName()) : null;
    String syncedWorkedTime = radiationHatch.isAvailable() ?
        syncer.syncString(GTQTDateHelper.getTimeFromTicks(radiationHatch.getRadiation())) : null;
    String syncedRemainingTime = radiationHatch.isAvailable() ?
        syncer.syncString(GTQTDateHelper.getTimeFromTicks(
            radiationHatch.getTotalTick() - radiationHatch.getWorkTime())) : null;

    // 同步玻璃相关数据
    Integer syncedGlassTier = syncer.syncInt(glass_tier);
    Double syncedGlassValue = syncer.syncDouble((10 - glass_tier) / 10.0);

    // 第二步：添加放射仓信息
    if (radiationHatch.isAvailable()) {
        keyManager.add(KeyUtil.lang(TextFormatting.GRAY,
            "放射仓等级：%s 辐射：%s Sv",
            syncedTier,
            syncedRadiation));

        keyManager.add(KeyUtil.lang(TextFormatting.GRAY,
            "辐射物质: %s",
            syncedMaterialName));

        keyManager.add(KeyUtil.lang(TextFormatting.GRAY,
            "已经工作: %s",
            syncedWorkedTime));

        keyManager.add(KeyUtil.lang(TextFormatting.GRAY,
            "剩余时间: %s",
            syncedRemainingTime));
    } else {
        keyManager.add(KeyUtil.lang(TextFormatting.GRAY,
            "放射仓等级：%s",
            syncedTier));
    }

    // 第三步：添加玻璃信息
    keyManager.add(KeyUtil.lang(TextFormatting.GRAY,
        "玻璃等级：%s 玻璃储量：%s",
        syncedGlassTier,
        syncedGlassValue));
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
        tooltip.add(I18n.format("gregtech.machine.gene_mutagenesis.gtqtupdate.1"));
        tooltip.add(I18n.format("gregtech.machine.gene_mutagenesis.gtqtupdate.2"));
        tooltip.add(I18n.format("gregtech.machine.gene_mutagenesis.gtqtupdate.3"));
        tooltip.add(I18n.format("gregtech.machine.gene_mutagenesis.gtqtupdate.4"));
    }
    public boolean checkAvailable(){
        return getRadiationHatch().isAvailable();
    }
    protected class BiologicalReactionLogic extends MultiblockRecipeLogic {

        boolean work = false;


        public BiologicalReactionLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity, true);
        }
        public int getParallelLimit() {
            IRadiation hatch = getRadiationHatch();
            return hatch != null && hatch.isAvailable() ? (int) Math.pow(2, hatch.getTier()) : 1;
        }

        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = (int) (maxProgress * (10 - glass_tier) / 10.0);
        }

        @Override
        protected void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true) && checkAvailable()) {
                if (!work) {
                    getRadiationHatch().setWork(true);
                    work = true;
                }
                this.drawEnergy(this.recipeEUt, false);

                if (++progressTime > maxProgressTime) {
                    completeRecipe();
                    getRadiationHatch().setWork(false);
                    work = false;
                }
            }
        }
    }

}