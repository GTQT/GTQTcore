package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.logic.OverclockingLogic;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTRecipeMapMultiblockOverwrite;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static gregtech.api.GTValues.VA;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CZPULLER_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Pyrotheum;

public class MetaTileEntityBlazingCZPuller extends GTQTRecipeMapMultiblockOverwrite implements IHeatingCoil {
    private int blastFurnaceTemperature;
    protected static int heatingCoilLevel;
    protected int heatingCoilDiscount;

    public MetaTileEntityBlazingCZPuller(ResourceLocation metaTileEntityId) {
            super(metaTileEntityId, CZPULLER_RECIPES);
        this.recipeMapWorkable = new BlazingBlastFurnaceWorkable(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityBlazingCZPuller(this.metaTileEntityId);
    }

    int ParallelNum=1;

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("modern", modern);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        modern = data.getInteger("modern");
    }

    @Override
    public void update() {
        super.update();
        if (modern == 0)
        {
            ParallelNum=ParallelNumA;
        }
        if (modern == 1)
        {
            P = (int) ((this.energyContainer.getEnergyStored() + energyContainer.getInputPerSec())/(getMinVa()==0?1:getMinVa()));
            ParallelNum = Math.min(P, ParallelLim);
        }
    }
    public int getMinVa()
    {
        if((Math.min(this.energyContainer.getEnergyCapacity()/32,VA[3])*20)==0)return 1;
        return (int)(Math.min(this.energyContainer.getEnergyCapacity()/32,VA[3]));

    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoilType");
        Object coilType = context.get("CoilType");
        if (type instanceof IHeatingCoilBlockStats) {
            this.blastFurnaceTemperature = ((IHeatingCoilBlockStats) type).getCoilTemperature();
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
        } else {
            this.blastFurnaceTemperature = BlockWireCoil.CoilType.CUPRONICKEL.getCoilTemperature();
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
        }
        ParallelLim=Math.min((int)Math.pow(2, heatingCoilLevel),64);
        ParallelNum=ParallelLim;
        this.blastFurnaceTemperature += 100 * Math.max(0, GTUtility.getTierByVoltage(getEnergyContainer().getInputVoltage()) - GTValues.MV);
    }


    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return this.blastFurnaceTemperature >= recipe.getProperty(TemperatureProperty.getInstance(), 0);
    }


    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX","PPP", "CCC", "CCC", "CCC", "XXX")
                .aisle("XXX","PPP", "C#C", "C#C", "C#C", "XMX")
                .aisle("XSX","PPP", "CCC", "CCC", "CCC", "XXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(9)
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY)))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('C', heatingCoils())
                .where('P', states(getCasingState2()))
                .where('#', air())
                .build();
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("EEM","PPP", "CCC","CCC", "CCC", "XXX")
                .aisle("FXX","PPP", "C#C","C#C", "C#C", "XHX")
                .aisle("ISO","PPP", "CCC","CCC", "CCC", "XXX")
                .where('X', getCasingState())
                .where('P', getCasingState2())
                .where('S', GTQTMetaTileEntities.BLAZING_CZ_PULLER, EnumFacing.SOUTH)
                .where('#', Blocks.AIR.getDefaultState())
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.LV], EnumFacing.NORTH)
                .where('I', MetaTileEntities.ITEM_IMPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
                .where('O', MetaTileEntities.ITEM_EXPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
                .where('F', MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.LV], EnumFacing.WEST)
                .where('H', MetaTileEntities.MUFFLER_HATCH[GTValues.LV], EnumFacing.UP)
                .where('M',  MetaTileEntities.MAINTENANCE_HATCH,EnumFacing.NORTH);
        GregTechAPI.HEATING_COILS.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
                .forEach(entry -> shapeInfo.add(builder.where('C', entry.getKey()).build()));
        return shapeInfo;
    }

    private static IBlockState getCasingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.HIGH_TEMPERATURE_CASING);
    }
    private static IBlockState getCasingState2() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.HEAT_VENT);
    }
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("风韵犹存的硅", new Object[0]));
        tooltip.add(I18n.format("gregtech.machine.cracker.gtqtupdate.1"));
        tooltip.add(I18n.format("gtqtcore.multiblock.vc.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.multiblock.vc.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.multiblock.vc.tooltip.3"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.3"));
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            super.addDisplayText(textList);
            if (getInputFluidInventory() != null) {
                FluidStack LubricantStack = getInputFluidInventory().drain(Pyrotheum.getFluid(Integer.MAX_VALUE), false);
                 int liquidOxygenAmount = LubricantStack == null ? 0 : LubricantStack.amount;
                 textList.add(new TextComponentTranslation("gtqtcore.multiblock.vc.amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
                }
            textList.add(new TextComponentTranslation("Temperature : %s / Discount ：%s", blastFurnaceTemperature,getDiscount()));
            if(modern==0) textList.add(new TextComponentTranslation("gtqtcore.tire1",heatingCoilLevel));
            if(modern==1) textList.add(new TextComponentTranslation("gtqtcore.tire2",heatingCoilLevel));
            textList.add(new TextComponentTranslation("gtqtcore.parr",ParallelNum,ParallelLim));
        }
    }
    public double getDiscount()
    {
        return (32-heatingCoilLevel)/32.0;
    }
    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (isStructureFormed()) {
            FluidStack lubricantStack = getInputFluidInventory().drain(Pyrotheum.getFluid(Integer.MAX_VALUE), false);
            if (lubricantStack == null || lubricantStack.amount == 0) {
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.vc.no"));
            }
        }
    }
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GCYMTextures.BLAST_CASING;
    }

    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return GCYMTextures.ALLOY_BLAST_SMELTER_OVERLAY;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public int getCurrentTemperature() {
        return this.blastFurnaceTemperature;
    }

    public static int getMaxParallel(int heatingCoilLevel) {
        return heatingCoilLevel;
    }



    private final FluidStack LUBRICANT_STACK = Pyrotheum.getFluid(heatingCoilLevel);

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        blastFurnaceTemperature = 0;
        heatingCoilLevel = 0;
    }
    protected class BlazingBlastFurnaceWorkable extends MultiblockRecipeLogic {

        private final MetaTileEntityBlazingCZPuller combustionEngine;
        public BlazingBlastFurnaceWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
            this.combustionEngine = (MetaTileEntityBlazingCZPuller) tileEntity;
        }

        @Override
        public int getParallelLimit() {
            return ParallelNum;
        }
        protected void updateRecipeProgress() {
            IMultipleTankHandler inputTank = combustionEngine.getInputFluidInventory();
            if (canRecipeProgress && drawEnergy((int) (recipeEUt*getDiscount()), true)&&LUBRICANT_STACK.isFluidStackIdentical(inputTank.drain(LUBRICANT_STACK, false))) {
                drawEnergy((int) (recipeEUt*getDiscount()), false);
                LUBRICANT_STACK.isFluidStackIdentical(inputTank.drain(LUBRICANT_STACK, true));
                if (++progressTime > maxProgressTime) {
                    completeRecipe();
                }
            }
        }
        protected void modifyOverclockPre( int[] values, IRecipePropertyStorage storage) {
            super.modifyOverclockPre(values, storage);
            values[0] = OverclockingLogic.applyCoilEUtDiscount(values[0], ((IHeatingCoil)this.metaTileEntity).getCurrentTemperature(), (Integer)storage.getRecipePropertyValue(TemperatureProperty.getInstance(), 0));
        }

        protected  int[] runOverclockingLogic( IRecipePropertyStorage propertyStorage, int recipeEUt, long maxVoltage, int duration, int amountOC) {
            return OverclockingLogic.heatingCoilOverclockingLogic(Math.abs(recipeEUt), maxVoltage, duration, amountOC, ((IHeatingCoil)this.metaTileEntity).getCurrentTemperature(), (Integer)propertyStorage.getRecipePropertyValue(TemperatureProperty.getInstance(), 0));
        }
        @Override
        public void setMaxProgress(int maxProgress)
        {
            if(ParallelNum==0)
                this.maxProgressTime = maxProgress;
            else if(ParallelNum<=16)
                this.maxProgressTime = maxProgress/ParallelNum;
            else if(ParallelNum<=64)
                this.maxProgressTime = maxProgress*16/ParallelNum;
            else if(ParallelNum<=256)
                this.maxProgressTime = maxProgress*128/ParallelNum;
            else
                this.maxProgressTime = maxProgress;
        }
    }
}