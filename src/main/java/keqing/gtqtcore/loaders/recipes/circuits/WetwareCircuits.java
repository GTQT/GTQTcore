package keqing.gtqtcore.loaders.recipes.circuits;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CVD_RECIPES;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.PRECISE_ASSEMBLER_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.PPB;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class WetwareCircuits {

    public static void init() {
        // Harder Wetware
        WetwareSoC();
        AdvancedWetwareProcessingUnit();
        CircuitBoard();

        // Fix autoclave recipe to obtain wetware mainframes
        GTRecipeHandler.removeRecipesByInputs(AUTOCLAVE_RECIPES,
                new ItemStack[]{QUANTUM_STAR.getStackForm()},
                new FluidStack[]{Neutronium.getFluid(L * 2)});

        AUTOCLAVE_RECIPES.recipeBuilder()
                .input(QUANTUM_STAR)
                .fluidInputs(Orichalcum.getFluid(L * 2))
                .output(GRAVI_STAR)
                .duration(480).EUt(VA[IV]).buildAndRegister();
    }

    private static void CircuitBoard() {

        //  Delete original recipe
        GTRecipeHandler.removeRecipesByInputs(CIRCUIT_ASSEMBLER_RECIPES,
                new ItemStack[]{MULTILAYER_FIBER_BOARD.getStackForm(16),
                        PETRI_DISH.getStackForm(),
                        ELECTRIC_PUMP_LuV.getStackForm(),
                        SENSOR_IV.getStackForm(),
                        OreDictUnifier.get(circuit, MarkerMaterials.Tier.IV),
                        OreDictUnifier.get(foil, NiobiumTitanium, 16)},
                new FluidStack[]{SterileGrowthMedium.getFluid(4000)});

        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, KaptonK, 16)
                .input(PETRI_DISH)
                .input(ELECTRIC_PUMP_LuV)
                .input(SENSOR_IV)
                .input(circuit, MarkerMaterials.Tier.IV)
                .input(foil, NiobiumTitanium, 16)
                .fluidInputs(SterileGrowthMedium.getFluid(4000))
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
        GTRecipeHandler.removeRecipesByInputs(CIRCUIT_ASSEMBLER_RECIPES,
                new ItemStack[]{NEURO_PROCESSOR.getStackForm(),
                        HIGHLY_ADVANCED_SOC.getStackForm(),
                        OreDictUnifier.get(wireFine, YttriumBariumCuprate, 8),
                        OreDictUnifier.get(bolt, Naquadah, 8)},
                new FluidStack[]{SolderingAlloy.getFluid(L / 2)});

        GTRecipeHandler.removeRecipesByInputs(CIRCUIT_ASSEMBLER_RECIPES,
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
