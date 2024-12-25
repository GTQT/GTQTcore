package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregicality.multiblocks.api.render.GCYMTextures;
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
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.logic.OverclockingLogic;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTRecipeMapMultiblockControllerOverwrite;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
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
import static keqing.gtqtcore.api.unification.GTQTMaterials.Pyrotheum;

public class MetaTileEntityBlazingBlastFurnace extends GTQTRecipeMapMultiblockControllerOverwrite implements IHeatingCoil {
    protected static int heatingCoilLevel;
    int ParallelNum = 1;
    private FluidStack LUBRICANT_STACK;
    private int blastFurnaceTemperature;

    public MetaTileEntityBlazingBlastFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.BLAST_RECIPES,
                GTQTcoreRecipeMaps.BURNER_REACTOR_RECIPES
        });
        this.recipeMapWorkable = new BlazingBlastFurnaceWorkable(this);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.ADVANCED_INVAR_CASING);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityBlazingBlastFurnace(this.metaTileEntityId);
    }

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
        if (modern == 0) {
            ParallelNum = ParallelNumA;
        }
        if (modern == 1) {
            P = (int) ((this.energyContainer.getEnergyStored() + energyContainer.getInputPerSec()) / (getMinVa() == 0 ? 1 : getMinVa()));
            ParallelNum = Math.min(P, ParallelLim);
        }
    }

    public int getMinVa() {
        if ((Math.min(this.energyContainer.getEnergyCapacity() / 32, VA[3]) * 20) == 0) return 1;
        return (int) (Math.min(this.energyContainer.getEnergyCapacity() / 32, VA[3]));

    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoilType");
        Object coilType = context.get("CoilType");
        if (type instanceof IHeatingCoilBlockStats) {
            this.blastFurnaceTemperature = ((IHeatingCoilBlockStats) type).getCoilTemperature();
            heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
        } else {
            this.blastFurnaceTemperature = BlockWireCoil.CoilType.CUPRONICKEL.getCoilTemperature();
            heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
        }
        ParallelLim = Math.min((int) Math.pow(2, heatingCoilLevel), 16);
        ParallelNum = ParallelLim;
        this.blastFurnaceTemperature += 100 * Math.max(0, GTUtility.getTierByVoltage(getEnergyContainer().getInputVoltage()) - GTValues.MV);
        LUBRICANT_STACK = Pyrotheum.getFluid(1);
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        if (!super.checkRecipe(recipe, consumeIfSuccess)) return false;
        return this.blastFurnaceTemperature >= recipe.getProperty(TemperatureProperty.getInstance(), 0);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start().aisle("XXX", "CCC", "CCC", "CCC", "XXX").aisle("XXX", "C#C", "C#C", "C#C", "XMX").aisle("XSX", "CCC", "CCC", "CCC", "XXX").where('S', selfPredicate()).where('X', states(getCasingState()).setMinGlobalLimited(9).or(autoAbilities(true, true, true, true, true, true, false))).where('M', abilities(MultiblockAbility.MUFFLER_HATCH)).where('C', heatingCoils()).where('#', air()).build();
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("EEM", "CCC", "CCC", "CCC", "XXX")
                .aisle("FXD", "C#C", "C#C", "C#C", "XHX")
                .aisle("ISO", "CCC", "CCC", "CCC", "XXX")
                .where('X', GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.ADVANCED_INVAR_CASING))
                .where('S', GTQTMetaTileEntities.BLAZING_BLAST_FURNACE, EnumFacing.SOUTH)
                .where('#', Blocks.AIR.getDefaultState())
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.LV], EnumFacing.NORTH)
                .where('I', MetaTileEntities.ITEM_IMPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
                .where('O', MetaTileEntities.ITEM_EXPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
                .where('F', MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.LV], EnumFacing.WEST)
                .where('D', MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.LV], EnumFacing.EAST)
                .where('H', MetaTileEntities.MUFFLER_HATCH[GTValues.LV], EnumFacing.UP)
                .where('M', () -> ConfigHolder.machines.enableMaintenance ? MetaTileEntities.MAINTENANCE_HATCH : GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.ADVANCED_INVAR_CASING), EnumFacing.NORTH);
        GregTechAPI.HEATING_COILS.entrySet().stream().sorted(Comparator.comparingInt(entry -> entry.getValue().getTier())).forEach(entry -> shapeInfo.add(builder.where('C', entry.getKey()).build()));
        return shapeInfo;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("什么东西烧起来了", new Object[0]));
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
            textList.add(new TextComponentTranslation("Temperature : %s", blastFurnaceTemperature));
            if (modern == 0) textList.add(new TextComponentTranslation("gtqtcore.tire1", heatingCoilLevel));
            if (modern == 1) textList.add(new TextComponentTranslation("gtqtcore.tire2", heatingCoilLevel));
            textList.add(new TextComponentTranslation("gtqtcore.parr", ParallelNum, ParallelLim));
        }
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

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.ADVANCED_INVAR_CASING;
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

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        blastFurnaceTemperature = 0;
        heatingCoilLevel = 0;
        LUBRICANT_STACK = null;
    }

    protected class BlazingBlastFurnaceWorkable extends MultiblockRecipeLogic {

        private final MetaTileEntityBlazingBlastFurnace combustionEngine;

        public BlazingBlastFurnaceWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
            this.combustionEngine = (MetaTileEntityBlazingBlastFurnace) tileEntity;
        }

        @Override
        public int getParallelLimit() {
            return ParallelNum;
        }

        protected void modifyOverclockPre(int[] values, IRecipePropertyStorage storage) {
            super.modifyOverclockPre(values, storage);
            values[0] = OverclockingLogic.applyCoilEUtDiscount(values[0], ((IHeatingCoil) this.metaTileEntity).getCurrentTemperature(), storage.getRecipePropertyValue(TemperatureProperty.getInstance(), 0));
        }

        protected int[] runOverclockingLogic(IRecipePropertyStorage propertyStorage, int recipeEUt, long maxVoltage, int duration, int amountOC) {
            return OverclockingLogic.heatingCoilOverclockingLogic(Math.abs(recipeEUt), maxVoltage, duration, amountOC, ((IHeatingCoil) this.metaTileEntity).getCurrentTemperature(), propertyStorage.getRecipePropertyValue(TemperatureProperty.getInstance(), 0));
        }

        protected void updateRecipeProgress() {
            IMultipleTankHandler inputTank = combustionEngine.getInputFluidInventory();
            if (canRecipeProgress && drawEnergy(recipeEUt, true) && LUBRICANT_STACK.isFluidStackIdentical(inputTank.drain(LUBRICANT_STACK.copy(), false))) {
                drawEnergy(recipeEUt, false);
                LUBRICANT_STACK.isFluidStackIdentical(inputTank.drain(LUBRICANT_STACK.copy(), true));
                if (++progressTime > maxProgressTime) {
                    completeRecipe();
                }
                if (this.hasNotEnoughEnergy && this.getEnergyInputPerSecond() > 19L * (long) this.recipeEUt) {
                    this.hasNotEnoughEnergy = false;
                }
            } else if (canRecipeProgress && !drawEnergy(recipeEUt, true) && this.recipeEUt > 0) {
                this.hasNotEnoughEnergy = true;
                this.decreaseProgress();
            }
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            if (ParallelNum == 0) this.maxProgressTime = maxProgress;
            else if (ParallelNum <= 16) this.maxProgressTime = (int) (maxProgress * 1.0 / ParallelNum);
            else if (ParallelNum <= 64) this.maxProgressTime = (int) (maxProgress * 16.0 / ParallelNum);
            else if (ParallelNum <= 256) this.maxProgressTime = (int) (maxProgress * 128.0 / ParallelNum);
            else this.maxProgressTime = maxProgress;

        }
    }
}