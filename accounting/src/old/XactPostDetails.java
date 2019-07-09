package com.xact;

import com.bean.OrmBean;
import com.util.SystemException;

/**
 * 
 * @author rterrell
 *@deprecated No Longer In use
 */
public class XactPostDetails extends OrmBean {
    private static final long serialVersionUID = 1L;

    private int xactItemPostId;

    private int glAccountId;

    private double postAmount;

    private String glAccountName;

    private int glAcctTypeId;

    private String glAcctTypeDesc;

    private int xactId;

    private int xactTypeId;

    private java.util.Date xactDate;

    private String xactTypeDesc;

    private String acctPeriod;

    private int acctPeriodTypeId;

    private String acctPeriodDesc;

    // Getter/Setter Methods

    public XactPostDetails() throws SystemException {
    }

    public void setXactItemPostId(int value) {
	this.xactItemPostId = value;
    }

    public int getXactItemPostId() {
	return this.xactItemPostId;
    }

    public void setGlAccountId(int value) {
	this.glAccountId = value;
    }

    public int getGlAccountId() {
	return this.glAccountId;
    }

    public void setPostAmount(double value) {
	this.postAmount = value;
    }

    public double getPostAmount() {
	return this.postAmount;
    }

    public void setGlAccountName(String value) {
	this.glAccountName = value;
    }

    public String getGlAccountName() {
	return this.glAccountName;
    }

    public void setGlAcctTypeId(int value) {
	this.glAcctTypeId = value;
    }

    public int getGlAcctTypeId() {
	return this.glAcctTypeId;
    }

    public void setGlAcctTypeDesc(String value) {
	this.glAcctTypeDesc = value;
    }

    public String getGlAcctTypeDesc() {
	return this.glAcctTypeDesc;
    }

    public void setXactId(int value) {
	this.xactId = value;
    }

    public int getXactId() {
	return this.xactId;
    }

    public void setXactTypeId(int value) {
	this.xactTypeId = value;
    }

    public int getXactTypeId() {
	return this.xactTypeId;
    }

    public void setXactDate(java.util.Date value) {
	this.xactDate = value;
    }

    public java.util.Date getXactDate() {
	return this.xactDate;
    }

    public void setXactTypeDesc(String value) {
	this.xactTypeDesc = value;
    }

    public String getXactTypeDesc() {
	return this.xactTypeDesc;
    }

    public void setAcctPeriod(String value) {
	this.acctPeriod = value;
    }

    public String getPeriod() {
	return this.acctPeriod;
    }

    public void setAcctPeriodTypeId(int value) {
	this.acctPeriodTypeId = value;
    }

    public int getAcctPeriodTypeId() {
	return this.acctPeriodTypeId;
    }

    public void setAcctPeriodDesc(String value) {
	this.acctPeriodDesc = value;
    }

    public String getAcctPeriodDesc() {
	return this.acctPeriodDesc;
    }

    public void initBean() throws SystemException {
    }
}