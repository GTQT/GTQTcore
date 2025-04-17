package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.GTValues;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtechfoodoption.GTFOMaterialHandler.SodiumCyanide;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

/**
 * <p>Credit to the <a href="https://github.com/GT-IMPACT">GT-IMPACT Modpack</a>.
 * This processing chain was adapted from their Hyper Fuel production process</p>
 * <p>
 * <p>
 * The Taranium Production Process
 *
 * <p>
 * Produces Taranium and Bedrock Dust from Bedrock Smoke
 * </p>
 *
 * <p>Main Products: Taranium Dust, Taranium Fuels, Bedrock Dust</p>
 * <p>Side Products: Platinum, Iridium, Osmium</p>
 */
public class TaraniumProcessing {

    public static void init() {
        BedrockProcess();
        BedrockSmokeProcess();
        TaraniumGasProcess();
        EnrichedBedrockProcess();
        EnrichedBedrockSmokeProcess();
        EnrichedTaraniumGasProcess();
        FantasyMaterials();

        //  Separation Electromagnet
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MAGNETRON)
                .input(plate, NiobiumNitride, 4)
                .input(stickLong, VanadiumGallium)
                .input(foil, Polybenzimidazole, 8)
                .input(wireFine, YttriumBariumCuprate, 16)
                .fluidInputs(BlackSteel.getFluid(L * 2))
                .output(SEPARATION_ELECTROMAGNET)
                .EUt(VA[ZPM])
                .duration(200)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
    }

    private static void FantasyMaterials() {
        StoneProcess();
    }

    private static void BedrockProcess() {

        //  Drilling Bedrock dust and smoke in Bedrock
        DRILLING_RECIPES.recipeBuilder()
                .notConsumable(new ItemStack(Blocks.BEDROCK))
                .chancedOutput(dust, Bedrock, 100, 0)
                .fluidOutputs(BedrockSmoke.getFluid(1000))
                .duration(20)
                .EUt(VA[UHV])
                .buildAndRegister();

        //  NH3 + HNO3 -> NH4NO3
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Ammonia.getFluid(1000))
                .fluidInputs(NitricAcid.getFluid(1000))
                .fluidOutputs(AmmoniumNitrate.getFluid(1000))
                .duration(60)
                .EUt(VA[GTValues.LV])
                .buildAndRegister();

        //  Bedrock Smoke -> Bedrock Soot Solution
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Naquadah)
                .fluidInputs(AmmoniumNitrate.getFluid(1000))
                .fluidInputs(BedrockSmoke.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(BedrockSootSolution.getFluid(1000))
                .duration(600)
                .EUt(1024)
                .buildAndRegister();

        //  Bedrock Soot Solution -> Clean Bedrock Solution (+ Pt, Ir, Nq)
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(BedrockSootSolution.getFluid(2000))
                .chancedOutput(dust, Platinum, 5, 1000, 0)
                .chancedOutput(dust, Iridium, 3, 1000, 0)
                .chancedOutput(dust, Naquadah, 1000, 0)
                .fluidOutputs(CleanBedrockSolution.getFluid(1000))
                .duration(600)
                .EUt(4096)
                .buildAndRegister();

        //  Clean Bedrock Solution -> Bedrock + Bedrock Smokes
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(CleanBedrockSolution.getFluid(1000))
                .output(dust, Bedrock, 3)
                .fluidOutputs(HeavyBedrockSmoke.getFluid(440))
                .fluidOutputs(MediumBedrockSmoke.getFluid(320))
                .fluidOutputs(LightBedrockSmoke.getFluid(180))
                .fluidOutputs(UltralightBedrockSmoke.getFluid(150))
                .duration(90)
                .EUt(VA[IV])
                .buildAndRegister();
    }

    private static void BedrockSmokeProcess() {

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(HeavyBedrockSmoke.getFluid(2000))
                .output(dust, Iridium)
                .output(dust, Bedrock, 3)
                .fluidOutputs(HeavyTaraniumGas.getFluid(1000))
                .duration(200)
                .EUt(VA[LuV])
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(MediumBedrockSmoke.getFluid(2000))
                .output(dust, Iridium)
                .output(dust, Bedrock, 2)
                .fluidOutputs(MediumTaraniumGas.getFluid(1000))
                .duration(140)
                .EUt(VA[LuV])
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(LightBedrockSmoke.getFluid(2000))
                .output(dust, Iridium)
                .output(dust, Bedrock)
                .fluidOutputs(LightTaraniumGas.getFluid(1000))
                .duration(90)
                .EUt(VA[LuV])
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(UltralightBedrockSmoke.getFluid(2000))
                .output(dust, Iridium)
                .chancedOutput(dust, Bedrock, 5000, 0)
                .fluidOutputs(BedrockGas.getFluid(1000))
                .duration(50)
                .EUt(VA[LuV])
                .buildAndRegister();
    }

    private static void TaraniumGasProcess() {

        CRACKING_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .fluidInputs(Fluorine.getFluid(6000))
                .fluidInputs(HeavyTaraniumGas.getFluid(1000))
                .fluidOutputs(CrackedHeavyTaranium.getFluid(2000))
                .duration(300)
                .EUt(9216)
                .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .fluidInputs(Fluorine.getFluid(4000))
                .fluidInputs(MediumTaraniumGas.getFluid(1000))
                .fluidOutputs(CrackedMediumTaranium.getFluid(1600))
                .duration(250)
                .EUt(9216)
                .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .fluidInputs(Fluorine.getFluid(2000))
                .fluidInputs(LightTaraniumGas.getFluid(1000))
                .fluidOutputs(CrackedLightTaranium.getFluid(1200))
                .duration(200)
                .EUt(9216)
                .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(CrackedHeavyTaranium.getFluid(1000))
                .output(dust, Taranium)
                .fluidOutputs(Fluorine.getFluid(250))
                .fluidOutputs(HeavyTaraniumFuel.getFluid(400))
                .fluidOutputs(MediumTaraniumFuel.getFluid(200))
                .fluidOutputs(LightTaraniumFuel.getFluid(100))
                .fluidOutputs(BedrockGas.getFluid(50))
                .duration(160)
                .EUt(16384)
                .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(CrackedMediumTaranium.getFluid(1000))
                .output(dust, Taranium)
                .fluidOutputs(Fluorine.getFluid(150))
                .fluidOutputs(HeavyTaraniumFuel.getFluid(100))
                .fluidOutputs(MediumTaraniumFuel.getFluid(400))
                .fluidOutputs(LightTaraniumFuel.getFluid(200))
                .fluidOutputs(BedrockGas.getFluid(150))
                .duration(140)
                .EUt(16384)
                .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(CrackedLightTaranium.getFluid(1000))
                .output(dust, Taranium)
                .fluidOutputs(Fluorine.getFluid(50))
                .fluidOutputs(HeavyTaraniumFuel.getFluid(50))
                .fluidOutputs(MediumTaraniumFuel.getFluid(150))
                .fluidOutputs(LightTaraniumFuel.getFluid(400))
                .fluidOutputs(BedrockGas.getFluid(350))
                .duration(120)
                .EUt(16384)
                .buildAndRegister();
    }

    private static void EnrichedBedrockProcess() {

        //  Bedrock Gas -> Enriched Bedrock Soot Solution
        MIXER_RECIPES.recipeBuilder()
                .input(dust, NaquadahEnriched)
                .fluidInputs(SulfuricAcid.getFluid(900))
                .fluidInputs(BedrockGas.getFluid(100))
                .fluidOutputs(EnrichedBedrockSootSolution.getFluid(1000))
                .duration(200)
                .EUt(VA[LuV])
                .buildAndRegister();

        //  Enriched Bedrock Soot Solution -> Clean Enriched Bedrock Solution
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(EnrichedBedrockSootSolution.getFluid(2000))
                .chancedOutput(dust, Platinum, 5, 1000, 0)
                .chancedOutput(dust, Osmium, 3, 1000, 0)
                .chancedOutput(dust, NaquadahEnriched, 1000, 0)
                .fluidOutputs(CleanEnrichedBedrockSolution.getFluid(1000))
                .duration(300)
                .EUt(98304)
                .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(CleanEnrichedBedrockSolution.getFluid(1000))
                .output(dust, Bedrock, 4)
                .fluidOutputs(HeavyEnrichedBedrockSmoke.getFluid(440))
                .fluidOutputs(MediumEnrichedBedrockSmoke.getFluid(320))
                .fluidOutputs(LightEnrichedBedrockSmoke.getFluid(180))
                .fluidOutputs(UltralightBedrockSmoke.getFluid(150))
                .duration(140)
                .EUt(40960)
                .buildAndRegister();
    }

    private static void EnrichedBedrockSmokeProcess() {

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(HeavyEnrichedBedrockSmoke.getFluid(8000))
                .output(dust, Bedrock, 5)
                .output(dust, Naquadria)
                .output(dust, Iridium, 2)
                .output(dust, Osmium, 3)
                .fluidOutputs(HeavyEnrichedTaraniumGas.getFluid(4000))
                .duration(1200)
                .EUt(VA[ZPM])
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(MediumEnrichedBedrockSmoke.getFluid(8000))
                .output(dust, Bedrock, 4)
                .output(dust, Naquadria)
                .output(dust, Iridium, 2)
                .output(dust, Osmium, 3)
                .fluidOutputs(MediumEnrichedTaraniumGas.getFluid(4000))
                .duration(960)
                .EUt(VA[ZPM])
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(LightEnrichedBedrockSmoke.getFluid(8000))
                .output(dust, Bedrock, 3)
                .output(dust, Naquadria)
                .output(dust, Iridium, 2)
                .output(dust, Osmium, 3)
                .fluidOutputs(LightEnrichedTaraniumGas.getFluid(4000))
                .duration(600)
                .EUt(VA[ZPM])
                .buildAndRegister();
    }

    private static void EnrichedTaraniumGasProcess() {

        CRACKING_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .fluidInputs(Radon.getFluid(6000))
                .fluidInputs(HeavyEnrichedTaraniumGas.getFluid(1000))
                .fluidOutputs(CrackedHeavyEnrichedTaranium.getFluid(2000))
                .duration(300).EUt(49152).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .fluidInputs(Radon.getFluid(4000))
                .fluidInputs(MediumEnrichedTaraniumGas.getFluid(1000))
                .fluidOutputs(CrackedMediumEnrichedTaranium.getFluid(1600))
                .duration(250)
                .EUt(49152)
                .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .fluidInputs(Radon.getFluid(2000))
                .fluidInputs(LightEnrichedTaraniumGas.getFluid(1000))
                .fluidOutputs(CrackedLightEnrichedTaranium.getFluid(1200))
                .duration(200)
                .EUt(49152)
                .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(CrackedHeavyEnrichedTaranium.getFluid(1000))
                .output(dust, Taranium, 2)
                .fluidOutputs(Radon.getFluid(250))
                .fluidOutputs(HeavyEnrichedTaraniumFuel.getFluid(400))
                .fluidOutputs(MediumEnrichedTaraniumFuel.getFluid(200))
                .fluidOutputs(LightEnrichedTaraniumFuel.getFluid(100))
                .fluidOutputs(BedrockGas.getFluid(50))
                .duration(160)
                .EUt(98304)
                .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(CrackedMediumEnrichedTaranium.getFluid(1000))
                .output(dust, Taranium, 2)
                .fluidOutputs(Radon.getFluid(150))
                .fluidOutputs(HeavyEnrichedTaraniumFuel.getFluid(100))
                .fluidOutputs(MediumEnrichedTaraniumFuel.getFluid(400))
                .fluidOutputs(LightEnrichedTaraniumFuel.getFluid(200))
                .fluidOutputs(BedrockGas.getFluid(150))
                .duration(140).EUt(98304).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(CrackedLightEnrichedTaranium.getFluid(1000))
                .output(dust, Taranium, 2)
                .fluidOutputs(Radon.getFluid(50))
                .fluidOutputs(HeavyEnrichedTaraniumFuel.getFluid(50))
                .fluidOutputs(MediumEnrichedTaraniumFuel.getFluid(150))
                .fluidOutputs(LightEnrichedTaraniumFuel.getFluid(400))
                .fluidOutputs(BedrockGas.getFluid(350))
                .duration(120)
                .EUt(98304)
                .buildAndRegister();
    }

    private static void StoneProcess() {

        //  24 Stone + 6HF -> H2SiF6?
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Stone, 24)
                .fluidInputs(HydrofluoricAcid.getFluid(6000))
                .fluidOutputs(DirtyHexafluorosilicicAcid.getFluid(3000))
                .EUt(VA[MV])
                .duration(SECOND * 2)
                .buildAndRegister();

        //  H2SiF6? + 4kL Red Mud -> + (H2SiF6)(H2O)2
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(DirtyHexafluorosilicicAcid.getFluid(3000))
                .fluidInputs(RedMud.getFluid(4000))
                .output(dust, StoneResidue, 12)
                .fluidOutputs(DilutedHexafluorosilicicAcid.getFluid(3000))
                .EUt(VA[GTValues.LV])
                .duration(8 * SECOND)
                .buildAndRegister();

        //  (H2SiF6)(H2O)2 -> H2SiF6 + 2H2O
        //  This is another recipe of H2SiF6 (for Power Int Circuit chain).
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(DilutedHexafluorosilicicAcid.getFluid(3000))
                .fluidOutputs(HexafluorosilicicAcid.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .EUt(VA[HV])
                .duration(4 * SECOND)
                .buildAndRegister();

        //  24 Stone Residue + NaOH + H2O -> Selected Stone Residue + Fe3O4 (chanced) + Red Mud
        ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust, StoneResidue, 24)
                .notConsumable(SEPARATION_ELECTROMAGNET)
                .fluidInputs(SodiumHydroxide.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .output(dust, SelectedStoneResidue)
                .chancedOutput(dust, Magnetite, 2500, 0)
                .fluidOutputs(RedMud.getFluid(1000))
                .EUt(VA[MV])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  2O + 2F -> F2O2
        VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .notConsumable(MICROFOCUS_X_RAY_TUBE)
                .fluidInputs(Oxygen.getFluid(FluidStorageKeys.LIQUID, 2000))
                .fluidInputs(Fluorine.getFluid(2000))
                .fluidOutputs(DioxygenDifluoride.getFluid(1000))
                .EUt(VA[HV])
                .duration(4 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Selected Stone Residue + F2O2 -> Partically Oxidized Residue
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, SelectedStoneResidue)
                .fluidInputs(DioxygenDifluoride.getFluid(1000))
                .output(dust, PartiallyOxidizedStoneResidue)
                .EUt(VA[MV])
                .duration(12 * SECOND)
                .buildAndRegister();

        //  10 Partically Oxidized Residue + 10H2O -> 10 Oxidized Residual Stone Slurry + Inert Residue
        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, PartiallyOxidizedStoneResidue, 10)
                .fluidInputs(DistilledWater.getFluid(10000))
                .output(dust, InertStoneResidue)
                .fluidOutputs(OxidizedResidualStoneSlurry.getFluid(10000))
                .EUt(VA[EV])
                .duration(5 * SECOND)
                .buildAndRegister();

        //  2 Oxidized Residual Stone Slurry -> Oxidized Stone Residue + Heavy Oxidized Residue
        DRYER_RECIPES.recipeBuilder()
                .fluidInputs(OxidizedResidualStoneSlurry.getFluid(2000))
                .output(dust, OxidizedStoneResidue)
                .output(dust, HeavyOxidizedStoneResidue)
                .EUt(VA[EV])
                .duration(4 * SECOND)
                .buildAndRegister();

        //  10 Oxidized Stone Residue + 60H -> Metallic Stone Residue + 40(HF)(H2O)
        BLAST_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .input(dust, OxidizedStoneResidue, 10)
                .fluidInputs(Hydrogen.getFluid(60000))
                .output(dust, MetallicStoneResidue)
                .fluidOutputs(DilutedHydrofluoricAcid.getFluid(40000))
                .EUt(VA[IV])
                .duration(80 * SECOND)
                .blastFurnaceTemp(3500)
                .buildAndRegister();

        //  10 Heavy Oxidized Residue + 60H -> Heavy Metallic Residue + 40(HF)(H2O)
        BLAST_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .input(dust, HeavyOxidizedStoneResidue, 10)
                .fluidInputs(Hydrogen.getFluid(60000))
                .output(dust, HeavyMetallicStoneResidue)
                .fluidOutputs(DilutedHydrofluoricAcid.getFluid(40000))
                .EUt(VA[IV])
                .duration(80 * SECOND)
                .blastFurnaceTemp(4500)
                .buildAndRegister();

        //  (HF)(H2O) -> H2O + HF
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(DilutedHydrofluoricAcid.getFluid(2000))
                .fluidOutputs(Water.getFluid(1000))
                .fluidOutputs(HydrofluoricAcid.getFluid(1000))
                .EUt(VA[MV])
                .duration(4 * SECOND)
                .buildAndRegister();

        //  10 Metallic Stone Residue -> Some residues
        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, MetallicStoneResidue, 10)
                .notConsumable(SEPARATION_ELECTROMAGNET)
                .output(dust, DiamagneticResidue, 3)
                .output(dust, ParamagneticResidue, 3)
                .output(dust, FerromagneticResidue, 3)
                .output(dust, SelectedStoneResidue)
                .EUt(VA[IV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  10 Heavy Metallic Stone Residue -> Some Heavy residues + Superheavy Stone Residue
        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, HeavyMetallicStoneResidue, 10)
                .notConsumable(SEPARATION_ELECTROMAGNET)
                .output(dust, HeavyDiamagneticResidue, 3)
                .output(dust, HeavyParamagneticResidue, 3)
                .output(dust, HeavyFerromagneticResidue, 3)
                .output(dust, SuperheavyStoneResidue)
                .EUt(VA[LuV])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  Diamagnetic Residue -> Calcium, Zinc, Copper, Gallium, Berylium, Tin
        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, DiamagneticResidue, 6)
                .chancedOutput(dust, Calcium, 2500, 0)
                .chancedOutput(dust, Zinc, 2500, 0)
                .chancedOutput(dust, Copper, 2500, 0)
                .chancedOutput(dust, Gallium, 2500, 0)
                .chancedOutput(dust, Beryllium, 2500, 0)
                .chancedOutput(dust, Tin, 2500, 0)
                .EUt(VH[IV])
                .duration(5 * SECOND)
                .buildAndRegister();

        //  Paramagnetic Residue -> Sodium, Potassium, Magnesium, Titanium, Vanadium, Manganese
        SIFTER_RECIPES.recipeBuilder()
                .input(dust, ParamagneticResidue, 6)
                .chancedOutput(dust, Sodium, 2500, 0)
                .chancedOutput(dust, Potassium, 2500, 0)
                .chancedOutput(dust, Magnesium, 2500, 0)
                .chancedOutput(dust, Titanium, 2500, 0)
                .chancedOutput(dust, Vanadium, 2500, 0)
                .chancedOutput(dust, Manganese, 2500, 0)
                .EUt(VH[IV])
                .duration(5 * SECOND)
                .buildAndRegister();

        //  Ferromagnetic Residue -> Iron, Nickel, Cobalt
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder()
                .input(dust, FerromagneticResidue, 6)
                .chancedOutput(dust, Iron, 2500, 0)
                .chancedOutput(dust, Nickel, 2500, 0)
                .chancedOutput(dust, Cobalt, 2500, 0)
                .EUt(VH[IV])
                .duration(5 * SECOND)
                .buildAndRegister();

        //  Heavy Diamagnetic Residue -> Lead, Cadmium, Indium, Gold, Bismuth
        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, HeavyDiamagneticResidue, 6)
                .chancedOutput(dust, Lead, 2500, 0)
                .chancedOutput(dust, Cadmium, 2500, 0)
                .chancedOutput(dust, Indium, 2500, 0)
                .chancedOutput(dust, Gold, 2500, 0)
                .chancedOutput(dust, Bismuth, 2500, 0)
                .fluidOutputs(Mercury.getFluid(36))
                .EUt(VH[LuV])
                .duration((int) (2.5 * SECOND))
                .buildAndRegister();

        //  Heavy Paramagnetic Residue -> Thorium, Uranium-235, Tungsten, Hafnium, Tantalum, Thallium
        SIFTER_RECIPES.recipeBuilder()
                .input(dust, HeavyParamagneticResidue, 6)
                .chancedOutput(dust, Thorium, 2500, 0)
                .chancedOutput(dust, Uranium235, 2500, 0)
                .chancedOutput(dust, Tungsten, 2500, 0)
                .chancedOutput(dust, Hafnium, 2500, 0)
                .chancedOutput(dust, Tantalum, 2500, 0)
                .chancedOutput(dust, Thallium, 2500, 0)
                .EUt(VH[LuV])
                .duration((int) (2.5 * SECOND))
                .buildAndRegister();

        //  Heavy Ferromagnetic Residue -> Dy2O3
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder()
                .input(dust, HeavyFerromagneticResidue, 6)
                .chancedOutput(dust, DysprosiumOxide, 5, 2500, 0)
                .EUt(VH[LuV])
                .duration((int) (2.5 * SECOND))
                .buildAndRegister();

        //  Superheavy Stone Residue Processing
        MIXER_RECIPES.recipeBuilder()
                .input(dust, SuperheavyStoneResidue, 16)
                .input(dust, SodiumHydroxide, 3)
                .input(PROTONATED_FULLERENE_SIEVING_MATRIX)
                .circuitMeta(4)
                .fluidInputs(DistilledWater.getFluid(2000))
                .output(SATURATED_FULLERENE_SIEVING_MATRIX)
                .EUt(VA[UHV])
                .duration(2 * SECOND)
                .buildAndRegister();

        //  Inert Residue -> Clean Inert Resique + Naquadria
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, InertStoneResidue, 10)
                .notConsumable(FluoroantimonicAcid.getFluid(1))
                .output(dust, CleanInertStoneResidue, 10)
                .chancedOutput(dust, Naquadria, 2500, 0)
                .EUt(VA[ZPM])
                .duration(16 * SECOND)
                .buildAndRegister();

        //  T + H -> TH
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Tritium.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidOutputs(TritiumHydride.getFluid(2000))
                .EUt(VH[EV])
                .duration(8 * SECOND)
                .buildAndRegister();

        //  TH -> He-3H + TH
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(TritiumHydride.getFluid(10000))
                .fluidOutputs(Helium3Hydride.getFluid(100))
                .fluidOutputs(TritiumHydride.getFluid(9900))
                .EUt(VH[HV])
                .duration(4 * SECOND)
                .buildAndRegister();

        //  Clean Inert Stone Residue + He-3H -> Ultraacidic Stone Residue Solution
        MIXER_RECIPES.recipeBuilder()
                .input(dust, CleanInertStoneResidue)
                .fluidInputs(Helium3Hydride.getFluid(1000))
                .fluidOutputs(UltraacidicStoneResidueSolution.getFluid(1000))
                .EUt(VA[EV])
                .duration(6 * SECOND)
                .buildAndRegister();

        //  Ultraacidic Stone Residue Solution -> Dusty Helium-3
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(UltraacidicStoneResidueSolution.getFluid(2000))
                .fluidInputs(Oxygen.getFluid(FluidStorageKeys.LIQUID, 4000))
                .fluidInputs(Xenon.getFluid(1000))
                .fluidOutputs(DustyHelium3.getFluid(2000))
                .fluidOutputs(XenicAcid.getFluid(1000))
                .blastFurnaceTemp(6000)
                .EUt(VA[LuV])
                .duration(6 * SECOND)
                .buildAndRegister();

        //  Another Application of H2XeO4: H2XeO4 -> XeO3 + H2O
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(XenicAcid.getFluid(1000))
                .fluidOutputs(XenonTrioxide.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[MV])
                .duration(10 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Dusty Helium-3 -> Taranium-enriched Helium-3 + Taranium-semidepleted Helium-3 + Taranium-depleted Helium-3
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(DustyHelium3.getFluid(1000))
                .fluidOutputs(TaraniumEnrichedHelium3.getFluid(100))
                .fluidOutputs(TaraniumSemidepletedHelium3.getFluid(300))
                .fluidOutputs(TaraniumDepletedHelium3.getFluid(600))
                .EUt(VA[UV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  Taranium-enriched Helium-3 -> Taranium Rich Dusty Helium Plasma
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(TaraniumEnrichedHelium3.getFluid(1000))
                .fluidInputs(Helium3.getFluid(1000))
                .fluidOutputs(TaraniumRichDustyHelium3.getPlasma(3000))
                .EUt(VA[IV])
                .duration(8 * SECOND)
                .EUToStart(480000000L)
                .buildAndRegister();

        //  Taranium Rich Dusty Helium Plasma -> Taranium rich Helium-4 (liquid), Taranium-depleted Helium-3 (plasma), Hydrogen
        CATALYTIC_REFORMER_RECIPES.recipeBuilder()
                .fluidInputs(TaraniumRichDustyHelium3.getPlasma(3000))
                .notConsumable(plate, Bedrock)
                .fluidOutputs(TaraniumRichHelium4.getPlasma(500))
                .fluidOutputs(TaraniumDepletedHelium3.getPlasma(500))
                .fluidOutputs(Hydrogen.getFluid(2000))
                .EUt(VA[ZPM])
                .duration(4 * SECOND)
                .buildAndRegister();

        //  Taranium-depleted Helium-3 (plasma) + He (plasma) -> Taranium-depleted Helium-3 (liquid)
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(TaraniumDepletedHelium3.getPlasma(1000))
                .fluidInputs(Helium.getPlasma(1000))
                .fluidOutputs(TaraniumDepletedHelium3.getFluid(2000))
                .EUt(VA[IV])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  Taranium-depleted Helium-3 (liquid) -> Helium (plasma) + Clean Inert Stone Residue
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(TaraniumDepletedHelium3.getFluid(10000))
                .notConsumable(SEPARATION_ELECTROMAGNET.getStackForm())
                .output(dust, CleanInertStoneResidue, 2)
                .fluidOutputs(Helium.getPlasma(5000))
                .EUt(VA[EV])
                .duration(10 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Taranium-semidepleted Helium-3 -> Taranium-enriched Helium-3 + Taranium-depleted Helium-3
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(TaraniumSemidepletedHelium3.getFluid(1000))
                .fluidOutputs(TaraniumEnrichedHelium3.getFluid(100))
                .fluidOutputs(TaraniumDepletedHelium3.getFluid(900))
                .EUt(VA[EV])
                .duration(20 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Taranium Rich He-4 (plasma) -> Taranium Rich He-4 (liquid)
        PLASMA_CONDENSER_RECIPES.recipeBuilder()
                .fluidInputs(TaraniumRichHelium4.getPlasma(1000))
                .fluidInputs(Helium.getFluid(FluidStorageKeys.LIQUID, 100))
                .circuitMeta(1)
                .fluidOutputs(TaraniumRichHelium4.getFluid(1000))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.GAS, 100))
                .EUt(VA[IV])
                .duration(100)
                .buildAndRegister();

        PLASMA_CONDENSER_RECIPES.recipeBuilder()
                .fluidInputs(TaraniumRichHelium4.getPlasma(1000))
                .fluidInputs(GelidCryotheum.getFluid(50))
                .circuitMeta(1)
                .fluidOutputs(TaraniumRichHelium4.getFluid(1000))
                .fluidOutputs(Ice.getFluid(50))
                .EUt(VA[IV])
                .duration(100)
                .buildAndRegister();

        //  Taranium rich He-4 -> Tn + Taranium Poor Helium
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(TaraniumRichHelium4.getFluid(400))
                .output(dust, Taranium)
                .fluidOutputs(TaraniumPoorHelium.getFluid(400))
                .EUt(VA[UHV])
                .duration(SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Taranium Poor Helium + He-3 -> Taranium Poor Helium Mixture
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(TaraniumPoorHelium.getFluid(1000))
                .fluidInputs(Helium3.getFluid(200))
                .fluidOutputs(TaraniumPoorHeliumMixture.getFluid(1200))
                .EUt(VA[LuV])
                .duration(4 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Taranium Poor Helium Mixture -> He + Dusty He-3
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(TaraniumPoorHeliumMixture.getFluid(1200))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 1000))
                .fluidOutputs(DustyHelium3.getFluid(200))
                .EUt(VA[IV])
                .duration(2 * SECOND)
                .buildAndRegister();

        //  Fullerene Polymer Matrix Processing

        //  HCHB11F11 + C6F6 + something -> Protonated Fullerene Sieving Matrix
        VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .input(dust, Fluorocarborane, 50)
                .input(wireFine, Naquadah, 6)
                .input(dust, Osmium)
                .fluidInputs(Perfluorobenzene.getFluid(2000))
                .output(PROTONATED_FULLERENE_SIEVING_MATRIX)
                .EUt(VA[UV])
                .duration(14 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Saturated Fullerene Sieving Matrix cycle

        //  Saturated Fullerene Sieving Matrix + 8H2SbF7 + 16KrF2 -> 8SbF3 + 2HCHB11F11 + 16Kr + 8 Heavy Fluorinated Trinium Solution
        CHEMICAL_RECIPES.recipeBuilder()
                .input(SATURATED_FULLERENE_SIEVING_MATRIX)
                .fluidInputs(FluoroantimonicAcid.getFluid(8000))
                .fluidInputs(KryptonDifluoride.getFluid(16000))
                .output(dust, AntimonyTrifluoride, 32)
                .output(dust, Fluorocarborane, 50)
                .fluidOutputs(Krypton.getFluid(16000))
                .fluidOutputs(HeavyFluorinatedTriniumSolution.getFluid(8000))
                .EUt(VA[ZPM])
                .duration(9 * SECOND)
                .buildAndRegister();

        //  8 Heavy Fluorinated Trinium Solution -> 12KeF4 + 16F + 2C6H6
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(HeavyFluorinatedTriniumSolution.getFluid(8000))
                .output(dust, TriniumTetrafluoride, 60)
                .fluidOutputs(Fluorine.getFluid(16000))
                .fluidOutputs(Perfluorobenzene.getFluid(2000))
                .EUt(VA[LuV])
                .duration(13 * SECOND)
                .buildAndRegister();

        //  KeF4 + 4Na -> 4NaF + Ke
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, TriniumTetrafluoride, 5)
                .input(dust, Sodium, 4)
                .output(dust, Trinium)
                .output(dust, SodiumFluoride, 8)
                .blastFurnaceTemp(4500)
                .EUt(VA[EV])
                .duration(6 * SECOND)
                .buildAndRegister();

        //  Kr + 2F -> KrF2
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Krypton.getFluid(1000))
                .fluidInputs(Fluorine.getFluid(2000))
                .fluidOutputs(KryptonDifluoride.getFluid(1000))
                .EUt(VA[HV])
                .duration((int) (6.5 * SECOND))
                .buildAndRegister();

        //  6KF + C6H6 + 6Cl -> 6KCl + C6F6 + 6H
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust, Rhenium)
                .input(dust, PotassiumFluoride, 12)
                .fluidInputs(Benzene.getFluid(1000))
                .fluidInputs(Chlorine.getFluid(6000))
                .output(dust, RockSalt, 12)
                .fluidOutputs(Perfluorobenzene.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(6000))
                .EUt(VA[HV])
                .duration((int) (8.5 * SECOND))
                .buildAndRegister();

        //  2CsCB11H12 + 2AgNO3 + 2I +44F + HCl + (CH3)3SiH -> 2HCHB11F11 + 2CsNO3 + 2AgI + 22HF + (CH3)3SiCl
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, CaesiumCarborane, 50)
                .input(dust, SilverNitrate, 10)
                .input(dust, Iodine, 2)
                .fluidInputs(Fluorine.getFluid(44000))
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .fluidInputs(Trimethylsilane.getFluid(1000))
                .output(dust, Fluorocarborane, 50)
                .output(dust, CaesiumNitrate, 10)
                .output(dust, SilverIodide, 4)
                .fluidOutputs(HydrofluoricAcid.getFluid(22000))
                .fluidOutputs(Trimethylchlorosilane.getFluid(1000))
                .EUt(VA[UHV])
                .duration(16 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Ag + HNO3 -> AgNO3 + H
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Silver)
                .circuitMeta(1)
                .fluidInputs(NitricAcid.getFluid(1000))
                .output(dust, SilverNitrate, 5)
                .fluidOutputs(Hydrogen.getFluid(1000))
                .EUt(VA[MV])
                .duration((int) (7.5 * SECOND))
                .buildAndRegister();

        //  Ag2O + 2HNO3 -> 2AgNO3 + H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SilverOxide)
                .circuitMeta(2)
                .fluidInputs(NitricAcid.getFluid(2000))
                .output(dust, SilverNitrate, 10)
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[MV])
                .duration((int) (7.5 * SECOND))
                .buildAndRegister();

        //  CsB10H12CN(CH3)3Cl + LiH + C2H9BS + -> CsCB11H12 + LiCl + (CH3)3N + H2S + 2CH4
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, CaesiumCarboranePrecursor, 38)
                .input(dust, LithiumHydride, 2)
                .notConsumable(Tetrahydrofuran.getFluid(1))
                .fluidInputs(BoraneDimethylsulfide.getFluid(1000))
                .output(dust, CaesiumCarborane, 25)
                .output(dust, LithiumChloride, 2)
                .fluidOutputs(Trimethylamine.getFluid(1000))
                .fluidOutputs(HydrogenSulfide.getFluid(1000))
                .fluidOutputs(Methane.getFluid(2000))
                .EUt(VA[LuV])
                .duration(13 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  CsOH + B10H14 + NaCN + 2HCl + 3CH4O -> CsB10H12CN(CH3)3Cl + 2NaCl + 4H2O
        CRYOGENIC_REACTOR_RECIPES.recipeBuilder()
                .input(dust, CaesiumHydroxide, 3)
                .input(dust, Decaborane, 24)
                .inputs(SodiumCyanide.getItemStack(3))
                .notConsumable(SulfuricAcid.getFluid(1))
                .fluidInputs(HydrochloricAcid.getFluid(2000))
                .fluidInputs(Methanol.getFluid(3000))
                .output(dust, CaesiumCarboranePrecursor, 38)
                .output(dust, Salt, 2)
                .fluidOutputs(Water.getFluid(4000))
                .EUt(VA[IV])
                .duration(12 * SECOND)
                .temperature(62)
                .buildAndRegister();

        //  2Cs + H2O2 -> 2CsOH
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, Caesium, 2)
                .fluidInputs(HydrogenPeroxide.getFluid(1000))
                .output(dust, CaesiumHydroxide, 6)
                .EUt(VA[HV])
                .duration(9 * SECOND)
                .buildAndRegister();

        //  8.5NaBH4 + HF + 2H2O2 + 10(BF3)(C2H5)2O -> B10H14 + NaF + 7.5NaBF4 + H2O + 20H + 10(C2H5)2O
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, SodiumBorohydride, 51)
                .fluidInputs(HydrofluoricAcid.getFluid(1000))
                .fluidInputs(HydrogenPeroxide.getFluid(2000))
                .fluidInputs(BoronTrifluorideEtherate.getFluid(10000))
                .output(dust, Decaborane, 24)
                .output(dust, SodiumFluoride, 2)
                .output(dust, SodiumTetrafluoroborate, 45)
                .fluidOutputs(Water.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(20000))
                .fluidOutputs(DiethylEther.getFluid(10000))
                .EUt(VA[IV])
                .duration(14 * SECOND)
                .blastFurnaceTemp(6000)
                .buildAndRegister();

        //  8Na + 4LiH -> NaBF4 + H3BO3 + 3C2H6O -> 3C2H5ONa + 4Li + 3H2O
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Sodium, 8)
                .input(dust, LithiumHydride, 8)
                .fluidInputs(BoricAcid.getFluid(1000))
                .fluidInputs(Ethanol.getFluid(3000))
                .notConsumable(SulfuricAcid.getFluid(1))
                .output(dust, SodiumBorohydride, 6)
                .output(dust, SodiumEthoxide, 27)
                .output(dust, Lithium, 4)
                .fluidOutputs(Water.getFluid(3000))
                .EUt(VA[EV])
                .duration(6 * SECOND)
                .buildAndRegister();

        //  NaBF4 -> NaF + BF3
        ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust, SodiumTetrafluoroborate, 6)
                .output(dust, SodiumFluoride, 2)
                .fluidOutputs(BoronTrifluoride.getFluid(1000))
                .EUt(VA[MV])
                .duration(6 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  (C2H5)2O + BF3 -> (BF3)(C2H5)2O
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(DiethylEther.getFluid(1000))
                .fluidInputs(BoronTrifluoride.getFluid(1000))
                .fluidOutputs(BoronTrifluorideEtherate.getFluid(1000))
                .EUt(VA[MV])
                .duration((int) (7.5 * SECOND))
                .buildAndRegister();

        //  C2H6O + C2H4 -> (C2H5)2O
        VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .notConsumable(spring, Cupronickel)
                .fluidInputs(Ethylene.getFluid(1000))
                .fluidInputs(Ethanol.getFluid(1000))
                .fluidOutputs(DiethylEther.getFluid(1000))
                .EUt(VA[MV])
                .duration(14 * SECOND)
                .buildAndRegister();

        //  B2H6 + (CH3)2S -> C2H9BS
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Diborane.getFluid(1000))
                .fluidInputs(DimethylSulfide.getFluid(1000))
                .fluidOutputs(BoraneDimethylsulfide.getFluid(2000))
                .EUt(VA[LuV])
                .duration(100)
                .buildAndRegister();

        //  2AgI + O -> Ag2O + 2I
        ROASTER_RECIPES.recipeBuilder()
                .input(dust, SilverIodide, 4)
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust, SilverOxide, 3)
                .output(dust, Iodine, 2)
                .EUt(VA[MV])
                .duration((int) (10.5 * SECOND))
                .temperature(1100)
                .buildAndRegister();

        //  LiH + (CH3)3SiCl -> LiCl + (CH3)3SiH
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, LithiumHydride, 2)
                .fluidInputs(Trimethylchlorosilane.getFluid(1000))
                .output(dust, LithiumChloride, 2)
                .fluidOutputs(Trimethylsilane.getFluid(1000))
                .EUt(VA[EV])
                .duration((int) (6.5 * SECOND))
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, SodiumEthoxide, 9)
                .output(SODIUM_ETHYLATE,9)
                .EUt(VA[MV])
                .duration(280)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
    }
}
