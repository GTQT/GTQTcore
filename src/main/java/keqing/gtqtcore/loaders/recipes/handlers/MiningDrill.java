package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTElectrobath;
import keqing.gtqtcore.loaders.recipes.GTQTRecipes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.L;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Coal;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.GRAVITY_SEPARATOR_RECIPES;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.MINING_DRILL_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.BET4;
import static net.minecraft.init.Blocks.SAND;

public class MiningDrill {
    public static void init() {
        //钻头
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[1].getStackForm())
                .input(ELECTRIC_MOTOR_LV, 4)
                .input(plate, Steel, 8)
                .input(frameGt, Steel, 8)
                .fluidInputs(Polyethylene.getFluid(L * 4))
                .circuitMeta(5)
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.DRILL_HEAD_LV))
                .duration(2000).EUt(30).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm())
                .input(ELECTRIC_MOTOR_MV, 4)
                .input(plate, Aluminium, 8)
                .input(frameGt, Aluminium, 8)
                .fluidInputs(PolyvinylChloride.getFluid(L * 4))
                .circuitMeta(5)
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.DRILL_HEAD_MV))
                .duration(200).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[3].getStackForm())
                .input(ELECTRIC_MOTOR_HV, 4)
                .input(plate, StainlessSteel, 8)
                .input(frameGt, StainlessSteel, 8)
                .fluidInputs(Epoxy.getFluid(L * 4))
                .circuitMeta(5)
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.DRILL_HEAD_HV))
                .duration(200).EUt(480).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[4].getStackForm())
                .input(ELECTRIC_MOTOR_EV, 4)
                .input(plate, Titanium, 8)
                .input(frameGt, Titanium, 8)
                .fluidInputs(ReinforcedEpoxyResin.getFluid(L * 4))
                .circuitMeta(5)
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.DRILL_HEAD_EV))
                .duration(200).EUt(1920).buildAndRegister();

        //钻头
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(dust,Diamond,8)
                .input(toolHeadDrill,Steel,16)
                .input(stick,Steel,4)
                .input(ELECTRIC_PUMP_LV,4)
                .output(DRILL_HEAD_STEEL)
                .fluidInputs(Polyethylene.getFluid(1440))
                .duration(200).EUt(30).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(dust,Diamond,8)
                .input(toolHeadDrill,Aluminium,16)
                .input(stick,Aluminium,4)
                .input(ELECTRIC_PUMP_MV,4)
                .output(DRILL_HEAD_ALUMINIUM)
                .fluidInputs(Polyethylene.getFluid(1440))
                .duration(200).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(dust,Diamond,8)
                .input(toolHeadDrill,StainlessSteel,16)
                .input(stick,StainlessSteel,4)
                .input(ELECTRIC_PUMP_HV,4)
                .output(DRILL_HEAD_STAINLESSSTEEL)
                .fluidInputs(Polyethylene.getFluid(1440))
                .duration(200).EUt(480).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(dust,Diamond,8)
                .input(toolHeadDrill,Titanium,16)
                .input(stick,Titanium,4)
                .input(ELECTRIC_PUMP_EV,4)
                .output(DRILL_HEAD_TITANIUM)
                .fluidInputs(Polyethylene.getFluid(1440))
                .duration(200).EUt(1960).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(dust,Diamond,8)
                .input(toolHeadDrill,TungstenSteel,16)
                .input(stick,TungstenSteel,4)
                .input(ELECTRIC_PUMP_IV,4)
                .output(DRILL_HEAD_TUNGSTENSTEEL)
                .fluidInputs(Polyethylene.getFluid(1440))
                .duration(200).EUt(7680).buildAndRegister();

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
        SIFTER_RECIPES.recipeBuilder()
                .input(Item)
                .chancedOutput(crushed, material1, 2000,500)
                .chancedOutput(crushed, material2, 2000,500)
                .chancedOutput(crushed, material3, 2000,500)
                .chancedOutput(crushed, material4, 2000,500)
                .chancedOutput(crushed, material5, 2000,500)
                .chancedOutput(crushed, material6, 2000,500)
                .EUt(120)
                .duration(400)
                .buildAndRegister();

        for(int i=-4;i<6;i++) {
            GRAVITY_SEPARATOR_RECIPES.recipeBuilder()
                    .input(Item)
                    .fluidInputs(Water.getFluid(1000))
                    .fluidInputs(Lubricant.getFluid(10))
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
