package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.material.Material;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTPowerSupply;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.block.blocks.GTQTPowerSupply.SupplyType.POWER_SUPPLY_BASIC;
import static keqing.gtqtcore.common.block.blocks.GTQTPowerSupply.SupplyType.*;

public class PowerSupply {
    public static void init() {
        //基础方块 框架+压电陶瓷
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt, NiobiumTitanium, 1)
                .input(plate, NanometerBariumTitanate, 6)
                .fluidInputs(Zylon.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.POWER.getItemVariant(POWER_SUPPLY_BASIC))
                .circuitMeta(1)
                .duration(400).EUt(480).buildAndRegister();
        //超导方块 基础方块+对应等级的超导+冷却液+铪
        ChaoDao(ManganesePhosphide,POWER_SUPPLY_I,1);
        ChaoDao(MagnesiumDiboride,POWER_SUPPLY_II,2);
        ChaoDao(MercuryBariumCalciumCuprate ,POWER_SUPPLY_III,3);
        ChaoDao(UraniumTriplatinum,POWER_SUPPLY_IV,4);
        ChaoDao(SamariumIronArsenicOxide,POWER_SUPPLY_V,5);
        ChaoDao(IndiumTinBariumTitaniumCuprate,POWER_SUPPLY_VI,6);
        ChaoDao(UraniumRhodiumDinaquadide,POWER_SUPPLY_VII,7);
        ChaoDao(EnrichedNaquadahTriniumEuropiumDuranide ,POWER_SUPPLY_VIII,8);
        ChaoDao(RutheniumTriniumAmericiumNeutronate,POWER_SUPPLY_IVV,9);
        //电池方块 基础方块+电池+线圈+锆
        DianChi(ENERGY_LAPOTRONIC_ORB,POWER_SUPPLY_BATTERY_I,5);
        DianChi(ENERGY_LAPOTRONIC_ORB_CLUSTER,POWER_SUPPLY_BATTERY_II,6);
        DianChi(ENERGY_MODULE,POWER_SUPPLY_BATTERY_III,7);
        DianChi(ENERGY_CLUSTER,POWER_SUPPLY_BATTERY_IV,8);
        DianChi(ULTIMATE_BATTERY,POWER_SUPPLY_BATTERY_V,9);
    }

    private static void ChaoDao(Material material, GTQTPowerSupply.SupplyType BLOCK,int tier) {
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.POWER.getItemVariant(POWER_SUPPLY_BASIC))
                .input(plate, ZirconiumCarbide, 6)
                .input(wireGtHex, material, 1)
                .fluidInputs(PCBCoolant.getFluid(L * 8))
                .outputs(GTQTMetaBlocks.POWER.getItemVariant(BLOCK))
                .circuitMeta(1)
                .duration(400).EUt(VA[tier]).buildAndRegister();
    }
    private static void DianChi(MetaItem<?>.MetaValueItem material, GTQTPowerSupply.SupplyType BLOCK, int tier) {
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.POWER.getItemVariant(POWER_SUPPLY_BASIC))
                .input(plate, Hafnium, 6)
                .input(material)
                .fluidInputs(SodiumPotassium.getFluid(L * 8))
                .outputs(GTQTMetaBlocks.POWER.getItemVariant(BLOCK))
                .circuitMeta(1)
                .duration(400).EUt(VA[tier]).buildAndRegister();
    }

}
