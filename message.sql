/*
-- Query: SHOW COLUMNS FROM bbs.message
-- Date: 2016-05-24 13:32
*/
INSERT INTO `message` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('id','int(11)','NO','PRI',NULL,'auto_increment');
INSERT INTO `message` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('subject','varchar(45)','NO','',NULL,'');
INSERT INTO `message` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('text','varchar(45)','NO','',NULL,'');
INSERT INTO `message` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('category','varchar(45)','NO','',NULL,'');
INSERT INTO `message` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('update_date','timestamp','NO','','CURRENT_TIMESTAMP','on update CURRENT_TIMESTAMP');
INSERT INTO `message` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('user_id','varchar(45)','NO','',NULL,'');
INSERT INTO `message` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('insert_date','timestamp','NO','','0000-00-00 00:00:00','');
