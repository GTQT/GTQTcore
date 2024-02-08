package keqing.gtqtcore.loaders.recipes;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockSteamCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.loaders.recipe.CraftingComponent;
import gregtech.loaders.recipe.MachineRecipeLoader;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;

import static gregicality.multiblocks.api.unification.GCYMMaterials.MolybdenumDisilicide;
import static gregicality.multiblocks.api.unification.GCYMMaterials.TitaniumCarbide;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.YttriumBariumCuprate;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.blocks.BlockMetalCasing.MetalCasingType.BRONZE_BRICKS;
import static gregtech.common.blocks.BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS;
import static gregtech.common.blocks.BlockWireCoil.CoilType.CUPRONICKEL;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static gregtech.loaders.recipe.CraftingComponent.*;
import static gregtech.loaders.recipe.CraftingComponent.CABLE_QUAD;
import static gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.*;


public class MetaTileEntityLoader {

    public static void init() {
        ModHandler.addShapedRecipe(true, "p_reactor", P_REACTOR.getStackForm(),
                "CGC", "ChC", "CGC",
                'G', new UnificationEntry(OrePrefix.gear, Wood),
                'C', MetaBlocks.STEAM_CASING.getItemVariant(BlockSteamCasing.SteamCasingType.WOOD_WALL));


        ModHandler.addShapedRecipe(true, "steam_compressor", GTQTMetaTileEntities.STEAM_COMPRESSOR.getStackForm(),
                "CGC", "CFC", "CGC",
                'G', new UnificationEntry(OrePrefix.gear, Materials.Potin),
                'F', MetaTileEntities.STEAM_COMPRESSOR_BRONZE.getStackForm(),
                'C', MetaBlocks.METAL_CASING.getItemVariant(BRONZE_BRICKS));

        ModHandler.addShapedRecipe(true, "steam_extractor", GTQTMetaTileEntities.STEAM_EXTRACTOR.getStackForm(),
                "CGC", "CFC", "CGC",
                'G', new UnificationEntry(OrePrefix.gear, Materials.Potin),
                'F', MetaTileEntities.STEAM_EXTRACTOR_BRONZE.getStackForm(),
                'C', MetaBlocks.METAL_CASING.getItemVariant(BRONZE_BRICKS));

        ModHandler.addShapedRecipe(true, "steam_ore_washer", GTQTMetaTileEntities.STEAM_ORE_WASHER.getStackForm(),
                "CGC", "CFC", "CGC",
                'G', new UnificationEntry(OrePrefix.gear, Materials.Potin),
                'F', STEAM_HAMMER_BRONZE.getStackForm(),
                'C', MetaBlocks.METAL_CASING.getItemVariant(BRONZE_BRICKS));

        ModHandler.addShapedRecipe(true, "steam_blast_furance", GTQTMetaTileEntities.STEAM_BLAST_FURANCE.getStackForm(),
                "CGC", "PFP", "CGC",
                'G', new UnificationEntry(OrePrefix.gear, Steel),
                'F', PRIMITIVE_BLAST_FURNACE.getStackForm(),
                'P',  new UnificationEntry(circuit, MarkerMaterials.Tier.LV),
                'C', MetaBlocks.METAL_CASING.getItemVariant(BRONZE_BRICKS));

        ModHandler.addShapedRecipe(true, "alloy_kiln", GTQTMetaTileEntities.ALLOY_KILN.getStackForm(),
                "PIP", "IwI", "PIP",
                'P', MetaBlocks.METAL_CASING.getItemVariant(BlockMetalCasing.MetalCasingType.COKE_BRICKS),
                'I', new UnificationEntry(OrePrefix.plate, Bronze));

        ModHandler.addShapedRecipe(true, "bronze_industrial_blast_furnace",
                GTQTMetaTileEntities.INDUSTRIAL_PRIMITIVE_BLAST_FURNACE.getStackForm(),
                "BPB", "PCP", "BPB",
                'C',  new UnificationEntry(circuit, MarkerMaterials.Tier.LV),
                'P', new UnificationEntry(OrePrefix.plateDouble, Steel),
                'B', PRIMITIVE_BLAST_FURNACE.getStackForm());

        ModHandler.addShapedRecipe(true, "crystallization_crucible", CRYSTALLIZATION_CRUCIBLE.getStackForm(),
                "CMC", "LHL", "PCP",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'M', new UnificationEntry(plateDouble, MolybdenumDisilicide),
                'L', new UnificationEntry(pipeNormalFluid, Titanium),
                'H', MetaTileEntities.HULL[3].getStackForm(),
                'P', new UnificationEntry(plate, Titanium)
        );

        ModHandler.addShapedRecipe(true, "distillation_tower", GTQTMetaTileEntities.DISTILLATION_TOWER.getStackForm(),
                "CBC", "FMF", "CBC", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'B',
                new UnificationEntry(OrePrefix.pipeLargeFluid, Steel), 'C',
                new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.LV), 'F', MetaItems.ELECTRIC_PUMP_LV);


        ModHandler.addShapedRecipe(true, "cracking_unit", GTQTMetaTileEntities.CRACKER.getStackForm(), "CEC", "PHP", "CEC",
                'C', MetaBlocks.WIRE_COIL.getItemVariant(CUPRONICKEL), 'E', MetaItems.ELECTRIC_PUMP_LV, 'P',
                new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.LV), 'H',
                MetaTileEntities.HULL[GTValues.LV].getStackForm());

        ModHandler.addShapedRecipe(true, "pyrolyse_oven", GTQTMetaTileEntities.PYROLYSE_OVEN.getStackForm(), "WEP", "EME",
                "WCP", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'W', MetaItems.ELECTRIC_PISTON_LV, 'P',
                new UnificationEntry(OrePrefix.wireGtQuadruple, Aluminium), 'E',
                new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.LV), 'C', MetaItems.ELECTRIC_PUMP_LV);

        ModHandler.addShapedRecipe(true, "vacuum_freezer", GTQTMetaTileEntities.VACUUM_FREEZER.getStackForm(), "PPP", "CMC",
                "WCW", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'P',
                MetaItems.ELECTRIC_PUMP_LV, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.LV), 'W',
                new UnificationEntry(OrePrefix.cableGtSingle, Aluminium));

        ModHandler.addShapedRecipe(true, "assembler_line", GTQTMetaTileEntities.ASSEMBLY_LINE.getStackForm(), "PPP", "CMC",
                "WCW", 'M', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'P',
                EMITTER_HV, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.HV), 'W',
                new UnificationEntry(OrePrefix.cableGtSingle, Gold));

        ModHandler.addShapedRecipe(true, "reaction_furnace", REACTION_FURNACE.getStackForm(),
                "KSK", "CHC", "PPP",
                'K', new UnificationEntry(cableGtQuadruple, Aluminium),
                'S', new UnificationEntry(spring, Steel),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.MV),
                'H', MetaTileEntities.HULL[2].getStackForm(),
                'P', new UnificationEntry(plate, Invar)
        );

        ModHandler.addShapedRecipe(true, "roaster", ROASTER.getStackForm(),
                "KSK", "CHC", "PPP",
                'K', new UnificationEntry(cableGtQuadruple, Platinum),
                'S', new UnificationEntry(spring, Tungsten),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.EV),
                'H', MetaTileEntities.HULL[3].getStackForm(),
                'P', new UnificationEntry(plate, TitaniumCarbide)
        );

        ModHandler.addShapedRecipe(true, "nanoscale_fabricator", NANOSCALE_FABRICATOR.getStackForm(),
                "KSK", "EHE", "CFC",
                'K', new UnificationEntry(cableGtSingle, Europium),
                'S', SENSOR_UHV.getStackForm(),
                'E', EMITTER_UHV.getStackForm(),
                'H', MetaTileEntities.HULL[8].getStackForm(),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UEV),
                'F', FIELD_GENERATOR_UHV.getStackForm()
        );

        ModHandler.addShapedRecipe(true, "cvd_unit", CVD_UNIT.getStackForm(),
                "PKP", "CHC", "ESE",
                'P', new UnificationEntry(plate, BlueSteel),
                'K', new UnificationEntry(cableGtSingle, Aluminium),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.EV),
                'H', MetaTileEntities.HULL[3].getStackForm(),
                'S', SENSOR_EV.getStackForm(),
                'E', EMITTER_EV.getStackForm()
        );

        ModHandler.addShapedRecipe(true, "burner_reactor", BURNER_REACTOR.getStackForm(),
                "KRK", "IHI", "CMC",
                'K', new UnificationEntry(cableGtSingle, Platinum),
                'R', new UnificationEntry(rotor, TungstenSteel),
                'I', new UnificationEntry(pipeSmallFluid, Tungsten),
                'H', MetaTileEntities.HULL[4].getStackForm(),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'M', ELECTRIC_MOTOR_IV.getStackForm()
        );

        ModHandler.addShapedRecipe(true, "cryo_reactor", CRYOGENIC_REACTOR.getStackForm(),
                "KRK", "IHI", "CWC",
                'K', new UnificationEntry(cableGtSingle, Platinum),
                'R', new UnificationEntry(rotor, Titanium),
                'I', new UnificationEntry(pipeSmallFluid, StainlessSteel),
                'H', MetaTileEntities.HULL[4].getStackForm(),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'W', ELECTRIC_PUMP_IV.getStackForm()
        );

        ModHandler.addShapedRecipe(true, "fracker", HYDRAULIC_FRACKER.getStackForm(),
                "CLC", "GHG", "PPP",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UV),
                'L', new UnificationEntry(pipeLargeFluid, Naquadah),
                'G', new UnificationEntry(gear, NaquadahAlloy),
                'H', MetaTileEntities.HULL[6].getStackForm(),
                'P', ELECTRIC_PUMP_ZPM.getStackForm()
        );

        ModHandler.addShapedRecipe(true, "sonicator", SONICATOR.getStackForm(),
                "LFL", "PHP", "CPC",
                'L', new UnificationEntry(pipeLargeFluid, Naquadah),
                'F', FIELD_GENERATOR_UV.getStackForm(),
                'P', ELECTRIC_PUMP_UV.getStackForm(),
                'H', MetaTileEntities.HULL[7].getStackForm(),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UV)
        );

        ModHandler.addShapedRecipe(true, "catalytic_reformer", CATALYTIC_REFORMER.getStackForm(),
                "MCM", "PHP", "MKM",
                'M', new UnificationEntry(pipeNormalFluid, StainlessSteel),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'P', ELECTRIC_PUMP_EV.getStackForm(),
                'H', MetaTileEntities.HULL[3].getStackForm(),
                'K', new UnificationEntry(cableGtDouble, Aluminium)
        );

        ModHandler.addShapedRecipe(true, "industrial_drill", INDUSTRIAL_DRILL.getStackForm(),
                "PKP", "CHC", "MMM",
                'P', ELECTRIC_PISTON_UV.getStackForm(),
                'K', new UnificationEntry(cableGtQuadruple, YttriumBariumCuprate),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UHV),
                'H', MetaTileEntities.HULL[7].getStackForm(),
                'M', ELECTRIC_MOTOR_UV.getStackForm()
        );
            ModHandler.addShapedRecipe(true, "crystallization_crucible", CRYSTALLIZATION_CRUCIBLE.getStackForm(),
                    "CMC", "LHL", "PCP",
                    'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                    'M', new UnificationEntry(plateDouble, MolybdenumDisilicide),
                    'L', new UnificationEntry(pipeNormalFluid, Titanium),
                    'H', MetaTileEntities.HULL[3].getStackForm(),
                    'P', new UnificationEntry(plate, Titanium)
            );

            ModHandler.addShapedRecipe(true, "roaster", ROASTER.getStackForm(),
                    "KSK", "CHC", "PPP",
                    'K', new UnificationEntry(cableGtQuadruple, Platinum),
                    'S', new UnificationEntry(spring, Tungsten),
                    'C', new UnificationEntry(circuit, MarkerMaterials.Tier.EV),
                    'H', MetaTileEntities.HULL[3].getStackForm(),
                    'P', new UnificationEntry(plate, TitaniumCarbide)
            );

            ModHandler.addShapedRecipe(true, "nanoscale_fabricator", NANOSCALE_FABRICATOR.getStackForm(),
                    "KSK", "EHE", "CFC",
                    'K', new UnificationEntry(cableGtSingle, Europium),
                    'S', SENSOR_UHV.getStackForm(),
                    'E', EMITTER_UHV.getStackForm(),
                    'H', MetaTileEntities.HULL[8].getStackForm(),
                    'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UEV),
                    'F', FIELD_GENERATOR_UHV.getStackForm()
            );

            ModHandler.addShapedRecipe(true, "cvd_unit", CVD_UNIT.getStackForm(),
                    "PKP", "CHC", "ESE",
                    'P', new UnificationEntry(plate, BlueSteel),
                    'K', new UnificationEntry(cableGtSingle, Aluminium),
                    'C', new UnificationEntry(circuit, MarkerMaterials.Tier.EV),
                    'H', MetaTileEntities.HULL[3].getStackForm(),
                    'S', SENSOR_EV.getStackForm(),
                    'E', EMITTER_EV.getStackForm()
            );

            ModHandler.addShapedRecipe(true, "burner_reactor", BURNER_REACTOR.getStackForm(),
                    "KRK", "IHI", "CMC",
                    'K', new UnificationEntry(cableGtSingle, Platinum),
                    'R', new UnificationEntry(rotor, TungstenSteel),
                    'I', new UnificationEntry(pipeSmallFluid, Tungsten),
                    'H', MetaTileEntities.HULL[4].getStackForm(),
                    'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                    'M', ELECTRIC_MOTOR_IV.getStackForm()
            );

            ModHandler.addShapedRecipe(true, "cryo_reactor", CRYOGENIC_REACTOR.getStackForm(),
                    "KRK", "IHI", "CWC",
                    'K', new UnificationEntry(cableGtSingle, Platinum),
                    'R', new UnificationEntry(rotor, Titanium),
                    'I', new UnificationEntry(pipeSmallFluid, StainlessSteel),
                    'H', MetaTileEntities.HULL[4].getStackForm(),
                    'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                    'W', ELECTRIC_PUMP_IV.getStackForm()
            );

            ModHandler.addShapedRecipe(true, "fracker", HYDRAULIC_FRACKER.getStackForm(),
                    "CLC", "GHG", "PPP",
                    'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UV),
                    'L', new UnificationEntry(pipeLargeFluid, Naquadah),
                    'G', new UnificationEntry(gear, NaquadahAlloy),
                    'H', MetaTileEntities.HULL[6].getStackForm(),
                    'P', ELECTRIC_PUMP_ZPM.getStackForm()
            );

            ModHandler.addShapedRecipe(true, "sonicator", SONICATOR.getStackForm(),
                    "LFL", "PHP", "CPC",
                    'L', new UnificationEntry(pipeLargeFluid, Naquadah),
                    'F', FIELD_GENERATOR_UV.getStackForm(),
                    'P', ELECTRIC_PUMP_UV.getStackForm(),
                    'H', MetaTileEntities.HULL[7].getStackForm(),
                    'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UV)
            );

            ModHandler.addShapedRecipe(true, "catalytic_reformer", CATALYTIC_REFORMER.getStackForm(),
                    "MCM", "PHP", "MKM",
                    'M', new UnificationEntry(pipeNormalFluid, StainlessSteel),
                    'C', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                    'P', ELECTRIC_PUMP_EV.getStackForm(),
                    'H', MetaTileEntities.HULL[3].getStackForm(),
                    'K', new UnificationEntry(cableGtDouble, Aluminium)
            );

            ModHandler.addShapedRecipe(true, "industrial_drill", INDUSTRIAL_DRILL.getStackForm(),
                    "PKP", "CHC", "MMM",
                    'P', ELECTRIC_PISTON_UV.getStackForm(),
                    'K', new UnificationEntry(cableGtQuadruple, YttriumBariumCuprate),
                    'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UHV),
                    'H', MetaTileEntities.HULL[7].getStackForm(),
                    'M', ELECTRIC_MOTOR_UV.getStackForm()
            );
        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, KQCC_COMPUTATION_HATCH_RECEIVER,
                "GEG", "PHP", "WXW",
                'E', CraftingComponent.EMITTER,
                'H', CraftingComponent.HULL,
                'P', CraftingComponent.PLATE,
                'G', CraftingComponent.SENSOR,
                'W', CraftingComponent.CABLE,
                'X', CraftingComponent.CIRCUIT);

        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, KQCC_COMPUTATION_HATCH_TRANSMITTER,
                "EGE", "PHP", "WXW",
                'E', CraftingComponent.EMITTER,
                'H', CraftingComponent.HULL,
                'P', CraftingComponent.PLATE,
                'G', CraftingComponent.SENSOR,
                'W', CraftingComponent.CABLE,
                'X', CraftingComponent.CIRCUIT);

        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, ULTRAVIOLET_LAMP_CHAMBER,
                "GEG", "PHP", "WXW",
                'E', CraftingComponent.EMITTER,
                'H', CraftingComponent.HULL,
                'P', CraftingComponent.PLATE,
                'G', CraftingComponent.GLASS,
                'W', CraftingComponent.CABLE,
                'X', CraftingComponent.CIRCUIT);

        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, VACUUM_CHAMBER,
                "GCG", "PHP", "GWG",
                'W', CraftingComponent.CABLE,
                'C', CraftingComponent.CIRCUIT,
                'P', CraftingComponent.PUMP,
                'G', CraftingComponent.GLASS,
                'H', CraftingComponent.HULL);

        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, DECAY_CHAMBER,
                "CFC", "RHR", "WFW",
                'H', CraftingComponent.HULL,
                'R', CraftingComponent.DOUBLE_PLATE,
                'F', CraftingComponent.FIELD_GENERATOR,
                'C', CraftingComponent.CIRCUIT,
                'W', CraftingComponent.CABLE);


        gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe(true, COMPONENT_ASSEMBLER,
                "PPP", "CHR", "WXW",
                'P', CraftingComponent.PLATE,
                'H', CraftingComponent.HULL,
                'C', CraftingComponent.CONVEYOR,
                'R', CraftingComponent.ROBOT_ARM,
                'W', CraftingComponent.CABLE,
                'X', CraftingComponent.CIRCUIT);
/*
        ModHandler.addShapedRecipe(true, "diesel_generator_ev", GTQTMetaTileEntities.COMBUSTION_GENERATOR[0].getStackForm(),
                "PCP", "EME", "GWG", 'M', HULL[GTValues.EV].getStackForm(), 'P',
                MetaItems.ELECTRIC_PISTON_EV, 'E', MetaItems.ELECTRIC_MOTOR_EV ,'C',
                new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.EV), 'W',
                new UnificationEntry(OrePrefix.cableGtSingle, Materials.Aluminium), 'G',
                new UnificationEntry(OrePrefix.gear, Materials.Titanium));
        ModHandler.addShapedRecipe(true, "diesel_generator_iv", GTQTMetaTileEntities.COMBUSTION_GENERATOR[1].getStackForm(),
                "PCP", "EME", "GWG", 'M', HULL[GTValues.IV].getStackForm(), 'P',
                MetaItems.ELECTRIC_PISTON_IV, 'E', MetaItems.ELECTRIC_MOTOR_IV, 'C',
                new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.IV), 'W',
                new UnificationEntry(OrePrefix.cableGtSingle, Materials.Platinum), 'G',
                new UnificationEntry(OrePrefix.gear, Materials.TungstenSteel));

        ModHandler.addShapedRecipe(true, "gas_turbine_ev", GTQTMetaTileEntities.GAS_TURBINE[0].getStackForm(), "CRC", "RMR",
                "EWE", 'M', HULL[GTValues.EV].getStackForm(), 'E', MetaItems.ELECTRIC_MOTOR_EV, 'R',
                new UnificationEntry(OrePrefix.rotor, Materials.Aluminium), 'C',
                new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.EV), 'W',
                new UnificationEntry(OrePrefix.cableGtSingle, Materials.Aluminium));
        ModHandler.addShapedRecipe(true, "gas_turbine_iv", GTQTMetaTileEntities.GAS_TURBINE[1].getStackForm(), "CRC", "RMR",
                "EWE", 'M', HULL[GTValues.IV].getStackForm(), 'E', MetaItems.ELECTRIC_MOTOR_IV, 'R',
                new UnificationEntry(OrePrefix.rotor, Materials.StainlessSteel), 'C',
                new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.IV), 'W',
                new UnificationEntry(OrePrefix.cableGtSingle, Materials.Platinum));

        ModHandler.addShapedRecipe(true, "steam_turbine_ev", GTQTMetaTileEntities.STEAM_TURBINE[0].getStackForm(), "PCP",
                "RMR", "EWE", 'M', HULL[GTValues.EV].getStackForm(), 'E', MetaItems.ELECTRIC_MOTOR_EV,
                'R', new UnificationEntry(OrePrefix.rotor, Materials.Aluminium), 'C',
                new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.EV), 'W',
                new UnificationEntry(OrePrefix.cableGtSingle, Materials.Aluminium), 'P',
                new UnificationEntry(OrePrefix.pipeNormalFluid, Materials.StainlessSteel));
        ModHandler.addShapedRecipe(true, "steam_turbine_iv", GTQTMetaTileEntities.STEAM_TURBINE[1].getStackForm(), "PCP",
                "RMR", "EWE", 'M', HULL[GTValues.IV].getStackForm(), 'E', MetaItems.ELECTRIC_MOTOR_IV,
                'R', new UnificationEntry(OrePrefix.rotor, Materials.StainlessSteel), 'C',
                new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.IV), 'W',
                new UnificationEntry(OrePrefix.cableGtSingle, Materials.Platinum), 'P',
                new UnificationEntry(OrePrefix.pipeNormalFluid, Materials.TungstenSteel));

        ModHandler.addShapedRecipe(true, "rocket_engine_ev", GTQTMetaTileEntities.ROCKET_ENGINE[0].getStackForm(),
                "PPP", "CMC", "EEE", 'M', HULL[GTValues.EV].getStackForm(), 'P',
                MetaItems.ELECTRIC_PISTON_EV, 'E', MetaItems.ELECTRIC_MOTOR_EV ,'C',
                new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.EV));

        ModHandler.addShapedRecipe(true, "rocket_engine_iv", GTQTMetaTileEntities.ROCKET_ENGINE[1].getStackForm(),
                "PPP", "CMC", "EEE", 'M', HULL[GTValues.IV].getStackForm(), 'P',
                MetaItems.ELECTRIC_PISTON_IV, 'E', MetaItems.ELECTRIC_MOTOR_IV, 'C',
                new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.IV));

        ModHandler.addShapedRecipe(true, "rocket_engine_luv", GTQTMetaTileEntities.ROCKET_ENGINE[2].getStackForm(),
                "PPP", "CMC", "EEE", 'M', HULL[GTValues.LuV].getStackForm(), 'P',
                MetaItems.ELECTRIC_PISTON_LUV, 'E', MetaItems.ELECTRIC_MOTOR_LuV, 'C',
                new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.LuV));
*/

        ModHandler.addShapedRecipe(true, "naquadah_reactor_iv", GTQTMetaTileEntities.NAQUADAH_REACTOR[0].getStackForm(),
                "PPP", "CFC", "EME", 'M', HULL[GTValues.IV].getStackForm(),
                'P', MetaItems.ELECTRIC_PISTON_IV,
                'E', MetaTileEntities.CHEMICAL_REACTOR[GTValues.IV].getStackForm(),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.IV),
                'F', MetaItems.ELECTRIC_PUMP_IV);

        ModHandler.addShapedRecipe(true, "naquadah_reactor_luv", GTQTMetaTileEntities.NAQUADAH_REACTOR[1].getStackForm(),
                "PPP", "CFC", "EME", 'M', HULL[GTValues.LuV].getStackForm(),
                'P', MetaItems.ELECTRIC_PISTON_LUV,
                'E', MetaTileEntities.CHEMICAL_REACTOR[GTValues.LuV].getStackForm(),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.LuV),
                'F', MetaItems.ELECTRIC_PUMP_LuV);

        ModHandler.addShapedRecipe(true, "naquadah_reactor_zpm", GTQTMetaTileEntities.NAQUADAH_REACTOR[2].getStackForm(),
                "PPP", "CFC", "EME", 'M', HULL[GTValues.ZPM].getStackForm(),
                'P', MetaItems.ELECTRIC_PISTON_ZPM,
                'E', MetaTileEntities.CHEMICAL_REACTOR[GTValues.ZPM].getStackForm(),
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.ZPM),
                'F', MetaItems.ELECTRIC_PUMP_ZPM);

    }
}
