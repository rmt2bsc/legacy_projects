/**
 * 
 */
package com.bean.criteria;

import java.util.List;

import com.util.SystemException;

/**
 * @author appdev
 *
 */
public class EmployeeCriteria extends AncestorQueryCriteria {

    private String qry_EmployeeId;

    private String qry_LoginId;

    private String qry_LoginName;

    private String qry_StartDate;

    private String qry_TerminationDate;

    private String qry_TitleId;

    private String qry_TypeId;

    private String qry_ManagerId;

    private String qry_ManagerName;

    private String qry_Firstname;

    private String qry_Lastname;

    private String qry_Ssn;

    private String qry_Email;

    private String qry_EmployeeTitle;

    private String qry_EmployeeType;
    
    private String qry_Company;
    
    private String qry_Project;
    
    private String qry_ProjectId;
    

    /**
     * @throws SystemException
     */
    protected EmployeeCriteria() throws SystemException {
	return;
    }

    public static EmployeeCriteria getInstance() {
	return new EmployeeCriteria();
    }

    /**
     * @return the qry_EmployeeId
     */
    public String getQry_EmployeeId() {
        return qry_EmployeeId;
    }

    /**
     * @param qry_EmployeeId the qry_EmployeeId to set
     */
    public void setQry_EmployeeId(String qry_EmployeeId) {
        this.qry_EmployeeId = qry_EmployeeId;
    }

    /**
     * @return the qry_LoginId
     */
    public String getQry_LoginId() {
        return qry_LoginId;
    }

    /**
     * @param qry_LoginId the qry_LoginId to set
     */
    public void setQry_LoginId(String qry_LoginId) {
        this.qry_LoginId = qry_LoginId;
    }

    /**
     * @return the qry_LoginName
     */
    public String getQry_LoginName() {
        return qry_LoginName;
    }

    /**
     * @param qry_LoginName the qry_LoginName to set
     */
    public void setQry_LoginName(String qry_LoginName) {
        this.qry_LoginName = qry_LoginName;
    }

    /**
     * @return the qry_StartDate
     */
    public String getQry_StartDate() {
        return qry_StartDate;
    }

    /**
     * @param qry_StartDate the qry_StartDate to set
     */
    public void setQry_StartDate(String qry_StartDate) {
        this.qry_StartDate = qry_StartDate;
    }

    /**
     * @return the qry_TerminationDate
     */
    public String getQry_TerminationDate() {
        return qry_TerminationDate;
    }

    /**
     * @param qry_TerminationDate the qry_TerminationDate to set
     */
    public void setQry_TerminationDate(String qry_TerminationDate) {
        this.qry_TerminationDate = qry_TerminationDate;
    }

    /**
     * @return the qry_EmpTitleId
     */
    public String getQry_TitleId() {
        return qry_TitleId;
    }

    /**
     * @param qry_EmpTitleId the qry_EmpTitleId to set
     */
    public void setQry_TitleId(String qry_EmpTitleId) {
        this.qry_TitleId = qry_EmpTitleId;
    }

    /**
     * @return the qry_EmpTypeId
     */
    public String getQry_TypeId() {
        return qry_TypeId;
    }

    /**
     * @param qry_EmpTypeId the qry_EmpTypeId to set
     */
    public void setQry_TypeId(String qry_EmpTypeId) {
        this.qry_TypeId = qry_EmpTypeId;
    }

    /**
     * @return the qry_ManagerId
     */
    public String getQry_ManagerId() {
        return qry_ManagerId;
    }

    /**
     * @param qry_ManagerId the qry_ManagerId to set
     */
    public void setQry_ManagerId(String qry_ManagerId) {
        this.qry_ManagerId = qry_ManagerId;
    }

    /**
     * @return the qry_ManagerName
     */
    public String getQry_ManagerName() {
        return qry_ManagerName;
    }

    /**
     * @param qry_ManagerName the qry_ManagerName to set
     */
    public void setQry_ManagerName(String qry_ManagerName) {
        this.qry_ManagerName = qry_ManagerName;
    }

    /**
     * @return the qry_Firstname
     */
    public String getQry_Firstname() {
        return qry_Firstname;
    }

    /**
     * @param qry_Firstname the qry_Firstname to set
     */
    public void setQry_Firstname(String qry_Firstname) {
        this.qry_Firstname = qry_Firstname;
    }

    /**
     * @return the qry_Lastname
     */
    public String getQry_Lastname() {
        return qry_Lastname;
    }

    /**
     * @param qry_Lastname the qry_Lastname to set
     */
    public void setQry_Lastname(String qry_Lastname) {
        this.qry_Lastname = qry_Lastname;
    }

    /**
     * @return the qry_Ssn
     */
    public String getQry_Ssn() {
        return qry_Ssn;
    }

    /**
     * @param qry_Ssn the qry_Ssn to set
     */
    public void setQry_Ssn(String qry_Ssn) {
        this.qry_Ssn = qry_Ssn;
    }

    /**
     * @return the qry_Email
     */
    public String getQry_Email() {
        return qry_Email;
    }

    /**
     * @param qry_Email the qry_Email to set
     */
    public void setQry_Email(String qry_Email) {
        this.qry_Email = qry_Email;
    }

    /**
     * @return the qry_EmployeeTitle
     */
    public String getQry_EmployeeTitle() {
        return qry_EmployeeTitle;
    }

    /**
     * @param qry_EmployeeTitle the qry_EmployeeTitle to set
     */
    public void setQry_EmployeeTitle(String qry_EmployeeTitle) {
        this.qry_EmployeeTitle = qry_EmployeeTitle;
    }

    /**
     * @return the qry_EmployeeType
     */
    public String getQry_EmployeeType() {
        return qry_EmployeeType;
    }

    /**
     * @param qry_EmployeeType the qry_EmployeeType to set
     */
    public void setQry_EmployeeType(String qry_EmployeeType) {
        this.qry_EmployeeType = qry_EmployeeType;
    }

    /**
     * @return the qry_Company
     */
    public String getQry_Company() {
        return qry_Company;
    }

    /**
     * @param qry_Company the qry_Company to set
     */
    public void setQry_Company(String qry_Company) {
        this.qry_Company = qry_Company;
    }

    /**
     * @return the qry_Project
     */
    public String getQry_Project() {
        return qry_Project;
    }

    /**
     * @param qry_Project the qry_Project to set
     */
    public void setQry_Project(String qry_Project) {
        this.qry_Project = qry_Project;
    }

    /**
     * @return the qry_ProjectId
     */
    public String getQry_ProjectId() {
        return qry_ProjectId;
    }

    /**
     * @param qry_ProjectId the qry_ProjectId to set
     */
    public void setQry_ProjectId(String qry_ProjectId) {
        this.qry_ProjectId = qry_ProjectId;
    }
}
