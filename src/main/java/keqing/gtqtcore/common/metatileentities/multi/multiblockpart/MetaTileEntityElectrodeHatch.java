package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.DynamicLabelWidget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.unification.material.Material;
import gregtech.common.items.behaviors.AbstractMaterialPartBehavior;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import keqing.gtqtcore.api.capability.IElectrode;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.utils.GTQTDateHelper;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.items.behaviors.ElectrodeBehavior;
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
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityElectrodeHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IElectrode>, IElectrode {
    private final ItemStackHandler containerInventory;
    long TotalTick;
    long workTime;
    int tier;
    boolean work = false;

    public MetaTileEntityElectrodeHatch(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.tier = tier;
        this.containerInventory = new NotifiableItemStackHandler(this, 1, null, false) {
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

        };
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

    @Override
    public IItemHandlerModifiable getImportItems() {
        return this.containerInventory;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setTag("ContainerInventory", this.containerInventory.serializeNBT());
        data.setBoolean("work", work);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.containerInventory.deserializeNBT(data.getCompoundTag("ContainerInventory"));
        work = data.getBoolean("work");
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 180, 240)
                .widget(new DynamicLabelWidget(28, 12, () -> "电极仓-等级：" + tier))
                .widget(new SlotWidget(this.containerInventory, 0, 8, 8, true, true, true)
                        .setBackgroundTexture(GuiTextures.SLOT)
                        .setChangeListener(this::markDirty)
                        .setTooltipText("请放入电极"))
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
            GTQTTextures.ELECTRODE_HATCH.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
    }

    public void update() {
        if (this.getController() == null) {
            work = false;
        }
        // 确保槽位存在且不为空
        ItemStack stack = containerInventory.getStackInSlot(0);
        if (stack.isEmpty()) {
            workTime = 0;
            TotalTick = 0;
            return;
        }

        // 确保行为对象不为 null
        ElectrodeBehavior behavior = getElectrodeBehavior();
        if (behavior == null) {
            workTime = 0;
            TotalTick = 0;
            return;
        }

        if (isItemValid(stack)) {
            if (work)
                if (getOffsetTimer() % tier == 0) behavior.applyDamage(containerInventory.getStackInSlot(0), 1);

            workTime = (long) AbstractMaterialPartBehavior.getPartDamage(containerInventory.getStackInSlot(0)) * (tier);
            TotalTick = (long) behavior.getPartMaxDurability(containerInventory.getStackInSlot(0)) * (tier);
        } else {
            workTime = 0;
            TotalTick = 0;
        }
    }

    public boolean isItemValid(@Nonnull ItemStack stack) {
        return ElectrodeBehavior.getInstanceFor(stack) != null;
    }

    @Nullable
    private ElectrodeBehavior getElectrodeBehavior() {
        ItemStack stack = containerInventory.getStackInSlot(0);
        if (stack.isEmpty()) return null;

        return ElectrodeBehavior.getInstanceFor(stack);
    }

    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentString("已经工作: " + GTQTDateHelper.getTimeFromTicks(workTime)));
        textList.add(new TextComponentString("距离损坏: " + GTQTDateHelper.getTimeFromTicks(TotalTick - workTime)));
        if (isAvailable())
            textList.add(new TextComponentString("电极材料: " + getElectrodeBehavior().getMaterial().getLocalizedName()));
        if (isAvailable())
            textList.add(new TextComponentString("极型等级: " + getElectrodeBehavior().getElectrodeTier()));
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityElectrodeHatch(metaTileEntityId, tier);
    }

    @Override
    public MultiblockAbility<IElectrode> getAbility() {
        return GTQTMultiblockAbility.ELECTRODE_MULTIBLOCK_ABILITY;
    }

    @Override
    public void registerAbilities(List<IElectrode> list) {
        list.add(this);
    }

    @Override
    public int getElectrodeTier() {
        if (isAvailable()) return getElectrodeBehavior().getElectrodeTier();
        return 0;
    }

    @Override
    public boolean isAvailable() {
        return isItemValid(containerInventory.getStackInSlot(0));
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public long getWorkTime() {
        return workTime;
    }

    @Override
    public long getTotalTick() {
        return TotalTick;
    }

    @Override
    public void setWork(boolean w) {
        work = w;
    }

    @Override
    public Material getMaterial() {
        return ElectrodeBehavior.getInstanceFor(containerInventory.getStackInSlot(0)).getMaterial();
    }
}
