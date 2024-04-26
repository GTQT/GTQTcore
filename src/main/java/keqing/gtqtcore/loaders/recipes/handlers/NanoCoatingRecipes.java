package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

import static gregicality.multiblocks.api.unification.GCYMMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.MarkerMaterials.Tier.HV;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.circuit;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class NanoCoatingRecipes {
    //设备：纳米喷涂器 机制（同光刻 追加效能模式，模式下并行*2 耗时/2 限制电压为正常的-2）
    // 配方：3D打印
    // 精密喷涂（用于加工透镜玻璃 或者其他需要覆膜的精密材料 算力配方）
    // 精密喷丝（编制滤网 催化剂框架 激光型算力配方）

    public static void init() {
        PRECISION_SPRAYING();//喷涂
        PRECISION_SPINNING();//喷丝
    }

    private static void PRECISION_SPINNING() {
        //催化剂框架 使用激光模板
        PRECISION_SPINNING.recipeBuilder()
                .duration(90000).EUt(VA[EV]).Laser(200).CWUt(256)
                .input(OrePrefix.frameGt,TungstenSteel,8)
                .input(OrePrefix.foil,Palladium,16)
                .input(circuit, MarkerMaterials.Tier.EV,8)
                .fluidInputs(Zylon.getFluid(1440))
                .output(CATALYST_FRAMEWORK_I)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .duration(90000).EUt(VA[EV]).Laser(200).CWUt(256)
                .input(OrePrefix.frameGt,HastelloyX,8)
                .input(OrePrefix.foil,Palladium,16)
                .input(circuit, MarkerMaterials.Tier.EV,8)
                .fluidInputs(Zylon.getFluid(1440))
                .output(CATALYST_FRAMEWORK_I)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .duration(90000).EUt(VA[EV]).Laser(200).CWUt(256)
                .input(OrePrefix.frameGt,Ruridit,8)
                .input(OrePrefix.foil,NiobiumTitanium,16)
                .input(circuit, MarkerMaterials.Tier.EV,8)
                .fluidInputs(Zylon.getFluid(1440))
                .output(CATALYST_FRAMEWORK_III)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
    }

    private static void PRECISION_SPRAYING() {
    }
}
