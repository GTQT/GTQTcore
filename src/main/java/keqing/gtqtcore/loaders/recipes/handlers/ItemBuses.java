package keqing.gtqtcore.loaders.recipes.handlers;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.OpV;
import static gregtech.common.metatileentities.MetaTileEntities.QUANTUM_CHEST;
import static keqing.gtqtcore.api.utils.GTRecipeHelper.createIOPartConv;
import static keqing.gtqtcore.api.utils.GTRecipeHelper.createIOPartRecipe;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.EXPORT_ITEM_HATCH;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.IMPORT_ITEM_HATCH;

public class ItemBuses {
    public static void init() {
        createIOPartRecipe(UEV, IMPORT_ITEM_HATCH[0], EXPORT_ITEM_HATCH[0], QUANTUM_CHEST[2].getStackForm());
        createIOPartRecipe(UIV, IMPORT_ITEM_HATCH[1], EXPORT_ITEM_HATCH[1], QUANTUM_CHEST[3].getStackForm());
        createIOPartRecipe(UXV, IMPORT_ITEM_HATCH[2], EXPORT_ITEM_HATCH[2], QUANTUM_CHEST[4].getStackForm());
        createIOPartRecipe(OpV, IMPORT_ITEM_HATCH[3], EXPORT_ITEM_HATCH[3], QUANTUM_CHEST[5].getStackForm());

        //  Add I to O and O to I convension of UEV-OpV Item I/O Buses.
        createIOPartConv(UEV, IMPORT_ITEM_HATCH[0], EXPORT_ITEM_HATCH[0], false);
        createIOPartConv(UIV, IMPORT_ITEM_HATCH[1], EXPORT_ITEM_HATCH[1], false);
        createIOPartConv(UXV, IMPORT_ITEM_HATCH[2], EXPORT_ITEM_HATCH[2], false);
        createIOPartConv(OpV, IMPORT_ITEM_HATCH[3], EXPORT_ITEM_HATCH[3], false);
    }
}
