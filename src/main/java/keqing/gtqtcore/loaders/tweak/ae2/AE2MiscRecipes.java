package keqing.gtqtcore.loaders.tweak.ae2;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.api.util.Mods;
import gregtech.common.items.MetaItems;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_BATH_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gtqt.api.util.MaterialHelper.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Fluix;
import static keqing.gtqtcore.api.unification.MaterialHelper.Glue;
import static keqing.gtqtcore.loaders.tweak.ae2.index.*;

public class AE2MiscRecipes {
    public static void init() {
        //主要是合成材料
        //三种处理器
        ProcessorRecipes();
        //纤维&&线缆
        FiberRecipes();
        //成型 破坏核心
        FormingAndDestructionCoreRecipes();
        //升级卡
        UpgradeCardRecipes();

    }

    private static void UpgradeCardRecipes() {
        ModHandler.removeRecipeByName("appliedenergistics2:materials/basiccard");
        ModHandler.removeRecipeByName("appliedenergistics2:materials/advancedcard");
        for(int i=0;i<5;i++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copy((int) Math.pow(2, i), calculationProcessor))
                    .input(plate, Cable.get(i))
                    .input(plate, Gold, 2)
                    .input(plate, Aluminium, 3)
                    .circuitMeta(2)
                    .fluidInputs(Glue[i+1].getFluid(288))
                    .outputs(GTUtility.copy((int) Math.pow(2, i), basicCard))
                    .EUt(VA[HV+i])
                    .duration(100)
                    .buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copy((int) Math.pow(2, i), calculationProcessor))
                    .input(plate, Cable.get(i))
                    .input(plate, Platinum, 2)
                    .input(plate, Titanium, 3)
                    .circuitMeta(2)
                    .fluidInputs(Glue[i+1].getFluid(288))
                    .outputs(GTUtility.copy((int) Math.pow(2, i), advancedCard))
                    .EUt(VA[HV])
                    .duration(100+i)
                    .buildAndRegister();
        }
    }

    private static void FormingAndDestructionCoreRecipes() {
        ModHandler.removeRecipeByName("appliedenergistics2:materials/annihilationcore");
        ModHandler.removeRecipeByName("appliedenergistics2:materials/formationcore");
        //原始产出
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, NetherQuartz, 4)
                .inputs(GTUtility.copy(4, logicProcessor))
                .input(gem, Fluix)
                .outputs(GTUtility.copy(2, forming))
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, CertusQuartz, 4)
                .inputs(GTUtility.copy(4, logicProcessor))
                .input(gem, Fluix)
                .outputs(GTUtility.copy(2, breaking))
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, logicProcessor))
                .input(MetaItems.SMD_CAPACITOR, 1)
                .input(gem, Fluix)
                .outputs(GTUtility.copy(4, forming))
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, logicProcessor))
                .input(MetaItems.SMD_CAPACITOR, 1)
                .input(gem, Fluix)
                .outputs(GTUtility.copy(4, breaking))
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, logicProcessor))
                .input(MetaItems.ADVANCED_SMD_CAPACITOR, 1)
                .input(gem, Fluix)
                .outputs(GTUtility.copy(8, forming))
                .EUt(VA[IV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, logicProcessor))
                .input(MetaItems.ADVANCED_SMD_CAPACITOR, 1)
                .input(gem, Fluix)
                .outputs(GTUtility.copy(8, breaking))
                .EUt(VA[IV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, logicProcessor))
                .input(GTQTMetaItems.OPTICAL_CAPACITOR, 1)
                .input(gem, Fluix)
                .outputs(GTUtility.copy(16, forming))
                .EUt(VA[UHV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, logicProcessor))
                .input(GTQTMetaItems.OPTICAL_CAPACITOR, 1)
                .input(gem, Fluix)
                .outputs(GTUtility.copy(16, breaking))
                .EUt(VA[UHV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, logicProcessor))
                .input(GTQTMetaItems.SPINTRONIC_CAPACITOR, 1)
                .input(gem, Fluix)
                .outputs(GTUtility.copy(32, forming))
                .EUt(VA[UEV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, logicProcessor))
                .input(GTQTMetaItems.SPINTRONIC_CAPACITOR, 1)
                .input(gem, Fluix)
                .outputs(GTUtility.copy(32, breaking))
                .EUt(VA[UEV])
                .duration(100)
                .buildAndRegister();
    }

    private static void FiberRecipes() {
        //石英纤维
        ModHandler.removeRecipeByName("appliedenergistics2:network/parts/quartz_fiber_part");
        RecipeMaps.WIREMILL_RECIPES.recipeBuilder()
                .input(plate, Materials.Quartzite)
                .outputs(siliconFiber)
                .EUt(VA[LV])
                .duration(80)
                .buildAndRegister();

        RecipeMaps.WIREMILL_RECIPES.recipeBuilder()
                .input(plate, Materials.NetherQuartz)
                .outputs(GTUtility.copy(4, siliconFiber))
                .EUt(VA[LV])
                .duration(80)
                .buildAndRegister();

        RecipeMaps.WIREMILL_RECIPES.recipeBuilder()
                .input(plate, Materials.CertusQuartz)
                .outputs(GTUtility.copy(16, siliconFiber))
                .EUt(VA[LV])
                .duration(80)
                .buildAndRegister();

        ModHandler.removeRecipeByName("appliedenergistics2:network/cables/glass_fluix");
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(3, siliconFiber))
                .input(dust, Fluix, 2)
                .outputs(GTUtility.copy(3, meGlassCable))
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        for (int i = 0; i <= 16; i++) {
            ItemStack colorGlassCable = Mods.AppliedEnergistics2.getItem("part", i, 1);
            //洗颜色
            CHEMICAL_BATH_RECIPES.recipeBuilder()
                    .inputs(colorGlassCable)
                    .fluidInputs(Water.getFluid(25))
                    .outputs(meGlassCable)
                    .duration(100).EUt(VA[LV]).buildAndRegister();

            //染色
            for (int j = 0; j <= 15; j++) {
                if (i == j) continue;
                ItemStack target = Mods.AppliedEnergistics2.getItem("part", j, 1);
                CHEMICAL_BATH_RECIPES.recipeBuilder()
                        .inputs(colorGlassCable)
                        .fluidInputs(Materials.CHEMICAL_DYES[j].getFluid(9))
                        .outputs(target)
                        .EUt(VA[LV]).duration(100)
                        .buildAndRegister();
            }
        }
        ModHandler.removeRecipeByName("appliedenergistics2:network/cables/covered_fluix");
        //包层线缆
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(meGlassCable)
                .circuitMeta(1)
                .outputs(meCable)
                .fluidInputs(Rubber.getFluid(288))
                .EUt(VA[MV])
                .duration(150)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(meGlassCable)
                .circuitMeta(2)
                .outputs(meCable)
                .fluidInputs(SiliconeRubber.getFluid(144))
                .EUt(VA[MV])
                .duration(150)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(meGlassCable)
                .circuitMeta(3)
                .outputs(meCable)
                .fluidInputs(StyreneButadieneRubber.getFluid(72))
                .EUt(VA[MV])
                .duration(150)
                .buildAndRegister();

        for (int i = 0; i <= 16; i++) {
            ItemStack colorGlassCable = Mods.AppliedEnergistics2.getItem("part", 20 + i, 1);
            //洗颜色
            CHEMICAL_BATH_RECIPES.recipeBuilder()
                    .inputs(colorGlassCable)
                    .fluidInputs(Water.getFluid(25))
                    .outputs(meCable)
                    .duration(100).EUt(VA[LV]).buildAndRegister();

            //染色
            for (int j = 0; j <= 15; j++) {
                if (i == j) continue;
                ItemStack target = Mods.AppliedEnergistics2.getItem("part", 20 + j, 1);
                CHEMICAL_BATH_RECIPES.recipeBuilder()
                        .inputs(colorGlassCable)
                        .fluidInputs(Materials.CHEMICAL_DYES[j].getFluid(9))
                        .outputs(target)
                        .EUt(VA[LV]).duration(100)
                        .buildAndRegister();
            }
        }
        ModHandler.removeRecipeByName("appliedenergistics2:network/cables/dense_covered_fluix");
        //致密线缆
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, meCable))
                .circuitMeta(1)
                .outputs(meCableDense)
                .fluidInputs(Rubber.getFluid(288 * 4))
                .EUt(VA[MV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, meCable))
                .circuitMeta(2)
                .outputs(meCableDense)
                .fluidInputs(SiliconeRubber.getFluid(144 * 4))
                .EUt(VA[MV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, meCable))
                .circuitMeta(3)
                .outputs(meCableDense)
                .fluidInputs(StyreneButadieneRubber.getFluid(72 * 4))
                .EUt(VA[MV])
                .duration(200)
                .buildAndRegister();

        for (int i = 0; i <= 16; i++) {
            ItemStack colorGlassCable = Mods.AppliedEnergistics2.getItem("part", 500 + i, 1);
            //洗颜色
            CHEMICAL_BATH_RECIPES.recipeBuilder()
                    .inputs(colorGlassCable)
                    .fluidInputs(Water.getFluid(25))
                    .outputs(meCableDense)
                    .duration(100).EUt(VA[LV]).buildAndRegister();

            //染色
            for (int j = 0; j <= 15; j++) {
                if (i == j) continue;
                ItemStack target = Mods.AppliedEnergistics2.getItem("part", 500 + j, 1);
                CHEMICAL_BATH_RECIPES.recipeBuilder()
                        .inputs(colorGlassCable)
                        .fluidInputs(Materials.CHEMICAL_DYES[j].getFluid(9))
                        .outputs(target)
                        .EUt(VA[LV]).duration(100)
                        .buildAndRegister();
            }
        }

        ModHandler.removeRecipeByName("appliedenergistics2:network/cables/smart_fluix");
        //智能线缆
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, meGlassCable))
                .input(circuit, MarkerMaterials.Tier.MV)
                .circuitMeta(1)
                .outputs(meCableSmart)
                .fluidInputs(Rubber.getFluid(288))
                .EUt(VA[MV])
                .duration(150)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, meGlassCable))
                .input(circuit, MarkerMaterials.Tier.MV)
                .circuitMeta(2)
                .outputs(meCableSmart)
                .fluidInputs(SiliconeRubber.getFluid(144))
                .EUt(VA[MV])
                .duration(150)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTUtility.copy(4, meGlassCable))
                .input(circuit, MarkerMaterials.Tier.MV)
                .circuitMeta(3)
                .outputs(meCableSmart)
                .fluidInputs(StyreneButadieneRubber.getFluid(72))
                .EUt(VA[MV])
                .duration(150)
                .buildAndRegister();

        for (int i = 0; i <= 16; i++) {
            ItemStack colorGlassCable = Mods.AppliedEnergistics2.getItem("part", 40 + i, 1);
            //洗颜色
            CHEMICAL_BATH_RECIPES.recipeBuilder()
                    .inputs(colorGlassCable)
                    .fluidInputs(Water.getFluid(25))
                    .outputs(meCableSmart)
                    .duration(100).EUt(VA[LV]).buildAndRegister();

            //染色
            for (int j = 0; j <= 15; j++) {
                if (i == j) continue;
                ItemStack target = Mods.AppliedEnergistics2.getItem("part", 40 + j, 1);
                CHEMICAL_BATH_RECIPES.recipeBuilder()
                        .inputs(colorGlassCable)
                        .fluidInputs(Materials.CHEMICAL_DYES[j].getFluid(9))
                        .outputs(target)
                        .EUt(VA[LV]).duration(100)
                        .buildAndRegister();
            }
        }
    }

    public static void ProcessorRecipes() {
        //注册格式：硅板+主材料+贴片三种（升级四次）+液态红石 144mb
        //逻辑 金
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(logicModel)
                .input(plate, Materials.Gold)
                .circuitMeta(1)
                .outputs(logicBase)
                .EUt(VA[MV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(logicModel)
                .input(plate, Materials.Platinum)
                .circuitMeta(2)
                .outputs(GTUtility.copy(4, logicBase))
                .EUt(VA[EV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(logicModel)
                .input(plate, Materials.Samarium)
                .circuitMeta(3)
                .outputs(GTUtility.copy(16, logicBase))
                .EUt(VA[LuV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(logicModel)
                .input(plate, GTQTMaterials.ChromiumGermaniumTellurideMagnetic)
                .circuitMeta(4)
                .outputs(GTUtility.copy(64, logicBase))
                .EUt(VA[UHV])
                .duration(200)
                .buildAndRegister();
        //运算 钻石
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(calculationModel)
                .input(plate, Materials.Diamond)
                .circuitMeta(1)
                .outputs(calculationBase)
                .EUt(VA[MV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(calculationModel)
                .input(plate, Materials.Palladium)
                .circuitMeta(2)
                .outputs(GTUtility.copy(4, calculationBase))
                .EUt(VA[EV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(calculationModel)
                .input(plate, Materials.Cerium)
                .circuitMeta(3)
                .outputs(GTUtility.copy(16, calculationBase))
                .EUt(VA[LuV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(calculationModel)
                .input(plate, GTQTMaterials.Orichalcum)
                .circuitMeta(4)
                .outputs(GTUtility.copy(64, calculationBase))
                .EUt(VA[UHV])
                .duration(200)
                .buildAndRegister();
        //工程 赛特斯
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(engineeringModel)
                .circuitMeta(1)
                .input(plate, Materials.CertusQuartz)
                .outputs(engineeringBase)
                .EUt(VA[MV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(engineeringModel)
                .circuitMeta(2)
                .input(plate, Materials.Neodymium)
                .outputs(GTUtility.copy(4, engineeringBase))
                .EUt(VA[EV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(engineeringModel)
                .circuitMeta(3)
                .input(plate, Materials.Yttrium)
                .outputs(GTUtility.copy(16, engineeringBase))
                .EUt(VA[LuV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(engineeringModel)
                .circuitMeta(4)
                .input(plate, Materials.Europium)
                .outputs(GTUtility.copy(64, engineeringBase))
                .EUt(VA[UHV])
                .duration(200)
                .buildAndRegister();
        ////////////////////////////////////////////////////////////
        //逻辑
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Materials.Silicon)
                .inputs(logicBase)
                .input(MetaItems.SMD_TRANSISTOR, 1)
                .input(MetaItems.SMD_RESISTOR, 1)
                .input(MetaItems.SMD_DIODE, 1)
                .input(MetaItems.SMD_INDUCTOR, 1)
                .fluidInputs(Materials.Redstone.getFluid(288))
                .outputs(logicProcessor)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Materials.Silicon, 4)
                .outputs(GTUtility.copy(4, logicBase))
                .input(MetaItems.ADVANCED_SMD_TRANSISTOR, 1)
                .input(MetaItems.ADVANCED_SMD_RESISTOR, 1)
                .input(MetaItems.ADVANCED_SMD_DIODE, 1)
                .input(MetaItems.ADVANCED_SMD_INDUCTOR, 1)
                .fluidInputs(Materials.Redstone.getFluid(288 * 4))
                .outputs(GTUtility.copy(4, logicProcessor))
                .EUt(VA[LuV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Materials.Silicon, 16)
                .outputs(GTUtility.copy(16, logicBase))
                .input(GTQTMetaItems.OPTICAL_TRANSISTOR, 1)
                .input(GTQTMetaItems.OPTICAL_RESISTOR, 1)
                .input(GTQTMetaItems.OPTICAL_DIODE, 1)
                .input(GTQTMetaItems.OPTICAL_INDUCTOR, 1)
                .fluidInputs(Materials.Redstone.getFluid(288 * 16))
                .outputs(GTUtility.copy(16, logicProcessor))
                .EUt(VA[UHV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Materials.Silicon, 64)
                .outputs(GTUtility.copy(64, logicBase))
                .input(GTQTMetaItems.SPINTRONIC_TRANSISTOR, 1)
                .input(GTQTMetaItems.SPINTRONIC_RESISTOR, 1)
                .input(GTQTMetaItems.SPINTRONIC_DIODE, 1)
                .input(GTQTMetaItems.SPINTRONIC_INDUCTOR, 1)
                .fluidInputs(Materials.Redstone.getFluid(288 * 64))
                .outputs(GTUtility.copy(64, logicProcessor))
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //运算
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Materials.Silicon)
                .inputs(calculationBase)
                .input(MetaItems.SMD_TRANSISTOR, 1)
                .input(MetaItems.SMD_RESISTOR, 1)
                .input(MetaItems.SMD_DIODE, 1)
                .input(MetaItems.SMD_INDUCTOR, 1)
                .outputs(calculationProcessor)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Materials.Silicon, 4)
                .outputs(GTUtility.copy(4, calculationBase))
                .input(MetaItems.ADVANCED_SMD_TRANSISTOR, 1)
                .input(MetaItems.ADVANCED_SMD_RESISTOR, 1)
                .input(MetaItems.ADVANCED_SMD_DIODE, 1)
                .input(MetaItems.ADVANCED_SMD_INDUCTOR, 1)
                .outputs(GTUtility.copy(4, calculationProcessor))
                .EUt(VA[LuV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Materials.Silicon, 16)
                .outputs(GTUtility.copy(16, calculationBase))
                .input(GTQTMetaItems.OPTICAL_TRANSISTOR, 1)
                .input(GTQTMetaItems.OPTICAL_RESISTOR, 1)
                .input(GTQTMetaItems.OPTICAL_DIODE, 1)
                .input(GTQTMetaItems.OPTICAL_INDUCTOR, 1)
                .outputs(GTUtility.copy(16, calculationProcessor))
                .EUt(VA[UHV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Materials.Silicon, 64)
                .outputs(GTUtility.copy(64, calculationBase))
                .input(GTQTMetaItems.SPINTRONIC_TRANSISTOR, 1)
                .input(GTQTMetaItems.SPINTRONIC_RESISTOR, 1)
                .input(GTQTMetaItems.SPINTRONIC_DIODE, 1)
                .input(GTQTMetaItems.SPINTRONIC_INDUCTOR, 1)
                .outputs(GTUtility.copy(64, calculationProcessor))
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();

        //工程
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Materials.Silicon)
                .inputs(engineeringBase)
                .input(MetaItems.SMD_TRANSISTOR, 1)
                .input(MetaItems.SMD_RESISTOR, 1)
                .input(MetaItems.SMD_DIODE, 1)
                .input(MetaItems.SMD_INDUCTOR, 1)
                .outputs(engineeringProcessor)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Materials.Silicon, 4)
                .outputs(GTUtility.copy(4, engineeringBase))
                .input(MetaItems.ADVANCED_SMD_TRANSISTOR, 1)
                .input(MetaItems.ADVANCED_SMD_RESISTOR, 1)
                .input(MetaItems.ADVANCED_SMD_DIODE, 1)
                .input(MetaItems.ADVANCED_SMD_INDUCTOR, 1)
                .outputs(GTUtility.copy(4, engineeringProcessor))
                .EUt(VA[LuV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Materials.Silicon, 16)
                .outputs(GTUtility.copy(16, engineeringBase))
                .input(GTQTMetaItems.OPTICAL_TRANSISTOR, 1)
                .input(GTQTMetaItems.OPTICAL_RESISTOR, 1)
                .input(GTQTMetaItems.OPTICAL_DIODE, 1)
                .input(GTQTMetaItems.OPTICAL_INDUCTOR, 1)
                .outputs(GTUtility.copy(16, engineeringProcessor))
                .EUt(VA[UHV])
                .duration(200)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Materials.Silicon, 64)
                .outputs(GTUtility.copy(64, engineeringBase))
                .input(GTQTMetaItems.SPINTRONIC_TRANSISTOR, 1)
                .input(GTQTMetaItems.SPINTRONIC_RESISTOR, 1)
                .input(GTQTMetaItems.SPINTRONIC_DIODE, 1)
                .input(GTQTMetaItems.SPINTRONIC_INDUCTOR, 1)
                .outputs(GTUtility.copy(64, engineeringProcessor))
                .EUt(VA[UXV])
                .duration(200)
                .buildAndRegister();
    }
}
