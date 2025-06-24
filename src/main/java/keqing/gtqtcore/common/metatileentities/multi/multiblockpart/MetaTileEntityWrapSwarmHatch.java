package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.DynamicLabelWidget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.AbilityInstances;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.unification.material.Material;
import gregtech.common.items.behaviors.AbstractMaterialPartBehavior;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import keqing.gtqtcore.api.capability.IWarpSwarm;
import keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.items.behaviors.WrapSwarmBehavior;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityWrapSwarmHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IWarpSwarm>, IWarpSwarm {
    private final ItemStackHandler containerInventory;


    public MetaTileEntityWrapSwarmHatch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, 9);
        this.containerInventory = new GTItemStackHandler(this, 1);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ?
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.containerInventory) :
                super.getCapability(capability, side);
    }

    @Override
    public void onRemoval() {
        super.onRemoval();
        var pos = getPos();
        if (!containerInventory.getStackInSlot(0).isEmpty()) {
            getWorld().spawnEntity(new EntityItem(getWorld(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, containerInventory.getStackInSlot(0)));
            containerInventory.extractItem(0, 1, false);
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setTag("ContainerInventory", this.containerInventory.serializeNBT());
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.containerInventory.deserializeNBT(data.getCompoundTag("ContainerInventory"));
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 180, 240)
                .widget(new DynamicLabelWidget(28, 12, () -> "纳米封装芯片仓"))
                .widget(new SlotWidget(this.containerInventory, 0, 8, 8, true, true, true)
                        .setBackgroundTexture(GuiTextures.SLOT)
                        .setChangeListener(this::markDirty)
                        .setTooltipText("请放入芯片"))
                .widget(new ImageWidget(8, 26, 18, 6, GuiTextures.BUTTON_POWER_DETAIL))
                .image(4, 28, 172, 128, GuiTextures.DISPLAY)
                .widget((new AdvancedTextWidget(8, 32, this::addDisplayText, 16777215)).setMaxWidthLimit(180))
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 8, 160);
        return builder.build(this.getHolder(), entityPlayer);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (this.shouldRenderOverlay()) {
            GTQTTextures.WRAP_SWARM.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
    }


    public boolean isItemValid(@Nonnull ItemStack stack) {
        return WrapSwarmBehavior.getInstanceFor(stack) != null;
    }


    protected void addDisplayText(List<ITextComponent> textList) {
        if (getWrapSwarmBehavior() == null || !isAvailable()) return;
        textList.add(new TextComponentString("芯片材料: " + getWrapSwarmBehavior().getMaterial().getLocalizedName()));
        textList.add(new TextComponentString("芯片等级: " + getWrapSwarmBehavior().getWrapSwarmTier()));
        textList.add(new TextComponentString("生命耐久: " + getWrapSwarmBehavior().getDurabilityPercent(containerInventory.getStackInSlot(0))));
        textList.add(new TextComponentString("使用次数: " + AbstractMaterialPartBehavior.getPartDamage(containerInventory.getStackInSlot(0))));
        textList.add(new TextComponentString("总计次数: " + getWrapSwarmBehavior().getPartMaxDurability(containerInventory.getStackInSlot(0))));
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityWrapSwarmHatch(metaTileEntityId);
    }

    @Override
    public MultiblockAbility<IWarpSwarm> getAbility() {
        return GTQTMultiblockAbility.WARP_SWARM_MULTIBLOCK_ABILITY;
    }

    @Override
    public void registerAbilities(AbilityInstances abilityInstances) {
        abilityInstances.add(this);
    }

    @Override
    public boolean isAvailable() {
        return isItemValid(containerInventory.getStackInSlot(0));
    }


    public boolean applyDamage(int damageApplied) {
        if (getWrapSwarmBehavior() == null || !isAvailable()) return false;
        getWrapSwarmBehavior().applyDamage(containerInventory.getStackInSlot(0), damageApplied);
        return true;

    }

    @Override
    public int getParallel() {
        if(isAvailable()) return (int) (256 * Math.pow(2, getWarpSwarmTier()));
        return 1;
    }

    @Nullable
    private WrapSwarmBehavior getWrapSwarmBehavior() {
        ItemStack stack = containerInventory.getStackInSlot(0);
        if (stack.isEmpty()) return null;

        return WrapSwarmBehavior.getInstanceFor(stack);
    }

    @Override
    public int getWarpSwarmTier() {
        if (getWrapSwarmBehavior() == null || !isAvailable()) return 0;
        return getWrapSwarmBehavior().getWrapSwarmTier();
    }

    @Override
    public Material getMaterial() {
        if (getWrapSwarmBehavior() == null || !isAvailable()) return null;
        return WrapSwarmBehavior.getInstanceFor(containerInventory.getStackInSlot(0)).getMaterial();
    }
}
