package com.api.db;


import com.api.filehandler.FileListenerConfig;

import com.bean.Content;

import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;

/**
 * An interface designed to manage the persistence and retrieval of Multipurpose Internet 
 * Mail Extensions (MIME) data to and from some external data source.
 * 
 * @author appdev
 *
 */
public interface MimeContentApi {
    
    /**
     * 
     * @param dbCon
     * @throws DatabaseException
     * @throws SystemException
     */
    void initApi(DatabaseConnectionBean dbCon) throws DatabaseException, SystemException;
    
    /**
     * Assigns file listener configuration instance
     * 
     * @param config
     */
    void setConfig(FileListenerConfig config);

    /**
     * Adds multi media content to some external datasource.
     * 
     * @param rec
     *          {@link com.bean.Content Content}
     * @return int
     *           the new mime type id just added.
     * @throws MimeException
     */
    int addContent(Content rec) throws MimeException;

    /**
     * Retrieves multi media content by its unique id.
     * 
     * @param uid
     *           a integer value representing the unique id or primary key of the 
     *           multi media content that is to be fetched.
     * @return Content 
     *           a Content object representing the mime type record related to 
     *           the uid, <i>uid</i>
     * @throws MimeException
     */
    Content fetchContent(int uid) throws MimeException;
    
    /**
     * Retrieves multi media content where the image content is represented as a File 
     * instance by its unique id.
     * 
     * @param uid
     * @return
     * @throws MimeException
     */
    Content fetchContentAsFile(int uid) throws MimeException;

    /**
     * Delete multi media content from some external data source.
     * 
     * @param uid
     *          a integer value representing the unique id or primary key of the 
     *           multi media content that is to be deleted.
     * @return int
     *          the total number of rows deleted.
     * @throws MimeException
     */
    int deleteContent(int uid) throws MimeException;

    /**
     * Retrieve all the mime type records from some external data source.
     * 
     * @return Object
     *           an arbitrary list of all the mime types registered in the database.
     * @throws MimeException
     */
    Object getMimeTypes() throws MimeException;

    /**
     * Retrieve a specific mime type record based on its unique id.
     * 
     * @param mimeTypeId
     *           the id of the mime type record that is to be fetched.
     * @return Object 
     *           an arbitrary object representing the mime type record related to 
     *           the uid, <i>mimeTypeid</i>
     * @throws MimeException
     */
    Object getMimeType(int mimeTypeId) throws MimeException;
    
    /**
     * Retrieve all mime type records applicable to a specific file extension.
     * 
     * @param fileExt
     *           the file extension
     * @return Object
     *           an arbitrary list of all the mime types applicable to the file 
     *           extention, <i>fileExt</i>.
     * @throws MimeException
     */
    Object getMimeType(String fileExt) throws MimeException;
    
    /**
     * Retrieves a list of file extensions applicable to the selected media type.
     * 
     * @param mediaType
     *           the media type of MIME type description.
     * @return Object
     *           an arbitrary list of all the mime types applicable to the media  
     *           type, <i>mediaType</i>.
     * @throws MimeException
     */
    Object getMimeTypeExt(String mediaType) throws MimeException;
}
