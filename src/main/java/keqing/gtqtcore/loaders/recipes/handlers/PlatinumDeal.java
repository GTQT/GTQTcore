package keqing.gtqtcore.loaders.recipes.handlers;

import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.GTValues.HV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class PlatinumDeal {
    public static void init() {
        //pd
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(ChloroplatinicAcid.getFluid(1000))
                .input(dust,Calcium,1)
                .output(dust, CalciumChloride, 3)
                .fluidOutputs(Hydrogen.getFluid(6000))
                .fluidOutputs(Oxygen.getFluid(2000))
                .output(dust,PlatinumRaw)
                .duration(20)
                .EUt(VA[HV])
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,PlatinumRaw,2)
                .input(dust,Calcium,1)
                .output(dust, CalciumChloride, 3)
                .output(dust,Platinum)
                .duration(20)
                .EUt(VA[HV])
                .buildAndRegister();

        //rh
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Zinc,1)
                .fluidInputs(RhodiumSulfate.getFluid(1000))
                .output(dust,ZincSulfate, 6)
                .output(dust,RhodiumRaw,2)
                .duration(20)
                .EUt(VA[HV])
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust,RhodiumRaw,2)
                .fluidOutputs(Chlorine.getFluid(1000))
                .input(dust,Salt, 1)
                .output(dust,RhodiumSalt,3)
                .duration(20)
                .blastFurnaceTemp(3600)
                .EUt(VA[HV])
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,SodiumNitrate,1)
                .fluidInputs(RhodiumSalt.getFluid(1000))
                .output(dust,RhodiumNitrateSolution, 1)
                .output(dust,Salt,3)
                .duration(20)
                .EUt(VA[HV])
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust,RhodiumNitrateSolution,1)
                .fluidOutputs(Water.getFluid(1000))
                .output(dust,RhodiumDo,1)
                .duration(20)
                .EUt(VA[HV])
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,RhodiumDo,1)
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .fluidOutputs(Ammonia.getFluid(1000))
                .fluidOutputs(Chlorine.getFluid(1000))
                .output(dust,Rhodium,3)
                .duration(20)
                .EUt(VA[HV])
                .buildAndRegister();

        //ru
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,RutheniumTetroxide,10)
                .fluidInputs(MethylAcetate.getFluid(1000))
                .fluidInputs(Chlorine.getFluid(10000))
                .fluidOutputs(RutheniumfOxide.getFluid(10000))
                .duration(20)
                .EUt(VA[HV])
                .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .output(dust,RutheniumOxide,1)
                .fluidOutputs(MethylAcetate.getFluid(100))
                .fluidInputs(RutheniumfOxide.getFluid(1000))
                .duration(20)
                .EUt(VA[HV])
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,RutheniumOxide,2)
                .output(dust,RutheniumgOxide,2)
                .input(dust,Calcium,1)
                .output(dust, CalciumChloride, 3)
                .duration(20)
                .EUt(VA[HV])
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust,RutheniumgOxide,1)
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(Water.getFluid(1000))
                .blastFurnaceTemp(3600)
                .output(dust,Ruthenium,1)
                .duration(20)
                .EUt(VA[HV])
                .buildAndRegister();

        //os
        BLAST_RECIPES.recipeBuilder()
                .input(dust,RarestMetalMixture,12)
                .input(dust,SodiumChlorate,12)
                .fluidOutputs(Oxygen.getFluid(1000))
                .blastFurnaceTemp(3600)
                .output(dust,IridiumMetalResidue,5)
                .output(dust,OsmiumTetroxide,5)
                .output(dust,Salt,4)
                .duration(20)
                .EUt(VA[HV])
                .buildAndRegister();

        //ir  IrCl3
        BLAST_RECIPES.recipeBuilder()
                .input(dust,IridiumChloride,4)
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .output(dust,IridiumFluid,3)
                .duration(20)
                .EUt(VA[HV])
                .buildAndRegister();

        //Ca   +2Cl    =CaCl2
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,IridiumFluid,3)
                .input(dust,Calcium,1)
                .output(dust,Iridium,1)
                .output(dust, CalciumChloride, 3)
                .duration(20)
                .EUt(VA[HV])
                .buildAndRegister();
    }
}
