package com.taglib.datasource;

import java.io.IOException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.ParseException;

import java.util.Date;

import com.taglib.RMT2TagSupportBase;
import com.util.SystemException;
import com.util.NotFoundException;
import com.util.RMT2Utility;

import com.api.DataSourceApi;
import com.api.db.DatabaseException;

/**
 * This class builds HTML input controls from a RMT2DataSourceApi data source.
 * 
 * @deprecated use {@link RMT2DsoInputControlTag} as a replacement.
 * @author roy.terrell
 * 
 */
public class RMT2InputControlTag extends RMT2TagSupportBase {

    /** This is the datasource used to obtain date from an external provider. */
    protected DataSourceApi dso = null;

    /** The name of the property targeted for this tag */
    protected String property = null;

    /** The datatype the property */
    protected String type = null;

    /** The name of the HTML input control */
    protected String name = null;

    /** The size of the HTML input control */
    protected String size = null;

    /** Maximumn length of HTML input control */
    protected String maxLength = null;

    /** Value indicating the alignment of the contents of the HTML input control */
    protected String align = null;

    /**
     * Indicates whether or not the HTML input control is disabled. It is
     * disabled by default.
     */
    protected boolean disabled = false;

    /**
     * Indicates whether or not the HTML input control is readonly. It is
     * readonly by default.
     */
    protected boolean readOnly = false;

    /** Indicates the tab ordering of the HTML input */
    protected String tabIndex = null;

    /** The style cluase of the HTML input control */
    protected String style = null;

    /** The onFocus event handler invocation implementation */
    protected String onFocus = null;

    /** The onBlur event handler invocation implementation */
    protected String onBlur = null;

    /** The onSelect event handler invocation implementation */
    protected String onSelect = null;

    /** The onChange event handler invocation implementation */
    protected String onChange = null;

    /** The onClick event handler invocation implementation */
    protected String onClick = null;

    /**
     * The format string to be applied to property value of the HTML input
     * control. Uses basic Java Masks
     */
    protected String format = null;

    /** The value of the property */
    protected String value = null;

    /**
     * Contains a value of 'checked' to indicate that the HTML radio button or
     * checkbox input control is selected
     */
    protected String checkedValue = null;

    /**
     * Contains a blank value to indicate that the HTML radio button or checkbox
     * input control is not selected.
     */
    protected String unCheckedValue = null;

    /**
     * Indicates that the HTML input control's name should be uniquely
     * identified.
     */
    protected boolean uniqueName = false;

    /**
     * Used as one of the custom tag properties to indicate whether or not the
     * HTML Select tag can have multiple selections
     */
    protected String multiSelect = null;

    /**
     * Used internally as a boolean value to indicate whether or not the HTML
     * Select tag can have multiple selections
     */
    protected boolean multiple = false;

    /**
     * The value contained in the HTML Select tag that is to be selected once
     * the Select tag is rendered.
     */
    protected String selectedValue = null;

    /**
     * This is the name of the property that is mapped to the values displayed
     * in the dropdown list
     */
    protected String displayProperty = null;

    /**
     * This is the name of the property that is mapped to the code values in the
     * dropdown list
     */
    protected String codeProperty = null;

    /** The title of the HTML input control */
    protected String title = null;

    /**
     * Used as one of the custom tag properties to indicate whether or not a
     * border should be drawn around the the HTML input control
     */
    protected String border = null;

    /**
     * Used internally to indicate whether or not a border should be drawn
     * around the the HTML input control
     */
    protected boolean drawBorder = false;

    /** Mandates the number of rows contained in the HTML input control. */
    protected String rows = null;

    /** Mandates the number of columns contained in the HTML input control. */
    protected String cols = null;

    /**
     * Sets the property name.
     * 
     * @param _value
     */
    public void setProperty(String _value) {
        this.property = _value;
    }

    /**
     * Set Property Type
     * 
     * @param _value
     */
    public void setType(String _value) {
        this.type = _value;
    }

    /**
     * Set the name of the HTML input cotrol.
     * 
     * @param _value
     */
    public void setName(String _value) {
        this.name = _value;
    }

    /**
     * Set the value of the HTML Input control.
     * 
     * @param _value
     */
    public void setValue(String _value) {
        this.value = _value;
    }

    /**
     * Set the size of the HTML input control.
     * 
     * @param _value
     */
    public void setSize(String _value) {
        this.size = _value;
    }

    /**
     * Set the maximum length of the contents of the HTML input cotrol.
     * 
     * @param _value
     */
    public void setMaxLength(String _value) {
        this.maxLength = _value;
    }

    /**
     * Set the alignment of the HTML input control.
     * 
     * @param _value
     */
    public void setAlign(String _value) {
        this.align = _value;
    }

    /**
     * Set the disable attribute of the HTML control.
     * 
     * @param _value
     */
    public void setDisabled(String _value) {
        this.disabled = (_value.equalsIgnoreCase("yes") || _value.equalsIgnoreCase("y") || _value.equalsIgnoreCase("true"));
    }

    /**
     * Set the read only attribute of the HTML input control.
     * 
     * @param _value
     */
    public void setReadOnly(String _value) {
        this.readOnly = (_value.equalsIgnoreCase("yes") || _value.equalsIgnoreCase("y") || _value.equalsIgnoreCase("true"));
    }

    /**
     * Set the tab order of the HTML input control.
     * 
     * @param _value
     */
    public void setTabIndex(String _value) {
        this.tabIndex = _value;
    }

    /**
     * Set the style of the HTML input control.
     * 
     * @param _value
     */
    public void setStyle(String _value) {
        this.style = _value;
    }

    /**
     * Set the onFoucus event Handler for the HTML control. _value should be the
     * actual call to the event handler.
     * 
     * @param _value
     */
    public void setOnFocus(String _value) {
        this.onFocus = _value;
    }

    /**
     * Set the onBlur event Handler for the HTML control. _value should be the
     * actual call to the event handler.
     * 
     * @param _value
     */
    public void setOnBlur(String _value) {
        this.onBlur = _value;
    }

    /**
     * Set the onSelect event Handler for the HTML control. _value should be the
     * actual call to the event handler.
     * 
     * @param _value
     */
    public void setOnSelect(String _value) {
        this.onSelect = _value;
    }

    /**
     * Set the onChange event Handler for the HTML control. _value should be the
     * actual call to the event handler.
     * 
     * @param _value
     */
    public void setOnChange(String _value) {
        this.onChange = _value;
    }

    /**
     * Set the onClick event Handler for the HTML control. _value should be the
     * actual call to the event handler.
     * 
     * @param _value
     */
    public void setOnClick(String _value) {
        this.onClick = _value;
    }

    /**
     * Set the format of the value property of the HTML input control.
     * 
     * @param _value
     */
    public void setFormat(String _value) {
        this.format = _value;
    }

    /**
     * Set the checked indicator for the radio button or checkbox HTML input
     * control.
     * 
     * @param _value
     */
    public void setCheckedValue(String _value) {
        this.checkedValue = _value;
    }

    /**
     * Set HTML radio button or checkbox input control as unchecked
     * 
     * @param _value
     */
    public void setUnCheckedValue(String _value) {
        this.unCheckedValue = _value;
    }

    /**
     * Set the unique name indicator of the HTML input control.
     * 
     * @param _value
     */
    public void setUniqueName(String _value) {
        this.uniqueName = (_value.equalsIgnoreCase("yes") || _value.equalsIgnoreCase("y") || _value.equalsIgnoreCase("true"));
    }

    /**
     * Set the multi-select inidcator for HTML Select tag
     * 
     * @param _value
     */
    public void setMultiSelect(String _value) {
        this.multiple = (_value.equalsIgnoreCase("yes") || _value.equalsIgnoreCase("y") || _value.equalsIgnoreCase("true"));
    }

    /**
     * Set the Selecte value property of the HTML Select cotrol.
     * 
     * @param _value
     */
    public void setSelectedValue(String _value) {
        this.selectedValue = _value;
    }

    /**
     * Set the display property of the dropdown list control.
     * 
     * @param _value
     */
    public void setDisplayProperty(String _value) {
        this.displayProperty = _value;
    }

    /**
     * Select the code value of the dropdown list control.
     * 
     * @param _value
     */
    public void setCodeProperty(String _value) {
        this.codeProperty = _value;
    }

    /**
     * Select the title of the HTML input control.
     * 
     * @param _value
     */
    public void setTitle(String _value) {
        this.title = _value;
    }

    /**
     * Set the border indicator for the HTML input control.
     * 
     * @param _value
     */
    public void setBorder(String _value) {
        this.drawBorder = (_value.equalsIgnoreCase("yes") || _value.equalsIgnoreCase("y") || _value.equalsIgnoreCase("true"));
    }

    /**
     * Set the number of rows in the HTML input control.
     * 
     * @param value
     */
    public void setRows(String value) {
        this.rows = value;
    }

    /**
     * Set the number of columns in the HTML input control.
     * 
     * @param value
     */
    public void setCols(String value) {
        this.cols = value;
    }

    /**
     * The entry point into this tag that creates the appropriate HTML input
     * control.
     * <ul>
     * <li>Obtains the RMT2DataSourceApi associated with the "dataSource"
     * property</li>
     * <li>Uses the property name to extract the value and data type from the
     * data source</li>
     * <li>Apply any formatting to the value, if applicable</li>
     * <li>Builds the appropriate HTML input control based on the Type
     * attribute of the custom tag</li>
     * <li>Outputs the control to the JSP page via the ServletResponse object</li>
     * </ul>
     * 
     * @throws JspException
     *             for I/O errors, System errors, Database errors, or references
     *             made to properties that do not exist
     */
    public int doStartTag() throws JspException {

        String valueHold = null;

        try {
            super.doStartTag();

            // Get the control's value from the database
            dso = (DataSourceApi) this.obj;
            if (dso != null && this.property != null) {
                this.value = dso.getColumnValue(this.property);
                Integer javaType = (Integer) dso.getDataSourceAttib().getColumnAttribute(this.property, "javaType");

                if (this.value == null) {
                    this.value = "";
                }
                if (this.format != null && this.value != "" && this.value != null) {
                    valueHold = this.value;
                    try {
                        switch (javaType.intValue()) {
                        case java.sql.Types.TIMESTAMP:
                            java.util.Date date = RMT2Utility.stringToDate(value);
                            this.value = RMT2Utility.formatDate(date, this.format);
                            break;

                        case java.sql.Types.INTEGER:
                        case java.sql.Types.FLOAT:
                        case java.sql.Types.DOUBLE:
                            this.value = RMT2Utility.formatNumber(new Double(value), this.format);
                            break;
                        }
                    }
                    catch (SystemException e) {
                        throw new JspException(e.getMessage());
                    }
                }
            }
            else {
                if (dso != null && this.property == null && this.value != null) {
                    if (this.value.equalsIgnoreCase("rowid")) {
                        this.value = (String) pageContext.getAttribute("ROW");
                    }
                }
            }

            this.outputHtml(buildInputControl());

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
     * No Action
     */
    protected void exportData() throws JspException {
        return;
    }

    /**
     * This method uses the information gathered and stored in its member
     * variables to build HTML input, textarea, or Select controls.
     * 
     * @return The HTML in the format of a String.
     * @throws DatabaseException
     * @throws NotFoundException
     * @throws SystemException
     * @throws JspException
     */
    protected String buildInputControl() throws DatabaseException, NotFoundException, SystemException {

        StringBuffer html = new StringBuffer(200);

        if (this.type.equalsIgnoreCase("select")) {
            return this.buildSelectControl(html);
        }
        if (this.type.equalsIgnoreCase("radiolookup")) {
            return this.buildRadioControl(html);
        }

        if (this.type.equalsIgnoreCase("multiline")) {
            return this.buildTextArea(html);
        }

        // Add type attribute
        if (this.type != null) {
            html.append(" type=\"");
            html.append(this.type.toLowerCase());
            html.append("\"");
        }
        else {
            html.append(" type=\"text\" ");
        }

        // Add name attribute
        if (this.name != null) {
            html.append(" name=\"");
            html.append(this.name);
            if (this.uniqueName) {
                String row = (String) pageContext.getAttribute("ROW");
                if (row != null && row.length() > 0) {
                    html.append(row);
                }
            }
            html.append("\"");
        }

        // Add value attribute
        if (this.value != null) {
            html.append(" value=\"");
            html.append(this.value);
            html.append("\"");
        }

        // Add size attribute
        if (this.size != null) {
            html.append(" size=\"");
            html.append(this.size);
            html.append("\"");
        }

        // Add maxLength attribute
        if (this.maxLength != null) {
            html.append(" maxlength=\"");
            html.append(this.maxLength);
            html.append("\"");
        }

        // Add align attribute
        if (this.align != null) {
            html.append(" align=\"");
            html.append(this.align.toLowerCase());
            html.append("\"");
        }

        // Add disabled attribute
        if (this.disabled) {
            html.append(" disabled");
        }

        // Add read only attribute
        if (this.readOnly) {
            html.append(" readonly");
        }

        // Add tabIndex attribute
        if (this.tabIndex != null) {
            html.append(" tabindex=\"");
            html.append(this.tabIndex);
            html.append("\"");
        }

        // Add style attribute
        if (this.style != null) {
            html.append(" style=\"");
            html.append(this.style);
            html.append("\"");
        }

        // Add Multiple attribute for Select HTML Control
        if (this.multiple) {
            html.append(" multiple");
        }

        // Determine if checkbox is slected.
        if (this.type.equalsIgnoreCase("checkbox") || this.type.equalsIgnoreCase("radio")) {
            if (this.value.equalsIgnoreCase(this.checkedValue)) {
                html.append(" checked ");
            }
        }

        // Add onFocus attribute
        if (this.onFocus != null) {
            html.append(" onFocus=\"");
            html.append(this.onFocus);
            html.append("\"");
        }

        // Add onBlur attribute
        if (this.onBlur != null) {
            html.append(" onBlur=\"");
            html.append(this.onBlur);
            html.append("\"");
        }

        // Add onSelect attribute
        if (this.onSelect != null) {
            html.append(" onSelect=\"");
            html.append(this.onSelect);
            html.append("\"");
        }

        // Add onChange attribute
        if (this.onChange != null) {
            html.append(" onChange=\"");
            html.append(this.onChange);
            html.append("\"");
        }

        // Add onClick attribute
        if (this.onClick != null) {
            html.append(" onClick=\"");
            html.append(this.onClick);
            html.append("\"");
        }

        // Complete construction of input control
        if (html.length() <= 0) {
            return "";
        }

        html.insert(0, "<input");
        html.append(">");

        if (this.type.equalsIgnoreCase("radio") || this.type.equalsIgnoreCase("checkbox")) {
            if (this.displayProperty != null) {
                html.append(this.displayProperty);
            }
        }

        return html.toString();
    }

    /**
     * Builds a HTML TextArea control when the custom tag attribute, type,
     * equals "multiline".
     * 
     * @param html
     *            variable used as a work area for building the control.
     * @return The HTML in the format of a String.
     */
    protected String buildTextArea(StringBuffer html) {
        if (this.name != null) {
            html.append("<textarea name=\"" + this.name + "\"");
        }
        else {
            html.append("<textarea ");
        }

        // Add rows
        if (this.rows != null) {
            html.append(" rows=");
            html.append(this.rows);
        }

        // Add cols
        if (this.cols != null) {
            html.append(" cols=");
            html.append(this.cols);
        }

        // Add disabled attribute
        if (this.disabled) {
            html.append(" disabled");
        }

        // Add read only attribute
        if (this.readOnly) {
            html.append(" readonly");
        }

        // Add style attribute
        if (this.style != null) {
            html.append(" style=\"");
            html.append(this.style);
            html.append("\"");
        }

        // Add tabIndex attribute
        if (this.tabIndex != null) {
            html.append(" tabindex=\"");
            html.append(this.tabIndex);
            html.append("\"");
        }

        // Add onFocus attribute
        if (this.onFocus != null) {
            html.append(" onFocus=\"");
            html.append(this.onFocus);
            html.append("\"");
        }

        // Add onBlur attribute
        if (this.onBlur != null) {
            html.append(" onBlur=\"");
            html.append(this.onBlur);
            html.append("\"");
        }

        // Add onSelect attribute
        if (this.onSelect != null) {
            html.append(" onSelect=\"");
            html.append(this.onSelect);
            html.append("\"");
        }

        // Add onChange attribute
        if (this.onChange != null) {
            html.append(" onChange=\"");
            html.append(this.onChange);
            html.append("\"");
        }

        html.append(">");
        if (this.value.trim().length() > 0) {
            html.append(this.value);
        }
        html.append("</textarea>");

        return html.toString();
    }

    /**
     * Builds a HTML Select control when the custom tag, type, equals "select".
     * 
     * @param html
     *            variable used as a work area for building the control.
     * @returnThe HTML in the format of a String.
     * @throws NotFoundException
     * @throws SystemException
     * @throws JspException
     */
    protected String buildSelectControl(StringBuffer html) throws DatabaseException, NotFoundException, SystemException {

        if (this.name != null) {
            html.append("<select name=\"" + this.name);
            if (this.uniqueName) {
                String row = (String) pageContext.getAttribute("ROW");
                if (row != null && row.length() > 0) {
                    html.append(row);
                }
            }
            html.append("\"");
        }
        else {
            html.append("<select ");
        }
        if (this.size != null) {
            html.append(" size=");
            html.append(this.size);
        }
        if (this.multiple) {
            html.append(" multiple");
        }

        // Add style attribute
        if (this.style != null) {
            html.append(" style=\"");
            html.append(this.style);
            html.append("\"");
        }

        // Add tabIndex attribute
        if (this.tabIndex != null) {
            html.append(" tabindex=\"");
            html.append(this.tabIndex);
            html.append("\"");
        }

        // Add onFocus attribute
        if (this.onFocus != null) {
            html.append(" onFocus=\"");
            html.append(this.onFocus);
            html.append("\"");
        }

        // Add onBlur attribute
        if (this.onBlur != null) {
            html.append(" onBlur=\"");
            html.append(this.onBlur);
            html.append("\"");
        }

        // Add onSelect attribute
        if (this.onSelect != null) {
            html.append(" onSelect=\"");
            html.append(this.onSelect);
            html.append("\"");
        }

        // Add onChange attribute
        if (this.onChange != null) {
            html.append(" onChange=\"");
            html.append(this.onChange);
            html.append("\"");
        }

        html.append(">  ");

        String displayValue = null;
        String codeValue = null;

        // Do not display "Select One" option if we allow multiple selections
        if (!this.multiple) {
            html.append("\n\t<option value=\"\">-- Select One --");
        }

        while (dso.nextRow()) {
            displayValue = dso.getColumnValue(this.displayProperty).trim();
            codeValue = dso.getColumnValue(this.codeProperty).trim();
            html.append("\n\t<option value=\"");
            html.append(codeValue);
            html.append("\"");
            if (this.selectedValue != null && codeValue.equalsIgnoreCase(this.selectedValue)) {
                html.append(" selected ");
            }
            html.append("> ");
            html.append(displayValue);
        }
        html.append("</select>");

        return html.toString();
    }

    /**
     * Builds a HTML radio button input control when the custom tag, type,
     * equals "radio" or "radiolookup".
     * 
     * @param html
     *            variable used as a work area for building the control.
     * @returnThe HTML in the format of a String.
     * @throws NotFoundException
     * @throws SystemException
     * @throws JspException
     */
    protected String buildRadioControl(StringBuffer html) throws DatabaseException, NotFoundException, SystemException {

        String displayValue = null;
        String codeValue = null;

        if (this.drawBorder) {
            html.append("<table width=\"100%\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\n");
            html.append("  <tr>\n");
            html.append("     <td>\n");
        }

        // begin building the table that is going to house the radio buttons
        html.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");

        while (dso.nextRow()) {
            html.append("<tr>\n");
            html.append("   <td valign=\"top\">\n");
            html.append("      <p>\n");
            html.append("         <font size=\"2\">\n");

            // begin building the actual radio button
            if (this.name != null) {
                html.append("\n<input type=\"radio\" name=\"" + this.name + "\"");
            }
            else {
                html.append("<input type=\"radio\" ");
            }
            // Add style attribute
            if (this.style != null) {
                html.append(" style=\"");
                html.append(this.style);
                html.append("\"");
            }

            // Add tabIndex attribute
            if (this.tabIndex != null) {
                html.append(" tabindex=\"");
                html.append(this.tabIndex);
                html.append("\"");
            }

            // Add onFocus attribute
            if (this.onFocus != null) {
                html.append(" onFocus=\"");
                html.append(this.onFocus);
                html.append("\"");
            }

            // Add onBlur attribute
            if (this.onBlur != null) {
                html.append(" onBlur=\"");
                html.append(this.onBlur);
                html.append("\"");
            }

            // Add onSelect attribute
            if (this.onSelect != null) {
                html.append(" onSelect=\"");
                html.append(this.onSelect);
                html.append("\"");
            }

            // Add onClick attribute
            if (this.onClick != null) {
                html.append(" onClick=\"");
                html.append(this.onClick);
                html.append("\"");
            }

            // Add onChange attribute
            if (this.onChange != null) {
                html.append(" onChange=\"");
                html.append(this.onChange);
                html.append("\"");
            }

            displayValue = dso.getColumnValue(this.displayProperty).trim();
            codeValue = dso.getColumnValue(this.codeProperty).trim();
            html.append(" value=\"");
            html.append(codeValue);
            html.append("\"");

            if (this.selectedValue != null) {
                if (codeValue.equalsIgnoreCase(this.selectedValue)) {
                    html.append(" checked ");
                }
            }
            html.append("> ");
            html.append(displayValue);

            // Finish up the table row
            html.append("     </font>\n");
            html.append("    </p>\n");
            html.append("  </td>\n");
            html.append("</tr>\n");
        } // end while

        // clean up the inner table
        html.append("</table>\n");

        // clean up the outer table if applicable
        if (this.drawBorder) {
            html.append("</td>\n");
            html.append("</tr>\n");
            html.append("</table>\n");
        }

        return html.toString();
    }

} // end class
