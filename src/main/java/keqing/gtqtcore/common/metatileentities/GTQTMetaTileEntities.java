package keqing.gtqtcore.common.metatileentities;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.common.metatileentities.multi.generators.MetaTileEntityLightningRod;

import static keqing.gtqtcore.api.GTQTValue.gtqtcoreId;

public class GTQTMetaTileEntities {
    public static MetaTileEntityLightningRod[] LIGHTNING_ROD = new MetaTileEntityLightningRod[3];

    public static void initialization() {
        GTQTLog.logger.info("Registering MetaTileEntities");

        //SingleBlock: 20000 - 20300
        LIGHTNING_ROD[0] = registerSingleMetaTileEntity(0, new MetaTileEntityLightningRod(gtqtcoreId("lightning_rod.zpm"), GTValues.ZPM));
        LIGHTNING_ROD[1] = registerSingleMetaTileEntity(1, new MetaTileEntityLightningRod(gtqtcoreId("lightning_rod.luv"), GTValues.LuV));
        LIGHTNING_ROD[2] = registerSingleMetaTileEntity(2, new MetaTileEntityLightningRod(gtqtcoreId("lightning_rod.iv"), GTValues.IV));

    }

    private static <T extends MetaTileEntity> T registerSingleMetaTileEntity(int id, T mte) {
        if (id > 300) return null;
        return MetaTileEntities.registerMetaTileEntity(id + 20000, mte);
    }

    public static void init() {}

}
