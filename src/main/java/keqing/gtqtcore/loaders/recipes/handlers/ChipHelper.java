package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.GTRecipeHandler;
import keqing.gtqtcore.api.metaileentity.multiblock.GCYLCleanroomType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.GTValues.UV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.CUTTER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.items.MetaItems.NEUTRONIUM_BOULE;
import static gregtech.common.items.MetaItems.NEUTRONIUM_WAFER;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class ChipHelper {
    public static void init() {
        soc();
        pic();
        wafer();
    }

    private static void wafer() {
        cutter(EUROPIUM_BOULE,EUROPIUM_WAFER,5,CleanroomType.CLEANROOM);
        cutter(AMERICIUM_BOULE,AMERICIUM_WAFER,6,CleanroomType.CLEANROOM);
        cutter(DUBNIUM_BOULE,DUBNIUM_WAFER,7,CleanroomType.CLEANROOM);
        //接中子素
        //  Delete Neutronium Boule -> Wafer recipes
        GTRecipeHandler.removeRecipesByInputs(CUTTER_RECIPES,
                new ItemStack[]{NEUTRONIUM_BOULE.getStackForm()},
                new FluidStack[]{Water.getFluid(1000)});

        GTRecipeHandler.removeRecipesByInputs(CUTTER_RECIPES,
                new ItemStack[]{NEUTRONIUM_BOULE.getStackForm()},
                new FluidStack[]{DistilledWater.getFluid(750)});

        GTRecipeHandler.removeRecipesByInputs(CUTTER_RECIPES,
                new ItemStack[]{NEUTRONIUM_BOULE.getStackForm()},
                new FluidStack[]{Lubricant.getFluid(250)});
        cutter(NEUTRONIUM_BOULE,NEUTRONIUM_WAFER,8,CleanroomType.STERILE_CLEANROOM);
    }

    private static void pic() {
        cutter(NANO_POWER_IC_WAFER,NANO_POWER_IC,9,CleanroomType.STERILE_CLEANROOM);
        cutter(PICO_POWER_IC_WAFER,PICO_POWER_IC,10,CleanroomType.STERILE_CLEANROOM);
        cutter(FEMTO_POWER_IC_WAFER,FEMTO_POWER_IC,11, GCYLCleanroomType.ISO3);
        cutter(ATTO_PIC_WAFER,ATTO_PIC_CHIP,12,GCYLCleanroomType.ISO2);
        cutter(ZEPTO_PIC_WAFER,ZEPTO_PIC_CHIP,13,GCYLCleanroomType.ISO1);
    }

    private static void soc() {
        cutter(UHASOC_WAFER,UHASOC_CHIP,9,CleanroomType.STERILE_CLEANROOM);
    }

    public static void cutter(MetaItem<?>.MetaValueItem wafer, MetaItem<?>.MetaValueItem chip, int tier,CleanroomType a)
    {
        CUTTER_RECIPES.recipeBuilder()
                .input(wafer)
                .output(chip, 2)
                .EUt(VA[tier])
                .duration(90*20)
                .cleanroom(a)
                .buildAndRegister();
    }
}
