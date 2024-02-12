package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.GTRecipeHandler;
import net.minecraftforge.fluids.FluidStack;

import static gregicality.science.api.recipes.GCYSRecipeMaps.DRYER_RECIPES;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.CRYOGENIC_REACTOR;

public class NaquadahChain {
    public static void init() {

        //  Enriched Naquadah Chain
        GTRecipeHandler.removeRecipesByInputs(CENTRIFUGE_RECIPES,
                new FluidStack[]{ImpureEnrichedNaquadahSolution.getFluid(2000)}
        );

        GTRecipeHandler.removeRecipesByInputs(CENTRIFUGE_RECIPES,
                new FluidStack[]{EnrichedNaquadahSolution.getFluid(3000)}
        );

        //  Impure Enriched Naquadah Solution + Fluorine -> Hexafluoride Enriched Naquadah Solution
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(ImpureEnrichedNaquadahSolution.getFluid(1000))
                .fluidInputs(Fluorine.getFluid(6000))
                .fluidOutputs(HexafluorideEnrichedNaquadahSolution.getFluid(1000))
                .EUt(VA[IV])
                .duration(400)
                .buildAndRegister();

        //  Hexafluoride Enriched Naquadah Solution + Xenon -> Xenon Hexafluoro Enriched Naquadate
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(HexafluorideEnrichedNaquadahSolution.getFluid(1000))
                .fluidInputs(Xenon.getFluid(1000))
                .fluidOutputs(XenonHexafluoroEnrichedNaquadate.getFluid(1000))
                .EUt(VA[HV])
                .duration(200)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Palladium on Carbon + Gold Trifluoride + Xenon Hexafluoro Enriched Naquadate + Fluoroantimonic Acid + Hydrogen -> Enriched Naquadah Solution + Enriched Naquadah Residue Solution + Hydrofluoric Acid
        CHEMICAL_PLANT.recipeBuilder()
                .notConsumable(dust, PalladiumOnCarbon)
                .input(dust, GoldTrifluoride, 4)
                .fluidInputs(XenonHexafluoroEnrichedNaquadate.getFluid(1000))
                .fluidInputs(FluoroantimonicAcid.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(9000))
                .fluidOutputs(EnrichedNaquadahSolution.getFluid(1000))
                .fluidOutputs(EnrichedNaquadahResidueSolution.getFluid(1000))
                .fluidOutputs(HydrofluoricAcid.getFluid(8000))
                .EUt(VA[LuV])
                .duration(1200)
                .buildAndRegister();

        //  Enriched Naquadah Residue Solution -> Trinium Sulfide + Xenoauric Fluoroantimonic Acid
        DRYER_RECIPES.recipeBuilder()
                .fluidInputs(EnrichedNaquadahResidueSolution.getFluid(2000))
                .output(dust, TriniumSulfide)
                .fluidOutputs(XenoauricFluoroantimonicAcid.getFluid(1000))
                .EUt(VA[EV])
                .duration(480)
                .buildAndRegister();

        //  Xenoauric Fluoroantimonic Acid Cycle
        ELECTROLYZER_RECIPES.recipeBuilder()
                .fluidInputs(XenoauricFluoroantimonicAcid.getFluid(1000))
                .output(dust, Gold)
                .output(dust, AntimonyTrifluoride)
                .fluidOutputs(Xenon.getFluid(1000))
                .fluidOutputs(HydrofluoricAcid.getFluid(3000))
                .EUt(VA[IV])
                .duration(1200)
                .buildAndRegister();

        //  Gold Chloride + Bromine Trifluoride -> Gold Trifluoride + Bromine + Chlorine
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(GoldChloride.getFluid(1000))
                .fluidInputs(BromineTrifluoride.getFluid(2000))
                .output(dust, GoldTrifluoride, 8)
                .fluidOutputs(Bromine.getFluid(2000))
                .fluidOutputs(Chlorine.getFluid(6000))
                .EUt(VA[MV])
                .duration(200)
                .buildAndRegister();

        //  Bromine + Chlorine -> Bromine Trifluoride
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Bromine.getFluid(1000))
                .fluidInputs(Chlorine.getFluid(3000))
                .circuitMeta(3)
                .fluidOutputs(BromineTrifluoride.getFluid(1000))
                .EUt(VA[LV])
                .duration(120)
                .buildAndRegister();

        //  Gold + Chlorine -> Gold Chloride
        ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust, Gold, 2)
                .fluidInputs(Chlorine.getFluid(6000))
                .fluidOutputs(GoldChloride.getFluid(1000))
                .EUt(VA[MV])
                .duration(360)
                .buildAndRegister();

        //  Naquadria Chain
        GTRecipeHandler.removeRecipesByInputs(CENTRIFUGE_RECIPES,
                new FluidStack[]{ImpureNaquadriaSolution.getFluid(2000)}
        );

        GTRecipeHandler.removeRecipesByInputs(CENTRIFUGE_RECIPES,
                new FluidStack[]{AcidicNaquadriaSolution.getFluid(3000)}
        );

        //  Impure Naquadria Solution + Fluorine -> Hexafluoride Naquadria Solution
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(ImpureNaquadriaSolution.getFluid(1000))
                .fluidInputs(Fluorine.getFluid(6000))
                .fluidOutputs(HexafluorideNaquadriaSolution.getFluid(1000))
                .EUt(VA[IV])
                .duration(400)
                .buildAndRegister();

        //  HexafluorideNaquadriaSolution + Radon Difluoride -> Radon Naquadria Octafluoride + Naquadria Residue Solution
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(HexafluorideNaquadriaSolution.getFluid(1000))
                .fluidInputs(RadonDifluoride.getFluid(1000))
                .fluidOutputs(RadonNaquadriaOctafluoride.getFluid(1000))
                .fluidOutputs(NaquadriaResidueSolution.getFluid(1000))
                .EUt(VA[HV])
                .duration(800)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Radon + Fluorine -> Radon Difluoride
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Radon.getFluid(1000))
                .fluidInputs(Fluorine.getFluid(2000))
                .circuitMeta(2)
                .fluidOutputs(RadonDifluoride.getFluid(1000))
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        //  Naquadria Residue Solution -> Indium Phosphide + Naquadria Solution
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(NaquadriaResidueSolution.getFluid(2000))
                .output(dust, IndiumPhosphide)
                .fluidOutputs(NaquadriaSolution.getFluid(1000))
                .EUt(VA[IV])
                .duration(1200)
                .temperature(880)
                .buildAndRegister();

        //  Naquadria Solution -> Sulfur + Naquadria Waste + Sulfur dust
        DRYER_RECIPES.recipeBuilder()
                .fluidInputs(NaquadriaSolution.getFluid(3000))
                .output(dust, Sulfur, 6)
                .fluidOutputs(NaquadriaWaste.getFluid(1000))
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();

        //  Caesium Xenontrioxide Fluoride
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(CaesiumFluoride.getFluid(1000))
                .fluidInputs(XenonTrioxide.getFluid(1000))
                .fluidOutputs(CaesiumXenontrioxideFluoride.getFluid(1000))
                .EUt(VA[MV])
                .duration(480)
                .buildAndRegister();

        //  Caesium + Fluorine -> Cesium Fluoride
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Caesium)
                .fluidInputs(Fluorine.getFluid(1000))
                .circuitMeta(1)
                .fluidOutputs(CaesiumFluoride.getFluid(1000))
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        //  Xenon + Oxygen -> Xenon Trioxide
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Xenon.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(3000))
                .fluidOutputs(XenonTrioxide.getFluid(1000))
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        //  Radon Naquadria Octafluoride + Caesium Xenontrioxide Fluoride -> Naquadria Caesium Xenonnonfluoride + Radon Trioxide
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(RadonNaquadriaOctafluoride.getFluid(1000))
                .fluidInputs(CaesiumXenontrioxideFluoride.getFluid(1000))
                .fluidOutputs(NaquadriaCaesiumXenonnonfluoride.getFluid(1000))
                .fluidOutputs(RadonTrioxide.getFluid(1000))
                .EUt(VA[IV])
                .duration(800)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Naquadria Caesium Xenonnonfluoride + Nitryl Fluoride -> Naquadria Caesiumfluoride + Nitrosonium Octafluoroxenate
        CRYOGENIC_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(NaquadriaCaesiumXenonnonfluoride.getFluid(1000))
                .fluidInputs(NitrylFluoride.getFluid(2000))
                .fluidOutputs(NaquadriaCaesiumfluoride.getFluid(1000))
                .fluidOutputs(NitrosoniumOctafluoroxenate.getFluid(1000))
                .EUt(VA[EV])
                .duration(400)
                .temperature(75)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Nitrogen Dioxide + Fluorine -> Nitryl Fluoride
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(NitrogenDioxide.getFluid(1000))
                .fluidInputs(Fluorine.getFluid(1000))
                .fluidOutputs(NitrylFluoride.getFluid(1000))
                .EUt(VA[MV])
                .duration(160)
                .buildAndRegister();

        //  Sulfuric Acid + Naquadria Caesiumfluoride -> Acidic Naquadria Caesiumfluoride
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(SulfuricAcid.getFluid(2000))
                .fluidInputs(NaquadriaCaesiumfluoride.getFluid(1000))
                .fluidOutputs(AcidicNaquadriaCaesiumfluoride.getFluid(3000))
                .EUt(VA[IV])
                .duration(360)
                .buildAndRegister();

        //  Acidic Naquadria Caesiumfluoride -> Naquadria Sulfate + Caesium + Fluorine
        ELECTROLYZER_RECIPES.recipeBuilder()
                .fluidInputs(AcidicNaquadriaCaesiumfluoride.getFluid(1000))
                .output(dust, NaquadriaSulfate)
                .output(dust, Caesium)
                .fluidOutputs(Fluorine.getFluid(3000))
                .EUt(VA[LuV])
                .duration(120)
                .buildAndRegister();

        //  Acidic Naquadria Solution Cycle
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(AcidicNaquadriaSolution.getFluid(3000))
                .fluidOutputs(NaquadriaWaste.getFluid(1000))
                .fluidOutputs(ImpureEnrichedNaquadahSolution.getFluid(1000))
                .EUt(VA[ZPM])
                .duration(1000)
                .temperature(1280)
                .buildAndRegister();
    }
}
