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
        ShapeMold();
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

    private static void ShapeMold() {
        // Re-modify all Shape Molds recipe.
        ModHandler.removeRecipeByName("gregtech:shape_mold_plate");
        ModHandler.addShapedRecipe(true, "shape_mold.plate", SHAPE_MOLD_PLATE.getStackForm(),
                " hf", " M ", "   ",
                'M', SHAPE_EMPTY);

        ModHandler.removeRecipeByName("gregtech:shape_mold_gear");
        ModHandler.addShapedRecipe(true,"shape_mold.gear", SHAPE_MOLD_GEAR.getStackForm(),
                " h ", " M ", "  f",
                'M', SHAPE_EMPTY);

        ModHandler.removeRecipeByName("gregtech:shape_mold_credit");
        ModHandler.addShapedRecipe(true, "shape_mold.credit", SHAPE_MOLD_CREDIT.getStackForm(),
                "   ", " M ", "hf ",
                'M', SHAPE_EMPTY);

        ModHandler.removeRecipeByName("gregtech:shape_mold_bottle");
        ModHandler.addShapedRecipe(true, "shape_mold.bottle", SHAPE_MOLD_BOTTLE.getStackForm(),
                " h ", " M ", " f ",
                'M', SHAPE_EMPTY);

        ModHandler.removeRecipeByName("gregtech:shape_mold_ingot");
        ModHandler.addShapedRecipe(true, "shape_mold.ingot", SHAPE_MOLD_INGOT.getStackForm(),
                " h ", " M ", "f  ",
                'M', SHAPE_EMPTY);

        ModHandler.removeRecipeByName("gregtech:shape_mold_ball");
        ModHandler.addShapedRecipe(true, "shape_mold.ball", SHAPE_MOLD_BALL.getStackForm(),
                " h ", "fM ", "   ",
                'M', SHAPE_EMPTY);

        ModHandler.removeRecipeByName("gregtech:shape_mold_block");
        ModHandler.addShapedRecipe(true, "shape_mold.block", SHAPE_MOLD_BLOCK.getStackForm(),
                "fh ", " M ", "   ",
                'M', SHAPE_EMPTY);

        ModHandler.removeRecipeByName("gregtech:shape_mold_nugget");
        ModHandler.addShapedRecipe(true, "shape_mold.nugget", SHAPE_MOLD_NUGGET.getStackForm(),
                "  h", " Mf", "   ",
                'M', SHAPE_EMPTY);

        ModHandler.removeRecipeByName("gregtech:shape_mold_cylinder");
        ModHandler.addShapedRecipe(true, "shape_mold.cylinder", SHAPE_MOLD_CYLINDER.getStackForm(),
                "  h", "fM ", "   ",
                'M', SHAPE_EMPTY);

        ModHandler.removeRecipeByName("gregtech:shape_mold_anvil");
        ModHandler.addShapedRecipe(true, "shape_mold.anvil", SHAPE_MOLD_ANVIL.getStackForm(),
                "f h", " M ", "   ",
                'M', SHAPE_EMPTY);

        ModHandler.removeRecipeByName("gregtech:shape_mold_name");
        ModHandler.addShapedRecipe(true, "shape_mold.name", SHAPE_MOLD_NAME.getStackForm(),
                " fh", " M ", "   ",
                'M', SHAPE_EMPTY);

        ModHandler.removeRecipeByName("gregtech:shape_mold_gear_small");
        ModHandler.addShapedRecipe(true, "shape_mold.gear_small", SHAPE_MOLD_GEAR_SMALL.getStackForm(),
                "   ", " Mh", " f ",
                'M', SHAPE_EMPTY);

        ModHandler.removeRecipeByName("gregtech:shape_mold_rotor");
        ModHandler.addShapedRecipe(true, "shape_mold.rotor", SHAPE_MOLD_ROTOR.getStackForm(),
                "   ", " M ", "f h",
                'M', SHAPE_EMPTY);

        // Add recipes for gtlitecore addition Shape Molds.
        ModHandler.addShapedRecipe(true, "shape_mold.rod", SHAPE_MOLD_ROD.getStackForm(),
                "   ", " Mh", "f  ",
                'M', SHAPE_EMPTY);

        FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_MOLD_ROD)
                .input(SHAPE_EMPTY)
                .output(SHAPE_MOLD_ROD)
                .EUt(22) // LV
                .duration(6 * SECOND)
                .buildAndRegister();


        ModHandler.addShapedRecipe(true, "shape_mold.bolt", SHAPE_MOLD_BOLT.getStackForm(),
                "   ", "fMh", "   ",
                'M', SHAPE_EMPTY);

        FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_MOLD_BOLT)
                .input(SHAPE_EMPTY)
                .output(SHAPE_MOLD_BOLT)
                .EUt(22) // LV
                .duration(6 * SECOND)
                .buildAndRegister();

        ModHandler.addShapedRecipe(true, "shape_mold.round", SHAPE_MOLD_ROUND.getStackForm(),
                "f  ", " Mh", "   ",
                'M', SHAPE_EMPTY);

        FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_MOLD_ROUND)
                .input(SHAPE_EMPTY)
                .output(SHAPE_MOLD_ROUND)
                .EUt(22) // LV
                .duration(6 * SECOND)
                .buildAndRegister();

        ModHandler.addShapedRecipe(true, "shape_mold.screw", SHAPE_MOLD_SCREW.getStackForm(),
                " f ", " Mh", "   ",
                'M', SHAPE_EMPTY);

        FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_MOLD_SCREW)
                .input(SHAPE_EMPTY)
                .output(SHAPE_MOLD_SCREW)
                .EUt(22) // LV
                .duration(6 * SECOND)
                .buildAndRegister();

        ModHandler.addShapedRecipe(true, "shape_mold.ring", SHAPE_MOLD_RING.getStackForm(),
                "  f", " Mh", "   ",
                'M', SHAPE_EMPTY);

        FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_MOLD_RING)
                .input(SHAPE_EMPTY)
                .output(SHAPE_MOLD_RING)
                .EUt(22) // LV
                .duration(6 * SECOND)
                .buildAndRegister();

        ModHandler.addShapedRecipe(true, "shape_mold.rod_long", SHAPE_MOLD_ROD_LONG.getStackForm(),
                "   ", " M ", " fh",
                'M', SHAPE_EMPTY);

        FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_MOLD_ROD_LONG)
                .input(SHAPE_EMPTY)
                .output(SHAPE_MOLD_ROD_LONG)
                .EUt(22) // LV
                .duration(6 * SECOND)
                .buildAndRegister();

        ModHandler.addShapedRecipe(true, "shape_mold.turbine_blade", SHAPE_MOLD_TURBINE_BLADE.getStackForm(),
                "   ", "fM ", "  h",
                'M', SHAPE_EMPTY);

        FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_MOLD_TURBINE_BLADE)
                .input(SHAPE_EMPTY)
                .output(SHAPE_MOLD_TURBINE_BLADE)
                .EUt(22) // LV
                .duration(6 * SECOND)
                .buildAndRegister();

        ModHandler.addShapedRecipe(true, "shape_mold.drill_head", SHAPE_MOLD_DRILL_HEAD.getStackForm(),
                "   ", " M ", " hf",
                'M', SHAPE_EMPTY);

        FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_MOLD_DRILL_HEAD)
                .input(SHAPE_EMPTY)
                .output(SHAPE_MOLD_DRILL_HEAD)
                .EUt(22) // LV
                .duration(6 * SECOND)
                .buildAndRegister();

        ModHandler.addShapedRecipe(true, "shape_extruder.turbine_blade", SHAPE_EXTRUDER_TURBINE_BLADE.getStackForm(),
                "   ", " M ", "  x",
                'M', SHAPE_EXTRUDER_PLATE);

        ModHandler.addShapedRecipe(true, "shape_extruder.drill_head", SHAPE_EXTRUDER_DRILL_HEAD.getStackForm(),
                "   ", " M ", " x ",
                'M', SHAPE_EXTRUDER_INGOT);
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
                .outputs(GTQTMetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockTransparentCasing.CasingType.PMMA))
                .duration(400).EUt(2).buildAndRegister();
    }
}
