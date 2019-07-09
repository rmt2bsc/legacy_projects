package com.xact.purchases.vendor;

/**
 * Constants for the Purchases module
 * @author RTerrell
 *
 */
public class VendorPurchasesConst {

    /** Request name for the purchase order object */
    public static final String CLIENT_DATA_PO = "po";

    /** Request name for a list of purchase order objects */
    public static final String CLIENT_DATA_POLIST = "poList";

    /** Request name for the purchase order vendor object */
    public static final String CLIENT_DATA_VENODR = "vendor";

    /** Request name for a list of purchase order vendor objects */
    public static final String CLIENT_DATA_VENODRLIST = "vendorList";

    /** Request name for the purchase order items collection */
    public static final String CLIENT_DATA_ITEMS = "poItems";

    /** Request name for the purchase order status object */
    public static final String CLIENT_DATA_STATUS = "pos";

    /** Request name for the purchase order status history object */
    public static final String CLIENT_DATA_STATUS_HIST = "posh";

    /** Request name for the purchase order available items collection */
    public static final String CLIENT_DATA_AVAIL_ITEMS = "AvailItems";

    /** Add an item to the purchase order client action code */
    public static final String REQ_ADDITEM = "additem";

    /** Saves purchase order's new item */
    public static final String REQ_SAVENEWITEM = "savenewitem";

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