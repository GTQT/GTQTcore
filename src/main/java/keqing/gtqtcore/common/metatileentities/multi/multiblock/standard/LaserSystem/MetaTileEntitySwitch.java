package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.LaserSystem;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.IncrementButtonWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import keqing.gtqtcore.api.capability.ILaser;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.api.metaileentity.MetaTileEntityBaseWithControl;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

import static gregtech.api.GTValues.V;
import static gregtech.api.GTValues.VN;
import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.LASER_INPUT;
import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.LASER_OUTPUT;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4.TurbineCasingType.NQ_TURBINE_CASING;

public class MetaTileEntitySwitch extends MetaTileEntityBaseWithControl {
    long Laser;//当前的激光
    long More;
    long Cost;
    int inputNum;
    int outputNum;
    int circuit;//指针
    int[][] io = new int[6][2];
    long[] La = new long[6];
    long[] Se = new long[6];
    long[] Ma = new long[6];

    public MetaTileEntitySwitch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(NQ_TURBINE_CASING);
    }

    //第一位 1-》输入 2-》输出
    //第二位 等级

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("inputNum", inputNum);
        data.setInteger("outputNum", outputNum);
        data.setLong("Laser", Laser);
        data.setLong("Cost", Cost);
        data.setLong("More", More);
        data.setInteger("circuit", circuit);
        for (int i = 0; i < 6; i++) {
            data.setLong("La" + i, La[i]);
            data.setLong("Se" + i, Se[i]);
            data.setLong("Ma" + i, Ma[i]);
        }
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        inputNum = data.getInteger("inputNum");
        outputNum = data.getInteger("outputNum");
        Laser = data.getLong("Laser");
        Cost = data.getLong("Cost");
        More = data.getLong("More");
        circuit = data.getInteger("circuit");
        for (int i = 0; i < 6; i++) {
            La[i] = data.getLong("La" + i);
            Se[i] = data.getLong("Se" + i);
            Ma[i] = data.getLong("Ma" + i);
        }
    }

    @Override
    protected void updateFormedValid() {
        long l = 0;
        long c = 0;
        for (int p = 0; p < 6; p++) {
            if (p < inputNum) {
                l += this.getAbilities(LASER_INPUT).get(p).Laser();
                io[p][1] = this.getAbilities(LASER_INPUT).get(p).tier();
                La[p] = this.getAbilities(LASER_INPUT).get(p).Laser();
                Se[p] = this.getAbilities(LASER_INPUT).get(p).SetLaser();
                Ma[p] = this.getAbilities(LASER_INPUT).get(p).MaxLaser();
                //this.getAbilities(LASER_INPUT).get(p).setMachinePos(this.getPos());
            } else if (p < inputNum + outputNum) {
                c += this.getAbilities(LASER_OUTPUT).get(p - inputNum).Laser();
                io[p][1] = this.getAbilities(LASER_OUTPUT).get(p - inputNum).tier();
                La[p] = this.getAbilities(LASER_OUTPUT).get(p - inputNum).Laser();
                Se[p] = this.getAbilities(LASER_OUTPUT).get(p - inputNum).SetLaser();
                Ma[p] = this.getAbilities(LASER_OUTPUT).get(p - inputNum).MaxLaser();
                this.getAbilities(LASER_OUTPUT).get(p - inputNum).setMachinePos(this.getPos());
            }
        }
        if (Laser != l) Laser = l;
        if (Cost != c) Cost = c;

        More = Laser - Cost;
        for (int p = inputNum; p < inputNum + outputNum; p++) {
            if (Se[p] > La[p] && More > 0) {
                if (More > Se[p] - La[p]) {
                    this.getAbilities(LASER_OUTPUT).get(p - inputNum).setLaser(Se[p]);

                } else {
                    this.getAbilities(LASER_OUTPUT).get(p - inputNum).setLaser(La[p] + More);

                }
            }
        }
        if (More < 0) {
            for (int p = inputNum; p < inputNum + outputNum; p++) {
                if (More >= 0) return;
                if (Math.abs(More) <= La[p]) {
                    this.getAbilities(LASER_OUTPUT).get(p - inputNum).setLaser(La[p] + More);
                    More = 0;
                    return;
                } else {
                    More += La[p];
                    this.getAbilities(LASER_OUTPUT).get(p - inputNum).setLaser(0);
                }
            }
        }
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        int p;
        List<ILaser> i = getAbilities(LASER_INPUT);
        this.inputNum = i.size();

        List<ILaser> o = getAbilities(LASER_OUTPUT);
        this.outputNum = o.size();

        for (p = 0; p < 6; p++) {
            if (p < inputNum) io[p][0] = 1;
            else if (p < inputNum + outputNum) io[p][0] = 2;
            else io[p][0] = 0;
        }
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 300, 240);

        // Display
        builder.image(132, 4, 162, 115, GuiTextures.DISPLAY);
        builder.dynamicLabel(135, 8, () -> "激光能源分配器", 0xFFFFFF);
        builder.widget((new AdvancedTextWidget(135, 20, this::addDisplayText, 16777215)).setMaxWidthLimit(160).setClickHandler(this::handleDisplayClick));

        int j = 0;
        //1号
        builder.image(3, 4 + j * 30, 130, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 30, this::addInfo1, 16777215)).setMaxWidthLimit(120).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(118, 4 + j * 30, 15, 30, "->", data -> circuit = 0));
        j++;
        //2号
        builder.image(3, 4 + j * 30, 130, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 30, this::addInfo2, 16777215)).setMaxWidthLimit(120).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(118, 4 + j * 30, 15, 30, "->", data -> circuit = 1));
        j++;
        //3号
        builder.image(3, 4 + j * 30, 130, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 30, this::addInfo3, 16777215)).setMaxWidthLimit(120).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(118, 4 + j * 30, 15, 30, "->", data -> circuit = 2));
        j++;
        //4号
        builder.image(3, 4 + j * 30, 130, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 30, this::addInfo4, 16777215)).setMaxWidthLimit(120).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(118, 4 + j * 30, 15, 30, "->", data -> circuit = 3));
        j++;
        //5号
        builder.image(3, 4 + j * 30, 130, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 30, this::addInfo5, 16777215)).setMaxWidthLimit(120).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(118, 4 + j * 30, 15, 30, "->", data -> circuit = 4));
        j++;
        //6号
        builder.image(3, 4 + j * 30, 130, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8 + j * 30, this::addInfo6, 16777215)).setMaxWidthLimit(120).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(118, 4 + j * 30, 15, 30, "->", data -> circuit = 5));


        // Display
        builder.image(3, 184, 130, 52, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 188, this::addTotal, 16777215)).setMaxWidthLimit(130).setClickHandler(this::handleDisplayClick));


        builder.widget(new ClickButtonWidget(132, 120, 60, 18, "V +1", this::incrementThresholdV));
        builder.widget(new ClickButtonWidget(200, 120, 60, 18, "V -1", this::decrementThresholdV));

        builder.widget(new IncrementButtonWidget(132, 140, 60, 18, 1, 4, 16, 64, this::setCurrentA)
                .setDefaultTooltip()
                .setShouldClientCallback(false));

        builder.widget(new IncrementButtonWidget(200, 140, 60, 18, -1, -4, -16, -64, this::setCurrentA)
                .setDefaultTooltip()
                .setShouldClientCallback(false));


        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 132, 160);
        return builder;
    }

    protected void addInfo1(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            if (isStructureFormed()) {
                if (io[0][0] == 0) return;
                if (io[0][0] == 1)
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光输入-等级：%s", io[0][1]));
                if (io[0][0] == 2)
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光输出-等级：%s", io[0][1]));
                tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光：%s %s", La[0], VN[GTUtility.getTierByVoltage(La[0])]));
            }
        });
    }

    protected void addInfo2(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            if (isStructureFormed()) {
                if (io[1][0] == 0) return;
                if (io[1][0] == 1)
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光输入-等级：%s", io[1][1]));
                if (io[1][0] == 2)
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光输出-等级：%s", io[1][1]));
                tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光：%s %s", La[1], VN[GTUtility.getTierByVoltage(La[1])]));
            }
        });
    }

    protected void addInfo3(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            if (isStructureFormed()) {
                if (io[2][0] == 0) return;
                if (io[2][0] == 1)
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光输入-等级：%s", io[2][1]));
                if (io[2][0] == 2)
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光输出-等级：%s", io[2][1]));
                tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光：%s %s", La[2], VN[GTUtility.getTierByVoltage(La[2])]));
            }
        });
    }

    protected void addInfo4(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            if (isStructureFormed()) {
                if (io[3][0] == 0) return;
                if (io[3][0] == 1)
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光输入-等级：%s", io[3][1]));
                if (io[3][0] == 2)
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光输出-等级：%s", io[3][1]));
                tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光：%s %s", La[3], VN[GTUtility.getTierByVoltage(La[3])]));
            }
        });
    }

    protected void addInfo5(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            if (isStructureFormed()) {
                if (io[4][0] == 0) return;
                if (io[4][0] == 1)
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光输入-等级：%s", io[4][1]));
                if (io[4][0] == 2)
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光输出-等级：%s", io[4][1]));
                tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光：%s %s", La[4], VN[GTUtility.getTierByVoltage(La[4])]));
            }
        });
    }

    protected void addInfo6(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            if (isStructureFormed()) {
                if (io[5][0] == 0) return;
                if (io[5][0] == 1)
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光输入-等级：%s", io[5][1]));
                if (io[5][0] == 2)
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光输出-等级：%s", io[5][1]));
                tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光：%s %s", La[5], VN[GTUtility.getTierByVoltage(La[5])]));
            }
        });
    }

    private void incrementThresholdV(Widget.ClickData clickData) {
        if (circuit < inputNum) this.getAbilities(LASER_INPUT).get(circuit).addVoltage(1);
        else if (circuit < inputNum + outputNum) {
            if (More >= this.getAbilities(LASER_OUTPUT).get(circuit - inputNum).Voltage() * 4L) {
                this.getAbilities(LASER_OUTPUT).get(circuit - inputNum).addVoltage(1);
                this.getAbilities(LASER_OUTPUT).get(circuit - inputNum).setLaser(this.getAbilities(LASER_OUTPUT).get(circuit - inputNum).Amperage() * V[this.getAbilities(LASER_OUTPUT).get(circuit - inputNum).Voltage()]);
            }
        }
    }

    private void decrementThresholdV(Widget.ClickData clickData) {
        if (circuit < inputNum) this.getAbilities(LASER_INPUT).get(circuit).addVoltage(-1);
        else if (circuit < inputNum + outputNum) this.getAbilities(LASER_OUTPUT).get(circuit - inputNum).addVoltage(-1);
    }

    public void setCurrentA(int i) {
        if(i>0)
        {
            if (circuit < inputNum)this.getAbilities(LASER_INPUT).get(circuit).setAmperage(MathHelper.clamp( this.getAbilities(LASER_INPUT).get(circuit).Amperage() + i, 0, 1024));
            else if (circuit < inputNum + outputNum) {
                if (More >= this.getAbilities(LASER_OUTPUT).get(circuit - inputNum).Voltage()) {
                    this.getAbilities(LASER_OUTPUT).get(circuit - inputNum).setAmperage(MathHelper.clamp( this.getAbilities(LASER_OUTPUT).get(circuit).Amperage() + i, 0, 1024));
                    this.getAbilities(LASER_OUTPUT).get(circuit - inputNum).setLaser(this.getAbilities(LASER_OUTPUT).get(circuit - inputNum).Amperage() * V[this.getAbilities(LASER_OUTPUT).get(circuit - inputNum).Voltage()]);
                }
            }
        }
        else
        {
            if (circuit < inputNum)this.getAbilities(LASER_INPUT).get(circuit).setAmperage(MathHelper.clamp( this.getAbilities(LASER_INPUT).get(circuit).Amperage() + i, 0, 1024));
            else if (circuit < inputNum + outputNum)
                this.getAbilities(LASER_OUTPUT).get(circuit - inputNum).setAmperage(MathHelper.clamp( this.getAbilities(LASER_OUTPUT).get(circuit - inputNum).Amperage() + i, 0, 1024));
        }
    }
    protected void addTotal(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("输入激光能量:%s", Laser));
        textList.add(new TextComponentTranslation("输出激光能量:%s", Cost));
        textList.add(new TextComponentTranslation("盈余激光能量:%s", More));
        textList.add(new TextComponentTranslation("激光输入：%s 激光输出：%s", inputNum, outputNum));
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("仓号:%s", circuit + 1));
        if (io[circuit][0] == 1)
            textList.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光输入-等级：%s", io[circuit][1]));
        if (io[circuit][0] == 2)
            textList.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光输出-等级：%s", io[circuit][1]));
        if (circuit < inputNum)
            textList.add(new TextComponentTranslation("电压：%s 电流：%s", this.getAbilities(LASER_INPUT).get(circuit).Voltage(), this.getAbilities(LASER_INPUT).get(circuit).Amperage()));
        else if (circuit < inputNum + outputNum)
            textList.add(new TextComponentTranslation("电压：%s 电流：%s", this.getAbilities(LASER_OUTPUT).get(circuit - inputNum).Voltage(), this.getAbilities(LASER_OUTPUT).get(circuit - inputNum).Amperage()));
        textList.add(new TextComponentTranslation("激光：%s %s", La[circuit], VN[GTUtility.getTierByVoltage(La[circuit])]));
        textList.add(new TextComponentTranslation("阈值：%s %s", Se[circuit], VN[GTUtility.getTierByVoltage(Se[circuit])]));
        textList.add(new TextComponentTranslation("极值：%s %s", Ma[circuit], VN[GTUtility.getTierByVoltage(Ma[circuit])]));
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("高能激光分配单元"));
        tooltip.add(I18n.format("最多支持总计6台高能激光源或靶仓的能量交换工作"));
        tooltip.add(I18n.format("在UI或各仓内控制工作状态"));
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("MMM", "MMM", "MMM")
                .aisle("MMM", "MMM", "MMM")
                .aisle("MMM", "MCM", "MMM")
                .where('C', selfPredicate())
                .where('M', states(getCasingState()).setMaxGlobalLimited(20)
                        .or(abilities(LASER_INPUT).setMinGlobalLimited(1).setMaxGlobalLimited(5))
                        .or(abilities(LASER_OUTPUT).setMinGlobalLimited(1).setMaxGlobalLimited(5)))
                .build();
    }

    public boolean hasMaintenanceMechanics() {
        return false;
    }

    public boolean hasMufflerMechanics() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.NQ_CASING;
    }

    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntitySwitch(metaTileEntityId);
    }

    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }
}
