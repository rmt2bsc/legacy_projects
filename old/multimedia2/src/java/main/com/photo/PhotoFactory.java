package com.photo;

import org.apache.log4j.Logger;

import com.api.BatchFileProcessor;
import com.api.db.DatabaseTransApi;

import com.bean.db.DatabaseConnectionBean;
import com.controller.Request;

/**
 * 
 * @author appdev
 *
 */
public class PhotoFactory {
    
    /**
     * 
     * @param con
     * @return
     */
    public static BatchFileProcessor createFileProcessor(DatabaseConnectionBean con) {
	try {
	    BatchFileProcessor api = new DateNamedPhotoAlbumLoaderImpl(con);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }
    
    public static BatchFileProcessor createFileProcessor(DatabaseTransApi tx) {
	try {
	    DatabaseConnectionBean con = (DatabaseConnectionBean) tx.getConnector();
	    BatchFileProcessor api = new DateNamedPhotoAlbumLoaderImpl(con);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }
    
    /**
     * 
     * @param dbConn
     * @return
     */
    public static final PhotoAlbumApi createApi(DatabaseConnectionBean dbConn) {
	try {
	    PhotoAlbumApi api = new PhotoAlbumImpl(dbConn);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * 
     * @param dbConn
     * @param request
     * @return
     */
    public static final PhotoAlbumApi createApi(DatabaseConnectionBean dbConn, Request request) {
	try {
	    PhotoAlbumApi api = new PhotoAlbumImpl(dbConn, request);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }
    
    
}