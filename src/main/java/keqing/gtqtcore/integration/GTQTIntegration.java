package keqing.gtqtcore.integration;

import keqing.gtqtcore.integration.theoneprobe.*;
import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;

public class GTQTIntegration {

    public static void init() {

        ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;
        oneProbe.registerProvider(new MultiblockCoreProvider());
        oneProbe.registerProvider(new MultiblockFaceProvider());
        oneProbe.registerProvider(new ElectricContainerIOInfoProvider());
        oneProbe.registerProvider(new CableInfoProvider());
        oneProbe.registerProvider(new MultiblockTemperatureProvider());
        oneProbe.registerProvider(new RecipeParallelInfoProvider());
        oneProbe.registerProvider(new RecipeItemOutputInfoProvider());
        oneProbe.registerProvider(new RecipeFluidOutputInfoProvider());
    }


    public GTQTIntegration() {}
}

