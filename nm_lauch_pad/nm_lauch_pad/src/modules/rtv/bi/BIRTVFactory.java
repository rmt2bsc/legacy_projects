package modules.rtv.bi;

import modules.rtv.RtvDao;

import com.nv.security.SecurityToken;

/**
 * A factory for creating BI (Buyer Initiated) RTV Administration related
 * objects.
 * 
 * @author rterrell
 *
 */
public class BIRTVFactory {

    /**
     * Creates a BIRTVFactory.
     */
    public BIRTVFactory() {
        return;
    }

    /**
     * Creates an instance of RtvDao interface using the
     * {@link BIRtvIbatisDaoImpl} implementation.
     * 
     * @return an instance of {@link RtvDao}
     */
    public RtvDao getDaoInstance(SecurityToken token) {
        return new BIRtvIbatisDaoImpl(token);
    }

}
