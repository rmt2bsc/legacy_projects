package com.loader.transactions;

import com.bean.RMT2BaseBean;
import com.util.SystemException;

/**
 * Type of loader transaction.
 * 
 * @author RTerrell
 *
 */
public class LoaderTransType extends RMT2BaseBean {
    private static final long serialVersionUID = 1L;

    private String name;

    private String code;

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
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @throws SystemException
     */
    public LoaderTransType() throws SystemException {
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
