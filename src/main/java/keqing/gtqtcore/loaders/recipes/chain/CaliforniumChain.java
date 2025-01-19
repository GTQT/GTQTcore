package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.ROASTER_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class CaliforniumChain {
    public static void init() {

        //  Californium -> Californium Nitrite
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Californium)
                .fluidInputs(NitricAcid.getFluid(2000))
                .output(dust, CaliforniumNitrite, 3)
                .fluidOutputs(Hydrogen.getFluid(2000))
                .EUt(VA[MV])
                .duration(320)
                .buildAndRegister();

        //  Californium Nitrite -> Californium Dioxide
        BLAST_RECIPES.recipeBuilder()
                .input(dust, CaliforniumNitrite, 3)
                .output(dust, CaliforniumDioxide)
                .fluidOutputs(NitrogenMonoxide.getFluid(2000))
                .EUt(VA[MV])
                .duration(144)
                .blastFurnaceTemp(600)
                .buildAndRegister();

        //  Californium Dioxide -> Californium Hexachloride
        ROASTER_RECIPES.recipeBuilder()
                .input(dust, CaliforniumDioxide)
                .fluidInputs(Chlorine.getFluid(6000))
                .fluidOutputs(CaliforniumHexachloride.getFluid(1000))
                .fluidOutputs(Oxygen.getFluid(2000))
                .EUt(VA[MV])
                .duration(960)
                .temperature(2700)
                .buildAndRegister();

        //  Californium Hexachloride -> Californium Hexafluoride
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(CaliforniumHexachloride.getFluid(1000))
                .fluidInputs(HydrofluoricAcid.getFluid(6000))
                .fluidOutputs(CaliforniumHexafluoride.getFluid(1000))
                .fluidOutputs(DilutedHydrochloricAcid.getFluid(6000))
                .EUt(VA[HV])
                .duration(160)
                .buildAndRegister();

        //  TODO Gas Centrifuge? Zalgo, please do it quickly TAT
        //  Californium Hexafluoride -> Californium-252 Hexafluoride
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(CaliforniumHexafluoride.getFluid(1000))
                .fluidOutputs(Californium252Hexafluoride.getFluid(900))
                .fluidOutputs(Californium.getFluid(100))
                .EUt(VA[EV])
                .duration(800)
                .buildAndRegister();

        //  Californium-252 Hexafluoride -> Steam Cracked Californium-252 Hexafluoride
        CRACKING_RECIPES.recipeBuilder()
                .fluidInputs(Californium252Hexafluoride.getFluid(1000))
                .fluidInputs(Steam.getFluid(3000))
                .fluidOutputs(SteamCrackedCalifornium252Hexafluoride.getFluid(1000))
                .EUt(VA[HV])
                .duration(640)
                .buildAndRegister();

        //  Steam Cracked Californium-252 Hexafluoride -> Californium-252 Dioxide + Hydrofluoric Acid
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(SteamCrackedCalifornium252Hexafluoride.getFluid(1000))
                .output(dust, Californium252Dioxide, 3)
                .fluidOutputs(HydrofluoricAcid.getFluid(6000))
                .EUt(VA[MV])
                .duration(180)
                .buildAndRegister();

        //  Californium-252 Dioxide -> Californium-252
        BLAST_RECIPES.recipeBuilder()
                .input(dust, Californium252Dioxide, 3)
                .output(ingot, Californium252)
                .fluidOutputs(Oxygen.getFluid(2000))
                .EUt(VA[MV])
                .duration(1600)
                .blastFurnaceTemp(2300)
                .buildAndRegister();
    }
}