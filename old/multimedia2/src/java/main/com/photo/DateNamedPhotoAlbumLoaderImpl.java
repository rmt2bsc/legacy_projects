package com.photo;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.helpers.NullEnumeration;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import com.bean.MimeTypes;
import com.bean.PhotoAlbum;
import com.bean.PhotoEvent;
import com.bean.PhotoImage;
import com.bean.RMT2Base;
import com.bean.db.DatabaseConnectionBean;

import com.api.BatchFileException;
import com.api.BatchFileProcessor;
import com.api.DaoApi;

import com.api.config.HttpSystemPropertyConfig;
import com.api.db.DatabaseTransApi;
import com.api.db.MimeContentApi;
import com.api.db.MimeException;
import com.api.db.MimeFactory;
import com.api.db.orm.RdbmsDaoImpl;
import com.api.security.pool.AppPropertyPool;


import com.util.RMT2Date;
import com.util.RMT2File;
import com.util.SystemException;

/**
 * 
 * @author appdev
 *
 */
class DateNamedPhotoAlbumLoaderImpl extends RdbmsDaoImpl implements BatchFileProcessor, DaoApi {

    private static Logger logger = Logger.getLogger("PhotoInfoLoaderImpl");

    private static final int DIR_TYPE_ALBUM = 100;

    private static final int DIR_TYPE_EVENT = 200;

    private File resourcePath;

//    private List<String> fileErrorMsg;

    private Date startTime;

    private Date endTime;

    protected int successCnt;

    protected int errorCnt;

    protected int totCnt;

    private int expectedFileCount;
    
    private PhotoAlbumApi api;
    
    private DatabaseTransApi tx;
    
    private String env;

    /**
     * Contructor to initialize dbConn and Audio-Video Bean.   User is responsible for providing 
     * server name, share name, and root path as input parameters which are used to create an 
     * MediaBean object.   _serverName, _shareName, and _rootName can contain null values 
     * as an input parameter.
     * 
     * @param con
     * @param dirPath
     * @throws PhotoBatchLoaderException
     */
    DateNamedPhotoAlbumLoaderImpl(DatabaseConnectionBean con) throws PhotoBatchLoaderException {
	super(con);
	String loaderCtx = "config.loader.PhotoLoader";
	DateNamedPhotoAlbumLoaderImpl.logger.log(Level.DEBUG, RMT2Base.LOGGER_INIT_MSG);
	this.initConnection(loaderCtx);
    }
    
    DateNamedPhotoAlbumLoaderImpl(DatabaseTransApi tx) throws PhotoBatchLoaderException {
	super((DatabaseConnectionBean) tx.getConnector());
	this.tx = tx;
	String loaderCtx = "config.loader.PhotoLoader";
	DateNamedPhotoAlbumLoaderImpl.logger.log(Level.DEBUG, RMT2Base.LOGGER_INIT_MSG);
	this.initConnection(loaderCtx);
    }

    /**
     * Setup connection for an arbitrary external datasource which the configuration 
     * is known at implementation.
     * 
     * @param dirPath
     * 
     * @throws BatchFileException
     */
    public void initConnection(Object loaderClassname) throws PhotoBatchLoaderException {
	if (loaderClassname == null) {
	    this.msg = "The configuration context for the Photo Albumn data loader is invalid or null";
	    DateNamedPhotoAlbumLoaderImpl.logger.log(Level.ERROR, this.msg);
	    throw new PhotoBatchLoaderException(this.msg);
	}

	ResourceBundle rb = ResourceBundle.getBundle(loaderClassname.toString());
	String dirPath = rb.getString("input_directory");
	this.resourcePath = new File(dirPath.toString());
	this.successCnt = 0;
	this.errorCnt = 0;
	this.totCnt = 0;
//	this.fileErrorMsg = new ArrayList<String>();
	
	// initialize Photo Albumn ORM API
	this.api = PhotoFactory.createApi(this.connector);
	this.purge();
	
	this.env = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_ENV);
    }

    /**
     * Removes all entries from the audio_video and audio_video_tracks tables 
     * where streaming files exist on the server.
     * 
     * @param projectTypeId
     * @return
     * @throws AudioVideoException
     */
    public int purge() {
	logger.log(Level.INFO, "Begin Audio/Video Purge process");
	int rows = 0;
	int tot = 0;

	StringBuffer sql = new StringBuffer();
	sql.append("delete from photo_image");
	rows = this.executeUpdate(sql.toString());
	tot += rows;
	logger.log(Level.INFO, "Total rows purged from photo_image: " + rows);

	sql = new StringBuffer();
	sql.append("delete from photo_event");
	rows = this.executeUpdate(sql.toString());
	tot += rows;
	logger.log(Level.INFO, "Total rows purged from photo_event: " + rows);
	
	sql = new StringBuffer();
	sql.append("delete from photo_album");
	rows = this.executeUpdate(sql.toString());
	tot += rows;
	logger.log(Level.INFO, "Total rows purged from photo_album: " + rows);
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
    public int processBatch() throws BatchFileException {
	boolean useLogger = true;
	Enumeration<Object> enm = logger.getAllAppenders();
	if (enm != null && enm instanceof NullEnumeration) {
	    useLogger = false;
	}

	this.startTime = new Date();
	if (useLogger) {
	    DateNamedPhotoAlbumLoaderImpl.logger.log(Level.INFO, "Begin Image File Batch Update");
	}
	else {
	    System.out.println("Begin Image File Batch Update");
	}

	// Begin process all Image files
	try {
	    this.expectedFileCount = this.computeTotalFileCount(this.resourcePath);
	    this.processDirectory(this.resourcePath, null);
	    return this.totCnt;
	}
	catch (PhotoBatchLoaderException e) {
	    throw e;
	}
	finally {
	    this.endTime = new Date();
	    if (useLogger) {
		DateNamedPhotoAlbumLoaderImpl.logger.log(Level.INFO, "Batch start time: " + startTime.toString());
		DateNamedPhotoAlbumLoaderImpl.logger.log(Level.INFO, "Batch end time: " + endTime.toString());
		DateNamedPhotoAlbumLoaderImpl.logger.log(Level.INFO, "Total Media Files Processed: " + this.totCnt);
		DateNamedPhotoAlbumLoaderImpl.logger.log(Level.INFO, "Total Media Files Successfully Processed: " + this.successCnt);
		DateNamedPhotoAlbumLoaderImpl.logger.log(Level.INFO, "Total Media Files Unsuccessfully Processed: " + this.errorCnt);
		DateNamedPhotoAlbumLoaderImpl.logger.log(Level.INFO, "End AImage File Batch Update");
	    }
	    else {
		System.out.println("Batch start time: " + startTime.toString());
		System.out.println("Batch end time: " + endTime.toString());
		System.out.println("Total Media Files Processed: " + this.totCnt);
		System.out.println("Total Media Files Successfully Processed: " + this.successCnt);
		System.out.println("Total Media Files Unsuccessfully Processed: " + this.errorCnt);
		System.out.println("End Image File Batch Update");
	    }
	    
	    
	    //	    // Send batch report via SMTP
	    //	    try {
	    //		this.sendAvBatchReport();
	    //	    }
	    //	    catch (AvBatchReportException e) {
	    //		logger.log(Level.ERROR, "Audio/Video Batch Report Failed...See Log for details");
	    //	    }
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
     * Process the content of the target input directory containing all photo albums.  
     * <p>
     * The current directory can be one of many root album directories or it 
     * can be an event directory.  Event direcotries are subdirectories of a 
     * given root album.  In either case, the directory type must be appropriately 
     * identified and its metadata persisted to the database before processing 
     * the next level.   Root album directories are generally identified as a date 
     * in the format of <YYYY_MM_DD>.   
     * 
     * @param mediaResource
     * @return
     * @throws AvBatchException
     */
    public Object processDirectory(File mediaResource, Object parent) throws PhotoBatchLoaderException {
	Object parentEntity = null;
	File mediaList[];
	int itemCount = 0;
	Object results = null;

	try {
	    mediaList = mediaResource.listFiles();
	    itemCount = mediaList.length;
	    for (int ndx = 0; ndx < itemCount; ndx++) {
		if (mediaList[ndx].isDirectory()) {
		    parentEntity = this.createDirectoryInstance(mediaList[ndx], parent);
		    // Make recursive call to process next level which should be either an event directory or a file.
		    results = this.processDirectory(mediaList[ndx], parentEntity);
		}
		
		if (mediaList[ndx].isFile()) {
		    this.totCnt++;
		    results = this.processSingleFile(mediaList[ndx], parent);
		    if (results == null) {
			this.errorCnt++;
			logger.warn("Image File ommitted from the database: " + mediaList[ndx].getPath());
		    }
		    else {
			this.successCnt++;
			logger.warn("Image File added to database successfully: " + mediaList[ndx].getPath());
		    }
		}
	    }
	    return 1;
	}
	catch (SecurityException e) {
	    throw new PhotoBatchLoaderException("File system security error", e);
	}
	catch (PhotoAlbumException e) {
	    throw new PhotoBatchLoaderException(e);
	}
    }

    private Object createDirectoryInstance(File f, Object parentEntity) throws SystemException, PhotoAlbumException {
	int parentType = 0;
	if (parentEntity == null && this.isValidAlbumDirectory(f)) {
	    parentType = DIR_TYPE_ALBUM;
	}
	else {
	    if (parentEntity instanceof PhotoAlbum) {
		parentType = DIR_TYPE_EVENT;
	    }
	}

	// Persist either the album directory or the event direcotry 
	// and return the appropriate ORM instance to the caller.
	Object parent = null;
	switch (parentType) {
	case DIR_TYPE_ALBUM:
	    parent = this.persistAlbumn(f);
	    break;
	    
	case DIR_TYPE_EVENT:
	    // persist event directory and return event id
	    parent = this.persistEvent(f, (PhotoAlbum) parentEntity);
	    break;
	}
	return parent;
    }
    
    private Object persistAlbumn(File f) {
	PhotoAlbum a = new PhotoAlbum();
	Date dt = this.parseAlbumName(f.getName());
	a.setAlbumDate(dt);
	a.setAlbumName(f.getName());
	a.setLocation(f.getAbsolutePath());
	
	// URI Info
	URI uri = f.toURI();
	String uriPath = uri.getPath();
	String [] uncParts = RMT2File.getUNCFilename(uriPath);
	
	// Set Albumn Host name and share name
	if (uncParts != null) {
	    a.setServername(uncParts[0]);
	    a.setSharename(uncParts[1]);    
	}
	
	int pk = 0;
	try {
	    pk = this.api.maintain(a);
	    a.setAlbumId(pk);
	}
	catch (PhotoAlbumException e) {
	}
	return a;
    }
    
    
    private Object persistEvent(File f, PhotoAlbum album) throws PhotoAlbumException {
	PhotoEvent evt = new PhotoEvent();
	evt.setAlbumId(album.getAlbumId());
	evt.setEventName(f.getName());
	evt.setLocation(f.getAbsolutePath());
	return this.persistEvent(evt);
    }
    
    private Object persistEvent(PhotoAlbum album) throws PhotoAlbumException {
	PhotoEvent evt = new PhotoEvent();
	evt.setAlbumId(album.getAlbumId());
	evt.setEventName(album.getAlbumName());
	evt.setLocation(album.getLocation());
	return this.persistEvent(evt);
    }
    
    private Object persistEvent(PhotoEvent evt) throws PhotoAlbumException {
	int pk = 0;
	pk = this.api.maintain(evt);
	evt.setEventId(pk);
	return evt;
    }
    
    
    private Date parseAlbumName(String dirName) {
	String[] parts = dirName.split("_");
	Date albumnDate;
	if (parts != null && parts.length == 3) {
	    // Test if directory name translates to a valid date 
	    String dateStr = parts[0] + "/" + parts[1] + "/" + parts[2];
	    try {
		albumnDate = RMT2Date.stringToDate(dateStr);
	    }
	    catch (SystemException e) {
		albumnDate = null;
	    }
	}
	else {
	    albumnDate = null;
	}
	return albumnDate;
    }
    
    private boolean isValidAlbumDirectory(File f) {
	Object rc = this.parseAlbumName(f.getName());
	return (rc != null && rc instanceof Date);
    }

    /**
     * Initiates the media file data extraction process.
     * 
     * @param mediaFile
     * @return
     * @throws MP3ApiInstantiationException
     * @throws AvBatchException
     */
    public Object processSingleFile(File mediaFile, Object parent) throws PhotoBatchLoaderException {
	String pathName;
	PhotoEvent albumEvent = null;
	
	if (parent == null) {
	    this.msg = "Unable to process photo albumn image file due the parent albumn event instance is unavailable";
	    logger.error(this.msg);
	    throw new PhotoBatchLoaderException(this.msg);
	}
	if (parent instanceof PhotoEvent) {
	    // Instance is valid...
	}
	else if (parent instanceof PhotoAlbum) {
	    PhotoAlbum a = (PhotoAlbum) parent;
	    try {
		List <PhotoEvent> list = this.api.findEvent(a.getAlbumName());
		if (list != null) {
		    // Check if event already exist.  If true then use the event from DB to prevent 
		    // duplicates from occurring when the direct parent of an image is the album directory.
		    parent = (PhotoEvent) list.get(0);
		}
		else {
		    parent = this.persistEvent(a);    
		}
	    }
	    catch (Exception e) {
		throw new PhotoBatchLoaderException(e);
	    }
	}
	else {
	    this.msg = "Unable to process photo albumn image file due the parent albumn event instance is of the wrong data type: " + parent.getClass().getName();
	    logger.error(this.msg);
	    throw new PhotoBatchLoaderException(this.msg);
	}
	
	// Cast parent
	albumEvent = (PhotoEvent) parent;
	
	pathName = mediaFile.getPath();
	logger.log(Level.DEBUG, "Processing File: " + pathName);
	
	PhotoImage img = new PhotoImage();
	img.setEventId(albumEvent.getEventId());
	img.setDirPath(mediaFile.getPath());
	img.setFileName(mediaFile.getName());
	String ext = RMT2File.getfileExt(mediaFile.getName());
	img.setFileExt(ext);
	Long fileSize = new Long(mediaFile.length()); 
	img.setFileSize(fileSize.intValue());
	Date dt = new Date();
	img.setDateCreated(dt);
	img.setDateUpdated(dt);
	img.setUserId("LOADER");
	
	MimeContentApi mime = MimeFactory.getMimeContentApiInstance(this.connector, "com.api.db.DefaultSybASABinaryImpl");
	try {
	    List<MimeTypes> list = (List) mime.getMimeType(ext);
	    if (list == null) {
		this.msg = "Image file, " + mediaFile.getAbsolutePath() + ", was not added to the database due to its MIME type is not supported!";
		logger.warn(this.msg);
		return null;
	    }
	    if (list.size() > 0) {
		MimeTypes mt = list.get(0);
		img.setMimeTypeId(mt.getMimeTypeId());
	    }
	}
	catch (MimeException e) {
	    throw new PhotoBatchLoaderException(e);
	}
	finally {
	    mime = null;
	}
	
	int pk = 0;
	try {
	    pk = this.api.maintain(img);
	    img.setImageId(pk);
	}
	catch (PhotoAlbumException e) {
	}
	return img;
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
    //    private void sendAvBatchReport() {
    //	StringBuffer body = new StringBuffer();
    //
    //	// Attempt to obtain From email address from the application's property pool which is loaded at server start up.
    //	String fromAddr = AppPropertyPool.getProperty(HttpSystemPropertyConfig.OWNER_EMAIL);
    //	if (fromAddr == null) {
    //	    // Attempt to obtain From email address from the application's SystemParms.properties file in root of classpath.
    //	    try {
    //		fromAddr = RMT2File.getPropertyValue("SystemParms", HttpSystemPropertyConfig.OWNER_EMAIL);
    //	    }
    //	    catch (Exception e) {
    //		// Attempt to obtain From email address from mime services test environment.
    //		try {
    //		    fromAddr = RMT2File.getPropertyValue("test.resources.SystemParms", HttpSystemPropertyConfig.OWNER_EMAIL);
    //		}
    //		catch (Exception ee) {
    //		    this.msg = "FROM Email address is invalid and will probably be the root cause of transmission failure of Audio/Video Batch Report";
    //		    logger.log(Level.ERROR, this.msg);
    //		    throw new AvBatchReportException(this.msg);
    //		}
    //	    }
    //	}
    //
    //	String toAddr = fromAddr;
    //	if (toAddr == null) {
    //	    this.msg = "TO Email address is invalid and will probably be the root cause of transmission failure of Audio/Video Batch Report";
    //	    logger.log(Level.ERROR, this.msg);
    //	    throw new AvBatchReportException(this.msg);
    //	}
    //	String subject = "Audio/Video Batch Report";
    //	String appname = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_APP_NAME);
    //	appname = (appname == null ? "[Unknown Application]" : appname);
    //
    //	body.append("This is a report of the results of the Audio/Video Batch process\n");
    //
    //	body.append("Start Time: ");
    //	body.append(RMT2Date.formatDate(this.startTime, "MM-dd-yyyy HH:mm:ss.S"));
    //	body.append("\n");
    //	body.append("End Time: ");
    //	body.append(RMT2Date.formatDate(this.endTime, "MM-dd-yyyy HH:mm:ss.S"));
    //	body.append("\n\n\n");
    //
    //	body.append("Total Media Files Processed: " + this.totCnt);
    //	body.append("\n");
    //	body.append("Total Media Files Successfully Processed: " + this.successCnt);
    //	body.append("\n");
    //	body.append("Total Media Files Unsuccessfully Processed: " + this.errorCnt);
    //	body.append("\n\n\n");
    //
    //	if (this.fileErrorMsg.size() > 0) {
    //	    body.append("Detail report of files that failed during this batch process");
    //	    body.append("\n");
    //	    body.append("=============================================================");
    //	    body.append("\n");
    //	}
    //	int count = 0;
    //	// Add details about each file that was processed
    //	for (String msg : this.fileErrorMsg) {
    //	    count++;
    //	    body.append(count);
    //	    body.append(".  ");
    //	    body.append(msg);
    //	    body.append("\n");
    //	}
    //
    //	// Setup bean that represents the email message.
    //	EmailMessageBean bean = new EmailMessageBean();
    //	bean.setFromAddress(fromAddr);
    //
    //	// You can optionally enter multiple email addresses separated by commas
    //	bean.setToAddress(toAddr);
    //	bean.setSubject(subject);
    //	bean.setBody(body.toString(), EmailMessageBean.TEXT_CONTENT);
    //
    //	// Declare and initialize SMTP api and allow the system to discover SMTP host 
    //	SmtpApi api = SmtpFactory.getSmtpInstance();
    //	// Send simple email to its intended destination
    //	try {
    //	    api.sendMessage(bean);
    //	    // Close the service.
    //	    api.close();
    //	}
    //	catch (Exception e) {
    //	    throw new AvBatchReportException(e);
    //	}
    //	return;
    //    }

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
