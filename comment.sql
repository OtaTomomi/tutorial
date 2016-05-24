/*
-- Query: SHOW COLUMNS FROM bbs.comment
-- Date: 2016-05-24 13:34
*/
INSERT INTO `comment` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('id','int(11)','NO','PRI',NULL,'auto_increment');
INSERT INTO `comment` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('message_id','int(11)','NO','',NULL,'');
INSERT INTO `comment` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('update_date','timestamp','NO','','CURRENT_TIMESTAMP','on update CURRENT_TIMESTAMP');
INSERT INTO `comment` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('user_id','int(11)','NO','',NULL,'');
INSERT INTO `comment` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('insert_date','int(11)','NO','',NULL,'');
