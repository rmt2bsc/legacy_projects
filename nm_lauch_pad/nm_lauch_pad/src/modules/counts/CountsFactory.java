package modules.counts;

import com.nv.security.SecurityToken;

/**
 * A factory for creating Counts Administration related objects.
 * 
 * @author rterrell
 *
 */
public class CountsFactory {

    /**
     * Creates a CountsFactory.
     */
    public CountsFactory() {
        return;
    }

    /**
     * Creates an instance of CountsDao interface using the
     * {@link CountsIbatisDaoImpl} implementation.
     * 
     * @return an instance of {@link CountsDao}
     */
    public CountsDao getDaoInstance(SecurityToken token) {
        return new CountsIbatisDaoImpl(token);
    }

}
