/**
 * 
 */
package com.zipfile;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.util.RMT2File;
import com.util.RMT2ZipFileManager;

/**
 * @author WinXP User
 *
 */
public class ZipFileTest {
    private String inDir;
    private String outDir;
    private String zipFileExisting;
    private String rarFileExisting;
    private String newZipFile;
    

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	this.inDir = "C:\\projects\\framework\\src\\test\\data\\";
	this.outDir = "c:\\tmp\\zip_file_test\\";
	this.zipFileExisting = "zip_file_test.zip";
	this.rarFileExisting = "rar_file_test.rar";
	this.newZipFile = "new_zip_file.zip";
	boolean result = RMT2File.createDirectories(this.outDir);
	Assert.assertTrue(result);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
	this.inDir = null;
	this.outDir = null;
	this.zipFileExisting = null;
	this.rarFileExisting = null;
	this.newZipFile = null;
//	RMT2File.deleteFile(this.outDir);
    }

    
    @Test
    public void testUnzipZipFile() {
	RMT2ZipFileManager mgr = new RMT2ZipFileManager(this.inDir + this.zipFileExisting);
	int count = mgr.getZipEntryCount();
	List list = mgr.getZipEntries();
	Assert.assertTrue(count == list.size());
	mgr.close();
    }
    
    @Test
    public void testCreateZipFile() {
	RMT2ZipFileManager mgr = new RMT2ZipFileManager(this.outDir + this.newZipFile);
	mgr.setFilePathType(RMT2ZipFileManager.FILEPATH_FULL);
	File file1 = new File(this.inDir + "CreditorView.xml");
	File file2 = new File(this.inDir + "sales_order.xml");
	File file3 = new File("C:\\projects\\framework\\src\\test\\data\\" + "AbstractSecureCommandServlet User Guide.doc");
	File dir = new File("c:\\tmp");
	
//	mgr.addEntry(file1.getName(), file1);
//	mgr.addEntry(file2.getName(), file2);
//	mgr.addEntry(file3.getName(), file3);
//	mgr.addEntry(dir.getName(), dir);
	
	mgr.addEntry(file1.getAbsolutePath(), file1);
	mgr.addEntry(file2.getAbsolutePath(), file2);
	mgr.addEntry(file3.getAbsolutePath(), file3);
	
	mgr.addEntry(dir.getName(), dir);
	mgr.close();
    }
}
