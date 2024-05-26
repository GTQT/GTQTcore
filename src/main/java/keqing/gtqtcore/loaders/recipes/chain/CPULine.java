package keqing.gtqtcore.loaders.recipes.chain;
import appeng.client.render.effects.AssemblerFX;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.common.items.MetaItems;
import gregtech.api.unification.material.MarkerMaterials.Color;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTElectrobath;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;

import static gregtech.common.blocks.MetaBlocks.OPTICAL_PIPES;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.items.MetaItems.ELECTRIC_MOTOR_IV;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static gregtech.common.metatileentities.MetaTileEntities.ITEM_IMPORT_BUS;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.LASER_ENGRAVING;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.electrode;
import static keqing.gtqtcore.common.block.blocks.GTQTADVGlass.CasingType.SILICATE_GLASS;
import static keqing.gtqtcore.common.block.blocks.GTQTStepper.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.GTQTStepper.CasingType.F_LASER_MKV;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.RETICLE_POWER_INTEGRATED_CIRCUIT;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.*;

public class CPULine {
    public static void init() {
        Assembler();    //机器加工
        LaserEngraving(); //光掩模
        LaserStepper();  //光刻
        Pre();          //基板
        Silicon();      //晶圆
        AE();
    }

    private static void Assembler() {
        //四氟乙烯
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust,CopperCl)
                .fluidInputs(Chloroform.getFluid(2000))
                .fluidInputs(HydrofluoricAcid.getFluid(4000))
                .fluidOutputs(HydrochloricAcid.getFluid(6000))
                .fluidOutputs(Tetrafluoroethylene.getFluid(1000))
                .duration(120).EUt(120).buildAndRegister();
        //
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm(4))
                .input(EMITTER_LV, 4)
                .input(SENSOR_LV, 4)
                .input(circuit, MarkerMaterials.Tier.MV, 4)
                .input(plate, Invar, 32)
                .input(gear, Aluminium, 32)
                .input(OPTICAL_PIPES[0], 8)
                .fluidInputs(PolyvinylChloride.getFluid(L * 4))
                .output(GTQTMetaTileEntities.STEPPER)
                .duration(200).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm(4))
                .input(EMITTER_LV, 4)
                .input(FIELD_GENERATOR_LV, 4)
                .input(circuit, MarkerMaterials.Tier.MV, 4)
                .input(plate, Invar, 32)
                .input(gear, Aluminium, 32)
                .input(OPTICAL_PIPES[0], 8)
                .fluidInputs(PolyvinylChloride.getFluid(L * 4))
                .output(GTQTMetaTileEntities.LASER_ENGRAVING)
                .duration(200).EUt(120).buildAndRegister();


        //紫外
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[1].getStackForm())
                .input(EMITTER_LV, 8)
                .input(circuit, MarkerMaterials.Tier.LV,8)
                .input(plate, Steel, 16)
                .fluidInputs(Polyethylene.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.STEPPER.getItemVariant(LASER_MKI))
                .duration(2000).EUt(30).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm())
                .input(EMITTER_MV, 8)
                .input(circuit, MarkerMaterials.Tier.MV,8)
                .input(plate, Aluminium, 16)
                .fluidInputs(PolyvinylChloride.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.STEPPER.getItemVariant(LASER_MKII))
                .scannerResearch(b -> b
                        .researchStack(EMITTER_MV.getStackForm())
                        .EUt(VA[MV]))
                .duration(200).EUt(120).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[3].getStackForm())
                .input(EMITTER_HV, 8)
                .input(circuit, MarkerMaterials.Tier.HV,8)
                .input(plate, StainlessSteel, 16)
                .fluidInputs(Epoxy.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.STEPPER.getItemVariant(LASER_MKIII))
                .scannerResearch(b -> b
                        .researchStack(EMITTER_HV.getStackForm())
                        .EUt(VA[HV]))
                .duration(200).EUt(480).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[4].getStackForm())
                .input(EMITTER_EV, 8)
                .input(circuit, MarkerMaterials.Tier.EV,8)
                .input(plate, Titanium, 16)
                .fluidInputs(ReinforcedEpoxyResin.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.STEPPER.getItemVariant(LASER_MKIV))
                .scannerResearch(b -> b
                        .researchStack(EMITTER_EV.getStackForm())
                        .EUt(VA[EV]))
                .duration(200).EUt(1960).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[5].getStackForm())
                .input(EMITTER_IV, 8)
                .input(circuit, MarkerMaterials.Tier.IV,8)
                .input(plate, TungstenSteel, 16)
                .fluidInputs(Polytetrafluoroethylene.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.STEPPER.getItemVariant(LASER_MKV))
                .scannerResearch(b -> b
                        .researchStack(EMITTER_IV.getStackForm())
                        .EUt(VA[IV]))
                .duration(200).EUt(7680).buildAndRegister();


        //射频
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[1].getStackForm())
                .input(FIELD_GENERATOR_LV, 8)
                .input(circuit, MarkerMaterials.Tier.LV,8)
                .input(plate, Steel, 16)
                .fluidInputs(Polyethylene.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.STEPPER.getItemVariant(F_LASER_MKI))
                .duration(2000).EUt(30).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm())
                .input(FIELD_GENERATOR_MV, 8)
                .input(circuit, MarkerMaterials.Tier.MV,8)
                .input(plate, Aluminium, 16)
                .fluidInputs(PolyvinylChloride.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.STEPPER.getItemVariant(F_LASER_MKII))
                .scannerResearch(b -> b
                        .researchStack(FIELD_GENERATOR_MV.getStackForm())
                        .EUt(VA[MV]))
                .duration(200).EUt(120).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[3].getStackForm())
                .input(FIELD_GENERATOR_HV, 8)
                .input(circuit, MarkerMaterials.Tier.HV,8)
                .input(plate, StainlessSteel, 16)
                .fluidInputs(Epoxy.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.STEPPER.getItemVariant(F_LASER_MKIII))
                .scannerResearch(b -> b
                        .researchStack(FIELD_GENERATOR_HV.getStackForm())
                        .EUt(VA[HV]))
                .duration(200).EUt(480).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[4].getStackForm())
                .input(FIELD_GENERATOR_EV, 8)
                .input(circuit, MarkerMaterials.Tier.EV,8)
                .input(plate, Titanium, 16)
                .fluidInputs(ReinforcedEpoxyResin.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.STEPPER.getItemVariant(F_LASER_MKIV))
                .scannerResearch(b -> b
                        .researchStack(FIELD_GENERATOR_EV.getStackForm())
                        .EUt(VA[EV]))
                .duration(200).EUt(1960).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HULL[5].getStackForm())
                .input(FIELD_GENERATOR_IV, 8)
                .input(circuit, MarkerMaterials.Tier.IV,8)
                .input(plate, TungstenSteel, 16)
                .fluidInputs(Polytetrafluoroethylene.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.STEPPER.getItemVariant(F_LASER_MKV))
                .scannerResearch(b -> b
                        .researchStack(FIELD_GENERATOR_IV.getStackForm())
                        .EUt(VA[IV]))
                .duration(200).EUt(7680).buildAndRegister();


        //自净
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[1].getStackForm())
                .input(ELECTRIC_MOTOR_LV, 8)
                .input(circuit, MarkerMaterials.Tier.LV,1)
                .input(rotor, Steel, 16)
                .fluidInputs(Polyethylene.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.STEPPER.getItemVariant(CLEAN_MKI))
                .duration(2000).EUt(30).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm())
                .input(ELECTRIC_MOTOR_MV, 8)
                .input(circuit, MarkerMaterials.Tier.MV,1)
                .input(rotor, Aluminium, 16)
                .fluidInputs(PolyvinylChloride.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.STEPPER.getItemVariant(CLEAN_MKII))
                .duration(200).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[3].getStackForm())
                .input(ELECTRIC_MOTOR_HV, 8)
                .input(circuit, MarkerMaterials.Tier.HV,1)
                .input(rotor, StainlessSteel, 16)
                .fluidInputs(Epoxy.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.STEPPER.getItemVariant(CLEAN_MKIII))
                .duration(200).EUt(480).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[4].getStackForm())
                .input(ELECTRIC_MOTOR_EV, 8)
                .input(circuit, MarkerMaterials.Tier.EV,1)
                .input(rotor, Titanium, 16)
                .fluidInputs(ReinforcedEpoxyResin.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.STEPPER.getItemVariant(CLEAN_MKIV))
                .duration(200).EUt(1960).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[5].getStackForm())
                .input(ELECTRIC_MOTOR_IV, 8)
                .input(circuit, MarkerMaterials.Tier.IV,1)
                .input(rotor, TungstenSteel, 16)
                .fluidInputs(Polytetrafluoroethylene.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.STEPPER.getItemVariant(CLEAN_MKV))
                .duration(200).EUt(7680).buildAndRegister();
    }

    private static void AE() {
        BLAST_RECIPES.recipeBuilder()
                .input(AE_WAFER)
                .input(dust,Gold,16)
                .output(AE_WAFERA)
                .blastFurnaceTemp(3600)
                .fluidInputs()
                .duration(9000)
                .EUt(VA[MV])
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(AE_WAFER)
                .input(dust,Fluix,16)
                .output(AE_WAFERB)
                .blastFurnaceTemp(3600)
                .fluidInputs()
                .duration(9000)
                .EUt(VA[MV])
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(AE_WAFER)
                .input(dust,Diamond,16)
                .output(AE_WAFERC)
                .blastFurnaceTemp(3600)
                .fluidInputs()
                .duration(9000)
                .EUt(VA[MV])
                .buildAndRegister();

        CUTTER_RECIPES.recipeBuilder()
                .input(AE_SILICON)
                .output(AE_WAFER,16)
                .fluidInputs(Lubricant.getFluid(4000))
                .duration(9000)
                .EUt(VA[MV])
                .buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEA)
                .Laser(600)
                .input(AE_WAFERA).output(AE_A,1)
                .CWUt(16).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEB)
                .Laser(600)
                .input(AE_WAFERB).output(AE_B,1)
                .CWUt(16).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEC)
                .Laser(600)
                .input(AE_WAFERC).output(AE_C,1)
                .CWUt(16).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();



        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEA)
                .Laser(200)
                .input(AE_WAFERA).output(AE_A,4)
                .CWUt(32).fluidInputs(SU8_Photoresist.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEB)
                .Laser(200)
                .input(AE_WAFERB).output(AE_B,4)
                .CWUt(32).fluidInputs(SU8_Photoresist.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEC)
                .Laser(200)
                .input(AE_WAFERC).output(AE_C,4)
                .CWUt(32).fluidInputs(SU8_Photoresist.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();



        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEA)
                .Laser(60)
                .input(AE_WAFERA).output(AE_A,16)
                .CWUt(64).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEB)
                .Laser(60)
                .input(AE_WAFERB).output(AE_B,16)
                .CWUt(64).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEC)
                .Laser(60)
                .input(AE_WAFERC).output(AE_C,16)
                .CWUt(64).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

    }

    private static void LaserStepper() {
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[MV]).notConsumable(RETICLE_INTEGRATED_LOGIC_CIRCUIT)
                .Laser(800)
                .input(SILICON_WAFER).output(INTEGRATED_LOGIC_CIRCUIT_WAFER)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_INTEGRATED_LOGIC_CIRCUIT)
                .Laser(600)
                .input(PHOSPHORUS_WAFER).output(INTEGRATED_LOGIC_CIRCUIT_WAFER,4)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_INTEGRATED_LOGIC_CIRCUIT)
                .Laser(400)
                .input(NAQUADAH_WAFER).output(INTEGRATED_LOGIC_CIRCUIT_WAFER,8)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_INTEGRATED_LOGIC_CIRCUIT)
                .Laser(200)
                .input(NEUTRONIUM_WAFER).output(INTEGRATED_LOGIC_CIRCUIT_WAFER,16)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[MV]).notConsumable(RETICLE_RANDOM_ACCESS_MEMORY)
                .Laser(800)
                .input(SILICON_WAFER).output(RANDOM_ACCESS_MEMORY_WAFER)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_RANDOM_ACCESS_MEMORY)
                .Laser(600)
                .input(PHOSPHORUS_WAFER).output(RANDOM_ACCESS_MEMORY_WAFER,4)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_RANDOM_ACCESS_MEMORY)
                .Laser(400)
                .input(NAQUADAH_WAFER).output(RANDOM_ACCESS_MEMORY_WAFER,8)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_RANDOM_ACCESS_MEMORY)
                .Laser(200)
                .input(NEUTRONIUM_WAFER).output(RANDOM_ACCESS_MEMORY_WAFER,16)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[MV]).notConsumable(RETICLE_CENTRAL_PROCESSING_UNIT)
                .Laser(800)
                .input(SILICON_WAFER).output(CENTRAL_PROCESSING_UNIT_WAFER)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_CENTRAL_PROCESSING_UNIT)
                .Laser(600)
                .input(PHOSPHORUS_WAFER).output(CENTRAL_PROCESSING_UNIT_WAFER,4)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_CENTRAL_PROCESSING_UNIT)
                .Laser(400)
                .input(NAQUADAH_WAFER).output(CENTRAL_PROCESSING_UNIT_WAFER,8)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_CENTRAL_PROCESSING_UNIT)
                .Laser(200)
                .input(NEUTRONIUM_WAFER).output(CENTRAL_PROCESSING_UNIT_WAFER,16)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[MV]).notConsumable(RETICLE_ULTRA_LOW_POWER_INTEGRATED_CIRCUIT)
                .Laser(800)
                .input(SILICON_WAFER).output(ULTRA_LOW_POWER_INTEGRATED_CIRCUIT_WAFER)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_ULTRA_LOW_POWER_INTEGRATED_CIRCUIT)
                .Laser(600)
                .input(PHOSPHORUS_WAFER).output(ULTRA_LOW_POWER_INTEGRATED_CIRCUIT_WAFER,4)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_ULTRA_LOW_POWER_INTEGRATED_CIRCUIT)
                .Laser(400)
                .input(NAQUADAH_WAFER).output(ULTRA_LOW_POWER_INTEGRATED_CIRCUIT_WAFER,8)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_ULTRA_LOW_POWER_INTEGRATED_CIRCUIT)
                .Laser(200)
                .input(NEUTRONIUM_WAFER).output(ULTRA_LOW_POWER_INTEGRATED_CIRCUIT_WAFER,16)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[MV]).notConsumable(RETICLE_LOW_POWER_INTEGRATED_CIRCUIT)
                .Laser(800)
                .input(SILICON_WAFER).output(LOW_POWER_INTEGRATED_CIRCUIT_WAFER)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_LOW_POWER_INTEGRATED_CIRCUIT) .Laser(600)
                .input(PHOSPHORUS_WAFER).output(LOW_POWER_INTEGRATED_CIRCUIT_WAFER,4)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_LOW_POWER_INTEGRATED_CIRCUIT) .Laser(400)
                .input(NAQUADAH_WAFER).output(LOW_POWER_INTEGRATED_CIRCUIT_WAFER,8)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_LOW_POWER_INTEGRATED_CIRCUIT) .Laser(200)
                .input(NEUTRONIUM_WAFER).output(LOW_POWER_INTEGRATED_CIRCUIT_WAFER,16)
                .CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[MV]).notConsumable(RETICLE_SIMPLE_SYSTEM_ON_CHIP) .Laser(800)
                .input(SILICON_WAFER).output(SIMPLE_SYSTEM_ON_CHIP_WAFER)
                .CWUt(8).fluidInputs(SU8_Photoresist.getFluid(1000)).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_SIMPLE_SYSTEM_ON_CHIP) .Laser(600)
                .input(PHOSPHORUS_WAFER).output(SIMPLE_SYSTEM_ON_CHIP_WAFER,4)
                .CWUt(8).fluidInputs(SU8_Photoresist.getFluid(1000)).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_SIMPLE_SYSTEM_ON_CHIP) .Laser(400)
                .input(NAQUADAH_WAFER).output(SIMPLE_SYSTEM_ON_CHIP_WAFER,8)
                .CWUt(8).fluidInputs(SU8_Photoresist.getFluid(1000)).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_SIMPLE_SYSTEM_ON_CHIP) .Laser(200)
                .input(NEUTRONIUM_WAFER).output(SIMPLE_SYSTEM_ON_CHIP_WAFER,16)
                .CWUt(8).fluidInputs(SU8_Photoresist.getFluid(1000)).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_NAND_MEMORY_CHIP) .Laser(600)
                .input(PHOSPHORUS_WAFER).output(NAND_MEMORY_CHIP_WAFER,1)
                .CWUt(32).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_NAND_MEMORY_CHIP) .Laser(400)
                .input(NAQUADAH_WAFER).output(NAND_MEMORY_CHIP_WAFER,4)
                .CWUt(32).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_NAND_MEMORY_CHIP) .Laser(200)
                .input(NEUTRONIUM_WAFER).output(NAND_MEMORY_CHIP_WAFER,8)
                .CWUt(32).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_NOR_MEMORY_CHIP) .Laser(600)
                .input(PHOSPHORUS_WAFER).output(NOR_MEMORY_CHIP_WAFER,1)
                .CWUt(32).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_NOR_MEMORY_CHIP) .Laser(400)
                .input(NAQUADAH_WAFER).output(NOR_MEMORY_CHIP_WAFER,4)
                .CWUt(32).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_NOR_MEMORY_CHIP) .Laser(200)
                .input(NEUTRONIUM_WAFER).output(NOR_MEMORY_CHIP_WAFER,8)
                .CWUt(32).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_POWER_INTEGRATED_CIRCUIT) .Laser(400)
                .input(PHOSPHORUS_WAFER).output(POWER_INTEGRATED_CIRCUIT_WAFER,1)
                .CWUt(32).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_POWER_INTEGRATED_CIRCUIT).Laser(200)
                .input(NAQUADAH_WAFER).output(POWER_INTEGRATED_CIRCUIT_WAFER,4)
                .CWUt(32).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_POWER_INTEGRATED_CIRCUIT).Laser(60)
                .input(NEUTRONIUM_WAFER).output(POWER_INTEGRATED_CIRCUIT_WAFER,8)
                .CWUt(32).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_SYSTEM_ON_CHIP).Laser(400)
                .input(PHOSPHORUS_WAFER).output(SYSTEM_ON_CHIP_WAFER,1)
                .CWUt(32).fluidInputs(Xmt.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_SYSTEM_ON_CHIP).Laser(200)
                .input(NAQUADAH_WAFER).output(SYSTEM_ON_CHIP_WAFER,4)
                .CWUt(32).fluidInputs(Xmt.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_SYSTEM_ON_CHIP).Laser(60)
                .input(NEUTRONIUM_WAFER).output(SYSTEM_ON_CHIP_WAFER,8)
                .CWUt(32).fluidInputs(Xmt.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_ADVANCED_SYSTEM_ON_CHIP).Laser(60)
                .input(NAQUADAH_WAFER).output(ADVANCED_SYSTEM_ON_CHIP_WAFER,1)
               .CWUt(128).fluidInputs(Xmt.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_ADVANCED_SYSTEM_ON_CHIP).Laser(40)
                .input(NEUTRONIUM_WAFER).output(ADVANCED_SYSTEM_ON_CHIP_WAFER,4)
               .CWUt(128).fluidInputs(Xmt.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_HIGHLY_ADVANCED_SYSTEM_ON_CHIP).Laser(20)
                .input(NEUTRONIUM_WAFER).output(HIGHLY_ADVANCED_SOC_WAFER,1)
                .CWUt(512).fluidInputs(Zrbtmst.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();


        //cpu EV
        PRECISION_SPINNING.recipeBuilder()
                .input(CENTRAL_PROCESSING_UNIT_WAFER)
                .input(CARBON_FIBERS, 16)
                .fluidInputs(Glowstone.getFluid(L * 4))
                .output(RETICLE_NANO_CENTRAL_PROCESSING_UNIT)
                .cleanroom(CleanroomType.CLEANROOM)
                .CWUt(120)
                .duration(400).EUt(VA[EV]).buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .input(NANO_CENTRAL_PROCESSING_UNIT_WAFER)
                .input(QUANTUM_EYE, 2)
                .fluidInputs(GalliumArsenide.getFluid(L * 2))
                .output(RETICLE_QBIT_CENTRAL_PROCESSING_UNIT)
                .cleanroom(CleanroomType.CLEANROOM)
                .CWUt(120)
                .duration(300).EUt(VA[EV]).buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .input(NANO_CENTRAL_PROCESSING_UNIT_WAFER)
                .input(dust, IndiumGalliumPhosphide)
                .fluidInputs(Radon.getFluid(50))
                .output(RETICLE_QBIT_CENTRAL_PROCESSING_UNIT)
                .cleanroom(CleanroomType.CLEANROOM)
                .CWUt(120)
                .duration(400).EUt(VA[EV]).buildAndRegister();

        //
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_NANO_CENTRAL_PROCESSING_UNIT)
                .input(SILICON_WAFER)
                .output(NANO_CENTRAL_PROCESSING_UNIT_WAFER)
                .CWUt(128).Laser(400).fluidInputs(SU8_Photoresist.getFluid(10000))
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_NANO_CENTRAL_PROCESSING_UNIT)
                .input(PHOSPHORUS_WAFER)
                .output(NANO_CENTRAL_PROCESSING_UNIT_WAFER,4)
                .CWUt(256).Laser(200).fluidInputs(SU8_Photoresist.getFluid(10000))
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_NANO_CENTRAL_PROCESSING_UNIT)
                .input(NAQUADAH_WAFER)
                .output(NANO_CENTRAL_PROCESSING_UNIT_WAFER,8)
                .CWUt(1024).Laser(80).fluidInputs(SU8_Photoresist.getFluid(10000))
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_NANO_CENTRAL_PROCESSING_UNIT)
                .input(NEUTRONIUM_WAFER)
                .output(NANO_CENTRAL_PROCESSING_UNIT_WAFER,16)
                .CWUt(2048).Laser(40).fluidInputs(SU8_Photoresist.getFluid(10000))
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();


        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_QBIT_CENTRAL_PROCESSING_UNIT)
                .input(SILICON_WAFER)
                .output(QUBIT_CENTRAL_PROCESSING_UNIT_WAFER)
                .CWUt(128).Laser(400).fluidInputs(SU8_Photoresist.getFluid(10000))
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_QBIT_CENTRAL_PROCESSING_UNIT)
                .input(PHOSPHORUS_WAFER)
                .output(QUBIT_CENTRAL_PROCESSING_UNIT_WAFER,4)
                .CWUt(256).Laser(200).fluidInputs(SU8_Photoresist.getFluid(10000))
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_QBIT_CENTRAL_PROCESSING_UNIT)
                .input(NAQUADAH_WAFER)
                .output(QUBIT_CENTRAL_PROCESSING_UNIT_WAFER,8)
                .CWUt(1024).Laser(80).fluidInputs(SU8_Photoresist.getFluid(10000))
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_QBIT_CENTRAL_PROCESSING_UNIT)
                .input(NEUTRONIUM_WAFER)
                .output(QUBIT_CENTRAL_PROCESSING_UNIT_WAFER,16)
                .CWUt(2048).Laser(40).fluidInputs(SU8_Photoresist.getFluid(10000))
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        //pic
        PRECISION_SPINNING.recipeBuilder()
                .input(POWER_INTEGRATED_CIRCUIT_WAFER)
                .input(dust, IndiumGalliumPhosphide, 2)
                .fluidInputs(VanadiumGallium.getFluid(L * 2))
                .output(RETICLE_ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT)
                .cleanroom(CleanroomType.CLEANROOM)
                .CWUt(240)
                .duration(600).EUt(VA[IV]).buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .input(HIGH_POWER_INTEGRATED_CIRCUIT_WAFER)
                .input(dust, IndiumGalliumPhosphide, 8)
                .fluidInputs(Naquadah.getFluid(L * 4))
                .output(RETICLE_HIGH_POWER_INTEGRATED_CIRCUIT)
                .cleanroom(CleanroomType.CLEANROOM)
                .CWUt(480)
                .duration(600).EUt(VA[LuV]).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).input(RETICLE_ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT)
                .input(SILICON_WAFER)
                .output(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT_WAFER)
                .CWUt(128).Laser(400).fluidInputs(SU8_Photoresist.getFluid(10000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).input(RETICLE_ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT)
                .input(PHOSPHORUS_WAFER)
                .output(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT_WAFER,4)
                .CWUt(256).Laser(200).fluidInputs(SU8_Photoresist.getFluid(10000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).input(RETICLE_ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT)
                .input(NAQUADAH_WAFER)
                .output(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT_WAFER,8)
                .CWUt(1024).Laser(80).fluidInputs(SU8_Photoresist.getFluid(10000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).input(RETICLE_ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT)
                .input(NEUTRONIUM_WAFER)
                .output(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT_WAFER,16)
                .CWUt(2048).Laser(40).fluidInputs(SU8_Photoresist.getFluid(10000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).input(RETICLE_HIGH_POWER_INTEGRATED_CIRCUIT)
                .input(SILICON_WAFER)
                .output(HIGH_POWER_INTEGRATED_CIRCUIT_WAFER)
                .CWUt(128).Laser(400).fluidInputs(SU8_Photoresist.getFluid(10000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).input(RETICLE_HIGH_POWER_INTEGRATED_CIRCUIT)
                .input(PHOSPHORUS_WAFER)
                .output(HIGH_POWER_INTEGRATED_CIRCUIT_WAFER,4)
                .CWUt(256).Laser(200).fluidInputs(SU8_Photoresist.getFluid(10000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).input(RETICLE_HIGH_POWER_INTEGRATED_CIRCUIT)
                .input(NAQUADAH_WAFER)
                .output(HIGH_POWER_INTEGRATED_CIRCUIT_WAFER,8)
                .CWUt(1024).Laser(80).fluidInputs(SU8_Photoresist.getFluid(10000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).input(RETICLE_HIGH_POWER_INTEGRATED_CIRCUIT)
                .input(NEUTRONIUM_WAFER)
                .output(HIGH_POWER_INTEGRATED_CIRCUIT_WAFER,16)
                .CWUt(2048).Laser(40).fluidInputs(SU8_Photoresist.getFluid(10000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        //TODO gcys pic

    }

    private static void LaserEngraving() {
        //初加工 CVD法
        REACTION_FURNACE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.ADV_GLASS.getItemVariant(SILICATE_GLASS))
                .output(RETICLE_SILICON)
                .fluidInputs(SiliconTetrachloride.getFluid(16000))
                .duration(1000)
                .EUt(120)
                .buildAndRegister();



        //TODO
        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[HV]).input(RETICLE_SILICON).CWUt(8)
            .fluidInputs(Water.getFluid(10000))
            .notConsumable(EMITTER_HV).output(AE_RETICLEA)
            .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[HV]).input(RETICLE_SILICON).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(SENSOR_HV).output(AE_RETICLEB)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[HV]).input(RETICLE_SILICON).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(FIELD_GENERATOR_HV).output(AE_RETICLEC)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();




        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Blue).output(RETICLE_INTEGRATED_LOGIC_CIRCUIT).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Red).output(RETICLE_RANDOM_ACCESS_MEMORY).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Purple).output(RETICLE_ADVANCED_SYSTEM_ON_CHIP)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();


        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.LightGray).output(RETICLE_CENTRAL_PROCESSING_UNIT).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.LightBlue).output(RETICLE_LOW_POWER_INTEGRATED_CIRCUIT).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Cyan)
                .cleanroom(CleanroomType.CLEANROOM).output(RETICLE_NAND_MEMORY_CHIP).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Gray).output(RETICLE_NOR_MEMORY_CHIP)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Brown).output(RETICLE_POWER_INTEGRATED_CIRCUIT)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Lime).output(RETICLE_SIMPLE_SYSTEM_ON_CHIP).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Magenta).output(RETICLE_SYSTEM_ON_CHIP)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();


        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Green).output(RETICLE_ULTRA_LOW_POWER_INTEGRATED_CIRCUIT).buildAndRegister();


        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Orange).output(RETICLE_HIGHLY_ADVANCED_SYSTEM_ON_CHIP)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

    }

    private static void Silicon()
    {
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Copper)
                .fluidInputs(Chlorine.getFluid(2000))
                .output(dust,CopperCl,3)
                .duration(200)
                .EUt(30)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust,CopperCl)
                .fluidInputs(Trichlorosilane.getFluid(4000))
                .fluidOutputs(Silane.getFluid(1000))
                .fluidOutputs(SiliconTetrachloride.getFluid(3000))
                .duration(200)
                .EUt(30)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(SiliconTetrachloride.getFluid(1000))
                .input(dust,Sodium,4)
                .output(dust,CSilicon)
                .output(dust,Salt,8)
                .duration(200)
                .EUt(30)
                .buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
                .fluidInputs(SiliconTetrachloride.getFluid(1000))
                .fluidOutputs(Chlorine.getFluid(4000))
                .output(dust, CSilicon, 1)
                .duration(200)
                .EUt(30)
                .buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
                .fluidInputs(Silane.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(4000))
                .output(dust, CSilicon, 1)
                .duration(200)
                .EUt(30)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(120)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,GalliumArsenide,1)
                .input(dust,Boron,1)
                .output(SILICON_BOULE)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(120)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,GalliumArsenide,1)
                .input(dust,Fluix,1)
                .output(AE_SILICON)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(480)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,GalliumArsenide,2)
                .input(dust,Phosphorus,1)
                .output(PHOSPHORUS_BOULE)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(480)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,GalliumArsenide,4)
                .input(dust,Naquadah,1)
                .output(NAQUADAH_BOULE)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(7680)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,GalliumArsenide,8)
                .input(dust,Neutronium,1)
                .output(NEUTRONIUM_BOULE)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(2500)
                .EUt(1960)
                .fluidInputs(Helium.getFluid(4000))
                .input(block,CSilicon,32)
                .input(dust,IndiumGalliumPhosphide,1)
                .input(dust,Boron,1)
                .output(SILICON_BOULE,2)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(120)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,IndiumGalliumPhosphide,1)
                .input(dust,Fluix,1)
                .output(AE_SILICON,2)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(2500)
                .EUt(480)
                .fluidInputs(Helium.getFluid(4000))
                .input(block,CSilicon,32)
                .input(dust,IndiumGalliumPhosphide,2)
                .input(dust,Phosphorus,1)
                .output(PHOSPHORUS_BOULE,2)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(2500)
                .EUt(1960)
                .fluidInputs(Helium.getFluid(4000))
                .input(block,CSilicon,32)
                .input(dust,IndiumGalliumPhosphide,4)
                .input(dust,Naquadah,1)
                .output(NAQUADAH_BOULE,2)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(2500)
                .EUt(7680)
                .fluidInputs(Helium.getFluid(4000))
                .input(block,CSilicon,32)
                .input(dust,IndiumGalliumPhosphide,8)
                .input(dust,Neutronium,1)
                .output(NEUTRONIUM_BOULE,2)
                .buildAndRegister();
    }
    private static void Pre()
    {
        //石墨电极线
        //石墨+沥青=浸渍石墨
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(1000)
                .EUt(8)
                .input(stick,Graphite,16)
                .fluidInputs(HighlyPurifiedCoalTar.getFluid(100))
                .output(IMPREGNATED_GRAPHITE_RODS)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .input(IMPREGNATED_GRAPHITE_RODS)
                .input(dust,Graphite,16)
                .fluidInputs(Asphalt.getFluid(500))
                .output(IMPREGNATED_GRAPHITE_RODSA)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .duration(2000)
                .EUt(120)
                .blastFurnaceTemp(1800)
                .input(IMPREGNATED_GRAPHITE_RODSA)
                .input(dust,Diamond)
                .fluidInputs(Nitrogen.getFluid(1000))
                .output(electrode,Graphite)
                .buildAndRegister();

        //酚醛基板
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .circuitMeta(1)
                .fluidInputs(Phenol.getFluid(1000))
                .fluidInputs(Formaldehyde.getFluid(1000))
                .fluidOutputs(Phenolic.getFluid(2000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .circuitMeta(1)
                .notConsumable(dust,Silver,16)
                .fluidInputs(Methanol.getFluid(1000))
                .fluidOutputs(Formaldehyde.getFluid(100))
                .fluidOutputs(Hydrogen.getFluid(200))
                .buildAndRegister();

        //酚醛基板简化版
        //防腐木板+胶水（浸渍基板）+酚醛
        FORGE_HAMMER_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .input(plank, TreatedWood)
                .output(plate,TreatedWood)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .input(plate,TreatedWood,4)
                .input(foil,Copper,2)
                .fluidInputs(RedAlloy.getFluid(576))
                .fluidInputs(Glue.getFluid(400))
                .output(IMPREGNATED_SUBSTRATE,4)
                .buildAndRegister();

        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .input(IMPREGNATED_SUBSTRATE)
                .fluidInputs(Phenolic.getFluid(250))
                .output(MetaItems.PHENOLIC_BOARD)
                .buildAndRegister();



        //这里是MV阶段的热固化树脂+玻璃纤维=塑料基板的产线
        //这里是玻璃纤维
        MIXER_RECIPES.recipeBuilder()
                .duration(2000)
                .EUt(120)
                .input(dust,QuartzSand)
                .input(dust,Quicklime)
                .input(dust,Uvarovite)
                .input(dust,Pyrope)
                .output(dust,Fiberglass,4)
                .buildAndRegister();

        FLUID_EXTRACTOR_RECIPES.recipeBuilder()
                .duration(20)
                .EUt(120)
                .fluidInputs(Fiberglass.getFluid(288))
                .output(wireFine,Fiberglass,16)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(120)
                .input(wireFine,Fiberglass,4)
                .input(foil,Gold,2)
                .fluidInputs(Epoxy.getFluid(576))
                .fluidInputs(Polyethylene.getFluid(400))
                .output(IMPREGNATED_PLASTIC_SUBSTRATE,4)
                .buildAndRegister();

        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(120)
                .input(IMPREGNATED_PLASTIC_SUBSTRATE)
                .fluidInputs(SulfuricAcid.getFluid(250))
                .output(PLASTIC_BOARD)
                .buildAndRegister();

        //这里是HV阶段的环氧树脂基板
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(480)
                .input(IMPREGNATED_EPOXY)
                .fluidInputs(PhosphoricAcid.getFluid(250))
                .output(EPOXY_BOARD)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(480)
                .input(plate,Polytetrafluoroethylene)
                .input(foil,Platinum,2)
                .fluidInputs(StyreneButadieneRubber.getFluid(400))
                .fluidInputs(Brominatedepoxyresins.getFluid(576))
                .output(IMPREGNATED_EPOXY,4)
                .buildAndRegister();

        //这里是EV阶段的纤维强化电路板
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(1920)
                .input(plate,ReinforcedEpoxyResin,4)
                .fluidInputs(Germanium.getFluid(288))
                .output(LAMINATION_GE,4)
                .buildAndRegister();

        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(1920)
                .input(plate,Zylon,1)
                .fluidInputs(Germanium.getFluid(288))
                .output(LAMINATION_GE,4)
                .buildAndRegister();

        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(1920)
                .input(IMPREGNATED_FIBER)
                .fluidInputs(FluoroantimonicAcid.getFluid(250))
                .output(FIBER_BOARD)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(1920)
                .input(LAMINATION_GE)
                .input(foil,NanometerBariumTitanate,2)
                .fluidInputs(StyreneButadieneRubber.getFluid(400))
                .fluidInputs(Zylon.getFluid(576))
                .output(IMPREGNATED_FIBER,4)
                .buildAndRegister();

    }
}
