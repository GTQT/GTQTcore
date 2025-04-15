package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.unification.OreDictUnifier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;


import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtechfoodoption.GTFOMaterialHandler.ChloroauricAcid;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class PreciousMetalChain {
    public static void init() {

        //  Gold Primitive Blast recipe
        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(ingot, PreciousMetal)
                .input(gem, Coal, 2)
                .output(nugget, Gold)
                .output(dustTiny, DarkAsh, 2)
                .duration(600)
                .buildAndRegister();

        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(ingot, PreciousMetal)
                .input(dust, Coal, 2)
                .output(nugget, Gold)
                .output(dustTiny, DarkAsh, 2)
                .duration(600)
                .buildAndRegister();

        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(ingot, PreciousMetal)
                .input(gem, Charcoal, 2)
                .output(nugget, Gold)
                .output(dustTiny, DarkAsh, 2)
                .duration(600)
                .buildAndRegister();

        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(ingot, PreciousMetal)
                .input(dust, Charcoal, 2)
                .output(nugget, Gold)
                .output(dustTiny, DarkAsh, 2)
                .duration(600)
                .buildAndRegister();

        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(ingot, PreciousMetal)
                .input(gem, Coke, 2)
                .output(nugget, Gold)
                .output(dustTiny, Ash, 2)
                .duration(300)
                .buildAndRegister();

        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(ingot, PreciousMetal)
                .input(dust, Coke, 2)
                .output(nugget, Gold)
                .output(dustTiny, Ash, 2)
                .duration(300)
                .buildAndRegister();

        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(block, PreciousMetal)
                .input(block, Coal, 2)
                .output(ingot, Gold)
                .output(dust, DarkAsh, 2)
                .duration(5400)
                .buildAndRegister();

        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(block, PreciousMetal)
                .input(block, Charcoal, 2)
                .output(ingot, Gold)
                .output(dust, DarkAsh, 2)
                .duration(5400)
                .buildAndRegister();

        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(block, PreciousMetal)
                .input(block, Coke, 2)
                .output(ingot, Gold)
                .output(dust, Ash, 2)
                .duration(5400)
                .buildAndRegister();

        //  Fix
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, Gold, 2)},
                new FluidStack[]{HydrochloricAcid.getFluid(8000)});

        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, Gold, 2)},
                new FluidStack[]{HydrochloricAcid.getFluid(8000)});

        //  Gold Copper Mixture
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Copper, 3)
                .input(dust, PreciousMetal)
                .output(dust, GoldCopperMixture, 4)
                .EUt(VA[LV])
                .duration(200)
                .buildAndRegister();

        //  Nickel Copper Mixture (For Platinum group step1)
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Nickel, 3)
                .input(dust, PreciousMetal)
                .output(dust, GoldNickelMixture, 4)
                .EUt(VA[LV])
                .duration(200)
                .buildAndRegister();

        //  Gold Copper Mixture -> Leaching Gold + Nitrogen Dioxide
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, GoldCopperMixture, 4)
                .fluidInputs(NitricAcid.getFluid(1000))
                .output(dust, LeachingGold, 4)
                .fluidOutputs(NitrogenDioxide.getFluid(2000))
                .EUt(VA[LV])
                .duration(80)
                .buildAndRegister();

        //  Gold Nickel Mixture -> Leaching Nickel + Nitrogen Dioxide
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, GoldNickelMixture, 4)
                .fluidInputs(NitricAcid.getFluid(1000))
                .output(dust, LeachingNickel, 4)
                .fluidOutputs(NitrogenDioxide.getFluid(2000))
                .EUt(VA[LV])
                .duration(80)
                .buildAndRegister();

        //  Leaching Gold Electrolyzer recipe
        ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust, LeachingGold, 4)
                .fluidInputs(Hydrogen.getFluid(1000))
                .output(dust, Copper, 3)
                .output(dustTiny, Gold, 8)
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[LV])
                .duration(300)
                .buildAndRegister();

        //  Leaching Nickel Electrolyzer recipe
        ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust, LeachingNickel, 4)
                .fluidInputs(Hydrogen.getFluid(1000))
                .output(dust, Nickel, 3)
                .output(dustTiny, Gold, 8)
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[LV])
                .duration(300)
                .buildAndRegister();

        //  Leaching Gold -> Leaching Copper + Chloroauric Acid
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, LeachingGold, 4)
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .output(dust, LeachingCopper, 4)
                .fluidOutputs(ChloroauricAcid.getFluid(1000))
                .EUt(VA[LV])
                .duration(80)
                .buildAndRegister();

        //  Potassium + Sulfur + Oxygen -> Potassium Metabisulfite
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Potassium, 2)
                .input(dust, Sulfur, 2)
                .fluidInputs(Oxygen.getFluid(5000))
                .circuitMeta(1)
                .output(dust, PotassiumMetabisulfite, 9)
                .EUt(VA[LV])
                .duration(120)
                .buildAndRegister();

        //  Chloroauric Acid -> Gold + Water + Chlorine (recycle)
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust, PotassiumMetabisulfite)
                .fluidInputs(ChloroauricAcid.getFluid(1000))
                .output(dust, Gold, 2)
                .fluidOutputs(Water.getFluid(1000))
                .fluidOutputs(Chlorine.getFluid(1000))
                .EUt(VA[LV])
                .duration(160)
                .buildAndRegister();

        //  Leaching Copper Dryer recipe
        DRYER_RECIPES.recipeBuilder()
                .input(dust, LeachingCopper, 4)
                .output(dust, Copper, 3)
                .output(dustSmall, Silver, 2)
                .EUt(VA[LV])
                .duration(120)
                .buildAndRegister();


    }
}