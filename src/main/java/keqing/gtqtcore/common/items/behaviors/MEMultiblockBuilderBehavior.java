package keqing.gtqtcore.common.items.behaviors;

import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.channels.IItemStorageChannel;
import appeng.api.storage.data.IAEItemStack;
import appeng.core.localization.GuiText;
import appeng.me.GridAccessException;
import appeng.me.helpers.AENetworkProxy;
import appeng.me.helpers.BaseActionSource;
import appeng.tile.misc.TileSecurityStation;
import gregtech.api.GregTechAPI;
import gregtech.api.block.machines.BlockMachine;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.items.gui.ItemUIFactory;
import gregtech.api.items.gui.PlayerInventoryHolder;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.pattern.PatternError;
import gregtech.api.util.GTUtility;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.integration.jei.multiblock.MultiblockInfoRecipeWrapper;
import keqing.gtqtcore.api.utils.GTQTLog;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static gregtech.common.metatileentities.MetaTileEntities.ENERGY_INPUT_HATCH;


public class MEMultiblockBuilderBehavior implements IItemBehaviour, ItemUIFactory {

    BlockPos AEpos;

    int perItemInputHatchTier = -1;
    int perItemOutputHatchTier = -1;
    int perFluidInputHatchTier = -1;
    int perFluidOutputHatchTier = -1;
    int perEnergyInputHatchTier = -1;
    int perEnergyOutputHatchTier = -1;
    int perMufflerHatchTier = -1;
    int perMaintenanceHatchTier = -1;

    boolean ignoredItemInputHatch = false;
    boolean ignoredItemOutputHatch = false;
    boolean ignoredFluidInputHatch = false;
    boolean ignoredFluidOutputHatch = false;
    boolean ignoredEnergyInputHatch = false;
    boolean ignoredEnergyOutputHatch = false;
    boolean ignoredMufflerHatch = false;
    boolean ignoredMaintenanceHatch = false;

    boolean setHatch = true;
    boolean MEPlace = false;
    int multiblockTier = 0;
    MetaTileEntity targetMte;
    boolean placeMultiblock = false;
    int amperage = 0;
    boolean out;
    int page;

    List<ItemStack> itemStacks = new ArrayList<>();
    int endPos = GregTechAPI.isHighTier() ? ENERGY_INPUT_HATCH.length - 1 : Math.min(ENERGY_INPUT_HATCH.length - 1, 10);

    MetaTileEntity[] hatchTypes = {
            MetaTileEntities.MAINTENANCE_HATCH,
            MetaTileEntities.CONFIGURABLE_MAINTENANCE_HATCH,
            MetaTileEntities.AUTO_MAINTENANCE_HATCH,
            MetaTileEntities.CLEANING_MAINTENANCE_HATCH
    };
    int tick = 0;
    private AENetworkProxy networkProxy;

    private static List<ItemStack> getItemStacks(int tier, MultiblockControllerBase multiblock) throws NoSuchFieldException, IllegalAccessException {
        // 获取第 i 项
        Object pattern = getObjects(tier, multiblock);
        // 获取 MBPattern 类
        Class<?> mbPatternClass = pattern.getClass();
        // 获取 parts 字段
        Field partsField = mbPatternClass.getDeclaredField("parts");
        partsField.setAccessible(true);
        // 获取 parts 字段值
        return (List<ItemStack>) partsField.get(pattern);
    }

    private static Object getObjects(int tier, MultiblockControllerBase multiblock) throws NoSuchFieldException, IllegalAccessException {
        MultiblockInfoRecipeWrapper wrapper = new MultiblockInfoRecipeWrapper(multiblock);
        // 获取 patterns 字段
        Field patternsField = wrapper.getClass().getDeclaredField("patterns");
        patternsField.setAccessible(true);
        // 获取 patterns 数组值
        Object[] patterns = (Object[]) patternsField.get(wrapper);
        // 检查索引是否有效
        if (tier < 0 || tier >= patterns.length) {
            tier = patterns.length - 1;
        }
        return patterns[tier];
    }

    public List<ItemStack> dealWithMultiblock(MultiblockControllerBase multiblock) {
        List<ItemStack> itemStackList = new ArrayList<>();
        try {
            List<ItemStack> original = getItemStacks(multiblockTier, multiblock);

            for (ItemStack itemStack : original) {

                out = false;

                if (itemStack == ItemStack.EMPTY) continue;

                //忽视控制器
                if (multiblock.getStackForm().isItemEqual(itemStack)) continue;

                if (!setHatch) {
                    Block block = Block.getBlockFromItem(itemStack.getItem());
                    if (block instanceof BlockMachine) {
                        continue;
                    }
                }

                ItemStack hatchStack = itemStack.copy();
                hatchStack.setCount(1);

                if (perMaintenanceHatchTier >= 0) {
                    for (MetaTileEntity hatchType : hatchTypes) {
                        if (hatchType.getStackForm(1).getItem() == hatchStack.getItem() &&
                                hatchType.getStackForm(1).getMetadata() == hatchStack.getMetadata()) {
                            out = true;
                            if (!ignoredMaintenanceHatch) {
                                switch (perMaintenanceHatchTier) {
                                    case 0:
                                        itemStackList.add(MetaTileEntities.MAINTENANCE_HATCH.getStackForm(itemStack.getCount()));
                                        break;
                                    case 1:
                                        itemStackList.add(MetaTileEntities.CONFIGURABLE_MAINTENANCE_HATCH.getStackForm(itemStack.getCount()));
                                        break;
                                    case 2:
                                        itemStackList.add(MetaTileEntities.AUTO_MAINTENANCE_HATCH.getStackForm(itemStack.getCount()));
                                        break;
                                    case 3:
                                        itemStackList.add(MetaTileEntities.CLEANING_MAINTENANCE_HATCH.getStackForm(itemStack.getCount()));
                                        break;
                                }
                            }
                            break;
                        }
                    }
                }


                if (perMufflerHatchTier >= 1) {
                    for (int i = 1; i < MetaTileEntities.MUFFLER_HATCH.length; i++) {

                        if (MetaTileEntities.MUFFLER_HATCH[i].getStackForm(1).getItem() == hatchStack.getItem() && MetaTileEntities.MUFFLER_HATCH[i].getStackForm(1).getMetadata() == hatchStack.getMetadata()) {
                            out = true;
                            if (!ignoredMufflerHatch)
                                itemStackList.add(MetaTileEntities.MUFFLER_HATCH[perMufflerHatchTier].getStackForm(itemStack.getCount()));
                            break;
                        }
                    }
                }
                if (out) continue;

                if (perItemInputHatchTier >= 0 || MEPlace) {
                    for (int i = 0; i < MetaTileEntities.ITEM_IMPORT_BUS.length; i++) {

                        if (MetaTileEntities.ITEM_IMPORT_BUS[i].getStackForm(1).getItem() == hatchStack.getItem() && MetaTileEntities.ITEM_IMPORT_BUS[i].getStackForm(1).getMetadata() == hatchStack.getMetadata()) {
                            out = true;
                            if (!ignoredItemInputHatch) {
                                if (MEPlace)
                                    itemStackList.add(MetaTileEntities.ITEM_IMPORT_BUS_ME.getStackForm(itemStack.getCount()));
                                else
                                    itemStackList.add(MetaTileEntities.ITEM_IMPORT_BUS[perItemInputHatchTier].getStackForm(itemStack.getCount()));
                            }
                            break;
                        }
                    }
                }
                if (out) continue;

                if (perItemOutputHatchTier >= 0 || MEPlace) {
                    for (int i = 0; i < MetaTileEntities.ITEM_EXPORT_BUS.length; i++) {
                        if (MetaTileEntities.ITEM_EXPORT_BUS[i].getStackForm(1).getItem() == hatchStack.getItem() && MetaTileEntities.ITEM_EXPORT_BUS[i].getStackForm(1).getMetadata() == hatchStack.getMetadata()) {
                            out = true;
                            if (!ignoredItemOutputHatch) {
                                if (MEPlace)
                                    itemStackList.add(MetaTileEntities.ITEM_EXPORT_BUS_ME.getStackForm(itemStack.getCount()));
                                else
                                    itemStackList.add(MetaTileEntities.ITEM_EXPORT_BUS[perItemOutputHatchTier].getStackForm(itemStack.getCount()));
                            }
                            break;
                        }
                    }
                }
                if (out) continue;

                if (perFluidInputHatchTier >= 0 || MEPlace) {
                    for (int i = 0; i < MetaTileEntities.FLUID_IMPORT_HATCH.length; i++) {
                        if (MetaTileEntities.FLUID_IMPORT_HATCH[i].getStackForm(1).getItem() == hatchStack.getItem() && MetaTileEntities.FLUID_IMPORT_HATCH[i].getStackForm(1).getMetadata() == hatchStack.getMetadata()) {
                            out = true;
                            if (!ignoredFluidInputHatch) {
                                if (MEPlace)
                                    itemStackList.add(MetaTileEntities.FLUID_IMPORT_HATCH_ME.getStackForm(itemStack.getCount()));
                                else
                                    itemStackList.add(MetaTileEntities.FLUID_IMPORT_HATCH[perFluidInputHatchTier].getStackForm(itemStack.getCount()));
                            }
                            break;
                        }
                    }
                }
                if (out) continue;

                if (perFluidOutputHatchTier >= 0 || MEPlace) {
                    for (int i = 0; i < MetaTileEntities.FLUID_EXPORT_HATCH.length; i++) {
                        if (MetaTileEntities.FLUID_EXPORT_HATCH[i].getStackForm(1).getItem() == hatchStack.getItem() && MetaTileEntities.FLUID_EXPORT_HATCH[i].getStackForm(1).getMetadata() == hatchStack.getMetadata()) {
                            out = true;
                            if (!ignoredFluidOutputHatch) {
                                if (MEPlace)
                                    itemStackList.add(MetaTileEntities.FLUID_EXPORT_HATCH_ME.getStackForm(itemStack.getCount()));
                                else
                                    itemStackList.add(MetaTileEntities.FLUID_EXPORT_HATCH[perFluidOutputHatchTier].getStackForm(itemStack.getCount()));
                            }
                            break;
                        }
                    }
                }
                if (out) continue;


                if (perEnergyInputHatchTier >= 1) {
                    for (int i = 1; i < endPos; i++) {
                        if (ENERGY_INPUT_HATCH[i].getStackForm(1).getItem() == hatchStack.getItem() && ENERGY_INPUT_HATCH[i].getStackForm(1).getMetadata() == hatchStack.getMetadata()) {
                            out = true;
                            if (!ignoredEnergyInputHatch) {
                                if (amperage == 1) {
                                    itemStackList.add(ENERGY_INPUT_HATCH[perEnergyInputHatchTier].getStackForm(itemStack.getCount()));
                                }
                                if (amperage == 4) {
                                    itemStackList.add(MetaTileEntities.ENERGY_INPUT_HATCH_4A[Math.min(perEnergyInputHatchTier, MetaTileEntities.ENERGY_INPUT_HATCH_4A.length)].getStackForm(itemStack.getCount()));
                                }
                                if (amperage == 16) {
                                    itemStackList.add(MetaTileEntities.ENERGY_INPUT_HATCH_16A[Math.min(perEnergyInputHatchTier, MetaTileEntities.ENERGY_INPUT_HATCH_16A.length)].getStackForm(itemStack.getCount()));
                                }
                            }
                            break;
                        }
                    }
                }
                if (out) continue;

                if (perEnergyOutputHatchTier >= 1) {
                    for (int i = 1; i < endPos; i++) {
                        if (MetaTileEntities.ENERGY_OUTPUT_HATCH[i].getStackForm(1).getItem() == hatchStack.getItem() && MetaTileEntities.ENERGY_OUTPUT_HATCH[i].getStackForm(1).getMetadata() == hatchStack.getMetadata()) {
                            out = true;
                            if (!ignoredEnergyOutputHatch) {

                                if (amperage == 1) {
                                    itemStackList.add(MetaTileEntities.ENERGY_OUTPUT_HATCH[perEnergyOutputHatchTier].getStackForm(itemStack.getCount()));
                                }
                                if (amperage == 4) {
                                    itemStackList.add(MetaTileEntities.ENERGY_OUTPUT_HATCH_4A[Math.min(perEnergyOutputHatchTier, MetaTileEntities.ENERGY_OUTPUT_HATCH_4A.length)].getStackForm(itemStack.getCount()));
                                }
                                if (amperage == 16) {
                                    itemStackList.add(MetaTileEntities.ENERGY_INPUT_HATCH_16A[Math.min(perEnergyOutputHatchTier, MetaTileEntities.ENERGY_INPUT_HATCH_16A.length)].getStackForm(itemStack.getCount()));
                                }
                            }
                            break;
                        }
                    }
                }
                if (out) continue;

                itemStackList.add(itemStack);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return itemStackList;
    }

    @Override
    public ModularUI createUI(PlayerInventoryHolder playerInventoryHolder, EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 320, 205)
                .image(10, 6, 164, 150, GuiTextures.DISPLAY)
                .widget((new AdvancedTextWidget(15, 11, this::addDisplayText, 16777215))
                        .setMaxWidthLimit(500))

                .widget(new ClickButtonWidget(10, 160, 38, 20, I18n.format("结构升级"), clickData -> this.multiblockTier = MathHelper.clamp(multiblockTier + 1, 0, 10)))
                .widget(new ClickButtonWidget(48, 160, 38, 20, I18n.format("结构降级"), clickData -> this.multiblockTier = MathHelper.clamp(multiblockTier - 1, 0, 10)))

                .widget(new ClickButtonWidget(90, 160, 38, 20, I18n.format("IO明细"), clickData -> this.page = MathHelper.clamp(page + 1, 0, 1)))
                .widget(new ClickButtonWidget(128, 160, 38, 20, I18n.format("部件总览"), clickData -> this.page = MathHelper.clamp(page - 1, 0, 1)))

                .widget(new ClickButtonWidget(10, 180, 76, 20, I18n.format("放置模式"), clickData -> placeMultiblock = !placeMultiblock).setTooltipText("确认放置模式，将真实放置多方块"))

                .widget(new ClickButtonWidget(90, 180, 38, 20, I18n.format("IG"), clickData -> setHatch = !setHatch).setTooltipText("忽视仓室，只放置非仓室结构"))
                .widget(new ClickButtonWidget(128, 180, 38, 20, I18n.format("ME"), clickData -> MEPlace = !MEPlace).setTooltipText("将输入/输出 总线/仓 替换为对应的ME仓"))


                .widget((new AdvancedTextWidget(180, 1, this::addDisplayText1, 16777215)).setMaxWidthLimit(500))

                .widget(new ClickButtonWidget(180, 20, 38, 20, I18n.format("II +1"), clickData -> this.perItemInputHatchTier = MathHelper.clamp(perItemInputHatchTier + 1, -1, 9)))
                .widget(new ClickButtonWidget(220, 20, 38, 20, I18n.format("II -1"), clickData -> this.perItemInputHatchTier = MathHelper.clamp(perItemInputHatchTier - 1, -1, 9)))
                .widget(new ClickButtonWidget(260, 20, 48, 20, I18n.format("忽视本仓"), clickData -> ignoredItemInputHatch = !ignoredItemInputHatch))

                .widget(new ClickButtonWidget(180, 40, 38, 20, I18n.format("IO +1"), clickData -> this.perItemOutputHatchTier = MathHelper.clamp(perItemOutputHatchTier + 1, -1, 9)))
                .widget(new ClickButtonWidget(220, 40, 38, 20, I18n.format("IO -1"), clickData -> this.perItemOutputHatchTier = MathHelper.clamp(perItemOutputHatchTier - 1, -1, 9)))
                .widget(new ClickButtonWidget(260, 40, 48, 20, I18n.format("忽视本仓"), clickData -> ignoredItemOutputHatch = !ignoredItemOutputHatch))

                .widget(new ClickButtonWidget(180, 60, 38, 20, I18n.format("FI +1"), clickData -> this.perFluidInputHatchTier = MathHelper.clamp(perFluidInputHatchTier + 1, -1, 9)))
                .widget(new ClickButtonWidget(220, 60, 38, 20, I18n.format("FI -1"), clickData -> this.perFluidInputHatchTier = MathHelper.clamp(perFluidInputHatchTier - 1, -1, 9)))
                .widget(new ClickButtonWidget(260, 60, 48, 20, I18n.format("忽视本仓"), clickData -> ignoredFluidInputHatch = !ignoredFluidInputHatch))

                .widget(new ClickButtonWidget(180, 80, 38, 20, I18n.format("FO +1"), clickData -> this.perFluidOutputHatchTier = MathHelper.clamp(perFluidOutputHatchTier + 1, -1, 9)))
                .widget(new ClickButtonWidget(220, 80, 38, 20, I18n.format("FO -1"), clickData -> this.perFluidOutputHatchTier = MathHelper.clamp(perFluidOutputHatchTier - 1, -1, 9)))
                .widget(new ClickButtonWidget(260, 80, 48, 20, I18n.format("忽视本仓"), clickData -> ignoredFluidOutputHatch = !ignoredFluidOutputHatch))

                .widget(new ClickButtonWidget(180, 100, 38, 20, I18n.format("EI +1"), clickData -> this.perEnergyInputHatchTier = MathHelper.clamp(perEnergyInputHatchTier + 1, 0, endPos - 1)))
                .widget(new ClickButtonWidget(220, 100, 38, 20, I18n.format("EI -1"), clickData -> this.perEnergyInputHatchTier = MathHelper.clamp(perEnergyInputHatchTier - 1, 0, endPos - 1)))
                .widget(new ClickButtonWidget(260, 100, 48, 20, I18n.format("忽视本仓"), clickData -> ignoredEnergyInputHatch = !ignoredEnergyInputHatch))

                .widget(new ClickButtonWidget(180, 120, 38, 20, I18n.format("EO +1"), clickData -> this.perEnergyOutputHatchTier = MathHelper.clamp(perEnergyOutputHatchTier + 1, 0, endPos - 1)))
                .widget(new ClickButtonWidget(220, 120, 38, 20, I18n.format("EO -1"), clickData -> this.perEnergyOutputHatchTier = MathHelper.clamp(perEnergyOutputHatchTier - 1, 0, endPos - 1)))
                .widget(new ClickButtonWidget(260, 120, 48, 20, I18n.format("忽视本仓"), clickData -> ignoredEnergyOutputHatch = !ignoredEnergyOutputHatch))

                .widget(new ClickButtonWidget(180, 140, 38, 20, I18n.format("AP *4"), clickData -> this.amperage = MathHelper.clamp(amperage * 4, 1, 16)))
                .widget(new ClickButtonWidget(220, 140, 38, 20, I18n.format("AP /4"), clickData -> this.amperage = MathHelper.clamp(amperage / 4, 1, 16)))

                .widget(new ClickButtonWidget(180, 160, 38, 20, I18n.format("MF +1"), clickData -> this.perMufflerHatchTier = MathHelper.clamp(perMufflerHatchTier + 1, 0, 8)))
                .widget(new ClickButtonWidget(220, 160, 38, 20, I18n.format("MF -1"), clickData -> this.perMufflerHatchTier = MathHelper.clamp(perMufflerHatchTier - 1, 0, 8)))
                .widget(new ClickButtonWidget(260, 160, 48, 20, I18n.format("忽视本仓"), clickData -> ignoredMufflerHatch = !ignoredMufflerHatch))

                .widget(new ClickButtonWidget(180, 180, 38, 20, I18n.format("MA +1"), clickData -> this.perMaintenanceHatchTier = MathHelper.clamp(perMaintenanceHatchTier + 1, -1, 3)))
                .widget(new ClickButtonWidget(220, 180, 38, 20, I18n.format("MA -1"), clickData -> this.perMaintenanceHatchTier = MathHelper.clamp(perMaintenanceHatchTier - 1, -1, 3)))
                .widget(new ClickButtonWidget(260, 180, 48, 20, I18n.format("忽视本仓"), clickData -> ignoredMaintenanceHatch = !ignoredMaintenanceHatch))

                .build(playerInventoryHolder, entityPlayer);
    }

    private void addDisplayText1(List<ITextComponent> iTextComponents) {
        if (networkProxy != null) iTextComponents.add(new TextComponentTranslation("高级构建器 网络：" + networkProxy));
        else iTextComponents.add(new TextComponentTranslation("高级构建器 网络：未连接"));

        iTextComponents.add(new TextComponentTranslation("放置模式："+placeMultiblock));
    }

    private void addDisplayText(List<ITextComponent> iTextComponents) {
        if (page == 0) {
            if (targetMte != null && targetMte instanceof MultiblockControllerBase mte) {
                iTextComponents.add(new TextComponentTranslation(mte.getMetaFullName()));

                tick++;
                if (tick == 20) {
                    tick = 0;
                    itemStacks = dealWithMultiblock(mte);
                }
                if (itemStacks == null) itemStacks = dealWithMultiblock(mte);
                for (ItemStack itemStack : this.itemStacks) {
                    iTextComponents.add(new TextComponentTranslation(itemStack.getDisplayName() + " x " + itemStack.getCount()));
                }
            }
        } else {
            iTextComponents.add(new TextComponentTranslation("输入总线：" + (ignoredItemInputHatch ? "忽视" : ("设定 Tier" + perItemInputHatchTier))));
            iTextComponents.add(new TextComponentTranslation("输出总线：" + (ignoredItemOutputHatch ? "忽视" : ("设定 Tier" + perItemOutputHatchTier))));
            iTextComponents.add(new TextComponentTranslation("输入仓：" + (ignoredFluidInputHatch ? "忽视" : ("设定 Tier" + perFluidInputHatchTier))));
            iTextComponents.add(new TextComponentTranslation("输出仓：" + (ignoredFluidOutputHatch ? "忽视" : ("设定 Tier" + perFluidOutputHatchTier))));
            iTextComponents.add(new TextComponentTranslation("能源仓：" + (ignoredEnergyInputHatch ? "忽视" : ("设定 Tier" + perEnergyInputHatchTier + " *" + amperage))));
            iTextComponents.add(new TextComponentTranslation("动力仓：" + (ignoredEnergyOutputHatch ? "忽视" : ("设定 Tier" + perEnergyOutputHatchTier + " *" + amperage))));
            iTextComponents.add(new TextComponentTranslation("消声仓：" + (ignoredMufflerHatch ? "忽视" : ("设定 Tier" + perMufflerHatchTier))));
            iTextComponents.add(new TextComponentTranslation("维护仓：" + (ignoredMaintenanceHatch ? "忽视" : ("设定 Tier" + perMaintenanceHatchTier))));

        }
    }

    public void writeToNBT(NBTTagCompound compound) {
        compound.setInteger("AEposX", AEpos.getX());
        compound.setInteger("AEposY", AEpos.getY());
        compound.setInteger("AEposZ", AEpos.getZ());
    }

    public void readFromNBT(NBTTagCompound compound) {
        int x = compound.getInteger("AEposX");
        int y = compound.getInteger("AEposY");
        int z = compound.getInteger("AEposZ");
        AEpos = new BlockPos(x, y, z);
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
                if (world.isRemote) player.sendMessage(new TextComponentString("成功绑定AE网络!"));
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
                if (world.isRemote) player.sendMessage(new TextComponentString("未绑定AE网络!"));
                return EnumActionResult.FAIL;
            }

        }

        if (tileEntity instanceof IGregTechTileEntity igtte) {
            MetaTileEntity mte = igtte.getMetaTileEntity();
            if (mte instanceof MultiblockControllerBase multiblock) {
                targetMte = multiblock;
                multiblockTier = 0;

                if (!player.canPlayerEdit(pos, side, player.getHeldItem(hand))) return EnumActionResult.FAIL;
                if (world.isRemote) return EnumActionResult.SUCCESS;

                if (!placeMultiblock) return EnumActionResult.PASS;

                if (player.isSneaking()) {
                    if (!multiblock.isStructureFormed()) {
                        if(player.isCreative())
                        {
                            player.sendMessage(new TextComponentTranslation("检测到玩家处于创造模式，自动忽视库存检测，正在直接构建！").setStyle(new Style().setColor(TextFormatting.GREEN)));
                            multiblock.structurePattern.autoBuild(player, multiblock);
                            return EnumActionResult.SUCCESS;
                        }
                        try {
                            IItemStorageChannel channel = AEApi.instance().storage().getStorageChannel(IItemStorageChannel.class);
                            IMEMonitor<IAEItemStack> monitor = networkProxy.getStorage().getInventory(channel);

                            List<ItemStack> allItemStackInputs = dealWithMultiblock(multiblock);

                            for (ItemStack itemStack : allItemStackInputs) {

                                IAEItemStack aeItemStack = channel.createStack(itemStack);
                                assert aeItemStack != null;
                                extractItemsFromNetwork(monitor, aeItemStack, player);
                            }
                        } catch (GridAccessException e) {
                            // 网络不可用，记录日志
                            GTQTLog.logger.warn("Grid access failed", e);
                            player.sendMessage(new TextComponentString("请求失败！：网络不可用").setStyle(new Style().setColor(TextFormatting.RED)));
                        } catch (Exception e) {
                            // 其他异常，记录日志
                            GTQTLog.logger.warn("Unexpected error occurred", e);
                            player.sendMessage(new TextComponentString("请求失败！：未知错误").setStyle(new Style().setColor(TextFormatting.RED)));
                        }

                        multiblock.structurePattern.autoBuild(player, multiblock);

                        return EnumActionResult.SUCCESS;
                    }
                    return EnumActionResult.PASS;
                } else {
                    // If not sneaking, try to show structure debug info (if any) in chat.
                    if (!multiblock.isStructureFormed()) {
                        PatternError error = multiblock.structurePattern.getError();
                        if (error != null) {
                            player.sendMessage(new TextComponentTranslation("gregtech.multiblock.pattern.error_message_header"));
                            player.sendMessage(new TextComponentString(error.getErrorInfo()));
                            return EnumActionResult.SUCCESS;
                        }
                    }
                    player.sendMessage(new TextComponentTranslation("gregtech.multiblock.pattern.no_errors").setStyle(new Style().setColor(TextFormatting.GREEN)));
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        if (player.isSneaking()) {
            if (!world.isRemote) {
                PlayerInventoryHolder holder = new PlayerInventoryHolder(player, hand);
                holder.openUI();
            }
        }
        return EnumActionResult.SUCCESS;
    }

    private void extractItemsFromNetwork(IMEMonitor<IAEItemStack> monitor, IAEItemStack itemStack, EntityPlayer player) {
        // 创建原型物品堆用于比较
        final ItemStack prototype = itemStack.createItemStack();
        player.sendMessage(new TextComponentTranslation("------------------"));
        player.sendMessage(new TextComponentTranslation("正在尝试获取 " + prototype.getDisplayName() + " x " + prototype.getCount()));

        // 计算玩家背包中已有该物品的数量
        long playerExistingCount = 0;
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            final ItemStack slotStack = player.inventory.getStackInSlot(i);
            if (!slotStack.isEmpty() &&
                    ItemStack.areItemsEqual(slotStack, prototype) &&
                    ItemStack.areItemStackTagsEqual(slotStack, prototype)) {
                playerExistingCount += slotStack.getCount();
            }
        }

        //多方块实际需求
        final long require = itemStack.getStackSize();

        //如果玩家拥有的比需求的多就不请求
        if (require <= playerExistingCount) {
            player.sendMessage(new TextComponentTranslation("玩家背包缓存 " + playerExistingCount + " 可用,取消网络请求。").setStyle(new Style().setColor(TextFormatting.GREEN)));
            player.sendMessage(new TextComponentTranslation("------------------"));
            return;
        }

        // 计算网络可用数量
        final IAEItemStack networkStack = monitor.getStorageList().findPrecise(itemStack);
        final long networkAvailable = networkStack != null ? networkStack.getStackSize() : 0;

        if (networkAvailable == 0) {
            player.sendMessage(new TextComponentTranslation("网络请求 " + (require - playerExistingCount) + " 缓存不足不足,取消网络请求！").setStyle(new Style().setColor(TextFormatting.RED)));
            player.sendMessage(new TextComponentTranslation("------------------"));
            return;
        }
        player.sendMessage(new TextComponentTranslation("玩家背包可用 " + playerExistingCount));
        player.sendMessage(new TextComponentTranslation("网络请求 " + (require - playerExistingCount) + " 合法,开始请求。"));

        //如果玩家没有应该请求 require-playerExistingCount (差的)个
        //网络内有networkAvailable个 是否满足需求？
        final long requireFromNet = Math.min(networkAvailable, require - playerExistingCount);

        //需要下单合成不足的吗
        long crafting = (require - playerExistingCount) - networkAvailable;

        //如果确实需要下单
        if (crafting > 0) {
            //向网络发送合成 itemStack.copy().setStackSize(crafting)
            player.sendMessage(new TextComponentTranslation("网络缺失 " + crafting).setStyle(new Style().setColor(TextFormatting.RED)));
        }
        player.sendMessage(new TextComponentTranslation("实际向网络请求 " + requireFromNet).setStyle(new Style().setColor(TextFormatting.YELLOW)));
        player.sendMessage(new TextComponentTranslation("------------------"));
        //如果无需下单，请求 require-playerExistingCount

        // 创建提取请求
        final IAEItemStack request = itemStack.copy().setStackSize(requireFromNet);

        // 模拟提取
        final IAEItemStack simulated = monitor.extractItems(request, Actionable.SIMULATE, getActionSource());

        if (simulated != null && simulated.getStackSize() > 0) {
            // 实际提取物品
            final IAEItemStack extracted = monitor.extractItems(simulated.copy(), Actionable.MODULATE, getActionSource());

            if (extracted != null) {
                ItemStack physicalStack = extracted.createItemStack();
                final int maxStackSize = physicalStack.getMaxStackSize();

                // 持续尝试填充背包直到完成或背包满
                boolean hasSpace;
                do {
                    hasSpace = false;
                    for (int i = 0; i < player.inventory.getSizeInventory() && physicalStack.getCount() > 0; i++) {
                        final ItemStack slotStack = player.inventory.getStackInSlot(i);

                        // 处理空槽位
                        if (slotStack.isEmpty()) {
                            final int toTransfer = Math.min(physicalStack.getCount(), maxStackSize);
                            final ItemStack newStack = physicalStack.copy();
                            newStack.setCount(toTransfer);
                            player.inventory.setInventorySlotContents(i, newStack);
                            physicalStack.shrink(toTransfer);
                            hasSpace = true;
                        }
                        // 处理可堆叠槽位
                        else if (ItemStack.areItemsEqual(slotStack, physicalStack) &&
                                ItemStack.areItemStackTagsEqual(slotStack, physicalStack) &&
                                slotStack.getCount() < maxStackSize) {
                            final int canAdd = maxStackSize - slotStack.getCount();
                            final int toAdd = Math.min(canAdd, physicalStack.getCount());
                            slotStack.setCount(slotStack.getCount() + toAdd);
                            physicalStack.shrink(toAdd);
                            hasSpace = true;
                        }
                    }
                } while (physicalStack.getCount() > 0 && hasSpace);
            }
        }
    }

    private IActionSource getActionSource() {
        return new BaseActionSource();
    }

    @Override
    public void addPropertyOverride(Item item) {
        item.addPropertyOverride(GTUtility.gregtechId("auto_mode"),
                (stack, world, entity) -> (entity != null && entity.isSneaking()) ? 1.0F : 0.0F);
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        lines.add(I18n.format("metaitem.tool.multiblock_builder.tooltip2"));
        lines.add(I18n.format("可在UI内调整多方块的各项仓口参数配置，支持各仓开关，升级，调A，ME模式等功能"));
        lines.add(I18n.format("如果多方块太大材料过多可能导致放置失败，请酌情使用"));
        lines.add(I18n.format("支持从网络拉取需求材料"));
        if (networkProxy != null) {
            lines.add(GuiText.Linked.getLocal());
            lines.add(I18n.format("网络唯一标识符:" + networkProxy));
        } else {
            lines.add(GuiText.Unlinked.getLocal());
        }
    }
}