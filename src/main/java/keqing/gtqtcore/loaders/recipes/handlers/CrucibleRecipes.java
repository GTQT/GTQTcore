package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.unification.material.Material;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockCrucible;
import net.minecraft.item.ItemStack;

import static gregicality.multiblocks.api.unification.GCYMMaterials.Zeron100;
import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.plate;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.HexagonalBoronNitride;
import static keqing.gtqtcore.api.unification.GTQTMaterials.IncoloyMA813;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Inconel792;
import static keqing.gtqtcore.api.utils.GTQTUniverUtil.MINUTE;

public class CrucibleRecipes {

    public static void register() {
        // Bronze Crucible, 1696K, ULV
        addCrucibleRecipe(0, BlockCrucible.CrucibleType.BRONZE_CRUCIBLE,
                Bronze, Tin);
        // Invar Crucible, 2395K, LV
        addCrucibleRecipe(1, BlockCrucible.CrucibleType.INVAR_CRUCIBLE,
                Invar, TinAlloy);
        // Quartzite Crucible, 2482K, MV
        addCrucibleRecipe(2, BlockCrucible.CrucibleType.QUARTZ_CRUCIBLE,
                Quartzite, SolderingAlloy);
        // Chrome Crucible, 2725K, HV
        addCrucibleRecipe(3, BlockCrucible.CrucibleType.CHROME_CRUCIBLE,
                Chrome, CobaltBrass);
        // Vanadium Crucible, 2728K, EV
        addCrucibleRecipe(4, BlockCrucible.CrucibleType.VANADIUM_CRUCIBLE,
                Vanadium, BlackSteel);
        // Niobium Titanium Crucible, 2931K, EV
        addCrucibleRecipe(4, BlockCrucible.CrucibleType.NIOBIUM_TITANIUM_CRUCIBLE,
                NiobiumTitanium, BlackSteel);
        // Iridium Crucible, 3398K, IV
        addCrucibleRecipe(5, BlockCrucible.CrucibleType.IRIDIUM_CRUCIBLE,
                Iridium, BlueSteel);
        // Molybdenum Crucible, 3620K, IV
        addCrucibleRecipe(5, BlockCrucible.CrucibleType.MOLYBDENUM_CRUCIBLE,
                Molybdenum, BlueSteel);
        // Tungsten Crucible, 3695K, LuV
        addCrucibleRecipe(6, BlockCrucible.CrucibleType.TUNGSTEN_CRUCIBLE,
                Tungsten, Inconel792);
        // Osmium Crucible, 4132K, LuV
        addCrucibleRecipe(6, BlockCrucible.CrucibleType.OSMIUM_CRUCIBLE,
                Osmium, IncoloyMA813);
        // Graphite Crucible, 4750K, ZPM
        addCrucibleRecipe(7, BlockCrucible.CrucibleType.GRAPHITE_CRUCIBLE,
                Graphene, IncoloyMA813);
        // Boron Nitride Crucible, 5328K, UV
        addCrucibleRecipe(8, BlockCrucible.CrucibleType.BORON_NITRIDE_CRUCIBLE,
                HexagonalBoronNitride, Zeron100);

    }

    public static void addCrucibleRecipe(int tier,
                                         BlockCrucible.CrucibleType outputType,
                                         Material material,
                                         Material catalyst) {
        ItemStack crucible = GTQTMetaBlocks.blockCrucible.getItemVariant(outputType);

        VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .circuitMeta(5)
                .input(plate, material, 5)
                .fluidInputs(catalyst.getFluid(L * 4))
                .outputs(crucible)
                .EUt(VA[tier])
                .duration(MINUTE)
                .buildAndRegister();

    }

}
