package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.unification.material.Material;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.MaterialHelper.Superconductor;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.*;

public class PowerSupply {
    public static void init() {
        //基础方块 框架+压电陶瓷
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt, NiobiumTitanium, 1)
                .input(plate,TungstenSteel,6)
                .input(screw, NanometerBariumTitanate, 2)
                .fluidInputs(Zylon.getFluid(L * 2))
                .output(POWER_SUPPLY_HATCH_BASIC)
                .circuitMeta(1)
                .duration(400).EUt(480).buildAndRegister();

        //超导方块 基础方块+对应等级的超导+冷却液+铪
        for(int i=0;i<8;i++)
            ChaoDao(Superconductor[i],POWER_SUPPLY_HATCH_SUPPLY[i],i+1);

        //电池方块 基础方块+电池+线圈+锆
        DianChi(ENERGY_LAPOTRONIC_ORB,POWER_SUPPLY_HATCH_BATTLE[0],5);
        DianChi(ENERGY_LAPOTRONIC_ORB_CLUSTER,POWER_SUPPLY_HATCH_BATTLE[1],6);
        DianChi(ENERGY_MODULE,POWER_SUPPLY_HATCH_BATTLE[2],7);
        DianChi(ENERGY_CLUSTER,POWER_SUPPLY_HATCH_BATTLE[3],8);
        DianChi(ULTIMATE_BATTERY,POWER_SUPPLY_HATCH_BATTLE[4],9);
    }

    private static void ChaoDao(Material material, MetaTileEntity mte, int tier) {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(POWER_SUPPLY_HATCH_BASIC)
                .input(plate, ZirconiumCarbide, 4)
                .input(wireGtHex, material, 1)
                .fluidInputs(PCBCoolant.getFluid(L * 8))
                .output(mte)
                .circuitMeta(1)
                .duration(400).EUt(VA[tier]).buildAndRegister();
    }
    private static void DianChi(MetaItem<?>.MetaValueItem material,  MetaTileEntity mte, int tier) {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(POWER_SUPPLY_HATCH_BASIC)
                .input(plate, Hafnium, 4)
                .input(material)
                .fluidInputs(SodiumPotassium.getFluid(L * 8))
                .output(mte)
                .circuitMeta(1)
                .duration(400).EUt(VA[tier]).buildAndRegister();
    }

}
