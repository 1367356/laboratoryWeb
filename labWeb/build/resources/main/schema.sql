/*
-- 修改数据库编码为utf-8
-- create database labWeb
-- use labWeb
-- 先删除具有外键的表
DROP TABLE IF EXISTS `news`;
-- 创建新闻索引表
DROP TABLE IF EXISTS `newslist`;
CREATE TABLE `newslist`(
  `htmlid`              BIGINT       NOT NULL,
  `pid`                 VARCHAR(2) NOT NULL,
  `id`               VARCHAR(2) NOT NULL,
  `title`            VARCHAR(100) DEFAULT NULL,
  `date_created`       date     NOT NULL,
  `titleImage`       VARCHAR(50) DEFAULT NULL,
  `type`             VARCHAR(20) DEFAULT NULL,
  INDEX index_pidid(pid,id),
  PRIMARY KEY (`htmlid`)
)ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- InnoDB
-- 创建科研团队表
DROP TABLE IF EXISTS `researchTeam`;
CREATE TABLE `researchTeam`(
  `htmlid`              BIGINT       NOT NULL,
  `pid`                 VARCHAR(2) NOT NULL,
  `id`               VARCHAR(2) NOT NULL,
  `title`            VARCHAR(255) DEFAULT NULL,
  `date_created`       date     NOT NULL,
  `titleImage`       VARCHAR(50) DEFAULT NULL,
  `type`             VARCHAR(20) DEFAULT NULL,
  INDEX index_id(id),
  PRIMARY KEY (`htmlid`)
)ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- 创建新闻表
DROP TABLE IF EXISTS `NEWS`;
CREATE TABLE `news` (
-- htmlid使用当前毫秒值作为值
   `pid`                varchar(2) not null,
   `id`                 varchar(2) not null,
  `htmlid`              BIGINT       NOT NULL,
  `count`               INTEGER,
  `publisher`           VARCHAR(20) DEFAULT 'admin',
  `title`               VARCHAR(255) DEFAULT NULL,
  `type`                VARCHAR(20) DEFAULT NULL,
  `abstractText`            TEXT DEFAULT NULL,
  `content`             MEDIUMTEXT DEFAULT NULL,
  `date_created`       date     DEFAULT NULL,
  UNIQUE KEY `index_id` (`htmlid`)  -- 外键需要建立索引，否则150错误。
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- ftp文件描述
DROP TABLE IF EXISTS `FtpPublicFile`;
CREATE TABLE `FtpPublicFile`(
`downloadLink`         varchar(100)  not null,
   `id`                   varchar(50)    not null,
  `filename`              varchar(100)     NOT NULL,
  `uploaduser`                 VARCHAR(50) NOT NULL,
  `description`               VARCHAR(255) default NULL,
  `date_created`       date     NOT NULL,
  INDEX index_id(date_created)
)ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- ftp文件描述
DROP TABLE IF EXISTS `FtpPrivateFile`;
CREATE TABLE `FtpPrivateFile`(
    `downloadLink`         varchar(100)  not null,
   `id`                   varchar(50)    not null,
  `filename`              varchar(100)     NOT NULL,
  `uploaduser`                 VARCHAR(50) NOT NULL,
  `description`               VARCHAR(255) default NULL,
  `date_created`       date     NOT NULL,
  INDEX index_id(date_created)
)ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- ftp用户
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL UNIQUE,
  `password` varchar(255) DEFAULT NULL,
  `description` TEXT DEFAULT NULL,
  `roles` varchar(15) DEFAULT 'USER',
  PRIMARY KEY (`uid`)
)ENGINE=MyISAM  DEFAULT CHARSET=utf8;

INSERT INTO `labweb`.`user` (`uid`, `username`, `password`, `description`, `roles`) VALUES ('13', 'admin', '$2a$10$gHonI5g6zgRb0IUHOCP5Xe8hzAl2ptHBdd2Qnc4k8fL4AOTWrQINS', '管理员', 'USER,ADMIN');

*/