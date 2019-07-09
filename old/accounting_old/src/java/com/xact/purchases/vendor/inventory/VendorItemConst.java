package com.xact.purchases.vendor.inventory;

public class VendorItemConst {
    /** Item Client Data name */
    public static final String CLIENT_DATA_ITEM = "item";

    /** Item History client data name. */
    public static final String CLIENT_DATA_ITEMHIST = "itemhistory";

    // Item Type Names for JSP Input controls
    public static final String SEL_NEW_ITEM_SRVC = "ServiceItems";

    public static final String SEL_NEW_ITEM_MERCH = "MerchItems";

    /** Code for Service item types */
    public final static String ITEMTYPE_SRVC = "Srvc";

    /** Code for Merchandise item types */
    public final static String ITEMTYPE_MERCH = "Merch";

    /** Action code for viewing vendor items */
    public static final String REQ_VEND_ITEM_VIEW = "back";

    /** Action code for assigning an item to a vendor */
    public static final String REQ_VEND_ITEM_ASSIGN = "vendoritemassign";

    /** Action code for unassigning an item from a vendor */
    public static final String REQ_VEND_ITEM_UNASSIGN = "vendoritemunassign";

    /** Action code for editing a vendor item association */
    public static final String REQ_VEND_ITEM_EDIT = "venditemedit";

    /** Action code for saving vendor item edits */
    public static final String REQ_VEND_ITEM_SAVE = "venditemsave";

    /** Action code for overriding an inventory item with a vendor item */
    public static final String REQ_VEND_ITEM_OVERRIDE = "venditemoverride";

    /** Action code for removing an inventory itemo override */
    public static final String REQ_VEND_ITEM_OVERRIDEREMOVE = "removevenditemoverride";

    /** Action code for returning back to vendor detail edit page from the vendor-item assoication page */
    public static final String REQ_VEND_ITEM_BACK = "venditemback";

    /** Service Item Type Code */
    public static final int ITEM_TYPE_SRVC = 1;

    /** Merchandise Item Type Code */
    public static final int ITEM_TYPE_MERCH = 2;

    /** Available Item Status Code */
    public static final int ITEM_STATUS_AVAIL = 26;

    /** In Service Item Status Code */
    public static final int ITEM_STATUS_INSRVC = 27;

    /** Out of Service Item Status Code */
    public static final int ITEM_STATUS_OUTSRVC = 28;

    /** Replaced Item Status Code */
    public static final int ITEM_STATUS_REPLACE = 29;

    /** Cancelled Item Status Code */
    public static final int ITEM_STATUS_CANCEL = 30;

    /** Out of Stock Item Status Code */
    public static final int ITEM_STATUS_OUTSTOCK = 31;

    /** Reorder Item Status Code */
    public static final int ITEM_STATUS_REORDER = 32;

    /** Replenish Item Status Code */
    public static final int ITEM_STATUS_REPLENISH = 33;

    /** Vendor Item override is activated for item */
    public static final int ITEM_STATUS_OVERRIDE_ACTIVE = 34;

    /** Vendor Item override is removed for item */
    public static final int ITEM_STATUS_OVERRIDE_INACTIVE = 35;

    /** Constant that indicates that item is active   */
    public static final int ITEM_ACTIVE_YES = 1;

    /** Constant that indicates that item has been inactive   */
    public static final int ITEM_ACTIVE_NO = 0;

    /** Value indicating Inventory item is overriden by vendor item data */
    public static final int ITEM_OVERRIDE_YES = 1;

    /** Value indicating Inventory item is not overriden by vendor item data */
    public static final int ITEM_OVERRIDE_NO = 0;
}