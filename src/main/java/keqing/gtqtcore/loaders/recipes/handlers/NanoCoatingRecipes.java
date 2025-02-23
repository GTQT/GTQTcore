package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.recipes.RecipeMaps.LARGE_CHEMICAL_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtechfoodoption.GTFOMaterialHandler.Acetaldehyde;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CHEMICAL_PLANT;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.PRECISION_SPINNING;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.Polyetheretherketone;
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
        Catalyst_v();
        Catalyst_vi();
    }

    private static void Catalyst_i() {
        //氧化
        //醇-》醛
        //醛-》酸
        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_I.getStackForm())
                .recipeLevel(3)
                .circuitMeta(5)
                .fluidInputs(Methanol.getFluid(1000))
                .fluidOutputs(Formaldehyde.getFluid(1000))
                .duration(400).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_I.getStackForm())
                .recipeLevel(3)
                .circuitMeta(5)
                .fluidInputs(Formaldehyde.getFluid(1000))
                .fluidOutputs(FormicAcid.getFluid(1000))
                .duration(400).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_I.getStackForm())
                .recipeLevel(3)
                .circuitMeta(5)
                .fluidInputs(Ethanol.getFluid(1000))
                .fluidOutputs(Acetaldehyde.getFluid(1000))
                .duration(400).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_I.getStackForm())
                .recipeLevel(3)
                .circuitMeta(5)
                .fluidInputs(Ethylene.getFluid(1000))
                .fluidOutputs(Acetaldehyde.getFluid(1000))
                .duration(400).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_I.getStackForm())
                .recipeLevel(3)
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
        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_II.getStackForm())
                .recipeLevel(3)
                .circuitMeta(5)
                .fluidInputs(Formaldehyde.getFluid(1000))
                .fluidOutputs(Methanol.getFluid(1000))
                .duration(400).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_II.getStackForm())
                .circuitMeta(5)
                .fluidInputs(FormicAcid.getFluid(1000))
                .fluidOutputs(Formaldehyde.getFluid(1000))
                .duration(400).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_II.getStackForm())
                .circuitMeta(5)
                .fluidInputs(Acetaldehyde.getFluid(1000))
                .fluidOutputs(Ethanol.getFluid(1000))
                .duration(400).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_II.getStackForm())
                .circuitMeta(5)
                .fluidInputs(AceticAcid.getFluid(1000))
                .fluidOutputs(Acetaldehyde.getFluid(1000))
                .duration(400).EUt(VA[GTValues.HV]).buildAndRegister();

    }

    private static void Catalyst_iii() {
        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_III.getStackForm())
                .input(dustTiny, Gold)
                .circuitMeta(5)
                .input(dust, Carbon, 2)
                .fluidInputs(Hydrogen.getFluid(4000))
                .fluidOutputs(Polyethylene.getFluid(1000))
                .recipeLevel(1)
                .duration(1000).EUt(VA[HV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_III.getStackForm())
                .input(dustTiny, Gold)
                .circuitMeta(5)
                .input(dust, Carbon, 2)
                .fluidInputs(Fluorine.getFluid(4000))
                .fluidOutputs(Polytetrafluoroethylene.getFluid(1000))
                .recipeLevel(4)
                .duration(1000).EUt(VA[IV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_III.getStackForm())
                .input(dustTiny, Gold)
                .circuitMeta(6)
                .input(dust, Carbon, 2)
                .fluidInputs(Hydrogen.getFluid(3000))
                .fluidInputs(Chlorine.getFluid(1000))
                .fluidOutputs(PolyvinylChloride.getFluid(1000))
                .recipeLevel(3)
                .duration(1000).EUt(VA[HV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_III.getStackForm())
                .input(dustTiny, Platinum)
                .circuitMeta(5)
                .input(dust, Carbon, 6)
                .fluidInputs(Hydrogen.getFluid(4000))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(Epoxy.getFluid(1000))
                .recipeLevel(4)
                .duration(1000).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_III.getStackForm())
                .input(dustTiny, Samarium)
                .circuitMeta(6)
                .input(dust, Carbon, 14)
                .fluidInputs(Hydrogen.getFluid(6000))
                .fluidInputs(Nitrogen.getFluid(2000))
                .fluidInputs(Oxygen.getFluid(2000))
                .fluidOutputs(Zylon.getFluid(144))
                .recipeLevel(5)
                .duration(1000).EUt(VA[IV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_III.getStackForm())
                .input(dustTiny, Naquadah)
                .circuitMeta(6)
                .input(dust, Carbon, 20)
                .fluidInputs(Hydrogen.getFluid(12000))
                .fluidInputs(Nitrogen.getFluid(4000))
                .fluidOutputs(Polybenzimidazole.getFluid(1000))
                .recipeLevel(6)
                .duration(1000).EUt(VA[LuV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_III.getStackForm())
                .input(dustTiny, Naquadria)
                .circuitMeta(6)
                .input(dust, Carbon, 20)
                .fluidInputs(Hydrogen.getFluid(12000))
                .fluidInputs(Oxygen.getFluid(3000))
                .fluidOutputs(Polyetheretherketone.getFluid(1000))
                .recipeLevel(7)
                .duration(1000).EUt(VA[ZPM]).buildAndRegister();
    }

    private static void Catalyst_iv() {
        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_IV.getStackForm())
                .recipeLevel(3)
                .circuitMeta(5)
                .fluidInputs(Biomass.getFluid(1000))
                .fluidInputs(DistilledWater.getFluid(2000))
                .fluidOutputs(FermentedBiomass.getFluid(3000))
                .duration(200).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_IV.getStackForm())
                .recipeLevel(3)
                .circuitMeta(5)
                .fluidInputs(RawOil.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(2000))
                .duration(100).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_IV.getStackForm())
                .recipeLevel(3)
                .circuitMeta(5)
                .fluidInputs(OilHeavy.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(2000))
                .duration(100).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_IV.getStackForm())
                .recipeLevel(3)
                .circuitMeta(5)
                .fluidInputs(OilLight.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(2000))
                .duration(100).EUt(VA[GTValues.HV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_IV.getStackForm())
                .recipeLevel(3)
                .circuitMeta(5)
                .fluidInputs(Oil.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(2000))
                .duration(100).EUt(VA[GTValues.HV]).buildAndRegister();
    }

    //嬗变
    private static void Catalyst_v() {
        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_V.getStackForm())
                .fluidInputs(HydrochloricAcid.getFluid(10000))
                .input(dust, SodiumTungstateDihydrate, 10)
                .chancedOutput(dust, Powellite, 10, 5000, 0)
                .fluidOutputs(ItungsticAcid.getFluid(4800))
                .fluidOutputs(ItungsticAcid.getFluid(800))
                .fluidOutputs(ItungsticAcid.getFluid(800))
                .fluidOutputs(ItungsticAcid.getFluid(800))
                .fluidOutputs(ItungsticAcid.getFluid(800))
                .fluidOutputs(ItungsticAcid.getFluid(800))
                .recipeLevel(5)
                .duration(800)
                .EUt(VA[EV])
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_V.getStackForm())
                .fluidInputs(ImpureTannicAcid.getFluid(2000))
                .input(OrePrefix.dust, Carbon, 80)
                .fluidOutputs(HydrobromicAcid.getFluid(200))
                .fluidOutputs(TannicAcid.getFluid(800))
                .recipeLevel(5)
                .duration(800)
                .EUt(VA[EV])
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_V.getStackForm())
                .fluidInputs(BioOil.getFluid(6000))
                .input(OrePrefix.dust, SodaAsh, 10)
                .fluidOutputs(RawOil.getFluid(750))
                .fluidOutputs(RawOil.getFluid(750))
                .fluidOutputs(RawOil.getFluid(750))
                .fluidOutputs(Biomass.getFluid(600))
                .recipeLevel(5)
                .duration(800)
                .EUt(VA[EV])
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_V.getStackForm())
                .fluidInputs(BioOil.getFluid(6000))
                .input(OrePrefix.dust, Meat, 10)
                .fluidOutputs(RawOil.getFluid(750))
                .fluidOutputs(RawOil.getFluid(750))
                .fluidOutputs(RawOil.getFluid(750))
                .fluidOutputs(Biomass.getFluid(600))
                .recipeLevel(5)
                .duration(800)
                .EUt(VA[EV])
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_V.getStackForm())
                .fluidInputs(BioOil.getFluid(6000))
                .input(OrePrefix.dust, Bone, 10)
                .fluidOutputs(RawOil.getFluid(750))
                .fluidOutputs(RawOil.getFluid(750))
                .fluidOutputs(RawOil.getFluid(750))
                .fluidOutputs(Biomass.getFluid(600))
                .recipeLevel(5)
                .duration(800)
                .EUt(VA[EV])
                .buildAndRegister();
    }

    //裂解
    private static void Catalyst_vi() {
        //木头线
        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_VI.getStackForm())
                .input(log, Wood, 64)
                .circuitMeta(1)
                .outputs(new ItemStack(Items.COAL, 64, 1))
                .fluidOutputs(Creosote.getFluid(300 * 8))
                .fluidOutputs(Phenol.getFluid(75 * 8))
                .fluidOutputs(Benzene.getFluid(350 * 8))
                .fluidOutputs(Toluene.getFluid(75 * 8))
                .fluidOutputs(Dimethylbenzene.getFluid(200 * 8))
                .recipeLevel(4)
                .duration(800)
                .EUt(VA[EV])
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_VI.getStackForm())
                .input(log, Wood, 64)
                .circuitMeta(2)
                .outputs(new ItemStack(Items.COAL, 64, 1))
                .fluidOutputs(AceticAcid.getFluid(100 * 8))
                .fluidOutputs(Water.getFluid(500 * 8))
                .fluidOutputs(Ethanol.getFluid(10 * 8))
                .fluidOutputs(Methanol.getFluid(300 * 8))
                .fluidOutputs(Acetone.getFluid(50 * 8))
                .fluidOutputs(MethylAcetate.getFluid(10 * 8))
                .recipeLevel(4)
                .duration(800)
                .EUt(VA[EV])
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_VI.getStackForm())
                .input(log, Wood, 64)
                .circuitMeta(3)
                .outputs(new ItemStack(Items.COAL, 64, 1))
                .fluidOutputs(CarbonDioxide.getFluid(490 * 8))
                .fluidOutputs(Ethylene.getFluid(20 * 8))
                .fluidOutputs(Methane.getFluid(130 * 8))
                .fluidOutputs(CarbonMonoxide.getFluid(340 * 8))
                .fluidOutputs(Hydrogen.getFluid(20 * 8))
                .recipeLevel(4)
                .duration(800)
                .EUt(VA[EV])
                .buildAndRegister();
        //石油线
        List<Material> oilTypes = Arrays.asList(Oil, OilLight, OilHeavy, RawOil);
        for (Material oil : oilTypes) {
            CHEMICAL_PLANT.recipeBuilder()
                    .Catalyst(CATALYST_FRAMEWORK_VI.getStackForm())
                    .fluidInputs(oil.getFluid(1000))
                    .fluidInputs(Hydrogen.getFluid(2000))
                    .circuitMeta(2)
                    .output(dust, Carbon, 1)
                    .fluidOutputs(Octane.getFluid(100))
                    .fluidOutputs(Naphtha.getFluid(250))
                    .fluidOutputs(Butane.getFluid(300))
                    .fluidOutputs(Propane.getFluid(300))
                    .fluidOutputs(Ethane.getFluid(175))
                    .fluidOutputs(Methane.getFluid(175))
                    .recipeLevel(5)
                    .duration(800)
                    .EUt(VA[IV])
                    .buildAndRegister();

            CHEMICAL_PLANT.recipeBuilder()
                    .Catalyst(CATALYST_FRAMEWORK_VI.getStackForm())
                    .fluidInputs(oil.getFluid(1000))
                    .fluidInputs(Steam.getFluid(2000))
                    .circuitMeta(2)
                    .output(dust, Carbon, 1)
                    .fluidOutputs(Toluene.getFluid(80))
                    .fluidOutputs(Benzene.getFluid(400))
                    .fluidOutputs(Butene.getFluid(80))
                    .fluidOutputs(Butadiene.getFluid(50))
                    .fluidOutputs(Propene.getFluid(100))
                    .fluidOutputs(Ethylene.getFluid(150))
                    .recipeLevel(5)
                    .duration(800)
                    .EUt(VA[IV])
                    .buildAndRegister();
        }
        ////////////////////////////////////////////////////
        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_VI.getStackForm())
                .fluidInputs(Oil.getFluid(1000))
                .circuitMeta(1)
                .chancedOutput(dust, Sulfur, 1000, 0)
                .fluidOutputs(HeavyFuel.getFluid(300))
                .fluidOutputs(LightFuel.getFluid(1000))
                .fluidOutputs(Naphtha.getFluid(400))
                .fluidOutputs(OilGas.getFluid(1200))
                .recipeLevel(5)
                .duration(800)
                .EUt(VA[IV])
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_VI.getStackForm())
                .fluidInputs(OilLight.getFluid(1500))
                .circuitMeta(1)
                .chancedOutput(dust, Sulfur, 1000, 0)
                .fluidOutputs(HeavyFuel.getFluid(100))
                .fluidOutputs(LightFuel.getFluid(200))
                .fluidOutputs(Naphtha.getFluid(300))
                .fluidOutputs(OilGas.getFluid(2400))
                .recipeLevel(5)
                .duration(800)
                .EUt(VA[IV])
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_VI.getStackForm())
                .fluidInputs(OilHeavy.getFluid(1000))
                .circuitMeta(1)
                .chancedOutput(dust, Sulfur, 1000, 0)
                .fluidOutputs(HeavyFuel.getFluid(2500))
                .fluidOutputs(LightFuel.getFluid(450))
                .fluidOutputs(Naphtha.getFluid(150))
                .fluidOutputs(OilGas.getFluid(600))
                .recipeLevel(5)
                .duration(800)
                .EUt(VA[IV])
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .Catalyst(CATALYST_FRAMEWORK_VI.getStackForm())
                .fluidInputs(RawOil.getFluid(1000))
                .circuitMeta(1)
                .chancedOutput(dust, Sulfur, 1000, 0)
                .fluidOutputs(HeavyFuel.getFluid(100))
                .fluidOutputs(LightFuel.getFluid(500))
                .fluidOutputs(Naphtha.getFluid(1500))
                .fluidOutputs(OilGas.getFluid(600))
                .recipeLevel(5)
                .duration(800)
                .EUt(VA[IV])
                .buildAndRegister();
    }

    private static void PRECISION_SPINNING() {

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .duration(600).EUt(VA[EV])
                .input(frameGt, StainlessSteel, 8)
                .input(OrePrefix.foil, Titanium, 4)
                .input(wireFine, Platinum, 2)
                .fluidInputs(Biomass.getFluid(200))
                .fluidInputs(Zylon.getFluid(144))
                .fluidInputs(StyreneButadieneRubber.getFluid(144))
                .output(CATALYST_FRAMEWORK_BLANK,8)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //催化剂框架 使用激光模板
        PRECISION_SPINNING.recipeBuilder()
                .duration(400).EUt(VA[EV]).CWUt(256)
                .input(CATALYST_FRAMEWORK_BLANK)
                .input(OrePrefix.foil, Platinum, 4)
                .input(circuit, MarkerMaterials.Tier.HV, 1)
                .fluidInputs(Enzymesbc.getFluid(200))
                .fluidInputs(Zylon.getFluid(144))
                .fluidInputs(StyreneButadieneRubber.getFluid(144))
                .output(CATALYST_FRAMEWORK_I)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .duration(400).EUt(VA[EV]).CWUt(256)
                .input(CATALYST_FRAMEWORK_BLANK)
                .input(OrePrefix.foil, Palladium, 4)
                .input(circuit, MarkerMaterials.Tier.HV, 1)
                .fluidInputs(Enzymesbb.getFluid(200))
                .fluidInputs(Zylon.getFluid(144))
                .fluidInputs(StyreneButadieneRubber.getFluid(144))
                .output(CATALYST_FRAMEWORK_II)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .duration(400).EUt(VA[EV]).CWUt(256)
                .input(CATALYST_FRAMEWORK_BLANK)
                .input(OrePrefix.foil, NiobiumTitanium, 4)
                .input(circuit, MarkerMaterials.Tier.HV, 1)
                .fluidInputs(Enzymesba.getFluid(200))
                .fluidInputs(Zylon.getFluid(144))
                .fluidInputs(StyreneButadieneRubber.getFluid(144))
                .output(CATALYST_FRAMEWORK_III)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .duration(400).EUt(VA[EV]).CWUt(256)
                .input(CATALYST_FRAMEWORK_BLANK)
                .input(OrePrefix.foil, NanometerBariumTitanate, 4)
                .input(circuit, MarkerMaterials.Tier.HV, 1)
                .fluidInputs(Enzymesca.getFluid(200))
                .fluidInputs(Zylon.getFluid(144))
                .fluidInputs(Mutagen.getFluid(144))
                .output(CATALYST_FRAMEWORK_IV)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .duration(400).EUt(VA[EV]).CWUt(256)
                .input(CATALYST_FRAMEWORK_BLANK)
                .input(OrePrefix.foil, Osmium, 4)
                .input(circuit, MarkerMaterials.Tier.HV, 1)
                .fluidInputs(Enzymesbd.getFluid(200))
                .fluidInputs(Zylon.getFluid(144))
                .fluidInputs(Mutagen.getFluid(144))
                .output(CATALYST_FRAMEWORK_V)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        PRECISION_SPINNING.recipeBuilder()
                .duration(400).EUt(VA[EV]).CWUt(256)
                .input(CATALYST_FRAMEWORK_BLANK)
                .input(OrePrefix.foil, Iridium, 4)
                .input(circuit, MarkerMaterials.Tier.HV, 1)
                .fluidInputs(Enzymesab.getFluid(200))
                .fluidInputs(Zylon.getFluid(144))
                .fluidInputs(Mutagen.getFluid(144))
                .output(CATALYST_FRAMEWORK_VI)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
    }

    private static void PRECISION_SPRAYING() {
        //镜头镀膜

    }
}
