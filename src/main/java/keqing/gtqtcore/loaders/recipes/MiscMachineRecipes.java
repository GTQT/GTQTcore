package keqing.gtqtcore.loaders.recipes;

import gregtech.api.GTValues;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.unification.material.Materials;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.init.Blocks;

import java.util.Collection;
import java.util.List;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.screw;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class MiscMachineRecipes {


    public static void init() {

        //燃料电池
        Collection<Recipe> oilsRecipes = RecipeMaps.COMBUSTION_GENERATOR_FUELS.getRecipeList();
        for (Recipe recipe : oilsRecipes) {
            List<GTRecipeInput> fluidInputs = recipe.getFluidInputs();
            int EUt = (int) GTValues.V[GTValues.LV];
            int baseDuration = 4 * recipe.getDuration() * recipe.getEUt() / EUt;
            GTQTcoreRecipeMaps.FUEL_CELL.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidInputs(Materials.Air.getFluid(baseDuration * 4))
                    .duration(baseDuration)
                    .EUt(EUt)
                    .buildAndRegister();
            GTQTcoreRecipeMaps.FUEL_CELL.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidInputs(Materials.Oxygen.getFluid(FluidStorageKeys.GAS, baseDuration * 2))
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
            int baseDuration = 4 * recipe.getDuration() * recipe.getEUt() / EUt;
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

    }
}
