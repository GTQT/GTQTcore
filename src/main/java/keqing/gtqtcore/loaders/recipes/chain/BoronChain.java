package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.*;

public class BoronChain {
    public static void init() {

        //  Boric Acid + Hydrofluoric Acid -> Fluoroboric Acid + Water
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(BoricAcid.getFluid(1000))
                .fluidInputs(HydrofluoricAcid.getFluid(4000))
                .fluidOutputs(FluoroboricAcid.getFluid(1000))
                .fluidOutputs(Water.getFluid(3000))
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        //  Sodium Nitrite + Fluoroboric Acid + Hydrochloric Acid -> Salt + Benzenediazonium Tetrafluoroborate + Water
        CHEMICAL_PLANT.recipeBuilder()
                .input(dust, SodiumNitrite, 4)
                .fluidInputs(FluoroboricAcid.getFluid(2000))
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .output(dust, Salt, 2)
                .fluidOutputs(BenzenediazoniumTetrafluoroborate.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .recipeLevel(4)
                .EUt(VA[LuV])
                .duration(120)
                .buildAndRegister();

        //  Benzenediazonium Tetrafluoroborate -> Boron Trifluoride + Nitrogen + Fluorobenzene
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(BenzenediazoniumTetrafluoroborate.getFluid(1000))
                .fluidOutputs(BoronTrifluoride.getFluid(3000))
                .fluidOutputs(Nitrogen.getFluid(2000))
                .fluidOutputs(Fluorobenzene.getFluid(1000))
                .EUt(VA[LuV])
                .duration(6000)
                .buildAndRegister();

        //  Sodium + Nitric Acid + Oxygen -> Sodium Nitrate + Water
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Sodium)
                .circuitMeta(1)
                .fluidInputs(NitricAcid.getFluid(2000))
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust, SodiumNitrate, 10)
                .fluidOutputs(Water.getFluid(1000))
                .EUt(60)
                .duration(20)
                .buildAndRegister();

        //  Sodium Nitrate + Co/AC-AB Catalyst -> Sodium Nitrite + Oxygen + Water
        CHEMICAL_PLANT.recipeBuilder()
                .notConsumable(dust, CoACABCatalyst)
                .fluidInputs(SodiumNitrate.getFluid(1000))
                .output(dust, SodiumNitrite, 4)
                .fluidOutputs(Oxygen.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .recipeLevel(1)
                .EUt(VA[MV])
                .duration(300)
                .buildAndRegister();

        //  Co/AC-AB Catalyst (UV)
        CVD_RECIPES.recipeBuilder()
                .input(dust, Cobalt)
                .input(dust, Charcoal, 2)
                .fluidInputs(Polybenzimidazole.getFluid(144))
                .fluidInputs(Acetylene.getFluid(1000))
                .fluidInputs(Steam.getFluid(1000))
                .output(dust, CoACABCatalyst)
                .fluidOutputs(Hydrogen.getFluid(4000))
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .EUt(VA[UV])
                .duration(120)
                .buildAndRegister();

        //  Co/AC-AB Catalyst (UHV)
        MOLECULAR_BEAM_RECIPES.recipeBuilder()
                .input(foil, Polybenzimidazole)
                .input(dust, Cobalt)
                .input(dust, Charcoal, 2)
                .fluidInputs(Acetylene.getFluid(1000))
                .fluidInputs(Steam.getFluid(1000))
                .output(dust, CoACABCatalyst)
                .EUt(VA[UHV])
                .duration(240)
                .temperature(2688)
                .buildAndRegister();
    }
}