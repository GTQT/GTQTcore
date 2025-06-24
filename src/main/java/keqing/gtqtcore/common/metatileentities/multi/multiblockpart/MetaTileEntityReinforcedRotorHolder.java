package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.impl.MultiblockFuelRecipeLogic;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.damagesources.DamageSources;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.ITieredMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.AbilityInstances;
import gregtech.api.metatileentity.multiblock.FuelMultiblockController;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.unification.material.Material;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.items.behaviors.AbstractMaterialPartBehavior;
import gregtech.common.items.behaviors.TurbineRotorBehavior;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import gregtech.core.advancement.AdvancementTriggers;
import keqing.gtqtcore.api.capability.GTQTDataCode;
import keqing.gtqtcore.api.capability.IReinforcedRotorHolder;
import keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityReinforcedRotorHolder extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IReinforcedRotorHolder>, IReinforcedRotorHolder {

    static final int SPEED_INCREMENT = 1;
    static final int SPEED_DECREMENT = 3;
    private final MetaTileEntityReinforcedRotorHolder.InventoryRotorHolder inventory = new InventoryRotorHolder();
    private final int maxSpeed;
    private int currentSpeed;
    private int rotorColor = -1;
    private boolean isRotorSpinning;
    private boolean frontFaceFree;

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ?
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.inventory) :
                super.getCapability(capability, side);
    }

    @Override
    public void onRemoval() {
        super.onRemoval();
        var pos = getPos();
        if (!inventory.getStackInSlot(0).isEmpty()) {
            getWorld().spawnEntity(new EntityItem(getWorld(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    inventory.getStackInSlot(0)));
            inventory.extractItem(0, 1, false);
        }
    }

    public MetaTileEntityReinforcedRotorHolder(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.maxSpeed = 2000 + 1000 * tier;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityReinforcedRotorHolder(metaTileEntityId, getTier());
    }

    @Override
    public IItemHandlerModifiable getImportItems() {
        return this.inventory;
    }

    @Override
    protected ModularUI createUI(@Nonnull EntityPlayer entityPlayer) {
        return ModularUI.defaultBuilder()
                .label(6, 6, getMetaFullName())
                .slot(inventory, 0, 79, 36, GuiTextures.SLOT, GuiTextures.TURBINE_OVERLAY)
                .bindPlayerInventory(entityPlayer.inventory)
                .build(getHolder(), entityPlayer);
    }

    @Override
    public void addInformation(ItemStack stack,
                               @Nullable World player,
                               @Nonnull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.rotor_holder.tooltip1"));
        tooltip.add(I18n.format("gregtech.machine.rotor_holder.tooltip2"));
        tooltip.add(I18n.format("gtqtcore.machine.reinforced_rotor_holder.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.reinforced_rotor_holder.tooltip.2"));
        tooltip.add(I18n.format("gregtech.universal.disabled"));
    }

    @Override
    public void addToolUsages(ItemStack stack,
                              @Nullable World world,
                              @Nonnull List<String> tooltip,
                              boolean advanced) {
        tooltip.add(I18n.format("gregtech.tool_action.screwdriver.access_covers"));
        tooltip.add(I18n.format("gregtech.tool_action.wrench.set_facing"));
        super.addToolUsages(stack, world, tooltip, advanced);
    }

    @Override
    public MultiblockAbility<IReinforcedRotorHolder> getAbility() {
        return GTQTMultiblockAbility.REINFORCED_ROTOR_HOLDER_ABILITY;
    }

    @Override
    public void update() {
        super.update();
        if (!this.getWorld().isRemote) {
            if (this.getOffsetTimer() % 20L == 0L) {
                boolean isFrontFree = this.checkTurbineFaceFree();
                if (isFrontFree != this.frontFaceFree) {
                    this.frontFaceFree = isFrontFree;
                    this.writeCustomData(GregtechDataCodes.FRONT_FACE_FREE, (buf) -> buf.writeBoolean(this.frontFaceFree));
                }
            }
        }

        FuelMultiblockController controller = (FuelMultiblockController) getController();
        if(controller==null||!controller.isStructureFormed())
        {
            this.setCurrentSpeed(0);
        }
        if (controller != null && controller.isActive()) {
            if (this.currentSpeed < this.maxSpeed) {
                this.setCurrentSpeed(this.currentSpeed + 1);
            }

            if (this.getOffsetTimer() % 20L == 0L) {
                this.damageRotor(1 + controller.getNumMaintenanceProblems());
            }
        } else if (!this.hasRotor()) {
            this.setCurrentSpeed(0);
        } else if (this.currentSpeed > 0) {
            this.setCurrentSpeed(Math.max(0, currentSpeed - SPEED_DECREMENT));
        }
    }

    @Override
    public void setCurrentSpeed(int speed) {
        if (currentSpeed != speed) {
            currentSpeed = speed;
            setRotorSpinning(currentSpeed > 0);
            markDirty();
        }
    }

    public void setRotorSpinning(boolean spinning) {
        if (isRotorSpinning != spinning) {
            isRotorSpinning = spinning;
            writeCustomData(GregtechDataCodes.IS_ROTOR_LOOPING, buf -> buf.writeBoolean(isRotorSpinning));
        }
    }

    @Override
    public void setRotor(ItemStack itemStack) {
        inventory.setStackInSlot(0, itemStack);
        inventory.onContentsChanged(0);
        if (!getWorld().isRemote) {
            writeCustomData(GTQTDataCode.ChannelReinforcedRotorHolder, buf -> buf.writeInt(rotorColor));
            markDirty();
        }
    }

    @Override
    public void registerAbilities(AbilityInstances abilityInstances) {
        abilityInstances.add(this);
    }
    @Override
    public boolean canPartShare() {
        return false;
    }

    /**
     * @return true if front face is free and contains only air blocks in 3x3 area
     */
    public boolean isFrontFaceFree() {
        return frontFaceFree;
    }

    private boolean checkTurbineFaceFree() {
        final EnumFacing front = getFrontFacing();
        // this can be anything really, as long as it is not up/down when on Y axis
        final EnumFacing upwards = front.getAxis() == EnumFacing.Axis.Y ? EnumFacing.NORTH : EnumFacing.UP;

        for (int left = -1; left <= 1; left++) {
            for (int up = -1; up <= 1; up++) {
                if (left == 0 && up == 0) continue;
                // flip doesn't affect anything here since we are checking a square anyway
                final BlockPos checkPos = RelativeDirection.offsetPos(
                        getPos(), front, upwards, false, up, left, 1);
                final IBlockState state = getWorld().getBlockState(checkPos);
                if (!state.getBlock().isAir(state, getWorld(), checkPos)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean onRotorHolderInteract(@Nonnull EntityPlayer player) {
        //if (player.isCreative()) return false;

        //if (!getWorld().isRemote && isRotorSpinning) {
        //    float damageApplied = Math.min(1, currentSpeed / 1000);
        //    player.attackEntityFrom(DamageSources.getTurbineDamage(), damageApplied);
        //    AdvancementTriggers.ROTOR_HOLDER_DEATH.trigger((EntityPlayerMP) player);
        //    return true;
        //}
        //return isRotorSpinning;

        if (player.isCreative()) {
            return false;
        } else if (!this.getWorld().isRemote && this.isRotorSpinning) {
            float damageApplied = (float)Math.min(1, this.currentSpeed / 1000);
            player.attackEntityFrom(DamageSources.getTurbineDamage(), damageApplied);
            AdvancementTriggers.ROTOR_HOLDER_DEATH.trigger((EntityPlayerMP)player);
            return true;
        } else {
            return this.isRotorSpinning;
        }
    }

    /**
     * returns true on both the Client and Server
     *
     * @return whether there is a rotor in the holder
     */

    public boolean hasRotor() {
        return rotorColor != -1;
    }

    protected void setRotorColor(int color) {
        this.rotorColor = color;
    }

    protected int getRotorColor() {
        return rotorColor;
    }

    @Override
    public int getRotorSpeed() {
        return this.currentSpeed;
    }

    @Override
    public int getRotorEfficiency() {
        return inventory.getRotorEfficiency();
    }

    @Override
    public int getRotorPower() {
        return inventory.getRotorPower();
    }

    @Override
    public int getRotorDurabilityPercent() {
        return inventory.getRotorDurabilityPercent();
    }

    @Override
    public void damageRotor(int amount) {
        inventory.damageRotor(amount);
    }

    @Override
    public Material getRotorMaterial() {
        return inventory.getRotorMaterial();
    }

    @Override
    public int getMaxRotorHolderSpeed() {
        return this.maxSpeed;
    }

    /**
     * calculates the holder's power multiplier: 2x per tier above the multiblock controller
     *
     * @return the power multiplier provided by the rotor holder
     */
    @Override
    public int getHolderPowerMultiplier() {
        int tierDifference = getTierDifference();
        return tierDifference == -1 ? -1 : (int) Math.pow(2.0, this.getTierDifference());
    }

    @Override
    public int getHolderEfficiency() {
        int tierDifference = this.getTierDifference();
        return tierDifference == -1 ? -1 : 100 + 10 * tierDifference;
    }

    private int getTierDifference() {
        return this.getController() instanceof ITieredMetaTileEntity ? this.getTier() - ((ITieredMetaTileEntity) this.getController()).getTier() : -1;
    }

    @Override
    public boolean onRightClick(EntityPlayer playerIn,
                                EnumHand hand,
                                EnumFacing facing,
                                CuboidRayTraceResult hitResult) {
        return onRotorHolderInteract(playerIn) || super.onRightClick(playerIn, hand, facing, hitResult);
    }

    @Override
    public boolean onWrenchClick(EntityPlayer playerIn,
                                 EnumHand hand,
                                 EnumFacing facing,
                                 CuboidRayTraceResult hitResult) {
        return onRotorHolderInteract(playerIn) || super.onWrenchClick(playerIn, hand, facing, hitResult);
    }

    @Override
    public boolean onScrewdriverClick(EntityPlayer playerIn,
                                      EnumHand hand,
                                      EnumFacing facing,
                                      CuboidRayTraceResult hitResult) {
        return onRotorHolderInteract(playerIn);
    }

    @Override
    public void onLeftClick(EntityPlayer player,
                            EnumFacing facing,
                            CuboidRayTraceResult hitResult) {
        onRotorHolderInteract(player);
    }

    @Override
    public void clearMachineInventory(List<ItemStack> itemBuffer) {
        super.clearMachineInventory(itemBuffer);
        clearInventory(itemBuffer, inventory);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("inventory", inventory.serializeNBT());
        data.setInteger("currentSpeed", currentSpeed);
        data.setBoolean("Spinning", isRotorSpinning);
        data.setBoolean("FrontFree", frontFaceFree);
        return data;
    }

   
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.inventory.deserializeNBT(data.getCompoundTag("inventory"));
        this.currentSpeed = data.getInteger("currentSpeed");
        this.isRotorSpinning = data.getBoolean("Spinning");
        this.frontFaceFree = data.getBoolean("FrontFree");
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GregtechDataCodes.IS_ROTOR_LOOPING) {
            this.isRotorSpinning = buf.readBoolean();
            scheduleRenderUpdate();
        } else if (dataId == GregtechDataCodes.FRONT_FACE_FREE) {
            this.frontFaceFree = buf.readBoolean();
        } else if (dataId == GTQTDataCode.ChannelReinforcedRotorHolder) {
            this.rotorColor = buf.readInt();
            scheduleRenderUpdate();
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(isRotorSpinning);
        buf.writeInt(rotorColor);
        buf.writeBoolean(frontFaceFree);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.isRotorSpinning = buf.readBoolean();
        this.rotorColor = buf.readInt();
        this.frontFaceFree = buf.readBoolean();
        scheduleRenderUpdate();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.ROTOR_HOLDER_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
        Textures.LARGE_TURBINE_ROTOR_RENDERER.renderSided(renderState, translation, pipeline, getFrontFacing(),
                getController() != null, hasRotor(), isRotorSpinning, getRotorColor());
    }

    private class InventoryRotorHolder extends NotifiableItemStackHandler {

        public InventoryRotorHolder() {
            super(MetaTileEntityReinforcedRotorHolder.this, 1, null, false);
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        protected void onLoad() {
            rotorColor = getRotorColor();
        }

        @Override
        public void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setRotorColor(getRotorColor());
            scheduleRenderUpdate();
        }

        @Nullable
        private ItemStack getTurbineStack() {
            return !this.hasRotor() ? null : this.getStackInSlot(0);
        }

        @Nullable
        private TurbineRotorBehavior getTurbineBehavior() {
            ItemStack stack = getStackInSlot(0);
            return stack.isEmpty() ? null : TurbineRotorBehavior.getInstanceFor(stack);
        }

        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        private boolean hasRotor() {
            return getTurbineBehavior() != null;
        }

        private int getRotorColor() {
            //if (!hasRotor()) return -1;
            //noinspection ConstantConditions
            //return getTurbineBehavior().getPartMaterial(getStackInSlot(0)).getMaterialRGB();
            if (!this.hasRotor()) {
                return -1;
            } else {
                this.getTurbineBehavior();
                return TurbineRotorBehavior.getPartMaterial(this.getStackInSlot(0)).getMaterialRGB();
            }
        }

        private Material getRotorMaterial() {
            if (!hasRotor())
                return null;
            return AbstractMaterialPartBehavior.getPartMaterial(getStackInSlot(0));
        }

        private int getRotorDurabilityPercent() {
            //if (!hasRotor()) return 0;

            //noinspection ConstantConditions
            //return getTurbineBehavior().getRotorDurabilityPercent(getStackInSlot(0));
            return !this.hasRotor() ? 0 : this.getTurbineBehavior().getRotorDurabilityPercent(this.getStackInSlot(0));
        }

        private int getRotorEfficiency() {
            //if (!hasRotor()) return -1;

            //noinspection ConstantConditions
            //return getTurbineBehavior().getRotorEfficiency(getTurbineStack());
            if (!this.hasRotor()) {
                return -1;
            } else {
                this.getTurbineBehavior();
                return TurbineRotorBehavior.getRotorEfficiency(this.getTurbineStack());
            }
        }

        private int getRotorPower() {
            //if (!hasRotor()) return -1;

            //noinspection ConstantConditions
            //return getTurbineBehavior().getRotorPower(getTurbineStack());
            if (!this.hasRotor()) {
                return -1;
            } else {
                this.getTurbineBehavior();
                return TurbineRotorBehavior.getRotorPower(this.getTurbineStack());
            }
        }

        private void damageRotor(int damageAmount) {
            //if (!hasRotor()) return;
            //noinspection ConstantConditions
            //getTurbineBehavior().applyRotorDamage(getStackInSlot(0), damageAmount);
            if (this.hasRotor()) {
                if (this.getTurbineBehavior().getPartMaxDurability(this.getTurbineStack()) <= AbstractMaterialPartBehavior.getPartDamage(this.getTurbineStack()) + damageAmount) {
                    MultiblockFuelRecipeLogic holder = (MultiblockFuelRecipeLogic) getController().getRecipeLogic();
                    if (holder != null && holder.isWorking()) {
                        holder.invalidate();
                    }
                }
                this.getTurbineBehavior().applyRotorDamage(this.getStackInSlot(0), damageAmount);
            }
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return TurbineRotorBehavior.getInstanceFor(stack) != null && super.isItemValid(slot, stack);
        }

        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            ItemStack itemStack = super.extractItem(slot, amount, simulate);
            if (!simulate && itemStack != ItemStack.EMPTY)
                setRotorColor(-1);
            return itemStack;
        }
    }
}
