%/****************************************************************
% * Usage:        Music/Video Table FOREING KEYS
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
   ADD FOREIGN KEY (ASSET_MASTER_ID) 
       REFERENCES ASSET_MASTER (ID);

ALTER TABLE AUDIO_VIDEO_DETAIL
   ADD FOREIGN KEY (MEDIA_TYPE) 
       REFERENCES GENERAL_CODES(CODE_ID);

ALTER TABLE AUDIO_VIDEO_DETAIL
   ADD  FOREIGN KEY (CATEGORY) 
        REFERENCES GENERAL_CODES(CODE_ID);

ALTER TABLE AUDIO_VIDEO_DETAIL
  ADD FOREIGN KEY ( COMMENT_ID ) 
      REFERENCES COMMENTS(COMMENT_ID);


ALTER TABLE AUDIO_VIDEO_TRACKS
   ADD FOREIGN KEY (AUDIO_VIDEO_DETAIL_ID) 
       REFERENCES AUDIO_VIDEO_DETAIL(ID);

ALTER TABLE AUDIO_VIDEO_TRACKS
  ADD FOREIGN KEY ( COMMENT_ID ) 
      REFERENCES COMMENTS(COMMENT_ID);
