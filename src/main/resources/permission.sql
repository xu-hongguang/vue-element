/*
 Navicat Premium Data Transfer

 Source Server         : xhgRoot
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : logistics

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 24/01/2019 11:35:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cars
-- ----------------------------
DROP TABLE IF EXISTS `cars`;
CREATE TABLE `cars`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `carNumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `driver` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cars
-- ----------------------------
INSERT INTO `cars` VALUES (1, '苏H3456', '萨达6');
INSERT INTO `cars` VALUES (2, '苏H3456', '萨达7');
INSERT INTO `cars` VALUES (3, '苏H3456', '萨达8');
INSERT INTO `cars` VALUES (4, '苏H3456', '萨达9');
INSERT INTO `cars` VALUES (5, '苏H3456', '萨达1');
INSERT INTO `cars` VALUES (6, '苏H3456', '萨达2');
INSERT INTO `cars` VALUES (7, '苏H3456', '萨达3');
INSERT INTO `cars` VALUES (8, '苏H3456', '萨达4');
INSERT INTO `cars` VALUES (9, '苏H3456', '萨达5');
INSERT INTO `cars` VALUES (10, '苏H3456', '萨达6');
INSERT INTO `cars` VALUES (11, '苏H3456', '哈哈1');
INSERT INTO `cars` VALUES (12, '苏H3456', '哈哈2');
INSERT INTO `cars` VALUES (13, '苏H3456', '哈哈3');
INSERT INTO `cars` VALUES (14, '苏H3456', '哈哈4');
INSERT INTO `cars` VALUES (15, '苏H3456', '哈哈5');
INSERT INTO `cars` VALUES (16, '苏H3456', '哈哈6');
INSERT INTO `cars` VALUES (17, '苏H3456', '哈哈7');
INSERT INTO `cars` VALUES (18, '苏H3456', '哈哈8');
INSERT INTO `cars` VALUES (19, '苏H3456', '哈哈9');
INSERT INTO `cars` VALUES (20, '苏H3456', '嘿嘿7');
INSERT INTO `cars` VALUES (21, '苏H3456', '嘿嘿8');
INSERT INTO `cars` VALUES (22, '苏H3456', '嘿嘿9');

-- ----------------------------
-- Table structure for invoice
-- ----------------------------
DROP TABLE IF EXISTS `invoice`;
CREATE TABLE `invoice`  (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime(0) NULL DEFAULT NULL,
  `invoice_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `invoice_price` double NULL DEFAULT NULL,
  `user1_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`no`) USING BTREE,
  INDEX `FKa4averh22l169p3gimjg72dog`(`user1_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1000105 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of invoice
-- ----------------------------
INSERT INTO `invoice` VALUES (1, '2018-11-10 00:00:00', '电视机8', 2180.3, 4);
INSERT INTO `invoice` VALUES (2, '2018-11-10 00:00:00', '电视机1', 2180.3, 4);
INSERT INTO `invoice` VALUES (3, '2018-11-10 00:00:00', '电视机2', 2180.3, 48);
INSERT INTO `invoice` VALUES (4, '2018-12-05 10:36:19', '冰箱', 3000, 1);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `resource` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `parientId` bigint(20) NOT NULL COMMENT '父级id',
  `isChild` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有子菜单 0 没有  1 有',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限类型  0 按钮  1 菜单',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '添加用户', 'user:addUser', NULL, 24, NULL, NULL, '0');
INSERT INTO `permission` VALUES (2, '修改用户', 'user:updateUser', NULL, 24, NULL, NULL, '0');
INSERT INTO `permission` VALUES (3, '删除用户', 'user:removeUser', NULL, 24, NULL, NULL, '0');
INSERT INTO `permission` VALUES (4, '批量删除用户', 'user:batchRemoveUser', NULL, 24, NULL, NULL, '0');
INSERT INTO `permission` VALUES (5, '查询用户列表', 'user:getUserList', NULL, 24, NULL, NULL, '0');
INSERT INTO `permission` VALUES (6, '导出用户列表Excel', 'export:exportUser1List', NULL, 24, NULL, NULL, '0');
INSERT INTO `permission` VALUES (7, '部门列表', 'department:list', NULL, 25, NULL, NULL, '0');
INSERT INTO `permission` VALUES (8, '部门删除', 'department:delete', NULL, 25, NULL, NULL, '0');
INSERT INTO `permission` VALUES (9, '部门保存', 'department:save', NULL, 25, NULL, NULL, '0');
INSERT INTO `permission` VALUES (10, '部门编辑', 'department:edit', NULL, 25, NULL, NULL, '0');
INSERT INTO `permission` VALUES (11, '员工列表', 'employee:list', NULL, 23, NULL, NULL, '0');
INSERT INTO `permission` VALUES (12, '员工删除', 'employee:delete', NULL, 23, NULL, NULL, '0');
INSERT INTO `permission` VALUES (13, '员工保存', 'employee:save', NULL, 23, NULL, NULL, '0');
INSERT INTO `permission` VALUES (14, '员工编辑', 'employee:edit', NULL, 23, NULL, NULL, '0');
INSERT INTO `permission` VALUES (15, '加载所有权限', 'sys:reloadPermission', NULL, 0, NULL, NULL, '0');
INSERT INTO `permission` VALUES (16, '导出用户列表Excel模板', 'export:exportUserExcelTemplate', NULL, 23, NULL, NULL, '0');
INSERT INTO `permission` VALUES (17, '导入用户列表Excel', 'import:importUserExcel', NULL, 23, NULL, NULL, '0');
INSERT INTO `permission` VALUES (18, '查询用户', 'user:userOne', NULL, 23, NULL, NULL, '0');
INSERT INTO `permission` VALUES (19, '系统管理', NULL, NULL, 27, '1', NULL, '1');
INSERT INTO `permission` VALUES (20, '用户管理', 'sysUser:list', NULL, 19, '0', 'sysUser', '1');
INSERT INTO `permission` VALUES (21, '角色管理', 'sysRole:list', NULL, 19, '0', 'sysRole', '1');
INSERT INTO `permission` VALUES (22, '菜单管理', 'sysMenu:list', NULL, 19, '0', 'menu', '1');
INSERT INTO `permission` VALUES (23, '客户管理', '', NULL, 27, '1', '', '1');
INSERT INTO `permission` VALUES (24, '客户信息', 'user:list', NULL, 23, '0', 'table', '1');
INSERT INTO `permission` VALUES (25, '部门管理', 'depa:list', NULL, 19, '0', 'depa', '1');
INSERT INTO `permission` VALUES (26, '字典管理', 'dict:list', NULL, 19, '0', 'dict', '1');
INSERT INTO `permission` VALUES (27, '首页', '', NULL, -1, '2', 'index.html', '1');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '系统管理员', 'sysAdmin');
INSERT INTO `role` VALUES (2, '普通用户', 'commonUser');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 2);
INSERT INTO `role_permission` VALUES (1, 1);
INSERT INTO `role_permission` VALUES (1, 3);
INSERT INTO `role_permission` VALUES (1, 4);
INSERT INTO `role_permission` VALUES (1, 5);
INSERT INTO `role_permission` VALUES (1, 6);
INSERT INTO `role_permission` VALUES (1, 7);
INSERT INTO `role_permission` VALUES (1, 8);
INSERT INTO `role_permission` VALUES (1, 9);
INSERT INTO `role_permission` VALUES (1, 10);
INSERT INTO `role_permission` VALUES (1, 11);
INSERT INTO `role_permission` VALUES (1, 12);
INSERT INTO `role_permission` VALUES (1, 13);
INSERT INTO `role_permission` VALUES (1, 14);
INSERT INTO `role_permission` VALUES (1, 15);
INSERT INTO `role_permission` VALUES (1, 16);
INSERT INTO `role_permission` VALUES (1, 17);
INSERT INTO `role_permission` VALUES (1, 18);
INSERT INTO `role_permission` VALUES (1, 19);
INSERT INTO `role_permission` VALUES (1, 20);
INSERT INTO `role_permission` VALUES (1, 21);
INSERT INTO `role_permission` VALUES (1, 22);
INSERT INTO `role_permission` VALUES (1, 23);
INSERT INTO `role_permission` VALUES (1, 24);
INSERT INTO `role_permission` VALUES (1, 25);
INSERT INTO `role_permission` VALUES (1, 26);
INSERT INTO `role_permission` VALUES (1, 27);
INSERT INTO `role_permission` VALUES (2, 15);
INSERT INTO `role_permission` VALUES (2, 27);
INSERT INTO `role_permission` VALUES (2, 5);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别, 0  女   1 男',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '状态  0 锁定  1 有效',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '64804ea5cd503875c47c468abcbb697e', '1', '1', '2018-11-08 10:31:34', '213@qq.com', '187232332');
INSERT INTO `user` VALUES (2, 'zhangsan', '3b58691d8ee2826b8f4e63f941f935f3', '0', '1', '2018-11-12 10:31:52', '312@qq.com', '12342322');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (2, 2);
INSERT INTO `user_role` VALUES (1, 1);

-- ----------------------------
-- Procedure structure for BatchInsert
-- ----------------------------
DROP PROCEDURE IF EXISTS `BatchInsert`;
delimiter ;;
CREATE PROCEDURE `BatchInsert`(IN init INT, IN loop_time INT)
BEGIN
      DECLARE Var INT;
      DECLARE ID INT;
      SET Var = 0;
      SET ID = init;
      WHILE Var < loop_time DO
          insert into invoice (date,invoice_name,invoice_price,user1_id) values (Now(), CONCAT('电视机', ID), 1000*ID, ID);
          SET ID = ID + 1;
          SET Var = Var + 1;
      END WHILE;
  END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
