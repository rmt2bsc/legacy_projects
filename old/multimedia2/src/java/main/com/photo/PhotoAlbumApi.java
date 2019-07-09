package com.photo;

import java.util.List;

import com.bean.PhotoAlbum;
import com.bean.PhotoEvent;
import com.bean.PhotoImage;
import com.bean.VwPhoto;

/**
 * Interface providing functionality for fetching, creating, updating, and deleting 
 * various photo albumn entities.
 * 
 * @author rterrell
 *
 */
public interface PhotoAlbumApi {
    
    /**
     * Fetch a single ablum instance by album id.
     * 
     * @param albumId
     * @return {@link com.bean.PhotoAlbum PhotoAlbum}
     * @throws PhotoAlbumException
     */
    PhotoAlbum findAlbum(int albumId) throws PhotoAlbumException;

    /**
     * Fetch one or more album instances by album name.
     * 
     * @param name
     * @return List {@link com.bean.PhotoAlbum PhotoAlbum}
     * @throws PhotoAlbumException
     */
    List<PhotoAlbum> findAlbum(String name) throws PhotoAlbumException;

    /**
     * Fetch a single album event instance by event id.
     * 
     * @param eventId
     * @return {@link com.bean.PhotoEvent PhotoEvent}
     * @throws PhotoAlbumException
     */
    PhotoEvent findEvent(int eventId) throws PhotoAlbumException;

    /**
     * Fetch one or more album event instance by event name.
     * 
     * @param name
     * @return List {@link com.bean.PhotoEvent PhotoEvent}
     * @throws PhotoAlbumException
     */
    List<PhotoEvent> findEvent(String name) throws PhotoAlbumException;
    
    /**
     * Fetch one or more album events by album id.
     * 
     * @param albumId
     * @return List {@link com.bean.PhotoEvent PhotoEvent}
     * @throws PhotoAlbumException
     */
    List<PhotoEvent> findAlbumEvents(int albumId) throws PhotoAlbumException;

    /**
     * Fetch a single album image by image id.
     * 
     * @param imageId
     * @return {@link com.bean.PhotoImage PhotoImage}
     * @throws PhotoAlbumException
     */
    PhotoImage findImage(int imageId) throws PhotoAlbumException;

    /**
     * Fetch one or more album images using album id.
     * 
     * @param albumId
     * @return {@link com.bean.VwPhoto VwPhoto}
     * @throws PhotoAlbumException
     */
    List<VwPhoto> findImageByAlbumn(int albumId) throws PhotoAlbumException;

    /**
     * Fetch one or more album images using event id.
     * 
     * @param eventId
     * @return {@link com.bean.VwPhoto VwPhoto}
     * @throws PhotoAlbumException
     */
    List<VwPhoto> findImageByEvent(int eventId) throws PhotoAlbumException;

    /**
     * Fetch one or more album images using custom selection criteria.
     * 
     * @param criteria
     * @return {@link com.bean.VwPhoto VwPhoto}
     * @throws PhotoAlbumException
     */
    List<VwPhoto> findImageByCriteria(String criteria) throws PhotoAlbumException;

    /**
     * Persist a new or existing photo album instance to some form of a data source.
     * 
     * @param album
     * @return int value representing new primary key when <i>album</i> is new or the 
     *             total number of rows effected when the <i>albumn</i> is existing.
     * @throws PhotoAlbumException
     */
    int maintain(PhotoAlbum album) throws PhotoAlbumException;

    /**
     * Persist a new or existing photo event instance to some form of a data source.
     * 
     * @param event
     * @return int value representing new primary key when <i>album</i> is new or the 
     *             total number of rows effected when the <i>albumn</i> is existing.
     * @throws PhotoAlbumException
     */
    int maintain(PhotoEvent event) throws PhotoAlbumException;

    /**
     * Persist a new or existing photo image instance to some form of a data source.
     * 
     * @param image
     * @return int value representing new primary key when <i>album</i> is new or the 
     *             total number of rows effected when the <i>albumn</i> is existing.
     * @throws PhotoAlbumException
     */
    int maintain(PhotoImage image) throws PhotoAlbumException;

    /**
     * 
     * @param album
     * @return
     * @throws PhotoAlbumException
     */
    int delete(PhotoAlbum album) throws PhotoAlbumException;

    /**
     * 
     * @param event
     * @return
     * @throws PhotoAlbumException
     */
    int delete(PhotoEvent event) throws PhotoAlbumException;

    /**
     * 
     * @param image
     * @return
     * @throws PhotoAlbumException
     */
    int delete(PhotoImage image) throws PhotoAlbumException;

}
