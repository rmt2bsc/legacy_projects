package com.bean.criteria;

import com.util.SystemException;

/**
 * Peer object that maps to the basic transaction selection criteria
 * 
 * @author Roy Terrell.
 */
public class XactCriteria extends AncestorQueryCriteria {
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
		} catch (SystemException e) {
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

}