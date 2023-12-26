package keqing.gtqtcore.loaders.recipes;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.loaders.recipe.CraftingComponent;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;

import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.*;


public class MetaTileEntityLoader {
    public static void init() {
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
