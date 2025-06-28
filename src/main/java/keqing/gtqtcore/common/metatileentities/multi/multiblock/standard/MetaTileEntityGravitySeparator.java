package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.value.sync.IntSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.ImageCycleButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.metatileentity.multiblock.ui.KeyManager;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIBuilder;
import gregtech.api.metatileentity.multiblock.ui.TemplateBarBuilder;
import gregtech.api.metatileentity.multiblock.ui.UISyncer;
import gregtech.api.mui.GTGuiTextures;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.*;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.UnaryOperator;

import static gregtech.api.recipes.RecipeMaps.SIFTER_RECIPES;
import static gregtech.api.unification.material.Materials.Steam;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.GRAVITY_SEPARATOR_RECIPES;
import static keqing.gtqtcore.common.block.blocks.BlockIsaCasing.CasingType.SEPARATOR_ROTOR;

public class MetaTileEntityGravitySeparator extends MultiMapMultiblockController implements ProgressBarMultiblock {

    int updatetime = 1;
    int[] steam = new int[3];
    FluidStack STEAM = Steam.getFluid(1000 * updatetime);
    @Override
    public boolean usesMui2() {
        return false;
    }
    public MetaTileEntityGravitySeparator(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GRAVITY_SEPARATOR_RECIPES,
                SIFTER_RECIPES
        });
        this.recipeMapWorkable = new GravitySeparatorLogic(this);
    }

    protected static IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 9, "", this::decrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("increment"));
        group.addWidget(new ClickButtonWidget(9, 0, 9, 9, "", this::incrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("decrement"));
        group.addWidget(
                (new ImageCycleButtonWidget(0, 9, 18, 9, GuiTextures.BUTTON_MULTI_MAP, this.getAvailableRecipeMaps().length, this::getRecipeMapIndex, this::setRecipeMapIndex)).shouldUseBaseBackground().singleTexture().setTooltipHoverString((i) -> LocalizationUtils.format("gregtech.multiblock.multiple_recipemaps.header") + " " + LocalizationUtils.format("recipemap." + this.getAvailableRecipeMaps()[i].getUnlocalizedName() + ".name"))
        );
        return group;
    }

    private void incrementThreshold(Widget.ClickData clickData) {
        this.updatetime = MathHelper.clamp(updatetime + 1, 1, 20);
    }

    private void decrementThreshold(Widget.ClickData clickData) {
        this.updatetime = MathHelper.clamp(updatetime - 1, 1, 20);
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
        if (getInputFluidInventory() != null) {
            FluidStack SteamStack = getInputFluidInventory().drain(Steam.getFluid(Integer.MAX_VALUE), false);
            int SteamAmount = SteamStack == null ? 0 : SteamStack.amount;
            keyManager.add(KeyUtil.lang(TextFormatting.GREEN, "gtqtcore.gc.count1",syncer.syncString(TextFormattingUtil.formatNumbers(SteamAmount))));
        }

        keyManager.add(KeyUtil.lang(TextFormatting.GREEN, "gtqtcore.msf.count2",syncer.syncDouble(steam[0] / 1000.0), syncer.syncDouble(steam[1] / 1000.0), syncer.syncDouble(steam[2] / 1000.0)));
        if (getStatue()) keyManager.add(KeyUtil.lang(TextFormatting.GREEN,
                "gtqtcore.msf.good"));
        else keyManager.add(KeyUtil.lang(TextFormatting.YELLOW,
                "gtqtcore.msf.no"));

    }
    @Override
    public void update() {
        super.update();
        if (steam[0] <= 9000) {
            IMultipleTankHandler inputTank = getInputFluidInventory();
            if (STEAM.isFluidStackIdentical(inputTank.drain(STEAM, false))) {
                inputTank.drain(STEAM, true);
                steam[0] = steam[0] + 800 * updatetime;

            }
        }

        for (int i = 0; i < 3; i++) {
            if (steam[0] > steam[1]) {
                steam[0] = steam[0] - 40;
                steam[1] = steam[1] + 30;
            }

            if (steam[0] < steam[1]) {
                steam[1] = steam[1] - 30;
                steam[0] = steam[0] + 40;
            }

            if (steam[1] > steam[2]) {
                steam[1] = steam[1] - 10;
                steam[2] = steam[2] + 10;
            }

            if (steam[1] < steam[2]) {
                steam[2] = steam[2] - 10;
                steam[1] = steam[1] + 10;
            }
        }

    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("fluid1", steam[0]);
        data.setInteger("fluid2", steam[1]);
        data.setInteger("fluid3", steam[2]);
        data.setInteger("updatetime", updatetime);
        return super.writeToNBT(data);
    }


    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        steam[0] = data.getInteger("fluid1");
        steam[1] = data.getInteger("fluid2");
        steam[2] = data.getInteger("fluid3");
        updatetime = data.getInteger("updatetime");
    }

    public boolean getStatue() {
        return steam[0] > 6000 && steam[1] > 6000 && steam[2] > 6000;
    }

    @Override
    public int getProgressBarCount() {
        return 3;
    }

    @Override
    public void registerBars(List<UnaryOperator<TemplateBarBuilder>> bars, PanelSyncManager syncManager) {
        for(int i=0;i<3;i++)
        {
            IntSyncValue hatch = new IntSyncValue(()->steam[0]);
            syncManager.syncValue("hatch"+i, hatch);

            bars.add(barTest -> barTest
                    .texture(GTGuiTextures.PROGRESS_BAR_FUSION_HEAT)
                    .tooltipBuilder(tooltip -> {
                        IKey heatInfo = KeyUtil.string(TextFormatting.AQUA,
                                "%s / %s  kPa",
                                hatch.getIntValue()+100, 10000);
                        tooltip.add(KeyUtil.lang(
                                "仓室压力",
                                heatInfo));
                    })
                    .progress(() -> hatch.getIntValue() > 0 ?
                            (double) (hatch.getIntValue() + 1000) /10000 : 0));
        }
    }
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityGravitySeparator(this.metaTileEntityId);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" CCCCCCC ", " CCCCCCC ", "FCCCCCCCF", "         ", "         ", "F       F")
                .aisle("FCCCCCCCF", "FCUUUUUCF", "FC     CF", "F       F", "F       F", "FFFFFFFFF")
                .aisle(" CCCCCCC ", " CUUUUUC ", "FC     CF", "         ", "         ", "F       F")
                .aisle(" CCCCCCC ", " CUUUUUC ", "FC     CF", "         ", "         ", "F       F")
                .aisle(" CCCCCCC ", " CUUUUUC ", "FC     CF", "         ", "         ", "F       F")
                .aisle("FCCCCCCCF", "FCUUUUUCF", "FC     CF", "F       F", "F       F", "FFFFFFFFF")
                .aisle(" CCCCCCC ", " CCCSCCC ", "FCCCCCCCF", "         ", "         ", "F       F")
                .where('S', selfPredicate())
                .where('U', states(steelRotorState()))
                .where('C', states(getCasingState()).setMinGlobalLimited(80)
                        .or(autoAbilities()))
                .where('F', this.getFramePredicate())
                .where(' ', any())
                .build();
    }

    public TraceabilityPredicate getFramePredicate() {
        return frames(Materials.Steel);
    }

    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.SOLID_STEEL_CASING;
    }

    protected IBlockState steelRotorState() {
        return GTQTMetaBlocks.blockIsaCasing.getState(SEPARATOR_ROTOR);
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("摇臂筛选机", new Object[0]));
        tooltip.add(I18n.format("gregtech.machine.gc.tooltip.4"));
        tooltip.add(I18n.format("gregtech.machine.msf.tooltip.4"));
    }

    @Nonnull
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.LARGE_FLUID_PHASE_TRANSFORMER_OVERLAY;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public boolean allowsExtendedFacing() {
        return false;
    }

    private class GravitySeparatorLogic extends MultiblockRecipeLogic {
        public GravitySeparatorLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        private boolean isCommonMode() {
            return this.getRecipeMap() == SIFTER_RECIPES;
        }

        @Override
        public int getParallelLimit() {
            if (getStatue()) {
                return 4 * (isCommonMode() ? 4 : 1);
            }
            return 1;
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            if (getStatue()) {
                maxProgressTime = maxProgress / 4;
            } else this.maxProgressTime = maxProgress;
        }

        public boolean getStatue() {
            return steam[0] > 6000 && steam[1] > 6000 && steam[2] > 6000;
        }

        protected void updateRecipeProgress() {
            if (canRecipeProgress && drawEnergy(recipeEUt, true)) {
                this.drawEnergy(this.recipeEUt, false);
                if (++progressTime > maxProgressTime) {
                    steam[0] = (int) (steam[0] * 0.72);
                    steam[1] = (int) (steam[1] * 0.84);
                    steam[2] = (int) (steam[2] * 0.96);
                    completeRecipe();
                }
            }
        }
    }
}