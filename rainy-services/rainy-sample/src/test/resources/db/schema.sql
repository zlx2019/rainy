DROP TABLE IF EXISTS `sample`;
CREATE TABLE `sample`
(
    `id` bigint NOT NULL primary key COMMENT 'ID',
    `name` varchar(255) DEFAULT NULL,
    `age` int DEFAULT NULL,
    `version` tinyint DEFAULT '1' COMMENT '乐观锁版本号',
    `status` tinyint DEFAULT NULL COMMENT '状态',
    `deleted` bit(1) DEFAULT '0' COMMENT '逻辑删除',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间'
);