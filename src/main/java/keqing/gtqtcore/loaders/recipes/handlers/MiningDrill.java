package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Coal;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.GRAVITY_SEPARATOR_RECIPES;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.MINING_DRILL_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.BET4;
import static net.minecraft.init.Blocks.SAND;

public class MiningDrill {
    public static void init() {
        //MiningDrillR();
        MiningDrill(BLANK,1);
        MiningDrill(HYDROTHERMAL,2);
        MiningDrill(MAGMATIC_HYDROTHERMAL,3);
        MiningDrill(ALLUVIAL,4);

        MiningDrill(DIAPHRAGMATIC,-3);
        MiningDrill(ORTHOSTATIC,-2);
        MiningDrill(METAMORPHIC,-1);

        MiningDrill(END1,5);
        MiningDrill(END2,6);
        MiningDrill(END3,7);
        MiningDrill(END4,8);

        MiningDrill(BET1,81);
        MiningDrill(BET2,82);
        MiningDrill(BET3,83);
        MiningDrill(BET4,84);

        GRAVITY_SEPARATOR(BLANK,Copper,Tin,Iron,Coal,Lead,Oilsands);
        GRAVITY_SEPARATOR(HYDROTHERMAL,Apatite,Realgar,Trona,Nickel,Graphite,Calcite);
        GRAVITY_SEPARATOR(MAGMATIC_HYDROTHERMAL,Mica,RockSalt,Ruby,Lapis,Sapphire,Amethyst);
        GRAVITY_SEPARATOR(ALLUVIAL,Opal,Redstone,Cinnabar,GarnetSand,Bauxite,Magnetite);

        GRAVITY_SEPARATOR(DIAPHRAGMATIC,Pyrolusite,Lepidolite,VanadiumMagnetite,Cryolite,Electrotine,Emerald);
        GRAVITY_SEPARATOR(ORTHOSTATIC,CertusQuartz,NetherQuartz,Beryllium,Molybdenum,Barite,Chalcocite);
        GRAVITY_SEPARATOR(METAMORPHIC,Stibnite,Sphalerite,Molybdenite,Sulfur,Pyrite,Crocoite);

        GRAVITY_SEPARATOR(END1,Magnetite,Gold,Bauxite,Beryllium,Emerald,Thorium);
        GRAVITY_SEPARATOR(END2,Chalcopyrite,Iron,Pyrite,Copper,BrownLimonite,YellowLimonite);
        GRAVITY_SEPARATOR(END3,BandedIron,Grossular,Pyrolusite,Pyrochlore,Tantalite,Wulfenite);
        GRAVITY_SEPARATOR(END4,Molybdenite,Molybdenum,Powellite,Diamond,Sphalerite,Tin);

        GRAVITY_SEPARATOR(BET1,Amblygonite,Crocoite,Coal,Lignite,Diamond,Chalcopyrite);
        GRAVITY_SEPARATOR(BET2,Copper,Iron,Lead,Nickel,Cryolite,Bauxite);
        GRAVITY_SEPARATOR(BET3,Ulexite,Salt,RockSalt,Almandine,Sapphire,Redstone);
        GRAVITY_SEPARATOR(BET4,Ruby,Silver,Bentonite,Olivine,Spessartine,Soapstone);

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Steel,16)
                .input(stick, Steel,2)
                .input(ring, Steel, 4)
                .input(BATTERY_LV_LITHIUM,1)
                .input(circuit, MarkerMaterials.Tier.MV, 1)
                .input(SENSOR_LV,1)
                .output(POS_ORE_CARD)
                .circuitMeta(2)
                .duration(600).EUt(30).buildAndRegister();
    }
    private static void GRAVITY_SEPARATOR(MetaItem.MetaValueItem Item, Material material1, Material material2, Material material3, Material material4, Material material5, Material material6)
    {
        for(int i=-4;i<6;i++) {
            GRAVITY_SEPARATOR_RECIPES.recipeBuilder()
                    .input(Item)
                    .fluidInputs(Water.getFluid(1000))
                    .fluidInputs(Lubricant.getFluid(100))
                    .chancedOutput(crushed, material1, 3000+500*Math.abs(5-i), 2000+200*Math.abs(-i))
                    .chancedOutput(crushed, material2, 3000+500*Math.abs(4-i), 2000+200*Math.abs(1-i))
                    .chancedOutput(crushed, material3, 3000+500*Math.abs(3-i), 2000+200*Math.abs(2-i))
                    .chancedOutput(crushed, material4, 3000+500*Math.abs(2-i), 2000+200*Math.abs(3-i))
                    .chancedOutput(crushed, material5, 3000+500*Math.abs(1-i), 2000+200*Math.abs(4-i))
                    .chancedOutput(crushed, material6, 3000+500*Math.abs(-i), 2000+200*Math.abs(5-i))
                    .EUt(120)
                    .duration(200)
                    .circuitMeta(i+5)
                    .buildAndRegister();
        }
    }
    private static void MiningDrill(MetaItem.MetaValueItem Item,int kind) {
        for(int i=1;i<=10;i++) {
            MINING_DRILL_RECIPES.recipeBuilder()
                    .notConsumable(Item)
                    .chancedOutput(Item, 5500-(500*i), 500*i)
                    .chancedOutput(Item, 5500-(500*i), 500*i)
                    .chancedOutput(Item, 5500-(500*i), 500*i)
                    .chancedOutput(Item, 5500-(500*i), 500*i)
                    .chancedOutput(Item, 5500-(500*i), 500*i)
                    .chancedOutput(Item, 5500-(500*i), 500*i)
                    .chancedOutput(Item, 5500-(500*i), 500*i)
                    .chancedOutput(Item, 5500-(500*i), 500*i)
                    .chancedOutput(Item, 5500-(500*i), 500*i)
                    .tier(kind)
                    .EUt(30)
                    .duration((int) (200*Math.pow(2,i)))
                    .circuitMeta(i)
                    .buildAndRegister();
        }
    }

}
