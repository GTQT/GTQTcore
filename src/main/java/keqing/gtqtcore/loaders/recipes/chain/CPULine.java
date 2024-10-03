package keqing.gtqtcore.loaders.recipes.chain;
import appeng.client.render.effects.AssemblerFX;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.common.items.MetaItems;
import gregtech.api.unification.material.MarkerMaterials.Color;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTElectrobath;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.item.Item;

import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_LENS;
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
    public static void Stepper(int tier, int EU, MetaItem<?>.MetaValueItem item1, MetaItem<?>.MetaValueItem item2, Material materials)
    {
        if(materials!=null) {
            if(materials.hasFlag(GENERATE_LENS))
            {
                LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[tier]).input(RETICLE_SILICON).CWUt(CWT[tier])
                        .fluidInputs(Water.getFluid(10000))
                        .notConsumable(lens, materials).output(item1).buildAndRegister();
            }
            else {
                LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[tier]).input(RETICLE_SILICON).CWUt(CWT[tier])
                        .fluidInputs(Water.getFluid(10000))
                        .notConsumable(craftingLens, materials).output(item1).buildAndRegister();
            }
        }
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
                    .input(EUROPIUM_WAFER)
                    .output(item2, (int)Math.pow(2,4-tier))
                    .CWUt(CWT[tier+3])
                    .duration(1500*tier*EU)
                    .buildAndRegister();
        }
        if(tier<=5) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU + 4]).notConsumable(item1)
                    .Laser(tier)
                    .input(AMERICIUM_WAFER)
                    .output(item2, (int)Math.pow(2,5-tier))
                    .CWUt(CWT[tier+4])
                    .duration(2000*tier*EU)
                    .buildAndRegister();
        }
        if(tier<=6) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU + 5]).notConsumable(item1)
                    .Laser(tier)
                    .input(DUBNIUM_WAFER)
                    .output(item2, (int)Math.pow(2,6-tier))
                    .CWUt(CWT[tier+5])
                    .duration(4000*tier*EU)
                    .buildAndRegister();
        }
        if(tier<=7) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU + 6]).notConsumable(item1)
                    .Laser(tier)
                    .input(NEUTRONIUM_WAFER)
                    .output(item2, (int)Math.pow(2,7-tier))
                    .CWUt(CWT[tier+6])
                    .duration(8000*tier*EU)
                    .buildAndRegister();
        }
    }
    public static void AE(int tier, int EU, MetaItem<?>.MetaValueItem item1, MetaItem<?>.MetaValueItem item2,MetaItem<?>.MetaValueItem item3)
    {
        if(tier<=1) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU]).notConsumable(item1)
                    .Laser(1)
                    .input(item3)
                    .output(item2)
                    .CWUt(CWT[tier])
                    .circuitMeta(1)
                    .duration(250*tier*EU)
                    .buildAndRegister();
        }
        if(tier<=2) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU + 1]).notConsumable(item1)
                    .Laser(2)
                    .input(item3)
                    .circuitMeta(2)
                    .output(item2, (int)Math.pow(2,2-tier))
                    .CWUt(CWT[tier+1])
                    .duration(500*tier*EU)
                    .buildAndRegister();
        }
        if(tier<=3) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU + 2]).notConsumable(item1)
                    .Laser(3)
                    .input(item3)
                    .circuitMeta(3)
                    .output(item2, (int)Math.pow(2,3-tier))
                    .CWUt(CWT[tier+2])
                    .duration(1000*tier*EU)
                    .buildAndRegister();
        }
        if(tier<=4) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU + 3]).notConsumable(item1)
                    .Laser(4)
                    .input(item3)
                    .circuitMeta(4)
                    .output(item2, (int)Math.pow(2,4-tier))
                    .CWUt(CWT[tier+3])
                    .duration(1500*tier*EU)
                    .buildAndRegister();
        }
        if(tier<=5) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU + 4]).notConsumable(item1)
                    .Laser(4)
                    .input(item3)
                    .circuitMeta(5)
                    .output(item2, (int)Math.pow(2,5-tier))
                    .CWUt(CWT[tier+4])
                    .duration(2000*tier*EU)
                    .buildAndRegister();
        }
        if(tier<=6) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU + 5]).notConsumable(item1)
                    .Laser(5)
                    .input(item3)
                    .circuitMeta(6)
                    .output(item2, (int)Math.pow(2,6-tier))
                    .CWUt(CWT[tier+5])
                    .duration(4000*tier*EU)
                    .buildAndRegister();
        }
        if(tier<=7) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU + 6]).notConsumable(item1)
                    .Laser(5)
                    .input(item3)
                    .circuitMeta(7)
                    .output(item2, (int)Math.pow(2,7-tier))
                    .CWUt(CWT[tier+6])
                    .duration(8000*tier*EU)
                    .buildAndRegister();
        }
    }
    public static void PIC(int tier, int EU, MetaItem<?>.MetaValueItem item1, MetaItem<?>.MetaValueItem item2)
    {
        if(tier<=1) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU]).input(item1)
                    .Laser(tier)
                    .input(SILICON_WAFER)
                    .output(item2)
                    .CWUt(CWT[tier])
                    .duration(250*tier*EU)
                    .buildAndRegister();
        }
        if(tier<=2) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU + 1]).input(item1)
                    .Laser(tier)
                    .input(PHOSPHORUS_WAFER)
                    .output(item2, (int)Math.pow(2,2-tier))
                    .CWUt(CWT[tier+1])
                    .duration(500*tier*EU)
                    .buildAndRegister();
        }
        if(tier<=3) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU + 2]).input(item1)
                    .Laser(tier)
                    .input(NAQUADAH_WAFER)
                    .output(item2, (int)Math.pow(2,3-tier))
                    .CWUt(CWT[tier+2])
                    .duration(1000*tier*EU)
                    .buildAndRegister();
        }
        if(tier<=4) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU + 3]).input(item1)
                    .Laser(tier)
                    .input(EUROPIUM_WAFER)
                    .output(item2, (int)Math.pow(2,4-tier))
                    .CWUt(CWT[tier+3])
                    .duration(1500*tier*EU)
                    .buildAndRegister();
        }
        if(tier<=5) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU + 4]).input(item1)
                    .Laser(tier)
                    .input(AMERICIUM_WAFER)
                    .output(item2, (int)Math.pow(2,5-tier))
                    .CWUt(CWT[tier+4])
                    .duration(2000*tier*EU)
                    .buildAndRegister();
        }
        if(tier<=6) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU + 5]).input(item1)
                    .Laser(tier)
                    .input(DUBNIUM_WAFER)
                    .output(item2, (int)Math.pow(2,6-tier))
                    .CWUt(CWT[tier+5])
                    .duration(4000*tier*EU)
                    .buildAndRegister();
        }
        if(tier<=7) {
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU + 6]).input(item1)
                    .Laser(tier)
                    .input(NEUTRONIUM_WAFER)
                    .output(item2, (int)Math.pow(2,7-tier))
                    .CWUt(CWT[tier+6])
                    .duration(8000*tier*EU)
                    .buildAndRegister();
        }
    }
    private static void LaserStepper() {
        //
        AE(1,2,AE_RETICLEA,AE_A,AE_WAFERA);
        AE(1,2,AE_RETICLEB,AE_B,AE_WAFERB);
        AE(1,2,AE_RETICLEC,AE_C,AE_WAFERC);

        Stepper(1,2,RETICLE_INTEGRATED_LOGIC_CIRCUIT,INTEGRATED_LOGIC_CIRCUIT_WAFER,Color.Blue);
        Stepper(1,2,RETICLE_RANDOM_ACCESS_MEMORY,RANDOM_ACCESS_MEMORY_WAFER,Color.Red);
        Stepper(1,2,RETICLE_CENTRAL_PROCESSING_UNIT,CENTRAL_PROCESSING_UNIT_WAFER,Color.LightGray);
        Stepper(1,2,RETICLE_ULTRA_LOW_POWER_INTEGRATED_CIRCUIT,ULTRA_LOW_POWER_INTEGRATED_CIRCUIT_WAFER,Color.Green);
        Stepper(1,2,RETICLE_LOW_POWER_INTEGRATED_CIRCUIT,LOW_POWER_INTEGRATED_CIRCUIT_WAFER,Color.LightBlue);
        Stepper(1,2,RETICLE_SIMPLE_SYSTEM_ON_CHIP,SIMPLE_SYSTEM_ON_CHIP_WAFER,Color.Lime);
        PIC(2,2,RETICLE_NANO_CENTRAL_PROCESSING_UNIT,NANO_CENTRAL_PROCESSING_UNIT_WAFER);
        PIC(2,2,RETICLE_QBIT_CENTRAL_PROCESSING_UNIT,QUBIT_CENTRAL_PROCESSING_UNIT_WAFER);
        PIC(2,3,RETICLE_ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT,ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT_WAFER);
        PIC(2,3,RETICLE_HIGH_POWER_INTEGRATED_CIRCUIT,HIGH_POWER_INTEGRATED_CIRCUIT_WAFER);
        Stepper(2,3,RETICLE_NOR_MEMORY_CHIP,NOR_MEMORY_CHIP_WAFER,Color.Gray);
        Stepper(2,3,RETICLE_POWER_INTEGRATED_CIRCUIT,POWER_INTEGRATED_CIRCUIT_WAFER,Color.Brown);
        Stepper(2,3,RETICLE_NAND_MEMORY_CHIP,NAND_MEMORY_CHIP_WAFER,Color.Cyan);
        Stepper(2,3,RETICLE_SYSTEM_ON_CHIP,SYSTEM_ON_CHIP_WAFER,Color.Magenta);
        Stepper(3,3,RETICLE_ADVANCED_SYSTEM_ON_CHIP,ADVANCED_SYSTEM_ON_CHIP_WAFER,Color.Purple);
        Stepper(4,3,RETICLE_HIGHLY_ADVANCED_SYSTEM_ON_CHIP,HIGHLY_ADVANCED_SOC_WAFER,Color.Orange);

        Stepper(4,4,RETICLE_NANO_POWER_IC_WAFER,NANO_POWER_IC_WAFER,LithiumNiobate);
        Stepper(4,5,RETICLE_PICO_POWER_IC_WAFER,PICO_POWER_IC_WAFER,LuTmYVO);
        Stepper(5,5,RETICLE_FEMTO_POWER_IC_WAFER,FEMTO_POWER_IC_WAFER,PrHoYLF);
        //PIC

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

        //  Si + 4Cl -> SiCl4
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Silicon)
                .fluidInputs(Chlorine.getFluid(4000))
                .circuitMeta(1)
                .fluidOutputs(SiliconTetrachloride.getFluid(1000))
                .EUt(VA[MV])
                .duration(300)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(SiliconTetrachloride.getFluid(1000))
                .input(dust,Sodium,4)
                .output(dust,Polysilicon)
                .output(dust,Salt,8)
                .duration(600)
                .EUt(VA[MV])
                .buildAndRegister();

        //三氯氢硅
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(300)
                .EUt(VA[MV])
                .input(dust, Silicon)
                .circuitMeta(1)
                .fluidInputs(HydrochloricAcid.getFluid(3000))
                .fluidOutputs(Trichlorosilane.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(2000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(300) // 反应持续时间设为300个ticks
                .EUt(VA[MV]) // 设定电力消耗等级为MV
                .fluidInputs(Trichlorosilane.getFluid(1000)) // 输入1000mB的三氯硅烷
                .fluidInputs(Hydrogen.getFluid(2000)) // 输入2000mB的氢气
                .fluidOutputs(HydrochloricAcid.getFluid(3000)) // 输出3000mB的盐酸
                .output(dust, Polysilicon, 1) // 输出1个多晶硅粉
                .buildAndRegister(); // 构建并注册此化学反应

        //长大
        czpuller(SILICON_BOULE,Boron,MV);
        czpuller(AE_SILICON,Fluix,HV);
        czpuller(PHOSPHORUS_BOULE,Phosphorus,EV);
        czpuller(NAQUADAH_BOULE,Naquadah,IV);
        czpuller(EUROPIUM_BOULE,Europium,LuV);
        czpuller(AMERICIUM_BOULE,Americium,ZPM);
        czpuller(DUBNIUM_BOULE,Dubnium,UV);
        czpuller(NEUTRONIUM_BOULE,Neutronium,UHV);

        //  SrSO4 + Bh -> Bh-doped SrSO4 Boule
        CZPULLER_RECIPES.recipeBuilder()
                .duration(20000)
                .EUt(VA[ZPM])
                .fluidInputs(MetastableOganesson.getFluid(4000))
                .input(dust, StrontiumCarbonate, 64)
                .input(dust, Bohrium, 8)
                .output(STRONTIUM_CARBONATE_BOHRIUM_BOULE)
                .blastFurnaceTemp(9000)
                .buildAndRegister();
    }
    public static void czpuller(MetaItem<?>.MetaValueItem doule, Material material, int tier)
    {
        if(tier<=2) {
            CZPULLER_RECIPES.recipeBuilder()
                    .duration(20000/(4-tier))
                    .EUt(VA[tier])
                    .fluidInputs(Nitrogen.getFluid(4000))
                    .input(block, Polysilicon, 32)
                    .input(dust, GalliumArsenide, 8)
                    .input(dust, material, 1)
                    .output(doule, (4-tier)/2)
                    .blastFurnaceTemp(900*tier)
                    .buildAndRegister();
        }
        if(tier<=4) {
            CZPULLER_RECIPES.recipeBuilder()
                    .duration(20000/(6-tier))
                    .EUt(VA[tier])
                    .fluidInputs(Helium.getFluid(4000))
                    .input(block, Polysilicon, 32)
                    .input(dust, GalliumArsenide, 8)
                    .input(dust, material, 1)
                    .output(doule, (6-tier)/2)
                    .blastFurnaceTemp(900*tier)
                    .buildAndRegister();
        }
        if(tier<=6) {
            CZPULLER_RECIPES.recipeBuilder()
                    .duration(20000/(8-tier))
                    .EUt(VA[tier])
                    .fluidInputs(Radon.getFluid(4000))
                    .input(block, Polysilicon, 32)
                    .input(dust, IndiumGalliumPhosphide, 8)
                    .input(dust, material, 1)
                    .output(doule, (8-tier)/2)
                    .blastFurnaceTemp(900*tier)
                    .buildAndRegister();
        }
        if(tier<=8) {
            CZPULLER_RECIPES.recipeBuilder()
                    .duration(20000/(10-tier))
                    .EUt(VA[tier])
                    .fluidInputs(Xenon.getFluid(4000))
                    .input(block, Polysilicon, 32)
                    .input(dust, IndiumGalliumPhosphide, 8)
                    .input(dust, material, 1)
                    .output(doule, (10-tier)/2)
                    .blastFurnaceTemp(900*tier)
                    .buildAndRegister();
        }
        if(tier<=10) {
            CZPULLER_RECIPES.recipeBuilder()
                    .duration(20000/(12-tier))
                    .EUt(VA[tier])
                    .fluidInputs(MetastableOganesson.getFluid(4000))
                    .input(block, Polysilicon, 32)
                    .input(dust, NaquadriaGalliumIndium, 8)
                    .input(dust, material, 1)
                    .output(doule, (12-tier)/2)
                    .blastFurnaceTemp(900*tier)
                    .buildAndRegister();
        }

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
                .fluidInputs(Polystyrene.getFluid(576))
                .fluidInputs(Polyethylene.getFluid(576))
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
                .fluidInputs(Bps.getFluid(576))
                .fluidInputs(StyreneButadieneRubber.getFluid(576))
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
