package com.api;

import java.util.List;

import com.bean.Xact;

import com.util.CashDisbursementsException;


public interface CashDisbursementsApi extends BaseDataSource {

   /**
    * Creates a general cash disbursement transaction or reverses and existing cash disbursement transaction.   If the transaction 
    * id that is encapsulated in _xact is 0, then a new transaction is created.   Otherwise, an existing transaction is reversed.
    * 
    *@param _xact Source transaction.
    * @param _custId The id of the customer of this transaction.
    * @return The id of the new transaction.
    * @throws CashDisbursementsException
    */
    int maintainCashDisbursement(Xact _xact, List _items) throws CashDisbursementsException;
   
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
    int maintainCashDisbursement(Xact _xact, List _items, int _creditorId) throws CashDisbursementsException;
}