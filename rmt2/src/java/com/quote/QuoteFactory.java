package com.quote;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.entity.Quote;

import com.api.db.DatabaseException;

import com.api.db.orm.DataSourceAdapter;

import com.util.SystemException;

/**
 * Contains factory methods for creating quote related entities.
 * 
 * @author appdev
 *
 */
public class QuoteFactory extends DataSourceAdapter {

    private static Logger logger = Logger.getLogger(QuoteFactory.class);

    /**
     * Creates an instance of the QuoteApi which is capable of interacting with the database.
     * 
     * @param conBean
     *          {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean}
     * @return  {@link QuoteApi}
     *          
     */
    public static QuoteApi createApi(DatabaseConnectionBean conBean) {
	QuoteApi api = null;
	String message = null;
	try {
	    api = new QuoteImpl(conBean);
	    return api;
	}
	catch (DatabaseException e) {
	    message = "Unable to create QuoteApi(DatabaseConnectionBean) due to a database error.  Message: " + e.getMessage();
	    logger.log(Level.ERROR, message);
	    return null;
	}
	catch (SystemException e) {
	    message = "Unable to create QuoteApi(DatabaseConnectionBean) due to a system error.  Message: " + e.getMessage();
	    logger.log(Level.ERROR, message);
	    return null;
	}
    }

    /**
     * Creates an instance of the QuoteApi which is capable of interacting with the database 
     * and the user's request.
     * 
     * @param conBean
     *          {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean}
     * @param request
     *          {@link com.controller.Request Request}
     * @return {@link QuoteApi}
     */
    public static QuoteApi createApi(DatabaseConnectionBean conBean, Request request) {
	QuoteApi api = null;
	String message = null;
	try {
	    api = new QuoteImpl(conBean, request);
	    return api;
	}
	catch (DatabaseException e) {
	    message = "Unable to create QuoteApi(DatabaseConnectionBean, Request) due to a database error.  Message: " + e.getMessage();
	    logger.log(Level.ERROR, message);
	    return null;
	}
	catch (SystemException e) {
	    message = "Unable to create QuoteApi(DatabaseConnectionBean, Request) due to a system error.  Message: " + e.getMessage();
	    logger.log(Level.ERROR, message);
	    return null;
	}
    }

    /**
     * Creates a Quote instance
     * 
     * @return {@link com.entity.Quote Quote}
     */
    public static Quote createQuote() {
	try {
	    Quote obj = new Quote();
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates a Quote instance from a Request object.
     * 
     * @param request
     *          {@link com.controller.Request Request}
     * @return {@link com.entity.Quote Quote}
     */
    public static Quote createQuote(Request request) {
	Quote obj = QuoteFactory.createQuote();
	try {
	    QuoteFactory.packageBean(request, obj);
	    return obj;
	}
	catch (SystemException e) {
	    e.printStackTrace();
	    return null;
	}
    }

}
