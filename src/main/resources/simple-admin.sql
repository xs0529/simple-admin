/*
 Navicat Premium Data Transfer

 Source Server         : 本地5.7
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3307
 Source Schema         : simple-admin

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 05/06/2019 00:03:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_authorities
-- ----------------------------
DROP TABLE IF EXISTS `sys_authorities`;
CREATE TABLE `sys_authorities`  (
  `authority` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '授权标识',
  `authority_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `parent_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序号',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'url',
  PRIMARY KEY (`authority`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_authorities
-- ----------------------------
INSERT INTO `sys_authorities` VALUES ('delete:/v1/authorities/role', '移除角色权限', '权限管理', 3, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('delete:/v1/menu/{id}', '删除菜单', '菜单管理', 8, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('delete:/v1/role/{id}', '删除角色', '角色管理', 12, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('delete:/v1/user/{id}', '删除用户', '用户管理', 22, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('get:/v1/authorities', '查询所有权限', '权限管理', 1, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('get:/v1/menu', '查询所有菜单', '菜单管理', 5, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('get:/v1/role', '查询所有角色', '角色管理', 9, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('get:/v1/user', '查询所有用户', '用户管理', 13, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('get:/v1/user/info', '获取个人信息', '个人信息', 16, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('get:/v1/user/menu', '获取所有菜单', '个人信息', 18, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('post:/v1/authorities/role', '给角色添加权限', '权限管理', 2, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('post:/v1/authorities/sync', '同步权限', '权限管理', 4, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('post:/v1/menu', '添加菜单', '菜单管理', 6, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('post:/v1/role', '添加角色', '角色管理', 10, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('post:/v1/user', '添加用户', '用户管理', 14, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('post:/v1/user/login', '用户登录', '个人信息', 17, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('put:/v1/menu', '修改菜单', '菜单管理', 7, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('put:/v1/role', '修改角色', '角色管理', 11, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('put:/v1/user', '修改用户', '用户管理', 15, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('put:/v1/user/psw', '修改自己密码', '用户管理', 19, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('put:/v1/user/psw/{id}', '重置密码', '用户管理', 20, '2019-03-29 13:07:48', NULL);
INSERT INTO `sys_authorities` VALUES ('put:/v1/user/state', '修改用户状态', '用户管理', 21, '2019-03-29 13:07:48', NULL);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL COMMENT '菜单id',
  `parent_id` bigint(20) NOT NULL DEFAULT -1 COMMENT '父级id',
  `menu_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `menu_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单url',
  `menu_icon` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `sort_number` int(11) NOT NULL DEFAULT 0 COMMENT '排序号',
  `authority` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对应权限',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, -1, '系统管理', '', 'layui-icon layui-icon-set', 1, '', '2019-03-26 13:19:08', '2019-03-26 16:18:08');
INSERT INTO `sys_menu` VALUES (2, 1, '用户管理', '#/system/user', '', 3, 'get:/v1/user', '2019-03-26 16:21:17', '2019-03-29 13:59:13');
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', '#/system/role', '', 4, 'get:/v1/role', '2019-03-26 16:22:03', '2019-03-29 13:59:13');
INSERT INTO `sys_menu` VALUES (4, 1, '权限管理', '#/system/authorities', '', 5, 'get:/v1/authorities', '2019-03-26 16:22:28', '2019-03-29 13:59:14');
INSERT INTO `sys_menu` VALUES (5, 1, '菜单管理', '#/system/menu', '', 6, 'get:/v1/menu', '2019-03-26 16:28:14', '2019-03-29 13:59:14');
INSERT INTO `sys_menu` VALUES (6, -1, '系统功能', '', 'layui-icon layui-icon-engine', 7, '', '2019-03-28 16:44:19', '2019-03-29 13:59:15');
INSERT INTO `sys_menu` VALUES (7, 6, 'Druid监控', '#/tpl/iframe/id=druid', '', 8, '', '2019-03-28 16:45:34', '2019-03-29 13:59:19');
INSERT INTO `sys_menu` VALUES (8, 6, '接口文档', '#/tpl/iframe/id=swagger', '', 9, '', '2019-03-28 16:47:56', '2019-03-29 13:59:20');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色id',
  `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `comments` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', '管理员', '2018-12-19 23:11:29', '2018-12-19 23:11:29');
INSERT INTO `sys_role` VALUES (2, '普通用户', '普通用户', '2018-12-19 23:12:09', '2018-12-19 23:12:09');

-- ----------------------------
-- Table structure for sys_role_authorities
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_authorities`;
CREATE TABLE `sys_role_authorities`  (
  `id` bigint(20) UNSIGNED NOT NULL,
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色id',
  `authority` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_sys_role_permission_pm`(`authority`) USING BTREE,
  INDEX `FK_sys_role_permission_role`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户id',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '男' COMMENT '性别',
  `phone` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `email_verified` int(1) NULL DEFAULT 0 COMMENT '邮箱是否验证，0未验证，1已验证',
  `true_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `id_card` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `department_id` int(11) NULL DEFAULT NULL COMMENT '部门id',
  `state` int(1) NOT NULL DEFAULT 0 COMMENT '状态，0正常，1冻结',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '注册时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_account`(`username`) USING BTREE,
  INDEX `FK_sys_user`(`true_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', '管理员', NULL, '男', '12345678901', NULL, 0, NULL, NULL, NULL, NULL, 0, '2018-12-19 23:30:05', '2019-03-29 13:06:48');
INSERT INTO `sys_user` VALUES (2, 'user01', 'e10adc3949ba59abbe56e057f20f883e', '用户01', NULL, '男', '12345678901', NULL, 0, NULL, NULL, NULL, NULL, 0, '2018-12-19 23:31:25', '2019-03-29 13:54:53');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户id',
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色id',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_sys_user_role`(`user_id`) USING BTREE,
  INDEX `FK_sys_user_role_role`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1, '2018-12-19 23:30:06');
INSERT INTO `sys_user_role` VALUES (2, 2, 2, '2019-03-29 13:13:35');

SET FOREIGN_KEY_CHECKS = 1;
