package keqing.gtqtcore.loaders.recipes.gcys;



import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockTransparentCasing;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

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
import static keqing.gtqtcore.api.unification.TJMaterials.Polyetheretherketone;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.boule;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.seedCrystal;
import static keqing.gtqtcore.common.block.blocks.GTQTBlockGlassCasing.CasingType.*;

/**
 * Use this class to add miscellaneous recipes which have no category otherwise
 */
public class MiscRecipes {

    public static void init() {
        metaBlockRecipes();

        //  Advanced Piston (Vanilla) recipes
        ModHandler.addShapedRecipe("piston_tungsten_steel", new ItemStack(Blocks.PISTON, 16),
                "WWW", "SIS", "SDS",
                'W', "plankWood",
                'S', "stoneCobble",
                'I', new UnificationEntry(ingot, TungstenSteel),
                'D', new UnificationEntry(dust, Redstone));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, TungstenSteel)
                .input("plankWood", 3)
                .input("stoneCobble", 4)
                .input(dust, Redstone)
                .outputs(new ItemStack(Blocks.PISTON, 16))
                .EUt(VA[LV])
                .duration(5 * SECOND)
                .buildAndRegister();

        ModHandler.addShapedRecipe("piston_rhodium_plated_palladium", new ItemStack(Blocks.PISTON, 32),
                "WWW", "SIS", "SDS",
                'W', "plankWood",
                'S', "stoneCobble",
                'I', new UnificationEntry(ingot, RhodiumPlatedPalladium),
                'D', new UnificationEntry(dust, Redstone));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, RhodiumPlatedPalladium)
                .input("plankWood", 3)
                .input("stoneCobble", 4)
                .input(dust, Redstone)
                .outputs(new ItemStack(Blocks.PISTON, 32))
                .EUt(VA[LV])
                .duration(5 * SECOND)
                .buildAndRegister();

        ModHandler.addShapedRecipe("piston_naquadah_alloy", new ItemStack(Blocks.PISTON, 64),
                "WWW", "SIS", "SDS",
                'W', "plankWood",
                'S', "stoneCobble",
                'I', new UnificationEntry(ingot, NaquadahAlloy),
                'D', new UnificationEntry(dust, Redstone));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, NaquadahAlloy)
                .input("plankWood", 3)
                .input("stoneCobble", 4)
                .input(dust, Redstone)
                .outputs(new ItemStack(Blocks.PISTON, 64))
                .EUt(VA[LV])
                .duration(5 * SECOND)
                .buildAndRegister();

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

        //  Advanced Recipe of Optical Fiber
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(wireFine, PMMA, 8)
                .input(foil, Electrum, 8)
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .output(MetaBlocks.OPTICAL_PIPES[0], 4)
                .EUt(VA[LuV])
                .duration(5 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(wireFine, CBDOPolycarbonate, 8)
                .input(foil, FluxedElectrum, 8)
                .fluidInputs(Polyetheretherketone.getFluid(L))
                .output(MetaBlocks.OPTICAL_PIPES[0], 16)
                .EUt(VA[ZPM])
                .duration(5 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(wireFine, PraseodymiumDopedZBLANGlass, 8)
                .input(foil, Cinobite, 8)
                .fluidInputs(Zylon.getFluid(L))
                .output(MetaBlocks.OPTICAL_PIPES[0], 64)
                .EUt(VA[UV])
                .duration(5 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Advanced Recipe of Laser Fiber
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(TI_BORON_SILICATE_GLASS))
                .input(foil, Tritanium, 2)
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .output(MetaBlocks.LASER_PIPES[0], 4)
                .EUt(VA[LuV])
                .duration(5 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(W_BORON_SILICATE_GLASS))
                .input(foil, Adamantium, 2)
                .fluidInputs(Polyetheretherketone.getFluid(L))
                .output(MetaBlocks.LASER_PIPES[0], 16)
                .EUt(VA[ZPM])
                .duration(5 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(NAQ_BORON_SILICATE_GLASS))
                .input(foil, Hdcs, 2)
                .fluidInputs(Zylon.getFluid(L))
                .output(MetaBlocks.LASER_PIPES[0], 64)
                .EUt(VA[UV])
                .duration(5 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
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
