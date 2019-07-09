package com.api.db;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.List;

import com.api.db.DatabaseException;

import com.bean.Content;

import com.util.RMT2File;
import com.util.RMT2String;
import com.util.SystemException;

/**
 * Generic implementation of the MimeContentApi interface to manage text type MIME content.
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
public class DefaultTextContentImpl extends AbstractMimeContentImpl implements MimeContentApi {
    private static final long serialVersionUID = -5438562667692863170L;

    private static Logger logger = Logger.getLogger(DefaultTextContentImpl.class);

    
    public DefaultTextContentImpl() throws SystemException {
	super();
	return;
    }
    

    /* (non-Javadoc)
     * @see com.bean.RMT2BaseBean#initBean()
     */
    @Override
    protected void initApi() throws DatabaseException, SystemException {
	if (DefaultTextContentImpl.logger == null) {
	    DefaultTextContentImpl.logger = Logger.getLogger(DefaultTextContentImpl.class);
	    DefaultTextContentImpl.logger.log(Level.INFO, "Logger Initialized");
	}
	else {
	    DefaultTextContentImpl.logger.log(Level.INFO, "Logger already initialized");
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
	super.validate(rec);

	// Set app code and module code from the first two values of 
	// the file name that are separated by the character, "_".
	List<String> tokens = RMT2String.getTokens(rec.getFilename(), "_");
	if (tokens != null && tokens.size() > 2) {
	    rec.setAppCode(tokens.get(0));
	    rec.setModuleCode(tokens.get(1));
	}

	// Get mime type id of file name.
	String ext = RMT2File.getfileExt(rec.getFilename());

	// Verify whether MIME data should be text or binary based on the extension of the file name. 
	boolean dataIsText = this.isPlainTextMime(ext);
	if (!dataIsText) {
	    this.msg = "The MIME content is not plain text as determined by the file extension of the data file";
	    DefaultTextContentImpl.logger.log(Level.ERROR, this.msg);
	    throw new MimeValidationException(this.msg);
	}

	// Reset image_data and text_data properties to null and allow separate block of logic 
	// to persist the data to the database later on.
	rec.setImageData(null);
	rec.setTextData(null);

	// Validate if file exist
	File file = new File(rec.getFilepath() + rec.getFilename());
	FileInputStream fis;
	String txtData = null;
	try {
	    fis = new FileInputStream(file);
	    txtData = RMT2File.getStreamStringData(fis);
	}
	catch (FileNotFoundException e) {
	    throw new MimeValidationException("Input file, " + rec.getFilepath() + rec.getFilename() + ", is not found");
	}

	try {
	    // First, insert the record without binary MIME data. 
	    int uid = this.insertRow(rec, true);
	    // Lastly, attempt to update the record with the long binary 
	    // or long text MIME data using straight JDBC call.
	    Statement stmt = this.con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
	    ResultSet rs = stmt.executeQuery("select text_data from content where content_id = " + uid);
	    if (rs != null && rs.next()) {
		rs.updateString("text_data", txtData);
		rs.updateRow();
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


    /* (non-Javadoc)
     * @see com.api.db.MimeContentApi#fetchContentAsFile(int)
     */
    public Content fetchContentAsFile(int uid) throws MimeException {
	throw new UnsupportedOperationException();
    }

}
