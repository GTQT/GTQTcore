package keqing.gtqtcore.integration.theoneprobe;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.common.pipelike.cable.tile.TileEntityCable;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class CableInfoProvider implements  IProbeInfoProvider {

        @Override
    public String getID() {
        return "gtqt:cable";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {
        if(world.getTileEntity(iProbeHitData.getPos()) instanceof TileEntityCable)
        {
            var s  = (TileEntityCable)world.getTileEntity(iProbeHitData.getPos());
            double EUt =  s.getAverageVoltage();
            double EUm =  s.getMaxVoltage();

            double It =  s.getAverageAmperage();
            double Im =  s.getMaxAmperage();

            iProbeInfo.progress((int)(It*EUt), (int)(EUm*Im), iProbeInfo.defaultProgressStyle()
                    .suffix(" / " + TextFormattingUtil.formatNumbers(EUm*Im) + " W")
                    .filledColor(0xFFEEE600)
                    .alternateFilledColor(0xFFEEE600)
                    .borderColor(0xFF555555).numberFormat(mcjty.theoneprobe.api.NumberFormat.COMMAS));


            iProbeInfo.text("{*gtqt.top.i*}"+ TextFormatting.RED + " "+TextFormatting.GOLD +s.getAverageVoltage()+TextFormatting.BOLD +"/"+TextFormatting.RED+s.getMaxVoltage()+
                    " EU" + TextFormatting.GREEN +
                    " (" + GTValues.VNF[GTUtility.getTierByVoltage((long) EUt)]+TextFormatting.GRAY +"/"+GTValues.VNF[GTUtility.getTierByVoltage((long) EUm)] + TextFormatting.GREEN + ")");

            iProbeInfo.text("{*gtqt.top.v*}"+ " "+TextFormatting.YELLOW+s.getAverageAmperage()+TextFormatting.BOLD +"/"+TextFormatting.RED+s.getMaxAmperage()+TextFormatting.LIGHT_PURPLE+ " A");
        }
    }
}