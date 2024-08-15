package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CHEMICAL_PLANT;
import static keqing.gtqtcore.api.unification.TJMaterials.*;


public class PEEKChain {
    public static void init() {
        //  PEEK (ZPM): Difluorobenzophenone + Soda Ash + Hydroquinone -> Sodium Fluoride + PEEK + Carbon Dioxide + Water
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Difluorobenzophenone, 24)
                .input(dust, SodaAsh, 6)
                .fluidInputs(Hydroquinone.getFluid(1000))
                .output(dust, SodiumFluoride, 4)
                .fluidOutputs(Polyetheretherketone.getFluid(2592))
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[IV])
                .duration(240)
                .buildAndRegister();

        //  PEEK (UHV): Soda Ash + Hydroquinone + Fluorobenzene -> Sodium Fluoride + PEEK + Water
        CHEMICAL_PLANT.recipeBuilder()
                .input(dust, SodaAsh, 6)
                .fluidInputs(Hydroquinone.getFluid(1000))
                .fluidInputs(Fluorobenzene.getFluid(2000))
                .output(dust, SodiumFluoride, 4)
                .fluidOutputs(Polyetheretherketone.getFluid(2592))
                .fluidOutputs(Water.getFluid(2000))
                .EUt(VA[ZPM])
                .recipeLevel(3)
                .duration(480)
                .buildAndRegister();

        //  Difluorobenzophenone
        CHEMICAL_PLANT.recipeBuilder()
                .notConsumable(dust, ZnFeAlClCatalyst)
                .fluidInputs(Fluorobenzene.getFluid(1000))
                .fluidInputs(Fluorotoluene.getFluid(1000))
                .fluidInputs(Chlorine.getFluid(6000))
                .fluidInputs(Water.getFluid(1000))
                .output(dust, Difluorobenzophenone, 24)
                .fluidOutputs(HydrofluoricAcid.getFluid(6000))
                .EUt(VA[EV])
                .recipeLevel(3)
                .duration(200)
                .buildAndRegister();

        //  Propene + Benzene + Oxygen -> Hydroquinone + Resorcinol + Acetone
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Propene.getFluid(1000))
                .fluidInputs(Benzene.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(3000))
                .circuitMeta(0)
                .fluidOutputs(Hydroquinone.getFluid(1000))
                .fluidOutputs(Resorcinol.getFluid(1000))
                .fluidOutputs(Acetone.getFluid(1000))
                .EUt(7860)
                .duration(200)
                .buildAndRegister();

        //  Fluorobenzene + Fluoroantimonic Acid + Methane -> AntimonyTrifluoride + Fluorotoluene + HydrofluoricAcid
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Fluorobenzene.getFluid(1000))
                .fluidInputs(FluoroantimonicAcid.getFluid(1000))
                .fluidInputs(Methane.getFluid(1000))
                .output(dust, AntimonyTrifluoride, 4)
                .fluidOutputs(Fluorotoluene.getFluid(1000))
                .fluidOutputs(HydrofluoricAcid.getFluid(4000))
                .EUt(VA[HV])
                .duration(140)
                .buildAndRegister();

        //  Zn-Fe-Al-Cl Catalyst
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Zinc)
                .input(dust, Iron)
                .input(dust, Aluminium)
                .fluidInputs(Chlorine.getFluid(1000))
                .output(dust, ZnFeAlClCatalyst, 4)
                .EUt(VA[IV])
                .duration(280)
                .buildAndRegister();
    }
}
