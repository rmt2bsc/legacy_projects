package com.bean.criteria;

import com.util.SystemException;

/**
 * Peer object that maps creditor selection criteria.
 * <p>
 * <b><u>NOTE</u></b>:&nbsp; This class did not capitalize the first
 * character following the prefix, "qry_", of its property names and still
 * functions successfully using the introspection methods of DataSourceAdapter
 * class.
 * 
 * @author Roy Terrell.
 */
public class CreditorCriteria extends AncestorQueryCriteria {
    private static final long serialVersionUID = -1660427118693360082L;

    private String qry_CreditorId;
    
    private String qry_CreditorTypeId;

    private String qry_AccountNo;

    private String qry_Name;

    private String qry_TaxId;

    private String qry_PhoneMain;

    /**
     * Default constructor.
     * 
     * @author Roy Terrell.
     */
    public CreditorCriteria() throws SystemException {
	super();
    }

    /**
     * Creates an instance of CreditorCriteria class.
     * 
     * @return {@link CreditorCriteria}
     */
    public static CreditorCriteria getInstance() {
	try {
	    return new CreditorCriteria();
	}
	catch (Exception e) {
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
	this.qry_CreditorId = "";
	this.qry_CreditorTypeId = "";
	this.qry_AccountNo = "";
	this.qry_Name = "";
	this.qry_TaxId = "";
	this.qry_PhoneMain = "";
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

    /**
     * @return the qry_CreditorId
     */
    public String getQry_CreditorId() {
        return qry_CreditorId;
    }

    /**
     * @param qry_CreditorId the qry_CreditorId to set
     */
    public void setQry_CreditorId(String qry_CreditorId) {
        this.qry_CreditorId = qry_CreditorId;
    }
}