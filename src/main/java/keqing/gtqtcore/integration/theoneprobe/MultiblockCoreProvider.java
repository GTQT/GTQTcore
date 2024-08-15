package keqing.gtqtcore.integration.theoneprobe;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.TextFormattingUtil;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockCore;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityPhotolithographyFactory;
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
        IProbeInfo horizontalPane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
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
                if (mte instanceof GTQTMultiblockCore) {

                    horizontalPane.text(TextStyleClass.INFO + "{*gtqt.top.corenumber*}");
                    horizontalPane.text(TextStyleClass.INFO + " " +TextFormatting.RED+((GTQTMultiblockCore) mte).getCoreNum()+ " ");

                    horizontalPane.text(TextStyleClass.INFO + "{*gtqt.top.speed*}");
                    horizontalPane.text(TextStyleClass.INFO + " " +TextFormatting.RED+((GTQTMultiblockCore) mte).speed+ " ");

                    for(int i=0;i<((GTQTMultiblockCore) mte).getCoreNum();i++) {

                        int timeProgress = ((GTQTMultiblockCore) mte).timeHelper[i][0];
                        int timeMax = ((GTQTMultiblockCore) mte).timeHelper[i][1];
                        if(timeMax>20) {
                            iProbeInfo.progress(timeProgress / 20, timeMax / 20, iProbeInfo.defaultProgressStyle()
                                    .suffix(" / " + TextFormattingUtil.formatNumbers(timeMax / 20) + " s")
                                    .filledColor(0xFFEEE600)
                                    .alternateFilledColor(0xFFEEE600)
                                    .borderColor(0xFF555555).numberFormat(mcjty.theoneprobe.api.NumberFormat.COMMAS));
                        }
                        else {
                            iProbeInfo.progress(timeProgress, timeMax, iProbeInfo.defaultProgressStyle()
                                    .suffix(" / " + TextFormattingUtil.formatNumbers(timeMax) + " t")
                                    .filledColor(0xFFEEE600)
                                    .alternateFilledColor(0xFFEEE600)
                                    .borderColor(0xFF555555).numberFormat(mcjty.theoneprobe.api.NumberFormat.COMMAS));
                        }
                    }
                }
            }
        }
    }
}