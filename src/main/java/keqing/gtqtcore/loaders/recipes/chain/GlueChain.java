package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CHEMICAL_PLANT;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class GlueChain {
    public static void init() {
        //高级胶水
        SuperGlue();
        UltraGlue();
    }
    private static void UltraGlue()
    {
        CHEMICAL_PLANT.recipeBuilder()
                .input(dust,Quicklime,6)
                .input(dust,SodiumCyanide,3)
                .fluidInputs(ChloroaceticAcid.getFluid(144))
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .output(dust,CyanoaceticAcid,9)
                .output(dust,Salt,6)
                .duration(200)
                .recipeLevel(4)
                .EUt(1920)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Ethanol.getFluid(1000))
                .fluidOutputs(Ethylcyanoacetate.getFluid(1000))
                .input(dust,CyanoaceticAcid,9)
                .duration(200)
                .EUt(1920)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Ethylcyanoacetate.getFluid(100))
                .fluidInputs(Formaldehyde.getFluid(100))
                .fluidOutputs(Cyanoacrylate.getFluid(100))
                .fluidOutputs(Water.getFluid(1000))
                .duration(200)
                .EUt(1920)
                .buildAndRegister();

        FLUID_HEATER_RECIPES.recipeBuilder()
                .fluidInputs(Cyanoacrylate.getFluid(1000))
                .fluidOutputs(UltraGlue.getFluid(1000))
                .duration(2000)
                .EUt(1920)
                .buildAndRegister();
    }
    private static void SuperGlue()
    {
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(VinylAcetate.getFluid(2000))
                .fluidInputs(MethylAcetate.getFluid(3000))
                .fluidOutputs(SuperGlue.getFluid(5000))
                .duration(200)
                .EUt(1920)
                .buildAndRegister();
    }

}
