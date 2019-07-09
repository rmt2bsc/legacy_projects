package com.gl.creditor;

/**
 * Creditor constants.
 * 
 * @author appdev
 *
 */
public class CreditorConst {
    /**
     * Vendor creditor type value
     */
    public static int CREDITORTYPE_VENDOR = 1;

    /** 
     * General Creditor type value
     */
    public static int CREDITORTYPE_CREDITOR = 2;

    /** 
     * Property names for search criteria deriving from the creditor entity.
     */
    public static String CRITERIATAGS_CREDITOR[] = { "Arg_AccountNo", "Arg_CreditorTypeId", "Arg_CreditorId" };

    /**
     * Property names for search criteria deriving from the business contact entity.
     */
    public static String CRITERIATAGS_CONTACT[] = { "Arg_Name", "Arg_PhoneMain", "Arg_TaxId" };

}