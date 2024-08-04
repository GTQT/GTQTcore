package keqing.gtqtcore.integration;

import keqing.gtqtcore.integration.theoneprobe.*;
import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;

public class GTQTIntegration {

    public static void init() {

        ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;
        oneProbe.registerProvider(new MultiblockTemperatureProvider());
        oneProbe.registerProvider(new MultiblockCoreProvider());
        oneProbe.registerProvider(new EvaporationPoolInfoProvider());
    }


    public GTQTIntegration() {}
}

