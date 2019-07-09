package com.bean.criteria;

import com.util.SystemException;

/**
 * Peer object that maps to the basic transaction selection criteria
 * 
 * @author Roy Terrell.
 */
public class XactCriteria extends AncestorQueryCriteria {
    private static final long serialVersionUID = -8836471182512499468L;

    public static final String PRESENT_TYPE_TRANS = "1";

    public static final String PRESENT_TYPE_ITEM = "2";

    private String qry_XactId;

    private String qry_XactDate;

    private String qry_XactDate_1;

    private String qry_XactDate_2;

    private String qry_XactAmount;

    private String qry_XactAmount_1;

    private String qry_XactAmount_2;

    private String qry_XactTypeId;

    private String qryRelOp_XactDate_1;

    private String qryRelOp_XactDate_2;

    private String qryRelOp_XactAmount_1;

    private String qryRelOp_XactAmount_2;

    private String qry_XactCatgId;

    private String qry_Reason;

    private String qry_XactCustomerNo;

    private String qry_XactCustomerName;

    private String qry_XactCreditorNo;

    private String qry_XactAccountNo;

    private String qry_XactCreditorName;

    private String qry_PresentationType;

    private String qry_XactTypeItemId;

    private String qry_ItemName;

    private String qry_Reason_ADVSRCHOPTS;
 
    private String qry_ItemAmount_1;
    
    private String qry_ItemAmount_2;
    
    private String qryRelOp_ItemAmount_1;
    
    private String qryRelOp_ItemAmount_2;
    
    private String qry_ConfirmNo;
    

    /**
    * Default constructor
    * 
    * @throws SystemException
    */
    public XactCriteria() throws SystemException {
	super();
    }

    /**
     * Instantiates a XactCriteria objects and returns the object to the caller.
     * 
     * @return XactCriteria
     */
    public static XactCriteria getInstance() {
	try {
	    return new XactCriteria();
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
	this.qry_XactDate = "";
	this.qry_XactDate_1 = "";
	this.qry_XactDate_2 = "";
	this.qry_XactAmount = "";
	this.qry_XactAmount_1 = "";
	this.qry_XactAmount_2 = "";
	this.qry_XactTypeId = "";
	this.qryRelOp_XactDate_1 = "";
	this.qryRelOp_XactDate_2 = "";
	this.qryRelOp_XactAmount_1 = "";
	this.qryRelOp_XactAmount_2 = "";
	this.qry_XactCatgId = "";
	this.qry_Reason = "";
	this.qry_XactCustomerNo = "";
	this.qry_XactCustomerName = "";
	this.qry_XactCreditorNo = "";
	this.qry_XactAccountNo = "";
	this.qry_XactCreditorName = "";
	this.qry_PresentationType = "";
	this.qry_XactTypeItemId = "";
	this.qry_ItemName = "";
	this.qry_Reason_ADVSRCHOPTS = "";
	this.qry_ItemAmount_1 = "";
	this.qry_ItemAmount_2 = "";
	this.qryRelOp_ItemAmount_1 = "";
	this.qryRelOp_ItemAmount_2 = "";
	this.qry_ConfirmNo = "";
    }

    /**
     * Sets the transaction id.
     * 
     * @param value
     */
    public void setQry_XactId(String value) {
	this.qry_XactId = value;
    }

    /**
     * Gets the transaction id
     * 
     * @return value of transaction id.
     */
    public String getQry_XactId() {
	return this.qry_XactId;
    }

    /**
     * Sets the transaction date
     * 
     * @param value
     */
    public void setQry_XactDate(String value) {
	this.qry_XactDate = value;
    }

    /**
     * Gets the transaction date.
     * 
     * @return
     */
    public String getQry_XactDate() {
	return this.qry_XactDate;
    }

    /**
     * Sets the beginning point of the transaction date range
     * 
     * @param value
     */
    public void setQry_XactDate_1(String value) {
	this.qry_XactDate_1 = value;
    }

    /**
     * Gets the beginning point of the transaction date range.
     * 
     * @return
     */
    public String getQry_XactDate_1() {
	return this.qry_XactDate_1;
    }

    /**
     * Sets the ending point of the transaction date range.
     * 
     * @param value
     */
    public void setQry_XactDate_2(String value) {
	this.qry_XactDate_2 = value;
    }

    /**
     * Gets the ending point of the transaction date range.
     * 
     * @return
     */
    public String getQry_XactDate_2() {
	return this.qry_XactDate_2;
    }

    /**
     * Sets the transaction amount.
     * 
     * @param value
     */
    public void setQry_XactAmount(String value) {
	this.qry_XactAmount = value;
    }

    /**
     * Gets the transaction amount
     * 
     * @return
     */
    public String getQry_XactAmount() {
	return this.qry_XactAmount;
    }

    /**
     * Sets the beginning point of the transaction amount range.
     * 
     * @param value
     */
    public void setQry_XactAmount_1(String value) {
	this.qry_XactAmount_1 = value;
    }

    /**
     * Gets the beginning point of the transaction amount range.
     * 
     * @return
     */
    public String getQry_XactAmount_1() {
	return this.qry_XactAmount_1;
    }

    /**
     * Sets the ending point of the transaction amount range.
     * 
     * @param value
     */
    public void setQry_XactAmount_2(String value) {
	this.qry_XactAmount_2 = value;
    }

    /**
     * Gets the ending point of the transaction amount range.
     * 
     * @return
     */
    public String getQry_XactAmount_2() {
	return this.qry_XactAmount_2;
    }

    /**
     * Sets the the transaction type id
     * 
     * @param value
     */
    public void setQry_XactTypeId(String value) {
	this.qry_XactTypeId = value;
    }

    /**
     * Gets the transaction type id.
     * 
     * @return
     */
    public String getQry_XactTypeId() {
	return this.qry_XactTypeId;
    }

    /**
     * Sets the relational operator for the beginning transaction date
     * 
     * @param value
     */
    public void setQryRelOp_XactDate_1(String value) {
	this.qryRelOp_XactDate_1 = value;
    }

    /**
     * Gets the relational operator for the beginning transaction date
     * 
     * @return
     */
    public String getQryRelOp_XactDate_1() {
	return this.qryRelOp_XactDate_1;
    }

    /**
     * Sets the relational operator for the ending transaction date
     * 
     * @param value
     */
    public void setQryRelOp_XactDate_2(String value) {
	this.qryRelOp_XactDate_2 = value;
    }

    /**
     * Gets the relational operator for the ending transaction date
     * 
     * @return
     */
    public String getQryRelOp_XactDate_2() {
	return this.qryRelOp_XactDate_2;
    }

    /**
     * Sets the relational operator for the beginning transaction amount
     * 
     * @param value
     */
    public void setQryRelOp_XactAmount_1(String value) {
	this.qryRelOp_XactAmount_1 = value;
    }

    /**
     * Gets the relational operator for the beginning transaction amount
     * 
     * @return
     */
    public String getQryRelOp_XactAmount_1() {
	return this.qryRelOp_XactAmount_1;
    }

    /**
     * Sets the relational operator for the ending transaction amount
     * 
     * @param value
     */
    public void setQryRelOp_XactAmount_2(String value) {
	this.qryRelOp_XactAmount_2 = value;
    }

    /**
     * Gets the relational operator for the endig transaction amount
     * 
     * @return
     */
    public String getQryRelOp_XactAmount_2() {
	return this.qryRelOp_XactAmount_2;
    }

    /**
     * Sets the transaction category id
     * 
     * @return
     */
    public String getQry_XactCatgId() {
	return this.qry_XactCatgId;
    }

    /**
     * Sets the transaction category id
     * 
     * @param value
     */
    public void setQry_XactCatgId(String value) {
	this.qry_XactCatgId = value;
    }

    /**
     * Sets the transaction reason
     * 
     * @param value
     */
    public void setQry_Reason(String value) {
	this.qry_Reason = value;
    }

    /**
     * Gets the transaction reason
     * 
     * @return
     */
    public String getQry_Reason() {
	return this.qry_Reason;
    }

    /**
     * Sets the customer number
     * 
     * @param value
     */
    public void setQry_XactCustomerNo(String value) {
	this.qry_XactCustomerNo = value;
    }

    /**
     * Gets the customer number.
     * 
     * @return
     */
    public String getQry_XactCustomerNo() {
	return this.qry_XactCustomerNo;
    }

    /**
     * Sets the customer name
     * 
     * @param value
     */
    public void setQry_XactCustomerName(String value) {
	this.qry_XactCustomerName = value;
    }

    /**
     * Gets the customer name
     * 
     * @return
     */
    public String getQry_XactCustomerName() {
	return this.qry_XactCustomerName;
    }

    /**
     * Sets the creditor number.
     * 
     * @param value
     */
    public void setQry_XactCreditorNo(String value) {
	this.qry_XactCreditorNo = value;
    }

    /**
     * Gets the creditor number.
     * 
     * @return
     */
    public String getQry_XactCreditorNo() {
	return this.qry_XactCreditorNo;
    }

    /**
     * Sets the transaction account number.
     * 
     * @param value
     */
    public void setQry_XactAccountNo(String value) {
	this.qry_XactAccountNo = value;
    }

    /**
     * Gets the transaction account number.
     * 
     * @return
     */
    public String getQry_XactAccountNo() {
	return this.qry_XactAccountNo;
    }

    /**
     * Sets the creditor's name
     * 
     * @param value
     */
    public void setQry_XactCreditorName(String value) {
	this.qry_XactCreditorName = value;
    }

    /**
     * Gets the creditor's name.
     * 
     * @return
     */
    public String getQry_XactCreditorName() {
	return this.qry_XactCreditorName;
    }

    /**
     * Gets the presentation layout type
     * @return String
     */
    public String getQry_PresentationType() {
	return qry_PresentationType;
    }

    /**
     * Sets the presentation layout type.
     * @param qry_PresentationType
     */
    public void setQry_PresentationType(String qry_PresentationType) {
	this.qry_PresentationType = qry_PresentationType;
    }

    /**
     * Gets transaction type item id.
     * 
     * @return String
     */
    public String getQry_XactTypeItemId() {
	return qry_XactTypeItemId;
    }

    /**
     * Sets transaction type item id
     * @param qry_XactTypeItemId
     */
    public void setQry_XactTypeItemId(String qry_XactTypeItemId) {
	this.qry_XactTypeItemId = qry_XactTypeItemId;
    }

    /**
     * Get item name
     * @return
     */
    public String getQry_ItemName() {
	return qry_ItemName;
    }

    /**
     * Set item name
     * @param qry_ItemName
     */
    public void setQry_ItemName(String qry_ItemName) {
	this.qry_ItemName = qry_ItemName;
    }

    /**
     * @return the qry_Reason_ADVSRCHOPTS
     */
    public String getQry_Reason_ADVSRCHOPTS() {
	return qry_Reason_ADVSRCHOPTS;
    }

    /**
     * @param qry_Reason_ADVSRCHOPTS the qry_Reason_ADVSRCHOPTS to set
     */
    public void setQry_Reason_ADVSRCHOPTS(String qry_Reason_ADVSRCHOPTS) {
	this.qry_Reason_ADVSRCHOPTS = qry_Reason_ADVSRCHOPTS;
    }

    /**
     * @return the qry_ItemAmount_1
     */
    public String getQry_ItemAmount_1() {
        return qry_ItemAmount_1;
    }

    /**
     * @param qry_ItemAmount_1 the qry_ItemAmount_1 to set
     */
    public void setQry_ItemAmount_1(String qry_ItemAmount_1) {
        this.qry_ItemAmount_1 = qry_ItemAmount_1;
    }

    /**
     * @return the qry_ItemAmount_2
     */
    public String getQry_ItemAmount_2() {
        return qry_ItemAmount_2;
    }

    /**
     * @param qry_ItemAmount_2 the qry_ItemAmount_2 to set
     */
    public void setQry_ItemAmount_2(String qry_ItemAmount_2) {
        this.qry_ItemAmount_2 = qry_ItemAmount_2;
    }

    /**
     * @return the qryRelOp_ItemAmount_1
     */
    public String getQryRelOp_ItemAmount_1() {
        return qryRelOp_ItemAmount_1;
    }

    /**
     * @param qryRelOp_ItemAmount_1 the qryRelOp_ItemAmount_1 to set
     */
    public void setQryRelOp_ItemAmount_1(String qryRelOp_ItemAmount_1) {
        this.qryRelOp_ItemAmount_1 = qryRelOp_ItemAmount_1;
    }

    /**
     * @return the qryRelOp_ItemAmount_2
     */
    public String getQryRelOp_ItemAmount_2() {
        return qryRelOp_ItemAmount_2;
    }

    /**
     * @param qryRelOp_ItemAmount_2 the qryRelOp_ItemAmount_2 to set
     */
    public void setQryRelOp_ItemAmount_2(String qryRelOp_ItemAmount_2) {
        this.qryRelOp_ItemAmount_2 = qryRelOp_ItemAmount_2;
    }

    /**
     * @return the qry_ConfirmNo
     */
    public String getQry_ConfirmNo() {
        return qry_ConfirmNo;
    }

    /**
     * @param qry_ConfirmNo the qry_ConfirmNo to set
     */
    public void setQry_ConfirmNo(String qry_ConfirmNo) {
        this.qry_ConfirmNo = qry_ConfirmNo;
    }

    
}