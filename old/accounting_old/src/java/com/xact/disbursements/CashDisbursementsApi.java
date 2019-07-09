package com.xact.disbursements;

import java.util.List;

import com.api.BaseDataSource;
import com.bean.Xact;

/**
 * Interface for creating and modifying cash disbursements.
 * 
 * @author RTerrell
 *
 */
public interface CashDisbursementsApi extends BaseDataSource {

    /**
     * Return a list of cash disbursements transaction which the result set is 
     * filtered by <i>criteria</i>
     * 
     * @param criteria
     *          Selection criteria to filter data.
     * @return Object
     *          Arbitrary object representing the result set of the query
     * @throws CashDisbursementsException
     */
    Object findXactList(String criteria) throws CashDisbursementsException;
    
    /**
     * Return a list of cash disbursements transaction items which the result set is 
     * filtered by <i>criteria</i>
     * 
     * @param criteria
     *          Selection criteria to filter data.
     * @return Object
     *          Arbitrary object representing the result set of the query
     * @throws CashDisbursementsException
     */
    Object findXactItems(String criteria) throws CashDisbursementsException;

    /**
     * Creates a general cash disbursement transaction or reverses and existing 
     * cash disbursement transaction.   If the transaction id that is encapsulated 
     * in _xact is 0, then a new transaction is created.   Otherwise, an existing 
     * transaction is reversed.
     * 
     *@param _xact Source transaction.
     * @param _custId The id of the customer of this transaction.
     * @return The id of the new transaction.
     * @throws CashDisbursementsException
     */
    int maintainCashDisbursement(Xact _xact, List _items) throws CashDisbursementsException;

    /**
     * Creates a creditor related cash disbursement transaction or reverses and 
     * existing one.   If the transaction id that is encapsulated in _xact is 0, 
     * then a new transaction is created.   Otherwise, an existing transaction is 
     * reversed.
     * 
     * @param _xact Source transaction.
     * @param _creditorId The id of the creditor to associate _xact.
     * @return id of the new transaction.
     * @throws CashDisbursementsException
     */
    int maintainCashDisbursement(Xact _xact, List _items, int _creditorId) throws CashDisbursementsException;
}