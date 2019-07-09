package com.constants;

import java.util.Hashtable;

public class AccountingConst {

   public static final int DEBIT_BAL_TYPE = 1;
   public static  final int CREDIT_BAL_TYPE = 2;
   
   /** Asset GL Account Type Code */
   public static  final int ACCT_TYPE_ASSET = 1;
   /** Liability GL Account Type Code */
   public static  final int ACCT_TYPE_LIABILITY = 2;
   /** Capital/ Owners Equity GL Account Type Code */
   public static  final int ACCT_TYPE_CAPITAL = 3;
   /** Revenue GL Account Type Code */
   public static  final int ACCT_TYPE_REVENUE = 4;
   /** Expense GL Account Type Code */
   public static  final int ACCT_TYPE_EXPENSE = 5;


   /** Creditor Type Code for vendor */
   public static  final int CREDITOR_TYPE_VENDOR = 1;
   /** Creditor Type Code for creditor */
   public static  final int CREDITOR_TYPE_CREDITOR = 2;

   
   /** Represents Creditor data */
   public static final String CLIENT_DATA_CREDITOR = "creditor";
   /** Represents the creditor's balance */
   public static final String CLIENT_DATA_CREDITOR_BAL = "creditorbalance";

   
    // Acccounting API Return Codes
  public static final int RC_ACCT_NAME_INVALID = -401;
  public static final int RC_ACCTCATG_NAME_INVALID = -402;
  public static final int RC_DUP_ACCTCATG_NAME = -403;
  public static final int RC_ACCTTYPE_NOTEXIST = -404;
  public static final int RC_ACCTNOERROR_ACCTTYPE = -405;
  public static final int RC_ACCTNOERROR_ACCTCAT = -406;
  public static final int RC_ACCTNOERROR_ACCTSEQ = -407;
  public static final int RC_ACCTCATG_NOTEXIST = -408;
  public static final int RC_DUP_ACCT_NAME = -409;
  public static final int RC_ACCT_NOTEXIST = -410;
  public static final int RC_FUNCNAME_INVALID = -411;
  public static final int RC_CRED1_INVALID = -412;
  public static final int RC_CRED2_INVALID = -413;
  public static final int RC_CRED3_INVALID = -414;
  public static final int RC_IM_NOVENDOR = -415;
  public static final int RC_IM_INVALID_GLACCT = -416;
  public static final int RC_IM_VENDITEMNO_INVALID = -417;
  public static final int RC_IM_VENDSERLIALNO_INVALID = -418;
  public static final int RC_IM_UNITCOST_INVALID = -419;
  public static final int RC_CUST_GLACCT_INVALID = -420;
  public static final int RC_CUST_CNTCT_INVALID = -421;




    // Acccounting API Messages
  public static final String MSG_ACCT_NAME_INVALID = "Account Name must be entered";
  public static final String MSG_ACCTCATG_NAME_INVALID = "Account Category Name is invalid";
  public static final String MSG_DUP_ACCTCATG_NAME = "Duplicate Account Category Name";
  public static final String MSG_DUP_ACCT_NAME = "Duplicate GL Account Name";
  public static final String MSG_ACCTTYPE_NOTEXIST = "Account Type does not exist";
  public static final String MSG_ACCTNOERROR_ACCTTYPE = "Account Number cannot be created without a valid account type code";
  public static final String MSG_ACCTNOERROR_ACCTCAT = "Account Number cannot be created without a valid account category type code";
  public static final String MSG_ACCTNOERROR_ACCTSEQ = "Account Number cannot be created without a valid account sequence number";
  public static final String MSG_ACCTCATG_NOTEXIST = "Account Category does not exist";
  public static final String MSG_ACCT_NOTEXIST = "General Ledger Account does not exist";
  public static final String MSG_FUNCNAME_INVALID = "Function Name must be provided to obtain a count of Account associations";
  public static final String MSG_CRED1_INVALID = "Cannot create Creditor when GL Account data is not available";
  public static final String MSG_CRED2_INVALID = "Cannot create Creditor when base creditor data is not available";
  public static final String MSG_CRED3_INVALID = "Function Name must be provided to obtain a count of Account associations";
  public static final String MSG_IM_NOVENDOR = "Item Master must be associated with a vendor";
  public static final String MSG_IM_INVALID_GLACCT = "Item Master must be associated with a base GL Account";
  public static final String MSG_IM_VENDITEMNO_INVALID = "Item Master Vendor Id is invalid...requires a value greater than zero";
  public static final String MSG_IM_VENDSERLIALNO_INVALID = "Item Master Vendor Serial Number is required";
  public static final String MSG_IM_UNITCOST_INVALID = "Item Master Unit Cost is invalid...requires a value greater than zero";
  public static final String MSG_CUST_GLACCT_INVALID = "Customer's GL Account number must be of a value greater than zero";
  public static final String MSG_CUST_CNTCT_INVALID = "Customer must have personal and/or business contact information";
}