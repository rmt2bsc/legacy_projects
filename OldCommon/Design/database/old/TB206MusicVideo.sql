%/****************************************************************
% * Usage:        Music/Video Tables
% *
% * Description:  This script creates tables that will stores data
% *               related to music cd's, cassettes, records, video 
% *               tapes, and ect.  This script should be executed 
% *               after the accounting tables and music/video code
% *               tables have been created.
% *
% * DBMS Target:  Sybase SQL AnyWhere
% ****************************************************************/


%/**************************************************************************
% This Table contains all detail information related to music and video
% media from the TANGIBLE_ASSET_COMMON table.
%
%  LEGEND
% ---------
%     Category:   1 = Jazz
%                 2 = Horror (Video)
%                 3 = Country and Western
%                 4 = Gospel
%                 5 = Funk, Rhytnm & Blues, Soul, Dance
%                 6 = Rap
%                 7 = Blues
%                 8 = Alternative
%                 9 = Latin
%                10 = Drama
%                11 = Comedy
%                12 = Science Fiction
%                13 = Other
%
%     Media Type: 1 = Compact Disc
%                 2 = Laser Disc
%                 3 = Cassette Tape
%                 4 = Reel to Reel
%                 5 = Digital Audio Tape
%                 6 = Album (LP)
%                 7 = Video Cassette (VHS)
%                 8 = Video Cassette (Beta)
%                 9 = Other
%****************************************************************************/

CREATE TABLE AUDIO_VIDEO_DETAIL
    (ID               INTEGER        NOT NULL,
     ASSET_MASTER_ID  INTEGER        NOT NULL,
     CATEGORY         INTEGER        NOT NULL,
     MEDIA_TYPE       INTEGER        NOT NULL,
     YEAR_RECORDED    CHAR(4),
     DATE_CREATED     DATE          NOT NULL DEFAULT CURRENT DATE ,
     DATE_UPDATED     DATE          NOT NULL DEFAULT CURRENT DATE ,
     CHANGE_REASON    VARCHAR(300),
     AREACODE_CREATED CHAR(3)        NOT NULL DEFAULT '972',
     CITY_CREATED     INTEGER        NOT NULL DEFAULT 1 ,
     USER_ID          CHAR(8)        NOT NULL DEFAULT 'DBA',
     COMMENT_ID       INTEGER);


%/**************************************************************************
%  This Table Details each row contained on the audio_video_detail table. *
%**************************************************************************/

CREATE TABLE AUDIO_VIDEO_TRACKS
    (ID                     INTEGER        NOT NULL,
     AUDIO_VIDEO_DETAIL_ID  INTEGER        NOT NULL,
     TRACK_NUMBER           SMALLINT       NOT NULL,
     TRACK_TITLE            CHAR(30),
     TRACK_HOURS            SMALLINT,
     TRACK_MINUTES          SMALLINT,
     TRACK_SECONDS          SMALLINT,
     PROJECT_SIDE           SMALLINT,
     TRACK_PRODUCER         CHAR(20),
     TRACK_COMPOSER         CHAR(20),
     TRACK_LYRICIST         CHAR(20),
     DATE_CREATED           DATE         NOT NULL DEFAULT CURRENT DATE ,
     DATE_UPDATED           DATE         NOT NULL DEFAULT CURRENT DATE ,
     CHANGE_REASON          VARCHAR(300),
     AREACODE_CREATED       CHAR(3)           NOT NULL DEFAULT '972',
     CITY_CREATED           INTEGER           NOT NULL DEFAULT 1 ,
     USER_ID                CHAR(8)           NOT NULL DEFAULT 'DBA',
     COMMENT_ID             INTEGER);
           


