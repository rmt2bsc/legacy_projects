CREATE TABLE comments2 
 (comment_id integer NOT NULL DEFAULT autoincrement, 
   comment_text char(1000) DEFAULT NULL, 
   date_created timestamp DEFAULT current timestamp, 
   date_updated timestamp DEFAULT current timestamp, 
   user_id char(8) DEFAULT 'DBA' , 
   PRIMARY KEY (comment_id)) ;