%/****************************************************************
% * Usage:        Music/Video Table PRIMARY KEYS
% *
% * Description:  This script creates tables that will stores data
% *               related to music cd's, cassettes, records, video 
% *               tapes, and ect.  This script should be executed 
% *               after the accounting tables and music/video code
% *               tables have been created.
% *
% * DBMS Target:  Sybase SQL AnyWhere
% ****************************************************************/


ALTER TABLE AUDIO_VIDEO_DETAIL
  ADD PRIMARY KEY (ID);

ALTER TABLE AUDIO_VIDEO_TRACKS
  ADD PRIMARY KEY (ID);

