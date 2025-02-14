package keqing.gtqtcore.integration.append.items;

import appeng.api.AEApi;
import appeng.api.config.FuzzyMode;
import appeng.api.exceptions.MissingDefinitionException;
import appeng.api.implementations.items.IItemGroup;
import appeng.api.implementations.items.IStorageCell;
import appeng.api.implementations.items.IUpgradeModule;
import appeng.api.storage.IMEInventoryHandler;
import appeng.api.storage.ISaveProvider;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAEStack;
import appeng.api.storage.data.IItemList;
import appeng.core.AEConfig;
import appeng.core.features.AEFeature;
import appeng.core.localization.GuiText;
import appeng.items.AEBaseItem;
import appeng.items.contents.CellConfig;
import appeng.items.contents.CellUpgrades;

import appeng.util.InventoryAdaptor;
import appeng.util.Platform;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

import java.util.List;
import java.util.Set;

public abstract class StorageCell<T extends IAEStack<T>> extends AEBaseItem implements IStorageCell<T>, IItemGroup {

    protected final int totalBytes;
    protected final int types;
    protected final Item component;

    public StorageCell(int types, int kilobytes, Item component) {
        this.setMaxStackSize(1);
        this.totalBytes = kilobytes * 1024;
        this.types = types;
        this.component = component;
    }

    @SideOnly(Side.CLIENT)
    public void addCheckedInformation(ItemStack stack, World world, List<String> lines, ITooltipFlag advancedTooltips) {
        AEApi.instance().client().addCellInformation(AEApi.instance().registries().cell().getCellInventory(stack, (ISaveProvider)null, this.getChannel()), lines);
    }

    public int getBytes(ItemStack cellItem) {
        return this.totalBytes;
    }

    public int getTotalTypes(ItemStack cellItem) {
        return this.types;
    }

    public boolean isBlackListed(ItemStack cellItem, T requestedAddition) {
        return false;
    }

    public boolean storableInStorageCell() {
        return false;
    }

    public boolean isStorageCell(ItemStack i) {
        return true;
    }

    public String getUnlocalizedGroupName(Set<ItemStack> others, ItemStack is) {
        return GuiText.StorageCells.getUnlocalized();
    }

    public boolean isEditable(ItemStack is) {
        return true;
    }

    public IItemHandler getUpgradesInventory(ItemStack is) {
        return new CellUpgrades(is, 2);
    }

    public IItemHandler getConfigInventory(ItemStack is) {
        return new CellConfig(is);
    }

    public FuzzyMode getFuzzyMode(ItemStack is) {
        String fz = Platform.openNbtData(is).getString("FuzzyMode");

        try {
            return FuzzyMode.valueOf(fz);
        } catch (Throwable var4) {
            return FuzzyMode.IGNORE_ALL;
        }
    }

    public void setFuzzyMode(ItemStack is, FuzzyMode fzMode) {
        Platform.openNbtData(is).setString("FuzzyMode", fzMode.name());
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        this.disassembleDrive(player.getHeldItem(hand), world, player);
        return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    private boolean disassembleDrive(ItemStack stack, World world, EntityPlayer player) {
        if (player.isSneaking()) {
            if (Platform.isClient()) {
                return false;
            }

            InventoryPlayer playerInventory = player.inventory;
            IMEInventoryHandler inv = AEApi.instance().registries().cell().getCellInventory(stack, (ISaveProvider)null, this.getChannel());
            if (inv != null && playerInventory.getCurrentItem() == stack) {
                InventoryAdaptor ia = InventoryAdaptor.getAdaptor(player);
                IItemList<IAEItemStack> list = inv.getAvailableItems(this.getChannel().createList());
                if (list.isEmpty() && ia != null) {
                    playerInventory.setInventorySlotContents(playerInventory.currentItem, ItemStack.EMPTY);
                    ItemStack extraB = ia.addItems(new ItemStack(component, 1));
                    if (!extraB.isEmpty()) {
                        player.dropItem(extraB, false);
                    }

                    IItemHandler upgradesInventory = this.getUpgradesInventory(stack);

                    for(int upgradeIndex = 0; upgradeIndex < upgradesInventory.getSlots(); ++upgradeIndex) {
                        ItemStack upgradeStack = upgradesInventory.getStackInSlot(upgradeIndex);
                        ItemStack leftStack = ia.addItems(upgradeStack);
                        if (!leftStack.isEmpty() && upgradeStack.getItem() instanceof IUpgradeModule) {
                            player.dropItem(upgradeStack, false);
                        }
                    }

                    this.dropEmptyStorageCellCase(ia, player);
                    if (player.inventoryContainer != null) {
                        player.inventoryContainer.detectAndSendChanges();
                    }

                    return true;
                }
            }
        }

        return false;
    }


    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        return this.disassembleDrive(player.getHeldItem(hand), world, player) ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
    }

    public ItemStack getContainerItem(ItemStack itemStack) {
        return (ItemStack)AEApi.instance().definitions().materials().emptyStorageCell().maybeStack(1).orElseThrow(() -> {
            return new MissingDefinitionException("Tried to use empty storage cells while basic storage cells are defined.");
        });
    }

    protected void dropEmptyStorageCellCase(final InventoryAdaptor ia, final EntityPlayer player) {
		AEApi.instance().definitions().materials().emptyStorageCell().maybeStack(1).ifPresent(is ->
		{
			final var extraA = ia.addItems(is);
			if (!extraA.isEmpty()) {
				player.dropItem(extraA, false);
			}
		});
	}



    public boolean hasContainerItem(ItemStack stack) {
        return AEConfig.instance().isFeatureEnabled(AEFeature.ENABLE_DISASSEMBLY_CRAFTING);
    }
}

