package com.xact;

import com.bean.OrmBean;

import com.util.SystemException;

/**
 * 
 * @author rterrell
 * @deprecated No longer In use
 */
public class XactXlatBean extends OrmBean {
    private static final long serialVersionUID = 3403521741118123051L;

    private int glAcctId;

    private double amount;

    public XactXlatBean() throws SystemException {

    }

    public void XactEntryBean() {
	return;
    }

    public int getGlAcctId() {
	return this.glAcctId;
    }

    public void setGlAcctId(int value) {
	this.glAcctId = value;
    }

    public double getAmount() {
	return this.amount;
    }

    public void setAmount(double value) {
	this.amount = value;
    }

    public void initBean() throws SystemException {
	return;
    }
}