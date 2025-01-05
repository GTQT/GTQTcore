package keqing.gtqtcore.loaders.recipes.circuits;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;


import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.PLASMA_CVD_RECIPES;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.PRECISE_ASSEMBLER_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.CeLAG;
import static keqing.gtqtcore.api.unification.GTQTMaterials.CeriumOxide;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class CrystalCircuits {

    public static void init() {
        crystalInterface();
        crystalModulators();
        crystalSOC();
        AdvancedCrystalSoC();
        removeGTCERecipes();

    }

    private static void removeGTCERecipes() {
        GTRecipeHandler.removeRecipesByInputs(LASER_ENGRAVER_RECIPES,
                CRYSTAL_CENTRAL_PROCESSING_UNIT.getStackForm(),
                OreDictUnifier.get(craftingLens, MarkerMaterials.Color.Blue)
        );
    }

    private static void crystalInterface() {
        GTQTcoreRecipeMaps.CRYSTALLIZER_RECIPES.recipeBuilder()
                .input(dust, CubicZirconia, 64)
                .input(dust, Europium, 8)
                .blastFurnaceTemp(3000)
                .output(EU_DOPED_CUBIC_ZIRCONIA_BOULE)
                .duration(120)
                .EUt(60)
                .buildAndRegister();

        RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                .input(EU_DOPED_CUBIC_ZIRCONIA_BOULE)
                .output(EU_DOPED_CUBIC_ZIRCONIA_WAFER, 8)
                .duration(100)
                .EUt(20)
                .buildAndRegister();

        LASER_ENGRAVER_RECIPES.recipeBuilder()
                .input(EU_DOPED_CUBIC_ZIRCONIA_WAFER)
                .notConsumable(lens, Diamond)
                .output(CRYSTAL_INTERFACE_WAFER)
                .duration(20)
                .EUt(100)
                .buildAndRegister();

        RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                .input(CRYSTAL_INTERFACE_WAFER)
                .output(CRYSTAL_INTERFACE_CHIP, 8)
                .duration(100)
                .EUt(20)
                .buildAndRegister();
    }

    private static void crystalModulators() {
        LASER_ENGRAVER_RECIPES.recipeBuilder()
                .input(gemExquisite, Sapphire)
                .notConsumable(GLASS_LENSES.get(MarkerMaterials.Color.Blue))
                .output(ENGRAVED_SAPPHIRE_CRYSTAL_CHIP)
                .duration(1200).EUt(VA[HV]).buildAndRegister();

        LASER_ENGRAVER_RECIPES.recipeBuilder()
                .input(gemExquisite, Ruby)
                .notConsumable(GLASS_LENSES.get(MarkerMaterials.Color.Red))
                .output(ENGRAVED_RUBY_CRYSTAL_CHIP)
                .duration(1200).EUt(VA[HV]).buildAndRegister();

        LASER_ENGRAVER_RECIPES.recipeBuilder()
                .input(gemExquisite, Diamond)
                .notConsumable(GLASS_LENSES.get(MarkerMaterials.Color.LightBlue))
                .output(ENGRAVED_DIAMOND_CRYSTAL_CHIP)
                .duration(1200).EUt(VA[HV]).buildAndRegister();

        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(ENGRAVED_SAPPHIRE_CRYSTAL_CHIP)
                .input(PLASTIC_CIRCUIT_BOARD)
                .input(wireFine, Palladium, 8)
                .input(bolt, Platinum, 4)
                .output(CRYSTAL_MODULATOR_SAPPHIRE, 8)
                .solderMultiplier(1)
                .duration(200).EUt(VA[IV]).buildAndRegister();

        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(ENGRAVED_RUBY_CRYSTAL_CHIP)
                .input(PLASTIC_CIRCUIT_BOARD)
                .input(wireFine, Palladium, 8)
                .input(bolt, Platinum, 4)
                .output(CRYSTAL_MODULATOR_RUBY, 8)
                .duration(200).EUt(VA[IV]).buildAndRegister();

        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(ENGRAVED_DIAMOND_CRYSTAL_CHIP)
                .input(PLASTIC_CIRCUIT_BOARD)
                .input(wireFine, Palladium, 8)
                .input(bolt, Platinum, 4)
                .output(CRYSTAL_MODULATOR_DIAMOND, 8)
                .duration(200).EUt(VA[IV]).buildAndRegister();
    }

    private static void crystalSOC() {
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(CRYSTAL_INTERFACE_CHIP)
                .input(CRYSTAL_MODULATOR_SAPPHIRE)
                .input(CRYSTAL_MODULATOR_RUBY)
                .input(CRYSTAL_MODULATOR_DIAMOND)
                .input(wireFine, Europium, 4)
                .output(CRYSTAL_SYSTEM_ON_CHIP_SOCKET)
                .duration(100).EUt(VA[LuV]).buildAndRegister();

        FORMING_PRESS_RECIPES.recipeBuilder()
                .input(CRYSTAL_SYSTEM_ON_CHIP_SOCKET)
                .input(CRYSTAL_CENTRAL_PROCESSING_UNIT)
                .output(CRYSTAL_SYSTEM_ON_CHIP)
                .duration(100).EUt(VA[ZPM]).buildAndRegister();
    }
    private static void AdvancedCrystalSoC() {

        //  1/2 CeO2 + 1/5 Lu2O3 + Al2O3 -> Ce:LAG
        //  this recipe just has some tweak, e.g. half consume of materials (because recipe should be more chip).
        MIXER_RECIPES.recipeBuilder()
                .input(dust, CeriumOxide)
                .input(dust, LutetiumOxide)
                .input(dust, Alumina, 5)
                .circuitMeta(3)
                .output(dust, CeLAG, 7)
                .EUt(VA[ZPM])
                .duration(10 * 20)
                .buildAndRegister();

        //  Advanced Crystal CPU recipes
        //  use Ce:LAG lens, and 1 step to get Crystal CPU (this machine required material over ZPM, so is not too imba).
        PLASMA_CVD_RECIPES.recipeBuilder()
                .input(plate, Emerald)
                .notConsumable(lens, CeLAG)
                .fluidInputs(Europium.getFluid(4))
                .output(CRYSTAL_CENTRAL_PROCESSING_UNIT)
                .EUt(VA[HV])
                .duration(10 * 20)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        PLASMA_CVD_RECIPES.recipeBuilder()
                .input(plate, Olivine)
                .notConsumable(lens, CeLAG)
                .fluidInputs(Europium.getFluid(4))
                .output(CRYSTAL_CENTRAL_PROCESSING_UNIT)
                .EUt(VA[HV])
                .duration(10 * 20)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Advanced Crystal SoC Socket recipe
        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Naquadah)
                .input(CRYSTAL_INTERFACE_CHIP)
                .input(ring, RhodiumPlatedPalladium, 2)
                .input(wireFine, Europium, 4)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .output(CRYSTAL_SYSTEM_ON_CHIP_SOCKET, 2)
                .EUt(VA[UV])
                .CWUt(CWT[UV])
                .duration(200)
                .Tier(3) // UV
                .buildAndRegister();
    }
}
