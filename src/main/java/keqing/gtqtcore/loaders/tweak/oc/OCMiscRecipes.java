package keqing.gtqtcore.loaders.tweak.oc;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.loaders.tweak.oc.index.*;

public class OCMiscRecipes {
    public static void init()
    {
        card();
        material();
        component();
    }

    private static void component() {
        //组件总线 3个
        //基板+微芯片+控制单元+螺栓
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.BASIC_CIRCUIT_BOARD)
                .inputs(GTUtility.copy(2, simple_chip))
                .inputs(control_unit)
                .inputs(memory_t1)
                .input(OrePrefix.bolt,RedAlloy,4)
                .outputs(basic_component_bus)
                .EUt(VA[LV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.GOOD_CIRCUIT_BOARD)
                .inputs(GTUtility.copy(2, simple_chip))
                .inputs(GTUtility.copy(8, control_unit))
                .inputs(memory_t2)
                .input(OrePrefix.bolt,Copper,4)
                .outputs(advanced_component_bus)
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.PLASTIC_CIRCUIT_BOARD)
                .inputs(GTUtility.copy(2, simple_chip))
                .inputs(GTUtility.copy(8, control_unit))
                .inputs(memory_t3)
                .input(OrePrefix.bolt,Gold,16)
                .outputs(super_component_bus)
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();

        //基础服务器 3个
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.BASIC_CIRCUIT_BOARD)
                .inputs(basic_cabinet)
                .inputs(GTUtility.copy(2, simple_chip))
                .inputs(GTUtility.copy(1, control_unit))
                .inputs(memory_t1)
                .input(OrePrefix.bolt,RedAlloy,4)
                .outputs(basic_server)
                .EUt(VA[LV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.GOOD_CIRCUIT_BOARD)
                .inputs(advanced_cabinet)
                .inputs(GTUtility.copy(2, advanced_chip))
                .inputs(GTUtility.copy(4, control_unit))
                .inputs(memory_t2)
                .input(OrePrefix.bolt,Copper,4)
                .outputs(advanced_server)
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.PLASTIC_CIRCUIT_BOARD)
                .inputs(super_cabinet)
                .inputs(GTUtility.copy(2, super_chip))
                .inputs(GTUtility.copy(8, control_unit))
                .inputs(memory_t3)
                .input(OrePrefix.bolt,Gold,16)
                .outputs(super_server)
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();

        //内存条
        //基板+微芯片+控制单元+foil
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.BASIC_CIRCUIT_BOARD)
                .inputs(GTUtility.copy(2, simple_chip))
                .inputs(GTUtility.copy(1, control_unit))
                .input(OrePrefix.foil,Copper,1)
                .circuitMeta(4)
                .outputs(memory_t1)
                .EUt(VA[LV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.BASIC_CIRCUIT_BOARD)
                .inputs(GTUtility.copy(4, simple_chip))
                .inputs(GTUtility.copy(2, control_unit))
                .input(OrePrefix.foil,Copper,4)
                .circuitMeta(5)
                .outputs(memory_t1_5)
                .EUt(VA[LV])
                .duration(400)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.GOOD_CIRCUIT_BOARD)
                .inputs(GTUtility.copy(4, advanced_chip))
                .inputs(GTUtility.copy(2, control_unit))
                .input(OrePrefix.foil,Gold,4)
                .circuitMeta(4)
                .outputs(memory_t2)
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.GOOD_CIRCUIT_BOARD)
                .inputs(GTUtility.copy(8, advanced_chip))
                .inputs(GTUtility.copy(4, control_unit))
                .input(OrePrefix.foil,Gold,8)
                .circuitMeta(5)
                .outputs(memory_t2_5)
                .EUt(VA[MV])
                .duration(400)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.PLASTIC_CIRCUIT_BOARD)
                .inputs(GTUtility.copy(16, super_chip))
                .inputs(GTUtility.copy(8, control_unit))
                .input(OrePrefix.foil,Gold,8)
                .circuitMeta(4)
                .outputs(memory_t3)
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.PLASTIC_CIRCUIT_BOARD)
                .inputs(GTUtility.copy(32, super_chip))
                .inputs(GTUtility.copy(16, control_unit))
                .input(OrePrefix.foil,Gold,16)
                .circuitMeta(5)
                .outputs(memory_t3_5)
                .EUt(VA[HV])
                .duration(400)
                .buildAndRegister();

        //运算加速单元
        //总线+处理器+显卡+微芯片
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(advanced_component_bus)
                .inputs(GTUtility.copy(2, advanced_central_processor))
                .inputs(GTUtility.copy(1, advanced_graphic_card))
                .inputs(GTUtility.copy(1, advanced_chip))
                .input(OrePrefix.foil,Gold,1)
                .circuitMeta(4)
                .outputs(advanced_computing_accelerator)
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(super_component_bus)
                .inputs(GTUtility.copy(4, super_central_processor))
                .inputs(GTUtility.copy(2, super_graphic_card))
                .inputs(GTUtility.copy(2, super_chip))
                .input(OrePrefix.foil,Platinum,1)
                .circuitMeta(4)
                .outputs(super_computing_accelerator)
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        //中央处理器 ALU+CU+微芯片+电路+铝外壳
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.BASIC_CIRCUIT_BOARD)
                .inputs(GTUtility.copy(8, alu))
                .inputs(GTUtility.copy(2, control_unit))
                .inputs(GTUtility.copy(2, simple_chip))
                .input(OrePrefix.circuit, MarkerMaterials.Tier.LV, 1)
                .circuitMeta(4)
                .outputs(basic_central_processor)
                .EUt(VA[LV])
                .duration(100)
                .buildAndRegister();
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.GOOD_CIRCUIT_BOARD)
                .inputs(GTUtility.copy(12, alu))
                .inputs(GTUtility.copy(4, control_unit))
                .inputs(GTUtility.copy(4, advanced_chip))
                .input(OrePrefix.circuit, MarkerMaterials.Tier.MV, 1)
                .circuitMeta(4)
                .outputs(advanced_central_processor)
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.PLASTIC_CIRCUIT_BOARD)
                .inputs(GTUtility.copy(16, alu))
                .inputs(GTUtility.copy(8, control_unit))
                .inputs(GTUtility.copy(8, super_chip))
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV, 1)
                .circuitMeta(4)
                .outputs(super_central_processor)
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();
    }

    private static void material() {
        //基板 5
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.BASIC_CIRCUIT_BOARD)
                .input(OrePrefix.stick,Steel,1)
                .input(OrePrefix.foil,Gold,1)
                .outputs(base_plate)
                .EUt(VA[LV])
                .duration(100)
                .buildAndRegister();

        //晶体管 6
        //请在配方中使用二极管代替

        //未加工电路 2
        //印刷电路 4
        ModHandler.removeFurnaceSmelting(unprocessed_circuit_board);
        //请在配方中使用PCB代替

        //ALU 10
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.BASIC_CIRCUIT_BOARD)
                .input("componentTransistor",16)
                .circuitMeta(1)
                .outputs(GTUtility.copy(2, alu))
                .EUt(VA[LV])
                .duration(100)
                .buildAndRegister();

        //控制单元 11
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input("componentTransistor",4)
                .input(OrePrefix.circuit,MarkerMaterials.Tier.LV)
                .circuitMeta(1)
                .outputs(control_unit)
                .EUt(VA[LV])
                .duration(100)
                .buildAndRegister();


        //微芯片：
        //基础
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.BASIC_CIRCUIT_BOARD)
                .input(OrePrefix.circuit,MarkerMaterials.Tier.LV)
                .input("componentTransistor",4)
                .input(OrePrefix.foil,Copper,4)
                .circuitMeta(2)
                .outputs(simple_chip)
                .EUt(VA[LV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.BASIC_CIRCUIT_BOARD)
                .input(OrePrefix.circuit,MarkerMaterials.Tier.LV)
                .input(MetaItems.ADVANCED_SMD_DIODE,1)
                .input(OrePrefix.foil,Copper,4)
                .circuitMeta(2)
                .outputs(simple_chip)
                .EUt(VA[LV])
                .duration(100)
                .buildAndRegister();
        //高级
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.GOOD_CIRCUIT_BOARD)
                .input(OrePrefix.circuit,MarkerMaterials.Tier.MV)
                .input("componentTransistor",8)
                .input(OrePrefix.foil,Gold,4)
                .circuitMeta(2)
                .outputs(advanced_chip)
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.GOOD_CIRCUIT_BOARD)
                .input(OrePrefix.circuit,MarkerMaterials.Tier.MV)
                .input(MetaItems.ADVANCED_SMD_DIODE,2)
                .input(OrePrefix.foil,Gold,4)
                .circuitMeta(2)
                .outputs(advanced_chip)
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();
        //超级
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.PLASTIC_CIRCUIT_BOARD)
                .input(OrePrefix.circuit,MarkerMaterials.Tier.HV)
                .input("componentTransistor",16)
                .input(OrePrefix.foil,Platinum,4)
                .circuitMeta(2)
                .outputs(super_chip)
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.PLASTIC_CIRCUIT_BOARD)
                .input(OrePrefix.circuit,MarkerMaterials.Tier.HV)
                .input(MetaItems.ADVANCED_SMD_DIODE,4)
                .input(OrePrefix.foil,Platinum,4)
                .circuitMeta(2)
                .outputs(super_chip)
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();
    }

    private static void card() {
        //数据卡 3
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(base_plate)
                .inputs(simple_chip)
                .inputs(GTUtility.copy(4, alu))
                .input(OrePrefix.bolt,RedAlloy,8)
                .circuitMeta(2)
                .outputs(basic_data_card)
                .EUt(VA[LV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(base_plate)
                .inputs(advanced_chip)
                .inputs(GTUtility.copy(8, alu))
                .input(OrePrefix.bolt,Copper,8)
                .circuitMeta(2)
                .outputs(advanced_data_card)
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(base_plate)
                .inputs(super_chip)
                .inputs(GTUtility.copy(16, alu))
                .input(OrePrefix.bolt,Gold,8)
                .circuitMeta(2)
                .outputs(super_data_card)
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();

        //显卡
        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(base_plate)
                .inputs(GTUtility.copy(8, alu))
                .inputs(GTUtility.copy(1, control_unit))
                .inputs(simple_chip)
                .inputs(memory_t1)
                .circuitMeta(3)
                .outputs(basic_graphic_card)
                .EUt(VA[LV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(base_plate)
                .inputs(GTUtility.copy(16, alu))
                .inputs(GTUtility.copy(4, control_unit))
                .inputs(GTUtility.copy(2, advanced_chip))
                .inputs(memory_t2)
                .circuitMeta(3)
                .outputs(basic_graphic_card)
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(base_plate)
                .inputs(GTUtility.copy(32, alu))
                .inputs(GTUtility.copy(8, control_unit))
                .inputs(GTUtility.copy(4, super_chip))
                .inputs(memory_t3)
                .circuitMeta(3)
                .outputs(basic_graphic_card)
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();

    }
}
