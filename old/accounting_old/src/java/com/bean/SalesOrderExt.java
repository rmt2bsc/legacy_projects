package com.bean;

import java.util.List;

import com.util.SystemException;

/**
 * Sales order graph class.
 *
 * @author auto generated.
 */
public class SalesOrderExt extends RMT2BaseBean {
    private static final long serialVersionUID = -6716194908623051714L;

    private int salesOrderId;

    private int customerId;
    
    private int businessId;

    private int invoiced;

    private double orderTotal;
    
    private String accountNo;

    private List <SalesOrderItems> items;
    
    private String reason;

    private java.util.Date dateCreated;

    private java.util.Date dateUpdated;

    private String userId;


    /**
     * Default constructor.
     *
     * @author auto generated.
     */
    public SalesOrderExt() throws SystemException {
	super();
    }

    
    /**
     * Gets the value of member variable soId
     *
     * @author atuo generated.
     */
    public int getSalesOrderId() {
        return salesOrderId;
    }

    /**
     * Sets the value of member variable soId
     *
     * @author auto generated.
     */
    public void setSalesOrderId(int salesOrderId) {
        this.salesOrderId = salesOrderId;
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
     * Get business id
     * 
     * @return
     */
    public int getBusinessId() {
        return businessId;
    }

    /**
     * Set business id
     * 
     * @param businessId
     */
    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    /**
     * Sets the value of member variable invoiced
     *
     * @author auto generated.
     */
    public void setInvoiced(int value) {
	this.invoiced = value;
    }

    /**
     * Gets the value of member variable invoiced
     *
     * @author atuo generated.
     */
    public int getInvoiced() {
	return this.invoiced;
    }

    /**
     * Sets the value of member variable orderTotal
     *
     * @author auto generated.
     */
    public void setOrderTotal(double value) {
	this.orderTotal = value;
    }

    /**
     * Gets the value of member variable orderTotal
     *
     * @author atuo generated.
     */
    public double getOrderTotal() {
	return this.orderTotal;
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
     * @return the items
     */
    public List <SalesOrderItems> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List <SalesOrderItems> items) {
        this.items = items;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /* (non-Javadoc)
     * @see com.bean.RMT2BaseBean#initBean()
     */
    @Override
    public void initBean() throws SystemException {
	// TODO Auto-generated method stub
	
    }


    /**
     * @return the accountNo
     */
    public String getAccountNo() {
        return accountNo;
    }


    /**
     * @param accountNo the accountNo to set
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }


   
    
}