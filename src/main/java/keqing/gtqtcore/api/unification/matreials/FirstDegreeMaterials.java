package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.fluids.fluidType.FluidTypes;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTUtility;
import keqing.gtqtcore.api.unification.GTQTMaterials;

public class FirstDegreeMaterials {
    public FirstDegreeMaterials() {
    }
    public static void register() {
        GTQTMaterials.HighPressureSteam = (new Material.Builder(20000, GTUtility.gregtechId("high_pressure_steam"))).fluid(FluidTypes.GAS).color(14601607).build();
        GTQTMaterials.SteamExhaustGas = (new Material.Builder(20001, GTUtility.gregtechId("steam_exhaust_gas"))).fluid(FluidTypes.GAS).color(14601607).build();
        GTQTMaterials.Pyrotheum = (new Material.Builder(20002, GTUtility.gregtechId("pyrotheum"))).fluid(FluidTypes.LIQUID).color(14601000).build();
    }
}
