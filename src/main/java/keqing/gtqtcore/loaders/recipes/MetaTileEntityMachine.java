package keqing.gtqtcore.loaders.recipes;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.RecyclingData;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockSteamCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.core.unification.material.internal.MaterialRegistryManager;
import gregtech.loaders.recipe.CraftingComponent;
import gregtech.loaders.recipe.MetaTileEntityLoader;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockEnergyCell;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockGlass1;
import keqing.gtqtcore.common.block.blocks.BlockNuclearCasing;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static gregicality.multiblocks.api.unification.GCYMMaterials.WatertightSteel;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.blocks.MetaBlocks.MACHINE_CASING;
import static gregtech.common.items.MetaItems.ORE_DICTIONARY_FILTER;
import static gregtech.common.items.MetaItems.SHAPE_MOLD_BLOCK;
import static gregtech.common.metatileentities.MetaTileEntities.ARC_FURNACE;
import static gregtech.loaders.recipe.CraftingComponent.*;
import static gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe;
import static gtqt.api.util.MaterialHelper.Cable;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.MaterialHelper.Glue;
import static keqing.gtqtcore.api.unification.MaterialHelper.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.plate_curved;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4.TurbineCasingType.FISHING_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing5.TurbineCasingType.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.ELECTRODE_GRAPHITE;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.*;

public class MetaTileEntityMachine {
    static int L = 144;

    public static void init() {
        VanillaOverrideRecipes();
        Hull();
        removeOldMachines();
        registerElectric();
        registerGalvanizedSteel();
        machinecasing();
        turbine();
        dust();
        EnergyCells();
    }

    private static void EnergyCells() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input(plate, Aluminium, 4)
                .input(frameGt, Steel, 2)
                .input(stickLong, Steel, 2)
                .input(block, Lapis)
                .outputs(GTQTMetaBlocks.blockNuclearCasing.getItemVariant(BlockNuclearCasing.CasingType.ENERGY_CELL))
                .EUt(VA[LV])
                .duration(100)
                .buildAndRegister();

        //  Energy Substation
        ModHandler.addShapedRecipe(true, "energy_substation", ENERGY_SUBSTATION.getStackForm(),
                "XBX", "BHB", "XBX",
                'H', MetaTileEntities.HULL[MV].getStackForm(),
                'B', new UnificationEntry(battery, MarkerMaterials.Tier.MV),
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.MV));

        //  ULV
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input(frameGt, Iron)
                .input(plate, Copper, 4)
                .input(battery, MarkerMaterials.Tier.ULV, 4)
                .input(circuit, MarkerMaterials.Tier.ULV, 4)
                .input(wireGtSingle, RedAlloy, 16)
                .fluidInputs(Helium.getFluid(4000))
                .outputs(GTQTMetaBlocks.blockEnergyCell.getItemVariant(BlockEnergyCell.CellTier.ULV))
                .EUt(VA[ULV])
                .duration(6 * SECOND)
                .buildAndRegister();
        //  LV
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input(frameGt, Steel)
                .input(plate, Tin, 4)
                .input(battery, MarkerMaterials.Tier.LV, 4)
                .input(circuit, MarkerMaterials.Tier.LV, 4)
                .input(wireGtSingle, ManganesePhosphide, 16)
                .fluidInputs(Helium.getFluid(4000))
                .outputs(GTQTMetaBlocks.blockEnergyCell.getItemVariant(BlockEnergyCell.CellTier.LV))
                .EUt(VA[LV])
                .duration(6 * SECOND)
                .buildAndRegister();

        //  MV
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input(frameGt, Aluminium)
                .input(plate, Lead, 4)
                .input(battery, MarkerMaterials.Tier.MV, 4)
                .input(circuit, MarkerMaterials.Tier.MV, 4)
                .input(wireGtSingle, MagnesiumDiboride, 16)
                .fluidInputs(Helium.getFluid(4000))
                .outputs(GTQTMetaBlocks.blockEnergyCell.getItemVariant(BlockEnergyCell.CellTier.MV))
                .EUt(VA[MV])
                .duration(6 * SECOND)
                .buildAndRegister();
        //  HV
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input(frameGt, StainlessSteel)
                .input(plate, Gold, 4)
                .input(battery, MarkerMaterials.Tier.HV, 4)
                .input(circuit, MarkerMaterials.Tier.HV, 4)
                .input(wireGtSingle, MercuryBariumCalciumCuprate, 16)
                .fluidInputs(Helium.getFluid(4000))
                .outputs(GTQTMetaBlocks.blockEnergyCell.getItemVariant(BlockEnergyCell.CellTier.HV))
                .EUt(VA[HV])
                .duration(6 * SECOND)
                .buildAndRegister();

        //  EV
        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input(frameGt, Titanium)
                .input(plate, Vanadium, 4)
                .input(battery, MarkerMaterials.Tier.EV, 4)
                .input(circuit, MarkerMaterials.Tier.EV, 4)
                .input(wireGtSingle, UraniumTriplatinum, 16)
                .fluidInputs(Neon.getFluid(4000))
                .outputs(GTQTMetaBlocks.blockEnergyCell.getItemVariant(BlockEnergyCell.CellTier.EV))
                .EUt(VA[EV])
                .duration((int) (3.2 * SECOND))
                .buildAndRegister();
    }

    private static void dust() {
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Titanium, 6)
                .input(dust, Aluminium, 4)
                .input(dust, Vanadium)
                .circuitMeta(11)
                .output(dust, TitaniumAlloyTCF, 11)
                .duration(800).EUt(VA[HV]).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, EnderPearl)
                .input(dust, Iron)
                .input(dust, RedstoneAlloy)
                .circuitMeta(3)
                .output(dust, PulsatingIron, 3)
                .duration(800).EUt(VA[MV]).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Silver)
                .input(dust, Iron)
                .input(dust, RedstoneAlloy)
                .circuitMeta(3)
                .output(dust, ConductiveIron, 3)
                .duration(800).EUt(VA[MV]).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, BlackSteel)
                .input(dust, Gold)
                .input(dust, ConductiveIron)
                .circuitMeta(3)
                .output(dust, EnergeticAlloy, 3)
                .duration(800).EUt(VA[MV]).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, EnderEye)
                .input(dust, EnergeticAlloy)
                .input(dust, Chromite)
                .circuitMeta(3)
                .output(dust, VibrantAlloy, 3)
                .duration(800).EUt(VA[MV]).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Gold)
                .input(dust, Carbon)
                .circuitMeta(2)
                .output(dust, Soularium, 2)
                .duration(800).EUt(VA[MV]).buildAndRegister();

        /*
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Steel)
                .input(dust, Coal)
                .input(dust, Silicon)
                .circuitMeta(3)
                .output(dust, ElectricalSteel, 3)
                .duration(800).EUt(VA[HV]).buildAndRegister();

         */

        MIXER_RECIPES.recipeBuilder()
                .input(dust, ElectricalSteel)
                .input(dust, Coal)
                .input(dust, Obsidian)
                .circuitMeta(3)
                .output(dust, DarkSteel, 3)
                .duration(800).EUt(VA[HV]).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, DarkSteel)
                .input(dust, Endstone)
                .input(dust, TungstenSteel)
                .circuitMeta(3)
                .output(dust, EndSteel, 3)
                .duration(800).EUt(VA[EV]).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Titanium, 3)
                .input(dust, Nickel, 2)
                .output(dust, Nitinol, 5)
                .circuitMeta(5)
                .duration(800).EUt(VA[EV]).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Manganese, 2)
                .input(dust, Iron, 2)
                .input(dust, Arsenic, 1)
                .input(dust, Phosphorus, 1)
                .output(dust, ManganeseIronArsenicPhosphide, 6)
                .circuitMeta(6)
                .duration(800).EUt(VA[EV]).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Praseodymium, 5)
                .input(dust, Nickel, 1)
                .output(dust, PraseodymiumNickel, 6)
                .circuitMeta(6)
                .duration(800).EUt(VA[EV]).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Gadolinium, 5)
                .input(dust, Silicon, 2)
                .input(dust, Germanium, 2)
                .output(dust, GadoliniumSiliconGermanium, 9)
                .circuitMeta(11)
                .duration(800).EUt(VA[EV]).buildAndRegister();
    }

    private static void turbine() {
        ModHandler.addShapedRecipe(true, "casing_aluminium_pipe",
                GTQTMetaBlocks.blockMultiblockCasing5.getItemVariant(AL_TURBINE_CASING, ConfigHolder.recipes.casingsPerCraft),
                "PIP", "IFI", "PIP", 'P', new UnificationEntry(OrePrefix.plate, Aluminium), 'F',
                new UnificationEntry(OrePrefix.frameGt, Materials.Aluminium), 'I',
                new UnificationEntry(OrePrefix.pipeNormalFluid, Materials.Aluminium));

        ModHandler.addShapedRecipe(true, "casing_stainless_pipe",
                GTQTMetaBlocks.blockMultiblockCasing5.getItemVariant(SA_TURBINE_CASING, ConfigHolder.recipes.casingsPerCraft), "PIP", "IFI",
                "PIP", 'P', new UnificationEntry(OrePrefix.plate, StainlessSteel), 'F',
                new UnificationEntry(OrePrefix.frameGt, Materials.StainlessSteel), 'I',
                new UnificationEntry(OrePrefix.pipeNormalFluid, Materials.StainlessSteel));

        ModHandler.addShapedRecipe(true, "casing_pd_pipe",
                GTQTMetaBlocks.blockMultiblockCasing4.getItemVariant(BlockMultiblockCasing4.TurbineCasingType.PD_MACHINE_CASING, ConfigHolder.recipes.casingsPerCraft), "PIP", "IFI",
                "PIP", 'P', new UnificationEntry(OrePrefix.plate, RhodiumPlatedPalladium), 'F',
                new UnificationEntry(OrePrefix.frameGt, Materials.RhodiumPlatedPalladium), 'I',
                new UnificationEntry(OrePrefix.pipeNormalFluid, NiobiumTitanium));

        ModHandler.addShapedRecipe(true, "brick",
                GTQTMetaBlocks.blockMultiblockCasing4.getItemVariant(BlockMultiblockCasing4.TurbineCasingType.BRICK),
                "PIP", "IFI", "PIP",
                'P', new UnificationEntry(OrePrefix.plate, Wood),
                'F', new UnificationEntry(OrePrefix.frameGt, Materials.Wood),
                'I', Blocks.BRICK_BLOCK);


        ModHandler.addShapedRecipe(true, "casing_nq_pipe",
                GTQTMetaBlocks.blockMultiblockCasing4.getItemVariant(BlockMultiblockCasing4.TurbineCasingType.NQ_MACHINE_CASING, ConfigHolder.recipes.casingsPerCraft), "PIP", "IFI",
                "PIP", 'P', new UnificationEntry(OrePrefix.plate, NaquadahAlloy), 'F',
                new UnificationEntry(OrePrefix.frameGt, Materials.NaquadahAlloy), 'I',
                new UnificationEntry(OrePrefix.pipeNormalFluid, Naquadah));

        ModHandler.addShapedRecipe(true, "casing_st_pipe",
                GTQTMetaBlocks.blockMultiblockCasing5.getItemVariant(ST_MACHINE_CASING, ConfigHolder.recipes.casingsPerCraft), "PIP", "IFI",
                "PIP", 'P', new UnificationEntry(OrePrefix.plate, Orichalcum), 'F',
                new UnificationEntry(OrePrefix.frameGt, Orichalcum), 'I',
                new UnificationEntry(OrePrefix.pipeNormalFluid, Orichalcum));

        ModHandler.addShapedRecipe(true, "casing_ad_pipe",
                GTQTMetaBlocks.blockMultiblockCasing5.getItemVariant(AD_MACHINE_CASING, ConfigHolder.recipes.casingsPerCraft), "PIP", "IFI",
                "PIP", 'P', new UnificationEntry(OrePrefix.plate, Adamantium), 'F',
                new UnificationEntry(OrePrefix.frameGt, Adamantium), 'I',
                new UnificationEntry(OrePrefix.pipeNormalFluid, Adamantium));

        ModHandler.addShapedRecipe(true, "fishing_casing",
                GTQTMetaBlocks.blockMultiblockCasing4.getItemVariant(FISHING_CASING, ConfigHolder.recipes.casingsPerCraft), "PIP", "IFI",
                "PIP", 'P', new UnificationEntry(OrePrefix.plate, Inconel792), 'F',
                new UnificationEntry(OrePrefix.frameGt, WatertightSteel), 'I',
                new UnificationEntry(OrePrefix.pipeNormalFluid, StainlessSteel));
    }

    private static void machinecasing() {
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(30)
                .input(plate, GalvanizedSteel, 6)
                .input(frameGt, GalvanizedSteel, 1)
                .circuitMeta(6)
                .outputs(GTQTMetaBlocks.blockMultiblockCasing5.getItemVariant(GALVANIZE_STEEL_CASING))
                .buildAndRegister();
    }

    private static void VanillaOverrideRecipes() {
        //  Iron bucket
        ModHandler.removeRecipeByName("gregtech:iron_bucket");
        ModHandler.addShapedRecipe("iron_bucket", new ItemStack(Items.BUCKET),
                "ChC", " P ",
                'C', new UnificationEntry(plate_curved, Iron),
                'P', new UnificationEntry(plate, Iron));

        //  Hopper
        ModHandler.removeRecipeByName("gregtech:hopper");
        ModHandler.addShapedRecipe("hopper", new ItemStack(Blocks.HOPPER),
                "UCU", "UGU", "wPh",
                'U', new UnificationEntry(plate_curved, Iron),
                'C', "chestWood",
                'G', new UnificationEntry(gearSmall, Iron),
                'P', new UnificationEntry(plate, Iron));

        //  Cauldron
        ModHandler.removeRecipeByName("gregtech:cauldron");
        ModHandler.addShapedRecipe("cauldron", new ItemStack(Items.CAULDRON),
                "C C", "ChC", "CPC",
                'C', new UnificationEntry(plate_curved, Iron),
                'P', new UnificationEntry(plate, Iron));

        //  Compass
        ModHandler.removeRecipeByName("minecraft:compass");
        ModHandler.addShapedRecipe("compass", new ItemStack(Items.COMPASS),
                " C ", "CRC", " C ",
                'C', new UnificationEntry(plate_curved, Iron),
                'R', new UnificationEntry(bolt, RedAlloy));

        //  Clock
        ModHandler.removeRecipeByName("minecraft:clock");
        ModHandler.addShapedRecipe("clock", new ItemStack(Items.CLOCK),
                " C ", "CRC", " C ",
                'C', new UnificationEntry(plate_curved, Gold),
                'R', new UnificationEntry(bolt, RedAlloy));

        //  Fishing Rod
        ModHandler.removeRecipeByName("minecraft:fishing_rod");
        ModHandler.addShapedRecipe("fishing_rod", new ItemStack(Items.FISHING_ROD),
                "  R", " RS", "R I",
                'R', new UnificationEntry(stickLong, Wood),
                'S', "string",
                'I', new UnificationEntry(ring, Iron));

        //  Shears
        ModHandler.removeRecipeByName("minecraft:shears");
        ModHandler.addShapedRecipe("shears", new ItemStack(Items.SHEARS),
                "PSP", "hRf", "XsX",
                'P', new UnificationEntry(plate, Iron),
                'S', new UnificationEntry(screw, Iron),
                'R', new UnificationEntry(ring, Iron),
                'X', new UnificationEntry(stick, Wood));

        //  Tool Override Recipe
        Collection<Material> list = MaterialRegistryManager.getInstance().getRegisteredMaterials();
        for (Material material : list) {
            if (material.hasFlag(MaterialFlags.GENERATE_FOIL) && !material.hasFlag(MaterialFlags.NO_SMASHING)) {
                ModHandler.removeRecipeByName(String.format("gregtech:foil_%s", material));
            }
            if (material.hasFlag(MaterialFlags.GENERATE_RING) && !material.hasFlag(MaterialFlags.NO_SMASHING)) {
                ModHandler.removeRecipeByName(String.format("gregtech:ring_%s", material));
            }
            if (material.hasFlag(MaterialFlags.GENERATE_SPRING) && !material.hasFlag(MaterialFlags.NO_SMASHING)) {
                ModHandler.removeRecipeByName(String.format("gregtech:spring_%s", material));
            }
            if (material.hasFlag(MaterialFlags.GENERATE_SPRING_SMALL) && !material.hasFlag(MaterialFlags.NO_SMASHING)) {
                ModHandler.removeRecipeByName(String.format("gregtech:spring_small_%s", material));
            }
            if (material.hasFlag(MaterialFlags.GENERATE_ROTOR) && !material.hasFlag(MaterialFlags.NO_SMASHING)) {
                ModHandler.removeRecipeByName(String.format("gregtech:rotor_%s", material));
            }
        }
    }

    private static void registerGalvanizedSteel() {
        OrePrefix[] galvanizedSteelPrefix = new OrePrefix[]{ingot, plate, stick, stickLong, bolt, screw, ring, gear, gearSmall, rotor, round};
        int tinyQuantity;
        for (OrePrefix prefix : galvanizedSteelPrefix) {
            tinyQuantity = (int) ((prefix.getMaterialAmount(Steel)) / M) + 1;
            ALLOY_SMELTER_RECIPES.recipeBuilder()
                    .duration(tinyQuantity * 320)
                    .EUt(8)
                    .input(prefix, Steel, 1)
                    .input(dustTiny, Zinc, tinyQuantity)
                    .output(prefix, GalvanizedSteel)
                    .buildAndRegister();

            CHEMICAL_BATH_RECIPES.recipeBuilder()
                    .duration(tinyQuantity * 80)
                    .EUt(32)
                    .input(prefix, Steel)
                    .fluidInputs(Zinc.getFluid(16))
                    .output(prefix, GalvanizedSteel)
                    .buildAndRegister();
        }
    }

    private static void Hull() {
        //机器外壳
        for (int i = 0; i <= UEV; i++) {
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(100).EUt(VA[1])
                    .inputs(MACHINE_CASING.getItemVariant(MachineCasing[i]))
                    .input(plate, SecondPlate[i], 2)
                    .input(OrePrefix.cableGtSingle, Cable.get(i), 2)
                    .outputs(MetaTileEntities.HULL[i].getStackForm())
                    .fluidInputs(Glue[i].getFluid(L * 2))
                    .buildAndRegister();
        }
        //////////////////////////////////////玻璃
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(120).EUt(120)
                .fluidInputs(BorosilicateGlass.getFluid(L * 4))
                .notConsumable(SHAPE_MOLD_BLOCK)
                .outputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(BlockMultiblockGlass1.CasingType.SILICATE_GLASS))
                .buildAndRegister();

        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(480).EUt(120)
                .input(dust, BorosilicateGlass, 4)
                .notConsumable(SHAPE_MOLD_BLOCK.getStackForm())
                .outputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(BlockMultiblockGlass1.CasingType.SILICATE_GLASS))
                .buildAndRegister();

        //  Boron Silicate Glass
        GregTechAPI.RECYCLING_MANAGER.registerRecyclingData(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(BlockMultiblockGlass1.CasingType.SILICATE_GLASS),
                new RecyclingData(new MaterialStack(BorosilicateGlass, M * 4)));

        //  Thorium-reinforced Glass
        GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(BlockMultiblockGlass1.CasingType.SILICATE_GLASS))
                .input(plate, Thorium, 4)
                .outputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(BlockMultiblockGlass1.CasingType.THY_SILICATE_GLASS))
                .EUt(VA[HV])
                .duration(500)
                .buildAndRegister();

        GregTechAPI.RECYCLING_MANAGER.registerRecyclingData(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(BlockMultiblockGlass1.CasingType.THY_SILICATE_GLASS),
                new RecyclingData(new MaterialStack(BorosilicateGlass, M * 4),
                        new MaterialStack(Thorium, M * 4)));

        //  Titanium-reinforced Glass
        GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(BlockMultiblockGlass1.CasingType.SILICATE_GLASS))
                .input(plate, Titanium, 4)
                .outputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(BlockMultiblockGlass1.CasingType.TI_BORON_SILICATE_GLASS))
                .EUt(VA[HV])
                .duration(500)
                .buildAndRegister();

        GregTechAPI.RECYCLING_MANAGER.registerRecyclingData(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(BlockMultiblockGlass1.CasingType.TI_BORON_SILICATE_GLASS),
                new RecyclingData(new MaterialStack(BorosilicateGlass, M * 4),
                        new MaterialStack(Titanium, M * 4)));

        //  Tungsten-reinforced Glass
        GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(BlockMultiblockGlass1.CasingType.SILICATE_GLASS))
                .input(plate, Tungsten, 4)
                .outputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(BlockMultiblockGlass1.CasingType.W_BORON_SILICATE_GLASS))
                .EUt(VA[EV])
                .duration(500)
                .buildAndRegister();

        GregTechAPI.RECYCLING_MANAGER.registerRecyclingData(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(BlockMultiblockGlass1.CasingType.W_BORON_SILICATE_GLASS),
                new RecyclingData(new MaterialStack(BorosilicateGlass, M * 4),
                        new MaterialStack(Tungsten, M * 4)));


        //  Osmiridium-reinforced Glass
        GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(BlockMultiblockGlass1.CasingType.SILICATE_GLASS))
                .input(plate, Osmiridium, 4)
                .outputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(BlockMultiblockGlass1.CasingType.OSMIR_BORON_SILICATE_GLASS))
                .EUt(VA[IV])
                .duration(500)
                .buildAndRegister();

        GregTechAPI.RECYCLING_MANAGER.registerRecyclingData(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(BlockMultiblockGlass1.CasingType.OSMIR_BORON_SILICATE_GLASS),
                new RecyclingData(new MaterialStack(BorosilicateGlass, M * 4),
                        new MaterialStack(Osmiridium, M * 4)));

        //  Naquadah-reinforced Glass
        GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(BlockMultiblockGlass1.CasingType.SILICATE_GLASS))
                .input(plate, Naquadah, 4)
                .outputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(BlockMultiblockGlass1.CasingType.NAQ_BORON_SILICATE_GLASS))
                .EUt(VA[LuV])
                .duration(500)
                .buildAndRegister();

        GregTechAPI.RECYCLING_MANAGER.registerRecyclingData(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(BlockMultiblockGlass1.CasingType.NAQ_BORON_SILICATE_GLASS),
                new RecyclingData(new MaterialStack(BorosilicateGlass, M * 4),
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

        registerMachineRecipe(true, ARC_FURNACE
                , "WGW", "CMC", "PPP",
                'M', CraftingComponent.HULL,
                'P', DOUBLE_PLATE,
                'C', CIRCUIT,
                'W', CABLE_QUAD,
                'G', ELECTRODE_GRAPHITE);

        //蒸馏室
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.distillery.lv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.distillery.mv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.distillery.hv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.distillery.ev");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.distillery.iv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.distillery.luv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.distillery.zpm");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.distillery.uv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.distillery.uhv");

        Component HIGH_TIER_CIRCUIT = new Component(Stream.of(new Object[]{0, new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.LV)}, new Object[]{1, new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.MV)}, new Object[]{2, new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.HV)}, new Object[]{3, new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.EV)}, new Object[]{4, new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.IV)}, new Object[]{5, new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.LuV)}, new Object[]{6, new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.ZPM)}, new Object[]{7, new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.UV)}, new Object[]{8, new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.UHV)}, new Object[]{9, new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.UEV)}, new Object[]{10, new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.UIV)}, new Object[]{11, new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.UXV)}, new Object[]{12, new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.OpV)}, new Object[]{13, new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.MAX)}).collect(Collectors.toMap((data) -> (Integer) data[0], (data) -> data[1])));

        registerMachineRecipe(MetaTileEntities.DISTILLERY,
                "GBG", "CMC", "WPW",
                'M', HULL,
                'P', PUMP,
                'B', STICK_DISTILLATION,
                'C', HIGH_TIER_CIRCUIT,
                'W', CABLE,
                'G', GLASS);
    }

    private static void registerElectric() {

        //  Steam Vacuum Chamber
        ModHandler.addShapedRecipe(true, "steam_vacuum_chamber.bronze", STEAM_VACUUM_CHAMBER[0].getStackForm(),
                "GCG", "PHP", "GWG",
                'W', new UnificationEntry(pipeTinyFluid, Bronze),
                'C', new UnificationEntry(gem, Diamond),
                'P', Blocks.PISTON,
                'G', "blockGlass",
                'H', MetaBlocks.STEAM_CASING.getItemVariant(BlockSteamCasing.SteamCasingType.BRONZE_HULL));

        ModHandler.addShapedRecipe(true, "steam_vacuum_chamber.steel", STEAM_VACUUM_CHAMBER[1].getStackForm(),
                "GCG", "PHP", "GWG",
                'W', new UnificationEntry(pipeTinyFluid, TinAlloy),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.ULV),
                'P', Blocks.PISTON,
                'G', "blockGlass",
                'H', MetaBlocks.STEAM_CASING.getItemVariant(BlockSteamCasing.SteamCasingType.STEEL_HULL));

        // Steam Spinner
        ModHandler.addShapedRecipe(true, "steam_spinner.bronze", STEAM_SPINNER[0].getStackForm(),
                " S ", "MHM", "WXW",
                'H', MetaBlocks.STEAM_CASING.getItemVariant(BlockSteamCasing.SteamCasingType.BRONZE_HULL),
                'M', Blocks.PISTON,
                'S', GTQTMetaItems.SPINNER,
                'W', new UnificationEntry(pipeTinyFluid, Bronze),
                'X', new UnificationEntry(gem, Diamond));

        ModHandler.addShapedRecipe(true, "steam_spinner.steel", STEAM_SPINNER[1].getStackForm(),
                " S ", "MHM", "WXW",
                'H', MetaBlocks.STEAM_CASING.getItemVariant(BlockSteamCasing.SteamCasingType.STEEL_HULL),
                'M', Blocks.PISTON,
                'S', GTQTMetaItems.SPINNER,
                'W', new UnificationEntry(pipeTinyFluid, TinAlloy),
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.ULV));

        // Catalytic Reformer
        MetaTileEntityLoader.registerMachineRecipe(true, CATALYTIC_REFORMER,
                "MCM", "PHP", "MKM",
                'M', CraftingComponent.PIPE_NORMAL,
                'C', CraftingComponent.CIRCUIT,
                'P', CraftingComponent.PUMP,
                'H', CraftingComponent.HULL,
                'K', CraftingComponent.DOUBLE_PLATE);

        // Sonicator
        MetaTileEntityLoader.registerMachineRecipe(true, SONICATOR,
                "RXR", "GPG", "MHM",
                'H', CraftingComponent.HULL,
                'M', CraftingComponent.MOTOR,
                'P', CraftingComponent.PIPE_REACTOR,
                'G', CraftingComponent.GLASS,
                'R', CraftingComponent.ROTOR,
                'X', CraftingComponent.BETTER_CIRCUIT);

        // CVD Unit
        MetaTileEntityLoader.registerMachineRecipe(true, CVD_UNIT,
                "PKP", "CHC", "ESE",
                'P', new UnificationEntry(plate, Carbon),
                'K', CraftingComponent.CABLE,
                'C', CraftingComponent.CIRCUIT,
                'H', CraftingComponent.HULL,
                'S', CraftingComponent.SENSOR,
                'E', CraftingComponent.EMITTER);

        // Flotation Cell
        MetaTileEntityLoader.registerMachineRecipe(true, FLOTATION_CELL,
                "XOX", "PHP", "RRR",
                'H', CraftingComponent.HULL,
                'P', CraftingComponent.PUMP,
                'R', CraftingComponent.PIPE_REACTOR,
                'O', ORE_DICTIONARY_FILTER,
                'X', CraftingComponent.CIRCUIT);

        //  Condenser
        MetaTileEntityLoader.registerMachineRecipe(true, CONDENSER,
                "RFR", "PHP", "WXW",
                'H', CraftingComponent.HULL,
                'P', CraftingComponent.PISTON,
                'F', CraftingComponent.FIELD_GENERATOR,
                'W', CraftingComponent.CABLE,
                'X', CraftingComponent.BETTER_CIRCUIT,
                'R', CraftingComponent.STICK_RADIOACTIVE);

        // Ion Implanter
        MetaTileEntityLoader.registerMachineRecipe(true, ION_IMPLANTER,
                "PXP", "EHE", "DXD",
                'E', CraftingComponent.EMITTER,
                'H', CraftingComponent.HULL,
                'D', CraftingComponent.DOUBLE_PLATE,
                'X', CraftingComponent.CIRCUIT,
                'P', CraftingComponent.PIPE_NORMAL);

        //  Bio Reactor
        registerMachineRecipe(true, BIO_REACTOR,
                "PXX", "pHp", "PMW",
                'H', CraftingComponent.HULL,
                'P', CraftingComponent.PUMP,
                'p', CraftingComponent.PIPE_NORMAL,
                'X', CraftingComponent.CIRCUIT,
                'W', CraftingComponent.CABLE,
                'M', CraftingComponent.MOTOR);
        // Burner Reactor
        MetaTileEntityLoader.registerMachineRecipe(true, BURNER_REACTOR,
                "KRK", "IHI", "CMC",
                'K', CraftingComponent.CABLE_QUAD,
                'R', CraftingComponent.ROTOR,
                'I', CraftingComponent.PIPE_NORMAL,
                'H', CraftingComponent.HULL,
                'C', CraftingComponent.CIRCUIT,
                'M', CraftingComponent.MOTOR);

        MetaTileEntityLoader.registerMachineRecipe(true, LOW_TEMP_ACTIVATOR,
                "EXE", "GHG", "WXW",
                'E', CraftingComponent.EMITTER,
                'X', CraftingComponent.CIRCUIT,
                'G', CraftingComponent.GLASS,
                'W', CraftingComponent.CABLE,
                'H', CraftingComponent.HULL);

        MetaTileEntityLoader.registerMachineRecipe(true, PRESSURE_LAMINATOR,
                "EEE", "XHX", "WXW",
                'E', CraftingComponent.PISTON,
                'X', CraftingComponent.CIRCUIT,
                'W', CraftingComponent.CABLE,
                'H', CraftingComponent.HULL);

        MetaTileEntityLoader.registerMachineRecipe(true, POLYMERIZATION_TANK,
                "GXG", "GMG", "PHP",
                'H', CraftingComponent.HULL,
                'P', CraftingComponent.PIPE_LARGE,
                'G', CraftingComponent.GLASS,
                'X', CraftingComponent.CIRCUIT,
                'M', CraftingComponent.MOTOR);

        // Spinner
        MetaTileEntityLoader.registerMachineRecipe(true, GTQTMetaTileEntities.SPINNER,
                " S ", "MHM", "WXW",
                'S', GTQTMetaItems.SPINNER,
                'H', CraftingComponent.HULL,
                'M', CraftingComponent.MOTOR,
                'W', CraftingComponent.CABLE,
                'X', CraftingComponent.CIRCUIT);


        registerMachineRecipe(GTQTMetaTileEntities.FLUID_EXTRACTOR, "PGP", "EGE", "CMC", 'M', HULL, 'P', PUMP, 'E', PISTON, 'C',
                CIRCUIT, 'G', GLASS);
        registerMachineRecipe(GTQTMetaTileEntities.FLUID_CANNER, "EGE", "PGP", "CMC", 'M', HULL, 'P', PUMP, 'E', PISTON, 'C',
                CIRCUIT, 'G', GLASS);
        registerMachineRecipe(GTQTMetaTileEntities.DEHYDRATOR, "WCW", "MHM", "GAG", 'C', CIRCUIT, 'M', CABLE_QUAD, 'H', HULL,
                'G', MOTOR, 'A', ROBOT_ARM, 'W', COIL_HEATING_DOUBLE);

        // Laminator
        MetaTileEntityLoader.registerMachineRecipe(true, LAMINATOR,
                "XGW", "PCS", "WHX",
                'H', CraftingComponent.HULL,
                'X', CraftingComponent.CIRCUIT,
                'W', CraftingComponent.CABLE,
                'P', CraftingComponent.PUMP,
                'C', CraftingComponent.CONVEYOR,
                'S', PISTON,
                'G', OreDictUnifier.get(gear, Steel));

        registerMachineRecipe(GTQTMetaTileEntities.RADIATION_HATCH,
                "SCS", "EHE", "SCS",
                'C', CIRCUIT,
                'H', HULL,
                'S', OreDictUnifier.get(plateDense, Lead),
                'E', OreDictUnifier.get(screw, TungstenSteel));

        registerMachineRecipe(GTQTMetaTileEntities.ELECTRODE_HATCH,
                "SCS", "EHE", "SCS",
                'C', CIRCUIT,
                'H', HULL,
                'S', OreDictUnifier.get(stick, Steel),
                'E', OreDictUnifier.get(screw, Copper));

        registerMachineRecipe(GTQTMetaTileEntities.DRILL_HEAD_HATCH,
                "SCS", "EHE", "SCS",
                'C', CIRCUIT,
                'H', HULL,
                'S', OreDictUnifier.get(rotor, Aluminium),
                'E', OreDictUnifier.get(screw, Invar));
    }

}
