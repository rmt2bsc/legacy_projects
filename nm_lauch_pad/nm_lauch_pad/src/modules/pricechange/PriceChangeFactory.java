package modules.pricechange;

import com.nv.security.SecurityToken;

/**
 * A factory creating Price Change Administration related objects
 * 
 * @author rterrell
 *
 */
public class PriceChangeFactory {

    public PriceChangeFactory() {
        return;
    }

    /**
     * Creates an instance of PriceChangeDao interface using the
     * {@link PriceChangeIbatisDaoImpl} implementation.
     * 
     * @return an instance of {@link PriceChangeDao}
     */
    public PriceChangeDao getDaoInstance(SecurityToken token) {
        return new PriceChangeIbatisDaoImpl(token);
    }

}
