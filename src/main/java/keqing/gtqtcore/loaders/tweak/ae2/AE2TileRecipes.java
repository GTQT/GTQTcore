package keqing.gtqtcore.loaders.tweak.ae2;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import net.minecraft.init.Blocks;

import static gregtech.api.GTValues.*;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static gtqt.api.util.MaterialHelper.Plastic;
import static keqing.gtqtcore.api.unification.MaterialHelper.Glue;
import static keqing.gtqtcore.loaders.tweak.ae2.index.*;

public class AE2TileRecipes {

    public static void init() {
        CPURecipes();
        EnergyCell();
        //控制器
        ModHandler.removeRecipeByName("appliedenergistics2:network/blocks/controller");
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.frameGt, Materials.Titanium)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV, 2)
                .input(OrePrefix.block, GTQTMaterials.Fluix)
                .outputs(controller)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        //驱动器
        ModHandler.removeRecipeByName("appliedenergistics2:network/blocks/storage_drive");
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(chest)
                .input(OrePrefix.plate, Materials.Titanium, 4)
                .inputs(GTUtility.copy(2, meGlassCable))
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV, 1)
                .inputs(logicProcessor)
                .outputs(drive)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();
        //压印器
        ModHandler.removeRecipeByName("appliedenergistics2:network/blocks/inscribe");

        //ME箱子
        ModHandler.removeRecipeByName("appliedenergistics2:network/blocks/storage_chest");
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.frameGt, Materials.StainlessSteel)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV, 2)
                .input(MetaTileEntities.QUANTUM_CHEST[2])
                .outputs(chest)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();
        //接口 流体接口
        ModHandler.removeRecipeByName("appliedenergistics2:network/blocks/interfaces_interface");
        ModHandler.removeRecipeByName("appliedenergistics2:network/blocks/fluid_interfaces_interface");
        for (int i = 1; i <= 5; i++) {
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(QUANTUM_CHEST[2 * i - 1])
                    .input(OrePrefix.plate, Materials.Titanium, 4)
                    .inputs(GTUtility.copy((int) Math.pow(2, i), meGlassCable))
                    .inputs(GTUtility.copy(2 * i, forming))
                    .inputs(GTUtility.copy(2 * i, breaking))
                    .fluidInputs(Glue[i+1].getFluid(288))
                    .outputs(GTUtility.copy((int) Math.pow(2, i), meInterface))
                    .EUt(VA[HV + i])
                    .duration(200)
                    .buildAndRegister();

            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(QUANTUM_TANK[2 * i - 1])
                    .input(OrePrefix.plate, Materials.Titanium, 4)
                    .inputs(GTUtility.copy((int) Math.pow(2, i), meGlassCable))
                    .inputs(GTUtility.copy(2 * i, forming))
                    .inputs(GTUtility.copy(2 * i, breaking))
                    .fluidInputs(Glue[i+1].getFluid(288))
                    .outputs(GTUtility.copy((int) Math.pow(2, i), meFluidInterface))
                    .EUt(VA[HV + i])
                    .duration(200)
                    .buildAndRegister();
        }
        //ME IO接口
        ModHandler.removeRecipeByName("appliedenergistics2:network/blocks/io_port");
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(2, drive))
                .input(OrePrefix.plate, Materials.Titanium, 4)
                .inputs(GTUtility.copy(2, meGlassCable))
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV, 1)
                .inputs(GTUtility.copy(4, logicProcessor))
                .outputs(mePatternInterface)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();
        //元件工作台
        ModHandler.removeRecipeByName("appliedenergistics2:network/blocks/cell_workbench");
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(Blocks.CRAFTING_TABLE)
                .input(OrePrefix.plate, Materials.Titanium, 4)
                .inputs(calculationProcessor)
                .input(MetaItems.COVER_SCREEN)
                .circuitMeta(2)
                .outputs(meCellWorkbench)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        //安全终端
        ModHandler.removeRecipeByName("appliedenergistics2:network/blocks/security_station");
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(chest)
                .input(OrePrefix.plate, Materials.Titanium, 4)
                .inputs(GTUtility.copy(2, meGlassCable))
                .inputs(GTUtility.copy(4, logicProcessor))
                .outputs(securityStation)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        //分子装配室
        ModHandler.removeRecipeByName("appliedenergistics2:network/crafting/molecular_assembler");
        for (int i = 1; i <= 5; i++) {
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .input(ASSEMBLER[3 + i])
                    .input(OrePrefix.plate, Materials.Titanium, 4)
                    .inputs(GTUtility.copy(2 * i, forming))
                    .inputs(GTUtility.copy(2 * i, breaking))
                    .circuitMeta(2)
                    .fluidInputs(Glue[i+1].getFluid(288))
                    .outputs(GTUtility.copy((int) Math.pow(2, i - 1), molecularAssembler))
                    .EUt(VA[HV + i])
                    .duration(200)
                    .buildAndRegister();
        }
    }

    private static void EnergyCell() {
        ModHandler.removeRecipeByName("appliedenergistics2:network/blocks/energy_energy_cell");
        ModHandler.removeRecipeByName("appliedenergistics2:network/blocks/energy_dense_energy_cell");
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.HULL[HV])
                .input(OrePrefix.battery, MarkerMaterials.Tier.HV)
                .input(OrePrefix.plate, Materials.Titanium, 4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV, 2)
                .inputs(calculationProcessor)
                .circuitMeta(1)
                .outputs(energyCell)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.HULL[LuV])
                .input(OrePrefix.battery, MarkerMaterials.Tier.LuV)
                .input(OrePrefix.plate, Materials.Titanium, 4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.LuV, 2)
                .inputs(calculationProcessor)
                .circuitMeta(1)
                .outputs(denseEnergyCell)
                .EUt(VA[LuV])
                .duration(200)
                .buildAndRegister();
    }

    private static void CPURecipes() {
        //合成单元
        ModHandler.removeRecipeByName("appliedenergistics2:network/crafting/cpu_crafting_unit");
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, Materials.Titanium, 4)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.MV, 2)
                .inputs(logicProcessor)
                .inputs(calculationProcessor)
                .inputs(engineeringProcessor)
                .outputs(craftingUnit)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        //并行处理单元
        ModHandler.removeRecipeByName("appliedenergistics2:network/crafting/cpu_crafting_accelerator");
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(craftingUnit)
                .inputs(GTUtility.copy(2, engineeringProcessor))
                .outputs(parallelProcessingUnit)
                .EUt(VA[HV])
                .duration(40)
                .buildAndRegister();

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(parallelProcessingUnit)
                .outputs(craftingUnit)
                .outputs(GTUtility.copy(2, engineeringProcessor))
                .EUt(VA[HV])
                .duration(40)
                .buildAndRegister();
        //1k
        ModHandler.removeRecipeByName("appliedenergistics2:network/crafting/cpu_crafting_storage_1k");
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(craftingUnit)
                .inputs(cell1k)
                .outputs(craftingStorage1k)
                .EUt(VA[HV])
                .duration(40)
                .buildAndRegister();

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(craftingStorage1k)
                .outputs(craftingUnit)
                .outputs(cell1k)
                .EUt(VA[HV])
                .duration(40)
                .buildAndRegister();
        //4k
        ModHandler.removeRecipeByName("appliedenergistics2:network/crafting/cpu_crafting_storage_4k");
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(craftingUnit)
                .inputs(cell4k)
                .outputs(craftingStorage4k)
                .EUt(VA[HV])
                .duration(40)
                .buildAndRegister();

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(craftingStorage4k)
                .outputs(craftingUnit)
                .outputs(cell4k)
                .EUt(VA[HV])
                .duration(40)
                .buildAndRegister();
        //16k
        ModHandler.removeRecipeByName("appliedenergistics2:network/crafting/cpu_crafting_storage_16k");
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(craftingUnit)
                .inputs(cell16k)
                .outputs(craftingStorage16k)
                .EUt(VA[HV])
                .duration(40)
                .buildAndRegister();

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(craftingStorage16k)
                .outputs(craftingUnit)
                .outputs(cell16k)
                .EUt(VA[HV])
                .duration(40)
                .buildAndRegister();
        //64k
        ModHandler.removeRecipeByName("appliedenergesis2:network/crafting/cpu_crafting_storage_64k");
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(craftingUnit)
                .inputs(cell64k)
                .outputs(craftingStorage64k)
                .EUt(VA[HV])
                .duration(40)
                .buildAndRegister();

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(craftingStorage64k)
                .outputs(craftingUnit)
                .outputs(cell64k)
                .EUt(VA[HV])
                .duration(40)
                .buildAndRegister();
    }
}
