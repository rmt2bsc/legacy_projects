package com.bean.criteria;

import com.util.SystemException;

/**
 * Peer object that maps to the vw_purchase_order_list database table/view.
 *
 * @author Roy Terrell.
 */
public class CreditChargeCriteria extends AncestorQueryCriteria {
    private static final long serialVersionUID = 6071136307013380116L;

    /** The javabean property equivalent of database column vw_purchase_order_list.id */
    private String qry_XactId;

    /** The javabean property equivalent of database column vw_purchase_order_list.vendor_id */
    private String qry_CreditorId;

    /** The javabean property equivalent of database column vw_purchase_order_list.ref_no */
    private String qry_RefNo;

    /** The javabean property equivalent of database column vw_purchase_order_list.effective_date */
    private String qry_XactDate;

    /** The javabean property equivalent of database column vw_purchase_order_list.end_date */
    private String qry_Reason;

    /** The javabean property equivalent of database column vw_purchase_order_list.user_id */
    private String qry_UserId;

    /** The javabean property equivalent of database column vw_purchase_order_list.account_number */
    private String qry_Item;

    /** The javabean property equivalent of database column vw_purchase_order_list.tax_id */
    private String qry_TaxId;

    /** The javabean property equivalent of database column vw_purchase_order_list.account_number */
    private String qry_AccountNo;

    private String qry_ItemDescription;
    
    private String qry_CreditorTypeId;

    /**
     * Default constructor.
     *
     * @author Roy Terrell.
     */
    public CreditChargeCriteria() throws SystemException {
	super();
    }

    public static CreditChargeCriteria getInstance() {
	try {
	    return new CreditChargeCriteria();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Initializes all properties to spaces.
     *
     * @author Roy Terrell.
     */
    public void initBean() throws SystemException {
	super.initBean();
	this.qry_XactId = "";
	this.qry_CreditorId = "";
	this.qry_RefNo = "";
	this.qry_XactDate = "";
	this.qry_Reason = "";
	this.qry_UserId = "";
	this.qry_Item = "";
	this.qry_TaxId = "";
	this.qry_AccountNo = "";
	this.qry_ItemDescription = "";
    }

    /**
     * Sets the value of member variable id
     *
     * @author Roy Terrell.
     */
    public void setQry_XactId(String value) {
	this.qry_XactId = value;
    }

    /**
     * Gets the value of member variable id
     *
     * @author Roy Terrell.
     */
    public String getQry_XactId() {
	return this.qry_XactId == null ? "" : this.qry_XactId;
    }

    /**
     * Sets the value of member variable vendorId
     *
     * @author Roy Terrell.
     */
    public void setQry_CreditorId(String value) {
	this.qry_CreditorId = value;
    }

    /**
     * Gets the value of member variable vendorId
     *
     * @author Roy Terrell.
     */
    public String getQry_CreditorId() {
	return this.qry_CreditorId == null ? "" : this.qry_CreditorId;
    }

    /**
     * Sets the value of member variable statusId
     *
     * @author Roy Terrell.
     */
    public void setQry_Item(String value) {
	this.qry_Item = value;
    }

    /**
     * Gets the value of member variable statusId
     *
     * @author Roy Terrell.
     */
    public String getQry_Item() {
	return this.qry_Item == null ? "" : this.qry_Item;
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
     * Sets the value of member variable statusHistId
     *
     * @author Roy Terrell.
     */
    public void setQry_Reason(String value) {
	this.qry_Reason = value;
    }

    /**
     * Gets the value of member variable statusHistId
     *
     * @author Roy Terrell.
     */
    public String getQry_Reason() {
	return this.qry_Reason == null ? "" : this.qry_Reason;
    }

    /**
     * Sets the value of member variable effectiveDate
     *
     * @author Roy Terrell.
     */
    public void setQry_XactDate(String value) {
	this.qry_XactDate = value;
    }

    /**
     * Gets the value of member variable effectiveDate
     *
     * @author Roy Terrell.
     */
    public String getQry_XactDate() {
	return this.qry_XactDate == null ? "" : this.qry_XactDate;
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
     * Sets the value of member variable accountNumber
     *
     * @author Roy Terrell.
     */
    public void setQry_AccountNo(String value) {
	this.qry_AccountNo = value;
    }

    /**
     * Gets the value of member variable accountNumber
     *
     * @author Roy Terrell.
     */
    public String getQry_AccountNo() {
	return this.qry_AccountNo == null ? "" : this.qry_AccountNo;
    }

    public void setQry_ItemDescription(String value) {
	this.qry_ItemDescription = value;
    }

    public String getQry_ItemDescription() {
	return this.qry_ItemDescription == null ? "" : this.qry_ItemDescription;
    }

    /**
     * @return the qry_CreditorTypeId
     */
    public String getQry_CreditorTypeId() {
        return qry_CreditorTypeId;
    }

    /**
     * @param qry_CreditorTypeId the qry_CreditorTypeId to set
     */
    public void setQry_CreditorTypeId(String qry_CreditorTypeId) {
        this.qry_CreditorTypeId = qry_CreditorTypeId;
    }
}