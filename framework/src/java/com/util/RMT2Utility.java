package com.util;

import javax.activation.MimetypesFileTypeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.NotSerializableException;
import java.io.PrintStream;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.ResourceBundle;
import java.util.Properties;
import java.util.MissingResourceException;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;

import java.math.BigDecimal;
import java.net.URL;
import java.net.URLClassLoader;

import junit.framework.Assert;

import com.bean.ElapsedTime;

import com.bean.custom.UserTimestamp;

import com.api.DataSourceApi;
import com.api.db.DatabaseException;
import com.api.security.authentication.RMT2SessionBean;

import com.util.SystemException;

import com.constants.RMT2SystemExceptionConst;
import com.constants.RMT2ServletConst;
import com.controller.Request;

/**
 * Class contains a collection general purpose utilities.
 * 
 * @author roy.terrell
 *
 */
public class RMT2Utility {

    protected static String className = "RMT2Utility";

    protected static String methodName = "";

    /** The name of the system ResourceBundle */
    public static final String CONFIG_SYSTEM = "SystemParms";

    /** The name of the application ResourceBundle */
    public static final String CONFIG_APP = "AppParms";

    /** 
     * List of the days of the week in abbreviated form.   
     * The first element = Sun and the last = Sat. 
     *
     * @deprecated reference RMT2Date
     */
    public static final String DAYS_OF_WEEK[] = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

    /** 
     * File exists code 
     *
     * @deprecated reference in RMT2File
     */
    public static final int FILE_IO_EXIST = 1;

    /** 
     * File does not exist code 
     * @deprecated reference in RMT2File 
     */
    public static final int FILE_IO_NOTEXIST = -1;

    /** 
     * file is null 
     * @deprecated reference in RMT2File
     * */
    public static final int FILE_IO_NULL = 0;

    /** 
     * File is inaccessible 
     * @deprecated reference in RMT2File
     * */
    public static final int FILE_IO_INACCESSIBLE = -2;

    /** 
     * File object is not a file 
     * @deprecated reference in RMT2File
     * */
    public static final int FILE_IO_NOT_FILE = -3;

    /** 
     * File object is not a directory 
     * @deprecated reference in RMT2File
     * */
    public static final int FILE_IO_NOT_DIR = -4;

    /** 
     * FIrst day of the week  
     * @deprecated reference RMT2Date
     */
    public static final int DOW_FIRST = 1;

    /** 
     * Last day of the week
     * @deprecated reference RMT2Date 
     */
    public static final int DOW_LAST = 7;

    /** 
     * Total number days in the week 
     *
     * @deprecated reference RMT2Date
     */
    public static final int DOW_TOTAL = 7;

    /** 
     * Pad String with leading characters 
     *
     * @deprecated reference RMT2String
     */
    public static final int PAD_LEADING = -1;

    /** 
     * Pad String with trailing characters 
     *
     * @deprecated reference RMT2String
     */
    public static final int PAD_TRAILING = 1;

    /**
     * Creates a string of spaces totaling "count".
     * 
     * @param count total number of spaces to produce
     * @return String
     * @deprecated reference RMT2String
     */
    public static final String spaces(int count) {

        String value = "";

        if (count <= 0) {
            return null;
        }
        for (int ndx = 1; ndx <= count; ndx++) {
            value += " ";
        }

        return value;
    }

    /**
     * Creates a string of ch totaling count.
     * 
     * @param ch Character to duplicate
     * @param count Total number of duplications
     * @return String
     * @deprecated reference RMT2String
     */
    public static final String dupChar(char ch, int count) {

        String value = "";

        if (count <= 0) {
            return null;
        }
        for (int ndx = 1; ndx <= count; ndx++) {
            value += ch;
        }

        return value;
    }

    /**
     * Adds leading or trailing zeros to an long value. 
     * <p>
     * <p>
     * Example: to pad 1234 with 6 leading zeros - padInt(1234, 10, RMT2Utility.PAD_LEADING).
     * 
     * @param _source The number to pad
     * @param _precision The total number of digits to exect as a result of this operation.
     * @param _direction -1=leading and 1=trailing
     * @return Padded number.
     * @deprecated reference RMT2String
     */
    public static final String padInt(long _source, int _precision, int _direction) {
        String temp = String.valueOf(_source);
        int addCount = _precision - temp.length();
        String padding = RMT2Utility.dupChar('0', addCount);
        if (_direction == RMT2Utility.PAD_LEADING) {
            temp = padding + temp;
        }
        if (_direction == RMT2Utility.PAD_TRAILING) {
            temp += padding;
        }
        return temp;
    }

    /**
     * Parses _str by extracting embedded values separted by character values that equal _delim.   Each value extracted will be                 
     * packaged in a ArrayList which is returned to the caller.
     * 
     * @param _str String that is to parsed for tokens
     * @param _delim The delimiter separates the tokens
     * @return  ArrayList of each value extracted from _str.
     * @deprecated reference RMT2String
     */
    public static final ArrayList getTokens(String _str, String _delim) {

        ArrayList tokens = new ArrayList();
        String value = null;

        if (_delim == null) {
            _delim = RMT2Utility.spaces(1);
        }

        StringTokenizer target = new StringTokenizer(_str, _delim);
        while (target.hasMoreTokens()) {
            value = target.nextToken();
            tokens.add(value);
        }
        if (tokens.size() <= 0) {
            tokens = null;
        }

        return tokens;
    }

    /**
     * Parses _source by replacing the first occurrence of "_delim" with "_replacement" and returns the results to the caller.
     * 
     * @param _source String that is to parsed for for placeholders
     * @param _replacement Value that will replace "_delim"
     * @param _delim The place holder that is to be replaced
     * @return String
     * @deprecated reference RMT2String
     */
    public static final String replace(String _source, String _replacement, String _delim) {

        String value = null;
        int ndx;

        if (_delim == null) {
            _delim = RMT2Utility.spaces(1);
        }

        // Determine the position of the first occurrence of the delmimter
        ndx = _source.indexOf(_delim);
        if (ndx <= -1) {
            return _source;
        }

        //  Get portion of string before delimiter
        value = _source.substring(0, ndx);

        // Add replacement
        value += _replacement;

        // Get the portion of string beyond the delimiter.
        value += _source.substring(ndx + _delim.length());

        // Retrun the results
        return value;

    }

    /**
     * Parses _source by replacing the all  occurrences of "_delim" with "_replacement" and returns the results to the caller.
     * 
     * @param _source String that is to be parsed for for placeholders
     * @param _replacement Value that will replace "_delim"
     * @param _delim The place holder representing the value that is to be replaced.
     * @return String
     * @deprecated reference RMT2String
     */
    public static final String replaceAll(String _source, String _replacement, String _delim) {

        String newString = null;
        int total;
        int ndx;

        ndx = 0;
        total = 0;

        // Determine the number of place holders are required to be substituted.
        while (ndx != -1) {
            ndx = _source.indexOf(_delim, ndx);
            if (ndx > -1) {
                total++;
                ndx++;
            }
        }

        // Replace each place holder with "_delim".
        newString = _source;
        for (ndx = 1; ndx <= total; ndx++) {
            newString = replace(newString, _replacement, _delim);
        }

        return newString;
    }

    /**
     * Counts the total occurrences of a character within a String.  Returns the total count to the caller.
     * 
     * @param _source String that is the source of target character to be counted.
     * @param _target Character that is being counted.
     * @return the number to occurrences _target was found.
     * @deprecated reference RMT2String
     */
    public static final int countChar(String _source, char _target) {

        int totalFound = 0;
        int sourceSize = _source.length();

        try {
            for (int ndx = 0; ndx < sourceSize; ndx++) {
                if (_source.charAt(ndx) == _target) {
                    totalFound++;
                } // end if
            } // end for

            return totalFound;
        } // end try

        catch (IndexOutOfBoundsException e) {
            return -1;
        }
    }

    /**
     * Obtains the value of a datasource's property from the ResultSet.  The value is converted and passed back to the caller as a String.
     * 
     * @param _rs The source of data.
     * @param _property The name of the column in _rs to target data.
     * @param _type The data type of _property.
     * @return The value of _property as a String.
     * @throws DatabaseException
     */
    public static final String getPropertyValue(ResultSet _rs, String _property, int _type) throws DatabaseException {
        String value = null;

        try {
            switch (_type) {
            case Types.INTEGER:
            case Types.TINYINT:
            case Types.SMALLINT:
                value = new Integer(_rs.getInt(_property)).toString();
                break;

            case Types.BIGINT:
                value = new Long(_rs.getLong(_property)).toString();
                break;

            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
                value = _rs.getString(_property);
                break;

            case Types.REAL:
            case Types.FLOAT:
                value = new Float(_rs.getFloat(_property)).toString();
                break;

            case Types.NUMERIC:
            case Types.DOUBLE:
            case Types.DECIMAL:
                value = new Double(_rs.getDouble(_property)).toString();
                break;

            case Types.DATE:
                value = _rs.getDate(_property).toString();
                break;

            case Types.TIME:
                value = _rs.getTime(_property).toString();
                break;

            case Types.TIMESTAMP:
                value = _rs.getTimestamp(_property).toString();
                break;
            } // end switch

            return value;

        } // end try

        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * Obtains the corresponding java data type class name of _type.   The value is returned to the caller as a String.
     * 
     * @param _type The integer representation of the native java class type or java sql type.
     * @return The class name.
     */
    public static final String getJavaType(int _type) {

        String type = null;

        switch (_type) {
        case Types.INTEGER:
            type = "java.lang.Integer";
            break;
        case Types.TINYINT:
        case Types.SMALLINT:
            type = "java.lang.Short";
            break;

        case Types.BIGINT:
            type = "java.lang.Long";
            break;

        case Types.CHAR:
        case Types.VARCHAR:
        case Types.LONGVARCHAR:
            type = "java.lang.String";
            break;

        case Types.REAL:
        case Types.FLOAT:
            type = "java.lang.Float";
            break;

        case Types.NUMERIC:
        case Types.DOUBLE:
        case Types.DECIMAL:
            type = "java.lang.Double";
            break;

        case Types.DATE:
            type = "java.sql.Types.Date";
            break;

        case Types.TIME:
            type = "java.sql.Types.Time";
            break;

        case Types.TIMESTAMP:
            type = "java.sql.Types.Timestamp";
            break;

        default:
            type = "java.lang.Object";
            break;
        } // end switch

        return type;
    }

    /**
     * Obtains the corresponding integer representation of  _type.   The value is returned to the caller as an integer.
     * 
     * @param _type The class name of a native java type or java.sql type.
     * @return The class name.
     */
    public static final int getJavaType(String _type) {

        if (_type.equalsIgnoreCase("java.lang.Integer") || _type.equalsIgnoreCase("java.sql.Types.INTEGER") || _type.equalsIgnoreCase("java.sql.Types.BIGINT")
                || _type.equalsIgnoreCase("java.sql.Types.SMALLINT") || _type.equalsIgnoreCase("java.sql.Types.TINYINT") || _type.equalsIgnoreCase("integer")
                || _type.equalsIgnoreCase("int"))
            return Types.INTEGER;

        else if (_type.equalsIgnoreCase("java.lang.String") || _type.equalsIgnoreCase("java.sql.Types.CHAR") || _type.equalsIgnoreCase("java.sql.Types.VARCHAR")
                || _type.equalsIgnoreCase("java.sql.Types.LONGVARCHAR") || _type.equalsIgnoreCase("varchar") || _type.equalsIgnoreCase("char"))
            return Types.VARCHAR;

        else if (_type.equalsIgnoreCase("java.lang.Float") || _type.equalsIgnoreCase("java.sql.Types.FLOAT") || _type.equalsIgnoreCase("java.sql.Types.REAL")
                || _type.equalsIgnoreCase("float") || _type.equalsIgnoreCase("real"))
            return Types.FLOAT;

        else if (_type.equalsIgnoreCase("java.lang.Double") || _type.equalsIgnoreCase("java.sql.Types.DOUBLE") || _type.equalsIgnoreCase("java.sql.Types.DECIMAL")
                || _type.equalsIgnoreCase("java.sql.Types.NUMERIC") || _type.equalsIgnoreCase("double") || _type.equalsIgnoreCase("decimal") || _type.equalsIgnoreCase("numeric")
                || _type.equalsIgnoreCase("number"))
            return Types.DOUBLE;

        else if (_type.equalsIgnoreCase("java.util.Date") || _type.equalsIgnoreCase("java.sql.Types.DATE") || _type.equalsIgnoreCase("date"))
            return Types.DATE;

        else if (_type.equalsIgnoreCase("java.sql.Types.TIME") || _type.equalsIgnoreCase("time"))
            return Types.TIME;

        else if (_type.equalsIgnoreCase("java.sql.Types.Timestamp") || _type.equalsIgnoreCase("timestamp") || _type.equalsIgnoreCase("datetime"))
            return Types.TIMESTAMP;
        else if (_type.equalsIgnoreCase("java.io.InputStream") || _type.equalsIgnoreCase("InputStream"))
            return Types.LONGVARBINARY;
        else
            return -1;

    }

    /**
     * Returns the java data type of a particular wrapper object.
     * 
     * @param wrapperObj The primitive wrapper object.
     * @return int {@link java.sql.Types.* Types}
     */
    public static final int getJavaType(Object wrapperObj) {

        if (wrapperObj instanceof Integer)
            return Types.INTEGER;

        else if (wrapperObj instanceof Short)
            return Types.SMALLINT;

        else if (wrapperObj instanceof String)
            return Types.VARCHAR;

        else if (wrapperObj instanceof Float)
            return Types.FLOAT;

        else if (wrapperObj instanceof Double || wrapperObj instanceof Number)
            return Types.DOUBLE;

        else if (wrapperObj instanceof Date)
            return Types.DATE;

        else if (wrapperObj instanceof Time)
            return Types.TIME;

        else if (wrapperObj instanceof Timestamp)
            return Types.TIMESTAMP;

        else
            return -1;

    }

    /**
     * Different date formats.
     * @deprecated reference RMT2Date
     */
    private static ArrayList dateFormats = new ArrayList();
    static {
        dateFormats.add("EEE MMM d HH:mm:ss z yyyy");
        dateFormats.add("yyyy-MM-dd HH:mm:ss");
        dateFormats.add("yyyy-MMM-dd HH:mm:ss");
        dateFormats.add("yyyy-MMMM-dd HH:mm:ss");
        dateFormats.add("MM-dd-yyyy HH:mm:ss");
        dateFormats.add("MMM-dd-yyyy HH:mm:ss");
        dateFormats.add("MM/dd/yyyy HH:mm:ss");
        dateFormats.add("yyyy/MM/dd HH:mm:ss");
        dateFormats.add("MMM/dd/yyyy HH:mm:ss");
        dateFormats.add("MMM dd, yyyy HH:mm:ss");
        dateFormats.add("MMMM dd, yyyy HH:mm:ss");
        dateFormats.add("yyyy-MM-dd hh:mm:ss a");
        dateFormats.add("yyyy-MMM-dd hh:mm:ss a");
        dateFormats.add("yyyy-MMMM-dd hh:mm:ss a");
        dateFormats.add("MM-dd-yyyy hh:mm:ss a");
        dateFormats.add("MMM-dd-yyyy hh:mm:ss a");
        dateFormats.add("MM/dd/yyyy hh:mm:ss a");
        dateFormats.add("yyyy/MM/dd hh:mm:ss a");
        dateFormats.add("MMM/dd/yyyy hh:mm:ss a");
        dateFormats.add("MMM dd, yyyy hh:mm:ss a");
        dateFormats.add("MMMM dd, yyyy hh:mm:ss a");
        dateFormats.add("yyyy-MM-dd hh:mm a");
        dateFormats.add("yyyy-MMM-dd hh:mm a");
        dateFormats.add("yyyy-MMMM-dd hh:mm a");
        dateFormats.add("MM-dd-yyyy hh:mm a");
        dateFormats.add("MMM-dd-yyyy hh:mm a");
        dateFormats.add("MM/dd/yyyy hh:mm a");
        dateFormats.add("yyyy/MM/dd hh:mm a");
        dateFormats.add("MMM/dd/yyyy hh:mm a");
        dateFormats.add("MMM dd, yyyy hh:mm a");
        dateFormats.add("MMMM dd, yyyy hm a");
        dateFormats.add("yyyy-MM-dd");
        dateFormats.add("yyyy-MMM-dd");
        dateFormats.add("yyyy-MMMM-dd");
        dateFormats.add("MM-dd-yyyy");
        dateFormats.add("MMM-dd-yyyy");
        dateFormats.add("MM/dd/yyyy");
        dateFormats.add("yyyy/MM/dd");
        dateFormats.add("MMM/dd/yyyy");
        dateFormats.add("MMM dd, yyyy");
        dateFormats.add("MMMM dd, yyyy");
        dateFormats.add("h:m a");
        dateFormats.add("H:m");
        dateFormats.add("H:m:s");
        dateFormats.add("E MM/dd");
        dateFormats.add("EEEE MM/dd");
    }

    /**
     * Different number formats
     * @deprecated reference RMT2Money
     */
    private static ArrayList numberFormats = new ArrayList();
    static {
        numberFormats.add("$#,##0.00");
        numberFormats.add("$###0.00");
        numberFormats.add("$###0");
        numberFormats.add("$#,##0.00;($#,##0.00)");
        numberFormats.add("$###0.00;($###0.00)");
        numberFormats.add("$###0;($###0)");
        numberFormats.add("($###0.00)");
        numberFormats.add("($###0)");
        numberFormats.add("(###0)");
        numberFormats.add("(###0.00)");
        numberFormats.add("###0.00");
        numberFormats.add("$###0");
        numberFormats.add("###0");
        numberFormats.add("###0.00");
    }

    /**
     * Accepts a date as a String and converts it to java.util.Date.  The date will be converted based on the date format 
     * passed as "format".   If format is null, then "value" is assumed to be in the format of "yyyy-MM-dd".  
     * 
     * @param value String value to convert
     * @param format Date Format
     * @return java.util.Date
     * @throws SystemException if "format" is not an acceptable java date format
     * @deprecated reference RMT2Date
     */
    public static final java.util.Date stringToDate(String value, String format) throws SystemException {

        SimpleDateFormat dateFormatter = null;

        methodName = "stringToDate(String, String)";
        try {
            if (value == null) {
                return null;
            }
            if (value.length() <= 0) {
                return null;
            }

            if (format == null) {
                dateFormatter = new SimpleDateFormat();
            }
            else {
                try {
                    dateFormatter = new SimpleDateFormat(format);
                }
                catch (IllegalArgumentException e) {
                    throw new SystemException("Date Format is invalid");
                }
            }
            dateFormatter.setLenient(false);
            java.util.Date nativeDate = dateFormatter.parse(value);
            if (nativeDate == null) {
                throw new SystemException(
                        RMT2SystemExceptionConst.MSG_INVALID_STRING_DATE_VALUE + ": " + value + " formatted as: " + format, RMT2SystemExceptionConst.RC_INVALID_STRING_DATE_VALUE);
            }

            return nativeDate;
        }
        catch (ParseException e) {
            throw new SystemException(
                    RMT2SystemExceptionConst.MSG_INVALID_STRING_DATE_VALUE + ": " + value + " formatted as: " + format, RMT2SystemExceptionConst.RC_INVALID_STRING_DATE_VALUE);
        }
    }

    /**
     * Accepts a date as a String and converts it to java.util.Date.  The format of, "value", is expected to match one of the date formats
     * in the ArrayList, "dateFormats".
     * 
     * @param value String value to convert
     * @return java.util.Date
     * @throws SystemException if a date format match is not found.
     * @deprecated reference RMT2Date
     */
    public static final java.util.Date stringToDate(String value) throws SystemException {

        methodName = "stringToDate()";
        String format = null;

        if (value == null || value.equalsIgnoreCase("null")) {
            return null;
        }
        if (value.length() <= 0) {
            return null;
        }

        for (int ndx = 0; ndx < dateFormats.size(); ndx++) {
            format = (String) dateFormats.get(ndx);
            try {
                return stringToDate(value, format);
            }
            catch (SystemException ee) {
            }
        }

        // throw exceptoin if date string could not be successfully converted
        throw new SystemException(RMT2SystemExceptionConst.MSG_INVALID_STRING_DATE_VALUE, RMT2SystemExceptionConst.RC_INVALID_STRING_DATE_VALUE);
    }

    /**
     *  Accepts a number as a String and converts it to the java wrapper class, Number.  The number will be converted based on 
     *  the numeric format passed as "_format".  If _format is null, then "_value" is assumed to be in a form acceptable for parsing 
     *  and converting _value without _format.
     *  
     * @param _value String value to convert
     * @param _format Number Format
     * @return Number
     * @throws SystemException if "format" is not an acceptable java numeric format
     * @deprecated reference in RMT2Money
     */
    public static final Number stringToNumber(String _value, String _format) throws SystemException {

        DecimalFormat decFormatter = null;

        methodName = "stringToNumber(String, String)";
        try {
            if (_value == null) {
                return null;
            }
            if (_value.length() <= 0) {
                return null;
            }

            if (_format == null) {
                decFormatter = new DecimalFormat();
            }
            else {
                decFormatter = new DecimalFormat(_format);
            }
            return decFormatter.parse(_value);
        }
        catch (IllegalArgumentException e) {
            throw new SystemException(RMT2SystemExceptionConst.MSG_INVALID_NUMBER_FORMAT + ": " + _format, RMT2SystemExceptionConst.RC_INVALID_NUMBER_FORMAT);
        }
        catch (ParseException e) {
            // throw exceptoin if number string could not be successfully converted
            throw new SystemException(RMT2SystemExceptionConst.MSG_INVALID_STRING_NUMBER_VALUE + ": " + _value, RMT2SystemExceptionConst.RC_INVALID_STRING_NUMBER_VALUE);
        }
    }

    /**
     * Accepts a number as a String and converts it to the java wrapper class, Number.  The format of, "_value", is expected to match 
     * one of the numeric formats in the ArrayList, "numberFormats".
     * 
     * @param _value String value to convert
     * @return Number
     * @throws SystemException if a number format match is not found
     * @deprecated reference in RMT2Money
     */
    public static final Number stringToNumber(String _value) throws SystemException {

        methodName = "stringToNumber";

        try {
            if (_value == null) {
                return null;
            }
            if (_value.length() <= 0) {
                return null;
            }
            DecimalFormat decFormatter = new DecimalFormat();
            return decFormatter.parse(_value);
        }
        catch (ParseException e) {
            String format = null;
            for (int ndx = 0; ndx < numberFormats.size(); ndx++) {
                format = (String) numberFormats.get(ndx);
                try {
                    return stringToNumber(_value, format);
                }
                catch (SystemException e2) {
                }
            }

            // throw exceptoin if number string could not be successfully converted
            throw new SystemException(RMT2SystemExceptionConst.MSG_INVALID_STRING_NUMBER_VALUE + ": " + _value, RMT2SystemExceptionConst.RC_INVALID_STRING_NUMBER_VALUE);
        }
    }

    /**
     * Accepts a java.util.Date object and converts it to a String in the format of "_format".  If _format is null, then _value is passed back
     * to the caller as is without any converting.
     * 
     * @param _value Date value to be converted.
     * @param _format The date format to be used for the conversion.
     * @return A String representation of _value.
     * @throws SystemException If _format is invalid or an unacceptable date format.
     * @deprecated reference RMT2Date
     */
    public static final String formatDate(java.util.Date _value, String _format) throws SystemException {

        methodName = "formatDate";
        if (_value == null) {
            return null;
        }
        if (_format == null) {
            return _value.toString();
        }

        SimpleDateFormat dateFormatter = new SimpleDateFormat(_format);
        return dateFormatter.format(_value);
    }

    /**
     * Accepts a Number object and converts it to a String in the format of "_format".  If _format is null, then _value is passed back
     * to the caller as is without any converting.
     * 
     * @param _value The number for convert.
     * @param _format The format to apply to the conversion.
     * @return The String representation of _value.
     * @throws SystemException  If _format is invalid or an unacceptable date format.
     * @deprecated reference in RMT2Money
     */
    public static final String formatNumber(Number _value, String _format) throws SystemException {
        methodName = "formatNumber";
        if (_value == null) {
            return null;
        }
        if (_format == null) {
            return _value.toString();
        }

        try {
            DecimalFormat decFormatter = new DecimalFormat(_format);
            return decFormatter.format(_value.doubleValue());
        }
        catch (IllegalArgumentException e) {
            throw new SystemException(RMT2SystemExceptionConst.MSG_INVALID_DATE_FORMAT + ": " + _format, RMT2SystemExceptionConst.RC_INVALID_DATE_FORMAT);
        }
    }

    /**
     * Accepts a String argument that is in the format of <value1_value2_valuex>,  
     * word capitalizes each portion of the String that is separated by an 
     * underscore character (value1, value2, and valuex) and removes all underscores.    
     * Example: ORDER_DETAIL_TABLE yields OrderDetailTable.  This method is handy 
     * for converting the names of objects described as metadata into bean names that
     * conform to the java bean specification.
     * 
     * @param entityName
     * @return A Rreformatted entity name conforming to the naming convention rules 
     *         of of DataSource view.
     */
    public static final String formatDsName(String entityName) {

        String newValue = "";
        String token = "";

        StringTokenizer str = new StringTokenizer(entityName, "_");
        while (str.hasMoreTokens()) {
            token = str.nextToken();
            newValue += wordCap(token.toLowerCase());
        }
        return newValue;
    }

    /**
     * Combines the date parts of the current timestamp in the form of an String and returns returns the results to the caller.
     * 
     * @param _date The target date to combine its parts.
     * @return An eight-byte string in the format of <yyyymmdd>.  Returns null if _date is invalid.
     * @deprecated reference RMT2Date
     */
    public static final String combineDateParts(java.util.Date _date) {
        String result = null;
        String temp = null;
        int datePart = 0;
        if (_date == null) {
            return null;
        }

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(_date);
        datePart = gc.get(Calendar.YEAR);
        result = Integer.toString(datePart);

        datePart = gc.get(Calendar.MONTH);
        temp = Integer.toString(datePart);
        if (temp.length() == 1) {
            temp = "0" + temp;
        }
        result += temp;

        datePart = gc.get(Calendar.DAY_OF_MONTH);
        temp = Integer.toString(datePart);
        if (temp.length() == 1) {
            temp = "0" + temp;
        }
        result += temp;

        return result;
    }

    /**
     * Capitalizes the first character of a string variable and returns the capitalized string to the caller.
     * 
     * @param value The source String that is to be converted.
     * @return The results of the conversion as a String.
     * @deprecated reference RMT2String
     */
    public static final String wordCap(String value) {

        StringBuffer capValue = new StringBuffer(100);

        for (int ndx = 0; ndx < value.length(); ndx++) {
            if (ndx == 0) {
                Character ch = new Character(value.charAt(ndx));
                capValue.append(ch.toString().toUpperCase());
            }
            else {
                Character ch = new Character(value.charAt(ndx));
                capValue.append(ch.toString());
            }
        }
        return capValue.toString();
    }

    /**
     * Builds a timestamp of message of the chronology of the Session when the session has been terminated.
     * 
     * @param _sessionBean The RMT2SessionBean object.
     * @return A string indicating the results of the logout process which useful in outputing to the console.
     */
    public static final String getLogoutMessage(RMT2SessionBean _sessionBean) {

        long create = 0;
        long current = 0;
        double elapsed = 0;
        String msg = null;
        boolean sessionTimedout = false;

        try {
            if (_sessionBean == null) {
                throw new IllegalStateException("");
            }
            create = _sessionBean.getSessionCreateTime();
            Date createDate = new Date(create);
            String createDateStr = formatDate(createDate, "yyyy/MM/dd HH:mm:ss:SSS");
            msg = "Session Create Date: " + createDateStr + "\n";
        }
        catch (SystemException e) {
            msg = "";
        }
        catch (IllegalStateException e) {
            sessionTimedout = true;
            msg = "Session timed out!\n";
        }

        current = System.currentTimeMillis();
        elapsed = (current - create) / 1000.0; // Compute time in seconds

        Date currentDate = new Date(current);
        String currentDateStr = null;

        try {
            currentDateStr = formatDate(currentDate, "yyyy/MM/dd HH:mm:ss:SSS");
        }
        catch (SystemException e) {
            msg = "";
        }

        msg += "Session Logout Date: " + currentDateStr + "\n";
        if (!sessionTimedout) {
            msg += "Session Elapsed Time: " + elapsed + " seconds";
        }

        return msg;

    }

    /**
     * Obtains the actual dates of a given week from Sunday to Saturday (7 days) using the current week. 
     * 
     * @return An array of java.util.Date objects, seven to be exact, ordered from Sunday to Saturday.
     * @throws SystemException
     * @deprecated reference RMT2Date
     */
    public static final Date[] getWeekDates() throws SystemException {
        return RMT2Utility.getWeekDates(new java.util.Date(), 0);
    }

    /**
     * Obtains the actual dates of a given week from Sunday to Saturday (7 days).   The target week is determined based 
     * on the date _anchor is set.
     * 
     * @param _anchor The date used to target a particular week.
     * @return An array of java.util.Date objects, seven to be exact, ordered from Sunday to Saturday.
     * @throws SystemException
     * @deprecated reference RMT2Date
     */
    public static final Date[] getWeekDates(Date _anchor) throws SystemException {
        return RMT2Utility.getWeekDates(_anchor, 0);
    }

    /**
     * Obtains the actual dates of a week which _anchor determines the week targeted.   _mode instructs 
     * this method to gather a subset of dates before or after _anchor or gather all the dates of the targeted 
     * week.  The target week is determined based on the date _anchor is set.
     *   
     * @param _anchor The date used to target a particular week.
     * @param _mode Indicates the range of dates to obtain for the target week.  0 = all dates, 1 = all dates after _anchor, and -1 all date before _anchor.
     * @return An array of java.util.Date objects.
     * @throws SystemException if _anchor is invalid.
     * @deprecated reference RMT2Date
     */
    public static final Date[] getWeekDates(Date _anchor, int _mode) throws SystemException {
        final int MODE_ALL = 0;
        final int MODE_PRIOR = -1;
        final int MODE_SUBSEQUENT = 1;
        int anchorDow = 0;
        int priorDowCount = 0;
        int subsequentDowCount = 0;
        int count = 0;
        int dowIncr = 0;
        int dateCount = 0;
        Calendar anchorDate = Calendar.getInstance();

        //  Convert anchor date to Calendar type
        if (_anchor == null) {
            throw new SystemException("Anchor date is invalid for method getDayOfWeekDates");
        }

        // Do initializations
        anchorDate.setTime(_anchor);
        anchorDow = anchorDate.get(Calendar.DAY_OF_WEEK);
        priorDowCount = anchorDow - RMT2Utility.DOW_FIRST;
        subsequentDowCount = RMT2Utility.DOW_LAST - anchorDow;

        switch (_mode) {
        case MODE_ALL:
            dateCount = RMT2Utility.DOW_TOTAL;
            break;
        case MODE_PRIOR:
            dateCount = priorDowCount;
            break;
        case MODE_SUBSEQUENT:
            dateCount = subsequentDowCount;
            break;
        }

        Date dates[] = new Date[dateCount];

        // Get dates prior to anchor day of the week
        if (_mode == MODE_ALL || _mode == MODE_PRIOR) {
            dowIncr = priorDowCount * -1;
            for (count = 0; count < priorDowCount; count++) {
                if (count > 0) {
                    dowIncr = 1;
                }
                anchorDate.add(Calendar.DATE, dowIncr);
                dates[count] = anchorDate.getTime();
            }
        }

        // Add anchor date to list of dates
        if (_mode == MODE_ALL) {
            dates[count++] = _anchor;
            anchorDate.add(Calendar.DATE, 1);
        }

        // Get dates subsequent to anchor day of the week
        if (_mode == MODE_ALL || _mode == MODE_SUBSEQUENT) {
            dowIncr = 1;
            for (int ndx = 0; ndx < subsequentDowCount; ndx++) {
                anchorDate.add(Calendar.DATE, dowIncr);
                dates[count++] = anchorDate.getTime();
            }
        }
        return dates;
    }

    /**
     * Calls doRowTimeStamp(sessionBean.getLoginId(), _dso, isNew) to construct a timestamp value and assign that value to 
     * a Datasource object.
     * 
     * @param _request The java request object.
     * @param _dso {@link RMT2DataSourceApi) object.
     * @param isNew true=create session, false=session exist.
     * @throws SystemException
     * @throws NotFoundException
     * @throws DatabaseException
     * @deprecated reference RMT2Date
     */
    public static final void doRowTimeStamp(HttpServletRequest _request, DataSourceApi _dso, boolean isNew) throws SystemException, NotFoundException, DatabaseException {

        HttpSession session = _request.getSession();

        // Get User's Session Bean
        RMT2SessionBean sessionBean = (RMT2SessionBean) session.getAttribute(RMT2ServletConst.SESSION_BEAN);
        doRowTimeStamp(sessionBean.getLoginId(), _dso, isNew);
    }

    /**
     * Constructs a timestamp value and assigns that value to a Datasource object.  Columns effected are base on whether the 
     * dso record already exist (DateUpdated and Userid) or if the record is new (DateCreated, DateUpdated, and Userid).
     * 
     * @param _userId The user id which timestamp is to be associated.
     * @param _dso {@link RMT2DataSourceApi) object.
     * @param isNew true=create session, false=session exist.
     * @throws SystemException if date format is invalid, or a problem existing setting column values of _dso.
     * @throws NotFoundException When invalid column name references are made towards _dso.
     * @throws DatabaseException An error occurred accessing _dso.
     * @deprecated reference RMT2Date
     */
    public static final void doRowTimeStamp(String _userId, DataSourceApi _dso, boolean isNew) throws SystemException, NotFoundException, DatabaseException {

        java.util.Date currentDate = new java.util.Date();

        if (isNew) {
            _dso.setColumnValue("DateCreated", formatDate(currentDate, "MM-dd-yyyy H:m:s"));
        }
        _dso.setColumnValue("DateUpdated", formatDate(currentDate, "MM-dd-yyyy H:m:s"));
        _dso.setColumnValue("UserId", _userId);
    }

    /**
     * Gets the current user's session.
     * 
     * @param _request
     * @return User's HttpSession object
     */
    public static final HttpSession getSession(HttpServletRequest _request) {
        // Get new session
        HttpSession session = _request.getSession();
        return session;
    }

    /**
     * Copies the request's parameter and attribute data into a Properties collection.   
     *  
     * @return Properties instance or null if <i>request</i> is null.
     */
    public static Properties getRequestData(Request request) {
        return RMT2Utility.copyRequestData(request, null);
    }

    /**
     * Copies the parameters and attributes key/value pairs in <i>request</i> to <i>prop</i>.
     * 
     * @param request The source HttpServletRequest
     * @param prop 
     *           A Properties instance which serves as the destination.  If null, a new 
     *           Properties instance is created.
     * @return Properties instance or null if <i>request</i> is null.
     */
    public static Properties getRequestData(Request request, Properties prop) {
        return RMT2Utility.copyRequestData(request, prop);
    }

    /**
     * Copies the parameters and attributes key/value pairs in <i>request</i> to <i>prop</i>.   
     * The combined keys between the request's parameters and attributes must be unique 
     * in the Properties object which the parameters will take precedence over the attributes. 
     * All parameter values are expected to be of type String.
     * 
     * @param request The source HttpServletRequest
     * @param prop 
     *           A Properties instance which serves as the destination.  If null, a new 
     *           Properties instance is created.
     * @return Properties instance or null if <i>request</i> is null.
     */
    private static Properties copyRequestData(Request request, Properties prop) {
        if (request == null) {
            return null;
        }
        if (prop == null) {
            prop = new Properties();
        }

        // Add available request attributes to the Properties object.
        Enumeration iter = request.getParameterNames();
        String key = null;
        String value = null;
        while (iter.hasMoreElements()) {
            key = (String) iter.nextElement();
            if (!prop.containsKey(key)) {
                value = request.getParameter(key);
                prop.setProperty(key, value);
            }
        }

        // Add available request attributes to the Properties object.
        iter = request.getAttributeNames();
        while (iter.hasMoreElements()) {
            key = (String) iter.nextElement();
            // Do not add if parm name already exist in target properties
            if (!prop.containsKey(key)) {
                Object obj = request.getAttribute(key);
                if (obj instanceof String || obj instanceof Integer || obj instanceof Double || obj instanceof Boolean || obj instanceof Date || obj instanceof Character) {
                    value = obj.toString();
                    prop.setProperty(key, value);
                }
            }
        }
        return prop;
    }

    /**
     * Creates a UserTimestamp object using the user's request.   The request should 
     * contain the user's {@link SessionBean}.
     * 
     * @param _request The HttpServletRequest object linked to the user's session.
     * @return {@link UserTimestamp} conatining the data needed to satisfy a user timestamp.
     * @throws SystemException If the session is invalid or if the user has not logged on to the system.
     * @deprecated reference RMT2Date
     */
    public static final UserTimestamp getUserTimeStamp(HttpServletRequest _request) throws SystemException {
        HttpSession session = null;
        RMT2SessionBean sessionBean = null;

        session = _request.getSession();
        if (session == null) {
            throw new SystemException("Session object is not availble for current user");
        }
        sessionBean = (RMT2SessionBean) session.getAttribute(RMT2ServletConst.SESSION_BEAN);
        if (sessionBean == null) {
            throw new SystemException("Current user's Session Bean object has not been setup...User may not be logged into the system.");
        }
        return RMT2Utility.getUserTimeStamp(sessionBean.getLoginId());
    }

    /**
     * Creates a UserTimestamp object using the user's login id.
     * 
     * @param loginId The user's login id.
     * @return {@link UserTimestamp} conatining the data needed to satisfy a user timestamp.
     * @throws SystemException If the login id is invalid
     * @deprecated reference RMT2Date
     */
    public static final UserTimestamp getUserTimeStamp(String loginId) throws SystemException {
        if (loginId == null) {
            throw new SystemException("Failed to obtain UserTimeStamp object, because login id is invalid");
        }
        java.util.Date currentDate = new java.util.Date();
        UserTimestamp ut = UserTimestamp.getInstance();
        ut.setDateCreated(currentDate);
        ut.setLoginId(loginId);
        return ut;
    }

    /**
     * Obtains the timestamp of the current date in milliseconds.
     * 
     * @return long.
     * @deprecated reference RMT2Date
     */
    public static final long getTimeStamp() {
        // Obtain timeStamp as a long
        long utilDate = (new java.util.Date()).getTime();
        return utilDate;
    }

    /**
     * Concatenates the input values "datePart" and "timePart" into a java.util.Date value and return the results to the caller.    Both 
     * parts of the datetime value are validated.
     * 
     * @param datePart The date portion of a timestamp.
     * @param timePart The time portion of a timestamp.
     * @return java.util.Date
     * @throws BusinessException When validation(s) fail.
     * @throws SystemException When the derived timestamp value is unable to be converted to a Date object.
     * @deprecated reference RMT2Date
     */
    public static final java.util.Date combineDateTime(String datePart, String timePart) throws SystemException {
        String dtValue;

        //  Determine if the date part was passed to this method
        if (datePart == null || datePart.length() <= 0) {
            throw new SystemException("Date is invalid.   Date part cannot be null or balnk");
        }

        //  Determine if the date part was passed to this method
        if (datePart == null || datePart.length() <= 0) {
            throw new SystemException("Date is invalid.   TIme part cannot be null or balnk");
        }

        // combine date part with time part to yield a complete timestamp
        dtValue = datePart + " " + timePart;
        return RMT2Utility.stringToDate(dtValue);

    }

    /**
     * Returns the difference in time between two dates as an ElapseTime object.   Caller is responsible for deciphering the contents of 
     * the return value.   Example: 5,600,000 milliseconds should return 1 hour, 33 minutes and 20 seconds.
     * 
     * @param _startDate The beginning of the date range.
     * @param _endDate The ending of the date range.
     * @return {@link ElapsedTime) object.
     * @deprecated reference RMT2Date
     */
    public static final ElapsedTime getDateDiff(Date _startDate, Date _endDate) {
        ElapsedTime et = null;
        long msInHr = 3600000; // Total number of milliseconds in 1 hour
        long msInMin = 60000; // Total number of milliseconds in 1 minute
        long msInSec = 1000; //  Total number of milliseconds in 1 second  
        double dateDiffMs = 0; //  Elapsed time in milliseconds
        BigDecimal totalTime;
        double timeRemain = 0;
        int temp;
        int quotientScale = 5;

        try {
            et = new ElapsedTime();
        }
        catch (SystemException e) {
        }

        dateDiffMs = getDateDiff(_startDate, _endDate, "ms");

        if (dateDiffMs == -1) {
            return null;
        }
        // Starting time
        totalTime = new BigDecimal(dateDiffMs);

        // Hours
        if (totalTime.intValue() >= msInHr) {
            // Get left over minutes from hour calculation
            timeRemain = totalTime.intValue() % msInHr;

            // Hours
            totalTime = totalTime.divide(new BigDecimal(msInHr), quotientScale, BigDecimal.ROUND_DOWN);
            temp = totalTime.intValue();
            et.setHours(temp);

            // Reset total time
            totalTime = new BigDecimal(timeRemain);
        }

        // Minutes
        if (totalTime.intValue() >= msInMin) {
            // Get left over seconds from minute calculaion
            timeRemain = totalTime.intValue() % msInMin;

            //  Calculate total minutes
            totalTime = totalTime.divide(new BigDecimal(msInMin), quotientScale, BigDecimal.ROUND_DOWN);
            temp = totalTime.intValue();
            et.setMins(temp);

            // Reset total time
            totalTime = new BigDecimal(timeRemain);
        }

        // Seconds
        if (totalTime.intValue() >= msInSec) {
            // Get left over milliseconds from second calculation
            timeRemain = totalTime.intValue() % msInSec;

            //  Calculate total seconds
            totalTime = totalTime.divide(new BigDecimal(msInSec), quotientScale, BigDecimal.ROUND_DOWN);
            temp = totalTime.intValue();
            et.setSecs(temp);

            // Reset total time
            totalTime = new BigDecimal(timeRemain);
        }

        //  Set Milliseconds to remaining total time
        et.setMs(totalTime.intValue());

        return et;
    }

    /**
     * Returns the difference in time between two dates.  The different  types time intervals returned can be:
     * <p>
     * <table border="1">
     *   <tr>
     *      <th align="left">Type</th>
     *      <th align="left">Input Argument Value</th>
     *   </tr>
     *   <tr>
     *      <td>milliseconds </td>
     *      <td>ms</td>
     *   </tr>
     *   <tr>
     *      <td>seconds </td>
     *      <td>sec</td>
     *   </tr>
     *   <tr>
     *      <td>minutes </td>
     *      <td>min</td>
     *   </tr>
     *   <tr>
     *      <td>hours </td>
     *      <td>hr</td>
     *   </tr>
     *   <tr>
     *      <td>days </td>
     *      <td>d</td>
     *   </tr>
     *  </table>
     *  
     * @param _startDate The beginning of the date range.
     * @param _endDate The ending of the date range.
     * @param _intervalType The time interval type.
     * @return The time interval
     * @deprecated reference RMT2Date
     */
    public static final double getDateDiff(Date _startDate, Date _endDate, String _intervalType) {
        long endDate_ms;
        long startDate_ms;
        double dateDiff;
        double dateDiffSec;
        double dateDiffMin;
        double dateDiffHr;
        //    int  hours;
        //    int mins;
        //    float minsFraction;
        //    BigDecimal temp;

        // Get start and end date times in milliseconds
        endDate_ms = _endDate.getTime();
        startDate_ms = _startDate.getTime();
        dateDiff = endDate_ms - startDate_ms; // get total elapsed time in milliseconds
        dateDiffSec = dateDiff / 1000; // get total elapsed time in seconds
        dateDiffMin = dateDiffSec / 60; // get total elapsed time in minutes
        dateDiffHr = dateDiffMin / 60; // get  get total elapsed time in hours

        if (_intervalType == null || _intervalType.equalsIgnoreCase("ms")) {
            return dateDiff;
        }
        if (_intervalType.equalsIgnoreCase("sec")) {
            return dateDiffSec;
        }
        if (_intervalType.equalsIgnoreCase("min")) {
            return dateDiffMin;
        }
        if (_intervalType.equalsIgnoreCase("hr")) {
            return dateDiffHr;
        }

        return -1;
        /*
         
         //  Parse the right and left sides of the deciimal point of dateDiff
         hours = new Float(dateDiffHr).intValue();   // Extract hours
         minsFraction = dateDiffHr - hours;          // Extract minutes fraction
         temp = new BigDecimal(minsFraction);

         // Convert fractional portion to actual minutes
         temp = temp.divide(new BigDecimal(1), 2, BigDecimal.ROUND_DOWN);
         minsFraction = temp.floatValue();                 // Get Fractional notation of minutes
         mins = new Float(minsFraction * 60).intValue();   // Calulate actual minutes

         // Determine what quarter of an hour mins represents such as
         // 15 minutes after the hour, 30 minutes after the hour, or
         // 45 minutes after the hour.  Afterwards, concatenate the hours
         // and minutes to return the total time.
         if (mins >= 0 & mins < 15) {
         return hours + .0;
         }
         if (mins >= 15 & mins < 30) {
         return hours + .25;
         }
         if (mins >= 30 & mins < 45) {
         return hours + .5;
         }
         if (mins >= 45 & mins <= 59) {
         return hours + .75;
         }

         return 0;
         */
    }

    /**
     * Formats a string to conform to the method naming conventions of 
     * the Javabean specification by converting the first character of 
     * the string to upper case.
     * 
     * @param entityName Source to be converted.
     * @return The method name.
     */
    public static final String getBeanMethodName(String entityName) {

        StringBuffer propName = new StringBuffer(50);

        propName.append(entityName.substring(0, 1).toUpperCase());
        propName.append(entityName.substring(1));

        return propName.toString();
    }

    /**
     * Extracts the class name from the fully qualified package name of _bean.
     *  
     * @param _bean The object to retreive the root class name.
     * @return The name of the bean, null when _bean is invalid or an empty 
     *         string ("") when package name of _bean could not be parsed.
     */
    public static final String getBeanClassName(Object _bean) {
        String temp = null;
        String beanName = null;
        List<String> list = null;

        if (_bean == null) {
            return null;
        }

        temp = _bean.getClass().getName();
        list = RMT2String.getTokens(temp, ".");

        if (list == null || list.size() <= 0) {
            return "";
        }
        beanName = (String) list.get(list.size() - 1);
        return beanName;
    }

    /**
     * Searches the client's workstation for the MIME Type of a file represented by "_fileName".   Returns the MIME type of the file.
     * 
     * @param _fileName The name of the file which MIME type is to be found.
     * @return The MIME type name.
     * @deprecated reference in RMT2File
     */
    public static final String getMimeType(String _fileName) {
        MimetypesFileTypeMap typMap = new MimetypesFileTypeMap();
        String mimeType = typMap.getContentType(_fileName);
        return mimeType;
    }

    /**
     * Verifies that _pathname is a valid Directory or File.
     * 
     * @param _pathName The file path.
     * @return 1=File Exist, -1=File does not exsit, -2=File inaccessible, and 0= Argument is an empty String or null.
     * @deprecated reference in RMT2File
     */
    public static int verifyFile(String _pathName) {
        File path;

        try {
            path = new File(_pathName);
        }
        catch (NullPointerException e) {
            return FILE_IO_NULL;
        }

        // Validate the existence of path
        try {
            if (!path.exists()) {
                return FILE_IO_NOTEXIST;
            }
            return FILE_IO_EXIST;
        }
        catch (Exception e) {
            return FILE_IO_INACCESSIBLE;
        }
    }

    /**
     * Verifies that _file is a valid File.
     * 
     * @param _pathName The file path.
     * @return 1=File Exist, -1=File does not exsit, -2=File inaccessible, -3=Not a File
     * @deprecated reference in RMT2File
     */
    public static int verifyFile(File _file) {
        // Validate the existence of path
        try {
            if (!_file.exists()) {
                return FILE_IO_NOTEXIST;
            }
            if (!_file.isFile()) {
                return RMT2Utility.FILE_IO_NOT_FILE;
            }
            return FILE_IO_EXIST;
        }
        catch (Exception e) {
            return FILE_IO_INACCESSIBLE;
        }
    }

    /**
     * Verifies that _file is a valid Directory.
     * 
     * @param _pathName The file path.
     * @return 1=File Exist, -1=File does not exsit, -2=File inaccessible, -4=Not a File.
     * @deprecated reference in RMT2File
     */
    public static int verifyDirectory(File _file) {
        // Validate the existence of path
        try {
            if (!_file.exists()) {
                return FILE_IO_NOTEXIST;
            }
            if (!_file.isDirectory()) {
                return RMT2Utility.FILE_IO_NOT_DIR;
            }
            return FILE_IO_EXIST;
        }
        catch (Exception e) {
            return FILE_IO_INACCESSIBLE;
        }
    }

    /**
     * Accesses the contents of a plain text file and returns the contents as a String to the caller.
     * 
     * @param _filename The file name.
     * @return The contents of the file as a String.
     * @throws SystemException if _filename is null, does not exist, or is inaccessible.
     * @deprecated reference in RMT2File.getTextFileContents(String)
     */
    public static String getTextFileContents(String _filename) throws SystemException {
        try {
            StringBuffer fileContent = new StringBuffer(100);
            String temp = null;

            switch (RMT2Utility.verifyFile(_filename)) {
            case FILE_IO_NULL:
                throw new SystemException("[RMT2Utility.GetTextFileContents] Argument [_filename] is null or empty");
            case FILE_IO_NOTEXIST:
                throw new SystemException("[RMT2Utility.GetTextFileContents] File does not exist: " + _filename);
            case FILE_IO_INACCESSIBLE:
                throw new SystemException("[RMT2Utility.GetTextFileContents] File is inaccessible: " + _filename);
            }

            //  At this point we have a file that exist and is accessible.
            FileReader fr = new FileReader(_filename);
            BufferedReader buf = new BufferedReader(fr);

            while ((temp = buf.readLine()) != null) {
                fileContent.append(temp);
            }
            return fileContent.toString();
        }
        catch (FileNotFoundException e) {
            throw new SystemException("[RMT2Utility.GetTextFileContents] File Not Found IO Exception for " + _filename);
        }
        catch (IOException e) {
            throw new SystemException("[RMT2Utility.GetTextFileContents] File IO Exception for " + _filename);
        }
    }

    /**
     * Obtains the web appliation context.
     * 
     * @param req Servlet request object.
     * @return String as the web application context.
     */
    public static String getWebAppContext(Object req) {
        if (req == null) {
            return null;
        }

        if (req instanceof ServletRequest) {
            return ((HttpServletRequest) req).getContextPath();
        }
        if (req instanceof Request) {
            return ((Request) req).getContextPath();
        }

        return null;

    }

    /**
     * Obtains the entire URI up to the servlet context.   This includes the scheme, server name, the 
     * server port number, and the servlet context name.
     * 
     * @param obj
     *          An instance of ServletRequest, HttpServletRequest, or {@link com.controller.Request Request}
     * @return
     *        The URI from the scheme to the servlet context.
     */
    public static String getWebAppAbsoluteContext(Object obj) {
        if (obj == null) {
            return null;
        }

        String uriPrefix = null;
        if (obj instanceof ServletRequest) {
            ServletRequest req = (ServletRequest) obj;
            uriPrefix = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort();
            if (obj instanceof HttpServletRequest) {
                HttpServletRequest req2 = (HttpServletRequest) obj;
                uriPrefix += req2.getContextPath();
            }
            return uriPrefix;
        }
        if (obj instanceof Request) {
            Request req = (Request) obj;
            uriPrefix = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
            return uriPrefix;
        }

        return null;

    }

    /**
     * Loads a property file that can be found in the classpath.
     * 
     * @param _fileName The name of the resourc bundle to load.  The file name cannot be null.
     * @return The resource bundle
     * @throws SystemException When _fileName cannot be found. a problem exist casting _fileName
     *         into a ResourceBundle, or the _fileName is null.
     * @deprecated reference in RMT2File         
     */
    public static ResourceBundle loadAppConfigProperties(String _fileName) throws SystemException {
        ResourceBundle bundle;

        try {
            bundle = ResourceBundle.getBundle(_fileName);
            return bundle;
        }
        catch (MissingResourceException e) {
            throw new SystemException("The properties file: " + _fileName + " could not be found.");
        }
        catch (ClassCastException e) {
            throw new SystemException("The properties file: " + _fileName + " could not be cast.");
        }
        catch (NullPointerException e) {
            throw new SystemException("The resource bundle name is null.");
        }
    }

    /**
     * Creates a Properties object from a Properties file in the Classpath.
     * 
     * @param _propFile Properties file name.
     * @return Properties object.
     * @throws SystemException
     * @deprecated reference in RMT2File
     */
    public static Properties loadPropertiesObject(String _propFile) throws SystemException {
        File file = null;
        FileInputStream fis = null;
        Properties properties = null;
        int fileStatus = 0;

        try {
            file = new File(_propFile);
            fileStatus = RMT2Utility.verifyFile(file);
            if (fileStatus != RMT2Utility.FILE_IO_EXIST) {
                throw new SystemException(_propFile + " was not loaded.   Error code: " + fileStatus);
            }
        }
        catch (NullPointerException e) {
            return null;
        }

        try {
            fis = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {
            throw new SystemException(_propFile + " is not found");
        }

        properties = new Properties();
        try {
            properties.load(fis);
            return properties;
        }
        catch (IOException e) {
            throw new SystemException("an error occurred when reading from the input stream for file, " + _propFile);
        }
    }

    /**
     * Obtains the property value using _keyCode from within the selected ResourceBundlee (_bundleName)
     * 
     * @param _bundleName The name of the ResourceBundle to query.
     * @param _keyCode The key of the desired value.
     * @return The value of the key/value pair
     * @throws SystemException
     * <p>
     * <ul>
     *   <li>The _bundleName is null</li>
     *   <li>The resource bundle cannot  be found in the classpath</li>
     *   <li>_keyCode is passed a null value</li>
     *   <li>Key (_keyCode) is not found in the resource bundle</li>
     *   <li>The value returned by _keyCode does not equate to be a String</li>
     * </ul>
     * 
     * @deprecated reference in RMT2File
     */
    public static String getPropertyValue(String _bundleName, String _keyCode) throws SystemException {
        ResourceBundle rb = null;
        String value = null;

        // Let's obtain the actual resource bundle file
        try {
            rb = ResourceBundle.getBundle(_bundleName);
        }
        catch (NullPointerException e) {
            throw new SystemException("Resource Bundle value is null ");
        }
        catch (MissingResourceException e) {
            throw new SystemException("Resource Bundle, " + _bundleName + ", cannot be found in classpath ");
        }

        // Let's get the property value
        try {
            value = rb.getString(_keyCode);
        }
        catch (NullPointerException e) {
            throw new SystemException("Key value was passed as null ");
        }
        catch (MissingResourceException e) {
            throw new SystemException("No value for key code, " + _keyCode + ", of Resource Bundle file, " + _bundleName + ", could not be found");
        }
        catch (ClassCastException e) {
            throw new SystemException("Value returned for key code, " + _keyCode + ", of Resource Bundle file, " + _bundleName + ", is not a string");
        }
        return value;
    }

    /**
     * Performs the same functionality as @see RMT2Utility.getgetPropertyValue(String, String) without throwing any exceptoins.
     * 
     * @param _bundleName
     * @param _keyCode
     * @return The value of key or null if key could not be found.
     * @deprecated reference in RMT2File
     */
    public static String getPropertyValueBuffered(String _bundleName, String _keyCode) {
        // Get the value of _keycode
        try {
            return RMT2Utility.getPropertyValue(_bundleName, _keyCode);
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Attempts to instantiate a class using _className.
     * 
     * @param _className The name of the class to instantiate.
     * @return An object of type _className.
     * @throws SystemException if an initializer error occurs, or linkage fails, or the class cannot be found, or class is 
     * illeaglly accessed, or a general instantiation problem.
     */
    public static Object getClassInstance(String _className) throws SystemException {
        String msg = "Error instantiating class:" + _className + ".  ";
        Object obj = null;

        try {
            obj = Class.forName(_className).newInstance();
            return obj;
        }
        catch (ExceptionInInitializerError e) {
            msg += " The initialization provoked by this method failed: ";
            throw new SystemException(msg);
        }
        catch (LinkageError e) {
            msg += " Linkage failed";
            throw new SystemException(msg);
        }
        catch (ClassNotFoundException e) {
            msg += " The class could not be found";
            throw new SystemException(msg);
        }
        catch (IllegalAccessException e) {
            msg += " The class or its nullary constructor is not accessible";
            throw new SystemException(msg);
        }
        catch (InstantiationException e) {
            msg += "Failure due to either 1) class represents an abstract class, an interface, an array class, a primitive type, or void;";
            msg += " 2) the class has no nullary constructor; or  3) for some other reason";
            throw new SystemException(msg);
        }
    }

    /**
     * Reads a disk file and return its contents as String.
     * 
     * @param filename The input file.
     * @return File contents as a String
     * @throws NotFoundException If filename does not exist in the file system
     * @throws SystemException General IO errors.
     * @deprecated reference in RMT2File
     */
    public static String inputFile(String filename) throws NotFoundException, SystemException {
        File sourceFile = new File(filename);
        if (!sourceFile.exists()) {
            throw new NotFoundException("Input File not found: " + filename);
        }

        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        int fileSize = new Long(sourceFile.length()).intValue();
        try {
            fis = new FileInputStream(sourceFile);
            baos = new ByteArrayOutputStream();
            byte buffer[];
            try {
                buffer = new byte[fileSize];
            }
            catch (OutOfMemoryError e) {
                throw new SystemException("Out of Memory error occured when attempting to allocate memory buffer for file input: " + filename);
            }
            int bytesRead = fis.read(buffer);
            while (bytesRead != -1) {
                baos.write(buffer);
                bytesRead = fis.read(buffer);
            }
            return baos.toString();
        }
        catch (IOException e) {
            throw new SystemException("IO Exception: " + e.getMessage());
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException e) {
                    ;
                }
            }
        }
    }

    /**
     * Writes data to a disk file.
     * 
     * @param data The data to be written to disk
     * @param filename The path name to where data is stored to disk.
     * @throws SystemException I fileDestination is not found or general IO error.
     * @deprecated reference in RMT2File
     */
    public static void outputFile(String data, String filename) throws SystemException {
        File destFile = new File(filename);
        if (destFile.isDirectory()) {
            throw new SystemException("Output path must be a file instead of a directory");
        }

        try {
            byte byteResulsts[] = data.getBytes();
            // Use an FileOutputStream to write data to disk.
            FileOutputStream fos = new FileOutputStream(destFile);
            fos.write(byteResulsts);
            fos.flush();
            fos.close();
        }
        catch (FileNotFoundException e) {
            throw new SystemException("File was not found.  Check whether or not the directory structure exists");
        }
        catch (NotSerializableException e) {
            throw new SystemException("Object is not serializable...Ensure that object implements java.io.Serializable interface");
        }
        catch (IOException e) {
            throw new SystemException("General file IO error occurred");
        }
    }

    /**
     * Serializes a text data to disk.
     * 
     * @param data The String data that is targeted for serialization.
     * @param filename The name of the file that will contain the serialized data.  
     *         The path will be determined using the serial_* hash values located in 
     *         the SystemParms properties file.
     * @throws SystemException
     * @deprecated reference in RMT2File
     */
    public static void serializeText(String data, String filename) throws SystemException {
        String msg = "File serialization failed for file " + filename + ": ";
        String filePath = RMT2Utility.getSerialLocation(filename);
        try {
            RMT2Utility.outputFile(data, filePath);
        }
        catch (SystemException e) {
            msg += e.getMessage();
            throw new SystemException(msg);
        }
    }

    /**
     * Serializes a java object.   Be sure that the taget objet implements the java.io.Serializable interface.
     * 
     * @param _obj The object that is targeted for serialization.
     * @param _destination The name of the file that will contain the serialized object.
     * @throws SystemException
     * @deprecated reference in RMT2File
     */
    public static void serializeObject(Object _obj, String _destination) throws SystemException {
        String msg = "File serialization failed for file " + _destination + ": ";
        String filename = RMT2Utility.getSerialLocation(_destination);

        try {
            // Use a FileOutputStream to send data to a file called _obj.
            FileOutputStream f_out = new FileOutputStream(filename);

            // Use an ObjectOutputStream to send object data to the FileOutputStream for writing to disk.
            ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

            // Pass our object to the ObjectOutputStream's writeObject() method to cause it to be written out to disk.
            obj_out.writeObject(_obj);
            obj_out.flush();
            obj_out.close();
        }
        catch (FileNotFoundException e) {
            throw new SystemException(msg + " File was not found");
        }
        catch (NotSerializableException e) {
            throw new SystemException(msg + " Object is not serializable...Ensure that object implements java.io.Serializable interface");
        }
        catch (IOException e) {
            throw new SystemException(msg + " General file IO error occurred");
        }
    }

    /**
     * Restorses a serailized object to some previous state.
     * 
     * @param _source The file that contains the seriali data needed to be restored to an object.
     * @return the restored object 
     * @throws SystemException
     */
    public static Object deSerializeObject(String _source) throws SystemException {
        Object obj = null;
        String msg = "File deserialization failed for file " + _source + ": ";
        String filename = RMT2Utility.getSerialLocation(_source);

        try {
            // Read from disk using FileInputStream.
            FileInputStream f_in = new FileInputStream(filename);

            // Read object using ObjectInputStream.
            ObjectInputStream obj_in = new ObjectInputStream(f_in);

            // Read an object.
            obj = obj_in.readObject();
            return obj;
        }
        catch (FileNotFoundException e) {
            throw new SystemException(msg + " File was not found");
        }
        catch (ClassNotFoundException e) {
            throw new SystemException(msg + " Class was not found");
        }
        catch (IOException e) {
            throw new SystemException(msg + " General file IO error occurred");
        }
    }

    /**
     * Uses the SystemParms.properties file to obtain drive, path, and file extension to be concatenated with 
     * _filename which the end result is the full path where objects are serialized and deserialized.
     * 
     * @param _filename The filename used to write/read serialiazed/deserialized object content.
     * @return The full path used to retreive or store a serialized file.
     * @throws SystemException
     * @deprecated reference in RMT2File
     */
    private static String getSerialLocation(String _filename) throws SystemException {
        String result = null;
        if (_filename == null || _filename.length() <= 0) {
            throw new SystemException("Problem determining the system location for serializing and deserializing objects");
        }

        String drive = RMT2Utility.getPropertyValue("SystemParms", "serial_drive");
        String path = RMT2Utility.getPropertyValue("SystemParms", "serial_path");
        //String ext = RMT2Utility.getPropertyValue("SystemParms", "serial_ext");
        result = drive + path + _filename;
        return result;
    }

    /**
     * Copies a file to another location in the Operating System
     * 
     * @param fromFileName The complete path of the source file.
     * @param toFileName The complete path of the destination file.
     * @throws IOException in the following cases:
     * <ul>
     *   <li>the source file does not exist.</li>
     *   <li>the source directory does not allow copying.</li>
     *   <li>the source file is unreadable.</li>
     *   <li>the destination file is not writeable.</li>
     *   <li>problem occurred attempting to overwrite the destination file.</li>
     *   <li>the destination directory does not exist.</li>
     *   <li>the destination is not a directory.</li>
     *   <li>the destination directory is not writeable.</li>
     *   <li>when the source file does not exist.</li>
     * </ul>
     */
    public static void copyFile(String fromFileName, String toFileName) throws IOException {
        File fromFile = new File(fromFileName);
        File toFile = new File(toFileName);

        if (!fromFile.exists())
            throw new IOException("FileCopy: " + "no such source file: " + fromFileName);
        if (!fromFile.isFile())
            throw new IOException("FileCopy: " + "can't copy directory: " + fromFileName);
        if (!fromFile.canRead())
            throw new IOException("FileCopy: " + "source file is unreadable: " + fromFileName);

        if (toFile.isDirectory())
            toFile = new File(toFile, fromFile.getName());

        if (toFile.exists()) {
            if (!toFile.canWrite()) {
                throw new IOException("FileCopy: " + "destination file is unwriteable: " + toFileName);
            }
            System.out.print("Overwrite existing file " + toFile.getName() + "? (Y/N): ");
            System.out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String response = in.readLine();
            if (!response.equals("Y") && !response.equals("y")) {
                throw new IOException("FileCopy: " + "existing file was not overwritten.");
            }
        }
        else {
            String parent = toFile.getParent();
            if (parent == null) {
                parent = System.getProperty("user.dir");
            }
            File dir = new File(parent);
            if (!dir.exists())
                throw new IOException("FileCopy: " + "destination directory doesn't exist: " + parent);
            if (dir.isFile())
                throw new IOException("FileCopy: " + "destination is not a directory: " + parent);
            if (!dir.canWrite())
                throw new IOException("FileCopy: " + "destination directory is unwriteable: " + parent);
        }

        FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream(fromFile);
            to = new FileOutputStream(toFile);
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = from.read(buffer)) != -1) {
                to.write(buffer, 0, bytesRead); // write    
            }
        }
        finally {
            if (from != null)
                try {
                    from.close();
                }
                catch (IOException e) {
                    ;
                }
            if (to != null)
                try {
                    to.close();
                }
                catch (IOException e) {
                    ;
                }
        }
    }

    /**
     * Deletes a file from the file system.  <p>When fileName is representing a directory, the 
     * directory is recursively processed until its contents are deleted.
     * 
     * @param fileName The file name.
     * @return The total number of files deleted.
     */
    public static final int deleteFile(String fileName) {
        int count = 0;
        File file = new File(fileName);
        if (file.isDirectory()) {
            String dirContents[] = file.list();
            for (int ndx = 0; ndx < dirContents.length; ndx++) {
                count += RMT2Utility.deleteFile(dirContents[ndx]);
            }
        }
        if (file.isFile()) {
            if (file.delete()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Obtains the stack trace pertaing to <b>e</b> and converts and concatenates each StackTraceElement into a String.
     * 
     * @param e The Exception object to obtai stack trace from.
     * @return Stack trace as a String.
     */
    public static final String getStackTrace(Throwable e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        String results = baos.toString();
        return results;
    }

    /**
     * Creates a List of messages from the stack trace of a Throwable instance, <b>e</b>.
     * 
     * @param e
     * @return a List of Strings represnting each message in the stack trace.
     */
    public static final List getStackTraceMessages(Throwable e) {
        List<String> errors = new ArrayList<String>();
        StackTraceElement ste[] = e.getStackTrace();
        for (int ndx = 0; ndx < ste.length; ndx++) {
            errors.add(ste[ndx].toString());
        }
        return errors;
    }

    /**
     * Verifies that _obj is a valid primitive wrapper data type.
     * 
     * @param _obj Object to verify
     * @return true data type is one othe primitvie wrappers.   false when incorrect data type.
     */
    public static boolean isWrapperType(Object _obj) {
        return (_obj instanceof Date || _obj instanceof String || _obj instanceof Integer || _obj instanceof Float || _obj instanceof Double || _obj instanceof Number || _obj instanceof Short);
    }

    /**
     * Verifies that _obj is an array
     * 
     * @param _obj Object to verify
     * @return true if object is an array.  Otherwise, false is returned.
     */
    public static boolean isArray(Object _obj) {
        try {
            java.lang.reflect.Array.getLength(_obj);
            return true;
        }
        catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Breaks up the equality statements of the specified selection criteria into a Map of key/value pairs.
     * For example, "AccountNo=9439392" would be added to a Map collection as key/value pairs where AccountNo 
     * would be the key and 9439392 would be the value.  This would apply to all equality statements of the 
     * selection criteria.
     *  
     * @param criteria The selection criteria to parse.
     * @return Map
     */
    public static Map<String, String> convertCriteriaToHash(String criteria) {
        criteria = RMT2String.replaceAll(criteria, "*", " and ");
        List tokens = RMT2String.getTokens(criteria, "*");
        Map<String, String> hash = new HashMap<String, String>();
        if (tokens != null) {
            for (int ndx = 0; ndx < tokens.size(); ndx++) {
                String element = (String) tokens.get(ndx);
                List tokens2 = RMT2Utility.separateCriteriaOperands(element);
                if (tokens2 == null) {
                    continue;
                }
                String key = (String) tokens2.get(0);
                String value = (String) tokens2.get(1);
                hash.put(key.trim(), value.trim());
            }
        }
        return hash;
    }

    /**
     * Parses a SQL predicate clause into two distince halfs separated by the operator.
     * The supported operators are =, like, >, <, <=, >=, <>, is null, and is not null.
     *  
     * @param clause The SQL predicate.
     * @return List of disjuncted predicates elements or null if <i>clause</i> contains 
     *         an operator that is not supported.
     */
    private static List separateCriteriaOperands(String clause) {
        List tokens = null;
        String temp;

        tokens = RMT2String.getTokens(clause, "=");
        if (tokens != null && tokens.size() > 1) {
            return tokens;
        }
        temp = RMT2String.replaceAll(clause, "*", " like ");
        tokens = RMT2String.getTokens(temp, "*");
        if (tokens != null && tokens.size() > 1) {
            return tokens;
        }
        tokens = RMT2String.getTokens(clause, "<");
        if (tokens != null && tokens.size() > 1) {
            return tokens;
        }
        tokens = RMT2String.getTokens(clause, ">");
        if (tokens != null && tokens.size() > 1) {
            return tokens;
        }
        tokens = RMT2String.getTokens(clause, "<=");
        if (tokens != null && tokens.size() > 1) {
            return tokens;
        }
        tokens = RMT2String.getTokens(clause, ">=");
        if (tokens != null && tokens.size() > 1) {
            return tokens;
        }
        tokens = RMT2String.getTokens(clause, "<>");
        if (tokens != null && tokens.size() > 1) {
            return tokens;
        }
        temp = RMT2String.replaceAll(clause, "*", " is null ");
        tokens = RMT2String.getTokens(temp, "*");
        if (tokens != null && tokens.size() > 1) {
            return tokens;
        }
        temp = RMT2String.replaceAll(clause, "*", " is not null ");
        tokens = RMT2String.getTokens(temp, "*");
        if (tokens != null && tokens.size() > 1) {
            return tokens;
        }

        // Return null if operator is not found
        return null;
    }

    /**
     * Removes all special characters from a String phone number and identifies the 
     * phone extension, if available.  First, the base phone number is parsed by removing
     * the following characters in the order specified: <i>(</i>, <i>)</i>, <i>-</i>, and 
     * all spaces.  Lastly, the phone extension is parsed, if available, searching for the 
     * following values in the specified order: <i>ext</i>, <i>Ext</i>, <i>x</i>, and <i>X</i>.
     * 
     * @param phoneNo
     *          the phone number to be evaluated
     * @return String
     *          a List of Strings which the first element is the phone number without 
     *          any special characters and spaces, and the second element is the phone 
     *          extension, if available.
     */
    public static List cleanUpPhoneNo(String phoneNo) {
        // parse base phone number.
        String value = RMT2String.replace(phoneNo, "", "(");
        value = RMT2String.replace(value, "", ")");
        value = RMT2String.replace(value, "", "-");
        value = RMT2String.replaceAll(value, "", " ");
        List<String> tokens = null;
        if (value != null && !value.equals("")) {
            tokens = new ArrayList<String>();
            tokens.add(value.trim());
        }

        // Try to parse phone extension, if available.
        List<String> ext = null;
        if (value.indexOf("ext") >= 0) {
            ext = RMT2String.getTokens(value, "ext");
        }
        else if (value.indexOf("Ext") >= 0) {
            ext = RMT2String.getTokens(value, "Ext");
        }
        else if (value.indexOf("x") >= 0) {
            ext = RMT2String.getTokens(value, "x");
        }
        else if (value.indexOf("X") >= 0) {
            ext = RMT2String.getTokens(value, "X");
        }

        if (ext != null && ext.size() == 2) {
            return ext;
        }
        return tokens;
    }

    public static String execShellCommand(String command) {
        Process process = null;
        StringBuffer inMsg = new StringBuffer();
        StringBuffer errMsg = new StringBuffer();
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            if (!stdInput.ready()) {
                System.out.println("Command Shell Input buffer is empty");
                return null;
            }
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                inMsg.append(s);
            }
            if (!stdError.ready()) {
                System.out.println("Command Shell Error buffer is empty");
                return null;
            }
            while ((s = stdError.readLine()) != null) {
                errMsg.append(s);
            }
            if (errMsg.length() > 0) {
                inMsg.append("\nError: ");
                inMsg.append(errMsg);
            }
            return inMsg.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Recursively traverses all nested Throwable causes of <i>e</i> until the Throwable.getMessage() 
     * method returns an error message.
     * 
     * @param e
     * @return String
     *          the error message.
     */
    public static String getRootErrorMessage(Throwable e) {
	final String unwantedMsg = "Original Exception runtime type is unknown";
        String msg = e.getMessage();
        if (msg == null || msg.trim().equalsIgnoreCase(unwantedMsg)) {
            Throwable prev = e.getCause();
            if (prev != null) {
                msg = RMT2Utility.getRootErrorMessage(prev);
            }
        }
        return msg;
    }

    /**
     * Converts a standard dot-separated IP address to a network address.
     * 
     * @param ipDot
     * @return
     * @throws InvalidDataException
     */
    public static long getNetworkIp(String ipDot) throws InvalidDataException {
        String msg = null;
        String ipDiv[] = ipDot.split("\\.");
        if (ipDiv.length != 4) {
            msg = "IP address is invalid...must contain four octets: " + ipDot;
            throw new InvalidDataException(msg);
        }

        long oct1 = 0;
        long oct2 = 0;
        long oct3 = 0;
        long oct4 = 0;
        try {
            oct1 = Integer.parseInt(ipDiv[0]);
            oct2 = Integer.parseInt(ipDiv[1]);
            oct3 = Integer.parseInt(ipDiv[2]);
            oct4 = Integer.parseInt(ipDiv[3]);
        }
        catch (NumberFormatException e) {
            msg = "IP address contains an invalid octect: " + ipDot;
            throw new InvalidDataException(msg);
        }

        long ip = ((oct1 * 256 + oct2) * 256 + oct3) * 256 + oct4;
        return ip;
    }

    
    /**
     * 
     * @param array
     * @return
     */
    public static String array2String(long[] array) {
        StringBuffer ret=new StringBuffer("[");

        if(array != null) {
            for(int i=0; i < array.length; i++)
                ret.append(array[i]).append(" ");
        }

        ret.append(']');
        return ret.toString();
    }

    /**
     * 
     * @param array
     * @return
     */
    public static String array2String(int[] array) {
        StringBuffer ret=new StringBuffer("[");

        if(array != null) {
            for(int i=0; i < array.length; i++)
                ret.append(array[i]).append(" ");
        }

        ret.append(']');
        return ret.toString();
    }

    /**
     * 
     * @param array
     * @return
     */
    public static String array2String(boolean[] array) {
        StringBuffer ret=new StringBuffer("[");

        if(array != null) {
            for(int i=0; i < array.length; i++)
                ret.append(array[i]).append(" ");
        }
        ret.append(']');
        return ret.toString();
    }

    /**
     * 
     * @param array
     * @return
     */
    public static String array2String(Object[] array) {
        StringBuffer ret=new StringBuffer("[");

        if(array != null) {
            for(int i=0; i < array.length; i++)
                ret.append(array[i]).append(" ");
        }
        ret.append(']');
        return ret.toString();
    }
    

    /**
     * Tries to load the class from the current thread's context class loader. If
     * not successful, tries to load the class from the current instance.
     * 
     * @param classname 
     *                 Desired class.
     * @param clazz 
     *                 Class object used to obtain a class loader if no context class loader is available.
     * @return Class, or null on failure.
     */
    public static Class loadClass(String classname, Class clazz) throws ClassNotFoundException {
	ClassLoader loader;

	try {
	    loader = Thread.currentThread().getContextClassLoader();
	    if (loader != null) {
		return loader.loadClass(classname);
	    }
	}
	catch (Throwable t) {
	}

	if (clazz != null) {
	    try {
		loader = clazz.getClassLoader();
		if (loader != null) {
		    return loader.loadClass(classname);
		}
	    }
	    catch (Throwable t) {
	    }
	}

	try {
	    loader = ClassLoader.getSystemClassLoader();
	    if (loader != null) {
		return loader.loadClass(classname);
	    }
	}
	catch (Throwable t) {
	}

	throw new ClassNotFoundException(classname);
    }
    
    /**
     * Retrieves the classpath of the current JVM as a List of Strings.
     * 
     * @return
     */
    public static final List<String> getClassPath() {
	List<String> list = new ArrayList<String>();
	//Get the System Classloader
        ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();

        //Get the URLs
        URL[] urls = ((URLClassLoader) sysClassLoader).getURLs();

        for(int ndx = 0; ndx < urls.length; ndx++)  {
            list.add(urls[ndx].getFile());
        }       
        return list;
    }

} // end class

