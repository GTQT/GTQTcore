package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregicality.science.common.block.GCYSMetaBlocks;
import gregicality.science.common.block.blocks.BlockTransparentCasing;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
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
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.chemical_plant.ChemicalPlantProperties;
import keqing.gtqtcore.api.metaileentity.GTQTRecipeMapMultiblockController;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.BRProperty;
import keqing.gtqtcore.api.recipes.properties.PAPartProperty;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTIsaCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import java.util.List;
import java.util.Random;

import static gregtech.api.unification.material.Materials.*;

public class MetaTileEntityBiologicalReaction extends GTQTRecipeMapMultiblockController {
    public MetaTileEntityBiologicalReaction(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[] {
                GTQTcoreRecipeMaps.BIOLOGICAL_REACTION_RECIPES,
                RecipeMaps.FERMENTING_RECIPES,
                RecipeMaps.BREWING_RECIPES
        });
        this.recipeMapWorkable = new BiologicalReactionLogic(this);
    }
    private int glass_tier;
    private int clean_tier;
    int liquid=0;
    int bio=0;
    double rate=0;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("liquid", liquid);
        data.setInteger("bio", bio);
        data.setDouble("rate", rate);
        return super.writeToNBT(data);
    }
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        liquid = data.getInteger("liquid");
        bio = data.getInteger("bio");
        rate = data.getDouble("rate");
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityBiologicalReaction(metaTileEntityId);
    }

    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 18, 18, "", this::clear)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("我不要了！"));

        return group;
    }

    private void clear(Widget.ClickData clickData) {
        liquid=0;
        bio=0;
        rate=0;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object glass_tier = context.get("LGLTiredStats");
        Object clean_tier = context.get("ZJTiredStats");
        this.glass_tier = GTQTUtil.getOrDefault(() -> glass_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)glass_tier).getIntTier(),
                0);
        this.clean_tier = GTQTUtil.getOrDefault(() -> clean_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)clean_tier).getIntTier(),
                0);
    }
    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("JCCCJ","JCCCJ", "GGGGG", "GGGGG", "CCCCC")
                .aisle("JCCCJ","JPPPJ", "G   G", "G   G", "CCCCC")
                .aisle("JCCCJ","JPPPJ", "G   G", "G   G", "CCCCC")
                .aisle("JCCCJ","JPPPJ", "G   G", "G   G", "CCCCC")
                .aisle("JCCCJ","JCSCJ", "GGGGG", "GGGGG", "CCCCC")
                .where('S', selfPredicate())
                .where('J', TiredTraceabilityPredicate.CP_ZJ_CASING)
                .where('G', TiredTraceabilityPredicate.CP_LGLASS)
                .where('C', states(getCasingState()).setMinGlobalLimited(38).or(autoAbilities()))
                .where('P', states(getCasingState1()))
                .where(' ', any())
                .build();
    }

    private static IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);
    }

    private static IBlockState getCasingState1() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.CLEAN_STAINLESS_STEEL_CASING;
    }
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);

        if (getInputFluidInventory() != null) {
            FluidStack STACK = getInputFluidInventory().drain(Water.getFluid(Integer.MAX_VALUE), false);
            int liquidOxygenAmount = STACK == null ? 0 : STACK.amount;
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.br.amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
        }
        textList.add(new TextComponentTranslation("gtqtcore.multiblock.br.1",liquid,bio,rate));
        if(rate>100)
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.br.2"));
    }


    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        int number=recipe.getProperty(BRProperty.getInstance(), 0);
        if(rate>=number)
            return super.checkRecipe(recipe, consumeIfSuccess);
        else return false;
    }

    protected class BiologicalReactionLogic extends MultiblockRecipeLogic {

        public BiologicalReactionLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity,true);
        }
        FluidStack WATER = Water.getFluid(100);
        FluidStack BIO1 = RawGrowthMedium.getFluid(10);
        FluidStack BIO2 = EnrichedBacterialSludge .getFluid(40);
        FluidStack BIO3 = FermentedBiomass.getFluid(80);
        FluidStack BIO4 = Biomass.getFluid(100);



        @Override
        public void update() {
            IMultipleTankHandler inputTank = getInputFluidInventory();
/*

            int mCurrentDirectionX;
            int mCurrentDirectionZ;
            int mOffsetX_Lower;
            int mOffsetX_Upper;
            int mOffsetZ_Lower;
            int mOffsetZ_Upper;

            mCurrentDirectionX = 2;
            mCurrentDirectionZ = 2;

            mOffsetX_Lower = -2;
            mOffsetX_Upper = 2;
            mOffsetZ_Lower = -2;
            mOffsetZ_Upper = 2;

            final int xDir = this.metaTileEntity.getFrontFacing().getOpposite().getXOffset()
                    * mCurrentDirectionX;
            final int zDir = this.metaTileEntity.getFrontFacing().getOpposite().getZOffset()
                    * mCurrentDirectionZ;

 */

            if (BIO1.isFluidStackIdentical(inputTank.drain(BIO1, false))) {
                inputTank.drain(BIO1, true);
                bio = bio + 100;
            }
            if (BIO2.isFluidStackIdentical(inputTank.drain(BIO2, false))) {
                inputTank.drain(BIO2, true);
                bio = bio + 100;
            }
            if (BIO3.isFluidStackIdentical(inputTank.drain(BIO3, false))) {
                inputTank.drain(BIO3, true);
                bio = bio + 100;
            }
            if (BIO4.isFluidStackIdentical(inputTank.drain(BIO4, false))) {
                inputTank.drain(BIO4, true);
                bio = bio + 100;
            }

            if (WATER.isFluidStackIdentical(inputTank.drain(WATER, false))) {
                if(liquid<32000) {
                    inputTank.drain(WATER, true);
                    liquid = liquid + 100;
                }

            }
/*
            if(liquid>16000) {
                    for (int i = mOffsetX_Lower + 1; i <= mOffsetX_Upper - 1; ++i) {
                        for (int j = mOffsetZ_Lower + 1; j <= mOffsetZ_Upper - 1; ++j) {
                            for (int h = 1; h <=1; h++) {
                                BlockPos waterCheckPos = this.metaTileEntity.getPos().add(xDir + i, h, zDir + j);
                                this.metaTileEntity.getWorld().setBlockState(
                                        waterCheckPos,
                                        Blocks.WATER.getDefaultState());
                            }
                        }
                }
            }

            if(liquid>24000) {
                for (int i = mOffsetX_Lower + 1; i <= mOffsetX_Upper - 1; ++i) {
                    for (int j = mOffsetZ_Lower + 1; j <= mOffsetZ_Upper - 1; ++j) {
                        for (int h = 2; h <=2; h++) {
                            BlockPos waterCheckPos = this.metaTileEntity.getPos().add(xDir + i, h, zDir + j);
                            this.metaTileEntity.getWorld().setBlockState(
                                    waterCheckPos,
                                    Blocks.WATER.getDefaultState());
                        }
                    }
                }
            }



 */
            if(liquid==0) rate=0;
            else rate=bio*100.0/liquid;
        }
        protected int getp()
        {
            if(rate>90)return 8;
            if(rate>80)return 4;
            if(rate>60)return 2;
            if(rate>40)return 1;
            return 1;
        }
        @Override
        public int getParallelLimit() {
            return getp()*clean_tier;
        }
        public void setMaxProgress(int maxProgress) {
                this.maxProgressTime = (maxProgress*(100-glass_tier*getp())/100);
        }
        /*
        protected void checjwater()
        {
            int mCurrentDirectionX;
            int mCurrentDirectionZ;

            mCurrentDirectionX = 2;
            mCurrentDirectionZ = 2;

            final int xDir = this.metaTileEntity.getFrontFacing().getOpposite().getXOffset()
                    * mCurrentDirectionX;
            final int zDir = this.metaTileEntity.getFrontFacing().getOpposite().getZOffset()
                    * mCurrentDirectionZ;

            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    for (int h = 1; h <= 2; h++) {
                        BlockPos waterCheckPos =this.metaTileEntity.getPos().add(xDir + i, h, zDir + j);
                        this.metaTileEntity.getWorld().setBlockState(
                                waterCheckPos,
                                Blocks.AIR.getDefaultState());
                    }
                }
            }
        }


         */
        protected void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)) {
                this.drawEnergy(this.recipeEUt, false);
                    if(liquid>16000) {
                        liquid = liquid - 100;
                        if (++progressTime > maxProgressTime) {
                            completeRecipe();
                            liquid=liquid/2;bio=bio*2/7;
                            //checjwater();
                        }
                    }
            }
        }
    }
}
