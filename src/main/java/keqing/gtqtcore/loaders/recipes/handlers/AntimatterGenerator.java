package keqing.gtqtcore.loaders.recipes.handlers;

import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.ParticleHelper;

import static gregtech.api.GTValues.UEV;
import static gregtech.api.GTValues.V;
import static keqing.gtqtcore.api.utils.ParticleHelper.metaItemArray;

public class AntimatterGenerator {
    public static void init() {
        ParticleHelper.init();
        for (ParticleHelper.MetaItemWithFloat metaItemWithFloat : metaItemArray) {
            GTQTcoreRecipeMaps.ANTIMATTER_GENERATOR.recipeBuilder()
                    .input(metaItemWithFloat.item1)
                    .input(metaItemWithFloat.item2)
                    .EUt((int) V[UEV])
                    .duration(metaItemWithFloat.getDurationByTicket())
                    .buildAndRegister();
        }
    }
}
