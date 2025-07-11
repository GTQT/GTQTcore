package keqing.gtqtcore.api.capability.impl;

import gregtech.api.capability.impl.RecipeLogicSteam;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.common.ConfigHolder;
import keqing.gtqtcore.api.GCYSValues;
import keqing.gtqtcore.api.capability.IPressureContainer;
import keqing.gtqtcore.api.capability.IPressureMachine;
import keqing.gtqtcore.api.recipes.properties.PressureProperty;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nonnull;

public class PressureSteamRecipeLogic extends RecipeLogicSteam {

    private double recipePressure = GCYSValues.EARTH_PRESSURE;

    public PressureSteamRecipeLogic(MetaTileEntity tileEntity, RecipeMap<?> recipeMap, boolean isHighPressure, IFluidTank steamFluidTank, double conversionRate) {
        super(tileEntity, recipeMap, isHighPressure, steamFluidTank, conversionRate);
    }

    @Override
    protected void updateRecipeProgress() {
        // do not simulate pressure so it keeps growing towards atmospheric
        if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true) && isPressureSuit()) {
            drawEnergy(recipeEUt, false);
            if (++this.progressTime > this.maxProgressTime) {
                if (drawPressure(this.recipePressure, true)) {
                    drawPressure(this.recipePressure, false);
                    this.completeRecipe();
                }
            }

            if (this.hasNotEnoughEnergy) {
                if (this.getEnergyInputPerSecond() > 19L * this.recipeEUt) {
                    this.hasNotEnoughEnergy = false;
                }
            }
        } else if (this.recipeEUt > 0 || this.recipePressure != GCYSValues.EARTH_PRESSURE) {
            this.hasNotEnoughEnergy = true;
            if (this.progressTime >= 2) {
                if (ConfigHolder.machines.recipeProgressLowEnergy) {
                    this.progressTime = 1;
                } else {
                    this.progressTime = Math.max(1, this.progressTime - 2);
                }
            }
        }
    }

    protected boolean drawPressure(double pressure, boolean simulate) {
        IPressureContainer container = this.getPressureContainer();
        final double containerPressure = container.getPressure();
        double pressureToChange;

        // pressure changes
        if (pressure != GCYSValues.EARTH_PRESSURE) pressureToChange = containerPressure * 0.2;
        else return true;

        if (pressure > GCYSValues.EARTH_PRESSURE) pressureToChange = -pressureToChange;

        final double newPressure = containerPressure + pressureToChange;
        // pressure must be within +/- 1 exponent of the target
        if (newPressure < pressure / 10 || newPressure > pressure * 10) {
            return false;
        }

        // P * V = n
        return container.changeParticles(pressureToChange * container.getVolume(), simulate);
    }

    protected IPressureContainer getPressureContainer() {
        return ((IPressureMachine) this.metaTileEntity).getPressureContainer();
    }

    @Override
    protected void setupRecipe(Recipe recipe) {
        super.setupRecipe(recipe);
        if (recipe.hasProperty(PressureProperty.getInstance())) {
            this.recipePressure = recipe.getProperty(PressureProperty.getInstance(), GCYSValues.EARTH_PRESSURE);
        }
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe) {
        if (recipe.hasProperty(PressureProperty.getInstance())) {
            double pressure = recipe.getProperty(PressureProperty.getInstance(), GCYSValues.EARTH_PRESSURE);

            IPressureContainer container = this.getPressureContainer();
            final double containerPressure = container.getPressure();
            if (pressure > GCYSValues.EARTH_PRESSURE * 0.95) {
                if (containerPressure >= pressure) return super.checkRecipe(recipe);
            }
            if (pressure < GCYSValues.EARTH_PRESSURE * 1.05) {
                if (containerPressure <= pressure) return super.checkRecipe(recipe);
            }
        }
        return false;
    }

    public boolean isPressureSuit() {
        IPressureContainer container = this.getPressureContainer();
        final double containerPressure = container.getPressure();
        if (recipePressure > GCYSValues.EARTH_PRESSURE) return containerPressure > recipePressure * 0.95;
        if (recipePressure < GCYSValues.EARTH_PRESSURE) return containerPressure < recipePressure * 1.05;
        return true;
    }

    public void setMaxProgress(int maxProgress) {
        IPressureContainer container = this.getPressureContainer();
        final double containerPressure = container.getPressure();
        int accelerate = 0;
        if (recipePressure > GCYSValues.EARTH_PRESSURE) {
            if (containerPressure > recipePressure * 10) {
                accelerate = (int) Math.log10(containerPressure / recipePressure);
            }
        }
        if (recipePressure < GCYSValues.EARTH_PRESSURE) {
            if (containerPressure < recipePressure * 10) {
                accelerate = (int) Math.log10(recipePressure / containerPressure);
            }
        }
        this.maxProgressTime = maxProgress * (100 - accelerate * 5) / 100;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        if (this.progressTime > 0) {
            compound.setDouble("pressure", this.recipePressure);
        }
        return compound;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        super.deserializeNBT(compound);
        if (this.progressTime > 0) {
            this.recipePressure = compound.getDouble("pressure");
        } else {
            this.recipePressure = GCYSValues.EARTH_PRESSURE;
        }
    }
}