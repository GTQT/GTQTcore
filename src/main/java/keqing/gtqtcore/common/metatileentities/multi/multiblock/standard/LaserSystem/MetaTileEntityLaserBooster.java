package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.LaserSystem;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.IncrementButtonWidget;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.IRenderSetup;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.shader.postprocessing.BloomEffect;
import gregtech.client.shader.postprocessing.BloomType;
import gregtech.client.utils.BloomEffectUtil;
import gregtech.client.utils.EffectRenderContext;
import gregtech.client.utils.IBloomEffect;
import gregtech.client.utils.RenderBufferHelper;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockFusionCasing;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.metaileentity.MetaTileEntityBaseWithControl;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static gregtech.api.GTValues.VN;
import static gregtech.api.metatileentity.multiblock.MultiblockAbility.INPUT_ENERGY;
import static gregtech.api.metatileentity.multiblock.MultiblockAbility.INPUT_LASER;
import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.LASER_INPUT;
import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.LASER_OUTPUT;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4.TurbineCasingType.NQ_MACHINE_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4.TurbineCasingType.NQ_TURBINE_CASING;
import static net.minecraft.util.EnumFacing.Axis.Y;

public class MetaTileEntityLaserBooster extends MetaTileEntityBaseWithControl implements IBloomEffect, IFastRenderMetaTileEntity {

    protected static final int NO_COLOR = 0;
    public int Amperage;
    public int Voltage;
    long Laser;//当前的激光
    long MaxLaser;
    long SetLaser;// 设定 的 最大激光强度
    int tier;
    boolean backA;
    int RadomTime;
    private int fusionRingColor = NO_COLOR;
    private boolean registeredBloomRenderTicket;

    public MetaTileEntityLaserBooster(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    private static IBlockState getUniqueCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(NQ_TURBINE_CASING);
    }

    private static IBlockState getThirdCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(NQ_MACHINE_CASING);
    }

    private static IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS);
    }

    private static IBlockState getCoilState() {
        return MetaBlocks.FUSION_CASING.getState(BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.IRIDIUM_CASING);
    }

    private static IBlockState getSecondCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.ADVANCED_FILTER_CASING);
    }

    private static BloomType getBloomType() {
        ConfigHolder.FusionBloom fusionBloom = ConfigHolder.client.shader.fusionBloom;
        return BloomType.fromValue(fusionBloom.useShader ? fusionBloom.bloomStyle : -1);
    }

    public TraceabilityPredicate getFramePredicate() {
        return frames(Materials.Naquadah);
    }

    @Override
    protected void updateFormedValid() {
        Amperage = this.getAbilities(LASER_OUTPUT).get(0).Amperage();
        Voltage = this.getAbilities(LASER_OUTPUT).get(0).Voltage();
        Laser = this.getAbilities(LASER_OUTPUT).get(0).Laser();
        MaxLaser = this.getAbilities(LASER_OUTPUT).get(0).MaxLaser();
        SetLaser = this.getAbilities(LASER_OUTPUT).get(0).SetLaser();
        tier = this.getAbilities(LASER_OUTPUT).get(0).tier();
        this.getAbilities(LASER_OUTPUT).get(0).setMachinePos(this.getPos());

        if (isWorkingEnabled()) {
            setActive(true);
            if (this.energyContainer.getEnergyStored() >= 0) {
                this.getAbilities(LASER_OUTPUT).get(0).setLaser(Math.min(this.getAbilities(LASER_OUTPUT).get(0).SetLaser(), this.energyContainer.getEnergyStored()));
                this.energyContainer.removeEnergy(Math.min(this.getAbilities(LASER_OUTPUT).get(0).SetLaser(), this.energyContainer.getEnergyStored()));
            } else this.getAbilities(LASER_OUTPUT).get(0).setLaser(0);
        } else {
            this.getAbilities(LASER_OUTPUT).get(0).setLaser(0);
            setActive(false);
        }
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);

        List<IEnergyContainer> energyContainer = new ArrayList<>(this.getAbilities(MultiblockAbility.INPUT_ENERGY));
        energyContainer.addAll(this.getAbilities(INPUT_LASER));
        this.energyContainer = new EnergyContainerList(energyContainer);

        this.getAbilities(LASER_OUTPUT).get(0).setMachinePos(this.getPos());
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 260, 240);

        builder.image(90, 10, 165, 145, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(100, 15, this::addDisplayText, 16777215)).setMaxWidthLimit(181));

        builder.widget(new ClickButtonWidget(7, 10, 80, 20, "V -1", this::decrementThresholdV));
        builder.widget(new ClickButtonWidget(7, 40, 80, 20, "V +1", this::incrementThresholdV));

        builder.widget(new IncrementButtonWidget(7, 70, 80, 20, 1, 4, 16, 64, this::setCurrentA)
                .setDefaultTooltip()
                .setShouldClientCallback(false));

        builder.widget(new IncrementButtonWidget(7, 100, 80, 20, -1, -4, -16, -64, this::setCurrentA)
                .setDefaultTooltip()
                .setShouldClientCallback(false));

        builder.widget(new ClickButtonWidget(7, 130, 80, 20, "I/O", data -> setWorkingEnabled(!isWorkingEnabled())));

        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 92, 160);
        return builder;
    }

    private void incrementThresholdV(Widget.ClickData clickData) {
        this.getAbilities(LASER_OUTPUT).get(0).addVoltage(1);
    }

    private void decrementThresholdV(Widget.ClickData clickData) {
        this.getAbilities(LASER_OUTPUT).get(0).addVoltage(-1);
    }

    public void setCurrentA(int i) {
        this.getAbilities(LASER_INPUT).get(0).setAmperage(MathHelper.clamp(this.getAbilities(LASER_INPUT).get(0).Amperage() + i, 0, 1024));
    }

    protected void addDisplayText(List<ITextComponent> textList) {
        if (!isStructureFormed()) return;
        textList.add(new TextComponentString("超高能激光激发器  端-等级: " + tier));
        textList.add(new TextComponentString("开关 是否传输: " + isWorkingEnabled()));
        textList.add(new TextComponentString("实际 传输电压: " + Voltage));
        textList.add(new TextComponentString("实际 传输电流: " + Amperage));
        textList.add(new TextComponentString("实际 传输激光: " + Laser + " " + VN[GTUtility.getTierByVoltage(Laser)]));
        textList.add(new TextComponentString("额定 传输激光: " + SetLaser + " " + VN[GTUtility.getTierByVoltage(SetLaser)]));
        textList.add(new TextComponentString("最大 传输激光: " + MaxLaser + " " + VN[GTUtility.getTierByVoltage(MaxLaser)]));

        textList.add(new TextComponentString("能量 缓存容量： " + energyContainer.getEnergyStored()));
        textList.add(new TextComponentString("能量 容量上限: " + energyContainer.getEnergyCapacity()));
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "              O              ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "              A              ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             BBB             ", "            BCCCB            ", "           BCCCCCB           ", "           BCCACCB           ", "           BCCCCCB           ", "            BCCCB            ", "             BBB             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             BBB             ", "            BAAAB            ", "           BAAAAAB           ", "           BAAAAAB           ", "           BAAAAAB           ", "            BAAAB            ", "             BBB             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             BBB             ", "            BCCCB            ", "           BCCCCCB           ", "           BCCGCCB           ", "           BCCCCCB           ", "            BCCCB            ", "             BBB             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             CCC             ", "             CGC             ", "             CCC             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "            BBBBB            ", "          BBBCCCBBB          ", "          BCCCCCCCB          ", "         BBCCCCCCCBB         ", "         BCCCCCCCCCB         ", "         BCCCCGCCCCB         ", "         BCCCCCCCCCB         ", "         BBCCCCCCCBB         ", "          BCCCCCCCB          ", "          BBBCCCBBB          ", "            BBBBB            ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "            BBBBB            ", "          BBBDDDBBB          ", "          BDD   DDB          ", "         BBD     DBB         ", "         BD       DB         ", "         BD   G   DB         ", "         BD       DB         ", "         BBD     DBB         ", "          BDD   DD           ", "          BBBDDDB B          ", "            BBBBB            ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "            BBBBB            ", "          BBBDDDBBB          ", "          BDD   DDB          ", "         BBD     DBB         ", "         BD       DB         ", "         BD   G   DB         ", "         BD       DB         ", "         BBD     DBB         ", "          BDD   DDB          ", "          BBBDDDBBB          ", "            BBBBB            ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "              B              ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "         BD   G   DB         ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "              B              ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             BBB             ", "             DDD             ", "           DDGGGDD           ", "           DG   GD           ", "         BDG     GDB         ", "         BDG  G  GDB         ", "         BDG     GDB         ", "           DG   GD           ", "           DDGGGDD           ", "             DDD             ", "             BBB             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D   G   D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D   G   D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D   G   D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D   G   D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D   G   D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("             EEE             ", "          EEEEEEEEE          ", "        EEEE     EEEE        ", "      EEE           EEE      ", "     EEE             EEE     ", "    EE                 EE    ", "   EE                   EE   ", "   EE                   EE   ", "  EE                     EE  ", "  E                       E  ", " EE          DDD          EE ", " EE        DD   DD        EE ", " E         D     D         E ", "EE        D       D        EE", "EE        D   G   D        EE", "EE        D       D        EE", " E         D     D         E ", " EE        DD   DD        EE ", " EE          DDD          EE ", "  E                       E  ", "  EE                     EE  ", "   EE                   EE   ", "   EE                   EE   ", "    EE                 EE    ", "     EEE             EEE     ", "      EEE           EEE      ", "        EEEE     EEEE        ", "          EEEEEEEEE          ", "             EEE             ")
                .aisle("                             ", "                             ", "           EEEEEEE           ", "         EEE     EEE         ", "       EEE         EEE       ", "      EE             EE      ", "     EE               EE     ", "    EE                 EE    ", "    E                   E    ", "   EE                   EE   ", "   E         DDD         E   ", "  EE       DDGGGDD       EE  ", "  E        DG   GD        E  ", "  E       DG     GD       E  ", "  E       DG  G  GD       E  ", "  E       DG     GD       E  ", "  E        DG   GD        E  ", "  EE       DDGGGDD       EE  ", "   E         DDD         E   ", "   EE                   EE   ", "    E                   E    ", "    EE                 EE    ", "     EE               EE     ", "      EE             EE      ", "       EEE         EEE       ", "         EEE     EEE         ", "           EEEEEEE           ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "           EEEEEEE           ", "         EEE     EEE         ", "       EEE         EEE       ", "      EE             EE      ", "     EE               EE     ", "     E                 E     ", "    EE                 EE    ", "    E        DDD        E    ", "   EE      DD   DD      EE   ", "   E       D     D       E   ", "   E      D       D      E   ", "   E      D   G   D      E   ", "   E      D       D      E   ", "   E       D     D       E   ", "   EE      DD   DD      EE   ", "    E        DDD        E    ", "    EE                 EE    ", "     E                 E     ", "     EE               EE     ", "      EE             EE      ", "       EEE         EEE       ", "         EEE     EEE         ", "           EEEEEEE           ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D   G   D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D   G   D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D   G   D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D   G   D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D   G   D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             BBB             ", "             DDD             ", "           DDGGGDD           ", "           DG   GD           ", "         BDG     GDB         ", "         BDG  G  GDB         ", "         BDG     GDB         ", "           DG   GD           ", "           DDGGGDD           ", "             DDD             ", "             BBB             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "              B              ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "         BD   G   DB         ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "              B              ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "            BBBBB            ", "          BBBDDDBBB          ", "          BDD   DDB          ", "         BBD     DBB         ", "         BD       DB         ", "         BD   G   DB         ", "         BD       DB         ", "         BBD     DBB         ", "          BDD   DDB          ", "          BBBDDDBBB          ", "            BBBBB            ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "            BBBBB            ", "          BBBDDDBBB          ", "          BDD   DDB          ", "         BBD     DBB         ", "         BD       DB         ", "         BD   G   DB         ", "         BD       DB         ", "         BBD     DBB         ", "          BDD   DDB          ", "          BBBDDDBBB          ", "            BBBBB            ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "            BBBBB            ", "          BBBCCCBBB          ", "          BCCCCCCCB          ", "         BBCCCCCCCBB         ", "         BCCCCCCCCCB         ", "         BCCCCGCCCCB         ", "         BCCCCCCCCCB         ", "         BBCCCCCCCBB         ", "          BCCCCCCCB          ", "          BBBCCCBBB          ", "            BBBBB            ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             CCC             ", "             CGC             ", "             CCC             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             BBB             ", "            BCCCB            ", "           BCCCCCB           ", "           BCCGCCB           ", "           BCCCCCB           ", "            BCCCB            ", "             BBB             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             BBB             ", "            BAAAB            ", "           BAAAAAB           ", "           BAAAAAB           ", "           BAAAAAB           ", "            BAAAB            ", "             BBB             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             BBB             ", "            BCCCB            ", "           BCCCCCB           ", "           BCCACCB           ", "           BCCCCCB           ", "            BCCCB            ", "             BBB             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "              A              ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "              S              ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ")
                .where('S', this.selfPredicate())
                .where('O', abilities(LASER_OUTPUT))
                .where('D', states(getCasingState())
                        .or(abilities(INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(16))
                        .or(abilities(INPUT_LASER).setMinGlobalLimited(0).setMaxGlobalLimited(4))
                )
                .where('B', getFramePredicate())
                .where('C', states(getGlassState()))
                .where('A', states(getSecondCasingState()))

                .where('E', states(getUniqueCasingState()))

                .where('F', states(getThirdCasingState()))
                .where('G', states(getCoilState()))
                .where(' ', any())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.IRIDIUM_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLaserBooster(metaTileEntityId);
    }

    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }

    protected int getFusionRingColor() {
        return this.fusionRingColor;
    }

    protected void setFusionRingColor(int fusionRingColor) {
        if (this.fusionRingColor != fusionRingColor) {
            this.fusionRingColor = fusionRingColor;
        }
    }

    protected boolean hasFusionRingColor() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(double x, double y, double z, float partialTicks) {
        if (this.hasFusionRingColor() && !this.registeredBloomRenderTicket) {
            this.registeredBloomRenderTicket = true;
            BloomEffectUtil.registerBloomRender(FusionBloomSetup.INSTANCE, getBloomType(), this, this);
        }
    }

    @Override
    public void update() {
        super.update();
        if (!backA) if (RadomTime <= 10) RadomTime++;
        if (backA) if (RadomTime >= -10) RadomTime--;
        if (RadomTime == 10) {
            backA = true;
        }
        if (RadomTime == -10) {
            backA = false;
        }
        setFusionRingColor(0xFF000000 + RadomTime * 1250 * 50);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderBloomEffect(BufferBuilder buffer, EffectRenderContext context) {
        int color = this.getFusionRingColor();


        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        EnumFacing relativeBack = RelativeDirection.BACK.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());

        if (isStructureFormed()) {
            if (this.getFrontFacing().getAxis() == Y) {
                RenderBufferHelper.renderRing(buffer,
                        getPos().getX() - context.cameraX() + relativeBack.getXOffset() + 0.5,
                        getPos().getY() - context.cameraY() + relativeBack.getYOffset() * 18 + 0.5,
                        getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() + 0.5,
                        10, 0.2, 10, 20,
                        r, g, b, a, this.getFrontFacing().getAxis());

                RenderBufferHelper.renderRing(buffer,
                        getPos().getX() - context.cameraX() + relativeBack.getXOffset() + 0.5,
                        getPos().getY() - context.cameraY() + relativeBack.getYOffset() * 18 + 0.5,
                        getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() + 0.5,
                        2, 0.2, 10, 20,
                        r, g, b, a, this.getFrontFacing().getAxis());

                RenderBufferHelper.renderRing(buffer,
                        getPos().getX() - context.cameraX() + relativeBack.getXOffset() + 0.5,
                        getPos().getY() - context.cameraY() + relativeBack.getYOffset() * 25 + 0.5,
                        getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() + 0.5,
                        2, 0.2, 10, 20,
                        r, g, b, a, this.getFrontFacing().getAxis());

                RenderBufferHelper.renderRing(buffer,
                        getPos().getX() - context.cameraX() + relativeBack.getXOffset() + 0.5,
                        getPos().getY() - context.cameraY() + relativeBack.getYOffset() * 11 + 0.5,
                        getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() + 0.5,
                        2, 0.2, 10, 20,
                        r, g, b, a, this.getFrontFacing().getAxis());
            } else {
                RenderBufferHelper.renderRing(buffer,
                        getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 18 + 0.5,
                        getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 0.5,
                        getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 18 + 0.5,
                        10, 0.2, 10, 20,
                        r, g, b, a, this.getFrontFacing().getAxis());

                RenderBufferHelper.renderRing(buffer,
                        getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 18 + 0.5,
                        getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 0.5,
                        getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 18 + 0.5,
                        2, 0.2, 10, 20,
                        r, g, b, a, this.getFrontFacing().getAxis());

                RenderBufferHelper.renderRing(buffer,
                        getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 25 + 0.5,
                        getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 0.5,
                        getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 25 + 0.5,
                        2, 0.2, 10, 20,
                        r, g, b, a, this.getFrontFacing().getAxis());

                RenderBufferHelper.renderRing(buffer,
                        getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 11 + 0.5,
                        getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 0.5,
                        getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 11 + 0.5,
                        2, 0.2, 10, 20,
                        r, g, b, a, this.getFrontFacing().getAxis());
            }
        }

    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRenderBloomEffect(EffectRenderContext context) {
        return this.hasFusionRingColor();
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        EnumFacing relativeRight = RelativeDirection.RIGHT.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());
        EnumFacing relativeBack = RelativeDirection.BACK.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());

        return new AxisAlignedBB(
                this.getPos().offset(relativeBack).offset(relativeRight, 6),
                this.getPos().offset(relativeBack, 13).offset(relativeRight.getOpposite(), 6));
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 0;
    }

    @Override
    public boolean isGlobalRenderer() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    private static final class FusionBloomSetup implements IRenderSetup {

        private static final FusionBloomSetup INSTANCE = new FusionBloomSetup();

        float lastBrightnessX;
        float lastBrightnessY;

        @Override
        public void preDraw(BufferBuilder buffer) {
            BloomEffect.strength = (float) ConfigHolder.client.shader.fusionBloom.strength;
            BloomEffect.baseBrightness = (float) ConfigHolder.client.shader.fusionBloom.baseBrightness;
            BloomEffect.highBrightnessThreshold = (float) ConfigHolder.client.shader.fusionBloom.highBrightnessThreshold;
            BloomEffect.lowBrightnessThreshold = (float) ConfigHolder.client.shader.fusionBloom.lowBrightnessThreshold;
            BloomEffect.step = 1;

            lastBrightnessX = OpenGlHelper.lastBrightnessX;
            lastBrightnessY = OpenGlHelper.lastBrightnessY;
            GlStateManager.color(1, 1, 1, 1);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GlStateManager.disableTexture2D();

            buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
        }

        @Override
        public void postDraw(BufferBuilder buffer) {
            Tessellator.getInstance().draw();

            GlStateManager.enableTexture2D();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
        }
    }
}
