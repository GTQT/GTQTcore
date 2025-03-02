package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.IGuiTexture;
import gregtech.api.gui.widgets.GhostCircuitSlotWidget;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import keqing.gtqtcore.api.capability.ICatalystHatch;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.items.behaviors.CatalystBehavior;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MetaTileEntityCatalystHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<ICatalystHatch>, ICatalystHatch {

    private final CatalystHolder catalystHolder;
    private boolean needUpdate;
    private final int slotCount;
    private final int tier;

    public MetaTileEntityCatalystHatch(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.slotCount = tier * tier;
        this.catalystHolder = new CatalystHolder(slotCount); // 传递 slotCount 给 CatalystHolder
        this.tier = tier;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCatalystHatch(metaTileEntityId, tier);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (this.shouldRenderOverlay()) {
            GTQTTextures.CATALYST_HATCH.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
    }

    protected ModularUI createUI(EntityPlayer entityPlayer) {
        int rowSize = (int) Math.sqrt(this.slotCount);
        return this.createUITemplate(entityPlayer, rowSize).build(this.getHolder(), entityPlayer);
    }

    private ModularUI.Builder createUITemplate(EntityPlayer player, int gridSize) {
        int backgroundWidth = gridSize > 6 ? 176 + (gridSize - 6) * 18 : 176;
        int center = backgroundWidth / 2;
        int gridStartX = center - gridSize * 9;
        int inventoryStartX = center - 9 - 72;
        int inventoryStartY = 18 + 18 * gridSize + 12;
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, backgroundWidth, 18 + 18 * gridSize + 94).label(10, 5, this.getMetaFullName());

        for (int circuitX = 0; circuitX < gridSize; ++circuitX) {
            for (int circuitY = 0; circuitY < gridSize; ++circuitY) {
                int index = circuitX * gridSize + circuitY;
                builder.widget(new SlotWidget(this.catalystHolder, index, gridStartX + circuitY * 18, 18 + circuitX * 18, true, true)
                        .setBackgroundTexture(GuiTextures.SLOT));
            }
        }
        return builder.bindPlayerInventory(player.inventory, GuiTextures.SLOT, inventoryStartX, inventoryStartY);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("只能放置催化剂喵！"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addToolUsages(ItemStack stack, World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.tool_action.screwdriver.access_covers"));
        tooltip.add(I18n.format("gregtech.tool_action.wrench.set_facing"));
        super.addToolUsages(stack, world, tooltip, advanced);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(this.needUpdate);
        buf.writeCompoundTag(this.catalystHolder.serializeNBT());
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.needUpdate = buf.readBoolean();
        try {
            this.catalystHolder.deserializeNBT(Objects.requireNonNull(buf.readCompoundTag()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setBoolean("needUpdate", this.needUpdate);
        data.setTag("item", this.catalystHolder.serializeNBT());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        if (data.hasKey("needUpdate")) {
            this.needUpdate = data.getBoolean("needUpdate");
        }

        if (data.hasKey("item")) {
            this.catalystHolder.deserializeNBT(data.getCompoundTag("item"));
        }
    }

    @Override
    public void clearMachineInventory(NonNullList<ItemStack> itemBuffer) {
        clearInventory(itemBuffer, this.catalystHolder);
    }

    @Override
    protected boolean shouldSerializeInventories() {
        return false;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ?
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.catalystHolder) :
                super.getCapability(capability, side);
    }

    @Override
    public List<ItemStack> getCatalystList() {
        return this.catalystHolder.getCatalystList();
    }
    public boolean ItemStackEqual(ItemStack a, ItemStack b)
    {
        return a.getItem()==b.getItem();
    }
    @Override
    public void consumeCatalyst(ItemStack catalyst, int amount) {
        for (int i = 0; i < this.catalystHolder.getSlots(); i++) {
            if (ItemStackEqual(this.catalystHolder.getCatalystStack(i),catalyst)) {
                this.catalystHolder.damageCatalyst(i, amount);
            }
        }
    }

    @Override
    public boolean hasCatalyst(ItemStack catalyst) {
        for (int i = 0; i < this.catalystHolder.getSlots(); i++) {
            if (ItemStackEqual(this.catalystHolder.getCatalystStack(i),catalyst)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public MultiblockAbility<ICatalystHatch> getAbility() {
        return GTQTMultiblockAbility.CATALYST_MULTIBLOCK_ABILITY;
    }

    @Override
    public void registerAbilities(List<ICatalystHatch> list) {
        list.add(this);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        this.catalystHolder.setStackInSlot(slot, stack);
    }

    @Override
    public int getSlots() {
        return this.catalystHolder.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.catalystHolder.getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return this.catalystHolder.insertItem(slot, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return this.catalystHolder.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.catalystHolder.getSlotLimit(slot);
    }
    private class CatalystHolder extends ItemStackHandler {
        private final List<CatalystBehavior> catalystBehaviors;

        // 修改构造函数，接受 slotCount 参数
        public CatalystHolder(int slotCount) {
            super(slotCount);
            this.catalystBehaviors = new ArrayList<>(slotCount);
            for (int i = 0; i < slotCount; i++) {
                catalystBehaviors.add(null);
            }
        }

        private CatalystBehavior getCatalystBehavior(int slot) {
            ItemStack stack = this.getStackInSlot(slot);
            if (stack.isEmpty()) return null;
            return CatalystBehavior.getInstanceFor(stack);
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        private void damageCatalyst(int slot, int damageAmount) {
            CatalystBehavior behavior = getCatalystBehavior(slot);
            if (behavior != null) {
                behavior.applyCatalystDamage(this.getStackInSlot(slot), damageAmount);
            }
        }

        private ItemStack getCatalystStack(int slot) {
            CatalystBehavior behavior = getCatalystBehavior(slot);
            if (behavior == null) return null;
            return this.getStackInSlot(slot);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return CatalystBehavior.getInstanceFor(stack) != null && super.isItemValid(slot, stack);
        }

        @Override
        protected void onLoad() {
            for (int i = 0; i < getSlots(); i++) {
                this.onContentsChanged(i);
            }
        }

        @Override
        protected void onContentsChanged(int slot) {
            catalystBehaviors.set(slot, getCatalystBehavior(slot));
            needUpdate = true;
        }

        private boolean hasCatalyst(int slot) {
            return getCatalystBehavior(slot) != null;
        }

        public List<ItemStack> getCatalystList() {
            List<ItemStack> catalysts = new ArrayList<>();
            for (int i = 0; i < getSlots(); i++) {
                ItemStack stack = getCatalystStack(i);
                if (stack != null) {
                    catalysts.add(stack);
                }
            }
            return catalysts;
        }
    }

    @Override
    public void onRemoval() {
        super.onRemoval();
        for (int i = 0; i < catalystHolder.getSlots(); i++) {
            var pos = getPos();
            if(!catalystHolder.getStackInSlot(i).isEmpty())
            {
                getWorld().spawnEntity(new EntityItem(getWorld(),pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,catalystHolder.getStackInSlot(i)));
                catalystHolder.extractItem(i,1,false);
            }

        }
    }

}