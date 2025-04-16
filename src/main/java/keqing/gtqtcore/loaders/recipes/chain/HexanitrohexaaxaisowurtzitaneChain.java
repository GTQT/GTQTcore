package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.unification.material.MarkerMaterials;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtechfoodoption.GTFOMaterialHandler.AceticAnhydride;
import static gregtechfoodoption.GTFOMaterialHandler.Glyoxal;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.NitrousAcid;
import static keqing.gtqtcore.api.unification.GTQTMaterials.SodiumAcetate;

public class HexanitrohexaaxaisowurtzitaneChain {
    public static void init() {
        //  Ammonium Carbonate
        ROASTER_RECIPES.recipeBuilder()
                .input(dust, Calcite, 5)
                .fluidInputs(AmmoniumSulfate.getFluid(1000))
                .output(dust, Gypsum, 6)
                .output(dust, AmmoniumCarbonate, 14)
                .EUt(VA[MV])
                .duration(270)
                .temperature(2700)
                .buildAndRegister();

        //  Ammonium Acetate
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, AmmoniumCarbonate, 14)
                .fluidInputs(AceticAcid.getFluid(2000))
                .output(dust, AmmoniumAcetate, 24)
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[EV])
                .duration(200)
                .buildAndRegister();

        //  Acetamide
        DRYER_RECIPES.recipeBuilder()
                .input(dust, AmmoniumAcetate, 12)
                .output(dust, Acetamide, 9)
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();

        //  Acetonitrile
        DRYER_RECIPES.recipeBuilder()
                .input(dust, Acetamide, 9)
                .output(dust, Acetonitrile, 6)
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();

        //  Hexamethylenetetramine
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Ammonia.getFluid(6000))
                .fluidInputs(Formaldehyde.getFluid(4000))
                .circuitMeta(1)
                .output(dust, Hexamethylenetetramine, 22)
                .fluidOutputs(Water.getFluid(6000))
                .EUt(VA[HV])
                .duration(340)
                .buildAndRegister();

        //  Benzyl Chloride
        ULTRAVIOLET_LAMP_CHAMBER_RECIPES.recipeBuilder()
                .notConsumable(craftingLens, MarkerMaterials.Color.LightBlue)
                .fluidInputs(Toluene.getFluid(1000))
                .fluidInputs(Chlorine.getFluid(1000))
                .fluidOutputs(BenzylChloride.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();

        //  Benzylamine
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Hexamethylenetetramine, 22)
                .fluidInputs(BenzylChloride.getFluid(1000))
                .fluidInputs(HydrochloricAcid.getFluid(2000))
                .fluidInputs(Water.getFluid(6000))
                .fluidOutputs(Benzylamine.getFluid(1000))
                .fluidOutputs(AmmoniumChloride.getFluid(3*144))
                .fluidOutputs(Formaldehyde.getFluid(6000))
                .EUt(VA[IV])
                .duration(200)
                .buildAndRegister();

        //  Hexabenzylhexaazaisowurtzitane
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Acetonitrile)
                .fluidInputs(Glyoxal.getFluid(3000))
                .fluidInputs(Benzylamine.getFluid(6000))
                .notConsumable(Hydrogen.getFluid(1))
                .output(dust, Hexabenzylhexaazaisowurtzitane)
                .EUt(VA[IV])
                .duration(100)
                .buildAndRegister();

        //  Succinic Anhydride
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust, CalciumChloride)
                .input(dust, SuccinicAcid, 14)
                .fluidInputs(AceticAnhydride.getFluid(1000))
                .output(dust, SuccinicAnhydride, 11)
                .fluidOutputs(AceticAcid.getFluid(2000))
                .EUt(VA[IV])
                .duration(120)
                .buildAndRegister();

        //  Potassium Carbonate
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, PotassiumHydroxide, 2)
                .fluidInputs(CarbonDioxide.getFluid(1000))
                .output(dust, PotassiumCarbonate, 6)
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[LV])
                .duration(90)
                .buildAndRegister();

        //  Potassium Bisulfite
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, PotassiumCarbonate, 6)
                .fluidInputs(SulfurDioxide.getFluid(2000))
                .fluidInputs(Water.getFluid(1000))
                .output(dust, PotassiumBisulfite, 12)
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .EUt(VA[HV])
                .duration(150)
                .buildAndRegister();

        //  Potassium Nitrite
        ROASTER_RECIPES.recipeBuilder()
                .input(dust, Saltpeter, 5)
                .input(dust, Galena)
                .output(dust, PotassiumNitrite, 4)
                .output(dust, Lead)
                .EUt(VA[HV])
                .duration(3600)
                .temperature(1300)
                .buildAndRegister();

        //  Nitrous Acid
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, PotassiumNitrite, 4)
                .input(dust, Salt, 2)
                .fluidInputs(AceticAcid.getFluid(1000))
                .output(dust, RockSalt, 2)
                .fluidOutputs(NitrousAcid.getFluid(1000))
                .fluidOutputs(SodiumAcetate.getFluid(1000))
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();

        //  Potassium Hydroxylaminedisulfonate
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, PotassiumBisulfite, 12)
                .fluidInputs(NitrousAcid.getFluid(1000))
                .output(dust, PotassiumHydroxylaminedisulfonate, 13)
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[HV])
                .duration(120)
                .buildAndRegister();

        //  Hydroxylammonium Sulfate
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, PotassiumHydroxylaminedisulfonate, 26)
                .fluidInputs(Water.getFluid(4000))
                .output(dust, HydroxylammoniumSulfate, 17)
                .output(dust, PotassiumSulfate, 12)
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .EUt(VA[EV])
                .duration(200)
                .buildAndRegister();

        //  Barium Dichloride
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Barium)
                .fluidInputs(HydrochloricAcid.getFluid(2000))
                .output(dust, BariumDichloride, 3)
                .fluidOutputs(Hydrogen.getFluid(2000))
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        //  Hydroxylamine Hydrochloride
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, HydroxylammoniumSulfate, 17)
                .input(dust, BariumDichloride, 3)
                .fluidOutputs(HydroxylamineHydrochloride.getFluid(2000))
                .fluidOutputs(BariumSulfateSuspension.getFluid(1000))//TODO  subsequent content
                .EUt(VA[HV])
                .duration(120)
                .buildAndRegister();

        //  N-Hydroxysuccinimide
        CHEMICAL_PLANT.recipeBuilder()
                .input(dust, SuccinicAnhydride, 64)
                .input(dust, SuccinicAnhydride, 2)
                .input(dust, Sodium, 6)
                .fluidInputs(HydroxylamineHydrochloride.getFluid(6000))
                .fluidInputs(Toluene.getFluid(6000))
                .fluidInputs(Methanol.getFluid(40000))
                .output(dust, NHydroxysuccinimide, 13)
                .output(dust, Salt, 12)
                .fluidOutputs(Hydrogen.getFluid(6000))
                .fluidOutputs(Water.getFluid(6000))
                .EUt(VA[EV])
                .duration(400)
                .recipeLevel(4)
                .buildAndRegister();

        //  Tetrahydrofuran
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Acetylene.getFluid(1000))
                .fluidInputs(Formaldehyde.getFluid(2000))
                .fluidInputs(Hydrogen.getFluid(4000))
                .circuitMeta(2)
                .fluidOutputs(Tetrahydrofuran.getFluid(1000))
                .EUt(VA[IV])
                .duration(166)
                .buildAndRegister();

        //  Succinimidyl Acetate
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, NHydroxysuccinimide, 13)
                .notConsumable(Trimethylamine.getFluid(1))
                .notConsumable(Tetrahydrofuran.getFluid(1))
                .fluidInputs(AceticAnhydride.getFluid(1000))
                .output(dust, SuccinimidylAcetate, 18)
                .fluidOutputs(AceticAcid.getFluid(1000))
                .EUt(VA[IV])
                .duration(200)
                .buildAndRegister();

        //  Dibenzyltetraacetylhexaazaisowurtzitane (C28H32N6O4)
        CHEMICAL_PLANT.recipeBuilder()
                .notConsumable(dust, PalladiumOnCarbon)
                .input(dust, Hexabenzylhexaazaisowurtzitane)
                .input(dust, SuccinimidylAcetate, 64)
                .input(dust, SuccinimidylAcetate, 8)
                .fluidInputs(Hydrogen.getFluid(8000))
                .fluidInputs(Dimethylformamide.getFluid(1000))
                .fluidInputs(Ethylbenzene.getFluid(1000))
                .fluidInputs(HydrobromicAcid.getFluid(1000))
                .output(dust, Dibenzyltetraacetylhexaazaisowurtzitane)
                .output(dust, Succinimide, 48)
                .fluidOutputs(Toluene.getFluid(4000))
                .fluidOutputs(Dimethylformamide.getFluid(1000))
                .fluidOutputs(Ethylbenzene.getFluid(1000))
                .fluidOutputs(HydrobromicAcid.getFluid(1000))
                .EUt(VA[IV])
                .duration(220)
                .recipeLevel(5)
                .buildAndRegister();


        //  Nitrogen Monoxide related recipes
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(NitrogenDioxide.getFluid(3000))
                .fluidInputs(Water.getFluid(1000))
                .circuitMeta(4)
                .fluidOutputs(NitrogenMonoxide.getFluid(1000))
                .fluidOutputs(NitricAcid.getFluid(2000))
                .EUt(VA[HV])
                .duration(300)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Ammonia.getFluid(4000))
                .fluidInputs(Oxygen.getFluid(10000))
                .fluidOutputs(NitrogenMonoxide.getFluid(4000))
                .fluidOutputs(Water.getFluid(6000))
                .EUt(VA[HV])
                .duration(300)
                .buildAndRegister();

        //  Dibenzyltetraacetylhexaazaisowurtzitane (C28H32N6O4) -> Tetraacetyldinitrosohexaazaisowurtzitane (C14H18N8O6)
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Dibenzyltetraacetylhexaazaisowurtzitane)
                .input(dust, NitrosoniumTetrafluoroborate, 42)
                .fluidInputs(Water.getFluid(2000))
                .output(dust, Tetraacetyldinitrosohexaazaisowurtzitane)
                .fluidOutputs(TetrafluoroboricAcid.getFluid(6000))
                .fluidOutputs(NitrogenMonoxide.getFluid(4000))
                .fluidOutputs(Benzaldehyde.getFluid(2000))
                .EUt(VA[IV])
                .duration(120)
                .buildAndRegister();

        //  Nitronium Tetrafluoroborate
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(BoronTrifluoride.getFluid(1000))
                .fluidInputs(HydrofluoricAcid.getFluid(1000))
                .fluidInputs(NitricAcid.getFluid(1000))
                .output(dust, NitroniumTetrafluoroborate, 8)
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[EV])
                .duration(100)
                .buildAndRegister();

        //  Nitrosonium Tetrafluoroborate
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(BoronTrifluoride.getFluid(2000))
                .fluidInputs(HydrofluoricAcid.getFluid(2000))
                .fluidInputs(DinitrogenTetroxide.getFluid(2000))
                .output(dust, NitrosoniumTetrafluoroborate, 7)
                .fluidOutputs(NitricAcid.getFluid(1000))
                .EUt(VA[EV])
                .duration(100)
                .buildAndRegister();

        //  Tetrafluoroboric Acid Cycle
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(TetrafluoroboricAcid.getFluid(1000))
                .fluidInputs(Water.getFluid(3000))
                .fluidOutputs(HydrofluoricAcid.getFluid(4000))
                .fluidOutputs(BoricAcid.getFluid(1000))
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        //  Tetraacetyldinitrosohexaazaisowurtzitane (C14H18N8O6) -> Crude Hexanitrohexaaxaisowurtzitane (C6H6N12O12)
        CHEMICAL_PLANT.recipeBuilder()
                .input(dust, Tetraacetyldinitrosohexaazaisowurtzitane)
                .input(dust, NitroniumTetrafluoroborate, 48)
                .fluidInputs(Water.getFluid(4000))
                .output(dust, CrudeHexanitrohexaaxaisowurtzitane)
                .output(dust, NitrosoniumTetrafluoroborate, 14)
                .fluidOutputs(TetrafluoroboricAcid.getFluid(4000))
                .fluidOutputs(AceticAcid.getFluid(4000))
                .EUt(VA[IV])
                .duration(250)
                .recipeLevel(5)
                .buildAndRegister();

        //  Silicon Dioxide + Sodium Hydroxide + Hydrochloric Acid + Steam -> Silica Gel Base
        MIXER_RECIPES.recipeBuilder()
                .input(dust, SiliconDioxide, 3)
                .input(dust, SodiumHydroxide, 3)
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .fluidInputs(Steam.getFluid(1000))
                .fluidOutputs(SilicaGelBase.getFluid(1000))
                .EUt(VA[MV])
                .duration(120)
                .buildAndRegister();

        //  Silica Gel Base -> Silica Gel + Salt
        DRYER_RECIPES.recipeBuilder()
                .fluidInputs(SilicaGelBase.getFluid(1000))
                .output(dust, SilicaGel, 3)
                .output(dust, Salt, 2)
                .EUt(VA[HV])
                .duration(130)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Crude Hexanitrohexaaxaisowurtzitane (C6H6N12O12) + Silica Gel + -> Hexanitrohexaaxaisowurtzitane (C6H6N12O12)
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, CrudeHexanitrohexaaxaisowurtzitane)
                .input(dust, SilicaGel)
                .fluidInputs(Ethylenediamine.getFluid(1000))
                .output(dust, Hexanitrohexaaxaisowurtzitane)
                .EUt(VA[IV])
                .duration(100)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
    }

}
