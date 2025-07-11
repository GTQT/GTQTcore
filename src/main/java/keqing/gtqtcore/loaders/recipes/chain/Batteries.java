package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.CARBON_FIBER_PLATE;
import static gregtech.common.items.MetaItems.NEUTRON_REFLECTOR;
import static gregtechfoodoption.GTFOMaterialHandler.*;
import static gregtechfoodoption.GTFOMaterialHandler.Acetaldehyde;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class Batteries {
    public static void init() {

        // 4NiO + 3H2SO4 + 6KOH -> 3K2SO4 + 4NiO2H + 4H + 2H2O (H + H2O lost to dehydrator)
        DRYER_RECIPES.recipeBuilder().duration(240).EUt(1300)
                .input(dust, Garnierite, 8)
                .fluidInputs(SulfuricAcid.getFluid(3000))
                .input(dust, PotassiumHydroxide, 6)
                .output(dust, PotassiumSulfate,21)
                .output(dust,NickelOxideHydroxide,16)
                .buildAndRegister();

        // 2CoO + Li2CO3(H2O) -> 2 LiCoO2 + CO + H2O (H2O lost to dehydrator)
        DRYER_RECIPES.recipeBuilder().duration(240).EUt(4000)
                .input(dust, CobaltOxide, 4)
                .fluidInputs(LithiumCarbonateSolution.getFluid(1000))
                .output(dust, LithiumCobaltOxide,6)
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .buildAndRegister();

        // C + 2S -> CS2
        BLAST_RECIPES.recipeBuilder().duration(120).EUt(120).blastFurnaceTemp(1000)
                .input(dust, Carbon)
                .input(dust, Sulfur, 2)
                .fluidOutputs(CarbonSulfide.getFluid(1000))
                .output(dustTiny, Ash)
                .buildAndRegister();

        // 6F + 2CS2 -> C2F6S2 + 2S
        CHEMICAL_RECIPES.recipeBuilder().duration(250).EUt(120)
                .notConsumable(dust, Iodine)
                .fluidInputs(Fluorine.getFluid(6000))
                .fluidInputs(CarbonSulfide.getFluid(2000))
                .fluidOutputs(Biperfluoromethanedisulfide.getFluid(1000))
                .output(dust, Sulfur, 2)
                .buildAndRegister();

        // Hg + 3H2O2 + C2F6S2 + BaCO3 -> [C2BaF6O6S2 + 3H2O + Hg] + C
        CHEMICAL_RECIPES.recipeBuilder().duration(240).EUt(480)
                .fluidInputs(Mercury.getFluid(1000))
                .fluidInputs(Water.getFluid(3000))
                .fluidInputs(Biperfluoromethanedisulfide.getFluid(1000))
                .input(dust, BariumCarbonate, 5)
                .fluidOutputs(BariumTriflateSolution.getFluid(3000))
                .output(dust, Carbon)
                .buildAndRegister();

        // [C2BaF6O6S2 + 3H2O + Hg] -> C2BaF6O6S2 + 3H2O + Hg
        CENTRIFUGE_RECIPES.recipeBuilder().duration(320).EUt(1920)
                .fluidInputs(BariumTriflateSolution.getFluid(3000))
                .output(dust, BariumTriflate, 17)
                .fluidOutputs(Mercury.getFluid(1000))
                .fluidOutputs(Water.getFluid(3000))
                .buildAndRegister();

        // BaO6S2C2F6 + Li2CO3(H2O) -> BaCO3 + 2LiCSO3F3 + H2O (H2O lost to dehydrator)
        DRYER_RECIPES.recipeBuilder().duration(220).EUt(480)
                .input(dust,BariumTriflate,17)
                .fluidInputs(LithiumCarbonateSolution.getFluid(1000))
                .output(dust,BariumCarbonate,5)
                .output(dust,LithiumTriflate,18)
                .buildAndRegister();

        // 2NaC6H7O6(H2O) + CaCl2 -> CaC12H14O12 + 2NaCl(H2O)
        MIXER_RECIPES.recipeBuilder().duration(290).EUt(30)
                .fluidInputs(SodiumAlginateSolution.getFluid(2000))
                .input(dust, CalciumChloride, 3)
                .output(dust, CalciumAlginate,39)
                .fluidOutputs(SaltWater.getFluid(4000))
                .buildAndRegister();

        // C3H10Si + 5C6H8O7 + C19H42BrN + 3NH3 -> Si + 2CO + 4NO2 + HBr + 25C2H4O
        LARGE_CHEMICAL_RECIPES.recipeBuilder().duration(220).EUt(1920)
                .fluidInputs(Trimethylsilane.getFluid(1000))
                .fluidInputs(CitricAcid.getFluid(5000))
                .fluidInputs(CetaneTrimethylAmmoniumBromide.getFluid(1000))
                .fluidInputs(Ammonia.getFluid(3000))
                .output(dust,SiliconNanoparticles,1)
                .fluidOutputs(CarbonMonoxide.getFluid(2000))
                .fluidOutputs(NitrogenDioxide.getFluid(4000))
                .fluidOutputs(HydrobromicAcid.getFluid(1000))
                .fluidOutputs(Acetaldehyde.getFluid(25000))
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder().duration(3200).EUt(120)
                .input(dust,Glucose,24)
                .input(dust,StreptococcusPyogenes,1)
                .output(dust,Sorbose,24)
                .buildAndRegister();

        // C6H12O6 -> C6H8O6 + 4H (H lost to dehydrator)
        DRYER_RECIPES.recipeBuilder().duration(280).EUt(480)
                .input(dust,Sorbose,24)
                .fluidOutputs(AscorbicAcid.getFluid(1000))
                .notConsumable(dust, Platinum)
                .buildAndRegister();

        // C6H6O6 + 2H -> C6H8O6
        CHEMICAL_RECIPES.recipeBuilder().duration(280).EUt(480)
                .fluidInputs(DehydroascorbicAcid.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(AscorbicAcid.getFluid(1000))
                .notConsumable(dust, Nickel)
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder().duration(390).EUt(7680)
                .input(dust,GrapheneOxide,3)
                .input(dust,SiliconNanoparticles,1)
                .input(dust,CalciumAlginate,1)
                .input(dust, CarbonNanotube)
                .fluidInputs(SodiumCarbonateSolution.getFluid(1000))
                .fluidInputs(AscorbicAcid.getFluid(1000))
                .outputs(NANOSILICON_CATHODE.getStackForm())
                .fluidOutputs(DehydroascorbicAcid.getFluid(1000))
                .buildAndRegister();

        // Ga + 3Cl -> GaCl3
        CHEMICAL_RECIPES.recipeBuilder().duration(100).EUt(120)
                .input(dust, Gallium)
                .fluidInputs(Chlorine.getFluid(3000))
                .output(dust,GalliumChloride,4)
                .buildAndRegister();

        // 9AlCl3 + GaCl3 + 10SiO2 + 15H2O + 30NH3 + 15O -> Al9Si10O50Ga + 30NH4Cl
        CHEMICAL_PLANT.recipeBuilder().duration(470).EUt(7680)
                .input(dust,AluminiumTrichloride,36)
                .input(dust,GalliumChloride,4)
                .input(dust,SilicaGel,30)
                .fluidInputs(Water.getFluid(15000))
                .fluidInputs(Ammonia.getFluid(30000))
                .fluidInputs(Oxygen.getFluid(15000))
                .output(dust,Halloysite,90)
                .output(dust,AmmoniumChloride,30)
                .recipeLevel(4)
                .circuitMeta(23)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder().duration(100).EUt(480)
                .input(dust,Halloysite,9)
                .input(dust,Xylose,40)
                .input(dust, Sulfur, 2)
                .output(dust,SulfurCoatedHalloysite,9)
                .fluidOutputs(Water.getFluid(10000))
                .buildAndRegister();

        // LaF3 + BaF2 + 10C7H7F + 10CH2O -> 10H2O
        CHEMICAL_RECIPES.recipeBuilder().duration(320).EUt(30720)
                .input(dust,LanthanumTrifluoride,36)
                .input(dust,BariumDifluoride,3)
                .fluidInputs(Fluorotoluene.getFluid(10000))
                .fluidInputs(Formaldehyde.getFluid(10000))
                .output(dust,FluorideBatteryElectrolyte,2)
                .fluidOutputs(Water.getFluid(10000))
                .buildAndRegister();

        // Ni + O -> NiO
        CHEMICAL_RECIPES.recipeBuilder().duration(200).EUt(120)
                .input(dust, Nickel)
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust, Garnierite, 2)
                .buildAndRegister();

        // 7La2O3 + 7NiO + Ca + 2C10H16N2O8 -> 7La2NiO4 + CaO + 15CO + 5CH4 + 4NH3
        CHEMICAL_PLANT.recipeBuilder().duration(420).EUt(30720)
                .input(dust,LanthanumOxide,35)
                .input(dust, Garnierite, 14)
                .input(dust, Calcium)
                .fluidInputs(EthylenediaminetetraaceticAcid.getFluid(2000))
                .output(dust,LanthanumNickelOxide,49)
                .output(dust, Quicklime, 2)
                .fluidOutputs(CarbonMonoxide.getFluid(15000))
                .fluidOutputs(Methane.getFluid(5000))
                .fluidOutputs(Ammonia.getFluid(4000))
                .buildAndRegister();


        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Titanium, 4)
                .input(plate, Vanadium, 2)
                .input(cableGtSingle, Aluminium, 8)
                .inputs(CARBON_FIBER_PLATE.getStackForm(4))
                .input(dust,NickelOxideHydroxide,2)
                .EUt(1920)
                .duration(100)
                .outputs(BATTERY_NIMH_EMPTY.getStackForm())
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, TungstenSteel, 4)
                .input(plate, Vanadium, 4)
                .input(cableGtSingle, Platinum, 8)
                .inputs(NEUTRON_REFLECTOR.getStackForm(4))
                .input(dust,LithiumCobaltOxide,3)
                .EUt(7680)
                .duration(100)
                .outputs(BATTERY_SMALL_LITHIUM_ION_EMPTY.getStackForm())
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(plate, Vanadium, 6)
                .input(cableGtSingle, NiobiumTitanium, 8)
                .inputs(NEUTRON_REFLECTOR.getStackForm(4))
                .input(dust,LithiumCobaltOxide,6)
                .EUt(7680 * 4)
                .duration(100)
                .outputs(BATTERY_MEDIUM_LITHIUM_ION_EMPTY.getStackForm())
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, HSSS, 4)
                .input(plate, Naquadria, 2)
                .input(cableGtSingle, Naquadah, 8)
                .inputs(NEUTRON_REFLECTOR.getStackForm(4))
                .input(dust,LithiumCobaltOxide,9)
                .EUt(7680 * 16)
                .duration(100)
                .outputs(BATTERY_LARGE_LITHIUM_ION_EMPTY.getStackForm())
                .buildAndRegister();

        //
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Tritanium, 4)
                .input(plate, Naquadria, 4)
                .input(cableGtSingle, NaquadahAlloy, 8)
                .inputs(NANOSILICON_CATHODE.getStackForm())
                .input(dust,SulfurCoatedHalloysite,3)
                .EUt(7680 * 64)
                .duration(100)
                .outputs(BATTERY_SMALL_LIS_EMPTY.getStackForm())
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Seaborgium, 4)
                .input(plate, Naquadria, 6)
                .input(cableGtSingle, AbyssalAlloy, 8)
                .inputs(NANOSILICON_CATHODE.getStackForm(2))
                .input(dust,SulfurCoatedHalloysite,6)
                .EUt(30720 * 16)
                .duration(100)
                .outputs(BATTERY_MEDIUM_LIS_EMPTY.getStackForm())
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Bohrium, 4)
                .input(plate, NaquadriaticTaranium, 2)
                .input(cableGtSingle, TitanSteel, 8)
                .inputs(NANOSILICON_CATHODE.getStackForm(4))
                .input(dust,SulfurCoatedHalloysite,9)
                .EUt(30720 * 64)
                .duration(100)
                .outputs(BATTERY_LARGE_LIS_EMPTY.getStackForm())
                .buildAndRegister();

        //
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Neutronium, 4)
                .input(plate, NaquadriaticTaranium, 10)
                .input(cableGtSingle, BlackTitanium, 8)
                .inputs(HYPERDIMENSIONAL_TACHYON_CONDENSED_MATTER.getStackForm(4))
                .input(dust,LanthanumNickelOxide,7)
                .EUt(122880 * 4)
                .duration(100)
                .outputs(BATTERY_SMALL_FLUORIDE_EMPTY.getStackForm())
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, CosmicNeutronium, 8)
                .input(plate, NaquadriaticTaranium, 10)
                .input(cableGtSingle, BlackTitanium, 8)
                .inputs(HYPERDIMENSIONAL_TACHYON_CONDENSED_MATTER.getStackForm(4))
                .input(dust,LanthanumNickelOxide,14)
                .EUt(122880 * 16)
                .duration(100)
                .outputs(BATTERY_MEDIUM_FLUORIDE_EMPTY.getStackForm())
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Infinity, 16)
                .input(plate, NaquadriaticTaranium, 10)
                .input(cableGtSingle, BlackTitanium, 8)
                .inputs(HYPERDIMENSIONAL_TACHYON_CONDENSED_MATTER.getStackForm(4))
                .input(dust,LanthanumNickelOxide,28)
                .EUt(122880 * 64)
                .duration(100)
                .outputs(BATTERY_LARGE_FLUORIDE_EMPTY.getStackForm())
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .input(dust, PotassiumHydroxide, 1)
                .inputs(BATTERY_NIMH_EMPTY.getStackForm())
                .EUt(480)
                .duration(60)
                .outputs(BATTERY_NIMH.getStackForm())
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .input(dust,LithiumTriflate,1)
                .inputs(BATTERY_SMALL_LITHIUM_ION_EMPTY.getStackForm())
                .EUt(480 * 4)
                .duration(60)
                .outputs(BATTERY_SMALL_LITHIUM_ION.getStackForm())
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .input(dust,LithiumTriflate,2)
                .inputs(BATTERY_MEDIUM_LITHIUM_ION_EMPTY.getStackForm())
                .EUt(480 * 16)
                .duration(60)
                .outputs(BATTERY_MEDIUM_LITHIUM_ION.getStackForm())
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .input(dust,LithiumTriflate,4)
                .inputs(BATTERY_LARGE_LITHIUM_ION_EMPTY.getStackForm())
                .EUt(480 * 64)
                .duration(60)
                .outputs(BATTERY_LARGE_LITHIUM_ION.getStackForm())
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .input(dust,LithiumTriflate,4)
                .inputs(BATTERY_SMALL_LIS_EMPTY.getStackForm())
                .EUt(30720 * 4)
                .duration(60)
                .outputs(BATTERY_SMALL_LIS.getStackForm())
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .input(dust,LithiumTriflate,8)
                .inputs(BATTERY_MEDIUM_LIS_EMPTY.getStackForm())
                .EUt(30720 * 16)
                .duration(60)
                .outputs(BATTERY_MEDIUM_LIS.getStackForm())
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .input(dust,LithiumTriflate,16)
                .inputs(BATTERY_LARGE_LIS_EMPTY.getStackForm())
                .EUt(30720 * 64)
                .duration(60)
                .outputs(BATTERY_LARGE_LIS.getStackForm())
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .input(dust,FluorideBatteryElectrolyte,1)
                .inputs(BATTERY_SMALL_FLUORIDE_EMPTY.getStackForm())
                .EUt(1966080 * 4)
                .duration(60)
                .outputs(BATTERY_SMALL_FLUORIDE.getStackForm())
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .input(dust,FluorideBatteryElectrolyte,2)
                .inputs(BATTERY_MEDIUM_FLUORIDE_EMPTY.getStackForm())
                .EUt(1966080 * 16)
                .duration(60)
                .outputs(BATTERY_MEDIUM_FLUORIDE.getStackForm())
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .input(dust,FluorideBatteryElectrolyte,4)
                .inputs(BATTERY_LARGE_FLUORIDE_EMPTY.getStackForm())
                .EUt(1966080 * 64)
                .duration(60)
                .outputs(BATTERY_LARGE_FLUORIDE.getStackForm())
                .buildAndRegister();

    }
}
