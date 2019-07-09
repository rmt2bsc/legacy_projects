package com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.*;

import java.util.Properties;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class TestJdbcDriver {
	private static final String OUTPUT_LOCATION = "c:/temp/";
	private ArrayList cons;
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestJdbcDriver test = new TestJdbcDriver();
		test.testMSSql();
		 test.testSQLAny();
	}

	public TestJdbcDriver() {
		this.cons = new ArrayList();
	}
	
	public void testMSSql() {
		ResourceBundle prop =  ResourceBundle.getBundle("DbHelper");;
		Driver driver = null;
		Connection msCon;
		
//		 Discover driver manager, open connection. 
		String serverName = prop.getString("ServerName");
		String databaseName = prop.getString("DatabaseName");
		String driverName = prop.getString("DriverName");
		String urlPrefix = prop.getString("URL");
		String uid = prop.getString("uid");
		String pw = prop.getString("pw");
		String port = prop.getString("DbPort");
		String url = null;
		
		url = urlPrefix + serverName + (port == null ? "/" : ":" + port + "/") + databaseName;
		url += (uid != null ? ";user=" + uid : "");
		url += (pw != null ? ";password=" + pw : "");
		url = url.trim();
		
		// Load driver
		int results;
		String sql = null;
		Properties props = new Properties();
		try {
			driver = (Driver) Class.forName(driverName).newInstance();	
			msCon = driver.connect(url, props);
			
			sql = "select * from plantype for xml auto, elements";
			results = this.queryDb(msCon, sql, "plantype.xml");
			System.out.println("Total umber of bytes written to plantype file: " + results);
			sql = "select * from rider for xml auto, elements";
			results = this.queryDb(msCon, sql, "rider.xml");
			System.out.println("Total umber of bytes written to rider file: " + results);
			sql = "select * from rvquote for xml auto, elements";
			results = this.queryDb(msCon, sql, "rvquote.xml");
			System.out.println("Total umber of bytes written to rvquote file: " + results);
		}
		catch (Exception e) {
			System.out.println("MS SQL Server testMSSql Error: " + e.getMessage());
		}
	}
	
	
	public void testSQLAny() {
		String results = null;
		Driver driver;
		Properties props;
		String url;

		// Establish connection
		props = new Properties();
		props.put("user", "dba");
	    props.put("password",  "sql");
		url = "jdbc:sybase:Tds:AMGHST-DEV11:2638/demo";
		
		try {
			driver = (Driver) Class.forName ("com.sybase.jdbc2.jdbc.SybDriver").newInstance();
			for (int ndx = 0; ndx < 100; ndx++) {
				Connection con = driver.connect(url, props);
				this.cons.add(con);
				System.out.println("Connection " + (ndx + 1) + " created");
			}
		}
		catch (Exception e) {
			System.out.println("Test failed: " + e.getMessage());
			return;
		}
		
		// Query database
		try {
			results = this.queryDb(this.cons.get(33), "Select * from employees for xml auto, elements");
			System.out.println(results);
			results = this.queryDb(this.cons.get(97), "Select * from customers for xml auto, elements");
			System.out.println(results);
			results = this.queryDb(this.cons.get(15), "Select * from products for xml auto, elements");
			System.out.println(results);
		}
		catch (Exception e) {
			System.out.println("testSQL failed: "  + e.getMessage());
			return;
		}
	}
	
	
	public int queryDb(Object _con, String sql, String _fileName) {
		String results = this.queryDb(_con, sql);
		byte byteResulsts[] = results.getBytes();
		File file = new File(TestJdbcDriver.OUTPUT_LOCATION + _fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);	
			fos.write(byteResulsts);
			fos.close();
			return byteResulsts.length;
		}
		catch (FileNotFoundException e) {
			System.out.println(TestJdbcDriver.OUTPUT_LOCATION + _fileName + " is not found");
			return -1;
		}
		catch (IOException e) {
			System.out.println(TestJdbcDriver.OUTPUT_LOCATION + _fileName + " is not found");
			return -2;
		}
		
	}
	
	
	public String queryDb(Object _con, String sql) {
		StringBuilder xml = new StringBuilder(1000);
		Connection con = (Connection) _con;
		Statement stmt;
		ResultSet rs;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			// Loop through resultset to string together data.  This is required for MS SQL Server since it breaks up
			// XML result set into one or more rows of data.   SQL Anywhere included the complete XML dataset into one row.
			while (rs.next()) {
				xml.append(rs.getString(1));
			}
			System.out.println("XML size: " + xml.length());
			return xml.toString();
		}
		catch (Exception e) {
			System.out.println("queryDb failed: " + e.getMessage());
			return null;
		}
	}
}
