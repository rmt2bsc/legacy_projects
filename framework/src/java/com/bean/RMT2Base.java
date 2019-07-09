package com.bean;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Level;

import com.api.AbstractApiImpl;
import com.api.config.HttpSystemPropertyConfig;
import com.api.security.pool.AppPropertyPool;

/**
 * This abstract class serves as the ancestor for all other classes in the system.
 * 
 * @author roy.terrell
 * 
 */
public abstract class RMT2Base {
    private static Logger logger = Logger.getLogger(RMT2Base.class);

    private static final String ENV = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_ENV);

    /**
     * Message indicating that the logger has been initialized.
     */
    protected static final String LOGGER_INIT_MSG = "Logger has been initialized";

    /** Single messabe literal */
    public static final String SINGLE_MSG = "msg";

    /** Multi Message literal */
    public static final String MULTI_MSG = "messages";

    /** Success code */
    public static final int SUCCESS = 1;

    /** Failure code */
    public static final int FAILURE = -1;

    /** Messages hash */
    protected Hashtable messages = new Hashtable();

    /** Used to contain a single message */
    protected String msg;

    /** Denotes the class name */
    protected String className;

    /** Denotes the method name */
    protected String methodName;

    /** Used to contain one or more messages */
    protected ArrayList msgArgs;

    /**
     * Constructs a RMT2Base object
     * 
     */
    public RMT2Base() {
        logger.log(Level.DEBUG, "Starting constructor, RMT2Base()");
        this.init();
        return;
    }

    /**
     * Initializes the messages list.
     * 
     */
    public void init() {
        logger.log(Level.DEBUG, "Starting init()");
        this.msgArgs = new ArrayList();
        return;
    }

    /**
     * Obtains the environment value.
     * 
     * @return String
     */
    public static final String getEnvironment() {
        return RMT2Base.ENV;
    }

    /**
     * Sets the method name
     * 
     * @param value
     *            String
     */
    public void setMethodName(String value) {
        this.methodName = value;
    }

    /**
     * Gets the method name
     * 
     * @return String
     */
    public String getMethodName() {
        return this.methodName;
    }

    /**
     * Sets the class name
     * 
     * @param value
     *            String
     */
    public void setClassName(String value) {
        this.className = value;
    }

    /**
     * Gets the class name
     * 
     * @return String
     */
    public String getClassName() {
        return this.className;
    }

}