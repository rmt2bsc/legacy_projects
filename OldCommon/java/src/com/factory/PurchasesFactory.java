package com.factory;

import javax.servlet.http.HttpServletRequest;

import com.bean.PurchaseOrder;
import com.bean.PurchaseOrderItems;
import com.bean.PurchaseOrderStatus;
import com.bean.PurchaseOrderStatusHist;
import com.bean.db.DatabaseConnectionBean;

import com.api.PurchasesApi;
import com.api.XactManagerApi;
import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.apiimpl.PurchasesManagerApiImpl;

import com.util.SystemException;


public class PurchasesFactory extends DataSourceAdapter  {

  public static XactManagerApi createBaseXactApi(DatabaseConnectionBean _dbo) {
	  try {
		  XactManagerApi api = new PurchasesManagerApiImpl(_dbo);
		  return api;  
	  }
	  catch (DatabaseException e) {
		  return null;
	  }
	  catch (SystemException e) {
		  return null;
	  }
	  
  }

  public static PurchasesApi createApi(DatabaseConnectionBean _dbo) throws SystemException, DatabaseException {
	  try {
		  PurchasesApi api = new PurchasesManagerApiImpl(_dbo);
		  api.setBaseView("PurchaseOrderView");
		  api.setBaseClass("com.bean.PurchaseOrder");
		  return api;  
	  }
	  catch (SystemException e) {
		  System.out.println("[PurchasesFactory.createApi] SystemException - " + e.getMessage());
		  return null;
	  }
	  catch (DatabaseException e) {
		  System.out.println("[PurchasesFactory.createApi] DatabaseException - " + e.getMessage());
		  return null;
	  }
	  
  }
  
  public static PurchasesApi createApi(DatabaseConnectionBean _dbo, HttpServletRequest _request) throws SystemException, DatabaseException {
	  try {
		  PurchasesApi api = new PurchasesManagerApiImpl(_dbo, _request);
		  api.setBaseView("PurchaseOrderView");
		  api.setBaseClass("com.bean.PurchaseOrder");
		  return api;  
	  }
	  catch (SystemException e) {
		  System.out.println("[PurchasesFactory.createApi] SystemException - " + e.getMessage());
		  return null;
	  }
	  catch (DatabaseException e) {
		  System.out.println("[PurchasesFactory.createApi] DatabaseException - " + e.getMessage());
		  return null;
	  }
   }

  public static PurchaseOrder createPurchaseOrder() {
	  try {
		  PurchaseOrder obj = new PurchaseOrder();
	      return obj;  
	  }
	  catch (SystemException e) {
		  System.out.println("[PurchasesFactory.createPurchaseOrder] SystemException - " + e.getMessage());
		  return null;
	  }
	  
  }

  public static PurchaseOrderItems createPurchaseOrderItem(int _poId) {
	  try {
		  PurchaseOrderItems obj = new PurchaseOrderItems();
		  obj.setPurchaseOrderId(_poId);
	      return obj;  
	  }
	  catch (SystemException e) {
		  System.out.println("[PurchasesFactory.createPurchaseOrder] SystemException - " + e.getMessage());
		  return null;
	  }
  }
  
  public static PurchaseOrderStatus createPurchaseOrderStatus() {
	  try {
		  PurchaseOrderStatus obj = new PurchaseOrderStatus();
	      return obj;  
	  }
	  catch (SystemException e) {
		  System.out.println("[PurchasesFactory.createPurchaseOrderStatus] SystemException - " + e.getMessage());
		  return null;
	  }
  }
  
  
  public static PurchaseOrderStatusHist createPurchaseOrderStatusHist() {
	  try {
		  PurchaseOrderStatusHist obj = new PurchaseOrderStatusHist();
	      return obj;  
	  }
	  catch (SystemException e) {
		  System.out.println("[PurchasesFactory.createPurchaseOrderStatusHist] SystemException - " + e.getMessage());
		  return null;
	  }
  }
	  
	  
  public static PurchaseOrderStatusHist createPurchaseOrderStatusHist(int _poId, int _statusId) {
	  PurchaseOrderStatusHist obj = PurchasesFactory.createPurchaseOrderStatusHist();
	  obj.setPurchaseOrderId(_poId);
	  obj.setPurchaseOrderStatusId(_statusId);
      return obj;  
  }

}


