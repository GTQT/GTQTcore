package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.LaserSystem;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
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
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.particle.GTParticleManager;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.GTQTCoreConfig;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.client.objmodels.ObjModels;
import keqing.gtqtcore.client.particle.LaserBeamParticle;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityBaseWithControl;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.LASER_INPUT;
import static keqing.gtqtcore.api.unification.GTQTMaterials.MaragingSteel250;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing.TurbineCasingType.NQ_TURBINE_CASING;

public class MetaTileEntitySBPRC extends MetaTileEntityBaseWithControl implements IFastRenderMetaTileEntity {
    int x;
    int y;
    int z;
    int switchInput;
    int switchOutput;
    int[] weightOutput = new int[32];
    int[][] input = new int[32][5];
    int[][] output = new int[32][5];
    int maxLength = 8;
    //0位 状态   1 2 3位置坐标 4位 数值
    int inputAmount;
    int outputAmount;
    int extraAmount;

    int timeInput;
    int timeOutput;
    int timeWeightTotal;
    int weightTotal;

    boolean LaserGroup;
    boolean ReflectGlass;
    boolean HighReflect;
    public MetaTileEntitySBPRC(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    //第一位 1-》输入 2-》输出
    //第二位 等级
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        if(LaserGroup)maxLength*=2;
        if(ReflectGlass)maxLength*=2;
        if(HighReflect)maxLength*=2;
    }
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setBoolean("LaserGroup",LaserGroup);
        data.setBoolean("ReflectGlass",ReflectGlass);
        data.setBoolean("HighReflect",HighReflect);
        data.setInteger("switchInput", switchInput);
        data.setInteger("switchOutput", switchOutput);
        data.setLong("inputAmount", inputAmount);
        data.setLong("outputAmount", outputAmount);
        data.setLong("extraAmount", extraAmount);
        data.setInteger("weightTotal", weightTotal);
        data.setInteger("maxLength", maxLength);
        data.setIntArray("weightOutput", weightOutput);
        for (int i = 0; i < maxLength; i++) {
            data.setIntArray("input" + i, input[i]);
            data.setIntArray("output" + i, output[i]);
        }
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        LaserGroup = data.getBoolean("LaserGroup");
        ReflectGlass = data.getBoolean("ReflectGlass");
        HighReflect = data.getBoolean("HighReflect");
        switchInput = data.getInteger("switchInput");
        switchOutput = data.getInteger("switchOutput");
        inputAmount = data.getInteger("inputAmount");
        outputAmount = data.getInteger("outputAmount");
        extraAmount = data.getInteger("extraAmount");
        weightTotal = data.getInteger("weightTotal");
        maxLength = data.getInteger("maxLength");
        weightOutput = data.getIntArray("weightOutput");
        for (int i = 0; i < maxLength; i++) {
            input[i] = data.getIntArray("input" + i);
            output[i] = data.getIntArray("output" + i);
        }
    }

    /////////////////////////////////////////
    @Override
    protected void updateFormedValid() {
        extraAmount = inputAmount - outputAmount;
        //初始化检查
        if (checkLocate(true, true) == 1) for (int i = 0; i < maxLength; i++) {
            if (input[i][0] == 0) {
                input[i][0] = 1;
                input[i][1] = x;
                input[i][2] = y;
                input[i][3] = z;
                checkLocate(false, false);
                GTTransferUtils.insertItem(this.outputInventory, setCard(), false);
                x = 0;
                y = 0;
                z = 0;
                return;
            }
        }
        if (checkLocate(true, true) == 2) for (int i = 0; i < maxLength; i++) {
            if (output[i][0] == 0) {
                output[i][0] = 1;
                output[i][1] = x;
                output[i][2] = y;
                output[i][3] = z;
                weightOutput[i] = 1;
                checkLocate(false, false);
                GTTransferUtils.insertItem(this.outputInventory, setCard(), false);
                x = 0;
                y = 0;
                z = 0;
                return;
            }

        }

        timeInput = 0;
        timeOutput = 0;
        timeWeightTotal = 0;



        //常规刷新
        for (int i = 0; i < maxLength; i++) {
            if (input[i][0] == 1) {
                input[i][4] = refreshList(i, false);
                timeInput += refreshList(i, false);
            }
            if (output[i][0] == 1) {
                output[i][4] = refreshList(i, true);
                timeOutput += refreshList(i, true);
                timeWeightTotal += weightOutput[i];
            }
        }
        if (timeInput != inputAmount) inputAmount = timeInput;
        if (timeOutput != outputAmount) outputAmount = timeOutput;
        if (timeWeightTotal != weightTotal) weightTotal = timeWeightTotal;

        //输出

        for (int i = 0; i < maxLength; i++) {
            if (output[i][0] == 1) {

                if (weightTotal == 0 || weightOutput[i] == 0) {
                    setLaser(i, 0);
                } else setLaser(i, (long) inputAmount * weightOutput[i] / weightTotal);
            }
        }
    }
    public void setLaser(int point, long amount) {
        MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(output[point][1], output[point][2], output[point][3]));
        ((MetaTileEntitySBPRO) mte).setLaser(amount);
    }

    public int refreshList(int point, boolean isOutput) {

        if (isOutput) {
            MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(output[point][1], output[point][2], output[point][3]));
            if (mte instanceof MetaTileEntitySBPRO) {
                ((MetaTileEntitySBPRO) mte).setMachinePos(this.getPos());
                return ((MetaTileEntitySBPRO) mte).getLaser();
            } else {
                output[point][0] = 0;
                output[point][1] = 0;
                output[point][2] = 0;
                output[point][3] = 0;
                output[point][4] = 0;
                weightOutput[point] = 0;
                return 0;
            }

        } else {
            MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(input[point][1], input[point][2], input[point][3]));
            if (mte instanceof MetaTileEntitySBPRI) {
                ((MetaTileEntitySBPRI) mte).setMachinePos(this.getPos());
                return (int) ((MetaTileEntitySBPRI) mte).Laser;
            } else {
                input[point][0] = 0;
                input[point][1] = 0;
                input[point][2] = 0;
                input[point][3] = 0;
                input[point][4] = 0;
                return 0;
            }
        }
    }

    //通过物品获取坐标
    public int checkLocate(boolean sim, boolean old) {
        if (this.getInputInventory() == null) return 0;
        var slots = this.getInputInventory().getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack item = this.getInputInventory().getStackInSlot(i);
            if (item.getItem() == GTQTMetaItems.GTQT_META_ITEM && item.getMetadata() == GTQTMetaItems.POS_BINDING_CARD.getMetaValue()) {
                NBTTagCompound compound = item.getTagCompound();
                if (compound != null && compound.hasKey("x") && compound.hasKey("y") && compound.hasKey("z")) {
                    x = compound.getInteger("x");
                    y = compound.getInteger("y");
                    z = compound.getInteger("z");

                    if (!old) {
                        this.getInputInventory().extractItem(i, 1, sim);
                        return 3;
                    }

                    for (int j = 0; j < maxLength; j++) {
                        if (output[j][0] == 1) {
                            if (x == output[j][1] && y == output[j][2] && z == output[j][3]) return 0;
                        }
                        if (input[j][0] == 1) {
                            if (x == input[j][1] && y == input[j][2] && z == input[j][3]) return 0;
                        }
                    }


                    MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), new BlockPos(x, y, z));
                    if (mte instanceof MetaTileEntitySBPRI) {
                        this.getInputInventory().extractItem(i, 1, sim);
                        return 1;
                    }
                    if (mte instanceof MetaTileEntitySBPRO) {
                        this.getInputInventory().extractItem(i, 1, sim);
                        return 2;
                    }
                }
            }
        }
        return 0;
    }

    /////////////////////////////////////////
    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 380, 240);

        //第一列 输入
        builder.image(0, 0, 100, 40, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(4, 4, this::addInputDisplayText, 16777215)).setMaxWidthLimit(100).setClickHandler(this::handleDisplayClick));

        builder.image(0, 40, 100, 50, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(4, 44, this::addInfoInput1, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(80, 40, 20, 25, "-5", data ->
        {
            if (switchInput > 5)
                switchInput -= 5;
        }));
        builder.widget(new ClickButtonWidget(80, 65, 20, 25, "-1", data ->
        {
            if (switchInput > 0)
                switchInput -= 1;
        }));

        builder.image(0, 90, 100, 50, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(4, 94, this::addInfoInput2, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(80, 90, 20, 50, "+1", data ->
        {
            if (switchInput + 1 < maxLength)
                switchInput += 1;
            else switchInput = maxLength - 1;
        }));

        builder.image(0, 140, 100, 50, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(4, 144, this::addInfoInput3, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(80, 140, 20, 50, "+2", data ->
        {
            if (switchInput + 2 < maxLength)
                switchInput += 2;
            else switchInput = maxLength - 1;
        }));

        builder.image(0, 190, 100, 50, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(4, 194, this::addInfoInput4, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(80, 190, 20, 50, "+3", data ->
        {
            if (switchInput + 3 < maxLength)
                switchInput += 3;
            else switchInput = maxLength - 1;
        }));

        //第二列 输出
        builder.image(100, 0, 100, 40, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(104, 4, this::addOutputDisplayText, 16777215)).setMaxWidthLimit(100).setClickHandler(this::handleDisplayClick));

        builder.image(100, 40, 100, 50, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(104, 44, this::addInfoOutput1, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(180, 40, 20, 25, "-5", data ->
        {
            if (switchOutput > 5)
                switchOutput -= 5;
        }));

        builder.widget(new ClickButtonWidget(180, 65, 20, 25, "-1", data ->
        {
            if (switchOutput > 0)
                switchOutput -= 1;
        }));

        builder.image(100, 90, 100, 50, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(104, 94, this::addInfoOutput2, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(180, 90, 20, 50, "+1", data ->
        {
            if (switchOutput + 1 < maxLength)
                switchOutput += 1;
            else switchOutput = maxLength - 1;
        }));

        builder.image(100, 140, 100, 50, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(104, 144, this::addInfoOutput3, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(180, 140, 20, 50, "+2", data ->
        {
            if (switchOutput + 2 < maxLength)
                switchOutput += 2;
            else switchOutput = maxLength - 1;
        }));
        builder.image(100, 190, 100, 50, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(104, 194, this::addInfoOutput4, 16777215)).setMaxWidthLimit(80).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(180, 190, 20, 50, "+3", data ->
        {
            if (switchOutput + 3 < maxLength)
                switchOutput += 3;
            else switchOutput = maxLength - 1;
        }));

        //第三列
        builder.image(200, 0, 180, 80, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(204, 4, this::addBody, 16777215)).setMaxWidthLimit(180).setClickHandler(this::handleDisplayClick));

        builder.image(200, 80, 180, 60, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(204, 84, this::addTotal, 16777215)).setMaxWidthLimit(180).setClickHandler(this::handleDisplayClick));

        //按钮
        builder.widget(new ClickButtonWidget(200, 140, 45, 20, "+1", this::incrementThresholdWeightO));
        builder.widget(new ClickButtonWidget(245, 140, 45, 20, "-1", this::decrementThresholdWeightO));
        builder.widget(new ClickButtonWidget(290, 140, 45, 20, "+5", this::incrementThresholdWeighF));
        builder.widget(new ClickButtonWidget(335, 140, 45, 20, "-5", this::decrementThresholdWeightF));

        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 205, 160);

        return builder;
    }

    /////////////////////////////////////////
    private void incrementThresholdWeightO(Widget.ClickData clickData) {
        if (output[switchOutput][0] == 1) weightOutput[switchOutput] += 1;
    }

    private void decrementThresholdWeightO(Widget.ClickData clickData) {
        if (output[switchOutput][0] == 1 && weightOutput[switchOutput] >= 1) weightOutput[switchOutput] -= 1;
    }

    private void incrementThresholdWeighF(Widget.ClickData clickData) {
        if (output[switchOutput][0] == 1) weightOutput[switchOutput] += 5;
    }

    private void decrementThresholdWeightF(Widget.ClickData clickData) {
        if (output[switchOutput][0] == 1 && weightOutput[switchOutput] >= 5) weightOutput[switchOutput] -= 5;
    }

    /////////////////////////////////////////
    protected void addBody(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            tl.add(new TextComponentTranslation("》》天基折射棱镜中央控制系统《《"));
            tl.add(new TextComponentTranslation("》高敏反射层:%s",HighReflect));
            tl.add(new TextComponentTranslation("》反射列镜面:%s",ReflectGlass));
            tl.add(new TextComponentTranslation("》激光群优化:%s",LaserGroup));
            tl.add(new TextComponentTranslation(String.format("》对象上限:%s", maxLength)));
        });
    }

    protected void addTotal(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            tl.add(new TextComponentTranslation("》》能源分配管理器《《"));
            tl.add(new TextComponentTranslation(String.format("》能量盈余:%s", extraAmount)));
            tl.add(new TextComponentTranslation(String.format("》总权重:%s", weightTotal)));
            tl.add(new TextComponentTranslation(String.format("》当前所选权重:%s", weightOutput[switchOutput])));
        });
    }

    /////////////////////////////////////////
    protected void addInputDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            tl.add(new TextComponentTranslation("棱镜激光接收控制器"));
            tl.add(new TextComponentTranslation(String.format("高能激光总输入:%s", inputAmount)));
        });
    }

    protected void addInfoInput1(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            if (input[switchInput][0] == 0) return;
            tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "序列：%s", switchInput + 1));
            tl.add(new TextComponentTranslation(String.format("X:%s Y:%s Z:%s", input[switchInput][1], input[switchInput][2], input[switchInput][3])));
            tl.add(new TextComponentTranslation(String.format("激光通量:%s", input[switchInput][4])));
        });
    }

    protected void addInfoInput2(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            if (input[switchInput + 1][0] == 0) return;
            if (switchInput + 1 <= maxLength) {
                tl.add(TextComponentUtil.translationWithColor(TextFormatting.BLUE, "序列：%s", switchInput + 2));
                tl.add(new TextComponentTranslation(String.format("X:%s Y:%s Z:%s", input[switchInput + 1][1], input[switchInput + 1][2], input[switchInput + 1][3])));
                tl.add(new TextComponentTranslation(String.format("激光通量:%s", input[switchInput + 1][4])));
            }
        });
    }

    protected void addInfoInput3(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            if (input[switchInput + 2][0] == 0) return;
            if (switchInput + 2 <= maxLength) {
                tl.add(TextComponentUtil.translationWithColor(TextFormatting.BLUE, "序列：%s", switchInput + 3));
                tl.add(new TextComponentTranslation(String.format("X:%s Y:%s Z:%s", input[switchInput + 2][1], input[switchInput + 2][2], input[switchInput + 2][3])));
                tl.add(new TextComponentTranslation(String.format("激光通量:%s", input[switchInput + 2][4])));
            }
        });
    }

    protected void addInfoInput4(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            if (input[switchInput + 3][0] == 0) return;
            if (switchInput + 3 <= maxLength) {
                tl.add(TextComponentUtil.translationWithColor(TextFormatting.BLUE, "序列：%s", switchInput + 4));
                tl.add(new TextComponentTranslation(String.format("X:%s Y:%s Z:%s", input[switchInput + 3][1], input[switchInput + 3][2], input[switchInput + 3][3])));
                tl.add(new TextComponentTranslation(String.format("激光通量:%s", input[switchInput + 3][4])));
            }
        });
    }

    /////////////////////////////////////////
    protected void addOutputDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            tl.add(new TextComponentTranslation("棱镜激光输出控制器"));
            tl.add(new TextComponentTranslation(String.format("高能激光总输出:%s", outputAmount)));
        });
    }

    protected void addInfoOutput1(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            if (output[switchOutput][0] == 0) return;
            tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "序列：%s", switchOutput + 1));
            tl.add(new TextComponentTranslation(String.format("X:%s Y:%s Z:%s", output[switchOutput][1], output[switchOutput][2], output[switchOutput][3])));
            tl.add(new TextComponentTranslation(String.format("激光通量:%s", output[switchOutput][4])));
            tl.add(new TextComponentTranslation(String.format("输出权重:%s", weightOutput[switchOutput])));
        });
    }

    protected void addInfoOutput2(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            if (output[switchOutput + 1][0] == 0) return;
            if (switchOutput + 1 <= maxLength) {
                tl.add(TextComponentUtil.translationWithColor(TextFormatting.BLUE, "序列：%s", switchOutput + 2));
                tl.add(new TextComponentTranslation(String.format("X:%s Y:%s Z:%s", output[switchOutput + 1][1], output[switchOutput + 1][2], output[switchOutput + 1][3])));
                tl.add(new TextComponentTranslation(String.format("激光通量:%s", output[switchOutput + 1][4])));
                tl.add(new TextComponentTranslation(String.format("输出权重:%s", weightOutput[switchOutput + 1])));
            }
        });
    }

    protected void addInfoOutput3(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            if (output[switchOutput + 2][0] == 0) return;
            if (switchOutput + 2 <= maxLength) {
                tl.add(TextComponentUtil.translationWithColor(TextFormatting.BLUE, "序列：%s", switchOutput + 3));
                tl.add(new TextComponentTranslation(String.format("X:%s Y:%s Z:%s", output[switchOutput + 2][1], output[switchOutput + 2][2], output[switchOutput + 2][3])));
                tl.add(new TextComponentTranslation(String.format("激光通量:%s", output[switchOutput + 2][4])));
                tl.add(new TextComponentTranslation(String.format("输出权重:%s", weightOutput[switchOutput + 2])));
            }
        });
    }

    protected void addInfoOutput4(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            if (output[switchOutput + 3][0] == 0) return;
            if (switchOutput + 3 <= maxLength) {
                tl.add(TextComponentUtil.translationWithColor(TextFormatting.BLUE, "序列：%s", switchOutput + 4));
                tl.add(new TextComponentTranslation(String.format("X:%s Y:%s Z:%s", output[switchOutput + 3][1], output[switchOutput + 3][2], output[switchOutput + 3][3])));
                tl.add(new TextComponentTranslation(String.format("激光通量:%s", output[switchOutput + 3][4])));
                tl.add(new TextComponentTranslation(String.format("输出权重:%s", weightOutput[switchOutput + 3])));
            }
        });
    }

    /////////////////////////////////////////
    public ItemStack setCard() {
        ItemStack card = new ItemStack(GTQTMetaItems.POS_BINDING_CARD.getMetaItem(), 1, 417);
        NBTTagCompound nodeTagCompound = new NBTTagCompound();
        nodeTagCompound.setInteger("x", x);
        nodeTagCompound.setInteger("y", y);
        nodeTagCompound.setInteger("z", z);
        card.setTagCompound(nodeTagCompound);
        return card;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" AAA ", "  A  ", "  A  ", "     ", "     ")
                .aisle("AAAAA", " AAA ", " BAB ", " B B ", " B B ")
                .aisle("AAAAA", "AAAAA", "AAAAA", "     ", "     ")
                .aisle("AAAAA", " AAA ", " BAB ", " B B ", " B B ")
                .aisle(" AAA ", "  S  ", "  A  ", "     ", "     ")
                .where('S', selfPredicate())
                .where('A', states(getCasingState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setExactLimit(1)))
                .where('B', frames(MaragingSteel250))
                .where(' ', any())
                .build();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("如何高效的传输激光？答：在天上放置一面镜子"));
        tooltip.add(I18n.format("将激光终端绑定至本多方块即可自动进行管理"));
        tooltip.add(I18n.format("默认对所有输出IO进行权重均为1的输出"));
        tooltip.add(I18n.format("最多同时管理64+64台设备"));
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(NQ_TURBINE_CASING);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.NQ_CASING;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.CVD_UNIT_OVERLAY;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntitySBPRC(metaTileEntityId);
    }

    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }

    public boolean hasMaintenanceMechanics() {
        return true;
    }

    public boolean hasMufflerMechanics() {
        return false;
    }
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(double x, double y, double z, float partialTicks) {
        IFastRenderMetaTileEntity.super.renderMetaTileEntity(x, y, z, partialTicks);

        if(isStructureFormed()&& GTQTCoreConfig.OBJRenderSwitch.EnableObj&& GTQTCoreConfig.OBJRenderSwitch.EnableObjSBPRC)
        {
            final int xDir = this.getFrontFacing().getOpposite().getXOffset();
            final int zDir = this.getFrontFacing().getOpposite().getZOffset();
            //机器开启才会进行渲染
            //这是一些opengl的操作,GlStateManager是mc自身封装的一部分方法  前四条详情请看 https://turou.fun/minecraft/legacy-render-tutor/
            //opengl方法一般需要成对出现，实际上他是一个状态机，改装状态后要还原  一般情况按照我这些去写就OK
            GlStateManager.pushAttrib(); //保存变换前的位置和角度
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            FMLClientHandler.instance().getClient().getTextureManager().bindTexture(ObjModels.SpaceLJ_pic); //自带的材质绑定 需要传递一个ResourceLocation
            GlStateManager.translate(x, y, z);//translate是移动方法 这个移动到xyz是默认的 不要动
            GlStateManager.translate(xDir * 2 + 0.5, 150, zDir * 2 + 0.5);//translate是移动方法 这个移动到xyz是默认的 不要动


            float angle = (System.currentTimeMillis() % 36000) / 100.0f; //我写的随时间变化旋转的角度
            //GlStateManager.rotate(90, 0F, 1F, 0F);//rotate是旋转模型的方法  DNA的初始位置不太对 我旋转了一下   四个参数为：旋转角度，xyz轴，可以控制模型围绕哪个轴旋转
            GlStateManager.rotate(angle, 0F, 1F, 0F);//我让dna围绕z轴旋转，角度是实时变化的


            GlStateManager.scale(6, 6, 6);
            // ObjModels.Tree_Model.renderAllWithMtl(); //这个是模型加载器的渲染方法  这是带MTL的加载方式
            ObjModels.SpaceLJ.renderAll(); //这个是模型加载器的渲染方法  这是不带MTL的加载方式
            GlStateManager.popMatrix();//读取变换前的位置和角度(恢复原状) 下面都是还原状态机的语句
            GlStateManager.enableLighting();
            GlStateManager.popAttrib();
            GlStateManager.enableCull();
        }

    }
    //渲染模型的位置
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        //这个影响模型的可视范围，正常方块都是 1 1 1，长宽高各为1，当这个方块离线玩家视线后，obj模型渲染会停止，所以可以适当放大这个大小能让模型有更多角度的可视
        return new AxisAlignedBB(this.getPos().add(-100,-100,-100),this.getPos().add(100,100,100));
    }
    public boolean isGlobalRenderer() {
        return true;
    }
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), true, true);
    }
}
