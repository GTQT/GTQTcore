package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtechfoodoption.GTFOMaterialHandler.SodiumChlorate;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.HexachloroplatinicAcid;

/**
 * The Platinum Process
 *
 * <p>
 * Produces Platinum Group Metals from Platinum Group Sludge
 * </p>
 *
 * <p>Main Products: Platinum, Palladium, Rhodium, Ruthenium, Iridium, Osmium</p>
 * <p>Side Products: Gold, Silicon Dioxide</p>
 *
 * <p>4.8 PGS -> 1 Platinum</p>
 * <p>8 PGS -> 1 Palladium</p>
 * <p>18 PGS -> 1 Rhodium</p>
 * <p>18 PGS -> 1 Ruthenium</p>
 * <p>18 PGS -> 1 Iridium</p>
 * <p>36 PGS -> 1 Osmium</p>
 */
public class PlatinumGroupProcessing {

    public static void init() {
        removeVanillaRecipes();
        PlatinumGroupSludgeProcess();
        PlatinumProcess();
        PalladiumProcess();
        RutheniumProcess();
        RhodiumProcess();
        IridiumProcess();
        OsmiumProcess();
    }

    private static void removeVanillaRecipes() {

        //  Remove Platinum Group Sludge Centrifuge recipe
        GTRecipeHandler.removeRecipesByInputs(CENTRIFUGE_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, PlatinumGroupSludge, 6)},
                new FluidStack[]{AquaRegia.getFluid(1200)});

        //  Remove Raw Platinum -> Platinum
        GTRecipeHandler.removeRecipesByInputs(ELECTROLYZER_RECIPES, OreDictUnifier.get(dust, PlatinumRaw, 3));

        //  Remove Raw Palladium -> Palladium
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, PalladiumRaw, 5)},
                new FluidStack[]{HydrochloricAcid.getFluid(1000)});

        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, PalladiumRaw, 5)},
                new FluidStack[]{HydrochloricAcid.getFluid(1000)});

        //  Remove Inert Metal Residue -> Ruthenium Tetroxide + Rhodium Sulfate + Hydrogen
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, InertMetalMixture, 6)},
                new FluidStack[]{SulfuricAcid.getFluid(1500)});

        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, InertMetalMixture, 6)},
                new FluidStack[]{SulfuricAcid.getFluid(1500)});

        //  Remove Rhodium Sulfate -> Rhodium
        GTRecipeHandler.removeRecipesByInputs(ELECTROLYZER_RECIPES, RhodiumSulfate.getFluid(1000));

        //  Remove Acidic Osmium Solution -> Osmium Tetroxide
        GTRecipeHandler.removeRecipesByInputs(DISTILLATION_RECIPES, AcidicOsmiumSolution.getFluid(2000));

        GTRecipeHandler.removeRecipesByInputs(DISTILLERY_RECIPES,
                new ItemStack[]{IntCircuitIngredient.getIntegratedCircuit(1)},
                new FluidStack[]{AcidicOsmiumSolution.getFluid(400)});

        GTRecipeHandler.removeRecipesByInputs(DISTILLERY_RECIPES,
                new ItemStack[]{IntCircuitIngredient.getIntegratedCircuit(2)},
                new FluidStack[]{AcidicOsmiumSolution.getFluid(400)});

        //  Remove Osmium Tetroxide -> Osmium
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, OsmiumTetroxide, 5)},
                new FluidStack[]{Hydrogen.getFluid(8000)});

        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, OsmiumTetroxide, 5)},
                new FluidStack[]{Hydrogen.getFluid(8000)});

        //  Remove Rarest Metal Mixture -> Iridium Residue and Osmium Solution
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, RarestMetalMixture, 7)},
                new FluidStack[]{HydrochloricAcid.getFluid(4000)});

        //  Remove Iridium Metal Residue -> Iridium Chloride
        GTRecipeHandler.removeRecipesByInputs(CENTRIFUGE_RECIPES, OreDictUnifier.get(dust, IridiumMetalResidue, 5));

    }

    private static void PlatinumGroupSludgeProcess() {

        //  Platinum Group Sludge + 3(HCl)2HNO3 -> RuRhIr2Os(HNO3)3 + AuPtPd(HCl)6
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, PlatinumGroupSludge, 3)
                .fluidInputs(AquaRegia.getFluid(9000))
                .output(dust, GTQTMaterials.PlatinumGroupResidue)
                .fluidOutputs(GTQTMaterials.PlatinumGroupConcentrate.getFluid(1000))
                .duration(100)
                .EUt(60)
                .buildAndRegister();

        //  AuPtPd(HCl)6 -> H2PtPdCl6 + Au + 4H (H lost)
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.PlatinumGroupConcentrate.getFluid(1000))
                .output(dust, PlatinumSludgeResidue)
                .fluidOutputs(GTQTMaterials.PurifiedPlatinumGroupConcentrate.getFluid(1000))
                .duration(100)
                .EUt(VA[LV])
                .buildAndRegister();

        //  HCl + NH3 -> NH4Cl
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Ammonia.getFluid(1000))
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .output(dust, AmmoniumChloride, 2)
                .duration(60)
                .EUt(VA[LV])
                .buildAndRegister();

        //  H2PtPdCl6 + 2NH4Cl -> 0.625 (NH4)2PtCl6 + 0.375 (NH4)2PdCl6 + 2HCl
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, AmmoniumChloride, 4)
                .fluidInputs(GTQTMaterials.PurifiedPlatinumGroupConcentrate.getFluid(1000))
                .fluidOutputs(GTQTMaterials.AmmoniumHexachloroplatinate.getFluid(625))
                .fluidOutputs(GTQTMaterials.AmmoniumHexachloropalladate.getFluid(375))
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .duration(100)
                .EUt(VA[HV])
                .buildAndRegister();

        //  RuRhIr2Os(HNO3)3 + 3NaHSO4 -> RhRu + Ir2Os + 3NaNO3 + 3H2SO4
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.PlatinumGroupResidue)
                .fluidInputs(SodiumBisulfate.getFluid(L * 21))
                .output(dust, InertMetalMixture)
                .output(dust, RarestMetalMixture)
                .output(dust, GTQTMaterials.SodiumNitrate, 5)
                .fluidOutputs(SulfuricAcid.getFluid(3000))
                .duration(200)
                .EUt(240)
                .buildAndRegister();
    }

    private static void PlatinumProcess() {

        //  (NH4)2PtCl6 -> H2PtCl6 + 2NH3
        ELECTROLYZER_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.AmmoniumHexachloroplatinate.getFluid(1000))
                .fluidOutputs(HexachloroplatinicAcid.getFluid(1000))
                .fluidOutputs(Ammonia.getFluid(2000))
                .duration(120)
                .EUt(VA[LV])
                .buildAndRegister();

        //  Back to Electrolyzer recipe
    }

    private static void PalladiumProcess() {

        //  (NH4)2PdCl6 + 2H -> PdCl2 + 2NH3 + 4HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.AmmoniumHexachloropalladate.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .output(dust, PalladiumRaw, 3)
                .fluidOutputs(Ammonia.getFluid(2000))
                .fluidOutputs(HydrochloricAcid.getFluid(4000))
                .duration(100)
                .EUt(VA[MV])
                .buildAndRegister();

        //  CH3OH + CO -> HCO2CH3
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust, SodiumHydroxide)
                .fluidInputs(Methanol.getFluid(1000))
                .fluidInputs(CarbonMonoxide.getFluid(1000))
                .fluidOutputs(GTQTMaterials.MethylFormate.getFluid(1000))
                .duration(16)
                .EUt(VA[LV])
                .buildAndRegister();

        //  HCO2CH3 + H2O -> HCOOH + CH3OH
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.MethylFormate.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(GTQTMaterials.FormicAcid.getFluid(1000))
                .fluidOutputs(Methanol.getFluid(1000))
                .duration(50)
                .EUt(VA[LV])
                .buildAndRegister();

        //  PdCl2 + HCOOH -> Pd + 2HCl + CO2
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, PalladiumRaw, 3)
                .fluidInputs(GTQTMaterials.FormicAcid.getFluid(1000))
                .output(dust, Palladium)
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .duration(250)
                .EUt(VA[LV])
                .buildAndRegister();

        //  PtCl2 + HCOOH -> Pt + 2HCl + CO2
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, PlatinumRaw, 3)
                .fluidInputs(GTQTMaterials.FormicAcid.getFluid(1000))
                .output(dust, Platinum)
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .duration(250)
                .EUt(VA[LV])
                .buildAndRegister();

    }

    private static void RutheniumProcess() {

        //  CH4 + 8Cl -> CCl4 + 4HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Methane.getFluid(1000))
                .fluidInputs(Chlorine.getFluid(8000))
                .circuitMeta(2)
                .fluidOutputs(GTQTMaterials.CarbonTetrachloride.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(4000))
                .duration(80)
                .EUt(VA[LV])
                .buildAndRegister();

        //  2RhRu + 2CCl4 + 3H2SO4 -> 2RuCl3 + Rh2(SO4)3 + 2HCl + CH4 + C (C lost)
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, InertMetalMixture, 6)
                .fluidInputs(GTQTMaterials.CarbonTetrachloride.getFluid(2000))
                .fluidInputs(SulfuricAcid.getFluid(3000))
                .output(dust, GTQTMaterials.RutheniumChloride, 8)
                .fluidOutputs(RhodiumSulfate.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .fluidOutputs(Methane.getFluid(1000))
                .duration(100)
                .EUt(VA[EV])
                .buildAndRegister();

        //  2Na + 2O -> Na2O2
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, Sodium)
                .circuitMeta(2)
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust, GTQTMaterials.SodiumPeroxide, 2)
                .blastFurnaceTemp(2700)
                .duration(40)
                .EUt(VA[LV])
                .buildAndRegister();

        //  RuCl3 + 2Na2O2 + Cl -> RuO4 + 4NaCl
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.RutheniumChloride, 4)
                .input(dust, GTQTMaterials.SodiumPeroxide, 8)
                .fluidInputs(Chlorine.getFluid(1000))
                .output(dust, RutheniumTetroxide, 5)
                .output(dust, Salt, 8)
                .duration(200)
                .EUt(VA[HV])
                .buildAndRegister();

        //  Back to Ruthenium Tetroxide (in GregTech Vanilla Platinum Group process)
    }

    private static void RhodiumProcess() {

        //  Rh2(SO4)3 + 3H2O -> Rh2O3 + 3H2SO4
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(RhodiumSulfate.getFluid(1000))
                .fluidInputs(Water.getFluid(3000))
                .output(dust, GTQTMaterials.RhodiumOxide, 5)
                .fluidOutputs(SulfuricAcid.getFluid(3000))
                .duration(200)
                .EUt(VA[HV])
                .buildAndRegister();

        //  Back to Electrolyzer recipe
    }

    private static void IridiumProcess() {
        //  NaCl + 3H2O -> NaClO3 + 6H
        ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust, Salt, 2)
                .fluidInputs(Water.getFluid(3000))
                .circuitMeta(2)
                .output(dust,SodiumChlorate,5)
                .fluidOutputs(Hydrogen.getFluid(6000))
                .duration(100)
                .EUt(VA[MV])
                .buildAndRegister();

        //  NaCl + 3H2O -> NaClO3 + 6H
        ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust, Salt, 2)
                .fluidInputs(Water.getFluid(3000))
                .circuitMeta(2)
                .output(dust,SodiumChlorate,5)
                .fluidOutputs(Hydrogen.getFluid(6000))
                .duration(100)
                .EUt(VA[MV])
                .buildAndRegister();

        //  NaClO3 -> NaCl + 3O
        DRYER_RECIPES.recipeBuilder()
                .input(dust,SodiumChlorate,5)
                .output(dust, Salt, 2)
                .fluidOutputs(Oxygen.getFluid(3000))
                .duration(100)
                .EUt(60)
                .buildAndRegister();

        //  Ir2Os + 2NaClO3 + O -> Ir2O3 + OsO4 + 2NaCl
        DRYER_RECIPES.recipeBuilder()
                .input(dust, RarestMetalMixture, 12)
                .input(dust,SodiumChlorate,10)
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust, IridiumMetalResidue, 5)
                .output(dust, OsmiumTetroxide, 5)
                .output(dust, Salt, 4)
                .duration(200)
                .EUt(VA[EV])
                .buildAndRegister();

        //  Ir2O3 + 6HCl -> 2IrCl3 + 3H2O
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, IridiumMetalResidue, 5)
                .fluidInputs(HydrochloricAcid.getFluid(6000))
                .output(dust, IridiumChloride, 8)
                .fluidOutputs(Water.getFluid(3000))
                .duration(100)
                .EUt(240)
                .buildAndRegister();

        //  Back to Iridium Chloride (in GregTech Vanilla Platinum Group process)
    }

    private static void OsmiumProcess() {

        //  S + 2Cl -> SCl2
        LOW_TEMP_ACTIVATOR_RECIPES.recipeBuilder()
                .input(dust, Sulfur)
                .fluidInputs(Chlorine.getFluid(2000))
                .fluidOutputs(GTQTMaterials.SulfurDichloride.getFluid(1000))
                .duration(80)
                .EUt(120)
                .buildAndRegister();

        //  SO3 + SCl2 -> SOCl2 + SO2
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(SulfurTrioxide.getFluid(1000))
                .fluidInputs(GTQTMaterials.SulfurDichloride.getFluid(1000))
                .fluidOutputs(GTQTMaterials.ThionylChloride.getFluid(1000))
                .fluidOutputs(SulfurDioxide.getFluid(1000))
                .duration(100)
                .EUt(VA[LV])
                .buildAndRegister();

        //  OsO4 + 2SOCl2 -> OsCl4 + 2SO3
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, OsmiumTetroxide, 5)
                .fluidInputs(GTQTMaterials.ThionylChloride.getFluid(2000))
                .output(dust, GTQTMaterials.OsmiumTetrachloride, 5)
                .fluidOutputs(SulfurTrioxide.getFluid(2000))
                .duration(100)
                .EUt(240)
                .buildAndRegister();

        //  Back to Electrolyzer recipe
    }
}
