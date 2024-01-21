package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import net.minecraft.item.ItemStack;

import static gregtech.api.recipes.ModHandler.removeRecipeByName;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;

public class GTComponents {
    public static void init(){
        removeOldComponents();
    }
    public static MetaItem<?>.MetaValueItem[] motors = new MetaItem<?>.MetaValueItem[]{ELECTRIC_MOTOR_LV, ELECTRIC_MOTOR_MV, ELECTRIC_MOTOR_HV, ELECTRIC_MOTOR_EV, ELECTRIC_MOTOR_IV, ELECTRIC_MOTOR_LuV, ELECTRIC_MOTOR_ZPM, ELECTRIC_MOTOR_UV, ELECTRIC_MOTOR_UHV, ELECTRIC_MOTOR_UEV, ELECTRIC_MOTOR_UIV, ELECTRIC_MOTOR_UXV, ELECTRIC_MOTOR_OpV};
    public static MetaItem<?>.MetaValueItem[] pistons = new MetaItem<?>.MetaValueItem[]{ELECTRIC_PISTON_LV, ELECTRIC_PISTON_MV, ELECTRIC_PISTON_HV, ELECTRIC_PISTON_EV, ELECTRIC_PISTON_IV, ELECTRIC_PISTON_LUV, ELECTRIC_PISTON_ZPM, ELECTRIC_PISTON_UV, ELECTRIC_PISTON_UHV, ELECTRIC_PISTON_UEV, ELECTRIC_PISTON_UIV, ELECTRIC_PISTON_UXV, ELECTRIC_PISTON_OpV};
    public static MetaItem<?>.MetaValueItem[] pump = new MetaItem<?>.MetaValueItem[]{ELECTRIC_PUMP_LV, ELECTRIC_PUMP_MV, ELECTRIC_PUMP_HV, ELECTRIC_PUMP_EV, ELECTRIC_PUMP_IV, ELECTRIC_PUMP_LuV, ELECTRIC_PUMP_ZPM, ELECTRIC_PUMP_UV, ELECTRIC_PUMP_UHV, ELECTRIC_PUMP_UEV, ELECTRIC_PUMP_UIV,ELECTRIC_PUMP_UXV, ELECTRIC_PUMP_OpV};
    public static MetaItem<?>.MetaValueItem[] conveyor = new MetaItem<?>.MetaValueItem[]{CONVEYOR_MODULE_LV, CONVEYOR_MODULE_MV, CONVEYOR_MODULE_HV, CONVEYOR_MODULE_EV, CONVEYOR_MODULE_IV, CONVEYOR_MODULE_LuV, CONVEYOR_MODULE_ZPM, CONVEYOR_MODULE_UV, CONVEYOR_MODULE_UHV, CONVEYOR_MODULE_UEV, CONVEYOR_MODULE_UIV, CONVEYOR_MODULE_UXV, CONVEYOR_MODULE_OpV};
    public static MetaItem<?>.MetaValueItem[] roboArm = new MetaItem<?>.MetaValueItem[]{ROBOT_ARM_LV, ROBOT_ARM_MV, ROBOT_ARM_HV, ROBOT_ARM_EV, ROBOT_ARM_IV, ROBOT_ARM_LuV, ROBOT_ARM_ZPM, ROBOT_ARM_UV, ROBOT_ARM_UHV, ROBOT_ARM_UEV, ROBOT_ARM_UIV, ROBOT_ARM_UXV, ROBOT_ARM_OpV};
    public static MetaItem<?>.MetaValueItem[] feildGen = new MetaItem<?>.MetaValueItem[]{FIELD_GENERATOR_LV, FIELD_GENERATOR_MV, FIELD_GENERATOR_HV, FIELD_GENERATOR_EV, FIELD_GENERATOR_IV, FIELD_GENERATOR_LuV, FIELD_GENERATOR_ZPM, FIELD_GENERATOR_UV, FIELD_GENERATOR_UHV, FIELD_GENERATOR_UEV, FIELD_GENERATOR_UIV, FIELD_GENERATOR_UXV, FIELD_GENERATOR_OpV};
    public static MetaItem<?>.MetaValueItem[] emitter = new MetaItem<?>.MetaValueItem[]{EMITTER_LV, EMITTER_MV, EMITTER_HV, EMITTER_EV, EMITTER_IV, EMITTER_LuV, EMITTER_ZPM, EMITTER_UV, EMITTER_UHV, EMITTER_UEV, EMITTER_UIV, EMITTER_UXV, EMITTER_OpV};
    public static MetaItem<?>.MetaValueItem[] sensor = new MetaItem<?>.MetaValueItem[]{SENSOR_LV, SENSOR_MV, SENSOR_HV, SENSOR_EV, SENSOR_IV, SENSOR_LuV, SENSOR_ZPM, SENSOR_UV, SENSOR_UHV, SENSOR_UEV, SENSOR_UIV, SENSOR_UXV, SENSOR_OpV};
    private static final Material[] rubbers = new Material[]{Rubber, SiliconeRubber, StyreneButadieneRubber};
    private static void removeOldComponents() {

        //TODO: Remove decomp recipes for components
        //this doesnt work
        //GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, motors[0].getStackForm());

        // remove motors
        removeRecipeByName("gregtech:electric_motor_lv_steel");
        removeRecipeByName("gregtech:electric_motor_lv_iron");
        removeRecipeByName("gregtech:electric_motor_mv");
        removeRecipeByName("gregtech:electric_motor_hv");
        removeRecipeByName("gregtech:electric_motor_ev");
        removeRecipeByName("gregtech:electric_motor_iv");
        removeRecipeByName("gregtech:electric_piston_lv");
        removeRecipeByName("gregtech:electric_piston_mv");
        removeRecipeByName("gregtech:electric_piston_hv");
        removeRecipeByName("gregtech:electric_piston_ev");
        removeRecipeByName("gregtech:electric_piston_iv");
        removeRecipeByName("gregtech:robot_arm_lv");
        removeRecipeByName("gregtech:robot_arm_mv");
        removeRecipeByName("gregtech:robot_arm_hv");
        removeRecipeByName("gregtech:robot_arm_ev");
        removeRecipeByName("gregtech:robot_arm_iv");
        removeRecipeByName("gregtech:electric_pump_lv_rubber");
        removeRecipeByName("gregtech:electric_pump_lv_silicone_rubber");
        removeRecipeByName("gregtech:electric_pump_lv_styrene_butadiene_rubber");
        removeRecipeByName("gregtech:electric_pump_mv_rubber");
        removeRecipeByName("gregtech:electric_pump_mv_silicone_rubber");
        removeRecipeByName("gregtech:electric_pump_mv_styrene_butadiene_rubber");
        removeRecipeByName("gregtech:electric_pump_hv_rubber");
        removeRecipeByName("gregtech:electric_pump_hv_silicone_rubber");
        removeRecipeByName("gregtech:electric_pump_hv_styrene_butadiene_rubber");
        removeRecipeByName("gregtech:electric_pump_ev_rubber");
        removeRecipeByName("gregtech:electric_pump_ev_silicone_rubber");
        removeRecipeByName("gregtech:electric_pump_ev_styrene_butadiene_rubber");
        removeRecipeByName("gregtech:electric_pump_iv_rubber");
        removeRecipeByName("gregtech:electric_pump_iv_silicone_rubber");
        removeRecipeByName("gregtech:electric_pump_iv_styrene_butadiene_rubber");
        removeRecipeByName("gregtech:conveyor_module_lv_rubber");
        removeRecipeByName("gregtech:conveyor_module_lv_silicone_rubber");
        removeRecipeByName("gregtech:conveyor_module_lv_styrene_butadiene_rubber");
        removeRecipeByName("gregtech:conveyor_module_mv_rubber");
        removeRecipeByName("gregtech:conveyor_module_mv_silicone_rubber");
        removeRecipeByName("gregtech:conveyor_module_mv_styrene_butadiene_rubber");
        removeRecipeByName("gregtech:conveyor_module_hv_rubber");
        removeRecipeByName("gregtech:conveyor_module_hv_silicone_rubber");
        removeRecipeByName("gregtech:conveyor_module_hv_styrene_butadiene_rubber");
        removeRecipeByName("gregtech:conveyor_module_ev_rubber");
        removeRecipeByName("gregtech:conveyor_module_ev_silicone_rubber");
        removeRecipeByName("gregtech:conveyor_module_ev_styrene_butadiene_rubber");
        removeRecipeByName("gregtech:conveyor_module_iv_rubber");
        removeRecipeByName("gregtech:conveyor_module_iv_silicone_rubber");
        removeRecipeByName("gregtech:conveyor_module_iv_styrene_butadiene_rubber");
        removeRecipeByName("gregtech:emitter_lv");
        removeRecipeByName("gregtech:emitter_mv");
        removeRecipeByName("gregtech:emitter_hv");
        removeRecipeByName("gregtech:emitter_ev");
        removeRecipeByName("gregtech:emitter_iv");
        removeRecipeByName("gregtech:field_generator_lv");
        removeRecipeByName("gregtech:field_generator_mv");
        removeRecipeByName("gregtech:field_generator_hv");
        removeRecipeByName("gregtech:field_generator_ev");
        removeRecipeByName("gregtech:field_generator_iv");
        removeRecipeByName("gregtech:sensor_lv");
        removeRecipeByName("gregtech:sensor_mv");
        removeRecipeByName("gregtech:sensor_hv");
        removeRecipeByName("gregtech:sensor_ev");
        removeRecipeByName("gregtech:sensor_iv");

        Material[] cables = new Material[]{Tin, Copper, Gold, Aluminium, Tungsten};
        Material[] cablesMotor = new Material[]{Tin, Copper, Silver, Aluminium, Tungsten};
        Material[] materials = new Material[]{Steel, Aluminium, StainlessSteel, Titanium, TungstenSteel};
        Material[] motorRodsMagnetic = new Material[]{SteelMagnetic, SteelMagnetic, SteelMagnetic, NeodymiumMagnetic, NeodymiumMagnetic};
        Material[] motorWires = new Material[]{Copper, Cupronickel, Electrum, Kanthal, Graphene};
        Material[] pumpScrew = new Material[]{Tin, Bronze, Steel, StainlessSteel, TungstenSteel};
        Material[] pumpPipe = new Material[]{Bronze, Steel, StainlessSteel, Titanium, TungstenSteel};
        ItemStack[] fgGem = new ItemStack[]{OreDictUnifier.get(gem, EnderPearl), OreDictUnifier.get(gem, EnderEye), QUANTUM_EYE.getStackForm(), OreDictUnifier.get(gem, NetherStar), QUANTUM_STAR.getStackForm()};
        Material[] superCons = new Material[]{ManganesePhosphide, MagnesiumDiboride, MercuryBariumCalciumCuprate, UraniumTriplatinum, SamariumIronArsenicOxide};
        Material[] emitterRod = new Material[]{Brass, Electrum, Chrome, Platinum, Iridium};
        ItemStack[] emitterGem = new ItemStack[]{OreDictUnifier.get(gem, Quartzite), OreDictUnifier.get(gemFlawless, Emerald), OreDictUnifier.get(gem, EnderEye), QUANTUM_EYE.getStackForm(), QUANTUM_STAR.getStackForm()};


        ///*
        for (int i = 0; i < 5; i++) {
            // Motors
            if (i == 0) {
                GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,
                        OreDictUnifier.get(cableGtSingle, cablesMotor[i], 2),
                        OreDictUnifier.get(stick, materials[i], 2),
                        OreDictUnifier.get(stick, motorRodsMagnetic[i]),
                        OreDictUnifier.get(wireGtSingle, motorWires[i], 4));
                GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,
                        OreDictUnifier.get(cableGtSingle, cablesMotor[i], 2),
                        OreDictUnifier.get(stick, Iron, 2),
                        OreDictUnifier.get(stick, IronMagnetic),
                        OreDictUnifier.get(wireGtSingle, motorWires[i], 4));
            } else if (i == 1) {
                GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,
                        OreDictUnifier.get(cableGtSingle, cablesMotor[i], 2),
                        OreDictUnifier.get(stick, materials[i], 2),
                        OreDictUnifier.get(stick, motorRodsMagnetic[i]),
                        OreDictUnifier.get(wireGtDouble, motorWires[i], 4));
            } else {
                GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,
                        OreDictUnifier.get(cableGtDouble, cablesMotor[i], 2),
                        OreDictUnifier.get(stick, materials[i], 2),
                        OreDictUnifier.get(stick, motorRodsMagnetic[i]),
                        OreDictUnifier.get(wireGtDouble, motorWires[i], 4));
            }

            // Pistons
            GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,
                    OreDictUnifier.get(stick,materials[i], 2),
                    OreDictUnifier.get(cableGtSingle, cables[i], 2),
                    OreDictUnifier.get(plate, materials[i], 3),
                    OreDictUnifier.get(gearSmall, materials[i], 1),
                    motors[i].getStackForm(1));


            //TODO these cannot be removed
            //GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,
            //    OreDictUnifier.get(cableGtSingle, cables[i], 3),
            //    OreDictUnifier.get(stick, materials[i], 2),
            //    motors[i].getStackForm(2),
            //    pistons[i].getStackForm(1),
            //    OreDictUnifier.get(circuit, tierCircuitNames[i+1]));

            // Pumps & Conveyors
            for (int j = 0; j < 3; j++) {
                if (!(i == 4 && j == 0)) {
                    GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,
                            OreDictUnifier.get(cableGtSingle, cables[i]),
                            OreDictUnifier.get(pipeNormalFluid, pumpPipe[i]),
                            OreDictUnifier.get(screw, pumpScrew[i]),
                            OreDictUnifier.get(rotor, pumpScrew[i]),
                            OreDictUnifier.get(ring, rubbers[j], 2),
                            motors[i].getStackForm());

                    //TODO these cannot be removed
//                    GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,
//                         OreDictUnifier.get(cableGtSingle, cables[i]),
//                         OreDictUnifier.get(plate, rubbers[j], 6),
//                            IntCircuitIngredient.getIntegratedCircuit(1),
//                         motors[i].getStackForm(2));
                }
            }

            // Field Gens
            if (i < 3) {
                //TODO these cannot be removed
//                GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,
//                     fgGem[i],
//                     OreDictUnifier.get(plate, materials[i], 2),
//                     OreDictUnifier.get(circuit, tierCircuitNames[i+1], 2),
//                     OreDictUnifier.get(wireGtQuadruple, superCons[i], 4));
            } else {
                //TODO these cannot be removed
//                GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,
//                     fgGem[i],
//                     OreDictUnifier.get(plateDouble, materials[i], 2),
//                     OreDictUnifier.get(circuit, tierCircuitNames[i+1], 2),
//                     OreDictUnifier.get(wireGtQuadruple, superCons[i], 4));
            }

            // Emitters
            //TODO these cannot be removed
//            GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,
//                OreDictUnifier.get(stick, emitterRod[i], 4),
//                OreDictUnifier.get(cableGtSingle, cables[i], 2),
//                OreDictUnifier.get(circuit, tierCircuitNames[i+1], 2),
//                IntCircuitIngredient.getIntegratedCircuit(1),
//                emitterGem[i]);

            // Sensors
            //TODO these cannot be removed
//            GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,
//                OreDictUnifier.get(stick, emitterRod[i]),
//                OreDictUnifier.get(plate, materials[i], 4),
//                OreDictUnifier.get(circuit, tierCircuitNames[i+1]),
//                emitterGem[i]);

            // Fluid Regulators
            //TODO these cannot be removed
//            GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,
//                IntCircuitIngredient.getIntegratedCircuit(1),
//                OreDictUnifier.get(circuit, tierCircuitNames[i+1], 2),
//                pump[i].getStackForm());
        }
        //*/
    }
}
