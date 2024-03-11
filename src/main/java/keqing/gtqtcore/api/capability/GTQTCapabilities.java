package keqing.gtqtcore.api.capability;

import gregtech.api.capability.IHeatingCoil;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import keqing.gtqtcore.api.capability.item.ICatalyst;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class GTQTCapabilities {

    @CapabilityInject(IHeatingCoil.class)
    public static Capability<IHeatingCoil> CAPABILITY_COIL = null;
    public static final MultiblockAbility<ICatalyst> CATALYST = new MultiblockAbility<>("catalyst");


    public GTQTCapabilities() {
    }
}