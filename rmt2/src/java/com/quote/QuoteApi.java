package com.quote;

import com.entity.Quote;


/**
 * An object that manages quotes.
 * 
 * @author appdev
 *
 */
public interface QuoteApi {
    
    /**
     * Get quotes based on custom selection criteria.
     * 
     * @param criteria
     *          the selection criteria
     * @return Object
     *           an arbitrary object representing the results of the query which 
     *           is implementation specific.
     * @throws QuoteException
     */
    Object getQuotes(String criteria) throws QuoteException;
    
    /**
     * Get a single quote from an external data source by is unique id.
     * 
     * @param quoteId
     *           the id of the quote which is a integer
     * @return Object
     *           an arbitrary object representing the results of the query which 
     *           is implementation specific.
     * @throws QuoteException
     */
    Object getQuote(int quoteId) throws QuoteException;
    
    /**
     * Get a single quote from an external data source by is email address.
     * 
     * @param emailId
     *           the email address as a String
     * @return Object 
     *           an arbitrary object representing the results of the query which 
     *           is implementation specific
     * @throws QuoteException
     */
    Object getQuote(String emailId) throws QuoteException;
    
    /**
     * Persist the current state of a quote.
     *  
     * @param quote
     *          {@link com.entity.Quote Quote}
     * @return int
     * @throws QuoteException
     */
    int maintainQuote(Quote quote) throws QuoteException;
    
    /**
     * Deletes a quote.
     *  
     * @param quoteId
     *          the id of the quote which is a integer
     * @return int
     *          the total number of rows deleted.
     * @throws QuoteException
     */
    int deleteQuote(int quoteId) throws QuoteException; 
}
