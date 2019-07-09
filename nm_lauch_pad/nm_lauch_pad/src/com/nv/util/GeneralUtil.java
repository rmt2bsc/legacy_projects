package com.nv.util;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.NotSerializableException;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import com.InvalidDataException;

/**
 * General purpose utility class.
 * 
 * @author rterrell
 *
 */
public class GeneralUtil {

    /** File exists code */
    public static final int FILE_IO_EXIST = 1;

    /** File does not exist code */
    public static final int FILE_IO_NOTEXIST = -1;

    /** file is null */
    public static final int FILE_IO_NULL = 0;

    /** File is inaccessible */
    public static final int FILE_IO_INACCESSIBLE = -2;

    /**
     * The key name representing the upper half of a sku value.
     */
    public static final String SKU_KEY_UPPER = "upper";

    /**
     * The key name representing the lower half of a sku value.
     */
    public static final String SKU_KEY_LOWER = "lower";

    /**
     * Different number formats
     */
    public static List<String> numberFormats = new ArrayList<String>();
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
     * Different date formats.
     */
    public static List<String> dateFormats = new ArrayList<String>();
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
        dateFormats.add("yyyyMMdd");
        dateFormats.add("MMddyyyy");
    }

    /**
     * Accepts a date as a String and converts it to java.util.Date. The date
     * will be converted based on the date format passed as "format". If format
     * is null, then "value" is assumed to be in the format of "yyyy-MM-dd".
     * 
     * @param value
     *            String value to convert
     * @param format
     *            Date Format
     * @return java.util.Date
     * @throws Exception
     *             if "format" is not an acceptable java date format
     */
    public static final Date stringToDate(String value, String format)
            throws Exception {
        SimpleDateFormat dateFormatter = null;
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
                } catch (IllegalArgumentException e) {
                    throw new Exception("Date Format is invalid");
                }
            }
            dateFormatter.setLenient(false);
            java.util.Date nativeDate = dateFormatter.parse(value);
            if (nativeDate == null) {
                throw new Exception(
                        "Data is invalid based on selected fotmat: " + value
                                + " formatted as: " + format);
            }

            return nativeDate;
        } catch (ParseException e) {
            throw new Exception("Data is invalid based on selected fotmat: "
                    + value + " formatted as: " + format);
        }
    }

    /**
     * Accepts a date as a String and converts it to java.util.Date. The format
     * of, "value", is expected to match one of the date formats in the
     * ArrayList, "dateFormats".
     * 
     * @param value
     *            String value to convert
     * @return java.util.Date
     * @throws Exception
     *             if a date format match is not found.
     */
    public static final Date stringToDate(String value) throws Exception {
        String format = null;

        if (value == null || value.equalsIgnoreCase("null") || value.equals("")) {
            return null;
        }
        if (value.length() <= 0) {
            return null;
        }

        for (int ndx = 0; ndx < GeneralUtil.dateFormats.size(); ndx++) {
            format = (String) GeneralUtil.dateFormats.get(ndx);
            try {
                return stringToDate(value, format);
            } catch (Exception ee) {
                // continue to the next date format, if available
            }
        }

        // throw exceptoin if date string could not be successfully converted
        String msg = "The date value entered is invalid, " + value;
        throw new Exception(msg);
    }

    /**
     * Accepts a number as a String and converts it to the java wrapper class,
     * Number. The format of, "_value", is expected to match one of the numeric
     * formats in the ArrayList, "numberFormats".
     * 
     * @param _value
     *            String value to convert
     * @return Number
     * @throws Exception
     *             if a number format match is not found
     */
    public static final Number stringToNumber(String _value) throws Exception {
        try {
            if (_value == null) {
                return null;
            }
            if (_value.length() <= 0) {
                return null;
            }
            DecimalFormat decFormatter = new DecimalFormat();
            return decFormatter.parse(_value);
        } catch (ParseException e) {
            String format = null;
            for (int ndx = 0; ndx < GeneralUtil.numberFormats.size(); ndx++) {
                format = (String) GeneralUtil.numberFormats.get(ndx);
                try {
                    return stringToNumber(_value, format);
                } catch (Exception e2) {
                }
            }

            // throw exceptoin if number string could not be successfully
            // converted
            throw new Exception("Invalid numeric String value: " + _value);
        }
    }

    /**
     * Accepts a number as a String and converts it to the java wrapper class,
     * Number. The number will be converted based on the numeric format passed
     * as "_format". If _format is null, then "_value" is assumed to be in a
     * form acceptable for parsing and converting _value without _format.
     * 
     * @param _value
     *            String value to convert
     * @param _format
     *            Number Format
     * @return Number
     * @throws Exception
     *             if "format" is not an acceptable java numeric format
     */
    public static final Number stringToNumber(String _value, String _format)
            throws Exception {
        DecimalFormat decFormatter = null;
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
        } catch (IllegalArgumentException e) {
            throw new Exception(
                    "The numeric value is invalid based on the selected format: "
                            + _value + ", " + _format);
        } catch (ParseException e) {
            // throw exceptoin if number string could not be successfully
            // converted
            throw new Exception(
                    "The numeric value could not be parsed based on the selected format: "
                            + _value + ", " + _format);
        }
    }

    /**
     * Accepts a Number object and converts it to a String in the format of
     * "_format". If _format is null, then _value is passed back to the caller
     * as is without any converting.
     * 
     * @param value
     *            The number for convert.
     * @param format
     *            The format to apply to the conversion.
     * @return The String representation of value.
     * @throws Exception
     *             If format is invalid or an unacceptable date format.
     */
    public static final String formatNumber(Number value, String format)
            throws Exception {
        if (value == null) {
            return null;
        }
        if (format == null) {
            return value.toString();
        }

        try {
            DecimalFormat decFormatter = new DecimalFormat(format);
            return decFormatter.format(value.doubleValue());
        } catch (IllegalArgumentException e) {
            throw new Exception(
                    "Number ["
                            + value
                            + "] could not be formatted to a String using the following format ["
                            + format + "]");
        }
    }

    /**
     * Determines if a String is valid number.
     * 
     * @param val
     *            the String value to test
     * @return true when <i>val</i> is a valid number and false otherwise.
     */
    public static final boolean isNumeric(String val) {
        try {
            Double.parseDouble(val);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Accepts a java.util.Date object and converts it to a String in the format
     * of "_format". If _format is null, then _value is passed back to the
     * caller as is without any converting.
     * 
     * @param _value
     *            Date value to be converted.
     * @param _format
     *            The date format to be used for the conversion.
     * @return A String representation of _value.
     * @throws Exception
     *             If _format is invalid or an unacceptable date format.
     */
    public static final String formatDate(java.util.Date _value, String _format)
            throws Exception {
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
     * Parses _str by extracting embedded values separted by character values
     * that equal _delim. Each value extracted will be packaged in a ArrayList
     * which is returned to the caller.
     * 
     * @param _str
     *            String that is to parsed for tokens
     * @param _delim
     *            The delimiter separates the tokens
     * @return List<String> of each value extracted from _str or null if _str
     *         was unable to be parsed.
     */
    public static final List<String> getTokens(String _str, String _delim) {
        List<String> tokens = new ArrayList<String>();
        String value = null;
        if (_delim == null) {
            _delim = GeneralUtil.spaces(1);
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
     * Creates a string of spaces totaling "count".
     * 
     * @param count
     *            total number of spaces to produce
     * @return String
     */
    public static final String spaces(int count) {
        String value = "";
        if (count == 0) {
            return "";
        }
        if (count < 0) {
            return null;
        }
        for (int ndx = 1; ndx <= count; ndx++) {
            value += " ";
        }
        return value;
    }

    /**
     * Creates a Properties object by loading its key/value pairs from the
     * resource, <i>propFileName</i>.
     * <p>
     * This method uses the option of locating <i>propFileName</i> from the
     * classpath or the file system.
     * 
     * @param propFileName
     *            a .properties file found within the classpath or file system.
     * @return an instance of {@link Properties}.
     */
    public static final Properties loadProperties(String propFileName) {
        Properties props = new Properties();
        String msg = null;
        try {
            // Try to load as a resource of the classpath
            InputStream is = GeneralUtil.class
                    .getResourceAsStream(propFileName);
            if (is == null) {
                // Try to load as a resource of the file system
                File file = new File(propFileName);
                is = new FileInputStream(file);
            }
            props.load(is);
            return props;
        } catch (NullPointerException e) {
            msg = "Unable to load contents of Properties file since Properties file name input argument is null";
            throw new RuntimeException(msg, e);
        } catch (FileNotFoundException e) {
            msg = "Unable to load contents of Properties file since it cannot be found: "
                    + propFileName;
            throw new RuntimeException(msg, e);
        } catch (IOException e) {
            msg = "Unable to load contents of Properties file due to a general File IO errors";
            throw new RuntimeException(msg, e);
        }
    }

    /**
     * 
     * @param prop
     * @param outFileName
     */
    public static final void persistProperties(Properties prop,
            String outFileName) {
        String msg = null;
        try {
            FileOutputStream fos = new FileOutputStream(new File(outFileName));
            prop.store(fos,
                    "This Properties file was genereated by the Launh Pad start up process");
            return;
        } catch (NullPointerException e) {
            msg = "Unable to persists contents of Properties file since Properties file name input argument is null";
            throw new RuntimeException(msg, e);
        } catch (FileNotFoundException e) {
            msg = "Unable to persists contents of Properties file due a problem creating and/or oopening the output file: "
                    + outFileName;
            throw new RuntimeException(msg, e);
        } catch (IOException e) {
            msg = "Unable to persist contents of Properties file due to specified Properties file due to a general IO error";
            throw new RuntimeException(msg, e);
        }
    }

    /**
     * Splits a sku value in th upper and lower parts.
     * <p>
     * The lower half should be the lowest 8 digits. The upper half should be
     * the everything greater than the lowers 8 digits. For example, if the sku
     * value is "11122223333", then the upper half would be "1111" and the lower
     * half would be "22223333". If the sku is 8 digits or less then the upper
     * half will equal "0".
     * 
     * @param sku
     *            a String representing the value of the sku
     * @return a Map <String, Interfer> containing the results of the split.
     *         Map's keys will equal "lower" and "upper". The mapped values will
     *         contain Integer types.
     */
    public static final Map<String, Integer> splitLongSku(String sku) {
        try {
            Long.parseLong(sku);
        } catch (NumberFormatException e) {
            return null;
        }

        Map<String, Integer> results = new HashMap<String, Integer>();

        // Handle a sku in which the total number of digits are less than or
        // equal to 8.
        if (sku.length() <= 8) {
            results.put(GeneralUtil.SKU_KEY_UPPER, 0);
            results.put(GeneralUtil.SKU_KEY_LOWER, Integer.parseInt(sku));
            return results;
        }

        // Handle the typical long sku
        int totDigits = sku.length();
        int lowerPos = totDigits - 8;
        String lowerVal = sku.substring(lowerPos);
        String upperVal = sku.substring(0, lowerPos);

        results.put(GeneralUtil.SKU_KEY_UPPER, Integer.parseInt(upperVal));
        results.put(GeneralUtil.SKU_KEY_LOWER, Integer.parseInt(lowerVal));
        return results;
    }

    /**
     * Creates an instance of {@link Color} from a list of RGB values containted
     * in <i>colors</i>.
     * 
     * @param colors
     *            a String representing the RGB numeric values that will
     *            comprise the color. The list of values should occur in the
     *            order of red, green, and blue and separated by commas.
     * @param defColor
     *            an instance of {@link Color} representing the default color
     *            assinged in the event an error occurs or if <i>colors</i> is
     *            found to be invalid.
     * @return an instance of {@link Color} based on the values containted in
     *         <i>colors</i> or the default known as <i>defColor</i>.
     */
    public static final Color createColorInstance(String colors, Color defColor) {
        // The color configuration input argument must not be null or ""
        if (colors == null || colors.equals("")) {
            return defColor;
        }

        // Convert colors argument into an array of 3 numeric Strings.
        String rgb[];
        int total = 0;
        try {
            rgb = colors.split(",");
            total = rgb.length;
            if (total != 3) {
                return defColor;
            }
        } catch (Exception e) {
            return defColor;
        }

        // Verify that all values are numerics and trim each String value.
        for (int ndx = 0; ndx < total; ndx++) {
            String item = rgb[ndx].trim();
            if (!GeneralUtil.isNumeric(item)) {
                return defColor;
            }
            rgb[ndx] = item;
        }

        // Create Color instance
        return new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]),
                Integer.parseInt(rgb[2]));
    }

    /**
     * Reports the current working directory.
     * 
     * @return The directory path as a String
     */
    public static final String getCurrentDir() {
        File file = new File(".");
        String path = null;
        try {
            path = file.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return path;
    }

    /**
     * Writes String data to a disk file.
     * 
     * @param data
     *            The String data to be written to disk
     * @param filename
     *            The path name to where data is stored to disk.
     * @throws Exception
     *             file destination is not found or general IO error.
     */
    public static void outputFile(String data, String filename)
            throws Exception {
        byte byteResulsts[] = data.getBytes();
        GeneralUtil.outputFile(byteResulsts, filename);
    }

    /**
     * Writes byte array to a disk file.
     * 
     * @param data
     *            the array of bytes to persist to file.
     * @param filename
     *            the output filename
     * @throws Exception
     */
    public static void outputFile(byte data[], String filename)
            throws Exception {
        File destFile = new File(filename);
        String msg;

        if (destFile.isDirectory()) {
            msg = "Output path must be a file instead of a directory";
            throw new Exception(msg);
        }

        try {
            // Use an FileOutputStream to write data to disk.
            FileOutputStream fos = new FileOutputStream(destFile);
            fos.write(data);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            msg = "File was not found.  Check whether or not the directory structure exists";
            throw new Exception(msg, e);
        } catch (NotSerializableException e) {
            msg = "Object is not serializable...Ensure that object implements java.io.Serializable interface";
            throw new Exception(msg, e);
        } catch (IOException e) {
            msg = "General file IO error occurred";
            throw new Exception(msg, e);
        }
    }

    /**
     * Accesses the contents of a plain text file and return the contents as a
     * String to the caller.
     * 
     * @param filename
     *            The file name.
     * @return The contents of the file as a String.
     * @throws Exception
     *             if filename is null, does not exist, or is inaccessible.
     */
    public static String getTextFileContents(String filename) throws Exception {
        String msg;
        try {
            StringBuffer fileContent = new StringBuffer(100);
            switch (GeneralUtil.verifyFile(filename)) {
                case FILE_IO_NULL:
                    msg = "Argument [_filename] is null or empty";
                    throw new Exception(msg);
                case FILE_IO_NOTEXIST:
                    msg = "File does not exist: " + filename;
                    throw new Exception(msg);
                case FILE_IO_INACCESSIBLE:
                    msg = "File is inaccessible: " + filename;
                    throw new Exception(msg);
            }

            // At this point we have a file that exist and is accessible.
            FileReader fr = new FileReader(filename);
            BufferedReader buf = new BufferedReader(fr);

            int temp;
            while ((temp = buf.read()) != -1) {
                fileContent.append((char) temp);
            }
            return fileContent.toString();
        } catch (FileNotFoundException e) {
            msg = "File Not Found IO Exception for " + filename;
            throw new Exception(msg);
        } catch (IOException e) {
            msg = "File IO Exception for " + filename;
            throw new Exception(msg);
        }
    }

    /**
     * Verifies that _pathname is a valid Directory or File.
     * 
     * @param _pathName
     *            The file path.
     * @return 1=File Exist, -1=File does not exsit, -2=File inaccessible, and
     *         0= Argument is an empty String or null.
     */
    public static int verifyFile(String _pathName) {
        File path;

        try {
            path = new File(_pathName);
        } catch (NullPointerException e) {
            return FILE_IO_NULL;
        }

        // Validate the existence of path
        try {
            if (!path.exists()) {
                return FILE_IO_NOTEXIST;
            }
            return FILE_IO_EXIST;
        } catch (Exception e) {
            return FILE_IO_INACCESSIBLE;
        }
    }

    /**
     * Validates a user's maintained date field.
     * <p>
     * This method ensures that the month, day, and year parts are a total of 8
     * digits and are all numeric, and <i>dateStr</i> is an actual valid date.
     * <i>dateStr</i> is reduced to the format of MMddyyyy before performing any
     * validateion rules.
     * 
     * @param fieldName
     *            the name of the date field being validated or null to use the
     *            default field description, "Date field ".
     * @param value
     *            the date value that is to be validated.
     * @throws InvalidDataException
     *             The length of the date is not 8 digits, date value contains a
     *             non-numeric character, or the date is invalid.
     */
    public static final Date validateLaunchPadDateEdits(String fieldName,
            String dateStr) throws InvalidDataException {
        String msg = null;
        String fieldDesc = null;

        if (fieldName == null) {
            fieldDesc = "Date field ";
        }
        else {
            fieldDesc = "The field, " + fieldName + " [" + dateStr + "], ";
        }

        // Ensure that a total of 8 digits are entered by converting to the
        // format, MMddyyy.
        String val = dateStr.replaceAll("/", "");
        val = val.replaceAll("-", "");
        if (val.length() != 8) {
            msg = fieldDesc + "must be in the format of mmddyyyy or mm/dd/yyyy";
            throw new InvalidDataException(msg);
        }

        // Month, day, and year parts must be all numeric
        try {
            Integer.parseInt(val);
        } catch (NumberFormatException e) {
            msg = fieldDesc + "must contain numeric values only";
            throw new InvalidDataException(msg);
        }

        // Verify that it is a valid date
        try {
            return GeneralUtil.stringToDate(dateStr);
        } catch (Exception e) {
            msg = fieldDesc + "is an invalid date";
            throw new InvalidDataException(msg);
        }
    }

    /**
     * Create an input stream using a String of data as the source.
     * 
     * @param data
     *            String
     * @return an instance of {@link InputStream}
     */
    public static InputStream createInputStream(String data) {
        InputStream is = new ByteArrayInputStream(data.getBytes());
        return is;
    }

    /**
     * Create a buffered reader using a String of data as the source.
     * 
     * @param data
     *            String
     * @return an instance of {@link BufferedReader}
     */
    public static BufferedReader createReader(String data) {
        InputStream is = GeneralUtil.createInputStream(data);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return br;
    }

    /**
     * Creates a JFormattedTextField using a specified mask.
     * 
     * @param mask
     *            a valid mask format.
     * @return an instance of {@link JFormattedTextField} with the specified
     *         mask applied. If the mask is invalid, a JFormattedTextField
     *         without any formatting is returned.
     */
    public static final JFormattedTextField createMaskedTextField(String mask) {
        JFormattedTextField f;
        MaskFormatter formatter;
        try {
            formatter = new MaskFormatter(mask);
            f = new JFormattedTextField(formatter);
        } catch (ParseException e) {
            f = new JFormattedTextField();
        }

        f.setFocusLostBehavior(JFormattedTextField.PERSIST);

        // Add focus listener to prevent the insertion of space place holders
        f.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                JFormattedTextField comp = (JFormattedTextField) e
                        .getComponent();
                comp.setText(comp.getText().trim());
            }

            @Override
            public void focusGained(FocusEvent e) {
                JFormattedTextField comp = (JFormattedTextField) e
                        .getComponent();
                comp.setText(comp.getText().trim());
            }
        });

        return f;
    }
}
