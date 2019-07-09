package modules.report;

import com.nv.security.SecurityToken;

/**
 * A factory for creating Print/Reprint Report Administration related objects.
 * 
 * @author rterrell
 *
 */
public class ReportFactory {

    /**
     * Creates a ReportFactory.
     */
    public ReportFactory() {
        return;
    }

    /**
     * Creates an instance of ReportDao interface using the
     * {@link ReportIbatisDaoImpl} implementation.
     * 
     * @return an instance of {@link ReportDao}
     */
    public ReportDao getDaoInstance(SecurityToken token) {
        return new ReportIbatisDaoImpl(token);
    }

}
