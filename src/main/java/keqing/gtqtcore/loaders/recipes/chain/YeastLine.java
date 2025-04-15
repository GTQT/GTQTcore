package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.items.metaitem.MetaItem;
import net.minecraft.init.Items;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.common.items.MetaItems.FERTILIZER;
import static gregtechfoodoption.GTFOMaterialHandler.Acetaldehyde;
import static gregtechfoodoption.GTFOMaterialHandler.SodiumCarbonateSolution;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.BIOLOGICAL_REACTION_RECIPES;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CHEMICAL_PLANT;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Butanol;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.OnePropanol;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class YeastLine {
    public static void init() {
        Yeast();
        Algae();
    }

    private static void Algae() {
        MetaItem.MetaValueItem [] algaeList={COMMON_ALGAE,GREEN_ALGAE,RED_ALGAE,BROWN_ALGAE,GOLD_ALGAE};
        for (MetaItem.MetaValueItem item: algaeList) {
            BREWING_RECIPES.recipeBuilder()
                    .input(item)
                    .fluidInputs(Water.getFluid(100))
                    .fluidOutputs(Biomass.getFluid(100))
                    .duration(400)
                    .EUt(VA[MV])
                    .buildAndRegister();

            // Algae + 6Na2CO3(H2O) -> 4C6H10O5 + 2C5H10O5 + 6NaC6H7O6(H2O) + 6CO2 + 6H2O
            CHEMICAL_PLANT.recipeBuilder().duration(600).EUt(1920)
                    .input(item,10)
                    .input(dust, Diatomite)
                    .fluidInputs(SodiumCarbonateSolution.getFluid(6000))
                    .output(dust, Cellulose,64)
                    .output(dust, Xylose,20)
                    .fluidOutputs(Biomass.getFluid(540))
                    .fluidOutputs(SodiumAlginateSolution.getFluid(6000))
                    .fluidOutputs(CarbonDioxide.getFluid(6000))
                    .fluidOutputs(Water.getFluid(6000))
                    .buildAndRegister();
        }
        // Algae + 6Na2CO3(H2O) -> 4C6H10O5 + 2C5H10O5 + 6NaC6H7O6(H2O) + 6CO2 + 6H2O
        CHEMICAL_PLANT.recipeBuilder().duration(600).EUt(1920)
                .input(CELLULOSE_FIBER_YELLOW,2)
                .input(dust, Diatomite)
                .fluidInputs(SodiumCarbonateSolution.getFluid(6000))
                .output(dust, Cellulose,64)
                .output(dust, Xylose,20)
                .fluidOutputs(Biomass.getFluid(540))
                .fluidOutputs(SodiumAlginateSolution.getFluid(6000))
                .fluidOutputs(CarbonDioxide.getFluid(6000))
                .fluidOutputs(Water.getFluid(6000))
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder().duration(600).EUt(1920)
                .input(CELLULOSE_FIBER_RED,2)
                .input(dust, Diatomite)
                .fluidInputs(SodiumCarbonateSolution.getFluid(6000))
                .output(dust, Cellulose,64)
                .output(dust, Xylose,20)
                .fluidOutputs(Biomass.getFluid(540))
                .fluidOutputs(SodiumAlginateSolution.getFluid(6000))
                .fluidOutputs(CarbonDioxide.getFluid(6000))
                .fluidOutputs(Water.getFluid(6000))
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder().duration(600).EUt(1920)
                .input(CELLULOSE_FIBER_GREEN,2)
                .input(dust, Diatomite)
                .fluidInputs(SodiumCarbonateSolution.getFluid(6000))
                .output(dust, Cellulose,64)
                .output(dust, Xylose,20)
                .fluidOutputs(Biomass.getFluid(540))
                .fluidOutputs(SodiumAlginateSolution.getFluid(6000))
                .fluidOutputs(CarbonDioxide.getFluid(6000))
                .fluidOutputs(Water.getFluid(6000))
                .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
                .duration(400)
                .EUt(VA[EV])
                .circuitMeta(1)
                .fluidInputs(Biomass.getFluid(1000))
                .fluidInputs(Steam.getFluid(1000))
                .fluidOutputs(CelluloseExposureSolution.getFluid(1000))
                .buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
                .duration(400)
                .EUt(VA[EV])
                .circuitMeta(2)
                .fluidInputs(Biomass.getFluid(1000))
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .fluidOutputs(CelluloseFermentationResidue.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .duration(400)
                .EUt(VA[EV])
                .Catalyst(CATALYST_FRAMEWORK_IV.getStackForm())
                .recipeLevel(4)
                .fluidInputs(CelluloseExposureSolution.getFluid(1000))
                .output(dust, Cellulose,10)
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .duration(400)
                .EUt(VA[EV])
                .Catalyst(CATALYST_FRAMEWORK_IV.getStackForm())
                .recipeLevel(4)
                .fluidInputs(CelluloseFermentationResidue.getFluid(1000))
                .output(dust, Cellulose,40)
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .buildAndRegister();
    }

    private static void Yeast() {
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(Biomass.getFluid(8000))
                .fluidOutputs(YeastExtract.getFluid(100))
                .fluidOutputs(Bacteria.getFluid(400))
                .fluidOutputs(Ethanol.getFluid(1500))
                .fluidOutputs(FermentedBiomass.getFluid(5000))
                .output(Items.SUGAR, 16)
                .duration(400)
                .EUt(VA[MV])
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .duration(800)
                .EUt(VA[MV])
                .input(Items.SUGAR, 1)
                .fluidInputs(YeastExtract.getFluid(100))
                .fluidInputs(Water.getFluid(100))
                .fluidOutputs(Yeast.getFluid(200))
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .duration(800)
                .EUt(VA[MV])
                .input(Items.SUGAR, 16)
                .fluidInputs(Yeast.getFluid(100))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(Yeast.getFluid(1000))
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .duration(800)
                .EUt(VA[MV])
                .input(dust, Glucose, 32)
                .fluidInputs(Water.getFluid(1000))
                .fluidInputs(Yeast.getFluid(1000))
                .fluidOutputs(GlucoseFermentationSolution.getFluid(1000))
                .fluidOutputs(CarbonDioxide.getFluid(500))
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .duration(800)
                .EUt(VA[MV])
                .fluidInputs(GlucoseFermentationSolution.getFluid(10000))
                .fluidOutputs(Ethanol.getFluid(7000))
                .fluidOutputs(Water.getFluid(1000))
                .fluidOutputs(GlucoseFermentationResidue.getFluid(2000))
                .buildAndRegister();


        DISTILLATION_RECIPES.recipeBuilder()
                .duration(800)
                .EUt(VA[MV])
                .output(FERTILIZER,5)
                .fluidInputs(GlucoseFermentationResidue.getFluid(10000))
                // 初馏分（低沸点组分）
                .fluidOutputs(Methanol.getFluid(800))        // 64.7℃
                .fluidOutputs(Acetaldehyde.getFluid(1500))   // 20.8℃（与乙醇形成共沸物）
                // 主馏分（目标产物）
                .fluidOutputs(OnePropanol.getFluid(1200))    // 97.1℃
                .fluidOutputs(Butanol.getFluid(1000))        // 117.7℃
                // 尾馏分（高沸点组分）
                .fluidOutputs(Isoamylalcohol.getFluid(800))  // 131℃（异戊醇）
                .fluidOutputs(AceticAcid.getFluid(2500))     // 118℃（需多级冷凝）
                .fluidOutputs(Creosote.getFluid(2000))       // 200-300℃（木质素衍生物）
                .buildAndRegister();

    }
}
