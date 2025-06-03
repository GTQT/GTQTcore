package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.OreDictUnifier;

import static gregtech.api.GTValues.CWT;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.pipeTinyFluid;
import static gregtech.api.unification.ore.OrePrefix.wireGtSingle;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.DISK_5;

public class SuperconductorRecipes {

    public static void init() {

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[2])
                .scannerResearch(b -> b
                        .researchStack(DISK_5.getStackForm()))
                .input(wireGtSingle, ManganesePhosphide, 8)
                .input(pipeTinyFluid, Steel, 4)
                .inputs(ELECTRIC_PUMP_LV.getStackForm(2))
                .fluidInputs(Nitrogen.getFluid(FluidStorageKeys.LIQUID, 1000))
                .output(wireGtSingle, LVSuperconductor, 8)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[3])
                .scannerResearch(b -> b
                        .researchStack(DISK_5.getStackForm()))
                .input(wireGtSingle, MagnesiumDiboride, 8)
                .input(pipeTinyFluid, StainlessSteel, 4)
                .inputs(ELECTRIC_PUMP_MV.getStackForm(2))
                .fluidInputs(Nitrogen.getFluid(FluidStorageKeys.LIQUID, 1000))
                .output(wireGtSingle, MVSuperconductor, 8)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[4])
                .scannerResearch(b -> b
                        .researchStack(DISK_5.getStackForm()))
                .input(wireGtSingle, MercuryBariumCalciumCuprate, 8)
                .input(pipeTinyFluid, Titanium, 4)
                .inputs(ELECTRIC_PUMP_HV.getStackForm(2))
                .fluidInputs(Nitrogen.getFluid(FluidStorageKeys.LIQUID, 2000))
                .output(wireGtSingle, HVSuperconductor, 8)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[5])
                .scannerResearch(b -> b
                        .researchStack(DISK_5.getStackForm()))
                .input(wireGtSingle, UraniumTriplatinum, 8)
                .input(pipeTinyFluid, TungstenSteel, 4)
                .inputs(ELECTRIC_PUMP_EV.getStackForm(2))
                .fluidInputs(Nitrogen.getFluid(FluidStorageKeys.LIQUID, 2000))
                .output(wireGtSingle, EVSuperconductor, 8)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[6])
                .scannerResearch(b -> b
                        .researchStack(DISK_5.getStackForm()))
                .input(wireGtSingle, SamariumIronArsenicOxide, 8)
                .input(pipeTinyFluid, NiobiumTitanium, 4)
                .inputs(ELECTRIC_PUMP_IV.getStackForm(2))
                .fluidInputs(Nitrogen.getFluid(FluidStorageKeys.LIQUID, 4000))
                .output(wireGtSingle, IVSuperconductor, 8)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[7])
                .scannerResearch(b -> b
                        .researchStack(DISK_5.getStackForm()))
                .input(wireGtSingle, IndiumTinBariumTitaniumCuprate, 8)
                .input(pipeTinyFluid, Polybenzimidazole, 4)
                .inputs(ELECTRIC_PUMP_LuV.getStackForm(2))
                .fluidInputs(Nitrogen.getFluid(FluidStorageKeys.LIQUID, 4000))
                .output(wireGtSingle, LuVSuperconductor, 8)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[8])
                .stationResearch(b -> b
                        .researchStack(OreDictUnifier.get(wireGtSingle, LuVSuperconductor))
                        .CWUt(CWT[7])
                        .EUt(VA[8]))
                .input(wireGtSingle, UraniumRhodiumDinaquadide, 8)
                .input(pipeTinyFluid, Naquadah, 4)
                .inputs(ELECTRIC_PUMP_ZPM.getStackForm(2))
                .fluidInputs(Helium.getFluid(FluidStorageKeys.LIQUID, 8000))
                .output(wireGtSingle, ZPMSuperconductor, 8)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[9])
                .stationResearch(b -> b
                        .researchStack(OreDictUnifier.get(wireGtSingle, ZPMSuperconductor))
                        .CWUt(CWT[8])
                        .EUt(VA[9]))
                .input(wireGtSingle, EnrichedNaquadahTriniumEuropiumDuranide, 8)
                .input(pipeTinyFluid, Duranium, 4)
                .inputs(ELECTRIC_PUMP_UV.getStackForm(2))
                .fluidInputs(Helium.getFluid(FluidStorageKeys.LIQUID, 8000))
                .output(wireGtSingle, UVSuperconductor, 8)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[10])
                .stationResearch(b -> b
                        .researchStack(OreDictUnifier.get(wireGtSingle, UVSuperconductor))
                        .CWUt(CWT[9])
                        .EUt(VA[10]))
                .input(wireGtSingle, RutheniumTriniumAmericiumNeutronate, 8)
                .input(pipeTinyFluid, Neutronium, 4)
                .inputs(ELECTRIC_PUMP_UHV.getStackForm(2))
                .fluidInputs(Helium.getFluid(FluidStorageKeys.LIQUID, 12000))
                .output(wireGtSingle, UHVSuperconductor, 8)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[11])
                .stationResearch(b -> b
                        .researchStack(OreDictUnifier.get(wireGtSingle, UHVSuperconductor))
                        .CWUt(CWT[9])
                        .EUt(VA[10]))
                .input(wireGtSingle, QuantumAlloy, 8)
                .input(pipeTinyFluid, Adamantium, 4)
                .inputs(ELECTRIC_PUMP_UEV.getStackForm(2))
                .fluidInputs(Helium.getFluid(FluidStorageKeys.LIQUID, 1600))
                .output(wireGtSingle, UEVSuperconductor, 8)
                .buildAndRegister();
    }
}
