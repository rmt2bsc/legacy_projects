package com.bean.criteria;

import com.bean.criteria.AncestorQueryCriteria;

import com.util.SystemException;

/**
 * Criteria class used for tracking UI selection criteria for the U.S. States/Province
 * Search page.
 * 
 * @author RTerrell
 *
 */
public class StateCriteria extends AncestorQueryCriteria {
    private static final long serialVersionUID = -9194934472559574966L;

    private String qry_Id;
    private String qry_StateId;
    private String qry_CountryId;
    private String qry_StateName;
    private String qry_SttVoidInd;
    private String qry_StateCode;

    /**
     * @return the qry_StateCode
     */
    public String getQry_StateCode() {
        return qry_StateCode;
    }

    /**
     * @param qry_StateCode the qry_StateCode to set
     */
    public void setQry_StateCode(String qry_StateCode) {
        this.qry_StateCode = qry_StateCode;
    }

    /**
     * Default constructor
     * 
     * @throws SystemException
     */
    private StateCriteria() throws SystemException {
    	super();
        return;
    }
    
    /**
     * Creates an instance of ContactCriteria class.
     * 
     * @return ContactCriteria.
     */
    public static StateCriteria getInstance() {
        try {
            StateCriteria criteria = new StateCriteria();
            return criteria;
        }
        catch (Exception e) {
            return null;
        }
    }


    /**
     * Initialize all member varialbes Zip, City, State, AreaCode, CountryName, and TimeZone.
     */
    public void initBean() throws SystemException {
        this.qry_Id = "";
        this.qry_StateId = "";
        this.qry_CountryId = "";
        this.qry_StateName = "";
        this.qry_StateCode = "";
        this.qry_SttVoidInd = "";
        return;
   }

    /**
     * @return the qry_CountryId
     */
    public String getQry_CountryId() {
        return qry_CountryId;
    }

    /**
     * @param qry_CountryId the qry_CountryId to set
     */
    public void setQry_CountryId(String qry_CountryId) {
        this.qry_CountryId = qry_CountryId;
    }

    /**
     * @return the qry_Id
     */
    public String getQry_Id() {
        return qry_Id;
    }

    /**
     * @param qry_Id the qry_Id to set
     */
    public void setQry_Id(String qry_Id) {
        this.qry_Id = qry_Id;
    }

    /**
     * @return the qry_StateId
     */
    public String getQry_StateId() {
        return qry_StateId;
    }

    /**
     * @param qry_StateId the qry_StateId to set
     */
    public void setQry_StateId(String qry_StateId) {
        this.qry_StateId = qry_StateId;
    }

    /**
     * @return the qry_StateName
     */
    public String getQry_StateName() {
        return qry_StateName;
    }

    /**
     * @param qry_StateName the qry_StateName to set
     */
    public void setQry_StateName(String qry_StateName) {
        this.qry_StateName = qry_StateName;
    }

    /**
     * @return the qry_SttVoidInd
     */
    public String getQry_SttVoidInd() {
        return qry_SttVoidInd;
    }

    /**
     * @param qry_SttVoidInd the qry_SttVoidInd to set
     */
    public void setQry_SttVoidInd(String qry_SttVoidInd) {
        this.qry_SttVoidInd = qry_SttVoidInd;
    }

    
 
}