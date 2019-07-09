package com.photo;

import java.util.List;

import org.apache.log4j.Logger;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.PhotoAlbum;
import com.bean.PhotoEvent;
import com.bean.PhotoImage;
import com.bean.VwPhoto;

import com.bean.custom.UserTimestamp;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.util.RMT2Date;
import com.util.SystemException;

/**
 * @author rterrell
 *
 */
class PhotoAlbumImpl extends RdbmsDaoImpl implements PhotoAlbumApi {
    
    private static final Logger logger = Logger.getLogger("PhotoAlbumImpl");
    
    private RdbmsDaoQueryHelper helper;
    
    /**
     * @throws DatabaseException
     * @throws SystemException
     */
    protected PhotoAlbumImpl() throws DatabaseException, SystemException {
	super();
	logger.info("Logger created");
    }

    /**
     * @param dbConn
     * @throws DatabaseException
     * @throws SystemException
     */
    public PhotoAlbumImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
	this.helper = new RdbmsDaoQueryHelper(dbConn);
    }

    /**
     * @param dbConn
     * @param request
     * @throws DatabaseException
     * @throws SystemException
     */
    public PhotoAlbumImpl(DatabaseConnectionBean dbConn, Request request) throws DatabaseException, SystemException {
	super(dbConn, request);
	this.helper = new RdbmsDaoQueryHelper(dbConn);
    }

   
    
    /* (non-Javadoc)
     * @see com.photo.PhotoAlbumApi#findAlbum(int)
     */
    public PhotoAlbum findAlbum(int albumId) throws PhotoAlbumException {
	PhotoAlbum obj = new PhotoAlbum();
	obj.addCriteria(PhotoAlbum.PROP_ALBUMID, albumId);
	obj = (PhotoAlbum) this.helper.retrieveObject(obj);
	return obj;
    }

    /* (non-Javadoc)
     * @see com.photo.PhotoAlbumApi#findAlbum(java.lang.String)
     */
    public List<PhotoAlbum> findAlbum(String name) throws PhotoAlbumException {
	PhotoAlbum obj = new PhotoAlbum();
	obj.addCriteria(PhotoAlbum.PROP_ALBUMNAME, name);
	List <PhotoAlbum> list = this.helper.retrieveList(obj);
	return list;
    }

    /* (non-Javadoc)
     * @see com.photo.PhotoAlbumApi#findEvent(int)
     */
    public PhotoEvent findEvent(int eventId) throws PhotoAlbumException {
	PhotoEvent obj = new PhotoEvent();
	obj.addCriteria(PhotoEvent.PROP_EVENTID, eventId);
	obj = (PhotoEvent) this.helper.retrieveObject(obj);
	return obj;
    }

    /* (non-Javadoc)
     * @see com.photo.PhotoAlbumApi#findEvent(java.lang.String)
     */
    public List<PhotoEvent> findEvent(String name) throws PhotoAlbumException {
	PhotoEvent obj = new PhotoEvent();
	obj.addCriteria(PhotoEvent.PROP_EVENTNAME, name);
	List <PhotoEvent> list = this.helper.retrieveList(obj);
	return list;
    }
    
    /* (non-Javadoc)
     * @see com.photo.PhotoAlbumApi#findAlbumEvents(int)
     */
    public List<PhotoEvent> findAlbumEvents(int albumId) throws PhotoAlbumException {
	PhotoEvent obj = new PhotoEvent();
	obj.addCriteria(PhotoEvent.PROP_ALBUMID, albumId);
	List <PhotoEvent> list = this.helper.retrieveList(obj);
	return list;
    }
    

    /* (non-Javadoc)
     * @see com.photo.PhotoAlbumApi#findImage(int)
     */
    public PhotoImage findImage(int imageId) throws PhotoAlbumException {
	PhotoImage obj = new PhotoImage();
	obj.addCriteria(PhotoImage.PROP_IMAGEID, imageId);
	obj = (PhotoImage) this.helper.retrieveObject(obj);
	return obj;
    }

    /* (non-Javadoc)
     * @see com.photo.PhotoAlbumApi#findImageByAlbumn(int)
     */
    public List<VwPhoto> findImageByAlbumn(int albumId) throws PhotoAlbumException {
	VwPhoto obj = new VwPhoto();
	obj.addCriteria(VwPhoto.PROP_ALBUMID, albumId);
	List <VwPhoto> list = this.helper.retrieveList(obj);
	return list;
    }

    /* (non-Javadoc)
     * @see com.photo.PhotoAlbumApi#findImageByEvent(int)
     */
    public List<VwPhoto> findImageByEvent(int eventId) throws PhotoAlbumException {
	VwPhoto obj = new VwPhoto();
	obj.addCriteria(VwPhoto.PROP_EVENTID, eventId);
	List <VwPhoto> list = this.helper.retrieveList(obj);
	return list;
    }

    /* (non-Javadoc)
     * @see com.photo.PhotoAlbumApi#findImageByCriteria(java.lang.String)
     */
    public List<VwPhoto> findImageByCriteria(String criteria) throws PhotoAlbumException {
	VwPhoto obj = new VwPhoto();
	obj.addCustomCriteria(criteria);
	List <VwPhoto> list = this.helper.retrieveList(obj);
	return list;
    }

    /* (non-Javadoc)
     * @see com.photo.PhotoAlbumApi#maintain(com.bean.PhotoAlbum)
     */
    public int maintain(PhotoAlbum album) throws PhotoAlbumException {
	if (album == null) {
	    return -1;
	}
	
	int rc = 0;
	if (album.getAlbumId() == 0) {
	    rc = this.insert(album);
	}
	if (album.getAlbumId() > 0) {
	    rc = this.update(album);
	}
	return rc;
    }

    private int insert(PhotoAlbum album) throws PhotoAlbumException {
	UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
	if (ut != null) {
	    album.setDateCreated(ut.getDateCreated());
	    album.setDateUpdated(ut.getDateCreated());
	    album.setUserId(ut.getLoginId());
	}
	int rc = this.insertRow(album, true);
	return rc;
    }
    
    private int update(PhotoAlbum album) throws PhotoAlbumException {
	UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
	if (ut != null) {
	    album.setDateUpdated(ut.getDateCreated());
	    album.setUserId(ut.getLoginId());
	}
	int rc = this.updateRow(album);
	return rc;
    }
    
    
    /* (non-Javadoc)
     * @see com.photo.PhotoAlbumApi#maintain(com.bean.PhotoEvent)
     */
    public int maintain(PhotoEvent event) throws PhotoAlbumException {
	if (event == null) {
	    return -1;
	}
	int rc = 0;
	if (event.getEventId() == 0) {
	    rc = this.insert(event);
	}
	if (event.getEventId() > 0) {
	    rc = this.update(event);
	}
	return rc;
    }
    
    private int insert(PhotoEvent event) throws PhotoAlbumException {
	UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
	if (ut != null) {
	    event.setDateCreated(ut.getDateCreated());
	    event.setDateUpdated(ut.getDateCreated());
	    event.setUserId(ut.getLoginId());
	}
	int rc = 0;
	try {
	    rc = this.insertRow(event, true);
	    return rc;
	}
	catch (Exception e) {
	    throw new PhotoAlbumException("Error occurred inserting Photo Event record", e);
	}
    }
    
    private int update(PhotoEvent event) throws PhotoAlbumException {
	UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
	if (ut != null) {
	    event.setDateUpdated(ut.getDateCreated());
	    event.setUserId(ut.getLoginId());
	}
	int rc = this.updateRow(event);
	return rc;
    }
    
    

    /* (non-Javadoc)
     * @see com.photo.PhotoAlbumApi#maintain(com.bean.PhotoImage)
     */
    public int maintain(PhotoImage image) throws PhotoAlbumException {
	if (image == null) {
	    return -1;
	}
	int rc = 0;
	if (image.getImageId() == 0) {
	    rc = this.insert(image);
	}
	if (image.getImageId() > 0) {
	    rc = this.update(image);
	}
	return rc;
    }
    
    private int insert(PhotoImage image) throws PhotoAlbumException {
	UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
	if (ut != null) {
	    image.setDateCreated(ut.getDateCreated());
	    image.setDateUpdated(ut.getDateCreated());
	    image.setUserId(ut.getLoginId());
	}
	int rc = this.insertRow(image, true);
	return rc;
    }
    
    private int update(PhotoImage image) throws PhotoAlbumException {
	UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
	if (ut != null) {
	    image.setDateUpdated(ut.getDateCreated());
	    image.setUserId(ut.getLoginId());
	}
	int rc = this.updateRow(image);
	return rc;
    }
    
    

    /* (non-Javadoc)
     * @see com.photo.PhotoAlbumApi#delete(com.bean.PhotoAlbum)
     */
    public int delete(PhotoAlbum album) throws PhotoAlbumException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.photo.PhotoAlbumApi#delete(com.bean.PhotoEvent)
     */
    public int delete(PhotoEvent event) throws PhotoAlbumException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.photo.PhotoAlbumApi#delete(com.bean.PhotoImage)
     */
    public int delete(PhotoImage image) throws PhotoAlbumException {
	// TODO Auto-generated method stub
	return 0;
    }
    
}
