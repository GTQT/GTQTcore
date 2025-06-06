package keqing.gtqtcore.loaders.recipes;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.init.Blocks;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.CANNER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.MIXER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Adamantium;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Vibranium;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class MiscMachineRecipes {


    public static void init() {
        LATEX_COLLECTOR_RECIPES.recipeBuilder()
                .fluidInputs(Water.getFluid(10))
                .fluidOutputs(Latex.getFluid(100))
                .blockStates("latex_logs", MetaBlocks.RUBBER_LOG.getBlockState())
                .duration(200)
                .EUt(7)
                .buildAndRegister();

        LATEX_COLLECTOR_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(10))
                .fluidOutputs(Resin.getFluid(100))
                .blockStates("extractable_logs_1", Blocks.LOG.getBlockState())
                .duration(200)
                .EUt(7)
                .buildAndRegister();

        LATEX_COLLECTOR_RECIPES.recipeBuilder()
                .fluidInputs(Lubricant.getFluid(10))
                .fluidOutputs(Resin.getFluid(100))
                .blockStates("extractable_logs_2", Blocks.LOG2.getBlockState())
                .duration(200)
                .EUt(7)
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
                .Heat(500)
                .circuitMeta(1)
                .buildAndRegister();


        SALT_FLIED.recipeBuilder()
                .fluidInputs(Water.getFluid(4000))
                .fluidOutputs(SaltWater.getFluid(1000))
                .duration(200)
                .Heat(500)
                .circuitMeta(1)
                .buildAndRegister();

        SALT_FLIED.recipeBuilder()
                .fluidInputs(SaltWater.getFluid(4000))
                .output(dust, Salt, 4)
                .duration(100)
                .Heat(500)
                .circuitMeta(1)
                .buildAndRegister();

        SALT_FLIED.recipeBuilder()
                .fluidInputs(Water.getFluid(4000))
                .output(dust, Salt, 1)
                .duration(100)
                .Heat(500)
                .circuitMeta(2)
                .buildAndRegister();

        // 砷化镓粉 * 2
        GTRecipeHandler.removeRecipesByInputs(MIXER_RECIPES, OreDictUnifier.get(dust, Gallium), OreDictUnifier.get(dust, Arsenic), IntCircuitIngredient.getIntegratedCircuit(1));
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, Gallium)
                .input(dust, Arsenic)
                .circuitMeta(2)
                .output(dust, GalliumArsenide, 2)
                .blastFurnaceTemp(1700)
                .EUt(120)
                .duration(900)
                .buildAndRegister();
    }
}
