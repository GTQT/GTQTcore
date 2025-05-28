package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.MarkerMaterials.Color;
import gregtech.api.unification.material.Material;
import gregtech.common.items.MetaItems;
import keqing.gtqtcore.api.GCYSValues;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_LENS;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.blocks.MetaBlocks.OPTICAL_PIPES;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.wrap;

import static keqing.gtqtcore.common.block.blocks.BlockMultiblockGlass1.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockStepperCasing.CasingType.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static supercritical.api.unification.material.SCMaterials.BoronTrioxide;

public class CPULine {
    public static void init() {
        Assembler();    //机器加工
        ReticleChain(); //光掩模
        LaserStepper();  //光刻
        Pre();          //基板
        Silicon();      //晶圆
        AE();
    }

    private static void AE() {
        BLAST_RECIPES.recipeBuilder()
                .input(AE_WAFER)
                .input(dust, Gold, 4)
                .output(AE_WAFERA)
                .duration(1600)
                .blastFurnaceTemp(3600)
                .EUt(120)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(AE_WAFER)
                .input(dust, Fluix, 4)
                .output(AE_WAFERB)
                .duration(1600)
                .blastFurnaceTemp(3600)
                .EUt(120)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(AE_WAFER)
                .input(dust, Diamond, 4)
                .output(AE_WAFERC)
                .duration(1600)
                .blastFurnaceTemp(3600)
                .EUt(120)
                .buildAndRegister();
    }

    private static void ReticleChain() {
        SPINNER_RECIPES.recipeBuilder()
                .input(wrap, PolyvinylChloride,16)
                .input(circuit, MarkerMaterials.Tier.MV)
                .inputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(SILICATE_GLASS))
                .output(RETICLE_GRID_MKI)
                .duration(1200)
                .EUt(VA[MV])
                .buildAndRegister();

        SPINNER_RECIPES.recipeBuilder()
                .input(wrap, ReinforcedEpoxyResin,16)
                .input(circuit, MarkerMaterials.Tier.HV)
                .inputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(THY_SILICATE_GLASS))
                .output(RETICLE_GRID_MKII)
                .duration(1200)
                .EUt(VA[HV])
                .buildAndRegister();

        SPINNER_RECIPES.recipeBuilder()
                .input(wrap, Zylon,16)
                .input(circuit, MarkerMaterials.Tier.IV)
                .inputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(W_BORON_SILICATE_GLASS))
                .output(RETICLE_GRID_MKIII)
                .duration(1200)
                .EUt(VA[IV])
                .buildAndRegister();

        SPINNER_RECIPES.recipeBuilder()
                .input(wrap, Polyetheretherketone,16)
                .input(circuit, MarkerMaterials.Tier.LuV)
                .inputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(OSMIR_BORON_SILICATE_GLASS))
                .output(RETICLE_GRID_MKIV)
                .duration(1200)
                .EUt(VA[LuV])
                .buildAndRegister();

        //////////////////////////////////////
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(GLASS_TUBE)
                .input(ring,Rubber)
                .input(screw,Gold,2)
                .input(foil, Aluminium, 2)
                .output(UV_LIGHT_EMPTY_MKI)
                .duration(200)
                .EUt(120)
                .buildAndRegister();

        FLUID_CANNER_RECIPES.recipeBuilder()
                .input(UV_LIGHT_EMPTY_MKI)
                .fluidInputs(Helium.getFluid(1000))
                .output(UV_LIGHT_MKI)
                .duration(200)
                .EUt(120)
                .buildAndRegister();

        FLUID_CANNER_RECIPES.recipeBuilder()
                .input(UV_LIGHT_EMPTY_MKI)
                .fluidInputs(Neon.getFluid(1000))
                .output(UV_LIGHT_MKII)
                .duration(200)
                .EUt(120)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(GLASS_TUBE)
                .input(ring,Zylon)
                .input(screw,NanometerBariumTitanate,2)
                .input(foil, TungstenSteel, 2)
                .output(UV_LIGHT_EMPTY_MKII)
                .duration(200)
                .EUt(VA[EV])
                .buildAndRegister();

        FLUID_CANNER_RECIPES.recipeBuilder()
                .input(UV_LIGHT_EMPTY_MKII)
                .fluidInputs(Argon.getFluid(1000))
                .output(UV_LIGHT_MKIII)
                .duration(200)
                .EUt(120)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(GLASS_TUBE)
                .input(ring, Kevlar)
                .input(screw,Naquadah,2)
                .input(foil, RhodiumPlatedPalladium, 2)
                .output(UV_LIGHT_EMPTY_MKIII)
                .duration(200)
                .EUt(VA[EV])
                .buildAndRegister();

        FLUID_CANNER_RECIPES.recipeBuilder()
                .input(UV_LIGHT_EMPTY_MKIII)
                .fluidInputs(Krypton.getFluid(1000))
                .output(UV_LIGHT_MKIV)
                .duration(200)
                .EUt(120)
                .buildAndRegister();
        //////////////////////////////////////
        BLAST_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_MOLD_BALL)
                .inputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(SILICATE_GLASS))
                .input(RETICLE_GRID_MKI)
                .output(RETICLE_GLASS_MKI)
                .duration(1000)
                .EUt(120)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_MOLD_BALL)
                .inputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(TI_BORON_SILICATE_GLASS))
                .input(RETICLE_GRID_MKII)
                .output(RETICLE_GLASS_MKII)
                .duration(1000)
                .EUt(480)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_MOLD_BALL)
                .inputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(W_BORON_SILICATE_GLASS))
                .input(RETICLE_GRID_MKIII)
                .output(RETICLE_GLASS_MKIII)
                .duration(1000)
                .EUt(1920)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_MOLD_BALL)
                .inputs(GTQTMetaBlocks.blockMultiblockGlass1.getItemVariant(NAQ_BORON_SILICATE_GLASS))
                .input(RETICLE_GRID_MKIV)
                .output(RETICLE_GLASS_MKIV)
                .duration(1000)
                .EUt(7680)
                .buildAndRegister();

        //////////////////////////////////
        LOW_TEMP_ACTIVATOR_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_MOLD_BALL)
                .input(block, Polysilicon, 1)
                .fluidInputs(SiliconTetrachloride.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(4000))
                .fluidOutputs(HydrochloricAcid.getFluid(4000))
                .output(RETICLE_BASE)
                .duration(1000)
                .EUt(120)
                .buildAndRegister();

        //////////////////////////////////
        PRESSURE_LAMINATOR_RECIPES.recipeBuilder()
                .input(RETICLE_BASE)
                .input(RETICLE_TIN)
                .output(RETICLE_BASE_TIN)
                .duration(1000)
                .EUt(30)
                .buildAndRegister();

        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_MOLD_BALL)
                .fluidInputs(Tin.getFluid(576))
                .output(RETICLE_TIN)
                .duration(200)
                .EUt(120)
                .buildAndRegister();

        //////////////////////////////////
        ULTRAVIOLET_LAMP_CHAMBER_RECIPES.recipeBuilder()
                .input(RETICLE_GLASS_MKI)
                .input(RETICLE_BASE_TIN)
                .notConsumable(UV_LIGHT_MKI)
                .output(RETICLE_BASE_TIN_MKI)
                .duration(1000)
                .EUt(120)
                .buildAndRegister();

        ULTRAVIOLET_LAMP_CHAMBER_RECIPES.recipeBuilder()
                .input(RETICLE_GLASS_MKII)
                .input(RETICLE_BASE_TIN)
                .notConsumable(UV_LIGHT_MKII)
                .output(RETICLE_BASE_TIN_MKII)
                .duration(1000)
                .EUt(120)
                .buildAndRegister();

        ULTRAVIOLET_LAMP_CHAMBER_RECIPES.recipeBuilder()
                .input(RETICLE_GLASS_MKIII)
                .input(RETICLE_BASE_TIN)
                .notConsumable(UV_LIGHT_MKIII)
                .output(RETICLE_BASE_TIN_MKIII)
                .duration(1000)
                .EUt(120)
                .buildAndRegister();

        ULTRAVIOLET_LAMP_CHAMBER_RECIPES.recipeBuilder()
                .input(RETICLE_GLASS_MKIV)
                .input(RETICLE_BASE_TIN)
                .notConsumable(UV_LIGHT_MKIV)
                .output(RETICLE_BASE_TIN_MKIV)
                .duration(1000)
                .EUt(120)
                .buildAndRegister();

        //////////////////////////////////
        WaferBath(RETICLE_BASE_MKI, RETICLE_GLASS_MKI);
        WaferBath(RETICLE_BASE_MKII, RETICLE_GLASS_MKII);
        WaferBath(RETICLE_BASE_MKIII, RETICLE_GLASS_MKIII);
        WaferBath(RETICLE_BASE_MKIV, RETICLE_GLASS_MKIV);
    }

    public static void WaferBath(MetaItem<?>.MetaValueItem item1, MetaItem<?>.MetaValueItem item2) {
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(item1)
                .output(RETICLE_BASE)
                .output(item2)
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidOutputs(Tin.getFluid(576))
                .duration(1000)
                .EUt(120)
                .buildAndRegister();
    }

    private static void Assembler() {
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
                .input(circuit, MarkerMaterials.Tier.LV, 8)
                .input(screw, Steel, 16)
                .circuitMeta(3)
                .fluidInputs(Polyethylene.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockStepperCasing.getItemVariant(LASER_MKI))
                .duration(2000).EUt(30).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm())
                .input(EMITTER_MV, 8)
                .input(circuit, MarkerMaterials.Tier.MV, 8)
                .input(screw, Aluminium, 16)
                .circuitMeta(3)
                .fluidInputs(PolyvinylChloride.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockStepperCasing.getItemVariant(LASER_MKII))
                .duration(200).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[3].getStackForm())
                .input(EMITTER_HV, 8)
                .input(circuit, MarkerMaterials.Tier.HV, 8)
                .input(screw, StainlessSteel, 16)
                .circuitMeta(3)
                .fluidInputs(Epoxy.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockStepperCasing.getItemVariant(LASER_MKIII))
                .duration(200).EUt(480).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[4].getStackForm())
                .input(EMITTER_EV, 8)
                .input(circuit, MarkerMaterials.Tier.EV, 8)
                .input(screw, Titanium, 16)
                .circuitMeta(3)
                .fluidInputs(ReinforcedEpoxyResin.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockStepperCasing.getItemVariant(LASER_MKIV))
                .duration(200).EUt(1920).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[5].getStackForm())
                .input(EMITTER_IV, 8)
                .input(circuit, MarkerMaterials.Tier.IV, 8)
                .input(screw, TungstenSteel, 16)
                .circuitMeta(3)
                .fluidInputs(Polytetrafluoroethylene.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockStepperCasing.getItemVariant(LASER_MKV))
                .duration(200).EUt(7680).buildAndRegister();

        //射频
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[1].getStackForm())
                .input(FIELD_GENERATOR_LV, 8)
                .input(circuit, MarkerMaterials.Tier.LV, 8)
                .input(spring, Steel, 16)
                .circuitMeta(3)
                .fluidInputs(Polyethylene.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockStepperCasing.getItemVariant(F_LASER_MKI))
                .duration(2000).EUt(30).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm())
                .input(FIELD_GENERATOR_MV, 8)
                .input(circuit, MarkerMaterials.Tier.MV, 8)
                .input(spring, Aluminium, 16)
                .circuitMeta(3)
                .fluidInputs(PolyvinylChloride.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockStepperCasing.getItemVariant(F_LASER_MKII))
                .duration(200).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[3].getStackForm())
                .input(FIELD_GENERATOR_HV, 8)
                .input(circuit, MarkerMaterials.Tier.HV, 8)
                .input(spring, StainlessSteel, 16)
                .circuitMeta(3)
                .fluidInputs(Epoxy.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockStepperCasing.getItemVariant(F_LASER_MKIII))
                .duration(200).EUt(480).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[4].getStackForm())
                .input(FIELD_GENERATOR_EV, 8)
                .input(circuit, MarkerMaterials.Tier.EV, 8)
                .input(spring, Titanium, 16)
                .circuitMeta(3)
                .fluidInputs(ReinforcedEpoxyResin.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockStepperCasing.getItemVariant(F_LASER_MKIV))
                .duration(200).EUt(1920).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[5].getStackForm())
                .input(FIELD_GENERATOR_IV, 8)
                .input(circuit, MarkerMaterials.Tier.IV, 8)
                .input(spring, TungstenSteel, 16)
                .circuitMeta(3)
                .fluidInputs(Polytetrafluoroethylene.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockStepperCasing.getItemVariant(F_LASER_MKV))
                .duration(200).EUt(7680).buildAndRegister();


        //自净
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[1].getStackForm())
                .input(ELECTRIC_MOTOR_LV, 8)
                .input(circuit, MarkerMaterials.Tier.LV, 1)
                .input(rotor, Steel, 16)
                .circuitMeta(3)
                .fluidInputs(Polyethylene.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockStepperCasing.getItemVariant(CLEAN_MKI))
                .duration(2000).EUt(30).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm())
                .input(ELECTRIC_MOTOR_MV, 8)
                .input(circuit, MarkerMaterials.Tier.MV, 1)
                .input(rotor, Aluminium, 16)
                .circuitMeta(3)
                .fluidInputs(PolyvinylChloride.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockStepperCasing.getItemVariant(CLEAN_MKII))
                .duration(200).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[3].getStackForm())
                .input(ELECTRIC_MOTOR_HV, 8)
                .input(circuit, MarkerMaterials.Tier.HV, 1)
                .input(rotor, StainlessSteel, 16)
                .circuitMeta(3)
                .fluidInputs(Epoxy.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockStepperCasing.getItemVariant(CLEAN_MKIII))
                .duration(200).EUt(480).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[4].getStackForm())
                .input(ELECTRIC_MOTOR_EV, 8)
                .input(circuit, MarkerMaterials.Tier.EV, 1)
                .input(rotor, Titanium, 16)
                .circuitMeta(3)
                .fluidInputs(ReinforcedEpoxyResin.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockStepperCasing.getItemVariant(CLEAN_MKIV))
                .duration(200).EUt(1920).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[5].getStackForm())
                .input(ELECTRIC_MOTOR_IV, 8)
                .input(circuit, MarkerMaterials.Tier.IV, 1)
                .input(rotor, TungstenSteel, 16)
                .circuitMeta(3)
                .fluidInputs(Polytetrafluoroethylene.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockStepperCasing.getItemVariant(CLEAN_MKV))
                .duration(200).EUt(7680).buildAndRegister();
    }

    public static MetaItem<?>.MetaValueItem getReticleByTier(int tier) {
        if (tier == 1) return RETICLE_BASE_MKI;
        if (tier == 2) return RETICLE_BASE_MKII;
        if (tier == 3) return RETICLE_BASE_MKIII;
        return RETICLE_BASE_MKIV;
    }

    public static MetaItem<?>.MetaValueItem getReticleBasicByTier(int tier) {
        if (tier == 1) return RETICLE_BASE_TIN_MKI;
        if (tier == 2) return RETICLE_BASE_TIN_MKII;
        if (tier == 3) return RETICLE_BASE_TIN_MKIII;
        return RETICLE_BASE_TIN_MKIV;
    }

    public static MetaItem<?>.MetaValueItem getWaferByTier(int tier) {
        if (tier == 1) return SILICON_WAFER;
        if (tier == 2) return PHOSPHORUS_WAFER;
        if (tier == 3) return NAQUADAH_WAFER;
        if (tier == 4) return EUROPIUM_WAFER;
        if (tier == 5) return AMERICIUM_WAFER;
        if (tier == 6) return DUBNIUM_WAFER;
        return NEUTRONIUM_WAFER;
    }

    public static void Stepper(int tier, int EU, MetaItem<?>.MetaValueItem item1, MetaItem<?>.MetaValueItem item2, Material materials) {
        //透镜+晶圆+光掩模基板 锡附着=掩模
        if (materials.hasFlag(GENERATE_LENS)) {
            LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[EU])
                    .notConsumable(lens, materials)
                    .input(getReticleBasicByTier(tier))
                    .output(item1)
                    .CWUt(CWT[tier])
                    .duration(250 * tier * EU)
                    .buildAndRegister();
        } else {
            LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[EU])
                    .notConsumable(craftingLens, materials)
                    .input(getReticleBasicByTier(tier))
                    .output(item1)
                    .CWUt(CWT[tier])
                    .duration(250 * tier * EU)
                    .buildAndRegister();
        }


        for (int i = tier; i <= 7; i++) {
            //掩模-》产品+光掩模基板 无锡附着+液态锡
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU+i-1])
                    .Laser(tier)
                    .input(getWaferByTier(i))
                    .input(item1)
                    .output(item2, (int) Math.pow(2, i - tier))
                    .output(getReticleByTier(tier))
                    .CWUt(CWT[i])
                    .buildAndRegister();
        }
    }

    public static void StepperAE(int tier, int EU, MetaItem<?>.MetaValueItem item1, MetaItem<?>.MetaValueItem item2, MetaItem<?>.MetaValueItem item3) {
        //透镜+晶圆+光掩模基板 锡附着=掩模

        LASER_ENGRAVING.recipeBuilder().duration(9000).EUt(VA[EU])
                .input(item3)
                .input(getReticleBasicByTier(tier))
                .output(item1)
                .CWUt(CWT[tier])
                .duration(250 * tier * EU)
                .buildAndRegister();


        for (int i = tier; i <= 7; i++) {
            //掩模-》产品+光掩模基板 无锡附着+液态锡
            STEPPER_RECIPES.recipeBuilder().duration(9000).EUt(VA[EU+tier-1])
                    .Laser(tier)
                    .input(getWaferByTier(i))
                    .input(item1)
                    .output(item2, (int) Math.pow(2, i - tier))
                    .output(getReticleByTier(tier))
                    .CWUt(CWT[i])
                    .buildAndRegister();
        }
    }

    private static void LaserStepper() {
        StepperAE(1, 2, AE_RETICLEA, AE_A, AE_WAFERA);
        StepperAE(1, 2, AE_RETICLEB, AE_B, AE_WAFERB);
        StepperAE(1, 2, AE_RETICLEC, AE_C, AE_WAFERC);

        Stepper(1, 2, RETICLE_INTEGRATED_LOGIC_CIRCUIT, INTEGRATED_LOGIC_CIRCUIT_WAFER, Color.Blue);
        Stepper(1, 2, RETICLE_RANDOM_ACCESS_MEMORY, RANDOM_ACCESS_MEMORY_WAFER, Color.Red);
        Stepper(1, 2, RETICLE_CENTRAL_PROCESSING_UNIT, CENTRAL_PROCESSING_UNIT_WAFER, Color.LightGray);
        Stepper(1, 2, RETICLE_ULTRA_LOW_POWER_INTEGRATED_CIRCUIT, ULTRA_LOW_POWER_INTEGRATED_CIRCUIT_WAFER, Color.Green);
        Stepper(1, 2, RETICLE_LOW_POWER_INTEGRATED_CIRCUIT, LOW_POWER_INTEGRATED_CIRCUIT_WAFER, Color.LightBlue);
        Stepper(1, 2, RETICLE_SIMPLE_SYSTEM_ON_CHIP, SIMPLE_SYSTEM_ON_CHIP_WAFER, Color.Lime);
        Stepper(2, 3, RETICLE_NOR_MEMORY_CHIP, NOR_MEMORY_CHIP_WAFER, Color.Gray);
        Stepper(2, 3, RETICLE_POWER_INTEGRATED_CIRCUIT, POWER_INTEGRATED_CIRCUIT_WAFER, Color.Brown);
        Stepper(2, 3, RETICLE_NAND_MEMORY_CHIP, NAND_MEMORY_CHIP_WAFER, Color.Cyan);
        Stepper(2, 3, RETICLE_SYSTEM_ON_CHIP, SYSTEM_ON_CHIP_WAFER, Color.Magenta);
        Stepper(3, 3, RETICLE_ADVANCED_SYSTEM_ON_CHIP, ADVANCED_SYSTEM_ON_CHIP_WAFER, Color.Purple);
        Stepper(4, 3, RETICLE_HIGHLY_ADVANCED_SYSTEM_ON_CHIP, HIGHLY_ADVANCED_SOC_WAFER, Color.Orange);

        Stepper(4, 4, RETICLE_NANO_POWER_IC_WAFER, NANO_POWER_IC_WAFER, LithiumNiobate);
        Stepper(4, 5, RETICLE_PICO_POWER_IC_WAFER, PICO_POWER_IC_WAFER, LuTmYVO);
        Stepper(5, 5, RETICLE_FEMTO_POWER_IC_WAFER, FEMTO_POWER_IC_WAFER, PrHoYLF);
        //PIC

        //cpu EV
        PRECISION_SPINNING.recipeBuilder()
                .input(CENTRAL_PROCESSING_UNIT_WAFER)
                .input(CARBON_FIBERS, 16)
                .fluidInputs(Glowstone.getFluid(L * 4))
                .output(NANO_CENTRAL_PROCESSING_UNIT_WAFER)
                .CWUt(120)
                .duration(200).EUt(VA[EV]).buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .input(NANO_CENTRAL_PROCESSING_UNIT_WAFER)
                .input(QUANTUM_EYE, 2)
                .fluidInputs(GalliumArsenide.getFluid(L * 2))
                .output(QUBIT_CENTRAL_PROCESSING_UNIT_WAFER)
                .CWUt(120)
                .duration(200).EUt(VA[EV]).buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .input(NANO_CENTRAL_PROCESSING_UNIT_WAFER)
                .input(dust, IndiumGalliumPhosphide)
                .fluidInputs(Radon.getFluid(50))
                .output(QUBIT_CENTRAL_PROCESSING_UNIT_WAFER)
                .CWUt(120)
                .duration(200).EUt(VA[EV]).buildAndRegister();

        //pic
        PRECISION_SPINNING.recipeBuilder()
                .input(POWER_INTEGRATED_CIRCUIT_WAFER)
                .input(dust, IndiumGalliumPhosphide, 2)
                .fluidInputs(VanadiumGallium.getFluid(L * 2))
                .output(HIGH_POWER_INTEGRATED_CIRCUIT_WAFER)
                .CWUt(240)
                .duration(200).EUt(VA[IV]).buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .input(HIGH_POWER_INTEGRATED_CIRCUIT_WAFER)
                .input(dust, IndiumGalliumPhosphide, 8)
                .fluidInputs(Naquadah.getFluid(L * 4))
                .output(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT_WAFER)
                .CWUt(480)
                .duration(200).EUt(VA[LuV]).buildAndRegister();
        //TODO gcys pic

    }

    private static void Silicon() {
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Copper)
                .fluidInputs(Chlorine.getFluid(2000))
                .output(dust, CopperChloride, 3)
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
                .input(dust, Sodium, 4)
                .output(dust, Polysilicon)
                .output(dust, Salt, 8)
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
                .duration(300)
                .EUt(VA[MV])
                .fluidInputs(Trichlorosilane.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(HydrochloricAcid.getFluid(3000))
                .output(dust, Polysilicon, 1)
                .buildAndRegister();

        //长大
        czpuller(SILICON_BOULE, Boron, MV);
        czpuller(AE_SILICON, Fluix, HV);
        czpuller(PHOSPHORUS_BOULE, Phosphorus, EV);
        czpuller(NAQUADAH_BOULE, Naquadah, IV);
        czpuller(EUROPIUM_BOULE, Europium, LuV);
        czpuller(AMERICIUM_BOULE, Americium, ZPM);
        czpuller(DUBNIUM_BOULE, Dubnium, UV);
        czpuller(NEUTRONIUM_BOULE, Neutronium, UHV);

        //  SrSO4 + Bh -> Bh-doped SrSO4 Boule
        CZPULLER_RECIPES.recipeBuilder()
                .duration(20000)
                .EUt(VA[ZPM])
                .fluidInputs(MetastableOganesson.getFluid(4000))
                .input(block, Polysilicon, 32)
                .input(dust, StrontiumCarbonate, 64)
                .input(dust, Bohrium, 8)
                .output(STRONTIUM_CARBONATE_BOHRIUM_BOULE)
                .pressure(GCYSValues.increaseRecipesPressure[ZPM])
                .temperature(9000)
                .buildAndRegister();
    }

    public static void czpuller(MetaItem<?>.MetaValueItem doule, Material material, int tier) {
        if (tier <= 2) {
            CZPULLER_RECIPES.recipeBuilder()
                    .duration(20000 / (4 - tier))
                    .EUt(VA[tier])
                    .pressure(GCYSValues.increaseRecipesPressure[tier])
                    .fluidInputs(Nitrogen.getFluid(4000))
                    .input(block, Polysilicon, 32)
                    .input(dust, GalliumArsenide, 8)
                    .input(dust, material, 1)
                    .output(doule, (4 - tier) / 2)
                    .temperature(900 * tier)
                    .buildAndRegister();
        }
        if (tier <= 4) {
            CZPULLER_RECIPES.recipeBuilder()
                    .duration(20000 / (6 - tier))
                    .EUt(VA[tier])
                    .pressure(GCYSValues.increaseRecipesPressure[tier])
                    .fluidInputs(Helium.getFluid(4000))
                    .input(block, Polysilicon, 32)
                    .input(dust, GalliumArsenide, 8)
                    .input(dust, material, 1)
                    .output(doule, (6 - tier) / 2)
                    .temperature(900 * tier)
                    .buildAndRegister();
        }
        if (tier <= 6) {
            CZPULLER_RECIPES.recipeBuilder()
                    .duration(20000 / (8 - tier))
                    .EUt(VA[tier])
                    .pressure(GCYSValues.increaseRecipesPressure[tier])
                    .fluidInputs(Radon.getFluid(4000))
                    .input(block, Polysilicon, 32)
                    .input(dust, IndiumGalliumPhosphide, 8)
                    .input(dust, material, 1)
                    .output(doule, (8 - tier) / 2)
                    .temperature(900 * tier)
                    .buildAndRegister();
        }
        if (tier <= 8) {
            CZPULLER_RECIPES.recipeBuilder()
                    .duration(20000 / (10 - tier))
                    .EUt(VA[tier])
                    .pressure(GCYSValues.increaseRecipesPressure[tier])
                    .fluidInputs(Xenon.getFluid(4000))
                    .input(block, Polysilicon, 32)
                    .input(dust, IndiumGalliumPhosphide, 8)
                    .input(dust, material, 1)
                    .output(doule, (10 - tier) / 2)
                    .temperature(900 * tier)
                    .buildAndRegister();
        }
        if (tier <= 10) {
            CZPULLER_RECIPES.recipeBuilder()
                    .duration(20000 / (12 - tier))
                    .EUt(VA[tier])
                    .pressure(GCYSValues.increaseRecipesPressure[tier])
                    .fluidInputs(MetastableOganesson.getFluid(4000))
                    .input(block, Polysilicon, 32)
                    .input(dust, NaquadriaGalliumIndium, 8)
                    .input(dust, material, 1)
                    .output(doule, (12 - tier) / 2)
                    .temperature(900 * tier)
                    .buildAndRegister();
        }

    }

    private static void Pre() {
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
                .notConsumable(dust, Silver, 16)
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
                .output(plate, TreatedWood)
                .buildAndRegister();

        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .input(plate, TreatedWood, 4)
                .fluidInputs(Glue.getFluid(200))
                .output(LAMINATION_WD, 4)
                .buildAndRegister();

        PRESSURE_LAMINATOR_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .input(LAMINATION_WD)
                .input(foil, Copper, 8)
                .fluidInputs(RedAlloy.getFluid(576))
                .fluidInputs(Tin.getFluid(576))
                .output(IMPREGNATED_SUBSTRATE, 2)
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
                .input(dust, QuartzSand)
                .input(dust, Quicklime)
                .input(dust, Uvarovite)
                .input(dust, Pyrope)
                .output(dust, Fiberglass, 4)
                .buildAndRegister();

        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(120)
                .input(wireFine, Fiberglass, 8)
                .fluidInputs(Glue.getFluid(288))
                .output(LAMINATION_FG, 4)
                .buildAndRegister();

        PRESSURE_LAMINATOR_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(120)
                .input(LAMINATION_FG)
                .input(foil, Gold, 8)
                .fluidInputs(Polyethylene.getFluid(576))
                .fluidInputs(Polystyrene.getFluid(576))
                .output(IMPREGNATED_PLASTIC_SUBSTRATE, 2)
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
                .input(CARBON_FIBER_PLATE, 4)
                .fluidInputs(ReinforcedEpoxyResin.getFluid(288))
                .output(LAMINATION_CA, 4)
                .buildAndRegister();

        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(480)
                .input(IMPREGNATED_EPOXY)
                .fluidInputs(PhosphoricAcid.getFluid(250))
                .output(EPOXY_BOARD)
                .buildAndRegister();

        PRESSURE_LAMINATOR_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(480)
                .input(LAMINATION_CA)
                .input(foil, Platinum, 8)
                .fluidInputs(Polytetrafluoroethylene.getFluid(576))
                .fluidInputs(Bps.getFluid(576))
                .output(IMPREGNATED_EPOXY, 2)
                .buildAndRegister();

        //这里是EV阶段的纤维强化电路板
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(1920)
                .input(plate, ReinforcedEpoxyResin, 4)
                .fluidInputs(Germanium.getFluid(288))
                .output(LAMINATION_GE, 4)
                .buildAndRegister();

        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(1920)
                .input(plate, Zylon, 1)
                .fluidInputs(Germanium.getFluid(288))
                .output(LAMINATION_GE, 4)
                .buildAndRegister();

        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(1920)
                .input(IMPREGNATED_FIBER)
                .fluidInputs(FluoroantimonicAcid.getFluid(250))
                .output(FIBER_BOARD)
                .buildAndRegister();

        PRESSURE_LAMINATOR_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(1920)
                .input(LAMINATION_GE)
                .input(foil, NanometerBariumTitanate, 8)
                .fluidInputs(Zylon.getFluid(576))
                .fluidInputs(PolyphenyleneSulfide.getFluid(576))
                .output(IMPREGNATED_FIBER, 2)
                .buildAndRegister();

        //这里是IV的纤维强化
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(7680)
                .input(IMPREGNATED_MULTILAYER_FIBER)
                .fluidInputs(DiethylhexylPhosphoricAcid.getFluid(250))
                .output(MULTILAYER_FIBER_BOARD)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(7680)
                .input(dust, SiliconDioxide, 1)
                .input(dust, Alumina, 1)
                .input(dust, BoronTrioxide, 1)
                .fluidInputs(Lead.getFluid(576))
                .fluidOutputs(GlassGlaze.getFluid(1000))
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .duration(200)
                .EUt(7680)
                .input(foil, Ruridit, 4)
                .input(wireFine, Gold)
                .fluidInputs(GlassGlaze.getFluid(500))
                .fluidInputs(UltraGlue.getFluid(500))
                .output(LAMINATION_IR, 4)
                .buildAndRegister();

        PRESSURE_LAMINATOR_RECIPES.recipeBuilder()
                .duration(200)
                .EUt(7680)
                .input(LAMINATION_IR)
                .input(foil, Platinum, 8)
                .fluidInputs(Polybenzimidazole.getFluid(576))
                .fluidInputs(PolyvinylButyral.getFluid(576))
                .output(IMPREGNATED_MULTILAYER_FIBER, 2)
                .buildAndRegister();
    }
}
