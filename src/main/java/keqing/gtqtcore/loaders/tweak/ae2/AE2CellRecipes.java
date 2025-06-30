package keqing.gtqtcore.loaders.tweak.ae2;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gtqt.api.util.MaterialHelper.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Fluix;
import static keqing.gtqtcore.loaders.tweak.ae2.index.*;
import static keqing.gtqtcore.loaders.tweak.ae2.index.securityStation;

public class AE2CellRecipes {
    public static void init() {
        //物品盘 流体盘
        //删除所有配方
        deleteRecipes();
        //基础盘符注册
        baseRecipes();
        //盘符注册
        cellRecipes();
    }
    private static void cellRecipes(){
        cellRecipes(cell1k, storage1k);
        cellRecipes(cell4k, storage4k);
        cellRecipes(cell16k, storage16k);
        cellRecipes(cell64k, storage64k);

        cellRecipes(fluidCell1k, fluidStorage1k);
        cellRecipes(fluidCell4k, fluidStorage4k);
        cellRecipes(fluidCell16k, fluidStorage16k);
        cellRecipes(fluidCell64k, fluidStorage64k);

        //物品
        //1k
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.ULV, 2)
                .input(dust,Fluix,2)
                .inputs(logicProcessor)
                .input(MetaItems.BASIC_CIRCUIT_BOARD)
                .circuitMeta(1)
                .outputs(cell1k)
                .EUt(VA[LV])
                .duration(200)
                .buildAndRegister();

        //4k
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, cell1k))
                .input(circuit, MarkerMaterials.Tier.ULV, 16)
                .inputs(engineeringProcessor)
                .input(MetaItems.GOOD_CIRCUIT_BOARD)
                .circuitMeta(1)
                .outputs(cell4k)
                .EUt(VA[MV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.LV, 4)
                .input(circuit, MarkerMaterials.Tier.ULV, 16)
                .inputs(engineeringProcessor)
                .input(MetaItems.GOOD_CIRCUIT_BOARD)
                .circuitMeta(1)
                .outputs(cell4k)
                .EUt(VA[MV])
                .duration(200)
                .buildAndRegister();

        //16k
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, cell4k))
                .input(circuit, MarkerMaterials.Tier.LV, 16)
                .inputs(engineeringProcessor)
                .input(MetaItems.PLASTIC_CIRCUIT_BOARD)
                .circuitMeta(1)
                .outputs(cell16k)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.MV, 4)
                .input(circuit, MarkerMaterials.Tier.LV, 16)
                .inputs(engineeringProcessor)
                .input(MetaItems.PLASTIC_CIRCUIT_BOARD)
                .circuitMeta(1)
                .outputs(cell16k)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        //64k
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, cell16k))
                .input(circuit, MarkerMaterials.Tier.MV, 16)
                .inputs(engineeringProcessor)
                .input(MetaItems.ADVANCED_CIRCUIT_BOARD)
                .circuitMeta(1)
                .outputs(cell64k)
                .EUt(VA[EV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.HV, 4)
                .input(circuit, MarkerMaterials.Tier.MV, 16)
                .inputs(engineeringProcessor)
                .input(MetaItems.ADVANCED_CIRCUIT_BOARD)
                .circuitMeta(1)
                .outputs(cell64k)
                .EUt(VA[EV])
                .duration(200)
                .buildAndRegister();


        //流体
        //1k
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.ULV, 2)
                .input(dust,Fluix,2)
                .inputs(logicProcessor)
                .input(MetaItems.BASIC_CIRCUIT_BOARD)
                .circuitMeta(2)
                .outputs(fluidCell1k)
                .EUt(VA[LV])
                .duration(200)
                .buildAndRegister();

        //4k
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, fluidCell1k))
                .input(circuit, MarkerMaterials.Tier.ULV, 16)
                .inputs(engineeringProcessor)
                .input(MetaItems.GOOD_CIRCUIT_BOARD)
                .circuitMeta(2)
                .outputs(fluidCell4k)
                .EUt(VA[MV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.LV, 4)
                .input(circuit, MarkerMaterials.Tier.ULV, 16)
                .inputs(engineeringProcessor)
                .input(MetaItems.GOOD_CIRCUIT_BOARD)
                .circuitMeta(2)
                .outputs(fluidCell4k)
                .EUt(VA[MV])
                .duration(200)
                .buildAndRegister();

        //16k
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, fluidCell4k))
                .input(circuit, MarkerMaterials.Tier.LV, 16)
                .inputs(engineeringProcessor)
                .input(MetaItems.PLASTIC_CIRCUIT_BOARD)
                .circuitMeta(2)
                .outputs(fluidCell16k)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.MV, 4)
                .input(circuit, MarkerMaterials.Tier.LV, 16)
                .inputs(engineeringProcessor)
                .input(MetaItems.PLASTIC_CIRCUIT_BOARD)
                .circuitMeta(2)
                .outputs(fluidCell16k)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        //64k
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, fluidCell16k))
                .input(circuit, MarkerMaterials.Tier.MV, 16)
                .inputs(engineeringProcessor)
                .input(MetaItems.ADVANCED_CIRCUIT_BOARD)
                .circuitMeta(2)
                .outputs(fluidCell64k)
                .EUt(VA[EV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.HV, 4)
                .input(circuit, MarkerMaterials.Tier.MV, 16)
                .inputs(engineeringProcessor)
                .input(MetaItems.ADVANCED_CIRCUIT_BOARD)
                .circuitMeta(2)
                .outputs(fluidCell64k)
                .EUt(VA[EV])
                .duration(200)
                .buildAndRegister();
    }
    private static void cellRecipes(ItemStack  cell, ItemStack  plate) {
        //cell+外壳=盘符
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(cell)
                .inputs(meStorageCell)
                .outputs(plate)
                .EUt(VA[LV])
                .duration(20)
                .buildAndRegister();

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(plate)
                .outputs(cell)
                .outputs(meStorageCell)
                .EUt(VA[LV])
                .duration(20)
                .buildAndRegister();
    }

    private static void baseRecipes() {
        //石英玻璃
        ModHandler.removeRecipeByName("appliedenergistics2:decorative/quartz_glass");
        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Materials.NetherQuartz, 16)
                .input(Blocks.GLASS,4)
                .outputs(quartzGlass)
                .outputs(GTUtility.copy(4, quartzGlass))
                .EUt(VA[MV])
                .duration(80)
                .buildAndRegister();

        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Materials.CertusQuartz, 4)
                .input(Blocks.GLASS,4)
                .outputs(quartzGlass)
                .outputs(GTUtility.copy(4, quartzGlass))
                .EUt(VA[MV])
                .duration(80)
                .buildAndRegister();
        //空白样板
        for(int i=0;i<7;i++) {
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(OrePrefix.dust, Materials.NetherQuartz, 4)
                    .inputs(GTUtility.copy(2, quartzGlass))
                    .input(plate,Plate.get(2+i),4)
                    .circuitMeta(1)
                    .outputs(GTUtility.copy((int) Math.pow(2, i), blankPattern))
                    .fluidInputs(Plastic.get(i+2).getFluid(144))
                    .EUt(VA[HV+i])
                    .duration(200)
                    .buildAndRegister();

            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(OrePrefix.dust, Materials.CertusQuartz, 1)
                    .inputs(GTUtility.copy(2, quartzGlass))
                    .input(plate,Plate.get(2+i),4)
                    .circuitMeta(1)
                    .outputs(GTUtility.copy((int) Math.pow(2, i), blankPattern))
                    .fluidInputs(Plastic.get(i+2).getFluid(144))
                    .EUt(VA[HV+i])
                    .duration(200)
                    .buildAndRegister();
        }
        //ME存储外壳
        for(int i=0;i<7;i++) {
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(plate,Plate.get(2+i),1)
                    .input(wireFine,Wire.get(2+i),4)
                    .input(screw,Pipe.get(2+i),4)
                    .circuitMeta(2)
                    .outputs(GTUtility.copy((int) Math.pow(2, i), meStorageCell))
                    .fluidInputs(Plastic.get(i+2).getFluid(288))
                    .EUt(VA[HV+i])
                    .duration(200)
                    .buildAndRegister();
        }
    }

    private static void deleteRecipes() {
        //物品
        //ME存储外壳
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/empty_storage_cell");
        //空白样板
        ModHandler.removeRecipeByName("appliedenergistics2:network/crafting/patterns_blank");
        //1k
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/storage_components_cell_1k_part");
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/storage_cell_1k");
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/storage_cell_1k_storage");
        //4k
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/storage_components_cell_4k_part");
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/storage_cell_4k");
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/storage_cell_4k_storage");
        //16k
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/storage_components_cell_16k_part");
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/storage_cell_16k");
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/storage_cell_16k_storage");
        //64k
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/storage_components_cell_64k_part");
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/storage_cell_64k");
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/storage_cell_64k_storage");
        //流体
        //1k
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/fluid_storage_components_cell_1k_part");
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/fluid_storage_cell_1k");
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/fluid_storage_cell_1k_storage");
        //4k
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/fluid_storage_components_cell_4k_part");
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/fluid_storage_cell_4k");
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/fluid_storage_cell_4k_storage");
        //16k
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/fluid_storage_components_cell_16k_part");
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/fluid_storage_cell_16k");
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/fluid_storage_cell_16k_storage");
        //64k
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/fluid_storage_components_cell_64k_part");
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/fluid_storage_cell_64k");
        ModHandler.removeRecipeByName("appliedenergistics2:network/cells/fluid_storage_cell_64k_storage");
    }
}
