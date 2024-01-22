package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.capability.impl.EnergyContainerWireless;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class MetaTileEntityWirelessEnergyHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IEnergyContainer> {

    private final boolean isExport;
    private int channel;
    private final EnergyContainerWireless energyContainer;

    public MetaTileEntityWirelessEnergyHatch(ResourceLocation metaTileEntityId, int tier,boolean isExport) {
        super(metaTileEntityId, tier);
        this.isExport = isExport;
        energyContainer = new EnergyContainerWireless(this,isExport, GTValues.V[tier],2,0);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityWirelessEnergyHatch(this.metaTileEntityId,this.getTier(),this.isExport);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public MultiblockAbility<IEnergyContainer> getAbility() {
        return isExport ? MultiblockAbility.OUTPUT_ENERGY : MultiblockAbility.INPUT_ENERGY;
    }

    @Override
    public void registerAbilities(List<IEnergyContainer> list) {
        list.add(energyContainer);
    }

    public void setChannel(int channel){
        this.channel = channel;
        energyContainer.setChannel(channel);
        this.writeCustomData(GTQTValue.REQUIRE_DATA_UPDATE, b -> b.writeInt(this.channel));
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == GTQTValue.REQUIRE_DATA_UPDATE){
            this.channel = buf.readInt();
        }
    }

}