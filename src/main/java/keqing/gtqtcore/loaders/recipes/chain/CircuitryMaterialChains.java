package keqing.gtqtcore.loaders.recipes.chain;

import keqing.gtqtcore.api.unification.GTQTMaterials;

import static gregtech.common.items.MetaItems.SHAPE_MOLD_CYLINDER;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.ROASTER_RECIPES;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

public class CircuitryMaterialChains {

    public static void init() {
        SiliconNanocrystals();
    }

    private static void SiliconNanocrystals() {

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(200)
                .EUt(VA[IV])
                .input(dust,Silicon)
                .fluidInputs(GTQTMaterials.SiliconTetrachloride.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(GTQTMaterials.Trichlorosilane.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(200)
                .EUt(VA[IV])
                .notConsumable(plate,Palladium)
                .fluidInputs(GTQTMaterials.Trichlorosilane.getFluid(2000))
                .fluidOutputs(GTQTMaterials.SiliconTetrachloride.getFluid(1000))
                .fluidOutputs(GTQTMaterials.DichIorosilane.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(200)
                .EUt(VA[IV])
                .notConsumable(plate,Platinum)
                .fluidInputs(GTQTMaterials.DichIorosilane.getFluid(3000))
                .fluidOutputs(GTQTMaterials.Trichlorosilane.getFluid(2000))
                .fluidOutputs(GTQTMaterials.Silane.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(140)
                .EUt(VA[IV])
                .fluidInputs(Argon.getFluid(1000))
                .fluidInputs(GTQTMaterials.Silane.getFluid(1000))
                .fluidOutputs(GTQTMaterials.ArgonSilane.getFluid(1000))
                .buildAndRegister();

        //  Alumino Silicate Glass Tube
        // 1 AL2O3  2 SIO2   2 ALSIO4
        ROASTER_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_MOLD_CYLINDER)
                .input(dust, GTQTMaterials.Alumina, 5)
                .input(dust, Quartzite,6)
                .output(dust, GTQTMaterials.AluminoSilicateGlass,12)
                .fluidOutputs(Oxygen.getFluid(1000))
                .EUt(VA[IV])
                .duration((int) (13.5 * 20))
                .temperature(1800)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .duration(530)
                .EUt(VA[LuV])
                .blastFurnaceTemp(6000)
                .fluidInputs(GTQTMaterials.ArgonSilane.getFluid(1000))
                .notConsumable(plate, GTQTMaterials.AluminoSilicateGlass)
                .fluidOutputs(GTQTMaterials.ArgonSilane.getPlasma(1000))
                .output(dust, GTQTMaterials.LuminescentSiliconNanocrystals)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .duration(220)
                .EUt(VA[LuV])
                .fluidInputs(GTQTMaterials.ArgonSilane.getPlasma(1000))
                .chancedOutput(dust, GTQTMaterials.LuminescentSiliconNanocrystals, 1200, 200)
                .chancedOutput(dust, GTQTMaterials.LuminescentSiliconNanocrystals, 1200, 200)
                .chancedOutput(dust, GTQTMaterials.LuminescentSiliconNanocrystals, 1200, 200)
                .chancedOutput(dust, GTQTMaterials.LuminescentSiliconNanocrystals, 1200, 200)
                .chancedOutput(dust, GTQTMaterials.LuminescentSiliconNanocrystals, 1200, 200)
                .chancedOutput(dust, GTQTMaterials.LuminescentSiliconNanocrystals, 1200, 200)
                .fluidOutputs(GTQTMaterials.Silane.getFluid(400))
                .fluidOutputs(Argon.getFluid(400))
                .buildAndRegister();

        //发光纳米硅
        AUTOCLAVE_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.LuminescentSiliconNanocrystals)
                .fluidInputs(Krypton.getFluid(1000))
                .fluidOutputs(GTQTMaterials.SuspendedPGQD.getFluid(1000))
                .EUt(VA[IV])
                .duration(800)
                .buildAndRegister();
    }
}
