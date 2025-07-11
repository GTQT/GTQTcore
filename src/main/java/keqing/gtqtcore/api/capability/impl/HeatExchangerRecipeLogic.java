package keqing.gtqtcore.api.capability.impl;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import keqing.gtqtcore.api.capability.GTQTDataCode;
import keqing.gtqtcore.api.capability.IHeatExchanger;
import keqing.gtqtcore.api.recipes.properties.FlowRateProperty;
import keqing.gtqtcore.api.recipes.properties.MaxRateProperty;
import keqing.gtsteam.api.capability.impl.NoEnergyMultiblockRecipeLogic;
import keqing.gtsteam.api.metatileentity.multiblock.NoEnergyMultiblockController;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static keqing.gtqtcore.api.utils.GTQTMathUtil.clamp;

@SuppressWarnings("all")
public class HeatExchangerRecipeLogic extends NoEnergyMultiblockRecipeLogic {

    private int maxHeat;
    private int currentHeat;
    private boolean isSuperheat;
    private int rate;

    public HeatExchangerRecipeLogic(NoEnergyMultiblockController tileEntity) {
        super(tileEntity, tileEntity.recipeMap);
        this.maxHeat = ((IHeatExchanger) tileEntity).getHeatTime() * 20;
    }

    public void setHeatTime(int maxHeat) {
        this.maxHeat = maxHeat * 20;
    }

    @Override
    public void update() {
        if ((!isActive() || !canProgressRecipe() || !isWorkingEnabled()) && currentHeat > 0) {
            setHeat(currentHeat - 1);
            setRate(0);
            setSuperheat(false);
        }
        super.updateWorkable();
    }

    @Override
    protected void updateRecipeProgress() {
        super.updateRecipeProgress();
        if (!canRecipeProgress)
            return;

        if (currentHeat < maxHeat) {
            setHeat(currentHeat + 1);
        }
    }

    @Override
    protected boolean checkPreviousRecipe() {
        if (this.previousRecipe == null) {
            return false;
        } else {
            List<FluidStack> fluidStackList = new ArrayList<>(GTUtility.fluidHandlerToList(getInputTank()));
            fluidStackList.add(Materials.DistilledWater.getFluid(Integer.MAX_VALUE));
            return (long) this.previousRecipe.getEUt() <= this.getMaxVoltage() && this.previousRecipe.matches(false, Collections.emptyList(), fluidStackList);
        }
    }

    @Override
    protected void trySearchNewRecipeCombined() {
        long maxVoltage = getMaxVoltage();
        List<FluidStack> fluidStackList = new ArrayList<>(GTUtility.fluidHandlerToList(getInputTank()));
        fluidStackList.add(Materials.DistilledWater.getFluid(Integer.MAX_VALUE));
        Recipe currentRecipe;

        // see if the last recipe we used still works
        if (checkPreviousRecipe()) {
            currentRecipe = this.previousRecipe;
            // If there is no active recipe, then we need to find one.
        } else {
            currentRecipe = getRecipeMap().findRecipe(Integer.MAX_VALUE, Collections.emptyList(), fluidStackList);
        }
        // If a recipe was found, then inputs were valid. Cache found recipe.
        if (currentRecipe != null) {
            this.previousRecipe = currentRecipe;
        }
        this.invalidInputsForRecipes = (currentRecipe == null);

        // proceed if we have a usable recipe.
        if (currentRecipe != null && checkRecipe(currentRecipe)) {
            prepareRecipe(currentRecipe);
        }
    }

    @Override
    public boolean prepareRecipe(Recipe recipe) {
        FluidStack recipeFluidStack = recipe.getFluidInputs().get(1).getInputFluidStack();
        FluidStack inputFluidStack = getInputFluidStack(recipeFluidStack);
        IHeatExchanger mte = (IHeatExchanger) metaTileEntity;
        int rMaxInletFlow = recipe.getProperty(MaxRateProperty.getInstance(), 0);

        int tMaxInletFlow = (int) Math.ceil((0.4 + 0.006 * mte.getThrottle()) * ((double) currentHeat / maxHeat) * mte.getParallel() * rMaxInletFlow);
        int threshold = (int) Math.ceil(0.01 * recipe.getProperty(FlowRateProperty.getInstance(), 0) * mte.getThrottle());

        if (inputFluidStack != null) {
            int amount = clamp(inputFluidStack.amount, 0, tMaxInletFlow);
            if (amount >= threshold) {
                setSuperheat(true);
                return setRecipe(recipe, amount, 1);
            } else {
                setSuperheat(false);
                return setRecipe(recipe, amount, 0);
            }
        }
        return false;
    }

    private boolean setRecipe(Recipe recipe, int amount, int i) {
        Recipe tRecipe =  getRecipeMap().recipeBuilder().append(recipe, 1, false)
                .clearFluidOutputs()
                .fluidOutputs(recipe.getFluidOutputs().get(i))
                .fluidOutputs(recipe.getFluidOutputs().get(2))
                .build().getResult();
        RecipeBuilder<?> recipeBuilder = getRecipeMap().recipeBuilder();
        recipeBuilder.append(tRecipe, amount, false);
        applyParallelBonus(recipeBuilder);
        recipe = recipeBuilder.build().getResult();
        if (recipe != null) {
            recipe = setupAndConsumeRecipeInputs(recipe, getInputInventory());
            if (recipe != null) {
                setupRecipe(recipe);
                setRate(amount);
                return true;
            }
            return false;
        }
        else {
            metaTileEntity.doExplosion(6);
            return false;
        }
    }

    public FluidStack getInputFluidStack(FluidStack fluidStack) {
        return getInputTank().drain(new FluidStack(fluidStack.getFluid(), Integer.MAX_VALUE), false);
    }

    private void setHeat(int heat) {
        if (heat != this.currentHeat && !metaTileEntity.getWorld().isRemote) {
            writeCustomData(GTQTDataCode.ChannelHeatExchangerHeat, b -> b.writeVarInt(heat));
        }
        this.currentHeat = heat;
    }

    public double getHeatEfficiency() {
        return (double) currentHeat / maxHeat;
    }

    public int getRate() {
        return rate;
    }

    private void setRate(int rate) {
        if (this.rate != rate && !metaTileEntity.getWorld().isRemote) {
            writeCustomData(GTQTDataCode.ChannelHeatExchangerRate, b -> b.writeVarInt(rate));
        }
        this.rate = rate;
    }

    public boolean isSuperheat() {
        return isSuperheat;
    }

    private void setSuperheat(boolean isSuperheat) {
        if (this.isSuperheat != isSuperheat && !metaTileEntity.getWorld().isRemote) {
            writeCustomData(GTQTDataCode.ChannelHeatExchangerSuperheat, b -> b.writeBoolean(isSuperheat));
        }
        this.isSuperheat = isSuperheat;
    }

    @NotNull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        compound.setInteger("Heat", currentHeat);
        compound.setInteger("Rate", rate);
        compound.setBoolean("isSuperheat", isSuperheat);
        return compound;
    }

    @Override
    public void deserializeNBT(@NotNull NBTTagCompound compound) {
        super.deserializeNBT(compound);
        currentHeat = compound.getInteger("Heat");
        rate = compound.getInteger("Rate");
        isSuperheat = compound.getBoolean("isSuperheat");
    }

    public void writeInitialData(@Nonnull PacketBuffer buf) {
        buf.writeVarInt(currentHeat);
        buf.writeVarInt(rate);
        buf.writeBoolean(isSuperheat);
    }

    public void receiveInitialData(@Nonnull PacketBuffer buf) {
        this.currentHeat = buf.readVarInt();
        this.rate = buf.readVarInt();
        this.isSuperheat = buf.readBoolean();
    }

    @Override
    public void receiveCustomData(int dataId, @NotNull PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GTQTDataCode.ChannelHeatExchangerHeat) {
            this.currentHeat = buf.readVarInt();
        }
        if (dataId == GTQTDataCode.ChannelHeatExchangerSuperheat) {
            this.isSuperheat = buf.readBoolean();
        }
        if (dataId == GTQTDataCode.ChannelHeatExchangerRate) {
            this.rate = buf.readVarInt();
        }
    }
}
