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
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.pattern.GTQTTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.Lava;
import static gregtech.api.unification.material.Materials.Water;

public class MetaTileEntityFluidizedBed extends RecipeMapMultiblockController implements IProgressBarMultiblock {
    boolean auxiliaryBlastFurnaceNumber;
    int temp;
    int tick;
    public MetaTileEntityFluidizedBed(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.FLUIDIZED_BED);
        this.recipeMapWorkable = new FluidizedBedWorkableHandler(this);
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("temp", temp);
        data.setInteger("tick", tick);
        return super.writeToNBT(data);
    }
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        temp = data.getInteger("temp");
        tick = data.getInteger("tick");
    }
    @Override
    public int getNumProgressBars() {
        return 2;
    }
    @Override
    public double getFillPercentage(int index) {
        return index == 0 ?  temp / 100000.0 :
                tick / 10.0;
    }
    @Override
    public TextureArea getProgressBarTexture(int index) {
        return GuiTextures.PROGRESS_BAR_LCE_FUEL;
    }
    @Override
    public void addBarHoverText(List<ITextComponent> hoverList, int index) {
        ITextComponent cwutInfo;
        if (index == 0) {
            cwutInfo = TextComponentUtil.stringWithColor(
                    TextFormatting.AQUA,
                    temp + " / " + 100000 + " k");
        } else {
            cwutInfo = TextComponentUtil.stringWithColor(
                    TextFormatting.AQUA,
                    tick + " / " + 10 + " P");
        }
        hoverList.add(TextComponentUtil.translationWithColor(
                TextFormatting.GRAY,
                "gregtech.multiblock.pb.computation",
                cwutInfo));
    }

    protected class FluidizedBedWorkableHandler extends MultiblockRecipeLogic {
        public FluidizedBedWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }


        public void setMaxProgress(int maxProgress) {
            if(auxiliaryBlastFurnaceNumber) this.maxProgressTime = maxProgress/4;
        }
        @Override
        public int getParallelLimit() {
            if(temp>80000) return 1;
            if(temp>60000) return 2;
            if(temp>40000) return 4;
            if(temp>30000) return 8;
            return 1;
        }

        @Override
        public void update() {
            super.update();
            if (temp<=100000) {
                if(auxiliaryBlastFurnaceNumber)if(temp>30000)temp=temp-tick*10;
            }

            //自然降温
            if(temp>80000){temp=temp-1;}
            else if(temp>60000){temp=temp-1;}
            else if(temp>40000){temp=temp-1;}
            else if(temp>30000){temp=temp-1;}
        }

        protected void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)) {
                this.drawEnergy(this.recipeEUt, false);
                if (temp < 100000) {
                    temp+=50;
                    if (++progressTime > maxProgressTime) {
                        completeRecipe();
                    }
                }

            }
        }
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityFluidizedBed(metaTileEntityId);
    }
    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 18, "", this::decrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("gtqtcore.multiblock.fb.threshold_decrement"));
        group.addWidget(new ClickButtonWidget(9, 0, 9, 18, "", this::incrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("gtqtcore.multiblock.fb.threshold_increment"));
        return group;
    }

    private void incrementThreshold(Widget.ClickData clickData) {
        if(auxiliaryBlastFurnaceNumber)this.tick = MathHelper.clamp(tick + 1, 0, 10);
        else this.tick = MathHelper.clamp(tick + 1, 0, 5);
    }

    private void decrementThreshold(Widget.ClickData clickData) {
        if(auxiliaryBlastFurnaceNumber)this.tick = MathHelper.clamp(tick - 1, 0, 10);
        else this.tick = MathHelper.clamp(tick - 1, 0, 5);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        if (context.get("AuxiliaryBlastFurnace1") != null) {
            auxiliaryBlastFurnaceNumber =true;
        }
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("gtqtcore.machine.fluidized", auxiliaryBlastFurnaceNumber));
            textList.add(new TextComponentTranslation("gtqtcore.machine.fluidized1", temp,tick));
        }
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("AAAPPPP", "BBBQQQQ", "BBBQQQQ", "BBBQQQQ", "ABARRRR", " B     ", " B     ", " B     ", " B     ", " B     ", " B     ", " B     ")
                .aisle("A A    ", "BBBQQQQ", "ICBQ  Q", "BCBQ  Q", "BCBRRRR", "BCB TT ", "BCB TT ", "BCB TT ", "BCB TT ", "BCBTTTT", "OCB    ", "BBB    ")
                .aisle("AAAPPPP", "BBBQQQQ", "BCBQQQQ", "BBBQQQQ", "ABARRRR", " B     ", " B     ", " B     ", " B     ", " B   T ", " B     ", " B     ")
                .aisle("       ", "BBB    ", "BCB    ", "BBB    ", "A A    ", "       ", "       ", "       ", "       ", "     T ", "       ", "       ")
                .aisle("       ", "BBB    ", "BCB    ", "BBB    ", "A A    ", "       ", "       ", "       ", "       ", "     T ", "       ", "       ")
                .aisle("       ", "BBB    ", "BCB    ", "BBB    ", "A A    ", "       ", "       ", "       ", "       ", "     T ", "       ", "       ")
                .aisle("AAAPPPP", "BBBQQQQ", "BCBQQQQ", "BBBQQQQ", "ABARRRR", " B     ", " B     ", " B     ", " B     ", " B   T ", " B     ", " B     ")
                .aisle("A A    ", "BBBQQQQ", "ICBQ  Q", "BCBQ  Q", "BCBRRRR", "BCB TT ", "BCB TT ", "BCB TT ", "BCB TT ", "BCBTTTT", "OCB    ", "BBB    ")
                .aisle("AAAPPPP", "BMBQQQQ", "BSBQQQQ", "BBBQQQQ", "ABARRRR", " B     ", " B     ", " B     ", " B     ", " B     ", " B     ", " B     ")
                .where('S', selfPredicate())
                .where('B', states(getCasingState()).setMinGlobalLimited(120)
                        .or(autoAbilities(true, true, true, true, false, false, false)))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('C', states(getCasingState2()))
                .where('A', states(getFrameState()))
                .where('I', states(getCasingState()).or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(2)))
                .where('O', states(getCasingState()).or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(2)))
                .where('#', any())
                //附属结构
                .where('P', GTQTTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace1",getFrameState()))
                .where('Q', GTQTTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace1", getCasingState()))
                .where('R', GTQTTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace1", getCasingState2()))
                .where('T', GTQTTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace1", getFrameState()))
                .build();
    }
    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = null;
        if (Blocks.AIR != null) {
            builder = MultiblockShapeInfo.builder()
                    .aisle("AAAPPPP", "BBBQQQQ", "BBBQQQQ", "BBBQQQQ", "ABARRRR", " B     ", " B     ", " B     ", " B     ", " B     ", " B     ", " B     ")
                    .aisle("A A    ", "BBBQQQQ", "BCBQ  Q", "BCBQ  Q", "BCBRRRR", "BCB TT ", "BCB TT ", "BCB TT ", "BCB TT ", "BCBTTTT", "BCB    ", "BBB    ")
                    .aisle("AAAPPPP", "BBBQQQQ", "BCBQQQQ", "BBBQQQQ", "ABARRRR", " B     ", " B     ", " B     ", " B     ", " B   T ", " B     ", " B     ")
                    .aisle("       ", "BBB    ", "BCB    ", "BBB    ", "A A    ", "       ", "       ", "       ", "       ", "     T ", "       ", "       ")
                    .aisle("       ", "BBB    ", "BCB    ", "BBB    ", "A A    ", "       ", "       ", "       ", "       ", "     T ", "       ", "       ")
                    .aisle("       ", "BBB    ", "BCB    ", "BBB    ", "A A    ", "       ", "       ", "       ", "       ", "     T ", "       ", "       ")
                    .aisle("AAAPPPP", "BBBQQQQ", "BCBQQQQ", "BBBQQQQ", "ABARRRR", " B     ", " B     ", " B     ", " B     ", " B   T ", " B     ", " B     ")
                    .aisle("A A    ", "BBBQQQQ", "BCBQ  Q", "BCBQ  Q", "BCBRRRR", "BCB TT ", "BCB TT ", "BCB TT ", "BCB TT ", "BCBTTTT", "BCB    ", "BBB    ")
                    .aisle("AAAPPPP", "BBBQQQQ", "BSBQQQQ", "BBBQQQQ", "ABARRRR", " B     ", " B     ", " B     ", " B     ", " B     ", " B     ", " B     ")
                    .where('S', GTQTMetaTileEntities.FLUIDIZED_BED, EnumFacing.SOUTH)
                    .where('C', getCasingState2())
                    .where('A', getFrameState())
                    .where('B', getCasingState())
                    .where(' ', Blocks.AIR.getDefaultState());
            shapeInfo.add(builder.build());
            shapeInfo.add(builder
                    .where('P', getFrameState())
                    .where('Q', getCasingState())
                    .where('R', getCasingState2())
                    .where('T', getFrameState())
                    .build());
        }
        return shapeInfo;
    }
    private static IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }
    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel);
    }

    private static IBlockState getCasingState2() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE);
    }

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
                return Textures.SOLID_STEEL_CASING;
    }


    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.POWER_SUBSTATION_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

}