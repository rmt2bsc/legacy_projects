package com.xact.purchases.creditor;

public class CreditorPurchasesConst {

    /** Edit Credit Charge client action code */
    public static final String REQ_EDIT = "edit";

    /** Add Credit Charge client action code */
    public static final String REQ_ADD = "add";

    /** Add an item to the Credit Charge client action code */
    public static final String REQ_ADDITEM = "additem";

    /** Saves Credit Charge's new item */
    public static final String REQ_SAVE = "save";

    /** Build selection criteria and perform a search of Credit Charges client action code */
    public static final String REQ_SEARCH = "search";

    /** Open New Credit Charge Search Console client action code */
    public static final String REQ_NEWSEARCH = "newsearch";

    /** Display Credit Charge search screen using search criteria stored in session object client action code */
    public static final String REQ_OLDSEARCH = "oldsearch";

    /** Delete Credit Charge client action code */
    public static final String REQ_DELETE = "delete";

    /** Reverse Credit Charge client action code */
    public static final String REQ_REVERSE = "reverse";

    /** Request name for creditor */
    public static final String CLIENT_DATA_CREDITOR = "creditor";

    /** Request name for creditor list*/
    public static final String CLIENT_DATA_CREDIOTRLIST = "creditorlist";

    /** Request name for creditor purchases transaction list */
    public static final String CLIENT_DATA_XACTLIST = "xactList";

    /** Base Transaction object attribute name used to map data to and from the HttpServletRequest object*/
    public static final String CLIENT_DATA_XACT = "xact";

    /** Base Transaction extension object attribute name used to map data to and from the HttpServletRequest object*/
    public static final String CLIENT_DATA_XACTEXT = "xactext";

    /** Transaction Type object attribute name used to map data to and from the HttpServletRequest object*/
    public static final String CLIENT_DATA_XACTTYPE = "xactType";

    /** Transaction Category object attribute name used to map data to and from the HttpServletRequest object*/
    public static final String CLIENT_DATA_XACTCATG = "xactCatg";

    /** Base Transaction Items object attribute name used to map data to and from the HttpServletRequest object*/
    public static final String CLIENT_DATA_XACTITEMS = "xactTypeItems";
}