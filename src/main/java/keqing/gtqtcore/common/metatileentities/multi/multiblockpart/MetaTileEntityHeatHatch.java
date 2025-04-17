package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.DynamicLabelWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.AbilityInstances;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import keqing.gtqtcore.api.capability.IHeat;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MetaTileEntityHeatHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IHeat>, IHeat {

    // 环境温度 300K
    final int ENVIRONMENT_TEMPERATURE = 300;
    private final Queue<Integer> heatHistory;
    int tier;
    // 冷却系数
    double COOLING_COEFFICIENT;
    // 热交换系数
    double HEATING_COEFFICIENT;
    int maxHeat;
    private double heat;
    private double currentHeat;

    public MetaTileEntityHeatHatch(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.tier = tier;
        heatHistory = new LinkedList<>();
        COOLING_COEFFICIENT = 0.001 * (11 - tier);
        HEATING_COEFFICIENT = 0.1 * tier;
        maxHeat = 750 * tier;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setDouble("Heat", heat);
        data.setDouble("CurrentHeat", currentHeat);

        // 保存 heatHistory 到 NBT
        NBTTagCompound heatHistoryTag = new NBTTagCompound();
        int[] heatArray = new int[heatHistory.size()];
        int index = 0;
        for (int temp : heatHistory) {
            heatArray[index++] = temp;
        }
        heatHistoryTag.setIntArray("HeatHistory", heatArray);
        data.setTag("HeatHistory", heatHistoryTag);

        return super.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        heat = data.getDouble("Heat");
        currentHeat = data.getDouble("CurrentHeat");

        // 从 NBT 读取 heatHistory
        NBTTagCompound heatHistoryTag = data.getCompoundTag("HeatHistory");
        int[] heatArray = heatHistoryTag.getIntArray("HeatHistory");
        heatHistory.clear();
        for (int temp : heatArray) {
            heatHistory.offer(temp);
        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityHeatHatch(metaTileEntityId, tier);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 209)
                .bindPlayerInventory(entityPlayer.inventory, 126)
                .widget(new DynamicLabelWidget(7, 7, () -> "热源仓"))
                .widget((new AdvancedTextWidget(7, 17, this::addDisplayText, 2302755)).setMaxWidthLimit(181));
        return builder.build(this.getHolder(), entityPlayer);
    }

    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentString("热量： " + (int) heat));
        textList.add(new TextComponentString("变化: " + currentHeat));
        textList.add(new TextComponentString("最大承载温度: " + maxHeat));
        textList.add(new TextComponentString("冷却系数: " + COOLING_COEFFICIENT));
        textList.add(new TextComponentString("热交换系数: " + HEATING_COEFFICIENT));

        // 获取最近五个时刻的温度趋势
        String temperatureTrend = generateTemperatureTrend();
        textList.add(new TextComponentString(temperatureTrend));
    }
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("烫！烫！烫！", new Object[0]));
        tooltip.add(I18n.format("配合需要 流体热交换器（Fluid Heat Exchange）的设备使用"));
        tooltip.add(I18n.format("配合需要 电加热器（Fluid Heat Exchange）的设备使用"));
        tooltip.add(I18n.format("需要将流体热交换器控制器背面紧贴热源仓，可热源仓进行加热，降温等操作"));
        tooltip.add(I18n.format("注意，热源仓拥有独立的自然冷却系统"));
    }
    private String generateTemperatureTrend() {
        StringBuilder trend = new StringBuilder();
        int index = 1; // 用于标记时刻
        for (int temp : heatHistory) {
            trend.append(String.format(">>上 %d 时刻 温度: %dk ", 6 - index, temp));
            trend.append(generateTemperatureString(temp)).append("\n"); // 添加换行符
            index++;
        }
        return trend.toString().trim();
    }

    private String generateTemperatureString(int heat) {
        StringBuilder temperatureString = new StringBuilder();
        int count = (heat - 300) / 50; // 每 50k 温度添加一个 ==
        for (int i = 0; i < count; i++) {
            temperatureString.append("-");
        }
        return temperatureString.toString();
    }

    public void update() {
        //范围300-无上限
        // 计算冷却量
        // 更新温度
        if (heat > 300) heat -= Math.max(0.1, (heat - ENVIRONMENT_TEMPERATURE) * COOLING_COEFFICIENT);
        else heat = 300;

        // 如果队列已满，移除最旧的温度值
        if (heatHistory.size() >= 5) {
            heatHistory.poll();
        }

        // 添加当前的 heat 值到队列
        heatHistory.offer((int) heat);

        // 计算 currentHeat 为当前 heat 减去上一个 heat 值
        if (heatHistory.size() > 1) {
            int previousHeat = heatHistory.peek();
            currentHeat = heat - previousHeat;
        } else {
            currentHeat = 0; // 如果队列中只有一个值，currentHeat 设为 0
        }
    }

    @Override
    public MultiblockAbility<IHeat> getAbility() {
        return GTQTMultiblockAbility.HEAT_MULTIBLOCK_ABILITY;
    }

    @Override
    public void registerAbilities(AbilityInstances abilityInstances) {
        abilityInstances.add(this);
    }

    @Override
    public double getHeat() {
        return this.heat;
    }

    @Override
    public double setHeat(int temp) {
        return this.heat = temp;
    }

    @Override
    public int getTier() {
        return tier;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (this.shouldRenderOverlay()) {
            GTQTTextures.HEAT_HATCH.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
    }

    @Override
    public void balanceHeat(int outsideTemp, int tick) {
        // 根据 tick 调整热交换系数
        double adjustedHeatingCoefficient = HEATING_COEFFICIENT * (tick / 10.0);
        // 总温度变化量
        double totalChange = (adjustedHeatingCoefficient * (outsideTemp - heat));
        // 更新内部温度
        if (heat + totalChange < maxHeat) heat += totalChange;
    }

    @Override
    public void addHeat(int outsidePower, int tick) {
        // 根据 tick 调整热交换系数
        double adjustedHeatingCoefficient = HEATING_COEFFICIENT * (tick / 10.0);
        // 总温度变化量
        double totalChange = adjustedHeatingCoefficient * outsidePower;
        // 更新内部温度
        if (heat + totalChange < maxHeat) heat += totalChange;
    }
}
