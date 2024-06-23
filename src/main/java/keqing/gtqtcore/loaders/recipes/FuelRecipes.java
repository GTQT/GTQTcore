package keqing.gtqtcore.loaders.recipes;

import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

import gregtech.api.GTValues;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.init.Blocks;

import java.util.Collection;
import java.util.List;

public class FuelRecipes {


    public static void init(){

        //燃料电池
        Collection<Recipe> oilsRecipes = RecipeMaps.COMBUSTION_GENERATOR_FUELS.getRecipeList();
        for (Recipe recipe : oilsRecipes) {
            List<GTRecipeInput> fluidInputs = recipe.getFluidInputs();
            int EUt = (int) GTValues.V[GTValues.LV];
            int baseDuration = 4*recipe.getDuration() * recipe.getEUt() / EUt;
            GTQTcoreRecipeMaps.FUEL_CELL.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidInputs(Materials.Air.getFluid(baseDuration*4))
                    .duration(baseDuration)
                    .EUt(EUt)
                    .buildAndRegister();
            GTQTcoreRecipeMaps.FUEL_CELL.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidInputs(Materials.Oxygen.getFluid(FluidStorageKeys.GAS,baseDuration * 2))
                    .duration(baseDuration * 2)
                    .EUt(EUt)
                    .buildAndRegister();
            GTQTcoreRecipeMaps.FUEL_CELL.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidInputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, baseDuration))
                    .duration(baseDuration * 4)
                    .EUt(EUt)
                    .buildAndRegister();

        }
        Collection<Recipe> gasRecipes = RecipeMaps.GAS_TURBINE_FUELS.getRecipeList();
        for (Recipe recipe : gasRecipes) {
            List<GTRecipeInput> fluidInputs = recipe.getFluidInputs();
            int EUt = (int) GTValues.V[GTValues.LV];
            int baseDuration = 4*recipe.getDuration() * recipe.getEUt() / EUt;
            GTQTcoreRecipeMaps.FUEL_CELL.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidInputs(Materials.Air.getFluid(baseDuration))
                    .duration(baseDuration)
                    .EUt(EUt)
                    .buildAndRegister();
            GTQTcoreRecipeMaps.FUEL_CELL.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidInputs(Materials.Oxygen.getFluid(FluidStorageKeys.GAS, (int) Math.ceil(baseDuration * 1.5)))
                    .duration((int) Math.floor(baseDuration * 1.5))
                    .EUt(EUt)
                    .buildAndRegister();
            GTQTcoreRecipeMaps.FUEL_CELL.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidInputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, baseDuration * 8))
                    .duration(baseDuration * 2)
                    .EUt(EUt)
                    .buildAndRegister();

        }
        LATEX_COLLECTOR_RECIPES.recipeBuilder()
                .fluidInputs(Water.getFluid(10))
                .fluidOutputs(Latex.getFluid(100))
                .blockStates("latex_logs", MetaBlocks.RUBBER_LOG.getBlockState())
                .duration(20)
                .EUt(7)
                .buildAndRegister();

        LATEX_COLLECTOR_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(10))
                .fluidOutputs(Resin.getFluid(100))
                .blockStates("extractable_logs_1", Blocks.LOG.getBlockState())
                .duration(20)
                .EUt(7)
                .buildAndRegister();

        LATEX_COLLECTOR_RECIPES.recipeBuilder()
                .fluidInputs(Lubricant.getFluid(10))
                .fluidOutputs(Resin.getFluid(100))
                .blockStates("extractable_logs_2", Blocks.LOG2.getBlockState())
                .duration(20)
                .EUt(7)
                .buildAndRegister();

                    PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                            .input(screw, Hdcs, 1)
                            .output(ingot,Iron)
                            .EUt(VA[MV])
                            .duration(120)
                            .part(11234)
                            .buildAndRegister();

                    FLUID_EXTRACTOR_RECIPES.recipeBuilder().EUt(VA[IV]).duration(100)
                            .fluidInputs(EnrichedBacterialSludge.getFluid(1000))
                            .circuitMeta(1)
                            .fluidOutputs(Mutagen.getFluid(100))
                            .cleanroom(CleanroomType.STERILE_CLEANROOM)
                            .buildAndRegister();

                    FLUID_EXTRACTOR_RECIPES.recipeBuilder()
                            .circuitMeta(1)
                            .fluidInputs(Toluene.getFluid(30))
                            .fluidOutputs(LightFuel.getFluid(30))
                            .duration(160).EUt(24).buildAndRegister();

                    FLUID_EXTRACTOR_RECIPES.recipeBuilder()
                            .circuitMeta(1)
                            .fluidInputs(HeavyFuel.getFluid(10))
                            .fluidOutputs(Toluene.getFluid(4))
                            .duration(16).EUt(24).buildAndRegister();

                    FLUID_EXTRACTOR_RECIPES.recipeBuilder()
                            .circuitMeta(2)
                            .fluidInputs(HeavyFuel.getFluid(10))
                            .fluidOutputs(Benzene.getFluid(4))
                            .duration(16).EUt(24).buildAndRegister();

                    FLUID_EXTRACTOR_RECIPES.recipeBuilder()
                            .circuitMeta(3)
                            .fluidInputs(HeavyFuel.getFluid(20))
                            .fluidOutputs(Phenol.getFluid(5))
                            .duration(32).EUt(24).buildAndRegister();

                    FLUID_EXTRACTOR_RECIPES.recipeBuilder()
                            .fluidInputs(Water.getFluid(576))
                            .fluidOutputs(DistilledWater.getFluid(520))
                            .duration(160).EUt(VA[MV]).buildAndRegister();

                    SALT_FLIED.recipeBuilder()
                            .fluidInputs(SeaWater.getFluid(4000))
                            .fluidOutputs(SaltWater.getFluid(2000))
                            .duration(200)
                            .circuitMeta(1)
                            .buildAndRegister();


                    SALT_FLIED.recipeBuilder()
                            .fluidInputs(Water.getFluid(4000))
                            .fluidOutputs(SaltWater.getFluid(1000))
                            .duration(200)
                            .circuitMeta(1)
                            .buildAndRegister();

                    SALT_FLIED.recipeBuilder()
                            .fluidInputs(SaltWater.getFluid(4000))
                            .output(dust, Salt, 4)
                            .duration(100)
                            .circuitMeta(1)
                            .buildAndRegister();

                    SALT_FLIED.recipeBuilder()
                            .fluidInputs(Water.getFluid(4000))
                            .output(dust, Salt, 1)
                            .duration(100)
                            .circuitMeta(2)
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
                        .EUt(32)
                        .duration(480*256)
                        .buildAndRegister();

                //  Light Enriched Taranium Fuel
                NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                        .fluidInputs(LightEnrichedTaraniumFuel.getFluid(1))
                        .EUt(32)
                        .duration(240*256)
                        .buildAndRegister();

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

                GTQTcoreRecipeMaps.STAR_MIXER.recipeBuilder()
                        .fluidInputs(Oxygen.getPlasma(1000))
                        .fluidInputs(Nitrogen.getPlasma(1000))
                        .fluidInputs(Helium.getPlasma(1000))
                        .fluidOutputs(StellarMaterialResidueA.getPlasma(1000))
                        .duration(18000)
                        .EUt(7864320)
                        .circuitMeta(1)
                        .buildAndRegister();

                GTQTcoreRecipeMaps.STAR_MIXER.recipeBuilder()
                        .fluidInputs(Oxygen.getPlasma(1000))
                        .fluidInputs(Nitrogen.getPlasma(1000))
                        .fluidInputs(Helium.getPlasma(1000))
                        .fluidInputs(Iron.getPlasma(1000))
                        .fluidInputs(Nickel.getPlasma(1000))
                        .fluidInputs(Argon.getPlasma(1000))
                        .fluidOutputs(StellarMaterialResidueB.getPlasma(1000))
                        .duration(36000)
                        .EUt(7864320)
                        .circuitMeta(2)
                        .buildAndRegister();

                GTQTcoreRecipeMaps.STAR_MIXER.recipeBuilder()
                        .fluidInputs(Oxygen.getPlasma(1000))
                        .fluidInputs(Nitrogen.getPlasma(1000))
                        .fluidInputs(Helium.getPlasma(1000))
                        .fluidInputs(Iron.getPlasma(1000))
                        .fluidInputs(Nickel.getPlasma(1000))
                        .fluidInputs(Argon.getPlasma(1000))
                        .fluidInputs(Adamantium.getPlasma(1000))
                        .fluidInputs(Vibranium.getPlasma(1000))
                        .fluidInputs(StellarMaterial.getPlasma(1000))
                        .fluidOutputs(StellarMaterialResidueC.getPlasma(1000))
                        .duration(72000)
                        .EUt(7864320)
                        .circuitMeta(3)
                        .buildAndRegister();

        GTQTcoreRecipeMaps.STEAM_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(ingot, WroughtIron)
                .output(ingot, Steel)
                .duration(3600)
                .EUt(24)
                .buildAndRegister();


    }
}
