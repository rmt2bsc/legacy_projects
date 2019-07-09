package com.api.db;

import java.io.File;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;

import javax.activation.FileTypeMap;

import com.api.filehandler.InboundFileFactory;
import com.bean.db.DatabaseConnectionBean;
import com.util.RMT2File;

/**
 * Provides an interface for data typing files by their file extension.  MIME type 
 * definitions are loaded from the MIME_TYPE table of the MIME database into a Map 
 * collection.
 * 
 * @author appdev
 *
 */
public class RMT2FileTypeMap extends FileTypeMap {
    private static Map<String, String> MIMEMAP;

    /**
     * Creates a  RMT2FileTypeMap object with a Map of MIME type descriptions keyed by file extension.
     */
    public RMT2FileTypeMap() {
	// Load Map of MIME types
	if (RMT2FileTypeMap.MIMEMAP == null) {
	    this.loadMimeTypes();
	}
    }

    /* (non-Javadoc)
     * @see javax.activation.FileTypeMap#getContentType(java.io.File)
     */
    @Override
    public String getContentType(File file) {
	String fileName = file.getAbsolutePath();
	return this.getContentType(fileName);
    }

    /* (non-Javadoc)
     * @see javax.activation.FileTypeMap#getContentType(java.lang.String)
     */
    @Override
    public String getContentType(String fileName) {
	String fileExt = RMT2File.getfileExt(fileName);
	String contentType = RMT2FileTypeMap.MIMEMAP.get(fileExt);
	return contentType;
    }

    /**
     * Load a Map collection with MIME type definitions where the key will represent 
     * the file extension prefixed with a "." character.  The value will represent 
     * the file content type description. 
     */
    private void loadMimeTypes() {
	String configFile = InboundFileFactory.ENV + "_" + InboundFileFactory.configFile;
	String dbUrl = RMT2File.getPropertyValue(configFile, "mime.dbURL");
	DatabaseConnectionBean con = JdbcFactory.getConnection(dbUrl, configFile);
	MimeContentApi api = MimeFactory.getMimeContentApiInstance("com.api.db.DefaultFileSystemContentImpl");
	api.initApi(con);
	try {
	    Statement stmt = con.getDbConn().createStatement();
	    ResultSet rs = stmt.executeQuery("select file_ext, media_type from mime_types order by file_ext");
	    while (rs.next()) {
		if (RMT2FileTypeMap.MIMEMAP == null) {
		    RMT2FileTypeMap.MIMEMAP = new HashMap<String, String>();    
		}
		String key = rs.getString("file_ext");
		String value = rs.getString("media_type");
		RMT2FileTypeMap.MIMEMAP.put(key, value);
	    }
	}
	catch (Exception e) {
	    RMT2FileTypeMap.MIMEMAP = null;
	}
    }

}
