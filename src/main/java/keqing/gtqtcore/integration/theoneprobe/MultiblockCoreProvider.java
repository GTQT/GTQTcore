package keqing.gtqtcore.integration.theoneprobe;

import gregicality.multiblocks.common.metatileentities.multiblock.standard.MetaTileEntityAlloyBlastSmelter;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.TextFormattingUtil;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityElectricBlastFurnace;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys.MetaTileEntityIndustrialRoaster;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.huge.MetaTileEntityHugeBlastFurnace;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class MultiblockCoreProvider implements  IProbeInfoProvider {

        @Override
    public String getID() {
        return "gtqt:core";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {
        if (iBlockState.getBlock().hasTileEntity(iBlockState)) {
            TileEntity te = world.getTileEntity(iProbeHitData.getPos());
            if (te instanceof IGregTechTileEntity igtte) {
                MetaTileEntity mte = igtte.getMetaTileEntity();
                if (mte instanceof MetaTileEntityPhotolithographyFactory) {

                    for(int i=0;i<4;i++) {
                        int timeProgress = ((MetaTileEntityPhotolithographyFactory) mte).core[i][4]/20;
                        int timeMax = ((MetaTileEntityPhotolithographyFactory) mte).core[i][3]/20;
                        iProbeInfo.progress(timeProgress, timeMax, iProbeInfo.defaultProgressStyle()
                                .suffix(" / " + TextFormattingUtil.formatNumbers(timeMax) + " s")
                                .filledColor(0xFFEEE600)
                                .alternateFilledColor(0xFFEEE600)
                                .borderColor(0xFF555555).numberFormat(mcjty.theoneprobe.api.NumberFormat.COMMAS));
                    }

                }
            }
        }
    }
}