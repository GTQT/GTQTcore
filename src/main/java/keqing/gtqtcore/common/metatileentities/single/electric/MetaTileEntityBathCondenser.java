package keqing.gtqtcore.common.metatileentities.single.electric;

import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.capability.impl.RecipeLogicEnergy;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import keqing.gtqtcore.api.metaileentity.multiblock.ICryogenicProvider;
import keqing.gtqtcore.api.metaileentity.multiblock.ICryogenicReceiver;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.CryogenicEnvironmentProperty;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

import static gregtech.api.recipes.logic.OverclockingLogic.standardOverclockingLogic;

public class MetaTileEntityBathCondenser extends SimpleMachineMetaTileEntity implements ICryogenicReceiver {

    private  ICryogenicProvider provider;

    public MetaTileEntityBathCondenser(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.BATH_CONDENSER, GTQTTextures.DRYER_OVERLAY, 1, true);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityBathCondenser(metaTileEntityId);
    }

    @Override
    protected RecipeLogicEnergy createWorkable(RecipeMap<?> recipeMap) {
        return new BathCondenserRecipeLogic(this, recipeMap, () -> energyContainer);
    }

    @Override
    protected void reinitializeEnergyContainer() {
        this.energyContainer = new EnergyContainerHandler(this, 0, 0, 0, 0, 0) {
            @Override
            public boolean isOneProbeHidden() {
                return true;
            }
        };
    }

    @Override
    public  ICryogenicProvider getCryogenicProvider() {
        return provider;
    }

    @Override
    public void setCryogenicProvider( ICryogenicProvider cryogenicProvider) {
        this.provider = cryogenicProvider;
        if (this.provider == null && this.workable instanceof BathCondenserRecipeLogic logic) {
            logic.invalidate();
        }
    }

    @Override
    public void addInformation(ItemStack stack,  World player, List<String> tooltip, boolean advanced) {
        if (workable.getRecipeMap() != null && workable.getRecipeMap().getMaxFluidInputs() != 0) {
            tooltip.add(I18n.format("gregtech.universal.tooltip.fluid_storage_capacity",
                    this.getTankScalingFunction().apply(getTier())));
        }
    }

    // TODO make this extend PrimitiveRecipeLogic in GT 2.9
    private static class BathCondenserRecipeLogic extends RecipeLogicEnergy {

        public BathCondenserRecipeLogic(MetaTileEntity tileEntity, RecipeMap<?> recipeMap, Supplier<IEnergyContainer> energyContainer) {
            super(tileEntity, recipeMap, energyContainer);
        }


        @Override
        public MetaTileEntityBathCondenser getMetaTileEntity() {
            return (MetaTileEntityBathCondenser) super.getMetaTileEntity();
        }

        @Override
        public boolean checkRecipe( Recipe recipe) {
            if (super.checkRecipe(recipe)) {
                Boolean value = recipe.getProperty(CryogenicEnvironmentProperty.getInstance(), null);
                return value == null || !value || (getMetaTileEntity().getCryogenicProvider() != null && getMetaTileEntity().getCryogenicProvider().isStructureFormed());
            }

            return false;
        }

        @Override
        protected boolean canProgressRecipe() {
            if (super.canProgressRecipe()) {
                if (previousRecipe == null) return true;
                Boolean value = previousRecipe.getProperty(CryogenicEnvironmentProperty.getInstance(), null);
                return value == null || !value || (getMetaTileEntity().getCryogenicProvider() != null && getMetaTileEntity().getCryogenicProvider().isStructureFormed());
            }
            return false;
        }

        @Override
        protected long getEnergyInputPerSecond() {
            return Integer.MAX_VALUE;
        }

        @Override
        protected long getEnergyStored() {
            return Integer.MAX_VALUE;
        }

        @Override
        protected long getEnergyCapacity() {
            return Integer.MAX_VALUE;
        }

        @Override
        protected boolean drawEnergy(int recipeEUt, boolean simulate) {
            return true; // spoof energy being drawn
        }

        @Override
        public long getMaxVoltage() {
            return GTValues.LV;
        }

        @Override
        protected int  [] runOverclockingLogic( IRecipePropertyStorage propertyStorage, int recipeEUt,
                                                       long maxVoltage, int recipeDuration, int amountOC) {
            return standardOverclockingLogic(
                    1,
                    getMaxVoltage(),
                    recipeDuration,
                    amountOC,
                    getOverclockingDurationDivisor(),
                    getOverclockingVoltageMultiplier()

            );
        }

        @Override
        public long getMaximumOverclockVoltage() {
            return GTValues.V[GTValues.LV];
        }

        /**
         * Used to reset cached values in the Recipe Logic on structure deform
         */
        public void invalidate() {
            previousRecipe = null;
            progressTime = 0;
            maxProgressTime = 0;
            recipeEUt = 0;
            fluidOutputs = null;
            itemOutputs = null;
            setActive(false); // this marks dirty for us
        }
    }
}