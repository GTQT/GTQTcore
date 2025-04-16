package keqing.gtqtcore.api.capability.impl;

import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.metatileentity.multiblock.IMaintenance;
import gregtech.api.util.GTTransferUtils;
import gregtech.common.ConfigHolder;
import keqing.gtqtcore.api.capability.IVoidMinerProvider;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.resourceCollection.MetaTileEntityVoidMiner;
import keqing.gtqtcore.loaders.recipes.handlers.OreRecipeHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.Collections;
import java.util.List;

import static gregtech.api.GTValues.*;
public class VoidMinerLogic {
    // Drilling Fluid amount which will be consumed, used to predicate working
    // when Drilling Fluid is satisfied some property and >= this amount.

    private static final int consumeAmountStart = 100;
    // Consume Multiplier of Drilling Fluid consumed, when used high temp liquid
    // i.e. Blazing Pyrotheum, this param will increase and when used low temp
    // coolant, i.e. Gelid Cryotheum, this parma will decrease.
    private static final float consumeMultiplier = 1.22F;
    private final MetaTileEntityVoidMiner voidMiner;
    private final int tier;

    // Void Miner MetaTileEntity.
    private final boolean hasMaintenance;
    // Energy Tier of Void Miner.
    private final int maxTemperature;
    // Check Void Miner if it has Maintenance problem.
    // Check if MetaTileEntity needs high temp liquid, i.e. Blazing Pyrotheum.
    private boolean needHighTempLiquid = true;
    // Check if MetaTileEntity consumed liquids.
    private boolean hasConsumed = false;
    private boolean isFluidOutputInventoryFull;
    private boolean isActive;
    private boolean isWorkingEnabled = true;
    private boolean wasActiveAndNeedsUpdate;
    private boolean isEnergyNotEnough;
    private boolean isOverheatStatus = false;
    private int maxProgress = 0;

    private int progressTime = 0;

    private int temperature = 0;


    private int currentDrillingFluid = consumeAmountStart;

    public VoidMinerLogic(MetaTileEntityVoidMiner voidMiner, int tier, int maxTemperature) {
        this.voidMiner = voidMiner;
        this.tier = tier;
        this.hasMaintenance = ConfigHolder.machines.enableMaintenance
                && ((IMaintenance) voidMiner).hasMaintenanceMechanics();
        this.maxTemperature = maxTemperature;
    }

    public void update() {
        // If Void Miner is disabled and its temperature > 0, then decrease temperature.
        if (!this.isWorkingEnabled) {
            if (this.temperature > 0) {
                temperature--;
            }
            return;
        }
        // Check Maintenance problems, if Void Miner has any Maintenance problem, then stop working.
        if (this.hasMaintenance && ((IMaintenance) this.voidMiner)
                .getNumMaintenanceProblems() > 1) {
            return;
        }

        // Processing Error status of Void Miner.
        if (this.isOverheatStatus || !this.consumeEnergy(true)
                || this.isFluidOutputInventoryFull) {
            if (this.temperature > 0) {
                this.temperature -= (int) (this.currentDrillingFluid / 100.0);
            }
            if (this.temperature == 0) {
                this.isOverheatStatus = false;
            }

            FluidStack drillingMudUsed = GTQTMaterials.UsedDrillingMud.getFluid(this.currentDrillingFluid);
            int drillingMudUsedAmount = this.getExportFluidHandler()
                    .fill(drillingMudUsed, false);

            if (drillingMudUsedAmount != 0
                    && drillingMudUsedAmount == this.currentDrillingFluid) {
                this.isFluidOutputInventoryFull = false;
            }

            if (this.currentDrillingFluid > consumeAmountStart) {
                this.currentDrillingFluid = (int) (this.currentDrillingFluid
                        / consumeMultiplier);
            }
            if (this.currentDrillingFluid < consumeAmountStart) {
                this.currentDrillingFluid = consumeAmountStart;
            }

            if (this.progressTime >= 2 * TICK) {
                if (ConfigHolder.machines.recipeProgressLowEnergy) {
                    this.progressTime = TICK;
                } else {
                    this.progressTime = Math.max(TICK, this.progressTime - 2 * TICK);
                }
            }
            this.isEnergyNotEnough = true;
            if (this.isActive) {
                this.setActive(false);
            }
        } else { // Processing correct status of Void Miner.
            // If temperature > maxTemperature of Void Miner, then set it to overheat status.
            if (this.temperature > this.maxTemperature) {
                this.isOverheatStatus = true;
                this.currentDrillingFluid = consumeAmountStart;
                return;
            }

            int liquidAmount = this.currentDrillingFluid / 10;

            FluidStack highTempLiquid = GTQTMaterials.Pyrotheum.getFluid(liquidAmount);
            FluidStack lowTempCoolant = GTQTMaterials.GelidCryotheum.getFluid(liquidAmount);
            FluidStack drillingMud = GTQTMaterials.DrillingMud.getFluid(this.currentDrillingFluid);
            FluidStack drillingMudUsed = GTQTMaterials.UsedDrillingMud.getFluid(this.currentDrillingFluid);

            FluidStack drainHighTempLiquid = this.getImportFluidHandler()
                    .drain(highTempLiquid, false);
            FluidStack drainLowTempCoolant = this.getImportFluidHandler()
                    .drain(lowTempCoolant, false);
            FluidStack drainDrillingMud = this.getImportFluidHandler()
                    .drain(drillingMud, false);
            int drillingMudUsedAmount = this.getExportFluidHandler()
                    .fill(drillingMudUsed, false);

            if (drainDrillingMud != null && drainDrillingMud.amount == this.currentDrillingFluid) {
                if (drillingMudUsedAmount != 0 && drillingMudUsedAmount == this.currentDrillingFluid) {
                    this.isFluidOutputInventoryFull = false;
                } else {
                    this.setActive(false);
                    this.isFluidOutputInventoryFull = true;
                }
            } else {
                this.setActive(false);
                return;
            }

            if (!this.isActive) {
                this.setActive(true);
            }
            this.consumeEnergy(false);
            this.progressTime++;
            if (this.progressTime % this.getMaxProgress() != 0) {
                return;
            }
            this.progressTime = 0;

            this.getImportFluidHandler().drain(drillingMud, true);
            this.getExportFluidHandler().fill(drillingMudUsed, true);

            if (this.needHighTempLiquid && drainHighTempLiquid != null
                    && drainHighTempLiquid.amount == liquidAmount) {
                this.getImportFluidHandler().drain(highTempLiquid, true);
                this.temperature += (int) (this.currentDrillingFluid / 100.0);
                this.currentDrillingFluid = (int) (this.currentDrillingFluid * consumeMultiplier);
                this.hasConsumed = true;
            } else if (this.temperature > 0 && drainLowTempCoolant != null
                    && drainLowTempCoolant.amount == liquidAmount) {
                this.getImportFluidHandler().drain(lowTempCoolant, true);
                this.currentDrillingFluid = (int) (this.currentDrillingFluid / consumeMultiplier);
                this.temperature -= (int) (this.currentDrillingFluid / 100.0);
            }

            if (this.temperature < 0) {
                this.temperature = 0;
            }
            if (this.currentDrillingFluid < consumeAmountStart) {
                this.currentDrillingFluid = consumeAmountStart;
            }

            if (!this.hasConsumed && this.needHighTempLiquid) {
                float divisor = (float) (((consumeMultiplier
                        - Math.floor(consumeMultiplier)) / 2.0)
                        + Math.floor(consumeMultiplier));
                this.temperature -= (int) (this.currentDrillingFluid / 200.0);
                this.currentDrillingFluid = (int) (this.currentDrillingFluid / divisor);
                if (this.temperature < 0) {
                    this.temperature = 0;
                }
                if (this.currentDrillingFluid < consumeAmountStart) {
                    this.currentDrillingFluid = consumeAmountStart;
                }
                return;
            }
            this.needHighTempLiquid = !this.needHighTempLiquid;

            // Calculate ore and ore amount to filled output inventory.
            int oreAmount = this.temperature / 1000;
            if (oreAmount == 0) {
                return;
            }

            List<ItemStack> ores = this.getOres();
            Collections.shuffle(ores);
            ores.stream()
                    .limit(10)
                    .peek(ore -> ore.setCount(oreAmount * oreAmount))
                    .forEach(ore -> GTTransferUtils.addItemsToItemHandler(this.getOutputInventory(),
                            false, Collections.singletonList(ore)));

        }
    }

    protected IMultipleTankHandler getImportFluidHandler() {
        return ((IVoidMinerProvider) this.voidMiner).getImportFluidHandler();
    }

    protected IMultipleTankHandler getExportFluidHandler() {
        return ((IVoidMinerProvider) this.voidMiner).getExportFluidHandler();
    }

    protected IItemHandlerModifiable getOutputInventory() {
        return ((IVoidMinerProvider) this.voidMiner).getOutputInventory();
    }

    protected boolean consumeEnergy(boolean simulate) {
        return ((IVoidMinerProvider) this.voidMiner).drainEnergy(simulate);
    }

    private List<ItemStack> getOres() {
        return switch (this.tier) {
            case LuV -> OreRecipeHandler.oreBasic;
            case ZPM -> OreRecipeHandler.oreAdvanced;
            case UV -> OreRecipeHandler.oreUltimate;
            default -> null;
        };
    }

    public void invalidate() {
        this.progressTime = 0;
        this.maxProgress = 0;
        this.setActive(false);
    }

    public boolean isActive() {
        return this.isActive && this.isWorkingEnabled();
    }

    public void setActive(boolean active) {
        if (this.isActive != active) {
            this.isActive = active;
            this.voidMiner.markDirty();
            if (this.voidMiner.getWorld() != null && !this.voidMiner.getWorld().isRemote) {
                this.voidMiner.writeCustomData(GregtechDataCodes.WORKABLE_ACTIVE, buf -> buf.writeBoolean(active));
            }
        }
    }

    public boolean isWorkingEnabled() {
        return this.isWorkingEnabled;
    }

    public void setWorkingEnabled(boolean workingEnabled) {
        this.isWorkingEnabled = workingEnabled;
        this.voidMiner.markDirty();
        if (this.voidMiner.getWorld() != null && !this.voidMiner.getWorld().isRemote) {
            this.voidMiner.writeCustomData(GregtechDataCodes.WORKING_ENABLED, buf -> buf.writeBoolean(workingEnabled));
        }
    }

    public boolean isWorking() {
        return this.isActive && !this.isEnergyNotEnough && this.isWorkingEnabled;
    }

    public int getProgressPercent() {
        return (int) ((this.getProgressTime() / this.getMaxProgress()) * 100);
    }

    public float getMaxProgress() {
        return this.maxProgress;
    }

    public void setMaxProgress(int i) {
        this.maxProgress = i;
    }

    public float getProgressTime() {
        return this.progressTime;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setTag("temperature", new NBTTagInt(this.temperature));
        data.setTag("currentDrillingFluid", new NBTTagDouble(this.currentDrillingFluid));
        data.setTag("isOverheatStatus", new NBTTagInt(this.isOverheatStatus ? 1 : 0));
        data.setBoolean("isActive", this.isActive);
        data.setBoolean("isWorkingEnabled", this.isWorkingEnabled);
        data.setBoolean("wasActiveAndNeedsUpdate", this.wasActiveAndNeedsUpdate);
        data.setBoolean("isFluidOutputInventoryFull", this.isFluidOutputInventoryFull);
        data.setBoolean("needHighTempLiquid", this.needHighTempLiquid);
        data.setBoolean("hasConsumed", this.hasConsumed);
        data.setInteger("progressTime", this.progressTime);
        data.setInteger("maxProgress", this.maxProgress);
        return data;
    }

    public void readFromNBT(NBTTagCompound data) {
        this.temperature = data.getInteger("temperature");
        this.currentDrillingFluid = data.getInteger("currentDrillingFluid");
        this.isOverheatStatus = data.getInteger("isOverheatStatus") != 0;
        this.isActive = data.getBoolean("isActive");
        this.isWorkingEnabled = data.getBoolean("isWorkingEnabled");
        this.wasActiveAndNeedsUpdate = data.getBoolean("wasActiveAndNeedsUpdate");
        this.needHighTempLiquid = data.getBoolean("needHighTempLiquid");
        this.isFluidOutputInventoryFull = data.getBoolean("isFluidOutputInventoryFull");
        this.hasConsumed = data.getBoolean("hasConsumed");
        this.progressTime = data.getInteger("progressTime");
        this.maxProgress = data.getInteger("maxProgress");
    }

    public void writeInitialSyncData(PacketBuffer buf) {
        buf.writeBoolean(this.isActive);
        buf.writeBoolean(this.isWorkingEnabled);
        buf.writeBoolean(this.wasActiveAndNeedsUpdate);
        buf.writeBoolean(this.isOverheatStatus);
        buf.writeBoolean(this.isFluidOutputInventoryFull);
        buf.writeBoolean(this.needHighTempLiquid);
        buf.writeBoolean(this.hasConsumed);
        buf.writeInt(this.progressTime);
        buf.writeInt(this.maxProgress);
        buf.writeInt(this.temperature);
        buf.writeInt(this.currentDrillingFluid);
    }

    public void receiveInitialSyncData(PacketBuffer buf) {
        this.setActive(buf.readBoolean());
        this.setWorkingEnabled(buf.readBoolean());
        this.setWasActiveAndNeedsUpdate(buf.readBoolean());
        this.isOverheatStatus = buf.readBoolean();
        this.isFluidOutputInventoryFull = buf.readBoolean();
        this.needHighTempLiquid = buf.readBoolean();
        this.hasConsumed = buf.readBoolean();
        this.progressTime = buf.readInt();
        this.maxProgress = buf.readInt();
        this.temperature = buf.readInt();
        this.currentDrillingFluid = buf.readInt();
    }

    public void setWasActiveAndNeedsUpdate(boolean b) {
        this.wasActiveAndNeedsUpdate = b;
    }

    public void receiveCustomData(int dataId, PacketBuffer buf) {
        if (dataId == GregtechDataCodes.IS_WORKING) {
            this.setActive(buf.readBoolean());
            this.voidMiner.scheduleRenderUpdate();
        }
    }

    public boolean wasActiveAndNeedsUpdate() {
        return this.wasActiveAndNeedsUpdate;
    }

    public boolean isOverheatStatus() {
        return this.isOverheatStatus;
    }

    public boolean isFluidOutputInventoryFull() {
        return this.isFluidOutputInventoryFull;
    }

    public float getConsumeMultiplier() {
        return consumeMultiplier;
    }

    public long getTemperature() {
        return this.temperature;
    }

    public long getCurrentDrillingFluid() {
        return this.currentDrillingFluid;
    }
}
