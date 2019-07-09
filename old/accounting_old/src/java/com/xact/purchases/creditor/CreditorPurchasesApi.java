package com.xact.purchases.creditor;

import java.util.List;

import com.api.BaseDataSource;
import com.bean.Xact;

/**
 * Programming interface for handling crediot purchases transactions.
 * 
 * @author appdev
 *
 */
public interface CreditorPurchasesApi extends BaseDataSource {

    /**
     * Fetch creditor purchases transactions based on custom criteria.
     * 
     * @param criteria The custom criteria to filter data set.
     * @return An arbitrary object
     * @throws CreditorPurchasesException
     */
    Object findCreditorPurchasesXact(String criteria) throws CreditorPurchasesException;

    /**
     * Creates a creditor related cash disbursement transaction or reverses and existing one.   If the transaction 
     * id that is encapsulated in _xact is 0, then a new transaction is created.   Otherwise, an existing 
     * transaction is reversed.
     * 
     * @param _xact Source transaction.
     * @param _creditorId The id of the creditor to associate _xact.
     * @return id of the new transaction.
     * @throws CashDisbursementsException
     */
    int maintainCreditCharge(Xact _xact, List _items, int _creditorId) throws CreditorPurchasesException;
}