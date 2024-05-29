package keqing.gtqtcore.api.metaileentity.multiblock;


/**
 * Something which receives a cryogenic property from {@link ICryogenicProvider}.
 */
public interface ICryogenicReceiver {

    /**
     * @return the cryogenic provider for this receiver
     */
    ICryogenicProvider getCryogenicProvider();

    /**
     * @param cryogenicProvider the cryogenic provider to associate with this
     */
    void setCryogenicProvider(ICryogenicProvider cryogenicProvider);
}