package modules.transfer.si;

import modules.transfer.TransferDao;

import com.nv.security.SecurityToken;

/**
 * A factory for creating SI (Store Initiated) Transfer Administration related
 * objects.
 * 
 * @author rterrell
 *
 */
public class SITransferFactory {

    /**
     * Creates a SITransferFactory.
     */
    public SITransferFactory() {
        return;
    }

    /**
     * Creates an instance of TransferDao interface using the
     * {@link SITransferIbatisDaoImpl} implementation.
     * 
     * @return an instance of {@link TransferDao}
     */
    public TransferDao getDaoInstance(SecurityToken token) {
        return new SITransferIbatisDaoImpl(token);
    }

}
