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
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import keqing.gtqtcore.api.metatileentity.MetaTileEntityBaseWithControl;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static gregtech.api.GTValues.VN;
import static gregtech.api.metatileentity.multiblock.MultiblockAbility.OUTPUT_ENERGY;
import static keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility.LASER_INPUT;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4.TurbineCasingType.NQ_TURBINE_CASING;

public class MetaTileEntityLaserTranslation extends MetaTileEntityBaseWithControl {

    public int Amperage;
    public int Voltage;
    long Laser;//当前的激光
    long MaxLaser;
    long SetLaser;// 设定 的 最大激光强度
    int tier;

    public MetaTileEntityLaserTranslation(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(NQ_TURBINE_CASING);
    }

    @Override
    protected void updateFormedValid() {
        Amperage = this.getAbilities(LASER_INPUT).get(0).Amperage();
        Voltage = this.getAbilities(LASER_INPUT).get(0).Voltage();
        Laser = this.getAbilities(LASER_INPUT).get(0).Laser();
        MaxLaser = this.getAbilities(LASER_INPUT).get(0).MaxLaser();
        SetLaser = this.getAbilities(LASER_INPUT).get(0).SetLaser();
        tier = this.getAbilities(LASER_INPUT).get(0).tier();
        this.energyContainer.addEnergy(this.getAbilities(LASER_INPUT).get(0).Laser());
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);

        List<IEnergyContainer> energyContainer = new ArrayList<>(this.getAbilities(MultiblockAbility.OUTPUT_ENERGY));
        energyContainer.addAll(this.getAbilities(MultiblockAbility.OUTPUT_LASER));
        this.energyContainer = new EnergyContainerList(energyContainer);
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
        this.getAbilities(LASER_INPUT).get(0).addVoltage(1);
    }

    private void decrementThresholdV(Widget.ClickData clickData) {
        this.getAbilities(LASER_INPUT).get(0).addVoltage(-1);
    }

    public void setCurrentA(int i) {
        this.getAbilities(LASER_INPUT).get(0).setAmperage(MathHelper.clamp(this.getAbilities(LASER_INPUT).get(0).Amperage() + i, 0, 1024));
    }


    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentString("激光传输器输入端-等级: " + tier));
        textList.add(new TextComponentString("开关 是否传输: " + isWorkingEnabled()));
        textList.add(new TextComponentString("实际 传输电压: " + Voltage));
        textList.add(new TextComponentString("实际 传输电流: " + Amperage));
        textList.add(new TextComponentString("实际 传输激光: " + Laser + " " + VN[GTUtility.getTierByVoltage(Laser)]));
        textList.add(new TextComponentString("额定 传输激光: " + SetLaser + " " + VN[GTUtility.getTierByVoltage(SetLaser)]));
        textList.add(new TextComponentString("最大 传输激光: " + MaxLaser + " " + VN[GTUtility.getTierByVoltage(MaxLaser)]));
        textList.add(new TextComponentString("能量 缓存容量： " + energyContainer.getEnergyStored()));
        textList.add(new TextComponentString("能量 容量上限: " + energyContainer.getEnergyCapacity()));
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("MMM", "MOM", "MMM")
                .aisle("MMM", "MMM", "MMM")
                .aisle("MMM", "MMM", "MMM")
                .aisle("MMM", "MCM", "MMM")
                .where('C', selfPredicate())
                .where('M', states(getCasingState()).setMinGlobalLimited(30)
                        .or(abilities(OUTPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3)))

                .where('O', abilities(LASER_INPUT))
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
        return new MetaTileEntityLaserTranslation(metaTileEntityId);
    }

    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }
}
