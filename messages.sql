/*
-- Query: DESC bbs.messages
-- Date: 2016-05-24 14:07
*/
INSERT INTO `messages` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('id','int(11)','NO','PRI',NULL,'auto_increment');
INSERT INTO `messages` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('subject','varchar(50)','NO','',NULL,'');
INSERT INTO `messages` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('text','varchar(1000)','NO','',NULL,'');
INSERT INTO `messages` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('category','varchar(10)','NO','',NULL,'');
INSERT INTO `messages` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('update_date','timestamp','NO','','CURRENT_TIMESTAMP','on update CURRENT_TIMESTAMP');
INSERT INTO `messages` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('user_id','varchar(45)','NO','',NULL,'');
INSERT INTO `messages` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('insert_date','timestamp','NO','','0000-00-00 00:00:00','');
