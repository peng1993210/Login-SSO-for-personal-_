/*
 Navicat Premium Data Transfer

 Source Server         : authenticate_sys
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : authenticate_sys

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 15/11/2018 20:05:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth_account_auth
-- ----------------------------
DROP TABLE IF EXISTS `auth_account_auth`;
CREATE TABLE `auth_account_auth`  (
  `acc_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '账户唯一ID',
  `acc_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户名',
  `acc_pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户密码',
  `acc_phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账户电话',
  `acc_nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账户昵称',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`acc_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
