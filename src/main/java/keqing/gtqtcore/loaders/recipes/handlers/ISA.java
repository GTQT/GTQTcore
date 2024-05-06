package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.OreProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.api.items.IntCircuitIngredientBiology;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.gem;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static net.minecraft.init.Blocks.*;

public class ISA {

    public static ItemStack getBiologyCircuitData(int configuration) {
        ItemStack stack = GTQTMetaItems.BIOLOGY_INTEGRATED_CIRCUIT.getStackForm();
        IntCircuitIngredientBiology.setCircuitConfiguration(stack, configuration);
        return stack;
    }

    public static void isaLine(Material materials1,Material materials2,Material materials3,Material materials4,Material materials5,Material materials6)
    {
        GTQTcoreRecipeMaps.FLOTATION_FACTORY_RECIPES.recipeBuilder()
                .inputs(GTQTMetaItems.POTASSIUM_ETHYLXANTHATE.getStackForm(32))
                .input(GTQTOrePrefix.milled, materials1, 64)
                .input(GTQTOrePrefix.milled, materials1, 64)
                .input(GTQTOrePrefix.milled, materials1, 64)
                .input(GTQTOrePrefix.milled, materials1, 64)
                .EUt(1920).duration(9600)
                .fluidInputs(GTQTMaterials.PineOil.getFluid(28000))
                .fluidOutputs(materials2.getFluid(1000))
                .buildAndRegister();

        GTQTcoreRecipeMaps.VACUUM_DRYING_FURNACE_RECIPES.recipeBuilder()
                .fluidInputs(materials2.getFluid(4000))
                .output(dust, materials3, 64)
                .output(dust, materials3, 64)
                .output(dust, materials3, 22)
                .output(dust, materials4, 64)
                .output(dust, materials4, 26)
                .output(dust, materials5, 30)
                .output(dust, materials6, 20)
                .circuitMeta(2)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(2000))
                .fluidOutputs(Materials.Water.getFluid(2000))
                .EUt(1920).duration(2400).blastFurnaceTemp(3500)
                .buildAndRegister();
    }
    public static void init() {
        //浮游选矿
        //32*乙基荒原酸钠+4*研磨矿+8000松油=1000泡沫
        //4000泡沫=2000红色泥浆+2000水+3*主产*64+2*副产1*64+副产2*50+副产3*10
        isaLine(Iron,IronFront,Iron,Iron,Nickel,Copper);
        isaLine(BandedIron,BandedIronFront,Iron,Iron,Nickel,Tin);
        isaLine(BrownLimonite,BrownLimoniteFront,Iron,Iron,Copper,Copper);
        isaLine(YellowLimonite,YellowLimoniteFront,Iron,Iron,Copper,Tin);
        isaLine(Chromite,ChromiteFront,Iron,Iron,Chrome,Magnesium);
        isaLine(Ilmenite,IlmeniteFront,Iron,Rutile,Titanium,Tungsten);
        isaLine(Magnetite,MagnetiteFront,Iron,Iron,Gold,Gold);
        isaLine(Pyrite,PyriteFront,Iron,Iron,Sulfur,TricalciumPhosphate);
        isaLine(Tantalite,TantaliteFront,Iron,Manganese,Tantalum,Niobium);

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(3)
                .input(dust, Materials.Potash, 3)
                .input(dust, Materials.Quicklime, 5)
                .fluidInputs(Materials.Water.getFluid(5000))
                .fluidInputs(Materials.CarbonDioxide.getFluid(1000))
                .circuitMeta(18)
                .EUt(VA[HV])
                .duration(600)
                .output(dust, GTQTMaterials.CalciumCarbonate, 5)
                .outputs(GTQTMetaItems.POTASSIUM_ETHYLATE.getStackForm(6))
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(3)
                .input(Blocks.SAPLING, 32)
                .fluidInputs(Materials.DistilledWater.getFluid(8000))
                .fluidInputs(Materials.Mutagen.getFluid(2000))
                .notConsumable(getBiologyCircuitData(6))
                .EUt(VA[HV])
                .duration(2400)
                .output(GTQTMetaBlocks.PINE_SAPLING, 16)
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(3)
                .input(dust, Materials.Sodium)
                .fluidInputs(Materials.Ethanol.getFluid(1000))
                .circuitMeta(16)
                .EUt(VA[HV])
                .duration(600)
                .fluidOutputs(Materials.Hydrogen.getFluid(1000))
                .outputs(GTQTMetaItems.SODIUM_ETHYLATE.getStackForm(9))
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(5)
                .inputs(GTQTMetaItems.POTASSIUM_ETHYLATE.getStackForm(3))
                .fluidInputs(GTQTMaterials.CarbenDisulfide.getFluid(1000))
                .circuitMeta(17)
                .EUt(VA[HV])
                .duration(1200)
                .outputs(GTQTMetaItems.POTASSIUM_ETHYLXANTHATE.getStackForm(12))
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(5)
                .inputs(GTQTMetaItems.SODIUM_ETHYLATE.getStackForm(9))
                .fluidInputs(GTQTMaterials.CarbenDisulfide.getFluid(1000))
                .fluidInputs(Materials.Ethanol.getFluid(1000))
                .circuitMeta(17)
                .EUt(VA[HV])
                .duration(1200)
                .outputs(GTQTMetaItems.SODIUM_ETHYLXANTHATE.getStackForm(12))
                .fluidOutputs(Materials.Water.getFluid(1000))
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(4)
                .inputs(GTQTMetaItems.PINE_FRAGMENT.getStackForm(64))
                .fluidInputs(Materials.Steam.getFluid(5000))
                .circuitMeta(16)
                .EUt(VA[HV])
                .duration(1200)
                .chancedOutput(dust, Materials.Ash, 5, 3000, 15)
                .chancedOutput(dust, Materials.Ash, 5, 3000, 15)
                .chancedOutput(dust, Materials.DarkAsh, 5, 3000, 15)
                .chancedOutput(dust, Materials.DarkAsh, 5, 3000, 15)
                .fluidOutputs(GTQTMaterials.PineOil.getFluid(500))
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(5)
                .inputs(GTQTMetaItems.PINE_FRAGMENT.getStackForm(64))
                .fluidInputs(Materials.Steam.getFluid(5000))
                .circuitMeta(18)
                .EUt(VA[HV])
                .duration(900)
                .chancedOutput(OrePrefix.dustTiny, Materials.Ash, 5, 3000, 15)
                .chancedOutput(OrePrefix.dustTiny, Materials.Ash, 5, 3000, 15)
                .chancedOutput(OrePrefix.dustTiny, Materials.DarkAsh, 5, 3000, 15)
                .chancedOutput(OrePrefix.dustTiny, Materials.DarkAsh, 5, 3000, 15)
                .fluidOutputs(GTQTMaterials.PineOil.getFluid(1500))
                .buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(gem, Materials.Coke, 8)
                .input(dust, Materials.Sulfur, 16)
                .output(dust, Materials.DarkAsh, 1)
                .fluidOutputs(GTQTMaterials.CarbenDisulfide.getFluid(4000))
                .blastFurnaceTemp(1500)
                .EUt(30).duration(12000)
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(3)
                .input(dust, Materials.Sulfur, 4)
                .fluidInputs(Materials.CoalGas.getFluid(1000))
                .circuitMeta(20)
                .EUt(VA[HV])
                .duration(6000)
                .fluidOutputs(GTQTMaterials.CarbenDisulfide.getFluid(2000))
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(4)
                .input(Item.getItemFromBlock(GTQTMetaBlocks.PINE_LEAVES))
                .circuitMeta(14)
                .EUt(VA[HV])
                .duration(200)
                .chancedOutput(GTQTMetaItems.PINE_FRAGMENT, 2, 5000, 0)
                .chancedOutput(GTQTMetaItems.PINE_FRAGMENT, 2, 5000, 0)
                .chancedOutput(GTQTMetaItems.PINE_FRAGMENT, 2, 2500, 0)
                .chancedOutput(GTQTMetaItems.PINE_FRAGMENT, 2, 2500, 0)
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(4)
                .input(Item.getItemFromBlock(GTQTMetaBlocks.PINE_LEAVES))
                .circuitMeta(14)
                .EUt(VA[HV])
                .duration(200)
                .chancedOutput(GTQTMetaItems.PINE_FRAGMENT, 4, 7500, 0)
                .chancedOutput(GTQTMetaItems.PINE_FRAGMENT, 4, 7500, 0)
                .chancedOutput(GTQTMetaItems.PINE_FRAGMENT, 4, 2500, 0)
                .chancedOutput(GTQTMetaItems.PINE_FRAGMENT, 4, 2500, 0)
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(4)
                .input(Item.getItemFromBlock(GTQTMetaBlocks.PINE_LOG))
                .circuitMeta(14)
                .EUt(VA[HV])
                .duration(200)
                .output(GTQTMetaItems.PINE_FRAGMENT, 16)
                .chancedOutput(GTQTMetaItems.PINE_FRAGMENT, 16, 7500, 0)
                .chancedOutput(GTQTMetaItems.PINE_FRAGMENT, 16, 5000, 0)
                .chancedOutput(GTQTMetaItems.PINE_FRAGMENT, 16, 2500, 0)
                .buildAndRegister();
        
        GTQTcoreRecipeMaps.FLOTATION_FACTORY_RECIPES.recipeBuilder()
                .inputs(GTQTMetaItems.SODIUM_ETHYLXANTHATE.getStackForm(32))
                .input(GTQTOrePrefix.milled, Materials.Pyrope, 64)
                .input(GTQTOrePrefix.milled, Materials.Pyrope, 64)
                .input(GTQTOrePrefix.milled, Materials.Pyrope, 64)
                .input(GTQTOrePrefix.milled, Materials.Pyrope, 64)
                .EUt(1920).duration(9600)
                .fluidInputs(GTQTMaterials.PineOil.getFluid(8000))
                .fluidOutputs(GTQTMaterials.PyropeFront.getFluid(1000))
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLOTATION_FACTORY_RECIPES.recipeBuilder()
                .inputs(GTQTMetaItems.POTASSIUM_ETHYLXANTHATE.getStackForm(32))
                .input(GTQTOrePrefix.milled, Materials.Chalcopyrite, 64)
                .input(GTQTOrePrefix.milled, Materials.Chalcopyrite, 64)
                .input(GTQTOrePrefix.milled, Materials.Chalcopyrite, 64)
                .input(GTQTOrePrefix.milled, Materials.Chalcopyrite, 64)
                .EUt(7680).duration(9600)
                .fluidInputs(GTQTMaterials.PineOil.getFluid(12000))
                .fluidOutputs(GTQTMaterials.ChalcopyriteFront.getFluid(1000))
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLOTATION_FACTORY_RECIPES.recipeBuilder()
                .inputs(GTQTMetaItems.SODIUM_ETHYLXANTHATE.getStackForm(32))
                .input(GTQTOrePrefix.milled, Materials.Redstone, 64)
                .input(GTQTOrePrefix.milled, Materials.Redstone, 64)
                .input(GTQTOrePrefix.milled, Materials.Redstone, 64)
                .input(GTQTOrePrefix.milled, Materials.Redstone, 64)
                .EUt(7680).duration(9600)
                .fluidInputs(GTQTMaterials.PineOil.getFluid(13000))
                .fluidOutputs(GTQTMaterials.RedstoneFront.getFluid(1000))
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLOTATION_FACTORY_RECIPES.recipeBuilder()
                .inputs(GTQTMetaItems.POTASSIUM_ETHYLXANTHATE.getStackForm(32))
                .input(GTQTOrePrefix.milled, Materials.Nickel, 64)
                .input(GTQTOrePrefix.milled, Materials.Nickel, 64)
                .input(GTQTOrePrefix.milled, Materials.Nickel, 64)
                .input(GTQTOrePrefix.milled, Materials.Nickel, 64)
                .EUt(7680).duration(9600)
                .fluidInputs(GTQTMaterials.PineOil.getFluid(25000))
                .fluidOutputs(GTQTMaterials.NickelFront.getFluid(1000))
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLOTATION_FACTORY_RECIPES.recipeBuilder()
                .inputs(GTQTMetaItems.SODIUM_ETHYLXANTHATE.getStackForm(32))
                .input(GTQTOrePrefix.milled, Materials.Almandine, 64)
                .input(GTQTOrePrefix.milled, Materials.Almandine, 64)
                .input(GTQTOrePrefix.milled, Materials.Almandine, 64)
                .input(GTQTOrePrefix.milled, Materials.Almandine, 64)
                .EUt(7680).duration(9600)
                .fluidInputs(GTQTMaterials.PineOil.getFluid(18000))
                .fluidOutputs(GTQTMaterials.AlmandineFront.getFluid(1000))
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLOTATION_FACTORY_RECIPES.recipeBuilder()
                .inputs(GTQTMetaItems.POTASSIUM_ETHYLXANTHATE.getStackForm(32))
                .input(GTQTOrePrefix.milled, Materials.Spessartine, 64)
                .input(GTQTOrePrefix.milled, Materials.Spessartine, 64)
                .input(GTQTOrePrefix.milled, Materials.Spessartine, 64)
                .input(GTQTOrePrefix.milled, Materials.Spessartine, 64)
                .EUt(30720).duration(9600)
                .fluidInputs(GTQTMaterials.PineOil.getFluid(35000))
                .fluidOutputs(GTQTMaterials.SpessartineFront.getFluid(1000))
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLOTATION_FACTORY_RECIPES.recipeBuilder()
                .inputs(GTQTMetaItems.SODIUM_ETHYLXANTHATE.getStackForm(32))
                .input(GTQTOrePrefix.milled, Materials.Sphalerite, 64)
                .input(GTQTOrePrefix.milled, Materials.Sphalerite, 64)
                .input(GTQTOrePrefix.milled, Materials.Sphalerite, 64)
                .input(GTQTOrePrefix.milled, Materials.Sphalerite, 64)
                .EUt(30720).duration(9600)
                .fluidInputs(GTQTMaterials.PineOil.getFluid(14000))
                .fluidOutputs(GTQTMaterials.SphaleriteFront.getFluid(1000))
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLOTATION_FACTORY_RECIPES.recipeBuilder()
                .inputs(GTQTMetaItems.POTASSIUM_ETHYLXANTHATE.getStackForm(32))
                .input(GTQTOrePrefix.milled, Materials.Pentlandite, 64)
                .input(GTQTOrePrefix.milled, Materials.Pentlandite, 64)
                .input(GTQTOrePrefix.milled, Materials.Pentlandite, 64)
                .input(GTQTOrePrefix.milled, Materials.Pentlandite, 64)
                .EUt(30720).duration(9600)
                .fluidInputs(GTQTMaterials.PineOil.getFluid(14000))
                .fluidOutputs(GTQTMaterials.PentlanditeFront.getFluid(1000))
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLOTATION_FACTORY_RECIPES.recipeBuilder()
                .inputs(GTQTMetaItems.SODIUM_ETHYLXANTHATE.getStackForm(32))
                .input(GTQTOrePrefix.milled, Materials.Platinum, 64)
                .input(GTQTOrePrefix.milled, Materials.Platinum, 64)
                .input(GTQTOrePrefix.milled, Materials.Platinum, 64)
                .input(GTQTOrePrefix.milled, Materials.Platinum, 64)
                .EUt(30720).duration(9600)
                .fluidInputs(GTQTMaterials.PineOil.getFluid(35000))
                .fluidOutputs(GTQTMaterials.PlatinumFront.getFluid(1000))
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLOTATION_FACTORY_RECIPES.recipeBuilder()
                .inputs(GTQTMetaItems.POTASSIUM_ETHYLXANTHATE.getStackForm(32))
                .input(GTQTOrePrefix.milled, Materials.Grossular, 64)
                .input(GTQTOrePrefix.milled, Materials.Grossular, 64)
                .input(GTQTOrePrefix.milled, Materials.Grossular, 64)
                .input(GTQTOrePrefix.milled, Materials.Grossular, 64)
                .EUt(30720).duration(9600)
                .fluidInputs(GTQTMaterials.PineOil.getFluid(28000))
                .fluidOutputs(GTQTMaterials.GrossularFront.getFluid(1000))
                .buildAndRegister();

        GTQTcoreRecipeMaps.FLOTATION_FACTORY_RECIPES.recipeBuilder()
                .inputs(GTQTMetaItems.SODIUM_ETHYLXANTHATE.getStackForm(32))
                .input(GTQTOrePrefix.milled, Materials.Monazite, 64)
                .input(GTQTOrePrefix.milled, Materials.Monazite, 64)
                .input(GTQTOrePrefix.milled, Materials.Monazite, 64)
                .input(GTQTOrePrefix.milled, Materials.Monazite, 64)
                .EUt(30720).duration(9600)
                .fluidInputs(GTQTMaterials.PineOil.getFluid(30000))
                .fluidOutputs(GTQTMaterials.MonaziteFront.getFluid(1000))
                .buildAndRegister();

        GTQTcoreRecipeMaps.VACUUM_DRYING_FURNACE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.PyropeFront.getFluid(4000))
                .output(dust, Materials.Magnesium, 64)
                .output(dust, Materials.Magnesium, 46)
                .output(dust, Materials.Manganese, 64)
                .output(dust, Materials.Manganese, 6)
                .output(dust, Materials.Borax, 60)
                .output(dust, Materials.Rhenium, 20)
                .circuitMeta(2)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(2000))
                .fluidOutputs(Materials.Water.getFluid(2000))
                .EUt(1920).duration(2400).blastFurnaceTemp(3500)
                .buildAndRegister();

        GTQTcoreRecipeMaps.VACUUM_DRYING_FURNACE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.NickelFront.getFluid(4000))
                .output(dust, Materials.Nickel, 64)
                .output(dust, Materials.Nickel, 64)
                .output(dust, Materials.Nickel, 22)
                .output(dust, Materials.Cobalt, 64)
                .output(dust, Materials.Cobalt, 56)
                .output(dust, Materials.Rhodium, 32)
                .output(dust, Materials.Ruthenium, 16)
                .circuitMeta(2)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(2000))
                .fluidOutputs(Materials.Water.getFluid(2000))
                .EUt(7680).duration(2400).blastFurnaceTemp(4500)
                .buildAndRegister();

        GTQTcoreRecipeMaps.VACUUM_DRYING_FURNACE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.RedstoneFront.getFluid(4000))
                .output(dust, Materials.Redstone, 64)
                .output(dust, Materials.Redstone, 64)
                .output(dust, Materials.Redstone, 64)
                .output(dust, Materials.Redstone, 64)
                .output(dust, Materials.Redstone, 44)
                .output(dust, Materials.Chrome, 60)
                //TODO FireStone Dust* 45
                .output(dust, Materials.Dysprosium, 16)
                .circuitMeta(2)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(2000))
                .fluidOutputs(Materials.Water.getFluid(2000))
                .EUt(7680).duration(2400).blastFurnaceTemp(4500)
                .buildAndRegister();

        GTQTcoreRecipeMaps.VACUUM_DRYING_FURNACE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.ChalcopyriteFront.getFluid(4000))
                .output(dust, Copper, 64)
                .output(dust, Copper, 64)
                .output(dust, Copper, 52)
                .output(dust, Iron, 64)
                .output(dust, Iron, 44)
                .output(dust, Materials.Cadmium, 50)
                .output(dust, Materials.Indium, 10)
                .circuitMeta(2)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(2000))
                .fluidOutputs(Materials.Water.getFluid(2000))
                .EUt(7680).duration(2400).blastFurnaceTemp(4500)
                .buildAndRegister();

        GTQTcoreRecipeMaps.VACUUM_DRYING_FURNACE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.PlatinumFront.getFluid(4000))
                .output(dust, Materials.Platinum, 64)
                .output(dust, Materials.Platinum, 64)
                .output(dust, Materials.Rhodium, 40)
                .output(dust, Materials.Selenium, 40)
                .output(dust, Materials.Tellurium, 10)
                .circuitMeta(2)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(2000))
                .fluidOutputs(Materials.Water.getFluid(2000))
                .EUt(30720).duration(2400).blastFurnaceTemp(5500)
                .buildAndRegister();

        GTQTcoreRecipeMaps.VACUUM_DRYING_FURNACE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.SpessartineFront.getFluid(4000))
                .output(dust, Materials.Manganese, 64)
                .output(dust, Materials.Manganese, 64)
                .output(dust, Materials.Manganese, 22)
                .output(dust, Materials.Aluminium, 64)
                .output(dust, Materials.Aluminium, 26)
                .output(dust, Materials.RarestMetalMixture, 30)
                .output(dust, Materials.Strontium, 20)
                .circuitMeta(2)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(2000))
                .fluidOutputs(Materials.Water.getFluid(2000))
                .EUt(30720).duration(2400).blastFurnaceTemp(5500)
                .buildAndRegister();

        GTQTcoreRecipeMaps.VACUUM_DRYING_FURNACE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.AlmandineFront.getFluid(4000))
                .output(dust, Materials.Aluminium, 64)
                .output(dust, Materials.Aluminium, 64)
                .output(dust, Materials.Aluminium, 22)
                .output(dust, Materials.Manganese, 64)
                .output(dust, Materials.Manganese, 26)
                .output(dust, Materials.Yttrium, 25)
                .output(dust, Materials.Ytterbium, 15)
                .circuitMeta(2)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(2000))
                .fluidOutputs(Materials.Water.getFluid(2000))
                .EUt(30720).duration(2400).blastFurnaceTemp(5500)
                .buildAndRegister();

        GTQTcoreRecipeMaps.VACUUM_DRYING_FURNACE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.SphaleriteFront.getFluid(4000))
                .output(dust, Materials.Zinc, 64)
                .output(dust, Materials.Zinc, 64)
                .output(dust, Materials.Zinc, 52)
                .output(dust, Iron, 64)
                .output(dust, Iron, 56)
                .output(dust, Materials.Indium, 64)
                .output(dust, Materials.Germanium, 15)
                .circuitMeta(2)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(2000))
                .fluidOutputs(Materials.Water.getFluid(2000))
                .EUt(30720).duration(2400).blastFurnaceTemp(5500)
                .buildAndRegister();

        GTQTcoreRecipeMaps.VACUUM_DRYING_FURNACE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.PentlanditeFront.getFluid(4000))
                .output(dust, Iron, 64)
                .output(dust, Iron, 64)
                .output(dust, Iron, 22)
                .output(dust, Materials.Nickel, 64)
                .output(dust, Materials.Nickel, 36)
                .output(dust, Materials.Promethium, 20)
                .output(dust, Materials.Hafnium, 10)
                .circuitMeta(2)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(2000))
                .fluidOutputs(Materials.Water.getFluid(2000))
                .EUt(30720).duration(2400).blastFurnaceTemp(5500)
                .buildAndRegister();

        GTQTcoreRecipeMaps.VACUUM_DRYING_FURNACE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.GrossularFront.getFluid(4000))
                .output(dust, Materials.Calcium, 64)
                .output(dust, Materials.Calcium, 64)
                .output(dust, Materials.Calcium, 52)
                .output(dust, Materials.Aluminium, 64)
                .output(dust, Materials.Aluminium, 46)
                .output(dust, Materials.Tungsten, 60)
                .output(dust, Materials.Thallium, 15)
                .circuitMeta(2)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(2000))
                .fluidOutputs(Materials.Water.getFluid(2000))
                .EUt(30720).duration(2400).blastFurnaceTemp(5500)
                .buildAndRegister();

        GTQTcoreRecipeMaps.VACUUM_DRYING_FURNACE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.MonaziteFront.getFluid(4000))
                .output(dust, Materials.Erbium, 64)
                .output(dust, Materials.Lanthanum, 32)
                .output(dust, Materials.Lutetium, 16)
                .output(dust, Materials.Europium, 8)
                .circuitMeta(2)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(2000))
                .fluidOutputs(Materials.Water.getFluid(2000))
                .EUt(122880).duration(2400).blastFurnaceTemp(5500)
                .buildAndRegister();

    }

}