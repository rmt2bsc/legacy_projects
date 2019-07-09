package com.employee;

import com.bean.RMT2BaseBean;
import com.util.SystemException;

/**
 * Extension bean for employee.
 * 
 * @author Roy Terrell.
 */
public class EmployeeExt extends RMT2BaseBean {
    private static final long serialVersionUID = 2853318800160305792L;

    private int employeeId;
    private String loginId;
    private int personId;
    private java.util.Date startDate;
    private java.util.Date terminationDate;
    private int titleId;
    private int typeId;
    private int managerId;
    private double billRate;
    private double otBillRate;
    private String firstname;
    private String midname;
    private String lastname;
    private String shortname;
    private String generation;
    private String maidenname;
    private int title;
    private String perTitleName;
    private String email;
    private int genderId;
    private String genderName;
    private int maritalStatus;
    private String maritalStatusName;
    private int raceId;
    private String raceName;
    private String ssn;
    private java.util.Date birthDate;
    private String employeeTitle;
    private String employeeType;

    /**
     * Default constructor.
     * 
     * @author Roy Terrell.
     */
    public EmployeeExt() throws SystemException {
        super();
    }

    /**
     * Sets the value of member variable employeeId
     * 
     * @author Roy Terrell.
     */
    public void setEmployeeId(int value) {
        this.employeeId = value;
    }

    /**
     * Gets the value of member variable employeeId
     * 
     * @author Roy Terrell.
     */
    public int getEmployeeId() {
        return this.employeeId;
    }

    /**
     * Sets the value of member variable loginId
     * 
     * @author Roy Terrell.
     */
    public void setLoginId(String value) {
        this.loginId = value;
    }

    /**
     * Gets the value of member variable loginId
     * 
     * @author Roy Terrell.
     */
    public String getLoginId() {
        return this.loginId;
    }

    /**
     * Sets the value of member variable personId
     * 
     * @author Roy Terrell.
     */
    public void setPersonId(int value) {
        this.personId = value;
    }

    /**
     * Gets the value of member variable personId
     * 
     * @author Roy Terrell.
     */
    public int getPersonId() {
        return this.personId;
    }

    /**
     * Sets the value of member variable startDate
     * 
     * @author Roy Terrell.
     */
    public void setStartDate(java.util.Date value) {
        this.startDate = value;
    }

    /**
     * Gets the value of member variable startDate
     * 
     * @author Roy Terrell.
     */
    public java.util.Date getStartDate() {
        return this.startDate;
    }

    /**
     * Sets the value of member variable terminationDate
     * 
     * @author Roy Terrell.
     */
    public void setTerminationDate(java.util.Date value) {
        this.terminationDate = value;
    }

    /**
     * Gets the value of member variable terminationDate
     * 
     * @author Roy Terrell.
     */
    public java.util.Date getTerminationDate() {
        return this.terminationDate;
    }

    /**
     * Sets the value of member variable titleId
     * 
     * @author Roy Terrell.
     */
    public void setTitleId(int value) {
        this.titleId = value;
    }

    /**
     * Gets the value of member variable titleId
     * 
     * @author Roy Terrell.
     */
    public int getTitleId() {
        return this.titleId;
    }

    /**
     * Sets the value of member variable typeId
     * 
     * @author Roy Terrell.
     */
    public void setTypeId(int value) {
        this.typeId = value;
    }

    /**
     * Gets the value of member variable typeId
     * 
     * @author Roy Terrell.
     */
    public int getTypeId() {
        return this.typeId;
    }

    /**
     * Sets the value of member variable managerId
     * 
     * @author Roy Terrell.
     */
    public void setManagerId(int value) {
        this.managerId = value;
    }

    /**
     * Gets the value of member variable managerId
     * 
     * @author Roy Terrell.
     */
    public int getManagerId() {
        return this.managerId;
    }

    /**
     * Sets the value of member variable billRate
     * 
     * @author Roy Terrell.
     */
    public void setBillRate(double value) {
        this.billRate = value;
    }

    /**
     * Gets the value of member variable billRate
     * 
     * @author Roy Terrell.
     */
    public double getBillRate() {
        return this.billRate;
    }

    /**
     * Sets the value of member variable otBillRate
     * 
     * @author Roy Terrell.
     */
    public void setOtBillRate(double value) {
        this.otBillRate = value;
    }

    /**
     * Gets the value of member variable otBillRate
     * 
     * @author Roy Terrell.
     */
    public double getOtBillRate() {
        return this.otBillRate;
    }

    /**
     * Sets the value of member variable firstname
     * 
     * @author Roy Terrell.
     */
    public void setFirstname(String value) {
        this.firstname = value;
    }

    /**
     * Gets the value of member variable firstname
     * 
     * @author Roy Terrell.
     */
    public String getFirstname() {
        return this.firstname;
    }

    /**
     * Sets the value of member variable midname
     * 
     * @author Roy Terrell.
     */
    public void setMidname(String value) {
        this.midname = value;
    }

    /**
     * Gets the value of member variable midname
     * 
     * @author Roy Terrell.
     */
    public String getMidname() {
        return this.midname;
    }

    /**
     * Sets the value of member variable lastname
     * 
     * @author Roy Terrell.
     */
    public void setLastname(String value) {
        this.lastname = value;
    }

    /**
     * Gets the value of member variable lastname
     * 
     * @author Roy Terrell.
     */
    public String getLastname() {
        return this.lastname;
    }

    /**
     * Sets the value of member variable shortname
     * 
     * @author Roy Terrell.
     */
    public void setShortname(String value) {
        this.shortname = value;
    }

    /**
     * Gets the value of member variable shortname
     * 
     * @author Roy Terrell.
     */
    public String getShortname() {
        return this.shortname;
    }

    /**
     * Sets the value of member variable generation
     * 
     * @author Roy Terrell.
     */
    public void setGeneration(String value) {
        this.generation = value;
    }

    /**
     * Gets the value of member variable generation
     * 
     * @author Roy Terrell.
     */
    public String getGeneration() {
        return this.generation;
    }

    /**
     * Sets the value of member variable maidenname
     * 
     * @author Roy Terrell.
     */
    public void setMaidenname(String value) {
        this.maidenname = value;
    }

    /**
     * Gets the value of member variable maidenname
     * 
     * @author Roy Terrell.
     */
    public String getMaidenname() {
        return this.maidenname;
    }

    /**
     * Sets the value of member variable title
     * 
     * @author Roy Terrell.
     */
    public void setTitle(int value) {
        this.title = value;
    }

    /**
     * Gets the value of member variable title
     * 
     * @author Roy Terrell.
     */
    public int getTitle() {
        return this.title;
    }

    /**
     * Sets the value of member variable perTitleName
     * 
     * @author Roy Terrell.
     */
    public void setPerTitleName(String value) {
        this.perTitleName = value;
    }

    /**
     * Gets the value of member variable perTitleName
     * 
     * @author Roy Terrell.
     */
    public String getPerTitleName() {
        return this.perTitleName;
    }

    /**
     * Sets the value of member variable email
     * 
     * @author Roy Terrell.
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of member variable email
     * 
     * @author Roy Terrell.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the value of member variable genderId
     * 
     * @author Roy Terrell.
     */
    public void setGenderId(int value) {
        this.genderId = value;
    }

    /**
     * Gets the value of member variable genderId
     * 
     * @author Roy Terrell.
     */
    public int getGenderId() {
        return this.genderId;
    }

    /**
     * Sets the value of member variable genderName
     * 
     * @author Roy Terrell.
     */
    public void setGenderName(String value) {
        this.genderName = value;
    }

    /**
     * Gets the value of member variable genderName
     * 
     * @author Roy Terrell.
     */
    public String getGenderName() {
        return this.genderName;
    }

    /**
     * Sets the value of member variable maritalStatus
     * 
     * @author Roy Terrell.
     */
    public void setMaritalStatus(int value) {
        this.maritalStatus = value;
    }

    /**
     * Gets the value of member variable maritalStatus
     * 
     * @author Roy Terrell.
     */
    public int getMaritalStatus() {
        return this.maritalStatus;
    }

    /**
     * Sets the value of member variable maritalStatusName
     * 
     * @author Roy Terrell.
     */
    public void setMaritalStatusName(String value) {
        this.maritalStatusName = value;
    }

    /**
     * Gets the value of member variable maritalStatusName
     * 
     * @author Roy Terrell.
     */
    public String getMaritalStatusName() {
        return this.maritalStatusName;
    }

    /**
     * Sets the value of member variable raceId
     * 
     * @author Roy Terrell.
     */
    public void setRaceId(int value) {
        this.raceId = value;
    }

    /**
     * Gets the value of member variable raceId
     * 
     * @author Roy Terrell.
     */
    public int getRaceId() {
        return this.raceId;
    }

    /**
     * Sets the value of member variable raceName
     * 
     * @author Roy Terrell.
     */
    public void setRaceName(String value) {
        this.raceName = value;
    }

    /**
     * Gets the value of member variable raceName
     * 
     * @author Roy Terrell.
     */
    public String getRaceName() {
        return this.raceName;
    }

    /**
     * Sets the value of member variable ssn
     * 
     * @author Roy Terrell.
     */
    public void setSsn(String value) {
        this.ssn = value;
    }

    /**
     * Gets the value of member variable ssn
     * 
     * @author Roy Terrell.
     */
    public String getSsn() {
        return this.ssn;
    }

    /**
     * Sets the value of member variable birthDate
     * 
     * @author Roy Terrell.
     */
    public void setBirthDate(java.util.Date value) {
        this.birthDate = value;
    }

    /**
     * Gets the value of member variable birthDate
     * 
     * @author Roy Terrell.
     */
    public java.util.Date getBirthDate() {
        return this.birthDate;
    }

    /**
     * Sets the value of member variable employeeTitle
     * 
     * @author Roy Terrell.
     */
    public void setEmployeeTitle(String value) {
        this.employeeTitle = value;
    }

    /**
     * Gets the value of member variable employeeTitle
     * 
     * @author Roy Terrell.
     */
    public String getEmployeeTitle() {
        return this.employeeTitle;
    }

    /**
     * Sets the value of member variable employeeType
     * 
     * @author Roy Terrell.
     */
    public void setEmployeeType(String value) {
        this.employeeType = value;
    }

    /**
     * Gets the value of member variable employeeType
     * 
     * @author Roy Terrell.
     */
    public String getEmployeeType() {
        return this.employeeType;
    }

    /**
     * Stubbed initialization method designed to implemented by developer.
     * 
     * 
     * @author Roy Terrell.
     */
    public void initBean() throws SystemException {
    }
}