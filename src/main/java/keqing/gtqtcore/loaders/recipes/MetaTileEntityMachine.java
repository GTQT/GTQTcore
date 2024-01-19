package keqing.gtqtcore.loaders.recipes;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.loaders.recipe.CraftingComponent;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTBlockGlassCasing;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.blocks.MetaBlocks.MACHINE_CASING;
import static gregtech.common.metatileentities.MetaTileEntities.ARC_FURNACE;
import static gregtech.loaders.recipe.CraftingComponent.*;
import static gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Infinity;
import static keqing.gtqtcore.api.unification.GTQTMaterials.GalvanizedSteel;
import static keqing.gtqtcore.api.unification.TJMaterials.HDCS_1;

public class MetaTileEntityMachine {
    static int L=144;

   public static void init() {
        Hull();
        removeOldMachines();
        registerElectric();
       registerGalvanizedSteel();
    }
    private static void registerGalvanizedSteel() {
            OrePrefix[] galvanizedSteelPrefix = new OrePrefix[]{ingot, plate, stick, stickLong, bolt, screw, ring, gear, gearSmall, rotor, round};
            int tinyQuantity;
            for (OrePrefix prefix : galvanizedSteelPrefix) {
                tinyQuantity = (int) ((prefix.getMaterialAmount(Steel)) / M) + 1;
                ALLOY_SMELTER_RECIPES.recipeBuilder()
                        .duration(tinyQuantity * 16)
                        .EUt(8)
                        .input(prefix, Steel, 1)
                        .input(dustTiny, Zinc, tinyQuantity)
                        .output(prefix, GalvanizedSteel)
                        .buildAndRegister();

                CHEMICAL_BATH_RECIPES.recipeBuilder()
                        .duration(tinyQuantity * 8)
                        .EUt(32)
                        .input(prefix, Steel)
                        .fluidInputs(Zinc.getFluid(16))
                        .output(prefix, GalvanizedSteel)
                        .buildAndRegister();
            }
    }
    private static void Hull() {
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.hull.ulv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.hull.lv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.hull.mv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.hull.hv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.hull.ev");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.hull.iv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.hull.luv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.hull.zpm");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.hull.uv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.hull.uhv");
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(25).EUt(16)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ULV))
                .input(plate, Materials.RedAlloy, 2)
                .input(OrePrefix.cableGtSingle, Materials.Lead, 2)
                .fluidInputs(Materials.Polyethylene.getFluid(L * 2)).outputs(MetaTileEntities.HULL[0].getStackForm())
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LV))
                .input(plate, GalvanizedSteel, 2)
                .input(OrePrefix.cableGtSingle, Materials.Tin, 2).fluidInputs(Materials.Polyethylene.getFluid(L * 2))
                .outputs(MetaTileEntities.HULL[1].getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.MV))
                .input(MetaItems.CARBON_FIBER_PLATE, 2)
                .input(OrePrefix.cableGtSingle, Materials.Copper, 2).fluidInputs(Materials.Polyethylene.getFluid(L * 2))
                .outputs(MetaTileEntities.HULL[2].getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.HV))
                .input(plate, Ultimet, 2)
                .input(OrePrefix.cableGtSingle, Materials.Aluminium, 2).fluidInputs(Materials.Polyethylene.getFluid(L * 2))
                .outputs(MetaTileEntities.HULL[3].getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.EV))
                .input(plate, Palladium, 2)
                .input(OrePrefix.cableGtSingle, Materials.Aluminium, 2)
                .fluidInputs(Materials.Polyethylene.getFluid(L * 2)).outputs(MetaTileEntities.HULL[4].getStackForm())
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.IV))
                .input(plate, Iridium, 2)
                .input(OrePrefix.cableGtSingle, Materials.Platinum, 2)
                .fluidInputs(Polytetrafluoroethylene.getFluid(L * 2)).outputs(MetaTileEntities.HULL[5].getStackForm())
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LuV))
                .input(plate, HSSS, 2)
                .input(OrePrefix.cableGtSingle, Materials.NiobiumTitanium, 2)
                .fluidInputs(Polytetrafluoroethylene.getFluid(L * 2)).outputs(MetaTileEntities.HULL[6].getStackForm())
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ZPM))
                .input(plate, Duranium, 2)
                .input(OrePrefix.cableGtSingle, Materials.VanadiumGallium, 2)
                .fluidInputs(Polybenzimidazole.getFluid(L * 2)).outputs(MetaTileEntities.HULL[7].getStackForm())
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.UV))
                .input(plate, Seaborgium, 2)
                .input(cableGtSingle, Materials.YttriumBariumCuprate, 2).fluidInputs(Polybenzimidazole.getFluid(L * 2))
                .outputs(MetaTileEntities.HULL[8].getStackForm()).buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(16)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.UHV))
                .input(plate, Infinity, 2)
                .input(cableGtSingle, Materials.Europium, 2).fluidInputs(Polybenzimidazole.getFluid(L * 2))
                .outputs(MetaTileEntities.HULL[9].getStackForm()).buildAndRegister();

        //  Boron Silicate Glass
        COMPRESSOR_RECIPES.recipeBuilder()
                .input(dust, BorosilicateGlass, 4)
                .outputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.SILICATE_GLASS))
                .duration(400)
                .EUt(2)
                .buildAndRegister();

        OreDictUnifier.registerOre(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.SILICATE_GLASS),
                new ItemMaterialInfo(new MaterialStack(BorosilicateGlass, M * 4)));

        //  Titanium-reinforced Glass
        ALLOY_SMELTER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.SILICATE_GLASS))
                .input(plate, Titanium, 4)
                .outputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.TI_BORON_SILICATE_GLASS))
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();

        OreDictUnifier.registerOre(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.TI_BORON_SILICATE_GLASS),
                new ItemMaterialInfo(new MaterialStack(BorosilicateGlass, M * 4),
                        new MaterialStack(Titanium, M * 4)));

        //  Tungsten-reinforced Glass
        ALLOY_SMELTER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.SILICATE_GLASS))
                .input(plate, Tungsten, 4)
                .outputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.W_BORON_SILICATE_GLASS))
                .EUt(VA[EV])
                .duration(100)
                .buildAndRegister();

        OreDictUnifier.registerOre(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.W_BORON_SILICATE_GLASS),
                new ItemMaterialInfo(new MaterialStack(BorosilicateGlass, M * 4),
                        new MaterialStack(Tungsten, M * 4)));

        //  Thorium-reinforced Glass
        ALLOY_SMELTER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.SILICATE_GLASS))
                .input(plate, Thorium, 4)
                .outputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.THY_BORON_SILICATE_GLASS))
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();

        OreDictUnifier.registerOre(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.THY_BORON_SILICATE_GLASS),
                new ItemMaterialInfo(new MaterialStack(BorosilicateGlass, M * 4),
                        new MaterialStack(Thorium, M * 4)));

        //  Osmiridium-reinforced Glass
        ALLOY_SMELTER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.SILICATE_GLASS))
                .input(plate, Osmiridium, 4)
                .outputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.OSMIR_BORON_SILICATE_GLASS))
                .EUt(VA[IV])
                .duration(100)
                .buildAndRegister();

        OreDictUnifier.registerOre(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.OSMIR_BORON_SILICATE_GLASS),
                new ItemMaterialInfo(new MaterialStack(BorosilicateGlass, M * 4),
                        new MaterialStack(Osmiridium, M * 4)));

        //  Naquadah-reinforced Glass
        ALLOY_SMELTER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.SILICATE_GLASS))
                .input(plate, Naquadah, 4)
                .outputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.NAQ_BORON_SILICATE_GLASS))
                .EUt(VA[LuV])
                .duration(100)
                .buildAndRegister();

        OreDictUnifier.registerOre(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.NAQ_BORON_SILICATE_GLASS),
                new ItemMaterialInfo(new MaterialStack(BorosilicateGlass, M * 4),
                        new MaterialStack(Naquadah, M * 4)));
    }
    private static void removeOldMachines() {
        //电弧炉
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.arc_furnace.lv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.arc_furnace.mv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.arc_furnace.hv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.arc_furnace.ev");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.arc_furnace.iv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.arc_furnace.luv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.arc_furnace.zpm");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.arc_furnace.uv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.arc_furnace.uhv");
        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, ARC_FURNACE
                , "WGW", "CMC", "PPP",
                'M', CraftingComponent.HULL,
                'P', DOUBLE_PLATE,
                'C', CIRCUIT,
                'W', CABLE_QUAD,
                'G',  new UnificationEntry(GTQTOrePrefix.electrode, Materials.Graphite));


    }
    private static void registerElectric() {



        registerMachineRecipe(GTQTMetaTileEntities.FLUID_EXTRACTOR, "PGP", "EGE", "CMC", 'M', HULL, 'P',  PUMP, 'E', PISTON, 'C',
                CIRCUIT,  'G', GLASS);
        registerMachineRecipe(GTQTMetaTileEntities.FLUID_CANNER, "EGE", "PGP", "CMC", 'M', HULL, 'P',  PUMP, 'E', PISTON, 'C',
                CIRCUIT,  'G', GLASS);

    }

}
