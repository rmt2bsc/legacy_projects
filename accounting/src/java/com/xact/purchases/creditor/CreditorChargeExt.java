package com.xact.purchases.creditor;

import com.bean.RMT2BaseBean;

import com.util.SystemException;

/**
 * Peer object that maps to the vw_xact_credit_charge_list database table/view.
 *
 * @author auto generated.
 */
public class CreditorChargeExt extends RMT2BaseBean {
    private static final long serialVersionUID = 1L;

    /** The property name constant equivalent to property, XactId, of respective DataSource view. */
    public static final String PROP_XACTID = "XactId";

    /** The property name constant equivalent to property, XactAmount, of respective DataSource view. */
    public static final String PROP_XACTAMOUNT = "XactAmount";

    /** The property name constant equivalent to property, XactDate, of respective DataSource view. */
    public static final String PROP_XACTDATE = "XactDate";

    /** The property name constant equivalent to property, PostedDate, of respective DataSource view. */
    public static final String PROP_POSTEDDATE = "PostedDate";

    /** The property name constant equivalent to property, ConfirmNo, of respective DataSource view. */
    public static final String PROP_CONFIRMNO = "ConfirmNo";

    /** The property name constant equivalent to property, NegInstrNo, of respective DataSource view. */
    public static final String PROP_NEGINSTRNO = "NegInstrNo";

    /** The property name constant equivalent to property, TenderId, of respective DataSource view. */
    public static final String PROP_TENDERID = "TenderId";

    /** The property name constant equivalent to property, XactSubtypeId, of respective DataSource view. */
    public static final String PROP_XACTSUBTYPEID = "XactSubtypeId";

    /** The property name constant equivalent to property, TenderDescription, of respective DataSource view. */
    public static final String PROP_TENDERDESCRIPTION = "TenderDescription";

    /** The property name constant equivalent to property, XactTypeId, of respective DataSource view. */
    public static final String PROP_XACTTYPEID = "XactTypeId";

    /** The property name constant equivalent to property, XactTypeName, of respective DataSource view. */
    public static final String PROP_XACTTYPENAME = "XactTypeName";

    /** The property name constant equivalent to property, Reason, of respective DataSource view. */
    public static final String PROP_REASON = "Reason";

    /** The property name constant equivalent to property, XactEntryDate, of respective DataSource view. */
    public static final String PROP_XACTENTRYDATE = "XactEntryDate";

    /** The property name constant equivalent to property, UserId, of respective DataSource view. */
    public static final String PROP_USERID = "UserId";

    /** The property name constant equivalent to property, XactCategoryId, of respective DataSource view. */
    public static final String PROP_XACTCATEGORYID = "XactCategoryId";

    /** The property name constant equivalent to property, ToMultiplier, of respective DataSource view. */
    public static final String PROP_TOMULTIPLIER = "ToMultiplier";

    /** The property name constant equivalent to property, FromMultiplier, of respective DataSource view. */
    public static final String PROP_FROMMULTIPLIER = "FromMultiplier";

    /** The property name constant equivalent to property, ToAcctTypeId, of respective DataSource view. */
    public static final String PROP_TOACCTTYPEID = "ToAcctTypeId";

    /** The property name constant equivalent to property, ToAcctCatgId, of respective DataSource view. */
    public static final String PROP_TOACCTCATGID = "ToAcctCatgId";

    /** The property name constant equivalent to property, FromAcctTypeId, of respective DataSource view. */
    public static final String PROP_FROMACCTTYPEID = "FromAcctTypeId";

    /** The property name constant equivalent to property, FromAcctCatgId, of respective DataSource view. */
    public static final String PROP_FROMACCTCATGID = "FromAcctCatgId";

    /** The property name constant equivalent to property, HasSubsidiary, of respective DataSource view. */
    public static final String PROP_HASSUBSIDIARY = "HasSubsidiary";

    /** The property name constant equivalent to property, CreditorId, of respective DataSource view. */
    public static final String PROP_CREDITORID = "CreditorId";

    /** The property name constant equivalent to property, CreditorTypeId, of respective DataSource view. */
    public static final String PROP_CREDITORTYPEID = "CreditorTypeId";

    /** The property name constant equivalent to property, AccountNo, of respective DataSource view. */
    public static final String PROP_ACCOUNTNO = "AccountNo";

    /** The property name constant equivalent to property, CreditLimit, of respective DataSource view. */
    public static final String PROP_CREDITLIMIT = "CreditLimit";

    /** The property name constant equivalent to property, Active, of respective DataSource view. */
    public static final String PROP_ACTIVE = "Active";

    /** The property name constant equivalent to property, Apr, of respective DataSource view. */
    public static final String PROP_APR = "Apr";

    /** The property name constant equivalent to property, CreditorDateCreated, of respective DataSource view. */
    public static final String PROP_CREDITORDATECREATED = "CreditorDateCreated";

    /** The property name constant equivalent to property, CreditorTypeDescription, of respective DataSource view. */
    public static final String PROP_CREDITORTYPEDESCRIPTION = "CreditorTypeDescription";

    /** The property name constant equivalent to property, BusinessId, of respective DataSource view. */
    public static final String PROP_BUSINESSID = "BusinessId";

    /** The property name constant equivalent to property, Name, of respective DataSource view. */
    public static final String PROP_NAME = "Name";

    /** The property name constant equivalent to property, Shortname, of respective DataSource view. */
    public static final String PROP_SHORTNAME = "Shortname";

    /** The property name constant equivalent to property, ServType, of respective DataSource view. */
    public static final String PROP_SERVTYPE = "ServType";

    /** The property name constant equivalent to property, BusType, of respective DataSource view. */
    public static final String PROP_BUSTYPE = "BusType";

    /** The property name constant equivalent to property, ContactFirstname, of respective DataSource view. */
    public static final String PROP_CONTACTFIRSTNAME = "ContactFirstname";

    /** The property name constant equivalent to property, ContactLastname, of respective DataSource view. */
    public static final String PROP_CONTACTLASTNAME = "ContactLastname";

    /** The property name constant equivalent to property, ContactPhone, of respective DataSource view. */
    public static final String PROP_CONTACTPHONE = "ContactPhone";

    /** The property name constant equivalent to property, ContactExt, of respective DataSource view. */
    public static final String PROP_CONTACTEXT = "ContactExt";

    /** The property name constant equivalent to property, TaxId, of respective DataSource view. */
    public static final String PROP_TAXID = "TaxId";

    /** The property name constant equivalent to property, Website, of respective DataSource view. */
    public static final String PROP_WEBSITE = "Website";

    /** The property name constant equivalent to property, Balance, of respective DataSource view. */
    public static final String PROP_BALANCE = "Balance";
    
    public static final String PROP_DOCUMENTID = "DocumentId";

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.xact_id */
    private int xactId;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.xact_amount */
    private double xactAmount;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.xact_date */
    private java.util.Date xactDate;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.posted_date */
    private java.util.Date postedDate;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.confirm_no */
    private String confirmNo;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.neg_instr_no */
    private String negInstrNo;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.tender_id */
    private int tenderId;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.xact_subtype_id */
    private int xactSubtypeId;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.tender_description */
    private String tenderDescription;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.xact_type_id */
    private int xactTypeId;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.xact_type_name */
    private String xactTypeName;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.reason */
    private String reason;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.xact_entry_date */
    private java.util.Date xactEntryDate;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.user_id */
    private String userId;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.xact_category_id */
    private int xactCategoryId;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.to_multiplier */
    private int toMultiplier;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.from_multiplier */
    private int fromMultiplier;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.to_acct_type_id */
    private int toAcctTypeId;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.to_acct_catg_id */
    private int toAcctCatgId;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.from_acct_type_id */
    private int fromAcctTypeId;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.from_acct_catg_id */
    private int fromAcctCatgId;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.has_subsidiary */
    private int hasSubsidiary;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.creditor_id */
    private int creditorId;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.creditor_type_id */
    private int creditorTypeId;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.account_no */
    private String accountNo;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.credit_limit */
    private double creditLimit;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.active */
    private int active;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.apr */
    private double apr;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.creditor_date_created */
    private java.util.Date creditorDateCreated;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.creditor_type_description */
    private String creditorTypeDescription;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.business_id */
    private int businessId;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.name */
    private String name;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.shortname */
    private String shortname;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.serv_type */
    private int servType;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.bus_type */
    private int busType;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.contact_firstname */
    private String contactFirstname;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.contact_lastname */
    private String contactLastname;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.contact_phone */
    private String contactPhone;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.contact_ext */
    private String contactExt;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.tax_id */
    private String taxId;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.website */
    private String website;

    /** The javabean property equivalent of database column vw_xact_credit_charge_list.balance */
    private double balance;
    
    private int documentId;

    // Getter/Setter Methods

    /**
     * Default constructor.
     *
     * @author auto generated.
     */
    public CreditorChargeExt() throws SystemException {
	super();
    }

    /**
     * Sets the value of member variable xactId
     *
     * @author auto generated.
     */
    public void setXactId(int value) {
	this.xactId = value;
    }

    /**
     * Gets the value of member variable xactId
     *
     * @author atuo generated.
     */
    public int getXactId() {
	return this.xactId;
    }

    /**
     * Sets the value of member variable xactAmount
     *
     * @author auto generated.
     */
    public void setXactAmount(double value) {
	this.xactAmount = value;
    }

    /**
     * Gets the value of member variable xactAmount
     *
     * @author atuo generated.
     */
    public double getXactAmount() {
	return this.xactAmount;
    }

    /**
     * Sets the value of member variable xactDate
     *
     * @author auto generated.
     */
    public void setXactDate(java.util.Date value) {
	this.xactDate = value;
    }

    /**
     * Gets the value of member variable xactDate
     *
     * @author atuo generated.
     */
    public java.util.Date getXactDate() {
	return this.xactDate;
    }

    /**
     * Sets the value of member variable postedDate
     *
     * @author auto generated.
     */
    public void setPostedDate(java.util.Date value) {
	this.postedDate = value;
    }

    /**
     * Gets the value of member variable postedDate
     *
     * @author atuo generated.
     */
    public java.util.Date getPostedDate() {
	return this.postedDate;
    }

    /**
     * Sets the value of member variable confirmNo
     *
     * @author auto generated.
     */
    public void setConfirmNo(String value) {
	this.confirmNo = value;
    }

    /**
     * Gets the value of member variable confirmNo
     *
     * @author atuo generated.
     */
    public String getConfirmNo() {
	return this.confirmNo;
    }

    /**
     * Sets the value of member variable negInstrNo
     *
     * @author auto generated.
     */
    public void setNegInstrNo(String value) {
	this.negInstrNo = value;
    }

    /**
     * Gets the value of member variable negInstrNo
     *
     * @author atuo generated.
     */
    public String getNegInstrNo() {
	return this.negInstrNo;
    }

    /**
     * Sets the value of member variable tenderId
     *
     * @author auto generated.
     */
    public void setTenderId(int value) {
	this.tenderId = value;
    }

    /**
     * Gets the value of member variable tenderId
     *
     * @author atuo generated.
     */
    public int getTenderId() {
	return this.tenderId;
    }

    /**
     * Sets the value of member variable xactSubtypeId
     *
     * @author auto generated.
     */
    public void setXactSubtypeId(int value) {
	this.xactSubtypeId = value;
    }

    /**
     * Gets the value of member variable xactSubtypeId
     *
     * @author atuo generated.
     */
    public int getXactSubtypeId() {
	return this.xactSubtypeId;
    }

    /**
     * Sets the value of member variable tenderDescription
     *
     * @author auto generated.
     */
    public void setTenderDescription(String value) {
	this.tenderDescription = value;
    }

    /**
     * Gets the value of member variable tenderDescription
     *
     * @author atuo generated.
     */
    public String getTenderDescription() {
	return this.tenderDescription;
    }

    /**
     * Sets the value of member variable xactTypeId
     *
     * @author auto generated.
     */
    public void setXactTypeId(int value) {
	this.xactTypeId = value;
    }

    /**
     * Gets the value of member variable xactTypeId
     *
     * @author atuo generated.
     */
    public int getXactTypeId() {
	return this.xactTypeId;
    }

    /**
     * Sets the value of member variable xactTypeName
     *
     * @author auto generated.
     */
    public void setXactTypeName(String value) {
	this.xactTypeName = value;
    }

    /**
     * Gets the value of member variable xactTypeName
     *
     * @author atuo generated.
     */
    public String getXactTypeName() {
	return this.xactTypeName;
    }

    /**
     * Sets the value of member variable reason
     *
     * @author auto generated.
     */
    public void setReason(String value) {
	this.reason = value;
    }

    /**
     * Gets the value of member variable reason
     *
     * @author atuo generated.
     */
    public String getReason() {
	return this.reason;
    }

    /**
     * Sets the value of member variable xactEntryDate
     *
     * @author auto generated.
     */
    public void setXactEntryDate(java.util.Date value) {
	this.xactEntryDate = value;
    }

    /**
     * Gets the value of member variable xactEntryDate
     *
     * @author atuo generated.
     */
    public java.util.Date getXactEntryDate() {
	return this.xactEntryDate;
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
     * Sets the value of member variable xactCategoryId
     *
     * @author auto generated.
     */
    public void setXactCategoryId(int value) {
	this.xactCategoryId = value;
    }

    /**
     * Gets the value of member variable xactCategoryId
     *
     * @author atuo generated.
     */
    public int getXactCategoryId() {
	return this.xactCategoryId;
    }

    /**
     * Sets the value of member variable toMultiplier
     *
     * @author auto generated.
     */
    public void setToMultiplier(int value) {
	this.toMultiplier = value;
    }

    /**
     * Gets the value of member variable toMultiplier
     *
     * @author atuo generated.
     */
    public int getToMultiplier() {
	return this.toMultiplier;
    }

    /**
     * Sets the value of member variable fromMultiplier
     *
     * @author auto generated.
     */
    public void setFromMultiplier(int value) {
	this.fromMultiplier = value;
    }

    /**
     * Gets the value of member variable fromMultiplier
     *
     * @author atuo generated.
     */
    public int getFromMultiplier() {
	return this.fromMultiplier;
    }

    /**
     * Sets the value of member variable toAcctTypeId
     *
     * @author auto generated.
     */
    public void setToAcctTypeId(int value) {
	this.toAcctTypeId = value;
    }

    /**
     * Gets the value of member variable toAcctTypeId
     *
     * @author atuo generated.
     */
    public int getToAcctTypeId() {
	return this.toAcctTypeId;
    }

    /**
     * Sets the value of member variable toAcctCatgId
     *
     * @author auto generated.
     */
    public void setToAcctCatgId(int value) {
	this.toAcctCatgId = value;
    }

    /**
     * Gets the value of member variable toAcctCatgId
     *
     * @author atuo generated.
     */
    public int getToAcctCatgId() {
	return this.toAcctCatgId;
    }

    /**
     * Sets the value of member variable fromAcctTypeId
     *
     * @author auto generated.
     */
    public void setFromAcctTypeId(int value) {
	this.fromAcctTypeId = value;
    }

    /**
     * Gets the value of member variable fromAcctTypeId
     *
     * @author atuo generated.
     */
    public int getFromAcctTypeId() {
	return this.fromAcctTypeId;
    }

    /**
     * Sets the value of member variable fromAcctCatgId
     *
     * @author auto generated.
     */
    public void setFromAcctCatgId(int value) {
	this.fromAcctCatgId = value;
    }

    /**
     * Gets the value of member variable fromAcctCatgId
     *
     * @author atuo generated.
     */
    public int getFromAcctCatgId() {
	return this.fromAcctCatgId;
    }

    /**
     * Sets the value of member variable hasSubsidiary
     *
     * @author auto generated.
     */
    public void setHasSubsidiary(int value) {
	this.hasSubsidiary = value;
    }

    /**
     * Gets the value of member variable hasSubsidiary
     *
     * @author atuo generated.
     */
    public int getHasSubsidiary() {
	return this.hasSubsidiary;
    }

    /**
     * Sets the value of member variable creditorId
     *
     * @author auto generated.
     */
    public void setCreditorId(int value) {
	this.creditorId = value;
    }

    /**
     * Gets the value of member variable creditorId
     *
     * @author atuo generated.
     */
    public int getCreditorId() {
	return this.creditorId;
    }

    /**
     * Sets the value of member variable creditorTypeId
     *
     * @author auto generated.
     */
    public void setCreditorTypeId(int value) {
	this.creditorTypeId = value;
    }

    /**
     * Gets the value of member variable creditorTypeId
     *
     * @author atuo generated.
     */
    public int getCreditorTypeId() {
	return this.creditorTypeId;
    }

    /**
     * Sets the value of member variable accountNo
     *
     * @author auto generated.
     */
    public void setAccountNo(String value) {
	this.accountNo = value;
    }

    /**
     * Gets the value of member variable accountNo
     *
     * @author atuo generated.
     */
    public String getAccountNo() {
	return this.accountNo;
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
     * Sets the value of member variable active
     *
     * @author auto generated.
     */
    public void setActive(int value) {
	this.active = value;
    }

    /**
     * Gets the value of member variable active
     *
     * @author atuo generated.
     */
    public int getActive() {
	return this.active;
    }

    /**
     * Sets the value of member variable apr
     *
     * @author auto generated.
     */
    public void setApr(double value) {
	this.apr = value;
    }

    /**
     * Gets the value of member variable apr
     *
     * @author atuo generated.
     */
    public double getApr() {
	return this.apr;
    }

    /**
     * Sets the value of member variable creditorDateCreated
     *
     * @author auto generated.
     */
    public void setCreditorDateCreated(java.util.Date value) {
	this.creditorDateCreated = value;
    }

    /**
     * Gets the value of member variable creditorDateCreated
     *
     * @author atuo generated.
     */
    public java.util.Date getCreditorDateCreated() {
	return this.creditorDateCreated;
    }

    /**
     * Sets the value of member variable creditorTypeDescription
     *
     * @author auto generated.
     */
    public void setCreditorTypeDescription(String value) {
	this.creditorTypeDescription = value;
    }

    /**
     * Gets the value of member variable creditorTypeDescription
     *
     * @author atuo generated.
     */
    public String getCreditorTypeDescription() {
	return this.creditorTypeDescription;
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
     * Sets the value of member variable balance
     *
     * @author auto generated.
     */
    public void setBalance(double value) {
	this.balance = value;
    }

    /**
     * Gets the value of member variable balance
     *
     * @author atuo generated.
     */
    public double getBalance() {
	return this.balance;
    }

    /**
     * Stubbed initialization method designed to implemented by developer.

     *
     * @author auto generated.
     */
    public void initBean() throws SystemException {
    }

    /**
     * @return the documentId
     */
    public int getDocumentId() {
        return documentId;
    }

    /**
     * @param documentId the documentId to set
     */
    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

  
}