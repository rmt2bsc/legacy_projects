
package com.api;

import com.bean.Xact;

import com.util.CashReceiptsException;


public interface CashReceiptsApi extends BaseDataSource {

   
   /**
    * Creates a customer payment transaction or reverses and existing customer payment transaction.   If the transaction 
    * id that is encapsulated in _xact is 0, then a new customer payment transaction is created.   Otherwise, an existing 
    * customer payment transaction is reversed.
    * 
    *@param _xact Source transaction.
    * @param _custId The id of the customer of this transaction.
    * @return The id of the new customer payment transaction.
    * @throws CashReceiptsException
    */
   public  int maintainCustomerPayment(Xact _xact, int _custId) throws CashReceiptsException;
}
