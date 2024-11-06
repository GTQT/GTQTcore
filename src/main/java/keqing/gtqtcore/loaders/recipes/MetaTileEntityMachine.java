package keqing.gtqtcore.loaders.recipes;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.BlockSteamCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.core.unification.material.internal.MaterialRegistryManager;
import gregtech.loaders.recipe.CraftingComponent;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTADVGlass;
import keqing.gtqtcore.common.block.blocks.GTQTBlockGlassCasing;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import keqing.gtqtcore.loaders.recipes.chain.CrystalRaw;
import keqing.gtqtcore.loaders.recipes.chain.PEEKChain;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Collection;

import static gregicality.multiblocks.api.unification.GCYMMaterials.WatertightSteel;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.blocks.MetaBlocks.MACHINE_CASING;
import static gregtech.common.items.MetaItems.SHAPE_MOLD_BLOCK;
import static gregtech.common.metatileentities.MetaTileEntities.ARC_FURNACE;
import static gregtech.loaders.recipe.CraftingComponent.*;
import static gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.Polyetheretherketone;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.plate_curved;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing.TurbineCasingType.FISHING_CASING;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing1.TurbineCasingType.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.STEAM_VACUUM_CHAMBER;

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
    }

    private static void dust() {
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Titanium, 6)
                .input(dust, Aluminium, 4)
                .input(dust, Vanadium)
                .circuitMeta(11)
                .output(dust, Tcfour, 11)
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

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Steel)
                .input(dust, Coal)
                .input(dust, Silicon)
                .circuitMeta(3)
                .output(dust, ElectricalSteel, 3)
                .duration(800).EUt(VA[HV]).buildAndRegister();

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
                GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(AL_TURBINE_CASING, ConfigHolder.recipes.casingsPerCraft),
                "PIP", "IFI", "PIP", 'P', new UnificationEntry(OrePrefix.plate, Aluminium), 'F',
                new UnificationEntry(OrePrefix.frameGt, Materials.Aluminium), 'I',
                new UnificationEntry(OrePrefix.pipeNormalFluid, Materials.Aluminium));

        ModHandler.addShapedRecipe(true, "casing_stainless_pipe",
                GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(SA_TURBINE_CASING, ConfigHolder.recipes.casingsPerCraft), "PIP", "IFI",
                "PIP", 'P', new UnificationEntry(OrePrefix.plate, StainlessSteel), 'F',
                new UnificationEntry(OrePrefix.frameGt, Materials.StainlessSteel), 'I',
                new UnificationEntry(OrePrefix.pipeNormalFluid, Materials.StainlessSteel));

        ModHandler.addShapedRecipe(true, "casing_pd_pipe",
                GTQTMetaBlocks.TURBINE_CASING.getItemVariant(GTQTTurbineCasing.TurbineCasingType.PD_MACHINE_CASING, ConfigHolder.recipes.casingsPerCraft), "PIP", "IFI",
                "PIP", 'P', new UnificationEntry(OrePrefix.plate, RhodiumPlatedPalladium), 'F',
                new UnificationEntry(OrePrefix.frameGt, Materials.RhodiumPlatedPalladium), 'I',
                new UnificationEntry(OrePrefix.pipeNormalFluid, NiobiumTitanium));

        ModHandler.addShapedRecipe(true, "brick",
                GTQTMetaBlocks.TURBINE_CASING.getItemVariant(GTQTTurbineCasing.TurbineCasingType.BRICK),
                "PIP", "IFI", "PIP",
                'P', new UnificationEntry(OrePrefix.plate, Wood),
                'F', new UnificationEntry(OrePrefix.frameGt, Materials.Wood),
                'I', Blocks.BRICK_BLOCK);


        ModHandler.addShapedRecipe(true, "casing_nq_pipe",
                GTQTMetaBlocks.TURBINE_CASING.getItemVariant(GTQTTurbineCasing.TurbineCasingType.NQ_MACHINE_CASING, ConfigHolder.recipes.casingsPerCraft), "PIP", "IFI",
                "PIP", 'P', new UnificationEntry(OrePrefix.plate, NaquadahAlloy), 'F',
                new UnificationEntry(OrePrefix.frameGt, Materials.NaquadahAlloy), 'I',
                new UnificationEntry(OrePrefix.pipeNormalFluid, Naquadah));

        ModHandler.addShapedRecipe(true, "casing_st_pipe",
                GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(ST_MACHINE_CASING, ConfigHolder.recipes.casingsPerCraft), "PIP", "IFI",
                "PIP", 'P', new UnificationEntry(OrePrefix.plate, Orichalcum), 'F',
                new UnificationEntry(OrePrefix.frameGt, Orichalcum), 'I',
                new UnificationEntry(OrePrefix.pipeNormalFluid, Orichalcum));

        ModHandler.addShapedRecipe(true, "casing_ad_pipe",
                GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(AD_MACHINE_CASING, ConfigHolder.recipes.casingsPerCraft), "PIP", "IFI",
                "PIP", 'P', new UnificationEntry(OrePrefix.plate, Adamantite), 'F',
                new UnificationEntry(OrePrefix.frameGt, Adamantite), 'I',
                new UnificationEntry(OrePrefix.pipeNormalFluid, Adamantite));

        ModHandler.addShapedRecipe(true, "fishing_casing",
                GTQTMetaBlocks.TURBINE_CASING.getItemVariant(FISHING_CASING, ConfigHolder.recipes.casingsPerCraft), "PIP", "IFI",
                "PIP", 'P', new UnificationEntry(OrePrefix.plate, Inconel792), 'F',
                new UnificationEntry(OrePrefix.frameGt, WatertightSteel), 'I',
                new UnificationEntry(OrePrefix.pipeNormalFluid, StainlessSteel));
    }

    private static void machinecasing() {
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(30)
                .input(plate, GalvanizedSteel, 6)
                .input(frameGt, GalvanizedSteel, 1)
                .circuitMeta(6)
                .outputs(GTQTMetaBlocks.TURBINE_CASING1.getItemVariant(GALVANIZE_STEEL_CASING))
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

        //ulv
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(25).EUt(2)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ULV))
                .input(plate, Materials.RedAlloy, 2)
                .input(OrePrefix.cableGtSingle, Materials.Lead, 2)
                .outputs(MetaTileEntities.HULL[0].getStackForm())
                .buildAndRegister();

        //lv
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(8)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LV))
                .input(plate, GalvanizedSteel, 2)
                .input(OrePrefix.cableGtSingle, Materials.Tin, 2)
                .fluidInputs(Rubber.getFluid(L * 2))
                .outputs(MetaTileEntities.HULL[1].getStackForm()).buildAndRegister();

        //mv
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(30)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.MV))
                .input(plate, Invar, 2)
                .input(OrePrefix.cableGtSingle, Materials.Copper, 2)
                .fluidInputs(Materials.Polyethylene.getFluid(L * 2))
                .outputs(MetaTileEntities.HULL[2].getStackForm()).buildAndRegister();

        //hv
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(120)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.HV))
                .input(plate, Talonite, 2)
                .input(OrePrefix.cableGtSingle, Materials.Aluminium, 2)
                .fluidInputs(Materials.Epoxy.getFluid(L * 2))
                .outputs(MetaTileEntities.HULL[3].getStackForm()).buildAndRegister();

        //ev
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(480)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.EV))
                .input(plate, Palladium, 2)
                .input(OrePrefix.cableGtSingle, Gold, 2)
                .fluidInputs(Materials.Polytetrafluoroethylene.getFluid(L * 2)).outputs(MetaTileEntities.HULL[4].getStackForm())
                .buildAndRegister();

        //iv
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(1920)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.IV))
                .input(plate, Iridium, 2)
                .input(OrePrefix.cableGtSingle, Materials.Platinum, 2)
                .fluidInputs(Zylon.getFluid(L * 2)).outputs(MetaTileEntities.HULL[5].getStackForm())
                .buildAndRegister();

        //luv
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(7680)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.LuV))
                .input(plate, HSSS, 2)
                .input(OrePrefix.cableGtSingle, Materials.NiobiumTitanium, 2)
                .fluidInputs(Polybenzimidazole.getFluid(L * 2)).outputs(MetaTileEntities.HULL[6].getStackForm())
                .buildAndRegister();
        //zpm
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(30720)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ZPM))
                .input(plate, Duranium, 2)
                .input(OrePrefix.cableGtSingle, Materials.VanadiumGallium, 2)
                .fluidInputs(Kevlar.getFluid(L * 2)).outputs(MetaTileEntities.HULL[7].getStackForm())
                .buildAndRegister();
        //uv
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(12280)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.UV))
                .input(plate, Americium, 2)
                .input(cableGtSingle, Materials.YttriumBariumCuprate, 2)
                .fluidInputs(Polyetheretherketone.getFluid(L * 2))
                .outputs(MetaTileEntities.HULL[8].getStackForm()).buildAndRegister();
        //uhv
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(50).EUt(491520)
                .inputs(MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.UHV))
                .input(plate, Neutronium, 2)
                .input(cableGtSingle, Materials.Europium, 2)
                .fluidInputs(KaptonE.getFluid(L * 2))
                .outputs(MetaTileEntities.HULL[9].getStackForm()).buildAndRegister();
        //////////////////////////////////////玻璃
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder().duration(120).EUt(120)
                .fluidInputs(BorosilicateGlass.getFluid(L*4))
                .notConsumable(SHAPE_MOLD_BLOCK)
                .outputs(GTQTMetaBlocks.ADV_GLASS.getItemVariant(GTQTADVGlass.CasingType.SILICATE_GLASS))
                .buildAndRegister();

        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder().duration(480).EUt(120)
                .input(dust, BorosilicateGlass,4)
                .notConsumable(SHAPE_MOLD_BLOCK.getStackForm())
                .outputs(GTQTMetaBlocks.ADV_GLASS.getItemVariant(GTQTADVGlass.CasingType.SILICATE_GLASS))
                .buildAndRegister();

        //  Boron Silicate Glass
        OreDictUnifier.registerOre(GTQTMetaBlocks.ADV_GLASS.getItemVariant(GTQTADVGlass.CasingType.SILICATE_GLASS),
                new ItemMaterialInfo(new MaterialStack(BorosilicateGlass, M * 4)));

        //  Thorium-reinforced Glass
        GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.ADV_GLASS.getItemVariant(GTQTADVGlass.CasingType.SILICATE_GLASS))
                .input(plate, Thorium, 4)
                .outputs(GTQTMetaBlocks.ADV_GLASS.getItemVariant(GTQTADVGlass.CasingType.THY_SILICATE_GLASS))
                .EUt(VA[HV])
                .duration(500)
                .buildAndRegister();

        OreDictUnifier.registerOre(GTQTMetaBlocks.ADV_GLASS.getItemVariant(GTQTADVGlass.CasingType.THY_SILICATE_GLASS),
                new ItemMaterialInfo(new MaterialStack(BorosilicateGlass, M * 4),
                        new MaterialStack(Thorium, M * 4)));

        //  Titanium-reinforced Glass
        GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.ADV_GLASS.getItemVariant(GTQTADVGlass.CasingType.SILICATE_GLASS))
                .input(plate, Titanium, 4)
                .outputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.TI_BORON_SILICATE_GLASS))
                .EUt(VA[HV])
                .duration(500)
                .buildAndRegister();

        OreDictUnifier.registerOre(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.TI_BORON_SILICATE_GLASS),
                new ItemMaterialInfo(new MaterialStack(BorosilicateGlass, M * 4),
                        new MaterialStack(Titanium, M * 4)));

        //  Tungsten-reinforced Glass
        GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.ADV_GLASS.getItemVariant(GTQTADVGlass.CasingType.SILICATE_GLASS))
                .input(plate, Tungsten, 4)
                .outputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.W_BORON_SILICATE_GLASS))
                .EUt(VA[EV])
                .duration(500)
                .buildAndRegister();

        OreDictUnifier.registerOre(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.W_BORON_SILICATE_GLASS),
                new ItemMaterialInfo(new MaterialStack(BorosilicateGlass, M * 4),
                        new MaterialStack(Tungsten, M * 4)));


        //  Osmiridium-reinforced Glass
        GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.ADV_GLASS.getItemVariant(GTQTADVGlass.CasingType.SILICATE_GLASS))
                .input(plate, Osmiridium, 4)
                .outputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.OSMIR_BORON_SILICATE_GLASS))
                .EUt(VA[IV])
                .duration(500)
                .buildAndRegister();

        OreDictUnifier.registerOre(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.OSMIR_BORON_SILICATE_GLASS),
                new ItemMaterialInfo(new MaterialStack(BorosilicateGlass, M * 4),
                        new MaterialStack(Osmiridium, M * 4)));

        //  Naquadah-reinforced Glass
        GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.ADV_GLASS.getItemVariant(GTQTADVGlass.CasingType.SILICATE_GLASS))
                .input(plate, Naquadah, 4)
                .outputs(GTQTMetaBlocks.GLASS_CASING.getItemVariant(GTQTBlockGlassCasing.CasingType.NAQ_BORON_SILICATE_GLASS))
                .EUt(VA[LuV])
                .duration(500)
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
                'G', new UnificationEntry(GTQTOrePrefix.electrode, Materials.Graphite));


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

        registerMachineRecipe(GTQTMetaTileEntities.FLUID_EXTRACTOR, "PGP", "EGE", "CMC", 'M', HULL, 'P', PUMP, 'E', PISTON, 'C',
                CIRCUIT, 'G', GLASS);
        registerMachineRecipe(GTQTMetaTileEntities.FLUID_CANNER, "EGE", "PGP", "CMC", 'M', HULL, 'P', PUMP, 'E', PISTON, 'C',
                CIRCUIT, 'G', GLASS);
        registerMachineRecipe(GTQTMetaTileEntities.DEHYDRATOR, "WCW", "MHM", "GAG", 'C', CIRCUIT, 'M', CABLE_QUAD, 'H', HULL,
                'G', MOTOR, 'A', ROBOT_ARM, 'W', COIL_HEATING_DOUBLE);
    }

}
