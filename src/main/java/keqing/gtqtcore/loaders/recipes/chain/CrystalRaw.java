package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.unification.material.MarkerMaterials;
import gregtech.common.items.MetaItems;

import static gregtech.api.GTValues.*;
import static gregtech.api.metatileentity.multiblock.CleanroomType.STERILE_CLEANROOM;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.recipes.RecipeMaps.IMPLOSION_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Carbon;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.*;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;

public class CrystalRaw {
    public static void init() {
        BLAST_RECIPES.recipeBuilder()
                .input(gemExquisite,HexagonalBoronNitride)
                .input(dust,GraphenePQD)
                .fluidInputs(Europium.getFluid(72))
                .fluidInputs(BacterialSludge.getFluid(1000))
                .fluidInputs(Mutagen.getFluid(1000))
                .chancedOutput(RAW_CRYSTAL_CHIP,1000,0)
                .blastFurnaceTemp(6000)
                .EUt(VA[LuV])
                .duration(3200)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(gemExquisite,CubicBoronNitride)
                .input(dust,GraphenePQD)
                .fluidInputs(Europium.getFluid(72))
                .fluidInputs(BacterialSludge.getFluid(1000))
                .fluidInputs(Mutagen.getFluid(1000))
                .chancedOutput(RAW_CRYSTAL_CHIP,5000,0)
                .blastFurnaceTemp(6000)
                .EUt(VA[LuV])
                .duration(3200)
                .buildAndRegister();


        CUTTER_RECIPES.recipeBuilder()
                .input(RAW_CRYSTAL_CHIP)
                .output(RAW_CRYSTAL_CHIP_PART, 4)
                .EUt(VA[LuV])
                .duration(800)
                .cleanroom(STERILE_CLEANROOM)
                .buildAndRegister();


        GENE_MUTAGENESIS.recipeBuilder()
                .input(gemExquisite,CubicBoronNitride)
                .input(dust,GraphenePQD)
                .fluidInputs(Europium.getFluid(36))
                .fluidInputs(BacterialSludge.getFluid(1000))
                .input(RAW_CRYSTAL_CHIP_PART,2)
                .rate(50)
                .chancedOutput(RAW_CRYSTAL_CHIP,6000,0)
                .chancedOutput(RAW_CRYSTAL_CHIP,6000,0)
                .chancedOutput(RAW_CRYSTAL_CHIP,6000,0)
                .chancedOutput(RAW_CRYSTAL_CHIP,6000,0)
                .chancedOutput(RAW_CRYSTAL_CHIP,6000,0)
                .chancedOutput(RAW_CRYSTAL_CHIP,6000,0)
                .EUt(VA[LuV])
                .duration(800)
                .cleanroom(STERILE_CLEANROOM)
                .buildAndRegister();

        GENE_MUTAGENESIS.recipeBuilder()
                .input(gemExquisite,CubicBoronNitride)
                .input(dust,GraphenePQD)
                .fluidInputs(Europium.getFluid(36))
                .fluidInputs(Mutagen.getFluid(1000))
                .input(RAW_CRYSTAL_CHIP_PART,2)
                .rate(50)
                .chancedOutput(RAW_CRYSTAL_CHIP,6000,0)
                .chancedOutput(RAW_CRYSTAL_CHIP,6000,0)
                .chancedOutput(RAW_CRYSTAL_CHIP,6000,0)
                .chancedOutput(RAW_CRYSTAL_CHIP,6000,0)
                .chancedOutput(RAW_CRYSTAL_CHIP,6000,0)
                .chancedOutput(RAW_CRYSTAL_CHIP,6000,0)
                .EUt(VA[LuV])
                .duration(800)
                .cleanroom(STERILE_CLEANROOM)
                .buildAndRegister();

        GENE_MUTAGENESIS.recipeBuilder()
                .input(gemExquisite,HexagonalBoronNitride)
                .input(dust,GraphenePQD)
                .fluidInputs(Europium.getFluid(36))
                .fluidInputs(BacterialSludge.getFluid(1000))
                .input(RAW_CRYSTAL_CHIP_PART,2)
                .rate(50)
                .chancedOutput(RAW_CRYSTAL_CHIP,4000,0)
                .chancedOutput(RAW_CRYSTAL_CHIP,4000,0)
                .chancedOutput(RAW_CRYSTAL_CHIP,4000,0)
                .chancedOutput(RAW_CRYSTAL_CHIP,4000,0)
                .EUt(VA[LuV])
                .duration(800)
                .cleanroom(STERILE_CLEANROOM)
                .buildAndRegister();

        GENE_MUTAGENESIS.recipeBuilder()
                .input(gemExquisite,HexagonalBoronNitride)
                .input(dust,GraphenePQD)
                .fluidInputs(Europium.getFluid(36))
                .fluidInputs(Mutagen.getFluid(1000))
                .input(RAW_CRYSTAL_CHIP_PART,2)
                .rate(50)
                .chancedOutput(RAW_CRYSTAL_CHIP,4000,0)
                .chancedOutput(RAW_CRYSTAL_CHIP,4000,0)
                .chancedOutput(RAW_CRYSTAL_CHIP,4000,0)
                .chancedOutput(RAW_CRYSTAL_CHIP,4000,0)
                .EUt(VA[LuV])
                .duration(800)
                .cleanroom(STERILE_CLEANROOM)
                .buildAndRegister();

        ////////////////////////////////////////////////////////////////////
        BLAST_RECIPES.recipeBuilder()
                .input(plate, Prasiolite)
                .input(dust, Selenium)
                .input(RAW_CRYSTAL_CHIP)
                .fluidInputs(Krypton.getFluid(1000))
                .output(ENGRAVED_CRYSTAL_CHIP)
                .blastFurnaceTemp(6000)
                .duration(1200).EUt(VA[LuV]).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(plate, Prasiolite)
                .input(dust, Selenium)
                .input(RAW_CRYSTAL_CHIP)
                .fluidInputs(Krypton.getFluid(1000))
                .output(ENGRAVED_CRYSTAL_CHIP)
                .blastFurnaceTemp(6000)
                .duration(1200).EUt(VA[LuV]).buildAndRegister();

        // Crystal Circuit Components
        STEPPER_RECIPES.recipeBuilder()
                .input(ENGRAVED_CRYSTAL_CHIP)
                .notConsumable(lens, MagnetoResonatic)
                .fluidInputs(Naquadria.getFluid(16))
                .output(CRYSTAL_CENTRAL_PROCESSING_UNIT)
                .cleanroom(STERILE_CLEANROOM)
                .Laser(5)
                .CWUt(CWT[LuV])
                .duration(100).EUt(10000).buildAndRegister();


    }
}
