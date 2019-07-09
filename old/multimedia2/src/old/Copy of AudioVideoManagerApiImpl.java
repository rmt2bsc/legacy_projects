package com.audiovideo;


import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.File;
import java.io.IOException;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.farng.mp3.MP3File;
import org.farng.mp3.id3.AbstractID3v2;
import org.farng.mp3.id3.ID3v2_2;
import org.farng.mp3.id3.ID3v2_2Frame;
import org.farng.mp3.id3.FrameBodyTPE1; // Artist
import org.farng.mp3.id3.FrameBodyTALB; // Album
import org.farng.mp3.id3.FrameBodyTIT2; // Track Title
import org.farng.mp3.id3.FrameBodyTRCK; // Track Number
import org.farng.mp3.id3.FrameBodyCOMM; // Notes
import org.farng.mp3.id3.FrameBodyTCON; // Genre
import org.farng.mp3.id3.FrameBodyTYER; // Year Released
import org.farng.mp3.id3.FrameBodyTLEN; // Recording Time
import org.farng.mp3.TagException;

import com.api.DaoApi;
//import com.api.RMT2DynamicSqlApi;

import com.bean.RMT2Base;
import com.bean.db.DatabaseConnectionBean;

//import com.apiimpl.RMT2BaseApiImpl;

import com.audiovideo.AudioVideoManagerApi;
import com.audiovideo.AudioVideoBatchUpdateApi;
import com.audiovideo.AudioVideoBean;
import com.audiovideo.AudioVideoArtist;
import com.audiovideo.AudioVideo;
import com.audiovideo.AudioVideoTracks;
import com.audiovideo.AudioVideoException;
import com.audiovideo.AudioVideoFactory;

import com.util.RMT2File;
import com.util.RMT2Utility;
import com.util.SystemException;
import com.api.db.DatabaseException;
import com.api.db.orm.RdbmsDaoImpl;




class AudioVideoManagerApiImpl extends RdbmsDaoImpl implements AudioVideoManagerApi, AudioVideoBatchUpdateApi, DaoApi {

    private static Logger logger = Logger.getLogger("AudioVideoManagerApiImpl");
    
    protected File fServer;

    protected File fShare;

    protected File fRootPath;

    protected File fPath;

    protected File fFile;

    protected String serverName;

    protected String shareName;

    protected String rootPathName;

    protected String disc;

    protected int successCnt;

    protected int errorCnt;

    protected int totCnt;

    protected boolean processRipped;

    protected boolean isPurged = false;

    private String criteria;

    private int curLevel; // Level of directory structure.  Root=1, Artist=2, Album=3, Disc=4

    private final int FILE_IO_EXIST = 1;

    private final int FILE_IO_NOTEXIST = -1;

    private final int FILE_IO_NULL = 0;

    private final int FILE_IO_INACCESSIBLE = -2;

    private final int DIR_LEVEL_ROOT = 1;

    private final int DIR_LEVEL_ARTIST = 2;

    private final int DIR_LEVEL_ALBUM = 3;

    private final int DIR_LEVEL_DISC = 4;

  
    /**
     * Private default Contructor.
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    private AudioVideoManagerApiImpl() throws DatabaseException, SystemException {
	super();
	AudioVideoManagerApiImpl.logger.log(Level.DEBUG, RMT2Base.LOGGER_INIT_MSG);
    }

    /**
     * Contructor to initialize dbConn and Audio-Video Bean.   User is responsible for providing a 
     * valid AudioVideoBean object as an input parameter.
     * 
     * @param dbConn
     * @throws DatabaseException
     * @throws SystemException
     */
    public AudioVideoManagerApiImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
	this.serverName = null;
	this.shareName = null;
	this.rootPathName = null;
	this.disc = null;
	this.curLevel = 0;
	this.successCnt = 0;
	this.errorCnt = 0;
	this.totCnt = 0;
    }

    /**
     * Contructor to initialize dbConn and Audio-Video Bean.   User is responsible for providing 
     * server name, share name, and root path as input parameters which are used to create an 
     * AudioVideoBean object.   _serverName, _shareName, and _rootName can contain null values 
     * as an input parameter.
     * 
     * @param dbConn
     * @param serverName
     * @param shareName
     * @param rootPathName
     * @throws DatabaseException
     * @throws SystemException
     */
    public AudioVideoManagerApiImpl(DatabaseConnectionBean dbConn, String serverName, String shareName, String rootPathName) throws DatabaseException, SystemException {
	this(dbConn);
	String method = "AudioVideoManagerApiImpl(String _serverName, String _shareName, String _rootPath)";
	this.serverName = serverName;
	this.shareName = shareName;
	this.rootPathName = rootPathName;
    }

    /**
     * Purges the av_project and av_tracks tables
     */
    public int purge() {
	// Call stored procedure to purge audio_video and audio_video_tracks tables
	try {
	    this.dynaApi.clearParms();
	    this.dynaApi.execute("exec usp_purge_audio_video_media");
	    isPurged = true;
	    return 1;
	}
	catch (DatabaseException e) {
	    return -1;
	}
	catch (SystemException e) {
	    return -2;
	}

    }

    /**
     * Creates an audoi/video library (audio_video and audio_video_tracks tables) from the files stored 
     * locally or remotely.   Returns the total number of tracks successfull processed.  Data from the 
     * tables audio_video and audio_video_tracks are deleted before processing artist's directories.
     * 
     * @throws AudioVideoException
     * @thows AvBatchValidationException
     */
    public int updateAvLibBatch() throws AudioVideoException {
	
	AudioVideoManagerApiImpl.logger.log(Level.INFO, "Begin Audio-Video Batch Update");
	this.validateMediaPath();

	// Begin process artist
	this.curLevel = 1;
	this.processMediaBatch(this.fRootPath);
	
	AudioVideoManagerApiImpl.logger.log(Level.INFO, "Total Media Files Processed: " + this.totCnt);
	AudioVideoManagerApiImpl.logger.log(Level.INFO, "Total Media Files Successfully Processed: " + this.successCnt);
	AudioVideoManagerApiImpl.logger.log(Level.INFO, "Total Media Files Unsuccessfully Processed: " + this.errorCnt);
	AudioVideoManagerApiImpl.logger.log(Level.INFO, "End Audio-Video Update");
	return this.totCnt;
    }

    
    /**
     * Verifies that Audio-Video Bean contains valid values which are used to validate the existence 
     * of the root path when combined together.
     * 
     * @throws AvBatchValidationException
     */
    public void validateMediaPath() {
	String rootPath;

	// Verify that bean has valid values
	if (this.serverName == null || this.serverName.length() <= 0) {
	    this.msg = "Audio/Video Library update failed!   Server Name must have a value";
	    logger.log(Level.ERROR, this.msg);
	    throw new AvBatchValidationException(this.msg);
	}
	if (this.shareName == null || this.shareName.length() <= 0) {
	    this.msg = "Audio/Video Library update failed!   Share Name must have a value";
	    logger.log(Level.ERROR, this.msg);
	    throw new AvBatchValidationException(this.msg);
	}
	if (this.rootPathName == null) {
	    this.msg = "Audio/Video Library update failed!   Root Path must have a value";
	    logger.log(Level.ERROR, this.msg);
	    throw new AvBatchValidationException(this.msg);
	}

	// Combine Server Name, Share Name, and Root Path Name using Universal Naming Convention standards.
	rootPath = "\\\\" + this.serverName + "\\" + this.shareName + "\\" + this.rootPathName;
	//			rootPath="C:\\data\\media\\music\\old\\Maze\\Maze";
	switch (RMT2File.verifyFile(rootPath)) {
	case 1:
	    // Get File Reference
	    this.fRootPath = new File(rootPath);
	    break;

	case -1:
	    this.msg = "Audio/Video Library update failed! Root Path, " + rootPath + ", cannot be found";
	    logger.log(Level.ERROR, this.msg);
	    throw new AvBatchValidationException(this.msg);

	case -2:
	    this.msg = "Audio/Video Library update failed!   A Security Manager exists and has denied read access to the file or directory";
	    logger.log(Level.ERROR, this.msg);
	    throw new AvBatchValidationException(this.msg);

	case 0:
	    this.msg = "Audio/Video Library update failed!   Root Path to Audio/Video Library is null";
	    logger.log(Level.ERROR, this.msg);
	    throw new AvBatchValidationException(this.msg);
	}
    }


    /**
     * Process all the high level audio/video artist directories
     * 
     * @param _media
     * @return
     * @throws AudioVideoException
     */
    protected int processMediaBatch(File _media) throws AudioVideoException {
	File mediaList[];
	int itemCount = 0;

	try {
	    mediaList = _media.listFiles();
	    itemCount = mediaList.length;
	    for (int ndx = 0; ndx < itemCount; ndx++) {
		if (mediaList[ndx].isDirectory()) {
		    // Make recursive call to process next level
		    this.curLevel++;
		    this.processMediaBatch(mediaList[ndx]);
		}
		if (mediaList[ndx].isFile()) {
		    this.processMediaFileBatch(mediaList[ndx]);
		}
	    }
	    this.curLevel--;
	    return 1;
	}
	catch (SecurityException e) {
	    throw new AudioVideoException(e);
	}
    }

    /**
     * Determines the absolute path of _media and initiates the media file data extraction process.
     * 
     * @param _media
     * @return
     * @throws AudioVideoException
     */
    protected int processMediaFileBatch(File _media) throws AudioVideoException {
	String method = "processMediaFileBatch()";
	String pathName;
	AudioVideoBean avb;

	pathName = _media.getPath();
	avb = this.extractMediaDataBatch(pathName);
	if (avb != null) {
	    this.updateMediaDb(avb, true);
	}
	this.totCnt++;
	return 1;
    }

    /**
     * Reads the tag data from the media file, "_mediaPath".   Afterwards, initiates the process of 
     * updating the database with tag data.
     * 
     * @param mediaPath
     * @return
     * @throws AudioVideoException
     */
    protected AudioVideoBean extractMediaDataBatch(String _mediaPath) throws AudioVideoException {

	String method = "extractMediaDataBatch()";
	String pathName;
	String value;
	String msg;
	long ms;
	int hr;
	int min;
	int sec;
	int year;
	int track = 0;
	File sourceFile = null;
	GregorianCalendar tm;
	AudioVideoBean avb = null;
	AudioVideoArtist ava = null;
	AudioVideo av = null;
	AudioVideoTracks avt = null;

	try {
	    avb = AudioVideoFactory.create();
	    ava = avb.getAva();
	    av = avb.getAv();
	    avt = avb.getAvt();
	}
	catch (SystemException e) {
	    return null;
	}

	try {
	    sourceFile = new File(_mediaPath);
	    MP3File mp3file = new MP3File(sourceFile);
	    ID3v2_2 tag = (ID3v2_2) mp3file.getID3v2Tag();
	    ID3v2_2Frame frame = null;

	    // Get Artist
	    frame = (ID3v2_2Frame) tag.getFrame("TPE1");
	    if (frame != null) {
		value = ((FrameBodyTPE1) frame.getBody()).getText();
		ava.setName(value);
	    }

	    // Get Album
	    frame = (ID3v2_2Frame) tag.getFrame("TALB");
	    if (frame != null) {
		value = ((FrameBodyTALB) frame.getBody()).getText();
		av.setTitle(value);
	    }

	    // Get Track Number
	    frame = (ID3v2_2Frame) tag.getFrame("TRCK");
	    if (frame != null) {
		value = ((FrameBodyTRCK) frame.getBody()).getText();
		try {
		    track = new Integer(value).intValue();
		}
		catch (NumberFormatException e) {
		    track = 0;
		}
		avt.setTrackNumber(track);
	    }

	    // Get Track Title
	    frame = (ID3v2_2Frame) tag.getFrame("TIT2");
	    if (frame != null) {
		value = ((FrameBodyTIT2) frame.getBody()).getText();
		avt.setTrackTitle((value == null ? "Unable to Access Title Track Name" : value));
	    }
	    else {
		// Compute track title
		value = ava.getName() + "-" + av.getTitle() + "-Track " + track;
		avt.setTrackTitle(value);
	    }

	    // Get Genre
	    frame = (ID3v2_2Frame) tag.getFrame("TCON");
	    if (frame != null) {
		value = ((FrameBodyTCON) frame.getBody()).getText();
		avb.setGenre((value == null ? "Unknown" : value));
	    }
	    else {
		avb.setGenre("Unknown");
	    }

	    // Get Year Released
	    frame = (ID3v2_2Frame) tag.getFrame("TYER");
	    if (frame != null) {
		value = ((FrameBodyTYER) frame.getBody()).getText();
		try {
		    year = new Integer(value).intValue();
		}
		catch (NumberFormatException e) {
		    year = 0;
		}
		av.setYearRecorded(year);
	    }

	    // Get Recording Time
	    frame = (ID3v2_2Frame) tag.getFrame("TLEN");
	    if (frame != null) {
		value = ((FrameBodyTLEN) frame.getBody()).getText();
		try {
		    ms = new Long(value).longValue();
		}
		catch (NumberFormatException e) {
		    ms = 0;
		}
		tm = new GregorianCalendar();
		tm.setTime(new java.util.Date(ms));
		hr = tm.get(Calendar.HOUR);
		min = tm.get(Calendar.MINUTE);
		sec = tm.get(Calendar.SECOND);
		avt.setTrackMinutes(min);
		avt.setTrackSeconds(sec);
	    }

	    // Capture the media file location data
	    avt.setLocServername(this.serverName);
	    avt.setLocSharename(this.shareName);
	    avt.setLocRootPath(rootPathName);
	    avt.setLocPath(_mediaPath);

	    if (this.curLevel == this.DIR_LEVEL_DISC) {
		File parentFile = sourceFile.getParentFile();
		value = parentFile.getName();
		avt.setTrackDisc(value);
	    }
	    else {
		avt.setTrackDisc(null);
	    }

	    value = sourceFile.getName();
	    avt.setFileName(value);

	    //  Initialized Ripped flag
	    av.setRipped(this.processRipped ? 1 : 0);

	    // Update avb with recent changes.
	    avb.setAva(ava);
	    avb.setAv(av);
	    avb.setAvt(avt);

	    return avb;
	}
	catch (TagException e) {
	    msg = "TagException - ";
	    msg += "File Location: " + _mediaPath;
	    this.createBatchMsg("audiovideo", msg);
	    this.errorCnt++;
	    return null;
	}
	catch (IOException e) {
	    msg = "IOException - ";
	    msg += "File Location: " + _mediaPath;
	    this.createBatchMsg("audiovideo", msg);
	    this.errorCnt++;
	    return null;
	}
	catch (Exception e) {
	    System.out.println("General Exception: " + e.getMessage());
	    msg = "A General Exception - ";
	    msg += "File Location: " + _mediaPath;
	    this.createBatchMsg("audiovideo", msg);
	    this.errorCnt++;
	    return null;
	}

    }

    /**
     * Uses the data stored in AudioVideoBean object and invokes a stored procedure to update 
     * the database.    _avb is assumed to be valid.   This method can be called from batch 
     * and online environments which is indicated by the input parameter, isBatch.
     * 
     * @param avb
     * @param isBatch
     * @throws AudioVideoException
     */
    public int updateMediaDb(AudioVideoBean _avb, boolean _isBatch) throws AudioVideoException {

	String method = "updateMediaDb";
	String msg = null;
	Object newObj = null;
	int rc = 0;
	AudioVideoArtist ava = _avb.getAva();
	AudioVideo av = _avb.getAv();
	AudioVideoTracks avt = _avb.getAvt();

	try {
	    this.dynaApi.clearParms();

	    // Call stored procedure to add or update audio video
	    if (_isBatch) {
		this.dynaApi.addParm("artist_name", Types.VARCHAR, ava.getName(), false);
		this.dynaApi.addParm("album", Types.VARCHAR, av.getTitle(), false);
		this.dynaApi.addParm("genre", Types.VARCHAR, _avb.getGenre(), false);
		this.dynaApi.addParm("media_type", Types.INTEGER, 1, false);
		this.dynaApi.addParm("year", Types.INTEGER, av.getYearRecorded(), false);
		this.dynaApi.addParm("ripped", Types.INTEGER, av.getRipped(), false);
		this.dynaApi.addParm("track_title", Types.VARCHAR, avt.getTrackTitle(), false);
		this.dynaApi.addParm("track_no", Types.INTEGER, avt.getTrackNumber(), false);
		this.dynaApi.addParm("track_min", Types.INTEGER, avt.getTrackMinutes(), false);
		this.dynaApi.addParm("track_sec", Types.INTEGER, avt.getTrackSeconds(), false);
		this.dynaApi.addParm("track_server", Types.VARCHAR, avt.getLocServername(), false);
		this.dynaApi.addParm("track_share", Types.VARCHAR, avt.getLocSharename(), false);
		this.dynaApi.addParm("track_rootPath", Types.VARCHAR, avt.getLocRootPath(), false);
		this.dynaApi.addParm("track_path", Types.VARCHAR, avt.getLocPath(), false);
		this.dynaApi.addParm("track_disc", Types.VARCHAR, avt.getTrackDisc(), false);
		this.dynaApi.addParm("track_filename", Types.VARCHAR, avt.getFileName(), false);
		this.dynaApi.addParm("return_code", Types.INTEGER, rc, true);
		this.dynaApi.execute("exec usp_add_audio_data_batch ?????????????????");
	    }
	    else {
		// Get Artist Data
		this.dynaApi.addParm("artist_id", Types.INTEGER, ava.getId(), false);
		this.dynaApi.addParm("artist_name", Types.VARCHAR, ava.getName(), false);

		// Get Album Data
		this.dynaApi.addParm("album_id", Types.INTEGER, av.getId(), false);
		this.dynaApi.addParm("album", Types.VARCHAR, av.getTitle(), false);
		this.dynaApi.addParm("genre", Types.INTEGER, av.getGenreId(), false);
		this.dynaApi.addParm("media_type", Types.INTEGER, av.getMediaTypeId(), false);
		this.dynaApi.addParm("year", Types.INTEGER, av.getYearRecorded(), false);
		this.dynaApi.addParm("ripped", Types.INTEGER, av.getRipped(), false);

		// Get Track Data
		this.dynaApi.addParm("track_id", Types.INTEGER, avt.getId(), false);
		this.dynaApi.addParm("track_title", Types.VARCHAR, avt.getTrackTitle(), false);
		this.dynaApi.addParm("track_no", Types.INTEGER, avt.getTrackNumber(), false);
		this.dynaApi.addParm("track_min", Types.INTEGER, avt.getTrackMinutes(), false);
		this.dynaApi.addParm("track_sec", Types.INTEGER, avt.getTrackSeconds(), false);
		this.dynaApi.addParm("track_server", Types.VARCHAR, avt.getLocServername(), false);
		this.dynaApi.addParm("track_share", Types.VARCHAR, avt.getLocSharename(), false);
		this.dynaApi.addParm("track_rootPath", Types.VARCHAR, avt.getLocRootPath(), false);
		this.dynaApi.addParm("track_path", Types.VARCHAR, avt.getLocPath(), false);
		this.dynaApi.addParm("track_disc", Types.VARCHAR, avt.getTrackDisc(), false);
		this.dynaApi.addParm("track_filename", Types.VARCHAR, avt.getFileName(), false);
		this.dynaApi.addParm("return_code", Types.INTEGER, rc, true);

		// Update Database
		this.dynaApi.execute("exec usp_upd_audio_data ????????????????????");
	    }

	    //  Get return code
	    newObj = this.dynaApi.getOutParm("return_code");
	    if (newObj instanceof Integer) {
		rc = ((Integer) newObj).intValue();
	    }
	    this.successCnt++;
	    return rc;
	}
	catch (DatabaseException e) {
	    if (_isBatch) {
		this.errorCnt++;
		msg = e.getMessage() + "  File Location: " + avt.getLocPath();
		this.createBatchMsg("audiovideo", msg);
		return -1;
	    }
	    throw new AudioVideoException(e);
	}
	catch (SystemException e) {
	    if (_isBatch) {
		this.errorCnt++;
		msg = e.getMessage() + "  File Location: " + avt.getLocPath();
		this.createBatchMsg("audiovideo", msg);
		return -2;
	    }
	    throw new AudioVideoException(e);
	}
    }


    /**
     * Locates a AudioVideoArtist object by primary key or it Artist Id and returns AudioVideoArtist object to the caller.
     * 
     * @param artistId
     * @throws AudioVideoException
     */
    public AudioVideoArtist findArtistById(int value) throws AudioVideoException {
	String method = "findArtistById";

	this.setBaseView("AudioVideoArtistView");
	this.setBaseClass("audiovideo.AudioVideoArtist");
	this.criteria = "id = " + value;

	try {
	    ArrayList list = this.find(this.criteria);
	    if (list.size() == 0) {
		return null;
	    }
	    return (AudioVideoArtist) list.get(0);
	}
	catch (IndexOutOfBoundsException e) {
	    throw new AudioVideoException(this.dbo, 0000, null);
	}
	catch (SystemException e) {
	    throw new AudioVideoException(this.dbo, 0000, null);
	}
    }

    
    /**
     * Locates one or more Artits  objects based on the name
     * 
     * @param name
     * @throws AudioVideoException
     */
    public ArrayList findArtistByName(String value) throws AudioVideoException {

	String method = "findArtistByName";
	this.setBaseView("AudioVideoArtistView");
	this.setBaseClass("audiovideo.AudioVideoArtist");
	try {
	    this.criteria = "name like \'" + value + "%\'";
	    ArrayList list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AudioVideoException(this.dbo, 0000, null);
	}
    }

    /**
     * Retrieves a particular artist's album.   album can be of type audio or video.
     * 
     * @param projectId
     * @throws AudioVideoException
     */
    public AudioVideo findAlbumById(int _id) throws AudioVideoException {
	String method = "findAlbumById";

	this.setBaseView("AudioVideoView");
	this.setBaseClass("audiovideo.AudioVideo");
	this.criteria = "id = " + _id;

	try {
	    ArrayList list = this.find(this.criteria);
	    if (list.size() == 0) {
		return null;
	    }
	    return (AudioVideo) list.get(0);
	}
	catch (IndexOutOfBoundsException e) {
	    throw new AudioVideoException(this.dbo, 0000, null);
	}
	catch (SystemException e) {
	    throw new AudioVideoException(this.dbo, 0000, null);
	}
    }


    /**
     * Retrieves one or more albums by artist id.   album can be of type audio or video.
     * 
     * @param artistId
     * @throws AudioVideoException
     */
    public ArrayList findAlbumByArtistId(int _id) throws AudioVideoException {
	String method = "findAlbumByArtistId";

	this.setBaseView("AudioVideoView");
	this.setBaseClass("audiovideo.AudioVideo");
	this.criteria = "id = " + _id;

	try {
	    ArrayList list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AudioVideoException(this.dbo, 0000, null);
	}
    }

    /**
     * Retrieves one or more albums of an artist by title.   Album can be of type audio or video.
     * 
     * @param title
     * @throws AudioVideoException
     */
    public ArrayList findAlbumByTitle(String _value) throws AudioVideoException {
	String method = "findAlbumByTitle";

	this.setBaseView("AudioVideoView");
	this.setBaseClass("audiovideo.AudioVideo");
	this.criteria = "title like '" + _value + "%'";

	try {
	    ArrayList list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AudioVideoException(this.dbo, 0000, null);
	}
    }

    /**
     * Retrieves one or more albums of an artist using genre.   Album can be of type audio or video.
     * 
     * @param genreId
     * @throws AudioVideoException
     */
    public ArrayList findAlbumByGenre(int _value) throws AudioVideoException {
	String method = "findAlbumByGenre";

	this.setBaseView("AudioVideoView");
	this.setBaseClass("audiovideo.AudioVideo");
	this.criteria = "genre = " + _value;

	try {
	    ArrayList list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AudioVideoException(this.dbo, 0000, null);
	}
    }

    /**
     * Retrieves one or more albums of an artist using year recorder.   Album can be of type audio or video.
     * 
     * @param year
     * @throws AudioVideoException
     */
    public ArrayList findAlbumByYear(int _value) throws AudioVideoException {
	String method = "findAlbumByGenre";

	this.setBaseView("AudioVideoView");
	this.setBaseClass("audiovideo.AudioVideo");
	this.criteria = "year_recorded = " + _value;

	try {
	    ArrayList list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AudioVideoException(this.dbo, 0000, null);
	}
    }

    /**
     * Retrieves one track record for a an album.   Track can derive from an album of type audio or video.
     * 
     * @param trackId
     * @return
     * @throws AudioVideoException
     */
    public AudioVideoTracks findTrack(int _value) throws AudioVideoException {
	String method = "findTracksById";

	this.setBaseView("AudioVideoTracksView");
	this.setBaseClass("audiovideo.AudioVideoTracks");
	this.criteria = "id = " + _value;

	try {
	    ArrayList list = this.find(this.criteria);
	    if (list.size() <= 0) {
		return null;
	    }
	    return (AudioVideoTracks) list.get(0);
	}
	catch (IndexOutOfBoundsException e) {
	    throw new AudioVideoException(this.dbo, 0000, null);
	}
	catch (SystemException e) {
	    throw new AudioVideoException(this.dbo, 0000, null);
	}
    }

    /**
     * Retrieves one or more album's track objects based on the album id.   Album can be of type audio or video.
     * 
     * @param projectId
     * @throws AudioVideoException
     */
    public ArrayList findTracksByAlbumId(int _value) throws AudioVideoException {
	String method = "findTracksByAlbumId";

	this.setBaseView("AudioVideoTracksView");
	this.setBaseClass("audiovideo.AudioVideoTracks");
	this.criteria = "audio_video_id = " + _value;

	try {
	    ArrayList list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AudioVideoException(this.dbo, 0000, null);
	}
    }

    /**
     * Retrieves one or more tracks based on track title.  The results can come from multiple albums  
     * type audio or video.
     * 
     * @param projectId
     * @throws AudioVideoException
     */
    public ArrayList findTracksByAlbumId(String _value) throws AudioVideoException {
	String method = "findTracksByAlbumId";

	this.setBaseView("AudioVideoTracksView");
	this.setBaseClass("audiovideo.AudioVideoTracks");
	this.criteria = "track_title like '" + _value + "%'";

	try {
	    ArrayList list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AudioVideoException(this.dbo, 0000, null);
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
    public ArrayList findAudioVideo(String _criteria) throws AudioVideoException {
	String method = "findAudioVideo";
	this.criteria = _criteria;
	try {
	    ArrayList list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AudioVideoException(this.dbo, 0000, null);
	}
    }

    /**
     * Retrieves an ArrayList of related to the Audio Video module based on custom criteria, base class, 
     * and base view properties.
     * 
     * @param criteria
     * @param baseClass
     * @param baseView
     * @throws AudioVideoException
     */
    public ArrayList findAudioVideo(String _criteria, String _baseClass, String _baseView) throws AudioVideoException {
	String method = "findAudioVideo(String _criteria, String _baseClass, String _baseView)";
	this.setBaseView(_baseView);
	this.setBaseClass(_baseClass);
	return this.findAudioVideo(_criteria);
    }

    public String setBaseView(String value) {
	return super.setBaseView(value);
    }

    public String setBaseClass(String value) {
	return super.setBaseClass(value);
    }

    public void setRipped(boolean value) {
	this.processRipped = value;
    }

}
