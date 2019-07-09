package com.taglib.primitive;

import java.io.IOException;

import java.util.Date;

import javax.servlet.jsp.JspException;

import com.taglib.RMT2TagSupportBase;
import com.util.RMT2Date;
import com.util.RMT2Money;
import com.util.SystemException;


/**
 * Creates read only HTML output using teh value of primitive wrapper objects.
 * 
 * @author roy.terrell
 * @deprecated Will be dropped in future releases.  Use {@link com.taglib.primitive.RMT2WrapperInputControlTag RMT2WrapperInputControlTag} implementation.
 *
 */
public class RMT2WrapperTag extends RMT2TagSupportBase {
	private static final long serialVersionUID = 8560568411363495550L;
	
	/** The format that is to be applied to primitive wrapper value */
	protected String format = null;
	/** The primitive wrapper's value */
	protected String value = null;
	/** The data type of the primitive wrapper. */
	protected String type = null;

	/**
	 * Sets the primitive wrapper's value
	 * @param _value
	 */
	public void setValue(String _value) {
		this.value = _value;
	}
	/**
	 * Sets the primitive wrapper's type
	 * @param _value
	 */
	public void setType(String _value) {
		this.type = _value;
	}	
	/**
	 * Sets the primitive wrapper's format
	 * @param _value
	 */
	public void setFormat(String _value) {
		this.format = _value;
	}

	
	  /**
	   * The entry point into this tag that creates the appropriate HTML text fro the primitive wrapper's value.
	   * <ul>
	   * 	<li>Obtains the Primitive Wrapper object from the "dataSource" property</li>
	   * 	<li>Apply any formatting to the value, if applicable</li>
	   * 	<li>Outputs the HTML text value to the JSP page via the ServletResponse object</li>
	   * </ul>
	   * 
	   *    @throws JspException for I/O errors, System errors, Database errors, or references made to properties that do not exist
	   */
	public int doStartTag() throws JspException {
		try {
			// Get the value stored as "dataSource"
			super.doStartTag();
			if (this.obj == null) {
				return SKIP_BODY;
			}
      
			if (this.format != null) {
				try {
					if (this.obj instanceof Date) {
						java.util.Date date = (Date) this.obj;
						this.value = RMT2Date.formatDate(date, this.format);
					}
					else if (this.obj instanceof Integer ||  this.obj instanceof Float || this.obj instanceof Double || this.obj instanceof Number) {
						this.value = RMT2Money.formatNumber(new Double(this.obj.toString()), this.format);
					}
				}
				catch (SystemException e) {
					throw new JspException(e.getMessage());
				}
			}
			else {
				if (this.obj != null) {
					if (this.value != null && this.value.equalsIgnoreCase("rowid")) {
						this.value = (String) pageContext.getAttribute("ROW");
					}
					else {
						this.value = this.obj.toString();
					}
				}
			}

			// Output on the value of thewrapper class and no html control tags
			this.outputHtml(this.value);
			return SKIP_BODY;
		}
		catch (IOException e) {
			throw new JspException("Class: " + this.className + "  Method: " + this.methodName + "  Message (IOException): " + e.getMessage());
		}
	}


	/**
	 * No Action
	 */
	protected void exportData() throws JspException {
		return;
	}

}  // end class
