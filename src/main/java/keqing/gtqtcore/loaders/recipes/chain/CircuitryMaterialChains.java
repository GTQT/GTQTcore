package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.common.items.MetaItems.SHAPE_MOLD_CYLINDER;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.ROASTER_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.unification.TJMaterials.*;

public class CircuitryMaterialChains {

    public static void init() {
        SiliconNanocrystals();
    }

    private static void SiliconNanocrystals() {

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(200)
                .EUt(VA[IV])
                .input(dust,Silicon)
                .fluidInputs(SiliconTetrachloride.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(Trichlorosilane.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(200)
                .EUt(VA[IV])
                .notConsumable(plate,Palladium)
                .fluidInputs(Trichlorosilane.getFluid(2000))
                .fluidOutputs(SiliconTetrachloride.getFluid(1000))
                .fluidOutputs(DichIorosilane.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(200)
                .EUt(VA[IV])
                .notConsumable(plate,Platinum)
                .fluidInputs(DichIorosilane.getFluid(3000))
                .fluidOutputs(Trichlorosilane.getFluid(2000))
                .fluidOutputs(Silane.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(140)
                .EUt(VA[IV])
                .fluidInputs(Argon.getFluid(1000))
                .fluidInputs(Silane.getFluid(1000))
                .fluidOutputs(ArgonSilane.getFluid(1000))
                .buildAndRegister();

        //  Alumino Silicate Glass Tube
        // 1 AL2O3  2 SIO2   2 ALSIO4
        ROASTER_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_MOLD_CYLINDER)
                .input(dust, Alumina, 5)
                .input(dust, Quartzite,6)
                .output(dust,AluminoSilicateGlass,12)
                .fluidOutputs(Oxygen.getFluid(1000))
                .EUt(VA[IV])
                .duration((int) (13.5 * 20))
                .temperature(1800)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .duration(530)
                .EUt(VA[LuV])
                .blastFurnaceTemp(6000)
                .fluidInputs(ArgonSilane.getFluid(1000))
                .notConsumable(plate, AluminoSilicateGlass)
                .fluidOutputs(ArgonSilane.getPlasma(1000))
                .output(dust, LuminescentSiliconNanocrystals)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .duration(220)
                .EUt(VA[LuV])
                .fluidInputs(ArgonSilane.getPlasma(1000))
                .chancedOutput(dust, LuminescentSiliconNanocrystals, 1200, 200)
                .chancedOutput(dust, LuminescentSiliconNanocrystals, 1200, 200)
                .chancedOutput(dust, LuminescentSiliconNanocrystals, 1200, 200)
                .chancedOutput(dust, LuminescentSiliconNanocrystals, 1200, 200)
                .chancedOutput(dust, LuminescentSiliconNanocrystals, 1200, 200)
                .chancedOutput(dust, LuminescentSiliconNanocrystals, 1200, 200)
                .fluidOutputs(Silane.getFluid(400))
                .fluidOutputs(Argon.getFluid(400))
                .buildAndRegister();

        //发光纳米硅
        AUTOCLAVE_RECIPES.recipeBuilder()
                .input(dust,LuminescentSiliconNanocrystals)
                .fluidInputs(Krypton.getFluid(1000))
                .fluidOutputs(SuspendedPGQD.getFluid(1000))
                .EUt(VA[IV])
                .duration(800)
                .buildAndRegister();
    }
}
