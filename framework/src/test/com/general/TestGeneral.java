package com.general;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;

import java.io.*;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;

import org.junit.Test;

import com.util.RMT2Date;

/**
 * @author RTerrell
 * 
 */
public class TestGeneral {
    public static int JGREG = 15 + 31 * (10 + 12 * 1582);

   
    @Test
    public void testMessageDigest() {
	MessageDigest md5 = null;
	StringBuffer sbMD5Before = new StringBuffer();

	try {
	    md5 = MessageDigest.getInstance("MD5");
	    //                md5 = MessageDigest.getInstance("SHA-1");
	}
	catch (NoSuchAlgorithmException e) {
	    System.out.println("Error: " + e);
	}

	try {
	    long time = System.currentTimeMillis();
	    SecureRandom mySecureRand = new SecureRandom();
	    long randNo = mySecureRand.nextLong();
	    String s_id = InetAddress.getLocalHost().toString();

	    // This StringBuffer can be a long as you need; the MD5
	    // hash will always return 128 bits. You can change
	    // the seed to include anything you want here.
	    // You could even stream a file through the MD5 making
	    // the odds of guessing it at least as great as that
	    // of guessing the contents of the file!
	    sbMD5Before.append(s_id);
	    sbMD5Before.append(":");
	    sbMD5Before.append(Long.toString(time));
	    sbMD5Before.append(":");
	    sbMD5Before.append(Long.toString(randNo));

	    String valueBeforeMD5 = sbMD5Before.toString();
	    md5.update(valueBeforeMD5.getBytes());

	    byte[] array = md5.digest();
	    StringBuffer sb = new StringBuffer();
	    for (int j = 0; j < array.length; ++j) {
		int b = array[j] & 0xFF;
		if (b < 0x10) {
		    sb.append('0');
		}
		sb.append(Integer.toHexString(b));
	    }

	    String valueAfterMD5 = sb.toString();
	    System.out.println(valueAfterMD5);
	}
	catch (Exception e) {
	    System.out.println("Error:" + e);
	}
    }

    public void testDeleteDir(String path) {
	int count = this.deleteFile(path);
	System.out.println("Total resources deleted: " + count);
    }

    public int deleteFile(String path) {
	File file = new File(path);
	return this.deleteFile(file);
    }

    public int deleteFile(File file) {
	int count = 0;
	if (file.isDirectory()) {
	    String dirContents[] = file.list();
	    for (int ndx = 0; ndx < dirContents.length; ndx++) {
		File childFile = new File(file, dirContents[ndx]);
		count += this.deleteFile(childFile);
	    }
	}

	if (file.exists()) {
	    try {
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getAbsoluteFile());
		System.out.println(file.getCanonicalPath());
		System.out.println(file.getCanonicalFile());
		System.out.println(file.getName());
		System.out.println(file.getPath());
	    }
	    catch (Exception e) {
		System.out.println(e.getMessage());
	    }
	}
	if (file.canWrite()) {

	}
	if (file.delete()) {
	    count++;
	}
	// if (file.isFile()) {
	// if (file.delete()) {
	// count++;
	// }
	// }
	return count;
    }

    @Test
    public void testMath() {
	double x1 = 300;
	double x2 = 45.66;
	double result;

	result = x1 + x2;
	BigDecimal bd = new BigDecimal(result);
	bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
	System.out.println("BigDecimal Rounded: " + bd.doubleValue());
	result = Math.round(result);

    }

    @Test
    public void testJulian() {
	java.util.Date dt = new java.util.Date();
	Calendar cal = new GregorianCalendar();
	cal.setTime(dt);
	double jul = TestGeneral.toJulian(cal);
	jul = this.toJulian("2008/03/12");
	jul = this.toJulian("2004-03-12");
	jul = this.toJulian("2004-43/12");
	jul = jul;
    }

    public double toJulian(String date) {
	try {
	    java.util.Date dt = RMT2Date.stringToDate(date);
	    Calendar cal = new GregorianCalendar();
	    cal.setTime(dt);
	    double results = TestGeneral.toJulian(cal);
	    return results;
	}
	catch (Exception e) {
	    // Throw exception
	    System.out.println(e.getMessage());
	    return -1;
	}
    }

    public static double toJulian(Calendar date) {
	int year = date.get(Calendar.YEAR);
	int month = date.get(Calendar.MONTH); // jan=1, feb=2,...
	int day = date.get(Calendar.DAY_OF_MONTH);
	int julianYear = year;
	if (year < 0) {
	    julianYear++;
	}
	int julianMonth = month;
	if (month > 2) {
	    julianMonth++;
	}
	else {
	    julianYear--;
	    julianMonth += 13;
	}

	double julian = (java.lang.Math.floor(365.25 * julianYear) + java.lang.Math.floor(30.6001 * julianMonth) + day + 1720995.0);
	if (day + 31 * (month + 12 * year) >= JGREG) {
	    // change over to Gregorian calendar
	    int ja = (int) (0.01 * julianYear);
	    julian += 2 - ja + (0.25 * ja);
	}
	return java.lang.Math.floor(julian);
    }

    public String[] testSplit(String source) {
	List list = new ArrayList<String>();
	int begPos;
	int endPos;
	String exp;
	begPos = source.indexOf("{");
	while (begPos > 0) {
	    endPos = source.indexOf("}", begPos);
	    exp = source.substring(begPos + 1, endPos);
	    list.add(exp);
	    begPos = source.indexOf("{", endPos);
	}

	String exps[] = null;
	if (list.size() > 0) {
	    exps = (String[]) list.toArray(new String[list.size()]);
	}

	for (int ndx = 0; ndx < exps.length; ndx++) {
	    String token = "\\{" + exps[ndx] + "\\}";
	    source = source.replaceAll(token, "\'777\'");
	}
	return exps;
    }

    @Test
    public void testFormat() {
	DecimalFormat format = new DecimalFormat("#,###.00");
	double num = 4323.93;
	String strNum = format.format(num);

    }

    @Test
    public void testSplit() {
	String url = "http://rmt2dal01:8080/authentication";
	String result[] = url.split("/");
	String item1, item2, item3 = null;
	item1 = result[0];
	item2 = result[1];
	item3 = result[2];

	String item;
	String val;
	val = "JohnMDoe";
	result = val.split("=");
	item = null;
	item = result[0];
	item = result[1];

	StringTokenizer token = new StringTokenizer(val, "|");
	while (token.hasMoreTokens()) {
	    item = token.nextToken();
	}
	item = item;
    }

    
    public void testURL(String url) {
	try {
	    URL urlTest = new URL(url);
	    System.out.println("Valid URL: " + url);
	    url = url;
	}
	catch (MalformedURLException e) {
	    System.out.println("URL is bad: " + url);
	    System.out.println("Message: " + e.getMessage());
	}
    }

    @Test
    public void testStreams() {
	ObjectOutputStream oos;
	ObjectInputStream ois;
	DataInputStream dis;
	String data = "The output from this program should be identical to the output from the program that opens a stream directly from the URL. You can use either way to read from a URL. However, sometimes reading from a URLConnection instead of reading directly from a URL might be more useful to you as you can use the URLConnection object for other tasks (like writing to the URL) at the same time";

	try {
	    StringBufferInputStream sbis = new StringBufferInputStream(data);
	    dis = new DataInputStream(sbis);
	    String inputLine;
	    String data2 = "";
	    while ((inputLine = dis.readLine()) != null) {
		data2 += inputLine;
		System.out.println(inputLine);
	    }
	    dis.close();

	    FileOutputStream file = new FileOutputStream("c://testotput.txt");
	    oos = new ObjectOutputStream(file);
	    oos.writeBytes(data);
	    oos.flush();
	    oos.close();

	}
	catch (MalformedURLException me) {
	    System.out.println("MalformedURLException: " + me);
	}
	catch (IOException ioe) {
	    System.out.println("IOException: " + ioe);
	}

    }

    @Test
    public void testJDBC() {
	try {
	    boolean rc;
	    String msg;
	    int intRc;
	    Driver driver = (Driver) Class.forName("com.sybase.jdbc2.jdbc.SybDriver").newInstance();
	    Properties prop = new Properties();
	    prop.put("User", "app");
	    prop.put("password", "app");
	    prop.put("ServiceName", "authentication");
	    // prop.put("RemotePWD", ",DatabaseFile=C:\\MyStuff\\data\\sql\\rmt2.db");
	    Connection con = driver.connect("jdbc:sybase:Tds:localhost:2638", prop);
	    CallableStatement stmt = con.prepareCall("{? = call dba.testfunc2(?)}");
	    stmt.setInt(2, 123);
	    // Sybase always expects the stored functions to return an int
	    stmt.registerOutParameter(1, Types.INTEGER);
	    rc = stmt.execute();
	    intRc = stmt.getInt(1);
	    rc = rc;

	}
	catch (InstantiationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	catch (IllegalAccessException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    @Test
    public void accessSysProps() {
	// get the environment variables
	System.out.println("Environment Variables");
	Map envMap = System.getenv();
	for (Object key : envMap.keySet()) {
	    String name = (String) key;
	    System.out.println(key + " = " + envMap.get(key));
	    System.out.println("Direct access of key: " + name);
	}

	// get the system properties
	Properties properties = System.getProperties();
	System.out.println("System Properties");
	for (Object key2 : properties.keySet()) {
	    System.out.println(key2 + " = " + properties.get(key2));
	}

    }

    @Test
    public void testResourceBundle() {
	String parm[] = { "123", "333" };
	System.out.println(String[].class.getName());

	ResourceBundle bundle;
	String filename = "C:\\MyStuff\\source\\config-java\\config-java\\config\\app\\authentication\\AppParms.properties";
	String value;
	String key = "dbdriver";
	try {
	    bundle = ResourceBundle.getBundle(filename);
	    value = bundle.getString(key);
	    System.out.println("Value from ResourceBundle: " + value);
	}
	catch (MissingResourceException e) {
	    System.out.println(e.getMessage());
	}
	catch (ClassCastException e) {
	    System.out.println(e.getMessage());
	}
	catch (NullPointerException e) {
	    System.out.println(e.getMessage());
	}

	PropertyResourceBundle bundle2;
	FileInputStream fis;
	try {
	    fis = new FileInputStream(filename);
	    bundle2 = new PropertyResourceBundle(fis);
	    value = bundle2.getString(key);
	    System.out.println("Value from PropertyResourceBundle: " + value);
	}
	catch (Exception e) {
	    System.out.println(e.getMessage());
	}

    }

    @Test
    public void testLocale() {
	ResourceBundle bundle = null;
	String filename = "com.SystemParms";
	Locale loc = Locale.getDefault();
	String lang = null;
	String server = null;
	try {
	    bundle = ResourceBundle.getBundle(filename);
	    server = bundle.getString("server");
	    lang = bundle.getString("lang");
	}
	catch (Exception e) {
	    System.out.println(e.getMessage());
	    System.out.println("The current locale country is: " + loc.getCountry());
	    System.out.println("The current locale language is: " + loc.getDisplayLanguage());
	}
	finally {
	    System.out.println("Language is: " + lang);
	    System.out.println("Server is: " + server);
	}
    }

    public static void main(String[] args) {
	TestGeneral test = new TestGeneral();
	test.testDecimal();
    }

    @Test
    public void testDecimal() {
	String source = "23,323.39";
	DecimalFormat format = new DecimalFormat();
	ParsePosition pos = new ParsePosition(0);
	//		format.setParseBigDecimal(true);
	Number num = format.parse(source, pos);
	if (num != null) {
	    System.out.println("Results: " + num.doubleValue());
	    System.out.println("String Length: " + source.length());
	    System.out.println("Last Pos Parsed: " + pos.getIndex());
	    System.out.println("Error Pos: " + pos.getErrorIndex());
	}

	source = "23323D39";
	pos = new ParsePosition(0);
	num = format.parse(source, pos);
	if (num != null) {
	    System.out.println("Results: " + num.doubleValue());
	    System.out.println("String Length: " + source.length());
	    System.out.println("Last Pos Parsed: " + pos.getIndex());
	    System.out.println("Error Pos: " + pos.getErrorIndex());
	}

	source = "23,323.....39";
	pos = new ParsePosition(0);
	num = format.parse(source, pos);
	if (num != null) {
	    System.out.println("Results: " + num.doubleValue());
	    System.out.println("String Length: " + source.length());
	    System.out.println("Last Pos Parsed: " + pos.getIndex());
	    System.out.println("Error Pos: " + pos.getErrorIndex());
	}

	source = "$323.00";
	pos = new ParsePosition(0);
	num = format.parse(source, pos);
	if (num != null) {
	    System.out.println("Results: " + num.doubleValue());
	    System.out.println("String Length: " + source.length());
	    System.out.println("Last Pos Parsed: " + pos.getIndex());
	    System.out.println("Error Pos: " + pos.getErrorIndex());
	}

	source = "S323.00";
	pos = new ParsePosition(0);
	num = format.parse(source, pos);
	if (num != null) {
	    System.out.println("Results: " + num.doubleValue());
	    System.out.println("String Length: " + source.length());
	    System.out.println("Last Pos Parsed: " + pos.getIndex());
	    System.out.println("Error Pos: " + pos.getErrorIndex());
	}
    }
}
