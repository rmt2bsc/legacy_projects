package com.audiovideo;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.File;

import com.audiovideo.AudioVideoManagerApi;
import com.audiovideo.AudioVideoBatchUpdateApi;
import com.audiovideo.AudioVideoManagerApiImpl;
import com.audiovideo.MediaBean;

import com.bean.AvArtist;
import com.bean.AvProject;
import com.bean.AvTracks;
import com.bean.db.DatabaseConnectionBean;

/**
 * 
 * @author appdev
 *
 */
public class AudioVideoFactory {

    private static String ERROR_MP3_API = "MP3 Api Instantiation Error: ";
    
    /**
     * 
     * @param con
     * @return
     */
    public static AudioVideoManagerApi createApi(DatabaseConnectionBean con) {
	try {
	    AudioVideoManagerApi api = new AudioVideoManagerApiImpl(con);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * 
     * @param con
     * @param dirPath
     * @return
     */
    public static AudioVideoBatchUpdateApi createBatchApi(DatabaseConnectionBean con, String dirPath) {
	try {
	    AudioVideoBatchUpdateApi api = new AvBatchImpl(con, dirPath);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * 
     * @param mp3Source
     * @return
     */
    public static MP3Reader createMyId3Api(File mp3Source) {
	String msg = ERROR_MP3_API;
	MP3Reader api = null;  
	try {
	   api = new MyMp3LibImpl(mp3Source);
	   return api;
	}
	catch (Exception e) {
	    msg += e.getMessage();
	    Logger.getLogger("AudioVideoFactory").log(Level.FATAL, ERROR_MP3_API);
	    throw new MP3ApiInstantiationException(e);
	}
    }
    
    
    public static MP3Reader createJID3Mp3Api(File mp3Source) {
	String msg = ERROR_MP3_API;
	MP3Reader api = null;
	try {
	   api = new JID3Mp3LibImpl(mp3Source);
	   return api;
	}
	catch (Exception e) {
	    msg += e.getMessage();
	    Logger.getLogger("AudioVideoFactory").log(Level.FATAL, msg);
	    throw new MP3ApiInstantiationException(e);
	}
    }
    
    public static MP3Reader createEntaggedId3Api(File mp3Source) {
	String msg = ERROR_MP3_API;
	MP3Reader api = null;
	try {
	   api = new EntaggedMp3LibImpl(mp3Source);
	   return api;
	}
	catch (Throwable e) {
	    msg += e.getMessage();
	    Logger.getLogger("AudioVideoFactory").log(Level.FATAL, msg);
	    throw new MP3ApiInstantiationException(e);
	}
    }
    
    /**
     * 
     * @return
     */
    public static MediaBean create() {
	try {
	    MediaBean obj = new MediaBean();
	    AvArtist ava = new AvArtist();
	    AvProject av = new AvProject();
	    AvTracks avt = new AvTracks();
	    obj.setAva(ava);
	    obj.setAv(av);
	    obj.setAvt(avt);
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

}