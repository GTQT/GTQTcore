package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.WireProperties;
import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collection;
import java.util.List;

import static gregicality.multiblocks.api.unification.GCYMMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.unification.material.MarkerMaterials.Tier.HV;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtechfoodoption.GTFOMaterialHandler.Acetaldehyde;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.Formaldehyde;
import static keqing.gtqtcore.api.unification.GCYSMaterials.FormicAcid;
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
        Catalyst_i();
        Catalyst_ii();
        Catalyst_iii();
        Catalyst_iv();
    }

    private static void Catalyst_i() {
        //氧化
        //醇-》醛
        //醛-》酸
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(CATALYST_FRAMEWORK_I)
                .circuitMeta(5)
                .fluidInputs(Methanol.getFluid(1000))
                .fluidOutputs(Formaldehyde.getFluid(1000))
                .duration(400).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(CATALYST_FRAMEWORK_I)
                .circuitMeta(5)
              
                .fluidInputs(Formaldehyde.getFluid(1000))
                .fluidOutputs(FormicAcid.getFluid(1000))
                .duration(400).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(CATALYST_FRAMEWORK_I)
                .circuitMeta(5)
              
                .fluidInputs(Ethanol.getFluid(1000))
                .fluidOutputs(Acetaldehyde.getFluid(1000))
                .duration(400).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(CATALYST_FRAMEWORK_I)
                .circuitMeta(5)
              
                .fluidInputs(Ethylene.getFluid(1000))
                .fluidOutputs(Acetaldehyde.getFluid(1000))
                .duration(400).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(CATALYST_FRAMEWORK_I)
                .circuitMeta(5)
              
                .fluidInputs(Acetaldehyde.getFluid(1000))
                .fluidOutputs(AceticAcid.getFluid(1000))
                .duration(400).EUt(VA[GTValues.HV]).buildAndRegister();

    }

    private static void Catalyst_ii() {
        //还原
        //炔-》烯
        //烯-》烷
        //烯->醇
        //烯-》醛
        //醛-》醇
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(CATALYST_FRAMEWORK_II)
                .circuitMeta(5)
              
                .fluidInputs(Formaldehyde.getFluid(1000))
                .fluidOutputs(Methanol.getFluid(1000))
                .duration(400).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(CATALYST_FRAMEWORK_II)
                .circuitMeta(5)
              
                .fluidInputs(FormicAcid.getFluid(1000))
                .fluidOutputs(Formaldehyde.getFluid(1000))
                .duration(400).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(CATALYST_FRAMEWORK_II)
                .circuitMeta(5)
              
                .fluidInputs(Acetaldehyde.getFluid(1000))
                .fluidOutputs(Ethanol.getFluid(1000))
                .duration(400).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(CATALYST_FRAMEWORK_II)
                .circuitMeta(5)
              
                .fluidInputs(AceticAcid.getFluid(1000))
                .fluidOutputs(Acetaldehyde.getFluid(1000))
                .duration(400).EUt(VA[GTValues.HV]).buildAndRegister();

    }

    private static void Catalyst_iii() {
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(CATALYST_FRAMEWORK_III)
                .circuitMeta(5)
                .input(dust,Carbon,2)
                .fluidInputs(Hydrogen.getFluid(4000))
                .fluidOutputs(Polyethylene.getFluid(1000))
                .duration(4000).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(CATALYST_FRAMEWORK_III)
                .circuitMeta(5)
                .input(dust,Carbon,2)
                .fluidInputs(Fluorine.getFluid(4000))
                .fluidOutputs(Polytetrafluoroethylene.getFluid(1000))
                .duration(4000).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(CATALYST_FRAMEWORK_III)
                .circuitMeta(5)
                .input(dust,Carbon,6)
                .fluidInputs(Hydrogen.getFluid(4000))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(Epoxy.getFluid(1000))
                .duration(4000).EUt(VA[EV]).buildAndRegister();
    }

    private static void Catalyst_iv() {
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(CATALYST_FRAMEWORK_IV)
                .circuitMeta(5)
                .fluidInputs(Biomass.getFluid(1000))
                .fluidInputs(DistilledWater.getFluid(2000))
                .fluidOutputs(FermentedBiomass.getFluid(3000))
                .duration(200).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(CATALYST_FRAMEWORK_IV)
                .circuitMeta(5)
              
                .fluidInputs(RawOil.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(2000))
                .duration(100).EUt(VA[GTValues.HV]).buildAndRegister();

         CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(CATALYST_FRAMEWORK_IV)
                .circuitMeta(5)
              
                .fluidInputs(OilHeavy.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(2000))
                .duration(100).EUt(VA[GTValues.HV]).buildAndRegister();

         CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(CATALYST_FRAMEWORK_IV)
                .circuitMeta(5)
              
                .fluidInputs(OilLight.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(2000))
                .duration(100).EUt(VA[GTValues.HV]).buildAndRegister();

         CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(CATALYST_FRAMEWORK_IV)
                .circuitMeta(5)
              
                .fluidInputs(Oil.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(2000))
                .duration(100).EUt(VA[GTValues.HV]).buildAndRegister();
    }
    
    private static void PRECISION_SPINNING() {

        //催化剂框架 使用激光模板
        PRECISION_SPINNING.recipeBuilder()
                .duration(90000).EUt(VA[EV]).Laser(200).CWUt(256)
                .input(OrePrefix.frameGt,Aluminium,8)
                .input(OrePrefix.foil,Platinum,16)
                .input(circuit, MarkerMaterials.Tier.EV,8)
                .fluidInputs(Zylon.getFluid(1440))
                .fluidInputs(StyreneButadieneRubber.getFluid(1440))
                .output(CATALYST_FRAMEWORK_I)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .duration(90000).EUt(VA[EV]).Laser(200).CWUt(256)
                .input(OrePrefix.frameGt,HastelloyX,8)
                .input(OrePrefix.foil,Palladium,16)
                .input(circuit, MarkerMaterials.Tier.EV,8)
                .fluidInputs(Zylon.getFluid(1440))
                .fluidInputs(StyreneButadieneRubber.getFluid(1440))
                .output(CATALYST_FRAMEWORK_II)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .duration(90000).EUt(VA[EV]).Laser(200).CWUt(256)
                .input(OrePrefix.frameGt,Ruridit,8)
                .input(OrePrefix.foil,NiobiumTitanium,16)
                .input(circuit, MarkerMaterials.Tier.EV,8)
                .fluidInputs(Zylon.getFluid(1440))
                .fluidInputs(StyreneButadieneRubber.getFluid(1440))
                .output(CATALYST_FRAMEWORK_III)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .duration(90000).EUt(VA[EV]).Laser(200).CWUt(256)
                .input(OrePrefix.frameGt,TungstenSteel,8)
                .input(OrePrefix.foil,NanometerBariumTitanate,16)
                .input(circuit, MarkerMaterials.Tier.EV,8)
                .fluidInputs(Zylon.getFluid(1440))
                .fluidInputs(SterileGrowthMedium.getFluid(1000))
                .output(CATALYST_FRAMEWORK_IV)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
    }

    private static void PRECISION_SPRAYING() {
        //镜头镀膜

    }
}
