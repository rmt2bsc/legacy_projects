package filehandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.SQLException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.api.BatchFileException;
import com.api.db.DatabaseException;
import com.api.db.JdbcFactory;
import com.api.db.MimeContentApi;
import com.api.db.MimeFactory;

import com.bean.Content;

import com.bean.db.DatabaseConnectionBean;

import com.util.RMT2File;

/**
 * @author appdev
 *
 */
public class SybaseSQLAnywhereMimeTests {
    private DatabaseConnectionBean con;

    private String path;

   

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	JdbcFactory.setupSystemProperties();
	String dbUrl = RMT2File.getPropertyValue("SystemParms", "dburl");
	this.con = JdbcFactory.getConnection(dbUrl);
	this.path = "C:/projects/mime/Data/test/";

	return;
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
	this.con.close();
	this.con = null;
	return;
    }

    @Test
    public void testAddFetchDeleteBinaryContent() {
	// Add Content
	int uid = this.addBinaryRecord();
	// Fetch added content
	Content rec = this.fetchRecord(uid);
//	Content rec = this.fetchRecord(8);
	// Remove most recent content
	int rows = this.deleteRecord(rec.getContentId());
	System.out.println("Total number of records added, fetch, and deleted for binary content: " + rows);
	return;
    }
    

    private int addBinaryRecord() {
	String fileName = null;
	fileName = this.path + "Solid.mp3";
//	fileName = this.path + "svdl_345_punishing_the_paparazzi.wmv";
	File file = new File(fileName);
	long size = file.length();
	String name = file.getName();

	Content mime = new Content();
	mime.setImageData(null);
	mime.setSize((int) size);
	mime.setFilename(name);
	mime.setFilepath(this.path);

	MimeContentApi api  = MimeFactory.getMimeContentApiInstance("test.filehandler.db.DefaultSybASABinaryImpl");
	api.initApi(this.con);
	try {
	    try {
		int uid = api.addContent(mime);
		this.con.getDbConn().commit();
		return uid;
	    }
	    catch (BatchFileException e) {
		this.con.getDbConn().rollback();
		e.printStackTrace();
		return -2;
	    }
	}
	catch (SQLException e) {
	    e.printStackTrace();
	    return -3;
	}
    }
    

    private Content fetchRecord(int uid) {
	Content result = null;
	MimeContentApi api  = MimeFactory.getMimeContentApiInstance("test.filehandler.db.DefaultSybASABinaryImpl");
	api.initApi(this.con);
	try {
	    result = (Content) api.fetchContent(uid);
	}
	catch (BatchFileException e) {
	    e.printStackTrace();
	    return null;
	}

	try {
	    InputStream imageIs = (InputStream) result.getImageData();
	    byte buffer[] = null;
	    if (imageIs != null) {
		buffer = RMT2File.getStreamByteData(imageIs);
	    }
	    
	    String fileExt = RMT2File.getfileExt(result.getFilename());
	    File file = new File("C:\\tmp\\test" + fileExt);
	    try {
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(buffer);
		fos.close();
	    }
	    catch (FileNotFoundException e) {
		return null;
	    }
	    catch (IOException e) {
		e.printStackTrace();
		return null;
	    }
	    Assert.assertNotNull(result);
	    return result;
	}
	catch (DatabaseException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    private int deleteRecord(int uid) {
	int rows = 0;
	MimeContentApi api  = MimeFactory.getMimeContentApiInstance("test.filehandler.db.DefaultSybASABinaryImpl");
	api.initApi(this.con);
	try {
	    try {
		rows = api.deleteContent(uid);
		this.con.getDbConn().commit();
		return rows;
	    }
	    catch (BatchFileException e) {
		e.printStackTrace();
		this.con.getDbConn().rollback();
		return -1;
	    }
	}
	catch (SQLException e) {
	    e.printStackTrace();
	    return -3;
	}
    }
}
