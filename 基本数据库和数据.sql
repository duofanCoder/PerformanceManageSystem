/*
SQLyog v10.2 
MySQL - 5.7.21 : Database - performance
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`performance` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `performance`;

/*Table structure for table `biz_category` */

DROP TABLE IF EXISTS `biz_category`;

CREATE TABLE `biz_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `biz_category` */

insert  into `biz_category`(`id`,`create_time`,`description`,`name`,`update_time`) values (1,'2022-04-04 17:38:02.094000','关于这类的的通知分类里','创新类','2022-04-04 17:38:02.094000'),(2,'2022-04-04 17:38:12.000000','不知道了','其他类','2022-04-04 17:38:12.000000'),(3,'2022-04-11 10:51:30.857000','测试分类','测试','2022-04-11 10:51:30.857000');

/*Table structure for table `biz_department` */

DROP TABLE IF EXISTS `biz_department`;

CREATE TABLE `biz_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `biz_department` */

insert  into `biz_department`(`id`,`create_time`,`description`,`name`,`update_time`) values (1,'2022-04-04 16:03:27.600000','企划部门的里面，不知道干啥','企划部','2022-04-04 16:03:27.600000'),(2,'2022-04-04 16:03:37.762000','里面都是研发','研发部门','2022-04-04 16:03:37.762000'),(3,'2022-04-04 16:03:52.350000','都是律师大佬','法务部门','2022-04-04 16:03:52.350000'),(4,'2022-04-11 10:44:59.360000','测试部门1','测试','2022-04-11 10:45:15.191000');

/*Table structure for table `biz_group` */

DROP TABLE IF EXISTS `biz_group`;

CREATE TABLE `biz_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `biz_group` */

insert  into `biz_group`(`id`,`create_time`,`description`,`name`,`update_time`) values (1,'2022-04-04 16:27:56.481000','这是一个分组，方便管理考核的人','第一次考核组','2022-04-04 17:29:26.356000'),(2,'2022-04-10 15:42:14.238000','这里准备让他们做一次程序的考核','程序员组','2022-04-10 15:42:14.238000'),(3,'2022-04-11 10:45:49.992000','测试分组','新的测试分组','2022-04-11 10:45:49.992000');

/*Table structure for table `biz_method` */

DROP TABLE IF EXISTS `biz_method`;

CREATE TABLE `biz_method` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `biz_method` */

insert  into `biz_method`(`id`,`create_time`,`description`,`name`,`update_time`) values (1,'2022-04-04 17:43:58.249000','必须要自拍','自拍','2022-04-04 17:43:58.249000'),(2,'2022-04-04 17:44:11.915000','必须要严格遵守','他评','2022-04-04 17:44:11.915000');

/*Table structure for table `biz_news` */

DROP TABLE IF EXISTS `biz_news`;

CREATE TABLE `biz_news` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK96dymjelt9vublxkdpq25h989` (`category_id`),
  CONSTRAINT `FK96dymjelt9vublxkdpq25h989` FOREIGN KEY (`category_id`) REFERENCES `biz_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `biz_news` */

insert  into `biz_news`(`id`,`create_time`,`description`,`name`,`update_time`,`category_id`) values (1,'2022-04-04 17:42:43.613000','明天八点半核酸检测','核酸检测通知','2022-04-04 17:42:43.613000',2),(2,'2022-04-11 10:51:46.593000','这里是内容哦','测试新闻','2022-04-11 10:51:46.593000',3);

/*Table structure for table `biz_postion` */

DROP TABLE IF EXISTS `biz_postion`;

CREATE TABLE `biz_postion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `biz_postion` */

insert  into `biz_postion`(`id`,`create_time`,`description`,`name`,`update_time`) values (1,'2022-04-04 16:04:03.859000','','高级领导','2022-04-04 16:04:03.859000'),(2,'2022-04-04 16:04:09.315000','','中级领导','2022-04-04 16:04:09.315000'),(3,'2022-04-04 16:04:17.418000','','打工人','2022-04-04 16:04:17.418000');

/*Table structure for table `biz_quota` */

DROP TABLE IF EXISTS `biz_quota`;

CREATE TABLE `biz_quota` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `biz_quota` */

insert  into `biz_quota`(`id`,`create_time`,`description`,`name`,`score`,`update_time`,`weight`) values (1,'2022-04-04 17:44:29.164000','一周内扶老奶奶次数','个人素质',12,'2022-04-04 18:11:25.029000',100),(2,'2022-04-04 17:44:38.002000','JAVA','专业技能',10,'2022-04-04 18:11:32.122000',300),(3,'2022-04-04 17:44:44.862000','C\n','专业技能',20,'2022-04-04 18:11:39.172000',400),(4,'2022-04-10 16:12:57.517000','C#','专业技能',12,'2022-04-10 16:12:57.517000',120),(5,'2022-04-11 10:48:46.255000','Python','专业技能',20,'2022-04-11 10:48:46.255000',80);

/*Table structure for table `biz_record` */

DROP TABLE IF EXISTS `biz_record`;

CREATE TABLE `biz_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `score_json` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `audience_id` bigint(20) DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  `scheme_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKcway4183pmmbd5ccg9k3pelw3` (`owner_id`,`audience_id`,`scheme_id`),
  KEY `FKlffgxnorr1xgij0wlo7ybfyh9` (`audience_id`),
  KEY `FKlamjw0w8cmlm7544c7ewip9ea` (`scheme_id`),
  CONSTRAINT `FK4bfve9iwsc3m2e1ktm85kx6vt` FOREIGN KEY (`owner_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKlamjw0w8cmlm7544c7ewip9ea` FOREIGN KEY (`scheme_id`) REFERENCES `biz_scheme` (`id`),
  CONSTRAINT `FKlffgxnorr1xgij0wlo7ybfyh9` FOREIGN KEY (`audience_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

/*Data for the table `biz_record` */

insert  into `biz_record`(`id`,`create_time`,`score_json`,`update_time`,`audience_id`,`owner_id`,`scheme_id`) values (1,'2022-04-10 22:45:27.922000','{1:300,2:900,3:1600,4:600}','2022-04-10 22:45:27.922000',1,4,13),(2,'2022-04-10 22:45:35.986000','{1:300,2:900,3:1600,4:600}','2022-04-10 22:45:35.986000',4,4,13),(5,'2022-04-10 23:35:08.153000','{1:400,2:1200,3:2000,4:720}','2022-04-10 23:35:08.153000',4,5,13),(6,'2022-04-11 10:53:58.942000','{1:500,2:1500,4:600}','2022-04-11 10:53:58.942000',7,4,10);

/*Table structure for table `biz_scheme` */

DROP TABLE IF EXISTS `biz_scheme`;

CREATE TABLE `biz_scheme` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `method_id` bigint(20) DEFAULT NULL,
  `frequency` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrec0vng70i0mbe6ds6rtb0oq4` (`group_id`),
  KEY `FKp9o8l51blqtk1m1iopbtaphw0` (`method_id`),
  CONSTRAINT `FKp9o8l51blqtk1m1iopbtaphw0` FOREIGN KEY (`method_id`) REFERENCES `biz_method` (`id`),
  CONSTRAINT `FKrec0vng70i0mbe6ds6rtb0oq4` FOREIGN KEY (`group_id`) REFERENCES `biz_group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;

/*Data for the table `biz_scheme` */

insert  into `biz_scheme`(`id`,`create_time`,`description`,`name`,`update_time`,`group_id`,`method_id`,`frequency`) values (9,'2022-04-10 17:54:27.000000','全体考核的','第一次全体考核','2022-04-10 17:37:58.583000',1,1,'每日'),(10,'2022-04-10 17:54:28.000000','滴哦二次','第二次程序考核','2022-04-10 17:38:07.365000',2,2,'每日'),(11,'2022-04-10 17:54:31.000000','第三次','第三次考核','2022-04-10 17:38:36.336000',2,2,'每日'),(13,'2022-04-10 17:53:58.793000','测','测试','2022-04-10 17:53:58.793000',1,1,'每日'),(15,NULL,'123','考核333',NULL,2,2,'每周'),(16,'2022-04-11 14:17:03.352000','12212121','123','2022-04-11 14:17:03.352000',2,2,'每日'),(23,NULL,'132312132','123123',NULL,1,1,'每周');

/*Table structure for table `biz_scheme_quota` */

DROP TABLE IF EXISTS `biz_scheme_quota`;

CREATE TABLE `biz_scheme_quota` (
  `scheme_id` bigint(20) NOT NULL,
  `quota_id` bigint(20) NOT NULL,
  KEY `FKnv6329od4tmaxl7gdfeb14n50` (`quota_id`),
  KEY `FKofvcn78vti11xj13f60fkcpta` (`scheme_id`),
  CONSTRAINT `FKnv6329od4tmaxl7gdfeb14n50` FOREIGN KEY (`quota_id`) REFERENCES `biz_quota` (`id`),
  CONSTRAINT `FKofvcn78vti11xj13f60fkcpta` FOREIGN KEY (`scheme_id`) REFERENCES `biz_scheme` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `biz_scheme_quota` */

insert  into `biz_scheme_quota`(`scheme_id`,`quota_id`) values (10,1),(10,2),(10,4),(11,1),(11,2),(11,5),(13,1),(13,2),(13,3),(13,4),(9,1),(9,2),(9,4),(9,3),(16,1),(16,5),(23,1),(23,2),(23,3),(23,5),(15,1),(15,2),(15,3),(15,5);

/*Table structure for table `biz_user_group` */

DROP TABLE IF EXISTS `biz_user_group`;

CREATE TABLE `biz_user_group` (
  `group_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  UNIQUE KEY `UKeo8kk6ovgjtetwf8ea1pgfv5x` (`group_id`,`user_id`),
  KEY `FKp3xh3w6vw6fm5353bq20umaaq` (`user_id`),
  CONSTRAINT `FKedpvbrq1we2irnh5rrxtrhnvq` FOREIGN KEY (`group_id`) REFERENCES `biz_group` (`id`),
  CONSTRAINT `FKp3xh3w6vw6fm5353bq20umaaq` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `biz_user_group` */

insert  into `biz_user_group`(`group_id`,`user_id`) values (1,1),(2,1),(3,1),(1,4),(2,4),(1,5),(2,5),(3,5),(1,7),(2,7),(3,7);

/*Table structure for table `sys_file` */

DROP TABLE IF EXISTS `sys_file`;

CREATE TABLE `sys_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `full_file_path` varchar(255) DEFAULT NULL,
  `height` int(11) NOT NULL,
  `original_file_name` varchar(255) DEFAULT NULL,
  `size` bigint(20) NOT NULL,
  `suffix` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `width` int(11) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjym8m658u46l75r72ctopy3kn` (`user_id`),
  CONSTRAINT `FKjym8m658u46l75r72ctopy3kn` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_file` */

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `gender` bit(1) NOT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  `position_id` bigint(20) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_username` (`username`),
  KEY `FK222pno20pk1w3bc93t5fdxbbr` (`department_id`),
  KEY `FK5tru0g4sbvy7jo0xftu5nlutb` (`position_id`),
  CONSTRAINT `FK222pno20pk1w3bc93t5fdxbbr` FOREIGN KEY (`department_id`) REFERENCES `biz_department` (`id`),
  CONSTRAINT `FK5tru0g4sbvy7jo0xftu5nlutb` FOREIGN KEY (`position_id`) REFERENCES `biz_postion` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`create_time`,`gender`,`mobile`,`name`,`password`,`update_time`,`username`,`department_id`,`position_id`,`role`) values (1,'2022-04-04 16:27:08.000000','\0','123123','多凡','$2a$10$swnNdvUC.xcw.MSaPV4e4.bp28OQCE/rHyTBiPtIxHl6XYbEZ//hG','2022-04-11 14:12:14.492000','duofan',1,1,'ADMIN'),(4,'2022-04-04 16:06:37.410000','','181151559878','小白1','$2a$10$Pmj5FRHdEpIqy8H3ZiQ/8OO7ptsizIBsteUIqcqydyzWvaGHpUeei','2022-04-04 16:06:37.410000','xiaobai',3,2,'COMMON'),(5,'2022-04-10 23:09:38.938000','\0','13775556645','测试人员1号','$2a$10$2OyFAi8bwFx2iAhJ3NH3.OuqDwGcGnPsseNFSjESSCh.u6yut5kwK','2022-04-10 23:09:38.938000','test1',2,2,'COMMON'),(7,'2022-04-10 23:11:22.964000','\0','1322412333','测试人员2号','$2a$10$n7yBkpq.vi480/EU.dBnmeOxl0uDSBQeYpdBXXzM6XYixbObkU7F6','2022-04-10 23:11:22.964000','test2',3,1,'COMMON'),(8,'2022-04-11 10:47:43.871000','\0','1233332222','测试人员3号','$2a$10$HJLLjRmGnO36kMHvS1kupeChiUbAnsTZtmt2wDnzn4Xpo48NeWlXC','2022-04-11 10:47:43.871000','test3',2,3,'COMMON');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
