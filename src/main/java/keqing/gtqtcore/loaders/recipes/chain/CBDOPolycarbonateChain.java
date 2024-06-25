package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtechfoodoption.GTFOMaterialHandler.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CHEMICAL_PLANT;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.DISSOLUTION_TANK_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class CBDOPolycarbonateChain {
    public static void init() {
        //  Propene + Carbon Monoxide + Water -> Isobutyric Acid
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Propene.getFluid(1000))
                .fluidInputs(CarbonMonoxide.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(IsobutyricAcid.getFluid(1000))
                .EUt(VA[HV])
                .duration(180)
                .buildAndRegister();

        //  Isobutyric Acid + Acetic Anhydride -> Isobutyric Anhydride + Acetic Acid
        DISSOLUTION_TANK_RECIPES.recipeBuilder()
                .fluidInputs(IsobutyricAcid.getFluid(2000))
                .fluidInputs(AceticAnhydride.getFluid(1000))
                .fluidOutputs(IsobutyricAnhydride.getFluid(1000))
                .fluidOutputs(AceticAcid.getFluid(2000))
                .EUt(VA[EV])
                .duration(60)
                .buildAndRegister();

        //  Isobutyric Anhyride -> Dimethylketene + Water
        PYROLYSE_RECIPES.recipeBuilder()
                .notConsumable(stickLong, YttriumBariumCuprate)
                .fluidInputs(IsobutyricAnhydride.getFluid(1000))
                .fluidOutputs(Dimethylketene.getFluid(2000))
                .EUt(VA[LuV])
                .duration(240)
                .buildAndRegister();

        //  Dimethylketene + Hydrogen -> Tetramethylcyclobutanediol
        CHEMICAL_PLANT.recipeBuilder()
                .notConsumable(dust, Rhenium)
                .fluidInputs(Dimethylketene.getFluid(2000))
                .fluidInputs(Hydrogen.getFluid(4000))
                .fluidOutputs(Tetramethylcyclobutanediol.getFluid(1000))
                .EUt(VA[UV])
                .recipeLevel(6)
                .duration(120)
                .buildAndRegister();

        //  CBDO Polycarbonate
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Tetramethylcyclobutanediol.getFluid(1000))
                .fluidInputs(DiphenylCarbonate.getFluid(1000))
                .fluidOutputs(CBDOPolycarbonate.getFluid(144))
                .fluidOutputs(Phenol.getFluid(2000))
                .EUt(VA[MV])
                .duration(160)
                .buildAndRegister();

        //BPAPolycarbonateChain
        //  Carbon Monoxide + Oxygen + Methanol - > Dimethyl Carbonate + Water
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(CarbonMonoxide.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidInputs(Methanol.getFluid(2000))
                .circuitMeta(1)
                .fluidOutputs(DimethylCarbonate.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[HV])
                .duration(120)
                .buildAndRegister();

        //  Dimethyl Carbonate + Phenol -> Diphenyl Carbonate + Methanol
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(DimethylCarbonate.getFluid(1000))
                .fluidInputs(Phenol.getFluid(2000))
                .fluidOutputs(DiphenylCarbonate.getFluid(1000))
                .fluidOutputs(Methanol.getFluid(2000))
                .EUt(VA[EV])
                .duration(120)
                .buildAndRegister();

        //  Diphenyl Carbonate + Bisphenol-A -> BPA Polycarbonate + Phenol
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(DiphenylCarbonate.getFluid(1000))
                .fluidInputs(BisphenolA.getFluid(1000))
                .fluidOutputs(BPAPolycarbonate.getFluid(144))
                .fluidOutputs(Phenol.getFluid(2000))
                .EUt(VA[IV])
                .duration(160)
                .buildAndRegister();
    }
}