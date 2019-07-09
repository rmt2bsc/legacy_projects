package com.xact;

import java.util.ArrayList;

import com.action.ActionHandlerException;
import com.api.db.DatabaseException;

/**
 * This interface provides common functionality that an Action Handler is required to
 *  perform for all transaction types (sales on account, cash disbursements, purchases, 
 *  and cash receipts).
 *  
 * @author roy.terrell
 *
 */
public interface ICommonXactAction {

    /**
     * Gets the selected row number from the client's request, if available.  <p>Generally the row 
     * number will be contained in a input control such as a checkbox or a radio button.
     * 
     * @return the row number or a number representing the id of an entity object
     */
    int getSelectedRow();

    /**
     * Retrieves transaction data from the xact, xact_type, and xact_category tables and packages the combined 
     * data in {@link VwXactList}.
     *   
     * @param _criteria The selection criteria
     * @return An ArrayList of one or more unknown objects
     * @throws XactException
     */
    ArrayList getCommonXact(String _criteria) throws XactException;

    /**
     * Reverses transaction using _xactId and sends the details of the new reversed transaction
     *  to the client via the HttpServletRequest object.
     * 
     * @param _xactId The id of the target transaction
     * @return The id of the reversed transaction
     * @throws XactException
     * @throws DatabaseException
     */
    int reverseXact(int _xactId) throws ActionHandlerException, DatabaseException;
}
