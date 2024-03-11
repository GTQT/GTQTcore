package keqing.gtqtcore.integration.theoneprobe;

import gregicality.multiblocks.common.metatileentities.multiblock.standard.MetaTileEntityAlloyBlastSmelter;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityElectricBlastFurnace;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityBlazingBlastFurnace;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityElectricArcFurnace;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntitySepticTank;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityVacuumDryingFurnace;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys.MetaTileEntityRoaster;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.huge.MetaTileEntityHugeBlastFurnace;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class MultiblockFaceProvider implements  IProbeInfoProvider {

        @Override
    public String getID() {
        return "gtqt:face";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {
        if (iBlockState.getBlock().hasTileEntity(iBlockState)&&entityPlayer.isSneaking()) {
            TileEntity te = world.getTileEntity(iProbeHitData.getPos());
            IProbeInfo horizontalPane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
            if (te instanceof IGregTechTileEntity igtte) {
                MetaTileEntity mte = igtte.getMetaTileEntity();
                EnumFacing Facing=mte.getFrontFacing();
                horizontalPane.text(TextStyleClass.INFO + "{*gtqt.top.face*}");
                horizontalPane.text(TextStyleClass.INFO + " " +TextFormatting.BLUE+Facing+"  ");

            }
        }
    }
}