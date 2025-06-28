package keqing.gtqtcore.api.metatileentity.multiblock;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import keqing.gtqtcore.api.capability.*;

public class GTQTMultiblockAbility {
    public static final MultiblockAbility<IReinforcedRotorHolder> REINFORCED_ROTOR_HOLDER_ABILITY = new MultiblockAbility<>("reinforced_rotor_holder", IReinforcedRotorHolder.class);
    public static final MultiblockAbility<IBuffer> BUFFER_MULTIBLOCK_ABILITY = new MultiblockAbility<>("buffer", IBuffer.class);
    public static final MultiblockAbility<IBall> GRINDBALL_MULTIBLOCK_ABILITY = new MultiblockAbility<>("ball", IBall.class);
    public static final MultiblockAbility<IBio> BIO_MULTIBLOCK_ABILITY = new MultiblockAbility<>("bio", IBio.class);
    public static final MultiblockAbility<IRadiation> RADIATION_MULTIBLOCK_ABILITY = new MultiblockAbility<>("radiation", IRadiation.class);
    public static final MultiblockAbility<IElectrode> ELECTRODE_MULTIBLOCK_ABILITY = new MultiblockAbility<>("electrode", IElectrode.class);
    public static final MultiblockAbility<IParticle> PARTICLE_MULTIBLOCK_ABILITY = new MultiblockAbility<>("particle", IParticle.class);
    public static final MultiblockAbility<IDrillHead> DRILL_HEAD_MULTIBLOCK_ABILITY = new MultiblockAbility<>("drill_head", IDrillHead.class);
    public static final MultiblockAbility<IKQCC> KQCC_MULTIBLOCK_ABILITY = new MultiblockAbility<>("kqcc", IKQCC.class);
    public static final MultiblockAbility<IHeat> HEAT_MULTIBLOCK_ABILITY = new MultiblockAbility<>("heat", IHeat.class);
    public static final MultiblockAbility<IPowerSupply> POWER_SUPPLY_ABILITY = new MultiblockAbility<>("power_supply", IPowerSupply.class);
    public static final MultiblockAbility<IWarpSwarm> WARP_SWARM_MULTIBLOCK_ABILITY = new MultiblockAbility<>("warp_swarm", IWarpSwarm.class);
    public static final MultiblockAbility<IPressureContainer> PRESSURE_CONTAINER = new MultiblockAbility<>("pressure_container", IPressureContainer.class);
    public static final MultiblockAbility<ILaser> LASER_INPUT = new MultiblockAbility<>("laser_input", ILaser.class);
    public static final MultiblockAbility<ILaser> LASER_OUTPUT = new MultiblockAbility<>("laser_output", ILaser.class);
    public static final MultiblockAbility<ICatalystHatch> CATALYST_MULTIBLOCK_ABILITY = new MultiblockAbility<>("catalyst", ICatalystHatch.class);
}
