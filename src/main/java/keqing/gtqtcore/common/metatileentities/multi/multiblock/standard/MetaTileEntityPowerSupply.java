package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
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
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
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
import java.util.List;

import static gregtech.api.GTValues.VA;
import static keqing.gtqtcore.common.block.blocks.GTQTPowerSupply.SupplyType.*;

public class  MetaTileEntityPowerSupply extends MultiblockWithDisplayBase  {
    int tier=10;//基础电量缓存等级
    int time;
    int eu=0;
    int maxTier=0;
    int updatetime=1;
    int t=0;
    private IEnergyContainer inenergyContainer;
    private IEnergyContainer outenergyContainer;
    boolean work;
    boolean charge=true;//供能模式
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

        time++;
        if(time==200)
        {
            PreCheck();
            time=0;
        }


        if(updatetime==0)updatetime=1;
        //更新频率

        t++;
        if(t==updatetime)
        {
            t=0;
            checkNetwork(poss1,network);
            checkNetwork(poss2,network);
            checkNetwork(poss3,network);
            checkNetwork(poss4,network);
            checkNetwork(poss5,network);
            checkNetwork(poss6,network);
            checkNetwork(poss7,network);
            checkNetwork(poss8,network);

            if (charge) {
                if (this.inenergyContainer != null && this.inenergyContainer.getEnergyStored() > 0 && eu < tier * 2048) {
                    eu = eu + Math.max((int) this.inenergyContainer.getEnergyStored(), 0);
                    this.inenergyContainer.removeEnergy(this.inenergyContainer.getEnergyStored());
                }
            } else {
                if (this.outenergyContainer != null && this.outenergyContainer.getEnergyStored() < this.outenergyContainer.getEnergyCapacity()) {
                    if (eu >= (this.outenergyContainer.getEnergyCapacity() - this.outenergyContainer.getEnergyStored())) {
                        eu = (int) (eu - (this.outenergyContainer.getEnergyCapacity() - this.outenergyContainer.getEnergyStored()));
                        this.outenergyContainer.addEnergy(this.outenergyContainer.getEnergyCapacity() - this.outenergyContainer.getEnergyStored());
                    } else {
                        this.outenergyContainer.addEnergy(eu);
                        eu = 0;
                    }
                }
            }
            if (work) for (int i = -5; i <= 5; ++i) {
                for (int j = -5; j <= 5; ++j) {
                    BlockPos poss = this.getPos().add(xDir + i, 0, zDir + j);
                    if (GetTier(poss, i, j) != 11 && GetTier(poss, i, j) != 0)//排除不供电区
                        if (GTUtility.getMetaTileEntity(this.getWorld(), poss.add(0, 1, 0)) instanceof MetaTileEntity) {
                            MetaTileEntity mte = (MetaTileEntity) GTUtility.getMetaTileEntity(this.getWorld(), poss.add(0, 1, 0));
                            int Energy = VA[GetTier(poss, i, j)];
                            for (EnumFacing facing : EnumFacing.VALUES) {
                                if (mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing) instanceof IEnergyContainer) {
                                    IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);
                                    if (charge) {
                                        //如果供电等级大于方块等级 则持续给满
                                        if (!fastCharge) {
                                            if (container.getEnergyStored() < container.getEnergyCapacity() && container.getEnergyCapacity() < Energy && eu > (container.getEnergyCapacity() - container.getEnergyStored())) {
                                                container.addEnergy(container.getEnergyCapacity() - container.getEnergyStored());
                                                eu -= (int) (container.getEnergyCapacity() - container.getEnergyStored());
                                            }
                                            //否则 只供Energy
                                            if (container.getEnergyStored() < (container.getEnergyCapacity() - Energy) && eu > Energy) {
                                                container.addEnergy(Energy);
                                                eu -= Energy;
                                            }
                                        }
                                        //闪充
                                        else if (container.getEnergyStored() < container.getEnergyCapacity()) {
                                            if (eu > (container.getEnergyCapacity() - container.getEnergyStored())) {
                                                container.addEnergy(container.getEnergyCapacity() - container.getEnergyStored());
                                                eu -= (int) (container.getEnergyCapacity() - container.getEnergyStored());
                                            } else {
                                                container.addEnergy(eu);
                                                eu = 0;
                                            }
                                        }
                                    } else//充能模式
                                    {
                                        if (container.getEnergyStored() > 0 && eu < tier * 2048) {
                                            eu += container.getEnergyStored();
                                            container.removeEnergy(container.getEnergyStored());
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
    public void PreCheck()
    {
        maxTier=0;
        //提前检测
        tier=10;//送你的
        final int xDir = this.getFrontFacing().getOpposite().getXOffset() * 5;
        final int zDir = this.getFrontFacing().getOpposite().getZOffset() * 5;
        for (int i = -4; i <= 4; ++i) {
            for (int j = -4; j <= 4; ++j) {//判断中心区域 外围无需判断
                BlockPos poss = this.getPos().add(xDir + i, 0, zDir + j);
                if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_I)){tier+=20;}
                if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_II)){tier+=80;}
                if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_III)){tier+=320;}
                if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_IV)){tier+=1280;}
                if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_V)){tier+=5120;}
                if(GetTier(poss,i,j)>maxTier&&GetTier(poss,i,j)!=11)maxTier=GetTier(poss,i,j);
                if (GetTier(poss,i,j) == 11) {
                    work=false;
                    return;
                }
            }
        }

        work = true;

        poss1 = this.getPos().add( + 11, 0,  + 11);
        poss2 = this.getPos().add( + 11, 0,0 );
        poss3 = this.getPos().add( + 11, 0,  - 11);

        poss4 = this.getPos().add(0,0,  + 11);
        poss5 = this.getPos().add(0, 0,  - 11);

        poss6 = this.getPos().add( - 11, 0,  + 11);
        poss7 = this.getPos().add( - 11, 0,0 );
        poss8 = this.getPos().add( - 11, 0,  - 11);
    }

    public int GetTier(BlockPos poss,int i,int j)
    {
        if(i==-5||i==5||j==-5||j==5)
        {
            return maxTier;//框架方块
        }
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BASIC))return 0;//框架方块
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_I))return 0;//框架方块
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_II))return 0;//框架方块
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_III))return 0;//框架方块
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_IV))return 0;//框架方块
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_BATTERY_V))return 0;//框架方块
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_I))return 1;//超导方块1
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_II))return 2;//超导方块2
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_III))return 3;//超导方块3
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_IV))return 4;//超导方块4
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_V))return 5;//超导方块5
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_VI))return 6;//超导方块6
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_VII))return 7;//超导方块7
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_VIII))return 8;//超导方块8
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_IVV))return 9;//超导方块9
        if(this.getWorld().getBlockState(poss)==GTQTMetaBlocks.POWER.getState(POWER_SUPPLY_VV))return 10;//超导方块10

        return 11;//不认识就开摆
    }

    public MetaTileEntityPowerSupply(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
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
    public boolean checkNetwork(BlockPos poss,boolean work) {
        MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), poss);
        if (mte instanceof MetaTileEntityPowerSupply) {
            if (work && ((MetaTileEntityPowerSupply) mte).isNetwork()&& ((MetaTileEntityPowerSupply) mte).isWork()) {
                if(eu<((MetaTileEntityPowerSupply) mte).getEu()&&eu<getMax()&&eu>=0&&((MetaTileEntityPowerSupply) mte).getEu()>=0)
                {
                    if(((MetaTileEntityPowerSupply) mte).getEu()>getMax()*2)
                    {
                        ((MetaTileEntityPowerSupply) mte).removeEu(getMax()-eu);
                        eu=getMax();
                    }
                    ((MetaTileEntityPowerSupply) mte).removeEu(VA[maxTier]);
                    eu += VA[maxTier];

                }
            }
            return true;
        }
        return false;
    }

    public boolean checkNet(BlockPos poss) {
        MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), poss);
        if (mte instanceof MetaTileEntityPowerSupply)
            return ((MetaTileEntityPowerSupply) mte).network;
        return false;
    }
    public int getNetMax(BlockPos poss)
    {
        if (!network) return 0;
        MetaTileEntity mte =GTUtility.getMetaTileEntity(this.getWorld(),poss);
        if (mte instanceof MetaTileEntityPowerSupply) {
            if (work && ((MetaTileEntityPowerSupply) mte).isNetwork())
            {
                return ((MetaTileEntityPowerSupply) mte).getMax();
            }

        }
        return 0;
    }
    public int getNetEu(BlockPos poss)
    {
        if (!network) return 0;
        MetaTileEntity mte =GTUtility.getMetaTileEntity(this.getWorld(),poss);
        if (mte instanceof MetaTileEntityPowerSupply) {
            if (work && ((MetaTileEntityPowerSupply) mte).isNetwork())
            {
                return ((MetaTileEntityPowerSupply) mte).getEu();
            }
        }
        return 0;
    }
    //初始化能源仓室
    public int getEu()
    {
        return this.eu;
    }
    public void removeEu(int remove)
    {
        if(eu>remove)eu-=remove;
        else eu=0;
    }
    public void setEu(int set)
    {
        if(set<tier*2048)eu=set;
    }
    public int getMax()
    {
        return tier*2048;
    }
    public boolean isNetwork()
    {
        return network;
    }
    public boolean isWork()
    {
        return work;
    }
    private void initializeAbilities() {
        this.inenergyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
        this.outenergyContainer =new EnergyContainerList(getAbilities(MultiblockAbility.OUTPUT_ENERGY));

    }
    private void resetTileAbilities() {
        this.inenergyContainer = new EnergyContainerList(new ArrayList());
        this.outenergyContainer = new EnergyContainerList(new ArrayList());
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
