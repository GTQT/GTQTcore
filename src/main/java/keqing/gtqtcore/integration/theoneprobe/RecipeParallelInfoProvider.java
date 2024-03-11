package keqing.gtqtcore.integration.theoneprobe;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IWorkable;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.capability.impl.ComputationRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SteamMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.RecipeMapSteamMultiblockController;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.TextFormattingUtil;

import gregtech.common.metatileentities.multi.MetaTileEntityLargeBoiler;
import gregtech.integration.theoneprobe.provider.CapabilityInfoProvider;
import mcjty.theoneprobe.api.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.Capability;

public class RecipeParallelInfoProvider extends CapabilityInfoProvider<AbstractRecipeLogic> {

    @Override
    public String getID() {
        return "gtqt:getparallelLimit";
    }



    @Override
    protected Capability<AbstractRecipeLogic> getCapability() {
        return GregtechTileCapabilities.CAPABILITY_RECIPE_LOGIC;
    }


    @Override
    protected void addProbeInfo( AbstractRecipeLogic capability,  IProbeInfo probeInfo,
                                 EntityPlayer player,  TileEntity tileEntity,
                                 IProbeHitData data) {
        if (!capability.isActive()) return;
        int parallel = capability.getParallelLimit();
        if (parallel==0||parallel==1) return;
        IProbeInfo horizontalPane = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
        horizontalPane.text(TextStyleClass.INFO + "{*gtqt.top.getparallelLimit*}");
        horizontalPane.text(TextStyleClass.INFO + "  "+ TextFormatting.BLUE+parallel);



    }
}