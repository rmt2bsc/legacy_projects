package com.api.db;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.api.db.MimeException;
import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;
import com.api.filehandler.FileListenerConfig;

import com.bean.Content;
import com.bean.MimeTypes;

import com.bean.db.DatabaseConnectionBean;

import com.util.NotFoundException;
import com.util.RMT2Base64Encoder;
import com.util.RMT2File;
import com.util.SystemException;

/**
 * An common implementation of the MimeContentApi interface which an lacks the functionality 
 * to add content to the data source.    
 * <p>
 * <b>NOTE</b><br>
 * It is best to use vanilla JDBC calls instead of the ORM api to obtain data via the 
 * mime services bean classes, because of the method used to locate the ORM datasource 
 * entity view config files.   The location of each datasource entity view config file 
 * is identified as an absolute file path in the file system which is configured in the 
 * web applicaltion's AppParms.properties file and computed at web server start up.   
 * The entity bean classes are stored in a .jar file of the class path of the specific 
 * application that uses the mime service library.   
 * 
 * @author appdev
 *
 */
abstract class AbstractMimeContentImpl extends RdbmsDaoImpl {
    private static final long serialVersionUID = -5438562667692863170L;

    private static Logger logger = Logger.getLogger("AbstractMimeContentImpl");

    private RdbmsDaoQueryHelper daoHelper;

    protected FileListenerConfig config;

    public AbstractMimeContentImpl() throws SystemException {
	super();
	return;
    }

    public void initApi(DatabaseConnectionBean dbCon) throws DatabaseException, SystemException {
	this.connector = dbCon;
	this.con = this.connector.getDbConn();
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /* (non-Javadoc)
     * @see com.bean.RMT2BaseBean#initBean()
     */
    @Override
    protected void initApi() throws DatabaseException, SystemException {
	if (AbstractMimeContentImpl.logger == null) {
	    AbstractMimeContentImpl.logger = Logger.getLogger("AbstractMimeContentImpl");
	    AbstractMimeContentImpl.logger.log(Level.INFO, "Logger Initialized");
	}
	else {
	    AbstractMimeContentImpl.logger.log(Level.INFO, "Logger already initialized");
	}
    }

    /**
     * Verifies if file name and file path properties are present.   Validates whether the file 
     * extension is registered in the database.  If the MIME type exists, then the MIME type id 
     * is assigned to <i>rec</i>.
     * 
     * @param rec
     * @throws MimeValidationException
     *           when either the file name or file path is not present.
     * @throws MimeException
     *            if general database errors while attempting to obtain MIME 
     *            type data based on file extension.           
     */
    protected void validate(Content rec) throws MimeException {
	logger.log(Level.INFO, "Validate file name");
	if (rec.getFilename() == null) {
	    this.msg = "File name is required to persist MIME type";
	    AbstractMimeContentImpl.logger.log(Level.ERROR, this.msg);
	    throw new MimeValidationException(this.msg);
	}
	logger.log(Level.INFO, "Validate file path");
	if (rec.getFilepath() == null) {
	    this.msg = "File path is required to persist MIME type";
	    AbstractMimeContentImpl.logger.log(Level.ERROR, this.msg);
	    throw new MimeValidationException(this.msg);
	}

	logger.log(Level.INFO, "Pass general content record validations");
	// Get mime type id of file name.
	String ext = RMT2File.getfileExt(rec.getFilename());
	List<MimeTypes> list = this.getMimeType(ext);
	if (list == null) {
	    this.msg = "File, " + rec.getFilename() + ", was not persisted due to file extension is not registered in the MIME database";
	    AbstractMimeContentImpl.logger.log(Level.ERROR, this.msg);
	    throw new MimeValidationException(this.msg);
	}
	MimeTypes mt = list.get(0);
	rec.setMimeTypeId(mt.getMimeTypeId());
    }

    /**
     * Delete multi media content from the database
     * 
     * @param uid
     *          a integer value representing the database primary key of the 
     *           content record that is to be deleted.
     * @return int
     *          the total number of rows deleted.
     * @throws MimeException
     */
    public int deleteContent(int uid) throws MimeException {
	Content mime = new Content();
	mime.addCriteria(Content.PROP_CONTENTID, uid);
	try {
	    int rows = this.deleteRow(mime);
	    return rows;
	}
	catch (Exception e) {
	    throw new MimeException(e);
	}

    }

    /**
     * Retrieves multi media content by its primary key value.  The columns image_data and text_data 
     * are excluded for performance reasons.  The implementor will have to provide vendor specific 
     * logic desgined for optimum performance in obtaining the large binary or large text column data. 
     * 
     * @param uid
     *           a integer value representing the database primary key of the 
     *           multi media content that is to be fetched.
     * @return Object 
     *           {@link com.bean.Content Content}
     * @throws MimeException
     *         NotFoundException
     */
    public Content fetchContent(int uid) throws MimeException {
	Content mime = new Content();
	String sql = "select content_id, mime_type_id, app_code, module_code, filepath, filename, size, date_created, user_id, image_data, text_data from content where content_id = " + uid;
	logger.info("Execute Query: " + sql);
	ResultSet rs = this.executeQuery(sql);
	try {
	    if (rs != null && rs.next()) {
		mime.setContentId(rs.getInt("content_id"));
		mime.setMimeTypeId(rs.getInt("mime_type_id"));
		mime.setAppCode(rs.getString("app_code"));
		mime.setModuleCode(rs.getString("module_code"));
		mime.setFilepath(rs.getString("filepath"));
		mime.setFilename(rs.getString("filename"));
		mime.setSize(rs.getInt("size"));
		mime.setDateCreated(rs.getDate("date_created"));
		mime.setUserId(rs.getString("user_id"));
		mime.setTextData(rs.getString("text_data"));

		// Use base64 encoding to encode image data in order to transport over the wire without data corruption
		byte binaryData[] = rs.getBytes("image_data");
		if (binaryData != null) {
		    String encodedImg = RMT2Base64Encoder.encode(binaryData);
		    mime.setImageData(encodedImg);
		}
		else {
		    mime.setImageData(null);
		}
		return mime;
	    }
	    else {
		throw new NotFoundException("Content Image is not found using content id: " + uid);
	    }
	}
	catch (SQLException e) {
	    throw new MimeException(e);
	}
	
    }

    /* (non-Javadoc)
     * @see com.api.db.MimeContentApi#getMimeTypes()
     */
    public List<MimeTypes> getMimeTypes() throws MimeException {
	MimeTypes mt = new MimeTypes();
	return this.daoHelper.retrieveList(mt);
    }

    /* (non-Javadoc)
     * @see com.api.db.MimeContentApi#getMimeType(java.lang.String)
     */
    public List<MimeTypes> getMimeType(String fileExt) throws MimeException {
	logger.log(Level.INFO, "Get mime type record for file extension, " + fileExt);
	if (this.con == null) {
	    this.msg = "Database connection is invalid";
	    logger.log(Level.ERROR, this.msg);
	    throw new DatabaseException(this.msg);
	}
	String sql = "select * from mime_types where file_ext = \'" + fileExt + "\'";
	logger.log(Level.INFO, sql);
	ResultSet rs = this.executeQuery(sql);
	List<MimeTypes> list = new ArrayList<MimeTypes>();
	try {
	    while (rs.next()) {
		MimeTypes mt = new MimeTypes();
		mt.setMimeTypeId(rs.getInt("mime_type_id"));
		mt.setFileExt(rs.getString("file_ext"));
		mt.setMediaType(rs.getString("media_type"));
		list.add(mt);
	    }
	    if (list.size() == 0) {
		list = null;
	    }
	    return list;
	}
	catch (SQLException e) {
	    throw new MimeException(e);
	}
    }

    /* (non-Javadoc)
     * @see com.api.db.MimeContentApi#getMimeType(int)
     */
    public MimeTypes getMimeType(int mimeTypeId) throws MimeException {
	if (this.con == null) {
	    this.msg = "Database connection is invalid";
	    logger.log(Level.ERROR, this.msg);
	    throw new DatabaseException(this.msg);
	}
	String sql = "select * from mime_types where mime_type_id = " + mimeTypeId;
	ResultSet rs = this.executeQuery(sql);
	if (rs == null) {
	    return null;
	}
	try {
	    while (rs.next()) {
		MimeTypes mt = new MimeTypes();
		mt.setMimeTypeId(rs.getInt("mime_type_id"));
		mt.setFileExt(rs.getString("file_ext"));
		mt.setMediaType(rs.getString("media_type"));
		return mt;
	    }
	    return null;
	}
	catch (SQLException e) {
	    throw new MimeException(e);
	}
    }

    /* (non-Javadoc)
     * @see com.api.db.MimeContentApi#getMimeTypeExt(java.lang.String)
     */
    public Object getMimeTypeExt(String mediaType) throws MimeException {
	throw new UnsupportedOperationException();
    }

    /**
     * @return the daoHelper
     */
    public RdbmsDaoQueryHelper getDaoHelper() {
	return daoHelper;
    }

    /* (non-Javadoc)
     * @see com.api.db.MimeContentApi#setConfig(com.api.filehandler.FileListenerConfig)
     */
    public void setConfig(FileListenerConfig config) {
	this.config = config;
    }
}
