package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
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
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import gregtech.common.pipelike.optical.tile.TileEntityOpticalPipe;
import keqing.gtqtcore.api.utils.GTQTDateHelper;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Collection;
import java.util.List;

import static gregtech.api.GTValues.UV;
import static gregtech.api.GTValues.VA;

public class MetaTileEntityLocalModelComputationHatch extends MetaTileEntityMultiblockPart implements
        IMultiblockAbilityPart<IOpticalComputationHatch>, IOpticalComputationHatch {
    private final ItemStackHandler containerInventory;
    int supportCWT;

    @Override
    public void onRemoval() {
        super.onRemoval();
        for (int i = 0; i < containerInventory.getSlots(); i++) {
            var pos = getPos();
            if(!containerInventory.getStackInSlot(i).isEmpty())
            {
                getWorld().spawnEntity(new EntityItem(getWorld(),pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,containerInventory.getStackInSlot(i)));
                containerInventory.extractItem(i,1,false);
            }

        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setTag("ContainerInventory", this.containerInventory.serializeNBT());
        data.setInteger("supportCWT", supportCWT);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.containerInventory.deserializeNBT(data.getCompoundTag("ContainerInventory"));
        supportCWT = data.getInteger("supportCWT");
    }

    public void update() {
        ItemStack stack = containerInventory.getStackInSlot(0);
        if (stack.getItem() != GTQTMetaItems.GTQT_META_ITEM)supportCWT=0;
        if (stack.getMetadata() == GTQTMetaItems.MODEL_LOCATION_MKI.getMetaValue()) {
            supportCWT=32;
        }
        else if (stack.getMetadata() == GTQTMetaItems.MODEL_LOCATION_MKII.getMetaValue()) {
            supportCWT=64;
        }
        else if (stack.getMetadata() == GTQTMetaItems.MODEL_LOCATION_MKIII.getMetaValue()) {
            supportCWT=128;
        }
        else if (stack.getMetadata() == GTQTMetaItems.MODEL_LOCATION_MKIV.getMetaValue()) {
            supportCWT=256;
        }
        else if (stack.getMetadata() == GTQTMetaItems.MODEL_LOCATION_MKV.getMetaValue()) {
            supportCWT=512;
        }
        else if (stack.getMetadata() == GTQTMetaItems.MODEL_LOCATION_MKVI.getMetaValue()) {
            supportCWT=1024;
        }
        else supportCWT=0;
    }

    public MetaTileEntityLocalModelComputationHatch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId,UV);
        this.containerInventory = new NotifiableItemStackHandler(this, 1, null, false)
        {
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }


    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLocalModelComputationHatch(metaTileEntityId);
    }

    @Override
    public boolean isTransmitter() {
        return false;
    }

    @Override
    public int requestCWUt(int cwut, boolean simulate, Collection<IOpticalComputationProvider> seen) {
        return supportCWT;

    }

    @Override
    public int getMaxCWUt(Collection<IOpticalComputationProvider> seen) {
        return supportCWT;
    }

    @Override
    public boolean canBridge(Collection<IOpticalComputationProvider> seen) {
        return false;
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 209)
                .bindPlayerInventory(entityPlayer.inventory, 126)
                .widget(new DynamicLabelWidget(7, 7, () -> "本地AI模型仓"))
                .widget(new SlotWidget(this.containerInventory, 0, 88 - 9, 30, true, true, true)
                        .setBackgroundTexture(GuiTextures.SLOT)
                        .setChangeListener(this::markDirty)
                        .setTooltipText("请放入蒸馏模型"))
                .widget(new ImageWidget(88 - 9, 48, 18, 6, GuiTextures.BUTTON_POWER_DETAIL))
                .widget((new AdvancedTextWidget(7, 68, this::addDisplayText, 2302755)).setMaxWidthLimit(181));
        return builder.build(this.getHolder(), entityPlayer);
    }
    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentString("AI提供算力: " + supportCWT));
    }
    @Override
    public boolean canPartShare() {
        return false;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (shouldRenderOverlay()) {
            Textures.OPTICAL_DATA_ACCESS_HATCH.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
    }

    @Override
    public MultiblockAbility<IOpticalComputationHatch> getAbility() {
        return MultiblockAbility.COMPUTATION_DATA_RECEPTION;
    }

    @Override
    public void registerAbilities(List<IOpticalComputationHatch> abilityList) {
        abilityList.add(this);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (side == getFrontFacing() && capability == GregtechTileCapabilities.CABABILITY_COMPUTATION_PROVIDER) {
            return GregtechTileCapabilities.CABABILITY_COMPUTATION_PROVIDER.cast(this);
        }
        return super.getCapability(capability, side);
    }

    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("作为算力靶仓安装在多方块内"));
        tooltip.add(I18n.format("在仓内放入本地AI蒸馏模型即可向多方块提供算力"));
    }

}