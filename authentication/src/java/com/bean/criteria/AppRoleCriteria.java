package com.bean.criteria;

import com.util.SystemException;

/**
 * Search criteria object for serving application-role search maintenance.
 *
 * @author Roy Terrell.
 */
public class AppRoleCriteria extends AncestorQueryCriteria {
    private static final long serialVersionUID = 4964192335033365535L;

    private String qry_Id;

    private String qry_ApplicationId;

    private String qry_RoleId;

    private String qry_AppRoleCode;

    private String qry_AppRoleName;

    private String qry_Description;

    /**
     * Default constructor.
     *
     * @author Roy Terrell.
     */
    private AppRoleCriteria() throws SystemException {
	super();
    }

    /**
     * Creates an instance of AppRoleCriteria.
     * @return AppRoleCriteria
     */
    public static AppRoleCriteria getInstance() {
	try {
	    return new AppRoleCriteria();
	}
	catch (Exception e) {
	    return null;
	}
    }
    /**
     * Stubbed initialization method designed to implemented by developer.
     *
     * @author Roy Terrell.
     */
    public void initBean() throws SystemException {
    }

    /**
     * @return the qry_ApplicationId
     */
    public String getQry_ApplicationId() {
	return qry_ApplicationId;
    }

    /**
     * @param qry_ApplicationId the qry_ApplicationId to set
     */
    public void setQry_ApplicationId(String qry_ApplicationId) {
	this.qry_ApplicationId = qry_ApplicationId;
    }

    /**
     * @return the qry_Code
     */
    public String getQry_AppRoleCode() {
	return qry_AppRoleCode;
    }

    /**
     * @param qry_Code the qry_Code to set
     */
    public void setQry_AppRoleCode(String qry_Code) {
	this.qry_AppRoleCode = qry_Code;
    }

    /**
     * @return the qry_Description
     */
    public String getQry_Description() {
	return qry_Description;
    }

    /**
     * @param qry_Description the qry_Description to set
     */
    public void setQry_Description(String qry_Description) {
	this.qry_Description = qry_Description;
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
     * @return the qry_Name
     */
    public String getQry_AppRoleName() {
	return qry_AppRoleName;
    }

    /**
     * @param qry_Name the qry_Name to set
     */
    public void setQry_AppRoleName(String qry_Name) {
	this.qry_AppRoleName = qry_Name;
    }

    /**
     * @return the qry_RoleId
     */
    public String getQry_RoleId() {
	return qry_RoleId;
    }

    /**
     * @param qry_RoleId the qry_RoleId to set
     */
    public void setQry_RoleId(String qry_RoleId) {
	this.qry_RoleId = qry_RoleId;
    }

}