package keqing.gtqtcore.loaders.recipes.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Coal;
import static gregtech.api.unification.ore.OrePrefix.ore;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.MINING_DRILL_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static net.minecraft.init.Blocks.SAND;

public class MiningDrill {
    public static void init() {
        MiningDrillR();

    }

    private static void MiningDrillR() {
        //泥土	50	沙子	50	砂砾	50	黏土	50
        MINING_DRILL_RECIPES.recipeBuilder()
                .chancedOutput(new ItemStack(Blocks.SAND),5000,500)
                .chancedOutput(new ItemStack(Blocks.GRAVEL),5000,500)
                .chancedOutput(new ItemStack(Blocks.CLAY),5000,500)
                .chancedOutput(new ItemStack(Blocks.DIRT),5000,500)
                .tier(1)
                .EUt(30)
                .duration(40)
                .circuitMeta(1)
                .buildAndRegister();
        //煤	50	铜	50	铁	50	锡	50	油砂	10
        MINING_DRILL_RECIPES.recipeBuilder()
                .chancedOutput(ore,Copper,5000,500)
                .chancedOutput(ore,Iron,5000,500)
                .chancedOutput(ore,Tin,5000,500)
                .chancedOutput(ore,Coal,5000,500)
                .chancedOutput(ore,Oilsands,1000,500)
                .tier(1)
                .EUt(120)
                .duration(40)
                .circuitMeta(2)
                .buildAndRegister();
        //磷灰石	20	雄黄	10	天然碱 20	石膏	20	石墨	20	方解石	20	云母	20	岩盐	20
        MINING_DRILL_RECIPES.recipeBuilder()
                .chancedOutput(ore,Apatite,2000,200)
                .chancedOutput(ore,Realgar,1000,100)
                .chancedOutput(ore,Trona,2000,200)
                .chancedOutput(ore,Gypsum,2000,200)
                .chancedOutput(ore,Graphite,2000,200)
                .chancedOutput(ore,Calcite,2000,200)
                .chancedOutput(ore,Mica,2000,200)
                .chancedOutput(ore,RockSalt,2000,200)
                .tier(1)
                .EUt(120)
                .duration(40)
                .circuitMeta(3)
                .buildAndRegister();
        //红宝石	10	青金石	10	蓝宝石	10	紫水晶	10	猫眼石	10	红石	20	朱砂	10
        MINING_DRILL_RECIPES.recipeBuilder()
                .chancedOutput(ore,Ruby,1000,100)
                .chancedOutput(ore,Lapis,1000,100)
                .chancedOutput(ore,Sapphire,1000,100)
                .chancedOutput(ore,Amethyst,1000,100)
                .chancedOutput(ore,Opal,1000,100)
                .chancedOutput(ore,Redstone,2000,200)
                .chancedOutput(ore,Cinnabar,1000,100)
                .tier(1)
                .EUt(120)
                .duration(40)
                .circuitMeta(4)
                .buildAndRegister();
        //石榴石矿砂	50	铝土	20	磁铁	20	软锰	20	锂云母	20	钒磁铁	20	盐	20
        MINING_DRILL_RECIPES.recipeBuilder()
                .chancedOutput(ore,GarnetSand,5000,500)
                .chancedOutput(ore,Bauxite,2000,200)
                .chancedOutput(ore,Magnetite,2000,200)
                .chancedOutput(ore,Pyrolusite,2000,200)
                .chancedOutput(ore,Lepidolite,2000,200)
                .chancedOutput(ore,VanadiumMagnetite,2000,200)
                .chancedOutput(ore,Salt,2000,200)
                .tier(1)
                .EUt(120)
                .duration(40)
                .circuitMeta(5)
                .buildAndRegister();
        //蓝石	20	绿宝石	10	赛特斯	20	下界石英	20	铍	10	钍	10	重晶石	20
        MINING_DRILL_RECIPES.recipeBuilder()
                .chancedOutput(ore,Electrotine,2000,200)
                .chancedOutput(ore,Emerald,1000,100)
                .chancedOutput(ore,CertusQuartz,2000,200)
                .chancedOutput(ore,NetherQuartz,2000,200)
                .chancedOutput(ore,Beryllium,1000,100)
                .chancedOutput(ore,Thorium,1000,100)
                .chancedOutput(ore,Barite,2000,200)
                .tier(2)
                .EUt(120)
                .duration(40)
                .circuitMeta(6)
                .buildAndRegister();
        //辉铜	20	辉锑	20	闪锌	20	辉钼	10	硫	20	黄铁	20
        MINING_DRILL_RECIPES.recipeBuilder()
                .chancedOutput(ore,Chalcocite,2000,200)
                .chancedOutput(ore,Stibnite,2000,200)
                .chancedOutput(ore,Sphalerite,2000,200)
                .chancedOutput(ore,Molybdenite,1000,100)
                .chancedOutput(ore,Sulfur,2000,200)
                .chancedOutput(ore,Pyrite,2000,200)
                .tier(2)
                .EUt(120)
                .duration(40)
                .circuitMeta(7)
                .buildAndRegister();
        //铬铅	20	锌锑方辉银	20	冰晶石	20		红宝石	20	镍	20
        MINING_DRILL_RECIPES.recipeBuilder()
                .chancedOutput(ore,Crocoite,2000,200)
                .chancedOutput(ore,Zincantimonygalvanite,2000,200)
                .chancedOutput(ore,Cryolite,2000,200)
                .chancedOutput(ore,Ruby,2000,200)
                .chancedOutput(ore,Nickel,2000,200)
                .tier(3)
                .EUt(480)
                .duration(40)
                .circuitMeta(8)
                .buildAndRegister();
        //磁铁	20	铝土	20	硅镁镍	20	方铅	20	红石	20
        MINING_DRILL_RECIPES.recipeBuilder()
                .chancedOutput(ore,Magnetite,2000,200)
                .chancedOutput(ore,Bauxite,2000,200)
                .chancedOutput(ore,Garnierite,2000,200)
                .chancedOutput(ore,Galena,2000,200)
                .chancedOutput(ore,Redstone,2000,200)
                .tier(3)
                .EUt(480)
                .duration(40)
                .circuitMeta(9)
                .buildAndRegister();
        //钛铁	20	钼	20	铝土	20	钽铁	20	金	20
        MINING_DRILL_RECIPES.recipeBuilder()
                .chancedOutput(ore,Ilmenite,2000,200)
                .chancedOutput(ore,Molybdenum,2000,200)
                .chancedOutput(ore,Bauxite,2000,200)
                .chancedOutput(ore,Tantalite,2000,200)
                .chancedOutput(ore,Gold,2000,200)
                .tier(4)
                .EUt(480)
                .duration(40)
                .circuitMeta(10)
                .buildAndRegister();


    }
}
