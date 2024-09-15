package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.UV;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.DilutedSulfuricAcid;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.screw;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.HYPER_REACTOR_MK3_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.HeavyHyperFuel;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class NaquadahFuelChain {
    public static void init()
    {
        NaquadahFuel();
        FuelRefineChain();
        HyperReactorFuel();
    }

    private static void HyperReactorFuel() {
        //  Hyper Reactor Mk I
        HYPER_REACTOR_MK1_RECIPES.recipeBuilder()
                .fluidInputs(LightHyperFuel.getFluid(1))
                .duration(200)
                .EUt((int) V[LuV])
                .buildAndRegister();

        HYPER_REACTOR_MK1_RECIPES.recipeBuilder()
                .fluidInputs(MediumHyperFuel.getFluid(1))
                .duration(400)
                .EUt((int) V[LuV])
                .buildAndRegister();

        HYPER_REACTOR_MK1_RECIPES.recipeBuilder()
                .fluidInputs(HeavyHyperFuel.getFluid(1))
                .duration(600)
                .EUt((int) V[LuV])
                .buildAndRegister();

        //  Hyper Reactor Mk II
        HYPER_REACTOR_MK2_RECIPES.recipeBuilder()
                .fluidInputs(LightHyperFuel.getFluid(1))
                .duration(200)
                .EUt((int) V[ZPM])
                .buildAndRegister();

        HYPER_REACTOR_MK2_RECIPES.recipeBuilder()
                .fluidInputs(MediumHyperFuel.getFluid(1))
                .duration(400)
                .EUt((int) V[ZPM])
                .buildAndRegister();

        HYPER_REACTOR_MK2_RECIPES.recipeBuilder()
                .fluidInputs(HeavyHyperFuel.getFluid(1))
                .duration(600)
                .EUt((int) V[ZPM])
                .buildAndRegister();

        //  Hyper Reactor Mk III
        HYPER_REACTOR_MK3_RECIPES.recipeBuilder()
                .fluidInputs(LightHyperFuel.getFluid(1))
                .duration(200)
                .EUt((int) V[UV])
                .buildAndRegister();

        HYPER_REACTOR_MK3_RECIPES.recipeBuilder()
                .fluidInputs(MediumHyperFuel.getFluid(1))
                .duration(400)
                .EUt((int) V[UV])
                .buildAndRegister();

        HYPER_REACTOR_MK3_RECIPES.recipeBuilder()
                .fluidInputs(HeavyHyperFuel.getFluid(1))
                .duration(600)
                .EUt((int) V[UV])
                .buildAndRegister();
    }

    private static void FuelRefineChain() {
        //燃料精炼
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .fluidInputs(Diesel.getFluid(1000))
                .fluidInputs(Nitrogen.getFluid(1000))
                .fluidOutputs(CetaneBoostedDiesel.getFluid(6000))
                .EUt(VA[EV])
                .duration(20)
                .buildAndRegister();

        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .fluidInputs(BioDiesel.getFluid(1000))
                .fluidInputs(Nitrogen.getFluid(1000))
                .fluidOutputs(CetaneBoostedDiesel.getFluid(6000))
                .EUt(VA[EV])
                .duration(20)
                .buildAndRegister();

        //  High Octane Gasoline
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .fluidInputs(Gasoline.getFluid(1000))
                .fluidInputs(Octane.getFluid(1000))
                .fluidOutputs(HighOctaneGasoline.getFluid(6000))
                .EUt(VA[IV])
                .duration(100)
                .buildAndRegister();

        //  Light Naquadah Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Naquadah)
                .fluidInputs(Uranium235.getFluid(500))
                .fluidInputs(Nitrogen.getFluid(500))
                .circuitMeta(1)
                .fluidOutputs(LightNaquadahFuel.getFluid(6000))
                .duration(300)
                .EUt(VA[ZPM])
                .buildAndRegister();

        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, GalliumSulfide)
                .fluidInputs(EnergeticNaquadria.getFluid(1000))
                .fluidInputs(Nitrogen.getPlasma(1000))
                .circuitMeta(11)
                .fluidOutputs(LightNaquadahFuel.getFluid(12000))
                .duration(300)
                .EUt(VA[UV])
                .buildAndRegister();

        //  Medium Naquadah Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, NaquadahEnriched)
                .fluidInputs(Uranium235.getFluid(500))
                .fluidInputs(Plutonium241.getFluid(500))
                .circuitMeta(2)
                .output(dust, Plutonium239)
                .fluidOutputs(MediumNaquadahFuel.getFluid(6000))
                .duration(300)
                .EUt(VA[ZPM])
                .buildAndRegister();

        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, IndiumPhosphide)
                .fluidInputs(EnergeticNaquadria.getFluid(1000))
                .fluidInputs(Nitrogen.getPlasma(1000))
                .circuitMeta(12)
                .fluidOutputs(MediumNaquadahFuel.getFluid(12000))
                .duration(300)
                .EUt(VA[UV])
                .buildAndRegister();

        //  Heavy Naquadah Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Naquadria)
                .input(dust, Plutonium239)
                .fluidInputs(Nitrogen.getPlasma(500))
                .circuitMeta(3)
                .output(dust, Naquadah)
                .fluidOutputs(HeavyNaquadahFuel.getFluid(6000))
                .duration(300)
                .EUt(VA[ZPM])
                .buildAndRegister();

        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Trinium)
                .fluidInputs(EnergeticNaquadria.getFluid(1000))
                .fluidInputs(Nitrogen.getPlasma(1000))
                .circuitMeta(13)
                .fluidOutputs(HeavyNaquadahFuel.getFluid(12000))
                .duration(300)
                .EUt(VA[UV])
                .buildAndRegister();

        //  Light Taranium Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Taranium)
                .input(dust, Gallium)
                .fluidInputs(DragonBlood.getFluid(1000))
                .fluidInputs(LightNaquadahFuel.getFluid(12000))
                .fluidInputs(Krypton.getFluid(6000))
                .circuitMeta(4)
                .fluidOutputs(LightTaraniumFuel.getFluid(12000))
                .duration(300)
                .EUt(VA[UV])
                .buildAndRegister();

        //  Medium Taranium Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Taranium)
                .input(dust, Duranium)
                .fluidInputs(DragonBlood.getFluid(2000))
                .fluidInputs(MediumNaquadahFuel.getFluid(12000))
                .fluidInputs(Xenon.getFluid(6000))
                .circuitMeta(5)
                .fluidOutputs(MediumTaraniumFuel.getFluid(12000))
                .duration(300)
                .EUt(VA[UV])
                .buildAndRegister();

        //  Heavy Taranium Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Taranium)
                .input(dust, Tritanium)
                .fluidInputs(DragonBlood.getFluid(4000))
                .fluidInputs(HeavyNaquadahFuel.getFluid(12000))
                .fluidInputs(Radon.getFluid(6000))
                .circuitMeta(6)
                .fluidOutputs(HeavyTaraniumFuel.getFluid(12000))
                .duration(300)
                .EUt(VA[UV])
                .buildAndRegister();

        //  Light Enriched Taranium Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Americium)
                .fluidInputs(DragonBlood.getFluid(1000))
                .fluidInputs(LightTaraniumFuel.getFluid(6000))
                .fluidInputs(EnergeticNaquadria.getFluid(1000))
                .circuitMeta(14)
                .fluidOutputs(LightEnrichedTaraniumFuel.getFluid(6000))
                .duration(300)
                .EUt(VA[UHV])
                .buildAndRegister();

        //  Medium Enriched Taranium Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Dubnium)
                .fluidInputs(DragonBlood.getFluid(2000))
                .fluidInputs(MediumTaraniumFuel.getFluid(6000))
                .fluidInputs(EnergeticNaquadria.getFluid(1000))
                .circuitMeta(15)
                .fluidOutputs(MediumEnrichedTaraniumFuel.getFluid(6000))
                .duration(300)
                .EUt(VA[UHV])
                .buildAndRegister();

        //  Heavy Enriched Taranium Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Livermorium)
                .fluidInputs(DragonBlood.getFluid(4000))
                .fluidInputs(HeavyTaraniumFuel.getFluid(6000))
                .fluidInputs(EnergeticNaquadria.getFluid(1000))
                .circuitMeta(16)
                .fluidOutputs(HeavyEnrichedTaraniumFuel.getFluid(6000))
                .duration(300)
                .EUt(VA[UHV])
                .buildAndRegister();

        //  Energetic Naquadria
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Naquadria)
                .fluidInputs(NitrogenDioxide.getFluid(500))
                .fluidInputs(SulfuricAcid.getFluid(500))
                .circuitMeta(0)
                .output(dust, Lutetium)
                .output(dust, Uranium238)
                .output(dust, Plutonium241)
                .output(dust, NaquadahEnriched)
                .fluidOutputs(EnergeticNaquadria.getFluid(1000))
                .duration(300)
                .EUt(65536)
                .buildAndRegister();

        //  Light Hyper Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Europium)
                .fluidInputs(LightTaraniumFuel.getFluid(500))
                .fluidInputs(LightEnrichedTaraniumFuel.getFluid(300))
                .fluidInputs(EnergeticNaquadria.getFluid(200))
                .fluidInputs(Uranium238.getFluid(L))
                .circuitMeta(7)
                .output(dust, Naquadah)
                .fluidOutputs(LightHyperFuel.getFluid(2000))
                .duration(460)
                .EUt(VA[UHV])
                .buildAndRegister();

        //  Medium Hyper Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Americium)
                .fluidInputs(MediumTaraniumFuel.getFluid(400))
                .fluidInputs(MediumEnrichedTaraniumFuel.getFluid(350))
                .fluidInputs(EnergeticNaquadria.getFluid(250))
                .fluidInputs(Uranium235.getFluid(L))
                .circuitMeta(8)
                .output(dust, NaquadahEnriched)
                .fluidOutputs(MediumHyperFuel.getFluid(2000))
                .duration(520)
                .EUt(VA[UHV])
                .buildAndRegister();

        //  Heavy Hyper Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Orichalcum)
                .fluidInputs(HeavyTaraniumFuel.getFluid(300))
                .fluidInputs(HeavyEnrichedTaraniumFuel.getFluid(400))
                .fluidInputs(EnergeticNaquadria.getFluid(300))
                .fluidInputs(Plutonium239.getFluid(L))
                .circuitMeta(9)
                .output(dust, NaquadahEnriched)
                .fluidOutputs(HeavyHyperFuel.getFluid(2000))
                .duration(580)
                .EUt(VA[UHV])
                .buildAndRegister();

        //  Adamantium + Bedrock Gas + Sulfuric Acid -> Adamantium Enriched + Deep Iron + Naquadah + Osmium + Diluted Sulfuric Acid
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Adamantium, 10)
                .fluidInputs(BedrockGas.getFluid(100))
                .fluidInputs(SulfuricAcid.getFluid(100))
                .circuitMeta(10)
                .output(dust, AdamantiumEnriched)
                .output(dust, DeepIron, 5)
                .output(dust, Naquadah, 2)
                .output(dust, Osmium, 2)
                .fluidOutputs(DilutedSulfuricAcid.getFluid(900))
                .duration(200)
                .EUt(VA[UHV])
                .buildAndRegister();

    }

    private static void NaquadahFuel() {
        //硅岩
        NAQUADAH_REACTOR.recipeBuilder()
                .input(screw,NaquadahEnriched)
                .output(screw,Lead)
                .EUt(8192)
                .duration(2000)
                .buildAndRegister();

        NAQUADAH_REACTOR.recipeBuilder()
                .input(screw,Naquadria)
                .output(screw,Lead)
                .EUt(8192)
                .duration(4000)
                .buildAndRegister();

        //  Heavy Naquadah Fuel
        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(HeavyNaquadahFuel.getFluid(1))
                .EUt(8192)
                .duration(45)
                .buildAndRegister();

        //  Medium Naquadah Fuel
        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(MediumNaquadahFuel.getFluid(1))
                .EUt(8192)
                .duration(32)
                .buildAndRegister();

        //  Light Naquadah Fuel
        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(LightNaquadahFuel.getFluid(1))
                .EUt(8192)
                .duration(15)
                .buildAndRegister();

        //  Heavy Taranium Fuel
        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(HeavyTaraniumFuel.getFluid(1))
                .EUt(8192)
                .duration(80)
                .buildAndRegister();

        //  Medium Taranium Fuel
        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(MediumTaraniumFuel.getFluid(1))
                .EUt(8192)
                .duration(60)
                .buildAndRegister();

        //  Light Taranium Fuel
        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(LightTaraniumFuel.getFluid(1))
                .EUt(8192)
                .duration(30)
                .buildAndRegister();

        //  Heavy Enriched Taranium Fuel
        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(HeavyEnrichedTaraniumFuel.getFluid(1))
                .EUt(8192)
                .duration(720)
                .buildAndRegister();

        //  Medium Enriched Taranium Fuel
        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(MediumEnrichedTaraniumFuel.getFluid(1))
                .EUt(8192)
                .duration(480)
                .buildAndRegister();

        //  Light Enriched Taranium Fuel
        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(LightEnrichedTaraniumFuel.getFluid(1))
                .EUt(8192)
                .duration(240)
                .buildAndRegister();

    }
}
