/*
-- Query: SHOW COLUMNS FROM bbs.user
-- Date: 2016-05-24 13:29
*/
INSERT INTO `user` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('id','int(11)','NO','PRI',NULL,'auto_increment');
INSERT INTO `user` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('loginId','varchar(45)','NO','',NULL,'');
INSERT INTO `user` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('password','varchar(45)','NO','',NULL,'');
INSERT INTO `user` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('name','varchar(45)','NO','',NULL,'');
INSERT INTO `user` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('branch','int(11)','NO','',NULL,'');
INSERT INTO `user` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('position','int(11)','NO','',NULL,'');
INSERT INTO `user` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('useable','varchar(45)','YES','',NULL,'');
INSERT INTO `user` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('insert_date','timestamp','NO','','CURRENT_TIMESTAMP','on update CURRENT_TIMESTAMP');
