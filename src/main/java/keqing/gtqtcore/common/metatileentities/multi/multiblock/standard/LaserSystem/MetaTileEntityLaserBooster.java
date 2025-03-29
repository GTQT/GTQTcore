package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.LaserSystem;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
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
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.metaileentity.MetaTileEntityBaseWithControl;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static gregtech.api.GTValues.VN;
import static gregtech.api.metatileentity.multiblock.MultiblockAbility.INPUT_ENERGY;
import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.LASER_INPUT;
import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.LASER_OUTPUT;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4.TurbineCasingType.NQ_MACHINE_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4.TurbineCasingType.NQ_TURBINE_CASING;

public class MetaTileEntityLaserBooster extends MetaTileEntityBaseWithControl {

    public int Amperage;
    public int Voltage;
    long Laser;//当前的激光
    long MaxLaser;
    long SetLaser;// 设定 的 最大激光强度
    int tier;

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

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.IRIDIUM_CASING);
    }

    private static IBlockState getSecondCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.ADVANCED_FILTER_CASING);
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
            if (this.energyContainer.getEnergyStored() >= 0) {
                this.getAbilities(LASER_OUTPUT).get(0).setLaser(Math.min(this.getAbilities(LASER_OUTPUT).get(0).SetLaser(), this.energyContainer.getEnergyStored()));
                this.energyContainer.removeEnergy(Math.min(this.getAbilities(LASER_OUTPUT).get(0).SetLaser(), this.energyContainer.getEnergyStored()));
            } else this.getAbilities(LASER_OUTPUT).get(0).setLaser(0);
        } else this.getAbilities(LASER_OUTPUT).get(0).setLaser(0);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);

        List<IEnergyContainer> energyContainer = new ArrayList<>(this.getAbilities(MultiblockAbility.INPUT_ENERGY));
        energyContainer.addAll(this.getAbilities(MultiblockAbility.INPUT_LASER));
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
        this.getAbilities(LASER_INPUT).get(0).setAmperage(MathHelper.clamp( this.getAbilities(LASER_INPUT).get(0).Amperage() + i, 0, 1024));
    }

    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentString("激光传输器输出  端-等级: " + tier));
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
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "              O              ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "              A              ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             BBB             ", "            BCCCB            ", "           BCCCCCB           ", "           BCCACCB           ", "           BCCCCCB           ", "            BCCCB            ", "             BBB             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             BBB             ", "            BAAAB            ", "           BAAAAAB           ", "           BAAAAAB           ", "           BAAAAAB           ", "            BAAAB            ", "             BBB             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             BBB             ", "            BCCCB            ", "           BCCCCCB           ", "           BCCCCCB           ", "           BCCCCCB           ", "            BCCCB            ", "             BBB             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             CCC             ", "             CCC             ", "             CCC             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "            BBBBB            ", "          BBBCCCBBB          ", "          BCCCCCCCB          ", "         BBCCCCCCCBB         ", "         BCCCCCCCCCB         ", "         BCCCCCCCCCB         ", "         BCCCCCCCCCB         ", "         BBCCCCCCCBB         ", "          BCCCCCCCB          ", "          BBBCCCBBB          ", "            BBBBB            ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "            BBBBB            ", "          BBBDDDBBB          ", "          BDD   DDB          ", "         BBD     DBB         ", "         BD       DB         ", "         BD       DB         ", "         BD       DB         ", "         BBD     DBB         ", "          BDD   DD           ", "          BBBDDDB B          ", "            BBBBB            ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "            BBBBB            ", "          BBBDDDBBB          ", "          BDD   DDB          ", "         BBD     DBB         ", "         BD       DB         ", "         BD       DB         ", "         BD       DB         ", "         BBD     DBB         ", "          BDD   DDB          ", "          BBBDDDBBB          ", "            BBBBB            ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "              B              ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "         BD       DB         ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "              B              ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             BBB             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "         BD       DB         ", "         BD       DB         ", "         BD       DB         ", "           D     D           ", "           DD   DD           ", "             DDD             ", "             BBB             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D       D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D       D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D       D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D       D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D       D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "           EEEEEEE           ", "         EEE     EEE         ", "       EEE         EEE       ", "      EE             EE      ", "     EE               EE     ", "     E                 E     ", "    EE                 EE    ", "    E        DDD        E    ", "   EE      DD   DD      EE   ", "   E       D     D       E   ", "   E      D       D      E   ", "   E      D       D      E   ", "   E      D       D      E   ", "   E       D     D       E   ", "   EE      DD   DD      EE   ", "    E        DDD        E    ", "    EE                 EE    ", "     E                 E     ", "     EE               EE     ", "      EE             EE      ", "       EEE         EEE       ", "         EEE     EEE         ", "           EEEEEEE           ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "           EEEEEEE           ", "         EEE     EEE         ", "       EEE         EEE       ", "      EE             EE      ", "     EE               EE     ", "    EE                 EE    ", "    E                   E    ", "   EE                   EE   ", "   E         DDD         E   ", "  EE       DD   DD       EE  ", "  E        D     D        E  ", "  E       D       D       E  ", "  E       D       D       E  ", "  E       D       D       E  ", "  E        D     D        E  ", "  EE       DD   DD       EE  ", "   E         DDD         E   ", "   EE                   EE   ", "    E                   E    ", "    EE                 EE    ", "     EE               EE     ", "      EE             EE      ", "       EEE         EEE       ", "         EEE     EEE         ", "           EEEEEEE           ", "                             ", "                             " )
                .aisle("             EEE             ", "          EEEEEEEEE          ", "        EEEE     EEEE        ", "      EEE           EEE      ", "     EEE             EEE     ", "    EE                 EE    ", "   EE                   EE   ", "   EE                   EE   ", "  EE                     EE  ", "  E                       E  ", " EE          DDD          EE ", " EE        DD   DD        EE ", " E         D     D         E ", "EE        D       D        EE", "EE        D       D        EE", "EE        D       D        EE", " E         D     D         E ", " EE        DD   DD        EE ", " EE          DDD          EE ", "  E                       E  ", "  EE                     EE  ", "   EE                   EE   ", "   EE                   EE   ", "    EE                 EE    ", "     EEE             EEE     ", "      EEE           EEE      ", "        EEEE     EEEE        ", "          EEEEEEEEE          ", "             EEE             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D       D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D       D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D       D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D       D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "          D       D          ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             BBB             ", "             DDD             ", "           DD   DD           ", "           D     D           ", "         BD       DB         ", "         BD       DB         ", "         BD       DB         ", "           D     D           ", "           DD   DD           ", "             DDD             ", "             BBB             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "              B              ", "             DDD             ", "           DD   DD           ", "           D     D           ", "          D       D          ", "         BD       DB         ", "          D       D          ", "           D     D           ", "           DD   DD           ", "             DDD             ", "              B              ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "            BBBBB            ", "          BBBDDDBBB          ", "          BDD   DDB          ", "         BBD     DBB         ", "         BD       DB         ", "         BD       DB         ", "         BD       DB         ", "         BBD     DBB         ", "          BDD   DDB          ", "          BBBDDDBBB          ", "            BBBBB            ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "            BBBBB            ", "          BBBDDDBBB          ", "          BDD   DDB          ", "         BBD     DBB         ", "         BD       DB         ", "         BD       DB         ", "         BD       DB         ", "         BBD     DBB         ", "          BDD   DDB          ", "          BBBDDDBCB          ", "            BBBBB            ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "            BBBBB            ", "          BBBCCCBBB          ", "          BCCCCCCCB          ", "         BBCCCCCCCBB         ", "         BCCCCCCCCCB         ", "         BCCCCCCCCCB         ", "         BCCCCCCCCCB         ", "         BBCCCCCCCBB         ", "          BCCCCCCCB          ", "          BBBCCCBBB          ", "            BBBBB            ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             CCC             ", "             CCC             ", "             CCC             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             BBB             ", "            BCCCB            ", "           BCCCCCB           ", "           BCCCCCB           ", "           BCCCCCB           ", "            BCCCB            ", "             BBB             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             BBB             ", "            BAAAB            ", "           BAAAAAB           ", "           BAAAAAB           ", "           BAAAAAB           ", "            BAAAB            ", "             BBB             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "             BBB             ", "            BCCCB            ", "           BCCCCCB           ", "           BCCACCB           ", "           BCCCCCB           ", "            BCCCB            ", "             BBB             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "              A              ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .aisle("                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "              S              ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             ", "                             " )
                .where('S', this.selfPredicate())
                .where('O', abilities(LASER_OUTPUT))
                .where('D', states(getCasingState())
                        .or(abilities(INPUT_ENERGY).setMinGlobalLimited(0).setMaxGlobalLimited(16))
                        .or(abilities(LASER_INPUT).setMinGlobalLimited(1).setMaxGlobalLimited(4))
                )
                .where('B', getFramePredicate())
                .where('C', states(getGlassState()))
                .where('A', states(getSecondCasingState()))

                .where('E', states(getUniqueCasingState()))//OK

                .where('F', states(getThirdCasingState()))

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
}
