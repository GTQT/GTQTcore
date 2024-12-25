package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.IProgressBarMultiblock;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static gregtech.api.unification.material.Materials.Steam;

public class MetaTileEntityGantryCrane extends RecipeMapMultiblockController implements IProgressBarMultiblock {

    int updatetime = 1;
    boolean work = true;
    int[] steam = new int[3];
    FluidStack STEAM = Steam.getFluid(1000 * updatetime);

    public MetaTileEntityGantryCrane(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.GANTRY_CRANE);
        this.recipeMapWorkable = new MFSWorkableHandler(this);
    }

    private static IBlockState getCasingAState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);
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
        return group;
    }

    private void incrementThreshold(Widget.ClickData clickData) {
        this.updatetime = MathHelper.clamp(updatetime + 1, 1, 20);
    }

    private void decrementThreshold(Widget.ClickData clickData) {
        this.updatetime = MathHelper.clamp(updatetime - 1, 1, 20);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (getInputFluidInventory() != null) {
            FluidStack SteamStack = getInputFluidInventory().drain(Steam.getFluid(Integer.MAX_VALUE), false);
            int SteamAmount = SteamStack == null ? 0 : SteamStack.amount;
            textList.add(new TextComponentTranslation("gtqtcore.gc.count1", TextFormattingUtil.formatNumbers(SteamAmount)));
        }
        textList.add(new TextComponentTranslation("gtqtcore.msf.count2", (double) steam[0] / 1000, (double) steam[1] / 1000, (double) steam[2] / 1000));
        if (getStatue()) textList.add(new TextComponentTranslation("gtqtcore.msf.good"));
        else textList.add(new TextComponentTranslation("gtqtcore.msf.no"));
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
    public int getNumProgressBars() {
        return 3;
    }

    @Override
    public void addBarHoverText(List<ITextComponent> hoverList, int index) {
        ITextComponent cwutInfo = TextComponentUtil.stringWithColor(
                TextFormatting.AQUA,
                (steam[index] + 1000) + " / " + (10000) + " kPa");
        hoverList.add(TextComponentUtil.translationWithColor(
                TextFormatting.GRAY,
                "gregtech.multiblock.msf.computation",
                cwutInfo));
    }

    @Override
    public double getFillPercentage(int index) {
        return (double) (steam[index] + 1000) / (10000);
    }

    @Override
    public TextureArea getProgressBarTexture(int index) {
        return GuiTextures.PROGRESS_BAR_HPCA_COMPUTATION;
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" CCC ", " CCC ", " CCC ", " CCC ", " CCC ")
                .aisle("CCCCC", "CAAAC", "CAAAC", "CAAAC", "CCCCC")
                .aisle("CCCCC", "CAAAC", "CAAAC", "CAAAC", "CCCCC")
                .aisle("CCCCC", "CAAAC", "CAAAC", "CAAAC", "CCCCC")
                .aisle(" CCC ", " CSC ", " CCC ", " CCC ", " CCC ")
                .where('S', selfPredicate())
                .where('C', states(getCasingAState()).setMinGlobalLimited(50)
                        .or(abilities(MultiblockAbility.MUFFLER_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2).setPreviewCount(1)))
                .where('A', states(MetaBlocks.FRAMES.get(Materials.StainlessSteel).getBlock(Materials.StainlessSteel)))
                .build();
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("黄焖鸡米饭", new Object[0]));
        tooltip.add(I18n.format("gregtech.machine.gc.tooltip.4"));
        tooltip.add(I18n.format("gregtech.machine.msf.tooltip.4"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.CLEAN_STAINLESS_STEEL_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityGantryCrane(metaTileEntityId);
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }

    private class MFSWorkableHandler extends MultiblockRecipeLogic {

        public MFSWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public int getParallelLimit() {
            if (getStatue()) {
                return 4;
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
                    steam[0] = (int) (steam[0] * 0.48);
                    steam[1] = (int) (steam[1] * 0.64);
                    steam[2] = (int) (steam[2] * 0.72);
                    completeRecipe();
                }
            }
        }
    }

}