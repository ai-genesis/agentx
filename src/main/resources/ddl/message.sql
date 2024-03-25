CREATE TABLE IF NOT EXISTS `message` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `thread_id` varchar(36) unsigned NOT NULL COMMENT 'Thread Id',
  `role` varchar(255) NOT NULL COMMENT 'Role',
  `content` text NOT NULL COMMENT 'Content',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Create Time',
  `modified_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'Modified Time',
  `is_deleted` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT 'Is Deleted, 1 true 0 false',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='message'