package keqing.gtqtcore.api.capability.impl;


import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.recipeproperties.ComputationProperty;
import gregtech.api.recipes.recipeproperties.TotalComputationProperty;
import net.minecraft.nbt.NBTTagCompound;

public class ComputationRecipeLogic extends MultiblockRecipeLogic {
    public final ComputationType type;
    public boolean isDurationTotalCWU;
    public int recipeCWUt;
    public boolean hasNotEnoughComputation;
    public int currentDrawnCWUt;

    public ComputationRecipeLogic(RecipeMapMultiblockController metaTileEntity, ComputationType type) {
        super(metaTileEntity);
        this.type = type;
        if (!(metaTileEntity instanceof IOpticalComputationReceiver)) {
            throw new IllegalArgumentException("MetaTileEntity must be instanceof IOpticalComputationReceiver");
        }
    }

    public  IOpticalComputationProvider getComputationProvider() {
        IOpticalComputationReceiver controller = (IOpticalComputationReceiver)this.metaTileEntity;
        return controller.getComputationProvider();
    }

    public boolean checkRecipe( Recipe recipe) {
        if (!super.checkRecipe(recipe)) {
            return false;
        } else if (!recipe.hasProperty(ComputationProperty.getInstance())) {
            return true;
        } else {
            IOpticalComputationProvider provider = this.getComputationProvider();
            int recipeCWUt = (Integer)recipe.getProperty(ComputationProperty.getInstance(), 0);
            return provider.requestCWUt(recipeCWUt, true) >= recipeCWUt;
        }
    }

    protected void setupRecipe(Recipe recipe) {
        super.setupRecipe(recipe);
        this.recipeCWUt = (Integer)recipe.getProperty(ComputationProperty.getInstance(), 0);
        this.isDurationTotalCWU = recipe.hasProperty(TotalComputationProperty.getInstance());
    }

    protected void updateRecipeProgress() {
        if (this.recipeCWUt == 0) {
            super.updateRecipeProgress();
        } else {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)) {
                this.drawEnergy(this.recipeEUt, false);
                IOpticalComputationProvider provider = this.getComputationProvider();
                int availableCWUt = provider.requestCWUt(Integer.MAX_VALUE, true);
                if (availableCWUt >= this.recipeCWUt) {
                    this.hasNotEnoughComputation = false;
                    if (this.isDurationTotalCWU) {
                        this.currentDrawnCWUt = provider.requestCWUt(availableCWUt, false);
                        this.progressTime += this.currentDrawnCWUt;
                    } else {
                        provider.requestCWUt(this.recipeCWUt, false);
                        ++this.progressTime;
                    }

                    if (this.progressTime > this.maxProgressTime) {
                        this.completeRecipe();
                    }
                } else {
                    this.currentDrawnCWUt = 0;
                    this.hasNotEnoughComputation = true;
                    if (this.type == ComputationType.STEADY) {
                        this.decreaseProgress();
                    }
                }

                if (this.hasNotEnoughEnergy && this.getEnergyInputPerSecond() > 19L * (long)this.recipeEUt) {
                    this.hasNotEnoughEnergy = false;
                }
            } else if (this.recipeEUt > 0) {
                this.hasNotEnoughEnergy = true;
                this.decreaseProgress();
            }

        }
    }

    protected void completeRecipe() {
        super.completeRecipe();
        this.recipeCWUt = 0;
        this.isDurationTotalCWU = false;
        this.hasNotEnoughComputation = false;
        this.currentDrawnCWUt = 0;
    }

    public int getRecipeCWUt() {
        return this.recipeCWUt;
    }

    public int getCurrentDrawnCWUt() {
        return this.isDurationTotalCWU ? this.currentDrawnCWUt : this.recipeCWUt;
    }

    public boolean isHasNotEnoughComputation() {
        return this.hasNotEnoughComputation;
    }

    public  NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        if (this.progressTime > 0) {
            compound.setInteger("RecipeCWUt", this.recipeCWUt);
            compound.setBoolean("IsDurationTotalCWU", this.isDurationTotalCWU);
        }

        return compound;
    }

    public void deserializeNBT( NBTTagCompound compound) {
        super.deserializeNBT(compound);
        if (this.progressTime > 0) {
            this.recipeCWUt = compound.getInteger("RecipeCWUt");
            this.isDurationTotalCWU = compound.getBoolean("IsDurationTotalCWU");
        }

    }

    public boolean shouldShowDuration() {
        return !this.isDurationTotalCWU;
    }

    public static enum ComputationType {
        STEADY,
        SPORADIC;

        private ComputationType() {
        }
    }
}
