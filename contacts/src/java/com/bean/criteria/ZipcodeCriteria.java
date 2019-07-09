package com.bean.criteria;

import com.bean.criteria.AncestorQueryCriteria;

import com.util.SystemException;

/**
 * Criteria class used for tracking UI selection criteria for the Postal zip code
 * Search page.
 * 
 * @author RTerrell
 *
 */
public class ZipcodeCriteria extends AncestorQueryCriteria {
    private static final long serialVersionUID = -9194934472559574966L;

    private String qry_Zip;
    private String qry_City;
    private String qry_State;
    private String qry_AreaCode;
    private String qry_CountyName;
    private String qry_CityAliasName;
    private String qry_TimeZone;


    /**
     * Default constructor
     * 
     * @throws SystemException
     */
    private ZipcodeCriteria() throws SystemException {
    	super();
        return;
    }
    
    /**
     * Creates an instance of ContactCriteria class.
     * 
     * @return ContactCriteria.
     */
    public static ZipcodeCriteria getInstance() {
        try {
            ZipcodeCriteria criteria = new ZipcodeCriteria();
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
        this.qry_Zip = "";
        this.qry_City = "";
        this.qry_State = "";
        this.qry_AreaCode = "";
        this.qry_CountyName = "";
        this.qry_TimeZone = "";
        this.qry_CityAliasName = "";
        return;
   }

    /**
     * @return the qry_AreaCode
     */
    public String getQry_AreaCode() {
        return qry_AreaCode;
    }

    /**
     * @param qry_AreaCode the qry_AreaCode to set
     */
    public void setQry_AreaCode(String qry_AreaCode) {
        this.qry_AreaCode = qry_AreaCode;
    }

    /**
     * @return the qry_City
     */
    public String getQry_City() {
        return qry_City;
    }

    /**
     * @param qry_City the qry_City to set
     */
    public void setQry_City(String qry_City) {
        this.qry_City = qry_City;
    }

    /**
     * @return the qry_CountyName
     */
    public String getQry_CountyName() {
        return qry_CountyName;
    }

    /**
     * @param qry_CountyName the qry_CountyName to set
     */
    public void setQry_CountyName(String qry_CountyName) {
        this.qry_CountyName = qry_CountyName;
    }

    /**
     * @return the qry_State
     */
    public String getQry_State() {
        return qry_State;
    }

    /**
     * @param qry_State the qry_State to set
     */
    public void setQry_State(String qry_State) {
        this.qry_State = qry_State;
    }

    /**
     * @return the qry_TimeZone
     */
    public String getQry_TimeZone() {
        return qry_TimeZone;
    }

    /**
     * @param qry_TimeZone the qry_TimeZone to set
     */
    public void setQry_TimeZone(String qry_TimeZone) {
        this.qry_TimeZone = qry_TimeZone;
    }

    /**
     * @return the qry_Zip
     */
    public String getQry_Zip() {
        return qry_Zip;
    }

    /**
     * @param qry_Zip the qry_Zip to set
     */
    public void setQry_Zip(String qry_Zip) {
        this.qry_Zip = qry_Zip;
    }

    /**
     * @return the qry_CityAliasName
     */
    public String getQry_CityAliasName() {
        return qry_CityAliasName;
    }

    /**
     * @param qry_CityAliasName the qry_CityAliasName to set
     */
    public void setQry_CityAliasName(String qry_CityAliasName) {
        this.qry_CityAliasName = qry_CityAliasName;
    }
}