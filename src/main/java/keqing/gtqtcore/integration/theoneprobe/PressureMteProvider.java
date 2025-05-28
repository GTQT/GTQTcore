package keqing.gtqtcore.integration.theoneprobe;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import keqing.gtqtcore.api.capability.IPressureContainer;
import keqing.gtqtcore.api.capability.IPressureMachine;
import keqing.gtqtcore.api.utils.NumberFormattingUtil;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.pressureSystem.MetaTileEntityPressureTank;
import keqing.gtqtcore.common.metatileentities.single.electric.MetaTileEntityPressureBooster;
import keqing.gtqtcore.common.metatileentities.single.steam.MetaTileEntitySteamEjector;
import keqing.gtqtcore.common.metatileentities.storage.MetaTileEntityGasTank;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class PressureMteProvider implements IProbeInfoProvider {
    @Override
    public String getID() {
        return "gtqt:pressure";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {
        if (iBlockState.getBlock().hasTileEntity(iBlockState)) {
            TileEntity te = world.getTileEntity(iProbeHitData.getPos());
            if (te == null) return; // 空值检查

            if (te instanceof IGregTechTileEntity igtte) {
                MetaTileEntity mte = igtte.getMetaTileEntity();
                if (mte instanceof IPressureContainer) {
                    handlePressureContainer((IPressureContainer) mte, iProbeInfo);
                } else if (mte instanceof IPressureMachine) {
                    handlePressureContainer(((IPressureMachine) mte).getPressureContainer(), iProbeInfo);
                } else if (mte instanceof MetaTileEntitySteamEjector) {
                    handlePressureContainer(((MetaTileEntitySteamEjector) mte).getPressureContainer(), iProbeInfo);
                }else if (mte instanceof MetaTileEntityPressureBooster) {
                    handlePressureContainer(((MetaTileEntityPressureBooster) mte).getPressureContainer(), iProbeInfo);
                }else if (mte instanceof MetaTileEntityPressureTank) {
                    if(((MetaTileEntityPressureTank) mte).isStructureFormed())
                        handlePressureContainer(((MetaTileEntityPressureTank) mte).getPressureContainer(), iProbeInfo);
                }else if (mte instanceof MetaTileEntityGasTank) {
                    handlePressureContainer(((MetaTileEntityGasTank) mte).getPressureContainer(), iProbeInfo);
                }
            }
        }
    }

    private void handlePressureContainer(IPressureContainer s, IProbeInfo iProbeInfo) {
        IProbeInfo horizontalPane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));

        String pressure = NumberFormattingUtil.formatDoubleToCompactString(s.getPressure());
        String minPressure = NumberFormattingUtil.formatDoubleToCompactString(s.getMinPressure());
        String maxPressure = NumberFormattingUtil.formatDoubleToCompactString(s.getMaxPressure());

        horizontalPane.text(TextStyleClass.INFO + "{*gtqt.top.pressure*}");
        horizontalPane.text(TextStyleClass.INFO + " " + TextFormatting.RED + minPressure + "/" + pressure + "/" + maxPressure);


        iProbeInfo.progress(((int)s.getPressure())==0?1:(int)s.getPressure(), ((int)s.getMaxPressure())==0?1:(int)s.getMaxPressure(), iProbeInfo.defaultProgressStyle()
                .suffix(" # "+NumberFormattingUtil.formatDoubleToCompactString(s.getPressure()) + " / " + NumberFormattingUtil.formatDoubleToCompactString(s.getMaxPressure()) + " Pa")
                .filledColor(0xFFEEE600)
                .alternateFilledColor(0xFFEEE600)
                .borderColor(0xFF555555).numberFormat(NumberFormat.COMMAS));

        iProbeInfo.progress(((int)s.getMinPressure())==0?1:(int)s.getMinPressure(), ((int)s.getPressure())==0?1:(int)s.getPressure(), iProbeInfo.defaultProgressStyle()
                .suffix(" # "+NumberFormattingUtil.formatDoubleToCompactString(s.getMinPressure()) + " / " + NumberFormattingUtil.formatDoubleToCompactString(s.getPressure()) + " Pa")
                .filledColor(0xFFEEE600)
                .alternateFilledColor(0xFFEEE600)
                .borderColor(0xFF555555).numberFormat(NumberFormat.COMMAS));
    }

}
