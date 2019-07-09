package com.audiovideo;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.helpers.NullEnumeration;

import java.io.File;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import com.bean.RMT2Base;
import com.bean.db.DatabaseConnectionBean;

import com.api.BatchFileException;
import com.api.config.HttpSystemPropertyConfig;

import com.api.db.DatabaseException;

import com.api.messaging.email.EmailMessageBean;
import com.api.messaging.email.smtp.SmtpApi;
import com.api.messaging.email.smtp.SmtpFactory;

import com.api.security.pool.AppPropertyPool;

import com.audiovideo.AudioVideoBatchUpdateApi;
import com.audiovideo.MediaBean;
import com.audiovideo.AudioVideoException;

import com.util.RMT2Date;
import com.util.RMT2File;
import com.util.SystemException;


/**
 * 
 * @author appdev
 *
 */
class AvBatchImpl extends AudioVideoManagerApiImpl implements AudioVideoBatchUpdateApi {

    private static Logger logger = Logger.getLogger(AudioVideoManagerApiImpl.class);

    private File resourcePath;

    private List<String> fileErrorMsg;

    private Date startTime;

    private Date endTime;

    protected int successCnt;

    protected int errorCnt;

    protected int totCnt;

    private int expectedFileCount;

    /**
     * Contructor to initialize dbConn and Audio-Video Bean.   User is responsible for providing 
     * server name, share name, and root path as input parameters which are used to create an 
     * MediaBean object.   _serverName, _shareName, and _rootName can contain null values 
     * as an input parameter.
     * 
     * @param con
     * @param dirPath
     * @throws DatabaseException
     * @throws SystemException
     */
    AvBatchImpl(DatabaseConnectionBean con, String dirPath) throws AvBatchException {
	super(con);
	AvBatchImpl.logger.log(Level.DEBUG, RMT2Base.LOGGER_INIT_MSG);
	this.initConnection(dirPath);
    }

    /**
     * Setup connection for an arbitrary external datasource which the configuration 
     * is known at implementation.
     * 
     * @param dirPath
     * 
     * @throws BatchFileException
     */
    public void initConnection(Object dirPath) throws AvBatchException {
	if (dirPath == null) {
	    this.msg = "The starting point resource of the audio/video batch process is invalid or null";
	    AvBatchImpl.logger.log(Level.ERROR, this.msg);
	    throw new AvBatchException(this.msg);
	}
	if (!(dirPath instanceof String)) {
	    this.msg = "The starting point resource of the audio/video batch process must be the name of the complete path of the directory to begin processing";
	    AvBatchImpl.logger.log(Level.ERROR, this.msg);
	    throw new AvBatchException(this.msg);
	}
	this.resourcePath = new File(dirPath.toString());
	this.successCnt = 0;
	this.errorCnt = 0;
	this.totCnt = 0;
	this.fileErrorMsg = new ArrayList<String>();

    }

    /**
     * Removes all entries from the audio_video and audio_video_tracks tables 
     * where streaming files exist on the server.
     * 
     * @param projectTypeId
     * @return
     * @throws AudioVideoException
     */
    public int purge(int projectTypeId) {
	logger.log(Level.INFO, "Begin Audio/Video Purge process");
	int rows = 0;
	int tot = 0;

	StringBuffer sql = new StringBuffer();
	sql.append("delete from av_tracks where project_id in (select b.project_id from av_project b where b.project_type_id = ");
	sql.append(projectTypeId);
	sql.append(")");
	rows = this.executeUpdate(sql.toString());
	tot += rows;
	logger.log(Level.INFO, "Total rows purged from av_tracks: " + rows);

	sql = new StringBuffer();
	sql.append("delete from av_project where project_type_id = ");
	sql.append(projectTypeId);
	rows = this.executeUpdate(sql.toString());
	tot += rows;

	this.dynaApi.clearParms();
	this.dynaApi.addParm("tbl_name", Types.VARCHAR, "av_project", false);
	this.dynaApi.addParm("owner_name", Types.VARCHAR, "dba", false);
	this.dynaApi.addParm("new_identity", Types.VARCHAR, 0, false);
	this.dynaApi.execute("exec sa_reset_identity ???");

	this.dynaApi.clearParms();
	this.dynaApi.addParm("tbl_name", Types.VARCHAR, "av_tracks", false);
	this.dynaApi.addParm("owner_name", Types.VARCHAR, "dba", false);
	this.dynaApi.addParm("new_identity", Types.VARCHAR, 0, false);
	this.dynaApi.execute("exec sa_reset_identity ???");

	// Comment so not to purge artist table.
	//	sql = new StringBuffer();
	//	sql.append("delete from av_artist");
	//	rows = this.executeUpdate(sql.toString());
	//	tot += rows;
	//	logger.log(Level.INFO, "Total rows purged from av_project: " + rows);
	//	this.dynaApi.clearParms();
	//	this.dynaApi.addParm("tbl_name", Types.VARCHAR, "av_artist", false);
	//	this.dynaApi.addParm("owner_name", Types.VARCHAR, "dba", false);
	//	this.dynaApi.addParm("new_identity", Types.VARCHAR, 0, false);
	//	this.dynaApi.execute("exec sa_reset_identity ???");

	return tot;
    }

    /**
     * Creates an audoi/video library (audio_video and audio_video_tracks tables) from the files stored 
     * locally or remotely.   Returns the total number of tracks successfull processed.  Data from the 
     * tables audio_video and audio_video_tracks are deleted before processing artist's directories.
     * 
     * @throws AudioVideoException
     * @thows AvBatchValidationException
     */
    public int processBatch() throws AvBatchException {
	boolean useLogger = true;
	Enumeration<Object> enm = logger.getAllAppenders();
	if (enm != null && enm instanceof NullEnumeration) {
	    useLogger = false;
	}

	this.startTime = new Date();
	if (useLogger) {
	    AvBatchImpl.logger.log(Level.INFO, "Begin Audio-Video Batch Update");
	}
	else {
	    System.out.println("Begin Audio-Video Batch Update");
	}

	try {
	    this.validate();
	}
	catch (AvSourceNotADirectoryException e) {
	    throw new AvBatchException(e);
	}

	// Begin process all files
	try {
	    this.expectedFileCount = this.computeTotalFileCount(this.resourcePath);
	    this.processDirectory(this.resourcePath, null);
	    return this.totCnt;
	}
	catch (AvBatchException e) {
	    throw e;
	}
	finally {
	    this.endTime = new Date();
	    if (useLogger) {
		AvBatchImpl.logger.log(Level.INFO, "Batch start time: " + startTime.toString());
		AvBatchImpl.logger.log(Level.INFO, "Batch end time: " + endTime.toString());
		AvBatchImpl.logger.log(Level.INFO, "Total Media Files Processed: " + this.totCnt);
		AvBatchImpl.logger.log(Level.INFO, "Total Media Files Successfully Processed: " + this.successCnt);
		AvBatchImpl.logger.log(Level.INFO, "Total Media Files Unsuccessfully Processed: " + this.errorCnt);
		AvBatchImpl.logger.log(Level.INFO, "End Audio-Video Update");
	    }
	    else {
		System.out.println("Batch start time: " + startTime.toString());
		System.out.println("Batch end time: " + endTime.toString());
		System.out.println("Total Media Files Processed: " + this.totCnt);
		System.out.println("Total Media Files Successfully Processed: " + this.successCnt);
		System.out.println("Total Media Files Unsuccessfully Processed: " + this.errorCnt);
		System.out.println("End Audio-Video Update");
	    }

	    // Send batch report via SMTP
	    try {
		this.sendAvBatchReport();
	    }
	    catch (AvBatchReportException e) {
		logger.log(Level.ERROR, "Audio/Video Batch Report Failed...See Log for details");
	    }
	}

    }

    /**
     * Verifies that the resource starting point for this batch process is a directory in the file system.
     * 
     * @throws AvSourceNotADirectoryException
     */
    public void validate() {
	if (!this.resourcePath.isDirectory()) {
	    this.msg = resourcePath + " is required to be a directory for Audio Video Batch process";
	    AvBatchImpl.logger.log(Level.ERROR, this.msg);
	    throw new AvSourceNotADirectoryException(this.msg);
	}
    }

    /**
     * Counts the total number of files of the directory, <i>mediaResource</i>, and its sub-directories.
     * 
     * @param mediaResource
     *          an instance of File which must represent a directory in the file system.
     * @return int
     *          the file count.
     */
    public int computeTotalFileCount(File mediaResource) {
	File mediaList[];
	int itemCount = 0;
	int total = 0;

	mediaList = mediaResource.listFiles();
	itemCount = mediaList.length;
	for (int ndx = 0; ndx < itemCount; ndx++) {
	    if (mediaList[ndx].isDirectory()) {
		// Make recursive call to process next level
		total += this.computeTotalFileCount(mediaList[ndx]);
	    }
	    if (mediaList[ndx].isFile()) {
		total += 1;
	    }
	}
	return total;
    }

    /**
     * Process all the high level audio/video artist directories.
     * 
     * @param mediaResource
     * @return
     * @throws AvBatchException
     */
    public Object processDirectory(File mediaResource, Object parent) throws AvBatchException {
	File mediaList[];
	int itemCount = 0;

	try {
	    mediaList = mediaResource.listFiles();
	    itemCount = mediaList.length;
	    for (int ndx = 0; ndx < itemCount; ndx++) {
		if (mediaList[ndx].isDirectory()) {
		    // Make recursive call to process next level
		    this.processDirectory(mediaList[ndx], null);
		}
		if (mediaList[ndx].isFile()) {
		    this.processSingleFile(mediaList[ndx], null);
		}
	    }
	    return 1;
	}
	catch (SecurityException e) {
	    throw new AvBatchException(e);
	}
	catch (MP3ApiInstantiationException e) {
	    throw new AvBatchException(e);
	}
    }

    /**
     * Initiates the media file data extraction process.
     * 
     * @param mediaFile
     * @return
     * @throws MP3ApiInstantiationException
     * @throws AvBatchException
     */
    public Object processSingleFile(File mediaFile, Object parent) throws AvBatchException {
	String pathName;
	MediaBean avb;

	pathName = mediaFile.getPath();
	logger.log(Level.DEBUG, "Processing File: " + pathName);
	try {
	    avb = this.extractFileData(mediaFile);
	    if (avb != null) {
		avb.getAv().setUserId("AVLoader");
		avb.getAvt().setUserId("AVLoader");
		this.updateProject(avb);
	    }
	    this.successCnt++;
	}
	catch (MP3ApiInstantiationException e) {
	    this.fileErrorMsg.add(this.buildFileErrorMessage(pathName, "Audio/Video Api Instantiation", e.getMessage()));
	    throw new MP3ApiInstantiationException(e);
	}
	catch (AvFileExtractionException e) {
	    this.fileErrorMsg.add(this.buildFileErrorMessage(pathName, "Source File Data Extraction", e.getMessage()));
	    this.errorCnt++;
	}
	catch (AvInvalidSourceFileException e) {
	    this.fileErrorMsg.add(this.buildFileErrorMessage(pathName, "Invalid Source File", e.getMessage()));
	    this.errorCnt++;
	}
	catch (AvProjectDataValidationException e) {
	    this.fileErrorMsg.add(this.buildFileErrorMessage(pathName, "Audio/Video Project Data Validation", e.getMessage()));
	    this.errorCnt++;
	}
	catch (AvTrackDataValidationException e) {
	    this.fileErrorMsg.add(this.buildFileErrorMessage(pathName, "Audio/Video Track Data Validation", e.getMessage()));
	    this.errorCnt++;
	}
	catch (SystemException e) {
	    this.fileErrorMsg.add(this.buildFileErrorMessage(pathName, "Audio/Video System", e.getMessage()));
	    this.errorCnt++;
	}
	catch (DatabaseException e) {
	    this.fileErrorMsg.add(this.buildFileErrorMessage(pathName, "Audio/Video Database Access", e.getMessage()));
	    this.errorCnt++;
	}
	catch (AudioVideoException e) {
	    this.fileErrorMsg.add(this.buildFileErrorMessage(pathName, "General Audio/Video", e.getMessage()));
	    this.errorCnt++;
	}
	finally {
	    this.totCnt++;
	}
	return 1;
    }

    /**
     * 
     * @param fileName
     * @param msgCatg
     * @param msg
     * @return
     */
    private String buildFileErrorMessage(String fileName, String msgCatg, String msg) {
	StringBuffer errMsg = new StringBuffer();
	errMsg.append("File: ");
	errMsg.append(fileName);
	errMsg.append("\n");
	errMsg.append("Error Category: ");
	errMsg.append(msgCatg);
	errMsg.append("\n");
	errMsg.append("Cause: ");
	if (msg == null) {
	    msg = "Unknown";
	}
	errMsg.append(msg);
	errMsg.append("\n");

	String m = errMsg.toString();
	System.out.println("Error ===> " + m);
	logger.log(Level.ERROR, "Error ===> " + m);
	return m;
    }

    /**
     * Marks the database connection for garbage collection.
     */
    public void close() {
	if (this.connector != null) {
	    this.connector = null;
	}
    }

    /**
     * Creates a report detailing the success or failure of all the files processed and 
     * transmits the report via SMTP to the user designated as the application's email  
     * recipient.
     * 
     * @throws AvBatchReportException
     *           problem occurred creaing or sending file drop report via SMTP. 
     */
    private void sendAvBatchReport() {
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
		    this.msg = "FROM Email address is invalid and will probably be the root cause of transmission failure of Audio/Video Batch Report";
		    logger.log(Level.ERROR, this.msg);
		    throw new AvBatchReportException(this.msg);
		}
	    }
	}

	String toAddr = fromAddr;
	if (toAddr == null) {
	    this.msg = "TO Email address is invalid and will probably be the root cause of transmission failure of Audio/Video Batch Report";
	    logger.log(Level.ERROR, this.msg);
	    throw new AvBatchReportException(this.msg);
	}
	String subject = "Audio/Video Batch Report";
	String appname = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_APP_NAME);
	appname = (appname == null ? "[Unknown Application]" : appname);

	body.append("This is a report of the results of the Audio/Video Batch process\n");

	body.append("Start Time: ");
	body.append(RMT2Date.formatDate(this.startTime, "MM-dd-yyyy HH:mm:ss.S"));
	body.append("\n");
	body.append("End Time: ");
	body.append(RMT2Date.formatDate(this.endTime, "MM-dd-yyyy HH:mm:ss.S"));
	body.append("\n\n\n");

	body.append("Total Media Files Processed: " + this.totCnt);
	body.append("\n");
	body.append("Total Media Files Successfully Processed: " + this.successCnt);
	body.append("\n");
	body.append("Total Media Files Unsuccessfully Processed: " + this.errorCnt);
	body.append("\n\n\n");

	if (this.fileErrorMsg.size() > 0) {
	    body.append("Detail report of files that failed during this batch process");
	    body.append("\n");
	    body.append("=============================================================");
	    body.append("\n");
	}
	int count = 0;
	// Add details about each file that was processed
	for (String msg : this.fileErrorMsg) {
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
	}
	catch (Exception e) {
	    throw new AvBatchReportException(e);
	}
	return;
    }

    /* (non-Javadoc)
     * @see com.api.BatchFileProcessor#getFileListing()
     */
    public List<String> getFileListing() {
	throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see com.api.BatchFileProcessor#initConnection()
     */
    public void initConnection() throws BatchFileException {
	throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see com.api.BatchFileProcessor#initConnection(java.lang.String, java.lang.String)
     */
    public void initConnection(String arg0, String arg1) throws BatchFileException {
	throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see com.api.BatchFileProcessor#processFiles(java.util.List)
     */
    public Object processFiles(List<String> arg0, Object parent) throws BatchFileException {
	throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see com.api.BatchFileProcessor#processSingleFile(java.lang.String)
     */
    public Object processSingleFile(String arg0, Object parent) throws BatchFileException {
	throw new UnsupportedOperationException();
    }
}
