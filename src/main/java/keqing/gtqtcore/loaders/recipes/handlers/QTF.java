package keqing.gtqtcore.loaders.recipes.handlers;

import static gregicality.multiblocks.api.unification.GCYMMaterials.*;
import static gregicality.science.api.unification.materials.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.QUANTUM_FORCE_TRANSFORMER_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.*;

public class QTF {
    public static void init() {
        QUANTUM_FORCE_TRANSFORMER_RECIPES.recipeBuilder()
                .fluidInputs(Hydrogen.getFluid(26000))
                .fluidInputs(Oxygen.getFluid(2000))
                .fluidInputs(Fluorine.getFluid(4000))
                .fluidInputs(Nitrogen.getFluid(6000))
                .fluidInputs(Carbon.getFluid(38000))
                .fluidOutputs(Polyethylene.getFluid(1000))
                .fluidOutputs(Polytetrafluoroethylene.getFluid(1000))
                .fluidOutputs(Polybenzimidazole.getFluid(1000))
                .fluidOutputs(Kevlar.getFluid(1000))
                .duration(360)
                .EUt(122880)
                .CasingTier(1)
                .buildAndRegister();
        //  Rubber-T1: Silicone Rubber, Styrene Butadiene Rubber, Polyphenylene Sulfide, Rubber
        QUANTUM_FORCE_TRANSFORMER_RECIPES.recipeBuilder()
                .input(dust, Carbon, 64)
                //.notConsumable(plate, Rhugnor)
                .circuitMeta(1)
                .fluidInputs(Chlorine.getFluid(16000))
                .fluidInputs(Hydrogen.getFluid(16000))
                .fluidInputs(Oxygen.getFluid(16000))
                .fluidOutputs(SiliconeRubber.getFluid(9216))
                .fluidOutputs(StyreneButadieneRubber.getFluid(9216))
                .fluidOutputs(PolyphenyleneSulfide.getFluid(18432))
                .fluidOutputs(Rubber.getFluid(36864))
                .EUt(VA[ZPM])
                .duration(400)
                .CasingTier(1)
                .buildAndRegister();

        //  Rubber-T2: Nitrile Butadiene Rubber, Poly (Phosphonitrile Fluoro) Rubber, Polyetheretherketone, Rubber
        QUANTUM_FORCE_TRANSFORMER_RECIPES.recipeBuilder()
                .input(dust, Carbon, 64)
                //.notConsumable(plate, Hypogen)
                .circuitMeta(10)
                .fluidInputs(Chlorine.getFluid(16000))
                .fluidInputs(Hydrogen.getFluid(16000))
                .fluidInputs(Oxygen.getFluid(16000))
                .fluidOutputs(NitrileButadieneRubber.getFluid(9216))
                .fluidOutputs(PolyPhosphonitrileFluoroRubber.getFluid(9216))
                .fluidOutputs(Polyetheretherketone.getFluid(18432))
                .fluidOutputs(Rubber.getFluid(36864))
                .EUt(VA[UV])
                .duration(400)
                .CasingTier(2)
                .buildAndRegister();

        //  Plastic-T1: Polyethylene, Polyvinyl Chloride, Polystyrene, Polytetrafluoroethylene, Epoxy, Polybenzimidazole
        QUANTUM_FORCE_TRANSFORMER_RECIPES.recipeBuilder()
                .input(dust, Carbon, 64)
                //.notConsumable(plate, CrystalMatrix)
                .circuitMeta(2)
                .fluidInputs(Chlorine.getFluid(16000))
                .fluidInputs(Hydrogen.getFluid(16000))
                .fluidInputs(Oxygen.getFluid(16000))
                .fluidInputs(Fluorine.getFluid(16000))
                .fluidOutputs(Polyethylene.getFluid(36864))
                .fluidOutputs(PolyvinylChloride.getFluid(18432))
                .fluidOutputs(Polystyrene.getFluid(9216))
                .fluidOutputs(Polytetrafluoroethylene.getFluid(18432))
                .fluidOutputs(Epoxy.getFluid(9216))
                .fluidOutputs(Polybenzimidazole.getFluid(9216))
                .EUt(VA[ZPM])
                .duration(400)
                .CasingTier(1)
                .buildAndRegister();

        //  Plastic-T2: Polybenzimidazole, Polystyrene Sulfonate, Fluorinated Ethylene Propylene, Zylon, Kevlar
        QUANTUM_FORCE_TRANSFORMER_RECIPES.recipeBuilder()
                .input(dust, Carbon, 64)
                .notConsumable(plate, Infinity)
                .circuitMeta(20)
                .fluidInputs(Chlorine.getFluid(16000))
                .fluidInputs(Hydrogen.getFluid(16000))
                .fluidInputs(Oxygen.getFluid(16000))
                .fluidInputs(Fluorine.getFluid(16000))
                .fluidOutputs(Epoxy.getFluid(36864))
                .fluidOutputs(Polybenzimidazole.getFluid(18432))
                .fluidOutputs(PolystyreneSulfonate.getFluid(9216))
                //.fluidOutputs(FluorinatedEthylenePropylene.getFluid(18432))
                //.fluidOutputs(Zylon.getFluid(9216))
                .fluidOutputs(Kevlar.getFluid(9216))
                .EUt(VA[UV])
                .duration(400)
                .CasingTier(2)
                .buildAndRegister();

        //  Platinum Group
        QUANTUM_FORCE_TRANSFORMER_RECIPES.recipeBuilder()
                .input(dust, PlatinumGroupSludge, 64)
                .notConsumable(plate, NaquadahAlloy)
                .circuitMeta(3)
                .output(dust, Platinum, 64)
                .output(dust, Palladium, 64)
                .output(dust, Iridium, 64)
                .output(dust, Osmium, 64)
                .output(dust, Rhodium, 64)
                .output(dust, Ruthenium, 64)
                .EUt(VA[UV])
                .duration(400)
                .CasingTier(1)
                .buildAndRegister();

        //  Thorium-Uranium Group
        QUANTUM_FORCE_TRANSFORMER_RECIPES.recipeBuilder()
                .input(dust, Uranium238, 32)
                .input(dust, Thorium, 32)
                .notConsumable(plate, Orichalcum)
                .circuitMeta(4)
                .output(dust, Uranium235, 64)
                .output(dust, Plutonium239, 64)
                .output(dust, Plutonium241, 64)
                .output(dust, Plutonium244, 64)
                .EUt(VA[UV])
                .duration(400)
                .CasingTier(1)
                .buildAndRegister();

        //  Titanium-Tungsten-Indium Group
        QUANTUM_FORCE_TRANSFORMER_RECIPES.recipeBuilder()
                .input(dust, Scheelite, 16)
                .input(dust, Bauxite, 32)
                .input(dust, Lead, 16)
                .notConsumable(plate, AstralTitanium)
                .circuitMeta(5)
                .output(dust, Titanium, 64)
                .output(dust, TungstenSteel, 64)
                .output(dust, TungstenCarbide, 64)
                .output(dust, Indium, 64)
                .EUt(VA[UV])
                .duration(400)
                .CasingTier(1)
                .buildAndRegister();

        QUANTUM_FORCE_TRANSFORMER_RECIPES.recipeBuilder()
                .input(dust, Tungstate, 16)
                .input(dust, Rutile, 32)
                .input(dust, Antimony, 16)
                .notConsumable(plate, CelestialTungsten)
                .circuitMeta(6)
                .output(dust, Tungsten, 64)
                .output(dust, NiobiumTitanium, 64)
                .output(dust, TitaniumCarbide, 64)
                .output(dust, Indium, 64)
                .EUt(VA[UV])
                .duration(400)
                .CasingTier(1)
                .buildAndRegister();

        //  Rare Earth Group
        QUANTUM_FORCE_TRANSFORMER_RECIPES.recipeBuilder()
                .input(dust, Bastnasite, 32)
                .notConsumable(plate, Tritanium)
                .circuitMeta(7)
                .output(dust, Lanthanum, 64)
                .output(dust, Cerium, 64)
                .output(dust, Praseodymium, 64)
                .output(dust, Neodymium, 64)
                .output(dust, Promethium, 64)
                .output(dust, Samarium, 64)
                .EUt(VA[UHV])
                .duration(400)
                .CasingTier(2)
                .buildAndRegister();

        QUANTUM_FORCE_TRANSFORMER_RECIPES.recipeBuilder()
                .input(dust, Monazite, 32)
                .notConsumable(plate, Adamantium)
                .circuitMeta(8)
                .output(dust, Europium, 64)
                .output(dust, Gadolinium, 64)
                .output(dust, Terbium, 64)
                .output(dust, Dysprosium, 64)
                .output(dust, Holmium, 64)
                .output(dust, Erbium, 64)
                .EUt(VA[UHV])
                .duration(400)
                .CasingTier(2)
                .buildAndRegister();

        QUANTUM_FORCE_TRANSFORMER_RECIPES.recipeBuilder()
                .input(dust, Bastnasite, 32)
                .input(dust, Monazite, 32)
                .notConsumable(plate, AwakenedDraconium)
                .circuitMeta(9)
                .output(dust, Thulium, 64)
                .output(dust, Ytterbium, 64)
                .output(dust, Lutetium, 64)
                .output(dust, Zirconium, 64)
                .output(dust, Hafnium, 64)
                .output(dust, Gallium, 64)
                .EUt(VA[UHV])
                .duration(400)
                .CasingTier(2)
                .buildAndRegister();

        //  Naquadah Group-T2
        QUANTUM_FORCE_TRANSFORMER_RECIPES.recipeBuilder()
                .input(dust, Naquadah, 32)
                .input(dust, Sodium, 64)
                .input(dust, Carbon, 16)
                .notConsumable(plate, Vibranium)
                .circuitMeta(10)
                .fluidInputs(Oxygen.getFluid(16000))
                .fluidInputs(Fluorine.getFluid(64000))
                .fluidInputs(Hydrogen.getFluid(64000))
                .output(dust, NaquadahEnriched, 64)
                .output(dust ,Naquadria, 64)
                .output(dust, Trinium, 64)
                .output(dust, Taranium, 64)
                .output(dust, Orichalcum, 64)
                .output(dust, Adamantium, 64)
                .EUt(VA[UEV])
                .duration(200)
                .CasingTier(2)
                .buildAndRegister();

        //  Naquadah Group-T3
        QUANTUM_FORCE_TRANSFORMER_RECIPES.recipeBuilder()
                .input(dust, Naquadah, 32)
                .input(dust, Bedrock, 32)
                .input(dust, Sodium, 64)
                .input(dust ,Carbon, 16)
                .notConsumable(plate, ChaoticDraconium)
                .circuitMeta(20)
                .fluidInputs(Oxygen.getFluid(16000))
                .fluidInputs(Helium.getFluid(16000))
                .fluidInputs(Fluorine.getFluid(64000))
                .fluidInputs(Hydrogen.getFluid(64000))
                .output(dust, Orichalcum, 64)
                .output(dust, Adamantium, 64)
                .output(dust, Vibranium, 64)
                .output(dust, Taranium, 64)
                .output(dust, AstralTitanium, 64)
                .output(dust, CelestialTungsten, 64)
                .EUt(VA[UIV])
                .duration(200)
                .CasingTier(3)
                .buildAndRegister();

        //  Biological Items
        //  TODO If more biological materials are added later, these need to be replaced
        QUANTUM_FORCE_TRANSFORMER_RECIPES.recipeBuilder()
                .input(dust, Meat, 64)
                .input(dust, Calcium, 16)
                .input(dust, Carbon, 16)
                .notConsumable(plate, KaptonE)
                .circuitMeta(11)
                .fluidInputs(Hydrogen.getFluid(16000))
                .fluidInputs(Nitrogen.getFluid(16000))
                .fluidInputs(Oxygen.getFluid(16000))
                .output(STEM_CELLS, 64)
                .output(STEM_CELLS, 64)
                .output(STEM_CELLS, 64)
                .output(STEM_CELLS, 64)
                .output(STEM_CELLS, 64)
                .output(STEM_CELLS, 64)
                .fluidOutputs(SterileGrowthMedium.getFluid(64000))
                .fluidOutputs(RawGrowthMedium.getFluid(64000))
                .fluidOutputs(BZMedium.getFluid(16000))
                .EUt(VA[UEV])
                .duration(400)
                .CasingTier(3)
                .buildAndRegister();
/*
        //  Adhesives
        QUANTUM_FORCE_TRANSFORMER_RECIPES.recipeBuilder()
                .input(dust, Carbon, 32)
                .notConsumable(plate, Polyetheretherketone)
                .circuitMeta(12)
                .fluidInputs(Hydrogen.getFluid(16000))
                .fluidInputs(Oxygen.getFluid(16000))
                .chancedOutput(METASTABLE_SELF_HEALING_ADHESIVE, 1, 2000, 0)
                .fluidOutputs(Glue.getFluid(64000))
                .fluidOutputs(SolderingAlloy.getFluid(18432))
                .EUt(VA[UV])
                .duration(400)
                .CasingTier(1)
                .buildAndRegister();

        //  Higgs Bosons
        QUANTUM_FORCE_TRANSFORMER_RECIPES.recipeBuilder()
                .input(QUANTUM_ANOMALY)
                .notConsumable(plate, MagnetoHydrodynamicallyConstrainedStarMatter)
                .fluidInputs(Americium.getFluid(30000))
                .fluidInputs(Helium.getPlasma(30000))
                .fluidInputs(Oxygen.getPlasma(30000))
                .fluidInputs(CelestialTungsten.getPlasma(30000))
                .chancedOutput(HYPERDIMENSIONAL_TACHYON_CONDENSED_MATTER, 1, 2000, 0)
                .fluidOutputs(HiggsBosons.getFluid(3000))
                .EUt(VA[UEV])
                .duration(400)
                .CasingTier(2)
                .buildAndRegister();

        //  Instantons
        QUANTUM_FORCE_TRANSFORMER_RECIPES.recipeBuilder()
                .input(QUANTUM_ANOMALY)
                .notConsumable(plate, NeutronStarCoreMaterial)
                .fluidInputs(Neutron.getPlasma(30000))
                .fluidInputs(Iron.getPlasma(30000))
                .fluidInputs(Nickel.getPlasma(30000))
                .fluidInputs(AstralTitanium.getPlasma(30000))
                .fluidOutputs(Instantons.getFluid(3000))
                .EUt(VA[UEV])
                .duration(400)
                .CasingTier(2)
                .buildAndRegister();
*/
        //  TODO Temporal Fluid
    }
}
