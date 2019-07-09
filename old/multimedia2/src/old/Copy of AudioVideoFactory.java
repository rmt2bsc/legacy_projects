package com.audiovideo;

import com.audiovideo.AudioVideoManagerApi;
import com.audiovideo.AudioVideoBatchUpdateApi;
import com.audiovideo.AudioVideoManagerApiImpl;
import com.audiovideo.AudioVideoArtist;
import com.audiovideo.AudioVideo;
import com.audiovideo.AudioVideoTracks;
import com.audiovideo.AudioVideoBean;
import com.audiovideo.AudioVideoException;

import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;
import com.api.db.DatabaseException;



public class AudioVideoFactory {

    public static AudioVideoManagerApi createApi(DatabaseConnectionBean _dbo) throws SystemException, DatabaseException, AudioVideoException {
	AudioVideoManagerApi api = new AudioVideoManagerApiImpl(_dbo);
	api.setBaseView("AudioVideoView");
	api.setBaseClass("com.bean.AudioVideoBean");
	return api;
    }

    public static AudioVideoBatchUpdateApi createBatchApi(DatabaseConnectionBean _dbo, String _serverName, String _shareName, String _rootPathName) throws SystemException,
	    DatabaseException, AudioVideoException {

	AudioVideoBatchUpdateApi api = new AudioVideoManagerApiImpl(_dbo, _serverName, _shareName, _rootPathName);
	api.setBaseView("AudioVideoView");
	api.setBaseClass("com.bean.AudioVideoBean");
	return api;
    }

    public static AudioVideoBean create() throws SystemException {
	AudioVideoBean obj = new AudioVideoBean();
	AudioVideoArtist ava = createAva();
	AudioVideo av = createAv();
	AudioVideoTracks avt = createAvt();
	obj.setAva(ava);
	obj.setAv(av);
	obj.setAvt(avt);
	return obj;
    }

    public static AudioVideoBean create(String _serverName, String _shareName, String _rootPath) throws SystemException {
	AudioVideoBean obj = create();
	AudioVideoArtist ava = createAva();
	AudioVideo av = createAv();
	AudioVideoTracks avt = createAvt();
	avt.setLocServername(_serverName);
	avt.setLocSharename(_shareName);
	avt.setLocRootPath(_rootPath);
	obj.setAva(ava);
	obj.setAv(av);
	obj.setAvt(avt);
	return obj;
    }

    public static AudioVideoArtist createAva() throws SystemException {
	AudioVideoArtist obj = new AudioVideoArtist();
	return obj;
    }

    public static AudioVideo createAv() throws SystemException {
	AudioVideo obj = new AudioVideo();
	return obj;
    }

    public static AudioVideoTracks createAvt() throws SystemException {
	AudioVideoTracks obj = new AudioVideoTracks();
	return obj;
    }
}