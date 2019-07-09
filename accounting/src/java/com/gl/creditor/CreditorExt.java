package com.gl.creditor;


import com.gl.GlCommonContact;

import com.util.SystemException;

/**
 * Creditor contact class for the accounting GL system.
 *
 * @author auto generated.
 */
public class CreditorExt extends GlCommonContact {
    
    private static final long serialVersionUID = 7432672611416508801L;

    private int creditorId;

    private int creditorTypeId;
    
    private String accountNo;

    private int glAccountId;

    private double creditLimit;

    private int active;

    private double apr;

    private java.util.Date dateCreated;

    private java.util.Date dateUpdated;

    private String userId;

    private String creditorTypeDescription;
    
 

    /**
     * Default constructor.
     *
     * @author auto generated.
     */
    public CreditorExt() throws SystemException {
	super();
    }

    /**
     * Sets the value of member variable creditorId
     *
     * @author auto generated.
     */
    public void setCreditorId(int value) {
	this.creditorId = value;
    }

    /**
     * Gets the value of member variable creditorId
     *
     * @author atuo generated.
     */
    public int getCreditorId() {
	return this.creditorId;
    }

    /**
     * Sets the value of member variable creditorTypeId
     *
     * @author auto generated.
     */
    public void setCreditorTypeId(int value) {
	this.creditorTypeId = value;
    }

    /**
     * Gets the value of member variable creditorTypeId
     *
     * @author atuo generated.
     */
    public int getCreditorTypeId() {
	return this.creditorTypeId;
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
     * Sets the value of member variable apr
     *
     * @author auto generated.
     */
    public void setApr(double value) {
	this.apr = value;
    }

    /**
     * Gets the value of member variable apr
     *
     * @author atuo generated.
     */
    public double getApr() {
	return this.apr;
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
     * Sets the value of member variable creditorTypeDescription
     *
     * @author auto generated.
     */
    public void setCreditorTypeDescription(String value) {
	this.creditorTypeDescription = value;
    }

    /**
     * Gets the value of member variable creditorTypeDescription
     *
     * @author atuo generated.
     */
    public String getCreditorTypeDescription() {
	return this.creditorTypeDescription;
    }


    /**
     * Stubbed initialization method designed to implemented by developer.
     *
     * @author auto generated.
     */
    public void initBean() throws SystemException {
    }
}