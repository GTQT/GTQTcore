package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;


import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.*;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.BlockInfo;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtechfoodoption.GTFOConfig;
import gregtechfoodoption.block.GTFOGlassCasing;
import gregtechfoodoption.block.GTFOMetaBlocks;
import gregtechfoodoption.client.GTFOGuiTextures;
import gregtechfoodoption.recipe.GTFORecipeMaps;
import gregtechfoodoption.utils.GTFOLog;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.InvalidBlockStateException;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static gregtech.api.unification.material.Materials.Biomass;
import static gregtech.api.unification.material.Materials.StainlessSteel;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Dfeltamethrin;

public class MetaTileEntityGreenhousePlus extends RecipeMapMultiblockController {
    protected static IBlockState[] GRASSES;
    int coilHeight;
    int heatingCoilLevel;
    int temp=0;
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("temp", temp);
        return super.writeToNBT(data);
    }
   
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        temp = data.getInteger("temp");
    }
    public static void addGrasses() {
        GRASSES = new IBlockState[GTFOConfig.gtfoMiscConfig.greenhouseDirts.length];
        boolean errorsFound = false;
        for (int i = 0; i < GTFOConfig.gtfoMiscConfig.greenhouseDirts.length; i++) {
            String blockStateString = GTFOConfig.gtfoMiscConfig.greenhouseDirts[i];
            try {
                IBlockState state;
                final String[] splitBlockStateString = StringUtils.split(blockStateString, "[");
                final String blockString = splitBlockStateString[0];
                final String stateString;
                if (splitBlockStateString.length == 1) {
                    stateString = "default";
                } else if (splitBlockStateString.length == 2) {
                    stateString = StringUtils.reverse(StringUtils.reverse(StringUtils.split(blockStateString, "[")[1]).replaceFirst("]", ""));
                } else {
                    GTFOLog.logger.error("Block/BlockState Parsing error for \"" + blockStateString + "\"");
                    errorsFound = true;
                    continue;
                }

                final Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockString));
                if (block == null) {
                    GTFOLog.logger.error("Block Parsing error for \"" + blockString + "\". Block does not exist!");
                    errorsFound = true;
                    continue;
                }
                try {
                    state = CommandBase.convertArgToBlockState(block, stateString);
                    GRASSES[i] = state;
                } catch (NumberInvalidException e) {
                    GTFOLog.logger.error("BlockState Parsing error " + e + " for \"" + stateString + "\". Invalid Number!");
                    errorsFound = true;
                } catch (InvalidBlockStateException e) {
                    GTFOLog.logger.error("BlockState Parsing error " + e + " for \"" + stateString + "\". Invalid BlockState!");
                    errorsFound = true;
                }
            } catch (Exception e) {
                GTFOLog.logger.error("Smoothable BlockState Parsing error " + e + " for \"" + blockStateString + "\"");
                errorsFound = true;
            }
        }
        if (errorsFound)
            throw new IllegalArgumentException("One or more errors were found with the Greenhouse Blocks configuration for GTFO. Check log above.");
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        coilHeight = context.getInt("Count")/3;

        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
        } else {
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
        }
    }
    public MetaTileEntityGreenhousePlus(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTFORecipeMaps.GREENHOUSE_RECIPES);
        this.recipeMapWorkable = new GreenhouseWorkable(this);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCCCCC", "CCCCCCC", "CCCCCCC", "XXXFXXX", "XXXFXXX", "XXXFXXX", "XXXFXXX", "XXXFXXX", "   F   ")
                .aisle("CCCCCCC", "CDDDDDC", "C#####C", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", " XXFXX ")
                .aisle("CCCCCCC", "CDDDDDC", "C#####C", "F#####F", "F#####F", "F#####F", "F#####F", "F#####F", "FFFFFFF")
                .aisle("CCCCCCC", "CHDDDHC", "CH###HC", "X#####X", "X#####X", "X#####X", "X#####X", "X##H##X", "FXXFXXF").setRepeatable(1, 10)
                .aisle("CCCCCCC", "CDDDDDC", "C#####C", "F#####F", "F#####F", "F#####F", "F#####F", "F#####F", "FFFFFFF")
                .aisle("CCCCCCC", "CDDDDDC", "C#####C", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", " XXFXX ")
                .aisle("CCCYCCC", "CCCCCCC", "CCCCCCC", "XXXFXXX", "XXXFXXX", "XXXFXXX", "XXXFXXX", "XXXFXXX", "   F   ")
                .where('X', states(getCasingState()))
                .where('F', states(getFrameState()))
                .where('D', states(Blocks.DIRT.getDefaultState(), Blocks.GRASS.getDefaultState()).or(states(GRASSES)))
                .where('C', states(getCasingState2()).setMinGlobalLimited(10).or(autoAbilities()))
                .where('#', air())
                .where(' ', any())
                .where('H', coilPredicate())
                .where('Y', selfPredicate())
                .build();
    }
    private TraceabilityPredicate coilPredicate() {
        return new TraceabilityPredicate((blockWorldState) -> {
            IBlockState blockState = blockWorldState.getBlockState();
            if (blockState.getBlock() instanceof BlockWireCoil) {
                BlockWireCoil blockWireCoil = (BlockWireCoil) blockState.getBlock();
                BlockWireCoil.CoilType coilType = blockWireCoil.getState(blockState);
                Object currentCoilType = blockWorldState.getMatchContext().getOrPut("CoilType", coilType);
                if (!currentCoilType.toString().equals(coilType.getName())) {
                    blockWorldState.setError(new PatternStringError("gregtech.multiblock.pattern.error.coils"));
                    return false;
                } else {
                    blockWorldState.getMatchContext().increment("Count", 1);
                    blockWorldState.getMatchContext().getOrPut("VABlock", new LinkedList<>()).add(blockWorldState.getPos());
                    return true;
                }
            } else {
                return false;
            }
        }, () -> Arrays.stream(BlockWireCoil.CoilType.values()).map((type) -> new BlockInfo(MetaBlocks.WIRE_COIL.getState(type), null)).toArray(BlockInfo[]::new)).addTooltips("gregtech.multiblock.pattern.error.coils");
    }
    protected IBlockState getCasingState() {
        return GTFOMetaBlocks.GTFO_GLASS_CASING.getState(GTFOGlassCasing.CasingType.GREENHOUSE_GLASS);
    }
    protected IBlockState getCasingState2() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);
    }

    protected IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(StainlessSteel).getBlock(StainlessSteel);
    }


    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.CLEAN_STAINLESS_STEEL_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityGreenhousePlus(metaTileEntityId);
    }

    public boolean checkNaturalLighting() {
        if (!this.getWorld().isDaytime())
            return false;
        for (BlockPos pos : BlockPos.getAllInBox(this.getPos().up(8).offset(this.frontFacing.rotateY(), 3),
                this.getPos().up(8).offset(this.getFrontFacing().rotateYCCW(), 3).offset(this.getFrontFacing().getOpposite(), 6))) {
            if (!this.getWorld().canSeeSky(pos.up())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("绿化", new Object[0]));
        tooltip.add(I18n.format("gregtechfoodoption.machine.greenhouse.tooltip.1"));
        tooltip.add(I18n.format("gregtechfoodoption.machine.greenhouse.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.greenhouse.tooltip.4"));
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (this.isStructureFormed()) {
            if (!((GreenhouseWorkable)this.recipeMapWorkable).hasSun())
                textList.add(new TextComponentTranslation("gregtech.multiblock.not_enough_sun")
                        .setStyle(new Style().setColor(TextFormatting.RED)));
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ((GreenhouseWorkable) this.recipeMapWorkable).hasSun = this.checkNaturalLighting();
        return super.createUI(entityPlayer);
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("gtqtcore.multiblock.gr.tooltips1",coilHeight,heatingCoilLevel));
        textList.add(new TextComponentTranslation("gtqtcore.multiblock.gr.tooltips2",temp));
    }
    protected class GreenhouseWorkable extends MultiblockRecipeLogic {
        IMultipleTankHandler inputTank = getInputFluidInventory();
        FluidStack HEAT_STACK = Dfeltamethrin.getFluid(1);
        FluidStack WATER_STACK = Biomass.getFluid(20);
        private boolean hasSun;

        public GreenhouseWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        protected void setupRecipe(Recipe recipe) {
            super.setupRecipe(recipe);
            this.hasSun = ((MetaTileEntityGreenhousePlus) metaTileEntity).checkNaturalLighting();
        }
        @Override
        public int getParallelLimit() {
            return coilHeight*heatingCoilLevel;
        }
        public boolean hasSun() {
            return hasSun;
        }

        @Override
        protected void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)) {
                this.drawEnergy(this.recipeEUt, false);

                if (WATER_STACK.isFluidStackIdentical(inputTank.drain(WATER_STACK, false))) {
                    inputTank.drain(WATER_STACK, true);
                    progressTime++;
                }

                if (this.hasSun) this.progressTime++;

                if(temp>0) {temp--;progressTime++;}
                progressTime++;
                if (this.progressTime > this.maxProgressTime&&temp>0) {
                    this.completeRecipe();
                }
            }
        }
        @Override
        public void setMaxProgress(int maxProgress)
        {
            this.maxProgressTime = maxProgress/getParallelLimit();
        }
        @Override
        public void update() {
            super.update();
            if (temp<=100000) {
                if (HEAT_STACK.isFluidStackIdentical(inputTank.drain(HEAT_STACK, false))) {
                    inputTank.drain(HEAT_STACK, true);
                    temp = temp + 20;
                }
            }

        }
        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound tag = super.serializeNBT();
            tag.setBoolean("hasSun", hasSun);
            return tag;
        }

        @Override
        public void deserializeNBT(@Nonnull NBTTagCompound compound) {
            super.deserializeNBT(compound);
            this.hasSun = compound.getBoolean("hasSun");
        }
    }

    @Override
    protected  TextureArea getLogo() {
        return GTFOGuiTextures.GTFO_LOGO_WORKING;
    }

    @Override
    protected  TextureArea getWarningLogo() {
        return GTFOGuiTextures.GTFO_LOGO_WARNING;
    }

    @Override
    protected  TextureArea getErrorLogo() {
        return GTFOGuiTextures.GTFO_LOGO_ERROR;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public boolean isMultiblockPartWeatherResistant( IMultiblockPart part) {
        return true;
    }
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ROASTER_OVERLAY;
    }
    @Override
    public boolean getIsWeatherOrTerrainResistant() {
        return true;
    }
}
