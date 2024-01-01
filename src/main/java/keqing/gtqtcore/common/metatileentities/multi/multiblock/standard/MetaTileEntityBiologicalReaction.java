package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregicality.science.common.block.GCYSMetaBlocks;
import gregicality.science.common.block.blocks.BlockTransparentCasing;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.capability.chemical_plant.ChemicalPlantProperties;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.BRProperty;
import keqing.gtqtcore.api.recipes.properties.PAPartProperty;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTIsaCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
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

public class MetaTileEntityBiologicalReaction extends RecipeMapMultiblockController {
    public MetaTileEntityBiologicalReaction(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.BIOLOGICAL_REACTION_RECIPES);
        this.recipeMapWorkable = new BiologicalReactionLogic(this);
    }

    int liquid=0;

    int bio=0;

    double rate=0;
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

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCCC", "GGGGG", "GGGGG", "CCCCC")
                .aisle("CCCCC", "G   G", "G   G", "CCCCC")
                .aisle("CCCCC", "G   G", "G   G", "CCCCC")
                .aisle("CCCCC", "G   G", "G   G", "CCCCC")
                .aisle("CCSCC", "GGGGG", "GGGGG", "CCCCC")
                .where('S', selfPredicate())
                .where('G', states(getGlassState()))
                .where('C', states(getCasingState()).setMinGlobalLimited(42).or(autoAbilities()))
                .where(' ', any())
                .build();
    }

    private static IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);
    }
    private static IBlockState getGlassState() {
        return GCYSMetaBlocks.TRANSPARENT_CASING.getState(BlockTransparentCasing.CasingType.PMMA);
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
        int number=recipe.getProperty(PAPartProperty.getInstance(), 0);
        if(rate>=number-3&&rate<=number+3)
            return super.checkRecipe(recipe, consumeIfSuccess);
        else return false;
    }

    protected class BiologicalReactionLogic extends MultiblockRecipeLogic {

        public BiologicalReactionLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity,true);
        }
        FluidStack WATER = Water.getFluid(100);
        FluidStack BIO = RawGrowthMedium.getFluid(100);


        @Override
        public void update() {
            IMultipleTankHandler inputTank = getInputFluidInventory();


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


            if (WATER.isFluidStackIdentical(inputTank.drain(WATER, false))) {
                if(liquid<32000) {
                    inputTank.drain(WATER, true);
                    liquid = liquid + 100;
                }

            }

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

            if (BIO.isFluidStackIdentical(inputTank.drain(BIO, false))) {
                inputTank.drain(BIO, true);
                bio = bio + 100;
            }
            if(liquid==0) rate=0;
            else rate=bio*100/liquid;
        }



        protected void updateRecipeProgress() {
            int mCurrentDirectionX;
            int mCurrentDirectionZ;

            mCurrentDirectionX = 2;
            mCurrentDirectionZ = 2;

            final int xDir = this.metaTileEntity.getFrontFacing().getOpposite().getXOffset()
                    * mCurrentDirectionX;
            final int zDir = this.metaTileEntity.getFrontFacing().getOpposite().getZOffset()
                    * mCurrentDirectionZ;
            if (canRecipeProgress) {
                    if(liquid>16000&&liquid<=24000) {
                        liquid = liquid - 100;
                        if (++progressTime > maxProgressTime) {
                            completeRecipe();
                            liquid=0;bio=0;
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
                    }
            }
        }
    }
}
