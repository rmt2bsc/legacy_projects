package modules.rtv.si;

import modules.rtv.RtvDao;

import com.nv.security.SecurityToken;

/**
 * A factory for creating SI (Store Initiated) RTV Administration related
 * objects.
 * 
 * @author rterrell
 *
 */
public class SIRTVFactory {

    /**
     * Creates a SIRTVFactory.
     */
    public SIRTVFactory() {
        return;
    }

    /**
     * Creates an instance of RtvDao interface using the
     * {@link SIRtvIbatisDaoImpl} implementation.
     * 
     * @return an instance of {@link RtvDao}
     */
    public RtvDao getDaoInstance(SecurityToken token) {
        return new SIRtvIbatisDaoImpl(token);
    }

}
