package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.fluids.fluidType.FluidTypes;
import gregtech.api.unification.Elements;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTUtility;
import keqing.gtqtcore.api.unification.GTQTMaterials;

public class FirstDegreeMaterials {
    public FirstDegreeMaterials() {
    }
    public static void register() {
        GTQTMaterials.HighPressureSteam = (new Material.Builder(20000, GTUtility.gregtechId("high_pressure_steam"))).fluid(FluidTypes.GAS).color(14601607).build();
        GTQTMaterials.SteamExhaustGas = (new Material.Builder(20001, GTUtility.gregtechId("steam_exhaust_gas"))).fluid(FluidTypes.GAS).color(14601607).build();
        GTQTMaterials.SuperheatedSteam = (new Material.Builder(20002, GTUtility.gregtechId("super_heated_steam"))).fluid(FluidTypes.GAS).color(14601607).build();
        GTQTMaterials.Pyrotheum = (new Material.Builder(20003, GTUtility.gregtechId("pyrotheum"))).fluid(FluidTypes.LIQUID).color(14601000).build();

        GTQTMaterials.StellarMaterialResidueA = (new Material.Builder(20004, GTUtility.gregtechId("stellar_material_residue_a"))).fluid(FluidTypes.GAS).plasma().fluidTemp(24000).color(14601000).build().setFormula("ST-α", true);
        GTQTMaterials.StellarMaterialResidueB = (new Material.Builder(20005, GTUtility.gregtechId("stellar_material_residue_b"))).fluid(FluidTypes.GAS).plasma().fluidTemp(28000).color(14600000).build().setFormula("ST-β", true);
        GTQTMaterials.StellarMaterialResidueC = (new Material.Builder(20006, GTUtility.gregtechId("stellar_material_residue_c"))).fluid(FluidTypes.GAS).plasma().fluidTemp(32000).color(14639000).build().setFormula("ST-γ", true);
        GTQTMaterials.StellarMaterial = (new Material.Builder(20007, GTUtility.gregtechId("stellar_material"))).fluid(FluidTypes.GAS).plasma().fluidTemp(18000).color(14638000).build().setFormula("ST-ST", true);
    }
}
