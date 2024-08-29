package keqing.gtqtcore.loaders.recipes.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.UHV;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static gregtech.common.metatileentities.MetaTileEntities.QUANTUM_TANK;
import static keqing.gtqtcore.api.utils.GTRecipeHelper.createIOPartConv;
import static keqing.gtqtcore.api.utils.GTRecipeHelper.createIOPartRecipe;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.EXPORT_FLUID_HATCH;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.IMPORT_FLUID_HATCH;

public class FluidHatches {
    public static void init() {
        createIOPartRecipe(UEV, IMPORT_FLUID_HATCH[0], EXPORT_FLUID_HATCH[0], QUANTUM_TANK[2].getStackForm());
        createIOPartRecipe(UIV, IMPORT_FLUID_HATCH[1], EXPORT_FLUID_HATCH[1], QUANTUM_TANK[3].getStackForm());
        createIOPartRecipe(UXV, IMPORT_FLUID_HATCH[2], EXPORT_FLUID_HATCH[2], QUANTUM_TANK[4].getStackForm());
        createIOPartRecipe(OpV, IMPORT_FLUID_HATCH[3], EXPORT_FLUID_HATCH[3], QUANTUM_TANK[5].getStackForm());

        //  Add I to O and O to I convension of UEV-OpV Fluid I/O Hatches.
        createIOPartConv(UEV, IMPORT_FLUID_HATCH[0], EXPORT_FLUID_HATCH[0], true);
        createIOPartConv(UIV, IMPORT_FLUID_HATCH[1], EXPORT_FLUID_HATCH[1], true);
        createIOPartConv(UXV, IMPORT_FLUID_HATCH[2], EXPORT_FLUID_HATCH[2], true);
        createIOPartConv(OpV, IMPORT_FLUID_HATCH[3], EXPORT_FLUID_HATCH[3], true);
    }
}
