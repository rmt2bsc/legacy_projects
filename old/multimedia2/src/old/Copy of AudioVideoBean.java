package com.audiovideo;

import com.bean.RMT2BaseBean;

import audiovideo.AudioVideoArtist;
import audiovideo.AudioVideo;
import audiovideo.AudioVideoTracks;

import com.util.RMT2Utility;
import com.util.SystemException;

public class AudioVideoBean extends RMT2BaseBean {

    private AudioVideoArtist ava;

    private AudioVideo av;

    private AudioVideoTracks avt;

    private String genre;

    public AudioVideoBean() throws SystemException {
    }

    public AudioVideoArtist getAva() {
	return this.ava;
    }

    public void setAva(AudioVideoArtist value) {
	this.ava = value;
    }

    public AudioVideo getAv() {
	return this.av;
    }

    public void setAv(AudioVideo value) {
	this.av = value;
    }

    public AudioVideoTracks getAvt() {
	return this.avt;
    }

    public void setAvt(AudioVideoTracks value) {
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