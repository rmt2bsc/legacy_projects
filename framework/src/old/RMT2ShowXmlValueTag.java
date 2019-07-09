package com.taglib.xml;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.api.DaoApi;

import com.api.db.DatabaseException;

import com.constants.CustomTypes;

import com.taglib.RMT2TagSupportBase;
import com.util.NotFoundException;
import com.util.RMT2Date;
import com.util.RMT2Money;
import com.util.SystemException;

/**
 * JSP custom tag implementation that extracts the value of an XML source stored
 * on one of the JSP variable scopes: application, session, request, or
 * pageContext. It is designed to render a single value as HTML output to the
 * client. The source of the XML element can be in the form of a
 * org.w3c.dom.Node or as an XML document in String format. If the data source
 * is of an XML document, XPath is used as the querying method to target a
 * specifie element.
 * <p>
 * For example, to obtain the value from a tag named, title, in an XML document (xmlDocRef) stored in 
 * one of the JSP variable scopes:<br>
 * &lt;xml:ElementValue dataSource="xmlDocRef" query="book[@year=1995]"
 * element="title"/&gt;
 * <p>
 * To obtain the value of tag named, title, from an XML Node object (xmlNodeRef): <br>
 * &lt;xml:ElementValue dataSource="xmlNodeRef" element="title"/&gt;
 * 
 * @author roy.terrell
 * @deprecated Will be removed in future releases.  Use {@link com.taglib.xml.RMT2XmlInputControlTag RMT2XmlInputControlTag} instead.
 * 
 */
public class RMT2ShowXmlValueTag extends RMT2TagSupportBase {
    private static final long serialVersionUID = 8181332946324462786L;

    /** The API instance of the dao used to manage the data that is to be presented. */
    protected DaoApi api;

    /** The data type of element value as an integer. */
    private int dataTypeVal;

    /** The XPath expression used to query the XML document */
    protected String query;

    /**
     * The name of the tag or tag attribute which to obtain its value from the
     * data source.
     */
    protected String element;

    /** The substitute value for spaces. Defaults to "" */
    protected String spaceFormat;

    /** The format that is to be applied to the property */
    protected String format;



    /**
     * Sets the XPath expression that is used to find an element in the XML
     * Document.
     * 
     * @param value
     */
    public void setQuery(String value) {
	this.query = value;
    }

    /**
     * Sets the name of the property.
     * 
     * @param value
     */
    public void setElement(String value) {
	this.element = value;
    }

    /**
     * Sets the spacing format.
     * 
     * @param value
     */
    public void setSpaceFormat(String value) {
	this.spaceFormat = value;
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
     * The entry point into this tag that creates the appropriate HTML text
     * value.
     * <ul>
     * <li>Validates the input values coming from the JSP page</li>
     * <li>Obtains the value from the target XML element</li>
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
	    // Get dataSource property value which is this.obj.
	    // If the dataSource property cannot be obtained
	    // then an error will be produced.
	    super.doStartTag();

	    // Get XML Dao which is positioned on the current row we need.
	    if (this.obj != null && this.obj instanceof DaoApi) {
		this.api = (DaoApi) this.obj;
	    }

	    // Validate input data
	    this.validate();
	    // Get element's data value
	    String value = this.getXmlValue();
	    // Format data, if applicable
	    value = this.formatData(value);
	    // Build HTML code
	    String html;
	    try {
		html = this.buildHtmlCode(value);
	    }
	    catch (Exception e) {
		throw new JspException(e);
	    }
	    // Send output to JSP
	    this.outputHtml(html);
	    return SKIP_BODY;
	}
	catch (IOException e) {
	    throw new JspException(e);
	}
    }

    /**
     * Validates the input values from the JSP. This method ensures that the
     * element property is supplied a value and that a reference is supplied for
     * the data source (either the XML node or XML document).
     * 
     * @throws JspException
     *             If the element is invalid, or if the references to the XML
     *             node and XML document are not mutually exclusive.
     */
    private void validate() throws JspException {
	if (this.element == null) {
	    throw new JspException("Element name is invalid or null");
	}
    }

    /**
     * Drives the process of obtaining the XML element's value from the object
     * that is mapped to the "dataSource" property (this.obj). This mehtod is
     * capable of determining the data type of the data source which is required
     * to be either a String or an org.w3x.dom.Node object. When the data type is
     * of String, the dataSource property is usually represented as an XML doucment.
     * 
     * @return Value of the XML element.
     * @throws JspException
     *             When the data source is of the incorrect data type or the XML
     *             document does not contain any content.
     */
    protected String getXmlValue() throws JspException {
	Object val = null;

	try {
	    val = this.api.getColumnValue(this.element);
	    return val.toString();
	}
	catch (Exception e) {
	    throw new JspException(e);
	}
    }

    /**
     * Apply formatting to the XML element's data value.
     * 
     * @param value
     *            The value of the XML element.
     * @return Formatted data value.
     * @throws JspException
     *             If element could not be formatted.
     */
    private String formatData(String value) throws JspException {
	String newValue = value;

	if (value == null) {
	    if (this.spaceFormat == null) {
		newValue = "&nbsp;";
	    }
	    else {
		newValue = this.spaceFormat;
	    }
	}
	else if (value.length() <= 0) {
	    if (this.spaceFormat == null) {
		newValue = "&nbsp;";
	    }
	    else {
		newValue = this.spaceFormat;
	    }
	}
	if (this.format != null && newValue.length() > 0 && !newValue.equalsIgnoreCase("&nbsp;")) {
	    try {
		switch (this.dataTypeVal) {
		case CustomTypes.DATE:
		    java.util.Date date = RMT2Date.stringToDate(value);
		    newValue = RMT2Date.formatDate(date, this.format);
		    break;

		case CustomTypes.NUMBER:
		    newValue = RMT2Money.formatNumber(new Double(value), this.format);
		    break;
		}
	    }
	    catch (SystemException e) {
		throw new JspException(e.getMessage());
	    }
	}
	return newValue;
    }

    /**
     * NO Action
     */
    protected void exportData() throws JspException {
	return;
    }

    /**
     * Uses the target property value as the HTML code to be rendered.
     * 
     * @param value the value of the property.
     * @return The value of the target property.
     * @throws DatabaseException
     * @throws NotFoundException
     * @throws SystemException
     * @throws JspException
     */
    protected String buildHtmlCode(String value) throws DatabaseException, NotFoundException, SystemException {
	return value;
    }
}