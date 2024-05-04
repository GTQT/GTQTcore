package keqing.gtqtcore.loaders.recipes.handlers;

import com.google.common.base.CaseFormat;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Materials;
import gregtech.common.ConfigHolder;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import team.chisel.api.carving.CarvingUtils;

import java.util.Arrays;
import java.util.List;

import static gregtech.api.GTValues.ULV;
import static gregtech.api.GTValues.VH;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.AUTO_CHISEL_RECIPES;
import static keqing.gtqtcore.api.utils.GTQTUtil.*;


public class AutoChiselRecipeLoader {

    public static void init() {
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack(Blocks.PLANKS, 6, 0), new ItemStack(Items.BOOK, 3));

        String[] bookshelf = new String[]{"oak", "spruce", "birch", "jungle", "acacia", "darkoak"};
        for (int i = 0; i < bookshelf.length; i++) {
            addGroup("bookshelf" + bookshelf[i].toUpperCase());
            ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(new ItemStack(Blocks.PLANKS, 6, i))
                    .inputs(new ItemStack(Items.BOOK, 3))
                    .outputs(getItemById("chisel", "bookshelf_" + bookshelf[i]))
                    .EUt(VH[ULV])
                    .duration(100)
                    .buildAndRegister();
            registerChiselRecipe("bookshelf" + bookshelf[i].toUpperCase());
        }

        // Material Blocks
        if (ConfigHolder.recipes.disableManualCompression) {
            Arrays.asList("charcoal_uncraft", "diamond", "emerald", "redstone", "coal", "uncraft_blocksilver",
                    "uncraft_blocklead", "uncraft_blocktin", "uncraft_blocksteel", "uncraft_blockplatinum",
                    "uncraft_blockiron", "uncraft_blockaluminium", "uncraft_blockcobalt", "uncraft_blocknickel",
                    "uncraft_blockelectrum", "uncraft_blockuranium", "uncraft_blockcopper", "uncraft_blockbronze",
                    "uncraft_blockinvar", "uncraft_blockgold").forEach(
                    block -> ModHandler.removeRecipeByName(getId("chisel", block)));
        }

        // Material Blocks
        List<ItemStack> aluminums = OreDictionary.getOres("blockAluminum");
        aluminums.forEach(alumimun -> OreDictionary.registerOre("blockAluminium", alumimun));
        Arrays.asList("blockAluminium", "blockBronze", "blockCharcoal", "blockCoal", "blockFuelCoke", "blockCobalt",
                "blockCopper", "blockDiamond", "blockElectrum", "blockEmerald", "blockGold", "blockInvar", "blockIron",
                "blockLapis", "blockLead", "blockNickel", "blockPlatinum", "blockSilver", "blockSteel", "blockTin",
                "blockUranium").forEach(AutoChiselRecipeLoader::registerChiselRecipe);


        // Glass Panes
        if (ConfigHolder.recipes.hardGlassRecipes) {
            Arrays.asList("glass/terrain-glassbubble", "glass/terrain-glassnoborder", "glass/terrain-glassshale",
                    "glass/terrain-glass-thingrid", "glass/chinese", "glass/japanese", "glass/terrain-glassdungeon",
                    "glass/terrain-glasslight", "glass/terrain-glass-ornatesteel", "glass/terrain-glass-screen",
                    "glass/terrain-glass-steelframe", "glass/terrain-glassstone", "glass/terrain-glassstreak",
                    "glass/terrain-glass-thickgrid", "glass/a1-glasswindow-ironfencemodern", "glass/chrono",
                    "glass/chinese2", "glass/japanese2").forEach(
                    block -> ModHandler.removeRecipeByName(getId("chisel", block)));
        }

        //  Lamps
        int i = 0;
        while (i < Materials.CHEMICAL_DYES.length) {
            EnumDyeColor dyeColor = EnumDyeColor.values()[i];
            String colorName = dyeColor.toString().equals("silver") ? "LightGray" : CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, dyeColor.getName());

            registerChiselRecipe("lamp" + colorName);
            registerChiselRecipe("lampBorderless" + colorName);
            i++;
        }

        // Andesite
        registerChiselRecipe("stoneAndesite");


        // Antiblock
        for (int j = 0; j < Materials.CHEMICAL_DYES.length; j++) {
            OreDictionary.registerOre("blockAntiblock", getMetaItemById("chisel", "antiblock", j, 1));
        }
        registerChiselRecipe("blockAntiblock");

        // Basalt
        registerChiselRecipe("stoneBasalt");

        // Brick
        for (int k = 0; k < 16; k++) {
            OreDictionary.registerOre("blockBrick", getMetaItemById("chisel", "bricks", k, 1));
        }
        for (int l = 0; l < 10; l++) {
            OreDictionary.registerOre("blockBrick", getMetaItemById("chisel", "bricks1", l, 1));
        }
        for (int m = 0; m < 6; m++) {
            OreDictionary.registerOre("blockBrick",getMetaItemById("chisel", "bricks2", m, 1));
        }
        registerChiselRecipe("blockBrick");

        // Brownstone
        for (int n = 0; n < 10; n++) {
            OreDictionary.registerOre("blockBrownstone", getMetaItemById("chisel", "brownstone", n, 1));
        }
        registerChiselRecipe("blockBrownstone");

        // Colored Blocks
        for (int o = 0; o < Materials.CHEMICAL_DYES.length; o++) {
            EnumDyeColor dyeColor = EnumDyeColor.values()[o];
            String upperColorName = dyeColor.toString().equals("silver") ? "LightGray" : CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, dyeColor.getName());
            String lowerColorName = dyeColor.toString().equals("silver") ? "lightgray" : dyeColor.toString().toLowerCase();

            // Carpet
            OreDictionary.registerOre("carpet" + upperColorName, new ItemStack(Blocks.CARPET, 1, o));
            OreDictionary.registerOre("carpet" + upperColorName, getItemById("chisel", "carpet_" + lowerColorName));
            OreDictionary.registerOre("carpet" + upperColorName, getMetaItemById("chisel", "carpet_" + lowerColorName, 1, 1));
            registerChiselRecipe("carpet" + upperColorName);

            // Concrete
            OreDictionary.registerOre("blockConcrete" + upperColorName, new ItemStack(Blocks.CONCRETE, 1, o));
            registerChiselRecipe("blockConcrete" + lowerColorName);

            // Stained Glass
            OreDictionary.registerOre("blockChisellableGlass" + upperColorName, new ItemStack(Blocks.STAINED_GLASS, 1, o));

            for (int p = 0; p < 6; p++) {
                OreDictionary.registerOre("blockChisellableGlass" + upperColorName, getMetaItemById("chisel", "glassdyed" + lowerColorName, p, 1));
            }

            // Colorless Glass
            registerChiselRecipe("blockChisellableGlass" + upperColorName);
            OreDictionary.registerOre("blockChisellableGlassColorless", new ItemStack(Blocks.GLASS));
            for (int q = 0; q < 15; q++) {
                OreDictionary.registerOre("blockChisellableGlassColorless", getMetaItemById("chisel", "glass", q, 1));
            }

            OreDictionary.registerOre("blockChisellableGlassColorless", getItemById("chisel", "glass1"));
            OreDictionary.registerOre("blockChisellableGlassColorless", getMetaItemById("chisel", "glass1", 1, 1));
            registerChiselRecipe("blockChisellableGlassColorless");

            // Glass Pane
            registerChiselRecipe("paneGlass" + upperColorName);
            registerChiselRecipe("paneGlassColorless");

            // Wool
            OreDictionary.registerOre("blockWool" + upperColorName, getItemById("chisel", "wool_" + lowerColorName));
            OreDictionary.registerOre("blockWool" + upperColorName, getMetaItemById("chisel", "wool_" + lowerColorName, 1, 1));
            registerChiselRecipe("blockWool" + upperColorName);
        }

        // Certus Quartz
        for (int r = 0; r < 16; r++) {
            OreDictionary.registerOre("blockCertus", getMetaItemById("chisel", "certus", r, 1));
            OreDictionary.registerOre("blockCertus", getMetaItemById("chisel", "certus1", r, 1));
        }

        OreDictionary.registerOre("blockCertus", getItemById("chisel", "certus2"));
        OreDictionary.registerOre("blockCertus", getMetaItemById("chisel", "certus2", 1, 1));
        registerChiselRecipe("blockCertus");

        // Cloud
        for (int s = 0; s < 5; s++) {
            OreDictionary.registerOre("blockCloud", getMetaItemById("chisel", "cloud", s, 1));
        }
        registerChiselRecipe("blockCloud");

        // Cobblestone
        registerChiselRecipe("cobblestone");

        // Moss Stone
        OreDictionary.registerOre("blockMossy", new ItemStack(Blocks.MOSSY_COBBLESTONE));
        registerChiselRecipe("blockMossy");

        // Diorite
        registerChiselRecipe("stoneDiorite");

        // Dirt
        registerChiselRecipe("dirt");

        // Endstone
        OreDictionary.registerOre("endstone", new ItemStack(Blocks.END_BRICKS));
        registerChiselRecipe("endstone");

        // Factory Block
        for (int t = 0; t < 16; t++) {
            OreDictionary.registerOre("blockFactory", getMetaItemById("chisel", "factory", t, 1));
            OreDictionary.registerOre("blockFactory", getMetaItemById("chisel", "technical", t, 1));
        }
        for (int u = 0; u < 5; u++) {
            OreDictionary.registerOre("blockFactory", getMetaItemById("chisel", "factory1", u, 1));
            OreDictionary.registerOre("blockFactory", getMetaItemById("chisel", "technical1", u, 1));
        }
        for (int v = 0; v < 9; v++) {
            OreDictionary.registerOre("blockFactory", getMetaItemById("chisel", "technicalnew", v, 1));
        }
        registerChiselRecipe("blockFactory");

        // Futura Block
        for (int w = 0; w < 6; w++) {
            OreDictionary.registerOre("blockFutura", getMetaItemById("chisel", "futura", w, 1));
        }
        registerChiselRecipe("blockFutura");

        // Glowstone
        registerChiselRecipe("glowstone");

        // Granite
        registerChiselRecipe("stoneGranite");

        // Terracotta
        OreDictionary.registerOre("hardenedClay", new ItemStack(Blocks.HARDENED_CLAY));
        registerChiselRecipe("hardenedClay");

        // Ice
        registerChiselRecipe("blockIce");

        // Iron Bars
        for (int x = 0; x < 13; x++) {
            OreDictionary.registerOre("barsIron", getMetaItemById("chisel", "ironpane", x, 1));
        }
        registerChiselRecipe("barsIron");

        // Laboratory Block
        for (int y = 0; y < 16; y++) {
            OreDictionary.registerOre("blockLaboratory", getMetaItemById("chisel", "laboratory", y, 1));
        }
        registerChiselRecipe("blockLaboratory");

        // Lavastone
        for (int z = 0; z < 16; z++) {
            OreDictionary.registerOre("blockLavastone", getMetaItemById("chisel", "lavastone", z, 1));
            OreDictionary.registerOre("blockLavastone", getMetaItemById("chisel", "lavastone1", z, 1));
        }

        OreDictionary.registerOre("blockLavastone", getItemById("chisel", "lavastone2"));
        registerChiselRecipe("blockLavastone");

        // Limestone
        registerChiselRecipe("stoneLimestone");

        // Marble
        registerChiselRecipe("stoneMarble");

        // Nether Brick
        OreDictionary.registerOre("brickNether", new ItemStack(Blocks.NETHER_BRICK));
        for (int a = 0; a < 16; a++) {
            OreDictionary.registerOre("brickNether", getMetaItemById("chisel", "netherbrick", a, 1));
        }
        registerChiselRecipe("brickNether");

        // Netherrack
        registerChiselRecipe("netherrack");

        // Obsidian
        registerChiselRecipe("obsidian");

        // Paper Wall
        for (int b = 0; b < 9; b++) {
            OreDictionary.registerOre("blockPaperWall", getMetaItemById("chisel", "paper", b, 1));
        }
        registerChiselRecipe("blockPaperWall");

        // Planks
        for (int c = 0; c < 15; c++) {
            OreDictionary.registerOre("plankWoodOak",
                    getMetaItemById("chisel", "planks-oak", c, 1));
            OreDictionary.registerOre("plankWoodSpruce",
                    getMetaItemById("chisel", "planks-spruce", c, 1));
            OreDictionary.registerOre("plankWoodBirch",
                    getMetaItemById("chisel", "planks-birch", c, 1));
            OreDictionary.registerOre("plankWoodJungle",
                    getMetaItemById("chisel", "planks-jungle", c, 1));
            OreDictionary.registerOre("plankWoodAcacia",
                    getMetaItemById("chisel", "planks-acacia", c, 1));
            OreDictionary.registerOre("plankWoodDarkOak",
                    getMetaItemById("chisel", "planks-dark-oak", c, 1));
        }
        OreDictionary.registerOre("plankWoodOak", new ItemStack(Blocks.PLANKS, 1));
        registerChiselRecipe("plankWoodOak");

        OreDictionary.registerOre("plankWoodSpruce", new ItemStack(Blocks.PLANKS, 1, 1));
        registerChiselRecipe("plankWoodSpruce");

        OreDictionary.registerOre("plankWoodBirch", new ItemStack(Blocks.PLANKS, 1, 2));
        registerChiselRecipe("plankWoodBirch");

        OreDictionary.registerOre("plankWoodJungle", new ItemStack(Blocks.PLANKS, 1, 3));
        registerChiselRecipe("plankWoodJungle");

        OreDictionary.registerOre("plankWoodAcacia", new ItemStack(Blocks.PLANKS, 1, 4));
        registerChiselRecipe("plankWoodAcacia");

        OreDictionary.registerOre("plankWoodDarkOak", new ItemStack(Blocks.PLANKS, 1, 5));
        registerChiselRecipe("plankWoodDarkOak");

        // Prismarine
        OreDictionary.registerOre("prismarineBrick", new ItemStack(Blocks.PRISMARINE, 1, 1));
        registerChiselRecipe("prismarineBrick");

        // Purpur
        OreDictionary.registerOre("blockPurpur", new ItemStack(Blocks.PURPUR_BLOCK));
        OreDictionary.registerOre("blockPurpur", new ItemStack(Blocks.PURPUR_PILLAR));

        for (int d = 0; d < 16; d++) {
            OreDictionary.registerOre("blockPurpur", getMetaItemById("chisel", "purpur", d, 1));
        }
        for (int e = 0; e < 10; e++) {
            OreDictionary.registerOre("blockPurpur", getMetaItemById("chisel", "purpur1", e, 1));
        }
        for (int f = 0; f < 5; f++) {
            OreDictionary.registerOre("blockPurpur", getMetaItemById("chisel", "purpur2", f, 1));
        }
        registerChiselRecipe("blockPurpur");

        // Quartz Block
        OreDictionary.registerOre("blockQuartz", new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1));
        OreDictionary.registerOre("blockQuartz", new ItemStack(Blocks.QUARTZ_BLOCK, 1, 2));
        registerChiselRecipe("blockQuartz");

        // Redstone Block
        registerChiselRecipe("blockRedstone");

        // Red Sandstone
        for (int g = 0; g < 2; g++) {
            OreDictionary.registerOre("sandstoneRed", new ItemStack(Blocks.RED_SANDSTONE, 1, g));
        }
        for (int h = 0; h < 16; h++) {
            OreDictionary.registerOre("sandstoneRed", getMetaItemById("chisel", "sandstonered", h, 1));
            OreDictionary.registerOre("sandstoneRed", getMetaItemById("chisel", "sandstonered-scribbles", h, 1));
        }
        for (int ii = 0; ii < 10; ii++) {
            OreDictionary.registerOre("sandstoneRed", getMetaItemById("chisel", "sandstonered1", ii, 1));
        }
        for (int jj = 0; jj < 8; jj++) {
            OreDictionary.registerOre("sandstoneRed", getMetaItemById("chisel", "sandstonered2", jj, 1));
        }
        registerChiselRecipe("sandstoneRed");

        // Sandstone
        for (int kk = 0; kk < 2; kk++) {
            OreDictionary.registerOre("sandstoneYellow", new ItemStack(Blocks.SANDSTONE, 1, kk));
        }
        for (int ll = 0; ll < 16; ll++) {
            OreDictionary.registerOre("sandstoneYellow", getMetaItemById("chisel", "sandstoneyellow", ll, 1));
            OreDictionary.registerOre("sandstoneYellow", getMetaItemById("chisel", "sandstone-scribbles", ll, 1));
        }
        for (int mm = 0; mm < 10; mm++) {
            OreDictionary.registerOre("sandstoneYellow", getMetaItemById("chisel", "sandstoneyellow1", mm, 1));
        }
        for (int nn = 0; nn < 8; nn++) {
            OreDictionary.registerOre("sandstoneYellow", getMetaItemById("chisel", "sandstoneyellow2", nn, 1));
        }
        registerChiselRecipe("sandstoneYellow");

        // Stone
        for (int oo = 0; oo < 4; oo++) {
            OreDictionary.registerOre("stone", new ItemStack(Blocks.STONEBRICK, 1, oo));
        }
        registerChiselRecipe("stone");

        // Temple Block
        for (int pp = 0; pp < 16; pp++) {
            OreDictionary.registerOre("blockTemple", getMetaItemById("chisel", "temple", pp, 1));
            OreDictionary.registerOre("blockTemple", getMetaItemById("chisel", "templemossy", pp, 1));
        }
        registerChiselRecipe("blockTemple");

        // Tyrian
        for (int qq = 0; qq < 15; qq++) {
            OreDictionary.registerOre("blockTyrian", getMetaItemById("chisel", "tyrian", qq, 1));
        }
        registerChiselRecipe("blockTyrian");

        // Valentines' Block
        for (int rr = 0; rr < 10; rr++) {
            OreDictionary.registerOre("blockValentine", getMetaItemById("chisel", "valentines", rr, 1));
        }
        registerChiselRecipe("blockValentine");

        // Voidstone
        for (int ss = 0; ss < 8; ss++) {
            OreDictionary.registerOre("blockVoidstone", getMetaItemById("chisel", "voidstone", ss, 1));
            OreDictionary.registerOre("blockVoidstone", getMetaItemById("chisel", "energizedvoidstone", ss, 1));
        }
        for (int tt = 0; tt < 15; tt++) {
            OreDictionary.registerOre("blockVoidstone", getMetaItemById("chisel", "voidstonerunic", tt, 1));
        }
        registerChiselRecipe("blockVoidstone");

        // Waterstone
        for (int uu = 0; uu < 16; uu++) {
            OreDictionary.registerOre("blockWaterstone", getMetaItemById("chisel", "waterstone", uu, 1));
            OreDictionary.registerOre("blockWaterstone", getMetaItemById("chisel", "waterstone1", uu, 1));
        }
        OreDictionary.registerOre("blockWaterstone", getItemById("chisel", "waterstone"));
        registerChiselRecipe("blockWaterstone");
    }

    private static void registerChiselRecipe(String oreDict) {
        List<ItemStack> targets = OreDictionary.getOres(oreDict);
        targets.forEach(target -> AUTO_CHISEL_RECIPES.recipeBuilder()
                .input(oreDict)
                .notConsumable(target)
                .outputs(target)
                .EUt(VH[ULV])
                .duration(10)
                .buildAndRegister());
    }

    public static void addGroup(String groupName) {
        CarvingUtils.getChiselRegistry().addGroup(CarvingUtils.getDefaultGroupFor(groupName));
    }

    public static void addVariation(String groupName, ItemStack stack) {
        CarvingUtils.getChiselRegistry().addVariation(groupName, CarvingUtils.variationFor(stack, stack.getItemDamage()));
        OreDictionary.registerOre(groupName, stack);
    }

    public static void removeVariation(ItemStack stack, String groupName) {
        CarvingUtils.getChiselRegistry().removeVariation(stack, groupName);
    }
}

