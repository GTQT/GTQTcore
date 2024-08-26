package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.BURNER_REACTOR_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class PETChain {
    static int SECOND=20;
    static int MINUTE=1200;

    public static void init() {

        //  C6H4(CH3)2 + 2O -> C8H8O2 + 2H
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust, Cobalt)
                .circuitMeta(2)
                .fluidInputs(ParaXylene.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(2000))
                .fluidOutputs(ParaToluicAcid.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(2000))
                .EUt(VA[MV])
                .duration(5 * SECOND)
                .buildAndRegister();

        //  C8H8O2 + CH4O -> C9H10O2 + H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(ParaToluicAcid.getFluid(1000))
                .fluidInputs(Methanol.getFluid(1000))
                .fluidOutputs(Methylparatoluate.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[HV])
                .duration((int) (2.5 * SECOND))
                .buildAndRegister();

        //  C9H10O2 + CO2 -> C10H10O4
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(Methylparatoluate.getFluid(1000))
                .fluidInputs(CarbonDioxide.getFluid(1000))
                .fluidOutputs(DimethylTerephthalate.getFluid(1000))
                .EUt(VA[EV])
                .duration((int) (7.5 * SECOND))
                .buildAndRegister();

        //  C10H10O4 + C2H6O2 -> 2C10H6O4 + 2H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(DimethylTerephthalate.getFluid(2592))
                .fluidInputs(EthyleneGlycol.getFluid(1000))
                .fluidOutputs(PolyethyleneTerephthalate.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .EUt(VA[UV])
                .duration(4 * SECOND)
                .buildAndRegister();

    }
}
