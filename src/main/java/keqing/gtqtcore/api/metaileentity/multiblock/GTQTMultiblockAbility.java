package keqing.gtqtcore.api.metaileentity.multiblock;

import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import keqing.gtqtcore.api.capability.IBall;
import keqing.gtqtcore.api.capability.IBuffer;

public class GTQTMultiblockAbility {

    public static final MultiblockAbility<IOpticalComputationHatch> KQCC_COMPUTATION_DATA_RECEPTION = new MultiblockAbility("kqcccomputation_data_reception");
    public static final MultiblockAbility<IOpticalComputationHatch> KQCC_COMPUTATION_DATA_TRANSMISSION = new MultiblockAbility("kqcccomputation_data_transmission");
    public static final MultiblockAbility<IBuffer> BUFFER_MULTIBLOCK_ABILITY = new MultiblockAbility<>("buffer");
    public static final MultiblockAbility<IBall> GRINDBALL_MULTIBLOCK_ABILITY = new MultiblockAbility<>("ball");

}
