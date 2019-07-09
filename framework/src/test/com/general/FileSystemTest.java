package com.general;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import junit.framework.Assert;

import org.junit.Test;

import com.util.NotFoundException;
import com.util.RMT2File;
import com.util.RMT2FileDirectoryFilter;
import com.util.RMT2Utility;
import com.util.SystemException;

public class FileSystemTest {

    @Test
    public void testDOSCopyFile() {
	String cmd = "xcopy /Y \\\\rmtdaldev04\\data\\mime\\out\\acct_cd_11174.jpg \\\\rmtdaldb01\\archives\\mime";
	String msg = RMT2Utility.execShellCommand(cmd);
	System.out.println(msg);
	
	cmd = "xcopy /Y \\\\rmtdaldev04\\data\\mime\\archive\\acct_cd_11174.jpg \\\\rmtdaldb01\\archives\\mime";
	msg = RMT2Utility.execShellCommand(cmd);
	System.out.println(msg);
	return;
    }
    
    @Test
    public void testFile() {
	String fileName = "//rmtdaldev04/data/mime/archive/test.txt";
	File file = new File(fileName);
	String path = file.getPath();
	String fn = file.getName();
	Assert.assertNotNull(path);
	Assert.assertNotNull(fn);
	return;
    }
    
    @Test
    public void testDir() {
	String dirName = "";
	File dir = new File(dirName);
	RMT2File.verifyDirectory(dir);
	
	dirName = "\\\\\\Rmtfmsys01\\mime";
	dir = new File(dirName);
	RMT2File.verifyDirectory(dir);
	
	dirName = "//rmtdaldev04/data/mime/archive/";
	dir = new File(dirName);
	RMT2File.verifyDirectory(dir);
	
	boolean result = RMT2File.isNetworkShareAccessible(dirName);
	Assert.assertTrue(result);
	
	dirName = "//rmtdaldev04/tmp/";
	result = RMT2File.isNetworkShareAccessible(dirName);
	Assert.assertFalse(result);
	
	try {
	    result = RMT2File.isNetworkShareAccessible(null);    
	}
	catch (Exception e) {
	    result = false;
	}
	Assert.assertFalse(result);
	
	return;
    }

    @Test
    public void testListFiles() {
	File folder = new File("c:/tmp");
	File[] listOfFiles = folder.listFiles();

	for (int i = 0; i < listOfFiles.length; i++) {
	    if (listOfFiles[i].isFile()) {
		System.out.println("File " + listOfFiles[i].getName());
	    }
	    else if (listOfFiles[i].isDirectory()) {
		System.out.println("Directory " + listOfFiles[i].getName());
	    }
	}
    }

    @Test
    public void testListFilesUsingWildcard() {
	File folder = new File("C:\\projects\\framework\\Data\\test");
	RMT2FileDirectoryFilter filter = new RMT2FileDirectoryFilter("acct_??_*.*;CR_*.sql");
	File[] listOfFiles = folder.listFiles(filter);

	for (int i = 0; i < listOfFiles.length; i++) {
	    if (listOfFiles[i].isFile()) {
		System.out.println("File " + listOfFiles[i].getName());
	    }
	    else if (listOfFiles[i].isDirectory()) {
		System.out.println("Directory " + listOfFiles[i].getName());
	    }
	}
    }

    @Test
    public void testListFilesUsingEmptyWildcard() {
	File folder = new File("C:\\projects\\framework\\Data\\test");
	RMT2FileDirectoryFilter filter = new RMT2FileDirectoryFilter();
	File[] listOfFiles = folder.listFiles(filter);

	for (int i = 0; i < listOfFiles.length; i++) {
	    if (listOfFiles[i].isFile()) {
		System.out.println("File " + listOfFiles[i].getName());
	    }
	    else if (listOfFiles[i].isDirectory()) {
		System.out.println("Directory " + listOfFiles[i].getName());
	    }
	}
    }

    @Test
    public void testFileListUtilityWithWildcards() {
	List<String> listing = RMT2File.getDirectoryListing("C:\\projects\\framework\\Data\\test", "acct_??_*.*;CR_*.sql");
	Assert.assertTrue(listing.size() == 3);
    }

    @Test
    public void testFileListUtility() {
	List<String> listing = RMT2File.getDirectoryListing("C:\\projects\\framework\\Data\\test", null);
	Assert.assertTrue(listing.size() > 3);
    }

    @Test
    public void testFileListUtilityDirectoryNotFound() {
	try {
	    RMT2File.getDirectoryListing("C:\\projects\\framework\\NonExistingDirectory", null);
	}
	catch (NotFoundException e) {
	    Assert.assertEquals(RMT2File.FILE_IO_NOTEXIST, e.getErrorCode());
	    return;
	}
	Assert.fail("Test failed due to an exception should have occurred regarding directory not found");
    }

    @Test
    public void testFileListUtilityDirectoryAsFile() {
	try {
	    RMT2File.getDirectoryListing("C:\\projects\\framework\\Data\\test\\Camera.wav", null);
	}
	catch (SystemException e) {
	    Assert.assertEquals(RMT2File.FILE_IO_NOT_DIR, e.getErrorCode());
	    return;
	}
	Assert.fail("Test failed due to an exception should have occurred regarding a file was loaded instead of a directory");
    }

    @Test
    public void testPropertisFileLoadByClassPath() {
	Properties props;
	String result;
	try {
	    props = RMT2File.loadPropertiesObject("C:/projects/framework/src/java/com/api/xml/RMT2NamespaceContext.properties");
	    result = props.getProperty("rmt2-hns");
	    System.out.println(result);
	}
	catch (Exception e) {
	    // Error
	}

	ResourceBundle rb;
	rb = RMT2File.loadAppConfigProperties("com.api.xml.RMT2NamespaceContext");
	result = rb.getString("rmt2-hns");
	System.out.println(result);

    }

    @Test
    public void testMimeTypeIdentification() {
	String path = "C:/projects/framework/Data/test/";
	String mimeType = RMT2File.getMimeType(path + "acct_cd_11783.jpg");
	mimeType = RMT2File.getMimeType(path + "Camera.wav");
	mimeType = RMT2File.getMimeType(path + "timesheet_20091220.pdf");
	mimeType = RMT2File.getMimeType(path + "VikingHorn_vbr.mp3");
	mimeType = RMT2File.getMimeType(path + "word_test.doc");
	mimeType = RMT2File.getMimeType(path + "test.xml");

	File file = new File(path + "acct_cd_11783.jpg");
	long size = file.length();
	String name = file.getName();
	return;
    }

    @Test
    public void testLocateFilenameExtension() {
	String path = "C:/projects/framework/Data/test/";
	String ext = RMT2File.getfileExt(path + "acct_cd_11783.jpg");
	ext = RMT2File.getfileExt(path + "Camera.wav");
	ext = RMT2File.getfileExt(path + "timesheet_20091220.pdf");
	ext = RMT2File.getfileExt(path + "VikingHorn_vbr.mp3");
	ext = RMT2File.getfileExt(path + "word_test.doc");
	ext = RMT2File.getfileExt(path + "test.xml");
    }

}
