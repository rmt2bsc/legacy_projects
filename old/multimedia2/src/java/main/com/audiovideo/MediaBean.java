package com.audiovideo;

import com.bean.AvArtist;
import com.bean.AvProject;
import com.bean.AvTracks;
import com.bean.RMT2BaseBean;

//import audiovideo.AudioVideoArtist;
//import audiovideo.AudioVideo;
//import audiovideo.AudioVideoTracks;

import com.util.SystemException;

public class MediaBean extends RMT2BaseBean {

    private static final long serialVersionUID = -5685002700685343457L;

    private AvArtist ava;

    private AvProject av;

    private AvTracks avt;

    private String genre;

    public MediaBean() throws SystemException {
    }

    public AvArtist getAva() {
	return this.ava;
    }

    public void setAva(AvArtist value) {
	this.ava = value;
    }

    public AvProject getAv() {
	return this.av;
    }

    public void setAv(AvProject value) {
	this.av = value;
    }

    public AvTracks getAvt() {
	return this.avt;
    }

    public void setAvt(AvTracks value) {
	this.avt = value;
    }

    public void setGenre(String value) {
	this.genre = value;
    }

    public String getGenre() {
	return this.genre;
    }

    public void initBean() throws SystemException {
    };
}