package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;

import static gregtech.api.GTValues.HV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class AlgaeChain {
    public static void init() {
        AlgaeGroth(COMMON_ALGAE,40);
        AlgaeGroth(GREEN_ALGAE,60);
        AlgaeGroth(RED_ALGAE,60);
        AlgaeGroth(BROWN_ALGAE,80);
        AlgaeGroth(GOLD_ALGAE,80);

        SFM.recipeBuilder()
                .duration(100)
                .EUt(120)
                .fluidInputs(Biooil.getFluid(6000))
                .input(OrePrefix.dust,SodaAsh,10)
                .fluidOutputs(RawOil.getFluid(400))
                .fluidOutputs(RawOil.getFluid(400))
                .fluidOutputs(RawOil.getFluid(400))
                .fluidOutputs(RawOil.getFluid(400))
                .fluidOutputs(RawOil.getFluid(400))
                .fluidOutputs(RawOil.getFluid(400))
                .fluidOutputs(RawOil.getFluid(200))
                .fluidOutputs(RawOil.getFluid(200))
                .fluidOutputs(RawOil.getFluid(200))
                .fluidOutputs(Biomass.getFluid(200))
                .fluidOutputs(Biomass.getFluid(200))
                .fluidOutputs(Biomass.getFluid(200))
                .buildAndRegister();

    }

    private static void AlgaeGroth(MetaItem.MetaValueItem items,int rate) {
        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(SterileGrowthMedium.getFluid(100))
                .notConsumable(items)
                .chancedOutput(items,4000,500)
                .rate(rate)
                .duration(400).EUt(VA[HV]).buildAndRegister();

        GANTRY_CRANE.recipeBuilder()
                .fluidInputs(Water.getFluid(4000))
                .input(items,4)
                .fluidOutputs(Biooil.getFluid(1000))
                .duration(800).EUt(120).buildAndRegister();
    }
}
