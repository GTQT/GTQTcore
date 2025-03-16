package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;


import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.implementations.ICraftingPatternItem;
import appeng.api.implementations.IPowerChannelState;
import appeng.api.networking.GridFlags;
import appeng.api.networking.IGridNode;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.crafting.ICraftingProvider;
import appeng.api.networking.crafting.ICraftingProviderHelper;
import appeng.api.networking.events.MENetworkCraftingPatternChange;
import appeng.api.networking.security.IActionHost;
import appeng.api.networking.security.IActionSource;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.IStorageChannel;
import appeng.api.storage.channels.IItemStorageChannel;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.util.AECableType;
import appeng.api.util.AEPartLocation;
import appeng.api.util.DimensionalCoord;
import appeng.me.GridAccessException;
import appeng.me.helpers.AENetworkProxy;
import appeng.me.helpers.BaseActionSource;
import appeng.me.helpers.IGridProxyable;
import appeng.me.helpers.MachineSource;
import appeng.util.item.AEItemStack;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.*;
import gregtech.api.capability.impl.GhostCircuitItemStackHandler;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.util.GTUtility;
import gregtech.api.util.Position;
import gregtech.api.util.TextFormattingUtil;
import gregtech.common.ConfigHolder;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import keqing.gtqtcore.api.gui.GTQTGuiTextures;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.client.widgets.ItemSlotTinyAmountTextWidget;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static gregtech.api.capability.GregtechDataCodes.UPDATE_ACTIVE;
import static gregtech.api.capability.GregtechDataCodes.UPDATE_ONLINE_STATUS;

public class MetaTileEntityBudgetCRIBA4 extends MetaTileEntityMultiblockNotifiablePart
        implements IMultiblockAbilityPart<IItemHandlerModifiable>, ICraftingProvider,
        IGridProxyable, IPowerChannelState, IGhostSlotConfigurable, IControllable,
        IDataStickIntractable {

    protected GhostCircuitItemStackHandler circuitInventory;
    private ItemStackHandler patternSlot;
    private List<ICraftingPatternDetails> patternDetails = new ArrayList<>() {{
        add(null);
        add(null);
        add(null);
        add(null);
    }};
    private NotifiableItemStackHandler patternItems;
    private IItemHandlerModifiable actualImportItems;
    private IItemHandlerModifiable extraItem;
    private boolean needPatternSync = true;
    // Controls blocking
    private boolean isWorkingEnabled = true;

    private AENetworkProxy aeProxy;
    private boolean isOnline;

    public MetaTileEntityBudgetCRIBA4(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTValues.IV, false);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();

        this.patternSlot = new ItemStackHandler(4) {

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.getItem() instanceof ICraftingPatternItem;
            }

            @Override
            protected void onContentsChanged(int slot) {
                needPatternSync = true;
                setPatternDetails();
            }
        };

        this.circuitInventory = new GhostCircuitItemStackHandler(this);
        this.circuitInventory.addNotifiableMetaTileEntity(this);

        this.extraItem = new NotifiableItemStackHandler(this, 2, null, false) {

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return !(stack.getItem() instanceof ICraftingPatternItem);
            }
        };

        this.patternItems = new NotifiableItemStackHandler(this, 16, null, false) {

            @Override
            public int getSlotLimit(int slot) {
                return Integer.MAX_VALUE;
            }

            @Override
            protected int getStackLimit(int slot, @NotNull ItemStack stack) {
                return getSlotLimit(slot);
            }

            @NotNull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                if (amount == 0) return ItemStack.EMPTY;

                validateSlotIndex(slot);

                ItemStack existing = this.stacks.get(slot);

                if (existing.isEmpty()) return ItemStack.EMPTY;

                if (existing.getCount() <= amount) {
                    if (!simulate) {
                        this.stacks.set(slot, ItemStack.EMPTY);
                        onContentsChanged(slot);
                    }

                    return existing;
                } else {
                    if (!simulate) {
                        this.stacks.set(slot, ItemHandlerHelper.copyStackWithSize(
                                existing, existing.getCount() - amount));
                        onContentsChanged(slot);
                    }

                    return ItemHandlerHelper.copyStackWithSize(existing, amount);
                }
            }
        };

        this.actualImportItems = new ItemHandlerList(
                Arrays.asList(this.patternItems, this.circuitInventory, this.extraItem));
    }

    @Override
    public IItemHandlerModifiable getImportItems() {
        return this.actualImportItems;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityBudgetCRIBA4(metaTileEntityId);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 166 + 18 * 2)
                .label(7, 7, getMetaFullName());


        //样板槽位
        WidgetGroup pattern = new WidgetGroup(new Position(25+18, 20));
        for(int i=0;i<4;i++) {
            pattern.addWidget(new SlotWidget(patternSlot, i, 0, 18*i)
                    .setBackgroundTexture(GuiTextures.SLOT, GTQTGuiTextures.ME_PATTERN_OVERLAY));
        }

        // Item slots
        WidgetGroup slots = new WidgetGroup(new Position(7 + 18 * 3, 20));
        for (int y = 0; y <= 3; y++) {
            for (int x = 0; x <= 3; x++) {
                int index = y * 4 + x;
                slots.addWidget(new ItemSlotTinyAmountTextWidget(patternItems, index, 18 * x, 18 * y,
                        false, false) {

                    @Override
                    public void drawInForeground(int mouseX, int mouseY) {
                        ItemStack item = patternItems.getStackInSlot(index);

                        if (isMouseOverElement(mouseX, mouseY) && !item.isEmpty()) {
                            List<String> tooltip = getItemToolTip(item);

                            tooltip.add(TextFormatting.GRAY + I18n.format("gtqtcore.machine.budget_crib.amount_tooltip",
                                    TextFormattingUtil.formatNumbers(item.getCount())));

                            drawHoveringText(item, tooltip, -1, mouseX, mouseY);
                        }
                    }
                }.setBackgroundTexture(GuiTextures.SLOT));
            }
        }

        WidgetGroup buttons = new WidgetGroup(new Position(7+36, (int) (18 * 5.25) + 3));

        // Extra item slot
        buttons.addWidget(new SlotWidget(extraItem, 0, 0, 0)
                .setBackgroundTexture(GuiTextures.SLOT).setTooltipText("gtqtcore.machine.budget_crib.extra_item"));

        buttons.addWidget(new SlotWidget(extraItem, 1, 18, 0)
                .setBackgroundTexture(GuiTextures.SLOT).setTooltipText("gtqtcore.machine.budget_crib.extra_item"));

        // Circuit slot
        buttons.addWidget(new GhostCircuitSlotWidget(circuitInventory, 0, 36, 0)
                .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.INT_CIRCUIT_OVERLAY)
                .setConsumer(this::getCircuitSlotTooltip));

        // Blocking toggle
        buttons.addWidget(new ImageCycleButtonWidget(18 * 3, 0, 18, 18, GuiTextures.BUTTON_POWER,
                this::isWorkingEnabled, this::setWorkingEnabled)
                .setTooltipHoverString("gtqtcore.machine.budget_crib.blocking_button"));

        // Return items
        buttons.addWidget(new ClickButtonWidget(18 * 4, 0, 18, 18, "", (clickData) -> returnItems())
                .setButtonTexture(GTQTGuiTextures.EXPORT).setTooltipText("gtqtcore.machine.budget_crib.return_button"));

        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 7, 18 + 18 * 5 + 12);
        return builder.widget(pattern).widget(slots).widget(buttons).build(getHolder(), entityPlayer);
    }

    protected void getCircuitSlotTooltip(@NotNull SlotWidget widget) {
        String configString;
        if (circuitInventory == null || circuitInventory.getCircuitValue() == GhostCircuitItemStackHandler.NO_CONFIG) {
            configString = new TextComponentTranslation("gregtech.gui.configurator_slot.no_value").getFormattedText();
        } else {
            configString = String.valueOf(circuitInventory.getCircuitValue());
        }

        widget.setTooltipText("gregtech.gui.configurator_slot.tooltip", configString);
    }

    @Override
    public boolean hasGhostCircuitInventory() {
        return this.circuitInventory != null;
    }

    @Override
    public void setGhostCircuitConfig(int config) {
        if (this.circuitInventory == null || this.circuitInventory.getCircuitValue() == config) {
            return;
        }
        this.circuitInventory.setCircuitValue(config);
        if (!getWorld().isRemote) {
            markDirty();
        }
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (isOnline) {
            GTQTTextures.CRIB_ACTIVE.renderSided(getFrontFacing(), renderState, translation, pipeline);
        } else {
            GTQTTextures.CRIB_INACTIVE.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
    }

    @Override
    public void update() {
        super.update();

        if (!getWorld().isRemote) {
            updateMEStatus();

            if (needPatternSync && getOffsetTimer() % 10 == 0) {
                needPatternSync = MEPatternChange();
            }
        }
    }

    private IActionSource getActionSource() {
        if (this.getHolder() instanceof IActionHost holder) {
            return new MachineSource(holder);
        }
        return new BaseActionSource();
    }

    private IMEMonitor<IAEItemStack> getMonitor() {
        AENetworkProxy proxy = getProxy();
        if (proxy == null) return null;

        IStorageChannel<IAEItemStack> channel = AEApi.instance().storage().getStorageChannel(IItemStorageChannel.class);

        try {
            return proxy.getStorage().getInventory(channel);
        } catch (GridAccessException ignored) {
            return null;
        }
    }

    private void returnItems() {
        if (checkIfEmpty()) return;

        IMEMonitor<IAEItemStack> monitor = getMonitor();
        if (monitor == null) return;

        for (int x = 0; x < this.patternItems.getSlots(); x++) {
            ItemStack itemStack = this.patternItems.getStackInSlot(x);
            if (itemStack.isEmpty()) continue;

            IAEItemStack iaeItemStack = AEItemStack.fromItemStack(itemStack);

            IAEItemStack notInserted = monitor.injectItems(iaeItemStack, Actionable.MODULATE, getActionSource());
            if (notInserted != null && notInserted.getStackSize() > 0) {
                itemStack.setCount((int) notInserted.getStackSize());
            } else {
                this.patternItems.setStackInSlot(x, ItemStack.EMPTY);
            }
        }
    }

    private boolean MEPatternChange() {
        // don't post until it's active
        if (getProxy() == null || !getProxy().isActive()) return true;

        try {
            getProxy().getGrid().postEvent(new MENetworkCraftingPatternChange(this, getProxy().getNode()));
        } catch (Exception ignored) {
            return true;
        }

        return false;
    }

    public boolean updateMEStatus() {
        boolean isOnline = this.aeProxy != null && this.aeProxy.isActive() && this.aeProxy.isPowered();
        if (this.isOnline != isOnline) {
            writeCustomData(UPDATE_ONLINE_STATUS, buf -> buf.writeBoolean(isOnline));
            this.isOnline = isOnline;
        }
        return this.isOnline;
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == UPDATE_ONLINE_STATUS) {
            boolean isOnline = buf.readBoolean();
            if (this.isOnline != isOnline) {
                this.isOnline = isOnline;
                scheduleRenderUpdate();
            } else if (dataId == UPDATE_ACTIVE) {
                this.isWorkingEnabled = buf.readBoolean();
            }
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        if (this.aeProxy != null) {
            buf.writeBoolean(true);
            NBTTagCompound proxy = new NBTTagCompound();
            this.aeProxy.writeToNBT(proxy);
            buf.writeCompoundTag(proxy);
        } else {
            buf.writeBoolean(false);
        }
        buf.writeBoolean(this.isOnline);
        buf.writeBoolean(this.isWorkingEnabled);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        if (buf.readBoolean()) {
            NBTTagCompound nbtTagCompound;
            try {
                nbtTagCompound = buf.readCompoundTag();
            } catch (IOException ignored) {
                nbtTagCompound = null;
            }

            if (this.aeProxy != null && nbtTagCompound != null) {
                this.aeProxy.readFromNBT(nbtTagCompound);
            }
        }
        this.isOnline = buf.readBoolean();
        this.isWorkingEnabled = buf.readBoolean();
    }

    @Override
    public IGridNode getGridNode(@NotNull AEPartLocation aePartLocation) {
        return getProxy().getNode();
    }

    @NotNull
    @Override
    public AECableType getCableConnectionType(@NotNull AEPartLocation part) {
        if (part.getFacing() != this.frontFacing) {
            return AECableType.NONE;
        }
        return AECableType.SMART;
    }

    @Override
    public AENetworkProxy getProxy() {
        if (this.aeProxy == null) {
            return this.aeProxy = this.createProxy();
        }
        if (!this.aeProxy.isReady() && this.getWorld() != null) {
            this.aeProxy.onReady();
        }
        return this.aeProxy;
    }

    private AENetworkProxy createProxy() {
        AENetworkProxy proxy = new AENetworkProxy(this, "mte_proxy", this.getStackForm(), true);
        proxy.setFlags(GridFlags.REQUIRE_CHANNEL);
        proxy.setIdlePowerUsage(ConfigHolder.compat.ae2.meHatchEnergyUsage);
        proxy.setValidSides(EnumSet.of(this.getFrontFacing()));
        return proxy;
    }

    @Override
    public void setFrontFacing(EnumFacing frontFacing) {
        super.setFrontFacing(frontFacing);
        if (this.aeProxy != null) {
            this.aeProxy.setValidSides(EnumSet.of(this.getFrontFacing()));
        }
    }

    @Override
    public void securityBreak() {}

    @Override
    public void provideCrafting(ICraftingProviderHelper iCraftingProviderHelper) {
        if (!isActive() || patternDetails == null) return;
        for (int i = 0; i < 4; i++) {
            if(patternDetails.get(i)!=null) iCraftingProviderHelper.addCraftingOption(this, patternDetails.get(i));
        }
    }

    private void setPatternDetails() {
        for(int i=0;i<4;i++) {
            ItemStack pattern = patternSlot.getStackInSlot(i);
            if (pattern.isEmpty()) {
                patternDetails.set(i, null);
                return;
            }

            if (pattern.getItem() instanceof ICraftingPatternItem patternItem) {
                patternDetails.set(i, patternItem.getPatternForItem(pattern, getWorld()));
            }
        }
    }

    @Override
    public boolean pushPattern(ICraftingPatternDetails iCraftingPatternDetails, InventoryCrafting inventoryCrafting) {
        if (!isActive()) return false;

        for (int i = 0; i < inventoryCrafting.getSizeInventory(); ++i) {
            ItemStack itemStack = inventoryCrafting.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            if (patternItems.insertItem(i, itemStack, true) != ItemStack.EMPTY) return false;
        }

        for (int i = 0; i < inventoryCrafting.getSizeInventory(); ++i) {
            ItemStack itemStack = inventoryCrafting.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            patternItems.insertItem(i, itemStack, false);
        }

        return true;
    }

    @Override
    public boolean isBusy() {
        return isWorkingEnabled && !checkIfEmpty();
    }

    /**
     * @return false if items are in any slot, true if empty
     */
    private boolean checkIfEmpty() {
        return GTQTUtil.isInventoryEmpty(patternItems);
    }

    @Override
    public void gridChanged() {
        needPatternSync = true;
    }

    @Override
    public DimensionalCoord getLocation() {
        return new DimensionalCoord(getWorld(), getPos());
    }

    @Override
    public MultiblockAbility<IItemHandlerModifiable> getAbility() {
        return MultiblockAbility.IMPORT_ITEMS;
    }

    @Override
    public void registerAbilities(List<IItemHandlerModifiable> abilityList) {
        abilityList.add(this.actualImportItems);
    }

    @Override
    public boolean isPowered() {
        return getProxy() != null && getProxy().isPowered();
    }

    @Override
    public boolean isActive() {
        return getProxy() != null && getProxy().isActive();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("Pattern", this.patternSlot.serializeNBT());
        GTUtility.writeItems(this.patternItems, "PatternItems", data);

        if (this.circuitInventory != null) {
            this.circuitInventory.write(data);
        }

        GTUtility.writeItems(this.extraItem, "ExtraItem", data);

        data.setBoolean("BlockingEnabled", this.isWorkingEnabled);

        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.patternSlot.deserializeNBT(data.getCompoundTag("Pattern"));

        setPatternDetails();
        GTUtility.readItems(this.patternItems, "PatternItems", data);

        circuitInventory.read(data);

        GTUtility.readItems(this.extraItem, "ExtraItem", data);

        this.isWorkingEnabled = data.getBoolean("BlockingEnabled");
    }

    @Override
    public void addToMultiBlock(MultiblockControllerBase controllerBase) {
        super.addToMultiBlock(controllerBase);

        for (IItemHandler handler : ((ItemHandlerList) this.actualImportItems).getBackingHandlers()) {
            if (handler instanceof INotifiableHandler notifiable) {
                notifiable.addNotifiableMetaTileEntity(controllerBase);
                notifiable.addToNotifiedList(this, handler, false);
            }
        }
    }

    @Override
    public void removeFromMultiBlock(MultiblockControllerBase controllerBase) {
        super.removeFromMultiBlock(controllerBase);

        for (IItemHandler handler : ((ItemHandlerList) this.actualImportItems).getBackingHandlers()) {
            if (handler instanceof INotifiableHandler notifiable) {
                notifiable.removeNotifiableMetaTileEntity(controllerBase);
            }
        }
    }

    @Override
    public boolean isWorkingEnabled() {
        return this.isWorkingEnabled;
    }

    @Override
    public void setWorkingEnabled(boolean isWorkingAllowed) {
        this.isWorkingEnabled = isWorkingAllowed;
        if (!getWorld().isRemote) {
            writeCustomData(GregtechDataCodes.UPDATE_ACTIVE, buf -> buf.writeBoolean(isWorkingAllowed));
        }
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public void clearMachineInventory(NonNullList<ItemStack> itemBuffer) {
        super.clearMachineInventory(itemBuffer);
        clearInventory(itemBuffer, this.patternSlot);
        clearInventory(itemBuffer, this.extraItem);
        this.returnItems();
    }

    @Override
    public void onDataStickLeftClick(EntityPlayer player, ItemStack dataStick) {
        NBTTagCompound tag = new NBTTagCompound();

        tag.setTag("BudgetCRIB", writeLocationToTag());
        dataStick.setTagCompound(tag);
        dataStick.setTranslatableName("gtqtcore.machine.budget_crib.data_stick_name");
        player.sendStatusMessage(new TextComponentTranslation("gtqtcore.machine.budget_crib.data_stick_use"), true);
    }

    private NBTTagCompound writeLocationToTag() {
        NBTTagCompound tag = new NBTTagCompound();

        tag.setInteger("MainX", getPos().getX());
        tag.setInteger("MainY", getPos().getY());
        tag.setInteger("MainZ", getPos().getZ());

        return tag;
    }

    @Override
    public boolean onDataStickRightClick(EntityPlayer player, ItemStack dataStick) {
        return false;
    }

    public NotifiableItemStackHandler getPatternItems() {
        return this.patternItems;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addToolUsages(ItemStack stack, World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gtqtcore.machine.budget_crib.tooltip"));
        super.addToolUsages(stack, world, tooltip, advanced);
    }
}