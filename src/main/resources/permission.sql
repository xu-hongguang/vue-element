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

 Date: 18/01/2019 17:36:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限类型  0 按钮  1 菜单',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '添加用户', 'user:addUser', NULL, 24, NULL, '0');
INSERT INTO `permission` VALUES (2, '修改用户', 'user:updateUser', NULL, 24, NULL, '0');
INSERT INTO `permission` VALUES (3, '删除用户', 'user:removeUser', NULL, 24, NULL, '0');
INSERT INTO `permission` VALUES (4, '批量删除用户', 'user:batchRemoveUser', NULL, 24, NULL, '0');
INSERT INTO `permission` VALUES (5, '查询用户列表', 'user:getUserList', NULL, 24, NULL, '0');
INSERT INTO `permission` VALUES (6, '导出用户列表Excel', 'export:exportUser1List', NULL, 24, NULL, '0');
INSERT INTO `permission` VALUES (7, '部门列表', 'department:list', NULL, 0, NULL, '');
INSERT INTO `permission` VALUES (8, '部门删除', 'department:delete', NULL, 0, NULL, '');
INSERT INTO `permission` VALUES (9, '部门保存', 'department:save', NULL, 0, NULL, '');
INSERT INTO `permission` VALUES (10, '部门编辑', 'department:edit', NULL, 0, NULL, '');
INSERT INTO `permission` VALUES (11, '员工列表', 'employee:list', NULL, 0, NULL, '');
INSERT INTO `permission` VALUES (12, '员工删除', 'employee:delete', NULL, 0, NULL, '');
INSERT INTO `permission` VALUES (13, '员工保存', 'employee:save', NULL, 0, NULL, '');
INSERT INTO `permission` VALUES (14, '员工编辑', 'employee:edit', NULL, 0, NULL, '');
INSERT INTO `permission` VALUES (15, '加载所有权限', 'sys:reloadPermission', NULL, 0, NULL, '0');
INSERT INTO `permission` VALUES (16, '导出用户列表Excel模板', 'export:exportUserExcelTemplate', NULL, 0, NULL, '0');
INSERT INTO `permission` VALUES (17, '导入用户列表Excel', 'import:importUserExcel', NULL, 0, NULL, '0');
INSERT INTO `permission` VALUES (18, '查询用户列表', 'user:userOne', NULL, 0, NULL, '0');
INSERT INTO `permission` VALUES (19, '系统管理', NULL, NULL, 0, NULL, '1');
INSERT INTO `permission` VALUES (20, '用户管理', 'sysUser:list', NULL, 19, 'sysUser', '1');
INSERT INTO `permission` VALUES (21, '角色管理', 'sysRole:list', NULL, 19, 'sysRole', '1');
INSERT INTO `permission` VALUES (22, '菜单管理', 'sysMenu:list', NULL, 19, 'menu', '1');
INSERT INTO `permission` VALUES (23, '客户管理', '', NULL, 0, '', '1');
INSERT INTO `permission` VALUES (24, '客户信息', 'user:list', NULL, 23, 'table', '1');
INSERT INTO `permission` VALUES (25, '部门管理', 'depa:list', NULL, 19, 'depa', '1');
INSERT INTO `permission` VALUES (26, '字典管理', 'dict:list', NULL, 19, 'dict', '1');

SET FOREIGN_KEY_CHECKS = 1;
