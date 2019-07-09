package com.constants;

/**
 * Constants for the sales order module.
 * 
 * @author Roy Terrell
 *
 */
public class SalesConst  {
	 /** Flag indicating sales order is invoiced */
	 public static final int INVOICED_FLAG_YES = 1;
	 /** Flag indicating sales order is cancelled */
	 public static final int INVOICED_FLAG_NO = 0;
	 
	 /** Sales Order Inventory update mode for increasing inventory items with sales order items */
	 public static final int UPDATE_INV_ADD = 100;
	 /** Sales Order Inventory update mode for decreasing inventory items with sales order items */
	 public static final int UPDATE_INV_DEPLETE = 200;
    
     /** New Sales Order Status Code */
     public static final int STATUS_CODE_NEW = 0;
    /** Quote Sales Order Status Code */
    public static final int STATUS_CODE_QUOTE = 1;
    /** Invoiced Order Status Code */
    public static final int STATUS_CODE_INVOICED = 2;
    /** Closed Sales Order Status Code */
    public static final int STATUS_CODE_CLOSED = 3;
    /** Cancelled Sales Order Status Code */
    public static final int STATUS_CODE_CANCELLED = 100;
    /** Refunded Sales Order Status Code */
    public static final int STATUS_CODE_REFUNDED = 200;
    
	 /** New Order  Sales order client action code */
	 public static final String REQ_NEW = "neworder";
	 /** Select Items Order  Sales order client action code */
	 public static final String REQ_ADDITEM = "additem";
	 /** Order History  Sales order client action code */
	 public static final String REQ_ORDERHIST = "orderhist";
	 /** Edit Sales Order  Sales order client action code */
	 public static final String REQ_EDIT = "editorder";
	 /** Cancel Sales Order client action code */
	 public static final String REQ_CANCEL = "cancelorder";
     /** Refund Sales Order client action code */
     public static final String REQ_REFUND = "salesrefund";
	 /** Payment History Order Sales order client action code */
	 public static final String REQ_PAYHIST = "payhist";
	 /** Make A Payment Sales order client action code */
	 public static final String REQ_PAYMENT = "acceptpayment";
	 /** Reverse Sales order payment client action code */
	 public static final String REQ_REVERSEPAYMENT = "reversepaymentedit";
     /** View orders request code */
     public final static String REQ_VIEWORDERS = "vieworders";
        
	 
	  /** Request attribute name mapping to customer's address data */
	  public static final String CLIENT_DATA_ADDRESS = "ADDRESS";
	  /** Request attribute name mapping to customer's business data */
	  public static final String CLIENT_DATA_BUSINESS = "BUSINESS";
	  /** Request attribute name mapping to customer's person data */
	  public static final String CLIENT_DATA_PERSON = "PERSON";
	  /** Request attribute name mapping for customer data */
	  public static final String CLIENT_DATA_CUSTOMER = "CUSTOMER";
	  /** Request attribute name mapping for customer's zip code data */
	  public static final String CLIENT_DATA_ZIP = "ZIP";
	  /** Request attribute name mapping for customer's type data */
	  public static final String CLIENT_DATA_CUSTTYPE = "CUSTTYPE";
	  /** Request attribute name mapping for customer's account  balance data */
	  public static final String CLIENT_DATA_BALANCE = "BALANCE";
      /** Request attribute name mapping to Customer Sales Order List */ 
      public static final String CLIENT_DATA_ORDERLIST = "OrderHist";
      /** Request attribute name mapping to Customer Extension data */
      public static final String CLIENT_DATA_CUSTOMER_EXT = "customer";
      /** Request attribute name mapping to Sales Order data */
      public static final String CLIENT_DATA_SALESORDER = "salesorder";
      /** Request attribute name mapping to Sales Order Status data */
      public static final String CLIENT_DATA_STATUS = "salesorderstatus";
      /** Request attribute name mapping to Sales Inovice data */
      public static final String CLIENT_DATA_INVOICE = "invoice";
      /** Request attribute name mapping to Transaction data */
      public static final String CLIENT_DATA_XACT = "xact";
      /** Request attribute name mapping to Sales Order service items */
      public static final String CLIENT_DATA_SERVICE_ITEMS = "services";
      /** Request attribute name mapping to Sales Order merchandise items */
      public static final String CLIENT_DATA_MERCHANDISE_ITEMS = "merchandise";
}