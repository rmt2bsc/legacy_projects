package com.api;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;

import com.api.db.DatabaseException;

import com.bean.RMT2Base;
import com.controller.Request;

import com.util.SystemException;
import com.util.RMT2Utility;
import com.util.RMT2BeanUtility;

/**
 * Abstract class that provides basic functionality for manipulatig the 
 * {@link com.controller.Request Request}.   This functionality can be 
 * extended by descendent implmenations.
 * 
 * @author roy.terrell
 * 
 */

public abstract class AbstractApiImpl extends RMT2Base {
    private static Logger logger = Logger.getLogger(AbstractApiImpl.class);

    protected static final String DEFAULT_CONFIG = "AppParms";

    /** The client's Http request object */
    protected Request request;

    /**
     * Default constuctor.
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    public AbstractApiImpl() {
        super();
        logger.log(Level.DEBUG, "Starting constructor, AbstractApiImpl()");
        try {
            this.initApi();
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    /**
     * Consturctor that initializes the Request object.
     * 
     * @param req {@link com.controller.Request Request}
     * @throws DatabaseException
     * @throws SystemException
     */
    public AbstractApiImpl(Request req) {
        this();
        logger.log(Level.DEBUG, "Starting constructor, AbstractApiImpl(Request)");
        this.request = req;
        return;
    }

    /**
     * Implement this method in order to provide custom initialization for
     * descendent object.  This  method is called from the default constructor.
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    protected abstract void initApi() throws DatabaseException, SystemException;

    /**
     * Set the client's request object.
     * 
     * @param value The {@link com.controller.Request Request}
     */
    protected final void setRequest(Request value) {
        request = value;
    }

    /**
     * Calculates the total number of rows the {@link com.controller.Request Request} object
     * contains. The row count should reflect a list of data that exist in the
     * request where each the name of each property on a given row in the list
     * has a indexed integer appended to it such as: XactId0, XactId1.
     * 
     * @param beanUtil
     *            An {@link RMT2BeanUtility} object tageting the properties that
     *            exist in the Request object.
     * @return The total row count
     */
    protected final int getRequestRowCount(RMT2BeanUtility beanUtil) {

        String prop = null;
        String value = null;
        boolean isPropFound = false;
        ArrayList props = beanUtil.getPropertyNames();

        // Since this method relies on a valid HttpServletRequest
        // (this.request), return an error code of -1 if this.request
        // is not valid.
        if (this.request == null) {
            return -1;
        }

        // Locate a parameter in the request object that can be used
        // to determine the request object's row count
        for (int ndx = 0; ndx < props.size(); ndx++) {
            prop = (String) props.get(ndx);
            prop = RMT2Utility.getBeanMethodName(prop);
            if (this.request.getParameter(prop + "0") != null) {
                isPropFound = true;
                break;
            }
        }

        // If bean property could not be match with a
        // parameter of the HttpServletRequest object
        // then return 0 as the total count.
        if (!isPropFound) {
            return 0;
        }

        // Calculate how many times "prop" uniquely occurs within
        // the HttpServletRequest object which render a total
        // row count.
        int ndx = 0;
        value = this.request.getParameter(prop + ndx);
        while (value != null) {
            ndx++;
            value = this.request.getParameter(prop + ndx);
        }

        return ndx;
    }

} // End class
