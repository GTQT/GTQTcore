package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IMufflerHatch;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.*;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.*;
import keqing.gtqtcore.api.metaileentity.GTQTRecipeMapMultiblockController;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static gregtech.api.unification.material.Materials.Steam;
import static gregtech.api.util.RelativeDirection.*;
//闪蒸
public class MetaTileEntityMSF extends GTQTRecipeMapMultiblockController implements  IProgressBarMultiblock {
    private int coilLevel;
    private int number;
    private int size;
    int[] steam=new int[3];
    FluidStack STEAM = Steam.getFluid(1000);
    @Override
    public void update() {
        super.update();
        if (steam[0] <= 9000) {
            IMultipleTankHandler inputTank = getInputFluidInventory();
            if (STEAM.isFluidStackIdentical(inputTank.drain(STEAM, false))) {
                    inputTank.drain(STEAM, true);
                    steam[0] = steam[0] + 80*coilLevel;

            }
        }

        for(int i=0;i<coilLevel;i++) {
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

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("fluid1", steam[0]);
        data.setInteger("fluid2", steam[1]);
        data.setInteger("fluid3", steam[2]);
        return super.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        steam[0] = data.getInteger("fluid1");
        steam[1] = data.getInteger("fluid2");
        steam[2] = data.getInteger("fluid3");
    }

    public MetaTileEntityMSF(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[] {
                GTQTcoreRecipeMaps.SFM,
                GTQTcoreRecipeMaps.DISTILLATION_KETTLE,
                RecipeMaps.DISTILLATION_RECIPES
        });
        this.recipeMapWorkable = new MFSWorkableHandler(this);
    }
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.msf.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.msf.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.msf.tooltip.3"));
        tooltip.add(I18n.format("gregtech.machine.msf.tooltip.4"));
    }
    private class MFSWorkableHandler extends MultiblockRecipeLogic {
        private boolean isDistilleryMode() {
            return this.getRecipeMap() == GTQTcoreRecipeMaps.DISTILLATION_KETTLE;
        }
        private boolean isDistillationMode() {
            return this.getRecipeMap() == GTQTcoreRecipeMaps.PYROLYSIS_TOWER;
        }
        public MFSWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }
        @Override
        public int getParallelLimit() {
            if(getStatue()) {
                if (isDistilleryMode()) {
                    return number;
                } else if (isDistillationMode()) {
                    return number*2;
                }
                else return number*3;
            }
            return number;
        }
        @Override
        public void setMaxProgress(int maxProgress)
        {
            if(getStatue()) {
                if (isDistilleryMode()) {
                    this.maxProgressTime = maxProgress /(number);
                } else if (isDistillationMode()) {
                    this.maxProgressTime = maxProgress /(number*2);
                }
                else maxProgressTime = maxProgress /(number * 3);
            }
            else this.maxProgressTime = maxProgress/number;
        }
        public  boolean getStatue()
        {
            return steam[0] > 6000 && steam[1] > 6000 && steam[2] > 6000;
        }
        protected void updateRecipeProgress() {
            if (canRecipeProgress && drawEnergy(recipeEUt, true)&&SIZE()) {
                this.drawEnergy(this.recipeEUt, false);
                if (++progressTime > maxProgressTime)
                {
                    steam[0]=(int) (steam[0]*0.24);
                    steam[1]=(int) (steam[1]*0.48);
                    steam[2]=(int) (steam[2]*0.64);
                    completeRecipe();
                }
            }
        }
    }
    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (isStructureFormed()) {
            FluidStack lubricantStack = getInputFluidInventory().drain(Steam.getFluid(Integer.MAX_VALUE), false);
            if (lubricantStack == null || lubricantStack.amount < 1000) {
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.sfm.no_water1"));
            }
            if (!SIZE()) {
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.sfm.no_water2"));
            }
        }
    }
    public Boolean SIZE()
    {
        return size==number;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        number = context.getInt("Count");

        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.coilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
        } else {
            this.coilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
        }

        List<IFluidTank> i = getAbilities(MultiblockAbility.EXPORT_FLUIDS);
        this.size=i.size();
    }
    public  boolean getStatue()
    {
        return steam[0] > 6000 && steam[1] > 6000 && steam[2] > 6000;
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (getInputFluidInventory() != null) {
            FluidStack SteamStack = getInputFluidInventory().drain(Steam.getFluid(Integer.MAX_VALUE), false);
            int SteamAmount = SteamStack == null ? 0 : SteamStack.amount;
            textList.add(new TextComponentTranslation("gtqtcore.msf.count1", number, coilLevel, TextFormattingUtil.formatNumbers(SteamAmount)));
        }
        textList.add(new TextComponentTranslation("gtqtcore.msf.count2", (double)steam[0]/1000,(double)steam[1]/1000,(double)steam[2]/1000));
        if(getStatue())  textList.add(new TextComponentTranslation("gtqtcore.msf.good"));
        else textList.add(new TextComponentTranslation("gtqtcore.msf.no"));
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityMSF(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        FactoryBlockPattern pattern = FactoryBlockPattern.start(RIGHT, BACK, UP)
                .aisle("HHH","HHH", "HHH", "HHH", " H ")
                .aisle("HHH","HHH", "HHH", "HHH", " S ")
                .aisle("HHH","HHH", "HHH", "HHH", "FHF")
                .aisle("F F","FUF", "FUF", "FUF", "F F")
                .aisle("F F","FUF", "FUF", "FUF", "F F")
                .aisle("F F","FUF", "FUF", "FUF", "F F")
                .aisle("F F","FUF", "FUF", "FUF", "F F")
                .aisle("F F","YYY", "YCO", "YYY", "FYF").setRepeatable(1, 12)
                .aisle("F F","YYY", "YYB", "YYY", " Y ")
                .where('S', selfPredicate())
                .where('Y',states(getCasingAState()))
                .where('U',states(getCasingBState()))
                .where('H',states(getCasingAState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2).setPreviewCount(2))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setExactLimit(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setMaxGlobalLimited(4).setPreviewCount(2))
                )
                .where('B',(abilities(MultiblockAbility.EXPORT_ITEMS)))
                .where('O',(abilities(MultiblockAbility.EXPORT_FLUIDS)))
                .where('C', coilPredicate())
                .where('F', states(getFrameState()))
                .where(' ', any());
        return pattern.build();
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
    public boolean hasMufflerMechanics() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.FROST_PROOF_CASING;
    }
    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.POWER_SUBSTATION_OVERLAY;
    }

    @Override
    public int getNumProgressBars() {
        return 3;
    }

    @Override
    public void addBarHoverText(List<ITextComponent> hoverList, int index) {
        ITextComponent cwutInfo = TextComponentUtil.stringWithColor(
                TextFormatting.AQUA,
                (steam[index] + 1000)+ " / " + (10000) + " kPa");
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

    private static IBlockState getCasingAState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.ALUMINIUM_FROSTPROOF);
    }
    private static IBlockState getCasingBState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE);
    }
    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.Aluminium).getBlock(Materials.Aluminium);
    }
}
