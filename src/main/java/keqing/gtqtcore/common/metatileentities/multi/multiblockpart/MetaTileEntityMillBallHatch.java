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
import gregtech.api.metatileentity.multiblock.AbilityInstances;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import keqing.gtqtcore.api.capability.IBall;
import keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.items.behaviors.MillBallBehavior;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;
public class MetaTileEntityMillBallHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IBall>, IBall{

    private final ItemBallHolder ballHolder;

    private boolean needUpdate;

    public MetaTileEntityMillBallHatch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, 4);
        this.ballHolder = new ItemBallHolder();
    }

    @Override
    public void onRemoval() {
        super.onRemoval();
        for (int i = 0; i < ballHolder.getSlots(); i++) {
            var pos = getPos();
            if(!ballHolder.getStackInSlot(i).isEmpty())
            {
                getWorld().spawnEntity(new EntityItem(getWorld(),pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,ballHolder.getStackInSlot(i)));
                ballHolder.extractItem(i,1,false);
            }

        }
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityMillBallHatch(this.metaTileEntityId);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (this.shouldRenderOverlay()){
            GTQTTextures.MULTIPART_BALL_HATCH.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 209)
                .bindPlayerInventory(entityPlayer.inventory, 126)
                .widget(new SlotWidget(this.ballHolder,0, 88-9,50,true,true,true)
                        .setBackgroundTexture(GuiTextures.SLOT)
                        .setChangeListener(this::markDirty))
                .widget(new LabelWidget(88,20,"gtqtcore.multipart.ball.only")
                        .setXCentered(true));

        return builder.build(this.getHolder(),entityPlayer);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.multipart.ball.only"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addToolUsages(ItemStack stack, @javax.annotation.Nullable World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.tool_action.screwdriver.access_covers"));
        tooltip.add(I18n.format("gregtech.tool_action.wrench.set_facing"));
        super.addToolUsages(stack, world, tooltip, advanced);
    }

    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(this.needUpdate);
        buf.writeCompoundTag(ballHolder.serializeNBT());
    }

    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.needUpdate = buf.readBoolean();
        try {
            ballHolder.deserializeNBT(buf.readCompoundTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setBoolean("needUpdate", this.needUpdate);
        data.setTag("item", this.ballHolder.serializeNBT());
        return data;
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        if (data.hasKey("needUpdate")) {
            this.needUpdate = data.getBoolean("needUpdate");
        }

        if (data.hasKey("item")) {
            ballHolder.deserializeNBT(data.getCompoundTag("item"));
        }
    }

    @Override
    public void clearMachineInventory(List<ItemStack> itemBuffer) {
        super.clearMachineInventory(itemBuffer);
        clearInventory(itemBuffer, ballHolder);
    }
    @Override
    protected boolean shouldSerializeInventories() {
        return false;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ?
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(ballHolder) : super.getCapability(capability, side);
    }

    @Override
    public boolean hasBall() {
        return ballHolder.hasBall();
    }

    @Override
    public int getGrinderTier() {
        return ballHolder.getBallTier();
    }

    @Override
    public void damageGrinder(int amount) {

        ballHolder.damageBall(amount);
    }

    @Override
    public MultiblockAbility<IBall> getAbility() {
        return GTQTMultiblockAbility.GRINDBALL_MULTIBLOCK_ABILITY;
    }

    @Override
    public void registerAbilities(AbilityInstances abilityInstances) {
        abilityInstances.add(this);
    }

    private class ItemBallHolder extends ItemStackHandler {
        @Nullable
        private MillBallBehavior getBallBehavior() {
            ItemStack stack = getStackInSlot(0);
            if (stack.isEmpty()) return null;

            return MillBallBehavior.getInstanceFor(stack);
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        private boolean hasBall() {
            return getBallBehavior() != null;
        }

        private void damageBall(int damageAmount) {
            if (!hasBall()) return;
            //noinspection ConstantConditions
            getBallBehavior().applyBallDamage(getStackInSlot(0), damageAmount);
        }

        @Nullable
        private ItemStack getBallStack() {
            if (!hasBall())
                return null;
            return getStackInSlot(0);
        }

        private int getBallTier(){
            if(getBallStack() == null)
                return 0;
            else
                return GTQTMetaItems.GRINDBALL_SOAPSTONE.isItemEqual(getBallStack()) ? 1 : 2;
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return MillBallBehavior.getInstanceFor(stack) != null && super.isItemValid(slot, stack);
        }

        @Override
        protected void onLoad() {
            onContentsChanged(0);
        }

        @Override
        protected void onContentsChanged(int slot) {
            needUpdate = true;
        }

    }
}