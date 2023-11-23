-- atp_common_folder definition

CREATE TABLE `atp_common_folder` (
  `id` varchar(64) NOT NULL COMMENT '主键id',
  `parent_id` varchar(64) DEFAULT NULL COMMENT '父文件夹id',
  `folder_name` varchar(255) DEFAULT NULL COMMENT '文件夹名称',
  `folder_type` tinyint(255) DEFAULT NULL COMMENT '文件夹类型',
  `parent_utreeid` varchar(64) DEFAULT NULL,
  `utreeid` varchar(64) DEFAULT NULL COMMENT '前端utree展示用',
  `enabled` tinyint(1) DEFAULT 1 COMMENT '是否生效(-1: 已删除, 0: 未生效, 1: 已生效)',
  `project_id` varchar(64) DEFAULT NULL COMMENT '空间ID',
  `execute_config` text DEFAULT NULL COMMENT '执行配置,Map<String,String>',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT current_timestamp() COMMENT '创建时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '修改者',
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `remark` varchar(4000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_parent_id` (`parent_id`) USING BTREE,
  KEY `index_folder_name` (`folder_name`,`folder_type`) USING BTREE,
  KEY `index_all` (`parent_id`,`folder_name`,`folder_type`) USING BTREE,
  KEY `index_utreeid` (`utreeid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='通用文件夹';


-- atp_tag_info definition

CREATE TABLE `atp_tag_info` (
  `id` varchar(64) NOT NULL COMMENT '主键id',
  `tag_id` varchar(64) DEFAULT NULL COMMENT 'tag',
  `tag_key` varchar(255) DEFAULT NULL COMMENT '标签Key值',
  `tag_value` varchar(255) DEFAULT NULL COMMENT '标签value值',
  `enabled` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `project_id` varchar(64) DEFAULT NULL COMMENT '空间id',
  `folder_id` varchar(64) DEFAULT NULL COMMENT '用例集id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `atp_tag_info_un` (`tag_id`),
  KEY `atp_tag_info_tag_id_IDX` (`tag_id`) USING BTREE,
  KEY `atp_tag_info_tag_key_IDX` (`tag_key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签明细';


-- atp_use_case definition

CREATE TABLE `atp_use_case` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `case_id` varchar(64) DEFAULT NULL COMMENT '用例id',
  `name` varchar(255) DEFAULT NULL COMMENT '用例名称',
  `folder_id` varchar(64) DEFAULT NULL COMMENT '所属用例集Id',
  `check_rule` text DEFAULT NULL COMMENT '检查规则',
  `case_type` varchar(20) DEFAULT NULL COMMENT '用例类型',
  `execute_config` text DEFAULT NULL COMMENT '执行配置,Map<String,String>',
  `enabled` tinyint(1) DEFAULT NULL COMMENT '是否生效(-1: 已删除, 0: 未生效, 1: 已生效)',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '修改时间',
  `interface_content` text DEFAULT NULL COMMENT '用例详情(具体的接口请求内容)',
  `remark` varchar(4000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UseCase_un` (`case_id`),
  KEY `UseCase_case_id_IDX` (`case_id`) USING BTREE,
  KEY `UseCase_folder_id_IDX` (`folder_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用例详情';


-- atp_use_case_instance definition

CREATE TABLE `atp_use_case_instance` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `instance_id` varchar(64) DEFAULT NULL COMMENT '实例id',
  `use_case_id` varchar(64) DEFAULT NULL COMMENT '所属用例Id',
  `execute_status` varchar(100) DEFAULT NULL COMMENT '执行状态',
  `start_user` varchar(255) DEFAULT NULL COMMENT '启动用户',
  `start_time` datetime DEFAULT current_timestamp() COMMENT '启动时间',
  `stop_time` datetime DEFAULT current_timestamp() COMMENT '停止时间',
  `bussiness_time` datetime DEFAULT current_timestamp() COMMENT '状态更新时间',
  `stop_user` varchar(255) DEFAULT NULL COMMENT '停止用户',
  PRIMARY KEY (`id`),
  UNIQUE KEY `atp_use_case_instance_un` (`instance_id`),
  KEY `atp_use_case_instance_instance_id_IDX` (`instance_id`) USING BTREE,
  KEY `atp_use_case_instance_execute_status_IDX` (`execute_status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试用例实例';


-- atp_ref_tag_use_case definition

CREATE TABLE `atp_ref_tag_use_case` (
  `id` varchar(64) NOT NULL COMMENT '主键id',
  `tag_id` varchar(64) DEFAULT NULL COMMENT '标签id',
  `case_id` varchar(64) DEFAULT NULL COMMENT '用例id',
  `project_id` varchar(64) DEFAULT NULL COMMENT '空间id',
  PRIMARY KEY (`id`),
  KEY `atp_ref_tag_case_tag_id_IDX` (`tag_id`,`project_id`) USING BTREE,
  KEY `atp_ref_tag_case_project_IDX` (`project_id`,`case_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用例与标签之间的关联关系';