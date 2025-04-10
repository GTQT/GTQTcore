package keqing.gtqtcore.api.metaileentity.multiblock;

import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import keqing.gtqtcore.api.capability.*;
import net.minecraftforge.fluids.IFluidTank;

public class GTQTMultiblockAbility {
    public static final MultiblockAbility<IReinforcedRotorHolder> REINFORCED_ROTOR_HOLDER_ABILITY = new MultiblockAbility<>("reinforced_rotor_holder");
    public static final MultiblockAbility<IOpticalComputationHatch> KQCC_COMPUTATION_DATA_RECEPTION = new MultiblockAbility("kqcccomputation_data_reception");
    public static final MultiblockAbility<IOpticalComputationHatch> KQCC_COMPUTATION_DATA_TRANSMISSION = new MultiblockAbility("kqcccomputation_data_transmission");
    public static final MultiblockAbility<IBuffer> BUFFER_MULTIBLOCK_ABILITY = new MultiblockAbility<>("buffer");
    public static final MultiblockAbility<IBall> GRINDBALL_MULTIBLOCK_ABILITY = new MultiblockAbility<>("ball");
    public static final MultiblockAbility<IBio> BIO_MULTIBLOCK_ABILITY = new MultiblockAbility<>("bio");
    public static final MultiblockAbility<IRadiation> RADIATION_MULTIBLOCK_ABILITY = new MultiblockAbility<>("radiation");
    public static final MultiblockAbility<IElectrode> ELECTRODE_MULTIBLOCK_ABILITY = new MultiblockAbility<>("electrode");
    public static final MultiblockAbility<IParticle> PARTICLE_MULTIBLOCK_ABILITY = new MultiblockAbility<>("particle");
    public static final MultiblockAbility<IDrillHead> DRILL_HEAD_MULTIBLOCK_ABILITY = new MultiblockAbility<>("drill_head");
    public static final MultiblockAbility<IKQCC> KQCC_MULTIBLOCK_ABILITY = new MultiblockAbility<>("kqcc");
    public static final MultiblockAbility<IHeat> HEAT_MULTIBLOCK_ABILITY = new MultiblockAbility<>("heat");
    public static final MultiblockAbility<IPowerSupply> POWER_SUPPLY_ABILITY = new MultiblockAbility<>("power_supply");
    public static final MultiblockAbility<IWarpSwarm> WARP_SWARM_MULTIBLOCK_ABILITY = new MultiblockAbility<>("warp_swarm");
    public static final MultiblockAbility<IPressureContainer> PRESSURE_CONTAINER = new MultiblockAbility<>("pressure_container");

    public static final MultiblockAbility<ILaser> LASER_INPUT = new MultiblockAbility<>("laser_input");
    public static final MultiblockAbility<ILaser> LASER_OUTPUT = new MultiblockAbility<>("laser_output");

    public static final MultiblockAbility<ICatalystHatch> CATALYST_MULTIBLOCK_ABILITY = new MultiblockAbility<>("catalyst");
}
