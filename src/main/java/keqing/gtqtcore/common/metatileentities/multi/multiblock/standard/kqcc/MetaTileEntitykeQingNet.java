package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.kqcc;

import gregtech.api.GTValues;
import gregtech.api.capability.IObjectHolder;
import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.capability.impl.ComputationRecipeLogic;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockComputerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.KQNetProperty;
import keqing.gtqtcore.api.utils.GTQTUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Collections;


public class MetaTileEntitykeQingNet extends RecipeMapMultiblockController implements IOpticalComputationReceiver {
        private IOpticalComputationProvider computationProvider;
        private IObjectHolder objectHolder;

        public MetaTileEntitykeQingNet(ResourceLocation metaTileEntityId) {
            super(metaTileEntityId, GTQTcoreRecipeMaps.KEQING_NET_RECIES);
            this.recipeMapWorkable = new ResearchStationRecipeLogic(this);
        }

        public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
            return new MetaTileEntitykeQingNet(this.metaTileEntityId);
        }
    int thresholdPercentage=0;
    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 9, "", this::decrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("gtqtcore.multiblock.kqn.threshold_decrement"));
        group.addWidget(new ClickButtonWidget(9, 0, 9, 9, "", this::incrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("gtqtcore.multiblock.kqn.threshold_increment"));

        group.addWidget(new ClickButtonWidget(0, 9, 9, 9, "", this::decrementThreshold1)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("gtqtcore.multiblock.kqn.threshold_decrement1"));
        group.addWidget(new ClickButtonWidget(9, 9, 9, 9, "", this::incrementThreshold1)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("gtqtcore.multiblock.kqn.threshold_increment1"));
        return group;
    }
    private void incrementThreshold(Widget.ClickData clickData) {
            this.thresholdPercentage = MathHelper.clamp(thresholdPercentage + 1, 0, 100);
    }

    private void decrementThreshold(Widget.ClickData clickData) {
            this.thresholdPercentage = MathHelper.clamp(thresholdPercentage - 1, 0, 100);
    }

    private void incrementThreshold1(Widget.ClickData clickData) {
        this.thresholdPercentage = MathHelper.clamp(thresholdPercentage + 10, 0, 100);
    }

    private void decrementThreshold1(Widget.ClickData clickData) {
        this.thresholdPercentage = MathHelper.clamp(thresholdPercentage - 10, 0, 100);
    }
    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        if(recipe.getProperty(KQNetProperty.getInstance(), 0)==thresholdPercentage)
        {
            return super.checkRecipe(recipe, consumeIfSuccess);
        }
        return false;
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentTranslation("gtqtcore.multiblock.kqn.thresholdPercentage",thresholdPercentage));
        textList.add(new TextComponentTranslation( String.format("gtqtcore.multiblock.kqn.nb%s",thresholdPercentage)));
    }


        @Nonnull
        protected  BlockPattern createStructurePattern() {
            return FactoryBlockPattern.start()
                    .aisle("PPP", "PPP", "PPP")
                    .aisle("PPP", "HSP", "PPP")
                    .where('S', this.selfPredicate())
                    .where('P', states(new IBlockState[]{getCasingState()})
                            .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1))
                            .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                            .or(abilities(MultiblockAbility.IMPORT_ITEMS).setExactLimit(1))
                            .or(abilities(MultiblockAbility.EXPORT_ITEMS).setExactLimit(1))
                            .or(abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1)))
                    .where('H', abilities(MultiblockAbility.OBJECT_HOLDER)).build();
        }


        protected IBlockState getCasingState() {
             return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PTFE_INERT_CASING);
        }

        @SideOnly(Side.CLIENT)
        public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
            return Textures.INERT_PTFE_CASING;
        }

        @Nonnull
        @SideOnly(Side.CLIENT)
        protected  ICubeRenderer getFrontOverlay() {
            return Textures.RESEARCH_STATION_OVERLAY;
        }

        protected boolean shouldShowVoidingModeButton() {
            return false;
        }

        public boolean canVoidRecipeItemOutputs() {
            return true;
        }

    public IOpticalComputationProvider getComputationProvider() {
        return this.computationProvider;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        List<IOpticalComputationHatch> providers = this.getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
        if (providers != null && providers.size() >= 1) {
            this.computationProvider = (IOpticalComputationProvider)providers.get(0);
        }
    }
    /*
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (this.isStructureFormed() && this.isActive() && this.getRecipeMapWorkable().isHasNotEnoughComputation()) {
            textList.add((new TextComponentTranslation("gregtech.multiblock.computation.not_enough_computation")).setStyle((new Style()).setColor(TextFormatting.RED)));
        }
    }
    */
        private static class ResearchStationRecipeLogic extends ComputationRecipeLogic {
            public ResearchStationRecipeLogic(MetaTileEntitykeQingNet metaTileEntity) {
                super(metaTileEntity, ComputationType.SPORADIC);
            }

            @Nonnull
            public  MetaTileEntitykeQingNet getMetaTileEntity() {
                return (MetaTileEntitykeQingNet)super.getMetaTileEntity();
            }

        }
    }
