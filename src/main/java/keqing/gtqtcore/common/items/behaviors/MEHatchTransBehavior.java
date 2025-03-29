package keqing.gtqtcore.common.items.behaviors;

import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.channels.IItemStorageChannel;
import appeng.api.storage.data.IAEItemStack;
import appeng.core.localization.GuiText;
import appeng.me.GridAccessException;
import appeng.me.helpers.AENetworkProxy;
import appeng.me.helpers.PlayerSource;
import appeng.tile.misc.TileSecurityStation;
import gregtech.api.GregTechAPI;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.items.gui.ItemUIFactory;
import gregtech.api.items.gui.PlayerInventoryHolder;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityEnergyHatch;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityFluidHatch;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityItemBus;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import keqing.gtqtcore.api.utils.GTQTLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import java.util.List;

import static gregtech.api.metatileentity.multiblock.MultiblockAbility.*;
import static gregtech.common.metatileentities.MetaTileEntities.*;

public class MEHatchTransBehavior implements IItemBehaviour, ItemUIFactory {
    BlockPos AEpos;
    boolean aeModel;
    boolean meModel;
    int targetTier;
    BlockPos targetPos;
    private AENetworkProxy networkProxy;

    MetaTileEntity[] hatchTypes = {
            MetaTileEntities.MAINTENANCE_HATCH,
            MetaTileEntities.CONFIGURABLE_MAINTENANCE_HATCH,
            MetaTileEntities.AUTO_MAINTENANCE_HATCH,
            MetaTileEntities.CLEANING_MAINTENANCE_HATCH
    };

    public static void setTier(MetaTileEntity trans, EntityPlayer player, BlockPos pos) {
        int facing = 0;

        player.sendMessage(new TextComponentString("正在尝试替换"+pos+"的目标为："+trans.getStackForm().getDisplayName()).setStyle(new Style().setColor(TextFormatting.GREEN)));

        TileEntity tileEntity = player.world.getTileEntity(pos);
        if (tileEntity instanceof IGregTechTileEntity igtte) {
            MetaTileEntity mte = igtte.getMetaTileEntity();
            facing = mte.getFrontFacing().getIndex();
        }

        if (trans != null) {
            ItemStack found = trans.getStackForm(1);
            ItemBlock itemBlock = (ItemBlock) found.getItem();
            IBlockState state = itemBlock.getBlock().getStateFromMeta(itemBlock.getMetadata(found.getMetadata()));
            player.world.setBlockState(pos, state);

            TileEntity holder = player.world.getTileEntity(pos);
            if (holder instanceof IGregTechTileEntity) {
                MetaTileEntity sampleMetaTileEntity = GregTechAPI.MTE_REGISTRY.getObjectById(found.getItemDamage());
                if (sampleMetaTileEntity != null) {
                    MetaTileEntity metaTileEntity = ((IGregTechTileEntity) holder).setMetaTileEntity(sampleMetaTileEntity);
                    metaTileEntity.onPlacement();
                    if (found.getTagCompound() != null) {
                        metaTileEntity.initFromItemStackData(found.getTagCompound());
                    }

                }
            }

            if (holder instanceof IGregTechTileEntity igtte) {
                MetaTileEntity mte = igtte.getMetaTileEntity();
                mte.setFrontFacing(EnumFacing.byIndex(facing));
            }
        }
    }

    public void writeToNBT(NBTTagCompound compound) {
        compound.setInteger("AEposX", AEpos.getX());
        compound.setInteger("AEposY", AEpos.getY());
        compound.setInteger("AEposZ", AEpos.getZ());
        compound.setBoolean("aeModel", aeModel);
        compound.setBoolean("meModel", meModel);
        compound.setInteger("targetTier", targetTier);
    }

    public void readFromNBT(NBTTagCompound compound) {
        int x = compound.getInteger("AEposX");
        int y = compound.getInteger("AEposY");
        int z = compound.getInteger("AEposZ");
        AEpos = new BlockPos(x, y, z);
        aeModel = compound.getBoolean("aeModel");
        meModel = compound.getBoolean("meModel");
        targetTier = compound.getInteger("targetTier");
    }

    @Override
    public ModularUI createUI(PlayerInventoryHolder playerInventoryHolder, EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 115)
                .image(10, 8, 156, 50, GuiTextures.DISPLAY)
                .widget((new AdvancedTextWidget(15, 11, this::addDisplayText, 16777215))
                        .setMaxWidthLimit(500))

                .widget(new ClickButtonWidget(10, 63, 76, 20, I18n.format("仓口升级"), clickData -> this.targetTier = MathHelper.clamp(targetTier + 1, 0, 14)))
                .widget(new ClickButtonWidget(90, 63, 76, 20, I18n.format("仓口降级"), clickData -> this.targetTier = MathHelper.clamp(targetTier - 1, 0, 14)))

                .widget(new ClickButtonWidget(10, 87, 76, 20, I18n.format("网络模式"), clickData -> aeModel = !aeModel).setTooltipText("将从网络内抽取仓室，并将返回物品发送至网络"))
                .widget(new ClickButtonWidget(90, 87, 76, 20, I18n.format("ME模式"), clickData -> meModel = !meModel).setTooltipText("将输入/输出 总线/仓 替换为对应的ME仓"))
                .build(playerInventoryHolder, entityPlayer);
    }

    private void addDisplayText(List<ITextComponent> iTextComponents) {
        iTextComponents.add(new TextComponentTranslation("仓室替换器"));
        iTextComponents.add(new TextComponentTranslation("目标等级" + targetTier));
        iTextComponents.add(new TextComponentTranslation("网络模式" + aeModel));
        if(aeModel)
        {
            if(networkProxy!=null)iTextComponents.add(new TextComponentTranslation("网络："+ networkProxy));
            else iTextComponents.add(new TextComponentTranslation("网络：未连接"));
        }
    }

    public void onUpdate(ItemStack itemStack, Entity entity) {
        if (AEpos != null && networkProxy == null) {
            TileEntity tileEntity = entity.world.getTileEntity(AEpos);
            if (tileEntity instanceof TileSecurityStation securityTerminal) {
                if (securityTerminal.getProxy() != null) {
                    networkProxy = securityTerminal.getProxy();
                }
            }
        }
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        // Initial checks
        TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity instanceof TileSecurityStation securityTerminal) {
            if (securityTerminal.getProxy() != null) {
                AEpos = pos;
                networkProxy = securityTerminal.getProxy();
                if (world.isRemote)player.sendMessage(new TextComponentString("成功绑定AE网络!"));
                ItemStack stack = player.getHeldItem(hand);
                NBTTagCompound compound;

                if (stack.hasTagCompound()) {
                    compound = stack.getTagCompound();
                } else {
                    compound = new NBTTagCompound();
                }

                compound.setInteger("AEposX", AEpos.getX());
                compound.setInteger("AEposY", AEpos.getY());
                compound.setInteger("AEposZ", AEpos.getZ());

                // 保存 NBT 数据到物品栈
                stack.setTagCompound(compound);

                return EnumActionResult.SUCCESS;
            } else {
                if (world.isRemote)player.sendMessage(new TextComponentString("未绑定AE网络!"));
                return EnumActionResult.FAIL;
            }
        }

        if (world.isRemote) return EnumActionResult.SUCCESS;

        if (tileEntity instanceof IGregTechTileEntity igtte) {
            MetaTileEntity mte = igtte.getMetaTileEntity();
            transHatch(mte, player);
        }

        if (player.isSneaking()) {
            if (!world.isRemote) {
                PlayerInventoryHolder holder = new PlayerInventoryHolder(player, hand);
                holder.openUI();
            }
        }
        return EnumActionResult.SUCCESS;
    }

    private void transHatch(MetaTileEntity mte, EntityPlayer player) {
        targetPos = mte.getPos();

        for (MetaTileEntity hatchType : hatchTypes) {
            if (mte == hatchType) {
                trans(mte, hatchTypes[targetTier], player, targetPos);
            }
        }

        if (mte instanceof MetaTileEntityMultiblockPart part) {
            int tier = part.getTier();


            if (mte instanceof MetaTileEntityItemBus hatch) {
                if (hatch.getAbility() == EXPORT_ITEMS) {
                    if(meModel)trans(ITEM_EXPORT_BUS[tier], ITEM_EXPORT_BUS_ME, player, targetPos);
                    else trans(ITEM_EXPORT_BUS[tier], ITEM_EXPORT_BUS[targetTier], player, targetPos);
                }
                if (hatch.getAbility() == IMPORT_ITEMS) {
                    if(meModel)trans(ITEM_IMPORT_BUS[tier], ITEM_IMPORT_BUS_ME, player, targetPos);
                    else trans(ITEM_IMPORT_BUS[tier], ITEM_IMPORT_BUS[targetTier], player, targetPos);
                }
            }
            if (mte instanceof MetaTileEntityFluidHatch hatch) {
                if (hatch.getAbility() == EXPORT_FLUIDS) {
                    if(meModel)trans(FLUID_EXPORT_HATCH[tier], FLUID_EXPORT_HATCH_ME, player, targetPos);
                    else trans(FLUID_EXPORT_HATCH[tier], FLUID_EXPORT_HATCH[targetTier], player, targetPos);
                }
                if (hatch.getAbility() == IMPORT_FLUIDS) {
                    if(meModel)trans(FLUID_IMPORT_HATCH[tier], FLUID_IMPORT_HATCH_ME, player, targetPos);
                    else trans(FLUID_IMPORT_HATCH[tier], FLUID_IMPORT_HATCH[targetTier], player, targetPos);
                }
            }
            if (mte instanceof MetaTileEntityEnergyHatch hatch) {
                ItemStack hatchStack = hatch.getStackForm(1);
                if (hatch.getAbility() == OUTPUT_ENERGY) {
                    if (MetaTileEntities.ENERGY_OUTPUT_HATCH[tier].getStackForm(1).getItem() == hatchStack.getItem() && MetaTileEntities.ENERGY_OUTPUT_HATCH[tier].getStackForm(1).getMetadata() == hatchStack.getMetadata())
                        trans(ENERGY_OUTPUT_HATCH[tier], ENERGY_OUTPUT_HATCH[targetTier], player, targetPos);

                    if (MetaTileEntities.ENERGY_OUTPUT_HATCH_4A[tier].getStackForm(1).getItem() == hatchStack.getItem() && MetaTileEntities.ENERGY_OUTPUT_HATCH_4A[tier].getStackForm(1).getMetadata() == hatchStack.getMetadata())
                        trans(ENERGY_OUTPUT_HATCH_4A[tier], ENERGY_OUTPUT_HATCH_4A[targetTier], player, targetPos);

                    if (MetaTileEntities.ENERGY_OUTPUT_HATCH_16A[tier].getStackForm(1).getItem() == hatchStack.getItem() && MetaTileEntities.ENERGY_OUTPUT_HATCH_16A[tier].getStackForm(1).getMetadata() == hatchStack.getMetadata())
                        trans(ENERGY_OUTPUT_HATCH_16A[tier], ENERGY_OUTPUT_HATCH_16A[targetTier], player, targetPos);
                }
                if (hatch.getAbility() == INPUT_ENERGY) {
                    if (MetaTileEntities.ENERGY_INPUT_HATCH[tier].getStackForm(1).getItem() == hatchStack.getItem() && MetaTileEntities.ENERGY_INPUT_HATCH[tier].getStackForm(1).getMetadata() == hatchStack.getMetadata())
                        trans(ENERGY_INPUT_HATCH[tier], ENERGY_INPUT_HATCH[targetTier], player, targetPos);

                    if (MetaTileEntities.ENERGY_INPUT_HATCH_4A[tier].getStackForm(1).getItem() == hatchStack.getItem() && MetaTileEntities.ENERGY_INPUT_HATCH_4A[tier].getStackForm(1).getMetadata() == hatchStack.getMetadata())
                        trans(ENERGY_INPUT_HATCH_4A[tier], ENERGY_INPUT_HATCH_4A[targetTier], player, targetPos);

                    if (MetaTileEntities.ENERGY_INPUT_HATCH_16A[tier].getStackForm(1).getItem() == hatchStack.getItem() && MetaTileEntities.ENERGY_INPUT_HATCH_16A[tier].getStackForm(1).getMetadata() == hatchStack.getMetadata())
                        trans(ENERGY_INPUT_HATCH_16A[tier], ENERGY_INPUT_HATCH_16A[targetTier], player, targetPos);
                }
            }
        }
    }

    private void trans(MetaTileEntity mte, MetaTileEntity trans, EntityPlayer player, BlockPos pos) {
        if (aeModel) {
            try {
                IItemStorageChannel channel = AEApi.instance().storage().getStorageChannel(IItemStorageChannel.class);
                IMEMonitor<IAEItemStack> monitor = networkProxy.getStorage().getInventory(channel);

                if (extractItemsFromNetwork(monitor, mte.getStackForm(1), trans.getStackForm(1), player)) {
                    setTier(trans, player, pos);
                }else
                {
                    player.sendMessage(new TextComponentString("正在尝试替换"+pos+"失败！：网络数量不可用").setStyle(new Style().setColor(TextFormatting.RED)));
                }
            } catch (GridAccessException e) {
                // 网络不可用，记录日志
                GTQTLog.logger.warn("Grid access failed", e);
                player.sendMessage(new TextComponentString("正在尝试替换"+pos+"失败！：网络不可用").setStyle(new Style().setColor(TextFormatting.RED)));
            } catch (Exception e) {
                // 其他异常，记录日志
                GTQTLog.logger.warn("Unexpected error occurred", e);
                player.sendMessage(new TextComponentString("正在尝试替换"+pos+"失败！：未知错误").setStyle(new Style().setColor(TextFormatting.RED)));
            }
        } else {
            if (extractItemsFromPlayer(mte.getStackForm(1), trans.getStackForm(1), player)) {
                setTier(trans, player, pos);
            }else
            {
                player.sendMessage(new TextComponentString("正在尝试替换"+pos+"失败！：背包数量不可用").setStyle(new Style().setColor(TextFormatting.RED)));
            }
        }
    }


    private boolean extractItemsFromPlayer(ItemStack extract, ItemStack inject, EntityPlayer player) {
        if (inject == null) return false;
        // 从玩家背包中提取一个 extract 物品（数量为 1）
        if (player.inventory.hasItemStack(inject)) {
            for (int i = 9; i < player.inventory.getSizeInventory(); i++) {
                ItemStack stackInSlot = player.inventory.getStackInSlot(i);
                if (stackInSlot.isItemEqual(inject) && stackInSlot.getCount() >= 1) {
                    // 减少物品数量
                    stackInSlot.shrink(1);

                    // 向玩家背包中注入一个 inject 物品（数量为 1）
                    ItemStack injectStack = extract.copy();
                    injectStack.setCount(1);
                    if (!player.inventory.addItemStackToInventory(injectStack)) {
                        // 如果背包已满，则直接掉落物品
                        player.dropItem(injectStack, false);
                    }

                    return true;
                }
            }
        }
        return false;
    }

    private boolean extractItemsFromNetwork(IMEMonitor<IAEItemStack> monitor, ItemStack extract, ItemStack inject, EntityPlayer player) {
        if (inject == null) return false;
        // 从 ME 网络请求一个 extract 物品（数量为 1）
        IItemStorageChannel channel = AEApi.instance().storage().getStorageChannel(IItemStorageChannel.class);
        IAEItemStack request = channel.createStack(inject);
        request.setStackSize(1); // 设置请求数量为 1
        IAEItemStack extracted = monitor.extractItems(request, Actionable.MODULATE, new PlayerSource(player, null));

        // 如果成功提取到物品，则直接销毁
        if (extracted != null) {
            // 向 ME 网络发送一个 inject 物品（数量为 1）
            IAEItemStack toInject = channel.createStack(extract);
            toInject.setStackSize(1); // 设置注入数量为 1
            monitor.injectItems(toInject, Actionable.MODULATE, new PlayerSource(player, null));

            return true;
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        lines.add(I18n.format("替换仓室,支持输入仓，输出仓，输入总线，输出总线，1-16A能源仓，动力仓，维护仓，ME仓"));
        lines.add(I18n.format("ME模式下会将输入仓，输出仓，输入总线，输出总线替换为对应的ME仓"));
        lines.add(I18n.format("维护仓列表为维护仓，可配置，无菌，全自动维护仓"));
        lines.add(I18n.format("支持从网络拉取需求材料，并将替换结构返回网络"));
        if (networkProxy != null) {
            lines.add(GuiText.Linked.getLocal());
            lines.add(I18n.format("网络唯一标识符:" + networkProxy));
        } else {
            lines.add(GuiText.Unlinked.getLocal());
        }
    }
}
