package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.GTValues;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import net.minecraft.item.ItemStack;

import static gregicality.multiblocks.api.unification.GCYMMaterials.WatertightSteel;
import static gregicality.multiblocks.api.unification.GCYMMaterials.Zeron100;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.recipes.RecipeMaps.CENTRIFUGE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Neutronium;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.Uranium234;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class SuperconductorRecipes {

    public static void init() {

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[2])
                .scannerResearch(b -> b
                        .researchStack(DISK_5.getStackForm()))
                .input(wireGtSingle, ManganesePhosphide , 8)
                .input(pipeTinyFluid, Steel, 4)
                .inputs(ELECTRIC_PUMP_LV.getStackForm(2))
                .notConsumable(IntCircuitIngredient.getIntegratedCircuit(1))
                .fluidInputs(LiquidNitrogen.getFluid(1000))
                .output(wireGtSingle, LVSuperconductor, 8)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[3])
                .scannerResearch(b -> b
                        .researchStack(DISK_5.getStackForm()))
                .input(wireGtSingle, MagnesiumDiboride, 8)
                .input(pipeTinyFluid, StainlessSteel, 4)
                .inputs(ELECTRIC_PUMP_MV.getStackForm(2))
                .notConsumable(IntCircuitIngredient.getIntegratedCircuit(1))
                .fluidInputs(LiquidNitrogen.getFluid(1000))
                .output(wireGtSingle, MVSuperconductor, 8)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[4])
                .scannerResearch(b -> b
                        .researchStack(DISK_5.getStackForm()))
                .input(wireGtSingle, MercuryBariumCalciumCuprate , 8)
                .input(pipeTinyFluid, Titanium, 4)
                .inputs(ELECTRIC_PUMP_HV.getStackForm(2))
                .notConsumable(IntCircuitIngredient.getIntegratedCircuit(1))
                .fluidInputs(LiquidNitrogen.getFluid(2000))
                .output(wireGtSingle, HVSuperconductor, 8)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[5])
                .scannerResearch(b -> b
                        .researchStack(DISK_5.getStackForm()))
                .input(wireGtSingle, UraniumTriplatinum , 8)
                .input(pipeTinyFluid, TungstenSteel, 4)
                .inputs(ELECTRIC_PUMP_EV.getStackForm(2))
                .notConsumable(IntCircuitIngredient.getIntegratedCircuit(1))
                .fluidInputs(LiquidNitrogen.getFluid(2000))
                .output(wireGtSingle, EVSuperconductor, 8)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[6])
                .scannerResearch(b -> b
                        .researchStack(DISK_5.getStackForm()))
                .input(wireGtSingle, SamariumIronArsenicOxide, 8)
                .input(pipeTinyFluid, NiobiumTitanium, 4)
                .inputs(ELECTRIC_PUMP_IV.getStackForm(2))
                .notConsumable(IntCircuitIngredient.getIntegratedCircuit(1))
                .fluidInputs(LiquidNitrogen.getFluid(4000))
                .output(wireGtSingle, IVSuperconductor, 8)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[7])
                .scannerResearch(b -> b
                        .researchStack(DISK_5.getStackForm()))
                .input(wireGtSingle, IndiumTinBariumTitaniumCuprate , 8)
                .input(pipeTinyFluid, Polybenzimidazole, 4)
                .inputs(ELECTRIC_PUMP_LuV.getStackForm(2))
                .notConsumable(IntCircuitIngredient.getIntegratedCircuit(1))
                .fluidInputs(LiquidNitrogen.getFluid(4000))
                .output(wireGtSingle, LuVSuperconductor, 8)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[8])
                .stationResearch(b -> b
                        .researchStack(OreDictUnifier.get(wireGtSingle, LuVSuperconductor))
                        .CWUt(CWT[7])
                        .EUt(VA[8]))
                .input(wireGtSingle, UraniumRhodiumDinaquadide , 8)
                .input(pipeTinyFluid, Naquadah, 4)
                .inputs(ELECTRIC_PUMP_ZPM.getStackForm(2))
                .notConsumable(IntCircuitIngredient.getIntegratedCircuit(1))
                .fluidInputs(LiquidHelium.getFluid(8000))
                .output(wireGtSingle, ZPMSuperconductor, 8)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[9])
                .stationResearch(b -> b
                        .researchStack(OreDictUnifier.get(wireGtSingle, ZPMSuperconductor))
                        .CWUt(CWT[8])
                        .EUt(VA[9]))
                .input(wireGtSingle, EnrichedNaquadahTriniumEuropiumDuranide , 8)
                .input(pipeTinyFluid, Duranium, 4)
                .inputs(ELECTRIC_PUMP_UV.getStackForm(2))
                .notConsumable(IntCircuitIngredient.getIntegratedCircuit(1))
                .fluidInputs(LiquidHelium.getFluid(8000))
                .output(wireGtSingle, UVSuperconductor, 8)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder().duration(600).EUt(VA[10])
                .stationResearch(b -> b
                        .researchStack(OreDictUnifier.get(wireGtSingle, UVSuperconductor))
                        .CWUt(CWT[9])
                        .EUt(VA[10]))
                .input(wireGtSingle, RutheniumTriniumAmericiumNeutronate , 8)
                .input(pipeTinyFluid, Neutronium, 4)
                .inputs(ELECTRIC_PUMP_UHV.getStackForm(2))
                .notConsumable(IntCircuitIngredient.getIntegratedCircuit(1))
                .fluidInputs(LiquidHelium.getFluid(12000))
                .output(wireGtSingle, UHVSuperconductor, 8)
                .buildAndRegister();

    }
}
