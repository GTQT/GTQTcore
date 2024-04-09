package keqing.gtqtcore.integration.theoneprobe;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.util.TextFormattingUtil;

import gregtech.integration.theoneprobe.provider.CapabilityInfoProvider;
import gregtech.integration.theoneprobe.provider.ElectricContainerInfoProvider;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.Capability;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;

public class ElectricContainerIOInfoProvider extends ElectricContainerInfoProvider {

    @Override
    protected void addProbeInfo( IEnergyContainer capability,  IProbeInfo probeInfo,
                                EntityPlayer player,  TileEntity tileEntity,  IProbeHitData data) {
        super.addProbeInfo(capability,probeInfo, player,tileEntity,data);

        long inA=capability.getInputVoltage();
        long inB=capability.getInputAmperage();
        long inC=capability.getInputPerSec();

        if(inA>0) {
            probeInfo.text("{*gtqt.top.in*}");
            probeInfo.text(" " + TextFormatting.BLUE + inA + "EU/t * " + inB + "A // " + TextFormatting.RED+inC/20+ "EU/t");
        }

        long onA=capability.getOutputVoltage();
        long onB=capability.getOutputAmperage();
        long onC=capability.getOutputPerSec();

        if(onA>0)
        {
            probeInfo.text("{*gtqt.top.on*}");
            probeInfo.text(" " + TextFormatting.BLUE + onA + "EU/t * " + onB + "A // " + TextFormatting.RED+onC/20+"EU/t");
        }
    }
}