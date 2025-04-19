package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterial;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.common.blocks.BlockComputerCasing;
import gregtech.common.blocks.BlockFusionCasing;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.MarkerMaterials.Color.VALUES;
import static gregtech.api.unification.material.MarkerMaterials.Tier.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.blocks.MetaBlocks.OPTICAL_PIPES;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.POWER_TRANSFORMER;

public class removeRecipes {
    public static void init() {
        ModHandler.removeRecipeByName("gregtech:shape_mold_credit");

        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, Ethylene.getFluid(1000), Chlorine.getFluid(2000));
        // 钢矿车车轮 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, OreDictUnifier.get(stick, Steel, 1), OreDictUnifier.get(ring, Steel, 2));
        // 铁矿车车轮 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, OreDictUnifier.get(stick, Iron, 1), OreDictUnifier.get(ring, Iron, 2));

        // 酚醛树脂电路基板 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(dust, Wood), IntCircuitIngredient.getIntegratedCircuit(1)}, new FluidStack[]{Glue.getFluid(50)});
        // 塑料电路基板 * 1
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate, Polyethylene), OreDictUnifier.get(foil, Copper, 4)}, new FluidStack[]{SulfuricAcid.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate, PolyvinylChloride), OreDictUnifier.get(foil, Copper, 4)}, new FluidStack[]{SulfuricAcid.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate, Polytetrafluoroethylene), OreDictUnifier.get(foil, Copper, 4)}, new FluidStack[]{SulfuricAcid.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate, Polybenzimidazole), OreDictUnifier.get(foil, Copper, 4)}, new FluidStack[]{SulfuricAcid.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate, Polyethylene), OreDictUnifier.get(foil, Copper, 4)}, new FluidStack[]{SulfuricAcid.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate, PolyvinylChloride), OreDictUnifier.get(foil, Copper, 4)}, new FluidStack[]{SulfuricAcid.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate, Polytetrafluoroethylene), OreDictUnifier.get(foil, Copper, 4)}, new FluidStack[]{SulfuricAcid.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate, Polybenzimidazole), OreDictUnifier.get(foil, Copper, 4)}, new FluidStack[]{SulfuricAcid.getFluid(250)});
        // 环氧树脂电路板 * 1
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate, Epoxy), OreDictUnifier.get(foil, Gold, 8)}, new FluidStack[]{SulfuricAcid.getFluid(500)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate, Epoxy), OreDictUnifier.get(foil, Gold, 8)}, new FluidStack[]{SulfuricAcid.getFluid(500)});
        // 纤维强化电路基板 * 1
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate, ReinforcedEpoxyResin), OreDictUnifier.get(foil, AnnealedCopper, 8)}, new FluidStack[]{SulfuricAcid.getFluid(500)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate, ReinforcedEpoxyResin), OreDictUnifier.get(foil, AnnealedCopper, 8)}, new FluidStack[]{SulfuricAcid.getFluid(500)});
        // 多层纤维强化电路基板 * 1
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{MetaItems.FIBER_BOARD.getStackForm(2), OreDictUnifier.get(foil, Palladium, 8)}, new FluidStack[]{SulfuricAcid.getFluid(500)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{MetaItems.FIBER_BOARD.getStackForm(2), OreDictUnifier.get(foil, Palladium, 8)}, new FluidStack[]{SulfuricAcid.getFluid(500)});
        // 硫酸 * 1000
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(dust, Sulfur, 4), IntCircuitIngredient.getIntegratedCircuit(24)}, new FluidStack[]{Water.getFluid(4000)});

        // 聚变线圈方块 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL), MetaItems.FIELD_GENERATOR_IV.getStackForm(2), MetaItems.ELECTRIC_PUMP_IV.getStackForm(), MetaItems.NEUTRON_REFLECTOR.getStackForm(2), OreDictUnifier.get(circuit, LuV, 4), OreDictUnifier.get(pipeSmallFluid, Naquadah, 4), OreDictUnifier.get(plate, Europium, 4)}, new FluidStack[]{VanadiumGallium.getFluid(576)});
        // 超导线圈方块 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtDouble, IndiumTinBariumTitaniumCuprate, 32), OreDictUnifier.get(foil, NiobiumTitanium, 32)}, new FluidStack[]{Trinium.getFluid(3456)});
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtDouble, UraniumRhodiumDinaquadide, 16), OreDictUnifier.get(foil, NiobiumTitanium, 16)}, new FluidStack[]{Trinium.getFluid(2304)});
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtDouble, EnrichedNaquadahTriniumEuropiumDuranide, 8), OreDictUnifier.get(foil, NiobiumTitanium, 8)}, new FluidStack[]{Trinium.getFluid(1152)});
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtDouble, RutheniumTriniumAmericiumNeutronate, 4), OreDictUnifier.get(foil, NiobiumTitanium, 4)}, new FluidStack[]{Trinium.getFluid(576)});


        // 铝粒 * 3
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(dust, Ruby, 1));
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(gem, Ruby, 1));
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(dust, GreenSapphire, 1));
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(gem, GreenSapphire, 1));
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(dust, Sapphire, 1));
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(gem, Sapphire, 1));
        // 铝锭 * 1
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(dust, Aluminium, 1), IntCircuitIngredient.getIntegratedCircuit(1));
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, new ItemStack[]{OreDictUnifier.get(dust, Aluminium, 1), IntCircuitIngredient.getIntegratedCircuit(2)}, new FluidStack[]{Nitrogen.getFluid(1000)});

        // 核聚变反应堆控制电脑MK-I * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLY_LINE_RECIPES, new ItemStack[]{MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL), OreDictUnifier.get(circuit, ZPM, 4), OreDictUnifier.get(plateDouble, Plutonium241), OreDictUnifier.get(plateDouble, Osmiridium), MetaItems.FIELD_GENERATOR_IV.getStackForm(2), MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64), OreDictUnifier.get(wireGtSingle, IndiumTinBariumTitaniumCuprate, 32)}, new FluidStack[]{SolderingAlloy.getFluid(1152), NiobiumTitanium.getFluid(1152)});
        // 核聚变反应堆控制电脑MK-II * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLY_LINE_RECIPES, new ItemStack[]{MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.FUSION_COIL), OreDictUnifier.get(circuit, UV, 4), OreDictUnifier.get(plateDouble, Naquadria), OreDictUnifier.get(plateDouble, Europium), MetaItems.FIELD_GENERATOR_LuV.getStackForm(2), MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64), MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(32), OreDictUnifier.get(wireGtSingle, UraniumRhodiumDinaquadide, 32)}, new FluidStack[]{SolderingAlloy.getFluid(1152), VanadiumGallium.getFluid(1152)});
        // 核聚变反应堆控制电脑MK-III * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLY_LINE_RECIPES, new ItemStack[]{MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.FUSION_COIL), OreDictUnifier.get(circuit, UHV, 4), MetaItems.QUANTUM_STAR.getStackForm(), OreDictUnifier.get(plateDouble, Americium), MetaItems.FIELD_GENERATOR_ZPM.getStackForm(2), MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64), MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64), OreDictUnifier.get(wireGtSingle, EnrichedNaquadahTriniumEuropiumDuranide, 32)}, new FluidStack[]{SolderingAlloy.getFluid(1152), YttriumBariumCuprate.getFluid(1152)});
        // 数据库 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLY_LINE_RECIPES, new ItemStack[]{MetaBlocks.COMPUTER_CASING.getItemVariant(BlockComputerCasing.CasingType.COMPUTER_CASING), OreDictUnifier.get(circuit, LuV, 8), MetaItems.TOOL_DATA_ORB.getStackForm(), OreDictUnifier.get(wireFine, Cobalt, 64), OreDictUnifier.get(wireFine, Copper, 64), new ItemStack(OPTICAL_PIPES[0], 4), OreDictUnifier.get(wireGtDouble, IndiumTinBariumTitaniumCuprate, 16)}, new FluidStack[]{SolderingAlloy.getFluid(288), Lubricant.getFluid(500)});
        //晶体芯片原料
        GTRecipeHandler.removeRecipesByInputs(AUTOCLAVE_RECIPES, new ItemStack[]{OreDictUnifier.get(gemExquisite, Olivine, 1)}, new FluidStack[]{Europium.getFluid(16)});
        GTRecipeHandler.removeRecipesByInputs(AUTOCLAVE_RECIPES, new ItemStack[]{OreDictUnifier.get(gemExquisite, Emerald, 1)}, new FluidStack[]{Europium.getFluid(16)});

        // 晶体芯片部件原料 * 9
        GTRecipeHandler.removeRecipesByInputs(FORGE_HAMMER_RECIPES, MetaItems.RAW_CRYSTAL_CHIP.getStackForm());

        // 晶体芯片原料 * 1
        GTRecipeHandler.removeRecipesByInputs(AUTOCLAVE_RECIPES, new ItemStack[]{MetaItems.RAW_CRYSTAL_CHIP_PART.getStackForm()}, new FluidStack[]{BacterialSludge.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(AUTOCLAVE_RECIPES, new ItemStack[]{MetaItems.RAW_CRYSTAL_CHIP_PART.getStackForm()}, new FluidStack[]{Mutagen.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(AUTOCLAVE_RECIPES, new ItemStack[]{MetaItems.RAW_CRYSTAL_CHIP_PART.getStackForm()}, new FluidStack[]{Europium.getFluid(16)});

        //
        // 刻蚀水晶芯片 * 1
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, new ItemStack[]{OreDictUnifier.get(plate, Olivine, 1), MetaItems.RAW_CRYSTAL_CHIP.getStackForm()}, new FluidStack[]{Helium.getFluid(1000)});
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, new ItemStack[]{OreDictUnifier.get(plate, Emerald, 1), MetaItems.RAW_CRYSTAL_CHIP.getStackForm()}, new FluidStack[]{Helium.getFluid(1000)});
        // 晶体CPU * 1
        GTRecipeHandler.removeRecipesByInputs(LASER_ENGRAVER_RECIPES, MetaItems.ENGRAVED_CRYSTAL_CHIP.getStackForm(), OreDictUnifier.get(craftingLens, MarkerMaterials.Color.Lime, 1));

        // 有源变压器 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{POWER_TRANSFORMER[6].getStackForm(), OreDictUnifier.get(circuit, LuV, 2), OreDictUnifier.get(wireGtSingle, IndiumTinBariumTitaniumCuprate, 8), ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(2)}, new FluidStack[]{PCBCoolant.getFluid(1000)});

        // 四氯化硅 * 1000
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(dust, Silicon, 1), IntCircuitIngredient.getIntegratedCircuit(1)}, new FluidStack[]{Chlorine.getFluid(4000)});

        ModHandler.removeRecipeByName("gregtech:vacuum_tube");

        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, GLASS_TUBE.getStackForm(), OreDictUnifier.get(bolt, Steel,2), OreDictUnifier.get(wireGtSingle, Copper, 2), IntCircuitIngredient.getIntegratedCircuit(1));

        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{GLASS_TUBE.getStackForm(), OreDictUnifier.get(bolt, Steel,2), OreDictUnifier.get(wireGtSingle, Copper, 2)}, new FluidStack[]{RedAlloy.getFluid(18)});

        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{GLASS_TUBE.getStackForm(), OreDictUnifier.get(bolt, Steel,2), OreDictUnifier.get(wireGtSingle, AnnealedCopper, 2)}, new FluidStack[]{RedAlloy.getFluid(18)});

    }

}
