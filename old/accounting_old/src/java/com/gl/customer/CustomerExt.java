package com.gl.customer;

import com.bean.RMT2BaseBean;
import com.gl.GlCommonContact;
import com.util.SystemException;

/**
 * Peer object that maps to the vw_customer_business database table/view.
 *
 * @author auto generated.
 */
public class CustomerExt extends GlCommonContact {
    private static final long serialVersionUID = 1L;

    
    private int customerId;

    private String accountNo;

    private int glAccountId;

    private double creditLimit;

    private int active;

    private java.util.Date dateCreated;

    private java.util.Date dateUpdated;

    private String userId;


    // Getter/Setter Methods

    /**
     * Default constructor.
     *
     * @author auto generated.
     */
    public CustomerExt() throws SystemException {
	super();
    }

    /**
     * Sets the value of member variable customerId
     *
     * @author auto generated.
     */
    public void setCustomerId(int value) {
	this.customerId = value;
    }

    /**
     * Gets the value of member variable customerId
     *
     * @author atuo generated.
     */
    public int getCustomerId() {
	return this.customerId;
    }

    /**
     * Sets the value of member variable accountNo
     *
     * @author auto generated.
     */
    public void setAccountNo(String value) {
	this.accountNo = value;
    }

    /**
     * Gets the value of member variable accountNo
     *
     * @author atuo generated.
     */
    public String getAccountNo() {
	return this.accountNo;
    }

    /**
     * Sets the value of member variable glAccountId
     *
     * @author auto generated.
     */
    public void setGlAccountId(int value) {
	this.glAccountId = value;
    }

    /**
     * Gets the value of member variable glAccountId
     *
     * @author atuo generated.
     */
    public int getGlAccountId() {
	return this.glAccountId;
    }

    /**
     * Sets the value of member variable creditLimit
     *
     * @author auto generated.
     */
    public void setCreditLimit(double value) {
	this.creditLimit = value;
    }

    /**
     * Gets the value of member variable creditLimit
     *
     * @author atuo generated.
     */
    public double getCreditLimit() {
	return this.creditLimit;
    }

    /**
     * Sets the value of member variable active
     *
     * @author auto generated.
     */
    public void setActive(int value) {
	this.active = value;
    }

    /**
     * Gets the value of member variable active
     *
     * @author atuo generated.
     */
    public int getActive() {
	return this.active;
    }

    /**
     * Sets the value of member variable dateCreated
     *
     * @author auto generated.
     */
    public void setDateCreated(java.util.Date value) {
	this.dateCreated = value;
    }

    /**
     * Gets the value of member variable dateCreated
     *
     * @author atuo generated.
     */
    public java.util.Date getDateCreated() {
	return this.dateCreated;
    }

    /**
     * Sets the value of member variable dateUpdated
     *
     * @author auto generated.
     */
    public void setDateUpdated(java.util.Date value) {
	this.dateUpdated = value;
    }

    /**
     * Gets the value of member variable dateUpdated
     *
     * @author atuo generated.
     */
    public java.util.Date getDateUpdated() {
	return this.dateUpdated;
    }

    /**
     * Sets the value of member variable userId
     *
     * @author auto generated.
     */
    public void setUserId(String value) {
	this.userId = value;
    }

    /**
     * Gets the value of member variable userId
     *
     * @author atuo generated.
     */
    public String getUserId() {
	return this.userId;
    }

  

    /**
     * Stubbed initialization method designed to implemented by developer.

     *
     * @author auto generated.
     */
    public void initBean() throws SystemException {
    }

}