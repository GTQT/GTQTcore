package keqing.gtqtcore.loaders.recipes;

import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.unification.material.MarkerMaterials;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;

public class FuelRecipes {


    public static void init(){

        //test
        STAR_SURVEY.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.HV, 1)
                .input(FIELD_GENERATOR_HV, 1)
                .input(EMITTER_HV, 1)
                .input(plateDouble, Platinum,16)
                .output(COMPUTERTIER1)
                .NB(1)
                .CWUt(24)
                .totalCWU(240000)
                .EUt(30)
                .duration(20)
                .buildAndRegister();

        STAR_SURVEY.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.EV, 2)
                .input(FIELD_GENERATOR_EV, 2)
                .input(EMITTER_EV, 2)
                .input(plateDouble, Platinum,16)
                .output(COMPUTERTIER2)
                .NB(2)
                .CWUt(96)
                .totalCWU(960000)
                .EUt(30)
                .duration(20)
                .buildAndRegister();

        STAR_SURVEY.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.IV, 4)
                .input(FIELD_GENERATOR_IV, 4)
                .input(EMITTER_IV, 4)
                .input(plateDouble, Platinum,16)
                .output(COMPUTERTIER3)
                .NB(3)
                .CWUt(384)
                .totalCWU(3840000)
                .EUt(30)
                .duration(20)
                .buildAndRegister();

        STAR_SURVEY.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.LuV, 8)
                .input(FIELD_GENERATOR_LuV, 8)
                .input(EMITTER_LuV, 8)
                .input(plateDouble, Platinum,16)
                .output(COMPUTERTIER4)
                .NB(4)
                .CWUt(1536)
                .totalCWU(15360000)
                .EUt(30)
                .duration(20)
                .buildAndRegister();

        STAR_SURVEY.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.ZPM, 16)
                .input(FIELD_GENERATOR_ZPM, 16)
                .input(EMITTER_ZPM, 16)
                .input(plateDouble, Platinum,16)
                .output(COMPUTERTIER5)
                .NB(5)
                .CWUt(6144)
                .totalCWU(514)
                .EUt(30)
                .duration(61440000)
                .buildAndRegister();

        STAR_SURVEY.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.UHV, 32)
                .input(FIELD_GENERATOR_UHV, 32)
                .input(EMITTER_UHV, 32)
                .input(plateDouble, Platinum,16)
                .output(COMPUTERTIER6)
                .NB(6)
                .CWUt(24576)
                .totalCWU(514)
                .EUt(30)
                .duration(245760000)
                .buildAndRegister();

                    MINING_DRILL_RECIPES.recipeBuilder()
                            .chancedOutput(ore,Copper,5000,500)
                            .chancedOutput(ore,Iron,5000,500)
                            .chancedOutput(ore,Tin,5000,500)
                            .chancedOutput(ore,Coal,5000,500)
                            .tier(1)
                            .EUt(30)
                            .duration(20)
                            .circuitMeta(1)
                            .buildAndRegister();

                    BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                            .input(screw, Hdcs, 1)
                            .output(ingot,Iron)
                            .EUt(VA[MV])
                            .duration(120)
                            .rate(60)
                            .buildAndRegister();

                    PARTICLE_ACCELERATOR_RECIPES.recipeBuilder()
                            .input(screw, Hdcs, 1)
                            .output(ingot,Iron)
                            .EUt(VA[MV])
                            .duration(120)
                            .part(11234)
                            .buildAndRegister();

                    CW_PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                            .input(screw, Hdcs, 1)
                            .fluidInputs(SolderingAlloy.getFluid(L * 1))
                            .output(ingot,Iron)
                            .EUt(VA[UHV])
                            .duration(120)
                            .totalCWU(600)
                            .CWUt(3)
                            .buildAndRegister();

                    PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                            .input(screw, Hdcs, 4)
                            .fluidInputs(SolderingAlloy.getFluid(L * 8))
                            .fluidInputs(Kevlar.getFluid(L * 4))
                            .output(ingot,Iron)
                            .EUt(VA[UHV])
                            .duration(120)
                            .CasingTier(3)
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
                            .fluidInputs(SeaWater.getFluid(16000))
                            .fluidOutputs(SaltWater.getFluid(800))
                            .duration(200)
                            .circuitMeta(1)
                            .buildAndRegister();


                    SALT_FLIED.recipeBuilder()
                            .fluidInputs(Water.getFluid(16000))
                            .fluidOutputs(SaltWater.getFluid(1600))
                            .duration(2000)
                            .circuitMeta(1)
                            .buildAndRegister();

                    SALT_FLIED.recipeBuilder()
                            .fluidInputs(SaltWater.getFluid(1600))
                            .output(dust, Salt, 1200)
                            .duration(500)
                            .circuitMeta(1)
                            .buildAndRegister();

                    SALT_FLIED.recipeBuilder()
                            .fluidInputs(Water.getFluid(16000))
                            .output(dust, Salt, 800)
                            .duration(2000)
                            .circuitMeta(2)
                            .buildAndRegister();

                //  Heavy Naquadah Fuel
                NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                        .fluidInputs(HeavyNaquadahFuel.getFluid(1))
                        .EUt(32)
                        .duration(180*65)
                        .buildAndRegister();

                //  Medium Naquadah Fuel
                NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                        .fluidInputs(MediumNaquadahFuel.getFluid(1))
                        .EUt(32)
                        .duration(120*64)
                        .buildAndRegister();

                //  Light Naquadah Fuel
                NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                        .fluidInputs(LightNaquadahFuel.getFluid(1))
                        .EUt(32)
                        .duration(60*64)
                        .buildAndRegister();

                //  Heavy Taranium Fuel
                NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                        .fluidInputs(HeavyTaraniumFuel.getFluid(1))
                        .EUt(32)
                        .duration(360*128)
                        .buildAndRegister();

                //  Medium Taranium Fuel
                NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                        .fluidInputs(MediumTaraniumFuel.getFluid(1))
                        .EUt(32)
                        .duration(240*128)
                        .buildAndRegister();

                //  Light Taranium Fuel
                NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                        .fluidInputs(LightTaraniumFuel.getFluid(1))
                        .EUt(32)
                        .duration(120*128)
                        .buildAndRegister();

                //  Heavy Enriched Taranium Fuel
                NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                        .fluidInputs(HeavyEnrichedTaraniumFuel.getFluid(1))
                        .EUt(32)
                        .duration(720*256)
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

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Naphtha.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(10)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(SulfuricLightFuel.getFluid(4))
                .fluidOutputs(HighPressureSteam.getFluid(10))
                .duration(5)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Methanol.getFluid(4))
                .fluidOutputs(HighPressureSteam.getFluid(16))
                .duration(8)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Ethanol.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(12))
                .duration(6)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Octane.getFluid(2))
                .fluidOutputs(HighPressureSteam.getFluid(10))
                .duration(5)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(BioDiesel.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(16))
                .duration(8)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(LightFuel.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(10)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Diesel.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(30))
                .duration(15)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(CetaneBoostedDiesel.getFluid(2))
                .fluidOutputs(HighPressureSteam.getFluid(90))
                .duration(45)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(RocketFuel.getFluid(16))
                .fluidOutputs(HighPressureSteam.getFluid(250))
                .duration(125)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Gasoline.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(100))
                .duration(50)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(HighOctaneGasoline.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(200))
                .duration(100)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Toluene.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(200))
                .duration(10)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(OilLight.getFluid(32))
                .fluidOutputs(HighPressureSteam.getFluid(10))
                .duration(5)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(RawOil.getFluid(64))
                .fluidOutputs(HighPressureSteam.getFluid(30))
                .duration(15)
                .EUt((int) V[LV])
                .buildAndRegister();




    }
}
