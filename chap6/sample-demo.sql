/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50543
 Source Host           : 127.0.0.1:3306
 Source Schema         : mybatis-demo

 Target Server Type    : MySQL
 Target Server Version : 50543
 File Encoding         : 65001

 Date: 01/11/2019 21:12:09
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
INSERT INTO `g_ac_user` VALUES (1, 'zealpane@163.com', '123', '作者');

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
  `temperature` double(255, 0) NULL DEFAULT NULL,
  `humidity` double(255, 0) NULL DEFAULT NULL,
  `pm2d5` double(255, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for g_item_device
-- ----------------------------
DROP TABLE IF EXISTS `g_item_device`;
CREATE TABLE `g_item_device`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '设备名称',
  `des` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `uuid` varchar(0) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'uuid',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `data_json` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '最新数据json序列化',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '设备' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
