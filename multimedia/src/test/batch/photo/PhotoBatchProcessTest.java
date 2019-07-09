package batch.photo;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.api.BatchFileProcessor;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.bean.db.DatabaseConnectionBean;
import com.photo.PhotoFactory;

/**
 * @author appdev
 *
 */
public class PhotoBatchProcessTest {

    private DatabaseConnectionBean con;



    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
//	JdbcFactory.setupSystemProperties("TestSystemParms");
//	String dbUrl = RMT2File.getPropertyValue("TestSystemParms", "dburl");
//	this.con = JdbcFactory.getConnection(dbUrl, "TestSystemParms.properties");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
//	this.con.close();
	this.con = null;
    }

    @Test
    public void startBatch() {
	DatabaseTransApi tx = DatabaseTransFactory.create("TestSystemParms");
	BatchFileProcessor api = PhotoFactory.createFileProcessor(tx);
	int count = 0;
	try {
	    count = api.processBatch();
	    tx.commitUOW();
	}
	catch (Throwable e) {
	    tx.rollbackUOW();
	    e.printStackTrace();
	}
	System.out.println("Total number of files to process: " + count);
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
