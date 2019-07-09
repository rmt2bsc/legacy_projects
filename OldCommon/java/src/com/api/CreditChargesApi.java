package com.api;

import java.util.List;

import com.bean.Xact;

import com.util.CreditChargeException;


public interface CreditChargesApi extends BaseDataSource {
 
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
   int maintainCreditCharge(Xact _xact, List _items, int _creditorId) throws CreditChargeException;
}