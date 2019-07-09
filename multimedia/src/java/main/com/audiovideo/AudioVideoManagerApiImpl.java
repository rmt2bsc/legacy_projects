package com.audiovideo;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.File;

import java.util.List;

import com.api.DaoApi;

import com.api.db.DatabaseException;
import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.AvArtist;
import com.bean.AvGenre;
import com.bean.AvProject;
import com.bean.AvTracks;
import com.bean.RMT2Base;

import com.bean.db.DatabaseConnectionBean;

import com.bean.custom.UserTimestamp;

import com.audiovideo.AudioVideoManagerApi;
import com.audiovideo.MediaBean;
import com.audiovideo.AudioVideoException;
import com.audiovideo.AudioVideoFactory;

import com.util.RMT2Date;
import com.util.SystemException;

/**
 * 
 * @author appdev
 *
 */
class AudioVideoManagerApiImpl extends RdbmsDaoImpl implements AudioVideoManagerApi, DaoApi {

    private static Logger logger = Logger.getLogger("AudioVideoManagerApiImpl");

    protected RdbmsDaoQueryHelper daoHelper;

    protected String disc;

    protected boolean processRipped;

    /**
     * Private default Contructor.
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    AudioVideoManagerApiImpl() throws DatabaseException, SystemException {
	super();
	AudioVideoManagerApiImpl.logger.log(Level.DEBUG, RMT2Base.LOGGER_INIT_MSG);
    }

    /**
     * Contructor to initialize dbConn and Audio-Video Bean.   User is responsible for providing a 
     * valid MediaBean object as an input parameter.
     * 
     * @param dbConn
     * @throws DatabaseException
     * @throws SystemException
     */
    AudioVideoManagerApiImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.disc = null;
    }

    /**
     * Reads the tag data from the media file, <i>mediaPath</i>.   Afterwards, initiates the process of 
     * updating the database with tag data.
     * 
     * @param mediaPath
     * @return
     * @throws AvFileExtractionException
     * @throws MP3ApiInstantiationException
     * @throws AvFileExtractionException
     */
    protected MediaBean extractFileData(File sourceFile) {
	if (sourceFile == null) {
	    this.msg = "The source file is invalid or null";
	    logger.log(Level.ERROR, this.msg);
	    throw new AvInvalidSourceFileException(this.msg);
	}

	MediaBean avb = null;
	AvArtist ava = null;
	AvProject av = null;
	AvTracks avt = null;

	try {
	    avb = AudioVideoFactory.create();
	    ava = avb.getAva();
	    av = avb.getAv();
	    avt = avb.getAvt();
	}
	catch (SystemException e) {
	    return null;
	}

	// Get file name with complet path
	String mediaPath = sourceFile.getPath();

	// Get the appropriate MP3Reader implementation
	MP3Reader mp3 = AudioVideoFactory.createJID3Mp3Api(sourceFile);
	
//	MP3Reader mp3 = AudioVideoFactory.createMyId3Api(sourceFile);
//	MP3Reader mp3 = AudioVideoFactory.createEntaggedId3Api(sourceFile);
	try {
	    // Get Artist
	    ava.setName(mp3.getArtist());

	    // Get Album
	    av.setTitle(mp3.getAlbum());

	    // Get Track Number
	    avt.setTrackNumber(mp3.getTrack());

	    // Get Track Title
	    avt.setTrackTitle(mp3.getTrackTitle());

	    // Get Genre
	    avb.setGenre(mp3.getGenre());

	    // Get Year Released
	    av.setYear(mp3.getYear());

	    // Get Recording Time
	    List<Integer> list = mp3.getDuration();
	    if (list != null && list.size() > 0) {
		avt.setTrackHours(list.get(0));
		avt.setTrackMinutes(list.get(1));
		avt.setTrackSeconds(list.get(2));
	    }

	    // Get Disc Number
	    int discNo = mp3.getDiscNumber();
	    avt.setTrackDisc(String.valueOf(discNo));

	    // Capture the media file location data
	    avt.setLocPath(mediaPath);

	    // Set File name
	    String fileName = sourceFile.getName();
	    avt.setLocFilename(fileName);

	    //  Initialized Ripped flag
	    av.setRipped(mediaPath.indexOf("non_ripped") > -1 ? 0 : 1);

	    // Update avb with recent changes.
	    avb.setAva(ava);
	    avb.setAv(av);
	    avb.setAvt(avt);

	    return avb;
	}
	catch (Exception e) {
	    this.msg = "File Error: " + mediaPath;
	    this.msg += ".  Cause: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new AvFileExtractionException(e);
	}

    }

    /**
     * Uses the data stored in MediaBean object and invokes a stored procedure to update 
     * the database.    _avb is assumed to be valid.   This method can be called from batch 
     * and online environments which is indicated by the input parameter, isBatch.
     * 
     * @param avb
     * @param isBatch
     * @throws SystemException
     * @throws DatabaseException
     * @throws AvProjectDataValidationException
     * @throws AvTrackDataValidationException
     * @throws AudioVideoException
     */
    public int updateProject(MediaBean avBean) throws AudioVideoException {
	int rc = 0;
	AvArtist artist = avBean.getAva();
	AvProject project = avBean.getAv();
	AvTracks track = avBean.getAvt();

	if (project.getProjectId() == 0) {
	    int artistId = this.createArtist(artist.getName());
	    // TODO:  add logic to determine if we are handling an audio of video project
	    project.setArtistId(artistId);
	    int projectId = this.createAudioProject(project, avBean.getGenre());
	    track.setProjectId(projectId);
	    int trackId = this.createTrack(track);
	    rc = projectId;
	}
	else {
	    // TODO: add logic to perform update of media
	}
	return rc;
    }

    /**
     * Adds an artist to the system.  If the artist already exist, then the artist's id is returned.
     * 
     * @param artistName
     * @return
     * @throws AudioVideoException
     */
    public int createArtist(String artistName) throws AudioVideoException {
	AvArtist obj = null;
	obj = this.findExactArtistByName(artistName);
	if (obj != null) {
	    return obj.getArtistId();
	}

	obj = new AvArtist();
	obj.setName(artistName);
	int artistId = this.insertRow(obj, true);
	obj.setArtistId(artistId);
	return artistId;
    }

    /**
     * 
     * @param proj
     * @param genre
     * @return
     * @throws AudioVideoException
     */
    public int createAudioProject(AvProject proj, String genre) throws AudioVideoException {
	int projectId = 0;

	//Default mediaType to Compact Disc
	proj.setMediaTypeId(1);
	// Set project type id to "audio"
	proj.setProjectTypeId(1);

	// Call genreic method to add project to database.
	try {
	    projectId = this.createProject(proj, genre);
	    return projectId;
	}
	catch (Exception e) {
	    throw new AudioVideoException(e);
	}
    }

    /**
     * 
     * @param proj
     * @param genre
     * @return
     * @throws AudioVideoException
     */
    public int createVideoProject(AvProject proj, String genre) throws AudioVideoException {
	int projectId = 0;

	//Default mediaType to DVD
	proj.setMediaTypeId(2);

	// Call genreic method to add project to database.
	try {
	    projectId = this.createProject(proj, genre);
	    return projectId;
	}
	catch (Exception e) {
	    throw new AudioVideoException(e);
	}
    }

    /**
     * 
     * @param proj
     * @param genre
     * @return
     * @throws SystemException
     * @throws DatabaseException
     * @throws AvProjectDataValidationException
     * @throws AudioVideoException
     *           general AudioVideoManagerApi access errors.
     */
    private int createProject(AvProject proj, String genre) throws AudioVideoException {
	// Evaluate artist id
	if (proj.getArtistId() <= 0) {
	    this.msg = "Audio/Video project is required to have an artist";
	    logger.log(Level.WARN, this.msg);
	    throw new AvProjectDataValidationException(this.msg);
	}

	// Evaluate project type
	if (proj.getProjectTypeId() < 1 || proj.getProjectTypeId() > 2) {
	    this.msg = "Audio/Video is required to have a project type";
	    logger.log(Level.WARN, this.msg);
	    throw new AvProjectDataValidationException(this.msg);
	}

	if (proj.getTitle() == null || proj.getTitle().length() <= 0) {
	    this.msg = "Audio/Video Project title is required";
	    logger.log(Level.WARN, this.msg);
	    throw new AvProjectDataValidationException(this.msg);
	}

	// If album already exists, then return the project id and do not create a duplicate.
	AvProject album = this.findAlbum(proj.getArtistId(), proj.getTitle());
	if (album != null) {
	    return album.getProjectId();
	}

	// Get Genre Id
	List<AvGenre> genres = this.findGenre(genre);
	if (genres == null || genres.size() > 1 || genres.size() == 0) {
	    this.msg = "Problem locating genre by name in the system: " + genre + ".  Genre attribute for Project[" + proj.getTitle() + "] will be set to null";
	    logger.log(Level.WARN, this.msg);
	    proj.setNull(AvProject.PROP_GENREID);
	}
	else {
	    AvGenre genreObj = genres.get(0);
	    proj.setGenreId(genreObj.getGenreId());
	}

	int ripped = proj.getRipped();
	if (ripped > 1) {
	    ripped = 1;
	}
	else if (ripped < 0) {
	    ripped = 0;
	}
	proj.setRipped(ripped);

	int projectId = 0;
	UserTimestamp ut = null;
	ut = RMT2Date.getUserTimeStamp(proj.getUserId());
	if (proj.getDateCreated() == null) {
	    proj.setDateCreated(ut.getDateCreated());
	}
	proj.setDateUpdated(ut.getDateCreated());

	// Insert record into database
	projectId = this.insertRow(proj, true);
	return projectId;
    }

    /**
     * 
     * @param track
     * @return
     * @throws SystemException
     * @throws DatabaseException
     * @throws AvTrackDataValidationException
     * @throws AudioVideoException
     *           general AudioVideoManagerApi access errors.
     */
    public int createTrack(AvTracks track) throws AudioVideoException {
	Object proj = this.findAlbum(track.getProjectId());
	if (proj == null) {
	    this.msg = "Track could not be added due to project (album) is invalid or does not exist.  Project Id: " + track.getProjectId();
	    logger.log(Level.ERROR, this.msg);
	    throw new AvTrackDataValidationException(this.msg);
	}
	if (track.getTrackTitle() == null) {
	    this.msg = "Track title is required";
	    logger.log(Level.ERROR, this.msg);
	    throw new AvTrackDataValidationException(this.msg);
	}
	if (track.getTrackNumber() <= 0) {
	    this.msg = "Track number is found to be invalid";
	    logger.log(Level.ERROR, this.msg);
	    throw new AvTrackDataValidationException(this.msg);
	}

	int trackId = 0;
	UserTimestamp ut = null;
	ut = RMT2Date.getUserTimeStamp(track.getUserId());
	if (track.getDateCreated() == null) {
	    track.setDateCreated(ut.getDateCreated());
	}
	track.setDateUpdated(ut.getDateCreated());

	// Insert record into database
	trackId = this.insertRow(track, true);
	return trackId;

    }

    /**
     * Locates a AudioVideoArtist object by primary key or it Artist Id and returns AudioVideoArtist object to the caller.
     * 
     * @param artistId
     * @throws AudioVideoException
     */
    public AvArtist findArtistById(int artistId) throws AudioVideoException {
	AvArtist obj = new AvArtist();
	obj.addCriteria(AvArtist.PROP_ARTISTID, artistId);
	try {
	    return (AvArtist) this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new AudioVideoException(e);
	}
    }

    /**
     * Locates an exact match for an artist by artist name
     * 
     * @param name
     * @throws AudioVideoException
     */
    public AvArtist findExactArtistByName(String artistName) throws AudioVideoException {
	AvArtist obj = new AvArtist();
	obj.addCriteria(AvArtist.PROP_NAME, artistName);
	try {
	    return (AvArtist) this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new AudioVideoException(e);
	}
    }

    /**
     * Locates on or more artists based on artist name
     * 
     * @param name
     * @throws AudioVideoException
     */
    public List<AvArtist> findArtistByName(String artistName) throws AudioVideoException {
	AvArtist obj = new AvArtist();
	obj.addLikeClause(AvArtist.PROP_NAME, artistName);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new AudioVideoException(e);
	}
    }

    /**
     * Retrieves a particular artist's album.   album can be of type audio or video.
     * 
     * @param projectId
     * @throws AudioVideoException
     */
    public AvProject findAlbum(int projectId) throws AudioVideoException {
	AvProject obj = new AvProject();
	obj.addCriteria(AvProject.PROP_PROJECTID, projectId);
	try {
	    return (AvProject) this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new AudioVideoException(e);
	}
    }

    /**
     * Retrieves one or more albums by artist id.   album can be of type audio or video.
     * 
     * @param artistId
     * @throws AudioVideoException
     */
    public List<AvProject> findAlbumByArtistId(int artistId) throws AudioVideoException {
	AvProject obj = new AvProject();
	obj.addCriteria(AvProject.PROP_ARTISTID, artistId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new AudioVideoException(e);
	}
    }

    /**
     * Retrieves an album by artist id and album title.
     * 
     * @param artistId
     * @param albumTitle
     * @return
     * @throws AudioVideoException
     */
    public AvProject findAlbum(int artistId, String albumTitle) throws AudioVideoException {
	AvProject obj = new AvProject();
	obj.addCriteria(AvProject.PROP_ARTISTID, artistId);
	obj.addCriteria(AvProject.PROP_TITLE, albumTitle);
	try {
	    return (AvProject) this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new AudioVideoException(e);
	}
    }

    /**
     * Retrieves one or more albums of an artist by title.   Album can be of type audio or video.
     * 
     * @param title
     * @throws AudioVideoException
     */
    public List<AvProject> findAlbumByTitle(String title) throws AudioVideoException {
	AvProject obj = new AvProject();
	obj.addCriteria(AvProject.PROP_TITLE, title);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new AudioVideoException(e);
	}
    }

    /**
     * Retrieves one or more albums of an artist using genre.   Album can be of type audio or video.
     * 
     * @param genreId
     * @throws AudioVideoException
     */
    public List<AvProject> findAlbumByGenre(int genreId) throws AudioVideoException {
	AvProject obj = new AvProject();
	obj.addCriteria(AvProject.PROP_GENREID, genreId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new AudioVideoException(e);
	}
    }

    /**
     * Retrieves one or more albums of an artist using year recorder.   Album can be of type audio or video.
     * 
     * @param year
     * @throws AudioVideoException
     */
    public List<AvProject> findAlbumByYear(int year) throws AudioVideoException {
	AvProject obj = new AvProject();
	obj.addCriteria(AvProject.PROP_YEAR, year);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new AudioVideoException(e);
	}
    }

    /**
     * Retrieves one track record for a an album.   Track can derive from an album of type audio or video.
     * 
     * @param trackId
     * @return
     * @throws AudioVideoException
     */
    public AvTracks findTrack(int trackId) throws AudioVideoException {
	AvTracks obj = new AvTracks();
	obj.addCriteria(AvTracks.PROP_TRACKID, trackId);
	try {
	    return (AvTracks) this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new AudioVideoException(e);
	}
    }

    /**
     * Retrieves one or more album's track objects based on the album id.   Album can be of type audio or video.
     * 
     * @param projectId
     * @throws AudioVideoException
     */
    public List<AvTracks> findTracksByAlbumId(int projectId) throws AudioVideoException {
	AvTracks obj = new AvTracks();
	obj.addCriteria(AvTracks.PROP_PROJECTID, projectId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new AudioVideoException(e);
	}
    }

    /**
     * Retrieves one or more tracks based on track title.  The results can come from multiple albums  
     * type audio or video.
     * 
     * @param projectId
     * @throws AudioVideoException
     */
    public List<AvTracks> findTracksByAlbumId(String projectId) throws AudioVideoException {
	String criteria = " project_id = " + projectId;
	AvTracks obj = new AvTracks();
	obj.addCustomCriteria(criteria);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new AudioVideoException(e);
	}
    }

    /**
     * Retrieves an ArrayList of related to the Audio Video module based on custom criteria.    
     * The appropriate  base class and base view properties must be set before this method 
     * is successfully executed.
     * 
     * @param criteria
     * @throws AudioVideoException
     */
    public List<AvProject> findAlbum(String _criteria) throws AudioVideoException {
	AvProject obj = new AvProject();
	obj.addCustomCriteria(_criteria);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new AudioVideoException(e);
	}
    }

    public void setRipped(boolean value) {
	this.processRipped = value;
    }

    /* (non-Javadoc)
     * @see com.audiovideo.AudioVideoManagerApi#findGenre(int)
     */
    public AvGenre findGenre(int genreId) throws AudioVideoException {
	AvGenre obj = new AvGenre();
	obj.addCriteria(AvGenre.PROP_GENREID, genreId);
	return (AvGenre) this.daoHelper.retrieveObject(obj);
    }

    /* (non-Javadoc)
     * @see com.audiovideo.AudioVideoManagerApi#findGenre(java.lang.String)
     */
    public List<AvGenre> findGenre(String name) throws AudioVideoException {
	AvGenre obj = new AvGenre();
	obj.addCriteria(AvGenre.PROP_DESCRIPTION, name);
	return this.daoHelper.retrieveList(obj);
    }

}
