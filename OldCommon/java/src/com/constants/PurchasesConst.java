package com.constants;

public class PurchasesConst {

	/** Request name for the purchase order object */
	public static final String CLIENT_DATA_PO = "po";
	/** Request name for the purchase order vendor object */
	public static final String CLIENT_DATA_VENODR = "vendor";
	/** Request name for the purchase order items collection */
	public static final String CLIENT_DATA_ITEMS = "poItems";
	/** Request name for the purchase order status object */
	public static final String CLIENT_DATA_STATUS = "pos";
	/** Request name for the purchase order status history object */
	public static final String CLIENT_DATA_STATUS_HIST = "posh";
	/** Request name for the purchase order available items collection */
	public static final String CLIENT_DATA_AVAIL_ITEMS = "AvailItems";
	

	
	/** Edit Purchase Order client action code */
	public static final String REQ_EDIT = "edit";
	/** Add Purchase Order client action code */
	public static final String REQ_ADD = "add";
	/** Add an item to the purchase order client action code */
	public static final String REQ_ADDITEM = "additem";
	/** Saves purchase order's new item */
	public static final String REQ_SAVENEWITEM = "savenewitem";
	/** Save Purchase Order client action code */
	public static final String REQ_SAVE = "save";

	/**
	 * Build selection criteria and perform a search of purchase orders client
	 * action code
	 */
	public static final String REQ_SEARCH = "search";
	/** Open New Purchase Order Search Console client action code */
	public static final String REQ_NEWSEARCH = "newsearch";

	/**
	 * Display purchase order search screen using search criteria stored in
	 * session object client action code
	 */
	public static final String REQ_OLDSEARCH = "oldsearch";
	/** Delete Purchase Order client action code */
	public static final String REQ_DELETE = "delete";
	/** Cancels Purchase Order client action code */
	public static final String REQ_CANCEL = "cancel";
	/** Submits a purchase order for the vendor to fill the order */
	public static final String REQ_FINALIZE = "finalize";

	/**
	 * Indicates that the purchase order is new and has not been assigned a
	 * status.
	 */
	public static final int NEW_PO_STATUS = -1;
	/** Quote Purchases Status Code */
	public static final int PURCH_STATUS_QUOTE = 1;
	/** Finalized Purchases Status Code */
	public static final int PURCH_STATUS_FINALIZE = 2;
	/** Received Purchases Status Code */
	public static final int PURCH_STATUS_RECEIVED = 3;
	/** Cancelled Purchases Status Code */
	public static final int PURCH_STATUS_CANCEL = 4;
	/** Purchases, Returns, and Allowances Status Code */
	public static final int PURCH_STATUS_RETURN = 5;
	/** Partial Purchase, Return, and Allowances Status Code */
	public static final int PURCH_STATUS_PARTRET = 6;
	/** Indicates the successfull update of a Quote or Finalize purchase order */
	public static final int PO_UPDATE_SUCCESSFUL = 1;

	/**
	 * Indicates the successfull update of a Finalize purchase order which its
	 * total order quantity has been received
	 */
	public static final int PO_UPDATE_RECEIVED = 0;
}