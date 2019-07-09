package com.api.db;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import com.api.config.HttpSystemPropertyConfig;

import com.api.db.DatabaseException;

import com.bean.Content;

import com.util.RMT2File;
import com.util.SystemException;

/**
 * File system database implementation of the MimeContentApi interface to manage large 
 * binary MIME content that exists as files.  
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
public class DefaultFileSystemContentImpl extends AbstractMimeContentImpl implements MimeContentApi {
    private static final long serialVersionUID = -5438562667692863170L;

    private static Logger logger = Logger.getLogger(DefaultFileSystemContentImpl.class);

    public DefaultFileSystemContentImpl() throws SystemException {
	super();
	return;
    }

    /* (non-Javadoc)
     * @see com.bean.RMT2BaseBean#initBean()
     */
    @Override
    protected void initApi() throws DatabaseException, SystemException {
	if (DefaultFileSystemContentImpl.logger == null) {
	    DefaultFileSystemContentImpl.logger = Logger.getLogger(DefaultFileSystemContentImpl.class);
	    DefaultFileSystemContentImpl.logger.log(Level.INFO, "Logger Initialized");
	}
	else {
	    DefaultFileSystemContentImpl.logger.log(Level.INFO, "Logger already initialized");
	}
    }

    /**
     * Not supported.
     */
    public int addContent(Content rec) throws MimeException {
	throw new UnsupportedOperationException();
    }

    /**
     * Retrieves long binary multi media content by file name from the file system.  
     * The content is loaded into an instance of {@link com.bean.Content Content} from disk 
     * where the file path is designated as "mime.outboundDir" configuration parameter and 
     * the file name is the value of the "filename" column belonging to the content table.  
     * 
     * @param uid
     *           a integer value representing the database primary key of the 
     *           multi media content that is to be fetched.
     * @return  {@link com.bean.Content Content}
     * @throws MimeException
     */
    public Content fetchContent(int uid) throws MimeException {

	// Try to input file to Content instance.
	Content mime = null;
	try {
	    mime = this.fetchContentAsFile(uid);
	}
	catch (Exception e) {
	    throw new MimeException(e);
	}
	if (mime.getImageData() instanceof File) {
	    File file = (File) mime.getImageData();
	    mime.setFilepath(file.getAbsolutePath());
	    try {
		FileDataSource fds = new FileDataSource(file);
		// Load MIME Types into custom FileTypeMap instance.
		fds.setFileTypeMap(new RMT2FileTypeMap());
		DataHandler dh = new DataHandler(fds);
		mime.setImageData(dh);
	    }
	    catch (Exception e) {
		mime.setFilepath(file.getAbsolutePath());
	    }
	}
	else {
	    mime.setImageData(null);
	}
	return mime;
    }

    /* (non-Javadoc)
     * @see com.api.db.MimeContentApi#fetchContentAsFile(int)
     */
    public Content fetchContentAsFile(int uid) throws MimeException {
	Content mime = super.fetchContent(uid);
	if (mime == null) {
	    return mime;
	}

	String configFile = System.getProperty(HttpSystemPropertyConfig.PROPNAME_ENV) + "_MimeConfig";
	String fetchDir = RMT2File.getPropertyValue(configFile, "mime.fetchDir");

	// Try to input file to Content instance.
	File file = new File(fetchDir + mime.getFilename());
	int fileVerifyCode = RMT2File.verifyFile(file);
	if (fileVerifyCode != RMT2File.FILE_IO_EXIST) {
	    this.msg = "Problem occurred fetching MIME content from file system...Error code is: " + fileVerifyCode + ".  [Verify code with RMT2File.FILE_IO_* constant values]";
	    logger.log(Level.ERROR, this.msg);
	    throw new MimeException(this.msg);
	}
	mime.setImageData(file);
	return mime;
    }

}
