package com.api.db;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.File;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.List;

import com.api.config.HttpSystemPropertyConfig;
import com.api.db.DatabaseException;
import com.api.filehandler.MimeFileProcessor;

import com.bean.Content;

import com.util.RMT2File;
import com.util.RMT2String;
import com.util.SystemException;

/**
 * Sybase SQL Anywhere implementation of the MimeContentApi interface to manage large 
 * binary MIME content.  
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
public class DefaultSybASABinaryImpl extends AbstractMimeContentImpl implements MimeContentApi {
    private static final long serialVersionUID = -5438562667692863170L;

    private static Logger logger = Logger.getLogger("DefaultSybASABinaryImpl");

    public DefaultSybASABinaryImpl() throws SystemException {
	super();
	return;
    }
    

    /* (non-Javadoc)
     * @see com.bean.RMT2BaseBean#initBean()
     */
    @Override
    protected void initApi() throws DatabaseException, SystemException {
	if (DefaultSybASABinaryImpl.logger == null) {
	    DefaultSybASABinaryImpl.logger = Logger.getLogger("DefaultSybASABinaryImpl");
	    DefaultSybASABinaryImpl.logger.log(Level.INFO, "Logger Initialized");
	}
	else {
	    DefaultSybASABinaryImpl.logger.log(Level.INFO, "Logger already initialized");
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
	logger.log(Level.INFO, "Validate Content record");
	try {
	    super.validate(rec);    
	}
	catch (Throwable e) {
	    e.printStackTrace();
	    logger.log(Level.ERROR, e.getMessage());
	    throw new MimeException(e.getMessage());
	}
	logger.log(Level.INFO, "Begin to add general record data to content table");
	rec.setUserId("mime_thread");
	// Set app code and module code from the first two values of 
	// the file name that are separated by the character, "_".
	List<String> tokens = RMT2String.getTokens(rec.getFilename(), "_");
	if (tokens != null && tokens.size() > 2) {
	    rec.setAppCode(tokens.get(0));
	    rec.setModuleCode(tokens.get(1));
	}
	
	try {
	    // Treat input path as if we are running a local process.
	    String inPath = rec.getFilepath() + rec.getFilename();
	    
	    // If not local process, copy file to database server to satisfy the 
	    // requirement that the file must be local to the DB Server
	    if (!this.config.isArchiveLocal()) {
		String outPath = config.getOutboundDir() + rec.getFilename();
		RMT2File.copyFileWithOverwrite(inPath, outPath);
		// Change input path to equal remote output path to satisfy ASA DB Server requirements.
		logger.log(Level.INFO, "Image file was copied from the remote location, " + inPath + ", to " + outPath + " successfully");
		inPath = outPath;
	    }
	    
	    // Obtain next primary key value for content table.
	    Statement stmt = this.con.createStatement();
	    ResultSet rs = stmt.executeQuery("select max(content_id) cur_key_val from content");
	    int nextKeyVal = 0;
	    if (rs != null && rs.next()) {
		nextKeyVal = rs.getInt("cur_key_val");
		nextKeyVal++;
	    }
	    else {
		this.msg = "Unable to add row for content table.  Error occured obtaining the next primary key value for the content table";
		logger.log(Level.ERROR, this.msg);
		throw new MimeException(this.msg);
	    }
	    
	    // First, insert the record without binary MIME data.
	    int uid = 0;
	    StringBuffer query = new StringBuffer();
	    query.append("insert into content (content_id, mime_type_id, app_code, module_code, filepath, filename, size, user_id, image_data) values (");
	    query.append(nextKeyVal);
	    query.append(", ");
	    query.append(rec.getMimeTypeId());
	    query.append(", \'");
	    query.append(rec.getAppCode());
	    query.append("\', \'");
	    query.append(rec.getModuleCode());
	    query.append("\', \'");
	    query.append(rec.getFilepath());
	    query.append("\', \'");
	    query.append(rec.getFilename());
	    query.append("\', ");
	    query.append(rec.getSize());
	    query.append(", \'");
	    query.append(rec.getUserId());
	    query.append("\', ");
	    
	    query.append("(select dbo.xp_read_file(\'" + inPath + "\'))) ");
	    stmt = this.con.createStatement();
	    logger.log(Level.INFO, query.toString());
	    int rc = stmt.executeUpdate(query.toString(), Statement.RETURN_GENERATED_KEYS);
	    if (rc == 1) {
		rs = stmt.getGeneratedKeys();
		if (rs.next()) {
		    String temp = rs.getString(1);
		    uid = Integer.parseInt(temp);
		}
		else {
		    uid = -1;
		}
	    }
	    return uid;
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new MimeException(e);
	}
    }
    
    /**
     * Retrieves multi media content where the image content is represented as a File 
     * instance by its unique id.
     * 
     * @param uid
     * @return
     * @throws MimeException
     */
    public Content fetchContentAsFile(int uid) throws MimeException {
	Content mime = super.fetchContent(uid);
	if (mime == null) {
	    return mime;
	}
	
	String configFile = MimeFileProcessor.CONFIG_CLASSPATH + System.getProperty(HttpSystemPropertyConfig.PROPNAME_ENV) + "_MimeConfig";
	String outBoundDir = RMT2File.getPropertyValue(configFile, "mime.outboundDir");
	String sql = "select xp_write_file(\'" + outBoundDir + "\' || filename, image_data) from content where content_id = " + uid;
	this.executeQuery(sql);

	// Try to input file to Content instance.
	File file = new File(outBoundDir + mime.getFilename());
	mime.setImageData(file);
	mime.setFilepath(outBoundDir);
	return mime;
    }
    
    
    
    /**
     * Retrieves multi media content where the image content is represented as an InputStream.  
     * The content is written to disk where the file path is designated as "mime.outboundDir" 
     * configuration parameter and the file name is the value of the "filename" column belonging 
     * to the content table.  Once the file is successfully written to disk, a byte stream is 
     * created and assigned to the property, imageData, of the Content instance that is returned 
     * to the client.  
     * 
     * @param uid
     *           a integer value representing the database primary key of the 
     *           multi media content that is to be fetched.
     * @return  {@link com.bean.Content Content}
     * @throws BatchFileException
     */
    public Content fetchContent(int uid) throws MimeException {
	Content mime = super.fetchContent(uid);
	if (mime == null) {
	    return mime;
	}
	return mime;
    }
    
}
