package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.GTValues;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.RecipeProgressWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapPrimitiveMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.particle.VanillaParticleEffects;
import gregtech.client.renderer.CubeRendererState;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.cclop.ColourOperation;
import gregtech.client.renderer.cclop.LightMapOperation;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.BloomEffectUtil;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;

import keqing.gtqtcore.api.capability.impl.NoEnergyMultiblockRecipeLogic;
import keqing.gtqtcore.api.metaileentity.multiblock.NoEnergyMultiblockController;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;

import java.util.List;

import static gregtech.api.unification.material.Materials.Lava;
import static gregtech.api.unification.material.Materials.Water;

public class MetaTileEntitySaltField  extends NoEnergyMultiblockController {

    public MetaTileEntitySaltField(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.SALT_FLIED);
        this.recipeMapWorkable = new MetaTileEntitySaltField.SaltFieldLogic(this);
    }

    public boolean hasMaintenanceMechanics() {
        return false;
    }


    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }

    protected IBlockState getCasingState1() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("EEEEEEEEE", "EEEEEEEEE")
                .aisle("EXXXXXXXE", "E#######E")
                .aisle("EXXXXXXXE", "E#######E")
                .aisle("EXXXXXXXE", "E#######E")
                .aisle("EXXXXXXXE", "E#######E")
                .aisle("EXXXXXXXE", "E#######E")
                .aisle("EXXXXXXXE", "E#######E")
                .aisle("EXXXXXXXE", "E#######E")
                .aisle("EEEESEEEE", "EEEEEEEEE")
                .where('S', selfPredicate())
                .where('X', states(getCasingState1()))
                .where('E', states(getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setMaxGlobalLimited(5))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setExactLimit(1)))
                .where('#', any())
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.sf.1"));
            if (getInputFluidInventory() != null) {
                FluidStack STACK = getInputFluidInventory().drain(Water.getFluid(Integer.MAX_VALUE), false);
                int liquidOxygenAmount = STACK == null ? 0 : STACK.amount;
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.sf.amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
            }

        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntitySaltField(metaTileEntityId);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.POWER_SUBSTATION_OVERLAY;
    }



    protected class SaltFieldLogic extends NoEnergyMultiblockRecipeLogic {

        public SaltFieldLogic(NoEnergyMultiblockController tileEntity) {
            super(tileEntity, tileEntity.recipeMap);
        }

        public SaltFieldLogic(NoEnergyMultiblockController tileEntity, RecipeMap<?> recipeMap) {
            super(tileEntity, recipeMap);
        }

        @Override
        public void update() {
            super.update();

        }
        FluidStack WATER = Water.getFluid(40);

        protected void updateRecipeProgress() {
            IMultipleTankHandler inputTank = getInputFluidInventory();
            if (canRecipeProgress) {
                int mCurrentDirectionX;
                int mCurrentDirectionZ;
                int mOffsetX_Lower;
                int mOffsetX_Upper;
                int mOffsetZ_Lower;
                int mOffsetZ_Upper;

                mCurrentDirectionX = 4;
                mCurrentDirectionZ = 4;

                mOffsetX_Lower = -4;
                mOffsetX_Upper = 4;
                mOffsetZ_Lower = -4;
                mOffsetZ_Upper = 4;

                // if (aBaseMetaTileEntity.fac)

                final int xDir = this.metaTileEntity.getFrontFacing().getOpposite().getXOffset()
                        * mCurrentDirectionX;
                final int zDir = this.metaTileEntity.getFrontFacing().getOpposite().getZOffset()
                        * mCurrentDirectionZ;

                if (WATER.isFluidStackIdentical(inputTank.drain(WATER, false))) {
                    inputTank.drain(WATER, true);


                    for (int i = mOffsetX_Lower + 1; i <= mOffsetX_Upper - 1; ++i) {
                        for (int j = mOffsetZ_Lower + 1; j <= mOffsetZ_Upper - 1; ++j) {
                         for (int h = 1; h < 2; h++) {
                              BlockPos waterCheckPos =this.metaTileEntity.getPos().add(xDir + i, h, zDir + j);
                              this.metaTileEntity.getWorld().setBlockState(
                                    waterCheckPos,
                                    Blocks.WATER.getDefaultState());
                            }
                        }
                    }

                    if (++progressTime > maxProgressTime) {
                        completeRecipe();
                        for (int i = mOffsetX_Lower + 1; i <= mOffsetX_Upper - 1; ++i) {
                            for (int j = mOffsetZ_Lower + 1; j <= mOffsetZ_Upper - 1; ++j) {
                                for (int h = 1; h < 2; h++) {
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