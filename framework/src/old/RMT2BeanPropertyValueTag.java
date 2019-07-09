package com.taglib.bean;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.api.bean.BeanDao;
import com.api.bean.BeanDaoFactory;
import com.api.db.DatabaseException;
import com.taglib.PageVariableHelper;
import com.taglib.RMT2TagSupportBase;
import com.util.RMT2Date;
import com.util.RMT2Money;
import com.util.SystemException;
import com.util.NotFoundException;

/**
 * Creates read only HTML output using contents of a java bean.
 * 
 * @author roy.terrell
 * @deprecated Will be removed in future releases.   Depend on RMT2BeanInputControlTag.java implentation.
 *
 */
public class RMT2BeanPropertyValueTag extends RMT2TagSupportBase {
    private static final long serialVersionUID = 5213183354551268863L;

    /**  The property name of the java bean to output */
    protected String property = null;

    /** The format the value will be displayed */
    protected String format = null;

    /** The space format used for property value. */
    protected String spaceFormat = null;
    
    /** The value to display */
    protected String value = null;

    /**
     * Sets the name of the property.
     * @param value
     */
    public void setProperty(String value) {
	this.property = value;
    }

    /**
     * Set the property format.
     *  
     * @param value
     */
    public void setFormat(String value) {
	this.format = value;
    }

    /**
     * Set the spacing format of property.
     * @param value
     */
    public void setSpaceFormat(String value) {
	// value = ""
	// value = "&nbsp;"
	this.spaceFormat = value;
    }
    
    

    /**
     * Set the value to display
     * @param value the value to set
     */
    public void setValue(String value) {
	this.value = value;
    }

    /**
     * The entry point into this tag that 
     * <ul>
     * 	<li>Obtains the bean object associated with the "dataSource" property</li>
     * 	<li>Uses the property name to extract the value and data type from the data source</li>
     * 	<li>Apply any formatting to the value, if applicable</li>
     * 	<li>Outputs the value of the property to the JSP page as HTML</li>
     * </ul>
     * 
     *    @throws JspException for I/O errors, System errors, Database errors, or references made to properties that do not exist
     */
    public int doStartTag() throws JspException {
	try {
	    super.doStartTag();
	    BeanDao dao;

	    if (this.obj != null && this.property != null) {
		// Check data type of incoming data object and cast accordingly.
		if (this.obj instanceof BeanDao) {
		    dao = (BeanDao) this.obj;    
		}
		else {
		    dao = (BeanDao) BeanDaoFactory.createApi();
		    dao.retrieve(this.obj);
		    dao.nextRow();
		}
		value = dao.getColumnValue(this.property);
		int javaType = dao.getJavaType(this.property);

		if (value == null) {
		    if (this.spaceFormat == null) {
			value = "";
		    }
		    else {
			value = this.spaceFormat;
		    }
		}
		else if (value.length() <= 0) {
		    if (this.spaceFormat == null) {
			value = "";
		    }
		    else {
			value = this.spaceFormat;
		    }
		}
		if (this.format != null && value.length() > 0 && !value.equalsIgnoreCase("&nbsp;")) {
		    try {
			switch (javaType) {
			case java.sql.Types.TIMESTAMP:
			case java.sql.Types.DATE:
			case java.sql.Types.TIME:
			    java.util.Date date = RMT2Date.stringToDate(value);
			    value = RMT2Date.formatDate(date, this.format);
			    break;

			case java.sql.Types.INTEGER:
			case java.sql.Types.FLOAT:
			case java.sql.Types.DOUBLE:
			    value = RMT2Money.formatNumber(new Double(value), this.format);
			    break;
			}
		    }
		    catch (SystemException e) {
			throw new JspException(e.getMessage());
		    }
		}
	    }
	    
	    if (this.value != null || !this.value.equals("")) {
		this.outputHtml(value);
	    }

	    return SKIP_BODY;
	}
	catch (IOException e) {
	    throw new JspException("IOException: " + e.getMessage());
	}
	catch (SystemException e) {
	    throw new JspException("SystemException: " + e.getMessage());
	}
	catch (DatabaseException e) {
	    throw new JspException("DatabaseException: " + e.getMessage());
	}
	catch (NotFoundException e) {
	    throw new JspException("NotFoundException: " + e.getMessage());
	}
    }

    /**
     * No Action
     */
    protected void exportData() throws JspException {
	return;
    }

}