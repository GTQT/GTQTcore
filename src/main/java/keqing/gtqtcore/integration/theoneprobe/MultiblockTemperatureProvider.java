package keqing.gtqtcore.integration.theoneprobe;

import gregicality.multiblocks.common.metatileentities.multiblock.standard.MetaTileEntityAlloyBlastSmelter;
import gregtech.api.GTValues;
import gregtech.api.capability.*;

import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityElectricBlastFurnace;
import gregtech.common.metatileentities.steam.boiler.SteamBoiler;
import gregtech.integration.theoneprobe.provider.CapabilityInfoProvider;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityBlazingBlastFurnace;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityElectricArcFurnace;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntitySepticTank;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityVacuumDryingFurnace;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys.MetaTileEntityRoaster;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.huge.MetaTileEntityHugeBlastFurnace;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.overwrite.MetaTileEntityCrackingUnit;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class MultiblockTemperatureProvider implements  IProbeInfoProvider {

        @Override
    public String getID() {
        return "gtqt:temperature";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {
        if (iBlockState.getBlock().hasTileEntity(iBlockState)) {
            TileEntity te = world.getTileEntity(iProbeHitData.getPos());
            IProbeInfo horizontalPane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
            if (te instanceof IGregTechTileEntity igtte) {
                MetaTileEntity mte = igtte.getMetaTileEntity();
                if (mte instanceof  MetaTileEntityElectricBlastFurnace) {
                    int Temperature = ((MetaTileEntityElectricBlastFurnace) mte).getCurrentTemperature();

                    horizontalPane.text(TextStyleClass.INFO + "{*gtqt.top.temperature*}");
                    horizontalPane.text(TextStyleClass.INFO + " " +TextFormatting.RED+Temperature+" K");
                }

                if (mte instanceof MetaTileEntityBlazingBlastFurnace) {
                    int Temperature = ((MetaTileEntityBlazingBlastFurnace) mte).getCurrentTemperature();
                    horizontalPane.text(TextStyleClass.INFO + "{*gtqt.top.temperature*}");
                    horizontalPane.text(TextStyleClass.INFO + " " +TextFormatting.RED+Temperature+" K");
                }

                if (mte instanceof MetaTileEntityAlloyBlastSmelter) {
                    int Temperature = ((MetaTileEntityAlloyBlastSmelter) mte).getCurrentTemperature();
                    horizontalPane.text(TextStyleClass.INFO + "{*gtqt.top.temperature*}");
                    horizontalPane.text(TextStyleClass.INFO + " " +TextFormatting.RED+Temperature+" K");
                }

                if (mte instanceof MetaTileEntityElectricArcFurnace) {
                    int Temperature = ((MetaTileEntityElectricArcFurnace) mte).getCurrentTemperature();
                    horizontalPane.text(TextStyleClass.INFO + "{*gtqt.top.temperature*}");
                    horizontalPane.text(TextStyleClass.INFO + " " +TextFormatting.RED+Temperature+" K");
                }

                if (mte instanceof MetaTileEntityVacuumDryingFurnace) {
                    int Temperature = ((MetaTileEntityVacuumDryingFurnace) mte).getCurrentTemperature();
                    horizontalPane.text(TextStyleClass.INFO + "{*gtqt.top.temperature*}");
                    horizontalPane.text(TextStyleClass.INFO + " " +TextFormatting.RED+Temperature+" K");
                }

                if (mte instanceof MetaTileEntityRoaster) {
                    int Temperature = ((MetaTileEntityRoaster) mte).getCurrentTemperature();
                    horizontalPane.text(TextStyleClass.INFO + "{*gtqt.top.temperature*}");
                    horizontalPane.text(TextStyleClass.INFO + " " +TextFormatting.RED+Temperature+" K");
                }

                if (mte instanceof MetaTileEntitySepticTank) {
                    int Temperature = ((MetaTileEntitySepticTank) mte).getCurrentTemperature();
                    horizontalPane.text(TextStyleClass.INFO + "{*gtqt.top.temperature*}");
                    horizontalPane.text(TextStyleClass.INFO + " " +TextFormatting.RED+Temperature+" K");
                }

                if (mte instanceof MetaTileEntityHugeBlastFurnace) {
                    int Temperature = ((MetaTileEntityHugeBlastFurnace) mte).getCurrentTemperature();
                    horizontalPane.text(TextStyleClass.INFO + "{*gtqt.top.temperature*}");
                    horizontalPane.text(TextStyleClass.INFO + " " +TextFormatting.RED+Temperature+" K");
                }
            }
        }
    }
}