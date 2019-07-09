package com.bean.criteria;

import com.util.SystemException;

/**
 * Peer object that maps to the basic transaction selection criteria
 * 
 * @author Roy Terrell.
 */
public class GenericXactCriteria extends AncestorQueryCriteria {
    private static final long serialVersionUID = -8836471182512499468L;

    private String qry_XactId;

    private String qry_XactDate_1;

    private String qry_XactDate_2;

    private String qry_XactAmount_1;

    private String qry_XactAmount_2;
    
    private String qry_ItemAmount_1;

    private String qry_ItemAmount_2;

    private String qryRelOp_XactDate_1;

    private String qryRelOp_XactDate_2;

    private String qryRelOp_XactAmount_1;

    private String qryRelOp_XactAmount_2;
    
    private String qryRelOp_ItemAmount_1;

    private String qryRelOp_ItemAmount_2;
    
    private String qry_XactTypeId;
    
    private String qry_XactSubtypeId;

    private String qry_InvoiceNo;

    private String qry_XactReason;

    private String qry_BusinessName;

    private String qry_AccountNo;
    
    private String qry_ConfirmNo;
    
    private String qry_XactReason_ADVSRCHOPTS;



  

    /**
     * Default constructor
     * 
     * @throws SystemException
     */
    public GenericXactCriteria() throws SystemException {
	super();
    }

    /**
     * Instantiates a XactCriteria objects and returns the object to the caller.
     * 
     * @return XactCriteria
     */
    public static GenericXactCriteria getInstance() {
	try {
	    return new GenericXactCriteria();
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
	this.qry_XactDate_1 = "";
	this.qry_XactDate_2 = "";
	this.qry_XactAmount_1 = "";
	this.qry_XactAmount_2 = "";
	this.qry_XactTypeId = "";
	this.qryRelOp_XactDate_1 = "";
	this.qryRelOp_XactDate_2 = "";
	this.qryRelOp_XactAmount_1 = "";
	this.qryRelOp_XactAmount_2 = "";
	this.qry_XactReason = "";
	this.qry_ConfirmNo = "";
	this.qry_BusinessName = "";
	this.qry_InvoiceNo = "";
	this.qry_AccountNo = "";
	this.qry_XactSubtypeId = "";
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
     * Sets the transaction reason
     * 
     * @param value
     */
    public void setQry_XactReason(String value) {
	this.qry_XactReason = value;
    }

    /**
     * Gets the transaction reason
     * 
     * @return
     */
    public String getQry_XactReason() {
	return this.qry_XactReason;
    }


    /**
     * Sets the transaction account number.
     * 
     * @param value
     */
    public void setQry_XactAccountNo(String value) {
	this.qry_AccountNo = value;
    }

    /**
     * Gets the transaction account number.
     * 
     * @return
     */
    public String getQry_XactAccountNo() {
	return this.qry_AccountNo;
    }

    /**
     * @return the qry_XactSubtypeId
     */
    public String getQry_XactSubtypeId() {
        return qry_XactSubtypeId;
    }

    /**
     * @param qry_XactSubtypeId the qry_XactSubtypeId to set
     */
    public void setQry_XactSubtypeId(String qry_XactSubtypeId) {
        this.qry_XactSubtypeId = qry_XactSubtypeId;
    }

    /**
     * @return the qry_InvoiceNo
     */
    public String getQry_InvoiceNo() {
        return qry_InvoiceNo;
    }

    /**
     * @param qry_InvoiceNo the qry_InvoiceNo to set
     */
    public void setQry_InvoiceNo(String qry_InvoiceNo) {
        this.qry_InvoiceNo = qry_InvoiceNo;
    }

    /**
     * @return the qry_BusinessName
     */
    public String getQry_BusinessName() {
        return qry_BusinessName;
    }

    /**
     * @param qry_BusinessName the qry_BusinessName to set
     */
    public void setQry_BusinessName(String qry_BusinessName) {
        this.qry_BusinessName = qry_BusinessName;
    }

    /**
     * @return the qry_AccountNo
     */
    public String getQry_AccountNo() {
        return qry_AccountNo;
    }

    /**
     * @param qry_AccountNo the qry_AccountNo to set
     */
    public void setQry_AccountNo(String qry_AccountNo) {
        this.qry_AccountNo = qry_AccountNo;
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
	 * @return the qry_Reason_ADVSRCHOPTS
	 */
	public String getQry_XactReason_ADVSRCHOPTS() {
	    return qry_XactReason_ADVSRCHOPTS;
	}

	/**
	 * @param qry_Reason_ADVSRCHOPTS the qry_Reason_ADVSRCHOPTS to set
	 */
	public void setQry_XactReason_ADVSRCHOPTS(String qry_Reason_ADVSRCHOPTS) {
	    this.qry_XactReason_ADVSRCHOPTS = qry_Reason_ADVSRCHOPTS;
	}

    
}