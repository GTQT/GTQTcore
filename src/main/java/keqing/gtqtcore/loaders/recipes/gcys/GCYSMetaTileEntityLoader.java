package keqing.gtqtcore.loaders.recipes.gcys;

import gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities;
import gregicality.science.api.GCYSValues;
import gregtech.api.GTValues;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.BlockSteamCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.loaders.recipe.CraftingComponent;
import gregtech.loaders.recipe.MetaTileEntityLoader;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.ArrayUtils;

import static gregicality.science.common.metatileentities.GCYSMetaTileEntities.*;
import static gregtech.api.GTValues.UHV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.blocks.BlockHermeticCasing.HermeticCasingsType.HERMETIC_UHV;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.TRANSFORMER;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static gregtech.loaders.recipe.CraftingComponent.HULL;
import static gregtech.loaders.recipe.CraftingComponent.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class GCYSMetaTileEntityLoader {

    public static void init() {
        singleBlocks();
        energy();
        multiblocks();
        hulls();
    }

    private static void singleBlocks() {
        // Steam Pressure Machines
        ModHandler.addShapedRecipe(true, "steam_ejector", STEAM_EJECTOR.getStackForm(),
                "MGM", "LHL", "MEM",
                'M', new UnificationEntry(pipeNormalFluid, TinAlloy),
                'G', new UnificationEntry(block, Glass),
                'L', new UnificationEntry(pipeLargeFluid, TinAlloy),
                'H', MetaBlocks.STEAM_CASING.getItemVariant(BlockSteamCasing.SteamCasingType.STEEL_HULL),
                'E', new UnificationEntry(gear, Iron));

        ModHandler.addShapedRecipe(true, "small_steam_vacuum_chamber", SMALL_VACUUM_CHAMBER.getStackForm(),
                "WPW", "PHP", "MPM",
                'W', new UnificationEntry(plate, WroughtIron),
                'P', new UnificationEntry(plateDouble, Steel),
                'H', MetaBlocks.STEAM_CASING.getItemVariant(BlockSteamCasing.SteamCasingType.STEEL_HULL),
                'M', new UnificationEntry(pipeNormalFluid, TinAlloy));

        // Simple Machines
        MetaTileEntityLoader.registerMachineRecipe(true, DRYER, "WCW", "SHS", "WCW", 'W', CraftingComponent.CABLE, 'C', CraftingComponent.CIRCUIT, 'S', CraftingComponent.SPRING, 'H', CraftingComponent.HULL);


        // Hatches
        // Pressure Hatches
        // TODO improve all of these recipes and add the rest
        // TODO PEEK in later pressure pipes and hatches
        ModHandler.addShapedRecipe(true, "vacuum_hatch.uhv", PRESSURE_HATCH[GCYSValues.UHV].getStackForm(),
                "MP", "PH",
                'M', new UnificationEntry(pipeNormalFluid, Polytetrafluoroethylene),
                'P', new UnificationEntry(plateDouble, Ultimet),
                'H', MetaTileEntities.HULL[3].getStackForm());

        ModHandler.addShapedRecipe(true, "pressure_hatch_hatch.uhp", PRESSURE_HATCH[GCYSValues.UHP].getStackForm(),
                "MP", "PH",
                'M', new UnificationEntry(pipeNormalFluid, Polytetrafluoroethylene),
                'P', new UnificationEntry(plateDense, TungstenSteel),
                'H', MetaTileEntities.HULL[3].getStackForm());

        ModHandler.addShapedRecipe(true, "pressure_hatch_hatch.edp", PRESSURE_HATCH[GCYSValues.EDP].getStackForm(),
                "MP", "PH",
                'M', new UnificationEntry(pipeNormalFluid, Duranium),
                'P', new UnificationEntry(plateDense, NaquadahAlloy),
                'H', MetaTileEntities.HULL[4].getStackForm());

        ModHandler.removeRecipeByName("gregtech:hermetic_casing_max");
        ModHandler.addShapedRecipe(true, "hermetic_casing_max", MetaBlocks.HERMETIC_CASING.getItemVariant(HERMETIC_UHV),
                "PPP", "PFP", "PPP",
                'P', new UnificationEntry(OrePrefix.plate, Orichalcum),
                'F', new UnificationEntry(OrePrefix.pipeLargeFluid, Materials.Duranium));

        ModHandler.removeRecipeByName("gregtech:quantum_chest_uhv");
        ModHandler.addShapedRecipe(true, "quantum_chest_uhv", MetaTileEntities.QUANTUM_CHEST[9].getStackForm(),
                "CPC", "PHP", "CFC",
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.UHV),
                'P', new UnificationEntry(OrePrefix.plate, Orichalcum),
                'F', MetaItems.FIELD_GENERATOR_UV.getStackForm(),
                'H', MetaTileEntities.HULL[9].getStackForm());

        ModHandler.removeRecipeByName("gregtech:quantum_tank_uhv");
        ModHandler.addShapedRecipe(true, "quantum_tank_uhv", MetaTileEntities.QUANTUM_TANK[9].getStackForm(),
                "CGC", "PHP", "CUC",
                'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.UHV),
                'P', new UnificationEntry(OrePrefix.plate, Orichalcum),
                'U', MetaItems.ELECTRIC_PUMP_UV.getStackForm(),
                'G', MetaItems.FIELD_GENERATOR_UV.getStackForm(),
                'H', MetaBlocks.HERMETIC_CASING.getItemVariant(HERMETIC_UHV));
    }

    private static void energy() {
        // Energy Hatches
        // Input
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLY_LINE_RECIPES,
                new ItemStack[]{MetaTileEntities.HULL[8].getStackForm(), OreDictUnifier.get(cableGtSingle, Europium, 4),
                        ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(2), OreDictUnifier.get(circuit, MarkerMaterials.Tier.UHV),
                        OreDictUnifier.get(wireGtDouble, RutheniumTriniumAmericiumNeutronate, 2)},
                new FluidStack[]{SodiumPotassium.getFluid(12000), SolderingAlloy.getFluid(5760)});

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(MetaTileEntities.HULL[8])
                .input(cableGtSingle, Europium, 4)
                .input(NANO_POWER_IC, 2)
                .input(circuit, MarkerMaterials.Tier.UHV)
                .input(VOLTAGE_COIL_UHV, 2)
                .fluidInputs(SodiumPotassium.getFluid(12000))
                .fluidInputs(SolderingAlloy.getFluid(5760))
                .output(ENERGY_INPUT_HATCH[8])
                .duration(1000).EUt(VA[UHV]).buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,
                ENERGY_INPUT_HATCH[8].getStackForm(2), ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(),
                OreDictUnifier.get(wireGtDouble, RutheniumTriniumAmericiumNeutronate), OreDictUnifier.get(wireGtQuadruple, Europium, 2));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(TRANSFORMER[8])
                .input(ENERGY_INPUT_HATCH[8])
                .input(NANO_POWER_IC)
                .input(VOLTAGE_COIL_UHV)
                .input(wireGtQuadruple, Europium, 2)
                .output(ENERGY_INPUT_HATCH_4A[5])
                .duration(100).EUt(VA[UHV]).buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,
                ENERGY_INPUT_HATCH_4A[5].getStackForm(2), ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(2),
                OreDictUnifier.get(wireGtDouble, RutheniumTriniumAmericiumNeutronate), OreDictUnifier.get(wireGtOctal, Europium, 2));

        ASSEMBLER_RECIPES.recipeBuilder()
               // .input(ADJUSTABLE_TRANSFORMER[UHV])
                .input(ENERGY_INPUT_HATCH_4A[5])
                .input(NANO_POWER_IC, 2)
                .input(VOLTAGE_COIL_UHV)
                .input(wireGtOctal, Europium, 2)
                .output(ENERGY_INPUT_HATCH_16A[4])
                .duration(200).EUt(VA[UHV]).buildAndRegister();

        // Output
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLY_LINE_RECIPES,
                new ItemStack[]{MetaTileEntities.HULL[8].getStackForm(), OreDictUnifier.get(spring, Europium, 4),
                        ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(2), OreDictUnifier.get(circuit, MarkerMaterials.Tier.UHV),
                        OreDictUnifier.get(wireGtDouble, RutheniumTriniumAmericiumNeutronate, 2)},
                new FluidStack[]{SodiumPotassium.getFluid(12000), SolderingAlloy.getFluid(5760)});

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(MetaTileEntities.HULL[8])
                .input(spring, Europium, 4)
                .input(NANO_POWER_IC, 2)
                .input(circuit, MarkerMaterials.Tier.UHV)
                .input(VOLTAGE_COIL_UHV, 2)
                .fluidInputs(SodiumPotassium.getFluid(12000))
                .fluidInputs(SolderingAlloy.getFluid(5760))
                .output(ENERGY_OUTPUT_HATCH[8])
                .duration(1000).EUt(VA[UHV]).buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,
                ENERGY_OUTPUT_HATCH[8].getStackForm(2), ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(),
                OreDictUnifier.get(wireGtDouble, RutheniumTriniumAmericiumNeutronate), OreDictUnifier.get(wireGtQuadruple, Europium, 2));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(TRANSFORMER[8])
                .input(ENERGY_OUTPUT_HATCH[8])
                .input(NANO_POWER_IC)
                .input(VOLTAGE_COIL_UHV)
                .input(wireGtQuadruple, Europium, 2)
                .output(ENERGY_OUTPUT_HATCH_4A[5])
                .duration(100).EUt(VA[UHV]).buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,
                ENERGY_OUTPUT_HATCH_4A[5].getStackForm(2), ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(2),
                OreDictUnifier.get(wireGtDouble, RutheniumTriniumAmericiumNeutronate), OreDictUnifier.get(wireGtOctal, Europium, 2));

        ASSEMBLER_RECIPES.recipeBuilder()
              //  .input(ADJUSTABLE_TRANSFORMER[UHV])
                .input(ENERGY_OUTPUT_HATCH_4A[5])
                .input(NANO_POWER_IC, 2)
                .input(VOLTAGE_COIL_UHV)
                .input(wireGtOctal, Europium, 2)
                .output(ENERGY_OUTPUT_HATCH_16A[4])
                .duration(200).EUt(VA[UHV]).buildAndRegister();

        // Transformers
        // Regular
        MetaTileEntityLoader.registerMachineRecipe(ArrayUtils.subarray(TRANSFORMER, GTValues.UHV, GTValues.MAX), "WCC", "TH ", "WCC", 'W', POWER_COMPONENT, 'C', CABLE, 'T', CABLE_TIER_UP, 'H', HULL);

    }

    private static void multiblocks() {
        ModHandler.addShapedRecipe(true, "subsonic_axial_compressor", SUBSONIC_AXIAL_COMPRESSOR.getStackForm(),
                "PMP", "MHM", "KCK",
                'P', new UnificationEntry(pipeNormalFluid, StainlessSteel),
                'M', ELECTRIC_MOTOR_EV.getStackForm(),
                'H', MetaTileEntities.HULL[3].getStackForm(),
                'K', new UnificationEntry(cableGtSingle, BlackSteel),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.LuV)
        );

        ModHandler.addShapedRecipe(true, "supersonic_axial_compressor", SUPERSONIC_AXIAL_COMPRESSOR.getStackForm(),
                "PMP", "MHM", "KCK",
                'P', new UnificationEntry(pipeNormalFluid, NiobiumTitanium),
                'M', ELECTRIC_MOTOR_IV.getStackForm(),
                'H', MetaTileEntities.HULL[4].getStackForm(),
                'K', new UnificationEntry(cableGtSingle, Osmium),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.ZPM)
        );

        ModHandler.addShapedRecipe(true, "low_power_turbomolecular_pump", LOW_POWER_TURBOMOLECULAR_PUMP.getStackForm(),
                "PMP", "MHM", "KCK",
                'P', new UnificationEntry(pipeNormalFluid, Polytetrafluoroethylene),
                'M', ELECTRIC_MOTOR_EV.getStackForm(),
                'H', MetaTileEntities.HULL[3].getStackForm(),
                'K', new UnificationEntry(cableGtSingle, BlackSteel),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.LuV)
        );

        ModHandler.addShapedRecipe(true, "high_power_turbomolecular_pump", HIGH_POWER_TURBOMOLECULAR_PUMP.getStackForm(),
                "PMP", "MHM", "KCK",
                'P', new UnificationEntry(pipeNormalFluid, Polybenzimidazole),
                'M', ELECTRIC_MOTOR_IV.getStackForm(),
                'H', MetaTileEntities.HULL[4].getStackForm(),
                'K', new UnificationEntry(cableGtSingle, Osmium),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.ZPM)
        );

        ModHandler.addShapedRecipe(true, "rotary_hearth_furnace", GCYMMetaTileEntities.MEGA_BLAST_FURNACE.getStackForm(),
                "SCS", "FEF", "PKP",
                'S', new UnificationEntry(spring, Europium),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UHV),
                'F', FIELD_GENERATOR_UHV.getStackForm(),
                'E', ELECTRIC_BLAST_FURNACE.getStackForm(),
                'P', new UnificationEntry(plate, Vibranium),
                'K', new UnificationEntry(cableGtHex, SiliconCarbide));

        ModHandler.addShapedRecipe(true, "mega_vacuum_freezer", GCYMMetaTileEntities.MEGA_VACUUM_FREEZER.getStackForm(),
                "SCS", "FEF", "PKP",
                'S', new UnificationEntry(pipeHugeFluid, Duranium),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.UHV),
                'F', FIELD_GENERATOR_UHV.getStackForm(),
                'E', ELECTRIC_BLAST_FURNACE.getStackForm(),
                'P', new UnificationEntry(plate, Vibranium),
                'K', new UnificationEntry(cableGtHex, SiliconCarbide));
    }

    private static void hulls() {
        ModHandler.removeRecipeByName("gregtech:casing_uhv");
        //TODO remove the aaaaaaaaaaaaaa after CEu #1119 is merged
        ModHandler.addShapedRecipe(true, "casing_uhvaaaaaaaaaaaaaa", MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.UHV),
                "PPP", "PwP", "PPP",
                'P', new UnificationEntry(plate, Orichalcum));

        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, IntCircuitIngredient.getIntegratedCircuit(8), OreDictUnifier.get(plate, Neutronium, 8));
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Orichalcum, 8)
                .circuitMeta(8)
                .outputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.UHV))
                .duration(50).EUt(16).buildAndRegister();

        ModHandler.addShapedRecipe(true, "casing_uev", MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.UEV),
                "PPP", "PwP", "PPP",
                'P', new UnificationEntry(plate, Adamantium));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Adamantium, 8)
                .circuitMeta(8)
                .outputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.UEV))
                .duration(50).EUt(16).buildAndRegister();
    }
}
