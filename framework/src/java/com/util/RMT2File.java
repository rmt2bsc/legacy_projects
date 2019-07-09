package com.util;

import javax.activation.MimetypesFileTypeMap;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.NotSerializableException;
import java.io.StreamCorruptedException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Properties;
import java.util.MissingResourceException;

import com.util.SystemException;

/**
 * Class contains a collection general purpose File I/O utilities.
 * 
 * @author roy.terrell
 *
 */
public class RMT2File {
    private static Logger logger = Logger.getLogger("RMT2File");

    /** File exists code */
    public static final int FILE_IO_EXIST = 1;

    /** File does not exist code */
    public static final int FILE_IO_NOTEXIST = -1;

    /** file is null */
    public static final int FILE_IO_NULL = 0;

    /** File is inaccessible */
    public static final int FILE_IO_INACCESSIBLE = -2;

    /** File object is not a file */
    public static final int FILE_IO_NOT_FILE = -3;

    /** File object is not a directory */
    public static final int FILE_IO_NOT_DIR = -4;

    /**
     * Searches the client's workstation for the MIME Type of a file represented by "_fileName".   
     * Returns the MIME type of the file.
     * 
     * @param _fileName The name of the file which MIME type is to be found.
     * @return The MIME type name.
     */
    public static final String getMimeType(String _fileName) {
        MimetypesFileTypeMap typMap = new MimetypesFileTypeMap();
        String mimeType = typMap.getContentType(_fileName);
        return mimeType;
    }

    /**
     * Extracts the the extension from the file name.
     *  
     * @param _fileName
     *          The filename to examine
     * @return String
     *          the extension of the file including the preceding "." character.
     *          Returns null if <i>_fileName</i> is null or if the file extension does not exist.
     */
    public static final String getfileExt(String _fileName) {
        if (_fileName == null) {
            return null;
        }
        int pos = _fileName.lastIndexOf(".");
        if (pos >= 0) {
            String ext = _fileName.substring(pos);
            return ext;
        }
        return null;
    }

    /**
     * 
     * @param fileName
     * @return
     */
    public static final String getFullFilePathWithoutDrive(String fileName) {
        if (fileName == null) {
            return null;
        }
        int pos = fileName.lastIndexOf(":");
        if (pos >= 0) {
            String path = fileName.substring(pos + 1);
            return path;
        }
        return null;
    }
    
    /**
     * Tests if a file name follows the UNC naming convention.
     * 
     * @param filename
     *         The file name to be tested
     * @return true when file name is UNC and false otherwise.
     */
    public static final boolean isFilenameUNCStyle(String filename) {
	String [] results = filename.split("//");
	if (results == null || results.length == 1) {
	    return false;
	}
	return true;
    }
    
    /**
     * Parses an UNC file name into separate URI components as a String array.
     * <p>
     *  In order for <i>filename</i> to be processed successfully, it must 
     *  contain a path and filename that conforms to UNC nameing convention.   
     *  For example, //mma-71801207728/TestData/images/tank.jpg.
     *   
     * @param filename
     *          The file name that is to be parsed.
     * @return String [] 
     *           An array of Strings representing the components of the UNC style file name.   
     *           The first element will represent the Host name, the second element is the 
     *           share name, and the remaining elements, if any, will represent the path.
     *           Returns null when <i>filename</i> is either equal to null or is not of the 
     *           UNC standard. 
     */
    public static final String [] getUNCFilename(String filename) {
	if (filename == null) {
	    return null;
	}
	String [] results2 = null;
	if (RMT2File.isFilenameUNCStyle(filename)) {
	    String [] results = filename.split("//");
	    results2 = results[1].split("/");
	}
	return results2;
    }
    
    
    /**
     * Verifies that _pathname is a valid Directory or File.
     * 
     * @param _pathName The file path.
     * @return 1=File Exist, -1=File does not exsit, -2=File inaccessible, 
     *         and 0= Argument is an empty String or null.
     */
    public static int verifyFile(String _pathName) {
        File path;

        try {
            path = new File(_pathName);
        }
        catch (NullPointerException e) {
            return FILE_IO_NULL;
        }

        // Validate the existence of path
        try {
            if (!path.exists()) {
                return FILE_IO_NOTEXIST;
            }
            return FILE_IO_EXIST;
        }
        catch (Exception e) {
            return FILE_IO_INACCESSIBLE;
        }
    }

    /**
     * Verifies that _file is a valid File.
     * 
     * @param _pathName The file path.
     * @return 1=File Exist, -1=File does not exsit, -2=File inaccessible, -3=Not a File
     */
    public static int verifyFile(File _file) {
        // Validate the existence of path
        try {
            if (!_file.exists()) {
                return FILE_IO_NOTEXIST;
            }
            if (!_file.isFile()) {
                return RMT2File.FILE_IO_NOT_FILE;
            }
            if (!_file.canRead()) {
                return FILE_IO_INACCESSIBLE;
            }
            if (!_file.canWrite()) {
                return FILE_IO_INACCESSIBLE;
            }
            return FILE_IO_EXIST;
        }
        catch (Exception e) {
            return FILE_IO_INACCESSIBLE;
        }
    }

    /**
     * Verifies that _file is a valid Directory.
     * 
     * @param dir 
     *          The file path.
     * @return int 
     *          1=File Exist, -1=File does not exsit, -2=File inaccessible, -4=Not a File.
     */
    public static int verifyDirectory(File dir) {
        // Validate the existence of path
        try {
            if (!dir.exists()) {
                return FILE_IO_NOTEXIST;
            }
            if (!dir.isDirectory()) {
                return RMT2File.FILE_IO_NOT_DIR;
            }
            return FILE_IO_EXIST;
        }
        catch (Exception e) {
            return FILE_IO_INACCESSIBLE;
        }
    }

    /**
     * Tests if a network share is accessible by the current user.  The name of the network 
     * share must be in the UNC (Universal Naming Convention) PC format.
     * 
     * @param dir
     *          the directory representing the network share in UNC format.
     * @return  boolean
     *          true if user possesses the correct permissions to access share.  Otherwise, 
     *          false is returned when the user does not have permission to access the network 
     *          share.
     * @throws SystemException
     *          if <i>dir</i> equals null or if the resource(s) pertaining to <i>dir</i> cannot 
     *          be released after performing tests.
     */
    public static boolean isNetworkShareAccessible(String dir) {
        if (dir == null) {
            String msg = "dir value is invalid or null";
            logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        String testFile = "test.txt";
        if (dir.endsWith("/")) {
            testFile = dir + testFile;
        }
        else {
            testFile = dir + "/" + testFile;
        }

        // Use a temporary output file stream stemmed from "dir" to 
        // test if directory is accessible by the current user.
        FileOutputStream to = null;
        try {
            File path = new File(testFile);
            to = new FileOutputStream(path);
            return true;
        }
        catch (Exception e) {
            logger.log(Level.ERROR, "Directory, " + dir + ", is inaccessible");
            return false;
        }
        finally {
            try {
                if (to != null) {
                    to.close();
                    to = null;
                }
            }
            catch (IOException e) {
                String msg = "Problem closing directory output stream during accessiblity test";
                logger.log(Level.ERROR, msg);
                throw new SystemException(msg);
            }
        }
    }

    /**
     * Accesses the contents of a plain text file and returns the contents as a String to the caller.
     * 
     * @param _filename The file name.
     * @return The contents of the file as a String.
     * @throws SystemException if _filename is null, does not exist, or is inaccessible.
     */
    public static String getTextFileContents(String _filename) throws SystemException {
        String msg;
        try {
            StringBuffer fileContent = new StringBuffer(100);
            String temp = null;

            switch (RMT2File.verifyFile(_filename)) {
            case FILE_IO_NULL:
                msg = "Argument [_filename] is null or empty";
                RMT2File.logger.log(Level.ERROR, msg);
                throw new SystemException(msg);
            case FILE_IO_NOTEXIST:
                msg = "File does not exist: " + _filename;
                RMT2File.logger.log(Level.ERROR, msg);
                throw new SystemException(msg);
            case FILE_IO_INACCESSIBLE:
                msg = "File is inaccessible: " + _filename;
                RMT2File.logger.log(Level.ERROR, msg);
                throw new SystemException(msg);
            }

            //  At this point we have a file that exist and is accessible.
            FileReader fr = new FileReader(_filename);
            BufferedReader buf = new BufferedReader(fr);

            while ((temp = buf.readLine()) != null) {
                fileContent.append(temp);
            }
            return fileContent.toString();
        }
        catch (FileNotFoundException e) {
            msg = "File Not Found IO Exception for " + _filename;
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        catch (IOException e) {
            msg = "File IO Exception for " + _filename;
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
    }

    /**
     * Loads a property file that can be found in the classpath.
     * 
     * @param _fileName The name of the resourc bundle to load.  The file name cannot be null.
     * @return The resource bundle
     * @throws SystemException When _fileName cannot be found. a problem exist casting _fileName
     *         into a ResourceBundle, or the _fileName is null.
     */
    public static ResourceBundle loadAppConfigProperties(String _fileName) throws SystemException {
        ResourceBundle bundle;

        try {
            bundle = ResourceBundle.getBundle(_fileName);
            return bundle;
        }
        catch (MissingResourceException e) {
            throw new SystemException("The properties file: " + _fileName + " could not be found.");
        }
        catch (ClassCastException e) {
            throw new SystemException("The properties file: " + _fileName + " could not be cast.");
        }
        catch (NullPointerException e) {
            throw new SystemException("The resource bundle name is null.");
        }
    }

    /**
     * Creates a Properties object from a Properties file in the Classpath.
     * 
     * @param _propFile Properties file name.
     * @return Properties object.
     * @throws SystemException
     */
    public static Properties loadPropertiesObject(String _propFile) throws SystemException {
        InputStream is = null;
        Properties properties = null;
        String msg;

        is = RMT2File.getFileInputStream(_propFile);
        if (is == null) {
            msg = "Unable to obtain an InputStream instance for properties file: " + _propFile;
            throw new SystemException(msg);
        }
        properties = new Properties();
        try {
            properties.load(is);
            return properties;
        }
        catch (IOException e) {
            msg = "An error occurred when reading from the input stream for file, " + _propFile;
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
    }

    /**
     * Converts a ResourceBundle to a Properties object using the bundle's file name as the source.
     * 
     * @param fileName The file name of the ResourceBundle.
     * @return Properties
     * @throws SystemException Error obtaining a instance of ResourceBundle.
     */
    public static Properties convertResourceBundleToProperties(String fileName) throws SystemException {
        ResourceBundle bundle = loadAppConfigProperties(fileName);
        return convertResourceBundleToProperties(bundle);
    }

    /**
     * Converts a ResourceBundle instance to a Properties object.
     * 
     * @param resource ResourceBundle.
     * @return Properties
     */
    public static Properties convertResourceBundleToProperties(ResourceBundle resource) {
        Enumeration iter;
        if (resource == null) {
            return null;
        }
        Properties props = new Properties();
        iter = resource.getKeys();
        while (iter.hasMoreElements()) {
            String key = (String) iter.nextElement();
            String value = resource.getString(key);
            props.put(key, value);
        }
        return props;
    }

    /**
     * Obtains the property value using keyCode from within the selected ResourceBundlee (bundleName)
     * 
     * @param bundleName 
     *         The name of the ResourceBundle to query.
     * @param keyCode 
     *         The key of the desired value.
     * @return String 
     *         The value of the key or null if <i>property</i> is not found
     * @throws SystemException
     * <p>
     * <ul>
     *   <li>The _bundleName is null</li>
     *   <li>The resource bundle cannot  be found in the classpath</li>
     *   <li>_keyCode is passed a null value</li>
     *   <li>Key (_keyCode) is not found in the resource bundle</li>
     *   <li>The value returned by _keyCode does not equate to be a String</li>
     * </ul>
     */
    public static String getPropertyValue(String bundleName, String keyCode) {
        ResourceBundle rb = null;
        String value = null;
        String msg;
        // Let's obtain the actual resource bundle file
        try {
            rb = ResourceBundle.getBundle(bundleName);
        }
        catch (NullPointerException e) {
            msg = "Resource Bundle value is null";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        catch (MissingResourceException e) {
            msg = "Resource Bundle, " + bundleName + ", cannot be found in classpath ";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }

        // Let's get the property value
        try {
            value = rb.getString(keyCode);
        }
        catch (NullPointerException e) {
            msg = "Key value was passed as null";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        catch (MissingResourceException e) {
            msg = "No value for key code, " + keyCode + ", of Resource Bundle file, " + bundleName + ", could not be found";
            return null;
        }
        catch (ClassCastException e) {
            msg = "Value returned for key code, " + keyCode + ", of Resource Bundle file, " + bundleName + ", is not a string";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        return value;
    }

    /**
     * Performs the same functionality as @see RMT2Utility.getgetPropertyValue(String, String) without throwing any exceptoins.
     * 
     * @param _bundleName
     * @param _keyCode
     * @return The value of key or null if key could not be found.
     */
    public static String getPropertyValueBuffered(String _bundleName, String _keyCode) {
        // Get the value of _keycode
        try {
            return RMT2File.getPropertyValue(_bundleName, _keyCode);
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Reads a disk file and return its contents as String.
     * 
     * @param filename The input file.
     * @return File contents as a String
     * @throws NotFoundException If filename does not exist in the file system
     * @throws SystemException General IO errors.
     */
    public static String inputFile(String filename) throws NotFoundException, SystemException {
        File sourceFile = new File(filename);
        if (!sourceFile.exists()) {
            throw new NotFoundException("Input File not found: " + filename);
        }

        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        int fileSize = new Long(sourceFile.length()).intValue();
        try {
            fis = new FileInputStream(sourceFile);
            baos = new ByteArrayOutputStream();
            byte buffer[];
            try {
                buffer = new byte[fileSize];
            }
            catch (OutOfMemoryError e) {
                throw new SystemException("Out of Memory error occured when attempting to allocate memory buffer for file input: " + filename);
            }
            int bytesRead = fis.read(buffer);
            while (bytesRead != -1) {
                baos.write(buffer);
                bytesRead = fis.read(buffer);
            }
            return baos.toString();
        }
        catch (IOException e) {
            throw new SystemException("IO Exception: " + e.getMessage());
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException e) {
                    ;
                }
            }
        }
    }

    /**
     * Writes String data to a disk file.
     * 
     * @param data  
     *          The String data to be written to disk
     * @param filename 
     *          The path name to where data is stored to disk.
     * @throws SystemException 
     *          file destination is not found or general IO error.
     */
    public static void outputFile(String data, String filename) throws SystemException {
        byte byteResulsts[] = data.getBytes();
        RMT2File.outputFile(byteResulsts, filename);
    }

    /**
     * Writes byte array to a disk file.
     * 
     * @param data
     *         the array of bytes to persist to file.
     * @param filename
     *         the output filename
     * @throws SystemException
     */
    public static void outputFile(byte data[], String filename) throws SystemException {
        File destFile = new File(filename);
        String msg;

        if (destFile.isDirectory()) {
            msg = "Output path must be a file instead of a directory";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }

        try {
            // Use an FileOutputStream to write data to disk.
            FileOutputStream fos = new FileOutputStream(destFile);
            fos.write(data);
            fos.flush();
            fos.close();
        }
        catch (FileNotFoundException e) {
            msg = "File was not found.  Check whether or not the directory structure exists";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        catch (NotSerializableException e) {
            msg = "Object is not serializable...Ensure that object implements java.io.Serializable interface";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        catch (IOException e) {
            msg = "General file IO error occurred";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
    }

    /**
     * Writes the content of input stream to a disk file.
     * 
     * @param data 
     *          The InputStream containing the data to be written to disk
     * @param filename 
     *          The path name to where data is stored to disk.
     * @throws SystemException 
     *             file destination is not found or general IO error.
     */
    public static void outputFile(InputStream data, String filename) throws SystemException {
        File destFile = new File(filename);
        String msg;

        if (destFile.isDirectory()) {
            msg = "Output path must be a file instead of a directory";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }

        try {
            byte byteResulsts[] = RMT2File.getStreamByteData(data);
            // Use an FileOutputStream to write data to disk.
            FileOutputStream fos = new FileOutputStream(destFile);
            fos.write(byteResulsts);
            fos.flush();
            fos.close();
        }
        catch (FileNotFoundException e) {
            msg = "File was not found.  Check whether or not the directory structure exists";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        catch (NotSerializableException e) {
            msg = "Object is not serializable...Ensure that object implements java.io.Serializable interface";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        catch (IOException e) {
            msg = "General file IO error occurred";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
    }

    /**
     * Creates a single directory.
     * 
     * @param dir
     * @return
     * @throws SystemException
     */
    public static boolean createDirectory(String dir) throws SystemException {
	 File newDir = new File(dir);
	 String msg;

	 if (newDir.exists()) {
	     if (newDir.isDirectory()) {
		 msg = "Directory, " + dir + ",  already exist";
		 RMT2File.logger.log(Level.WARN, msg);
	     }    
	     else if (newDir.isFile()){
		 msg = "Unable to create directory, " + dir + ",  because it already exists as a file";
		 RMT2File.logger.log(Level.ERROR, msg);
		 throw new SystemException(msg);
	     }
	     return true;
	 }
	 
	 try {
	     boolean result = newDir.mkdir();    
	     return result;
	 }
	 catch (Exception e) {
	     msg = "Problem creating directory, " + dir + ",  due to general exception";
	     RMT2File.logger.log(Level.ERROR, msg);
	     throw new SystemException(msg);
	 }
	 
    }
    
    /**
     * Creates multiple directories in one call.
     * 
     * @param dir
     * @return
     * @throws SystemException
     */
    public static boolean createDirectories(String dir) throws SystemException {
	 File newDir = new File(dir);
	 String msg;

	 if (newDir.exists()) {
	     if (newDir.isDirectory()) {
		 msg = "Directory, " + dir + ",  already exist";
		 RMT2File.logger.log(Level.WARN, msg);
	     }    
	     else if (newDir.isFile()){
		 msg = "Unable to create directory, " + dir + ",  because it already exists as a file";
		 RMT2File.logger.log(Level.ERROR, msg);
		 throw new SystemException(msg);
	     }
	     return true;
	 }
	 
	 try {
	     boolean result = newDir.mkdirs();    
	     return result;
	 }
	 catch (Exception e) {
	     msg = "Problem creating multiple directories, " + dir + ",  due to general exception";
	     RMT2File.logger.log(Level.ERROR, msg);
	     throw new SystemException(msg);
	 }
	 
   }
    
    
    /**
     * Writes data to a disk file.
     * 
     * @param data The data to be written to disk
     * @param filename The path name to where data is stored to disk.
     * @throws SystemException I fileDestination is not found or general IO error.
     */
    public static void createFile(String data, String filename) throws SystemException {
        outputFile(data, filename);
    }

    /**
     * Serializes a text data to disk.
     * 
     * @param data The String data that is targeted for serialization.
     * @param filename The name of the file that will contain the serialized data.  
     *         The path will be determined using the serial_* hash values located in 
     *         the SystemParms properties file.
     * @throws SystemException
     */
    public static void serializeText(String data, String filename) throws SystemException {
        String msg = "File serialization failed for file " + filename + ": ";
        String filePath = RMT2File.getSerialLocation(filename);
        try {
            RMT2File.outputFile(data, filePath);
        }
        catch (SystemException e) {
            msg += e.getMessage();
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(e);
        }
    }

    /**
     * Serializes a java object.   Be sure that the taget objet implements the java.io.Serializable interface.
     * 
     * @param _obj The object that is targeted for serialization.
     * @param _destination The name of the file that will contain the serialized object.
     * @throws SystemException
     */
    public static void serializeObject(Object _obj, String _destination) throws SystemException {
        String msg = "File serialization failed for file " + _destination + ": ";
        String filename = RMT2File.getSerialLocation(_destination);

        try {
            // Use a FileOutputStream to send data to a file called _obj.
            FileOutputStream f_out = new FileOutputStream(filename);

            // Use an ObjectOutputStream to send object data to the FileOutputStream for writing to disk.
            ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

            // Pass our object to the ObjectOutputStream's writeObject() method to cause it to be written out to disk.
            obj_out.writeObject(_obj);
            obj_out.flush();
            obj_out.close();
        }
        catch (FileNotFoundException e) {
            msg = msg + " File was not found";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        catch (NotSerializableException e) {
            msg = msg + " Object is not serializable...Ensure that object implements java.io.Serializable interface";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        catch (IOException e) {
            msg = msg + " General file IO error occurred";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
    }

    /**
     * Restorses a serailized object to some previous state.
     * 
     * @param _source The file that contains the seriali data needed to be restored to an object.
     * @return the restored object 
     * @throws SystemException
     */
    public static Object deSerializeObject(String _source) throws SystemException {
        Object obj = null;
        String msg = "File deserialization failed for file " + _source + ": ";
        String filename = RMT2File.getSerialLocation(_source);
        try {
            // Read from disk using FileInputStream.
            FileInputStream f_in = new FileInputStream(filename);

            // Read object using ObjectInputStream.
            ObjectInputStream obj_in = new ObjectInputStream(f_in);

            // Read an object.
            obj = obj_in.readObject();
            return obj;
        }
        catch (FileNotFoundException e) {
            msg = msg + " File was not found";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(e);
        }
        catch (ClassNotFoundException e) {
            msg = msg + " Class was not found";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(e);
        }
        catch (IOException e) {
            msg = msg + " General file IO error occurred";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(e);
        }
    }

    /**
     * Uses the SystemParms.properties file to obtain drive, path, and file extension to be concatenated with 
     * _filename which the end result is the full path where objects are serialized and deserialized.
     * 
     * @param _filename The filename used to write/read serialiazed/deserialized object content.
     * @return The full path used to retreive or store a serialized file.
     * @throws SystemException
     */
    private static String getSerialLocation(String _filename) throws SystemException {
        String result = null;
        String msg;
        if (_filename == null || _filename.length() <= 0) {
            msg = "Problem determining the system location for serializing and deserializing objects";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }

        String drive = RMT2File.getPropertyValue("SystemParms", "serial_drive");
        String path = RMT2File.getPropertyValue("SystemParms", "serial_path");
        //String ext = RMT2Utility.getPropertyValue("SystemParms", "serial_ext");
        result = drive + path + _filename;
        return result;
    }

    /**
     * Copies a file to another location in the Operating System
     * 
     * @param fromFileName The complete path of the source file.
     * @param toFileName The complete path of the destination file.
     * @throws IOException in the following cases:
     * <ul>
     *   <li>the source file does not exist.</li>
     *   <li>the source directory does not allow copying.</li>
     *   <li>the source file is unreadable.</li>
     *   <li>the destination file is not writeable.</li>
     *   <li>problem occurred attempting to overwrite the destination file.</li>
     *   <li>the destination directory does not exist.</li>
     *   <li>the destination is not a directory.</li>
     *   <li>the destination directory is not writeable.</li>
     *   <li>when the source file does not exist.</li>
     * </ul>
     */
    public static void copyFile(String fromFileName, String toFileName) throws IOException {
        File fromFile = new File(fromFileName);
        File toFile = new File(toFileName);
        String msg;

        if (!fromFile.exists()) {
            msg = "FileCopy: " + "no such source file: " + fromFileName;
            RMT2File.logger.log(Level.ERROR, msg);
            throw new IOException(msg);
        }
        if (!fromFile.isFile()) {
            msg = "FileCopy: " + "can't copy directory: " + fromFileName;
            RMT2File.logger.log(Level.ERROR, msg);
            throw new IOException(msg);
        }
        if (!fromFile.canRead()) {
            msg = "FileCopy: " + "source file is unreadable: " + fromFileName;
            RMT2File.logger.log(Level.ERROR, msg);
            throw new IOException(msg);
        }

        if (toFile.isDirectory())
            toFile = new File(toFile, fromFile.getName());

        if (toFile.exists()) {
            if (!toFile.canWrite()) {
                msg = "FileCopy: " + "destination file is unwriteable: " + toFileName;
                RMT2File.logger.log(Level.ERROR, msg);
                throw new IOException(msg);
            }
            System.out.print("Overwrite existing file " + toFile.getName() + "? (Y/N): ");
            System.out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String response = in.readLine();
            if (!response.equals("Y") && !response.equals("y")) {
                msg = "FileCopy: " + "existing file was not overwritten";
                RMT2File.logger.log(Level.ERROR, msg);
                throw new IOException(msg);
            }
        }
        else {
            String parent = toFile.getParent();
            if (parent == null) {
                parent = System.getProperty("user.dir");
            }
            File dir = new File(parent);
            if (!dir.exists()) {
                msg = "FileCopy: " + "destination directory doesn't exist: " + parent;
                RMT2File.logger.log(Level.ERROR, msg);
                throw new IOException(msg);
            }
            if (dir.isFile()) {
                msg = "FileCopy: " + "destination is not a directory: " + parent;
                RMT2File.logger.log(Level.ERROR, msg);
                throw new IOException(msg);
            }
            if (!dir.canWrite()) {
                msg = "FileCopy: " + "destination directory is unwriteable: " + parent;
                RMT2File.logger.log(Level.ERROR, msg);
                throw new IOException(msg);
            }
        }

        FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream(fromFile);
            to = new FileOutputStream(toFile);
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = from.read(buffer)) != -1) {
                to.write(buffer, 0, bytesRead); // write    
            }
        }
        finally {
            if (from != null)
                try {
                    from.close();
                }
                catch (IOException e) {
                    ;
                }
            if (to != null)
                try {
                    to.close();
                }
                catch (IOException e) {
                    ;
                }
        }
    }

    /**
     * Copies a file to another location in the Operating System.  If the file exists, then the file 
     * is overwritten without prompting the user.
     * 
     * @param fromFileName The complete path of the source file.
     * @param toFileName The complete path of the destination file.
     * @throws IOException in the following cases:
     * <ul>
     *   <li>the source file does not exist.</li>
     *   <li>the source directory does not allow copying.</li>
     *   <li>the source file is unreadable.</li>
     *   <li>the destination file is not writeable.</li>
     *   <li>problem occurred attempting to overwrite the destination file.</li>
     *   <li>the destination directory does not exist.</li>
     *   <li>the destination is not a directory.</li>
     *   <li>the destination directory is not writeable.</li>
     *   <li>when the source file does not exist.</li>
     * </ul>
     */
    public static void copyFileWithOverwrite(String fromFileName, String toFileName) throws IOException {
        File fromFile = new File(fromFileName);
        File toFile = new File(toFileName);
        String msg;

        if (!fromFile.exists()) {
            msg = "FileCopy: " + "no such source file: " + fromFileName;
            RMT2File.logger.log(Level.ERROR, msg);
            throw new IOException(msg);
        }
        if (!fromFile.isFile()) {
            msg = "FileCopy: " + "can't copy directory: " + fromFileName;
            RMT2File.logger.log(Level.ERROR, msg);
            throw new IOException(msg);
        }
        if (!fromFile.canRead()) {
            msg = "FileCopy: " + "source file is unreadable: " + fromFileName;
            RMT2File.logger.log(Level.ERROR, msg);
            throw new IOException(msg);
        }

        if (toFile.isDirectory())
            toFile = new File(toFile, fromFile.getName());

        String parent = toFile.getParent();
        if (parent == null) {
            parent = System.getProperty("user.dir");
        }
        File dir = new File(parent);
        if (!dir.exists()) {
            msg = "FileCopy: " + "destination directory doesn't exist: " + parent;
            RMT2File.logger.log(Level.ERROR, msg);
            throw new IOException(msg);
        }
        if (dir.isFile()) {
            msg = "FileCopy: " + "destination is not a directory: " + parent;
            RMT2File.logger.log(Level.ERROR, msg);
            throw new IOException(msg);
        }
        if (!dir.canWrite()) {
            msg = "FileCopy: " + "destination directory is unwriteable: " + parent;
            RMT2File.logger.log(Level.ERROR, msg);
            throw new IOException(msg);
        }

        FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream(fromFile);
            to = new FileOutputStream(toFile);
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = from.read(buffer)) != -1) {
                to.write(buffer, 0, bytesRead); // write    
            }
        }
        finally {
            if (from != null)
                try {
                    from.close();
                }
                catch (IOException e) {
                    ;
                }
            if (to != null)
                try {
                    to.close();
                }
                catch (IOException e) {
                    ;
                }
        }
    }

    /**
     * Deletes a file or directory from the file system.
     * 
     * @param filePath
     *          a String containing the full path and file name of the 
     *          resource to delete.  This can be the path to a file or 
     *          directory.
     * @return int
     *          the total number of resources deleted.
     */
    public static final int deleteFile(String filePath) {
        File file = new File(filePath);
        return RMT2File.deleteFile(file);
    }

    /**
     * Deletes a single file or a directory and its entire contents from the 
     * file system.  When fileName is representing a directory, the directory 
     * is recursively processed until its contents are deleted.
     * 
     * @param file 
     *          a File instance pointing to the file or directory to delete.
     * @return int
     *          the total number of files and/or directories deleted.  Zero may 
     *          be returned if <i>file</i> does not exist or is write protected.
     */
    public static final int deleteFile(File file) {
        int count = 0;
        String msg = null;
        if (file.isDirectory()) {
            String dirContents[] = file.list();
            for (int ndx = 0; ndx < dirContents.length; ndx++) {
                // Create child File object using its parent File to establish root path
                File childFile = new File(file, dirContents[ndx]);
                // Examine child File
                count += RMT2File.deleteFile(childFile);
            }
        }

        if (!file.exists()) {
            msg = "File Delete Problem: no such file or directory: " + file.getPath();
            logger.log(Level.WARN, msg);
            return 0;
        }
        if (!file.canWrite()) {
            msg = "File Delete Problem: file or directory is write protected: " + file.getPath();
            logger.log(Level.WARN, msg);
            return 0;
        }
        if (file.delete()) {
            count++;
        }
        return count;
    }

    /**
     * Extracts an Object from a valid InputStream.
     * 
     * @param in An instance of java.io.InputStream.  Cannot be null.
     * @return An arbitrary data object.
     * @throws SystemException 
     *           General I/O errors, input stream type is conflicting with that of 
     *           the sender, class of the target input object cannot found, dor when 
     *           in is invalid.
     */
    public static final Object getStreamObjectData(InputStream in) throws SystemException {
        String msg;
        if (in == null) {
            msg = "Input Stream object is invalid";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(in);
            Object data = null;
            if (ois != null) {
                data = ois.readObject();
            }
            return data;
        }
        catch (StreamCorruptedException e) {
            msg = "Stream Corrupted Error: " + e.getMessage();
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        catch (IOException e) {
            msg = "General IO Error: " + e.getMessage();
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        catch (ClassNotFoundException e) {
            msg = "Class Not Found Error: " + e.getMessage();
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
    }

    /**
     * Extracts a String object from the an InputStream.
     * 
     * @param in An instance of java.io.InputStream.  Cannot be null.
     * @return Data represented as a String.
     * @throws SystemException 
     *           General I/O errors, input stream type is conflicting with that of 
     *           the sender, or when in is invalid.
     */
    public static final String getStreamStringData(InputStream in) throws SystemException {
        String msg;
        if (in == null) {
            msg = "Input Stream object is invalid";
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        try {
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer data = new StringBuffer(100);
            String temp;
            while ((temp = br.readLine()) != null) {
                data.append(temp);
            }
            return data.toString();
        }
        catch (StreamCorruptedException e) {
            msg = "Stream Corrupted Error: " + e.getMessage();
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        catch (IOException e) {
            msg = "General IO Error: " + e.getMessage();
            RMT2File.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
    }

    /**
     * Returns the size of an input stream based on the number of bytes detected.
     * 
     * @param is
     * @return
     * @throws SystemException
     */
    public static final long getStreamByteSize(InputStream is) throws SystemException {
	byte bytes[] = RMT2File.getStreamByteData(is);
	return bytes.length;
    }
    
    
    /**
     * Transfer the contents of an InputStream to an array of byte values.
     * 
     * @param is
     *          The InputStream to transfer
     * @return
     *          byte[]
     * @throws SystemException
     */
    public static final byte[] getStreamByteData(InputStream is) throws SystemException {
        int byteSize = 1024;
        return RMT2File.getStreamByteData(is, byteSize);
    }

    /**
     * Transfers the contents of the an InputStream instance to an array of bytes in 
     * chunks of <i>byteSize</i> bytes.
     * 
     * @param is
     *          The InputStream to transfer
     * @param byteSize
     *          The total number of bytes to transfer per read iteration.
     * @return
     *          byte[]
     * @throws SystemException
     */
    public static final byte[] getStreamByteData(InputStream is, int byteSize) throws SystemException {
        ByteArrayOutputStream baos = RMT2File.createOutputByteStream(is, byteSize);
        return baos.toByteArray();
    }

    /**
     * Creates a ByteArrayOutputStream by transfering the contents of the an InputStream instance to the ByteArrayOutputStram 
     * instance in chunks of <i>byteSize</i> bytes.
     * 
     * @param is
     *          The InputStream to transfer
     * @param byteSize
     *          The total number of bytes to transfer per read iteration.
     * @return
     *          ByteArrayOutputStream
     * @throws SystemException
     */
    public static final ByteArrayOutputStream createOutputByteStream(InputStream is, int byteSize) throws SystemException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            byte buffer[] = new byte[byteSize];
            while (is.read(buffer) != -1) {
                dos.write(buffer);
            }
            dos.close();
            return baos;
        }
        catch (IOException e) {
            throw new SystemException(e);
        }
    }

    /**
     * Creates an InputStream for a given file name.
     * 
     * @param fileName
     *         The file name to create an InputStream instance for.  The 
     *         value can be an absolute fiel path or the path can relative 
     *         to the classpath. 
     * @return An instance of InputStream or null if the <i>fileName</i> is a malformed URL 
     *         or an I/O error occurred.
     */
    public static final InputStream getFileInputStream(String fileName) {
        InputStream in = null;
        URL url = null;
        File file = null;
        
        // Thread.currentThread is generally used for web application contexts
        in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        if (in == null) {
            // Try to get stream in stand alone context
            in = RMT2File.class.getClass().getResourceAsStream(fileName);
            
            if (in == null) {
                url = Thread.currentThread().getContextClassLoader().getResource(fileName);
                if (url == null) {
                    url = RMT2File.class.getClass().getResource(fileName);
                    try {
                        if (url == null) {
                            file = new File(fileName);
                            in = file.toURL().openStream();
                        }    
                        else {
                            in = url.openStream();
                        }
                    }
                    catch (MalformedURLException e) {
                        logger.log(Level.ERROR, "MalformedURLException: " + e.getMessage());
                        in = null;
                    }
                    catch (IOException e) {
                        logger.log(Level.ERROR, "IOException: " + e.getMessage());
                        in = null;
                    }
                }
            }
        }

        return in;
    }

    /**
     * Retrieves a list files from a specified directory based on selection criteria known as wildcards.
     * This method does not process subdirectories recursively.
     * 
     * @param directory
     *          The directory to perform a file listing.
     * @param fileNameCriteria
     *          a String containing one or more wild card notations separated by the character, ";".
     * @return List<String>
     *          a list of all files found in the directory matching the selection criteria.
     */
    public static final List<String> getDirectoryListing(String directory, String fileNameCriteria) {
        String msg;
        File folder = null;

        try {
            folder = new File(directory);
        }
        catch (NullPointerException e) {
            msg = "The directory parameter is invalid or null";
            logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }

        // validate directory
        switch (RMT2File.verifyDirectory(folder)) {
        case RMT2File.FILE_IO_NOTEXIST:
            msg = "The directory, " + directory + ", does not exist";
            logger.log(Level.ERROR, msg);
            throw new NotFoundException(msg, RMT2File.FILE_IO_NOTEXIST);

        case RMT2File.FILE_IO_NOT_DIR:
            msg = directory + " is not a directory";
            logger.log(Level.ERROR, msg);
            throw new SystemException(msg, RMT2File.FILE_IO_NOT_DIR);

        case RMT2File.FILE_IO_INACCESSIBLE:
            msg = directory + " is inaccessible";
            logger.log(Level.ERROR, msg);
            throw new SystemException(msg, RMT2File.FILE_IO_INACCESSIBLE);
        }

        RMT2FileDirectoryFilter filter = new RMT2FileDirectoryFilter(fileNameCriteria);
        File[] listOfFiles = folder.listFiles(filter);

        List<String> listing = new ArrayList<String>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                listing.add(listOfFiles[i].getName());
                logger.log(Level.INFO, "Found file: " + listOfFiles[i].getName());
            }
            else if (listOfFiles[i].isDirectory()) {
                logger.log(Level.INFO, "SubDirectory was found but not added to listing: " + listOfFiles[i].getName());
            }
        }
        return listing;
    }
    
    /**
     * 
     * @param s
     * @param out
     * @throws IOException
     */
    public static void writeString(String s, DataOutputStream out) throws IOException {
        if(s != null) {
            out.writeUTF(s);
        }
        else {
            out.write(0);
        }
    }

    /**
     * 
     * @param in
     * @return
     * @throws IOException
     */
    public static String readString(DataInputStream in) throws IOException {
        return in.readUTF();
    }
    

    /**
     * Adds properties to the System class using an input .properties file, <i>propFileName</i>, as the source.
     * 
     * @param propFileName
     * @throws SystemException
     */
    public static void loadSystemProperties(String propFileName) throws SystemException {
        // Locate and load properties to System properties collection
        String msg = null;
        Properties props = null;
        try {
            // Try using to file system to locate resource
            props = RMT2File.loadPropertiesObject(propFileName);
        }
        catch (SystemException e) {
            msg = e.getMessage();
            logger.warn(msg);
            
            try {
                // try to locate resource from the classpath
                ResourceBundle r = RMT2File.loadAppConfigProperties(propFileName);
                props = RMT2File.convertResourceBundleToProperties(r);    
            }
            catch (Exception ee) {
                msg = "Unable to load System properties using source Properties file, " + propFileName;
                throw new SystemException(msg, ee);
            }
        }
       
        // Add local properties to the System property collection.
        for (Object key : props.keySet()) {
            String propName = (String) key;
            System.setProperty(propName, props.getProperty(propName));
        }
        
        return;
    }

} // end class

