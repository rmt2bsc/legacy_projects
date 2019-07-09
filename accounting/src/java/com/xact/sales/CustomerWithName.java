package com.xact.sales;

import com.bean.OrmBean;
import com.util.SystemException;

/**
 * 
 * @author appdev
 */
public class CustomerWithName extends OrmBean {
    private static final long serialVersionUID = -2172491780272060792L;

    private int id;

    private int glAccountId;

    private String name;

    private String accountNo;

    private int personId;

    private int businessId;

    private double creditLimit;

    private int active;

    private java.util.Date dateCreated;

    private java.util.Date dateUpdated;

    private String userId;

    // Getter/Setter Methods
    public CustomerWithName() throws SystemException {
    }

    public void setId(int value) {
	this.id = value;
    }

    public int getId() {
	return this.id;
    }

    public void setGlAccountId(int value) {
	this.glAccountId = value;
    }

    public int getGlAccountId() {
	return this.glAccountId;
    }

    public void setName(String value) {
	this.name = value;
    }

    public String getName() {
	return this.name;
    }

    public void setAccountNo(String value) {
	this.accountNo = value;
    }

    public String getAccountNo() {
	return this.accountNo;
    }

    public void setPersonId(int value) {
	this.personId = value;
    }

    public int getPersonId() {
	return this.personId;
    }

    public void setBusinessId(int value) {
	this.businessId = value;
    }

    public int getBusinessId() {
	return this.businessId;
    }

    public void setCreditLimit(double value) {
	this.creditLimit = value;
    }

    public double getCreditLimit() {
	return this.creditLimit;
    }

    public void setActive(int value) {
	this.active = value;
    }

    public int getActive() {
	return this.active;
    }

    public void setDateCreated(java.util.Date value) {
	this.dateCreated = value;
    }

    public java.util.Date getDateCreated() {
	return this.dateCreated;
    }

    public void setDateUpdated(java.util.Date value) {
	this.dateUpdated = value;
    }

    public java.util.Date getDateUpdated() {
	return this.dateUpdated;
    }

    public void setUserId(String value) {
	this.userId = value;
    }

    public String getUserId() {
	return this.userId;
    }

    public void initBean() throws SystemException {
    }
}