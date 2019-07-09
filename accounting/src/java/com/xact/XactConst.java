package com.xact;

/**
 * Constants defined to supplement the transaction module.
 * 
 * @author Roy Terrell
 *
 */
public class XactConst {

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //  Attribute names used to map data to and from the HttpServletRequest object
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /** Base Transaction object attribute name used to map data to and from the HttpServletRequest object*/
    public static final String CLIENT_DATA_XACT = "xact";

    /** Base Transaction extension object attribute name used to map data to and from the HttpServletRequest object*/
    public static final String CLIENT_DATA_XACTEXT = "xactext";

    /** Request name for transaction list */
    public static final String CLIENT_DATA_XACTLIST = "xactList";

    /** Transaction Type object attribute name used to map data to and from the HttpServletRequest object*/
    public static final String CLIENT_DATA_XACTTYPE = "xactType";

    /** Transaction Category object attribute name used to map data to and from the HttpServletRequest object*/
    public static final String CLIENT_DATA_XACTCATG = "xactCatg";

    /** Base Transaction Items object attribute name used to map data to and from the HttpServletRequest object*/
    public static final String CLIENT_DATA_XACTITEMS = "xactTypeItems";

    /** Transaction Type Item List object attribute name used to map data to and from the HttpServletRequest object.  This is usually used when retrieving a list of items by transaction type. */
    public static final String CLIENT_DATA_XACTITEMTYPELIST = "xactTypeItemList";

    /** Sales payment history list attribute name used to map data to and from the HttpServletRequest object*/
    public static final String CLIENT_DATA_SALESPAYMENT_HIST = "PaymentHist";
    
    /** Transaction tender list attribute name used to map data to and from the HttpServletRequest object*/
    public static final String CLIENT_DATA_TENDERLIST = "TenderList";
    
    /** Total amount attribute name used to map data to and from the HttpServletRequest object*/
    public static final String CLIENT_DATA_TOTAL = "TotalAmount";
    /** Total item count attribute name used to map data to and from the HttpServletRequest object*/
    public static final String CLIENT_DATA_COUNT = "TotalCount";

    /** Row count indicator to determine total number of items that exist in the General transaction JSP */
    public static final String ITEM_COUNT_IND = "itemcount";

    /** Name of check box or radio button control used to select an item from a list transaction base items. */
    public static final String ITEM_SELECTOR = "selCbx";

    /** Reverse request code */
    public final static String REQ_REVERSE = "reverse";

    /** View request code */
    public final static String REQ_VIEW = "view";

    /** Add Item request code */
    public final static String REQ_ADDITEM = "addXactItem";

    /** Payment transaction request code */
    public final static String REQ_PAYMENT = "payment";

    /** Transacation List request code */
    public final static String REQ_TRANSACTIONS = "transactions";

    /** Vendor Items request code */
    public final static String REQ_VENDORITEMS = "vendoritemview";

    /** Base Transaction object attribute name used to map data to and from the HttpServletRequest object 
     *@deprecated 
     */
    public static final String PARM_XACT = "xact";

    /** Transaction Type object attribute name used to map data to and from the HttpServletRequest object
     * @deprecated
     */
    public static final String PARM_XACTTYPE = "xactType";

    /** Transaction Category object attribute name used to map data to and from the HttpServletRequest object 
     * @deprecated
     */
    public static final String PARM_XACTCATG = "xactCatg";

    /** Base Transaction Items object attribute name used to map data to and from the HttpServletRequest object
     * @deprecated
     */
    public static final String PARM_XACTITEMS = "xactTypeItems";

    /** Transaction Type Item List object attribute name used to map data to and from the HttpServletRequest object.  This is usually used when retrieving a list of items by transaction type. 
     * @deprecated
     */
    public static final String PARM_XACTITEMSLIST = "xactTypeItemList";

    /////////////////////////////////////////////////////////////////////
    // Numerical Transaction Category Codes 
    /////////////////////////////////////////////////////////////////////
    /** Bad Debts numerical Transaction Category Code*/
    public static final int CATG_BADDEBT = 1;

    /** Cash Receipts numerical Transaction Category Code*/
    public static final int CATG_CASHRECT = 2;

    /** Cash Payments numerical Transaction Category Code*/
    public static final int CATG_CASHPAY = 3;

    /** Credit Sales numerical Transaction Category Code*/
    public static final int CATG_CREDSALES = 4;

    /** Fees numerical Transaction Category Code*/
    public static final int CATG_FEES = 6;

    /** Interest numerical Transaction Category Code*/
    public static final int CATG_INTEREST = 7;

    /** Purchases numerical Transaction Category Code*/
    public static final int CATG_PURCH = 8;

    /** Sales Returns numerical Transaction Category Code*/
    public static final int CATG_SALESRET = 9;

    /** Bank Deposits numerical Transaction Category Code*/
    public static final int CATG_DEPOSIT = 10;

    /** Bank Withdrawals numerical Transaction Category Code*/
    public static final int CATG_WITHDRAW = 11;

    /** Write Offs numerical Transaction Category Code*/
    public static final int CATG_WRITEOFF = 12;

    /** Purchases Returns numerical Transaction Category Code*/
    public static final int CATG_PURCHRTN = 13;

    /** Sales Discounts numerical Transaction Category Code*/
    public static final int CATG_SALESDISC = 14;

    /** Purchases Discounts numerical Transaction Category Code*/
    public static final int CATG_PURCHDISC = 15;

    /** Sale of Merchandise numerical Transaction Category Code*/
    public static final int CATG_MNDSESALES = 16;

    /** Reversal Transaction Category Code */
    public static final int CATG_REVERSE = 17;

    /** Cancellation Transaction Category Code */
    public static final int CATG_CANCEL = 18;

    //////////////////////////////////////////////////////////////
    // Transaction Category String Codes
    //////////////////////////////////////////////////////////////
    /** Bad Debts string Transaction Category Code*/
    public static final String CATG_BADDEBT_STR = "BADDEBT";

    /** Cash Receipts string Transaction Category Code*/
    public static final String CATG_CASHRECT_STR = "CASHRECT";

    /** Cash Payments string Transaction Category Code*/
    public static final String CATG_CASHPAY_STR = "CASHPAY";

    /** Credit Sales string Transaction Category Code*/
    public static final String CATG_CREDSALES_STR = "CREDSALES";

    /** Fees string Transaction Category Code*/
    public static final String CATG_FEES_STR = "FEES";

    /** Interest string Transaction Category Code*/
    public static final String CATG_INTEREST_STR = "INTEREST";

    /** Purchases string Transaction Category Code*/
    public static final String CATG_PURCH_STR = "PURCH";

    /** Sales Returns string Transaction Categsory Code*/
    public static final String CATG_SALESRET_STR = "SALESRET";

    /** Bank Deposits string Transaction Category Code*/
    public static final String CATG_DEPOSIT_STR = "DEPOSIT";

    /** Bank Withdrawals string Transaction Category Code*/
    public static final String CATG_WITHDRAW_STR = "WITHDRAW";

    /** Write Offs string Transaction Category Code*/
    public static final String CATG_WRITEOFF_STR = "WRITEOFF";

    /** Purchases Returns string Transaction Category Code*/
    public static final String CATG_PURCHRTN_STR = "PURCHRTN";

    /** Sales Discounts string Transaction Category Code*/
    public static final String CATG_SALESDISC_STR = "SALESDISC";

    /** Purchases Discounts string Transaction Category Code*/
    public static final String CATG_PURCHDISC_STR = "PURCHDISC";

    /** Sale of Merchandise string Transaction Category Code*/
    public static final String CATG_MNDSESALES_STR = "REVERSE";

    /** Reverse string Transaction Category Code*/
    public static final String CATG_REVERSE_STR = "MNDSESALES";

    /** Cancel string Transacton Category Code */
    public static final String CATG_CANCEL_STR = "CANCEL";

    /** Multiplier used to reverse transactions */
    public final static double REVERSE_MULTIPLIER = -1;

    /** Cash Tender */
    public final static int TENDER_CASH = 11;

    /** Check Tender */
    public final static int TENDER_CHECK = 12;

    /** Credit Card tender */
    public final static int TENDER_CREDITCARD = 13;

    /** Money Order tender */
    public final static int TENDER_MONEYORDER = 14;

    /** Finance Company Credit */
    public final static int TENDER_COMPANY_CREDIT = 15;

    /** Debit Card tender */
    public final static int TENDER_DEBITCARD = 16;

    /** Insurance tender */
    public final static int TENDER_INSURANCE = 17;

    /** Other tender */
    public final static int TENDER_OTHER = 18;

    // Transaction API Return Codes
    public static final int RC_XACT_TYPE_INVALID = -601;

    public static final int RC_XACT_GLACCTID_INVALID = -602;

    public static final int RC_XACT_TARGET_NOT_INIT = -603;

    public static final int RC_XACT_OFFSET_NOT_INIT = -604;

    public static final int RC_XACT_OFFSET_NO_ELEM = -605;

    public static final int RC_XACT_DUP_GLACCTS = -606;

    public static final int RC_XACT_NOT_BAL = -607;

    public static final int RC_XACT_TRGT_NOT_BAL = -608;

    // Transaction API Messages
    public static final String MSG_XACT_TYPE_INVALID = "Transaction Type code must be greater than zero";

    public static final String MSG_XACT_GLACCTID_INVALID = "Target or one the Offset Transaction's GL Account ID must be a value greater than zero";

    public static final String MSG_XACT_TARGET_NOT_INIT = "Target Entry is not initialized";

    public static final String MSG_XACT_OFFSET_NOT_INIT = "Offset Entry is not initialized";

    public static final String MSG_XACT_OFFSET_NO_ELEM = "Offset Entry is initialized but does not contain any entries offsetting the Target Entry";

    public static final String MSG_XACT_DUP_GLACCTS = "A GL Account Id cannot exist as a debit and credit within a transaction";

    public static final String MSG_XACT_NOT_BAL = "Transaction is not balanced";

    public static final String MSG_XACT_TRGT_NOT_BAL = "Transaction and Target Entry Amounts cannot differ";

    /** Transaction polling error indicator */
    public final static String XACT_POLL_ERROR = "error";

    ///////////////////////////////////////////////////////////////////////////////
    // JSP Transaction origination codes
    //////////////////////////////////////////////////////////////////////////////
    /** General JSP origination code */
    public final static String XACT_JSP_ORIGIN_GENERAL = "general";

    /** Disbursement JSP origination code */
    public final static String XACT_JSP_ORIGIN_DISBURSEMENT = "cashdisb";

    /** Purchases JSP origination code */
    public final static String XACT_JSP_ORIGIN_PURCHASE = "purchase";

    /****************************************************************
     *                   Transaction Type Codes
     ****************************************************************/
    public final static int XACT_TYPE_CASHSALES = 1;

    public final static int XACT_TYPE_CASHPAY = 2;

    public final static int XACT_TYPE_SALESONACCTOUNT = 10;

    public final static int XACT_TYPE_CASHDISBACCT = 20;

    public final static int XACT_TYPE_CASHDISBEXP = 60;

    public final static int XACT_TYPE_CASHDISBASSET = 70;

    public final static int XACT_TYPE_SALESRETURNS = 90;

    public final static int XACT_TYPE_INVPURCHASES = 130;

    public final static int XACT_TYPE_CREDITCHARGE = 180;

    public final static int XACT_TYPE_REVERSE = 999;

    public final static int XACT_TYPE_CANCEL = 888;

    public final static int XACT_TYPE_FINAL = -100;

    /****************************************************************
     *             General Transaction Client actions
     ****************************************************************/
    public final static String RETURN = "return";

    public final static String BACK = "back";

    public final static String RESET = "reset";

    public final static String REFRESH_CF = "cfRefresh";

    public final static String VIEWJOURNALLIST = "viewjournallist";

    public final static String JOURNALDETAILS = "journaldetails";

    /************************************************************************
     * General Transaction Client actions
     ************************************************************************/
    /** @deprecated */
    public final static String SAVE = "addSave";

    /** @deprecated */
    public final static String NEWSEARCH = "newsearch";

    /** @deprecated */
    public final static String OLDSEARCH = "oldsearch";

    /** @deprecated */
    public final static String REVERSE = "reverse";

    /** @deprecated */
    public final static String SEARCH = "search";

    /** @deprecated */
    public final static String VIEW = "view";

    /** @deprecated */
    public final static String ADD = "add";

    /** @deprecated */
    public final static String ADDITEM = "addXactItem";

    /** @deprecated */
    public final static String EDIT = "edit";

    /*****************************************************************
     *        Sales Order Item specfic client actions
     *****************************************************************/
    public final static String SO_ADDOFFSET = "addOffset";

    public final static String SO_SELECTITEMS = "selectitems";

    public final static String SO_ADDITEM = "additem";

    /*****************************************************************
     *       Sales Order specific client actions
     *****************************************************************/
    public final static String SO_UPDATEORDER = "updateorder";

    public final static String SO_CANCELORDER = "cancelorder";

    public final static String SO_SALESRETURN = "salesrefund";

    public final static String SO_ADDSALESRETURN = "addsalesrefund";

    /*****************************************************************
     *      Cash Receipts specific client actions
     *****************************************************************/
    public final static String CR_SAVECUSTOMERPAYMENT = "applypayment";

    public final static String CR_CUSTOMERPAYMENT = "customerpayment";

    public final static String CR_CUSTOMERXACT = "customerxact";

    public final static String CR_REVERSE = "reversepayment";

    public final static String CR_ADDREVERSE = "addreversepayment";

    /*****************************************************************
     *  Creditor Cash Disbursements specific client actions
     *****************************************************************/
    public final static String CD_CREDITORPAYMENT = "creditorpayment";

    public final static String CD_SAVECREDITORPAYMENT = "applycreditorpayment";

    public final static String CD_CREDITORXACT = "creditorxact";

    public final static String CD_ADDXACT = "cashdisburseaddxact";

    public final static String CD_REVERSE = "cashdisbursereverse";

    public final static String CD_ADDREVERSE = "addcashdisbursereverse";

    /*****************************************************************
     *      Purchase Order specific client actions
     *****************************************************************/
    public final static String PO_SAVE = "applypo";

    public final static String PO_RECV = "recvpo";

    public final static String PO_FINAL = "finalizepo";

    public final static String PO_CANCEL = "cancelpo";

    public final static String PO_SEARCH = "searchpo";

    public final static String PO_ADDITEM = "addpoitem";
}