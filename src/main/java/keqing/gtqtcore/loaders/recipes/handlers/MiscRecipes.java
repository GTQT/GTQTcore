package keqing.gtqtcore.loaders.recipes.handlers;



import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockTransparentCasing;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.LV;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.NetherStar;
import static gregtech.api.unification.ore.OrePrefix.*;
import static java.util.Calendar.SECOND;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CRYSTALLIZER_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.boule;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.seedCrystal;

/**
 * Use this class to add miscellaneous recipes which have no category otherwise
 */
public class MiscRecipes {

    public static void init() {
        metaBlockRecipes();
        //  Flux Electrum
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Electrum, 8)
                .input(dust, RoseGold, 4)
                .input(dust, SterlingSilver, 4)
                .input(dust, NaquadahEnriched, 2)
                .circuitMeta(4)
                .output(dust, FluxedElectrum, 18)
                .EUt(VA[ZPM])
                .duration(240)
                .buildAndRegister();

        AUTOCLAVE_RECIPES.recipeBuilder()
                .input(dust, Platinum)
                .input(plate, Richmagic)
                .fluidInputs(ConcentrateDragonBreath.getFluid(L * 4))
                .output(seedCrystal, NetherStar)
                .EUt(VA[EV])
                .duration((int) (8.6 * SECOND))
                .buildAndRegister();

        CRYSTALLIZER_RECIPES.recipeBuilder()
                .input(seedCrystal, NetherStar)
                .input(dust, Platinum)
                .input(plate, Richmagic)
                .fluidInputs(ConcentrateDragonBreath.getFluid(1000))
                .output(boule, NetherStar)
                .EUt(VA[IV])
                .duration((int) (6.7 * SECOND))
                .blastFurnaceTemp(3200)
                .buildAndRegister();

        CUTTER_RECIPES.recipeBuilder()
                .input(boule, NetherStar)
                .fluidInputs(Water.getFluid(4))
                .output(gem, NetherStar)
                .output(seedCrystal, NetherStar)
                .EUt(VA[LV])
                .duration((int) (7.2 * SECOND))
                .buildAndRegister();

        CUTTER_RECIPES.recipeBuilder()
                .input(boule, NetherStar)
                .fluidInputs(DistilledWater.getFluid(3))
                .output(gem, NetherStar)
                .output(seedCrystal, NetherStar)
                .EUt(VA[LV])
                .duration((int) (5.4 * SECOND))
                .buildAndRegister();

        CUTTER_RECIPES.recipeBuilder()
                .input(boule, NetherStar)
                .fluidInputs(Lubricant.getFluid(1))
                .output(gem, NetherStar)
                .output(seedCrystal, NetherStar)
                .EUt(VA[LV])
                .duration((int) (3.6 * SECOND))
                .buildAndRegister();
        //TODO add Iodine-131 gas or liquid
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Alumina)
                .input("blockSand", 3)
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(FracturingFluid.getFluid(1000))
                .duration(100).EUt(VA[IV]).buildAndRegister();

        // c-BN sawblade
        LATHE_RECIPES.recipeBuilder()
                .input(gemExquisite, CubicBoronNitride)
                .output(toolHeadBuzzSaw, CubicBoronNitride)
                .duration((int) (CubicBoronNitride.getMass() * 4)).EUt(240).buildAndRegister();
    }

    private static void metaBlockRecipes() {
        COMPRESSOR_RECIPES.recipeBuilder()
                .input(plate, PMMA, 4)
                .outputs(GTQTMetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockTransparentCasing.CasingType.PMMA))
                .duration(400).EUt(2).buildAndRegister();
    }
}
