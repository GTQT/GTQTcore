package keqing.gtqtcore.common.metatileentities;

import gregicality.multiblocks.common.metatileentities.multiblockpart.MetaTileEntityParallelHatch;
import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityFluidHatch;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiFluidHatch;
import gregtech.common.metatileentities.storage.MetaTileEntityQuantumTank;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.common.metatileentities.multi.generators.MetaTileEntityLightningRod;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityBlazingBlastFurnace;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityHugeChemicalReactor;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static keqing.gtqtcore.api.GTQTValue.gtqtcoreId;

public class GTQTMetaTileEntities {

    public static MetaTileEntityBlazingBlastFurnace BLAZING_BLAST_FURNACE ;
    public static MetaTileEntityHugeChemicalReactor HUGE_CHEMICAL_REACTOR;

    public static MetaTileEntityLightningRod[] LIGHTNING_ROD = new MetaTileEntityLightningRod[3];

    public static void initialization() {
        GTQTLog.logger.info("Registering MetaTileEntities");

        //SingleBlock: 20000 - 20300
        LIGHTNING_ROD[0] = registerSingleMetaTileEntity(0, new MetaTileEntityLightningRod(gtqtcoreId("lightning_rod.zpm"), GTValues.ZPM));
        LIGHTNING_ROD[1] = registerSingleMetaTileEntity(1, new MetaTileEntityLightningRod(gtqtcoreId("lightning_rod.luv"), GTValues.LuV));
        LIGHTNING_ROD[2] = registerSingleMetaTileEntity(2, new MetaTileEntityLightningRod(gtqtcoreId("lightning_rod.iv"), GTValues.IV));

        HUGE_CHEMICAL_REACTOR = registerMetaTileEntity(3000, new MetaTileEntityHugeChemicalReactor(gtqtcoreId("huge_chemical_reactor")));
        BLAZING_BLAST_FURNACE = registerMetaTileEntity(3001, new MetaTileEntityBlazingBlastFurnace(gtqtcoreId("blazing_blast_furnace")));

       registerMetaTileEntity(3100, new MetaTileEntityQuantumTank(gtqtcoreId("quantum_tank.uev"), 6,114514));

        registerMetaTileEntity(3105, new MetaTileEntityFluidHatch(gtqtcoreId("fluid_hatch.import.uev"), 1, false));
        registerMetaTileEntity(3120, new MetaTileEntityFluidHatch(gtqtcoreId("fluid_hatch.export.uev"), 1, true));

        registerMetaTileEntity(3122, new MetaTileEntityMultiFluidHatch(gtqtcoreId("fluid_hatch.import_16x"), 4, false));
        registerMetaTileEntity(3124, new MetaTileEntityMultiFluidHatch(gtqtcoreId("fluid_hatch.export_16x"), 4, true));

        registerMetaTileEntity(3129, new MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[9])), 9));
        registerMetaTileEntity(3130, new MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[10])), 10));
        registerMetaTileEntity(3131, new MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[11])), 11));
        registerMetaTileEntity(3132, new MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[12])), 12));

    }

    private static <T extends MetaTileEntity> T registerSingleMetaTileEntity(int id, T mte) {
        if (id > 300) return null;
        return MetaTileEntities.registerMetaTileEntity(id + 20000, mte);
    }

}
