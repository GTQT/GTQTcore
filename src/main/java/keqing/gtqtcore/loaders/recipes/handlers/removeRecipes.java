package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterial;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.common.blocks.BlockComputerCasing;
import gregtech.common.blocks.BlockFusionCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;


import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.MarkerMaterials.Color.*;
import static gregtech.api.unification.material.MarkerMaterials.Tier.*;
import static gregtech.api.unification.material.Materials.*;

import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.blocks.MetaBlocks.OPTICAL_PIPES;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.items.MetaItems.NEUTRONIUM_WAFER;
import static gregtech.common.metatileentities.MetaTileEntities.POWER_TRANSFORMER;

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
        // 硫酸 * 1000
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(dust,Sulfur,4),IntCircuitIngredient.getIntegratedCircuit(24)},new FluidStack[]{Water.getFluid(4000)});


        // HPIC晶圆 * 1
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{MetaItems.POWER_INTEGRATED_CIRCUIT_WAFER.getStackForm(1), OreDictUnifier.get(dust,IndiumGalliumPhosphide,2)},new FluidStack[]{VanadiumGallium.getFluid(288)});
        // UHPIC晶圆 * 1
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{MetaItems.HIGH_POWER_INTEGRATED_CIRCUIT_WAFER.getStackForm(1), OreDictUnifier.get(dust,IndiumGalliumPhosphide,8)},new FluidStack[]{Naquadah.getFluid(576)});
        // HPIC晶圆 * 1
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{MetaItems.POWER_INTEGRATED_CIRCUIT_WAFER.getStackForm(1), OreDictUnifier.get(dust,IndiumGalliumPhosphide,2)},new FluidStack[]{VanadiumGallium.getFluid(288)});
        // UHPIC晶圆 * 1
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{MetaItems.HIGH_POWER_INTEGRATED_CIRCUIT_WAFER.getStackForm(1), OreDictUnifier.get(dust,IndiumGalliumPhosphide,8)},new FluidStack[]{Naquadah.getFluid(576)});

        // 聚变线圈方块 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL), MetaItems.FIELD_GENERATOR_IV.getStackForm(2), MetaItems.ELECTRIC_PUMP_IV.getStackForm(), MetaItems.NEUTRON_REFLECTOR.getStackForm(2), OreDictUnifier.get(circuit,LuV,4),OreDictUnifier.get(pipeSmallFluid,Naquadah,4), OreDictUnifier.get(plate,Europium,4)},new FluidStack[]{VanadiumGallium.getFluid(576)});
        // 超导线圈方块 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtDouble,IndiumTinBariumTitaniumCuprate,32),OreDictUnifier.get(foil,NiobiumTitanium,32)},new FluidStack[]{Trinium.getFluid(3456)});
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtDouble,UraniumRhodiumDinaquadide,16),OreDictUnifier.get(foil,NiobiumTitanium,16)},new FluidStack[]{Trinium.getFluid(2304)});
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtDouble,EnrichedNaquadahTriniumEuropiumDuranide,8),OreDictUnifier.get(foil,NiobiumTitanium,8)},new FluidStack[]{Trinium.getFluid(1152)});
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{OreDictUnifier.get(wireGtDouble,RutheniumTriniumAmericiumNeutronate,4),OreDictUnifier.get(foil,NiobiumTitanium,4)},new FluidStack[]{Trinium.getFluid(576)});


        // 铝粒 * 3
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(dust,Ruby,1));
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(gem,Ruby,1));
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(dust,GreenSapphire,1));
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(gem,GreenSapphire,1));
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(dust,Sapphire,1));
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(gem,Sapphire,1));
        // 铝锭 * 1
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(dust,Aluminium,1),IntCircuitIngredient.getIntegratedCircuit(1));
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, new ItemStack[]{OreDictUnifier.get(dust,Aluminium,1),IntCircuitIngredient.getIntegratedCircuit(2)},new FluidStack[]{Nitrogen.getFluid(1000)});

        // 核聚变反应堆控制电脑MK-I * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLY_LINE_RECIPES, new ItemStack[]{MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL),OreDictUnifier.get(circuit,ZPM,4),OreDictUnifier.get(plateDouble,Plutonium241),OreDictUnifier.get(plateDouble,Osmiridium), MetaItems.FIELD_GENERATOR_IV.getStackForm(2), MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64),OreDictUnifier.get(wireGtSingle,IndiumTinBariumTitaniumCuprate,32)},new FluidStack[]{SolderingAlloy.getFluid(1152),NiobiumTitanium.getFluid(1152)});
        // 核聚变反应堆控制电脑MK-II * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLY_LINE_RECIPES, new ItemStack[]{MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.FUSION_COIL),OreDictUnifier.get(circuit,UV,4),OreDictUnifier.get(plateDouble,Naquadria),OreDictUnifier.get(plateDouble,Europium), MetaItems.FIELD_GENERATOR_LuV.getStackForm(2), MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64),MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(32),OreDictUnifier.get(wireGtSingle,UraniumRhodiumDinaquadide,32)},new FluidStack[]{SolderingAlloy.getFluid(1152),VanadiumGallium.getFluid(1152)});
        // 核聚变反应堆控制电脑MK-III * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLY_LINE_RECIPES, new ItemStack[]{MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.FUSION_COIL),OreDictUnifier.get(circuit,UHV,4),MetaItems.QUANTUM_STAR.getStackForm(),OreDictUnifier.get(plateDouble,Americium), MetaItems.FIELD_GENERATOR_ZPM.getStackForm(2), MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64),MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(64),OreDictUnifier.get(wireGtSingle,EnrichedNaquadahTriniumEuropiumDuranide,32)},new FluidStack[]{SolderingAlloy.getFluid(1152),YttriumBariumCuprate.getFluid(1152)});


        // 数据库 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLY_LINE_RECIPES, new ItemStack[]{MetaBlocks.COMPUTER_CASING.getItemVariant(BlockComputerCasing.CasingType.COMPUTER_CASING),OreDictUnifier.get(circuit,LuV,8),MetaItems.TOOL_DATA_ORB.getStackForm(),OreDictUnifier.get(wireFine,Cobalt,64),OreDictUnifier.get(wireFine,Copper,64),new ItemStack( OPTICAL_PIPES[0],4),OreDictUnifier.get(wireGtDouble,IndiumTinBariumTitaniumCuprate,16)},new FluidStack[]{SolderingAlloy.getFluid(288),Lubricant.getFluid(500)});

        // 纳米CPU晶圆 * 1
        // 量子位CPU晶圆 * 1
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{MetaItems.CENTRAL_PROCESSING_UNIT_WAFER.getStackForm(1),MetaItems.CARBON_FIBERS.getStackForm(16)},new FluidStack[]{Glowstone.getFluid(576)});
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{MetaItems.NANO_CENTRAL_PROCESSING_UNIT_WAFER.getStackForm(1),MetaItems.QUANTUM_EYE.getStackForm(2)},new FluidStack[]{GalliumArsenide.getFluid(288)});
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, new ItemStack[]{MetaItems.NANO_CENTRAL_PROCESSING_UNIT_WAFER.getStackForm(1),OreDictUnifier.get(dust,IndiumGalliumPhosphide,1)},new FluidStack[]{Radon.getFluid(50)});

        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{MetaItems.CENTRAL_PROCESSING_UNIT_WAFER.getStackForm(1),MetaItems.CARBON_FIBERS.getStackForm(16)},new FluidStack[]{Glowstone.getFluid(576)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{MetaItems.NANO_CENTRAL_PROCESSING_UNIT_WAFER.getStackForm(1),MetaItems.QUANTUM_EYE.getStackForm(2)},new FluidStack[]{GalliumArsenide.getFluid(288)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{MetaItems.NANO_CENTRAL_PROCESSING_UNIT_WAFER.getStackForm(1),OreDictUnifier.get(dust,IndiumGalliumPhosphide,1)},new FluidStack[]{Radon.getFluid(50)});

        // 不锈钢粉 * 9
        GTRecipeHandler.removeRecipesByInputs(MIXER_RECIPES, OreDictUnifier.get(dust,Iron,4), OreDictUnifier.get(dust,Invar,3), OreDictUnifier.get(dust,Manganese,1),OreDictUnifier.get(dust,Chrome,1),IntCircuitIngredient.getIntegratedCircuit(1));
        GTRecipeHandler.removeRecipesByInputs(MIXER_RECIPES, OreDictUnifier.get(dust,Iron,6), OreDictUnifier.get(dust,Nickel,1), OreDictUnifier.get(dust,Manganese,1),OreDictUnifier.get(dust,Chrome,1),IntCircuitIngredient.getIntegratedCircuit(3));

        //晶体芯片原料
        GTRecipeHandler.removeRecipesByInputs(AUTOCLAVE_RECIPES, new ItemStack[]{OreDictUnifier.get(gemExquisite,Olivine,1)},new FluidStack[]{Europium.getFluid(16)});
        GTRecipeHandler.removeRecipesByInputs(AUTOCLAVE_RECIPES, new ItemStack[]{OreDictUnifier.get(gemExquisite,Emerald,1)},new FluidStack[]{Europium.getFluid(16)});

        // 晶体芯片部件原料 * 9
        GTRecipeHandler.removeRecipesByInputs(FORGE_HAMMER_RECIPES, MetaItems.RAW_CRYSTAL_CHIP.getStackForm());

        // 晶体芯片原料 * 1
        GTRecipeHandler.removeRecipesByInputs(AUTOCLAVE_RECIPES, new ItemStack[]{MetaItems.RAW_CRYSTAL_CHIP_PART.getStackForm()},new FluidStack[]{BacterialSludge.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(AUTOCLAVE_RECIPES, new ItemStack[]{MetaItems.RAW_CRYSTAL_CHIP_PART.getStackForm()},new FluidStack[]{Mutagen.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(AUTOCLAVE_RECIPES, new ItemStack[]{MetaItems.RAW_CRYSTAL_CHIP_PART.getStackForm()},new FluidStack[]{Europium.getFluid(16)});

        //
        // 刻蚀水晶芯片 * 1
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, new ItemStack[]{OreDictUnifier.get(plate,Olivine,1),MetaItems.RAW_CRYSTAL_CHIP.getStackForm()},new FluidStack[]{Helium.getFluid(1000)});
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, new ItemStack[]{OreDictUnifier.get(plate,Emerald,1),MetaItems.RAW_CRYSTAL_CHIP.getStackForm()},new FluidStack[]{Helium.getFluid(1000)});
        // 晶体CPU * 1
        GTRecipeHandler.removeRecipesByInputs(LASER_ENGRAVER_RECIPES, MetaItems.ENGRAVED_CRYSTAL_CHIP.getStackForm(),OreDictUnifier.get(craftingLens, MarkerMaterials.Color.Lime,1));


        MetaItem.MetaValueItem []wafer={SILICON_WAFER,PHOSPHORUS_WAFER,NAQUADAH_WAFER,NEUTRONIUM_WAFER};

        for(MetaItem.MetaValueItem item:wafer)
            for(MarkerMaterial color:VALUES)
        GTRecipeHandler.removeRecipesByInputs(LASER_ENGRAVER_RECIPES, item.getStackForm(),OreDictUnifier.get(craftingLens, color,1));


        // 有源变压器 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, new ItemStack[]{POWER_TRANSFORMER[6].getStackForm(),OreDictUnifier.get(circuit,LuV,2),OreDictUnifier.get(wireGtSingle,IndiumTinBariumTitaniumCuprate,8),ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT.getStackForm(2)},new FluidStack[]{PCBCoolant.getFluid(1000)});


    }

}
