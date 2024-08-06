package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;


import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.NotifiableFluidTank;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import keqing.gtqtcore.api.capability.GTQTDataCode;
import keqing.gtqtcore.api.capability.IBuffer;
import keqing.gtqtcore.api.capability.IPHValue;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.utils.GTQTUniverUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetaTileEntityBufferHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IBuffer>, IBuffer {

    private final BufferFluidTank fluidTank;
    private final BufferItemHandler itemHandler;

    public static Map<Integer,double[]> BufferItemMap = new HashMap<>();
    public static Map<String,double[]> BufferFluidMap = new HashMap<>();

    private boolean needUpdate = false;

    private String ph = "7.0";

    public MetaTileEntityBufferHatch(ResourceLocation metaTileEntityId) {

        super(metaTileEntityId, 5);
        this.fluidTank = new BufferFluidTank(8000, this);
        this.itemHandler = new BufferItemHandler();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityBufferHatch(metaTileEntityId);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176,
                        18 * 2 + 94)
                .label(10, 5, getMetaFullName());
                builder.widget(new TankWidget(fluidTank, 89 - 1 * 9, 18, 18, 18)
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setContainerClicking(true, true)
                        .setAlwaysShowFull(true));

                builder.widget(new SlotWidget(itemHandler, 0, 89 + 1 * 9, 18, true, true, true)
                        .setBackgroundTexture(GuiTextures.SLOT)
                        .setChangeListener(this::markDirty));
                /*builder.label(10, 20, "gtqtcore.multipart.ph.name");*/
                builder.dynamicLabel(10, 20, () -> I18n.format("gtqtcore.multipart.ph.name", this.ph), 99);
                builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 7, 18 + 18 + 12);
                builder.bindOpenListener(this::setPH);

        return builder.build(getHolder(), entityPlayer);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (this.shouldRenderOverlay()){
            GTQTTextures.MULTIPART_BUFFER_HATCH.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }

    }
    @Override
    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture() {
        MultiblockControllerBase controller = getController();
        if (controller != null) {
            return this.hatchTexture = controller.getBaseTexture(this);
        } else if (this.hatchTexture != null) {
            if (hatchTexture != Textures.getInactiveTexture(hatchTexture)) {
                return this.hatchTexture = Textures.getInactiveTexture(hatchTexture);
            }
            return this.hatchTexture;
        } else {
            return Textures.VOLTAGE_CASINGS[5];
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gtqtcore.machine.buffer_hatch.import.tooltip"));
        tooltip.add(I18n.format("gregtech.universal.disabled"));
    }

    @Override
    public void addToolUsages(ItemStack stack, @Nullable World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.tool_action.screwdriver.access_covers"));
        tooltip.add(I18n.format("gregtech.tool_action.wrench.set_facing"));
        super.addToolUsages(stack, world, tooltip, advanced);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        this.itemHandler.deserializeNBT(data.getCompoundTag("BufferItem"));
        this.fluidTank.readFromNBT(data);
        this.needUpdate = data.getBoolean("needupdate");
        super.readFromNBT(data);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setTag("BufferItem", this.itemHandler.serializeNBT());
        data.setBoolean("needupdate", this.needUpdate);
        this.fluidTank.writeToNBT(data);
        return super.writeToNBT(data);
    }

    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(this.needUpdate);
        buf.writeCompoundTag(itemHandler.serializeNBT());
    }

    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.needUpdate = buf.readBoolean();
        try {
            itemHandler.deserializeNBT( buf.readCompoundTag());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPH() {
        if (!this.getWorld().isRemote) {
            this.writeCustomData(GTQTDataCode.GTQT_CHANNEL_1, (buf) -> {
                buf.writeString(getPH());
            });
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GTQTDataCode.GTQT_CHANNEL_1){
            this.ph = buf.readString(99);
        }
    }

    @Override
    public void addToMultiBlock(MultiblockControllerBase controllerBase) {
        super.addToMultiBlock(controllerBase);
        this.needUpdate = true;
    }

    @Override
    public void removeFromMultiBlock(MultiblockControllerBase controllerBase) {
        super.removeFromMultiBlock(controllerBase);
        this.needUpdate = false;
    }

    public String getPH() {
        if (getController() != null) {
            return String.format("%, .2f", ((IPHValue) getController()).getCurrentPHValue());
        }else {
            return "7.0";
        }
    }

    //TODO 重写
    public void changePH() {
        ItemStack bufferitem = itemHandler.getStackInSlot(0);
        FluidStack bufferfluid = fluidTank.getFluid();
        if (bufferitem.isEmpty() && bufferfluid == null) {
            needUpdate = false;
        } else {
            if (!bufferitem.isEmpty()){
                double[] phchangeitem = BufferItemMap.get(GTQTUniverUtil.stackToInt(bufferitem));
                MultiblockControllerBase current_controller = getController();
                if (current_controller == null || !current_controller.isStructureFormed()) {
                    needUpdate = false;
                } else {
                    if (current_controller.isActive()) {
                        needUpdate = true;
                    } else {
                        if (phchangeitem != null) {
                            itemHandler.extractItem(0,1,false);
                            ((IPHValue) current_controller).changeCurrentPHValue(phchangeitem[0], phchangeitem[1]);
                            needUpdate = true;
                        } else {
                            needUpdate = false;
                        }
                    }
                }
            }
            if (bufferfluid != null && bufferfluid.amount >= 1000){
                double[] phchangefluid = BufferFluidMap.get(bufferfluid.getFluid().getName());
                MultiblockControllerBase current_controller = getController();
                if (current_controller == null || !current_controller.isStructureFormed()) {
                    needUpdate = false;
                } else {
                    if (current_controller.isActive()) {
                        needUpdate = true;
                    } else {
                        if (phchangefluid != null) {
                            fluidTank.drain(1000,true);
                            ((IPHValue) current_controller).changeCurrentPHValue(phchangefluid[0], phchangefluid[1]);
                            needUpdate = true;
                        }else {
                            needUpdate = false;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if(needUpdate && !getWorld().isRemote){
            this.changePH();
            this.setPH();
            this.markDirty();
        }
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
        } else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidTank);
        } else {
            return super.getCapability(capability, side);
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> dropsList, @Nullable EntityPlayer harvester) {
        dropsList.add(itemHandler.getStackInSlot(0));
    }

    @Override
    public boolean hasBuffer() {
        return !(itemHandler.getStackInSlot(0).isEmpty()) || !(fluidTank.getFluid() == null);
    }

    @Override
    public MultiblockAbility<IBuffer> getAbility() {
        return GTQTMultiblockAbility.BUFFER_MULTIBLOCK_ABILITY;
    }

    @Override
    public void registerAbilities(List<IBuffer> list) {
        list.add(this);
    }

    @Override
    public boolean canPartShare() {
        return false;
    }

    private class BufferFluidTank extends NotifiableFluidTank {

        public BufferFluidTank(int capacity, MetaTileEntity entityToNotify) {
            super(capacity, entityToNotify, false);
        }

        // override for visibility
        @Override
        public void onContentsChanged() {
            super.onContentsChanged();
            MetaTileEntityBufferHatch.this.markDirty();
            needUpdate = true;
        }

    }

    private class BufferItemHandler extends ItemStackHandler {
        public BufferItemHandler() {
            super();
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            needUpdate = true;
        }
    }
}
