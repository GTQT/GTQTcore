package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.ConfigHolder;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTPowerSupply;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static gregtech.api.GTValues.V;
import static gregtech.api.GTValues.VA;
import static keqing.gtqtcore.api.GTQTAPI.MAP_PA_CASING;
import static keqing.gtqtcore.api.GTQTAPI.MAP_PS_CASING;
import static keqing.gtqtcore.common.block.blocks.GTQTPowerSupply.SupplyType.*;

public class MetaTileEntityPowerSupply extends MultiblockWithDisplayBase {
    int tier = 10;//基础电量缓存等级
    int time;
    int eu = 0;
    int maxTier = 0;
    int updatetime = 1;
    int t = 0;
    boolean work;
    boolean charge = true;//供能模式
    boolean network;//互联模式
    boolean fastCharge;//闪充模式
    BlockPos poss1;
    BlockPos poss2;
    BlockPos poss3;
    BlockPos poss4;
    BlockPos poss5;
    BlockPos poss6;
    BlockPos poss7;
    BlockPos poss8;
    private IEnergyContainer inenergyContainer;
    private IEnergyContainer outenergyContainer;

    public MetaTileEntityPowerSupply(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("eu", eu);
        data.setInteger("tier", tier);
        data.setInteger("maxTier", maxTier);
        data.setInteger("updatetime", updatetime);
        data.setBoolean("work", work);

        data.setBoolean("charge", charge);
        data.setBoolean("network", network);
        data.setBoolean("fastCharge", fastCharge);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        eu = data.getInteger("eu");
        tier = data.getInteger("tier");
        maxTier = data.getInteger("maxTier");
        updatetime = data.getInteger("updatetime");
        work = data.getBoolean("work");

        charge = data.getBoolean("charge");
        network = data.getBoolean("network");
        fastCharge = data.getBoolean("fastCharge");
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentTranslation("供能：%s 互联：%s 闪充：%s", charge, network, fastCharge));
        textList.add(new TextComponentTranslation("状态：%s 检测频率：%s", work, updatetime));
        textList.add(new TextComponentTranslation("蓄能：%s / %s EU", eu, tier * 2048));

    }

    protected void ventInfo(List<ITextComponent> textList) {
        if (inenergyContainer != null)
            textList.add(new TextComponentTranslation("能源仓缓存：%s  EU", inenergyContainer.getEnergyStored()));
        if (inenergyContainer != null)
            textList.add(new TextComponentTranslation("能源仓蓄能：%s  EU", inenergyContainer.getEnergyCapacity()));

        if (outenergyContainer != null)
            textList.add(new TextComponentTranslation("动力仓缓存：%s  EU", outenergyContainer.getEnergyStored()));
        if (outenergyContainer != null)
            textList.add(new TextComponentTranslation("动力仓蓄能：%s  EU", outenergyContainer.getEnergyCapacity()));
        if (!work)
            textList.add(TextComponentUtil.translationWithColor(TextFormatting.RED, "模块填充错误！请填充完毕后刷新加载！"));

    }

    protected void addInfo1(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .addCustom(tl -> {
                    if (isStructureFormed() && network) {
                        if (checkNetwork(poss1, false)) {
                            if (checkNet(poss1)) {
                                textList.add(new TextComponentTranslation("连接：%s %s %s", poss1.getX(), poss1.getY(), poss1.getZ()));
                                textList.add(new TextComponentTranslation("EU:%s / %s", getNetEu(poss1), getNetMax(poss1)));
                            } else
                                textList.add(TextComponentUtil.translationWithColor(TextFormatting.RED, "访问被拒！"));
                        }
                    }

                });
    }

    protected void addInfo2(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .addCustom(tl -> {
                    if (isStructureFormed() && network) {
                        if (checkNetwork(poss2, false)) {
                            if (checkNet(poss2)) {
                                textList.add(new TextComponentTranslation("连接：%s %s %s", poss2.getX(), poss2.getY(), poss2.getZ()));
                                textList.add(new TextComponentTranslation("EU:%s / %s", getNetEu(poss2), getNetMax(poss2)));
                            } else
                                textList.add(TextComponentUtil.translationWithColor(TextFormatting.RED, "访问被拒！"));
                        }
                    }

                });
    }

    protected void addInfo3(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .addCustom(tl -> {
                    if (isStructureFormed() && network) {
                        if (checkNetwork(poss3, false)) {
                            if (checkNet(poss3)) {
                                textList.add(new TextComponentTranslation("连接：%s %s %s", poss3.getX(), poss3.getY(), poss3.getZ()));
                                textList.add(new TextComponentTranslation("EU:%s / %s", getNetEu(poss3), getNetMax(poss3)));
                            } else
                                textList.add(TextComponentUtil.translationWithColor(TextFormatting.RED, "访问被拒！"));
                        }
                    }

                });
    }

    protected void addInfo4(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .addCustom(tl -> {
                    if (isStructureFormed() && network) {
                        if (checkNetwork(poss4, false)) {
                            if (checkNet(poss4)) {
                                textList.add(new TextComponentTranslation("连接：%s %s %s", poss4.getX(), poss4.getY(), poss4.getZ()));
                                textList.add(new TextComponentTranslation("EU:%s / %s", getNetEu(poss4), getNetMax(poss4)));
                            } else
                                textList.add(TextComponentUtil.translationWithColor(TextFormatting.RED, "访问被拒！"));
                        }
                    }

                });
    }

    protected void addInfo5(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .addCustom(tl -> {
                    if (isStructureFormed() && network) {
                        if (checkNetwork(poss5, false)) {
                            if (checkNet(poss5)) {
                                textList.add(new TextComponentTranslation("连接：%s %s %s", poss5.getX(), poss5.getY(), poss5.getZ()));
                                textList.add(new TextComponentTranslation("EU:%s / %s", getNetEu(poss5), getNetMax(poss5)));
                            } else
                                textList.add(TextComponentUtil.translationWithColor(TextFormatting.RED, "访问被拒！"));
                        }
                    }

                });
    }

    protected void addInfo6(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .addCustom(tl -> {
                    if (isStructureFormed() && network) {
                        if (checkNetwork(poss6, false)) {
                            if (checkNet(poss6)) {
                                textList.add(new TextComponentTranslation("连接：%s %s %s", poss6.getX(), poss6.getY(), poss6.getZ()));
                                textList.add(new TextComponentTranslation("EU:%s / %s", getNetEu(poss6), getNetMax(poss6)));
                            } else
                                textList.add(TextComponentUtil.translationWithColor(TextFormatting.RED, "访问被拒！"));
                        }
                    }

                });
    }

    protected void addInfo7(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .addCustom(tl -> {
                    if (isStructureFormed() && network) {
                        if (checkNetwork(poss7, false)) {
                            if (checkNet(poss7)) {
                                textList.add(new TextComponentTranslation("连接：%s %s %s", poss7.getX(), poss7.getY(), poss7.getZ()));
                                textList.add(new TextComponentTranslation("EU:%s / %s", getNetEu(poss7), getNetMax(poss7)));
                            } else
                                textList.add(TextComponentUtil.translationWithColor(TextFormatting.RED, "访问被拒！"));
                        }
                    }

                });
    }

    protected void addInfo8(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .addCustom(tl -> {
                    if (isStructureFormed() && network) {
                        if (checkNetwork(poss8, false)) {
                            if (checkNet(poss8)) {
                                textList.add(new TextComponentTranslation("连接：%s %s %s", poss8.getX(), poss8.getY(), poss8.getZ()));
                                textList.add(new TextComponentTranslation("EU:%s / %s", getNetEu(poss8), getNetMax(poss8)));
                            } else
                                textList.add(TextComponentUtil.translationWithColor(TextFormatting.RED, "访问被拒！"));
                        }
                    }

                });
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 260, 240);

        int j = 0;
        //1号
        builder.image(3, 0, 90, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 4, this::addInfo1, 16777215)).setMaxWidthLimit(90).setClickHandler(this::handleDisplayClick));
        j++;
        //2号
        builder.image(3, j * 30, 90, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, j * 30 + 4, this::addInfo2, 16777215)).setMaxWidthLimit(90).setClickHandler(this::handleDisplayClick));
        j++;
        //3号
        builder.image(3, j * 30, 90, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, j * 30 + 4, this::addInfo3, 16777215)).setMaxWidthLimit(90).setClickHandler(this::handleDisplayClick));
        j++;
        //4号
        builder.image(3, j * 30, 90, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, j * 30 + 4, this::addInfo4, 16777215)).setMaxWidthLimit(90).setClickHandler(this::handleDisplayClick));
        j++;
        //5号
        builder.image(3, j * 30, 90, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, j * 30 + 4, this::addInfo5, 16777215)).setMaxWidthLimit(90).setClickHandler(this::handleDisplayClick));
        j++;
        //6号
        builder.image(3, j * 30, 90, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, j * 30 + 4, this::addInfo6, 16777215)).setMaxWidthLimit(90).setClickHandler(this::handleDisplayClick));
        j++;
        //7号
        builder.image(3, j * 30, 90, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, j * 30 + 4, this::addInfo7, 16777215)).setMaxWidthLimit(90).setClickHandler(this::handleDisplayClick));
        j++;
        //8号
        builder.image(3, j * 30, 90, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, j * 30 + 4, this::addInfo8, 16777215)).setMaxWidthLimit(90).setClickHandler(this::handleDisplayClick));


        builder.image(92, 4, 162, 50, GuiTextures.DISPLAY);
        builder.dynamicLabel(95, 8, () -> "超导矩阵管理中心", 0xFFFFFF);
        builder.widget((new AdvancedTextWidget(95, 20, this::addDisplayText, 16777215)).setMaxWidthLimit(162).setClickHandler(this::handleDisplayClick));

        //UI
        builder.image(92, 54, 162, 62, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(95, 58, this::ventInfo, 16777215)).setMaxWidthLimit(162).setClickHandler(this::handleDisplayClick));

        builder.widget(new ClickButtonWidget(132 - 32, 120, 48, 18, "充能模式", data -> charge = !charge).setTooltipText("切换供能模式与发电模式"));
        builder.widget(new ClickButtonWidget(180 - 32, 120, 48, 18, "互联模式", data -> network = !network).setTooltipText("开启后将允许九宫格内的超导矩阵组网"));
        builder.widget(new ClickButtonWidget(228 - 32, 120, 48, 18, "闪充模式", data -> fastCharge = !fastCharge).setTooltipText("开启后对低于模块等级的设备瞬间完成供能"));

        builder.widget(new ClickButtonWidget(132 - 32, 140, 24, 18, "+1", data -> this.updatetime = MathHelper.clamp(updatetime + 1, 1, 40)));
        builder.widget(new ClickButtonWidget(156 - 32, 140, 24, 18, "+5", data -> this.updatetime = MathHelper.clamp(updatetime + 5, 1, 40)));
        builder.widget(new ClickButtonWidget(180 - 32, 140, 24, 18, "-1", data -> this.updatetime = MathHelper.clamp(updatetime - 1, 1, 40)));
        builder.widget(new ClickButtonWidget(204 - 32, 140, 24, 18, "-5", data -> this.updatetime = MathHelper.clamp(updatetime - 5, 1, 40)));

        builder.widget(new ClickButtonWidget(228 - 32, 140, 48, 18, "检测刷新", data -> {
            tier = 0;
            PreCheck();
        }).setTooltipText("重载所有模块信息"));


        builder.widget((new ProgressWidget(() -> (double) eu / (tier * 2048), 92, 53, 162, 4, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, eu, tier * 2048L)));

        builder.widget((new ProgressWidget(() -> getNetMax(poss1) == 0 ? 0 : (double) getNetEu(poss1) / getNetMax(poss1), 3, 28, 88, 3, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, getNetEu(poss1), getNetMax(poss1))));
        builder.widget((new ProgressWidget(() -> getNetMax(poss2) == 0 ? 0 : (double) getNetEu(poss2) / getNetMax(poss2), 3, 58, 88, 3, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, getNetEu(poss2), getNetMax(poss2))));
        builder.widget((new ProgressWidget(() -> getNetMax(poss3) == 0 ? 0 : (double) getNetEu(poss3) / getNetMax(poss3), 3, 88, 88, 3, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, getNetEu(poss3), getNetMax(poss3))));
        builder.widget((new ProgressWidget(() -> getNetMax(poss4) == 0 ? 0 : (double) getNetEu(poss4) / getNetMax(poss4), 3, 118, 88, 3, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, getNetEu(poss4), getNetMax(poss4))));
        builder.widget((new ProgressWidget(() -> getNetMax(poss5) == 0 ? 0 : (double) getNetEu(poss5) / getNetMax(poss5), 3, 148, 88, 3, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, getNetEu(poss5), getNetMax(poss5))));
        builder.widget((new ProgressWidget(() -> getNetMax(poss6) == 0 ? 0 : (double) getNetEu(poss6) / getNetMax(poss6), 3, 178, 88, 3, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, getNetEu(poss6), getNetMax(poss6))));
        builder.widget((new ProgressWidget(() -> getNetMax(poss7) == 0 ? 0 : (double) getNetEu(poss7) / getNetMax(poss7), 3, 208, 88, 3, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, getNetEu(poss7), getNetMax(poss7))));
        builder.widget((new ProgressWidget(() -> getNetMax(poss8) == 0 ? 0 : (double) getNetEu(poss8) / getNetMax(poss8), 3, 238, 88, 3, GuiTextures.PROGRESS_BAR_MULTI_ENERGY_YELLOW, ProgressWidget.MoveType.HORIZONTAL)).setHoverTextConsumer((list) -> addBarHoverText(list, getNetEu(poss8), getNetMax(poss8))));

        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 92, 160);
        return builder;
    }

    public void addBarHoverText(List<ITextComponent> hoverList, long a, long b) {
        ITextComponent cwutInfo = TextComponentUtil.stringWithColor(
                TextFormatting.AQUA,
                a + " / " + b + " EU");
        hoverList.add(TextComponentUtil.translationWithColor(
                TextFormatting.GRAY,
                "存储电量: %s",
                cwutInfo));
    }

    @Override
    protected void updateFormedValid() {
        final int xDir = this.getFrontFacing().getOpposite().getXOffset() * 5;
        final int zDir = this.getFrontFacing().getOpposite().getZOffset() * 5;

        final int ENERGY_CAPACITY_MAX = 2048;

        // 定义常量
        final int UPDATE_INTERVAL = 200;
        final int ENERGY_INCREMENT = 5;

        // 更新计数器
        time++;
        if (time == UPDATE_INTERVAL) {
            PreCheck();
            time = 0;
        }

        // 更新频率
        if (getOffsetTimer()%updatetime==0) {

            for (int i = 1; i <= 8; i++) {
                BlockPos pos = switch (i) {
                    case 1 -> poss1;
                    case 2 -> poss2;
                    case 3 -> poss3;
                    case 4 -> poss4;
                    case 5 -> poss5;
                    case 6 -> poss6;
                    case 7 -> poss7;
                    case 8 -> poss8;
                    default -> throw new IllegalArgumentException("Invalid position index: " + i);
                };
                checkNetwork(pos, network);
            }

            if (charge) {
                if (this.inenergyContainer != null && this.inenergyContainer.getEnergyStored() > 0 && eu < tier * ENERGY_CAPACITY_MAX) {
                    long energyToAdd = Math.min(this.inenergyContainer.getEnergyStored(), (long) tier * ENERGY_CAPACITY_MAX - eu);
                    eu += (int) energyToAdd;
                    this.inenergyContainer.removeEnergy(energyToAdd);
                }
            } else {
                if (this.outenergyContainer != null && this.outenergyContainer.getEnergyStored() < this.outenergyContainer.getEnergyCapacity()) {
                    long energyToTransfer = Math.min(eu, this.outenergyContainer.getEnergyCapacity() - this.outenergyContainer.getEnergyStored());
                    eu -= (int) energyToTransfer;
                    this.outenergyContainer.addEnergy(energyToTransfer);
                }
            }

            if (work) {
                for (int i = -ENERGY_INCREMENT; i <= ENERGY_INCREMENT; ++i) {
                    for (int j = -ENERGY_INCREMENT; j <= ENERGY_INCREMENT; ++j) {

                        BlockPos pos = this.getPos().add(xDir + i, 0, zDir + j);
                        int tier = GetTier(pos, i, j);
                        if (tier!= 11 &&tier!= 0) {
                            MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), pos.add(0, 1, 0));
                            if (mte instanceof MetaTileEntity) {
                                long energyLimit = V[tier];
                                for (EnumFacing facing : EnumFacing.VALUES) {
                                    if (mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing) instanceof IEnergyContainer) {
                                        IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);
                                        assert container != null;
                                        if (charge) {
                                            if (!fastCharge) {
                                                if (container.getEnergyStored() < container.getEnergyCapacity() && eu > energyLimit) {
                                                    long energyToAdd = Math.min(container.getEnergyCapacity() - container.getEnergyStored(), eu);
                                                    container.addEnergy(energyToAdd);
                                                    eu -= (int) energyToAdd;
                                                }
                                            } else {
                                                if (container.getEnergyStored() < container.getEnergyCapacity()) {
                                                    long energyToAdd = Math.min(container.getEnergyCapacity() - container.getEnergyStored(), eu);
                                                    container.addEnergy(energyToAdd);
                                                    eu -= (int) energyToAdd;
                                                }
                                            }
                                        } else {
                                            if (container.getEnergyStored() > 0 && eu < tier * ENERGY_CAPACITY_MAX) {
                                                long energyToAdd = container.getEnergyStored();
                                                eu += (int) energyToAdd;
                                                container.removeEnergy(energyToAdd);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("谁还需要地板", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.4"));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.5"));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.6"));
        tooltip.add(I18n.format("gtqtcore.machine.powersupply.tooltip.7"));
    }

    @Override
    public boolean isMultiblockPartWeatherResistant(@Nonnull IMultiblockPart part) {
        return true;
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (!work) {
            textList.add(new TextComponentTranslation("结构错误"));
        }
    }

    public void PreCheck() {
        maxTier = 0;
        tier = 10; // 送你的
        final int xDir = this.getFrontFacing().getOpposite().getXOffset() * 5;
        final int zDir = this.getFrontFacing().getOpposite().getZOffset() * 5;

        for (int i = -4; i <= 4; ++i) {
            for (int j = -4; j <= 4; ++j) { // 判断中心区域 外围无需判断
                BlockPos poss = this.getPos().add(xDir + i, 0, zDir + j);
                addTierFromBlock(poss, POWER_SUPPLY_BATTERY_I, 20);
                addTierFromBlock(poss, POWER_SUPPLY_BATTERY_II, 80);
                addTierFromBlock(poss, POWER_SUPPLY_BATTERY_III, 320);
                addTierFromBlock(poss, POWER_SUPPLY_BATTERY_IV, 1280);
                addTierFromBlock(poss, POWER_SUPPLY_BATTERY_V, 5120);

                if (GetTier(poss, i, j) > maxTier && GetTier(poss, i, j) != 11) maxTier = GetTier(poss, i, j);
                if (GetTier(poss, i, j) == 11) {
                    work = false;
                    return;
                }
            }
        }

        work = true;

        poss1 = this.getPos().add(+11, 0, +11);
        poss2 = this.getPos().add(+11, 0, 0);
        poss3 = this.getPos().add(+11, 0, -11);

        poss4 = this.getPos().add(0, 0, +11);
        poss5 = this.getPos().add(0, 0, -11);

        poss6 = this.getPos().add(-11, 0, +11);
        poss7 = this.getPos().add(-11, 0, 0);
        poss8 = this.getPos().add(-11, 0, -11);
    }

    private void addTierFromBlock(BlockPos poss, GTQTPowerSupply.SupplyType blockType, int value) {
        if (this.getWorld().getBlockState(poss) == GTQTMetaBlocks.POWER.getState(blockType)) {
            tier += value;
        }
    }

    public int GetTier(BlockPos poss, int i, int j) {
        // 边界条件处理
        if (i == -5 || i == 5 || j == -5 || j == 5) {
            return maxTier; // 框架方块
        }

        // 获取当前方块状态
        IBlockState blockState = this.getWorld().getBlockState(poss);

        // 判断是否为框架方块
        if (isPowerSupplyBasic(blockState) || isBatteryPowerSupply(blockState)) {
            return 0;
        }

        // 判断是否为超导方块
        if (isPowerSupply(blockState)) {
            return getPowerSupplyTier(blockState);
        }

        return 11; // 不认识就开摆
    }

    private boolean isPowerSupplyBasic(IBlockState blockState) {
        return blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BASIC);
    }

    private boolean isBatteryPowerSupply(IBlockState blockState) {
        return blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_I)
                || blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_II)
                || blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_III)
                || blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_IV)
                || blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_V);
    }

    private boolean isPowerSupply(IBlockState blockState) {
        return blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_I)
                || blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_II)
                || blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_III)
                || blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_IV)
                || blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_V)
                || blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_VI)
                || blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_VII)
                || blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_VIII)
                || blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_IVV)
                || blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_VV);
    }

    private int getPowerSupplyTier(IBlockState blockState) {
        if (blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_I)) return 1;
        if (blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_II)) return 2;
        if (blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_III)) return 3;
        if (blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_IV)) return 4;
        if (blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_V)) return 5;
        if (blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_VI)) return 6;
        if (blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_VII)) return 7;
        if (blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_VIII)) return 8;
        if (blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_IVV)) return 9;
        if (blockState == GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_VV)) return 10;

        return 11; // 不认识就开摆
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPowerSupply(metaTileEntityId);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCCCCCCCCC")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("CCCCCSCCCCC")
                .where('S', selfPredicate())
                .where(' ', any())
                .where('C', states(getCasingAState())
                        .or(abilities(MultiblockAbility.OUTPUT_ENERGY).setMaxLayerLimited(4).setMinGlobalLimited(0))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMaxLayerLimited(4).setMinGlobalLimited(0)))
                .build();
    }
    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder;
        builder = MultiblockShapeInfo.builder()
                .aisle("CCCCCCCCCCC")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("C         C")
                .aisle("CCCCISOCCCC")
                .where('S', GTQTMetaTileEntities.POWER_SUPPLY, EnumFacing.SOUTH)
                .where('C', getCasingAState())
                .where('I', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.LV], EnumFacing.SOUTH)
                .where('O', MetaTileEntities.ENERGY_OUTPUT_HATCH[GTValues.LV], EnumFacing.SOUTH);
        MultiblockShapeInfo.Builder finalBuilder = builder;
        MAP_PS_CASING.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> ((WrappedIntTired) entry.getValue()).getIntTier()))
                .forEach(entry -> shapeInfo.add(finalBuilder.where(' ', entry.getKey()).build()));
        return shapeInfo;
    }

    private IBlockState getCasingAState() {
        return GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BASIC);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.IRIDIUM_CASING;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        PreCheck();//成型的时候刷新一遍区块
        initializeAbilities();
    }

    public boolean checkNetwork(BlockPos poss, boolean work) {
        // 确保 this.getWorld() 不为空
        World world = this.getWorld();
        if (world == null) {
            return false;
        }

        MetaTileEntity mte = GTUtility.getMetaTileEntity(world, poss);
        if (mte instanceof MetaTileEntityPowerSupply powerSupply) { // 安全地进行类型转换
            if (work && powerSupply.isNetwork() && powerSupply.isWork()) {
                if (eu < powerSupply.getEu() && eu < getMax() && eu >= 0 && powerSupply.getEu() >= 0) {
                    if (powerSupply.getEu() > getMax() * 2) {
                        powerSupply.removeEu(getMax() - eu);
                        eu = getMax();
                    } else {
                        // 确保 maxTier 在有效范围内
                        if (maxTier >= 0 && maxTier < VA.length) {
                            powerSupply.removeEu(VA[maxTier]);
                            eu += VA[maxTier];
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean checkNet(BlockPos poss) {
        World world = this.getWorld();
        if (world == null) {
            throw new IllegalStateException("World is null");
        }
        MetaTileEntity mte = GTUtility.getMetaTileEntity(world, poss);
        if (mte instanceof MetaTileEntityPowerSupply) {
            return ((MetaTileEntityPowerSupply) mte).network;
        }
        return false;
    }

    private int getNetworkValue(BlockPos poss, Function<MetaTileEntityPowerSupply, Integer> valueFunction) {
        if (!network || !work) return 0;
        World world = this.getWorld();
        if (world == null) {
            throw new IllegalStateException("World is null");
        }
        MetaTileEntity mte = GTUtility.getMetaTileEntity(world, poss);
        if (mte instanceof MetaTileEntityPowerSupply powerSupply) {
            if (powerSupply.isNetwork()) {
                return valueFunction.apply(powerSupply);
            }
        }
        return 0;
    }

    public int getNetMax(BlockPos poss) {
        return getNetworkValue(poss, MetaTileEntityPowerSupply::getMax);
    }

    public int getNetEu(BlockPos poss) {
        return getNetworkValue(poss, MetaTileEntityPowerSupply::getEu);
    }
    //初始化能源仓室
    public int getEu() {
        return this.eu;
    }

    public void setEu(int set) {
        if (set < tier * 2048) eu = set;
    }

    public void removeEu(int remove) {
        if (eu > remove) eu -= remove;
        else eu = 0;
    }

    public int getMax() {
        return tier * 2048;
    }

    public boolean isNetwork() {
        return network;
    }

    public boolean isWork() {
        return work;
    }

    private void initializeAbilities() {
        this.inenergyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
        this.outenergyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.OUTPUT_ENERGY));

    }

    private void resetTileAbilities() {
        this.inenergyContainer = new EnergyContainerList(new ArrayList<>());
        this.outenergyContainer = new EnergyContainerList(new ArrayList<>());
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();

        resetTileAbilities();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), true,
                isStructureFormed());
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }
}
