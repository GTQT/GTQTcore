package keqing.gtqtcore.loaders.recipes.chain;
import appeng.client.render.effects.AssemblerFX;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.common.items.MetaItems;
import gregtech.api.unification.material.MarkerMaterials.Color;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTElectrobath;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.item.Item;

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
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
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
                .input(gear, Aluminium, 16)
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
                .input(gear, Aluminium, 16)
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
                .duration(200).EUt(1920).buildAndRegister();

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
                .duration(200).EUt(1920).buildAndRegister();

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
                .duration(200).EUt(1920).buildAndRegister();

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
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEA)
                .Laser(1)
                .circuitMeta(1)
                .input(AE_WAFERA).output(AE_A,1)
                .CWUt(16).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEB)
                .Laser(1)
                .circuitMeta(1)
                .input(AE_WAFERB).output(AE_B,1)
                .CWUt(16).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEC)
                .Laser(1)
                .circuitMeta(1)
                .input(AE_WAFERC).output(AE_C,1)
                .CWUt(16).buildAndRegister();



        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEA)
                .Laser(2)
                .circuitMeta(2)
                .input(AE_WAFERA).output(AE_A,4)
                .CWUt(32).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEB)
                .Laser(2)
                .circuitMeta(2)
                .input(AE_WAFERB).output(AE_B,4)
                .CWUt(32).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEC)
                .Laser(2)
                .circuitMeta(2)
                .input(AE_WAFERC).output(AE_C,4)
                .CWUt(32).buildAndRegister();



        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEA)
                .Laser(3)
                .circuitMeta(3)
                .input(AE_WAFERA).output(AE_A,16)
                .CWUt(64).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEB)
                .Laser(3)
                .circuitMeta(3)
                .input(AE_WAFERB).output(AE_B,16)
                .CWUt(64).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEC)
                .Laser(3)
                .circuitMeta(3)
                .input(AE_WAFERC).output(AE_C,16)
                .CWUt(64).buildAndRegister();


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

    }
    public static void Stepper(int tier, int EU, MetaItem<?>.MetaValueItem item1,MetaItem<?>.MetaValueItem item2)
    {
        if(tier<=1) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU]).notConsumable(item1)
                    .Laser(tier)
                    .input(SILICON_WAFER)
                    .output(item2)
                    .CWUt(CWT[tier])
                    .duration(250*tier*EU)
                    .buildAndRegister();
        }
        if(tier<=2) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU + 1]).notConsumable(item1)
                    .Laser(tier)
                    .input(PHOSPHORUS_WAFER)
                    .output(item2, (int)Math.pow(2,2-tier))
                    .CWUt(CWT[tier+1])
                    .duration(500*tier*EU)
                    .buildAndRegister();
        }
        if(tier<=3) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU + 2]).notConsumable(item1)
                    .Laser(tier)
                    .input(NAQUADAH_WAFER)
                    .output(item2, (int)Math.pow(2,3-tier))
                    .CWUt(CWT[tier+2])
                    .duration(1000*tier*EU)
                    .buildAndRegister();
        }
        if(tier<=4) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU + 3]).notConsumable(item1)
                    .Laser(tier)
                    .input(NEUTRONIUM_WAFER)
                    .output(item2, (int)Math.pow(2,4-tier))
                    .CWUt(CWT[tier+3])
                    .duration(2000*tier*EU)
                    .buildAndRegister();
        }
    }
    private static void LaserStepper() {
        Stepper(1,2,RETICLE_INTEGRATED_LOGIC_CIRCUIT,INTEGRATED_LOGIC_CIRCUIT_WAFER);
        Stepper(1,2,RETICLE_RANDOM_ACCESS_MEMORY,RANDOM_ACCESS_MEMORY_WAFER);
        Stepper(1,2,RETICLE_CENTRAL_PROCESSING_UNIT,CENTRAL_PROCESSING_UNIT_WAFER);
        Stepper(1,2,RETICLE_ULTRA_LOW_POWER_INTEGRATED_CIRCUIT,ULTRA_LOW_POWER_INTEGRATED_CIRCUIT_WAFER);
        Stepper(1,2,RETICLE_LOW_POWER_INTEGRATED_CIRCUIT,LOW_POWER_INTEGRATED_CIRCUIT_WAFER);
        Stepper(1,2,RETICLE_SIMPLE_SYSTEM_ON_CHIP,SIMPLE_SYSTEM_ON_CHIP_WAFER);
        Stepper(2,3,RETICLE_NOR_MEMORY_CHIP,NOR_MEMORY_CHIP_WAFER);
        Stepper(2,3,RETICLE_POWER_INTEGRATED_CIRCUIT,POWER_INTEGRATED_CIRCUIT_WAFER);
        Stepper(2,3,RETICLE_NAND_MEMORY_CHIP,NAND_MEMORY_CHIP_WAFER);
        Stepper(2,3,RETICLE_SYSTEM_ON_CHIP,SYSTEM_ON_CHIP_WAFER);
        Stepper(3,3,RETICLE_ADVANCED_SYSTEM_ON_CHIP,ADVANCED_SYSTEM_ON_CHIP_WAFER);
        Stepper(4,3,RETICLE_HIGHLY_ADVANCED_SYSTEM_ON_CHIP,HIGHLY_ADVANCED_SOC_WAFER);


        //cpu EV
        PRECISION_SPINNING.recipeBuilder()
                .input(CENTRAL_PROCESSING_UNIT_WAFER)
                .input(CARBON_FIBERS, 16)
                .fluidInputs(Glowstone.getFluid(L * 4))
                .output(RETICLE_NANO_CENTRAL_PROCESSING_UNIT)

                .CWUt(120)
                .duration(400).EUt(VA[EV]).buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .input(NANO_CENTRAL_PROCESSING_UNIT_WAFER)
                .input(QUANTUM_EYE, 2)
                .fluidInputs(GalliumArsenide.getFluid(L * 2))
                .output(RETICLE_QBIT_CENTRAL_PROCESSING_UNIT)

                .CWUt(120)
                .duration(300).EUt(VA[EV]).buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .input(NANO_CENTRAL_PROCESSING_UNIT_WAFER)
                .input(dust, IndiumGalliumPhosphide)
                .fluidInputs(Radon.getFluid(50))
                .output(RETICLE_QBIT_CENTRAL_PROCESSING_UNIT)

                .CWUt(120)
                .duration(400).EUt(VA[EV]).buildAndRegister();

        //
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_NANO_CENTRAL_PROCESSING_UNIT)
                .input(SILICON_WAFER)
                .output(NANO_CENTRAL_PROCESSING_UNIT_WAFER)
                .CWUt(128).Laser(2)
                .buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_NANO_CENTRAL_PROCESSING_UNIT)
                .input(PHOSPHORUS_WAFER)
                .output(NANO_CENTRAL_PROCESSING_UNIT_WAFER,4)
                .CWUt(256).Laser(3)
                .buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_NANO_CENTRAL_PROCESSING_UNIT)
                .input(NAQUADAH_WAFER)
                .output(NANO_CENTRAL_PROCESSING_UNIT_WAFER,8)
                .CWUt(1024).Laser(4)
                .buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_NANO_CENTRAL_PROCESSING_UNIT)
                .input(NEUTRONIUM_WAFER)
                .output(NANO_CENTRAL_PROCESSING_UNIT_WAFER,16)
                .CWUt(2048).Laser(5)
                .buildAndRegister();


        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_QBIT_CENTRAL_PROCESSING_UNIT)
                .input(SILICON_WAFER)
                .output(QUBIT_CENTRAL_PROCESSING_UNIT_WAFER)
                .CWUt(128).Laser(2)
                .buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_QBIT_CENTRAL_PROCESSING_UNIT)
                .input(PHOSPHORUS_WAFER)
                .output(QUBIT_CENTRAL_PROCESSING_UNIT_WAFER,4)
                .CWUt(256).Laser(3)
                .buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_QBIT_CENTRAL_PROCESSING_UNIT)
                .input(NAQUADAH_WAFER)
                .output(QUBIT_CENTRAL_PROCESSING_UNIT_WAFER,8)
                .CWUt(1024).Laser(4)
                .buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_QBIT_CENTRAL_PROCESSING_UNIT)
                .input(NEUTRONIUM_WAFER)
                .output(QUBIT_CENTRAL_PROCESSING_UNIT_WAFER,16)
                .CWUt(2048).Laser(5)
                .buildAndRegister();

        //pic
        PRECISION_SPINNING.recipeBuilder()
                .input(POWER_INTEGRATED_CIRCUIT_WAFER)
                .input(dust, IndiumGalliumPhosphide, 2)
                .fluidInputs(VanadiumGallium.getFluid(L * 2))
                .output(RETICLE_HIGH_POWER_INTEGRATED_CIRCUIT)

                .CWUt(240)
                .duration(600).EUt(VA[IV]).buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .input(HIGH_POWER_INTEGRATED_CIRCUIT_WAFER)
                .input(dust, IndiumGalliumPhosphide, 8)
                .fluidInputs(Naquadah.getFluid(L * 4))
                .output(RETICLE_ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT)
                .CWUt(480)
                .duration(600).EUt(VA[LuV]).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).input(RETICLE_ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT)
                .input(SILICON_WAFER)
                .output(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT_WAFER)
                .CWUt(128).Laser(2).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).input(RETICLE_ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT)
                .input(PHOSPHORUS_WAFER)
                .output(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT_WAFER,4)
                .CWUt(256).Laser(3).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).input(RETICLE_ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT)
                .input(NAQUADAH_WAFER)
                .output(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT_WAFER,8)
                .CWUt(1024).Laser(4).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).input(RETICLE_ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT)
                .input(NEUTRONIUM_WAFER)
                .output(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT_WAFER,16)
                .CWUt(2048).Laser(5).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).input(RETICLE_HIGH_POWER_INTEGRATED_CIRCUIT)
                .input(SILICON_WAFER)
                .output(HIGH_POWER_INTEGRATED_CIRCUIT_WAFER)
                .CWUt(128).Laser(2).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).input(RETICLE_HIGH_POWER_INTEGRATED_CIRCUIT)
                .input(PHOSPHORUS_WAFER)
                .output(HIGH_POWER_INTEGRATED_CIRCUIT_WAFER,4)
                .CWUt(256).Laser(3).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).input(RETICLE_HIGH_POWER_INTEGRATED_CIRCUIT)
                .input(NAQUADAH_WAFER)
                .output(HIGH_POWER_INTEGRATED_CIRCUIT_WAFER,8)
                .CWUt(1024).Laser(4).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).input(RETICLE_HIGH_POWER_INTEGRATED_CIRCUIT)
                .input(NEUTRONIUM_WAFER)
                .output(HIGH_POWER_INTEGRATED_CIRCUIT_WAFER,16)
                .CWUt(2048).Laser(5).buildAndRegister();

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
                .buildAndRegister();




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
                .blastFurnaceTemp(1800)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(120)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,GalliumArsenide,1)
                .input(dust,Fluix,1)
                .output(AE_SILICON)
                .blastFurnaceTemp(1800)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(480)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,GalliumArsenide,2)
                .input(dust,Phosphorus,1)
                .output(PHOSPHORUS_BOULE)
                .blastFurnaceTemp(1800)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(480)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,GalliumArsenide,4)
                .input(dust,Naquadah,1)
                .output(NAQUADAH_BOULE)
                .blastFurnaceTemp(1800)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(7680)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,GalliumArsenide,8)
                .input(dust,Neutronium,1)
                .output(NEUTRONIUM_BOULE)
                .blastFurnaceTemp(1800)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(2500)
                .EUt(1920)
                .fluidInputs(Helium.getFluid(4000))
                .input(block,CSilicon,32)
                .input(dust,IndiumGalliumPhosphide,1)
                .input(dust,Boron,1)
                .output(SILICON_BOULE,2)
                .blastFurnaceTemp(1800)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(120)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,IndiumGalliumPhosphide,1)
                .input(dust,Fluix,1)
                .output(AE_SILICON,2)
                .blastFurnaceTemp(1800)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(2500)
                .EUt(480)
                .fluidInputs(Helium.getFluid(4000))
                .input(block,CSilicon,32)
                .input(dust,IndiumGalliumPhosphide,2)
                .input(dust,Phosphorus,1)
                .output(PHOSPHORUS_BOULE,2)
                .blastFurnaceTemp(1800)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(2500)
                .EUt(1920)
                .fluidInputs(Helium.getFluid(4000))
                .input(block,CSilicon,32)
                .input(dust,IndiumGalliumPhosphide,4)
                .input(dust,Naquadah,1)
                .output(NAQUADAH_BOULE,2)
                .blastFurnaceTemp(1800)
                .buildAndRegister();

        CZPULLER_RECIPES.recipeBuilder()
                .duration(2500)
                .EUt(7680)
                .fluidInputs(Helium.getFluid(4000))
                .input(block,CSilicon,32)
                .input(dust,IndiumGalliumPhosphide,8)
                .input(dust,Neutronium,1)
                .output(NEUTRONIUM_BOULE,2)
                .blastFurnaceTemp(1800)
                .buildAndRegister();
    }
    private static void Pre()
    {
        //石墨电极线
        //石墨+沥青=浸渍石墨
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(400)
                .EUt(30)
                .input(stick,Graphite,8)
                .fluidInputs(HighlyPurifiedCoalTar.getFluid(500))
                .output(IMPREGNATED_GRAPHITE_RODS)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(300)
                .EUt(30)
                .input(IMPREGNATED_GRAPHITE_RODS)
                .input(dust,Graphite,8)
                .fluidInputs(Asphalt.getFluid(500))
                .output(IMPREGNATED_GRAPHITE_RODSA)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .duration(1000)
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
                .input(foil,Gold,8)
                .fluidInputs(Epoxy.getFluid(576))
                .fluidInputs(Polyethylene.getFluid(400))
                .output(IMPREGNATED_PLASTIC_SUBSTRATE)
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
                .input(foil,Platinum,8)
                .fluidInputs(StyreneButadieneRubber.getFluid(576))
                .fluidInputs(Brominatedepoxyresins.getFluid(576))
                .output(IMPREGNATED_EPOXY)
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
                .input(foil,NanometerBariumTitanate,8)
                .fluidInputs(StyreneButadieneRubber.getFluid(576))
                .fluidInputs(Zylon.getFluid(576))
                .output(IMPREGNATED_FIBER)
                .buildAndRegister();

        //这里是IV的纤维强化
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(7680)
                .input(IMPREGNATED_MULTILAYER_FIBER)
                .fluidInputs(DiethylhexylPhosphoricAcid.getFluid(250))
                .output(MULTILAYER_FIBER_BOARD)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(1920)
                .input(dust,SiliconDioxide,1)
                .input(dust,Alumina,1)
                .input(dust,BoronTrioxide,1)
                .fluidInputs(Lead.getFluid(576))
                .fluidOutputs(GlassGlaze.getFluid(1000))
                .blastFurnaceTemp(2700)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(200)
                .EUt(7680)
                .input(foil,Ruridit,4)
                .input(wireFine,Platinum)
                .fluidInputs(GlassGlaze.getFluid(500))
                .fluidInputs(UltraGlue.getFluid(500))
                .output(LAMINATION_IR)
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .duration(200)
                .EUt(7680)
                .input(LAMINATION_IR)
                .input(FIBER_BOARD,2)
                .input(foil,RhodiumPlatedPalladium,8)
                .fluidInputs(Polyetheretherketone.getFluid(576))
                .fluidInputs(Polybenzimidazole.getFluid(576))
                .output(IMPREGNATED_MULTILAYER_FIBER)
                .buildAndRegister();
    }
}
