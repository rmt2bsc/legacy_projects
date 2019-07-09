package com.bean.criteria;

import com.util.SystemException;


/**
 * Peer object that maps to the vw_purchase_order_list database table/view.
 *
 * @author Roy Terrell.
 */
public class PurchaseOrderCriteria extends AncestorQueryCriteria {

/** The javabean property equivalent of database column vw_purchase_order_list.vendor_id */
  private String qry_VendorId;
/** The javabean property equivalent of database column vw_purchase_order_list.status_id */
  private String qry_StatusId;
/** The javabean property equivalent of database column vw_purchase_order_list.ref_no */
  private String qry_RefNo;
/** The javabean property equivalent of database column vw_purchase_order_list.total */
  private String qry_Total;
/** The javabean property equivalent of database column vw_purchase_order_list.status_description */
  private String qry_StatusDescription;
/** The javabean property equivalent of database column vw_purchase_order_list.status_hist_id */
  private String qry_StatusHistId;
/** The javabean property equivalent of database column vw_purchase_order_list.effective_date */
  private String qry_EffectiveDate;
/** The javabean property equivalent of database column vw_purchase_order_list.end_date */
  private String qry_EndDate;
/** The javabean property equivalent of database column vw_purchase_order_list.user_id */
  private String qry_UserId;
/** The javabean property equivalent of database column vw_purchase_order_list.account_number */
  private String qry_AccountNumber;
/** The javabean property equivalent of database column vw_purchase_order_list.credit_limit */
  private String qry_CreditLimit;
/** The javabean property equivalent of database column vw_purchase_order_list.credit_type_id */
  private String qry_CreditTypeId;
/** The javabean property equivalent of database column vw_purchase_order_list.creditor_type_id */
  private String qry_CreditorTypeId;
/** The javabean property equivalent of database column vw_purchase_order_list.longname */
  private String qry_Longname;
/** The javabean property equivalent of database column vw_purchase_order_list.shortname */
  private String qry_Shortname;
/** The javabean property equivalent of database column vw_purchase_order_list.tax_id */
  private String qry_TaxId;
  
  private String qry_ItemNumber;
  private String qry_ItemDescription;
  private String qry_VendorItemNumber;
  private String qry_SerialNo;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public PurchaseOrderCriteria() throws SystemException {
	  super();
  }
  
  public static PurchaseOrderCriteria getInstance() {
      try {
          return new PurchaseOrderCriteria();
      }
      catch (SystemException e) {
          return null;
      }
  }
  

  /**
   * Initializes all properties to spaces.
   *
   *
   * @author Roy Terrell.
   */
  	public void initBean() throws SystemException {
  		super.initBean();
  		this.qry_VendorId = "";
  		this.qry_StatusId = "";
  		this.qry_RefNo = "";
  		this.qry_Total = "";
  		this.qry_StatusDescription = "";
  		this.qry_StatusHistId = "";
  		this.qry_EffectiveDate = "";
  		this.qry_EndDate = "";
  		this.qry_UserId = "";
  		this.qry_AccountNumber = "";
  		this.qry_CreditLimit = "";
  		this.qry_CreditTypeId = "";
  		this.qry_CreditorTypeId = "";
  		this.qry_Longname = "";
  		this.qry_Shortname = "";
  		this.qry_TaxId = "";
  		this.qry_ItemNumber = "";
  		this.qry_ItemDescription = "";
  		this.qry_VendorItemNumber = "";
  		this.qry_SerialNo = "";
  	}
  	
/**
 * Sets the value of member variable vendorId
 *
 * @author Roy Terrell.
 */
  public void setQry_VendorId(String value) {
    this.qry_VendorId = value;
  }
/**
 * Gets the value of member variable vendorId
 *
 * @author Roy Terrell.
 */
  public String getQry_VendorId() {
    return this.qry_VendorId == null ? "" : this.qry_VendorId;
  }
/**
 * Sets the value of member variable statusId
 *
 * @author Roy Terrell.
 */
  public void setQry_StatusId(String value) {
    this.qry_StatusId = value;
  }
/**
 * Gets the value of member variable statusId
 *
 * @author Roy Terrell.
 */
  public String getQry_StatusId() {
    return this.qry_StatusId == null ? "" : this.qry_StatusId;
  }
/**
 * Sets the value of member variable refNo
 *
 * @author Roy Terrell.
 */
  public void setQry_RefNo(String value) {
    this.qry_RefNo = value;
  }
/**
 * Gets the value of member variable refNo
 *
 * @author Roy Terrell.
 */
  public String getQry_RefNo() {
    return this.qry_RefNo == null ? "" : this.qry_RefNo;
  }
/**
 * Sets the value of member variable total
 *
 * @author Roy Terrell.
 */
  public void setQry_Total(String value) {
    this.qry_Total = value;
  }
/**
 * Gets the value of member variable total
 *
 * @author Roy Terrell.
 */
  public String getQry_Total() {
    return this.qry_Total == null ? "" : this.qry_Total;
  }
/**
 * Sets the value of member variable statusDescription
 *
 * @author Roy Terrell.
 */
  public void setQry_StatusDescription(String value) {
    this.qry_StatusDescription = value;
  }
/**
 * Gets the value of member variable statusDescription
 *
 * @author Roy Terrell.
 */
  public String getQry_StatusDescription() {
    return this.qry_StatusDescription == null ? "" : this.qry_StatusDescription;
  }
/**
 * Sets the value of member variable statusHistId
 *
 * @author Roy Terrell.
 */
  public void setQry_StatusHistId(String value) {
    this.qry_StatusHistId = value;
  }
/**
 * Gets the value of member variable statusHistId
 *
 * @author Roy Terrell.
 */
  public String getQry_StatusHistId() {
    return this.qry_StatusHistId == null ? "" : this.qry_StatusHistId; 
  }
/**
 * Sets the value of member variable effectiveDate
 *
 * @author Roy Terrell.
 */
  public void setQry_EffectiveDate(String value) {
    this.qry_EffectiveDate = value;
  }
/**
 * Gets the value of member variable effectiveDate
 *
 * @author Roy Terrell.
 */
  public String getQry_EffectiveDate() {
    return this.qry_EffectiveDate == null ? "" : this.qry_EffectiveDate;
  }
/**
 * Sets the value of member variable endDate
 *
 * @author Roy Terrell.
 */
  public void setQry_EndDate(String value) {
    this.qry_EndDate = value;
  }
/**
 * Gets the value of member variable endDate
 *
 * @author Roy Terrell.
 */
  public String getQry_EndDate() {
    return this.qry_EndDate == null ? "" : this.qry_EndDate;
  }
/**
 * Sets the value of member variable userId
 *
 * @author Roy Terrell.
 */
  public void setQry_UserId(String value) {
    this.qry_UserId = value;
  }
/**
 * Gets the value of member variable userId
 *
 * @author Roy Terrell.
 */
  public String getQry_UserId() {
    return this.qry_UserId == null ? "" : this.qry_UserId;
  }
/**
 * Sets the value of member variable accountNumber
 *
 * @author Roy Terrell.
 */
  public void setQry_AccountNumber(String value) {
    this.qry_AccountNumber = value;
  }
/**
 * Gets the value of member variable accountNumber
 *
 * @author Roy Terrell.
 */
  public String getQry_AccountNumber() {
    return this.qry_AccountNumber == null ? "" : this.qry_AccountNumber;
  }
/**
 * Sets the value of member variable creditLimit
 *
 * @author Roy Terrell.
 */
  public void setQry_CreditLimit(String value) {
    this.qry_CreditLimit = value;
  }
/**
 * Gets the value of member variable creditLimit
 *
 * @author Roy Terrell.
 */
  public String getQry_CreditLimit() {
    return this.qry_CreditLimit == null ? "" : this.qry_CreditLimit;
  }
/**
 * Sets the value of member variable creditTypeId
 *
 * @author Roy Terrell.
 */
  public void setQry_CreditTypeId(String value) {
    this.qry_CreditTypeId = value;
  }
/**
 * Gets the value of member variable creditTypeId
 *
 * @author Roy Terrell.
 */
  public String getQry_CreditTypeId() {
    return this.qry_CreditTypeId == null ? "" : this.qry_CreditTypeId;
  }
/**
 * Sets the value of member variable creditorTypeId
 *
 * @author Roy Terrell.
 */
  public void setQry_CreditorTypeId(String value) {
    this.qry_CreditorTypeId = value;
  }
/**
 * Gets the value of member variable creditorTypeId
 *
 * @author Roy Terrell.
 */
  public String getQry_CreditorTypeId() {
    return this.qry_CreditorTypeId == null ? "" : this.qry_CreditorTypeId;
  }
/**
 * Sets the value of member variable longname
 *
 * @author Roy Terrell.
 */
  public void setQry_Longname(String value) {
    this.qry_Longname = value;
  }
/**
 * Gets the value of member variable longname
 *
 * @author Roy Terrell.
 */
  public String getQry_Longname() {
    return this.qry_Longname == null ? "" : this.qry_Longname;
  }
/**
 * Sets the value of member variable shortname
 *
 * @author Roy Terrell.
 */
  public void setQry_Shortname(String value) {
    this.qry_Shortname = value;
  }
/**
 * Gets the value of member variable shortname
 *
 * @author Roy Terrell.
 */
  public String getQry_Shortname() {
    return this.qry_Shortname == null ? "" : this.qry_Shortname;
  }
/**
 * Sets the value of member variable taxId
 *
 * @author Roy Terrell.
 */
  public void setQry_TaxId(String value) {
    this.qry_TaxId = value;
  }
/**
 * Gets the value of member variable taxId
 *
 * @author Roy Terrell.
 */
  public String getQry_TaxId() {
    return this.qry_TaxId == null ? "" : this.qry_TaxId; 
  }
  /**
   * Sets the item number.
   * @param value
   */
  public void setQry_ItemNumber(String value) {
	  this.qry_ItemNumber = value;
  }
  /**
   * Gets the item number
   * @return
   */
  public String getQry_ItemNumber() {
	  return this.qry_ItemNumber == null ? "" : this.qry_ItemNumber;
  }
  /**
   * Sets the item description
   * @param value
   */
  public void setQry_ItemDescription(String value) {
	  this.qry_ItemDescription = value;
  }
  /**
   * Gets the item description
   * @return
   */
  public String getQry_ItemDescription() {
	  return this.qry_ItemDescription == null ? "" : this.qry_ItemDescription;
  }
  /**
   * Sets the vendor item number.
   * @param value
   */
  public void setQry_VendorItemNumber(String value) {
	  this.qry_VendorItemNumber = value;
  }
  /**
   * Gets the vendor item number.
   * @return
   */
  public String getQry_VendorItemNumber() {
	  return this.qry_VendorItemNumber == null ? "" : this.qry_VendorItemNumber;
  }  
  /**
   * Sets the item serial number.
   * @param value
   */
  public void setQry_SerialNo(String value) {
	  this.qry_SerialNo = value;
  }
  /**
   * Gets the item serial  number
   * @return
   */
  public String getQry_SerialNo() {
	  return this.qry_SerialNo == null ? "" : this.qry_SerialNo;
  }  

}