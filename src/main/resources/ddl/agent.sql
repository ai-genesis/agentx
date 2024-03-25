CREATE TABLE IF NOT EXISTS agent(
    id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    agent_id VARCHAR(36) NOT NULL COMMENT 'Agent Id',
    name VARCHAR(255) NOT NULL COMMENT 'Name',
    description VARCHAR(255) NOT NULL COMMENT 'Description',
    type VARCHAR(16) NOT NULL COMMENT 'Type',
    system_message TEXT NOT NULL COMMENT 'System Message',
    model_id VARCHAR(36) NOT NULL COMMENT 'Model Id',
    model_parameter VARCHAR(255) NOT NULL COMMENT 'Model Parameter',
    creator_id VARCHAR(36) NOT NULL COMMENT 'Creator Id',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Create Time',
    modified_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT 'Modified Time',
    is_deleted TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Is Deleted, 1 true 0 false'
) COMMENT 'agent';