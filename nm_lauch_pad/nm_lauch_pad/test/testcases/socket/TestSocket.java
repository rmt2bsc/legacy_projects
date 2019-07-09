/**
 * 
 */
package testcases.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.net.SocketFactory;

import modules.report.ReportRequestWorker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nv.util.AppUtil;
import com.nv.util.GeneralUtil;

/**
 * @author rterrell
 *
 */
public class TestSocket {
    private String serverName;

    private int port;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	Properties prop = GeneralUtil.loadProperties(AppUtil.CONFIG_COMMON_FILE);
	this.serverName = prop.getProperty("report.socket.serverName");
	String portStr = prop.getProperty("report.socket.port");
	this.port = Integer.parseInt(portStr);

    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }
    
    /**
     * Since the test case is not executed in a Swing environment, this method will not 
     * execute the {@link ReportRequestWorker} class properly and serves only as model 
     * for setting up the report worker.  The Report worker class inherits 
     * {@link javax.swing.SwingWorker} which in turns requires the use of the Swings' 
     * Event Dispatch Thread 
     */
    @Test
    public void testReportWorker() {
	String request = "0024audit-daily D 04/19/2012";
	ReportRequestWorker worker = new ReportRequestWorker(request, null);
	worker.execute();
	return;
    }

    @Test
    public void getReportPerCharacter() {
	SocketFactory f = SocketFactory.getDefault();
	Socket s = null;
	try {
	    PrintWriter out = null;
	    BufferedReader in = null;

	    try {
		s = f.createSocket(this.serverName, this.port);
		out = new PrintWriter(s.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
	    }
	    catch (UnknownHostException e) {
		System.err.println("Don't know about host: " + this.serverName);
		System.exit(1);
	    }
	    catch (IOException e) {
		System.err.println("Couldn't get I/O for the connection to: " + this.serverName);
		System.exit(1);
	    }

	    //	    out.println("0024audit-daily D 04/19/2012");
	    out.println("0024audit-daily D 04/19/2012");

	    StringBuffer rptBuf = new StringBuffer();

	    int inVal = 0;
	    while ((inVal = in.read()) != -1) {
		char c = (char) inVal;
		rptBuf.append(c);
	    }
	    GeneralUtil.outputFile(rptBuf.toString(), "C:\\temp\\LaunchPadReportByChar.txt");
	    out.close();
	    in.close();
	    s.close();
	}
	catch (UnknownHostException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    @Test
    public void getReportPerLine() {
	SocketFactory f = SocketFactory.getDefault();
	Socket s = null;
	try {
	    PrintWriter out = null;
	    BufferedReader in = null;

	    try {
		s = f.createSocket(this.serverName, this.port);
		out = new PrintWriter(s.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
	    }
	    catch (UnknownHostException e) {
		System.err.println("Don't know about host: " + this.serverName);
		System.exit(1);
	    }
	    catch (IOException e) {
		System.err.println("Couldn't get I/O for the connection to: " + this.serverName);
		System.exit(1);
	    }

	    //	    out.println("0024audit-daily D 04/19/2012");
	    out.println("0024audit-daily D 04/19/2012");

	    StringBuffer rptBuf = new StringBuffer();
	    String lineData = null;
	    while ((lineData = in.readLine()) != null) {
		if (lineData.equalsIgnoreCase("@@@EndOfReport")) {
		    continue;
		}
		if (lineData.equalsIgnoreCase("@@@StartOfReport")) {
		    System.out.println("Receiving Output...");
		}
		else {
		    rptBuf.append(lineData);
		}
	    }
	    
	    GeneralUtil.outputFile(rptBuf.toString(), "C:\\temp\\LaunchPadReportByLine.txt");
	    out.close();
	    in.close();
	    s.close();
	}
	catch (UnknownHostException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
