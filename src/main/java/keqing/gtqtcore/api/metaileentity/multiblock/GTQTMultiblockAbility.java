package keqing.gtqtcore.api.metaileentity.multiblock;

import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import keqing.gtqtcore.api.capability.*;

public class GTQTMultiblockAbility {
    public static final MultiblockAbility<IReinforcedRotorHolder> REINFORCED_ROTOR_HOLDER_ABILITY = new MultiblockAbility<>("reinforced_rotor_holder");
    public static final MultiblockAbility<IOpticalComputationHatch> KQCC_COMPUTATION_DATA_RECEPTION = new MultiblockAbility("kqcccomputation_data_reception");
    public static final MultiblockAbility<IOpticalComputationHatch> KQCC_COMPUTATION_DATA_TRANSMISSION = new MultiblockAbility("kqcccomputation_data_transmission");
    public static final MultiblockAbility<IBuffer> BUFFER_MULTIBLOCK_ABILITY = new MultiblockAbility<>("buffer");
    public static final MultiblockAbility<IBall> GRINDBALL_MULTIBLOCK_ABILITY = new MultiblockAbility<>("ball");

    public static final MultiblockAbility<ILaser> LASER_INPUT = new MultiblockAbility<>("laser_input");
    public static final MultiblockAbility<ILaser> LASER_OUTPUT = new MultiblockAbility<>("laser_output");

    public static final MultiblockAbility<ICatalystHatch> CATALYST_MULTIBLOCK_ABILITY = new MultiblockAbility<>("catalyst");
}
