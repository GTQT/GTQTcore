package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.block.VariantBlock;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockActiveUniqueCasing;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1;
import keqing.gtqtcore.common.block.blocks.BlockIsaCasing;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3;
import net.minecraft.util.IStringSerializable;

import static gregicality.multiblocks.api.recipes.GCYMRecipeMaps.ALLOY_BLAST_RECIPES;
import static gregicality.multiblocks.api.unification.GCYMMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.PRECISE_ASSEMBLER_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.common.block.blocks.BlockIsaCasing.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4.TurbineCasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing5.TurbineCasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing6.CasingType.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.CIRCUIT_GOOD_III;

public class MachineCasing {
    public static void init() {
        DustMixer();
        CasingAssembler();
        Arc();
        CasingRecipes();
    }

    private static void CasingRecipes() {
        //粉碎
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(HULL[EV], 2)
                .input(plate, Nichrome, 4)
                .input(plate, WatertightSteel, 4)
                .input(stickLong, HSSG, 2)
                .input(bolt, HastelloyN, 16)
                .fluidInputs(StainlessSteel.getFluid(L * 8))
                .outputs(GTQTMetaBlocks.blockIsaCasing.getItemVariant(FLOTATION_CASING, 2))
                .EUt(VA[IV])
                .duration(280)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt, HSSG)
                .input(plate, HSSG, 4)
                .input(gear, HastelloyN, 3)
                .input(gearSmall, HSSG, 6)
                .input(bolt, TungstenCarbide, 16)
                .fluidInputs(HastelloyX.getFluid(L * 2))
                .outputs(GTQTMetaBlocks.blockIsaCasing.getItemVariant(FLOTATION_CASING_GEARBOX, 2))
                .EUt(VA[LuV])
                .duration(140)
                .buildAndRegister();

        ModHandler.addShapedRecipe(true, "hc_alloy_casing",GTQTMetaBlocks.blockMultiblockCasing3.getItemVariant(BlockMultiblockCasing3.CasingType.HC_ALLOY_CASING),
                "APA", "PFP", "APA",
                'F', new UnificationEntry(frameGt, HG1223),
                'P', new UnificationEntry(pipeNormalFluid, Iridium),
                'A', new UnificationEntry(plate, HG1223));

        ModHandler.addShapedRecipe(true, "hastelloy_n_pipe", GTQTMetaBlocks.blockIsaCasing.getItemVariant(FLOTATION_CASING_PIPE, 1),
                "APA", "PFP", "APA",
                'F', new UnificationEntry(frameGt, WatertightSteel),
                'P', new UnificationEntry(pipeNormalFluid, HastelloyN),
                'A', new UnificationEntry(plate, VanadiumGallium));

        ModHandler.addShapedRecipe(true, "iridium_turbine", GTQTMetaBlocks.blockIsaCasing.getItemVariant(IRIDIUM_TURBINE, 1),
                "APA", "PFP", "APA",
                'F', new UnificationEntry(frameGt, Iridium),
                'P', new UnificationEntry(pipeNormalFluid, Naquadah),
                'A', new UnificationEntry(plate, Iridium));

        ModHandler.addShapedRecipe(true, "intake_pipe", GTQTMetaBlocks.blockIsaCasing.getItemVariant(FLOTATION_INTAKE_CASING, 1),
                "APA", "PFP", "APA",
                'F', new UnificationEntry(frameGt, NaquadahAlloy),
                'P', new UnificationEntry(pipeNormalFluid, Naquadah),
                'A', new UnificationEntry(plate, VanadiumGallium));

        //  Advanced Assembly Control Casing
        ModHandler.addShapedRecipe(true, "casing_assembly_control.advanced", GTQTMetaBlocks.blockActiveUniqueCasing.getItemVariant(BlockActiveUniqueCasing.ActiveCasingType.ADVANCED_ASSEMBLY_CONTROL_CASING, 1),
                "OPO", "SFE", "OMO",
                'O', new UnificationEntry(circuit, MarkerMaterials.Tier.UV),
                'P', ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT,
                'F', new UnificationEntry(frameGt, NaquadahAlloy),
                'S', SENSOR_ZPM,
                'E', EMITTER_ZPM,
                'M', ELECTRIC_MOTOR_ZPM);

        //  Advanced Assembly Casing
        ModHandler.addShapedRecipe(true, "casing_assembly_line.advanced", GTQTMetaBlocks.blockActiveUniqueCasing.getItemVariant(BlockActiveUniqueCasing.ActiveCasingType.ADVANCED_ASSEMBLY_LINE_CASING, 1),
                "PGP", "RFR", "PGP",
                'P', new UnificationEntry(plate, Iridium),
                'G', new UnificationEntry(gear, Osmiridium),
                'R', ROBOT_ARM_ZPM,
                'F', new UnificationEntry(frameGt, NaquadahAlloy));

        //  Circuit Assembly Casing
        ModHandler.addShapedRecipe(true, "circuit_assembly_casing", GTQTMetaBlocks.blockActiveUniqueCasing.getItemVariant(BlockActiveUniqueCasing.ActiveCasingType.CIRCUIT_ASSEMBLY_LINE_CASING, 1),
                "PGP", "RFR", "PGP",
                'P', new UnificationEntry(plate, Osmium),
                'G', new UnificationEntry(gear, Rhodium),
                'R', ROBOT_ARM_LuV,
                'F', new UnificationEntry(frameGt, HSSE));

        //  Advanced Grate Casing
        ModHandler.addShapedRecipe(true, "advanced_grate_casing", GTQTMetaBlocks.blockActiveUniqueCasing.getItemVariant(BlockActiveUniqueCasing.ActiveCasingType.CIRCUIT_ASSEMBLY_CONTROL_CASING, 1),
                "PRP", "PXP", "PMP",
                'X', MetaBlocks.MULTIBLOCK_CASING.getItemVariant(gregtech.common.blocks.BlockMultiblockCasing.MultiblockCasingType.GRATE_CASING),
                'R', new UnificationEntry(rotor, NaquadahAlloy),
                'M', ELECTRIC_MOTOR_LuV,
                'P', new UnificationEntry(plate, Osmiridium));

        // 研磨
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(HULL[EV], 2)
                .input(plate, Inconel625, 4)
                .input(plate, HSSE, 8)
                .input(ring, Inconel625, 8)
                .input(bolt, Inconel625, 16)
                .fluidInputs(Titanium.getFluid(L * 8))
                .outputs(GTQTMetaBlocks.blockIsaCasing.getItemVariant(ISA_MILL_CASING, 1))
                .EUt(VA[EV])
                .duration(240)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt, HSSS)
                .input(gear, Inconel625, 3)
                .input(gearSmall, HSSG, 6)
                .input(bolt, HSSE, 16)
                .input(COMPONENT_GRINDER_TUNGSTEN, 2)
                .fluidInputs(Zeron100.getFluid(L * 2))
                .outputs(GTQTMetaBlocks.blockIsaCasing.getItemVariant(ISA_MILL_CASING_GEARBOX, 1))
                .EUt(VA[LuV])
                .duration(300)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt, Steel)
                .input(gear, Aluminium, 3)
                .input(gearSmall, Steel, 6)
                .input(bolt, Gold, 16)
                .fluidInputs(Glue.getFluid(L * 2))
                .outputs(GTQTMetaBlocks.blockIsaCasing.getItemVariant(SEPARATOR_ROTOR, 1))
                .EUt(VA[LV])
                .duration(300)
                .buildAndRegister();

        ModHandler.addShapedRecipe(true, "inconel_625_pipe", GTQTMetaBlocks.blockIsaCasing.getItemVariant(ISA_MILL_CASING_PIPE, 1),
                "APA", "PFP", "APA",
                'F', new UnificationEntry(frameGt, MaragingSteel300),
                'P', new UnificationEntry(pipeNormalFluid, Inconel625),
                'A', new UnificationEntry(plate, NiobiumTitanium));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt, Neutronium)
                .input(pipeNormalFluid, Naquadah, 2)
                .input(plate, Talonite, 4)
                .input(bolt, Duranium, 8)
                .fluidInputs(Kevlar.getFluid(L * 2))
                .outputs(GTQTMetaBlocks.blockMultiblockCasing4.getItemVariant(TALONITE_CASING, 1))
                .circuitMeta(10)
                .EUt(VA[ZPM])
                .duration(800)
                .buildAndRegister();

        //硅岩
        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt, Americium)
                .input(gear, HMS1J79Alloy, 2)
                .input(ELECTRIC_MOTOR_UV,4)
                .input(bolt, NaquadahAlloy, 8)
                .fluidInputs(Polybenzimidazole.getFluid(L * 12))
                .fluidInputs(Kevlar.getFluid(L * 8))
                .fluidInputs(KaptonE.getFluid(L * 4))
                .fluidInputs(Nitrogen.getPlasma(L * 2))
                .outputs(GTQTMetaBlocks.blockMultiblockCasing3.getItemVariant(SFTC, 2))
                .EUt(VA[UV])
                .duration(1000)
                .Tier(3)
                .CWUt(CWT[ZPM])
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt, Americium)
                .input(gear, HMS1J79Alloy, 2)
                .input(FIELD_GENERATOR_UV,4)
                .input(plateDouble, Naquadria, 8)
                .fluidInputs(Polybenzimidazole.getFluid(L * 12))
                .fluidInputs(Kevlar.getFluid(L * 8))
                .fluidInputs(KaptonE.getFluid(L * 4))
                .fluidInputs(Nitrogen.getPlasma(L * 2))
                .outputs(GTQTMetaBlocks.blockMultiblockCasing3.getItemVariant(SFTS, 1))
                .EUt(VA[UV])
                .duration(1000)
                .Tier(3)
                .CWUt(CWT[ZPM])
                .buildAndRegister();

        //  Naquadria Casing
        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt, Neutronium)
                .input(gear, Naquadria, 2)
                .input(stick, Naquadria, 4)
                .input(bolt, Duranium, 8)
                .fluidInputs(Polybenzimidazole.getFluid(L * 12))
                .fluidInputs(Kevlar.getFluid(L * 8))
                .fluidInputs(KaptonE.getFluid(L * 4))
                .fluidInputs(NaquadahEnriched.getFluid(L * 2))
                .outputs(GTQTMetaBlocks.blockMultiblockCasing3.getItemVariant(NAQUADRIA_CASING, 2))
                .EUt(VA[UV])
                .duration(1000)
                .Tier(3)
                .CWUt(CWT[ZPM])
                .buildAndRegister();

        //  Naquadri alloy Casing
        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt, Tritanium)
                .input(gear, NaquadahAlloy, 2)
                .input(stick, NaquadahAlloy, 4)
                .input(bolt, Duranium, 8)
                .fluidInputs(Polybenzimidazole.getFluid(L * 6))
                .fluidInputs(Kevlar.getFluid(L * 4))
                .fluidInputs(KaptonE.getFluid(L * 2))
                .fluidInputs(NaquadahEnriched.getFluid(L))
                .outputs(GTQTMetaBlocks.blockMultiblockCasing3.getItemVariant(NAQUADAH_ALLOY_CASING, 2))
                .EUt(VA[ZPM])
                .duration(1000)
                .Tier(2)
                .CWUt(CWT[LuV])
                .buildAndRegister();

        //  Polybenzimidazole Pipe Casing
        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III, 4)
                .inputs(GTQTMetaBlocks.blockMultiblockCasing4.getItemVariant(NQ_MACHINE_CASING))
                .input(frameGt, Tritanium, 2)
                .input(plate, BlackPlutonium, 8)
                .fluidInputs(Polybenzimidazole.getFluid(L * 24))
                .fluidInputs(Kevlar.getFluid(L * 16))
                .fluidInputs(KaptonE.getFluid(L * 8))
                .fluidInputs(NaquadahEnriched.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockMultiblockCasing4.getItemVariant(POLYBENZIMIDAZOLE_PIPE))
                .EUt(VA[ZPM])
                .duration(800)
                .Tier(3)
                .CWUt(CWT[ZPM])
                .buildAndRegister();

        //  Hyper casings
        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III, 1)
                .inputs(GTQTMetaBlocks.blockMultiblockCasing4.getItemVariant(NQ_TURBINE_CASING))
                .input(frameGt, Neutronium, 2)
                .input(plate, Adamantium, 8)
                .fluidInputs(Polybenzimidazole.getFluid(L * 24))
                .fluidInputs(Kevlar.getFluid(L * 16))
                .fluidInputs(KaptonE.getFluid(L * 8))
                .fluidInputs(BlackPlutonium.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockMultiblockCasing4.getItemVariant(HYPER_CASING, 2))
                .EUt(VA[UHV])
                .duration(1000)
                .Tier(3)
                .CWUt(CWT[UV])
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(MetaBlocks.METAL_CASING.getItemVariant(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF))
                .circuitMeta(6)
                .fluidInputs(AusteniticStainlessSteel904L.getFluid(L * 2))
                .outputs(GTQTMetaBlocks.blockMultiblockCasing4.getItemVariant(ADVANCED_INVAR_CASING))
                .EUt(VA[LV])
                .duration(50)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(MetaBlocks.METAL_CASING.getItemVariant(BlockMetalCasing.MetalCasingType.ALUMINIUM_FROSTPROOF))
                .circuitMeta(6)
                .fluidInputs(TanmolyiumBetaC.getFluid(L * 2))
                .outputs(GTQTMetaBlocks.blockMultiblockCasing5.getItemVariant(ADVANCED_COLD_CASING))
                .EUt(VA[LV])
                .duration(50)
                .buildAndRegister();

        createCasingRecipe("red_steel_casing",
                GTQTMetaBlocks.blockIsaCasing,
                BlockIsaCasing.CasingType.VACUUM_CASING,
                RedSteel);

        createCasingRecipe("red_steel_casing",
                GTQTMetaBlocks.blockMultiblockCasing3,
                BlockMultiblockCasing3.CasingType.NITINOL_MACHINE_CASING,
                Nitinol);

        createCasingRecipe("inconel_625", GTQTMetaBlocks.blockMultiblockCasing1, BlockMultiblockCasing1.CasingType.Inconel625, Inconel625);
        createCasingRecipe("hastelloy_n", GTQTMetaBlocks.blockMultiblockCasing1, BlockMultiblockCasing1.CasingType.HastelloyN, HastelloyN);
        createCasingRecipe("stellite", GTQTMetaBlocks.blockMultiblockCasing1, BlockMultiblockCasing1.CasingType.Stellite, Stellite);
        createCasingRecipe("hdcs", GTQTMetaBlocks.blockMultiblockCasing1, BlockMultiblockCasing1.CasingType.Hdcs, Hdcs);
        createCasingRecipe("lafium", GTQTMetaBlocks.blockMultiblockCasing1, BlockMultiblockCasing1.CasingType.Lafium, Lafium);
        createCasingRecipe("black_titanium", GTQTMetaBlocks.blockMultiblockCasing1, BlockMultiblockCasing1.CasingType.BlackTitanium, BlackTitanium);
        createCasingRecipe("talonite", GTQTMetaBlocks.blockMultiblockCasing1, BlockMultiblockCasing1.CasingType.Talonite, Talonite);
        createCasingRecipe("black_plutonium", GTQTMetaBlocks.blockMultiblockCasing1, BlockMultiblockCasing1.CasingType.BlackPlutonium, BlackPlutonium);
        createCasingRecipe("maraging_steel_250", GTQTMetaBlocks.blockMultiblockCasing1, BlockMultiblockCasing1.CasingType.MaragingSteel250, MaragingSteel250);
        createCasingRecipe("staballoy", GTQTMetaBlocks.blockMultiblockCasing1, BlockMultiblockCasing1.CasingType.Staballoy, Staballoy);
        createCasingRecipe("babbitt_alloy", GTQTMetaBlocks.blockMultiblockCasing1, BlockMultiblockCasing1.CasingType.BabbittAlloy, BabbittAlloy);
        createCasingRecipe("zirconium_carbide", GTQTMetaBlocks.blockMultiblockCasing1, BlockMultiblockCasing1.CasingType.ZirconiumCarbide, ZirconiumCarbide);
        createCasingRecipe("inconel_792", GTQTMetaBlocks.blockMultiblockCasing1, BlockMultiblockCasing1.CasingType.Inconel792, Inconel792);
        createCasingRecipe("incoloy_ma_813", GTQTMetaBlocks.blockMultiblockCasing1, BlockMultiblockCasing1.CasingType.IncoloyMA813, IncoloyMA813);
        createCasingRecipe("hastelloy_x_78", GTQTMetaBlocks.blockMultiblockCasing1, BlockMultiblockCasing1.CasingType.HastelloyX78, HastelloyX78);
        createCasingRecipe("hastelloy_k_243", GTQTMetaBlocks.blockMultiblockCasing1, BlockMultiblockCasing1.CasingType.HastelloyK243, HastelloyK243);
        createCasingRecipe("iridium_casing", GTQTMetaBlocks.blockMultiblockCasing4, IRIDIUM_CASING, Osmiridium);
        createCasingRecipe("eglin_steel", GTQTMetaBlocks.blockMultiblockCasing3, eglin_steel, EglinSteel);
        createCasingRecipe("grisium", GTQTMetaBlocks.blockMultiblockCasing3, grisium, Grisium);
        createCasingRecipe("potin", GTQTMetaBlocks.blockMultiblockCasing3, potin, Potin);
        createCasingRecipe("black_steel", GTQTMetaBlocks.blockMultiblockCasing3, black_steel, BlackSteel);
        createCasingRecipe("blue_steel", GTQTMetaBlocks.blockMultiblockCasing3, blue_steel, BlueSteel);
        createCasingRecipe("red_steel", GTQTMetaBlocks.blockMultiblockCasing3, red_steel, RedSteel);
        createCasingRecipe("tumbaga", GTQTMetaBlocks.blockMultiblockCasing3, tumbaga, Tumbaga);
        createCasingRecipe("rhodium_plated_palladium", GTQTMetaBlocks.blockMultiblockCasing6, RHODIUM_PLATED_PALLADIUM, RhodiumPlatedPalladium);
        createCasingRecipe("osmiridium", GTQTMetaBlocks.blockMultiblockCasing6, OSMIRIDIUM, Osmiridium);
        createCasingRecipe("neutronium", GTQTMetaBlocks.blockMultiblockCasing6, NEUTRONIUM, Neutronium);

    }


    private static void Arc() {
        ALLOY_BLAST_RECIPES.recipeBuilder().duration(600).EUt(VA[EV])
                .input(dust, Iron, 4)
                .input(dust, Invar, 3)
                .input(dust, Manganese)
                .input(dust, Chrome)
                .circuitMeta(1)
                .fluidOutputs(StainlessSteel.getFluid(9 * 144))
                .blastFurnaceTemp(3600)
                .buildAndRegister();

        ALLOY_BLAST_RECIPES.recipeBuilder().duration(600).EUt(VA[EV])
                .input(dust, Iron, 6)
                .input(dust, Nickel)
                .input(dust, Manganese)
                .input(dust, Chrome)
                .circuitMeta(1)
                .fluidOutputs(StainlessSteel.getFluid(9 * 144))
                .blastFurnaceTemp(3600)
                .buildAndRegister();

    }

    private static void DustMixer() {
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Naquadria.getFluid(144))
                .input(dust, Gallium, 1)
                .input(dust, Indium, 1)
                .circuitMeta(15)
                .output(dust, NaquadriaGalliumIndium, 3)
                .EUt(VA[LuV])
                .duration(800)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Titanium, 5)
                .input(dust, Molybdenum, 5)
                .input(dust, Vanadium, 2)
                .input(dust, Chrome, 3)
                .input(dust, Aluminium)
                .circuitMeta(16)
                .output(dust, TanmolyiumBetaC, 16)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Cobalt, 4)
                .input(dust, Chrome, 3)
                .input(dust, Phosphorus, 2)
                .input(dust, Molybdenum, 1)
                .output(dust, Talonite, 10)
                .duration(120)
                .EUt(VA[MV])
                .buildAndRegister();

        //  Eglin Steel Base
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Iron, 4)
                .input(dust, Kanthal)
                .input(dust, Invar, 5)
                .circuitMeta(10)
                .output(dust, EglinSteelBase, 10)
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        //  Eglin Steel
        MIXER_RECIPES.recipeBuilder()
                .input(dust, EglinSteelBase, 10)
                .input(dust, Sulfur)
                .input(dust, Silicon)
                .input(dust, Carbon)
                .circuitMeta(13)
                .output(dust, EglinSteel, 13)
                .EUt(VA[MV])
                .duration(120)
                .buildAndRegister();

        // Tumbaga
        MIXER_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .input(dust, Gold, 7)
                .input(dust, Bronze, 3)
                .input(dust, Titanium, 2)
                .output(dust, Tumbaga, 12)
                .EUt(VA[HV])
                .duration(280)
                .buildAndRegister();

        //  Silicon Carbide
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Graphite)
                .input(dust, Silicon)
                .circuitMeta(2)
                .output(dust, SiliconCarbide, 2)
                .duration(300)
                .EUt(VA[EV])
                .buildAndRegister();

        //  MAR-Ce-M200 Steel
        MIXER_RECIPES.recipeBuilder()
                .input(dust, MARM200Steel, 18)
                .input(dust, Cerium)
                .output(dust, MARM200CeSteel, 19)
                .EUt(VA[IV])
                .duration(350)
                .buildAndRegister();

        //  Zirconium Carbide
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Zirconium)
                .input(dust, Carbon)
                .circuitMeta(2)
                .output(dust, ZirconiumCarbide, 2)
                .EUt(VA[MV])
                .duration(120)
                .buildAndRegister();

        //  Maraging Steel 250
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Steel, 16)
                .input(dust, Molybdenum)
                .input(dust, Titanium)
                .input(dust, Nickel, 4)
                .input(dust, Cobalt, 2)
                .circuitMeta(24)
                .output(dust, MaragingSteel250, 24)
                .EUt(VA[EV])
                .duration(200)
                .buildAndRegister();

        //  HG-1223
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Barium, 2)
                .input(dust, Calcium, 2)
                .input(dust, Copper, 3)
                .fluidInputs(Mercury.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(8000))
                .circuitMeta(16)
                .output(dust, HG1223, 16)
                .EUt(VA[EV])
                .duration(240)
                .buildAndRegister();

        //  Staballoy
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Uranium238, 9)
                .input(dust, Titanium)
                .circuitMeta(10)
                .output(dust, Staballoy, 10)
                .EUt(VA[EV])
                .duration(260)
                .buildAndRegister();

        //  HMS-1J22 Alloy
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Nickel, 12)
                .input(dust, TinAlloy, 8)
                .input(dust, Chrome, 4)
                .input(dust, Phosphorus, 2)
                .input(dust, Silicon, 2)
                .circuitMeta(28)
                .output(dust, HMS1J22Alloy, 28)
                .EUt(VA[EV])
                .duration(580)
                .buildAndRegister();

        //  Inconel-792
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Nickel, 2)
                .input(dust, Niobium)
                .input(dust, Aluminium, 2)
                .input(dust, Nichrome)
                .circuitMeta(6)
                .output(dust, Inconel792, 6)
                .EUt(VA[IV])
                .duration(250)
                .buildAndRegister();

        //  Stellite
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Chrome, 9)
                .input(dust, Cobalt, 9)
                .input(dust, Manganese, 5)
                .input(dust, Titanium)
                .circuitMeta(24)
                .output(dust, Stellite, 24)
                .EUt(VA[EV])
                .duration(310)
                .buildAndRegister();

        //  Super Austenitic Stainless Steel-904L
        MIXER_RECIPES.recipeBuilder()
                .input(dust, StainlessSteel, 8)
                .input(dust, NickelZincFerrite, 4)
                .input(dust, Kanthal, 4)
                .input(dust, Molybdenum, 4)
                .circuitMeta(20)
                .output(dust, AusteniticStainlessSteel904L, 20)
                .EUt(VA[EV])
                .duration(600)
                .buildAndRegister();

        //  Tanmolyium Beta-C
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Titanium, 5)
                .input(dust, Molybdenum, 5)
                .input(dust, Vanadium, 2)
                .input(dust, Chrome, 3)
                .input(dust, Aluminium)
                .circuitMeta(16)
                .output(dust, TanmolyiumBetaC, 16)
                .EUt(VA[EV])
                .duration(400)
                .buildAndRegister();


    }

    private static void CasingAssembler() {
        ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(120)
                .input(plate, Talonite, 6)
                .input(frameGt, Talonite, 1)
                .circuitMeta(6)
                .outputs(GTQTMetaBlocks.blockMultiblockCasing1.getItemVariant(BlockMultiblockCasing1.CasingType.Talonite))
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(120)
                .input(plate, RhodiumPlatedPalladium, 6)
                .input(frameGt, RhodiumPlatedPalladium, 1)
                .circuitMeta(6)
                .outputs(GTQTMetaBlocks.blockMultiblockCasing4.getItemVariant(PD_TURBINE_CASING))
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(120)
                .input(plate, NaquadahAlloy, 6)
                .input(frameGt, NaquadahAlloy, 1)
                .circuitMeta(6)
                .outputs(GTQTMetaBlocks.blockMultiblockCasing4.getItemVariant(NQ_TURBINE_CASING))
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(30)
                .input(plate, Steel, 4)
                .input(gear, Steel, 1)
                .input(frameGt, Steel, 1)
                .fluidInputs(Polyethylene.getFluid(576))
                .circuitMeta(10)
                .outputs(GTQTMetaBlocks.blockMultiblockCasing5.getItemVariant(CLARIFIER_CASING, 4))
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(1920)
                .input(frameGt, TungstenSteel, 1)
                .input(plate, StainlessSteel, 4)
                .input(screw, NanometerBariumTitanate, 2)
                .fluidInputs(Zylon.getFluid(576))
                .circuitMeta(10)
                .outputs(GTQTMetaBlocks.blockMultiblockCasing5.getItemVariant(FLOATING_CASING, 4))
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(120)
                .input(plate, Orichalcum, 6)
                .input(frameGt, Orichalcum, 1)
                .circuitMeta(6)
                .outputs(GTQTMetaBlocks.blockMultiblockCasing5.getItemVariant(ST_TURBINE_CASING))
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(120)
                .input(plate, Adamantium, 6)
                .input(frameGt, Adamantium, 1)
                .circuitMeta(6)
                .outputs(GTQTMetaBlocks.blockMultiblockCasing5.getItemVariant(AD_TURBINE_CASING))
                .buildAndRegister();
    }

    /**
     * Create common Metal Casing recipe.
     *
     * <p>
     * This method will add two recipe of each Metal Casing,
     * one is crafting table recipe by Hammer (hard) and Wrench,
     * another is assembler recipe.
     * </p>
     *
     * @param regName          Register Name of recipe.
     * @param outputCasingType Variant Block class of {@code MetaBlock}.
     * @param outputCasing     Casing type of {@code MetaBlock}.
     * @param material         Basic {@code material} of Metal Casing,
     *                         means plate and frame material.
     */
    private static <T extends Enum<T> & IStringSerializable> void createCasingRecipe(String regName,
                                                                                     VariantBlock<T> outputCasingType,
                                                                                     T outputCasing,
                                                                                     Material material) {
        ModHandler.addShapedRecipe(true, regName, outputCasingType.getItemVariant(outputCasing, ConfigHolder.recipes.casingsPerCraft),
                "PhP", "PFP", "PwP",
                'P', new UnificationEntry(plate, material),
                'F', new UnificationEntry(frameGt, material));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, material, 6)
                .input(frameGt, material)
                .circuitMeta(6)
                .outputs(outputCasingType.getItemVariant(outputCasing))
                .EUt(VA[LV])
                .duration(50)
                .buildAndRegister();
    }

    /**
     * Create Metal Casing recipe with different plate-frame material.
     *
     * <p>
     * This method will add two recipe of each Metal Casing,
     * one is crafting table recipe by Hammer (hard) and Wrench,
     * another is assembler recipe.
     * </p>
     *
     * @param regName          Register Name of recipe.
     * @param outputCasingType Variant Block class of {@code MetaBlock}.
     * @param outputCasing     Casing type of {@code MetaBlock}.
     * @param plateMaterial    Plate {@code material} of Metal Casing.
     * @param frameMaterial    Frame {@code material} of Metal Casing.
     */
    private static <T extends Enum<T> & IStringSerializable> void createCasingRecipe(String regName,
                                                                                     VariantBlock<T> outputCasingType,
                                                                                     T outputCasing,
                                                                                     Material plateMaterial,
                                                                                     Material frameMaterial) {
        ModHandler.addShapedRecipe(true, regName, outputCasingType.getItemVariant(outputCasing, ConfigHolder.recipes.casingsPerCraft),
                "PhP", "PFP", "PwP",
                'P', new UnificationEntry(plate, plateMaterial),
                'F', new UnificationEntry(frameGt, frameMaterial));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, plateMaterial, 6)
                .input(frameGt, frameMaterial)
                .circuitMeta(6)
                .outputs(outputCasingType.getItemVariant(outputCasing, ConfigHolder.recipes.casingsPerCraft))
                .EUt(VA[LV])
                .duration(50)
                .buildAndRegister();
    }

    /**
     * Create Metal Casing recipe with different plate-frame material and new double plate material.
     *
     * <p>
     * This method will add two recipe of each Metal Casing,
     * one is crafting table recipe by Hammer (hard) and Wrench,
     * another is assembler recipe.
     * </p>
     *
     * @param regName             Register Name of recipe.
     * @param outputCasingType    Variant Block class of {@code MetaBlock}.
     * @param outputCasing        Casing type of {@code MetaBlock}.
     * @param plateDoubleMaterial Double Plate {@code material} of Metal Casing.
     * @param plateMaterial       Plate {@code material} of Metal Casing.
     * @param frameMaterial       Frame {@code material} of Metal Casing.
     */
    private static <T extends Enum<T> & IStringSerializable> void createCasingRecipe(String regName,
                                                                                     VariantBlock<T> outputCasingType,
                                                                                     T outputCasing,
                                                                                     Material plateDoubleMaterial,
                                                                                     Material plateMaterial,
                                                                                     Material frameMaterial) {
        ModHandler.addShapedRecipe(true, regName, outputCasingType.getItemVariant(outputCasing, ConfigHolder.recipes.casingsPerCraft),
                "PhP", "TFT", "PwP",
                'P', new UnificationEntry(plateDouble, plateDoubleMaterial),
                'T', new UnificationEntry(plate, plateMaterial),
                'F', new UnificationEntry(frameGt, frameMaterial));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plateDouble, plateDoubleMaterial, 4)
                .input(plate, plateMaterial, 2)
                .input(frameGt, frameMaterial)
                .circuitMeta(6)
                .outputs(outputCasingType.getItemVariant(outputCasing, ConfigHolder.recipes.casingsPerCraft))
                .EUt(VA[LV])
                .duration(50)
                .buildAndRegister();
    }
}
