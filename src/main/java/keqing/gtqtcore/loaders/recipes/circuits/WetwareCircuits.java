package keqing.gtqtcore.loaders.recipes.circuits;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.GTRecipeHandler.removeRecipesByInputs;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtechfoodoption.GTFOMaterialHandler.AceticAnhydride;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Polyetheretherketone;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class WetwareCircuits {

    public static void init() {
        // Harder Wetware
        WetwareSoC();
        AdvancedWetwareProcessingUnit();
        CircuitBoard();
        StemCell();
        RawGrowthMedium();
        // Fix autoclave recipe to obtain wetware mainframes
        removeRecipesByInputs(AUTOCLAVE_RECIPES,
                new ItemStack[]{QUANTUM_STAR.getStackForm()},
                new FluidStack[]{Neutronium.getFluid(L * 2)});

        AUTOCLAVE_RECIPES.recipeBuilder()
                .input(QUANTUM_STAR)
                .fluidInputs(Orichalcum.getFluid(L * 2))
                .output(GRAVI_STAR)
                .duration(480).EUt(VA[IV]).buildAndRegister();
    }

    private static void RawGrowthMedium() {
        //raw growth medium
        removeRecipesByInputs(MIXER_RECIPES, new ItemStack[]{OreDictUnifier.get(dust, Meat, 4), OreDictUnifier.get(dust, Salt, 4), OreDictUnifier.get(dust, Calcium, 4), OreDictUnifier.get(dust, Agar, 4)}, new FluidStack[]{Mutagen.getFluid(4000)});

        BIO_CENTRIFUGE.recipeBuilder()
                .fluidInputs(BloodPlasma.getFluid(1000))
                .fluidOutputs(Catalase.getFluid(200))
                .fluidOutputs(BFGF.getFluid(200))
                .fluidOutputs(EGF.getFluid(200))
                .EUt(480)
                .duration(50)
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .fluidInputs(Biotin.getFluid(1000))
                .fluidInputs(LinoleicAcid.getFluid(1000))
                .fluidInputs(Catalase.getFluid(1000))
                .fluidInputs(VitaminA.getFluid(1000))
                .fluidInputs(Ethanolamine.getFluid(1000))
                .fluidOutputs(B27Supplement.getFluid(5000))
                .EUt(7680)
                .duration(150)
                .recipeLevel(7)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(Ammonia.getFluid(1000))
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidOutputs(CleanAmmoniaSolution.getFluid(2000))
                .EUt(480)
                .duration(100)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(CleanAmmoniaSolution.getFluid(1000))
                .input(dust, BrevibacteriumFlavium, 1)
                .input(dust, Sugar)
                .output(dust, Glutamine, 40)
                .EUt(30720)
                .duration(500)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .fluidInputs(B27Supplement.getFluid(1000))
                .fluidInputs(AmmoniumNitrate.getFluid(1000))
                .input(dust, Glutamine, 20)
                .fluidInputs(BFGF.getFluid(1000))
                .fluidInputs(EGF.getFluid(1000))
                .fluidOutputs(RawGrowthMedium.getFluid(4000))
                .EUt(480)
                .duration(500)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .recipeLevel(3)
                .buildAndRegister();
    }

    private static void StemCell() {
        removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(dust, Osmiridium, 1)}, new FluidStack[]{SterileGrowthMedium.getFluid(500),Bacteria.getFluid(500)});
        removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(dust, Osmiridium, 1)}, new FluidStack[]{SterileGrowthMedium.getFluid(500),Bacteria.getFluid(500)});

        //干细胞重做
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Meat)
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidOutputs(AnimalCells.getFluid(1000))
                .EUt(480)
                .duration(100)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .notConsumable(dust, Naquadria)
                .fluidInputs(AnimalCells.getFluid(1000))
                .fluidOutputs(RapidlyReplicatingAnimalCells.getFluid(1000))
                .EUt(7680)
                .duration(500)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .buildAndRegister();



        GENE_MUTAGENESIS.recipeBuilder()
                .fluidInputs(RapidlyReplicatingAnimalCells.getFluid(1000))
                .notConsumable(new IntCircuitIngredient(1))
                .EUt(480)
                .duration(100)
                .fluidOutputs(MycGene.getFluid(1000))
                .rate(10)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .buildAndRegister();

        GENE_MUTAGENESIS.recipeBuilder()
                .fluidInputs(RapidlyReplicatingAnimalCells.getFluid(1000))
                .notConsumable(new IntCircuitIngredient(2))
                .EUt(480)
                .duration(100)
                .fluidOutputs(Oct4Gene.getFluid(1000))
                .rate(10)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .buildAndRegister();

        GENE_MUTAGENESIS.recipeBuilder()
                .fluidInputs(RapidlyReplicatingAnimalCells.getFluid(1000))
                .notConsumable(new IntCircuitIngredient(3))
                .EUt(480)
                .duration(100)
                .fluidOutputs(SOX2Gene.getFluid(1000))
                .rate(10)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .buildAndRegister();

        GENE_MUTAGENESIS.recipeBuilder()
                .fluidInputs(RapidlyReplicatingAnimalCells.getFluid(1000))
                .notConsumable(new IntCircuitIngredient(4))
                .EUt(480)
                .duration(100)
                .fluidOutputs(KFL4Gene.getFluid(1000))
                .rate(10)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(SterileGrowthMedium.getFluid(1000))
                .fluidInputs(AnimalCells.getFluid(1000))
                .fluidInputs(GeneTherapyFluid.getFluid(1000))
                .EUt(30720)
                .duration(1000)
                .outputs(STEM_CELLS.getStackForm())
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .inputs(STEM_CELLS.getStackForm())
                .fluidInputs(SterileGrowthMedium.getFluid(1000))
                .outputs(STEM_CELLS.getStackForm(2))
                .fluidOutputs(DepletedGrowthMedium.getFluid(500))
                .EUt(480)
                .duration(100)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .buildAndRegister();

        FERMENTING_RECIPES.recipeBuilder()
                .fluidInputs(DepletedGrowthMedium.getFluid(1000))
                .fluidOutputs(FermentedBiomass.getFluid(1000))
                .EUt(30)
                .duration(100)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .notConsumable(ULTRASONIC_HOMOGENIZER.getStackForm())
                .input(dust, StreptococcusPyogenes, 1)
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidOutputs(Cas9.getFluid(1000))
                .EUt(480)
                .duration(100)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .fluidInputs(Cas9.getFluid(1000))
                .fluidInputs(MycGene.getFluid(1000))
                .fluidInputs(Oct4Gene.getFluid(1000))
                .fluidInputs(SOX2Gene.getFluid(1000))
                .fluidInputs(KFL4Gene.getFluid(1000))
                .input(dust, EschericiaColi, 1)
                .fluidOutputs(GenePlasmids.getFluid(5000))
                .EUt(1920)
                .duration(50)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .recipeLevel(7)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .notConsumable(ULTRASONIC_HOMOGENIZER.getStackForm())
                .inputs(new ItemStack(Blocks.BROWN_MUSHROOM))
                .fluidOutputs(Chitin.getFluid(100))
                .EUt(30)
                .duration(100)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .notConsumable(ULTRASONIC_HOMOGENIZER.getStackForm())
                .inputs(new ItemStack(Blocks.RED_MUSHROOM))
                .fluidOutputs(Chitin.getFluid(100))
                .EUt(30)
                .duration(100)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .notConsumable(ULTRASONIC_HOMOGENIZER.getStackForm())
                .inputs(new ItemStack(Blocks.BROWN_MUSHROOM, 1))
                .fluidOutputs(Chitin.getFluid(100))
                .EUt(30)
                .duration(100)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .notConsumable(ULTRASONIC_HOMOGENIZER.getStackForm())
                .inputs(new ItemStack(Blocks.RED_MUSHROOM, 1))
                .fluidOutputs(Chitin.getFluid(100))
                .EUt(30)
                .duration(100)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Chitin.getFluid(1000))
                .input(dust, BifidobacteriumBreve, 1)
                .fluidOutputs(Chitosan.getFluid(1000))
                .EUt(120)
                .duration(100)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(GenePlasmids.getFluid(1000))
                .fluidInputs(Chitosan.getFluid(1000))
                .fluidOutputs(GeneTherapyFluid.getFluid(2000))
                .EUt(7680)
                .duration(25)
                .buildAndRegister();
    }

    private static void CircuitBoard() {

        //培养基重做
        //sterilized growth medium
        removeRecipesByInputs(FLUID_HEATER_RECIPES, new ItemStack[]{IntCircuitIngredient.getIntegratedCircuit(1)}, new FluidStack[]{RawGrowthMedium.getFluid(100)});

        // HCl + 2H2SO4 + O -> HSO3Cl + 2H2O + SO3
        CHEMICAL_PLANT.recipeBuilder()
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .fluidInputs(SulfuricAcid.getFluid(2000))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(ChlorosulfuricAcid.getFluid(2000))
                .fluidOutputs(Water.getFluid(2000))
                .fluidOutputs(SulfurTrioxide.getFluid(1000))
                .recipeLevel(7)
                .EUt(480)
                .duration(200)
                .buildAndRegister();

        // C6H5NH2 + (CH3CO)2O + HSO3Cl -> C8H8ClNO3S + H2O + CH3COOH
        CHEMICAL_PLANT.recipeBuilder()
                .fluidInputs(Aniline.getFluid(1000))
                .fluidInputs(AceticAnhydride.getFluid(1000))
                .fluidInputs(ChlorosulfuricAcid.getFluid(1000))
                .fluidOutputs(AcetylsulfanilylChloride.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .fluidOutputs(AceticAcid.getFluid(1000))
                .recipeLevel(7)
                .EUt(1920)
                .duration(100)
                .buildAndRegister();

        // NaHCO3 + C8H8ClNO3S + NH3 -> NaCl + C6H8N2O2S + CO2 + CH3COOH
        CHEMICAL_PLANT.recipeBuilder()
                .input(dust, SodiumBicarbonate, 6)
                .fluidInputs(AcetylsulfanilylChloride.getFluid(1000))
                .fluidInputs(Ammonia.getFluid(1000))
                .output(dust, Salt, 2)
                .fluidOutputs(Sulfanilamide.getFluid(1000))
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .fluidOutputs(AceticAcid.getFluid(1000))
                .recipeLevel(7)
                .EUt(30720)
                .duration(50)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(RawGrowthMedium.getFluid(1000))
                .fluidInputs(Sulfanilamide.getFluid(250))
                .fluidOutputs(SterileGrowthMedium.getFluid(1250))
                .EUt(7680)
                .duration(100)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .buildAndRegister();

        //  Delete original recipe
        removeRecipesByInputs(CIRCUIT_ASSEMBLER_RECIPES,
                new ItemStack[]{MULTILAYER_FIBER_BOARD.getStackForm(16),
                        PETRI_DISH.getStackForm(),
                        ELECTRIC_PUMP_LuV.getStackForm(),
                        SENSOR_IV.getStackForm(),
                        OreDictUnifier.get(circuit, MarkerMaterials.Tier.IV),
                        OreDictUnifier.get(foil, NiobiumTitanium, 16)},
                new FluidStack[]{SterileGrowthMedium.getFluid(4000)});

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(VA[IV])
                .input(foil, Yttrium, 4)
                .input(ACRYLIC_YARN,16)
                .fluidInputs(Polybenzimidazole.getFluid(288))
                .fluidInputs(UltraGlue.getFluid(500))
                .output(LAMINATION_YR, 4)
                .buildAndRegister();

        PRESSURE_LAMINATOR_RECIPES.recipeBuilder()
                .duration(200)
                .EUt(VA[LuV])
                .input(LAMINATION_YR)
                .input(foil, Samarium, 8)
                .fluidInputs(Polyetheretherketone.getFluid(576))
                .fluidInputs(UltraGlue.getFluid(576))
                .output(IMPREGNATED_BIO_BOARD, 2)
                .buildAndRegister();

        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(IMPREGNATED_BIO_BOARD, 16)
                .input(ELECTRICALLY_WIRED_PETRI_DISH)
                .input(ELECTRIC_PUMP_LuV)
                .input(SENSOR_IV)
                .input(circuit, MarkerMaterials.Tier.LuV)
                .input(foil, NaquadahAlloy, 16)
                .fluidInputs(SterileGrowthMedium.getFluid(16000))
                .output(WETWARE_BOARD, 16)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .duration(1200)
                .EUt(VA[LuV])
                .buildAndRegister();

    }

    private static void AdvancedWetwareProcessingUnit() {

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Polybenzimidazole)
                .input(STEM_CELLS, 4)
                .input(pipeTinyFluid, Gold)
                .input(bolt, HSSS, 4)
                .fluidInputs(KaptonE.getFluid(L * 4))
                .fluidInputs(SterileGrowthMedium.getFluid(125))
                .output(NEURO_PROCESSOR, 2)
                .EUt(VA[UV])
                .CWUt(CWT[UV])
                .duration(10 * 20)
                .Tier(3)
                .buildAndRegister();
    }

    private static void WetwareSoC() {

        //  Remove original recipes.
        removeRecipesByInputs(CIRCUIT_ASSEMBLER_RECIPES,
                new ItemStack[]{NEURO_PROCESSOR.getStackForm(),
                        HIGHLY_ADVANCED_SOC.getStackForm(),
                        OreDictUnifier.get(wireFine, YttriumBariumCuprate, 8),
                        OreDictUnifier.get(bolt, Naquadah, 8)},
                new FluidStack[]{SolderingAlloy.getFluid(L / 2)});

        removeRecipesByInputs(CIRCUIT_ASSEMBLER_RECIPES,
                new ItemStack[]{NEURO_PROCESSOR.getStackForm(),
                        HIGHLY_ADVANCED_SOC.getStackForm(),
                        OreDictUnifier.get(wireFine, YttriumBariumCuprate, 8),
                        OreDictUnifier.get(bolt, Naquadah, 8)},
                new FluidStack[]{Tin.getFluid(L)});

        //  Ruby Chip -> Wetware Crystal Chip
        CVD_RECIPES.recipeBuilder()
                .input(CRYSTAL_MODULATOR_RUBY)
                .notConsumable(lens, Ruby)
                .fluidInputs(SterileGrowthMedium.getFluid(L))
                .fluidInputs(Americium.getFluid(16))
                .output(WETWARE_CRYSTAL_CHIP)
                .EUt(VA[UV])
                .duration(2 * 20)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Wetware Crystal Chip -> Rich Bacteria SoC
        FORMING_PRESS_RECIPES.recipeBuilder()
                .input(WETWARE_CRYSTAL_CHIP)
                .input(ring, PPB, 2)
                .input(HIGHLY_ADVANCED_SOC, 4)
                .output(RICH_BACTERIA_SOC, 2)
                .EUt(VA[UV])
                .duration(5 * 20)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Add new Rich bacteria SoC for Wetware Processor.
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(WETWARE_CIRCUIT_BOARD)
                .input(RICH_BACTERIA_SOC)
                .input(wireFine, YttriumBariumCuprate, 8)
                .input(bolt, Naquadah, 8)
                .output(WETWARE_PROCESSOR_LUV, 4)
                .EUt(150000)
                .duration(5 * 20)
                .cleanroom(CleanroomType.CLEANROOM)
                .solderMultiplier(1)
                .buildAndRegister();
    }
}
