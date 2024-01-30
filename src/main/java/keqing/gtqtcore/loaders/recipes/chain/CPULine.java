package keqing.gtqtcore.loaders.recipes.chain;
import gregicality.science.api.recipes.GCYSRecipeMaps;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.common.items.MetaItems;
import gregtech.api.unification.material.MarkerMaterials.Color;
import static gregicality.science.api.unification.materials.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;

import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.electrode;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.RETICLE_POWER_INTEGRATED_CIRCUIT;

public class CPULine {
    public static void init() {
        LaserEngraving(); //光掩模
        LaserStepper();  //光刻
        Pre();          //基板
        Silicon();      //晶圆
        AE();
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
                .totalCWU(9600).CWUt(16).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEB)
                .Laser(600)
                .input(AE_WAFERB).output(AE_B,1)
                .totalCWU(9600).CWUt(16).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEC)
                .Laser(600)
                .input(AE_WAFERC).output(AE_C,1)
                .totalCWU(9600).CWUt(16).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();



        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEA)
                .Laser(200)
                .input(AE_WAFERA).output(AE_A,4)
                .totalCWU(32000).CWUt(32).fluidInputs(SU8_Photoresist.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEB)
                .Laser(200)
                .input(AE_WAFERB).output(AE_B,4)
                .totalCWU(32000).CWUt(32).fluidInputs(SU8_Photoresist.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEC)
                .Laser(200)
                .input(AE_WAFERC).output(AE_C,4)
                .totalCWU(32000).CWUt(32).fluidInputs(SU8_Photoresist.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();



        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEA)
                .Laser(60)
                .input(AE_WAFERA).output(AE_A,16)
                .totalCWU(64000).CWUt(64).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEB)
                .Laser(60)
                .input(AE_WAFERB).output(AE_B,16)
                .totalCWU(64000).CWUt(64).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(AE_RETICLEC)
                .Laser(60)
                .input(AE_WAFERC).output(AE_C,16)
                .totalCWU(64000).CWUt(64).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

    }

    private static void LaserStepper() {
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[MV]).notConsumable(RETICLE_INTEGRATED_LOGIC_CIRCUIT)
                .Laser(800)
                .input(SILICON_WAFER).output(INTEGRATED_LOGIC_CIRCUIT_WAFER)
                .totalCWU(9600).CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_INTEGRATED_LOGIC_CIRCUIT)
                .Laser(800)
                .input(PHOSPHORUS_WAFER).output(INTEGRATED_LOGIC_CIRCUIT_WAFER,4)
                .totalCWU(9600).CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_INTEGRATED_LOGIC_CIRCUIT)
                .Laser(800)
                .input(NAQUADAH_WAFER).output(INTEGRATED_LOGIC_CIRCUIT_WAFER,8)
                .totalCWU(9600).CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_INTEGRATED_LOGIC_CIRCUIT)
                .Laser(800)
                .input(NEUTRONIUM_WAFER).output(INTEGRATED_LOGIC_CIRCUIT_WAFER,16)
                .totalCWU(9600).CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[MV]).notConsumable(RETICLE_RANDOM_ACCESS_MEMORY)
                .Laser(600)
                .input(SILICON_WAFER).output(RANDOM_ACCESS_MEMORY_WAFER)
                .totalCWU(9600).CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_RANDOM_ACCESS_MEMORY)
                .Laser(600)
                .input(PHOSPHORUS_WAFER).output(RANDOM_ACCESS_MEMORY_WAFER,4)
                .totalCWU(9600).CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_RANDOM_ACCESS_MEMORY)
                .Laser(600)
                .input(NAQUADAH_WAFER).output(RANDOM_ACCESS_MEMORY_WAFER,8)
                .totalCWU(9600).CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_RANDOM_ACCESS_MEMORY)
                .Laser(600)
                .input(NEUTRONIUM_WAFER).output(RANDOM_ACCESS_MEMORY_WAFER,16)
                .totalCWU(9600).CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[MV]).notConsumable(RETICLE_CENTRAL_PROCESSING_UNIT)
                .Laser(400)
                .input(SILICON_WAFER).output(CENTRAL_PROCESSING_UNIT_WAFER)
                .totalCWU(9600).CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_CENTRAL_PROCESSING_UNIT)
                .Laser(400)
                .input(PHOSPHORUS_WAFER).output(CENTRAL_PROCESSING_UNIT_WAFER,4)
                .totalCWU(9600).CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_CENTRAL_PROCESSING_UNIT)
                .Laser(400)
                .input(NAQUADAH_WAFER).output(CENTRAL_PROCESSING_UNIT_WAFER,8)
                .totalCWU(9600).CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_CENTRAL_PROCESSING_UNIT)
                .Laser(400)
                .input(NEUTRONIUM_WAFER).output(CENTRAL_PROCESSING_UNIT_WAFER,16)
                .totalCWU(9600).CWUt(8).fluidInputs(HydrogenSilsesquioxane.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[MV]).notConsumable(RETICLE_ULTRA_LOW_POWER_INTEGRATED_CIRCUIT)
                .Laser(200)
                .input(SILICON_WAFER).output(ULTRA_LOW_POWER_INTEGRATED_CIRCUIT_WAFER)
                .totalCWU(9600).CWUt(8).fluidInputs(SU8_Photoresist.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_ULTRA_LOW_POWER_INTEGRATED_CIRCUIT)
                .Laser(200)
                .input(PHOSPHORUS_WAFER).output(ULTRA_LOW_POWER_INTEGRATED_CIRCUIT_WAFER,4)
                .totalCWU(9600).CWUt(8).fluidInputs(SU8_Photoresist.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_ULTRA_LOW_POWER_INTEGRATED_CIRCUIT)
                .Laser(200)
                .input(NAQUADAH_WAFER).output(ULTRA_LOW_POWER_INTEGRATED_CIRCUIT_WAFER,8)
                .totalCWU(9600).CWUt(8).fluidInputs(SU8_Photoresist.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_ULTRA_LOW_POWER_INTEGRATED_CIRCUIT)
                .Laser(200)
                .input(NEUTRONIUM_WAFER).output(ULTRA_LOW_POWER_INTEGRATED_CIRCUIT_WAFER,16)
                .totalCWU(9600).CWUt(8).fluidInputs(SU8_Photoresist.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[MV]).notConsumable(RETICLE_LOW_POWER_INTEGRATED_CIRCUIT)
                .Laser(200)
                .input(SILICON_WAFER).output(LOW_POWER_INTEGRATED_CIRCUIT_WAFER)
                .totalCWU(9600).CWUt(8).fluidInputs(SU8_Photoresist.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_LOW_POWER_INTEGRATED_CIRCUIT) .Laser(200)
                .input(PHOSPHORUS_WAFER).output(LOW_POWER_INTEGRATED_CIRCUIT_WAFER,4)
                .totalCWU(9600).CWUt(8).fluidInputs(SU8_Photoresist.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_LOW_POWER_INTEGRATED_CIRCUIT) .Laser(200)
                .input(NAQUADAH_WAFER).output(LOW_POWER_INTEGRATED_CIRCUIT_WAFER,8)
                .totalCWU(9600).CWUt(8).fluidInputs(SU8_Photoresist.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_LOW_POWER_INTEGRATED_CIRCUIT) .Laser(200)
                .input(NEUTRONIUM_WAFER).output(LOW_POWER_INTEGRATED_CIRCUIT_WAFER,16)
                .totalCWU(9600).CWUt(8).fluidInputs(SU8_Photoresist.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[MV]).notConsumable(RETICLE_SIMPLE_SYSTEM_ON_CHIP) .Laser(200)
                .input(SILICON_WAFER).output(SIMPLE_SYSTEM_ON_CHIP_WAFER)
                .totalCWU(9600).CWUt(8).fluidInputs(SU8_Photoresist.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_SIMPLE_SYSTEM_ON_CHIP) .Laser(200)
                .input(PHOSPHORUS_WAFER).output(SIMPLE_SYSTEM_ON_CHIP_WAFER,4)
                .totalCWU(9600).CWUt(8).fluidInputs(SU8_Photoresist.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_SIMPLE_SYSTEM_ON_CHIP) .Laser(200)
                .input(NAQUADAH_WAFER).output(SIMPLE_SYSTEM_ON_CHIP_WAFER,8)
                .totalCWU(9600).CWUt(8).fluidInputs(SU8_Photoresist.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_SIMPLE_SYSTEM_ON_CHIP) .Laser(200)
                .input(NEUTRONIUM_WAFER).output(SIMPLE_SYSTEM_ON_CHIP_WAFER,16)
                .totalCWU(9600).CWUt(8).fluidInputs(SU8_Photoresist.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_NAND_MEMORY_CHIP) .Laser(200)
                .input(PHOSPHORUS_WAFER).output(NAND_MEMORY_CHIP_WAFER,1)
                .totalCWU(38400).CWUt(32).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_NAND_MEMORY_CHIP) .Laser(200)
                .input(NAQUADAH_WAFER).output(NAND_MEMORY_CHIP_WAFER,4)
                .totalCWU(38400).CWUt(32).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_NAND_MEMORY_CHIP) .Laser(200)
                .input(NEUTRONIUM_WAFER).output(NAND_MEMORY_CHIP_WAFER,8)
                .totalCWU(38400).CWUt(32).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_NOR_MEMORY_CHIP) .Laser(200)
                .input(PHOSPHORUS_WAFER).output(NOR_MEMORY_CHIP_WAFER,1)
                .totalCWU(38400).CWUt(32).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_NOR_MEMORY_CHIP) .Laser(200)
                .input(NAQUADAH_WAFER).output(NOR_MEMORY_CHIP_WAFER,4)
                .totalCWU(38400).CWUt(32).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_NOR_MEMORY_CHIP) .Laser(200)
                .input(NEUTRONIUM_WAFER).output(NOR_MEMORY_CHIP_WAFER,8)
                .totalCWU(38400).CWUt(32).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_POWER_INTEGRATED_CIRCUIT) .Laser(60)
                .input(PHOSPHORUS_WAFER).output(POWER_INTEGRATED_CIRCUIT_WAFER,1)
                .totalCWU(38400).CWUt(32).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_POWER_INTEGRATED_CIRCUIT).Laser(60)
                .input(NAQUADAH_WAFER).output(POWER_INTEGRATED_CIRCUIT_WAFER,4)
                .totalCWU(38400).CWUt(32).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_POWER_INTEGRATED_CIRCUIT).Laser(60)
                .input(NEUTRONIUM_WAFER).output(POWER_INTEGRATED_CIRCUIT_WAFER,8)
                .totalCWU(38400).CWUt(32).fluidInputs(Vinylcinnamate.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[HV]).notConsumable(RETICLE_SYSTEM_ON_CHIP).Laser(40)
                .input(PHOSPHORUS_WAFER).output(SYSTEM_ON_CHIP_WAFER,1)
                .totalCWU(38400).CWUt(32).fluidInputs(Xmt.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_SYSTEM_ON_CHIP).Laser(40)
                .input(NAQUADAH_WAFER).output(SYSTEM_ON_CHIP_WAFER,4)
                .totalCWU(38400).CWUt(32).fluidInputs(Xmt.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_SYSTEM_ON_CHIP).Laser(40)
                .input(NEUTRONIUM_WAFER).output(SYSTEM_ON_CHIP_WAFER,8)
                .totalCWU(38400).CWUt(32).fluidInputs(Xmt.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV]).notConsumable(RETICLE_ADVANCED_SYSTEM_ON_CHIP).Laser(20)
                .input(NAQUADAH_WAFER).output(ADVANCED_SYSTEM_ON_CHIP_WAFER,1)
                .totalCWU(153600).CWUt(128).fluidInputs(Xmt.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_ADVANCED_SYSTEM_ON_CHIP).Laser(20)
                .input(NEUTRONIUM_WAFER).output(ADVANCED_SYSTEM_ON_CHIP_WAFER,4)
                .totalCWU(153600).CWUt(128).fluidInputs(Xmt.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV]).notConsumable(RETICLE_HIGHLY_ADVANCED_SYSTEM_ON_CHIP).Laser(10)
                .input(NEUTRONIUM_WAFER).output(HIGHLY_ADVANCED_SOC_WAFER,1)
                .totalCWU(614400).CWUt(512).fluidInputs(Zrbtmst.getFluid(1000)).cleanroom(CleanroomType.CLEANROOM).buildAndRegister();


        //cpu
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV])
                .notConsumable(RETICLE_NANO_CENTRAL_PROCESSING_UNIT)
                .input(SILICON_WAFER)
                .output(NANO_CENTRAL_PROCESSING_UNIT_WAFER)
                .totalCWU(153600).CWUt(128)
                .fluidInputs(Water.getFluid(10000))
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EV])
                .notConsumable(RETICLE_QBIT_CENTRAL_PROCESSING_UNIT)
                .input(SILICON_WAFER)
                .output(QUBIT_CENTRAL_PROCESSING_UNIT_WAFER)
                .totalCWU(153600).CWUt(128)
                .fluidInputs(Water.getFluid(10000))
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        //pic
        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[IV])
                .notConsumable(RETICLE_ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT)
                .input(SILICON_WAFER)
                .output(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT_WAFER)
                .totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[LuV])
                .notConsumable(RETICLE_HIGH_POWER_INTEGRATED_CIRCUIT)
                .input(SILICON_WAFER)
                .output(HIGH_POWER_INTEGRATED_CIRCUIT_WAFER)
                .totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        //TODO gcys pic

    }

    private static void LaserEngraving() {
        //TODO
        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
            .fluidInputs(Water.getFluid(10000))
            .notConsumable(ingot, Iron).output(AE_RETICLEA)
            .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(ingot, Boron).output(AE_RETICLEB)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(ingot, Steel).output(AE_RETICLEC)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();




        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Blue).output(RETICLE_INTEGRATED_LOGIC_CIRCUIT)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.LightGray).output(RETICLE_RANDOM_ACCESS_MEMORY)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Purple).output(RETICLE_ADVANCED_SYSTEM_ON_CHIP)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();


        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Red).output(RETICLE_CENTRAL_PROCESSING_UNIT)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Brown).output(RETICLE_LOW_POWER_INTEGRATED_CIRCUIT)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[HV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Cyan).output(RETICLE_NAND_MEMORY_CHIP)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[MV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Black).output(RETICLE_NANO_CENTRAL_PROCESSING_UNIT)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[HV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Gray).output(RETICLE_NOR_MEMORY_CHIP)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[HV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Green).output(RETICLE_POWER_INTEGRATED_CIRCUIT)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[HV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.LightBlue).output(RETICLE_QBIT_CENTRAL_PROCESSING_UNIT)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Lime).output(RETICLE_SIMPLE_SYSTEM_ON_CHIP)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[HV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Magenta).output(RETICLE_SYSTEM_ON_CHIP)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.White).output(RETICLE_ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Yellow).output(RETICLE_ULTRA_LOW_POWER_INTEGRATED_CIRCUIT)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Pink).output(RETICLE_HIGH_POWER_INTEGRATED_CIRCUIT)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[EV]).input(RETICLE_SILICON).totalCWU(9600).CWUt(8)
                .fluidInputs(Water.getFluid(10000))
                .notConsumable(craftingLens, Color.Orange).output(RETICLE_HIGHLY_ADVANCED_SYSTEM_ON_CHIP)
                .cleanroom(CleanroomType.CLEANROOM).buildAndRegister();

    }

    private static void Silicon()
    {
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Copper)
                .fluidInputs(Chlorine.getFluid(2000))
                .output(dust,CopperCl)
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
                .fluidOutputs(CSilicon.getFluid(1000))
                .output(dust,Salt,4)
                .duration(200)
                .EUt(30)
                .buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
                .fluidInputs(SiliconTetrachloride.getFluid(1000))
                .fluidOutputs(Chlorine.getFluid(4000))
                .fluidOutputs(CSilicon.getFluid(1000))
                .duration(200)
                .EUt(30)
                .buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
                .fluidInputs(Silane.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(4000))
                .fluidOutputs(CSilicon.getFluid(1000))
                .duration(200)
                .EUt(30)
                .buildAndRegister();

        GCYSRecipeMaps.CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(480)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,GalliumArsenide,1)
                .input(dust,Boron,1)
                .output(SILICON_BOULE)
                .buildAndRegister();

        GCYSRecipeMaps.CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(1960)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,GalliumArsenide,2)
                .input(dust,Phosphorus,1)
                .output(PHOSPHORUS_BOULE)
                .buildAndRegister();

        GCYSRecipeMaps.CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(7960)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,GalliumArsenide,4)
                .input(dust,Naquadah,1)
                .output(NAQUADAH_BOULE)
                .buildAndRegister();

        GCYSRecipeMaps.CZPULLER_RECIPES.recipeBuilder()
                .duration(5000)
                .EUt(30720)
                .fluidInputs(Nitrogen.getFluid(16000))
                .input(block,CSilicon,32)
                .input(dust,GalliumArsenide,8)
                .input(dust,Neutronium,1)
                .output(NEUTRONIUM_BOULE)
                .buildAndRegister();

        GCYSRecipeMaps.CZPULLER_RECIPES.recipeBuilder()
                .duration(2500)
                .EUt(480)
                .fluidInputs(Helium.getFluid(4000))
                .input(block,CSilicon,32)
                .input(dust,IndiumGalliumPhosphide,1)
                .input(dust,Boron,1)
                .output(SILICON_BOULE,2)
                .buildAndRegister();

        GCYSRecipeMaps.CZPULLER_RECIPES.recipeBuilder()
                .duration(2500)
                .EUt(1960)
                .fluidInputs(Helium.getFluid(4000))
                .input(block,CSilicon,32)
                .input(dust,IndiumGalliumPhosphide,2)
                .input(dust,Phosphorus,1)
                .output(PHOSPHORUS_BOULE,2)
                .buildAndRegister();

        GCYSRecipeMaps.CZPULLER_RECIPES.recipeBuilder()
                .duration(2500)
                .EUt(7680)
                .fluidInputs(Helium.getFluid(4000))
                .input(block,CSilicon,32)
                .input(dust,IndiumGalliumPhosphide,4)
                .input(dust,Naquadah,1)
                .output(NAQUADAH_BOULE,2)
                .buildAndRegister();

        GCYSRecipeMaps.CZPULLER_RECIPES.recipeBuilder()
                .duration(2500)
                .EUt(30720)
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
                .duration(2000)
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
                .fluidInputs(Asphalt.getFluid(2000))
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
        CUTTER_RECIPES.recipeBuilder()
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
                .input(plate,ReinforcedEpoxyResin)
                .input(foil,Platinum,2)
                .fluidInputs(StyreneButadieneRubber.getFluid(400))
                .fluidInputs(Polytetrafluoroethylene.getFluid(576))
                .output(IMPREGNATED_EPOXY,4)
                .buildAndRegister();
    }
}
