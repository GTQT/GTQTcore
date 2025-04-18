package keqing.gtqtcore.loaders.recipes.handlers;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.NaquadahAlloy;
import static gregtech.api.unification.material.Materials.Polybenzimidazole;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.unification.GTQTMaterials.KaptonK;
import static keqing.gtqtcore.api.unification.MaterialHelper.Plate;
import static keqing.gtqtcore.api.unification.MaterialHelper.Superconductor;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Polyetheretherketone;

import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.*;

import gregtech.api.GTValues;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.unification.material.MarkerMaterial;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.metatileentities.MetaTileEntities;

public class CoverRecipes {

    public static void init() {
        LaserRecipes();
        wireless(MICROWAVE_ENERGY_RECEIVER[1],MICROWAVE_ENERGY_RECEIVER_LV,1);
        wireless(MICROWAVE_ENERGY_RECEIVER[2],MICROWAVE_ENERGY_RECEIVER_MV,2);
        wireless(MICROWAVE_ENERGY_RECEIVER[3],MICROWAVE_ENERGY_RECEIVER_HV,3);
        wireless(MICROWAVE_ENERGY_RECEIVER[4],MICROWAVE_ENERGY_RECEIVER_EV,4);
        wireless(MICROWAVE_ENERGY_RECEIVER[5],MICROWAVE_ENERGY_RECEIVER_IV,5);
        wireless(MICROWAVE_ENERGY_RECEIVER[6],MICROWAVE_ENERGY_RECEIVER_LuV,6);
        wireless(MICROWAVE_ENERGY_RECEIVER[7],MICROWAVE_ENERGY_RECEIVER_ZPM,7);
        wireless(MICROWAVE_ENERGY_RECEIVER[8],MICROWAVE_ENERGY_RECEIVER_UV,8);
        wireless(MICROWAVE_ENERGY_RECEIVER[9],MICROWAVE_ENERGY_RECEIVER_UHV,9);
        wireless(MICROWAVE_ENERGY_RECEIVER[10],MICROWAVE_ENERGY_RECEIVER_UEV,10);
        wireless(MICROWAVE_ENERGY_RECEIVER[11],MICROWAVE_ENERGY_RECEIVER_UIV,11);
        wireless(MICROWAVE_ENERGY_RECEIVER[12],MICROWAVE_ENERGY_RECEIVER_UXV,12);
        wireless(MICROWAVE_ENERGY_RECEIVER[13],MICROWAVE_ENERGY_RECEIVER_OpV,13);
    }

    private static void LaserRecipes() {
        for (int i = 1; i < 9; i++) {

            if(i<5)
            {
                ASSEMBLY_LINE_RECIPES.recipeBuilder()
                        .input(HULL[i],4)
                        .input(KQCC_COMPUTATION_HATCH_TRANSMITTER[i])
                        .input(MetaTileEntities.ENERGY_INPUT_HATCH[i])
                        .input(OrePrefix.frameGt,Plate[i],16)
                        .input(OrePrefix.circuit, MarkerMaterial.create(GTValues.VN[i].toLowerCase()),16)
                        .input(OrePrefix.wireGtSingle,Superconductor[i],64)
                        .fluidInputs(KaptonK.getFluid(L * 32))
                        .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                        .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                        .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                        .stationResearch(research -> research
                                .researchStack(DISK_28.getStackForm())
                                .CWUt(CWT[LuV])
                                .EUt(VA[LuV]))
                        .output(LASER_INPUT[i])
                        .EUt(VA[LuV]).duration(1000*i)
                        .buildAndRegister();

                ASSEMBLY_LINE_RECIPES.recipeBuilder()
                        .input(HULL[i],4)
                        .input(KQCC_COMPUTATION_HATCH_RECEIVER[i])
                        .input(MetaTileEntities.ENERGY_OUTPUT_HATCH[i])
                        .input(OrePrefix.frameGt,Plate[i],16)
                        .input(OrePrefix.circuit, MarkerMaterial.create(GTValues.VN[i].toLowerCase()),16)
                        .input(OrePrefix.wireGtSingle,Superconductor[i],64)
                        .fluidInputs(KaptonK.getFluid(L * 32))
                        .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                        .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                        .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                        .stationResearch(research -> research
                                .researchStack(DISK_28.getStackForm())
                                .CWUt(CWT[LuV])
                                .EUt(VA[LuV]))
                        .output(LASER_OUTPUT[i])
                        .EUt(VA[LuV]).duration(1000*i)
                        .buildAndRegister();
            }
            else {
                ASSEMBLY_LINE_RECIPES.recipeBuilder()
                        .input(HULL[i],4)
                        .input(KQCC_COMPUTATION_HATCH_TRANSMITTER[i])
                        .input(MetaTileEntities.LASER_INPUT_HATCH_256[i-5])
                        .input(OrePrefix.frameGt,Plate[i],16)
                        .input(OrePrefix.circuit, MarkerMaterial.create(GTValues.VN[i].toLowerCase()), 16)
                        .input(OrePrefix.wireGtSingle, Superconductor[i], 64)
                        .fluidInputs(KaptonK.getFluid(L * 32))
                        .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                        .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                        .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                        .stationResearch(research -> research
                                .researchStack(DISK_28.getStackForm())
                                .CWUt(CWT[LuV])
                                .EUt(VA[LuV]))
                        .output(LASER_INPUT[i])
                        .EUt(VA[UV]).duration(1000*i)
                        .buildAndRegister();

                ASSEMBLY_LINE_RECIPES.recipeBuilder()
                        .input(HULL[i],4)
                        .input(KQCC_COMPUTATION_HATCH_RECEIVER[i])
                        .input(MetaTileEntities.LASER_OUTPUT_HATCH_256[i-5])
                        .input(OrePrefix.frameGt,Plate[i],16)
                        .input(OrePrefix.circuit, MarkerMaterial.create(GTValues.VN[i].toLowerCase()), 16)
                        .input(OrePrefix.wireGtSingle, Superconductor[i], 64)
                        .fluidInputs(KaptonK.getFluid(L * 32))
                        .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                        .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                        .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                        .stationResearch(research -> research
                                .researchStack(DISK_28.getStackForm())
                                .CWUt(CWT[LuV])
                                .EUt(VA[LuV]))
                        .output(LASER_OUTPUT[i])
                        .EUt(VA[UV]).duration(1000*i)
                        .buildAndRegister();
            }

        }
    }
    private static void wireless(MetaTileEntity mte, MetaItem.MetaValueItem item,int tier) {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(mte)
                .output(item)
                .EUt(VA[tier]).duration(200)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .output(mte)
                .input(item)
                .EUt(VA[tier]).duration(200)
                .buildAndRegister();
    }
}