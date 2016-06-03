/*
-- Query: DESC bbs.comments
-- Date: 2016-05-24 14:06
*/
INSERT INTO `comments` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('id','int(11)','NO','PRI',NULL,'auto_increment');
INSERT INTO `comments` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('message_id','int(11)','NO','',NULL,'');
INSERT INTO `comments` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('text','varchar(500)','NO','',NULL,'');
INSERT INTO `comments` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('update_date','timestamp','NO','','CURRENT_TIMESTAMP','on update CURRENT_TIMESTAMP');
INSERT INTO `comments` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('user_id','int(11)','NO','',NULL,'');
INSERT INTO `comments` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('insert_date','int(11)','NO','',NULL,'');
