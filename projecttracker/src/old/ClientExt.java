package com.project.setup;

import com.bean.RMT2BaseBean;
import com.util.SystemException;

/**
 * Extension bean for client.
 * 
 * @author Roy Terrell.
 */
public class ClientExt extends RMT2BaseBean {
    private static final long serialVersionUID = -3806888445248998275L;
    private int clientId;
    private int businessId;
    private String name;
    private String accountNo;
    private double creditLimit;
    private int active;
    private double balance;
    private double billRate;
    private double otBillRate;
    private String customerType;
    private java.util.Date dateCreated;

    /**
     * Default constructor.
     * 
     * @author Roy Terrell.
     */
    public ClientExt() throws SystemException {
        return;
    }

    /**
     * Sets the value of member variable clientId
     * 
     * @author Roy Terrell.
     */
    public void setClientId(int value) {
        this.clientId = value;
    }

    /**
     * Gets the value of member variable clientId
     * 
     * @author Roy Terrell.
     */
    public int getClientId() {
        return this.clientId;
    }

    /**
     * Sets the value of member variable accountNo
     * 
     * @author Roy Terrell.
     */
    public void setAccountNo(String value) {
        this.accountNo = value;
    }

    /**
     * Gets the value of member variable accountNo
     * 
     * @author Roy Terrell.
     */
    public String getAccountNo() {
        return this.accountNo;
    }

    /**
     * Sets the value of member variable creditLimit
     * 
     * @author Roy Terrell.
     */
    public void setCreditLimit(double value) {
        this.creditLimit = value;
    }

    /**
     * Gets the value of member variable creditLimit
     * 
     * @author Roy Terrell.
     */
    public double getCreditLimit() {
        return this.creditLimit;
    }

    /**
     * Sets the value of member variable active
     * 
     * @author Roy Terrell.
     */
    public void setActive(int value) {
        this.active = value;
    }

    /**
     * Gets the value of member variable active
     * 
     * @author Roy Terrell.
     */
    public int getActive() {
        return this.active;
    }

    /**
     * Sets the value of member variable dateCreated
     * 
     * @author Roy Terrell.
     */
    public void setDateCreated(java.util.Date value) {
        this.dateCreated = value;
    }

    /**
     * Gets the value of member variable dateCreated
     * 
     * @author Roy Terrell.
     */
    public java.util.Date getDateCreated() {
        return this.dateCreated;
    }

   /**
    * Gets the value of business id
    * @return
    */
    public int getBusinessId() {
        return businessId;
    }

    /**
     * Sets the value of business id
     * @param businessId
     */
    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    /**
     * Sets the value of business name
     * @param the name of the business.
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of business name
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the value of member variable balance
     * 
     * @author Roy Terrell.
     */
    public void setBalance(double value) {
        this.balance = value;
    }

    /**
     * Gets the value of member variable balance
     * 
     * @author Roy Terrell.
     */
    public double getBalance() {
        return this.balance;
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
     * Sets the value of member variable customerType
     * 
     * @author Roy Terrell.
     */
    public void setCustomerType(String value) {
        this.customerType = value;
    }

    /**
     * Gets the value of member variable customerType
     * 
     * @author Roy Terrell.
     */
    public String getCustomerType() {
        return this.customerType;
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