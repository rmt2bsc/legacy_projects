package com.api.filehandler;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.File;
import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.api.BatchFileException;
import com.api.config.HttpSystemPropertyConfig;

import com.api.db.DatabaseException;
import com.api.db.JdbcFactory;
import com.api.db.MimeContentApi;
import com.api.db.MimeFactory;

import com.api.messaging.email.EmailMessageBean;
import com.api.messaging.email.smtp.SmtpApi;
import com.api.messaging.email.smtp.SmtpFactory;

import com.api.security.pool.AppPropertyPool;
import com.api.security.pool.DatabaseConnectionPool;

import com.bean.Content;
import com.bean.RMT2Base;

import com.bean.db.DatabaseConnectionBean;

import com.util.RMT2Date;
import com.util.RMT2File;
import com.util.RMT2String;
import com.util.RMT2Utility;
import com.util.SystemException;

/**
 * An implementation of the BatchFileProcessor for inserting various MIME type files into the 
 * MIME database and associating each MIME database record with a record from the home application.  
 * This class depends on the existence of a .properties file containing the necessary configuration 
 * information for batch processing MIME files.   It expects to pickup one or more files from a 
 * specified inbound directory, persist the content of each MIME file to the MIME database , link 
 * the MIME content to some arbitrary record of the Home application, archive the source data file, 
 * and send a file drop report to the user designated in the home application's MIME configuration.
 * 
 * @author appdev
 *
 */
class CommonMimeFileProcessorImp extends RMT2Base implements MimeFileProcessor {

    private static Logger logger = Logger.getLogger("CommonMimeFileProcessorImp");

    private FileListenerConfig config;

    private DatabaseConnectionBean appConBean;

    private DatabaseConnectionBean mimeConBean;
    
    private Map<Integer, DatabaseConnectionBean> conMap;

    private List<String> mimeMsgs;

    private Date startTime;

    private Date endTime;
    
    private int moduleId;

    /**
     * Creates a CommonMimeFileProcessorImp object initialized with the MIME file listener configuration 
     * of a specific application.
     * 
     * @param config 
     *          an instance of {@link com.api.filehandler.FileListenerConfig FileListenerConfig}
     */
    public CommonMimeFileProcessorImp(FileListenerConfig config) {
	this.config = config;
	CommonMimeFileProcessorImp.logger.log(Level.DEBUG, RMT2Base.LOGGER_INIT_MSG);

	// Initialize MIME message collection
	this.mimeMsgs = new ArrayList<String>();
	return;
    }

    
    public synchronized void setModuleId(int moduleId) {
	this.moduleId = moduleId;
	return;
    }
    
    /**
     * Obtains a list of file names from the file system based on the wildcard 
     * specifications configured for each application module via the internal 
     * MIME configuration.
     * 
     * @return List<String>
     *    a list of String values representing file names
     * @throws SystemException
     *    when the internal MIME configuration is invalid or null
     */
    public synchronized List<String> getFileListing() {
	if (this.config == null) {
	    this.msg = "MIME File listing operation failed.  The MIME configuration instance is invalid or null";
	    logger.log(Level.ERROR, this.msg);
	    throw new SystemException(this.msg);
	}
	
	
	// Get file pattern by targeting the module using module id
	ModuleConfig mod = config.getModules().get(this.moduleId);
	StringBuffer wildcards = new StringBuffer();
	wildcards.append(mod.getFilePattern());
	List<String> fileListing = RMT2File.getDirectoryListing(config.getInboundDir(), wildcards.toString());
	return fileListing;
    }

    /**
     * Setup database connections for the home application as well as the MIME database using the 
     * MIME configuration file based on the current execution environment.  The connection for the 
     * home application is obtained from the {@link com.api.security.pool.DatabaseConnectionPool DatabaseConnectionPool} 
     * instance and the MIME connection is obtained from the MimeConfig.properties. 
     * 
     * @throws BatchFileException
     *           when the attempt to establish a connection for either system fails.
     */
    public synchronized void initConnection() throws BatchFileException {
	String propFile = MimeFileProcessor.CONFIG_CLASSPATH + InboundFileFactory.ENV + "_" + InboundFileFactory.configFile;
	this.initConnection(propFile);
    }
    
    
    /**
     * Creates a connection to the MIME database and to one or more home application databases based 
     * on the supplied configuration file, <i>extSource</i>.
     * 
     * @param extSource
     *          the source of the configuration
     * @throws BatchFileException
     */
    public void initConnection(Object extSource) throws BatchFileException {
	String propFile = null;
	if (extSource == null) {
	    throw new BatchFileException("Database connection could not be estabished due to invalid or null input configuration file");
	}
	propFile = extSource.toString();
	int modCount = this.config.getModuleCount();
	this.conMap = new HashMap<Integer, DatabaseConnectionBean>();
	int count = 0;
	try {
	    for (int ndx = 0; ndx < modCount; ndx++) {
		ModuleConfig mod = this.config.getModules().get(ndx);
		DatabaseConnectionBean con = JdbcFactory.getConnection(mod.getDbUrl(), propFile);
		this.conMap.put(ndx, con);
		logger.info("Opened database connection object for application, " + con.getName() + ", DB URL: " + con.getDbUrl());
		count++;
	    }
	}
	catch (Exception e) {
	    throw new BatchFileException(e);
	}
	if (this.conMap.isEmpty()) {
	    this.msg = "Failed to obtain a database connection for Home Application";
	    logger.log(Level.ERROR, this.msg);
	    throw new BatchFileException(this.msg);
	}
	logger.info("MIME thread opened " + count + " home application database connection(s)");

	// Obtain database connection for MIME database.
	this.mimeConBean = DatabaseConnectionPool.getAvailConnectionBean();
	if (this.mimeConBean == null) {
	    this.msg = "Failed to obtain a database connection for the MIME Application";
	    logger.log(Level.ERROR, this.msg);
	    throw new BatchFileException(this.msg);
	}
	logger.info("Opened local database connection object for MIME application" + this.mimeConBean.getName() + ", DB URL: " + this.mimeConBean.getDbUrl());
	logger.info("MIME thread opened 1 local database connection");
    }

    /**
     * Closes the database connection for the home application and the MIME application.
     */
    public synchronized void close() {
        // Close all module DB connections
        if (this.conMap != null && !this.conMap.isEmpty()) {
            Iterator <Integer> iter = this.conMap. keySet().iterator();
            int count = 0;
            while(iter.hasNext()) {
                Integer key = iter.next();
                DatabaseConnectionBean con = this.conMap.get(key);
                logger.info("Closing database connection object for application, " + con.getName() + ", DB URL: " + con.getDbUrl());
                con.close();
                con = null;
                count++;
            }
            logger.info(count + " home application database connections were closed successfully for the MIME thread");
        }
        this.appConBean = null;
	if (this.mimeConBean != null) {
	    this.mimeConBean.close();
	    this.mimeConBean = null;
	    logger.info("The MIME database connection was closed sucessfully");
	}
    }

    /**
     * Traverses the list of file names in <i>files</i> adding the content of each file 
     * to the MIME database and assoicating the MIME content record with the targeted 
     * record of the home application.  This method is the driver of concept of processing 
     * the files dropped in the Inbound directory.
     * 
     * @param files
     *         a List<String> of fiel names to process
     * @return int
     *         a count of all the files processed.
     * @throws BatchFileException   
     *         file validation errors and general database errors
     */
    public synchronized Object processFiles(List<String> files, Object parent) throws BatchFileException {
	this.startTime = new Date();
	int count = 0;
	String cmd = null;
	String cmdMsg = null;

	try {
	    // Establish a connection to the computer share which will be used for copy/moving files remotely
	    if (!this.config.isArchiveLocal()) {
		cmd = "cmd /c net use " + config.getArchiveDir();
		logger.log(Level.DEBUG, "Connecting to shared resource, " + config.getArchiveDir());
		cmdMsg = RMT2Utility.execShellCommand(cmd);
		logger.log(Level.DEBUG, cmdMsg);
	    }
	    
	    // Process each module
	    for (int ndx = 0; ndx < config.getModuleCount(); ndx++) {
		this.setModuleId(ndx);
		files = this.getFileListing();    
		// Process all files of the current module
		if (files != null && files.size() > 0) {
		    for (String fileName : files) {
			String fileMsg = null;
			try {
			    CommonMimeFileProcessorImp.logger.log(Level.INFO, "Begin proecessing file, " + fileName);
			    Integer rc = (Integer) this.processSingleFile(fileName, null);
			    int uid = rc.intValue();
			    CommonMimeFileProcessorImp.logger.log(Level.INFO, fileName + " was added to the database");
			    this.mimeConBean.getDbConn().commit();
			    CommonMimeFileProcessorImp.logger.log(Level.INFO, "MIME File was committed in MIME database");
			    this.appConBean.getDbConn().commit();
			    CommonMimeFileProcessorImp.logger.log(Level.INFO, "MIME File was committed in HOME database");
			    fileMsg = fileName + " was added to the MIME database successfully as " + uid;
			}
			catch (Exception e) {
			    try {
				this.mimeConBean.getDbConn().rollback();
				if (this.appConBean != null) {
				    this.appConBean.getDbConn().rollback();
				}
			    }
			    catch (SQLException ee) {
				throw new DatabaseException(e);
			    }
			    fileMsg = "[ERROR] " + fileName + " was not added to the MIME database and/or assoicated with the target record of the home application.  Cause: " + e.getMessage();
			}
			finally {
			    CommonMimeFileProcessorImp.logger.log(Level.INFO, fileMsg);
			    this.mimeMsgs.add(fileMsg);
			    CommonMimeFileProcessorImp.logger.log(Level.INFO, "MIME Message buffer size: " + this.mimeMsgs.size());
			    this.appConBean = null;
			    count++;
			}
		    } // end for		
		    this.msg = "MIME files were processed for module: " + this.moduleId;
		    CommonMimeFileProcessorImp.logger.log(Level.INFO, this.msg);
		} // end if
	    } // end for	       
	}
	catch  (Exception e) {
	    // Do nothing
	}
	finally {
	    // Cancel the connection to computer share(s)
	    if (!this.config.isArchiveLocal()) {
		cmd = "cmd /c net use " + config.getArchiveDir() + " /delete";
		logger.log(Level.DEBUG, "Disconnecting from shared resource, " + config.getArchiveDir());
		cmdMsg = RMT2Utility.execShellCommand(cmd);
		logger.log(Level.DEBUG, cmdMsg);
	    }
	}
	
	this.endTime = new Date();
	return count;
    }

    /**
     * Adds MIME content to the database and joins the content with the target record of the 
     * home application.  Successful execution of this method requires that the name of the 
     * file, <i>fileName</i>, is of the correct format 
     * "<app_code>_<module_code>_<primary key value of target recrod>.*".  After determining 
     * that <i>fileName</i> is correctly formatted, the MIME content is added to the MIME 
     * database and then associated with target record of the home application.  The primary 
     * key value identified in <i>fileName</i> is used to locate the correct home appliation 
     * record.
     * 
     * @param fileName
     *          the filename of the content that is to be added to the system.
     * @return int
     *          the primary key value from the <i>Content</i> table representing 
     *          the recently added MIME content record.
     * @throws BatchFileException
     *          if an error occurs adding the content to the MIME database or and 
     *          error occurs associating the MIME content record with the target 
     *          record of the home application.
     * @throws InvalidMimeFileFormatException
     *          if <i>fileName</i> is not in the correct format.
     * @throws SystemException
     *          problem occurs renaming and copying <i>fileName</i> to the archive destination 
     *          or deleting the <i>fileName</i> from the inbound directory. 
     */
    public Object processSingleFile(String fileName, Object parent) throws BatchFileException {
	List<String> fileNameTokens = null;
	int uid = 0;

	try {
	    // Validate the format of the file name.
	    CommonMimeFileProcessorImp.logger.log(Level.INFO, "Validate file format");
	    fileNameTokens = this.verifyFileFormat(fileName);
	    // Build MIME content object
	    CommonMimeFileProcessorImp.logger.log(Level.INFO, "Create Content Object");
	    Content mime = this.createContentObject(fileName);
	    // Add MIME content to database.
	    CommonMimeFileProcessorImp.logger.log(Level.INFO, "Get content API");
	    MimeContentApi api = MimeFactory.getMimeContentApiInstance(this.config);
	    CommonMimeFileProcessorImp.logger.log(Level.INFO, "initialize content API");
	    api.initApi(this.mimeConBean);
	    CommonMimeFileProcessorImp.logger.log(Level.INFO, "Add content to MIME database");
	    uid = api.addContent(mime);
	    
	    // Assoicate MIME content with a record from the target application 
	    // using content id and the module code
	    CommonMimeFileProcessorImp.logger.log(Level.INFO, "Add content to HOME database");
	    this.updateHomeApp(uid, fileNameTokens);
	    CommonMimeFileProcessorImp.logger.log(Level.INFO, "MIME Database updates completed");
	    return uid;
	}
	catch (InvalidMimeFileFormatException e) {
	    throw e;
	}
	catch (Exception e) {
	    CommonMimeFileProcessorImp.logger.log(Level.ERROR, e.getMessage());
	    throw new BatchFileException(e);
	}
	finally {
	    this.archiveFile(fileName);
	}
    }

    /**
     * Renames the source file, <i>fileName</i>, and copies the renamed results to the 
     * archived desination, and deletes the source file from its inbound location.  The
     * file is renamed using the following format:  
     * <file name>_<yyyymmdd_HHmmss_millsecs>.<original file extension>.
     * 
     * @param fileName
     *          the name of the file to archive.
     */
    private void archiveFile(String fileName) {
	// Rename inbound file 
	List<String> fileToken = RMT2String.getTokens(fileName, ".");
	Date timestamp = new Date();
	StringBuffer timestampStr = new StringBuffer();
	timestampStr.append("_");
	timestampStr.append(RMT2Date.formatDate(timestamp, "yyyymmdd"));
	timestampStr.append("_");
	timestampStr.append(RMT2Date.formatDate(timestamp, "HHmmss"));
	timestampStr.append("_");
	timestampStr.append(RMT2Date.formatDate(timestamp, "S"));
	timestampStr.append(".");
	String fileNameTs = fileToken.get(0) + timestampStr.toString() + fileToken.get(1);
	try {
	    // Move file from inbound directory to archive destination
	    String fromFile = this.config.getInboundDir() + fileName;
	    String toFile = this.config.getArchiveDir() + fileNameTs;

	    // Rename and copy file to archive destination
	    RMT2File.copyFile(fromFile, toFile);
	    // Delete file from inbound directory
	    RMT2File.deleteFile(this.config.getInboundDir() + fileName);
	}
	catch (IOException e) {
	    throw new SystemException(e);
	}
    }

    /**
     * Creates a {@link com.bean.Content Content} object using the metadata of <i>fileName</i> 
     * obtained from the file system.
     * 
     * @param fileName
     *          the name of the file to build Content object.
     * @return {@link com.bean.Content Content}
     * @throws BatchFileException
     *          if <i>fileName</i> does not exist or is a directory.
     */
    private Content createContentObject(String fileName) throws BatchFileException {
	File file = new File(this.config.getInboundDir() + "/" + fileName);
	if (RMT2File.verifyFile(file) != RMT2File.FILE_IO_EXIST) {
	    this.msg = fileName + ": File does not exist or is a directory";
	    logger.log(Level.ERROR, this.msg);
	    throw new BatchFileException(this.msg);
	}
	Content mime = new Content();
	mime.setImageData(null);
	mime.setSize((int) file.length());
	mime.setFilename(fileName);
	mime.setFilepath(this.config.getInboundDir());
	return mime;
    }

    /**
     * Validate naming convention of the file name.  Should be in the format of 
     * <app_code>_<module>_<primary_key>.<file extension>
     *  
     * @param fileName
     * @return List<String>
     *          the application code, moudle code, and the primary key.
     */
    private List<String> verifyFileFormat(String fileName) {
	List<String> tokens = RMT2String.getTokens(fileName, "_");
	if (tokens == null) {
	    this.msg = fileName + ": Parsing error occurred...The underscore character must be used as a separator in the file name";
	    logger.log(Level.ERROR, this.msg);
	    throw new InvalidMimeFileFormatException(this.msg);
	}
	if (tokens.size() != 3) {
	    this.msg = fileName + ": Parsing error occurred...The file name must be in the format  <app_code>_<module>_<primary_key>.<file extension>";
	    logger.log(Level.ERROR, this.msg);
	    throw new InvalidMimeFileFormatException(this.msg);
	}

	// Validate that third element is an integer value representing the primary key.
	List<String> tokens2 = RMT2String.getTokens(tokens.get(2), ".");
	try {
	    Integer.parseInt(tokens2.get(0));
	}
	catch (NumberFormatException e) {
	    this.msg = fileName + ": Failure to recognize the third position in the file name as the primary key for the target datasource during the second phase of file name parsing.  The invalid value is " + tokens.get(2);
	    logger.log(Level.ERROR, this.msg);
	    throw new InvalidMimeFileFormatException(this.msg);
	}
	// Assign the parsed primary key value to original set of tokens
	tokens.set(2, tokens2.get(0));
	return tokens;
    }

    /**
     * Applies database updates to the target record of the home application using the primary key 
     * value of the MIME record recently added to the content table.  The parameters, <i>contentId</i> 
     * and <i>fileNameTokens</i> are used to dynamically build SQL query needed target and update the 
     * appropriate record of the home application based on the mime configuration specified by the 
     * application. 
     *   
     * @param contentId
     *         the id of the content record from the MIME database. 
     * @param fileNameTokens
     *         a List of Strings representing the parsed file name.  The List collection should 
     *         contain three elements where element(0) is the applictaion code, element(1) is 
     *         the module code, and element(2) is the target recrod's primary key value. 
     * @throws HomeApplicationRecordCommittedException
     *          home application record is already associated with MIME content.
     * @throws HomeApplicationRecordNotFoundException
     *          hombe application record was not found using the primary key contained in 
     *          <i>fileNameTokens</i>
     * @throws DatabaseException
     *          general database errors
     * @throws BatchFileException
     *          an invalid module configuration instance was obtained.
     *         
     */
    private void updateHomeApp(int contentId, List<String> fileNameTokens) throws BatchFileException {
	logger.log(Level.INFO, "Inside updateHomeApp");
	String pkVal = fileNameTokens.get(2);

	ModuleConfig mod = this.config.getModules().get(this.moduleId);
	if (mod == null) {
	    this.msg = "Failure to update target application record due to an invalid moudle configuration instance";
	    logger.log(Level.ERROR, this.msg);
	    throw new BatchFileException(this.msg);
	}
	String sql = this.buildTargetAppQuery(mod, pkVal, contentId);
	logger.log(Level.INFO, sql);
	this.appConBean = this.conMap.get(this.moduleId);
	try {
	    Statement stmt = this.appConBean.getDbConn().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
	    ResultSet rs = stmt.executeQuery(sql);
	    if (rs == null || !rs.next()) {
		this.msg = "The home application record was not found by primary key, " + fileNameTokens.get(2) + ".  SQL: " + sql;
		CommonMimeFileProcessorImp.logger.log(Level.ERROR, this.msg);
		throw new HomeApplicationRecordNotFoundException(this.msg);
	    }
	    // Verify that home application record is not already assoicated with content. 
	    int content_id = rs.getInt(mod.getForeignKey());
	    logger.log(Level.INFO, "Content Id value fetched from Home App record foreign key, [" + mod.getForeignKey() + "] :" + content_id);
	    if (content_id > 0) {
		this.msg = "The home application record is not available for content association, because it is currently commited to content identified in MIME database as: "	+ content_id;
		CommonMimeFileProcessorImp.logger.log(Level.ERROR, this.msg);
		throw new HomeApplicationRecordCommittedException(this.msg);
	    }
	    logger.log(Level.INFO, "Updating Home App with document id, " + contentId);
	    rs.updateInt(mod.getForeignKey(), contentId);
	    rs.updateRow();
	    logger.log(Level.INFO, "Home App updated successfully");
	    return;
	}
	catch (SQLException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new DatabaseException(e);
	}
    }

    /**
     * Builds SQL query using a valid moduld configuration instance, the primary key of 
     * the target record of the home application, and the content id from the MIME database.
     * 
     * @param moduleConfig
     *         the configuration needed to obtain the database table name, primary key 
     *         column name, and the foreign key name of the home application's target 
     *         record to build SQL query.
     * @param primaryKey
     *         the primary key value that is encoded as part of the source file's filename 
     *         which represents the id of the home application's target record.
     * @param contentId
     *         the id of the MIME record.
     * @return
     *         SQL query.
     */
    private String buildTargetAppQuery(ModuleConfig moduleConfig, String primaryKey, int contentId) {
	StringBuffer sql = new StringBuffer();
	sql.append("select ");
	sql.append(moduleConfig.getForeignKey());
	sql.append(" from ");
	sql.append(moduleConfig.getTable());
	sql.append(" where ");
	sql.append(moduleConfig.getPrimaryKey());
	sql.append(" = ");
	sql.append(primaryKey);
	return sql.toString();
    }

    /**
     * Creates a report detailing the success or failure of all the files processed and 
     * transmits the report via SMTP to the user designated in the home application's 
     * module configuration.
     * 
     * @throws FileDropReportException
     *           problem occurred creaing or sending file drop report via SMTP. 
     */
    public void sendDropReport() {
	if (!this.config.isEmailResults()) {
	   return;
	}
	logger.log(Level.INFO, "Creating MIME Drop Report...");
	StringBuffer body = new StringBuffer();

	// Attempt to obtain From email address from the application's property pool which is loaded at server start up.
	String fromAddr = AppPropertyPool.getProperty(HttpSystemPropertyConfig.OWNER_EMAIL);
	if (fromAddr == null) {
	    // Attempt to obtain From email address from the application's SystemParms.properties file in root of classpath.
	    try {
		fromAddr = RMT2File.getPropertyValue("SystemParms", HttpSystemPropertyConfig.OWNER_EMAIL);
	    }
	    catch (Exception e) {
		// Attempt to obtain From email address from mime services test environment.
		try {
		    fromAddr = RMT2File.getPropertyValue("test.resources.SystemParms", HttpSystemPropertyConfig.OWNER_EMAIL);
		}
		catch (Exception ee) {
		    this.msg = "FROM Email address is invalid and will probably be the root cause of transmission failure of MIME File Drop Report";
		    logger.log(Level.ERROR, this.msg);
		    throw new FileDropReportException(this.msg);
		}
	    }
	}

	String toAddr = this.config.getReportEmail();
	if (toAddr == null) {
	    this.msg = "TO Email address is invalid and will probably be the root cause of transmission failure of MIME File Drop Report";
	    logger.log(Level.ERROR, this.msg);
	    throw new FileDropReportException(this.msg);
	}
	String subject = "MIME Content File Drop Report";
	String appname = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_APP_NAME);
	appname = (appname == null ? "[Unknown Application]" : appname);

	body.append("The following files where dropped in the inbound directory, ");
	body.append(this.config.getInboundDir());
	body.append(", in order to be added to the MIME database and linked to various record types of the application, ");
	body.append(appname);
	body.append(".\n\n");

	body.append("Start Time: ");
	body.append(RMT2Date.formatDate(this.startTime, "MM-dd-yyyy HH:mm:ss.S"));
	body.append("\n");
	body.append("End Time: ");
	body.append(RMT2Date.formatDate(this.endTime, "MM-dd-yyyy HH:mm:ss.S"));
	body.append("\n\n\n");

	int count = 0;
	// Add details about each file that was processed
	logger.log(Level.INFO, "MIME Drop Report will detail " + this.mimeMsgs.size() + " messages regarding MIME files processed");
	for (String msg : this.mimeMsgs) {
	    count++;
	    body.append(count);
	    body.append(".  ");
	    body.append(msg);
	    body.append("\n");
	}

	// Setup bean that represents the email message.
	EmailMessageBean bean = new EmailMessageBean();
	bean.setFromAddress(fromAddr);

	// You can optionally enter multiple email addresses separated by commas
	bean.setToAddress(toAddr);
	bean.setSubject(subject);
	bean.setBody(body.toString(), EmailMessageBean.TEXT_CONTENT);

	// Declare and initialize SMTP api and allow the system to discover SMTP host 
	SmtpApi api = SmtpFactory.getSmtpInstance();
	// Send simple email to its intended destination
	try {
	    api.sendMessage(bean);
	    // Close the service.
	    api.close();
	    logger.log(Level.INFO, "MIME Drop Report email was sent to " + toAddr);
	}
	catch (Exception e) {
	    this.msg = "MIME Drop Report email submission failed: " + e.getMessage();
	    logger.log(Level.INFO, this.msg);
	    throw new FileDropReportException(this.msg);
	}
	return;
    }

    /**
     * This operation is not supported.
     * 
     * @param dir
     *          N/A
     * @return N/A
     * @throws BatchFileException
     */
    public Object processDirectory(File dir, Object parent) throws BatchFileException {
	throw new UnsupportedOperationException();
    }

    /**
     * This operation is not supported.
     * 
     * @param file
     *          N/A
     * @return N/A
     * @throws BatchFileException
     */
    public Object processSingleFile(File file, Object parent) throws BatchFileException {
	throw new UnsupportedOperationException();
    }
    
    /**
     * This operation is not supported.
     */
    public void initConnection(String appConfigFile, String mimeConfigFile) throws BatchFileException {
	throw new UnsupportedOperationException();
    }


    /**
     * Checks the Inbound directory for available files regardless of the modules configured.
     * 
     * @return true when files are availabe, and false otherwise.
     */
    public boolean isFilesAvailable() {
        if (this.config == null) {
            return false;
        }
        // See if one or more files exist in the Inbound directory
        List<String> fileListing = RMT2File.getDirectoryListing(config.getInboundDir(), null);
        return fileListing.size() > 0;
    }


    /* (non-Javadoc)
     * @see com.api.BatchFileProcessor#processBatch()
     */
    public int processBatch() throws BatchFileException {
	throw new UnsupportedOperationException();
    }

}
