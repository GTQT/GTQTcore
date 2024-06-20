package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.GregtechDataCodes;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.ArrayUtils;

import static gregtech.api.GTValues.VA;

public class MetaTileEntityMicrowaveEnergyReceiver extends MetaTileEntity implements EnergyContainerHandler.IEnergyChangeListener, ITieredMetaTileEntity {
    int tier;
    public IEnergyContainer energyContainer;
    public int Amperage;
    public int Voltage;
    int[][] pos = {
            {0, 0, 1} ,
            {0, 0, -1} ,
            {0, 1, 0} ,
            {0, -1, 0} ,
            {1, 0, 0} ,
            {-1, 0, 0}
    };
    private boolean active = false;
    private boolean source = true;
    //接口暴露
    public void setAmperageVoltage(int x,int y)
    {
        if (Voltage <tier&&x==1) {
            Voltage++;
        }

        if (Amperage < 16&&y==1) {
            Amperage++;
        }

        if (Voltage >=0&&x==0) {
            Voltage--;
        }

        if (Amperage >=0&&y==0) {
            Amperage--;
        }
    }


    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("Amperage", Amperage);
        data.setInteger("Voltage", Voltage);
        data.setBoolean("active",active);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        Amperage = data.getInteger("Amperage");
        Voltage = data.getInteger("Voltage");
        active = data.getBoolean("active");
    }
    public MetaTileEntityMicrowaveEnergyReceiver(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId);
        this.tier = tier;
        this.energyContainer = EnergyContainerHandler.receiverContainer(this, GTValues.V[tier] * 64L ,tier, 16);
    }

    public void update() {
        super.update();
        if (!active||Voltage <= 0 || Amperage <= 0) return;

        for(int i=0;i<6;i++) {
            BlockPos poss = this.getPos();
            if (GTUtility.getMetaTileEntity(this.getWorld(), poss.add(pos[i][0], pos[i][1], pos[i][2])) instanceof MetaTileEntity) {
                MetaTileEntity mte = (MetaTileEntity) GTUtility.getMetaTileEntity(this.getWorld(), poss.add(pos[i][0], pos[i][1], pos[i][2]));
                for (EnumFacing facing : EnumFacing.VALUES) {
                    if (mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing) instanceof IEnergyContainer) {
                        IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, facing);
                            if (energyContainer.getEnergyStored() > (long) Voltage * Amperage*20) {
                                if (container.getEnergyCapacity() - container.getEnergyStored() < (long) Voltage * Amperage * 20) {
                                    container.addEnergy(container.getEnergyCapacity() - container.getEnergyStored());
                                    energyContainer.removeEnergy(container.getEnergyCapacity() - container.getEnergyStored());
                                } else {
                                    container.addEnergy((long) Voltage * Amperage * 20);
                                    energyContainer.removeEnergy((long) Voltage * Amperage * 20);
                                }
                            }
                            else if(energyContainer.getEnergyStored() > (long) Voltage * Amperage) {
                                if (container.getEnergyCapacity() - container.getEnergyStored() < (long) Voltage * Amperage) {
                                    container.addEnergy(container.getEnergyCapacity() - container.getEnergyStored());
                                    energyContainer.removeEnergy(container.getEnergyCapacity() - container.getEnergyStored());
                                } else {
                                    container.addEnergy((long) Voltage * Amperage);
                                    energyContainer.removeEnergy((long) Voltage * Amperage);
                                }
                            } else {
                                if (container.getEnergyCapacity() - container.getEnergyStored() < energyContainer.getEnergyStored()) {
                                    container.addEnergy(container.getEnergyCapacity() - container.getEnergyStored());
                                    energyContainer.removeEnergy(container.getEnergyCapacity() - container.getEnergyStored());
                                } else {
                                    container.addEnergy(energyContainer.getEnergyStored());
                                    energyContainer.removeEnergy(energyContainer.getEnergyStored());
                                }
                            }
                    }
                }
            }
        }

    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMicrowaveEnergyReceiver(metaTileEntityId,tier);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {

        ModularUI.Builder builder = ModularUI.defaultBuilder();

        builder.dynamicLabel(7, 10, () -> "微波无线能量接收器 等级：" + tier, 0x232323);

        builder.label(7, 32, "gregtech.creative.energy.voltage");
        builder.widget(new ClickButtonWidget(7, 45, 20, 20, "-", data -> Voltage = --Voltage == -1 ? 0 : Voltage));
        builder.widget(new ImageWidget(29, 45, 118, 20, GuiTextures.DISPLAY));
        builder.widget(new TextFieldWidget2(31, 51, 114, 16, () -> String.valueOf(Voltage), value1 -> {
            if (!value1.isEmpty()) {
                Voltage = Integer.parseInt(value1);
            }
        }).setMaxLength(10).setNumbersOnly(0, tier));
        builder.widget(new ClickButtonWidget(149, 45, 20, 20, "+", data -> {
            if (Voltage <tier) {
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

        builder.dynamicLabel(7, 110, () -> "能量传输量: " + Amperage*VA[Voltage]+" EU/tick", 0x232323);

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
        pipeline = (IVertexOperation[])ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(this.getPaintingColorForRendering())));
        if (!(baseTexture instanceof FireboxActiveRenderer) && !(baseTexture instanceof SimpleOrientedCubeRenderer)) {
            baseTexture.render(renderState, translation, pipeline);
        } else {
            baseTexture.renderOriented(renderState, translation, pipeline, this.getFrontFacing());
        }

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

}
