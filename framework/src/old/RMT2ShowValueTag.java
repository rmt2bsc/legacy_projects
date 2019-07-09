package com.taglib.datasource;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.taglib.RMT2TagSupportBase;
import com.util.RMT2Date;
import com.util.RMT2Money;
import com.util.SystemException;
import com.util.NotFoundException;
import com.util.RMT2Utility;

import com.api.DaoApi;
import com.api.DataSourceApi;
import com.api.db.DatabaseException;

/**
 * Creates read only HTML output using contents of properties contained in the
 * RMT2DataSourceApi.
 * 
 * @author roy.terrell
 * @deprecated Use RMT2DsoInputControlTag to produce plain text output.
 * 
 */
public class RMT2ShowValueTag extends RMT2TagSupportBase {

    /** The property of the data source to be displayed. */
    protected String property = null;

    /** The format that is to be applied to the property */
    protected String format = null;

    /** The substitute value for spaces. Defaults to "" */
    protected String spaceFormat = null;

    /**
     * Sets the name of the property.
     * 
     * @param value
     */
    public void setProperty(String value) {
	this.property = value;
    }

    /**
     * Sets the property formating.
     * 
     * @param value
     */
    public void setFormat(String value) {
	this.format = value;
    }

    /**
     * Sets the spacing format.
     * 
     * @param value
     */
    public void setSpaceFormat(String value) {
	// value = ""
	// value = "&nbsp;"
	this.spaceFormat = value;
    }

    /**
     * The entry point into this tag that creates the appropriate HTML text
     * value.
     * <ul>
     * <li>Obtains the RMT2DataSourceApi associated with the "dataSource"
     * property</li>
     * <li>Uses the property name to extract the value and data type from the
     * data source</li>
     * <li>Apply any formatting to the value, if applicable</li>
     * <li>Outputs the HTML text value to the JSP page via the ServletResponse
     * object</li>
     * </ul>
     * 
     * @throws JspException
     *             for I/O errors, System errors, Database errors, or references
     *             made to properties that do not exist
     */
    public int doStartTag() throws JspException {
	try {
	    super.doStartTag();
	    DaoApi dso = (DaoApi) this.obj;
	    if (dso != null && this.property != null) {
		String value = dso.getColumnValue(this.property);
		Integer javaType = (Integer) dso.getDataSourceAttib().getColumnAttribute(this.property, "javaType");

		if (value == null) {
		    if (this.spaceFormat == null) {
			value = "&nbsp;";
		    }
		    else {
			value = this.spaceFormat;
		    }
		}
		else if (value.length() <= 0) {
		    if (this.spaceFormat == null) {
			value = "&nbsp;";
		    }
		    else {
			value = this.spaceFormat;
		    }
		}
		if (this.format != null && value.length() > 0 && !value.equalsIgnoreCase("&nbsp;")) {
		    try {
			switch (javaType.intValue()) {
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
		this.outputHtml(value);
	    }

	    return SKIP_BODY;
	}
	catch (IOException e) {
	    throw new JspException("Class: " + this.className + "  Method: " + this.methodName + "  Message (IOException): " + e.getMessage());
	}
	catch (SystemException e) {
	    throw new JspException("Class: " + this.className + "  Method: " + this.methodName + "  Message (SystemException): " + e.getMessage());
	}
	catch (DatabaseException e) {
	    throw new JspException("Class: " + this.className + "  Method: " + this.methodName + "  Message (DatabaseException): " + e.getMessage());
	}
	catch (NotFoundException e) {
	    throw new JspException("Class: " + this.className + "  Method: " + this.methodName + "  Message (NotFoundException): " + e.getMessage());
	}
    }

    /**
     * NO Action
     */
    protected void exportData() throws JspException {
	return;
    }

}