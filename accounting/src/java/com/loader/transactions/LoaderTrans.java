package com.loader.transactions;

import java.util.Date;

import com.bean.RMT2BaseBean;
import com.util.SystemException;

/**
 * Transaction to be used for loading tranactions.
 * 
 * @author RTerrell
 *
 */
public class LoaderTrans extends RMT2BaseBean {
    private static final long serialVersionUID = 1L;

    private String code;

    private Date date;

    private String description;

    private Double amount;

    /**
     * @return the amount
     */
    public Double getAmount() {
	return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(Double amount) {
	this.amount = amount;
    }

    /**
     * @return the code
     */
    public String getCode() {
	return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
	this.code = code;
    }

    /**
     * @return the date
     */
    public Date getDate() {
	return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
	this.date = date;
    }

    /**
     * @return the description
     */
    public String getDescription() {
	return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * @throws SystemException
     */
    public LoaderTrans() throws SystemException {
	// TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see com.bean.RMT2BaseBean#initBean()
     */
    @Override
    public void initBean() throws SystemException {
	// TODO Auto-generated method stub

    }

}
