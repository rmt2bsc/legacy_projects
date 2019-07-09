package com.bean.bindings;


import org.apache.log4j.Logger;


import com.bean.RMT2Base;



/**
 * Serves to provide JAXB/DTO adaptor functionality for authentication entities.
 *  
 * @author Roy Terrell
 *
 */
public class JaxbAuthenticationAdapter extends RMT2Base {
    static Logger logger = Logger.getLogger("JaxbAuthenticationAdapter");

    /**
     * Default Constructor
     */
    public JaxbAuthenticationAdapter() {
	super();
    }
}
