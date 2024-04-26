package keqing.gtqtcore.loaders.recipes.oreprocessing;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.ore.OrePrefix;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.PotassiumFluorideRefractoryMixture;
import static keqing.gtqtcore.api.unification.TJMaterials.SodiumFluoride;

/**
 * The Niobium-Tantalum Process
 *
 * <p>
 * Produces Niobium and Tantalum from Tantalite and Pyrochlore
 * </p>
 *
 * <p>Main Products: Niobium, Tantalum</p>
 * <p>Side Products: None</p>
 *
 * <p>11 Pyrochlore -> 2 Niobium + 2/7 Tantalum </p>
 * <p>9 Tantalite -> 2 Tantalum + 2/7 Niobium </p>
 */
public class NiobiumTantalumChain {

    public static void init() {
        NewProcess();
    }

    private static void NewProcess() {
        //
        REACTION_FURNACE_RECIPES.recipeBuilder()
                .input(dust, Pyrochlore, 11)
                .input(dust,Carbon)
                .fluidInputs(Oxygen.getFluid(2000))
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .output(dust,NiobiumTantalumFe,8)
                .duration(400).EUt(VA[EV]).buildAndRegister();

        REACTION_FURNACE_RECIPES.recipeBuilder()
                .input(dust, Tantalite, 7)
                .input(dust,Carbon)
                .fluidInputs(Oxygen.getFluid(2000))
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .output(dust,NiobiumTantalumFe,8)
                .duration(400).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,NiobiumTantalumFe,1)
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .fluidOutputs(NiobiumTantalumFec.getFluid(1000))
                .duration(200).EUt(VA[EV]).buildAndRegister();

        SFM.recipeBuilder()
                .duration(1000)
                .EUt(120)
                .fluidInputs(NiobiumTantalumFec.getFluid(6000))
                .input(OrePrefix.dust,Sodium,24)
                .fluidOutputs(Nbcl.getFluid(200))
                .fluidOutputs(Tacl.getFluid(200))
                .fluidOutputs(Nbcl.getFluid(200))
                .fluidOutputs(Tacl.getFluid(200))
                .fluidOutputs(Nbcl.getFluid(200))
                .fluidOutputs(Tacl.getFluid(200))
                .fluidOutputs(Nbcl.getFluid(200))
                .fluidOutputs(Tacl.getFluid(200))
                .fluidOutputs(Nbcl.getFluid(200))
                .fluidOutputs(Tacl.getFluid(200))
                .fluidOutputs(Nbcl.getFluid(200))
                .fluidOutputs(Tacl.getFluid(200))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(HydrofluoricAcid.getFluid(4000))
                .fluidInputs(Nbcl.getFluid(1000))
                .fluidOutputs(Nbcla.getFluid(1000))
                .output(dust,Salt,4)
                .duration(400).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(HydrofluoricAcid.getFluid(4000))
                .fluidInputs(Tacl.getFluid(1000))
                .fluidOutputs(Tacla.getFluid(1000))
                .output(dust,Salt,4)
                .duration(400).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Nbcla.getFluid(1000))
                .fluidInputs(HydrofluoricAcid.getFluid(3000))
                .input(dust,Potassium,2)
                .fluidOutputs(Nbclb.getFluid(1000))
                .duration(400).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Tacla.getFluid(1000))
                .fluidInputs(HydrofluoricAcid.getFluid(3000))
                .input(dust,Potassium,2)
                .fluidOutputs(Taclb.getFluid(1000))
                .duration(400).EUt(VA[EV]).buildAndRegister();

        ROASTER_RECIPES.recipeBuilder()
                .fluidInputs(Nbclb.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(5000))
                .input(dust,Sodium,1)
                .output(dust,SodiumFluoride,2)
                .output(dust,NiobiumPentoxide,7)
                .fluidOutputs(PotassiumFluorideRefractoryMixture.getFluid(1000))
                .temperature(3600)
                .duration(400).EUt(VA[EV]).buildAndRegister();

        ROASTER_RECIPES.recipeBuilder()
                .fluidInputs(Taclb.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(5000))
                .input(dust,Sodium,1)
                .output(dust,SodiumFluoride,2)
                .output(dust,TantalumPentoxide,7)
                .fluidOutputs(PotassiumFluorideRefractoryMixture.getFluid(1000))
                .temperature(3600)
                .duration(400).EUt(VA[EV]).buildAndRegister();
    }

}
