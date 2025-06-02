package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.BlockWireCoil.*;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.PRECISE_ASSEMBLER_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.SilicaCeramic;

import static keqing.gtqtcore.common.block.blocks.BlockCoolingCoil.CoolingCoilType.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.INSULATINGMICA;

public class CoilWire {
    public static void init() {

        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().EUt(30)
                .input(OrePrefix.dust, Clay, 1)
                .input(plate,NetherQuartz)
                .output(plate,SilicaCeramic)
                .duration(400)
                .buildAndRegister();

        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().EUt(30).input(OrePrefix.dust, Materials.Mica, 1)
                .input(plate,SilicaCeramic)
                .output(INSULATINGMICA)
                .duration(400)
                .buildAndRegister();


        //
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(VA[HV]).input(OrePrefix.wireGtDouble, ManganeseIronArsenicPhosphide, 8)
                .input(OrePrefix.foil, Materials.StainlessSteel, 8).input(INSULATINGMICA,4)
                .fluidInputs(Materials.Aluminium.getFluid(GTValues.L))
                .outputs(GTQTMetaBlocks.blockCoolingCoil.getItemVariant(MANGANESE_IRON_ARSENIC_PHOSPHIDE)).duration(400).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(VA[EV]).input(OrePrefix.wireGtDouble, PraseodymiumNickel, 8)
                .input(OrePrefix.foil, Materials.VanadiumSteel, 8).fluidInputs(Materials.Nichrome.getFluid(GTValues.L)).input(INSULATINGMICA,6)
                .outputs(GTQTMetaBlocks.blockCoolingCoil.getItemVariant(PRASEODYMIUM_NICKEL)).duration(500).buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(VA[IV]).input(OrePrefix.wireGtDouble, GadoliniumSiliconGermanium, 8)
                .input(OrePrefix.foil, Materials.TungstenCarbide, 8).input(INSULATINGMICA,8)
                .fluidInputs(Materials.Tungsten.getFluid(GTValues.L))
                .outputs(GTQTMetaBlocks.blockCoolingCoil.getItemVariant(GADOLINIUM_SILICON_GERMANIUM)).duration(600).buildAndRegister();
        //
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(VA[LV])
                .input(OrePrefix.wireGtDouble, Materials.Cupronickel, 8)
                .input(OrePrefix.foil, Materials.Bronze, 8).fluidInputs(Materials.TinAlloy.getFluid(GTValues.L))
                .input(INSULATINGMICA,1)
                .outputs(MetaBlocks.WIRE_COIL.getItemVariant(CoilType.CUPRONICKEL))
                .duration(200)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(VA[MV])
                .input(OrePrefix.wireGtDouble, Materials.Kanthal, 8)
                .input(OrePrefix.foil, Materials.Aluminium, 8)
                .fluidInputs(Materials.Copper.getFluid(GTValues.L))
                .input(INSULATINGMICA,2)
                .outputs(MetaBlocks.WIRE_COIL.getItemVariant(CoilType.KANTHAL))
                .duration(300)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(VA[HV])
                .input(OrePrefix.wireGtDouble, Materials.Nichrome, 8)
                .input(OrePrefix.foil, Materials.StainlessSteel, 8)
                .fluidInputs(Materials.Aluminium.getFluid(GTValues.L))
                .input(INSULATINGMICA,4)
                .outputs(MetaBlocks.WIRE_COIL.getItemVariant(CoilType.NICHROME))
                .duration(400)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(VA[EV])
                .input(OrePrefix.wireGtDouble, Materials.RTMAlloy, 8)
                .input(OrePrefix.foil, Materials.VanadiumSteel, 8)
                .fluidInputs(Materials.Nichrome.getFluid(GTValues.L))
                .input(INSULATINGMICA,8)
                .outputs(MetaBlocks.WIRE_COIL.getItemVariant(CoilType.RTM_ALLOY))
                .duration(500)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(VA[IV])
                .input(OrePrefix.wireGtDouble, Materials.HSSG, 8)
                .input(OrePrefix.foil, Materials.TungstenCarbide, 8)
                .fluidInputs(Materials.Tungsten.getFluid(GTValues.L))
                .input(INSULATINGMICA,16)
                .outputs(MetaBlocks.WIRE_COIL.getItemVariant(CoilType.HSS_G))
                .duration(600)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(VA[LuV])
                .input(OrePrefix.wireGtDouble, Materials.Naquadah, 8)
                .input(OrePrefix.foil, Materials.Osmium, 8)
                .fluidInputs(Materials.TungstenSteel.getFluid(GTValues.L))
                .input(INSULATINGMICA,32)
                .outputs(MetaBlocks.WIRE_COIL.getItemVariant(CoilType.NAQUADAH))
                .duration(700)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(VA[ZPM])
                .input(OrePrefix.wireGtDouble, Materials.Trinium, 8)
                .input(OrePrefix.foil, Materials.NaquadahEnriched, 8)
                .input(INSULATINGMICA,48)
                .fluidInputs(Materials.Naquadah.getFluid(GTValues.L))
                .outputs(MetaBlocks.WIRE_COIL.getItemVariant(CoilType.TRINIUM))
                .duration(800)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().EUt(VA[UV])
                .input(OrePrefix.wireGtDouble, Materials.Tritanium, 8)
                .input(OrePrefix.foil, Materials.Naquadria, 8)
                .input(INSULATINGMICA,64)
                .fluidInputs(Materials.Trinium.getFluid(GTValues.L))
                .outputs(MetaBlocks.WIRE_COIL.getItemVariant(CoilType.TRITANIUM))
                .duration(900)
                .buildAndRegister();
    }
}
