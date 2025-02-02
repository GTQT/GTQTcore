package keqing.gtqtcore.api.capability.impl;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.worldgen.bedrockFluids.BedrockFluidVeinHandler;
import gregtech.common.ConfigHolder;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityFracturing;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.unification.material.Materials.DrillingFluid;

public class FracturingLogic  {
    public static final int MAX_PROGRESS = 20;
    private int progressTime = 0;
    private final MetaTileEntityFracturing metaTileEntity;
    private boolean isActive;
    private boolean isWorkingEnabled = true;
    private boolean wasActiveAndNeedsUpdate;
    private boolean isDone = false;
    protected boolean isInventoryFull;
    private boolean hasNotEnoughEnergy;
    private Fluid veinFluid;


    public FracturingLogic(MetaTileEntityFracturing metaTileEntity) {
        this.metaTileEntity = metaTileEntity;
        this.veinFluid = null;
    }


    public void performDrilling() {
        IMultipleTankHandler inputTank = metaTileEntity.getInputFluidInventory();
        FluidStack drillingFluidFluid = DrillingFluid.getFluid(metaTileEntity.getThresholdPercentage());
        if (drillingFluidFluid.isFluidStackIdentical(inputTank.drain(drillingFluidFluid, false))) {
            if (!this.metaTileEntity.getWorld().isRemote) {
                if (this.veinFluid != null || this.acquireNewFluid()) {
                    inputTank.drain(drillingFluidFluid, true);
                    if (this.isWorkingEnabled) {
                        if (this.checkCanDrain()) {
                            if (!this.isInventoryFull) {
                                this.consumeEnergy(false);
                                if (!this.isActive) {
                                    this.setActive(true);
                                }
                                ++this.progressTime;
                                if (this.progressTime % 20 == 0) {
                                    this.progressTime = 0;
                                    int amount = this.getFluidToProduce();
                                    if (this.metaTileEntity.fillTanks(new FluidStack(this.veinFluid, amount+(metaTileEntity.getThresholdPercentage()-1)*1000), true)) {
                                        this.metaTileEntity.fillTanks(new FluidStack(this.veinFluid, amount+(metaTileEntity.getThresholdPercentage()-1)*1000), false);
                                        this.depleteVein();
                                    } else {
                                        this.isInventoryFull = true;
                                        this.setActive(false);
                                        this.setWasActiveAndNeedsUpdate(true);
                                    }

                                }
                            } else {
                                if (this.isActive) {
                                    this.setActive(false);
                                }

                            }
                        }
                    }
                }
            }
        }
    }
    private int overclockAmount = 0;
    public int getOverclockAmount() {
        return this.overclockAmount;
    }
    public void setOverclockAmount(int amount) {
        this.overclockAmount = amount;
    }
    protected boolean consumeEnergy(boolean simulate) {
        return this.metaTileEntity.drainEnergy(simulate);
    }

    private boolean acquireNewFluid() {
        this.veinFluid = BedrockFluidVeinHandler.getFluidInChunk(this.metaTileEntity.getWorld(), this.getChunkX(), this.getChunkZ());
        return this.veinFluid != null;
    }

    public Fluid getDrilledFluid() {
        return this.veinFluid;
    }

    protected void depleteVein() {
        int chance = this.metaTileEntity.getDepletionChance();
        if (chance == 1 || GTValues.RNG.nextInt(chance) == 0) {
            BedrockFluidVeinHandler.depleteVein(this.metaTileEntity.getWorld(), this.getChunkX(), this.getChunkZ(), 0, false);
        }

    }

    public int getFluidToProduce() {
        int depletedYield = BedrockFluidVeinHandler.getDepletedFluidYield(this.metaTileEntity.getWorld(), this.getChunkX(), this.getChunkZ());
        int regularYield = BedrockFluidVeinHandler.getFluidYield(this.metaTileEntity.getWorld(), this.getChunkX(), this.getChunkZ());
        int remainingOperations = BedrockFluidVeinHandler.getOperationsRemaining(this.metaTileEntity.getWorld(), this.getChunkX(), this.getChunkZ());
        int produced = Math.max(depletedYield, regularYield * remainingOperations / 100000);
        produced *= this.metaTileEntity.getRigMultiplier();
        if (this.isOverclocked()) {
            produced = produced * 3 / 2;
        }

        return produced;
    }

    protected boolean checkCanDrain() {
        if (!this.consumeEnergy(true)) {
            if (this.progressTime >= 2) {
                if (ConfigHolder.machines.recipeProgressLowEnergy) {
                    this.progressTime = 1;
                } else {
                    this.progressTime = Math.max(1, this.progressTime - 2);
                }

                this.hasNotEnoughEnergy = true;
            }

            return false;
        } else {
            if (this.hasNotEnoughEnergy && this.metaTileEntity.getEnergyInputPerSecond() > 19L * (long)GTValues.VA[this.metaTileEntity.getEnergyTier()]) {
                this.hasNotEnoughEnergy = false;
            }

            if (this.metaTileEntity.fillTanks(new FluidStack(this.veinFluid, this.getFluidToProduce()), true)) {
                this.isInventoryFull = false;
                return true;
            } else {
                this.isInventoryFull = true;
                if (this.isActive()) {
                    this.setActive(false);
                    this.setWasActiveAndNeedsUpdate(true);
                }

                return false;
            }
        }
    }

    public int getChunkX() {
        return Math.floorDiv(this.metaTileEntity.getPos().getX(), 16);
    }

    public int getChunkZ() {
        return Math.floorDiv(this.metaTileEntity.getPos().getZ(), 16);
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean active) {
        if (this.isActive != active) {
            this.isActive = active;
            this.metaTileEntity.markDirty();
            if (this.metaTileEntity.getWorld() != null && !this.metaTileEntity.getWorld().isRemote) {
                this.metaTileEntity.writeCustomData(GregtechDataCodes.WORKABLE_ACTIVE, (buf) -> {
                    buf.writeBoolean(active);
                });
            }
        }

    }

    public void setWorkingEnabled(boolean isWorkingEnabled) {
        if (this.isWorkingEnabled != isWorkingEnabled) {
            this.isWorkingEnabled = isWorkingEnabled;
            this.metaTileEntity.markDirty();
            if (this.metaTileEntity.getWorld() != null && !this.metaTileEntity.getWorld().isRemote) {
                this.metaTileEntity.writeCustomData(GregtechDataCodes.WORKING_ENABLED, (buf) -> {
                    buf.writeBoolean(isWorkingEnabled);
                });
            }
        }

    }

    public boolean isWorkingEnabled() {
        return this.isWorkingEnabled;
    }

    public boolean isWorking() {
        return this.isActive && !this.hasNotEnoughEnergy && this.isWorkingEnabled;
    }

    public int getProgressTime() {
        return this.progressTime;
    }

    public double getProgressPercent() {
        return (double)this.getProgressTime() * 1.0 / 20.0;
    }

    protected boolean isOverclocked() {
        return this.metaTileEntity.getEnergyTier() > this.metaTileEntity.getTier();
    }

    public boolean isInventoryFull() {
        return this.isInventoryFull;
    }

    public NBTTagCompound writeToNBT( NBTTagCompound data) {
        data.setBoolean("isActive", this.isActive);
        data.setBoolean("isWorkingEnabled", this.isWorkingEnabled);
        data.setBoolean("wasActiveAndNeedsUpdate", this.wasActiveAndNeedsUpdate);
        data.setBoolean("isDone", this.isDone);
        data.setInteger("progressTime", this.progressTime);
        data.setBoolean("isInventoryFull", this.isInventoryFull);
        return data;
    }

    public void readFromNBT( NBTTagCompound data) {
        this.isActive = data.getBoolean("isActive");
        this.isWorkingEnabled = data.getBoolean("isWorkingEnabled");
        this.wasActiveAndNeedsUpdate = data.getBoolean("wasActiveAndNeedsUpdate");
        this.isDone = data.getBoolean("isDone");
        this.progressTime = data.getInteger("progressTime");
        this.isInventoryFull = data.getBoolean("isInventoryFull");
    }

    public void writeInitialSyncData( PacketBuffer buf) {
        buf.writeBoolean(this.isActive);
        buf.writeBoolean(this.isWorkingEnabled);
        buf.writeBoolean(this.wasActiveAndNeedsUpdate);
        buf.writeInt(this.progressTime);
        buf.writeBoolean(this.isInventoryFull);
    }

    public void receiveInitialSyncData( PacketBuffer buf) {
        this.setActive(buf.readBoolean());
        this.setWorkingEnabled(buf.readBoolean());
        this.setWasActiveAndNeedsUpdate(buf.readBoolean());
        this.progressTime = buf.readInt();
        this.isInventoryFull = buf.readBoolean();
    }

    public void receiveCustomData(int dataId, PacketBuffer buf) {
        if (dataId == GregtechDataCodes.WORKABLE_ACTIVE) {
            this.isActive = buf.readBoolean();
            this.metaTileEntity.scheduleRenderUpdate();
        } else if (dataId == GregtechDataCodes.WORKING_ENABLED) {
            this.isWorkingEnabled = buf.readBoolean();
            this.metaTileEntity.scheduleRenderUpdate();
        }

    }

    public boolean wasActiveAndNeedsUpdate() {
        return this.wasActiveAndNeedsUpdate;
    }

    public void setWasActiveAndNeedsUpdate(boolean wasActiveAndNeedsUpdate) {
        this.wasActiveAndNeedsUpdate = wasActiveAndNeedsUpdate;
    }
}
