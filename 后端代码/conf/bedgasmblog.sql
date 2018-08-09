/*
SQLyog Community v12.5.0 (64 bit)
MySQL - 5.7.23 : Database - bedgasmblog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bedgasmblog` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bedgasmblog`;

/*Table structure for table `t_article` */

DROP TABLE IF EXISTS `t_article`;

CREATE TABLE `t_article` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `summary` varchar(200) DEFAULT NULL COMMENT '文章摘要',
  `releaseDate` varchar(30) NOT NULL,
  `clickCount` int(20) DEFAULT NULL,
  `replyCount` int(20) DEFAULT NULL,
  `content` mediumtext COMMENT '文章正文',
  `mdcontent` mediumtext COMMENT 'markdown正文',
  `tags` varchar(50) DEFAULT NULL,
  `blogtypeid` int(10) DEFAULT NULL,
  `author` int(20) NOT NULL COMMENT '作者的用户id',
  PRIMARY KEY (`id`),
  KEY `authorinfo` (`author`),
  CONSTRAINT `authorinfo` FOREIGN KEY (`author`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8;

/*Table structure for table `t_blacklist` */

DROP TABLE IF EXISTS `t_blacklist`;

CREATE TABLE `t_blacklist` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `userid` int(20) NOT NULL,
  `msg` varchar(140) NOT NULL COMMENT '拉黑原因',
  `msgstatus` int(1) NOT NULL COMMENT '0为已经失效，1为还没失效',
  `blockDate` varchar(30) NOT NULL COMMENT '拉黑日期',
  `deblockDate` varchar(30) NOT NULL COMMENT '解封日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_blogtype` */

DROP TABLE IF EXISTS `t_blogtype`;

CREATE TABLE `t_blogtype` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `typename` varchar(20) NOT NULL,
  `typecount` int(20) NOT NULL COMMENT '该类别下的文章数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=169 DEFAULT CHARSET=utf8;

/*Table structure for table `t_comment` */

DROP TABLE IF EXISTS `t_comment`;

CREATE TABLE `t_comment` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(200) NOT NULL,
  `commentDate` varchar(30) DEFAULT NULL,
  `userid` int(20) NOT NULL,
  `articleid` int(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userinfo` (`userid`),
  CONSTRAINT `userinfo` FOREIGN KEY (`userid`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;

/*Table structure for table `t_link` */

DROP TABLE IF EXISTS `t_link`;

CREATE TABLE `t_link` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `linkname` varchar(100) DEFAULT NULL,
  `linkurl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

/*Table structure for table `t_media` */

DROP TABLE IF EXISTS `t_media`;

CREATE TABLE `t_media` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `imagepath` varchar(100) DEFAULT NULL,
  `userid` int(20) NOT NULL,
  `releaseDate` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;

/*Table structure for table `t_star` */

DROP TABLE IF EXISTS `t_star`;

CREATE TABLE `t_star` (
  `subscriber` int(20) NOT NULL COMMENT '订阅者id',
  `subscribee` int(20) NOT NULL COMMENT '被订阅者id',
  PRIMARY KEY (`subscriber`,`subscribee`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_login` varchar(20) NOT NULL,
  `user_pass` varchar(50) NOT NULL,
  `user_nickname` varchar(20) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `signature` varchar(200) DEFAULT NULL COMMENT '个性签名',
  `user_profile` mediumtext COMMENT '头像路径',
  `role` int(1) DEFAULT NULL COMMENT '0为管理员，1为普通用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1047 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
