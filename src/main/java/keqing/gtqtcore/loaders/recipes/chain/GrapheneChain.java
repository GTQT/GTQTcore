package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import keqing.gtqtcore.common.items.GTQTMetaItems;

import static gregicality.multiblocks.api.unification.GCYMMaterials.HSLASteel;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.PRECISE_ASSEMBLER_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.common.items.GTQTMetaItems.MTE_COPY_CARD;
import static keqing.gtqtcore.common.items.GTQTMetaItems.SPINNER;

public class GrapheneChain {

    public static void init() {
        // Remove Graphene Mixing
            GTRecipeHandler.removeRecipesByInputs(RecipeMaps.MIXER_RECIPES,
                    OreDictUnifier.get(dust, Graphite),
                    OreDictUnifier.get(dust, Carbon, 4),
                    OreDictUnifier.get(dust, Silicon),
                    IntCircuitIngredient.getIntegratedCircuit(1));


        // G -> GO
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Graphite)
                .fluidInputs(NitrationMixture.getFluid(2000))
                .notConsumable(dust, SodiumHydroxide)
                .output(dust, GrapheneOxide)
                .fluidOutputs(DilutedSulfuricAcid.getFluid(1000))
                .fluidOutputs(NitricAcid.getFluid(1000))
                .duration(100).EUt(VA[HV]).buildAndRegister();

        // G -> G
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Graphite)
                .fluidInputs(NitrationMixture.getFluid(2000))
                .notConsumable(GTQTMetaItems.MAGNETRON.getStackForm())
                .output(dust, Graphene)
                .fluidOutputs(DilutedSulfuricAcid.getFluid(1000))
                .fluidOutputs(NitricAcid.getFluid(1000))
                .duration(100).EUt(VA[HV]).buildAndRegister();

        // GO -> G
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, GrapheneOxide)
                .fluidInputs(Hydrazine.getFluid(100))
                .fluidInputs(Argon.getFluid(50))
                .output(dust, Graphene)
                .duration(100).EUt(VA[HV]).buildAndRegister();

        // Peroxide Process

        // 2NH3 + H2O2 -> N2H4 + 2H2O
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Ammonia.getFluid(2000))
                .fluidInputs(HydrogenPeroxide.getFluid(1000))
                .fluidOutputs(Hydrazine.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .duration(80).EUt(VA[HV]).buildAndRegister();

        // Magnetron

        // Be + O -> BeO
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Beryllium)
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust, BerylliumOxide, 2)
                .duration(60).EUt(VA[LV]).buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .CWUt(CWT[ZPM]).Tier(2)
                .input(ring, BerylliumOxide, 64)
                .input(ring, BerylliumOxide, 64)
                .input(plate, HSLASteel, 6)
                .inputs(MetaItems.VACUUM_TUBE.getStackForm())
                .fluidInputs(Naquadria.getFluid(1440))
                .fluidInputs(VanadiumGallium.getFluid(1440))
                .outputs(GTQTMetaItems.MAGNETRON.getStackForm())
                .duration(600).EUt(VA[ZPM]).buildAndRegister();

        ModHandler.addShapedRecipe(true, "spinner", SPINNER.getStackForm(),
                "SF ", "DPR", "SF ",
                'R', new UnificationEntry(ring, WroughtIron),
                'P', new UnificationEntry(pipeSmallItem, Cobalt),
                'D', new UnificationEntry(plate, Brass),
                'S', new UnificationEntry(screw, Bronze),
                'F', new UnificationEntry(foil, Nickel));
        // TODO LIG Graphene
    }
}
