package com.api.config;

import com.util.RMT2Exception;

/**
 * @author RTerrell
 *
 */
public class ConfigException extends RMT2Exception {
    private static final long serialVersionUID = 749903225419625851L;

    /**
     * 
     */
    public ConfigException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public ConfigException(Exception e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param code
     */
    public ConfigException(int code) {
        super(code);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param _msg
     * @param _code
     */
    public ConfigException(String _msg, int _code) {
        super(_msg, _code);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public ConfigException(String msg) {
        super(msg);
        // TODO Auto-generated constructor stub
    }
    
    /**
     * 
     * @param msg
     * @param e
     */
    public ConfigException(String msg, Throwable e) {
	super(msg, e);
    }

}
