package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.LaserSystem;

import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.IncrementButtonWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.particle.GTParticleManager;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import keqing.gtqtcore.api.capability.ILaser;
import keqing.gtqtcore.api.metatileentity.MetaTileEntityBaseWithControl;
import keqing.gtqtcore.client.particle.LaserBeamParticle;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static gregtech.api.GTValues.VN;
import static keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility.LASER_INPUT;
import static keqing.gtqtcore.api.unification.GTQTMaterials.MaragingSteel250;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4.TurbineCasingType.NQ_TURBINE_CASING;

public class MetaTileEntitySBPRI extends MetaTileEntityBaseWithControl {
    long Laser;
    int length;
    long l;
    int circuit;
    int dim;
    int[][] io = new int[6][2];
    long[] La = new long[6];
    long[] Se = new long[6];
    long[] Ma = new long[6];

    BlockPos TargetPos;
    boolean findTarget = false;

    public MetaTileEntitySBPRI(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(NQ_TURBINE_CASING);
    }

    public void setMachinePos(BlockPos pos, int dim) {
        findTarget = true;
        TargetPos = pos;
        this.dim = dim;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("length", length);
        data.setLong("Laser", Laser);
        data.setInteger("circuit", circuit);
        data.setInteger("dim", dim);
        for (int i = 0; i < 6; i++) {
            data.setLong("La" + i, La[i]);
            data.setLong("Se" + i, Se[i]);
            data.setLong("Ma" + i, Ma[i]);
        }
        if (TargetPos != null) {
            data.setInteger("X1", TargetPos.getX());
            data.setInteger("Y1", TargetPos.getY());
            data.setInteger("Z1", TargetPos.getZ());
        }
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        length = data.getInteger("length");
        Laser = data.getLong("Laser");
        circuit = data.getInteger("circuit");
        dim = data.getInteger("dim");
        for (int i = 0; i < 6; i++) {
            La[i] = data.getLong("La" + i);
            Se[i] = data.getLong("Se" + i);
            Ma[i] = data.getLong("Ma" + i);
        }
        TargetPos = new BlockPos(data.getInteger("X1"), data.getInteger("Y1"), data.getInteger("Z1"));
    }

    @Override
    protected void updateFormedValid() {
        MetaTileEntity mte = GTUtility.getMetaTileEntity(DimensionManager.getWorld(dim), TargetPos);
        if (mte instanceof MetaTileEntitySBPRC && ((MetaTileEntitySBPRC) mte).isStructureFormed()) {
            findTarget = true;
            writeCustomData(GregtechDataCodes.UPDATE_PARTICLE, this::writeParticles);
        } else {
            findTarget = false;
            TargetPos = null;
            dim = 0;
        }

        l = 0;
        for (int p = 0; p < length; p++) {
            l += this.getAbilities(LASER_INPUT).get(p).Laser();
            io[p][1] = this.getAbilities(LASER_INPUT).get(p).tier();
            La[p] = this.getAbilities(LASER_INPUT).get(p).Laser();
            Se[p] = this.getAbilities(LASER_INPUT).get(p).SetLaser();
            Ma[p] = this.getAbilities(LASER_INPUT).get(p).MaxLaser();
        }
        if (l != Laser) Laser = l;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        int p;
        List<ILaser> i = getAbilities(LASER_INPUT);
        this.length = i.size();

        for (p = 0; p < length; p++) {
            io[p][0] = 1;
        }
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 300, 240);

        // Display
        builder.image(132, 4, 162, 115, GuiTextures.DISPLAY);
        builder.dynamicLabel(135, 8, () -> "高能激光聚焦控制器", 0xFFFFFF);
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
                tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光：%s %s", La[5], VN[GTUtility.getTierByVoltage(La[5])]));
            }
        });
    }

    private void incrementThresholdV(Widget.ClickData clickData) {
        if (circuit < length) this.getAbilities(LASER_INPUT).get(circuit).addVoltage(1);
    }

    private void decrementThresholdV(Widget.ClickData clickData) {
        if (circuit < length) this.getAbilities(LASER_INPUT).get(circuit).addVoltage(-1);
    }

    public void setCurrentA(int parallelAmount) {
        if (circuit < length)
            this.getAbilities(LASER_INPUT).get(circuit).setAmperage(MathHelper.clamp(this.getAbilities(LASER_INPUT).get(circuit).Amperage() + parallelAmount, 0, 1024));
    }

    protected void addTotal(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("输入激光能量:%s", Laser));
        textList.add(new TextComponentTranslation("激光输入IO数量：%s", length));
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("控制器绑定状态:%s", findTarget));
        textList.add(new TextComponentTranslation("目标坐标：%s %s %s D:%s", TargetPos.getX(), TargetPos.getY(), TargetPos.getZ(), dim));
        textList.add(new TextComponentTranslation("仓号:%s", circuit + 1));
        if (io[circuit][0] == 1)
            textList.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "激光输入-等级：%s", io[circuit][1]));
        if (circuit < length)
            textList.add(new TextComponentTranslation("电压：%s 电流：%s", this.getAbilities(LASER_INPUT).get(circuit).Voltage(), this.getAbilities(LASER_INPUT).get(circuit).Amperage()));
        textList.add(new TextComponentTranslation("激光：%s %s", La[circuit], VN[GTUtility.getTierByVoltage(La[circuit])]));
        textList.add(new TextComponentTranslation("阈值：%s %s", Se[circuit], VN[GTUtility.getTierByVoltage(Se[circuit])]));
        textList.add(new TextComponentTranslation("极值：%s %s", Ma[circuit], VN[GTUtility.getTierByVoltage(Ma[circuit])]));
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
                        .or(abilities(LASER_INPUT).setMinGlobalLimited(1).setMaxGlobalLimited(6)))
                .where('B', frames(MaragingSteel250))
                .where(' ', any())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.NQ_CASING;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.HPCA_OVERLAY;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntitySBPRI(metaTileEntityId);
    }

    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("使用 坐标绑定卡 绑定即可链接至棱镜网络"));
        tooltip.add(I18n.format("最多安装六个IO设备"));
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        writeParticles(buf);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        try {
            readParticles(buf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GregtechDataCodes.UPDATE_PARTICLE) {
            try {
                readParticles(buf);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void writeParticles(PacketBuffer buf) {
        NBTTagCompound tag = new NBTTagCompound();
        if (findTarget) {
            tag.setInteger("Sx", this.getPos().getX());
            tag.setInteger("Sy", this.getPos().getY());
            tag.setInteger("Sz", this.getPos().getZ());
            tag.setInteger("Ex", TargetPos.getX());
            tag.setInteger("Ey", TargetPos.getY());
            tag.setInteger("Ez", TargetPos.getZ());

        }
        buf.writeCompoundTag(tag);
    }

    @SideOnly(Side.CLIENT)
    private void readParticles(PacketBuffer buf) throws IOException {
        NBTTagCompound tag = buf.readCompoundTag();
        if (tag.hasKey("Ex")) {
            BlockPos Spos = new BlockPos(tag.getInteger("Sx"), tag.getInteger("Sy"), tag.getInteger("Sz"));
            BlockPos Epos = new BlockPos(tag.getInteger("Ex"), tag.getInteger("Ey"), tag.getInteger("Ez"));
            operateClient(Spos, Epos, 20);
        }

    }

    @SideOnly(Side.CLIENT)
    public void operateClient(BlockPos Spos, BlockPos Epos, int age) {
        GTParticleManager.INSTANCE.addEffect(new LaserBeamParticle(this, Spos, Epos.add(0, 150, 0), age));
    }
}
