/*
-- Query: DESC bbs.users
-- Date: 2016-05-24 14:11
*/
INSERT INTO `users` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('id','int(11)','NO','PRI',NULL,'auto_increment');
INSERT INTO `users` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('login_id','varchar(20)','NO','',NULL,'');
INSERT INTO `users` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('password','varchar(255)','NO','',NULL,'');
INSERT INTO `users` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('name','varchar(10)','NO','',NULL,'');
INSERT INTO `users` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('branch_id','int(11)','NO','',NULL,'');
INSERT INTO `users` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('position_id','int(11)','NO','',NULL,'');
INSERT INTO `users` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('useable','tinyint(4)','NO','',NULL,'');
INSERT INTO `users` (`Field`,`Type`,`Null`,`Key`,`Default`,`Extra`) VALUES ('insert_date','timestamp','NO','','CURRENT_TIMESTAMP','on update CURRENT_TIMESTAMP');
