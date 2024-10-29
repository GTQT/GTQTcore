package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.utils.GTQTCPUHelper;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static gregtech.api.GTValues.VA;
import static keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate.CP_PAF_CASING;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.HydrogenSilsesquioxane;
import static keqing.gtqtcore.api.unification.TJMaterials.SU8_Photoresist;
import static keqing.gtqtcore.api.utils.GTQTCPUHelper.item;

public class MetaTileEntityPhotolithographyFactory extends MetaTileEntityBaseWithControl{

    int laserTier;
    int laserKind;
    int LaserAmount;
    int []wafer={0,0,0,0,0,0,0};
    boolean work;
    boolean balance;
    boolean check;
    boolean outputCheck;
    boolean speed;
    boolean []coreWork= {false,false,false,false};
    public int [][]core={{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
    int []speedMultiplier={0,0,0,0};
    //光刻胶等级
    FluidStack LASER1 = HydrogenSilsesquioxane.getFluid(1000);
    FluidStack LASER2 = Vinylcinnamate .getFluid(1000);
    FluidStack LASER3 = SU8_Photoresist.getFluid(1000);
    FluidStack LASER4 = Xmt.getFluid(1000);
    FluidStack LASER5 = Zrbtmst.getFluid(1000);

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("laserTier", laserTier);
        data.setInteger("laserKind", laserKind);
        data.setInteger("LaserAmount", LaserAmount);

        data.setIntArray("wafer", wafer);

        data.setIntArray("core0", core[0]);
        data.setIntArray("core1", core[1]);
        data.setIntArray("core2", core[2]);
        data.setIntArray("core3", core[3]);

        data.setBoolean("work",work);
        data.setBoolean("balance",balance);
        data.setBoolean("check",check);
        data.setBoolean("outputCheck",outputCheck);
        data.setBoolean("speed",speed);

        data.setBoolean("coreWork1",coreWork[0]);
        data.setBoolean("coreWork2",coreWork[1]);
        data.setBoolean("coreWork3",coreWork[2]);
        data.setBoolean("coreWork4",coreWork[3]);
        return super.writeToNBT(data);
    }
   
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        laserTier = data.getInteger("laserTier");
        laserKind = data.getInteger("laserKind");
        LaserAmount = data.getInteger("LaserAmount");

        wafer=data.getIntArray("wafer");

        core[0]=data.getIntArray("core0");
        core[1]=data.getIntArray("core1");
        core[2]=data.getIntArray("core2");
        core[3]=data.getIntArray("core3");

        work=data.getBoolean("work");
        balance=data.getBoolean("balance");
        check=data.getBoolean("check");
        outputCheck=data.getBoolean("outputCheck");
        speed=data.getBoolean("speed");

        coreWork[0]=data.getBoolean("coreWork1");
        coreWork[1]=data.getBoolean("coreWork2");
        coreWork[2]=data.getBoolean("coreWork3");
        coreWork[3]=data.getBoolean("coreWork4");
    }

    //核心缓存： wafer等级0 wafer数量1 光刻胶等级2 耗时3
    public MetaTileEntityPhotolithographyFactory(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }
    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 298, 240);

        //左侧四个线程渲染
        int j = 0;
        //1号
        builder.image(3, 4 + j * 40, 128, 40, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 40, this::addInfo1, 16777215)).setMaxWidthLimit(108).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(115, 4 + j * 40, 15, 40, "I/O", data -> coreWork[0]=!coreWork[0]));
        j++;
        //2号
        builder.image(3, 4 + j * 40, 128, 40, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 40, this::addInfo2, 16777215)).setMaxWidthLimit(108).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(115, 4 + j * 40, 15, 40, "I/O", data -> coreWork[1]=!coreWork[1]));
        j++;
        //3号
        builder.image(3, 4 + j * 40, 128, 40, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 40, this::addInfo3, 16777215)).setMaxWidthLimit(108).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(115, 4 + j * 40, 15, 40, "I/O", data -> coreWork[2]=!coreWork[2]));
        j++;
        //4号
        builder.image(3, 4 + j * 40, 128, 40, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 40, this::addInfo4, 16777215)).setMaxWidthLimit(108).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(115, 4 + j * 40, 15, 40, "I/O", data -> coreWork[3]=!coreWork[3]));

        // Display
        builder.image(3, 164, 128, 82, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 168, this::addTotal, 16777215)).setMaxWidthLimit(130).setClickHandler(this::handleDisplayClick));

        // Display
        builder.image(130, 4, 162, 132, GuiTextures.DISPLAY);
        builder.dynamicLabel(95+38, 8, () -> "大型光刻厂", 0xFFFFFF);
        builder.widget((new AdvancedTextWidget(95+38, 20, this::addDisplayText, 16777215)).setMaxWidthLimit(162).setClickHandler(this::handleDisplayClick));

        //按钮
        builder.widget(new ClickButtonWidget(214, 120, 36, 18, "溢出检测", this::outputCheck).setTooltipText("开启后忽视输出总线情况强制输出，这可能会导致你的产物不会被输出"));
        builder.widget(new ClickButtonWidget(256, 120, 36, 18, "自动超频", this::speed).setTooltipText("根据能量缓存与能量输入速率自行进行各线程超频，耗能倍率为加工倍率的平方"));

        builder.widget(new ClickButtonWidget(130, 140, 36, 18, "自动工作", this::work).setTooltipText("在原料输入完毕后打开开关开始工作即可开始工作"));
        builder.widget(new ClickButtonWidget(172, 140, 36, 18, "自动均分", this::balance).setTooltipText("独立线程都会帮助其他在工作的线程分担任务"));
        builder.widget(new ClickButtonWidget(214, 140, 36, 18, "强制保险", this::check).setTooltipText("部分线程工作完毕后会因为存在未完成工作的线程而强制设置多方块为工作状态，这意味着如果可能会在还在属于原料时各线程自行工作"));
        builder.widget(new ClickButtonWidget(256, 140, 36, 18, "退回缓存", this::outputlaser).setTooltipText("在输出仓输出总线返还光刻胶以及晶圆, 不含正在工作的"));


        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 130, 160);
        return builder;
    }
    protected void addTotal(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed()) {

                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "能量存储上限： %s", this.energyContainer.getEnergyCapacity()));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "能量缓存上限： %s", this.energyContainer.getEnergyStored()));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "能量输入速率： %s", this.energyContainer.getInputPerSec()));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "超频倍率：%s 基础耗能： %s", speedMultiplier[0],((long) core[0][1] * VA[core[0][0] + core[0][2]]) / laserTier  ));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "超频倍率：%s 基础耗能： %s", speedMultiplier[1],((long) core[1][1] * VA[core[1][0] + core[1][2]]) / laserTier  ));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "超频倍率：%s 基础耗能： %s", speedMultiplier[2],((long) core[2][1] * VA[core[2][0] + core[2][2]]) / laserTier  ));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "超频倍率：%s 基础耗能： %s", speedMultiplier[3],((long) core[3][1] * VA[core[3][0] + core[3][2]]) / laserTier  ));
                    }
                });
    }
    protected void addInfo1(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, ">>线程 1 状态： %s",coreWork[0]));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "工作进度： %s / %s",core[0][4],core[0][3]));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "晶圆等级 %s/光刻胶等级 %s",core[0][0],core[0][2]));
                });
    }
    protected void addInfo2(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, ">>线程 2 状态： %s",coreWork[1]));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "工作进度： %s / %s",core[1][4],core[1][3]));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "晶圆等级 %s/光刻胶等级 %s",core[1][0],core[1][2]));
                });
    }
    protected void addInfo3(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, ">>线程 3 状态： %s",coreWork[2]));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "工作进度： %s / %s",core[2][4],core[2][3]));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "晶圆等级 %s/光刻胶等级 %s",core[2][0],core[2][2]));
                });
    }
    protected void addInfo4(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, ">>线程 4 状态： %s",coreWork[3]));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "工作进度： %s / %s",core[3][4],core[3][3]));
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "晶圆等级 %s/光刻胶等级 %s",core[3][0],core[3][2]));
                });
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        // 晶圆等级
        String waferList = String.format(">>各级晶圆缓存: %s/%s/%s/%s/%s/%s/%s", wafer[0],wafer[1],wafer[2],wafer[3],wafer[4],wafer[5],wafer[6]);
        String waferGrade = String.format("各线程配方数: %s %s %s %s", core[0][1],core[1][1],core[1][1],core[1][1]);
        // 光刻胶等级
        String laserKindDesc = String.format(">>光刻胶等级: %s 支持配方数: %s", laserKind,LaserAmount / 1000);
        // 开关状态
        String workStatus = String.format("//:开关状态: %s", work);
        // 均分状态
        String balanceStatus = String.format("//:均分状态: %s", balance);
        // 保险状态
        String checkStatus = String.format("//:保险状态: %s", check);
        // 保险状态
        String outputCheckStatus = String.format("//:溢出检测: %s", outputCheck);
        // 保险状态
        String speedStatus = String.format("//:超频状态: %s", speed);
        // 组合所有信息
        String displayText = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n",
                waferList,waferGrade, laserKindDesc, workStatus, balanceStatus, checkStatus,outputCheckStatus,speedStatus);
        // 添加到文本列表
        textList.add(new TextComponentTranslation(displayText));
    }


    private void outputlaser(Widget.ClickData clickData) {
        if(laserKind==1) this.getOutputFluidInventory().fill(HydrogenSilsesquioxane.getFluid(LaserAmount),true);
        if(laserKind==2) this.getOutputFluidInventory().fill(Vinylcinnamate.getFluid(LaserAmount),true);
        if(laserKind==3) this.getOutputFluidInventory().fill(SU8_Photoresist.getFluid(LaserAmount),true);
        if(laserKind==4) this.getOutputFluidInventory().fill(Xmt.getFluid(LaserAmount),true);
        if(laserKind==5) this.getOutputFluidInventory().fill(Zrbtmst.getFluid(LaserAmount),true);
        LaserAmount=0;
        for(int j=0;j<this.wafer.length;j++) {
            if (this.wafer[j] > 0)
                GTTransferUtils.insertItem(this.outputInventory, new ItemStack(GTQTCPUHelper.wafer[j].getMetaItem(), wafer[j], GTQTCPUHelper.wafer[j].getMetaValue()), false);
        }
    }
    private void check(Widget.ClickData clickData) {
        check=!check;
    }
    private void balance(Widget.ClickData clickData) {
        balance=!balance;
    }
    private void work(Widget.ClickData clickData) {
        work=!work;
    }
    private void speed(Widget.ClickData clickData) {
        speed=!speed;
    }
    private void outputCheck(Widget.ClickData clickData) {
        outputCheck=!outputCheck;
    }
    @Override
    protected void updateFormedValid() {
        InputWafer();
        handleLaser();
        updateCoreWork();
        performWork();
    }

    private void handleLaser() {
        IMultipleTankHandler inputTank = getInputFluidInventory();
        if (LaserAmount + 1000 <= 640000) {
            for (int i = 1; i <= 5; i++) {
                FluidStack laser = getLaser(i);
                FluidStack drained = inputTank.drain(laser, false);
                if (drained != null && laser.isFluidStackIdentical(drained) && (laserKind == i || LaserAmount == 0)) {
                    inputTank.drain(laser, true);
                    addLaserAmount(i);
                }
            }
        }
    }
    public void setCoreToZeroIfAnyZero(int[] core) {
        // 检查 core 数组中是否有任何一个元素为 0
        boolean hasZero = core[0] == 0 || core[1] == 0 || core[2] == 0;

        // 如果有任意一个元素为 0，则将所有元素设置为 0
        if (hasZero) {
            Arrays.fill(core, 0);
        }
    }
    /**
     * 更新核心工作状态的私有方法
     * 本方法根据当前的check状态和coreWork数组状态，更新work变量和check状态，并对core数组中的元素进行更新
     * 此外，根据LaserAmount（激光数量）和wafer数组（晶圆状态）的值，对core数组中的元素进行相应的调整
     */
    private void updateCoreWork() {
        // 根据check状态，更新work变量，work为true如果任一coreWork元素为true
        if (check) {
            for(int i=0;i<4;i++) coreWork[i]=(core[i][1]!=0);
            work = coreWork[0] || coreWork[1] || coreWork[2] || coreWork[3];

        }

        /*
        // 如果所有coreWork元素都为true且check为true，将check设置为false
        if (coreWork[0] && coreWork[1] && coreWork[2] && coreWork[3] && check) {
            check = false;
        }
        */

        // 遍历core数组，更新每个元素的状态
        for (int i = 0; i < 4; i++) {
            setCoreToZeroIfAnyZero(core[i]);

            // 根据条件更新core数组的第一列和第二列的值，以及LaserAmount和wafer数组的值
            for (int j = 0; j < wafer.length; j++) {
                if ((core[i][0] == j + 1 || core[i][0] == 0) && Math.min(LaserAmount / 1000, wafer[j]) > 1) {
                    core[i][0] = j + 1;
                    int minLaserAmount = Math.min(LaserAmount / 1000, wafer[j]);
                    if (core[i][1] + minLaserAmount < 65) {
                        core[i][1] += minLaserAmount;
                        LaserAmount -= minLaserAmount * 1000;
                        wafer[j] -= minLaserAmount;

                    }
                    else {
                        int deltaLaserAmount = 64 - core[i][1];
                        core[i][1] = 64;
                        LaserAmount -= deltaLaserAmount * 1000;
                        wafer[j] -= deltaLaserAmount;
                    }
                    core[i][2] = laserKind;
                    coreWork[i]=true;
                }
            }
            if (balance) {
                // 如果balance为true，尝试平衡core数组中不同元素的第二列的值
                for (int k = 0; k < 4; k++) {
                    if (k != i &&       (core[i][1] == 0    || (core[i][0] == core[k][0] && Math.abs(core[i][1] - core[k][1]) >= 1) )      && core[k][1] > 2) {
                        core[i][2] = core[k][2];
                        core[i][0] = core[k][0];
                        int sum = core[i][1] + core[k][1];
                        core[i][1] = sum / 2;
                        core[k][1] = sum - core[i][1];
                        coreWork[k]=true;
                    }
                }
            }
            // 更新core数组的第四列的值，通过调用TimeConsume方法计算
            core[i][3] = TimeConsume(core[i][0], core[i][1], core[i][2]);
        }
    }

    /**
     * 执行工作任务的方法
     * 此方法根据当前的工作状态和能量存储情况，执行一系列的工作任务
     * 它检查每个核心的工作状态和能量需求，如果满足条件，则消耗能量并完成工作任务
     */
    private void performWork() {
        // 检查是否有工作需要执行
        if (work) {
            // 使用ExecutorService管理多个线程
            ExecutorService executor = Executors.newFixedThreadPool(4);

            // 提交任务给线程池
            for (int i = 0; i < 4; i++) {
                final int index = i;
                int finalI = i;
                executor.submit(() -> {

                    if(core[index][4]<core[index][3])
                    {
                        long currentCoreEnergyCost = ((long) core[index][1] * VA[core[index][0] + core[index][2]]) / laserTier;

                        if (coreWork[index] && energyContainer.getEnergyStored() - currentCoreEnergyCost > 0) {
                            long inputEnergy = energyContainer.getEnergyStored() / 80 + energyContainer.getInputVoltage() / 4;

                            if(speed) {
                                if (inputEnergy >= 1024 * currentCoreEnergyCost) {
                                    energyContainer.removeEnergy(1024 * currentCoreEnergyCost);
                                    speedMultiplier[finalI] = 32;
                                } else if (inputEnergy >= 256 * currentCoreEnergyCost) {
                                    energyContainer.removeEnergy(256 * currentCoreEnergyCost);
                                    speedMultiplier[finalI] = 16;
                                } else if (inputEnergy >= 64 * currentCoreEnergyCost) {
                                    energyContainer.removeEnergy(64 * currentCoreEnergyCost);
                                    speedMultiplier[finalI] = 8;
                                } else if (inputEnergy >= 16 * currentCoreEnergyCost) {
                                    energyContainer.removeEnergy(16 * currentCoreEnergyCost);
                                    speedMultiplier[finalI] = 4;
                                } else if (inputEnergy >= 4 * currentCoreEnergyCost) {
                                    energyContainer.removeEnergy(4 * currentCoreEnergyCost);
                                    speedMultiplier[finalI] = 2;
                                } else {
                                    energyContainer.removeEnergy(currentCoreEnergyCost);
                                    speedMultiplier[finalI] = 1;
                                }
                            }
                            else {
                                energyContainer.removeEnergy(currentCoreEnergyCost);
                                speedMultiplier[finalI] = 1;
                            }

                            // 增加当前核心的工作进度
                            core[index][4] += speedMultiplier[finalI];

                        } else speedMultiplier[finalI]=0;
                    }
                    else {
                        List<ItemStack> itemList = new ArrayList<>();
                        for (int number = 0; number < GTQTCPUHelper.item.length; number++) {
                            ItemStack stack = GTQTCPUHelper.getStack(number, core[index][1], core[index][0], core[index][2]);
                            itemList.add(stack);
                        }

                        if (outputCheck) {
                            for (ItemStack stack : itemList) {
                                if (!GTTransferUtils.addItemsToItemHandler(outputInventory, true, Collections.singletonList(stack))) {
                                    return;
                                }
                            }
                        }

                        for (ItemStack stack : itemList) {
                            GTTransferUtils.insertItem(outputInventory, stack, false);
                        }
                        // 重置当前核心的工作状态
                        core[index][0] = core[index][1] = core[index][2] = core[index][3] = core[index][4] = 0;
                        coreWork[index] = false;
                    }
                });
            }

            // 关闭线程池
            executor.shutdown();
            try {
                // 等待所有任务完成
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public int TimeConsume(int tier, int amount, int LaserTier) {
        return 20 * amount * (10 - LaserTier) * (10 - tier) / laserTier;
    }

    public void InputWafer() {
        var slots = inputInventory.getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack item = inputInventory.getStackInSlot(i);
            for (int j = 0; j < GTQTCPUHelper.wafer.length; j++) {
                if (GTQTCPUHelper.checkWafer(item, GTQTCPUHelper.wafer[j])) {
                    inputInventory.extractItem(i, 1, false);
                    wafer[j] += 1;
                }
            }
        }
    }

    public void addLaserAmount(int n) {
        if (laserKind == n) {
            LaserAmount += 1000;
        } else if (laserKind == 0 || LaserAmount == 0) {
            LaserAmount = 1000;
            laserKind = n;
        }
    }

    private FluidStack getLaser(int n) {
        return switch (n) {
            case 1 -> LASER1;
            case 2 -> LASER2;
            case 3 -> LASER3;
            case 4 -> LASER4;
            case 5 -> LASER5;
            default -> null;
        };
    }
    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.NQ_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPhotolithographyFactory(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("                                               ", "                                               ", "                    FCCCCCF                    ", "                    FCCBCCF                    ", "                    FCCCCCF                    ", "                                               ", "                                               ")
                .aisle("                                               ", "                    FCBBBCF                    ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                    FCBBBCF                    ", "                                               ")
                .aisle("                    FCBBBCF                    ", "                   CC#####CC                   ", "                CCCCC#####CCCCC                ", "                CCCHHHHHHHHHCCC                ", "                CCCCC#####CCCCC                ", "                   CC#####CC                   ", "                    FCBBBCF                    ")
                .aisle("                    FCCBCCF                    ", "                CCCCC#####CCCCC                ", "              CCCCCHHHHHHHHHCCCCC              ", "              CCHHHHHHHHHHHHHHHCC              ", "              CCCCCHHHHHHHHHCCCCC              ", "                CCCCC#####CCCCC                ", "                    FCCBCCF                    ")
                .aisle("                    FCBBBCF                    ", "              CCCCCCC#####CCCCCCC              ", "            CCCCHHHCC#####CCHHHCCCC            ", "            CCHHHHHHHHHHHHHHHHHHHCC            ", "            CCCCHHHCC#####CCHHHCCCC            ", "              CCCCCCC#####CCCCCCC              ", "                    FCBBBCF                    ")
                .aisle("                                               ", "            CCCCCCC FCBBBCF CCCCCCC            ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "           CHHHHHHHCC#####CCHHHHHHHC           ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "            CCCCCCC FCBBBCF CCCCCCC            ", "                                               ")
                .aisle("                                               ", "           CCCCC               CCCCC           ", "          CCHHCCCCC FCCCCCF CCCCCHHCC          ", "          CHHHHHCCC FCCBCCF CCCHHHHHC          ", "          CCHHCCCCC FCCCCCF CCCCCHHCC          ", "           CCCCC               CCCCC           ", "                                               ")
                .aisle("                                               ", "          CCCC                   CCCC          ", "         CCHCCCC               CCCCHCC         ", "         CHHHHCC               CCHHHHC         ", "         CCHCCCC               CCCCHCC         ", "          CCCC                   CCCC          ", "                                               ")
                .aisle("                                               ", "         CCC                       CCC         ", "        CCHCCC                   CCCHCC        ", "        CHHHCC                   CCHHHC        ", "        CCHCCC                   CCCHCC        ", "         CCC                       CCC         ", "                                               ")
                .aisle("                                               ", "        CCC                         CCC        ", "       CCHCC                       CCHCC       ", "       CHHHC                       CHHHC       ", "       CCHCC                       CCHCC       ", "        CCC                         CCC        ", "                                               ")
                .aisle("                                               ", "       CCC                           CCC       ", "      CCHCC                         CCHCC      ", "      CHHHC                         CHHHC      ", "      CCHCC                         CCHCC      ", "       CCC                           CCC       ", "                                               ")
                .aisle("                                               ", "      CCC                             CCC      ", "     CCHCC                           CCHCC     ", "     CHHHC                           CHHHC     ", "     CCHCC                           CCHCC     ", "      CCC                             CCC      ", "                                               ")
                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                .aisle("                                               ", "  CCC                                     CCC  ", " CCHCC                                   CCHCC ", " CHHHC                                   CHHHC ", " CCHCC                                   CCHCC ", "  CCC                                     CCC  ", "                                               ")
                .aisle("  FFF                                     FFF  ", " FCCCF                                   FCCCF ", "FCCHCCF                                 FCCHCCF", "FCHHHCF                                 FCHHHCF", "FCCHCCF                                 FCCHCCF", " FCCCF                                   FCCCF ", "  FFF                                     FFF  ")
                .aisle("  CCC                                     CCC  ", " C###C                                   C###C ", "C##H##C                                 C##H##C", "C#HHH#C                                 C#HHH#C", "C##H##C                                 C##H##C", " C###C                                   C###C ", "  CCC                                     CCC  ")
                .aisle("  CCC                                     CCC  ", " B###B                                   B###B ", "C##H##C                                 C##H##C", "C#HHH#C                                 C#HHH#C", "C##H##C                                 C##H##C", " B###B                                   B###B ", "  CCC                                     CCC  ")
                .aisle("  CBC                                     CBC  ", " B###B                                   B###B ", "C##H##C                                 C##H##C", "B#HHH#B                                 B#HHH#B", "C##H##C                                 C##H##C", " B###B                                   B###B ", "  CBC                                     CBC  ")
                .aisle("  CCC                                     CCC  ", " B###B                                   B###B ", "C##H##C                                 C##H##C", "C#HHH#C                                 C#HHH#C", "C##H##C                                 C##H##C", " B###B                                   B###B ", "  CCC                                     CCC  ")
                .aisle("  CCC                                     CCC  ", " C###C                                   C###C ", "C##H##C                                 C##H##C", "C#HHH#C                                 C#HHH#C", "C##H##C                                 C##H##C", " C###C                                   C###C ", "  CCC                                     CCC  ")
                .aisle("  FFF                                     FFF  ", " FCCCF                                   FCCCF ", "FCCHCCF                                 FCCHCCF", "FCHHHCF                                 FCHHHCF", "FCCHCCF                                 FCCHCCF", " FCCCF                                   FCCCF ", "  FFF                                     FFF  ")
                .aisle("                                               ", "  CCC                                     CCC  ", " CCHCC                                   CCHCC ", " CHHHC                                   CHHHC ", " CCHCC                                   CCHCC ", "  CCC                                     CCC  ", "                                               ")
                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                .aisle("                                               ", "      CCC                             CCC      ", "     CCHCC                           CCHCC     ", "     CHHHC                           CHHHC     ", "     CCHCC                           CCHCC     ", "      CCC                             CCC      ", "                                               ")
                .aisle("                                               ", "       CCC                           CCC       ", "      CCHCC                         CCHCC      ", "      CHHHC                         CHHHC      ", "      CCHCC                         CCHCC      ", "       CCC                           CCC       ", "                                               ")
                .aisle("                                               ", "        CCC                         CCC        ", "       CCHCC                       CCHCC       ", "       CHHHC                       CHHHC       ", "       CCHCC                       CCHCC       ", "        CCC                         CCC        ", "                                               ")
                .aisle("                                               ", "         CCC                       CCC         ", "        CCHCCC                   CCCHCC        ", "        CHHHCC                   CCHHHC        ", "        CCHCCC                   CCCHCC        ", "         CCC                       CCC         ", "                                               ")
                .aisle("                                               ", "          CCCC                   CCCC          ", "         CCHCCCC               CCCCHCC         ", "         CHHHHCC               CCHHHHC         ", "         CCHCCCC               CCCCHCC         ", "          CCCC                   CCCC          ", "                                               ")
                .aisle("                                               ", "           CCCCC               CCCCC           ", "          CCHHCCCCC FCCCCCF CCCCCHHCC          ", "          CHHHHHCCC FCCBCCF CCCHHHHHC          ", "          CCHHCCCCC FCCCCCF CCCCCHHCC          ", "           CCCCC               CCCCC           ", "                                               ")
                .aisle("                                               ", "            CCCCCCC FCBBBCF CCCCCCC            ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "           CHHHHHHHCC#####CCHHHHHHHC           ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "            CCCCCCC FCBBBCF CCCCCCC            ", "                                               ")
                .aisle("                    FCBBBCF                    ", "              CCCCCCC#####CCCCCCC              ", "            CCCCHHHCC#####CCHHHCCCC            ", "            CCHHHHHHHHHHHHHHHHHHHCC            ", "            CCCCHHHCC#####CCHHHCCCC            ", "              CCCCCCC#####CCCCCCC              ", "                    FCBBBCF                    ")
                .aisle("                    FCCBCCF                    ", "                CCCCC#####CCCCC                ", "              CCCCCHHHHHHHHHCCCCC              ", "              CCHHHHHHHHHHHHHHHCC              ", "              CCCCCHHHHHHHHHCCCCC              ", "                CCCCC#####CCCCC                ", "                    FCCBCCF                    ")
                .aisle("                    FCBBBCF                    ", "                   CC#####CC                   ", "                CCCCC#####CCCCC                ", "                CCCHHHHHHHHHCCC                ", "                CCCCC#####CCCCC                ", "                   CC#####CC                   ", "                    FCBBBCF                    ")
                .aisle("                                               ", "                    FCBBBCF                    ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                    FCBBBCF                    ", "                                               ")
                .aisle("                                               ", "                                               ", "                    FCCCCCF                    ", "                    FCCSCCF                    ", "                    FCCCCCF                    ", "                                               ", "                                               ")
                .where('S', this.selfPredicate())
                .where('B', states(getGlassState()))
                .where('C', states(getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(4)))
                .where('F', states(getFrameState()))
                .where('H', CP_PAF_CASING.get())
                .where('#', air())
                .where(' ', any())
                .build();
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
        return Textures.DATA_BANK_OVERLAY;
    }
    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object laserTier = context.get("PAFTieredStats");
        this.laserTier = GTQTUtil.getOrDefault(() -> laserTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)laserTier).getIntTier(),
                0);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.NQ_TURBINE_CASING);
    }

    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.Naquadria).getBlock(Materials.Naquadria);
    }

    private static IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS);
    }
    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        return new LinkedList<>();
    }

    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gtqtcore.pf.tooltip.1", new Object[0]));
        tooltip.add(TooltipHelper.BLINKING_CYAN+I18n.format("gtqtcore.pf.tooltip.2", 4));
        tooltip.add(I18n.format("gtqtcore.pf.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.pf.tooltip.4"));
        tooltip.add(I18n.format("gtqtcore.pf.tooltip.5"));
        tooltip.add(I18n.format("gtqtcore.pf.tooltip.6"));
        tooltip.add(I18n.format("gtqtcore.pf.tooltip.7"));
        //tooltip.add(I18n.format("=================================="));
        //tooltip.add(I18n.format("如宇宙间最精密的织梦者，以光年丈量着世界的边界，悄然模糊科技与艺术的边界。"));
        //tooltip.add(I18n.format("如星辰运转于浩瀚银河之中，光线织工在超高速微电路上起舞绘制出纳米级的电路蓝图。"));
        //tooltip.add(I18n.format("如同晨曦中露珠滑过蜘蛛网的细腻，每一滴都承载着光的意志，编织着电子世界的经纬。"));
        //tooltip.add(I18n.format("如乐团中的每一位乐手，虽各自独立却又完美同步，演奏出超越物理极限的乐章。"));
    }
}
