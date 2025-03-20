package keqing.gtqtcore.loaders.recipes.chain;

import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.*;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;


public class MaterialOreChains {

    public static void init() {
        Indium();
        ZirconiumHafnium();
        FixRoaster();
        PrimitiveRoasting();
    }
    private static void PrimitiveRoasting() {

        Material[] fuel = new Material[]{Coke, Coal, Charcoal};
        for (int i = 0; i < fuel.length; i++) {
            PRIMITIVE_ROASTING_RECIPES.recipeBuilder()
                    .duration(345)
                    .input(crushed, Sphalerite, 1)
                    .input(gem, fuel[i], i == 2 ? 4 : i + 1)
                    .output(ingot, Zinc)
                    .chancedOutput(ingot, SulfuricZincSlag,5000, 0)
                    .fluidOutputs(SulfuricFlueGas.getFluid(100))
                    .buildAndRegister();

            ROASTER_RECIPES.recipeBuilder()
                    .duration(345)
                    .EUt(VA[MV])
                    .temperature(2700)
                    .input(crushed, Sphalerite, 1)
                    .output(ingot, Zinc)
                    .chancedOutput(ingot, SulfuricZincSlag,9000, 0)
                    .fluidOutputs(SulfuricFlueGas.getFluid(100))
                    .buildAndRegister();


            PRIMITIVE_ROASTING_RECIPES.recipeBuilder()
                    .duration(315)
                    .input(gem, fuel[i], i == 2 ? 4 : i + 1)
                    .input(crushed, Chalcopyrite, 1)
                    .output(ingot, Copper)
                    .chancedOutput(ingot, SulfuricCopperSlag,  2500, 0)
                    .chancedOutput(ingot, SulfuricIronSlag, 7500, 0)
                    .fluidOutputs(SulfuricFlueGas.getFluid(100))
                    .buildAndRegister();

            ROASTER_RECIPES.recipeBuilder()
                    .duration(315)
                    .EUt(VA[MV])
                    .temperature(2700)
                    .input(crushed, Chalcopyrite, 1)
                    .output(ingot, Copper)
                    .chancedOutput(ingot, SulfuricCopperSlag,  5000, 0)
                    .chancedOutput(ingot, SulfuricIronSlag, 9000, 0)
                    .fluidOutputs(SulfuricFlueGas.getFluid(100))
                    .buildAndRegister();

            PRIMITIVE_ROASTING_RECIPES.recipeBuilder()
                    .duration(380)
                    .input(crushed, Pyrite, 1)
                    .input(gem, fuel[i], i == 2 ? 4 : i + 1)
                    .output(ingot, Iron)
                    .chancedOutput(ingot, SulfuricIronSlag,5000, 0)
                    .fluidOutputs(SulfuricFlueGas.getFluid(100))
                    .buildAndRegister();

            ROASTER_RECIPES.recipeBuilder()
                    .duration(380)
                    .EUt(VA[MV])
                    .temperature(2700)
                    .input(crushed, Pyrite, 1)
                    .output(ingot, Iron)
                    .chancedOutput(ingot, SulfuricIronSlag,9000, 0)
                    .fluidOutputs(SulfuricFlueGas.getFluid(100))
                    .buildAndRegister();

            PRIMITIVE_ROASTING_RECIPES.recipeBuilder()
                    .duration(335)
                    .input(gem, fuel[i], i == 2 ? 4 : i + 1)
                    .input(crushed, Bornite, 1)
                    .output(ingot, Copper)
                    .chancedOutput(ingot, SulfuricCopperSlag, 2500, 0)
                    .chancedOutput(ingot, SulfuricIronSlag,7500, 0)
                    .fluidOutputs(SulfuricFlueGas.getFluid(100))
                    .buildAndRegister();

            ROASTER_RECIPES.recipeBuilder()
                    .duration(335)
                    .EUt(VA[MV])
                    .temperature(2700)
                    .input(crushed, Bornite, 1)
                    .output(ingot, Copper)
                    .chancedOutput(ingot, SulfuricCopperSlag, 5000, 0)
                    .chancedOutput(ingot, SulfuricIronSlag,9000, 0)
                    .fluidOutputs(SulfuricFlueGas.getFluid(100))
                    .buildAndRegister();

            PRIMITIVE_ROASTING_RECIPES.recipeBuilder()
                    .duration(310)
                    .input(gem, fuel[i], i == 2 ? 4 : i + 1)
                    .input(crushed, Chalcocite)
                    .output(ingot, Copper)
                    .chancedOutput(ingot, SulfuricCopperSlag,5000, 0)
                    .fluidOutputs(SulfuricFlueGas.getFluid(100))
                    .buildAndRegister();

            ROASTER_RECIPES.recipeBuilder()
                    .duration(310)
                    .EUt(VA[MV])
                    .temperature(2700)
                    .input(crushed, Chalcocite)
                    .output(ingot, Copper)
                    .chancedOutput(ingot, SulfuricCopperSlag,9000, 0)
                    .fluidOutputs(SulfuricFlueGas.getFluid(100))
                    .buildAndRegister();

            PRIMITIVE_ROASTING_RECIPES.recipeBuilder()
                    .duration(345)
                    .input(gem, fuel[i], i == 2 ? 4 : i + 1)
                    .input(crushed, Pentlandite)
                    .output(ingot, Nickel)
                    .chancedOutput(ingot, SulfuricNickelSlag, 5000, 0)
                    .fluidOutputs(SulfuricFlueGas.getFluid(100))
                    .buildAndRegister();

            ROASTER_RECIPES.recipeBuilder()
                    .duration(345)
                    .EUt(VA[MV])
                    .temperature(2700)
                    .input(crushed, Pentlandite)
                    .output(ingot, Nickel)
                    .chancedOutput(ingot, SulfuricNickelSlag, 9000, 0)
                    .fluidOutputs(SulfuricFlueGas.getFluid(100))
                    .buildAndRegister();

            CHEMICAL_RECIPES.recipeBuilder()
                    .duration(200)
                    .EUt(VA[LV])
                    .fluidInputs(SulfuricFlueGas.getFluid(400))
                    .fluidInputs(Water.getFluid(100))
                    .fluidOutputs(DilutedSulfuricAcid.getFluid(300))
                    .chancedOutput(dust,Ash,5000,0)
                    .chancedOutput(dust,Ash,5000,0)
                    .buildAndRegister();

            GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(ingot, SulfuricCopperSlag));
            GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(ingot, SulfuricZincSlag));
            GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(ingot, SulfuricIronSlag));
            GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(ingot, SulfuricNickelSlag));

            MACERATOR_RECIPES.recipeBuilder()
                    .duration(85)
                    .EUt(8)
                    .input(ingot, SulfuricCopperSlag)
                    .output(dust, Sulfur)
                    .chancedOutput(dust, Copper, 5000,0)
                    .buildAndRegister();

            MACERATOR_RECIPES.recipeBuilder()
                    .duration(85)
                    .EUt(8)
                    .input(ingot, SulfuricZincSlag)
                    .output(dust, Sulfur)
                    .chancedOutput(dust, Zinc, 5000,0)
                    .buildAndRegister();
            MACERATOR_RECIPES.recipeBuilder()
                    .duration(85)
                    .EUt(8)
                    .input(ingot, SulfuricIronSlag)
                    .output(dust, Sulfur)
                    .chancedOutput(dust, Iron, 5000,0)
                    .buildAndRegister();
            MACERATOR_RECIPES.recipeBuilder()
                    .duration(85)
                    .EUt(8)
                    .input(ingot, SulfuricNickelSlag)
                    .output(dust, Sulfur)
                    .chancedOutput(dust, Nickel, 5000,0)
                    .buildAndRegister();
        }

    }

    private static void FixRoaster() {
        BLAST_RECIPES.recipeBuilder()
                .duration(40)
                .EUt(VA[LV])
                .input(dust, Sodium)
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust, SodiumPeroxide, 2)
                .blastFurnaceTemp(2100)
                .buildAndRegister();

        DRYER_RECIPES.recipeBuilder()
                .duration(160)
                .EUt(VA[HV])
                .input(dust, Molybdenite,3)
                .fluidInputs(Oxygen.getFluid(8000))
                .fluidOutputs(SulfurDioxide.getFluid(2000))
                .fluidOutputs(MolybdenumFlue.getFluid(1000))
                .output(dust, MolybdenumTrioxide, 4)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .duration(200)
                .EUt(VA[HV])
                .input(dust, Quicklime, 2)
                .input(gem, Coke)
                .output(dust, CalciumCarbide, 3)
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .blastFurnaceTemp(1500)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(VA[MV])
                .input(dust, WhitePhosphorus)
                .fluidInputs(Argon.getFluid(50))
                .output(gem, RedPhosphorus)
                .blastFurnaceTemp(5600)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(VA[HV])
                .input(dust, Quicklime, 2)
                .input(dust, Carbon, 3)
                .output(dust, CalciumCarbide, 3)
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .blastFurnaceTemp(1500)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .duration(220)
                .EUt(VA[HV])
                .input(dust, ChalcogenAnodeMud)
                .input(dust, SodaAsh, 6)
                .fluidInputs(Oxygen.getFluid(4000))
                .output(dust, SodiumTellurite, 6)
                .output(dust, SeleniumDioxide, 3)
                .output(ingot, Silver, 2)
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .blastFurnaceTemp(4500)
                .buildAndRegister();
    }



    private static void Indium() {
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, Aluminium, 4)},
                new FluidStack[]{IndiumConcentrate.getFluid(1000)});
        GTRecipeHandler.removeRecipesByInputs(MIXER_RECIPES,
                new ItemStack[]{OreDictUnifier.get(crushedPurified, Galena),OreDictUnifier.get(crushedPurified, Sphalerite)},
                new FluidStack[]{SulfuricAcid.getFluid(4000)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES,
                new ItemStack[]{OreDictUnifier.get(dust, Aluminium, 4)},
                new FluidStack[]{IndiumConcentrate.getFluid(1000)});

        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .duration(160)
                .EUt(VA[HV])
                .input(dust, WaelzSlag, 5)
                .fluidInputs(Water.getFluid(1000))
                .output(dust, ZincOxide, 2)
                .chancedOutput(dust, Gallium, 2000, 1000)
                .fluidOutputs(IndiumSolution.getFluid(1000))
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .duration(190)
                .EUt(VA[HV])
                .fluidInputs(IndiumSolution.getFluid(1000))
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .fluidOutputs(IndiumConcentrate.getFluid(1000))
                .buildAndRegister();

        DRYER_RECIPES.recipeBuilder()
                .duration(120)
                .EUt(VA[HV])
                .fluidInputs(IndiumConcentrate.getFluid(1000))
                .chancedOutput(dust, IndiumSulfide, 6000, 1500)
                .chancedOutput(dustSmall, Zinc, 2500, 1500)
                .fluidOutputs(IndiumResidue.getFluid(500))
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .duration(210)
                .EUt(VA[HV])
                .fluidInputs(IndiumResidue.getFluid(1000))
                .chancedOutput(dust, ThalliumSulfate, 7500, 500)
                .chancedOutput(dust, GalliumSulfide, 5000, 500)
                .fluidOutputs(SulfurDioxide.getFluid(100))
                .buildAndRegister();

    }

    private static void ZirconiumHafnium() {
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(dust, Hafnium));
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, OreDictUnifier.get(dust, Zirconium));
        MIXER_RECIPES.recipeBuilder()
                .duration(30)
                .EUt(VA[LV])
                .input(dust, Aluminium, 6)
                .fluidInputs(HydrochloricAcid.getFluid(4500))
                .fluidInputs(Water.getFluid(5000))
                .fluidOutputs(BauxiteSlurry.getFluid(1000))
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .duration(120)
                .EUt(VA[MV])
                .input(dust, Ilmenite, 4)
                .fluidInputs(HydrochloricAcid.getFluid(4500))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(IlmeniteSlurry.getFluid(1000))
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .duration(180)
                .EUt(VA[LV])
                .fluidInputs(BauxiteSlurry.getFluid(1000))
                .fluidOutputs(RedMud.getFluid(1000))
                .output(dust, AluminiumHydroxide, 6)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .duration(180)
                .EUt(VA[MV])
                .fluidInputs(IlmeniteSlurry.getFluid(1000))
                .fluidOutputs(RedMud.getFluid(1000))
                .output(dust, Iron)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(VA[EV])
                .fluidInputs(RedMud.getFluid(4000))
                .output(dust,Rutile)
                .output(dust,Gallium)
                .fluidOutputs(Water.getFluid(1000))
                .fluidOutputs(HeavyRedMudResidue.getFluid(250))
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .duration(310)
                .EUt(VA[EV])
                .fluidInputs(HeavyRedMudResidue.getFluid(1000))
                .fluidOutputs(Iron3Chloride.getFluid(250))
                .fluidOutputs(CrudeRareEarthTurbidSolution.getFluid(250))
                .fluidOutputs(RefractoryMetalResidue.getFluid(500))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(VA[EV])
                .fluidInputs(RefractoryMetalResidue.getFluid(1000))
                .fluidInputs(Fluorine.getFluid(6000))
                .input(dust, Potassium, 2)
                .fluidOutputs(PotassiumFluorideRefractoryMixture.getFluid(1000))
                .buildAndRegister();

        AUTOCLAVE_RECIPES.recipeBuilder()
                .duration(400)
                .EUt(VA[EV])
                .notConsumable(plate, Platinum)
                .fluidInputs(PotassiumFluorideRefractoryMixture.getFluid(1000))
                .output(gemExquisite, PotassiumHexafluorohafnate)
                .output(gemExquisite, PotassiumHexafluorozirconate)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(140)
                .EUt(VA[MV])
                .input(dust, Zirconium)
                .fluidInputs(Chlorine.getFluid(4000))
                .fluidOutputs(ZirconiumTetrachloride.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(140)
                .EUt(VA[MV])
                .input(dust, Hafnium)
                .fluidInputs(Chlorine.getFluid(4000))
                .fluidOutputs(HafniumTetrachloride.getFluid(1000))
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .duration(1840)
                .EUt(VA[MV])
                .blastFurnaceTemp(3200)
                .input(dust, Magnesium, 2)
                .fluidInputs(ZirconiumTetrachloride.getFluid(1000))
                .output(ingotHot, Zirconium)
                .output(dust, MagnesiumChloride, 6)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .duration(1840)
                .EUt(VA[MV])
                .blastFurnaceTemp(3200)
                .input(dust, Magnesium, 2)
                .fluidInputs(HafniumTetrachloride.getFluid(1000))
                .output(dust, Hafnium)
                .output(dust, MagnesiumChloride, 6)
                .buildAndRegister();

    }
}
