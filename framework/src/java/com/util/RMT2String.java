package com.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;



/**
 * Class contains a collection general purpose String utilities.
 * 
 * @author roy.terrell
 *
 */
public class RMT2String {
    /** Pad String with leading characters */
    public static final int PAD_LEADING = -1;

    /** Pad String with trailing characters */
    public static final int PAD_TRAILING = 1;

    /**
     * Creates a string of spaces totaling "count".
     * 
     * @param count total number of spaces to produce
     * @return String
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
     */
    public static final String padInt(long _source, int _precision, int _direction) {
        String temp = String.valueOf(_source);
        int addCount = _precision - temp.length();
        String padding = RMT2String.dupChar('0', addCount);
        if (_direction == RMT2String.PAD_LEADING) {
            temp = padding + temp;
        }
        if (_direction == RMT2String.PAD_TRAILING) {
            temp += padding;
        }
        return temp;
    }

    /**
     * Parses _str by extracting embedded values separted by character 
     * values that equal _delim.   Each value extracted will be packaged 
     * in a ArrayList which is returned to the caller.
     * 
     * @param _str String that is to parsed for tokens
     * @param _delim The delimiter separates the tokens
     * @return  List<String> of each value extracted from _str or null if _str 
     *          was unable to be parsed.
     */
    public static final List<String> getTokens(String _str, String _delim) {

        List<String> tokens = new ArrayList<String>();
        String value = null;

        if (_delim == null) {
            _delim = RMT2String.spaces(1);
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
     * Parses _source by replacing the first occurrence of "_delim" 
     * with "_replacement" and returns the results to the caller.
     * 
     * @param _source String that is to parsed for for placeholders
     * @param _replacement Value that will replace "_delim"
     * @param _delim The place holder that is to be replaced
     * @return String
     */
    public static final String replace(String _source, String _replacement, String _delim) {

        String value = null;
        int ndx;

        if (_delim == null) {
            _delim = RMT2String.spaces(1);
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
     * Parses _source by replacing the all occurrences of "_delim" 
     * with "_replacement" and returns the results to the caller.
     * 
     * @param _source String that is to be parsed for for placeholders
     * @param _replacement Value that will replace "_delim"
     * @param _delim The place holder representing the value that is to be replaced.
     * @return String
     */
    public static final String replaceAll(String _source, String _replacement, String _delim) {

        String newString = null;
        int total;
        int ndx;

        ndx = 0;
        total = 0;

        // Determine the number of place holders are required to be substituted.
        newString = _source.replaceAll(_delim, _replacement);
        return newString;
    }

    /**
     * Parses _source by replacing the all occurrences of "_delim" 
     * with "_replacement" and returns the results to the caller.
     * 
     * @param _source String that is to be parsed for for placeholders
     * @param _replacement Value that will replace "_delim"
     * @param _delim The place holder representing the value that is to be replaced.
     * @return String
     */
    public static final String replaceAll2(String _source, String _replacement, String _delim) {

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
     * Counts the total occurrences of a character within a String.  Returns 
     * the total count to the caller.
     * 
     * @param _source String that is the source of target character to be counted.
     * @param _target Character that is being counted.
     * @return the number to occurrences _target was found.
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
     * Capitalizes the first character of a string variable and 
     * returns the capitalized string to the caller.
     * 
     * @param value The source String that is to be converted.
     * @return The results of the conversion as a String.
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
     * Creates a credit card mask only exposing the last f digits of the credit card
     * 
     * @param ccNo
     * @return returns a String in the format of [xxxxxxxxx9999] or null if <i>ccNo</i> is null or empty.
     */
    public static String maskCreditCardNumber(String ccNo) {
	StringBuffer sb = new StringBuffer();
	String results = null;
	if (RMT2String2.isNotEmpty(ccNo)) {
	    int length = ccNo.length();
	    int maskPos = length;
	    if (length > 7) {
		maskPos = length - 4;
		String mask = RMT2String.dupChar('x', maskPos);
		sb.append(mask);
		sb.append(ccNo.substring(maskPos));
	    }
	    else {
		// if account.length too short, do not show length and mark
		// as xxxxxN
		maskPos = length - 1;

		sb.append("xxxxx");
		sb.append(ccNo.substring(maskPos));
		if (length > 4) {
		    sb.replace(0, 1, String.valueOf(ccNo.charAt(0)));
		}
	    }
	    results = sb.toString();
	}
	return results;
    }
 

} // end class

