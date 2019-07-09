package filehandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.api.db.MimeValidationException;
import com.bean.Content;
import com.bean.db.DatabaseConnectionBean;
import com.util.RMT2File;

/**
 * @author appdev
 *
 */
public class CommonTextMimeTests {
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
    public void testAddFetchDeleteTextContent() {
	// Add Content
	int uid = this.addTextRecord("test.xml");
	// Fetch added content
	Content rec = this.fetchRecord(uid);
	// Remove most recent content
	int rows = this.deleteRecord(rec.getContentId());
	System.out.println("Total number of records added, fetch, and deleted for text content: " + rows);
	return;
    }
    
    @Test
    public void testAddWithIncorrectFile() {
	// Add Content
	int uid = this.addTextRecord("Solid.mp3");
	Assert.assertEquals(-3, uid);
	return;
    }

    
    
    private int addTextRecord(String fileName) {
	fileName = this.path + fileName;
	File file = new File(fileName);
	long size = file.length();
	String name = file.getName();

	Content mime = new Content();
	mime.setImageData(null);
	mime.setTextData(null);
	mime.setSize((int) size);
	mime.setFilename(name);
	mime.setFilepath(this.path);

	MimeContentApi api  = MimeFactory.getMimeContentApiInstance("test.filehandler.db.DefaultTextContentImpl");
	api.initApi(this.con);
	try {
	    try {
		int uid = api.addContent(mime);
		this.con.getDbConn().commit();
		return uid;
	    }
	    catch (BatchFileException e) {
		this.con.getDbConn().rollback();
		return -2;
	    }
	    catch (MimeValidationException e) {
		this.con.getDbConn().rollback();
		return -3;
	    }
	}
	catch (SQLException e) {
	    return -4;
	}
    }

    private Content fetchRecord(int uid) {
	Content result = null;
	MimeContentApi api  = MimeFactory.getMimeContentApiInstance("test.filehandler.db.DefaultTextContentImpl");
	api.initApi(this.con);
	try {
	    result = (Content) api.fetchContent(uid);
	}
	catch (BatchFileException e) {
	    e.printStackTrace();
	    return null;
	}

	try {
	    byte buffer[] = null;
	    if (result.getTextData() != null && !result.getTextData().equals("")) {
		buffer = result.getTextData().getBytes();
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
	MimeContentApi api  = MimeFactory.getMimeContentApiInstance("test.filehandler.db.DefaultTextContentImpl");
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
