package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.DynamicLabelWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import keqing.gtqtcore.api.capability.IPowerSupply;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityPowerSupply;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;
import java.util.Objects;

import static gregtech.api.GTValues.V;

public class MetaTileEntityPowerSupplyHatch extends MetaTileEntityMultiblockPart implements
        IMultiblockAbilityPart<IPowerSupply>, IPowerSupply {

    String type;
    int level;
    int batteryStore;
    int chargeHeight;
    boolean fastCharge;
    boolean charge;
    long euStore;
    long maxStore;

    public MetaTileEntityPowerSupplyHatch(ResourceLocation metaTileEntityId, String type, int level) {
        super(metaTileEntityId, level);
        this.type = type;
        this.level = level;
        if (Objects.equals(type, "battle")) {
            batteryStore = (int) (Math.pow(4, level) * 10);
        } else if (Objects.equals(type, "supply")) {
            batteryStore = level * 10;
        }
    }

    public long getBatteryStore() {
        return batteryStore * V[1] * 64;
    }

    public void update() {
        super.update();

        // 获取控制器并进行类型检查
        MetaTileEntityPowerSupply controller = getControllerSafe();
        if (controller == null) {
            return;
        }
        if (!controller.isStructureFormed() || !controller.getWork() || !"supply".equals(type)) {
            return;
        }

        // 缓存控制器属性
        euStore = controller.getEuStore();
        maxStore = controller.getMaxEuStore();
        fastCharge = controller.getFastCharge();
        charge = controller.getCharge();
        chargeHeight = controller.getChargeHeight();

        // 获取相邻的MetaTileEntity并进行类型检查
        long energyLimit = V[level] * 64;
        for (int h = 1; h <= chargeHeight; h++) {
            MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), this.getPos().offset(frontFacing, h));
            if (mte == null) {
                continue;
            }

            for (EnumFacing facing : EnumFacing.VALUES) {
                IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);
                if (container != null) {
                    long capacity = container.getEnergyCapacity();
                    long stored = container.getEnergyStored();
                    long energyToAdd;

                    if (charge) {
                        energyToAdd = capacity - stored;
                        if (energyToAdd > 0 && euStore >= 0) {
                            if (capacity <= energyLimit && fastCharge) {
                                if (euStore >= energyToAdd) {
                                    container.addEnergy(energyToAdd);
                                    changeEU(-energyToAdd, controller);
                                } else {
                                    container.addEnergy(euStore);
                                    changeEU(-euStore, controller);
                                }
                            } else {
                                if (euStore >= energyToAdd) {
                                    if (energyToAdd > V[level]) {
                                        container.addEnergy(V[level]);
                                        changeEU(-V[level], controller);
                                    } else {
                                        container.addEnergy(energyToAdd);
                                        changeEU(-energyToAdd, controller);
                                    }
                                } else {
                                    container.addEnergy(euStore);
                                    changeEU(-euStore, controller);
                                }
                            }
                        }
                    } else {
                        if (stored > 0 && euStore < maxStore) {
                            energyToAdd = Math.min(stored, maxStore - euStore);
                            changeEU(energyToAdd, controller);
                            container.removeEnergy(energyToAdd);
                        }
                    }
                }
            }
        }
    }


    private MetaTileEntityPowerSupply getControllerSafe() {
        Object controller = this.getController();
        if (controller instanceof MetaTileEntityPowerSupply) {
            return (MetaTileEntityPowerSupply) controller;
        }
        return null;
    }

    public void changeEU(long eu, MetaTileEntityPowerSupply controller) {
        controller.changeEu(eu);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 209)
                .bindPlayerInventory(entityPlayer.inventory, 126)
                .widget(new DynamicLabelWidget(7, 7, () -> "超导矩阵模块"))
                .widget((new AdvancedTextWidget(7, 17, this::addDisplayText, 2302755)).setMaxWidthLimit(181));
        return builder.build(this.getHolder(), entityPlayer);
    }

    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentString("类型： " + type));
        textList.add(new TextComponentString("等级: " + level));
        textList.add(new TextComponentString("充电模式: " + charge));
        textList.add(new TextComponentString("当前电量: " + euStore + "/" + maxStore));
        textList.add(new TextComponentString("快速充电: " + fastCharge));
        textList.add(new TextComponentString("充电高度: " + chargeHeight));
    }
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        if (Objects.equals(type, "frame"))tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("奢侈的地板", new Object[0]));
        if (Objects.equals(type, "supply"))tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("内置御坂美琴", new Object[0]));
        if (Objects.equals(type, "battle"))tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("并不是新能源汽车电池", new Object[0]));
        tooltip.add(I18n.format("可填充在超导矩阵多方块内的任意位置，不同模块拥有不同功能，详情请见 超导矩阵 多方块的tooltips"));
        tooltip.add(I18n.format("模块类型："+type));
        tooltip.add(I18n.format("模块等级："+level));
        tooltip.add(I18n.format("蓄能上限："+getBatteryStore()+" EU"));
        if (Objects.equals(type, "frame")) tooltip.add(I18n.format("超导矩阵的框架模块，用于填充空缺部分，只拥有少量电力缓存功能，除此以外没有其他实质性功能，"));
        if (Objects.equals(type, "supply")) tooltip.add(I18n.format("超导矩阵的供能模块，用于填充需要链接用电器的位置，可对用电器进行供能或者用电操作，电学参数取决于多方块的供能模式，注意，本模块供电无视电压限制，"));
        if (Objects.equals(type, "battle")) tooltip.add(I18n.format("超导矩阵的蓄能模块，用于提供大量能量缓存，除此以外没有其他实质性功能"));



    }
    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        ICubeRenderer baseTexture = GTQTTextures.POWER_SUPPLY_COMMON;
        pipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(this.getPaintingColorForRendering())));
        baseTexture.render(renderState, translation, pipeline);

        if (this.shouldRenderOverlay()) {
            if (Objects.equals(type, "frame")) {
                GTQTTextures.POWER_SUPPLY_COMMON.renderSided(getFrontFacing(), renderState, translation, pipeline);
            }
            if (Objects.equals(type, "supply")) {
                if (level == 1)
                    GTQTTextures.POWER_SUPPLY_S1.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 2)
                    GTQTTextures.POWER_SUPPLY_S2.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 3)
                    GTQTTextures.POWER_SUPPLY_S3.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 4)
                    GTQTTextures.POWER_SUPPLY_S4.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 5)
                    GTQTTextures.POWER_SUPPLY_S5.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 6)
                    GTQTTextures.POWER_SUPPLY_S6.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 7)
                    GTQTTextures.POWER_SUPPLY_S7.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 8)
                    GTQTTextures.POWER_SUPPLY_S8.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 9)
                    GTQTTextures.POWER_SUPPLY_S9.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 10)
                    GTQTTextures.POWER_SUPPLY_S10.renderSided(getFrontFacing(), renderState, translation, pipeline);

            }
            if (Objects.equals(type, "battle")) {
                if (level == 1)
                    GTQTTextures.POWER_SUPPLY_B1.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 2)
                    GTQTTextures.POWER_SUPPLY_B2.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 3)
                    GTQTTextures.POWER_SUPPLY_B3.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 4)
                    GTQTTextures.POWER_SUPPLY_B4.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 5)
                    GTQTTextures.POWER_SUPPLY_B5.renderSided(getFrontFacing(), renderState, translation, pipeline);
            }

        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPowerSupplyHatch(metaTileEntityId, type, level);
    }


    @Override
    public void registerAbilities(List<IPowerSupply> list) {
        list.add(this);
    }

    @Override
    public MultiblockAbility<IPowerSupply> getAbility() {
        return GTQTMultiblockAbility.POWER_SUPPLY_ABILITY;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getLevel() {
        return this.level;
    }
}
