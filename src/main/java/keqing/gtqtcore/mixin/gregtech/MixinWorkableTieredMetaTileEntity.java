package keqing.gtqtcore.mixin.gregtech;

import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.capability.impl.RecipeLogicEnergy;
import gregtech.api.metatileentity.*;
import gregtech.api.metatileentity.multiblock.ICleanroomProvider;
import gregtech.api.metatileentity.multiblock.ICleanroomReceiver;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.ICubeRenderer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Function;

import static gregtech.api.GTValues.VA;

@Mixin(WorkableTieredMetaTileEntity.class)
public abstract class MixinWorkableTieredMetaTileEntity extends TieredMetaTileEntity implements IDataInfoProvider, ICleanroomReceiver {
    protected final AbstractRecipeLogic workable;
    protected final RecipeMap<?> recipeMap;
    protected final ICubeRenderer renderer;
    private final Function<Integer, Integer> tankScalingFunction;
    public final boolean handlesRecipeOutputs;
    private ICleanroomProvider cleanroom;

    public MixinWorkableTieredMetaTileEntity(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, ICubeRenderer renderer, int tier, Function<Integer, Integer> tankScalingFunction, boolean handlesRecipeOutputs) {
        super(metaTileEntityId, tier);
        this.renderer = renderer;
        this.handlesRecipeOutputs = handlesRecipeOutputs;
        this.workable = this.createWorkable(recipeMap);
        this.recipeMap = recipeMap;
        this.tankScalingFunction = tankScalingFunction;
        this.initializeInventory();
        this.reinitializeEnergyContainer();
    }

    protected RecipeLogicEnergy createWorkable(RecipeMap<?> recipeMap) {
        return new RecipeLogicEnergy(this, recipeMap, () -> this.energyContainer);
    }

    protected long gettierVoltage(long tierVoltage)
    {
        if(tierVoltage==32)return 32;
        if(tierVoltage==128)return 128;
        if(tierVoltage==512)return 480;
        if(tierVoltage==2048)return 1792;
        if(tierVoltage==8192)return 6656;
        if(tierVoltage==32768)return 24576;
        if(tierVoltage==131072)return 98304;
        if(tierVoltage==524288)return 393216;
        if(tierVoltage==2097152)return 1572864;
        return tierVoltage;
    }

    protected void reinitializeEnergyContainer() {
        long tierVoltage = GTValues.V[this.getTier()];
        if (this.isEnergyEmitter()) {
            this.energyContainer = EnergyContainerHandler.emitterContainer(this, tierVoltage * 64L, gettierVoltage(tierVoltage), this.getMaxInputOutputAmperage());
        } else {
            this.energyContainer = new EnergyContainerHandler(this, tierVoltage * 64L, tierVoltage, 2L, 0L, 0L) {
                public long getInputAmperage() {
                    return this.getEnergyCapacity() / 2L > this.getEnergyStored() && isActive() ? 2L : 1L;
                }
            };
        }

    }

    public boolean isActive()
    {
        return this.workable.isActive() && this.workable.isWorkingEnabled();
    }

}

