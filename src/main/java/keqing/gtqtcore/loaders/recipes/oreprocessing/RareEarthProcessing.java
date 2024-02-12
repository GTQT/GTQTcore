package keqing.gtqtcore.loaders.recipes.oreprocessing;



import static gregicality.science.api.recipes.GCYSRecipeMaps.DRYER_RECIPES;
import static gregtech.api.unification.material.Materials.Hafnium;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.SodiumFluoride;
import static keqing.gtqtcore.common.items.GTQTMetaItems.PROTONATED_FULLERENE_SIEVING_MATRIX;


public class RareEarthProcessing {

    public static void init() {
        EP();
        lite();
        bastnasite();
        monazite();
        HF();
        CE();
        caosuan();
        fuguisuanna();
        p507();

    }

    private static void EP() {
        //  Rare Earth -> Crude Rare Earth Turbid Solution
        MIXER_RECIPES.recipeBuilder()
                .input(dust, RareEarth)
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidOutputs(CrudeRareEarthTurbidSolution.getFluid(2000))
                .EUt(VA[HV])
                .duration(120)
                .buildAndRegister();

        //  Crude Rare Earth Turbid Solution + Nitric Acid -> Nitrated Rare Earth Turbid Solution
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(CrudeRareEarthTurbidSolution.getFluid(2000))
                .fluidInputs(NitricAcid.getFluid(2000))
                .fluidOutputs(NitratedRareEarthTurbidSolution.getFluid(2000))
                .EUt(VA[EV])
                .duration(350)
                .buildAndRegister();

        //  Sodium Hydroxide + Di-(2-ethylhexyl)phosphoric Acid + Nitrated Rare Earth Turbid Solution -> Sodium Nitrate + Rare Earth Hydroxides Solution
        DIGESTER_RECIPES.recipeBuilder()
                .input(dust, SodiumHydroxide, 3)
                .fluidInputs(DiethylhexylPhosphoricAcid.getFluid(1000))
                .fluidInputs(NitratedRareEarthTurbidSolution.getFluid(2000))
                .output(dust, SodiumNitrate, 3)
                .fluidOutputs(RareEarthHydroxidesSolution.getFluid(1000))
                .duration(240)
                .EUt(VA[EV])
                .buildAndRegister();

        //  Rare Earth Hydroxides Solution + Hydrochloric Acid -> Rare Earth Chorides Slurry + Steam
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(RareEarthHydroxidesSolution.getFluid(1000))
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .output(dust, RareEarthChloridesSlurry, 4)
                .fluidOutputs(Steam.getFluid(600))
                .temperature(1125)
                .duration(120)
                .EUt(VA[MV])
                .buildAndRegister();

        //  Rare Earth Chorides Slurry (MV)
        ROASTER_RECIPES.recipeBuilder()
                .input(dust, RareEarthChloridesSlurry, 4)
                .input(dust, SodiumBicarbonate, 8)
                .fluidInputs(DistilledWater.getFluid(1000))
                .output(dust, Sodium, 4)
                .fluidOutputs(LowPurityRareEarthChloridesSolution.getFluid(1000))
                .fluidOutputs(CarbonDioxide.getFluid(2000))
                .temperature(980)
                .EUt(VA[MV])
                .duration(1200)
                .buildAndRegister();

        //  Rare Earth Chorides Slurry (HV)
        ROASTER_RECIPES.recipeBuilder()
                .input(dust, RareEarthChloridesSlurry, 2)
                .input(dust, BariumCarbonate, 4)
                .fluidInputs(DistilledWater.getFluid(500))
                .output(dust, Barium, 2)
                .fluidOutputs(LowPurityRareEarthChloridesSolution.getFluid(2000))
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .temperature(980)
                .EUt(VA[HV])
                .duration(600)
                .buildAndRegister();

        //  Low Purity Rare Earth Chlorides Solution + AquaRegia -> Roughly Purified Rare Earth Chlorides Solution
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(LowPurityRareEarthChloridesSolution.getFluid(4000))
                .fluidInputs(AquaRegia.getFluid(2000))
                .fluidOutputs(RoughlyPurifiedRareEarthChloridesSolution.getFluid(6000))
                .EUt(VA[EV])
                .duration(1200)
                .buildAndRegister();

        //  Roughly Purified Rare Earth Chlorides Solution -> High Purity Rare Earth Chlorides Slurry + Diluted Hydrochloric Acid
        DRYER_RECIPES.recipeBuilder()
                .fluidInputs(RoughlyPurifiedRareEarthChloridesSolution.getFluid(6000))
                .output(dust, HighPurityRareEarthChloridesSlurry, 3)
                .output(dust, LowPurityRareEarthChloridesSlag, 2)
                .fluidOutputs(DilutedHydrochloricAcid.getFluid(1000))
                .EUt(VA[HV])
                .duration(300)
                .buildAndRegister();

        //  High Purity Rare Earth Chlorides Slurry + Hydrochloric Acid -> High Purity Rare Earth Chlorides Solution
        DISSOLUTION_TANK_RECIPES.recipeBuilder()
                .input(dust, HighPurityRareEarthChloridesSlurry)
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .fluidOutputs(HighPurityRareEarthChloridesSolution.getFluid(1000))
                .EUt(VA[EV])
                .duration(600)
                .buildAndRegister();

        //  High Purity Rare Earth Chlorides Solution -> Neodymium Oxide
        DISTILLERY_RECIPES.recipeBuilder()
                .fluidInputs(HighPurityRareEarthChloridesSolution.getFluid(1000))
                .circuitMeta(1)
                .output(dust, NeodymiumOxide)
                .fluidOutputs(DilutedHydrochloricAcid.getFluid(500))
                .duration(200)
                .EUt(VA[HV])
                .buildAndRegister();

        //  High Purity Rare Earth Chlorides Solution -> Cerium Oxide
        DISTILLERY_RECIPES.recipeBuilder()
                .fluidInputs(HighPurityRareEarthChloridesSolution.getFluid(1000))
                .circuitMeta(2)
                .output(dust, CeriumOxide)
                .fluidOutputs(DilutedHydrochloricAcid.getFluid(300))
                .duration(200)
                .EUt(VA[IV])
                .buildAndRegister();

        //  High Purity Rare Earth Chlorides Solution -> Samarium Oxide
        DISTILLERY_RECIPES.recipeBuilder()
                .fluidInputs(HighPurityRareEarthChloridesSolution.getFluid(1000))
                .circuitMeta(3)
                .output(dust, SamariumOxide)
                .fluidOutputs(HydrochloricAcid.getFluid(800))
                .duration(200)
                .EUt(VA[EV])
                .buildAndRegister();

        //  Low Purity Rare Earth Chlorides Slag + Cobaltite -> Yttrium Oxide, Lanthanum Oxide
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, LowPurityRareEarthChloridesSlag, 4)
                .input(dust, Cobaltite, 2)
                .output(dust, YttriumOxide)
                .output(dust, Sulfur, 2)
                .EUt(3840)
                .duration(300)
                .temperature(450)
                .buildAndRegister();

        //  Low Purity Rare Earth Chlorides Slag + Ferric Oxide -> Lanthanum Oxide + Oxygen
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, LowPurityRareEarthChloridesSlag, 4)
                .input(dust, FerricOxide, 2)
                .output(dust, LanthanumOxide)
                .fluidOutputs(Oxygen.getFluid(3000))
                .duration(300)
                .EUt(15360)
                .temperature(860)
                .buildAndRegister();
    }

    private static void lite() {
        DIGESTER_RECIPES.recipeBuilder()
                .input(dust, RareEarth)
                .fluidInputs(DiethylhexylPhosphoricAcid.getFluid(100))
                .fluidInputs(Water.getFluid(900))
                .fluidOutputs(RareEarthHydroxidesSolution.getFluid(1000))
                .duration(120).EUt(VA[IV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(RareEarthHydroxidesSolution.getFluid(1000))
                .notConsumable(PROTONATED_FULLERENE_SIEVING_MATRIX)
                .fluidOutputs(RareEarthChloridesSolution.getFluid(1000))
                .duration(120).EUt(VA[UV]).buildAndRegister();

        MOLECULAR_DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(RareEarthChloridesSolution.getFluid(1000))
                .output(dustSmall, Thorium)
                .fluidOutputs(LaPrNdCeOxidesSolution.getFluid(250))
                .fluidOutputs(ScEuGdSmOxidesSolution.getFluid(250))
                .fluidOutputs(YTbDyHoOxidesSolution.getFluid(250))
                .fluidOutputs(ErTmYbLuOxidesSolution.getFluid(250))
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .duration(200).EUt(VA[UV]).buildAndRegister();
    }

    private static void bastnasite() {

        DIGESTER_RECIPES.recipeBuilder()
                .fluidInputs(NitricAcid.getFluid(700))
                .input(crushed,Bastnasite,1)
                .fluidOutputs(Futanlanshixituzhuo.getFluid(400))
                .output(dust,SiliconDioxide)
                .duration(600).EUt(120).buildAndRegister();

        DIGESTER_RECIPES.recipeBuilder()
                .fluidInputs(NitricAcid.getFluid(700))
                .input(dust,Bastnasite,2)
                .fluidOutputs(Futanlanshixituzhuo.getFluid(400))
                .output(dust,SiliconDioxide)
                .duration(600).EUt(120).buildAndRegister();

        DIGESTER_RECIPES.recipeBuilder()
                .fluidInputs(NitricAcid.getFluid(700))
                .input(crushed,Bastnasite,1)
                .input(dust,RareEarth,1)
                .fluidOutputs(Futanlanshixituzhuo.getFluid(1200))
                .output(dust,SiliconDioxide)
                .duration(600).EUt(120).buildAndRegister();

        DIGESTER_RECIPES.recipeBuilder()
                .fluidInputs(NitricAcid.getFluid(700))
                .input(dust,Bastnasite,2)
                .input(dust,RareEarth,1)
                .fluidOutputs(Futanlanshixituzhuo.getFluid(1200))
                .output(dust,SiliconDioxide)
                .duration(600).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
                .fluidInputs(Futanlanshixituzhuo.getFluid(1000))
                .fluidInputs(Steam.getFluid(1000))
                .fluidOutputs(Zhengqiliehuafutanlanshinijiang.getFluid(1000))
                .duration(600).EUt(120).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(Zhengqiliehuafutanlanshinijiang.getFluid(1000))
                .fluidInputs(SodiumSilicofluoride.getFluid(320))
                .fluidOutputs(Tiaozhifutanlanshinijiang.getFluid(1320))
                .duration(600).EUt(120).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(Tiaozhifutanlanshinijiang.getFluid(1000))
                .output(dust,Guolvfutanlanshinijiang)
                .chancedOutput(dust,SodiumNitrate,8000,0)
                .chancedOutput(dust,Rutile,2000,0)
                .chancedOutput(dust,Ilmenite,2000,0)
                .duration(600).EUt(120).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust,Guolvfutanlanshinijiang)
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust,Futanlanshixituyanghuawu,1)
                .fluidOutputs(Fluorine.getFluid(36))
                .blastFurnaceTemp(3500)
                .duration(600).EUt(120).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Futanlanshixituyanghuawu)
                .fluidInputs(NitricAcid.getFluid(1000))
                .fluidOutputs(Suanjinfutanlanshixituyanghuawu.getFluid(1000))
                .fluidOutputs(NitricAcid.getFluid(500))
                .duration(600).EUt(120).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust,Calcite)
                .fluidInputs(Suanjinfutanlanshixituyanghuawu.getFluid(1000))
                .fluidOutputs(NitricAcid.getFluid(500))
                .output(dust,Bsxituyanghuawu,1)
                .output(dust,Calcium)
                .blastFurnaceTemp(3500)
                .duration(600).EUt(120).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Bsxituyanghuawu)
                .fluidInputs(Fluorine.getFluid(4000))
                .fluidOutputs(Yanghuaxituyanghauwu.getFluid(1000))
                .fluidOutputs(HydrofluoricAcid.getFluid(4000))
                .duration(600).EUt(120).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(Yanghuaxituyanghauwu.getFluid(4000))
                .fluidOutputs(NitricAcid.getFluid(200))
                .output(dust,Futanlanshihantuyanghuawu)
                .output(dust,CeriumOxide)
                .duration(600).EUt(120).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Futanlanshihantuyanghuawu)
                .fluidInputs(Acetone.getFluid(1000))
                .fluidOutputs(Futanlanshihantuyanghuawuxuanzhuo.getFluid(1000))
                .duration(600).EUt(120).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(Futanlanshihantuyanghuawuxuanzhuo.getFluid(4000))
                .output(dust,Shanxitujingfen)
                .output(dust,LanthanumOxide)
                .output(dust,NeodymiumOxide)
                .fluidOutputs(Acetone.getFluid(4000))
                .duration(600).EUt(120).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Shanxitujingfen)
                .fluidInputs(HydrofluoricAcid.getFluid(2000))
                .fluidOutputs(Fhanxitujingfen.getFluid(2000))
                .duration(600).EUt(120).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust,Sodium,2)
                .fluidInputs(Fhanxitujingfen.getFluid(2000))
                .fluidOutputs(Shantui.getFluid(2000))
                .output(dust,HolmiumOxide)
                .output(dust,SodiumFluoride,4)
                .duration(600).EUt(120).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(Shantui.getFluid(3000))
                .output(dust,TerbiumOxide)
                .output(dust,GadoliniumOxide)
                .output(dust,Shanjing)
                .duration(600).EUt(120).buildAndRegister();
    }





    private static void monazite() {
        //快乐独居石处理
        DIGESTER_RECIPES.recipeBuilder()
                .fluidInputs(NitricAcid.getFluid(700))
                .input(crushed,Monazite,1)
                .fluidOutputs(Dujushixitu.getFluid(400))
                .output(dust,SiliconDioxide)
                .duration(600).EUt(120).buildAndRegister();

        DIGESTER_RECIPES.recipeBuilder()
                .fluidInputs(NitricAcid.getFluid(700))
                .input(dust,Monazite,2)
                .fluidOutputs(Dujushixitu.getFluid(400))
                .output(dust,SiliconDioxide)
                .duration(600).EUt(120).buildAndRegister();

        DIGESTER_RECIPES.recipeBuilder()
                .fluidInputs(NitricAcid.getFluid(700))
                .input(crushed,Monazite,1)
                .input(dust,RareEarth,1)
                .fluidOutputs(Dujushixitu.getFluid(1200))
                .output(dust,SiliconDioxide)
                .duration(600).EUt(120).buildAndRegister();

        DIGESTER_RECIPES.recipeBuilder()
                .fluidInputs(NitricAcid.getFluid(700))
                .input(dust,Monazite,2)
                .input(dust,RareEarth,1)
                .fluidOutputs(Dujushixitu.getFluid(1200))
                .output(dust,SiliconDioxide)
                .duration(600).EUt(120).buildAndRegister();

        DISSOLUTION_TANK_RECIPES.recipeBuilder()
                .fluidInputs(Dujushixitu.getFluid(9000))
                .fluidInputs(Water.getFluid(90000))
                .input(dust,Saltpeter,9)
                .fluidOutputs(Xishidujushixitu.getFluid(99000))
                .output(dust,Yanghuahayanghuagao,4)
                .output(dust,Thorianite,9)
                .output(dust,Monazite,2)
                .duration(600).EUt(120).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(Xishidujushixitu.getFluid(1000))
                .fluidOutputs(Liusuandujushi.getFluid(1000))
                .output(dust,Rutile,3)
                .chancedOutput(dust,SodiumNitrate,8000,0)
                .chancedOutput(dust,Rutile,2000,0)
                .chancedOutput(dust,Ilmenite,2000,0)
                .duration(600).EUt(120).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(Liusuandujushi.getFluid(1000))
                .chancedOutput(dust,Dujushixitulvzha,9000,0)
                .chancedOutput(dust,Dujushixitulvb,9000,0)
                .duration(600).EUt(120).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,SodiumHydroxide)
                .fluidInputs(NitricAcid.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .output(dust,SodiumNitrate)
                .duration(600).EUt(120).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust,Dujushixitulvb,1)
                .output(dust,Dujushixitulvbf,1)
                .chancedOutput(dust,SodiumNitrate,8000,0)
                .blastFurnaceTemp(3500)
                .duration(600).EUt(120).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust,Dujushixitulvbf,1)
                .output(dust,Thorium)
                .output(dust,Phosphate)
                .duration(600).EUt(120).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Dujushixitulvzha)
                .fluidInputs(AmmoniumNitrate.getFluid(1000))
                .output(dust,Zhonghedujushixitulvzha)
                .duration(600).EUt(120).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust,Zhonghedujushixitulvzha)
                .output(dust,Nongsuodujushiqingyanghuawu)
                .output(dust,Youlvzhafen)
                .duration(600).EUt(120).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust,Youlvzhafen)
                .output(dust,Uranium235)
                .output(dust,Uranium238)
                .duration(600).EUt(120).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust,Nongsuodujushiqingyanghuawu,1)
                .output(dust,Ganzaonongsuoxiaosuandujushixitu,1)
                .chancedOutput(dust,SodiumNitrate,2000,0)
                .blastFurnaceTemp(3500)
                .duration(600).EUt(120).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust,Ganzaonongsuoxiaosuandujushixitu)
                .output(dust,Dujushihantuchendian)
                .output(dust,EuropiumOxide)
                .output(dust,CeriumOxide)
                .duration(600).EUt(120).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Dujushihantuchendian)
                .fluidInputs(Chlorine.getFluid(1000))
                .output(dust,Yizhiluhuadujushi)
                .duration(600).EUt(120).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Yizhiluhuadujushi)
                .fluidInputs(Acetone.getFluid(2000))
                .output(dust,Baohedujushixituhunhe,3)
                .duration(600).EUt(120).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust,Baohedujushixituhunhe)
                .output(dust,Shanjing)
                .fluidOutputs()
                .duration(600).EUt(120).buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust,Shanjing)
                .output(dust,SamariumOxide)
                .output(dust,GadoliniumOxide)
                .duration(600).EUt(120).buildAndRegister();
    }
    private static void HF() {
        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust,Yanghuahayanghuagao,1)
                .output(dust,Yanghuaha,3)
                .output(dust,CubicZirconia,1)
                .duration(600).EUt(120).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(HydrofluoricAcid.getFluid(4000))
                .input(dust,Yanghuaha,3)
                .fluidOutputs(Silvhuaha.getFluid(4000))
                .fluidOutputs(Water.getFluid(2000))
                .duration(600).EUt(120).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .fluidInputs(Silvhuaha.getFluid(1000))
                .input(dust,Magnesium,2)
                .output(dust,Dilvhuaha,1)
                .output(dust,MagnesiumChloride,2)
                .duration(600).EUt(120).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Iodine.getFluid(5000))
                .input(dust,Dilvhuaha,1)
                .fluidOutputs(Iilvhuaha.getFluid(5000))
                .duration(600).EUt(120).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .fluidInputs(Iilvhuaha.getFluid(10000))
                .output(dust,Hafnium,1)
                .output(dust,Zirconium,1)
                .fluidOutputs(Iodine.getFluid(10000))
                .blastFurnaceTemp(3500)
                .duration(600).EUt(120).buildAndRegister();
    }
    private static void fuguisuanna() {
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(HydrofluoricAcid.getFluid(6000))
                .input(dust,CSilicon,1)
                .fluidOutputs(Hydrogen.getFluid(4000))
                .fluidOutputs(HexafluorosilicicAcid.getFluid(1000))
                .duration(600).EUt(120).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(HexafluorosilicicAcid.getFluid(1000))
                .input(dust,Salt,4)
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .fluidOutputs(SodiumSilicofluoride.getFluid(1000))
                .duration(600).EUt(120).buildAndRegister();
    }

    private static void caosuan() {
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Oxygen.getFluid(5000))
                .input(dust,Vanadium,2)
                .output(dust,VanadiumOxide)
                .duration(600).EUt(120).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(NitricAcid.getFluid(1000))
                .input(dust,Sugar,24)
                .fluidOutputs(EthanedioicAcid.getFluid(3000))
                .fluidOutputs(NitricOxide.getFluid(6000))
                .notConsumable(dust,VanadiumOxide)
                .duration(600).EUt(120).buildAndRegister();
    }
    private static void CE() {
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,AmmoniumChloride,3)
                .fluidInputs(Hydrogen.getFluid(1000))
                .input(dust,CeriumOxide,3)
                .output(dust,Lvhuashi,4)
                .fluidOutputs(Ammonia.getFluid(3000))
                .fluidOutputs(Steam.getFluid(1000))
                .duration(600).EUt(120).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Lvhuashi,8)
                .fluidInputs(EthanedioicAcid.getFluid(5000))
                .output(dust,Cvhuashi,5)
                .fluidOutputs(HydrochloricAcid.getFluid(6000))
                .duration(600).EUt(120).buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust,Cvhuashi,5)
                .input(dust,Carbon,3)
                .output(dust,Tvhuashi,5)
                .fluidOutputs(CarbonMonoxide.getFluid(3000))
                .blastFurnaceTemp(3500)
                .duration(600).EUt(120).buildAndRegister();

        ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust,Tvhuashi,5)
                .output(dust,Cerium,2)
                .fluidOutputs(Oxygen.getFluid(3000))
                .duration(600).EUt(120).buildAndRegister();
    }
    private static void p507() {
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Ethanol.getFluid(2000))
                .notConsumable(dust, SodiumHydroxide, 1)
                .fluidOutputs(Crotonaldehyde.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .duration(600).EUt(16).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Crotonaldehyde.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(Butyraldehyde.getFluid(1000))
                .duration(600).EUt(16).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Butyraldehyde.getFluid(2000))
                .notConsumable(dust, SodiumHydroxide, 1)
                .fluidOutputs(Ethylhexenal.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .duration(600).EUt(16).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Ethylhexenal.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(Ethylhexanol.getFluid(1000))
                .duration(600).EUt(16).buildAndRegister();
        // 5C8H18O + 0.5P4O10 -> 2C16H35O4P + 2C4H10 + 2O (lost)
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Ethylhexanol.getFluid(5000))
                .input(dust, PhosphorusPentoxide, 7)
                .fluidOutputs(DiethylhexylPhosphoricAcid.getFluid(2000))
                .fluidOutputs(Butane.getFluid(2000))
                .duration(600).EUt(16).buildAndRegister();
    }
}
