/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : studentmanager

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-04-15 09:58:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `courseId` int(11) NOT NULL AUTO_INCREMENT COMMENT '课程表ID',
  `courseNumber` varchar(60) NOT NULL COMMENT '课程编号',
  `courseName` varchar(60) DEFAULT NULL COMMENT '课程名称',
  PRIMARY KEY (`courseId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='课程表';

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1', 'KC00', '编程思想');
INSERT INTO `course` VALUES ('2', 'KC01', '计算机网络');
INSERT INTO `course` VALUES ('3', 'KC02', 'Java程序设计');
INSERT INTO `course` VALUES ('4', 'KC03', '网页设计');
INSERT INTO `course` VALUES ('5', 'KC04', '大学英语');
INSERT INTO `course` VALUES ('6', 'KC05', '高等数学');
INSERT INTO `course` VALUES ('12', 'KC06', 'Flash');

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `gradeId` int(11) NOT NULL AUTO_INCREMENT COMMENT '成绩表ID',
  `userId` int(11) NOT NULL COMMENT '用户编号,用户表的外键',
  `courseNumber` varchar(60) NOT NULL COMMENT '课程编号，课程表的外键',
  `score` decimal(4,1) DEFAULT '0.0' COMMENT '成绩',
  PRIMARY KEY (`gradeId`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8 COMMENT='成绩表，通过用户表的编号和课程表的编号相关联';

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES ('1', '2', 'KC01', '90.0');
INSERT INTO `grade` VALUES ('2', '2', 'KC02', '87.8');
INSERT INTO `grade` VALUES ('3', '2', 'KC03', '90.0');
INSERT INTO `grade` VALUES ('4', '3', 'KC01', '88.0');
INSERT INTO `grade` VALUES ('5', '3', 'KC02', '90.0');
INSERT INTO `grade` VALUES ('6', '3', 'KC03', '90.0');
INSERT INTO `grade` VALUES ('7', '2', 'KC04', '86.3');
INSERT INTO `grade` VALUES ('8', '2', 'KC05', '78.0');
INSERT INTO `grade` VALUES ('9', '3', 'KC04', '89.2');
INSERT INTO `grade` VALUES ('10', '3', 'KC05', '89.0');
INSERT INTO `grade` VALUES ('11', '4', 'KC01', '88.0');
INSERT INTO `grade` VALUES ('12', '4', 'KC02', '80.0');
INSERT INTO `grade` VALUES ('13', '4', 'KC03', '91.7');
INSERT INTO `grade` VALUES ('14', '4', 'KC04', '85.0');
INSERT INTO `grade` VALUES ('15', '4', 'KC05', '90.0');
INSERT INTO `grade` VALUES ('16', '28', 'KC01', '80.5');
INSERT INTO `grade` VALUES ('17', '28', 'KC02', '87.3');
INSERT INTO `grade` VALUES ('18', '28', 'KC03', '89.2');
INSERT INTO `grade` VALUES ('19', '28', 'KC04', '80.5');
INSERT INTO `grade` VALUES ('20', '28', 'KC05', '85.0');
INSERT INTO `grade` VALUES ('21', '33', 'KC01', '86.6');
INSERT INTO `grade` VALUES ('22', '33', 'KC02', '84.5');
INSERT INTO `grade` VALUES ('23', '33', 'KC03', '80.0');
INSERT INTO `grade` VALUES ('24', '33', 'KC04', '85.0');
INSERT INTO `grade` VALUES ('25', '33', 'KC05', '89.0');
INSERT INTO `grade` VALUES ('31', '35', 'KC01', '88.0');
INSERT INTO `grade` VALUES ('32', '35', 'KC02', '0.0');
INSERT INTO `grade` VALUES ('33', '35', 'KC03', '123.0');
INSERT INTO `grade` VALUES ('34', '35', 'KC04', '0.0');
INSERT INTO `grade` VALUES ('35', '35', 'KC05', '123.0');
INSERT INTO `grade` VALUES ('37', '51', 'KC01', '85.3');
INSERT INTO `grade` VALUES ('38', '51', 'KC02', '0.0');
INSERT INTO `grade` VALUES ('39', '51', 'KC03', '0.0');
INSERT INTO `grade` VALUES ('40', '51', 'KC04', '0.0');
INSERT INTO `grade` VALUES ('41', '51', 'KC05', '0.0');
INSERT INTO `grade` VALUES ('43', '67', 'KC01', '78.0');
INSERT INTO `grade` VALUES ('44', '67', 'KC02', '0.0');
INSERT INTO `grade` VALUES ('45', '67', 'KC03', '0.0');
INSERT INTO `grade` VALUES ('46', '67', 'KC04', '0.0');
INSERT INTO `grade` VALUES ('47', '67', 'KC05', '0.0');
INSERT INTO `grade` VALUES ('49', '68', 'KC01', '123.0');
INSERT INTO `grade` VALUES ('50', '68', 'KC02', '0.0');
INSERT INTO `grade` VALUES ('51', '68', 'KC03', '78.0');
INSERT INTO `grade` VALUES ('52', '68', 'KC04', '0.0');
INSERT INTO `grade` VALUES ('53', '68', 'KC05', '0.0');
INSERT INTO `grade` VALUES ('72', '68', 'KC00', '12.0');
INSERT INTO `grade` VALUES ('73', '67', 'KC00', '123.0');
INSERT INTO `grade` VALUES ('74', '51', 'KC00', '0.0');
INSERT INTO `grade` VALUES ('75', '2', 'KC00', '88.0');
INSERT INTO `grade` VALUES ('76', '33', 'KC00', '0.0');
INSERT INTO `grade` VALUES ('77', '35', 'KC00', '0.0');
INSERT INTO `grade` VALUES ('78', '28', 'KC00', '0.0');
INSERT INTO `grade` VALUES ('79', '3', 'KC00', '11.0');
INSERT INTO `grade` VALUES ('80', '4', 'KC00', '80.0');
INSERT INTO `grade` VALUES ('193', '68', 'KC06', '0.0');
INSERT INTO `grade` VALUES ('194', '67', 'KC06', '0.0');
INSERT INTO `grade` VALUES ('195', '51', 'KC06', '51.0');
INSERT INTO `grade` VALUES ('196', '2', 'KC06', '2.0');
INSERT INTO `grade` VALUES ('197', '33', 'KC06', '0.0');
INSERT INTO `grade` VALUES ('198', '35', 'KC06', '0.0');
INSERT INTO `grade` VALUES ('199', '28', 'KC06', '28.0');
INSERT INTO `grade` VALUES ('200', '3', 'KC06', '0.0');
INSERT INTO `grade` VALUES ('201', '4', 'KC06', '0.0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `userName` varchar(10) DEFAULT NULL COMMENT '用户名称',
  `sex` int(1) DEFAULT NULL COMMENT '性别，1为男，0为女',
  `identity` bit(1) NOT NULL COMMENT '身份，true为老师，false为学生',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `introduction` varchar(40) DEFAULT '该用户很懒,什么也没写' COMMENT '个人介绍',
  `createTime` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2', '露萨', '0', '\0', '2018-02-26', '我是露萨，露萨是我。。。', '2018-02-03 22:00:03');
INSERT INTO `user` VALUES ('3', '瓦斯斯', '1', '\0', '2018-01-27', '该用户很懒,什么也没写', '2018-01-15 18:00:00');
INSERT INTO `user` VALUES ('4', '伊尔', '0', '\0', '2018-01-09', '该用户很懒,什么也没写', '2018-01-01 20:03:00');
INSERT INTO `user` VALUES ('28', '美亚', '0', '\0', '2018-01-31', '我，美亚。没啥说的', '2018-01-19 21:03:00');
INSERT INTO `user` VALUES ('32', '路人甲', '1', '', '2018-02-07', '该用户很懒,什么也没写', '2018-01-02 03:04:00');
INSERT INTO `user` VALUES ('33', '学习Fighting', '1', '\0', '2018-02-07', '我要恋爱？！哈', '2018-02-01 22:00:03');
INSERT INTO `user` VALUES ('34', '我在写bug', '1', '', '2018-01-31', '不写咋知道是Bug', '2018-02-01 08:00:03');
INSERT INTO `user` VALUES ('35', '年轻无极限', '0', '\0', '2018-02-07', '该用户很懒,什么也没写', '2018-02-01 20:00:03');
INSERT INTO `user` VALUES ('36', 'admin', '1', '', '2018-02-06', '该用户很懒,什么也没写', '2018-02-15 05:03:00');
INSERT INTO `user` VALUES ('37', '程序媛', '0', '', '2018-02-15', '该用户很懒,什么也没写', '2018-02-15 12:12:12');
INSERT INTO `user` VALUES ('51', '宫傲', '1', '\0', '2017-06-14', '该用户很懒,什么也没写', '2018-02-15 20:54:08');
INSERT INTO `user` VALUES ('53', '伊丽莎白', '0', '', '2018-01-01', '该用户很懒,什么也没写', '2018-02-15 21:25:12');
INSERT INTO `user` VALUES ('67', 'Tom', '1', '\0', '2018-02-28', '该用户很懒,什么也没写`...', '2018-02-19 12:47:32');
INSERT INTO `user` VALUES ('68', 'Lucy', '1', '\0', '2018-02-20', '该用户很懒,什么也没写', '2018-02-19 12:48:08');
INSERT INTO `user` VALUES ('69', 'Mr. Yan', '1', '', '2018-02-15', '嗯嗯Mr.Yan就是我', '2018-02-19 12:49:57');
INSERT INTO `user` VALUES ('72', 'HelloHello', '0', '', '2018-03-01', '该用户很懒,什么也没写/', '2018-03-21 19:26:33');
SET FOREIGN_KEY_CHECKS=1;
