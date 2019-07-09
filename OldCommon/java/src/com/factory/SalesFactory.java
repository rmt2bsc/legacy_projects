package com.factory;

import javax.servlet.http.HttpServletRequest;

import com.bean.SalesOrder;
import com.bean.SalesOrderItems;
import com.bean.SalesInvoice;
import com.bean.SalesOrderStatusHist;
import com.bean.db.DatabaseConnectionBean;

import com.api.SalesOrderApi;
import com.api.CashReceiptsApi;
import com.api.XactManagerApi;
import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.apiimpl.SalesOrderManagerApiImpl;
import com.apiimpl.CashReceiptsApiImpl;

import com.util.SystemException;

/**
 * Factory class that contain methods responsible for the creation of  various objects pertaining to the sales order module.
 * 
 * @author Roy Terrell.
 *
 */
public class SalesFactory extends DataSourceAdapter  {
	
	/**
	 * Creates an {@link XactManagerApi} api implemented as {@link SalesOrderManagerApiImpl} with a specifiic 
	 * database connection bean and servlet request object.
	 * 
	 * @param _dbo {@link DatabaseConnectionBean}
	 * @param _request HttpServletRequest
	 * @return {@link XactManagerApi}
	 */
  public static XactManagerApi createBaseXactApi(DatabaseConnectionBean _dbo, HttpServletRequest _request) {
	  try {
		  XactManagerApi api = new SalesOrderManagerApiImpl(_dbo, _request);
		  return api;  
	  }
	  catch (DatabaseException e) {
		  return null;
	  }
	  catch (SystemException e) {
		  return null;
	  }
  }

  /**
   * Creates an sales order api implemented with a specific database connection bean
   * 
   * @param _dbo {@link DatabaseConnectionBean}
   * @return Sales Order api
   * @throws SystemException
   * @throws DatabaseException
   */
  public static SalesOrderApi createApi(DatabaseConnectionBean _dbo) throws SystemException, DatabaseException {
     SalesOrderApi api = new SalesOrderManagerApiImpl(_dbo);
     api.setBaseView("SalesOrderView");
     api.setBaseClass("com.bean.SalesOrder");
     return api;
  }
  
  /**
   * Creates an sales order api implemented with a specific database connection bean and servlet request object.
   * 
   * @param _dbo {@link DatabaseConnectionBean}
   * @param _request HttpServletRequest
   * @return Sales Order api
   * @throws SystemException
   * @throws DatabaseException
   */
  public static SalesOrderApi createApi(DatabaseConnectionBean _dbo, HttpServletRequest _request) throws SystemException, DatabaseException {
      SalesOrderApi api = new SalesOrderManagerApiImpl(_dbo, _request);
      api.setBaseView("SalesOrderView");
      api.setBaseClass("com.bean.SalesOrder");
      return api;
   }

  
  /**
   * Creates a cash receiptsr api implemented with a specific database connection bean.
   * @param _dbo
   * @param _request
   * @return
   */
  public static CashReceiptsApi createCashReceiptsApi(DatabaseConnectionBean _dbo)  {
	  try {
		  CashReceiptsApi api = new CashReceiptsApiImpl(_dbo);
	      return api;
	  }
	  catch (DatabaseException e) {
		  return null;
	  }
	  catch (SystemException e) {
		  return null;
	  }
   }
  
  /**
   * Creates a cash receiptsr api implemented with a specific database connection bean and servlet request object.
   * @param _dbo
   * @param _request
   * @return
   */
  public static CashReceiptsApi createCashReceiptsApi(DatabaseConnectionBean _dbo, HttpServletRequest _request)  {
	  try {
		  CashReceiptsApi api = new CashReceiptsApiImpl(_dbo, _request);
	      return api;
	  }
	  catch (DatabaseException e) {
		  return null;
	  }
	  catch (SystemException e) {
		  return null;
	  }
   }
 
	/**
	 * Creates a new sales order object
	 * @return {@link SalesOrder}
	 * @throws SystemException
	 */
  public static SalesOrder createSalesOrder()  throws SystemException{
      SalesOrder obj = new SalesOrder();
      obj.setInvoiced(0);
      return obj;
  }

  /**
   * Creates a sales order object which initializing its customer id property to _cusId
   *  
   * @param _custId The id of a particular customer.
   * @return {@link SalesOrder}
   * @throws SystemException
   */
  public static SalesOrder createSalesOrder(int _custId)   throws SystemException {
	  SalesOrder obj = SalesFactory.createSalesOrder();
      obj.setCustomerId(_custId);
      return obj;
  }

  /**
   * Creates a single sales order item object
   * 
   * @return {@link SalesOrderItems}
   * @throws SystemException
   */
  public static SalesOrderItems createSalesOrderItem() throws SystemException {
	  SalesOrderItems obj = new SalesOrderItems();
	  return obj;
  }

  /**
   * Creates a single sales order item object using sales order id, item id, and item quantity
   * 
   * @param _salesOrderId The id of a sales order
   * @param _itemId The id of an inventory item
   * @param _qty The quantity to associate with _itemId
   * @return {@link SalesOrderItems}
   * @throws SystemException
   */
  public static SalesOrderItems createSalesOrderItem(int _salesOrderId, int _itemId, double _qty) throws SystemException {
	  SalesOrderItems obj = SalesFactory.createSalesOrderItem();
	  obj.setSalesOrderId(_salesOrderId);
	  obj.setItemMasterId(_itemId);
	  obj.setOrderQty(_qty);
	  return obj;
  }

  /**
   * Creates a sales invoice object
   * 
   * @return {@link SalesInvoice}
   * @throws SystemException
   */
	public static SalesInvoice createSalesInvoice() throws SystemException {
			SalesInvoice obj = new SalesInvoice();
			return obj;
	}

	/**
	 * Creates a sales invoice object using sales order id and transaction id
	 * 
	 * @param _salesOrderId The id of a sales order
	 * @param _xactId The id of the transaction that is assoicated with _salesOrderId
	 * @return {@link SalesInvoice}
	 * @throws SystemException
	 */
	public static SalesInvoice createSalesInvoice(int _salesOrderId, int _xactId) throws SystemException {
			SalesInvoice obj =  SalesFactory.createSalesInvoice();
			obj.setSalesOrderId(_salesOrderId);
			obj.setXactId(_xactId);
			return obj;
	}
	
	/**
	 * Creates a sales order status history object using sales order id
	 * 
	 * @param _salesOrderId The id of a particular sales order
	 * @return {@link SalesOrderStatusHist}
	 */
	public static SalesOrderStatusHist createSalesStatusHistory(int _salesOrderId) {
		SalesOrderStatusHist obj = null;
		try {
			obj = new SalesOrderStatusHist();
			obj.setSalesOrderId(_salesOrderId);
			return obj;
		}
		catch (SystemException e) {
			return null;
		}
	}

}


