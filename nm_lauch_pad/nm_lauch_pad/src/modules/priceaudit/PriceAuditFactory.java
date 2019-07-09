package modules.priceaudit;

import com.nv.security.SecurityToken;

/**
 * A factory creating Price Audit Administration related objects
 * 
 * @author rterrell
 *
 */
public class PriceAuditFactory {

    public PriceAuditFactory() {
        return;
    }

    /**
     * Creates an instance of PriceAuditDao interface using the
     * {@link PriceAuditIbatisDaoImpl} implementation.
     * 
     * @return an instance of {@link PriceAuditDao}
     */
    public PriceAuditDao getDaoInstance(SecurityToken token) {
        return new PriceAuditIbatisDaoImpl(token);
    }

}
