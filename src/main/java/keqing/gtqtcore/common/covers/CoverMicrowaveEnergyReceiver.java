package keqing.gtqtcore.common.covers;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.cover.CoverBase;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.cover.CoverWithUI;
import gregtech.api.cover.CoverableView;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

import static gregtech.api.GTValues.V;

public class CoverMicrowaveEnergyReceiver extends CoverBase implements CoverWithUI{

    public final int tier;
    public int Amperage;
    public int Voltage;

    public boolean active = false;
    public boolean source = true;
    public CoverMicrowaveEnergyReceiver(CoverDefinition definition,  CoverableView coverableView, EnumFacing attachedSide,int tier) {
        super(definition, coverableView, attachedSide);
        this.tier=tier;
    }
    @Override
    public void writeToNBT( NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("Amperage", Amperage);
        tagCompound.setInteger("Voltage", Voltage);
        tagCompound.setBoolean("active",active);
    }

    @Override
    public void readFromNBT( NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        Amperage = tagCompound.getInteger("Amperage");
        Voltage = tagCompound.getInteger("Voltage");
        active = tagCompound.getBoolean("active");
    }

    public boolean canAttach( CoverableView coverable,  EnumFacing side) {
        return coverable.hasCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, side);
    }

    @Override
    public void renderCover( CCRenderState renderState,  Matrix4 translation,
                             IVertexOperation[] pipeline,  Cuboid6 plateBox,
                             BlockRenderLayer layer) {
        GTQTTextures.WIRELESS_OVERLAY.renderSided(getAttachedSide(), plateBox, renderState, pipeline, translation);
    }
    protected ModularUI buildUI(ModularUI.Builder builder, EntityPlayer player) {
        return builder.build(this, player);
    }

    public EnumActionResult onRightClick( EntityPlayer player,  EnumHand hand,  CuboidRayTraceResult hitResult) {
        if (!this.getCoverableView().getWorld().isRemote) {
            this.openUI((EntityPlayerMP)player);
        }
        return EnumActionResult.SUCCESS;
    }
    @Override
    public ModularUI createUI(EntityPlayer player) {
        WidgetGroup primaryGroup = new WidgetGroup();

        primaryGroup.addWidget(new LabelWidget(7, 10, "微波无线能量接收覆盖板-等级：" +tier));
        primaryGroup.addWidget(new LabelWidget(7, 32, "gregtech.creative.energy.voltage"));
        primaryGroup.addWidget(new ClickButtonWidget(7, 45, 20, 20, "-", data -> Voltage = --Voltage == -1 ? 0 : Voltage));
        primaryGroup.addWidget(new ImageWidget(29, 45, 118, 20, GuiTextures.DISPLAY));
        primaryGroup.addWidget(new TextFieldWidget2(31, 51, 114, 16, () -> String.valueOf(Voltage), value1 -> {
            if (!value1.isEmpty()) {
                Voltage = Integer.parseInt(value1);
            }
        }).setMaxLength(10).setNumbersOnly(0, tier));
        primaryGroup.addWidget(new ClickButtonWidget(149, 45, 20, 20, "+", data -> {
            if (Voltage <tier) {
                Voltage++;
            }
        }));
        primaryGroup.addWidget(new LabelWidget(7, 74, "gregtech.creative.energy.amperage"));

        primaryGroup.addWidget(new ClickButtonWidget(7, 87, 20, 20, "-", data -> Amperage = --Amperage == -1 ? 0 : Amperage));
        primaryGroup.addWidget(new ImageWidget(29, 87, 118, 20, GuiTextures.DISPLAY));
        primaryGroup.addWidget(new TextFieldWidget2(31, 93, 114, 16, () -> String.valueOf(Amperage), value2 -> {
            if (!value2.isEmpty()) {
                Amperage = Integer.parseInt(value2);
            }
        }).setMaxLength(10).setNumbersOnly(0, 16));
        primaryGroup.addWidget(new ClickButtonWidget(149, 87, 20, 20, "+", data -> {
            if (Amperage < 16) {
                Amperage++;
            }
        }));
        primaryGroup.addWidget(new DynamicLabelWidget(7, 110, () -> "能量传输量: " + Amperage*V[Voltage]+" EU/tick"));
        primaryGroup.addWidget(new CycleButtonWidget(7, 139, 77, 20, () -> active, this::setActive,
                "gregtech.creative.activity.off", "gregtech.creative.activity.on"));


        primaryGroup.addWidget(new CycleButtonWidget(85, 139, 77, 20, () -> source, this::setSource,
                "gregtech.creative.energy.sink", "gregtech.creative.energy.source"));

        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 166 + 82)
                .widget(primaryGroup)
                .bindPlayerInventory(player.inventory, GuiTextures.SLOT, 7, 166);
        return buildUI(builder, player);
    }

    public void setAmperageVoltage(int x,int y)
    {
        if (Voltage <tier&&x==1) {
            Voltage++;
        }

        if (Amperage < 16&&y==1) {
            Amperage++;
        }

        if (Voltage >0&&x==-1) {
            Voltage--;
        }

        if (Amperage >0&&y==-1) {
            Amperage--;
        }
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
}

