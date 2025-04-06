package keqing.gtqtcore.integration.theoneprobe;

import keqing.gtqtcore.api.utils.NumberFormattingUtil;
import keqing.gtqtcore.common.pipelike.pressure.tile.TileEntityPressurePipe;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class PipeInfoProvider implements IProbeInfoProvider {
    @Override
    public String getID() {
        return "gtqt:pipe";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {
        if (world.getTileEntity(iProbeHitData.getPos()) instanceof TileEntityPressurePipe s) {
            IProbeInfo horizontalPane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));

            String pressure = NumberFormattingUtil.formatDoubleToCompactString(s.getPressure());
            String minPressure = NumberFormattingUtil.formatDoubleToCompactString(s.getMinPressure());
            String maxPressure = NumberFormattingUtil.formatDoubleToCompactString(s.getMaxPressure());


            horizontalPane.text(TextStyleClass.INFO + "{*gtqt.top.pressure*}");
            horizontalPane.text(TextStyleClass.INFO + " " + TextFormatting.RED + minPressure + "/" + pressure + "/" + maxPressure);

            iProbeInfo.progress( (int)s.getPressure() +1, (int)s.getMaxPressure() +1, iProbeInfo.defaultProgressStyle()
                    .suffix(" # "+NumberFormattingUtil.formatDoubleToCompactString(s.getPressure()) +" / " + NumberFormattingUtil.formatDoubleToCompactString(s.getMaxPressure()) + " Pa")
                    .filledColor(0xFFEEE600)
                    .alternateFilledColor(0xFFEEE600)
                    .borderColor(0xFF555555).numberFormat(NumberFormat.COMMAS));

            iProbeInfo.progress( (int)s.getMinPressure() +1, (int)s.getPressure() +1, iProbeInfo.defaultProgressStyle()
                    .suffix( " # "+NumberFormattingUtil.formatDoubleToCompactString(s.getMinPressure()) +" / " + NumberFormattingUtil.formatDoubleToCompactString(s.getPressure()) + " Pa")
                    .filledColor(0xFFEEE600)
                    .alternateFilledColor(0xFFEEE600)
                    .borderColor(0xFF555555).numberFormat(NumberFormat.COMMAS));

        }
    }
}
