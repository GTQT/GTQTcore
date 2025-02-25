package keqing.gtqtcore.integration.append.tile;

import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.parts.IPartModel;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;
import appeng.core.sync.GuiBridge;
import appeng.items.misc.ItemEncodedPattern;
import appeng.items.parts.PartModels;
import appeng.parts.PartModel;
import appeng.parts.reporting.PartExpandedProcessingPatternTerminal;
import appeng.tile.inventory.AppEngInternalInventory;
import appeng.util.Platform;
import appeng.util.inv.InvOperation;
import com.glodblock.github.common.item.ItemFluidDrop;
import com.glodblock.github.common.item.ItemFluidEncodedPattern;
import com.glodblock.github.common.item.ItemLargeEncodedPattern;
import com.glodblock.github.common.item.fake.FakeFluids;
import com.glodblock.github.common.item.fake.FakeItemRegister;
import com.glodblock.github.inventory.ExAppEngInternalInventory;
import com.glodblock.github.inventory.GuiType;
import com.glodblock.github.inventory.InventoryHandler;
import com.glodblock.github.loader.FCItems;
import com.glodblock.github.util.Util;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntIterator;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class PartExtendedFluidPatternTerminal extends PartExpandedProcessingPatternTerminal {
    private boolean combine = false;
    private boolean fluidFirst = false;
    @PartModels
    public static ResourceLocation[] MODELS = new ResourceLocation[]{new ResourceLocation("ae2fc", "part/f_pattern_ex_term_on"), new ResourceLocation("ae2fc", "part/f_pattern_ex_term_off")};
    private static final IPartModel MODELS_ON;
    private static final IPartModel MODELS_OFF;
    private static final IPartModel MODELS_HAS_CHANNEL;

    public PartExtendedFluidPatternTerminal(ItemStack is) {
        super(is);
        ExAppEngInternalInventory exCraft = new ExAppEngInternalInventory((AppEngInternalInventory)this.getInventoryByName("crafting"));
        ExAppEngInternalInventory exOutput = new ExAppEngInternalInventory((AppEngInternalInventory)this.getInventoryByName("output"));
        this.crafting = exCraft;
        this.output = exOutput;
    }

    @Nonnull
    public IPartModel getStaticModels() {
        return this.selectModel(MODELS_OFF, MODELS_ON, MODELS_HAS_CHANNEL);
    }

    public boolean onPartActivate(EntityPlayer player, EnumHand hand, Vec3d pos) {
        TileEntity te = this.getTile();
        BlockPos tePos = te.getPos();
        if (Platform.isWrench(player, player.inventory.getCurrentItem(), tePos)) {
            return super.onPartActivate(player, hand, pos);
        } else {
            if (Platform.isServer()) {
                if (GuiBridge.GUI_EXPANDED_PROCESSING_PATTERN_TERMINAL.hasPermissions(te, tePos.getX(), tePos.getY(), tePos.getZ(), this.getSide(), player)) {
                    InventoryHandler.openGui(player, te.getWorld(), tePos, this.getSide().getFacing(), GuiType.FLUID_EXTENDED_PATTERN_TERMINAL);
                } else {
                    Platform.openGUI(player, this.getHost().getTile(), this.getSide(), GuiBridge.GUI_ME);
                }
            }

            return true;
        }
    }

    public void onChangeInventory(IItemHandler inv, int slot, InvOperation mc, ItemStack removedStack, ItemStack newStack) {
        if (slot == 1) {
            ItemStack is = inv.getStackInSlot(1);
            if (!is.isEmpty() && (is.getItem() instanceof ItemFluidEncodedPattern || is.getItem() instanceof ItemLargeEncodedPattern)) {
                ItemEncodedPattern pattern = (ItemEncodedPattern)is.getItem();
                ICraftingPatternDetails details = pattern.getPatternForItem(is, this.getHost().getTile().getWorld());
                if (details != null) {
                    int x;
                    for(x = 0; x < this.getInventoryByName("crafting").getSlots(); ++x) {
                        ((AppEngInternalInventory)this.getInventoryByName("crafting")).setStackInSlot(x, ItemStack.EMPTY);
                    }

                    for(x = 0; x < this.getInventoryByName("output").getSlots(); ++x) {
                        ((AppEngInternalInventory)this.getInventoryByName("output")).setStackInSlot(x, ItemStack.EMPTY);
                    }

                    IAEItemStack item;
                    ItemStack packet;
                    for(x = 0; x < this.getInventoryByName("crafting").getSlots() && x < details.getInputs().length; ++x) {
                        item = details.getInputs()[x];
                        if (item != null && item.getItem() == FCItems.FLUID_DROP) {
                            packet = FakeFluids.packFluid2Packet((FluidStack)FakeItemRegister.getStack(item.createItemStack()));
                            ((AppEngInternalInventory)this.getInventoryByName("crafting")).setStackInSlot(x, packet);
                        } else {
                            ((AppEngInternalInventory)this.getInventoryByName("crafting")).setStackInSlot(x, item == null ? ItemStack.EMPTY : item.createItemStack());
                        }
                    }

                    for(x = 0; x < this.getInventoryByName("output").getSlots() && x < details.getOutputs().length; ++x) {
                        item = details.getOutputs()[x];
                        if (item != null && item.getItem() instanceof ItemFluidDrop) {
                            packet = FakeFluids.packFluid2Packet((FluidStack)FakeItemRegister.getStack(item.createItemStack()));
                            ((AppEngInternalInventory)this.getInventoryByName("output")).setStackInSlot(x, packet);
                        } else {
                            ((AppEngInternalInventory)this.getInventoryByName("output")).setStackInSlot(x, item == null ? ItemStack.EMPTY : item.createItemStack());
                        }
                    }
                }

                this.getHost().markForSave();
                return;
            }
        }

        super.onChangeInventory(inv, slot, mc, removedStack, newStack);
    }

    public void onChangeCrafting(Int2ObjectMap<ItemStack[]> inputs, List<ItemStack> outputs, boolean combine) {
        IItemHandler crafting = this.getInventoryByName("crafting");
        IItemHandler output = this.getInventoryByName("output");
        IItemList<IAEItemStack> storageList = this.getInventory(Util.getItemChannel()) == null ? null : this.getInventory(Util.getItemChannel()).getStorageList();
        if (crafting instanceof AppEngInternalInventory && output instanceof AppEngInternalInventory) {
            Util.clearItemInventory((IItemHandlerModifiable)crafting);
            Util.clearItemInventory((IItemHandlerModifiable)output);
            ItemStack[] fuzzyFind = new ItemStack[Util.findMax(inputs.keySet()) + 1];
            IntIterator var8 = inputs.keySet().iterator();

            int x;
            while(var8.hasNext()) {
                x = (Integer)var8.next();
                Util.fuzzyTransferItems(x, (ItemStack[])inputs.get(x), fuzzyFind, storageList);
            }

            if (combine) {
                fuzzyFind = Util.compress(fuzzyFind);
            }

            int bound = Math.min(crafting.getSlots(), fuzzyFind.length);

            ItemStack item;
            for(x = 0; x < bound; ++x) {
                item = fuzzyFind[x];
                ((AppEngInternalInventory)crafting).setStackInSlot(x, item == null ? ItemStack.EMPTY : item);
            }

            bound = Math.min(output.getSlots(), outputs.size());

            for(x = 0; x < bound; ++x) {
                item = (ItemStack)outputs.get(x);
                ((AppEngInternalInventory)output).setStackInSlot(x, item == null ? ItemStack.EMPTY : item);
            }
        }

    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.combine = data.getBoolean("combineMode");
        this.fluidFirst = data.getBoolean("fluidFirst");
    }

    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setBoolean("combineMode", this.combine);
        data.setBoolean("fluidFirst", this.fluidFirst);
    }

    public void setCombineMode(boolean value) {
        this.combine = value;
    }

    public boolean getCombineMode() {
        return this.combine;
    }

    public void setFluidPlaceMode(boolean value) {
        this.fluidFirst = value;
    }

    public boolean getFluidPlaceMode() {
        return this.fluidFirst;
    }

    static {
        MODELS_ON = new PartModel(new ResourceLocation[]{MODEL_BASE, MODELS[0], MODEL_STATUS_ON});
        MODELS_OFF = new PartModel(new ResourceLocation[]{MODEL_BASE, MODELS[1], MODEL_STATUS_OFF});
        MODELS_HAS_CHANNEL = new PartModel(new ResourceLocation[]{MODEL_BASE, MODELS[0], MODEL_STATUS_HAS_CHANNEL});
    }
}
