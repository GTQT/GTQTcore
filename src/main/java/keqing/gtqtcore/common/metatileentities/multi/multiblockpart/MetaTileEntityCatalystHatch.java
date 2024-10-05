package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MetaTileEntityCatalystHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<ICatalystHatch>, ICatalystHatch {

    private final CatalystHolder catalystHolder;
    private boolean needUpdate;

    public MetaTileEntityCatalystHatch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, 2);
        this.catalystHolder = new CatalystHolder();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCatalystHatch(metaTileEntityId);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (this.shouldRenderOverlay()) {
            GTQTTextures.CATALYST_HATCH.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 209).bindPlayerInventory(entityPlayer.inventory, 126).widget(new SlotWidget(this.catalystHolder, 0, 88 - 9, 50, true, true, true).setBackgroundTexture(GuiTextures.SLOT).setChangeListener(this::markDirty)).widget(new LabelWidget(88, 20, "只能使用催化剂喵！").setXCentered(true));

        return builder.build(this.getHolder(), entityPlayer);
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
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.catalystHolder) : super.getCapability(capability, side);
    }

    @Override
    public boolean hasCatalyst() {
        return this.catalystHolder.hasCatalyst();
    }

    @Override
    public void catalystConsumed(int amount) {
        this.catalystHolder.damageCatalyst(amount);
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


        private CatalystBehavior getCatalystBehavior() {
            ItemStack stack = this.getStackInSlot(0);
            if (stack.isEmpty()) return null;
            return CatalystBehavior.getInstanceFor(stack);
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        private void damageCatalyst(int damageAmount) {
            if (!this.hasCatalyst()) return;
            Objects.requireNonNull(this.getCatalystBehavior()).applyCatalystDamage(this.getStackInSlot(0), damageAmount);
        }


        private ItemStack getCatalystStack() {
            if (!this.hasCatalyst()) return null;
            return this.getStackInSlot(0);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return CatalystBehavior.getInstanceFor(stack) != null && super.isItemValid(slot, stack);
        }

        @Override
        protected void onLoad() {
            this.onContentsChanged(0);
        }

        @Override
        protected void onContentsChanged(int slot) {
            needUpdate = true;
        }

        private boolean hasCatalyst() {
            return this.getCatalystBehavior() != null;
        }

    }
}