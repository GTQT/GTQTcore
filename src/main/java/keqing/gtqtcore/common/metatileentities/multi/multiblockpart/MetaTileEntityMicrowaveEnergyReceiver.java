package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.CycleButtonWidget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.TextFieldWidget2;
import gregtech.api.metatileentity.ITieredMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOrientedCubeRenderer;
import gregtech.client.renderer.texture.custom.FireboxActiveRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.GTValues.V;

public class MetaTileEntityMicrowaveEnergyReceiver extends MetaTileEntity implements EnergyContainerHandler.IEnergyChangeListener, ITieredMetaTileEntity {
    public IEnergyContainer energyContainer;
    public int Amperage;
    public int Voltage;
    public boolean active = false;
    public boolean source = true;
    int tier;

    public MetaTileEntityMicrowaveEnergyReceiver(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId);
        this.tier = tier;
        this.energyContainer = EnergyContainerHandler.receiverContainer(this, V[tier] * 16 * 20L, tier, 16);
    }

    //接口暴露
    public void setAmperageVoltage(int x, int y) {
        if (Voltage < tier && x == 1) {
            Voltage++;
        }

        if (Amperage < 16 && y == 1) {
            Amperage++;
        }

        if (Voltage > 0 && x == -1) {
            Voltage--;
        }

        if (Amperage > 0 && y == -1) {
            Amperage--;
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("Amperage", Amperage);
        data.setInteger("Voltage", Voltage);
        data.setBoolean("active", active);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        Amperage = data.getInteger("Amperage");
        Voltage = data.getInteger("Voltage");
        active = data.getBoolean("active");
    }

    @Override
    public void update() {
        super.update();
        if (getWorld().isRemote) return;  // 客户端直接返回

        if (!active || Voltage <= 0 || Amperage <= 0) return;  // 检查工作状态

        long ampsUsed = 0;
        // 遍历所有方向传输能量
        for (EnumFacing facing : EnumFacing.VALUES) {
            EnumFacing opposite = facing.getOpposite();
            TileEntity tile = getNeighbor(facing);
            if (tile == null) continue;

            // 尝试获取能量容器能力
            IEnergyContainer container = tile.getCapability(
                    GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, opposite);

            // 如果未找到则尝试激光能力
            if (container == null) {
                container = tile.getCapability(
                        GregtechTileCapabilities.CAPABILITY_LASER, opposite);
            }

            // 验证容器有效性
            if (container == null ||
                    !container.inputsEnergy(opposite) ||
                    container.getEnergyCanBeInserted() == 0) {
                continue;
            }

            // 计算可传输的电流量（不超过剩余电流上限）
            long availableAmps = Amperage - ampsUsed;
            long transferredAmps = container.acceptEnergyFromNetwork(
                    opposite,
                    Voltage,
                    availableAmps
            );

            ampsUsed += transferredAmps;
            if (ampsUsed >= Amperage) break;  // 达到电流上限时停止
        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMicrowaveEnergyReceiver(metaTileEntityId, tier);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {

        ModularUI.Builder builder = ModularUI.defaultBuilder();

        builder.dynamicLabel(7, 10, () -> "微波无线能量接收器-等级：" + tier, 0x232323);

        builder.label(7, 32, "gregtech.creative.energy.voltage");
        builder.widget(new ClickButtonWidget(7, 45, 20, 20, "-", data -> Voltage = --Voltage == -1 ? 0 : Voltage));
        builder.widget(new ImageWidget(29, 45, 118, 20, GuiTextures.DISPLAY));
        builder.widget(new TextFieldWidget2(31, 51, 114, 16, () -> String.valueOf(Voltage), value1 -> {
            if (!value1.isEmpty()) {
                Voltage = Integer.parseInt(value1);
            }
        }).setMaxLength(10).setNumbersOnly(0, tier));
        builder.widget(new ClickButtonWidget(149, 45, 20, 20, "+", data -> {
            if (Voltage < tier) {
                Voltage++;
            }
        }));

        builder.label(7, 74, "gregtech.creative.energy.amperage");
        builder.widget(new ClickButtonWidget(7, 87, 20, 20, "-", data -> Amperage = --Amperage == -1 ? 0 : Amperage));
        builder.widget(new ImageWidget(29, 87, 118, 20, GuiTextures.DISPLAY));
        builder.widget(new TextFieldWidget2(31, 93, 114, 16, () -> String.valueOf(Amperage), value2 -> {
            if (!value2.isEmpty()) {
                Amperage = Integer.parseInt(value2);
            }
        }).setMaxLength(10).setNumbersOnly(0, 16));
        builder.widget(new ClickButtonWidget(149, 87, 20, 20, "+", data -> {
            if (Amperage < 16) {
                Amperage++;
            }
        }));

        builder.dynamicLabel(7, 110, () -> "能量传输量: " + Amperage * V[Voltage] + " EU/tick", 0x232323);

        builder.widget(new CycleButtonWidget(7, 139, 77, 20, () -> active, this::setActive,
                "gregtech.creative.activity.off", "gregtech.creative.activity.on"));

        builder.widget(new CycleButtonWidget(85, 139, 77, 20, () -> source, this::setSource,
                "gregtech.creative.energy.sink", "gregtech.creative.energy.source"));

        return builder.build(getHolder(), entityPlayer);
    }

    public void setActive(boolean active) {
        this.active = active;
        if (!getWorld().isRemote) {
            writeCustomData(GregtechDataCodes.UPDATE_ACTIVE, buf -> buf.writeBoolean(active));
        }
    }

    public void setSource(boolean source) {
        this.source = source;
        if (source) {
            Voltage = 0;
            Amperage = 0;

        } else {
            Voltage = tier;
            Amperage = 16;
        }
    }

    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        ICubeRenderer baseTexture = this.getBaseTexture();
        pipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(this.getPaintingColorForRendering())));
        if (!(baseTexture instanceof FireboxActiveRenderer) && !(baseTexture instanceof SimpleOrientedCubeRenderer)) {
            baseTexture.render(renderState, translation, pipeline);
        } else {
            baseTexture.renderOriented(renderState, translation, pipeline, this.getFrontFacing());
        }
        Textures.ENERGY_OUT.renderSided(this.getFrontFacing(), renderState, translation, pipeline);

    }

    public ICubeRenderer getBaseTexture() {
        return Textures.VOLTAGE_CASINGS[this.tier];
    }

    @Override
    public void onEnergyChanged(IEnergyContainer iEnergyContainer, boolean b) {

    }

    public int getTier() {
        return this.tier;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("需要使用坐标卡绑定到微波控制器使用"));
        tooltip.add(I18n.format("调节电压电流改变六面输出电量（无视电压电流限制）"));
        tooltip.add(I18n.format("微波无线接收仓也是能源容器，因此也会因为下雨，电压过载等原因触发爆炸！"));
    }
}
