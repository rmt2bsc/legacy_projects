package com.audiovideo;

import java.io.File;

import com.api.BatchFileProcessor;
import com.audiovideo.AudioVideoException;


/**
 * 
 * @author appdev
 *
 */
public interface AudioVideoBatchUpdateApi extends BatchFileProcessor {

    
    /**
     * Perform any validations specific to the implementation design.
     * 
     * @throws AvBatchValidationException
     */
    void validate();

    /**
     * Removes all entries from the audio_video and audio_video_tracks tables 
     * where streaming files exist on the server.
     * 
     * @param projectTypeId
     * @return
     * @throws AudioVideoException
     */
    int purge(int projectTypeId) throws AudioVideoException;
    
    /**
     * Counts the total number of files of the directory, <i>mediaResource</i>, and its sub-directories.
     * 
     * @param mediaResource
     *          an instance of File which must represent a directory in the file system.
     * @return int
     *          the file count.
     */
    int computeTotalFileCount(File mediaResource);

}
