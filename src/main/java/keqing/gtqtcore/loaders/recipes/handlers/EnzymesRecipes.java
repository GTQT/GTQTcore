package keqing.gtqtcore.loaders.recipes.handlers;

import com.cleanroommc.groovyscript.api.IIngredient;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import ibxm.Player;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.BREWING_RECIPES;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtechfoodoption.GTFOMaterialHandler.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Enzymesca;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class EnzymesRecipes {
    public static void init() {
        growth(Enzymesaz,Enzymesa);
        growth(Enzymesbz,Enzymesb);
        growth(Enzymescz,Enzymesc);
        growth(Enzymesdz,Enzymesd);
        growth(Enzymesez,Enzymese);

        enzymesmix(Enzymesaa,Iron,101,1,1);
        enzymesmix(Enzymesab,Gold,102,1,2);
        enzymesmix(Enzymesac,Mercury,103,1,3);
        enzymesmix(Enzymesad,Titanium,104,1,4);

        enzymesmix(Enzymesba,Polyethylene,201,2,5);
        enzymesmix(Enzymesbb,Oxygen,202,2,6);
        enzymesmix(Enzymesbc,Hydrogen,203,2,7);
        enzymesmix(Enzymesbd,Benzene,204,2,8);

        enzymesmix(Enzymesca,Stearin,301,3,9);
        enzymesmix(Enzymescb,Biomass,302,3,10);
        enzymesmix(Enzymescc,Methane,303,3,11);

        enzymesmix(Enzymesda,Brominatedepoxyresins,401,4,12);


        //物尽其用
        /*
        普适矿处菌种	1	倍产铁 铜 锡	HV
        定向铂系菌种	2	倍产金 银 铂系	EV
        普适魔性菌种	1	转化魔力矿到源质	HV
        普适副产菌种	2	倍产副产（暂定HV前的矿）	HV

        工业合成菌种I	1	催化塑料 四氟乙烯 环氧树脂	HV
        工业还原菌种	1	还原反应催化	HV
        工业氧化菌种	1	氧化反应催化	HV
        工业催化菌种	1	催化部分石化产物	HV

        定向脂肪酶	1	催化肉转化为脂肪	HV
        普适发酵酶	1	嘎嘎产沼气	HV
        定向发酵酶	1	转化作物到生物燃料	HV

        活性诱变酶	2	植物魔法 启动	HV
        */
        //格式 小消耗细菌 原材料 输出材料
        //普适矿处菌种	1	倍产铁 铜 锡	HV
        SpecialOut(Enzymesaa,10,ore,Iron,1,dust,Iron,4,1,10);
        SpecialOut(Enzymesaa,10,ore,BandedIron,1,dust,Iron,4,1,10);
        SpecialOut(Enzymesaa,10,ore,BrownLimonite,1,dust,Iron,4,1,10);
        SpecialOut(Enzymesaa,10,ore,Chromite,1,dust,Iron,4,1,10);
        SpecialOut(Enzymesaa,10,ore,Magnetite,1,dust,Iron,4,1,10);
        SpecialOut(Enzymesaa,10,ore,Pyrite,1,dust,Iron,4,1,10);
        SpecialOut(Enzymesaa,10,ore,YellowLimonite,1,dust,Iron,4,1,10);

        SpecialOut(Enzymesaa,10,ore,Pentlandite,1,dust,Nickel,4,1,10);

        SpecialOut(Enzymesaa,10,ore,Copper,1,dust,Copper,4,1,10);
        SpecialOut(Enzymesaa,10,ore,Chalcopyrite,1,dust,Copper,4,1,10);
        SpecialOut(Enzymesaa,10,ore,Bornite,1,dust,Copper,4,1,10);
        SpecialOut(Enzymesaa,10,ore,Chalcocite,1,dust,Copper,4,1,10);
        SpecialOut(Enzymesaa,10,ore,Tetrahedrite,1,dust,Copper,4,1,10);

        SpecialOut(Enzymesaa,10,ore,Tin,1,dust,Tin,4,1,10);
        SpecialOut(Enzymesaa,10,ore,Cassiterite,1,dust,Tin,4,1,10);
        SpecialOut(Enzymesaa,10,ore,CassiteriteSand,1,dust,Tin,4,1,10);

        SpecialOut(Enzymesaa,10,ore,Aluminium,1,dust,Aluminium,4,1,10);
        SpecialOut(Enzymesaa,10,ore,Almandine,1,dust,Aluminium,4,1,10);
        SpecialOut(Enzymesaa,10,ore,Grossular,1,dust,Aluminium,4,1,10);
        SpecialOut(Enzymesaa,10,ore,Pyrope,1,dust,Aluminium,4,1,10);
        SpecialOut(Enzymesaa,10,ore,Spessartine,1,dust,Aluminium,4,1,10);
        SpecialOut(Enzymesaa,10,ore,Amblygonite,1,dust,Aluminium,4,1,10);
        SpecialOut(Enzymesaa,10,ore,Bauxite,1,dust,Aluminium,4,1,10);

        //定向铂系菌种	2	倍产金 银 铂系	EV
        SpecialOut(Enzymesab,10,ore,Gold,1,dust,Gold,4,1,20);
        SpecialOut(Enzymesab,10,ore,Silver,1,dust,Silver,4,1,20);
        SpecialOutCircuit(Enzymesab,10,1,dust,PlatinumGroupSludge,1,dust, Platinum,2,3,40);
        SpecialOutCircuit(Enzymesab,10,2,dust,PlatinumGroupSludge,1,dust, Palladium,2,3,40);
        SpecialOutCircuit(Enzymesab,10,3,dust,PlatinumGroupSludge,1,dust, Iridium,2,3,40);
        SpecialOutCircuit(Enzymesab,10,4,dust,PlatinumGroupSludge,1,dust, Osmium,2,3,40);
        SpecialOutCircuit(Enzymesab,10,5,dust,PlatinumGroupSludge,1,dust, Rhodium,2,3,40);
        SpecialOutCircuit(Enzymesab,10,6,dust,PlatinumGroupSludge,1,dust, Ruthenium,2,3,40);

        // 普适副产菌种	2	倍产副产（暂定HV前的矿）	HV
        SpecialOutCircuit(Enzymesad,10,1,ore,Aluminium,1,dust, Rutile,2,2,20);

        SpecialOutCircuit(Enzymesad,10,1,ore,Copper,1,dust, Gold,2,1,20);
        SpecialOutCircuit(Enzymesad,10,2,ore,Copper,1,dust, Cobalt,2,1,20);

        SpecialOutCircuit(Enzymesad,10,1,ore,Magnetite,1,dust, Gold,2,1,20);

        SpecialOutCircuit(Enzymesad,10,1,ore,Pyrite,1,dust, Sulfur,2,1,20);

        SpecialOutCircuit(Enzymesad,10,1,ore,Gold,1,dust, Copper,2,1,20);
        SpecialOutCircuit(Enzymesad,10,2,ore,Gold,1,dust, Silver,2,1,20);

        SpecialOutCircuit(Enzymesad,10,1,ore,Silver,1,dust, Gold,2,1,20);
        SpecialOutCircuit(Enzymesad,10,2,ore,Silver,1,dust, Lead,2,1,20);

        SpecialOutCircuit(Enzymesad,10,1,ore,Galena,1,dust, Silver,2,1,20);
        SpecialOutCircuit(Enzymesad,10,2,ore,Galena,1,dust, Sulfur,2,1,20);

        SpecialOutCircuit(Enzymesad,10,1,ore,Iron,1,dust, Gold,2,1,20);
        SpecialOutCircuit(Enzymesad,10,2,ore,Iron,1,dust, Nickel,2,1,20);
        SpecialOutCircuit(Enzymesad,10,3,ore,Iron,1,dust, Zinc,2,1,20);

        SpecialOutCircuit(Enzymesad,10,1,ore,Lead,1,dust, Nickel,2,1,20);

        SpecialOutCircuit(Enzymesad,20,1,ore,Nickel,1,dust, Platinum,2,2,40);

        //  工业合成菌种I	1	催化塑料 四氟乙烯 环氧树脂	HV
        Enzymesba();
        //氧化还原催化
        Enzymesbb();
        Enzymesbc();
        Enzymesbd();
        
        //定向脂肪酶	1	催化肉转化为脂肪	HV
        Enzymesca();

        //普适发酵酶	1	嘎嘎产沼气	HV
        Enzymescb();
        //定向发酵酶	1	转化作物到生物燃料	HV
        Enzymescc();


        //缓冲剂
        Ethanesulphonate();
        //原始离心
        BioCentrifuge();
        //富集培养基

    }

    private static void BioCentrifuge() {
        BIO_CENTRIFUGE.recipeBuilder()
                .input(dust,Endstone)
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidInputs(Ethanesulphonate.getFluid(1000))
                .fluidOutputs(Enzymesaz.getFluid(100))
                .duration(4000).EUt(VA[HV]).buildAndRegister();

        BIO_CENTRIFUGE.recipeBuilder()
                .fluidInputs(LiquidBetAir.getFluid(10000))
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidInputs(Ethanesulphonate.getFluid(1000))
                .fluidOutputs(Enzymesbz.getFluid(100))
                .duration(4000).EUt(VA[HV]).buildAndRegister();

        BIO_CENTRIFUGE.recipeBuilder()
                .input(dust,RareEarth)
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidInputs(Ethanesulphonate.getFluid(1000))
                .fluidOutputs(Enzymescz.getFluid(100))
                .duration(4000).EUt(VA[HV]).buildAndRegister();

        BIO_CENTRIFUGE.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(10000))
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidInputs(Ethanesulphonate.getFluid(1000))
                .fluidOutputs(Enzymesdz.getFluid(100))
                .duration(4000).EUt(VA[HV]).buildAndRegister();

        BIO_CENTRIFUGE.recipeBuilder()
                .input(dust,Plutonium241)
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidInputs(Ethanesulphonate.getFluid(1000))
                .fluidOutputs(Enzymesez.getFluid(100))
                .duration(4000).EUt(VA[HV]).buildAndRegister();
    }

    private static void Ethanesulphonate() {
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Butanediol.getFluid(1000))
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .fluidOutputs(THF.getFluid(1000))
                .duration(400).EUt(VA[HV]).buildAndRegister();

        BREWING_RECIPES.recipeBuilder()
                .input(dust,Sugar,16)
                .fluidInputs(Water.getFluid(16000))
                .fluidOutputs(Hydroxymethylfurfural.getFluid(1600))
                .duration(400).EUt(VA[HV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .fluidInputs(THF.getFluid(1000))
                .fluidInputs(Hydroxymethylfurfural.getFluid(1000))
                .fluidOutputs(Methylfuran.getFluid(1000))
                .recipeLevel(3)
                .duration(400).EUt(VA[HV]).buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .fluidInputs(Methylfuran.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(CHES.getFluid(1000))
                .recipeLevel(3)
                .duration(400).EUt(VA[HV]).buildAndRegister();

        //生物缓冲剂
        CHEMICAL_PLANT.recipeBuilder()
                .fluidInputs(CHES.getFluid(1000))
                .fluidInputs(SodiumHydroxideSolution.getFluid(1000))
                .fluidOutputs(Ethanesulphonate.getFluid(1000))
                .recipeLevel(3)
                .duration(400).EUt(VA[HV]).buildAndRegister();
    }

    private static void growth(Material material1,Material material2) {
        CHEMICAL_PLANT.recipeBuilder()
                .fluidInputs(material1.getFluid(8000))
                .fluidInputs(TRIS.getFluid(1000))
                .fluidInputs(Hydroxyethanethiol.getFluid(1000))
                .fluidInputs(CTAB.getFluid(1000))
                .fluidInputs(Isoamylalcohol.getFluid(1000))
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidOutputs(material2.getFluid(16000))
                .recipeLevel(3)
                .duration(400).EUt(VA[HV]).buildAndRegister();
    }

    private static void Enzymescb() {
        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymescb.getFluid(10))
                .fluidInputs(Biomass.getFluid(1000))
                .fluidInputs(DistilledWater.getFluid(2000))
                .fluidOutputs(FermentedBiomass.getFluid(3000))
                .rate(20)
                .duration(200).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymescb.getFluid(5))
                .fluidInputs(RawOil.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(2000))
                .rate(5)
                .duration(100).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymescb.getFluid(5))
                .fluidInputs(OilHeavy.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(2000))
                .rate(5)
                .duration(100).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymescb.getFluid(5))
                .fluidInputs(OilLight.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(2000))
                .rate(5)
                .duration(100).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymescb.getFluid(5))
                .fluidInputs(Oil.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(2000))
                .rate(5)
                .duration(100).EUt(VA[HV]).buildAndRegister();
    }

    private static void Enzymescc() {
        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymescc.getFluid(10))
                .fluidInputs(FermentedBiomass.getFluid(1000))
                .fluidOutputs(Biooil.getFluid(2000))
                .rate(20)
                .duration(200).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymescc.getFluid(10))
                .fluidInputs(FishOil.getFluid(1000))
                .fluidOutputs(BioDiesel.getFluid(1000))
                .rate(20)
                .duration(200).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymescc.getFluid(10))
                .fluidInputs(SeedOil.getFluid(1000))
                .fluidOutputs(BioDiesel.getFluid(1000))
                .rate(20)
                .duration(200).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymescc.getFluid(10))
                .fluidInputs(OliveOil.getFluid(1000))
                .fluidOutputs(BioDiesel.getFluid(1000))
                .rate(20)
                .duration(200).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymescc.getFluid(10))
                .fluidInputs(FryingOil.getFluid(1000))
                .fluidOutputs(BioDiesel.getFluid(1000))
                .rate(20)
                .duration(200).EUt(VA[HV]).buildAndRegister();
    }

    private static void Enzymesca() {
        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesca.getFluid(10))
                .input(dust,Meat)
                .fluidOutputs(Stearin.getFluid(1000))
                .rate(20)
                .duration(200).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesca.getFluid(10))
                .fluidInputs(FishOil.getFluid(100))
                .fluidOutputs(Stearin.getFluid(1000))
                .rate(20)
                .duration(200).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesca.getFluid(10))
                .fluidInputs(SeedOil.getFluid(100))
                .fluidOutputs(Stearin.getFluid(1000))
                .rate(20)
                .duration(200).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesca.getFluid(10))
                .fluidInputs(OliveOil.getFluid(100))
                .fluidOutputs(Stearin.getFluid(1000))
                .rate(20)
                .duration(200).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesca.getFluid(10))
                .fluidInputs(FryingOil.getFluid(100))
                .fluidOutputs(Stearin.getFluid(1000))
                .rate(20)
                .duration(200).EUt(VA[HV]).buildAndRegister();
    }

    private static void Enzymesbd() {
        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesbb.getFluid(10))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidInputs(Acetylene.getFluid(1000))
                .fluidOutputs(Ethylene.getFluid(1000))
                .rate(20)
                .duration(400).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesbb.getFluid(10))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidInputs(Ethylene.getFluid(1000))
                .fluidOutputs(Ethane.getFluid(1000))
                .rate(20)
                .duration(400).EUt(VA[HV]).buildAndRegister();
    }

    private static void Enzymesbb() {
        //还原
        //炔-》烯
        //烯-》烷
        //烯->醇
        //烯-》醛
        //醛-》醇
        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesbb.getFluid(10))
                .fluidInputs(Formaldehyde.getFluid(1000))
                .fluidOutputs(Methanol.getFluid(1000))
                .rate(20)
                .duration(400).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesbb.getFluid(10))
                .fluidInputs(FormicAcid.getFluid(1000))
                .fluidOutputs(Formaldehyde.getFluid(1000))
                .rate(20)
                .duration(400).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesbb.getFluid(10))
                .fluidInputs(Acetaldehyde.getFluid(1000))
                .fluidOutputs(Ethanol.getFluid(1000))
                .rate(20)
                .duration(400).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesbb.getFluid(10))
                .fluidInputs(AceticAcid.getFluid(1000))
                .fluidOutputs(Acetaldehyde.getFluid(1000))
                .rate(20)
                .duration(400).EUt(VA[HV]).buildAndRegister();

    }

    private static void Enzymesbc() {
        //氧化
        //醇-》醛
        //醛-》酸
        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesbc.getFluid(10))
                .fluidInputs(Methanol.getFluid(1000))
                .fluidOutputs(Formaldehyde.getFluid(1000))
                .rate(20)
                .duration(400).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesbc.getFluid(10))
                .fluidInputs(Formaldehyde.getFluid(1000))
                .fluidOutputs(FormicAcid.getFluid(1000))
                .rate(20)
                .duration(400).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesbc.getFluid(10))
                .fluidInputs(Ethanol.getFluid(1000))
                .fluidOutputs(Acetaldehyde.getFluid(1000))
                .rate(20)
                .duration(400).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesbc.getFluid(10))
                .fluidInputs(Ethylene.getFluid(1000))
                .fluidOutputs(Acetaldehyde.getFluid(1000))
                .rate(20)
                .duration(400).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesbc.getFluid(10))
                .fluidInputs(Acetaldehyde.getFluid(1000))
                .fluidOutputs(AceticAcid.getFluid(1000))
                .rate(20)
                .duration(400).EUt(VA[HV]).buildAndRegister();

    }

    private static void Enzymesba() {
        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesba.getFluid(10))
                .input(dust,Carbon,2)
                .fluidInputs(Hydrogen.getFluid(4000))
                .fluidOutputs(Polyethylene.getFluid(1000))
                .circuitMeta(1)
                .rate(20)
                .duration(400).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesba.getFluid(10))
                .input(dust,Carbon,2)
                .fluidInputs(Fluorine.getFluid(4000))
                .fluidOutputs(Polytetrafluoroethylene.getFluid(1000))
                .circuitMeta(1)
                .rate(20)
                .duration(400).EUt(VA[HV]).buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(Enzymesba.getFluid(10))
                .input(dust,Carbon,6)
                .fluidInputs(Hydrogen.getFluid(4000))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(Epoxy.getFluid(1000))
                .circuitMeta(2)
                .rate(20)
                .duration(400).EUt(VA[EV]).buildAndRegister();
    }

    private static void SpecialOut(Material material1,int origin, OrePrefix orePrefix1,Material material2,int input, OrePrefix orePrefix2, Material material3,int output, int tier, int rate) {
        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(material1.getFluid(origin))
                .input(orePrefix1,material2,input)
                .output(orePrefix2,material3,output)
                .rate(rate)
                .duration(100*tier).EUt(VA[MV+tier]).buildAndRegister();
    }
    private static void SpecialOutCircuit(Material material1,int origin,int circuit, OrePrefix orePrefix1,Material material2,int input, OrePrefix orePrefix2, Material material3,int output, int tier, int rate) {
        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(material1.getFluid(origin))
                .input(orePrefix1,material2,input)
                .output(orePrefix2,material3,output)
                .rate(rate)
                .circuitMeta(circuit)
                .duration(100*tier).EUt(VA[MV+tier]).buildAndRegister();
    }

    public static MetaItem<?>.MetaValueItem[]DISK={BIO_0,BIO_1,BIO_2,BIO_3,BIO_4,BIO_5,BIO_6,BIO_7,BIO_8,BIO_9,BIO_10,BIO_11,BIO_12};


    public static void enzymesmix(Material material1,Material material2, int rate, int tier,int num)
    {
        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .Tier(2)
                .KI(12)
                .input(BIO_0)
                .notConsumable(material1.getFluid(1000))
                .output(DISK[num])
                .EUt(VA[LuV])
                .CWUt(CWT[LuV])
                .duration(20000)
                .NB(num)
                .buildAndRegister();

        GENE_MUTAGENESIS.recipeBuilder()
                .notConsumable(DISK[num])
                .fluidInputs(Enzymesa.getFluid(1000))
                .fluidInputs(Enzymesb.getFluid(1000))
                .fluidInputs(Enzymesc.getFluid(1000))
                .fluidInputs(Enzymesd.getFluid(1000))
                .fluidInputs(Enzymese.getFluid(1000))
                .fluidInputs(Rnzymes.getFluid(500))
                .fluidOutputs(material1.getFluid(5000))
                .EUt(VA[HV+tier])
                .duration(960*tier)
                .rate(tier*20)
                .buildAndRegister();
        //自交
        ENZYMES_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(material1.getFluid(10))
                .fluidOutputs(material1.getFluid(20))
                .EUt(VA[HV+tier])
                .duration(960*tier)
                .circuitMeta(2)
                .rate(rate)
                .buildAndRegister();
        //杂交
        ENZYMES_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(material2.getFluid(1000*tier))
                .fluidInputs(EnrichedBacterialSludge.getFluid(100*tier))
                .fluidOutputs(material1.getFluid(10))
                .EUt(VA[MV+tier])
                .duration(120*tier)
                .circuitMeta(1)
                .rate(rate)
                .buildAndRegister();

        ENZYMES_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(material2.getFluid(1000*tier))
                .fluidInputs(RawGrowthMedium.getFluid(25*tier))
                .fluidOutputs(material1.getFluid(10))
                .EUt(VA[MV+tier])
                .duration(120*tier)
                .circuitMeta(1)
                .rate(rate)
                .buildAndRegister();
    }
}
