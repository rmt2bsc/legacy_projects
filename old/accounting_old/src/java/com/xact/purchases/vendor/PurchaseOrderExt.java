package com.xact.purchases.vendor;

import com.bean.RMT2BaseBean;

import com.util.SystemException;

/**
 * Peer object that maps to the vw_purchase_order_list database table/view.
 *
 * @author auto generated.
 */
public class PurchaseOrderExt extends RMT2BaseBean {
    private static final long serialVersionUID = 1L;

    public static final String PROP_ID = "Id";

    public static final String PROP_VENDORID = "VendorId";

    public static final String PROP_STATUSID = "StatusId";

    public static final String PROP_REFNO = "RefNo";

    public static final String PROP_TOTAL = "Total";

    public static final String PROP_STATUSDESCRIPTION = "StatusDescription";

    public static final String PROP_STATUSHISTID = "StatusHistId";

    public static final String PROP_EFFECTIVEDATE = "EffectiveDate";

    public static final String PROP_ENDDATE = "EndDate";

    public static final String PROP_USERID = "UserId";

    public static final String PROP_ACCOUNTNUMBER = "AccountNumber";

    public static final String PROP_CREDITLIMIT = "CreditLimit";

    public static final String PROP_CREDITTYPEID = "CreditTypeId";

    public static final String PROP_CREDITORTYPEID = "CreditorTypeId";

    public static final String PROP_BUSINESSID = "BusinessId";

    public static final String PROP_NAME = "Name";

    public static final String PROP_SHORTNAME = "Shortname";

    public static final String PROP_SERVTYPE = "ServType";

    public static final String PROP_BUSTYPE = "BusType";

    public static final String PROP_CONTACTFIRSTNAME = "ContactFirstname";

    public static final String PROP_CONTACTLASTNAME = "ContactLastname";

    public static final String PROP_CONTACTPHONE = "ContactPhone";

    public static final String PROP_CONTACTEXT = "ContactExt";

    public static final String PROP_TAXID = "TaxId";

    public static final String PROP_WEBSITE = "Website";

    /** The javabean property equivalent of database column vw_purchase_order_list.id */
    private int id;

    /** The javabean property equivalent of database column vw_purchase_order_list.vendor_id */
    private int vendorId;

    /** The javabean property equivalent of database column vw_purchase_order_list.status_id */
    private int statusId;

    /** The javabean property equivalent of database column vw_purchase_order_list.ref_no */
    private String refNo;

    /** The javabean property equivalent of database column vw_purchase_order_list.total */
    private double total;

    /** The javabean property equivalent of database column vw_purchase_order_list.status_description */
    private String statusDescription;

    /** The javabean property equivalent of database column vw_purchase_order_list.status_hist_id */
    private int statusHistId;

    /** The javabean property equivalent of database column vw_purchase_order_list.effective_date */
    private java.util.Date effectiveDate;

    /** The javabean property equivalent of database column vw_purchase_order_list.end_date */
    private java.util.Date endDate;

    /** The javabean property equivalent of database column vw_purchase_order_list.user_id */
    private String userId;

    /** The javabean property equivalent of database column vw_purchase_order_list.account_number */
    private String accountNumber;

    /** The javabean property equivalent of database column vw_purchase_order_list.credit_limit */
    private double creditLimit;

    /** The javabean property equivalent of database column vw_purchase_order_list.credit_type_id */
    private int creditTypeId;

    /** The javabean property equivalent of database column vw_purchase_order_list.creditor_type_id */
    private String creditorTypeId;

    /** The javabean property equivalent of database column vw_purchase_order_list.business_id */
    private int businessId;

    /** The javabean property equivalent of database column vw_purchase_order_list.name */
    private String name;

    /** The javabean property equivalent of database column vw_purchase_order_list.shortname */
    private String shortname;

    /** The javabean property equivalent of database column vw_purchase_order_list.serv_type */
    private int servType;

    /** The javabean property equivalent of database column vw_purchase_order_list.bus_type */
    private int busType;

    /** The javabean property equivalent of database column vw_purchase_order_list.contact_firstname */
    private String contactFirstname;

    /** The javabean property equivalent of database column vw_purchase_order_list.contact_lastname */
    private String contactLastname;

    /** The javabean property equivalent of database column vw_purchase_order_list.contact_phone */
    private String contactPhone;

    /** The javabean property equivalent of database column vw_purchase_order_list.contact_ext */
    private String contactExt;

    /** The javabean property equivalent of database column vw_purchase_order_list.tax_id */
    private String taxId;

    /** The javabean property equivalent of database column vw_purchase_order_list.website */
    private String website;

    /**
     * Default constructor.
     *
     * @author auto generated.
     */
    public PurchaseOrderExt() throws SystemException {
	super();
    }

    /**
     * Sets the value of member variable id
     *
     * @author auto generated.
     */
    public void setId(int value) {
	this.id = value;
    }

    /**
     * Gets the value of member variable id
     *
     * @author atuo generated.
     */
    public int getId() {
	return this.id;
    }

    /**
     * Sets the value of member variable vendorId
     *
     * @author auto generated.
     */
    public void setVendorId(int value) {
	this.vendorId = value;
    }

    /**
     * Gets the value of member variable vendorId
     *
     * @author atuo generated.
     */
    public int getVendorId() {
	return this.vendorId;
    }

    /**
     * Sets the value of member variable statusId
     *
     * @author auto generated.
     */
    public void setStatusId(int value) {
	this.statusId = value;
    }

    /**
     * Gets the value of member variable statusId
     *
     * @author atuo generated.
     */
    public int getStatusId() {
	return this.statusId;
    }

    /**
     * Sets the value of member variable refNo
     *
     * @author auto generated.
     */
    public void setRefNo(String value) {
	this.refNo = value;
    }

    /**
     * Gets the value of member variable refNo
     *
     * @author atuo generated.
     */
    public String getRefNo() {
	return this.refNo;
    }

    /**
     * Sets the value of member variable total
     *
     * @author auto generated.
     */
    public void setTotal(double value) {
	this.total = value;
    }

    /**
     * Gets the value of member variable total
     *
     * @author atuo generated.
     */
    public double getTotal() {
	return this.total;
    }

    /**
     * Sets the value of member variable statusDescription
     *
     * @author auto generated.
     */
    public void setStatusDescription(String value) {
	this.statusDescription = value;
    }

    /**
     * Gets the value of member variable statusDescription
     *
     * @author atuo generated.
     */
    public String getStatusDescription() {
	return this.statusDescription;
    }

    /**
     * Sets the value of member variable statusHistId
     *
     * @author auto generated.
     */
    public void setStatusHistId(int value) {
	this.statusHistId = value;
    }

    /**
     * Gets the value of member variable statusHistId
     *
     * @author atuo generated.
     */
    public int getStatusHistId() {
	return this.statusHistId;
    }

    /**
     * Sets the value of member variable effectiveDate
     *
     * @author auto generated.
     */
    public void setEffectiveDate(java.util.Date value) {
	this.effectiveDate = value;
    }

    /**
     * Gets the value of member variable effectiveDate
     *
     * @author atuo generated.
     */
    public java.util.Date getEffectiveDate() {
	return this.effectiveDate;
    }

    /**
     * Sets the value of member variable endDate
     *
     * @author auto generated.
     */
    public void setEndDate(java.util.Date value) {
	this.endDate = value;
    }

    /**
     * Gets the value of member variable endDate
     *
     * @author atuo generated.
     */
    public java.util.Date getEndDate() {
	return this.endDate;
    }

    /**
     * Sets the value of member variable userId
     *
     * @author auto generated.
     */
    public void setUserId(String value) {
	this.userId = value;
    }

    /**
     * Gets the value of member variable userId
     *
     * @author atuo generated.
     */
    public String getUserId() {
	return this.userId;
    }

    /**
     * Sets the value of member variable accountNumber
     *
     * @author auto generated.
     */
    public void setAccountNumber(String value) {
	this.accountNumber = value;
    }

    /**
     * Gets the value of member variable accountNumber
     *
     * @author atuo generated.
     */
    public String getAccountNumber() {
	return this.accountNumber;
    }

    /**
     * Sets the value of member variable creditLimit
     *
     * @author auto generated.
     */
    public void setCreditLimit(double value) {
	this.creditLimit = value;
    }

    /**
     * Gets the value of member variable creditLimit
     *
     * @author atuo generated.
     */
    public double getCreditLimit() {
	return this.creditLimit;
    }

    /**
     * Sets the value of member variable creditTypeId
     *
     * @author auto generated.
     */
    public void setCreditTypeId(int value) {
	this.creditTypeId = value;
    }

    /**
     * Gets the value of member variable creditTypeId
     *
     * @author atuo generated.
     */
    public int getCreditTypeId() {
	return this.creditTypeId;
    }

    /**
     * Sets the value of member variable creditorTypeId
     *
     * @author auto generated.
     */
    public void setCreditorTypeId(String value) {
	this.creditorTypeId = value;
    }

    /**
     * Gets the value of member variable creditorTypeId
     *
     * @author atuo generated.
     */
    public String getCreditorTypeId() {
	return this.creditorTypeId;
    }

    /**
     * Sets the value of member variable businessId
     *
     * @author auto generated.
     */
    public void setBusinessId(int value) {
	this.businessId = value;
    }

    /**
     * Gets the value of member variable businessId
     *
     * @author atuo generated.
     */
    public int getBusinessId() {
	return this.businessId;
    }

    /**
     * Sets the value of member variable name
     *
     * @author auto generated.
     */
    public void setName(String value) {
	this.name = value;
    }

    /**
     * Gets the value of member variable name
     *
     * @author atuo generated.
     */
    public String getName() {
	return this.name;
    }

    /**
     * Sets the value of member variable shortname
     *
     * @author auto generated.
     */
    public void setShortname(String value) {
	this.shortname = value;
    }

    /**
     * Gets the value of member variable shortname
     *
     * @author atuo generated.
     */
    public String getShortname() {
	return this.shortname;
    }

    /**
     * Sets the value of member variable servType
     *
     * @author auto generated.
     */
    public void setServType(int value) {
	this.servType = value;
    }

    /**
     * Gets the value of member variable servType
     *
     * @author atuo generated.
     */
    public int getServType() {
	return this.servType;
    }

    /**
     * Sets the value of member variable busType
     *
     * @author auto generated.
     */
    public void setBusType(int value) {
	this.busType = value;
    }

    /**
     * Gets the value of member variable busType
     *
     * @author atuo generated.
     */
    public int getBusType() {
	return this.busType;
    }

    /**
     * Sets the value of member variable contactFirstname
     *
     * @author auto generated.
     */
    public void setContactFirstname(String value) {
	this.contactFirstname = value;
    }

    /**
     * Gets the value of member variable contactFirstname
     *
     * @author atuo generated.
     */
    public String getContactFirstname() {
	return this.contactFirstname;
    }

    /**
     * Sets the value of member variable contactLastname
     *
     * @author auto generated.
     */
    public void setContactLastname(String value) {
	this.contactLastname = value;
    }

    /**
     * Gets the value of member variable contactLastname
     *
     * @author atuo generated.
     */
    public String getContactLastname() {
	return this.contactLastname;
    }

    /**
     * Sets the value of member variable contactPhone
     *
     * @author auto generated.
     */
    public void setContactPhone(String value) {
	this.contactPhone = value;
    }

    /**
     * Gets the value of member variable contactPhone
     *
     * @author atuo generated.
     */
    public String getContactPhone() {
	return this.contactPhone;
    }

    /**
     * Sets the value of member variable contactExt
     *
     * @author auto generated.
     */
    public void setContactExt(String value) {
	this.contactExt = value;
    }

    /**
     * Gets the value of member variable contactExt
     *
     * @author atuo generated.
     */
    public String getContactExt() {
	return this.contactExt;
    }

    /**
     * Sets the value of member variable taxId
     *
     * @author auto generated.
     */
    public void setTaxId(String value) {
	this.taxId = value;
    }

    /**
     * Gets the value of member variable taxId
     *
     * @author atuo generated.
     */
    public String getTaxId() {
	return this.taxId;
    }

    /**
     * Sets the value of member variable website
     *
     * @author auto generated.
     */
    public void setWebsite(String value) {
	this.website = value;
    }

    /**
     * Gets the value of member variable website
     *
     * @author atuo generated.
     */
    public String getWebsite() {
	return this.website;
    }

    /**
     * Stubbed initialization method designed to implemented by developer.

     *
     * @author auto generated.
     */
    public void initBean() throws SystemException {
    }
}