package modules.transfer.bi;

import modules.transfer.TransferDao;

import com.nv.security.SecurityToken;

/**
 * A factory for creating BI (Buyer Initiated) Transfer Administration related
 * objects.
 * 
 * @author rterrell
 *
 */
public class BITransferFactory {

    /**
     * Creates a BITransferFactory.
     */
    public BITransferFactory() {
        return;
    }

    /**
     * Creates an instance of TransferDao interface using the
     * {@link BITransferIbatisDaoImpl} implementation.
     * 
     * @return an instance of {@link TransferDao}
     */
    public TransferDao getDaoInstance(SecurityToken token) {
        return new BITransferIbatisDaoImpl(token);
    }

}
