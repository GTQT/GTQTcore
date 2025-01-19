package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

import static gregtech.api.GTValues.VA;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3.CasingType.tumbaga;


public class MetaTileEntityBioCentrifuge extends MultiMapMultiblockController {
    int tier;
    private int glass_tier;
    private int clean_tier;
    private int tubeTier;

    public MetaTileEntityBioCentrifuge(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.CENTRIFUGE_RECIPES,
                GTQTcoreRecipeMaps.GRAVITY_SEPARATOR_RECIPES,
                GTQTcoreRecipeMaps.BIO_CENTRIFUGE
        });
        this.recipeMapWorkable = new BioCentrifugeWorkableHandler(this);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public String[] getDescription() {
        return new String[]{I18n.format("gtqt.tooltip.update")};
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityBioCentrifuge(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXX", " P P ", " P P ", " P P ", "XXXXX")
                .aisle("XXXXX", "PPGPP", "PPGPP", "PPGPP", "XXXXX")
                .aisle("XGGGX", " F F ", " FcF ", " F F ", "XGGGX")
                .aisle("XGGGX", " F F ", " FcF ", " F F ", "XGGGX")
                .aisle("XGGGX", " F F ", " FcF ", " F F ", "XGGGX")
                .aisle("XGGGX", " F F ", " FcF ", " F F ", "XGGGX")
                .aisle("XXXXX", "PPGPP", "PPGPP", "PPGPP", "XXXXX")
                .aisle("XXXXX", " P P ", " PSP ", " P P ", "XXXXX")
                .where('S', selfPredicate())

                .where('P', states(getCasingState()))
                .where('F', TiredTraceabilityPredicate.CP_LGLASS.get())
                .where('G', TiredTraceabilityPredicate.CP_ZJ_CASING.get())
                .where('c', TiredTraceabilityPredicate.CP_TUBE.get())
                .where('X', states(getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2).setPreviewCount(2))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.MUFFLER_HATCH).setExactLimit(1).setPreviewCount(1)))
                .build();
    }
    protected IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing3.getState(tumbaga);
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
        Object glass_tier = context.get("LGLTieredStats");
        Object clean_tier = context.get("ZJTieredStats");
        Object tubeTier = context.get("ChemicalPlantTubeTieredStats");
        this.tubeTier = GTQTUtil.getOrDefault(() -> tubeTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) tubeTier).getIntTier(),
                0);
        this.glass_tier = GTQTUtil.getOrDefault(() -> glass_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) glass_tier).getIntTier(),
                0);
        this.clean_tier = GTQTUtil.getOrDefault(() -> clean_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) clean_tier).getIntTier(),
                0);

        tier = Math.min(this.glass_tier, this.tubeTier * 2);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("Glass:%s Tube:%s Clean:%s", glass_tier, tubeTier, clean_tier));
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.tumbaga;
    }
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("爱的魔力转圈圈", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.parallel.pow.machineTier", 2, 64));
        tooltip.add(I18n.format("gtqtcore.machine.progress_time","maxProgress /coilLevel"));
    }
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.INDUSTRIAL_ROASTER_OVERLAY;
    }

    private class BioCentrifugeWorkableHandler extends MultiblockRecipeLogic {

        public BioCentrifugeWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public int getParallelLimit() {
            return Math.min((int) Math.pow(2, tier), 64);
        }
        @Override
        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = maxProgress / (10 - clean_tier) / 10;
        }
    }
}