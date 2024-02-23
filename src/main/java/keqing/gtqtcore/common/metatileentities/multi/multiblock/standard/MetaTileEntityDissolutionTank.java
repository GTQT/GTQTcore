package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregicality.science.common.block.GCYSMetaBlocks;
import gregicality.science.common.block.blocks.BlockTransparentCasing;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.impl.ComputationRecipeLogic;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTRecipeMapMultiblockOverwrite;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.Lubricant;

public class MetaTileEntityDissolutionTank extends GTQTRecipeMapMultiblockOverwrite {
    public MetaTileEntityDissolutionTank(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.DISSOLUTION_TANK_RECIPES);
        this.recipeMapWorkable = new DissolutionTankWorkableHandler(this);
    }
    private int glass_tier;
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityDissolutionTank(metaTileEntityId);
    }
     int ParallelNum;
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("modern", modern);
        return super.writeToNBT(data);
    }
    @Override
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
            P = (int) ((this.energyContainer.getEnergyStored() + energyContainer.getInputPerSec()) / getMinVa());
            ParallelNum = Math.min(P, ParallelLim);
        }
    }
    public int getMinVa()
    {
        if((Math.min(this.energyContainer.getEnergyCapacity()/32,VA[glass_tier])*20)==0)return 1;
        return (int)(Math.min(this.energyContainer.getEnergyCapacity()/32,VA[glass_tier]));

    }
    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        if (super.checkRecipe(recipe, consumeIfSuccess)) {
            List<IItemHandlerModifiable> itemInputInventory = getAbilities(MultiblockAbility.IMPORT_ITEMS);
            List<IFluidTank> fluidInputInventory = getAbilities(MultiblockAbility.IMPORT_FLUIDS);
            double ratio = 1;
            int count = 0;
            for (IFluidTank input : fluidInputInventory) {
                for (GTRecipeInput recipe_input : recipe.getFluidInputs()) {
                    if (recipe_input.acceptsFluid(input.getFluid())) {
                        if (count == 0) {
                            ratio = (double) recipe_input.getAmount() / input.getFluidAmount();
                            count = 1;
                        } else {
                            if (!(ratio == (double) recipe_input.getAmount() / input.getFluidAmount())) {
                                return false;
                            }
                        }
                    }
                }
            }
            for (IItemHandlerModifiable input : itemInputInventory) {
                for (GTRecipeInput recipe_input : recipe.getInputs()) {
                    if (recipe_input.isNonConsumable()) {
                        continue;
                    }
                    for (int index = 0; index < input.getSlots(); index++) {
                        // Skip this slot if it is empty.
                        final ItemStack currentInputItem = input.getStackInSlot(index);
                        if (currentInputItem.isEmpty())
                            continue;
                        if (recipe_input.acceptsStack(input.getStackInSlot(index))) {
                            if (count == 1) {
                                if (!(ratio == (double) recipe_input.getAmount() / input.getStackInSlot(index).getCount())) {
                                    return false;
                                }
                            } else {
                                ratio = (double) recipe_input.getAmount() / input.getStackInSlot(index).getCount();
                                count = 1;
                            }
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("M   M", "MMMMM", "MGGGM", "MGGGM", " MMM ")
                .aisle("     ", "MNNNM", "G###G", "G###G", "MMMMM")
                .aisle("     ", "MNNNM", "G###G", "G###G", "MMMMM")
                .aisle("     ", "MNNNM", "G###G", "G###G", "MMMMM")
                .aisle("M   M", "MMSMM", "MGGGM", "MGGGM", " MMM ")
                .where('S', selfPredicate())
                .where('M', states(getCasingAState()).or(autoAbilities()))
                .where('G', TiredTraceabilityPredicate.CP_LGLASS)
                .where('N', states(getCasingBState()))
                .where('#', air())
                .where(' ', any())
                .build();
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object glass_tier = context.get("LGLTiredStats");

        this.glass_tier = GTQTUtil.getOrDefault(() -> glass_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)glass_tier).getIntTier(),
                0);
        ParallelLim=(int)Math.pow(2, this.glass_tier);
        ParallelNum=ParallelLim;
    }
    private static IBlockState getCasingAState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);
    }

    private static IBlockState getCasingBState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.CLEAN_STAINLESS_STEEL_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.POWER_SUBSTATION_OVERLAY;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("epimorphism.machine.dissolution_tank.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.cracker.gtqtupdate.1"));
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            if(modern==0) textList.add(new TextComponentTranslation("gtqtcore.tire1",glass_tier));
            if(modern==1) textList.add(new TextComponentTranslation("gtqtcore.tire2",glass_tier));
            textList.add(new TextComponentTranslation("gtqtcore.parr",ParallelNum,ParallelLim));
        }
        super.addDisplayText(textList);
    }

    protected class DissolutionTankWorkableHandler extends MultiblockRecipeLogic {
        public DissolutionTankWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }


        public void setMaxProgress(int maxProgress) {
                this.maxProgressTime = maxProgress*(100-glass_tier)/100;
            }

        public long getMaxVoltage() {
            return VA[glass_tier];
        }

        @Override
        public int getParallelLimit() {
            return ParallelNum;
        }
    }
}
