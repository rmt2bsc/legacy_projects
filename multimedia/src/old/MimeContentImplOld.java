package com.api.db;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.Content;
import com.bean.MimeTypes;
import com.bean.db.DatabaseConnectionBean;

import com.util.RMT2File;
import com.util.RMT2String;
import com.util.SystemException;

/**
 * Implementation of the MimeContentApi interface.
 * 
 * @author appdev
 *
 */
class MimeContentImplOld extends RdbmsDaoImpl implements MimeContentApi {
    private static final long serialVersionUID = -5438562667692863170L;

    private static Logger logger;

    private RdbmsDaoQueryHelper daoHelper;

    private DatabaseConnectionBean dbCon;

    /**
     * @throws SystemException
     */
    protected MimeContentImplOld(DatabaseConnectionBean dbCon) throws SystemException {
	super(dbCon);
	this.dbCon = dbCon;
	this.daoHelper = new RdbmsDaoQueryHelper(this.dbCon);
	return;
    }

    /* (non-Javadoc)
     * @see com.bean.RMT2BaseBean#initBean()
     */
    @Override
    protected void initApi() throws DatabaseException, SystemException {
	if (MimeContentImplOld.logger == null) {
	    MimeContentImplOld.logger = Logger.getLogger(MimeContentImplOld.class);
	    MimeContentImplOld.logger.log(Level.INFO, "Logger Initialized");
	}
	else {
	    MimeContentImplOld.logger.log(Level.INFO, "Logger already initialized");
	}
    }

    /**
     * Adds multi media content to the database.  The content can be text character 
     * sets and various binary data sets.
     * 
     * @param rec
     *          {@link com.bean.Content Content}
     * @return int
     *           the new mime type id just added.
     * @throws MimeException
     *           <ul>
     *             <li>if the filename is absent</li>
     *             <li>if the image data and text data are both null</li>
     *             <li>if the image data and text data are not mutually exclusive</li>
     *             <li>when the image or text data is found to be of the incorrect format based on the extension for the file name</li>
     *           </ul>
     */
    public int addContent(Content rec) throws MimeException {
	if (rec.getFilename() == null) {
	    this.msg = "Filename must be available in order to persist MIME type";
	    MimeContentImplOld.logger.log(Level.ERROR, this.msg);
	    throw new MimeException(this.msg);
	}
	if (rec.getImageData() != null && rec.getTextData() != null) {
	    this.msg = "MIME content object cannot be composed of both binary and text data";
	    MimeContentImplOld.logger.log(Level.ERROR, this.msg);
	    throw new MimeException(this.msg);
	}
	if (rec.getImageData() == null && rec.getTextData() == null) {
	    this.msg = "MIME content object must be represented by either binary and text data";
	    MimeContentImplOld.logger.log(Level.ERROR, this.msg);
	    throw new MimeException(this.msg);
	}

	// Set app code and module code from the first two values of 
	// the file name that are separated by the character, "_".
	List<String> tokens = RMT2String.getTokens(rec.getFilename(), "_");
	if (tokens != null && tokens.size() > 2) {
	    rec.setAppCode(tokens.get(0));
	    rec.setModuleCode(tokens.get(1));
	}

	// Get mime type id of file name.
	String ext = RMT2File.getfileExt(rec.getFilename());
	List<MimeTypes> list = this.getMimeType(ext);
	if (list == null) {
	    this.msg = "File, " + rec.getFilename() + ", was not persisted due to file extension is not registered in the MIME database";
	    MimeContentImplOld.logger.log(Level.ERROR, this.msg);
	    throw new MimeException(this.msg);
	}
	MimeTypes mt = list.get(0);
	rec.setMimeTypeId(mt.getMimeTypeId());

	// Verify whether MIME data should be text or binary based on the extension of the file name. 
	boolean dataIsText = this.isPlainTextMime(ext);
	
	// Verify if the target property is assigned the appropriate data
	if (dataIsText) {
	    if (rec.getTextData() == null) {
		this.msg = "Based on the extension of the filename [ " + rec.getFilename() + "], the MIME data is required to be assigned to the the text data property";
		MimeContentImplOld.logger.log(Level.ERROR, this.msg);
		throw new MimeException(this.msg);
	    }
	}
	else {
	    if (rec.getImageData() == null) {
		this.msg = "Based on the extension of the filename [ " + rec.getFilename() + "], the MIME data is required to be assigned to the the binary [image] data property";
		MimeContentImplOld.logger.log(Level.ERROR, this.msg);
		throw new MimeException(this.msg);
	    }
	}

	// Reset image_data and text_data properties to null and allow separate block of logic 
	// to persist the data to the database later on.
	InputStream binaryData = rec.getImageData();
	String txtData = rec.getTextData();
	rec.setImageData(null);
	rec.setTextData(null);

	try {
	    // First, insert the record without binary MIME data. 
	    int uid = this.insertRow(rec, true);
	    // Lastly, attempt to update the record with the long binary 
	    // or long text MIME data using straight JDBC call.
	    if (dataIsText) {
		Statement stmt = this.dbCon.getDbConn().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery("select text_data from content where content_id = " + uid);
		if (rs != null && rs.next()) {
		    rs.updateString("text_data", txtData);
		    rs.updateRow();
		}
	    }
	    else {
		Statement stmt = this.dbCon.getDbConn().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery("select image_data from content where content_id = " + uid);
		if (rs != null && rs.next()) {
		    rs.updateBinaryStream("image_data", binaryData, rec.getSize());
		    rs.updateRow();
		}
	    }
	    return uid;
	}
	catch (Exception e) {
	    throw new MimeException(e);
	}
    }

    /**
     * Determine if MIME extension is truely plain text.
     * 
     * @param fileExt
     * @return
     * @throws MimeException
     */
    private boolean isPlainTextMime(String fileExt) throws MimeException {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT a.file_ext file_ext, a.media_type media_type  ");
	sql.append("FROM MIME_TYPES a  ");
	sql.append("where a.file_ext in (select c.file_ext from mime_types c where c.media_type like \'text/%\' and c.media_type = a.media_type)   ");
	sql.append("and a.file_ext = \'");
	sql.append(fileExt);
	sql.append("\'  ");
	sql.append("order by a.media_type  ");

	try {
	    ResultSet rs = this.executeQuery(sql.toString());
	    if (rs != null && rs.next()) {
		// RTF file format is an exception.
		if (fileExt.equalsIgnoreCase(".rtf")) {
		    return false;
		}
		return true;
	    }
	    return false;
	}
	catch (Exception e) {
	    throw new MimeException(e);
	}
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
     * Retrieves multi media content by its primary key value.
     * 
     * @param uid
     *           a integer value representing the database primary key of the 
     *           multi media content that is to be fetched.
     * @return Object 
     *           {@link com.bean.Content Content}
     * @throws MimeException
     */
    public Content fetchContent(int uid) throws MimeException {
	Content mime = new Content();
	mime.addCriteria(Content.PROP_CONTENTID, uid);
	Content result = (Content) this.daoHelper.retrieveObject(mime);
	return result;
    }

    /* (non-Javadoc)
     * @see com.api.db.MimeContentApi#getMimeTypes()
     */
    public List<MimeTypes> getMimeTypes() throws MimeException {
	// TODO Auto-generated method stub
	return null;
    }

    /* (non-Javadoc)
     * @see com.api.db.MimeContentApi#getMimeType(java.lang.String)
     */
    public List<MimeTypes> getMimeType(String fileExt) throws MimeException {
	MimeTypes mt = new MimeTypes();
	mt.addCriteria(MimeTypes.PROP_FILEEXT, fileExt);
	try {
	    List result = this.daoHelper.retrieveList(mt);
	    if (result == null || result.size() <= 0) {
		return null;
	    }
	    return result;
	}
	catch (DatabaseException e) {
	    throw new MimeException(e);
	}

    }

    /* (non-Javadoc)
     * @see com.api.db.MimeContentApi#getMimeType(int)
     */
    public MimeTypes getMimeType(int mimeTypeId) throws MimeException {
	throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see com.api.db.MimeContentApi#getMimeTypeExt(java.lang.String)
     */
    public Object getMimeTypeExt(String mediaType) throws MimeException {
	throw new UnsupportedOperationException();
    }

}
