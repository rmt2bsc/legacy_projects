package modules.idt;

import com.nv.security.SecurityToken;

/**
 * A factory for creating IDT Administration related objects.
 * 
 * @author rterrell
 *
 */
public class IDTFactory {

    /**
     * Creates a SIRTVFactory.
     */
    public IDTFactory() {
        return;
    }

    /**
     * Creates an instance of IdtDao interface using the
     * {@link IdtIbatisDaoImpl} implementation.
     * 
     * @return an instance of {@link IdtDao}
     */
    public IdtDao getDaoInstance(SecurityToken token) {
        return new IdtIbatisDaoImpl(token);
    }

}
