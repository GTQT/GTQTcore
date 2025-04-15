package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.unification.material.Materials;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockEvaporationBed;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static net.minecraft.init.Blocks.DIRT;

public class BoronChain {
    public static void init() {
        EVAPORATION_POOL.recipeBuilder()
                .fluidInputs(Materials.Water.getFluid(10000))
                .fluidOutputs(SaltWater.getFluid(5000))
                .chancedOutput(dust,Salt,8000,500)
                .chancedOutput(dust,Salt,8000,500)
                .chancedOutput(dust,Salt,8000,500)
                .chancedOutput(dust,Salt,8000,500)
                .Jt(300)
                .duration(400)
                .buildAndRegister();

        EVAPORATION_POOL.recipeBuilder()
                .fluidInputs(SeaWater.getFluid(10000))
                .fluidOutputs(SaltWater.getFluid(8000))
                .chancedOutput(dust,Salt,8000,500)
                .chancedOutput(dust,Salt,8000,500)
                .chancedOutput(dust,Salt,8000,500)
                .chancedOutput(dust,Salt,8000,500)
                .Jt(300)
                .duration(200)
                .buildAndRegister();

        EVAPORATION_POOL.recipeBuilder()
                .fluidInputs(SaltWater.getFluid(10000))
                .output(dust,Salt,20)
                .Jt(300)
                .duration(100)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(DIRT,1)
                .input(dust,Stone,2)
                .input(dust,SiliconDioxide,2)
                .fluidInputs(Concrete.getFluid(200))
                .output(GTQTMetaBlocks.blockEvaporationBed.getState(BlockEvaporationBed.EvaporationBedType.DIRT).getBlock())
                .EUt(VA[LV])
                .duration(200)
                .buildAndRegister();

        //  Boric Acid + Hydrofluoric Acid -> Fluoroboric Acid + Water
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,BoricAcid,1)
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
                .recipeLevel(3)
                .EUt(VA[IV])
                .duration(120)
                .buildAndRegister();

        //  Benzenediazonium Tetrafluoroborate -> Boron Trifluoride + Nitrogen + Fluorobenzene
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(BenzenediazoniumTetrafluoroborate.getFluid(1000))
                .fluidOutputs(BoronTrifluoride.getFluid(3000))
                .fluidOutputs(Nitrogen.getFluid(2000))
                .fluidOutputs(Fluorobenzene.getFluid(1000))
                .EUt(VA[IV])
                .duration(6000)
                .buildAndRegister();

        //  Sodium + Nitric Acid + Oxygen -> Sodium Nitrate + Water
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Sodium)
                .circuitMeta(1)
                .fluidInputs(NitricAcid.getFluid(2000))
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust, SodiumNitrate, 1)
                .fluidOutputs(Water.getFluid(1000))
                .EUt(60)
                .duration(20)
                .buildAndRegister();

        //  Sodium Nitrate + Co/AC-AB Catalyst -> Sodium Nitrite + Oxygen + Water
        CHEMICAL_PLANT.recipeBuilder()
                .notConsumable(dust,AuPdCCatalyst)
                .input(dust,SodiumNitrate,5)
                .output(dust, SodiumNitrite, 4)
                .fluidOutputs(Oxygen.getFluid(1000))
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