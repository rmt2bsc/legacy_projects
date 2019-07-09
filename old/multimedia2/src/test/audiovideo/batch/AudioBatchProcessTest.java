package audiovideo.batch;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.api.db.JdbcFactory;
import com.audiovideo.AudioVideoBatchUpdateApi;
import com.audiovideo.AudioVideoException;
import com.audiovideo.AudioVideoFactory;
import com.audiovideo.AvBatchException;
import com.bean.db.DatabaseConnectionBean;
import com.util.RMT2File;

/**
 * @author appdev
 *
 */
public class AudioBatchProcessTest {
    private DatabaseConnectionBean con;

//        private String rippedDir = "//rmtdaldev04/data/audio/ripped";
    private String rippedDir = "//Rmtdaldb01/multimedia/audio/ripped";

        private String nonRippedDir = "//rmtdaldev04/data/audio/non_ripped";
//    private String nonRippedDir = "//Rmtdaldb01/multimedia/audio/non_ripped";

//    private String allDir = "//Rmtdaldb01/multimedia/audio";
    private String allDir = "//rmtdaldev04/data/audio";
        // This works when the computer is acting as a stand alone machine that is not connected to a network.
//    private String allDir = "c:/data/audio";

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	JdbcFactory.setupSystemProperties("test.resources.SystemParms");
	String dbUrl = RMT2File.getPropertyValue("test.resources.SystemParms", "dburl");
	this.con = JdbcFactory.getConnection(dbUrl, "test.resources.SystemParms");
	
	System.setProperty("mail.host.smtp", "outgoing.verizon.net");
	System.setProperty("mail.authentication", "true");
	System.setProperty("mail.userId", "rmt2bsc2");
	System.setProperty("mail.password", "drum7777");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
	this.con.close();
	this.con = null;
    }

    @Test
    public void calculateFiles() {
	AudioVideoBatchUpdateApi api = AudioVideoFactory.createBatchApi(this.con, this.allDir);
	File dir = new File(this.allDir);
	int count = api.computeTotalFileCount(dir);
	System.out.println("Total number of files to process: " + count);
    }
//    @Test
    public void testPurge() {
	AudioVideoBatchUpdateApi api = AudioVideoFactory.createBatchApi(this.con, this.rippedDir);
	try {
	    api.purge(1);
	    this.commitWork();
	}
	catch (AudioVideoException e) {
	    this.rollbackWork();
	    e.printStackTrace();
	}
	finally {
	    api.close();
	    api = null;
	}
    }

//    @Test
    public void testRipped() {
	AudioVideoBatchUpdateApi api = AudioVideoFactory.createBatchApi(this.con, this.rippedDir);
	try {
	    api.processBatch();
	    this.commitWork();
	}
	catch (Exception e) {
	    this.rollbackWork();
	    e.printStackTrace();
	}
	finally {
	    api.close();
	    api = null;
	}
    }

//    @Test
    public void testNonRipped() {
	AudioVideoBatchUpdateApi api = AudioVideoFactory.createBatchApi(this.con, this.nonRippedDir);
	try {
	    api.processBatch();
	    this.commitWork();
	}
	catch (Exception e) {
	    this.rollbackWork();
	    e.printStackTrace();
	}
	finally {
	    api.close();
	    api = null;
	}
    }

//        @Test
        public void testAll() {
    	this.testPurge();
    	AudioVideoBatchUpdateApi api = AudioVideoFactory.createBatchApi(this.con, this.allDir);
    	try {
    	    api.processBatch();
    	    this.commitWork();
    	}
    	catch (Exception e) {
    	    this.rollbackWork();
    	    e.printStackTrace();
    	}
    	finally {
    	    api.close();
    	    api = null;
    	}
        }

    private void commitWork() {
	try {
	    this.con.getDbConn().commit();
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private void rollbackWork() {
	try {
	    this.con.getDbConn().rollback();
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
