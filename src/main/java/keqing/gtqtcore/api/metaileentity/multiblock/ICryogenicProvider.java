package keqing.gtqtcore.api.metaileentity.multiblock;

import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.util.BlockInfo;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.init.Blocks;

import java.util.Objects;

/**
 * Something with provides a cryogenic property to {@link ICryogenicReceiver}.
 */
public interface ICryogenicProvider {

    /**
     * @param receiver the receiver to associate with this provider
     */
    void setReceiver( ICryogenicReceiver receiver);

    default  TraceabilityPredicate cryogenicRecieverPredicate() {
        return new TraceabilityPredicate(blockWorldState -> {
            if (blockWorldState.getTileEntity() instanceof IGregTechTileEntity igtte &&
                    igtte.getMetaTileEntity() instanceof ICryogenicReceiver receiver) {
                if (receiver.getCryogenicProvider() == null) {
                    receiver.setCryogenicProvider(this);
                    setReceiver(receiver);
                } else if (receiver.getCryogenicProvider() != this) {
                    GTQTLog.logger.warn("ICryogenicReceiver has >1 provider. This should not happen.");
                }
                return true;
            }
            return MultiblockControllerBase.air().test(blockWorldState);
        }, () -> {
            MetaTileEntityHolder holder = new MetaTileEntityHolder();
            holder.setMetaTileEntity(GTQTMetaTileEntities.BATH_CONDENSER[0]);
            holder.getMetaTileEntity().onPlacement();
            BlockInfo[] info = new BlockInfo[2];
            info[0] = new BlockInfo(MetaBlocks.MACHINE.getDefaultState(), holder);
            info[1] = new BlockInfo(Objects.requireNonNull(Blocks.AIR));
            return info;
        });
    }

    boolean isStructureFormed();
}