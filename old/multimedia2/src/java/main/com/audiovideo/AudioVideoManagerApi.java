package com.audiovideo;


import java.util.List;

import com.audiovideo.AudioVideoException;

import com.bean.AvArtist;
import com.bean.AvGenre;
import com.bean.AvProject;
import com.bean.AvTracks;



public interface AudioVideoManagerApi {

     

     AvArtist findArtistById(int artistId) throws AudioVideoException;

     List findArtistByName(String name) throws AudioVideoException;

     AvProject findAlbum(int albumId) throws AudioVideoException;
     
     /**
      * 
      * @param artistId
      * @param albumTitle
      * @return
      * @throws AudioVideoException
      */
     AvProject findAlbum(int artistId, String albumTitle) throws AudioVideoException;

     List findAlbum(String criteria) throws AudioVideoException;
     
     List findAlbumByArtistId(int id) throws AudioVideoException;

     List findAlbumByTitle(String title) throws AudioVideoException;

     List findAlbumByGenre(int genreId) throws AudioVideoException;

     List findAlbumByYear(int year) throws AudioVideoException;

     AvTracks findTrack(int trackId) throws AudioVideoException;

     List findTracksByAlbumId(int albumId) throws AudioVideoException;

     List findTracksByAlbumId(String albumId) throws AudioVideoException;

     AvGenre findGenre(int genreId) throws AudioVideoException;
     
     List findGenre(String name) throws AudioVideoException;
     
     

     /**
      * 
      * @param artistName
      * @return
      * @throws AudioVideoException
      */
     int createArtist(String artistName) throws AudioVideoException;
     
     /**
      * 
      * @param proj
      * @param genre
      * @return
      * @throws AudioVideoException
      */
     int createAudioProject(AvProject proj, String genre) throws AudioVideoException;
     
     /**
      * 
      * @param proj
      * @param genre
      * @return
      * @throws AudioVideoException
      */
     int createVideoProject(AvProject proj, String genre) throws AudioVideoException;
     
     /**
      * 
      * @param track
      * @return
      * @throws AudioVideoException
      */
     int createTrack(AvTracks track) throws AudioVideoException;
     
     
     /**
      * 
      * @param avb
      * @return
      * @throws AudioVideoException
      */
     int updateProject(MediaBean avb) throws AudioVideoException;

     void setRipped(boolean flag);
}
