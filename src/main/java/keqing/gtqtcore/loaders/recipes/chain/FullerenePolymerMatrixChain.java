package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.metatileentity.multiblock.CleanroomType;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.UHV;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.common.items.MetaItems.PETRI_DISH;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.BIOLOGICAL_REACTION_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.utils.GTQTUniversUtil.*;
import static keqing.gtqtcore.api.utils.GTQTUtil.VZ;

public class FullerenePolymerMatrixChain {

    public static void init() {
        PCBSChain();
        PdFullereneMatrixChain();

        //  PdC73H15NFe + C80H21O2 -> PdFeC153H36NO2
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, PalladiumFullereneMatrix)
                .fluidInputs(PCBS.getFluid(1000))
                .output(dust, FullerenePolymerMatrix, 2)
                .EUt(VZ[UEV])
                .duration(2 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
    }

    private static void PCBSChain() {

        //  4I + N2H4 -> 4HI + 2N
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Iodine, 4)
                .fluidInputs(Hydrazine.getFluid(1000))
                .fluidOutputs(HydroiodicAcid.getFluid(4000))
                .fluidOutputs(Nitrogen.getFluid(2000))
                .EUt(VA[HV])
                .duration(10 * SECOND + 10)
                .buildAndRegister();

        //  2LiH + Al2O3 + 3H2O -> 2LiAlH4 + 6O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, LithiumHydride, 2)
                .input(dust, Alumina, 5)
                .fluidInputs(Water.getFluid(3000))
                .output(dust, LithiumAluminiumHydride, 12)
                .fluidOutputs(Oxygen.getFluid(6000))
                .EUt(VA[EV])
                .duration(5 * SECOND)
                .buildAndRegister();

        //  LiH + AlH3 -> LiAlH4
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, LithiumHydride, 2)
                .input(dust, AluminiumHydride, 4)
                .output(dust, LithiumAluminiumHydride, 6)
                .EUt(VA[HV])
                .duration(2 * SECOND + 10)
                .buildAndRegister();

        //  LiI + H -> HI + Li
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, LithiumIodide, 2)
                .fluidInputs(Hydrogen.getFluid(1000))
                .output(dust, Lithium)
                .fluidOutputs(HydroiodicAcid.getFluid(1000))
                .EUt(VA[MV])
                .duration(2 * SECOND)
                .buildAndRegister();

        //  LiAlH4 + C3H3N + HI + 2H2O + C8H8 -> AlH3 (cycle) + LiI (cycle) + C11H14O2 + NH3
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, LithiumAluminiumHydride, 6)
                .fluidInputs(Acrylonitrile.getFluid(1000))
                .fluidInputs(HydroiodicAcid.getFluid(1000))
                .fluidInputs(Water.getFluid(2000))
                .fluidInputs(Styrene.getFluid(1000))
                .notConsumable(TrimethyltinChloride.getFluid(1))
                .output(dust, AluminiumHydride, 4)
                .output(dust, LithiumIodide, 2)
                .fluidOutputs(PhenylpentanoicAcid.getFluid(1000))
                .fluidOutputs(Ammonia.getFluid(1000))
                .EUt(VA[ZPM])
                .duration(5 * SECOND + 10)
                .buildAndRegister();

        //  H2S + 2CH4O -> (CH3)2S + 2H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(HydrogenSulfide.getFluid(1000))
                .fluidInputs(Methanol.getFluid(2000))
                .circuitMeta(2)
                .fluidOutputs(DimethylSulfide.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .EUt(VA[MV])
                .duration(7 * SECOND)
                .buildAndRegister();

        //  8C60 + 7C8H8 + 8CH2Cl2 + 8C11H14O2 + 8(CH3)2S + C6H5Cl-> 8PdC73H15NFe + 24HCl + 8C7H8 + 8H2S
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Fullerene, 8)
                .fluidInputs(Styrene.getFluid(7000))
                .fluidInputs(Dichloromethane.getFluid(8000))
                .fluidInputs(PhenylpentanoicAcid.getFluid(8000))
                .fluidInputs(DimethylSulfide.getFluid(8000))
                .fluidInputs(Chlorobenzene.getFluid(8000))
                .fluidOutputs(PCBS.getFluid(8000))
                .fluidOutputs(HydrochloricAcid.getFluid(24000))
                .fluidOutputs(Toluene.getFluid(8000))
                .fluidOutputs(HydrogenSulfide.getFluid(8000))
                .EUt(VA[UXV])
                .duration(3 * SECOND)
                .buildAndRegister();
    }

    private static void PdFullereneMatrixChain() {

        //  C10H16N2O8 + 3NH3 + 2O -> 5NH2CH2COOH
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(EthylenediaminetetraaceticAcid.getFluid(1000))
                .fluidInputs(Ammonia.getFluid(3000))
                .fluidInputs(Oxygen.getFluid(2000))
                .notConsumable(dust, Salt)
                .fluidOutputs(Glycine.getFluid(5000))
                .EUt(VA[EV])
                .duration(5 * SECOND)
                .buildAndRegister();

        //  NH2CH2COOH + CH4 -> C3H7NO2 + H2
        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .notConsumable(PETRI_DISH)
                .fluidInputs(Glycine.getFluid(1000))
                .fluidInputs(Methanol.getFluid(1000))
                .output(dust, Sarcosine, 13)
                .fluidOutputs(Hydrogen.getFluid(2000))
                .EUt(VA[HV])
                .duration(24 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  C2H6O + Na -> C2H5ONa + H
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Sodium)
                .fluidInputs(Ethanol.getFluid(1000))
                .output(dust, SodiumEthoxide, 9)
                .fluidOutputs(Hydrogen.getFluid(1000))
                .EUt(VA[HV])
                .duration(7 * SECOND)
                .buildAndRegister();

        //  C7H8 + C4H8 + FeCl3 -> C10H10Fe + CH2Cl2 + HCl + 3H (lost)
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust, Rhenium)
                .fluidInputs(Toluene.getFluid(1000))
                .fluidInputs(Butene.getFluid(1000))
                .fluidInputs(Iron3Chloride.getFluid(1000))
                .notConsumable(FormicAcid.getFluid(1))
                .notConsumable(SulfuricAcid.getFluid(1))
                .fluidOutputs(Ferrocene.getFluid(1000))
                .fluidOutputs(Dichloromethane.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .EUt(VA[ZPM])
                .duration(3 * SECOND)
                .buildAndRegister();

        //  3C2H5ONa + C3H7NO2 + C60 + CHCl3 + C10H10Fe -> 3NaCl + C74H19FeN + CO2 + C2H6O
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SodiumEthoxide, 27)
                .input(dust, Sarcosine, 13)
                .input(dust, Fullerene)
                .fluidInputs(Chloroform.getFluid(1000))
                .fluidInputs(Ferrocene.getFluid(1000))
                .notConsumable(TitaniumTetrachloride.getFluid(1))
                .notConsumable(Toluene.getFluid(1))
                .output(dust, Salt, 6)
                .fluidOutputs(Ferrocenylfulleropyrddolidine.getFluid(1000))
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .fluidOutputs(Ethanol.getFluid(3000))
                .EUt(VA[UV])
                .duration(30 * SECOND)
                .buildAndRegister();

        //  Pd + C74H19FeN -> PdC73H15NFe
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Palladium)
                .fluidInputs(Ferrocenylfulleropyrddolidine.getFluid(1000))
                .notConsumable(NitricAcid.getFluid(1))
                .output(dust, PalladiumFullereneMatrix)
                .EUt(VZ[UHV])
                .duration(10 * SECOND)
                .buildAndRegister();
    }
}

