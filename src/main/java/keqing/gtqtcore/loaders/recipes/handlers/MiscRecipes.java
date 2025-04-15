package keqing.gtqtcore.loaders.recipes.handlers;



import gregtech.api.recipes.ModHandler;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockTransparentCasing;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.LV;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.NetherStar;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static java.util.Calendar.SECOND;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CRYSTALLIZER_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.boule;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.seedCrystal;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

/**
 * Use this class to add miscellaneous recipes which have no category otherwise
 */
public class MiscRecipes {

    public static void init() {
        metaBlockRecipes();
        ExoticExtruders();
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
    private static void ExoticExtruders() {

        //  Plate
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_PLATE)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_PLATE)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //  Rod
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_ROD)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_ROD)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //  Bolt
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_BOLT)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_BOLT)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //  Ring
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_RING)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_RING)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //  Cell
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_CELL)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_CELL)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //  Ingot
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_INGOT)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_INGOT)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //  Wire
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_WIRE)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_WIRE)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //  Pipe Tiny
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_PIPE_TINY)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_PIPE_TINY)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //  Pipe Small
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_PIPE_SMALL)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_PIPE_SMALL)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //  Pipe Normal
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_PIPE_NORMAL)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_PIPE_NORMAL)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //  Pipe Large
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_PIPE_LARGE)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_PIPE_LARGE)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //  Pipe Huge
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_PIPE_HUGE)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_PIPE_HUGE)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //  Block
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_BLOCK)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_BLOCK)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //  Gear
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_GEAR)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_GEAR)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //  Bottle
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_BOTTLE)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_BOTTLE)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //  Foil
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_FOIL)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_FOIL)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //  Small Gear
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_GEAR_SMALL)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_GEAR_SMALL)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //  Long Rod
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_ROD_LONG)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_ROD_LONG)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //  Rotor
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(SHAPE_EXTRUDER_ROTOR)
                .fluidInputs(Infinity.getFluid(L * 4))
                .output(EXOTIC_SHAPE_EXTRUDER_ROTOR)
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();
    }

    private static void metaBlockRecipes() {
        COMPRESSOR_RECIPES.recipeBuilder()
                .input(plate, PMMA, 4)
                .outputs(GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.PMMA))
                .duration(400).EUt(2).buildAndRegister();
    }
}
