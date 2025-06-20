package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.resourceCollection;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.worldgen.bedrockFluids.BedrockFluidVeinHandler;
import gregtech.common.ConfigHolder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class AdvancedFluidDrillLogic {

    public static final int MAX_PROGRESS = 20;
    private final MetaTileEntityAdvancedFluidDrill metaTileEntity;
    public boolean isInventoryFull;
    public int progressTime = 0;
    public boolean isActive;
    public boolean isWorkingEnabled = true;
    public boolean needUpdate;
    public boolean isDone = false;
    public boolean hasNotEnoughEnergy;
    public Fluid veinFluid;

    public AdvancedFluidDrillLogic(MetaTileEntityAdvancedFluidDrill metaTileEntity) {
        this.metaTileEntity = metaTileEntity;
        this.veinFluid = null;
    }

    public void performDrilling() {
        if (!this.metaTileEntity.getWorld().isRemote) {
            if (this.veinFluid != null || this.acquireNewFluid()) {
                if (this.isWorkingEnabled) {
                    if (this.checkCanDrain()) {
                        if (!this.isInventoryFull) {
                            this.consumeEnergy(false);
                            if (!this.isActive) {
                                this.setActive(true);
                            }

                            ++this.progressTime;
                            if (this.progressTime % MAX_PROGRESS == 0) {
                                this.progressTime = 0;
                                int amount = this.getFluidToProduce();
                                if (this.metaTileEntity.fillTanks(new FluidStack(this.veinFluid, amount), true)) {
                                    this.metaTileEntity.fillTanks(new FluidStack(this.veinFluid, amount), false);
                                    this.depleteVein();
                                } else {
                                    this.isInventoryFull = true;
                                    this.setActive(false);
                                    this.setNeedUpdate(true);
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

    public void setNeedUpdate(boolean b) {
        this.needUpdate = b;
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

    /**
     *
     * @return the current progress towards producing fluid of the rig
     */
    public int getProgressTime() {
        return this.progressTime;
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
            if (this.hasNotEnoughEnergy && this.metaTileEntity.getEnergyInputPerSecond() > 19L * (long) GTValues.VA[this.metaTileEntity.getEnergyTier()]) {
                this.hasNotEnoughEnergy = false;
            }

            if (this.metaTileEntity.fillTanks(new FluidStack(this.veinFluid, this.getFluidToProduce()), true)) {
                this.isInventoryFull = false;
                return true;
            } else {
                this.isInventoryFull = true;
                if (this.isActive()) {
                    this.setActive(false);
                    this.setNeedUpdate(true);
                }

                return false;
            }
        }
    }

    boolean isActive() {
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

    public int getChunkX() {
        return Math.floorDiv(this.metaTileEntity.getPos().getX(), 16);
    }

    public int getChunkZ() {
        return Math.floorDiv(this.metaTileEntity.getPos().getZ(), 16);
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
    /**
     *
     * @return whether working is enabled for the logic
     */
    public boolean isWorkingEnabled() {
        return isWorkingEnabled;
    }

    public boolean isWorking() {
        return this.isActive && !this.hasNotEnoughEnergy && this.isWorkingEnabled;
    }

    public double getProgressPercent() {
        return (double) this.progressTime / MAX_PROGRESS;
    }

    protected boolean isOverclocked() {
        return this.metaTileEntity.getEnergyTier() > this.metaTileEntity.getTier();
    }

    /**
     *
     * @return whether the inventory is full
     */
    public boolean isInventoryFull() {
        return this.isInventoryFull;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setBoolean("isActive", this.isActive);
        data.setBoolean("isWorkingEnabled", this.isWorkingEnabled);
        data.setBoolean("needUpdate", this.needUpdate);
        data.setBoolean("isDone", this.isDone);
        data.setInteger("progressTime", this.progressTime);
        data.setBoolean("isInventoryFull", this.isInventoryFull);
        return data;
    }

    public void readFromNBT(NBTTagCompound data) {
        this.isActive = data.getBoolean("isActive");
        this.isWorkingEnabled = data.getBoolean("isWorkingEnabled");
        this.needUpdate = data.getBoolean("needUpdate");
        this.isDone = data.getBoolean("isDone");
        this.progressTime = data.getInteger("progressTime");
        this.isInventoryFull = data.getBoolean("isInventoryFull");
    }

    public void writeInitialSyncData(PacketBuffer buf) {
        buf.writeBoolean(this.isActive);
        buf.writeBoolean(this.isWorkingEnabled);
        buf.writeBoolean(this.needUpdate);
        buf.writeInt(this.progressTime);
        buf.writeBoolean(this.isInventoryFull);
    }

    public void receiveInitialSyncData(PacketBuffer buf) {
        this.setActive(buf.readBoolean());
        this.setWorkingEnabled(buf.readBoolean());
        this.setNeedUpdate(buf.readBoolean());
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
}
