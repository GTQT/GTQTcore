package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.common.blocks.BlockFusionCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;


import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.MarkerMaterials.Tier.*;
import static gregtech.api.unification.material.Materials.*;

import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.Vibranium;

public class removeRecipes {
    public static void init() {
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, Ethylene.getFluid(1000), Chlorine.getFluid(2000));

        // 钢矿车车轮 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, OreDictUnifier.get(stick, Steel,1),OreDictUnifier.get(ring, Steel,2));
        // 铁矿车车轮 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, OreDictUnifier.get(stick, Iron,1),OreDictUnifier.get(ring, Iron,2));
        // 白铜线圈方块 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,   new ItemStack[]{OreDictUnifier.get(wireGtDouble, Cupronickel,8),  OreDictUnifier.get(foil, Bronze,8)},   new FluidStack[]{TinAlloy.getFluid(144)});
        // 坎塔尔合金线圈方块 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,  new ItemStack[]{OreDictUnifier.get(wireGtDouble, Kanthal,8),  OreDictUnifier.get(foil, Aluminium,8)},new FluidStack[]{Copper.getFluid(144)});
        // 镍铬合金线圈方块 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,  new ItemStack[]{OreDictUnifier.get(wireGtDouble, Nichrome,8),  OreDictUnifier.get(foil, StainlessSteel,8)},new FluidStack[]{Aluminium.getFluid(144)});
        // 钌钨钼合金线圈方块 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,  new ItemStack[]{OreDictUnifier.get(wireGtDouble, RTMAlloy,8),  OreDictUnifier.get(foil, VanadiumSteel,8)},new FluidStack[]{Nichrome.getFluid(144)});
        // 高速钢-G线圈方块 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,  new ItemStack[]{OreDictUnifier.get(wireGtDouble, HSSG,8),  OreDictUnifier.get(foil, TungstenCarbide,8)},new FluidStack[]{Tungsten.getFluid(144)});
        // 硅岩线圈方块 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,  new ItemStack[]{OreDictUnifier.get(wireGtDouble, Naquadah,8),  OreDictUnifier.get(foil, Osmium,8)},new FluidStack[]{TungstenSteel.getFluid(144)});
        // 凯金线圈方块 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,  new ItemStack[]{OreDictUnifier.get(wireGtDouble, Trinium,8),  OreDictUnifier.get(foil, NaquadahEnriched,8)},new FluidStack[]{Naquadah.getFluid(144)});
        // 三钛合金线圈方块 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES,  new ItemStack[]{OreDictUnifier.get(wireGtDouble, Tritanium,8),  OreDictUnifier.get(foil, Naquadria,8)},new FluidStack[]{Trinium.getFluid(144)});

        // 酚醛树脂电路基板 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(dust, Wood), IntCircuitIngredient.getIntegratedCircuit(1)}, new FluidStack[]{Glue.getFluid(50)});
        // 塑料电路基板 * 1
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate,Polyethylene), OreDictUnifier.get(foil,Copper,4)},new FluidStack[]{SulfuricAcid.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate,PolyvinylChloride), OreDictUnifier.get(foil,Copper,4)},new FluidStack[]{SulfuricAcid.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate,Polytetrafluoroethylene), OreDictUnifier.get(foil,Copper,4)},new FluidStack[]{SulfuricAcid.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate,Polybenzimidazole), OreDictUnifier.get(foil,Copper,4)},new FluidStack[]{SulfuricAcid.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate,Polyethylene), OreDictUnifier.get(foil,Copper,4)},new FluidStack[]{SulfuricAcid.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate,PolyvinylChloride), OreDictUnifier.get(foil,Copper,4)},new FluidStack[]{SulfuricAcid.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate,Polytetrafluoroethylene), OreDictUnifier.get(foil,Copper,4)},new FluidStack[]{SulfuricAcid.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate,Polybenzimidazole), OreDictUnifier.get(foil,Copper,4)},new FluidStack[]{SulfuricAcid.getFluid(250)});
        // 环氧树脂电路板 * 1
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate,Epoxy), OreDictUnifier.get(foil,Gold,8)},new FluidStack[]{SulfuricAcid.getFluid(500)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate,Epoxy), OreDictUnifier.get(foil,Gold,8)},new FluidStack[]{SulfuricAcid.getFluid(500)});
        // 纤维强化电路基板 * 1
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate,ReinforcedEpoxyResin), OreDictUnifier.get(foil,AnnealedCopper,8)},new FluidStack[]{SulfuricAcid.getFluid(500)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(plate,ReinforcedEpoxyResin), OreDictUnifier.get(foil,AnnealedCopper,8)},new FluidStack[]{SulfuricAcid.getFluid(500)});
        // 多层纤维强化电路基板 * 1
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{MetaItems.FIBER_BOARD.getStackForm(2), OreDictUnifier.get(foil,Palladium,8)},new FluidStack[]{SulfuricAcid.getFluid(500)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{MetaItems.FIBER_BOARD.getStackForm(2), OreDictUnifier.get(foil,Palladium,8)},new FluidStack[]{SulfuricAcid.getFluid(500)});

        // 聚变线圈方块 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL), MetaItems.FIELD_GENERATOR_IV.getStackForm(2), MetaItems.ELECTRIC_PUMP_IV.getStackForm(), MetaItems.NEUTRON_REFLECTOR.getStackForm(2), OreDictUnifier.get(circuit,LuV,4),OreDictUnifier.get(pipeSmallFluid,Naquadah,4), OreDictUnifier.get(plate,Europium,4)},new FluidStack[]{VanadiumGallium.getFluid(576)});
        // 超导线圈方块 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtDouble,IndiumTinBariumTitaniumCuprate,32),OreDictUnifier.get(foil,NiobiumTitanium,32)},new FluidStack[]{Trinium.getFluid(3456)});
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtDouble,UraniumRhodiumDinaquadide,16),OreDictUnifier.get(foil,NiobiumTitanium,16)},new FluidStack[]{Trinium.getFluid(2304)});
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtDouble,EnrichedNaquadahTriniumEuropiumDuranide,8),OreDictUnifier.get(foil,NiobiumTitanium,8)},new FluidStack[]{Trinium.getFluid(1152)});
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtDouble,RutheniumTriniumAmericiumNeutronate,4),OreDictUnifier.get(foil,NiobiumTitanium,4)},new FluidStack[]{Trinium.getFluid(576)});


        // 核聚变反应堆控制电脑MK-I * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLY_LINE_RECIPES, new ItemStack[]{MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL),OreDictUnifier.get(circuit,ZPM,4),OreDictUnifier.get(plateDouble,Plutonium241),OreDictUnifier.get(plateDouble,Osmiridium), MetaItems.FIELD_GENERATOR_IV.getStackForm(2), MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64),OreDictUnifier.get(wireGtSingle,IndiumTinBariumTitaniumCuprate,32)},new FluidStack[]{SolderingAlloy.getFluid(1152),NiobiumTitanium.getFluid(1152)});
        // 核聚变反应堆控制电脑MK-II * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLY_LINE_RECIPES, new ItemStack[]{MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.FUSION_COIL),OreDictUnifier.get(circuit,UV,4),OreDictUnifier.get(plateDouble,Naquadria),OreDictUnifier.get(plateDouble,Europium), MetaItems.FIELD_GENERATOR_LuV.getStackForm(2), MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64),MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(32),OreDictUnifier.get(wireGtSingle,UraniumRhodiumDinaquadide,32)},new FluidStack[]{SolderingAlloy.getFluid(1152),VanadiumGallium.getFluid(1152)});
        // 核聚变反应堆控制电脑MK-III * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLY_LINE_RECIPES, new ItemStack[]{MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.FUSION_COIL),OreDictUnifier.get(circuit,UHV,4),MetaItems.QUANTUM_STAR.getStackForm(),OreDictUnifier.get(plateDouble,Americium), MetaItems.FIELD_GENERATOR_ZPM.getStackForm(2), MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64),MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64),OreDictUnifier.get(wireGtSingle,EnrichedNaquadahTriniumEuropiumDuranide,32)},new FluidStack[]{SolderingAlloy.getFluid(1152),YttriumBariumCuprate.getFluid(1152)});


    }

}
