package keqing.gtqtcore.loaders.recipes;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.metatileentities.MetaTileEntities;

public class MetaTileEntityLoader {

    public static void init() {
        ModHandler.addShapedRecipe(true, "huge_steam_turbine", MetaTileEntities.LARGE_STEAM_TURBINE.getStackForm(), "PSP", "SAS", "CSC", 'S', new UnificationEntry(OrePrefix.gear, Materials.Steel), 'P', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.UV), 'A', MetaTileEntities.HULL[GTValues.UV].getStackForm(), 'C', new UnificationEntry(OrePrefix.pipeLargeFluid, Materials.Steel));
        ModHandler.addShapedRecipe(true, "huge_gas_turbine", MetaTileEntities.LARGE_GAS_TURBINE.getStackForm(), "PSP", "SAS", "CSC", 'S', new UnificationEntry(OrePrefix.gear, Materials.StainlessSteel), 'P', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.UEV), 'A', MetaTileEntities.HULL[GTValues.UEV].getStackForm(), 'C', new UnificationEntry(OrePrefix.pipeLargeFluid, Materials.StainlessSteel));
        ModHandler.addShapedRecipe(true, "huge_plasma_turbine", MetaTileEntities.LARGE_PLASMA_TURBINE.getStackForm(), "PSP", "SAS", "CSC", 'S', new UnificationEntry(OrePrefix.gear, Materials.TungstenSteel), 'P', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.UEV), 'A', MetaTileEntities.HULL[GTValues.UEV].getStackForm(), 'C', new UnificationEntry(OrePrefix.pipeLargeFluid, Materials.TungstenSteel));

    };


}
