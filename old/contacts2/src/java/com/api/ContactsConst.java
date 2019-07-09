package com.api;

public class ContactsConst {

        //  Constants
  public static final String CONTACT_TYPE_BUSINESS = "1";
  public static final String CONTACT_TYPE_PERSONAL = "2";
  
  public static final String CLIENT_CONTACT_TYPE = "contacttype";
  public static final String CLIENT_DATA = "data";

       // Acccounting API Return Codes
  public static final int RC_PRSN_FN_INVALID = -501;
  public static final int RC_PRSN_LN_INVALID = -502;
  public static final int RC_PRSN_SSN_INVALID = -503;
  public static final int RC_PRSN_DOB_INVALID = -504;
  public static final int RC_BUS_LGNNM_INVALID = -505;
  public static final int RC_BUS_TAXID_INVALID = -506;
  public static final int RC_ADDR_ZIP_INVALID = -507;
  public static final int RC_ADDR_PERBUS_INVALID = -508;
  public static final int RC_ADDR_ZIP_NOTEXIST = -509;

    // Acccounting API Messages
  public static final String MSG_PRSN_FN_INVALID = "Person First Name must be supplied";
  public static final String MSG_PRSN_LN_INVALID = "Person Last Name must be supplied";
  public static final String MSG_PRSN_SSN_INVALID = "Social Security Number is invalid";
  public static final String MSG_PRSN_DOB_INVALID = "Date of Birth is invalid";
  public static final String MSG_BUS_LGNNM_INVALID = "Business Long Name is invalid";
  public static final String MSG_BUS_TAXID_INVALID = "Tax Id Number is invalid";
  public static final String MSG_ADDR_ZIP_INVALID = "Zip Code is invalid";
  public static final String MSG_ADDR_PERBUS_INVALID = "Person Id and Business Id cannot be null.   Either or both must exist.";
  public static final String MSG_ADDR_ZIP_NOTEXIST = "Zip Code is required";

}