package keqing.gtqtcore.loaders.recipes;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.items.toolitem.IGTTool;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.properties.*;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.crafting.ToolHeadReplaceRecipe;
import gregtech.common.items.ToolItems;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.items.metaitems.GTQTMetaToolItems;
import keqing.gtqtcore.loaders.recipes.handlers.BouleRecipeHandler;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.FLUID_SOLIDFICATION_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_PLATE;
import static gregtech.api.unification.material.properties.PropertyKey.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.loaders.recipe.handlers.ToolRecipeHandler.addToolRecipe;
import static gregtech.loaders.recipe.handlers.ToolRecipeHandler.powerUnitItems;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.material.info.GTQTMaterialFlags.GENERATE_CURVED_PLATE;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.*;
import static keqing.gtqtcore.common.items.metaitems.GTQTMetaToolItems.*;

public class GTQTRecipes {

    public static void register() {
    }

    public static void registerTool() {
        OrePrefix.gem.addProcessingHandler(PropertyKey.GEM, BouleRecipeHandler::processCrystallizer);
        OrePrefix.plate.addProcessingHandler(PropertyKey.TOOL, GTQTRecipes::processTool);
        milled.addProcessingHandler(PropertyKey.ORE, GTQTRecipes::processMilled);
        plate_curved.addProcessingHandler(PropertyKey.INGOT, GTQTRecipes::processPlateCurved);
        plate_big.addProcessingHandler(PropertyKey.INGOT, GTQTRecipes::processPlateBig);
        round_cover.addProcessingHandler(PropertyKey.INGOT, GTQTRecipes::processRoundCover);
        shell.addProcessingHandler(PropertyKey.INGOT, GTQTRecipes::processShell);
        soldering_iron_head.addProcessingHandler(PropertyKey.INGOT, GTQTRecipes::processSolderingIron);
        OrePrefix.ring.addProcessingHandler(PropertyKey.DUST, GTQTRecipes::processRing);
        OrePrefix.stick.addProcessingHandler(PropertyKey.DUST, GTQTRecipes::processStick);
        OrePrefix.plate.addProcessingHandler(PropertyKey.DUST, GTQTRecipes::processPlate);
        OrePrefix.plate.addProcessingHandler(TOOL, GTQTRecipes::processTool);
        OrePrefix.spring.addProcessingHandler(PropertyKey.INGOT, GTQTRecipes::processSpring);
        OrePrefix.springSmall.addProcessingHandler(PropertyKey.INGOT, GTQTRecipes::processSpringSmall);
        OrePrefix.foil.addProcessingHandler(PropertyKey.INGOT, GTQTRecipes::processFoil);
        OrePrefix.rotor.addProcessingHandler(PropertyKey.INGOT, GTQTRecipes::processRotorA);
        wireGtSingle.addProcessingHandler(PropertyKey.WIRE, GTQTRecipes::processWireSingle);
        registerMortarRecipes();
    }

    private static void registerMortarRecipes() {
        for (Material material : new Material[]{
                Inconel625, HastelloyN, Hdcs, BlackTitanium, MaragingSteel250, BabbittAlloy, EglinSteel, Pikyonium64B, HG1223,
                Draconium, AwakenedDraconium, Infinity, Rhugnor, Hypogen}) {

            if (!(material.hasProperty(GEM) && material.hasProperty(INGOT))) continue;
            addToolRecipe(material, ToolItems.MORTAR, false,
                    " I ", "SIS", "SSS",
                    'I', new UnificationEntry(material.hasProperty(GEM) ? OrePrefix.gem : OrePrefix.ingot, material),
                    'S', OrePrefix.stone);
        }
    }

    public static void processWireSingle(OrePrefix wirePrefix, Material material, WireProperties property) {

        if (material.hasFluid()) {
            PRECISION_SPINNING.recipeBuilder()
                    .fluidInputs(material.getFluid(144 * 8))
                    .circuitMeta(1)
                    .output(wireGtSingle, material, 16)
                    .duration((int) material.getMass())
                    .EUt(120)
                    .CWUt(96)
                    .buildAndRegister();

            PRECISION_SPINNING.recipeBuilder()
                    .fluidInputs(material.getFluid(144 * 16))
                    .circuitMeta(2)
                    .output(wireGtDouble, material, 16)
                    .duration((int) material.getMass() * 2)
                    .EUt(120)
                    .CWUt(96)
                    .buildAndRegister();

            PRECISION_SPINNING.recipeBuilder()
                    .fluidInputs(material.getFluid(144 * 32))
                    .circuitMeta(4)
                    .output(wireGtQuadruple, material, 16)
                    .duration((int) material.getMass() * 4)
                    .EUt(120)
                    .CWUt(96)
                    .buildAndRegister();

            PRECISION_SPINNING.recipeBuilder()
                    .fluidInputs(material.getFluid(144 * 64))
                    .circuitMeta(8)
                    .output(wireGtOctal, material, 16)
                    .duration((int) material.getMass() * 8)
                    .EUt(120)
                    .CWUt(96)
                    .buildAndRegister();

            PRECISION_SPINNING.recipeBuilder()
                    .fluidInputs(material.getFluid(144 * 128))
                    .circuitMeta(16)
                    .output(wireGtHex, material, 16)
                    .duration((int) material.getMass() * 16)
                    .EUt(120)
                    .CWUt(96)
                    .cleanroom(CleanroomType.CLEANROOM)
                    .buildAndRegister();
        }
    }

    private static void processShell(OrePrefix orePrefix, Material material, IngotProperty ingotProperty) {
        CW_LASER_ENGRAVER_RECIPES.recipeBuilder()
                .input(plate_big, material, 1)
                .fluidInputs(Water.getFluid(1000))
                .outputs(OreDictUnifier.get(orePrefix, material, 4))
                .output(dust, material, 1)
                .duration(100)
                .circuitMeta(1)
                .EUt(GTValues.VA[HV])
                .buildAndRegister();
    }

    private static void processRoundCover(OrePrefix orePrefix, Material material, IngotProperty ingotProperty) {
        CW_LASER_ENGRAVER_RECIPES.recipeBuilder()
                .input(plate_big, material, 1)
                .fluidInputs(Water.getFluid(1000))
                .outputs(OreDictUnifier.get(orePrefix, material, 1))
                .output(dust, material, 1)
                .duration(100)
                .circuitMeta(2)
                .EUt(GTValues.VA[HV])
                .buildAndRegister();
    }

    private static void processPlateBig(OrePrefix orePrefix, Material material, IngotProperty ingotProperty) {
        if (material.hasFlag(GENERATE_PLATE)) {
            if (material.hasFluid()) {
                FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                        .notConsumable(GTQTMetaItems.MOLD_GAS)
                        .fluidInputs(material.getFluid(1224))
                        .output(cylinder, material)
                        .duration(1200)
                        .EUt(GTValues.VA[HV])
                        .buildAndRegister();

                FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                        .notConsumable(GTQTMetaItems.MOLD_MOTOR)
                        .fluidInputs(material.getFluid(1620))
                        .output(motor_stick, material)
                        .duration(1200)
                        .EUt(GTValues.VA[HV])
                        .buildAndRegister();

                FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                        .notConsumable(GTQTMetaItems.MOLD_VALUE)
                        .fluidInputs(material.getFluid(1692))
                        .output(valve, material)
                        .duration(1200)
                        .EUt(GTValues.VA[EV])
                        .buildAndRegister();
            }

            if (!material.hasFlag(MaterialFlags.NO_SMASHING)) {
                ModHandler.addShapedRecipe(String.format("aplate_big_%s", material),
                        OreDictUnifier.get(plate_big, material),
                        "SSh", "SSB",
                        'S', new UnificationEntry(plate, material),
                        'B',"toolSolderingIron");

                ModHandler.addShapedRecipe(String.format("bplate_big_%s", Steel),
                        OreDictUnifier.get(plate_big, Steel),
                        "SSh", "SSB",
                        'S', new UnificationEntry(plate, Steel),
                        'B', Items.FLINT);

                ModHandler.addShapedRecipe(String.format("bplate_big_%s", Bronze),
                        OreDictUnifier.get(plate_big, Bronze),
                        "SSh", "SSB",
                        'S', new UnificationEntry(plate, Bronze),
                        'B', Items.FLINT);

                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                        .EUt(30).duration(1200)
                        .input(plate, material, 4)
                        .circuitMeta(10)
                        .output(plate_big, material)
                        .fluidInputs(Tin.getFluid(36))
                        .buildAndRegister();

                RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                        .EUt(30).duration(400)
                        .input(plate, material)
                        .output(shell, material)
                        .fluidInputs(Water.getFluid(1000))
                        .buildAndRegister();

                RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                        .EUt(32).duration(200)
                        .input(plate_big, material)
                        .output(round_cover, material)
                        .fluidInputs(Water.getFluid(1000))
                        .buildAndRegister();

                RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                        .EUt(30).duration(200)
                        .input(plate, material)
                        .output(shell, material)
                        .fluidInputs(Lubricant.getFluid(200))
                        .buildAndRegister();

                RecipeMaps.CUTTER_RECIPES.recipeBuilder()
                        .EUt(32).duration(100)
                        .input(plate_big, material)
                        .output(round_cover, material)
                        .fluidInputs(Lubricant.getFluid(200))
                        .buildAndRegister();

                FLUID_EXTRACTOR_RECIPES.recipeBuilder()
                        .EUt(32).duration(40)
                        .fluidOutputs(material.getFluid(576))
                        .input(plate_big, material)
                        .buildAndRegister();

                FLUID_EXTRACTOR_RECIPES.recipeBuilder()
                        .EUt(32).duration(40)
                        .fluidOutputs(material.getFluid(72))
                        .input(shell, material)
                        .buildAndRegister();

                FLUID_EXTRACTOR_RECIPES.recipeBuilder()
                        .EUt(32).duration(40)
                        .fluidOutputs(material.getFluid(144))
                        .input(plate_curved, material)
                        .buildAndRegister();

                ModHandler.addShapedRecipe(String.format("acylinder_%s", material),
                        OreDictUnifier.get(cylinder, material),
                        "hCT", "SAS", "LCB",
                        'T', new UnificationEntry(gearSmall, material),
                        'L', new UnificationEntry(stick, material),
                        'S', new UnificationEntry(plate_curved, material),
                        'A', new UnificationEntry(spring, material),
                        'C', new UnificationEntry(round_cover, material),
                        'B',"toolSolderingIron");

                ModHandler.addShapedRecipe(String.format("bcylinder_%s", Steel),
                        OreDictUnifier.get(cylinder, Steel),
                        "hCT", "SAS", "LCB",
                        'T', new UnificationEntry(gearSmall, Steel),
                        'L', new UnificationEntry(stick, Steel),
                        'S', new UnificationEntry(plate_curved, Steel),
                        'A', new UnificationEntry(spring, Steel),
                        'C', new UnificationEntry(round_cover, Steel),
                        'B', Items.FLINT);

                ModHandler.addShapedRecipe(String.format("bcylinder_%s", Bronze),
                        OreDictUnifier.get(cylinder, Bronze),
                        "hCT", "SAS", "LCB",
                        'T', new UnificationEntry(gearSmall, Bronze),
                        'L', new UnificationEntry(stick, Bronze),
                        'S', new UnificationEntry(plate_curved, Bronze),
                        'A', new UnificationEntry(spring, Bronze),
                        'C', new UnificationEntry(round_cover, Bronze),
                        'B', Items.FLINT);

                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                        .EUt(32).duration(40)
                        .circuitMeta(20)
                        .input(gearSmall, material, 1)
                        .input(stick, material, 1)
                        .input(plate_curved, material, 2)
                        .input(spring, material, 1)
                        .input(round_cover, material, 1)
                        .output(cylinder, material)
                        .buildAndRegister();

                FLUID_EXTRACTOR_RECIPES.recipeBuilder()
                        .EUt(32).duration(40)
                        .fluidOutputs(material.getFluid(1224))
                        .input(cylinder, material)
                        .buildAndRegister();

                ModHandler.addShapedRecipe(String.format("amotor_stick_%s", material),
                        OreDictUnifier.get(motor_stick, material),
                        "ACh", "SSS", "AfB",
                        'S', new UnificationEntry(stickLong, material),
                        'A', new UnificationEntry(gear, material),
                        'C', new UnificationEntry(springSmall, material),
                        'B',"toolSolderingIron");

                ModHandler.addShapedRecipe(String.format("bmotor_stick_%s", Steel),
                        OreDictUnifier.get(motor_stick, Steel),
                        "ACh", "SSS", "AfB",
                        'S', new UnificationEntry(stickLong, Steel),
                        'A', new UnificationEntry(gear, Steel),
                        'C', new UnificationEntry(springSmall, Steel),
                        'B', Items.FLINT);

                ModHandler.addShapedRecipe(String.format("bmotor_stick_%s", Bronze),
                        OreDictUnifier.get(motor_stick, Bronze),
                        "ACh", "SSS", "AfB",
                        'S', new UnificationEntry(stickLong, Bronze),
                        'A', new UnificationEntry(gear, Bronze),
                        'C', new UnificationEntry(springSmall, Bronze),
                        'B', Items.FLINT);

                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                        .EUt(32).duration(40)
                        .circuitMeta(20)
                        .input(stickLong, material, 3)
                        .input(gear, material, 2)
                        .input(springSmall, material, 1)
                        .output(motor_stick, material)
                        .buildAndRegister();

                FLUID_EXTRACTOR_RECIPES.recipeBuilder()
                        .EUt(32).duration(40)
                        .fluidOutputs(material.getFluid(1620))
                        .input(motor_stick, material)
                        .buildAndRegister();

                ModHandler.addShapedRecipe(String.format("avalve_%s", material),
                        OreDictUnifier.get(valve, material),
                        "SAL", "fCh", "STB",
                        'S', new UnificationEntry(shell, material),
                        'T', new UnificationEntry(cylinder, material),
                        'A', new UnificationEntry(gearSmall, material),
                        'L', new UnificationEntry(stick, material),
                        'C', new UnificationEntry(ring, material),
                        'B',"toolSolderingIron");

                ModHandler.addShapedRecipe(String.format("bvalve_%s", Steel),
                        OreDictUnifier.get(valve, material),
                        "SAL", "fCh", "STB",
                        'S', new UnificationEntry(shell, Steel),
                        'T', new UnificationEntry(cylinder, Steel),
                        'A', new UnificationEntry(gearSmall, Steel),
                        'L', new UnificationEntry(stick, Steel),
                        'C', new UnificationEntry(ring, Steel),
                        'B', Items.FLINT);

                ModHandler.addShapedRecipe(String.format("bvalve_%s", Bronze),
                        OreDictUnifier.get(valve, Bronze),
                        "SAL", "fCh", "STB",
                        'S', new UnificationEntry(shell, Bronze),
                        'T', new UnificationEntry(cylinder, Bronze),
                        'A', new UnificationEntry(gearSmall, Bronze),
                        'L', new UnificationEntry(stick, Bronze),
                        'C', new UnificationEntry(ring, Bronze),
                        'B', Items.FLINT);

                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                        .EUt(32).duration(40)
                        .circuitMeta(20)
                        .input(shell, material, 2)
                        .input(cylinder, material, 1)
                        .input(gearSmall, material, 1)
                        .input(stick, material, 1)
                        .input(ring, material, 1)
                        .output(valve, material)
                        .buildAndRegister();

                FLUID_EXTRACTOR_RECIPES.recipeBuilder()
                        .EUt(32).duration(40)
                        .fluidOutputs(material.getFluid(1692))
                        .input(valve, material)
                        .buildAndRegister();

                ModHandler.addShapedRecipe(String.format("round_cover_%s", material),
                        OreDictUnifier.get(round_cover, material),
                        "cSh",
                        'S', new UnificationEntry(plate_big, material));

                ModHandler.addShapedRecipe(String.format("shell_%s", material),
                        OreDictUnifier.get(shell, material),
                        "cSh",
                        'S', new UnificationEntry(plate, material));
            }
        }
    }

    public static void processSolderingIron(OrePrefix SolderingPrefix, Material material, IngotProperty property) {
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(32).duration(40)
                .input(OrePrefix.plate, material, 2)
                .input(OrePrefix.stick, material, 2)
                .output(SolderingPrefix, material)
                .circuitMeta(21)
                .buildAndRegister();

        ToolHeadReplaceRecipe.setToolHeadForTool(soldering_iron_head, SOLDERING_IRON_LV);
        ToolHeadReplaceRecipe.setToolHeadForTool(soldering_iron_head, SOLDERING_IRON_HV);
        ToolHeadReplaceRecipe.setToolHeadForTool(soldering_iron_head, SOLDERING_IRON_IV);
    }

    public static void processPlateCurved(OrePrefix curvedPrefix, Material material, IngotProperty property) {
        if (material.hasFlag(GENERATE_PLATE)) {
            if (!material.hasFlag(MaterialFlags.NO_SMASHING)) {
                ModHandler.addShapedRecipe(String.format("plate_curved_%s", material),
                        OreDictUnifier.get(GTQTOrePrefix.plate_curved, material),
                        "h", "P", "B",
                        'P', new UnificationEntry(OrePrefix.plate, material),
                        'B', BENDING_CYLINDER);
                RecipeMaps.BENDER_RECIPES.recipeBuilder().EUt(32).duration(40)
                        .input(OrePrefix.plate, material, 1)
                        .output(curvedPrefix, material)
                        .circuitMeta(5)
                        .buildAndRegister();
                RecipeMaps.BENDER_RECIPES.recipeBuilder().EUt(32).duration(40)
                        .input(curvedPrefix, material)
                        .output(OrePrefix.plate, material, 1)
                        .circuitMeta(5)
                        .buildAndRegister();
            }
        }
    }

    public static void processSpring(OrePrefix springPrefix, Material material, IngotProperty property) {
        if (material.hasFlag(MaterialFlags.GENERATE_LONG_ROD)) {
            if (!material.hasFlag(MaterialFlags.NO_SMASHING)) {
                ModHandler.addShapedRecipe(String.format("bending_spring_%s", material),
                        OreDictUnifier.get(OrePrefix.spring, material),
                        "hSB",
                        'S', new UnificationEntry(OrePrefix.stickLong, material),
                        'B', BENDING_CYLINDER);
            }
        }
    }

    public static void processSpringSmall(OrePrefix springSmallPrefix, Material material, IngotProperty property) {
        if (material.hasFlag(MaterialFlags.GENERATE_ROD)) {
            if (!material.hasFlag(MaterialFlags.NO_SMASHING)) {
                ModHandler.addShapedRecipe(String.format("bending_small_spring_%s", material),
                        OreDictUnifier.get(OrePrefix.springSmall, material),
                        "hSB",
                        'S', new UnificationEntry(OrePrefix.stick, material),
                        'B', SMALL_BENDING_CYLINDER);
            }
        }
    }

    public static void processFoil(OrePrefix foilPrefix, Material material, IngotProperty property) {
        if (material.hasFlag(GENERATE_PLATE)) {
            if (!material.hasFlag(MaterialFlags.NO_SMASHING)) {
                ModHandler.addShapedRecipe(String.format("bending_foil_%s", material),
                        OreDictUnifier.get(OrePrefix.foil, material, 2),
                        "hPB",
                        'P', new UnificationEntry(OrePrefix.plate, material),
                        'B', SMALL_BENDING_CYLINDER);
            }
        }
    }

    public static void processRotorA(OrePrefix rotorPrefix, Material material, IngotProperty property) {
        if (material.hasFlag(GENERATE_PLATE)) {
            if (!material.hasFlag(MaterialFlags.NO_SMASHING)) {
                ModHandler.addShapedRecipe(String.format("curved_plate_rotor_%s", material),
                        OreDictUnifier.get(OrePrefix.rotor, material),
                        "PhP", "SRf", "PdP",
                        'P', new UnificationEntry(GTQTOrePrefix.plate_curved, material),
                        'S', new UnificationEntry(OrePrefix.screw, material),
                        'R', new UnificationEntry(OrePrefix.ring, material));
            }
        }
    }

    private static void processTool(OrePrefix prefix, Material material, ToolProperty property) {
        addSolderinIronDrillRecipe(soldering_iron_head, material,
                new IGTTool[]{GTQTMetaToolItems.SOLDERING_IRON_LV, GTQTMetaToolItems.SOLDERING_IRON_HV, GTQTMetaToolItems.SOLDERING_IRON_IV});
        UnificationEntry rod = new UnificationEntry(OrePrefix.stick, material);
        UnificationEntry plate = new UnificationEntry(OrePrefix.plate, material);
        UnificationEntry ingot = new UnificationEntry(material.hasProperty(GEM) ? OrePrefix.gem : OrePrefix.ingot, material);

        addToolRecipe(material, BENDING_CYLINDER, true,
                "shf", "III", "III",
                'I', ingot);
        addToolRecipe(material, SMALL_BENDING_CYLINDER, true,
                "shf", "III",
                'I', ingot);

        if (material.hasFlag(GENERATE_PLATE) && !material.hasFlag(MaterialFlags.NO_SMASHING)) {
            if (material.hasFlag(MaterialFlags.GENERATE_ROD)) {
                addToolRecipe(material, UNIVERSAL_SPADE, true,
                        "hPP", "DRP", "RDf",
                        'P', plate,
                        'D', new UnificationEntry(OrePrefix.dye, MarkerMaterials.Color.Blue),
                        'R', rod);
            }
        }
    }


    private static void processRing(OrePrefix orePrefix, Material material, DustProperty dustProperty) {
        if (material.hasFlag(GENERATE_CURVED_PLATE)) {
            CW_LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .input(plate_big, material, 1)
                    .fluidInputs(Water.getFluid(1000))
                    .outputs(OreDictUnifier.get(orePrefix, material, 16))
                    .duration(100)
                    .circuitMeta(3)
                    .EUt(GTValues.VA[HV])
                    .buildAndRegister();
        }
        if (material.hasFlag(MaterialFlags.GENERATE_ROD)) {
            if (!material.hasFlag(MaterialFlags.NO_SMASHING)) {
                ModHandler.addShapedRecipe(String.format("bending_ring_%s", material),
                        OreDictUnifier.get(OrePrefix.ring, material),
                        "h", "S", "B",
                        'S', new UnificationEntry(OrePrefix.stick, material),
                        'B', GTQTMetaToolItems.SMALL_BENDING_CYLINDER);
            }
        }
    }

    private static void processStick(OrePrefix orePrefix, Material material, DustProperty dustProperty) {
        if (material.hasFlag(GENERATE_CURVED_PLATE)) {
            CW_LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .input(plate_big, material, 1)
                    .fluidInputs(Water.getFluid(1000))
                    .outputs(OreDictUnifier.get(orePrefix, material, 8))
                    .duration(100)
                    .circuitMeta(4)
                    .EUt(GTValues.VA[HV])
                    .buildAndRegister();
        }
    }

    public static void processPlate(OrePrefix platePrefix, Material material, DustProperty property) {
        if (material.hasFlag(GENERATE_CURVED_PLATE)) {
            CW_LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .input(plate_big, material, 1)
                    .fluidInputs(Water.getFluid(1000))
                    .outputs(OreDictUnifier.get(platePrefix, material, 4))
                    .duration(100)
                    .circuitMeta(5)
                    .EUt(GTValues.VA[HV])
                    .buildAndRegister();
        }
    }

    public static void processMilled(OrePrefix milledPrefix, Material material, OreProperty property) {
        GTQTcoreRecipeMaps.ISA_MILL_GRINDER.recipeBuilder()
                .EUt(GTValues.VA[EV])
                .duration(1200)
                .input(OrePrefix.crushed, material, 16)
                .output(milledPrefix, material, 16)
                .circuitMeta(11)
                .grindBallTier(1)
                .buildAndRegister();

        GTQTcoreRecipeMaps.ISA_MILL_GRINDER.recipeBuilder()
                .EUt(GTValues.VA[EV])
                .duration(900)
                .input(OrePrefix.crushed, material, 16)
                .output(milledPrefix, material, 32)
                .circuitMeta(10)
                .grindBallTier(2)
                .buildAndRegister();
    }


    public static void addSolderinIronDrillRecipe(OrePrefix toolHead, Material material, IGTTool[] toolItems) {
        for (IGTTool toolItem : toolItems) {
            int tier = toolItem.getElectricTier();
            ItemStack powerUnitStack = powerUnitItems.get(tier).getStackForm();
            IElectricItem powerUnit = powerUnitStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
            ItemStack tool = toolItem.get(material, 0, powerUnit.getMaxCharge());
            ModHandler.addShapedEnergyTransferRecipe(String.format("%s_%s", toolItem.getToolId(), material),
                    tool,
                    Ingredient.fromStacks(powerUnitStack), true, true,
                    "wHd", " U ",
                    'H', new UnificationEntry(toolHead, material),
                    'U', powerUnitStack);
        }
    }
}
