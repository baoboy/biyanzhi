/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : biyanzhi

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2015-05-28 15:30:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `publisher_id` int(11) DEFAULT NULL,
  `picture_id` int(11) DEFAULT NULL,
  `comment_content` varchar(255) DEFAULT NULL,
  `comment_time` varchar(255) NOT NULL,
  `reply_someone_id` int(11) DEFAULT NULL,
  `reply_someone_name` varchar(255) DEFAULT NULL,
  `publisher_name` varchar(255) DEFAULT NULL,
  `publisher_avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for `picture`
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture` (
  `picture_id` int(11) NOT NULL AUTO_INCREMENT,
  `publisher_id` int(11) DEFAULT NULL,
  `publish_time` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `publisher_name` varchar(255) DEFAULT NULL,
  `publisher_avatar` varchar(255) DEFAULT NULL,
  `picture_image_url` varchar(255) DEFAULT NULL,
  `picture_image_height` int(11) DEFAULT NULL,
  `picture_image_width` int(11) DEFAULT NULL,
  PRIMARY KEY (`picture_id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of picture
-- ----------------------------
INSERT INTO `picture` VALUES ('80', '0', '2015-05-21 10:00', '哦呜吐了咯啦咯空军建军节', '', '', 'http://192.168.1.108:8080/biyanzhi/picture_image/2015-05-21-10-00-35JPEG', '360', '270');
INSERT INTO `picture` VALUES ('81', '0', '2015-05-21 10:00', 'all里啦咯了具体说嘿嘿', '', '', 'http://192.168.1.108:8080/biyanzhi/picture_image/2015-05-21-10-00-47.jpg', '150', '150');
INSERT INTO `picture` VALUES ('82', '0', '2015-05-21 10:00', '空军建军节', '', '', 'http://192.168.1.108:8080/biyanzhi/picture_image/2015-05-21-10-00-57.jpg', '192', '129');
INSERT INTO `picture` VALUES ('83', '0', '2015-05-21 10:01', '啦啦啦', '', '', 'http://192.168.1.108:8080/biyanzhi/picture_image/2015-05-21-10-01-20.jpg', '200', '120');
INSERT INTO `picture` VALUES ('84', '0', '2015-05-21 17:29', '我努力了了推荐', '', '', 'http://192.168.1.108:8080/biyanzhi/picture_image/2015-05-21-17-29-29.jpg', '240', '320');

-- ----------------------------
-- Table structure for `picture_score`
-- ----------------------------
DROP TABLE IF EXISTS `picture_score`;
CREATE TABLE `picture_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `picture_id` int(11) DEFAULT NULL,
  `picture_score` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of picture_score
-- ----------------------------
INSERT INTO `picture_score` VALUES ('7', '0', '75', '96');
INSERT INTO `picture_score` VALUES ('8', '0', '75', '42');
INSERT INTO `picture_score` VALUES ('9', '0', '75', '100');
INSERT INTO `picture_score` VALUES ('10', '0', '80', '88');
INSERT INTO `picture_score` VALUES ('11', '0', '80', '50');
INSERT INTO `picture_score` VALUES ('12', '0', '80', '84');
INSERT INTO `picture_score` VALUES ('13', '0', '80', '94');
INSERT INTO `picture_score` VALUES ('14', '0', '80', '48');
INSERT INTO `picture_score` VALUES ('15', '0', '80', '90');
INSERT INTO `picture_score` VALUES ('16', '0', '81', '76');
INSERT INTO `picture_score` VALUES ('17', '0', '81', '54');
INSERT INTO `picture_score` VALUES ('18', '0', '81', '86');
INSERT INTO `picture_score` VALUES ('19', '0', '81', '42');
INSERT INTO `picture_score` VALUES ('20', '0', '82', '76');
INSERT INTO `picture_score` VALUES ('21', '0', '82', '98');
INSERT INTO `picture_score` VALUES ('22', '0', '82', '56');
INSERT INTO `picture_score` VALUES ('23', '0', '82', '86');
INSERT INTO `picture_score` VALUES ('24', '0', '83', '72');
INSERT INTO `picture_score` VALUES ('25', '0', '83', '88');
INSERT INTO `picture_score` VALUES ('26', '0', '83', '52');
INSERT INTO `picture_score` VALUES ('27', '0', '83', '34');
INSERT INTO `picture_score` VALUES ('28', '0', '83', '90');
INSERT INTO `picture_score` VALUES ('29', '0', '81', '88');
INSERT INTO `picture_score` VALUES ('30', '0', '83', '96');
INSERT INTO `picture_score` VALUES ('31', '0', '80', '50');
INSERT INTO `picture_score` VALUES ('32', '0', '81', '92');
INSERT INTO `picture_score` VALUES ('33', '0', '81', '54');
INSERT INTO `picture_score` VALUES ('34', '0', '81', '48');
INSERT INTO `picture_score` VALUES ('35', '0', '84', '94');
INSERT INTO `picture_score` VALUES ('36', '0', '81', '98');
INSERT INTO `picture_score` VALUES ('37', '0', '82', '56');
INSERT INTO `picture_score` VALUES ('38', '0', '81', '38');
INSERT INTO `picture_score` VALUES ('39', '0', '81', '74');
INSERT INTO `picture_score` VALUES ('40', '0', '81', '50');
INSERT INTO `picture_score` VALUES ('41', '0', '81', '96');
INSERT INTO `picture_score` VALUES ('42', '0', '81', '36');
INSERT INTO `picture_score` VALUES ('43', '0', '81', '38');
INSERT INTO `picture_score` VALUES ('44', '0', '81', '42');
INSERT INTO `picture_score` VALUES ('45', '0', '81', '54');
INSERT INTO `picture_score` VALUES ('46', '0', '81', '30');
INSERT INTO `picture_score` VALUES ('47', '0', '81', '76');
INSERT INTO `picture_score` VALUES ('48', '0', '81', '48');
INSERT INTO `picture_score` VALUES ('49', '0', '81', '100');
INSERT INTO `picture_score` VALUES ('50', '0', '81', '36');
INSERT INTO `picture_score` VALUES ('51', '0', '81', '76');

-- ----------------------------
-- Table structure for `sms_code`
-- ----------------------------
DROP TABLE IF EXISTS `sms_code`;
CREATE TABLE `sms_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_cellphone` varchar(255) DEFAULT NULL,
  `sms_code` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1708 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sms_code
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `user_cellphone` varchar(255) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `user_gender` varchar(255) DEFAULT NULL,
  `user_birthday` varchar(255) DEFAULT NULL,
  `user_avatar` varchar(255) DEFAULT NULL,
  `user_address` varchar(255) DEFAULT NULL,
  `user_province` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=932 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('931', '五月', '18560133195', '123456', '女', '1989-5-20', 'http://192.168.1.108:8080/biyanzhi/user-avatar/2015-05-28-14-41-46.jpg', '', '');
