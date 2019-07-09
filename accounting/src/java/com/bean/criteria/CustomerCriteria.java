package com.bean.criteria;

import com.util.SystemException;

/**
 * Peer object that maps customer selection criteria.
 * <p>
 * <b><u>NOTE</u></b>:&nbsp; This class did not capitalize the first
 * character following the prefix, "qry_", of its property names and still
 * functions successfully using the introspection methods of DataSourceAdapter
 * class.
 * 
 * @author Roy Terrell.
 */
public class CustomerCriteria extends AncestorQueryCriteria {
    private static final long serialVersionUID = -7440305695999872651L;

    private String qry_AccountNo;
    
    private String qry_CustomerId;
    
    private String qry_BusinessId;
    
    private String qry_CustomerIdList;
    
    private String qry_BusinessIdList;

    private String qry_Name;

    private String qry_TaxId;

    private String qry_PhoneMain;

    /**
     * Default constructor.
     * 
     * @author Roy Terrell.
     */
    private CustomerCriteria() throws SystemException {
	super();
    }

    /**
     * Creates an instance of CreditorCriteria class.
     * 
     * @return {@link CustomerCriteria}
     */
    public static CustomerCriteria getInstance() {
	try {
	    return new CustomerCriteria();
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Initializes all properties to spaces.  This is required so that the JSP client will 
     * not display null lieterals in its respective input fields. 
     *
     * @author Roy Terrell.
     */
    public void initBean() throws SystemException {
	super.initBean();
	// Initialize to spaces so that JSP client will not display null lieterals in its respective input fields
	this.qry_AccountNo = "";
	this.qry_Name = "";
	this.qry_TaxId = "";
	this.qry_PhoneMain = "";
	this.qry_CustomerId = "";
	this.qry_BusinessId = "";
	this.qry_CustomerIdList = "";
	this.qry_BusinessIdList = "";
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
     * @return the qry_Name
     */
    public String getQry_Name() {
	return qry_Name;
    }

    /**
     * @return the qry_TaxId
     */
    public String getQry_TaxId() {
	return qry_TaxId;
    }

    /**
     * @param qry_TaxId the qry_TaxId to set
     */
    public void setQry_TaxId(String qry_TaxId) {
	this.qry_TaxId = qry_TaxId;
    }

    /**
     * @param qry_Name the qry_Name to set
     */
    public void setQry_Name(String qry_Name) {
	this.qry_Name = qry_Name;
    }

    /**
     * @return the qry_PhoneMain
     */
    public String getQry_PhoneMain() {
	return qry_PhoneMain;
    }

    /**
     * @param qry_PhoneMain the qry_PhoneMain to set
     */
    public void setQry_PhoneMain(String qry_PhoneMain) {
	this.qry_PhoneMain = qry_PhoneMain;
    }

    /**
     * @return the qry_CustomerId
     */
    public String getQry_CustomerId() {
        return qry_CustomerId;
    }

    /**
     * @param qry_CustomerId the qry_CustomerId to set
     */
    public void setQry_CustomerId(String qry_CustomerId) {
        this.qry_CustomerId = qry_CustomerId;
    }

    /**
     * @return the qry_CustomerIdList
     */
    public String getQry_CustomerIdList() {
        return qry_CustomerIdList;
    }

    /**
     * @param qry_CustomerIdList the qry_CustomerIdList to set
     */
    public void setQry_CustomerIdList(String qry_CustomerIdList) {
        this.qry_CustomerIdList = qry_CustomerIdList;
    }

    /**
     * @return the qry_BusinessId
     */
    public String getQry_BusinessId() {
        return qry_BusinessId;
    }

    /**
     * @param qryBusinessId the qry_BusinessId to set
     */
    public void setQry_BusinessId(String qryBusinessId) {
        qry_BusinessId = qryBusinessId;
    }

    /**
     * @return the qry_BusinessIdList
     */
    public String getQry_BusinessIdList() {
        return qry_BusinessIdList;
    }

    /**
     * @param qryBusinessIdList the qry_BusinessIdList to set
     */
    public void setQry_BusinessIdList(String qryBusinessIdList) {
        qry_BusinessIdList = qryBusinessIdList;
    }

}