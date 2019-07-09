package com.io.file;


import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.util.RMT2File;

/**
 * @author Roy Terrell
 *
 */
public class TestDirecotryCreation {
    private String existingDir ;
    private String newSingleDir;
    private String newMultiDir;
    private String newMultiDirWithDrive;
    private String multiDirDelete;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	existingDir = "c:\\tmp\\";
	newSingleDir = "c:\\tmp\\new_single_dir\\";
	newMultiDirWithDrive = "c:\\new_multi_dir\\sub_dir_1\\sub_dir_2\\sub_dir_3\\";
	newMultiDir = "\\new_multi_dir\\sub_dir_1\\sub_dir_2\\sub_dir_3\\";
	multiDirDelete = "\\new_multi_dir";
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
	File file1 = new File(this.newSingleDir);
	File file2 = new File(this.multiDirDelete);
	file1.delete();
	int rc = RMT2File.deleteFile(file2);
//	Assert.assertTrue(rc > 0);
	System.out.println("total number of multiple directories deleted: " + rc);
    }

    
    @Test
    public void testCreateExistingSingleDir() {
	boolean result = RMT2File.createDirectory(this.existingDir);
	Assert.assertTrue(result);
    }
    
    @Test
    public void testCreateNewSingleDir() {
	boolean result = RMT2File.createDirectory(this.newMultiDirWithDrive);
	Assert.assertFalse(result);
	result = RMT2File.createDirectory(this.newSingleDir);
	Assert.assertTrue(result);
    }
    
    @Test
    public void testCreateNewDirWithSubDirectories() {
	boolean result = RMT2File.createDirectories(this.newSingleDir);
	Assert.assertTrue(result);
	result = RMT2File.createDirectories(this.newMultiDirWithDrive);
	Assert.assertTrue(result);
	result = RMT2File.createDirectories(this.newMultiDir);
	Assert.assertTrue(result);
    }
    
    @Test
    public void testFileParsingWithoutDrive() {
	String filename = "c:\\tmp\\changes\\test.txt";
	String result = RMT2File.getFullFilePathWithoutDrive(filename);
	System.out.println(result);
    }
}
