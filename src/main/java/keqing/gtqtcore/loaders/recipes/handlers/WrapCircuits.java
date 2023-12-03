package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.unification.material.MarkerMaterials;


import static gregtech.api.GTValues.ULV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.PACKER_RECIPES;
import static gregtech.api.unification.ore.OrePrefix.circuit;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class WrapCircuits {
    public static void init() {
        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.ULV, 64)
                .circuitMeta(32)
                .output(WRAP_CIRCUIT_ULV)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.LV, 64)
                .circuitMeta(32)
                .output(WRAP_CIRCUIT_LV)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.MV, 64)
                .circuitMeta(32)
                .output(WRAP_CIRCUIT_MV)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.HV, 64)
                .circuitMeta(32)
                .output(WRAP_CIRCUIT_HV)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.EV, 64)
                .circuitMeta(32)
                .output(WRAP_CIRCUIT_EV)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.IV, 64)
                .circuitMeta(32)
                .output(WRAP_CIRCUIT_IV)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.LuV, 64)
                .circuitMeta(32)
                .output(WRAP_CIRCUIT_LuV)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.ZPM, 64)
                .circuitMeta(32)
                .output(WRAP_CIRCUIT_ZPM)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.UV, 64)
                .circuitMeta(32)
                .output(WRAP_CIRCUIT_UV)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.UHV, 64)
                .circuitMeta(32)
                .output(WRAP_CIRCUIT_UHV)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.UEV, 64)
                .circuitMeta(32)
                .output(WRAP_CIRCUIT_UEV)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.UIV, 64)
                .circuitMeta(32)
                .output(WRAP_CIRCUIT_UIV)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.UXV, 64)
                .circuitMeta(32)
                .output(WRAP_CIRCUIT_UXV)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.OpV, 64)
                .circuitMeta(32)
                .output(WRAP_CIRCUIT_OpV)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        PACKER_RECIPES.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.MAX, 64)
                .circuitMeta(32)
                .output(WRAP_CIRCUIT_MAX)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();
    }
}
