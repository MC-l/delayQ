CREATE TABLE `t_delay_task` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `biz_data` varchar(256) DEFAULT NULL COMMENT '业务数据',
  `exe_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '任务执行的时间',
  `biz_callback_url` varchar(200) NOT NULL COMMENT '业务回调URL',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_exe_time` (`exe_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='延时任务表';