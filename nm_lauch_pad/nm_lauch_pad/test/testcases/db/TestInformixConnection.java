package testcases.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author rterrell
 *
 */
public class TestInformixConnection {

    private static final String DB_URL = "jdbc:informix-sqli://nmposdev1:2301/rfdb:INFORMIXSERVER=";
        
    private Connection con;
    
    
    
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	// Load the JDBC Driver
	try {
	    Class.forName("com.informix.jdbc.IfxDriver");
	    System.out.println("Informix DB Driver loaded successfully");
	}
	catch (Exception e) {
	    throw new Exception("Informix DB Driver failed to load");
	}
	
	// Setup DB URL (Connection String)
	try {
	    this.con = DriverManager.getConnection(TestInformixConnection.DB_URL, "rfuser", "rfuser");    

	    // We want to handle dtabase transactions  ourselves
	    this.con.setAutoCommit(false);
	}
	catch (SQLException e) {
	    System.out.println(e.getMessage());
	    throw e;
	}
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
	if (this.con == null) {
	    return;
	}
	if (!this.con.isClosed()) {
	    this.con.close();
	}
    }

    @Test
    public void testFetch() {
	String sql = "select assoc_id, assoc_first_name, assoc_last_name, assoc_type from assoc order by assoc_last_name, assoc_first_name";
	try {
	    Statement stmt = this.con.createStatement();
	    ResultSet rs = stmt.executeQuery(sql);
	    int count = 0;
	    while(rs.next()) {
		int id = rs.getInt("assoc_id");
		String fn = rs.getString("assoc_first_name");
		String ln = rs.getString("assoc_last_name");
		String aType = rs.getString("assoc_type");
		System.out.println(++count + ".  Id: " + id + ", First Name: " + fn + ", Last Name: " + ln + ", Type: " + (aType == null || aType.equals(" ") ? "N/A" : aType) );
	    }
	    rs.close();
	    stmt.close();
	}
	catch (SQLException e) {
	    e.printStackTrace();
	}
    }
    
    
    @Test
    public void testUpdate() {
	// Get target record for update
	int id = 111;
	String prevFn = null;
	String prevLn = null;
	String query = "select assoc_id, assoc_first_name, assoc_last_name from assoc where assoc_id = " + id;
	try {
	    Statement stmt = this.con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
		id = rs.getInt("assoc_id");
		prevFn = rs.getString("assoc_first_name");
		prevLn = rs.getString("assoc_last_name");
	    }
	    rs.close();
	    stmt.close();
	}
	catch (SQLException e) {
	    e.printStackTrace();
	}
	
	// Perform Update
	String dml = "update assoc set assoc_first_name = 'Roy', assoc_last_name = 'Terrell' where assoc_id = " + id;
	try {
	    Statement stmt = this.con.createStatement();
	    int rc = stmt.executeUpdate(dml);
	    System.out.println("Total rows updated: " + rc);
	    stmt.close();
	}
	catch (SQLException e) {
	    e.printStackTrace();
	}
	
	// Verify the update
	try {
	    Statement stmt = this.con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
		int checkId = rs.getInt("assoc_id");
		String checkFn = rs.getString("assoc_first_name");
		String checkLn = rs.getString("assoc_last_name");
		System.out.println("Update verification - Id: " + checkId + ", First Name: " + checkFn + ", Last Name: " + checkLn);
	    }
	    rs.close();
	    stmt.close();
	}
	catch (SQLException e) {
	    e.printStackTrace();
	}
    
    
	// Restore record to previous state
	dml = "update assoc set assoc_first_name = \'" + prevFn + "\' , assoc_last_name = \'" + prevLn + "\' where assoc_id = " + id;
	try {
	    Statement stmt = this.con.createStatement();
	    int rc = stmt.executeUpdate(dml);
	    System.out.println("Total rows updated: " + rc);
	    stmt.close();
	}
	catch (SQLException e) {
	    e.printStackTrace();
	    try {
		this.con.rollback();
	    }
	    catch (SQLException e1) {
		e1.printStackTrace();
	    }
	}
	
	// Verify record restore
	try {
	    Statement stmt = this.con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
		int checkId = rs.getInt("assoc_id");
		String checkFn = rs.getString("assoc_first_name");
		String checkLn = rs.getString("assoc_last_name");
		System.out.println("Restore verification - Id: " + checkId + ", First Name: " + checkFn + ", Last Name: " + checkLn);
	    }
	    rs.close();
	    stmt.close();
	}
	catch (SQLException e) {
	    e.printStackTrace();
	}

	// Commit unit of work
	try {
	    this.con.commit();
	}
	catch (SQLException e) {
	    e.printStackTrace();
	}
	
    } // end test
    
    
}
