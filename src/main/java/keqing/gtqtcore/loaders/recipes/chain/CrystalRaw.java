package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.common.items.MetaItems;

import static gregtech.api.GTValues.*;
import static gregtech.api.metatileentity.multiblock.CleanroomType.STERILE_CLEANROOM;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.recipes.RecipeMaps.IMPLOSION_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Carbon;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.RAW_CRYSTAL_CHIP;
import static gregtech.common.items.MetaItems.RAW_CRYSTAL_CHIP_PART;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.*;

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
    }
}
