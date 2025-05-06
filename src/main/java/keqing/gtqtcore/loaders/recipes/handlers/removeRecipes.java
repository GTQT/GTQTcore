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
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, Ethylene.getFluid(1000), Chlorine.getFluid(2000));
        // 钢矿车车轮 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, OreDictUnifier.get(stick, Steel, 1), OreDictUnifier.get(ring, Steel, 2));
        // 铁矿车车轮 * 1
        GTRecipeHandler.removeRecipesByInputs(ASSEMBLER_RECIPES, OreDictUnifier.get(stick, Iron, 1), OreDictUnifier.get(ring, Iron, 2));
        // 硫酸 * 1000
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{OreDictUnifier.get(dust, Sulfur, 4), IntCircuitIngredient.getIntegratedCircuit(24)}, new FluidStack[]{Water.getFluid(4000)});
        //晶体芯片原料
        GTRecipeHandler.removeRecipesByInputs(AUTOCLAVE_RECIPES, new ItemStack[]{OreDictUnifier.get(gemExquisite, Olivine, 1)}, new FluidStack[]{Europium.getFluid(16)});
        GTRecipeHandler.removeRecipesByInputs(AUTOCLAVE_RECIPES, new ItemStack[]{OreDictUnifier.get(gemExquisite, Emerald, 1)}, new FluidStack[]{Europium.getFluid(16)});

        // 晶体芯片部件原料 * 9
        GTRecipeHandler.removeRecipesByInputs(FORGE_HAMMER_RECIPES, MetaItems.RAW_CRYSTAL_CHIP.getStackForm());

        // 晶体芯片原料 * 1
        GTRecipeHandler.removeRecipesByInputs(AUTOCLAVE_RECIPES, new ItemStack[]{MetaItems.RAW_CRYSTAL_CHIP_PART.getStackForm()}, new FluidStack[]{BacterialSludge.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(AUTOCLAVE_RECIPES, new ItemStack[]{MetaItems.RAW_CRYSTAL_CHIP_PART.getStackForm()}, new FluidStack[]{Mutagen.getFluid(250)});
        GTRecipeHandler.removeRecipesByInputs(AUTOCLAVE_RECIPES, new ItemStack[]{MetaItems.RAW_CRYSTAL_CHIP_PART.getStackForm()}, new FluidStack[]{Europium.getFluid(16)});

        // 刻蚀水晶芯片 * 1
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, new ItemStack[]{OreDictUnifier.get(plate, Olivine, 1), MetaItems.RAW_CRYSTAL_CHIP.getStackForm()}, new FluidStack[]{Helium.getFluid(1000)});
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, new ItemStack[]{OreDictUnifier.get(plate, Emerald, 1), MetaItems.RAW_CRYSTAL_CHIP.getStackForm()}, new FluidStack[]{Helium.getFluid(1000)});
        // 晶体CPU * 1
        GTRecipeHandler.removeRecipesByInputs(LASER_ENGRAVER_RECIPES, MetaItems.ENGRAVED_CRYSTAL_CHIP.getStackForm(), OreDictUnifier.get(craftingLens, MarkerMaterials.Color.Lime, 1));



        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, new ItemStack[]{OreDictUnifier.get(dust, Aluminium, 1), IntCircuitIngredient.getIntegratedCircuit(1)});
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, new ItemStack[]{OreDictUnifier.get(dust, Aluminium, 1), IntCircuitIngredient.getIntegratedCircuit(2)}, new FluidStack[]{Nitrogen.getFluid(1000)});
    }

}
