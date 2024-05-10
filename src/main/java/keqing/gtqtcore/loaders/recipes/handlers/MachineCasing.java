package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.block.VariantBlock;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.ConfigHolder;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTADVBlock;
import keqing.gtqtcore.common.block.blocks.GTQTIsaCasing;
import net.minecraft.util.IStringSerializable;

import static gregicality.multiblocks.api.recipes.GCYMRecipeMaps.ALLOY_BLAST_RECIPES;
import static gregicality.multiblocks.api.unification.GCYMMaterials.TitaniumCarbide;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.BLAST_ARC_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing.TurbineCasingType.*;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing1.TurbineCasingType.GALVANIZE_STEEL_CASING;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing1.TurbineCasingType.*;

public class MachineCasing {
    public static void init() {
        DustMixer();
        CasingAssembler();
        Arc();
        CasingRecipes();
    }

    private static void CasingRecipes() {
        createCasingRecipe("red_steel_casing",
                GTQTMetaBlocks.ISA_CASING,
                GTQTIsaCasing.CasingType.VACUUM_CASING,
                RedSteel);

    }


    private static void Arc() {
        BLAST_ARC_RECIPES.recipeBuilder()
                .input(ingot,Steel,16)
                .output(dust, Ash, 16)
                .duration(2000)
                .EUt(VA[HV])
                .buildAndRegister();

        ALLOY_BLAST_RECIPES.recipeBuilder().duration(600).EUt(VA[EV])
                .input(dust, Iron, 4)
                .input(dust, Invar, 3)
                .input(dust, Manganese)
                .input(dust, Chrome)
                .circuitMeta(1)
                .fluidOutputs(StainlessSteel.getFluid(9*144))
                .buildAndRegister();

        ALLOY_BLAST_RECIPES.recipeBuilder().duration(600).EUt(VA[EV])
                .input(dust, Iron, 6)
                .input(dust, Nickel)
                .input(dust, Manganese)
                .input(dust, Chrome)
                .circuitMeta(1)
                .fluidOutputs(StainlessSteel.getFluid(9*144))
                .buildAndRegister();

    }

    private static void DustMixer() {
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Titanium, 5)
                .input(dust, Molybdenum, 5)
                .input(dust, Vanadium, 2)
                .input(dust, Chrome, 3)
                .input(dust, Aluminium)
                .circuitMeta(16)
                .output(dust, TanmolyiumBetaC, 16)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust,Cobalt,4)
                .input(dust,Chrome,3)
                .input(dust,Phosphorus,2)
                .input(dust,Molybdenum,1)
                .output(dust, Talonite, 10)
                .duration(120)
                .EUt(VA[MV])
                .buildAndRegister();

        //  Eglin Steel Base
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Iron, 4)
                .input(dust, Kanthal)
                .input(dust, Invar, 5)
                .circuitMeta(10)
                .output(dust, EglinSteelBase, 10)
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        //  Eglin Steel
        MIXER_RECIPES.recipeBuilder()
                .input(dust, EglinSteelBase, 10)
                .input(dust, Sulfur)
                .input(dust, Silicon)
                .input(dust, Carbon)
                .circuitMeta(13)
                .output(dust, EglinSteel, 13)
                .EUt(VA[MV])
                .duration(120)
                .buildAndRegister();

        //  Silicon Carbide
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Graphite)
                .input(dust, Silicon)
                .circuitMeta(2)
                .output(dust, SiliconCarbide, 2)
                .duration(300)
                .EUt(VA[EV])
                .buildAndRegister();

        //  MAR-Ce-M200 Steel
        MIXER_RECIPES.recipeBuilder()
                .input(dust, MARM200Steel, 18)
                .input(dust, Cerium)
                .output(dust, MARM200CeSteel, 19)
                .EUt(VA[IV])
                .duration(350)
                .buildAndRegister();

        //  Zirconium Carbide
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Zirconium)
                .input(dust, Carbon)
                .circuitMeta(2)
                .output(dust, ZirconiumCarbide, 2)
                .EUt(VA[MV])
                .duration(120)
                .buildAndRegister();

        //  Maraging Steel 250
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Steel, 16)
                .input(dust, Molybdenum)
                .input(dust, Titanium)
                .input(dust, Nickel, 4)
                .input(dust, Cobalt, 2)
                .circuitMeta(24)
                .output(dust, MaragingSteel250, 24)
                .EUt(VA[EV])
                .duration(200)
                .buildAndRegister();

        //  HG-1223
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Barium, 2)
                .input(dust, Calcium, 2)
                .input(dust, Copper, 3)
                .fluidInputs(Mercury.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(8000))
                .circuitMeta(16)
                .output(dust, HG1223, 16)
                .EUt(VA[EV])
                .duration(240)
                .buildAndRegister();

        //  Staballoy
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Uranium238, 9)
                .input(dust, Titanium)
                .circuitMeta(10)
                .output(dust, Staballoy, 10)
                .EUt(VA[EV])
                .duration(260)
                .buildAndRegister();

        //  HMS-1J22 Alloy
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Nickel, 12)
                .input(dust, TinAlloy, 8)
                .input(dust, Chrome, 4)
                .input(dust, Phosphorus, 2)
                .input(dust, Silicon, 2)
                .circuitMeta(28)
                .output(dust, HMS1J22Alloy, 28)
                .EUt(VA[EV])
                .duration(580)
                .buildAndRegister();

        //  Inconel-792
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Nickel, 2)
                .input(dust, Niobium)
                .input(dust, Aluminium, 2)
                .input(dust, Nichrome)
                .circuitMeta(6)
                .output(dust, Inconel792, 6)
                .EUt(VA[IV])
                .duration(250)
                .buildAndRegister();

        //  Stellite
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Chrome, 9)
                .input(dust, Cobalt, 9)
                .input(dust, Manganese, 5)
                .input(dust, Titanium)
                .circuitMeta(24)
                .output(dust, Stellite, 24)
                .EUt(VA[EV])
                .duration(310)
                .buildAndRegister();

        //  Super Austenitic Stainless Steel-904L
        MIXER_RECIPES.recipeBuilder()
                .input(dust, StainlessSteel, 8)
                .input(dust, NickelZincFerrite, 4)
                .input(dust, Kanthal, 4)
                .input(dust, Molybdenum, 4)
                .circuitMeta(20)
                .output(dust, AusteniticStainlessSteel904L, 20)
                .EUt(VA[EV])
                .duration(600)
                .buildAndRegister();

        //  Tanmolyium Beta-C
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Titanium, 5)
                .input(dust, Molybdenum, 5)
                .input(dust, Vanadium, 2)
                .input(dust, Chrome, 3)
                .input(dust, Aluminium)
                .circuitMeta(16)
                .output(dust, TanmolyiumBetaC, 16)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();


    }

    private static void CasingAssembler() {
        ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(120)
                .input(plate,Talonite, 6)
                .input(frameGt,Talonite, 1)
                .circuitMeta(6)
                .outputs(GTQTMetaBlocks.ADV_BLOCK.getItemVariant(GTQTADVBlock.CasingType.Talonite))
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(120)
                .input(plate,RhodiumPlatedPalladium, 6)
                .input(frameGt,RhodiumPlatedPalladium, 1)
                .circuitMeta(6)
                .outputs(GTQTMetaBlocks.TURBINE_CASING.getItemVariant(PD_TURBINE_CASING))
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(120)
                .input(plate,Naquadah, 6)
                .input(frameGt,Naquadah, 1)
                .circuitMeta(6)
                .outputs(GTQTMetaBlocks.TURBINE_CASING.getItemVariant(NQ_TURBINE_CASING))
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(30)
                .input(plate,Steel, 4)
                .input(gear,Steel, 1)
                .input(frameGt,Talonite, 1)
                .fluidInputs(Polyethylene.getFluid(576))
                .circuitMeta(6)
                .outputs(GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(CLARIFIER_CASING,4))
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(1920)
                .input(plate,TungstenSteel, 4)
                .input(gear,TungstenSteel, 1)
                .input(frameGt,NanometerBariumTitanate, 1)
                .fluidInputs(Zylon.getFluid(576))
                .circuitMeta(6)
                .outputs(GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(FLOATING_CASING,4))
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(120)
                .input(plate,Orichalcum, 6)
                .input(frameGt,Orichalcum, 1)
                .circuitMeta(6)
                .outputs(GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(ST_TURBINE_CASING))
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(120)
                .input(plate,Adamantite, 6)
                .input(frameGt,Adamantite, 1)
                .circuitMeta(6)
                .outputs(GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(AD_TURBINE_CASING))
                .buildAndRegister();
    }
    /**
     * Create common Metal Casing recipe.
     *
     * <p>
     *     This method will add two recipe of each Metal Casing,
     *     one is crafting table recipe by Hammer (hard) and Wrench,
     *     another is assembler recipe.
     * </p>
     *
     * @param regName           Register Name of recipe.
     * @param outputCasingType  Variant Block class of {@code MetaBlock}.
     * @param outputCasing      Casing type of {@code MetaBlock}.
     * @param material          Basic {@code material} of Metal Casing,
     *                          means plate and frame material.
     */
    private static <T extends Enum<T> & IStringSerializable> void createCasingRecipe(String regName,
                                                                                     VariantBlock<T> outputCasingType,
                                                                                     T outputCasing,
                                                                                     Material material) {
        ModHandler.addShapedRecipe(true, regName, outputCasingType.getItemVariant(outputCasing, ConfigHolder.recipes.casingsPerCraft),
                "PhP", "PFP","PwP",
                'P', new UnificationEntry(plate, material),
                'F', new UnificationEntry(frameGt, material));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, material, 6)
                .input(frameGt, material)
                .circuitMeta(6)
                .outputs(outputCasingType.getItemVariant(outputCasing, ConfigHolder.recipes.casingsPerCraft))
                .EUt(VA[LV])
                .duration(50)
                .buildAndRegister();
    }

    /**
     * Create Metal Casing recipe with different plate-frame material.
     *
     * <p>
     *     This method will add two recipe of each Metal Casing,
     *     one is crafting table recipe by Hammer (hard) and Wrench,
     *     another is assembler recipe.
     * </p>
     *
     * @param regName           Register Name of recipe.
     * @param outputCasingType  Variant Block class of {@code MetaBlock}.
     * @param outputCasing      Casing type of {@code MetaBlock}.
     * @param plateMaterial     Plate {@code material} of Metal Casing.
     * @param frameMaterial     Frame {@code material} of Metal Casing.
     */
    private static <T extends Enum<T> & IStringSerializable> void createCasingRecipe(String regName,
                                                                                     VariantBlock<T> outputCasingType,
                                                                                     T outputCasing,
                                                                                     Material plateMaterial,
                                                                                     Material frameMaterial) {
        ModHandler.addShapedRecipe(true, regName, outputCasingType.getItemVariant(outputCasing, ConfigHolder.recipes.casingsPerCraft),
                "PhP", "PFP","PwP",
                'P', new UnificationEntry(plate, plateMaterial),
                'F', new UnificationEntry(frameGt, frameMaterial));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, plateMaterial, 6)
                .input(frameGt, frameMaterial)
                .circuitMeta(6)
                .outputs(outputCasingType.getItemVariant(outputCasing, ConfigHolder.recipes.casingsPerCraft))
                .EUt(VA[LV])
                .duration(50)
                .buildAndRegister();
    }

    /**
     * Create Metal Casing recipe with different plate-frame material and new double plate material.
     *
     * <p>
     *     This method will add two recipe of each Metal Casing,
     *     one is crafting table recipe by Hammer (hard) and Wrench,
     *     another is assembler recipe.
     * </p>
     *
     * @param regName              Register Name of recipe.
     * @param outputCasingType     Variant Block class of {@code MetaBlock}.
     * @param outputCasing         Casing type of {@code MetaBlock}.
     * @param plateDoubleMaterial  Double Plate {@code material} of Metal Casing.
     * @param plateMaterial        Plate {@code material} of Metal Casing.
     * @param frameMaterial        Frame {@code material} of Metal Casing.
     */
    private static <T extends Enum<T> & IStringSerializable> void createCasingRecipe(String regName,
                                                                                     VariantBlock<T> outputCasingType,
                                                                                     T outputCasing,
                                                                                     Material plateDoubleMaterial,
                                                                                     Material plateMaterial,
                                                                                     Material frameMaterial) {
        ModHandler.addShapedRecipe(true, regName, outputCasingType.getItemVariant(outputCasing, ConfigHolder.recipes.casingsPerCraft),
                "PhP", "TFT","PwP",
                'P', new UnificationEntry(plateDouble, plateDoubleMaterial),
                'T', new UnificationEntry(plate, plateMaterial),
                'F', new UnificationEntry(frameGt, frameMaterial));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plateDouble, plateDoubleMaterial, 4)
                .input(plate, plateMaterial, 2)
                .input(frameGt, frameMaterial)
                .circuitMeta(6)
                .outputs(outputCasingType.getItemVariant(outputCasing, ConfigHolder.recipes.casingsPerCraft))
                .EUt(VA[LV])
                .duration(50)
                .buildAndRegister();
    }
}
