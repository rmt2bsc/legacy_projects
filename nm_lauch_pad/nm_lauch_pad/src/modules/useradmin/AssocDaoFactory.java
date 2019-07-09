package modules.useradmin;

import com.nv.security.SecurityToken;

/**
 * A factory creating User Administration related objects
 * 
 * @author rterrell
 *
 */
public class AssocDaoFactory {

    public AssocDaoFactory() {
        return;
    }

    /**
     * Creates an instance of AssocDao interface using the
     * {@link AssocIbatisDaoImpl} implementation.
     * 
     * @return an instance of {@link AssocDao}
     */
    public AssocDao getDaoInstance(SecurityToken token) {
        return new AssocIbatisDaoImpl(token);
    }

}
