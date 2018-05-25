/*
-- 修改数据库编码为utf-8
-- create database labWeb
-- use labWeb
-- 先删除具有外键的表
DROP TABLE IF EXISTS `NEWS`;
-- 创建新闻索引表
DROP TABLE IF EXISTS `NEWSLIST`;
CREATE TABLE `NEWSLIST`(
  `htmlid`              BIGINT       NOT NULL,
  `pid`                 VARCHAR(5) NOT NULL,
  `id`               VARCHAR(5) NOT NULL,
  `title`            VARCHAR(255) NOT NULL,
  `date_created`       date     NOT NULL,
  `titleImage`       VARCHAR(50),
  PRIMARY KEY (`htmlid`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- 创建新闻表
DROP TABLE IF EXISTS `NEWS`;
CREATE TABLE `NEWS` (
-- htmlid使用当前毫秒值作为值
  `htmlid`              BIGINT       NOT NULL,
  `count`               INTEGER,
  `publisher`           VARCHAR(255) DEFAULT 'admin',
  `title`               VARCHAR(255) DEFAULT NULL,
  `type`                VARCHAR(255) DEFAULT NULL,
  `abstractText`            TEXT DEFAULT NULL,
  `content`             TEXT NOT NULL,
  `date_created`       date     NOT NULL,
  UNIQUE KEY `index_id` (`htmlid`),  -- 外键需要建立索引，否则150错误。
  foreign KEY(`htmlid`) references NEWSLIST(`htmlid`)   -- 外键约束
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ftp文件描述
DROP TABLE IF EXISTS `FtpFile`;
CREATE TABLE `FtpPublicFile`(
   `id`                   varchar(50)    not null,
  `filename`              varchar(255)    primary key  NOT NULL,
  `uploaduser`                 VARCHAR(25) NOT NULL,
  `description`               VARCHAR(255) NOT NULL,
  `date_created`       date     NOT NULL
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ftp文件描述
DROP TABLE IF EXISTS `FtpFile`;
CREATE TABLE `FtpPrivateFile`(
   `id`                   varchar(50)    not null,
  `filename`              varchar(255)    primary key  NOT NULL,
  `uploaduser`                 VARCHAR(25) NOT NULL,
  `description`               VARCHAR(255) NOT NULL,
  `date_created`       date     NOT NULL
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ftp用户
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `description` TEXT DEFAULT NULL,
  `roles` varchar(200) DEFAULT 'USER',
  PRIMARY KEY (`uid`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;

*/