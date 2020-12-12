/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : mybatis_demo

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 12/12/2020 18:55:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for g_ac_user
-- ----------------------------
DROP TABLE IF EXISTS `g_ac_user`;
CREATE TABLE `g_ac_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '邮箱地址',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of g_ac_user
-- ----------------------------
INSERT INTO `g_ac_user` VALUES (1, 'zealpane@163.com', '111111', 'admin');

-- ----------------------------
-- Table structure for g_ac_user_info
-- ----------------------------
DROP TABLE IF EXISTS `g_ac_user_info`;
CREATE TABLE `g_ac_user_info`  (
  `user_id` int(11) NOT NULL,
  `id_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '身份证号码',
  `unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '单位',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '住址',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for g_item_alarm
-- ----------------------------
DROP TABLE IF EXISTS `g_item_alarm`;
CREATE TABLE `g_item_alarm`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` int(255) NULL DEFAULT NULL COMMENT '告警类型',
  `val` double(255, 0) NULL DEFAULT NULL COMMENT '告警值',
  `device_id` int(11) NULL DEFAULT NULL COMMENT '设备id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '告警' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for g_item_data
-- ----------------------------
DROP TABLE IF EXISTS `g_item_data`;
CREATE TABLE `g_item_data`  (
  `id` bigint(20) NOT NULL,
  `temperature` double(255, 0) NULL DEFAULT NULL COMMENT '温度',
  `humidity` double(255, 0) NULL DEFAULT NULL COMMENT '湿度',
  `pm2d5` double(255, 0) NULL DEFAULT NULL COMMENT 'pm2.5',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '数据上传时间',
  `device_id` bigint(20) NULL DEFAULT NULL COMMENT '设备id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of g_item_data
-- ----------------------------
INSERT INTO `g_item_data` VALUES (1, 30, 15, 2, '2020-12-12 18:25:12', 1);

-- ----------------------------
-- Table structure for g_item_device
-- ----------------------------
DROP TABLE IF EXISTS `g_item_device`;
CREATE TABLE `g_item_device`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '设备名称',
  `des` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `uuid` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'uuid',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `data_json` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '最新数据json序列化',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '设备状态',
  `last_data_time` datetime(0) NULL DEFAULT NULL COMMENT '最后数据时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '设备' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of g_item_device
-- ----------------------------
INSERT INTO `g_item_device` VALUES (1, '设备#1', '轻量级ssm', '1111111111', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (2, '设备#2', '轻量级ssm', '1111111112', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (3, '设备#3', '轻量级ssm', '1111111113', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (4, '设备#4', '轻量级ssm', '1111111114', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (5, '设备#5', '轻量级ssm', '1111111115', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (6, '设备#6', '轻量级ssm', '1111111116', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (7, '设备#7', '轻量级ssm', '1111111117', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (8, '设备#8', '轻量级ssm', '1111111118', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (9, '设备#9', '轻量级ssm', '1111111119', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (10, '设备#10', '轻量级ssm', '1111111120', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (11, '设备#11', '轻量级ssm', '1111111121', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (12, '设备#12', '轻量级ssm', '1111111122', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (13, '设备#13', '轻量级ssm', '1111111123', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (14, '设备#14', '轻量级ssm', '1111111124', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (15, '设备#15', '轻量级ssm', '1111111125', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (16, '设备#16', '轻量级ssm', '1111111126', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (17, '设备#17', '轻量级ssm', '1111111127', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (18, '设备#18', '轻量级ssm', '1111111128', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (19, '设备#19', '轻量级ssm', '1111111129', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');
INSERT INTO `g_item_device` VALUES (20, '设备#20', '轻量级ssm', '1111111130', 1, '{\"temerature\": 12}', 1, '2019-12-08 18:04:08');

SET FOREIGN_KEY_CHECKS = 1;
