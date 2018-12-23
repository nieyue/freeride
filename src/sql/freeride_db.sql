# 数据库 
#创建数据库
DROP DATABASE IF EXISTS freeride_db;
CREATE DATABASE freeride_db;

#使用数据库 
use freeride_db;


#创建角色表
CREATE TABLE role_tb(
role_id bigint(20) NOT NULL  COMMENT '角色id',
name varchar(255) COMMENT '角色名',
duty varchar(255) COMMENT '角色职责',
update_date datetime COMMENT '更新时间',
PRIMARY KEY (role_id)
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='角色表';

#创建权限表
CREATE TABLE permission_tb(
permission_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限id',
type tinyint(4) COMMENT '权限类型，默认0开放，1鉴权',
manager_name varchar(255) COMMENT '权限管理名',
name varchar(255) COMMENT '权限名',
route varchar(255) unique COMMENT '权限路由',
update_date datetime COMMENT '更新时间',
PRIMARY KEY (permission_id),
INDEX INDEX_TYPE (type) USING BTREE,
INDEX INDEX_MANAGERNAME (manager_name) USING BTREE,
INDEX INDEX_NAME (name) USING BTREE,
INDEX INDEX_ROUTE (route) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='权限表';

#创建角色权限表
CREATE TABLE role_permission_tb(
role_permission_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色权限id',
region tinyint(4) COMMENT '范围，1公共，2自身',
role_id int(11) COMMENT '角色id,外键',
update_date datetime COMMENT '更新时间',
permission_id bigint(20) COMMENT '权限id,外键',
PRIMARY KEY (role_permission_id),
UNIQUE KEY UNIQUE_ROLEID_PERMISSIONID (role_id,permission_id),
INDEX INDEX_REGION (region) USING BTREE,
INDEX INDEX_ROLEID (role_id) USING BTREE,
INDEX INDEX_PERMISSIONID (permission_id) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='角色权限表';

#创建账户表 
CREATE TABLE account_tb(
account_id bigint(20) NOT NULL  COMMENT '账户id',
phone varchar(255) COMMENT '注册手机号',
password varchar(255) COMMENT '密码',
nickname varchar(255) COMMENT '昵称',
icon varchar(255) COMMENT '图像',
sex tinyint(4) DEFAULT 0 COMMENT '性别,默认为0未知，为1男性，为2女性',
country varchar(255) COMMENT '国家,默认中国',
realname varchar(255) COMMENT '真实姓名',
email varchar(255) COMMENT 'email',
invite_code varchar(255) COMMENT '邀请码',
address varchar(255) COMMENT '收货地址',
auth tinyint(4) COMMENT '认证，0没认证，1审核中，2已认证',
identity_cards_front_img varchar(255) COMMENT '身份证正面',
identity_cards_back_img varchar(255) COMMENT '身份证反面',
driving_license_front_img varchar(255) COMMENT '驾照正面',
driving_license_back_img varchar(255) COMMENT '驾照反面',
create_date datetime COMMENT '创建时间',
login_date datetime COMMENT '登陆时间',
status tinyint DEFAULT 0 COMMENT '状态，默认0正常，1封禁，2异常',
role_id bigint(20) COMMENT '角色id外键',
master_id bigint(20) COMMENT '上级id外键',
PRIMARY KEY (account_id),
INDEX INDEX_AUTH (auth) USING BTREE,
INDEX INDEX_PHONE (phone) USING BTREE,
INDEX INDEX_REALNAME (realname) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_LOGINDATE (login_date) USING BTREE,
INDEX INDEX_ROLEID (role_id) USING BTREE,
INDEX INDEX_INVITECODE (invite_code) USING BTREE,
INDEX INDEX_MASTERID (master_id) USING BTREE,
INDEX INDEX_STATUS (status) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='账户表';

#创建积分表
CREATE TABLE integral_tb(
integral_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '积分id',
integral decimal(11,2) DEFAULT 0 COMMENT '剩余积分',
recharge decimal(11,2) DEFAULT 0 COMMENT '充值积分',
consume decimal(11,2) DEFAULT 0 COMMENT '消费积分',
base_profit decimal(11,2) DEFAULT 0 COMMENT '赠送积分',
create_date datetime COMMENT '创建时间',
update_date datetime COMMENT '更新时间',
account_id bigint(20) COMMENT '账户id外键',
PRIMARY KEY (integral_id),
INDEX INDEX_ACCOUNTID (account_id) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='积分表';

#创建激活码表
CREATE TABLE activation_code_tb(
activation_code_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '激活码id',
code varchar(255) COMMENT '激活码',
is_usered tinyint(4) COMMENT '是否使用，1未使用，2已使用',
create_date datetime COMMENT '创建时间',
update_date datetime COMMENT '更新时间',
account_id bigint(20) COMMENT '账户id外键',
PRIMARY KEY (activation_code_id),
INDEX INDEX_ACCOUNTID (account_id) USING BTREE,
INDEX INDEX_ISUSERED (is_usered) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='激活码表';

#创建行程表
CREATE TABLE trip_tb(
trip_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '行程id',
start_address varchar(255) COMMENT '出发地',
end_address varchar(255) COMMENT '目的地',
middle_address varchar(255) COMMENT '途径地',
start_date datetime COMMENT '出发时间',
is_door tinyint(4) COMMENT '是否上门接送，1是，2否',
person_number tinyint(4) COMMENT '可带人数',
unit_price decimal(11,2) COMMENT '金额/人',
create_date datetime COMMENT '创建时间',
update_date datetime COMMENT '更新时间',
account_id bigint(20) COMMENT '账户id,外键',
PRIMARY KEY (trip_id),
INDEX INDEX_ISDOOR (is_door) USING BTREE,
INDEX INDEX_ACCOUNTID (account_id) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='行程表';


#创建联系表
CREATE TABLE contact_tb(
  contact_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '联系id',
  address varchar(255)  COMMENT '联系地址',
  create_date datetime COMMENT '创建时间',
  update_date datetime COMMENT '更新时间',
  PRIMARY KEY (contact_id)
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='联系表';

#创建配置表
CREATE TABLE config_tb(
  config_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置id',
  platform_name varchar(255)  COMMENT '平台名称',
  service_phone varchar(255)  COMMENT '平台联系电话',
  service_qq varchar(255)  COMMENT '平台联系qq',
  free_number tinyint(4)  COMMENT '免费发布次数',
  create_date datetime COMMENT '创建时间',
  update_date datetime COMMENT '更新时间',
  PRIMARY KEY (config_id)
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='配置表';


#设置初始角色
INSERT IGNORE INTO role_tb (role_id,name,duty,update_date) 
VALUES (1000,'超级管理员','超级管理员',now());
INSERT IGNORE INTO role_tb (role_id,name,duty,update_date) 
VALUES (1001,'普通管理员','普通管理员',now());
INSERT IGNORE INTO role_tb (role_id,name,duty,update_date)
VALUES (1002,'车主','车主',now());
INSERT IGNORE INTO role_tb (role_id,name,duty,update_date)
VALUES (1003,'用户','用户',now());

#初始化配置
INSERT IGNORE INTO config_tb (config_id,platform_name,free_number,create_date,update_date)
VALUES (1000,'赤兔顺风车',3,now(),now());

#设置初始管理员密码MD5加密123456
INSERT IGNORE INTO account_tb (account_id,nickname,phone,email,password,create_date,login_date,role_id) 
VALUES (1000,'聂跃','1000','1000@qq.com','11874bb6149dd45428da628c9766b252',now(),now(),1000);

INSERT IGNORE INTO integral_tb (integral_id,integral,recharge,consume,base_profit,create_date,update_date,account_id)
VALUES (1000,0,0,0,0,now(),now(),1000);