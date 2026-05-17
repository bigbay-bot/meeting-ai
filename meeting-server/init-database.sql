-- 会议纪要AI服务 - 数据库初始化脚本
-- 版本: 1.0.0
-- 创建时间: 2026-05-17
-- 说明: 初始化数据库表结构

-- 1. 创建数据库
CREATE DATABASE IF NOT EXISTS `meeting_ai` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `meeting_ai`;

-- 2. 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `email` varchar(100) NOT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `role` varchar(20) NOT NULL DEFAULT 'user' COMMENT '角色: admin/user',
  `department` varchar(100) DEFAULT NULL COMMENT '部门',
  `position` varchar(100) DEFAULT NULL COMMENT '职位',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态: 0-禁用,1-启用',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`),
  KEY `idx_status` (`status`),
  KEY `idx_department` (`department`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 3. 会议表
CREATE TABLE IF NOT EXISTS `meeting` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '会议ID',
  `title` varchar(200) NOT NULL COMMENT '会议标题',
  `organizer_id` bigint NOT NULL COMMENT '组织者ID',
  `type` varchar(20) NOT NULL DEFAULT 'project' COMMENT '类型: project/review/summary/other',
  `status` varchar(20) NOT NULL DEFAULT 'scheduled' COMMENT '状态: scheduled/ongoing/completed/cancelled',
  `progress_percent` int DEFAULT 0 COMMENT '生成进度百分比',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `duration` varchar(20) DEFAULT NULL COMMENT '持续时间描述',
  `location` varchar(200) DEFAULT NULL COMMENT '地点',
  `description` text COMMENT '会议描述',
  `tags` json DEFAULT NULL COMMENT '标签数组',
  `is_starred` tinyint DEFAULT 0 COMMENT '是否收藏',
  `ai_processing_status` varchar(20) DEFAULT 'pending' COMMENT 'AI处理状态: pending/processing/completed/failed',
  `estimated_remaining_time` int DEFAULT NULL COMMENT '预计剩余时间(秒)',
  `created_by` bigint NOT NULL COMMENT '创建人',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_organizer` (`organizer_id`),
  KEY `idx_type` (`type`),
  KEY `idx_status` (`status`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_created_by` (`created_by`),
  KEY `idx_ai_status` (`ai_processing_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会议表';

-- 4. 会议参与者表
CREATE TABLE IF NOT EXISTS `meeting_participant` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '参与者记录ID',
  `meeting_id` bigint NOT NULL COMMENT '会议ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role` varchar(20) DEFAULT 'participant' COMMENT '角色: organizer/participant/observer',
  `attendance_status` varchar(20) DEFAULT 'invited' COMMENT '出席状态: invited/accepted/declined/attended/absent',
  `is_responsible` tinyint DEFAULT 0 COMMENT '是否为主要负责人',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_meeting_user` (`meeting_id`, `user_id`),
  KEY `idx_user_meetings` (`user_id`, `meeting_id`),
  KEY `idx_attendance_status` (`attendance_status`),
  CONSTRAINT `fk_meeting_participant_meeting` FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_meeting_participant_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会议参与者表';

-- 5. 会议纪要表
CREATE TABLE IF NOT EXISTS `meeting_minutes` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '纪要ID',
  `meeting_id` bigint NOT NULL COMMENT '会议ID',
  `version` int NOT NULL DEFAULT 1 COMMENT '版本号',
  `content_type` varchar(20) NOT NULL DEFAULT 'structured' COMMENT '内容类型: structured/markdown/raw',
  `title` varchar(200) DEFAULT NULL COMMENT '纪要标题',
  `summary` text COMMENT '会议摘要',
  `key_decisions` json DEFAULT NULL COMMENT '关键决策',
  `action_items` json DEFAULT NULL COMMENT '行动项',
  `controversies` json DEFAULT NULL COMMENT '争议点',
  `consensus` json DEFAULT NULL COMMENT '共识点',
  `full_content` longtext COMMENT '完整纪要内容',
  `generated_by_ai` tinyint DEFAULT 1 COMMENT '是否AI生成',
  `ai_model_used` varchar(50) DEFAULT NULL COMMENT '使用的AI模型',
  `ai_prompt_version` varchar(50) DEFAULT NULL COMMENT '提示词版本',
  `generation_time` int DEFAULT NULL COMMENT '生成耗时(秒)',
  `editor_id` bigint DEFAULT NULL COMMENT '最后编辑人',
  `is_finalized` tinyint DEFAULT 0 COMMENT '是否定稿',
  `finalized_at` datetime DEFAULT NULL COMMENT '定稿时间',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_meeting_version` (`meeting_id`, `version`),
  KEY `idx_meeting` (`meeting_id`),
  KEY `idx_editor` (`editor_id`),
  KEY `idx_is_finalized` (`is_finalized`),
  CONSTRAINT `fk_meeting_minutes_meeting` FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会议纪要表';

-- 6. 待办事项表
CREATE TABLE IF NOT EXISTS `todo_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '待办ID',
  `meeting_id` bigint DEFAULT NULL COMMENT '关联的会议ID',
  `minutes_id` bigint DEFAULT NULL COMMENT '关联的纪要ID',
  `title` varchar(200) NOT NULL COMMENT '待办标题',
  `description` text COMMENT '详细描述',
  `assignee_id` bigint NOT NULL COMMENT '负责人ID',
  `assigner_id` bigint DEFAULT NULL COMMENT '分配人ID',
  `status` varchar(20) NOT NULL DEFAULT 'todo' COMMENT '状态: todo/in_progress/done/cancelled',
  `priority` varchar(20) DEFAULT 'medium' COMMENT '优先级: high/medium/low',
  `due_date` date DEFAULT NULL COMMENT '截止日期',
  `completion_percent` int DEFAULT 0 COMMENT '完成百分比',
  `tags` json DEFAULT NULL COMMENT '标签',
  `is_private` tinyint DEFAULT 0 COMMENT '是否私密',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `completed_at` datetime DEFAULT NULL COMMENT '完成时间',
  PRIMARY KEY (`id`),
  KEY `idx_meeting` (`meeting_id`),
  KEY `idx_minutes` (`minutes_id`),
  KEY `idx_assignee` (`assignee_id`),
  KEY `idx_assigner` (`assigner_id`),
  KEY `idx_status_due_date` (`status`, `due_date`),
  KEY `idx_priority` (`priority`),
  CONSTRAINT `fk_todo_item_meeting` FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_todo_item_minutes` FOREIGN KEY (`minutes_id`) REFERENCES `meeting_minutes` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_todo_item_assignee` FOREIGN KEY (`assignee_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_todo_item_assigner` FOREIGN KEY (`assigner_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='待办事项表';

-- 7. 会议源材料表
CREATE TABLE IF NOT EXISTS `meeting_source` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '源材料ID',
  `meeting_id` bigint NOT NULL COMMENT '会议ID',
  `source_type` varchar(20) NOT NULL COMMENT '源类型: audio/recording/text/transcript',
  `file_name` varchar(255) NOT NULL COMMENT '文件名',
  `file_path` varchar(500) NOT NULL COMMENT '文件存储路径',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小(字节)',
  `duration` int DEFAULT NULL COMMENT '音频时长(秒)',
  `transcript_text` longtext COMMENT '转写文本',
  `transcript_status` varchar(20) DEFAULT 'pending' COMMENT '转写状态: pending/processing/completed/failed',
  `speaker_labels` json DEFAULT NULL COMMENT '说话人标签',
  `ai_extracted_info` json DEFAULT NULL COMMENT 'AI提取的信息',
  `uploaded_by` bigint NOT NULL COMMENT '上传人',
  `uploaded_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  PRIMARY KEY (`id`),
  KEY `idx_meeting` (`meeting_id`),
  KEY `idx_source_type` (`source_type`),
  KEY `idx_transcript_status` (`transcript_status`),
  KEY `idx_uploaded_by` (`uploaded_by`),
  CONSTRAINT `fk_meeting_source_meeting` FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_meeting_source_uploader` FOREIGN KEY (`uploaded_by`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会议源材料表';

-- 8. AI分析会话表
CREATE TABLE IF NOT EXISTS `ai_analysis_session` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分析记录ID',
  `session_id` varchar(50) NOT NULL COMMENT '会话ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `analysis_type` varchar(20) NOT NULL COMMENT '分析类型: cross_meeting/comparison/trend/summary',
  `meeting_ids` json DEFAULT NULL COMMENT '关联的会议ID数组',
  `question` text NOT NULL COMMENT '分析问题',
  `answer` longtext COMMENT 'AI回答',
  `insights` json DEFAULT NULL COMMENT '洞察结果',
  `fastgpt_session_id` varchar(100) DEFAULT NULL COMMENT 'FastGPT会话ID',
  `knowledge_base_used` varchar(100) DEFAULT NULL COMMENT '使用的知识库',
  `model_used` varchar(50) DEFAULT NULL COMMENT '使用的模型',
  `status` varchar(20) NOT NULL DEFAULT 'processing' COMMENT '状态: processing/completed/failed',
  `processing_time` int DEFAULT NULL COMMENT '处理耗时(毫秒)',
  `tokens_used` int DEFAULT NULL COMMENT '使用的token数',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_session_id` (`session_id`),
  KEY `idx_user` (`user_id`),
  KEY `idx_analysis_type` (`analysis_type`),
  KEY `idx_status` (`status`),
  KEY `idx_created_at` (`created_at`),
  CONSTRAINT `fk_ai_analysis_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI分析会话表';

-- 9. 知识库条目表
CREATE TABLE IF NOT EXISTS `knowledge_entry` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '知识条目ID',
  `knowledge_base_id` varchar(50) NOT NULL COMMENT '知识库ID',
  `entry_type` varchar(20) NOT NULL COMMENT '条目类型: meeting_pattern/decision_template/action_template/topic_insight',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `content` longtext NOT NULL COMMENT '内容',
  `metadata` json DEFAULT NULL COMMENT '元数据',
  `source_meeting_ids` json DEFAULT NULL COMMENT '来源会议ID',
  `tags` json DEFAULT NULL COMMENT '标签',
  `relevance_score` int DEFAULT 100 COMMENT '相关性分数',
  `usage_count` int DEFAULT 0 COMMENT '使用次数',
  `is_active` tinyint DEFAULT 1 COMMENT '是否激活',
  `created_by` bigint DEFAULT NULL COMMENT '创建人',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_knowledge_base` (`knowledge_base_id`),
  KEY `idx_entry_type` (`entry_type`),
  KEY `idx_is_active` (`is_active`),
  KEY `idx_relevance_score` (`relevance_score`),
  KEY `idx_created_by` (`created_by`),
  CONSTRAINT `fk_knowledge_entry_creator` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='知识库条目表';

-- 10. 系统配置表
CREATE TABLE IF NOT EXISTS `system_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `config_key` varchar(100) NOT NULL COMMENT '配置键',
  `config_value` text NOT NULL COMMENT '配置值',
  `config_type` varchar(20) DEFAULT 'string' COMMENT '配置类型: string/number/boolean/json',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `module` varchar(50) DEFAULT NULL COMMENT '所属模块',
  `is_public` tinyint DEFAULT 0 COMMENT '是否公开',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_config_key` (`config_key`),
  KEY `idx_module` (`module`),
  KEY `idx_is_public` (`is_public`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';

-- 11. 插入初始系统配置
INSERT INTO `system_config` (`config_key`, `config_value`, `config_type`, `description`, `module`, `is_public`) VALUES
('system.name', '智会纪要', 'string', '系统名称', 'system', 1),
('system.version', '1.0.0', 'string', '系统版本', 'system', 1),
('ai.default_model', 'fastgpt-default', 'string', '默认AI模型', 'ai', 0),
('ai.enable_cross_analysis', 'true', 'boolean', '是否启用跨会议分析', 'ai', 0),
('file.max_upload_size', '104857600', 'number', '最大上传文件大小(字节)', 'file', 1),
('meeting.auto_generate_minutes', 'true', 'boolean', '是否自动生成纪要', 'meeting', 0),
('notification.enable_email', 'false', 'boolean', '是否启用邮件通知', 'notification', 0);

-- 12. 插入默认管理员用户 (密码: admin123)
INSERT INTO `user` (`username`, `email`, `real_name`, `role`, `department`, `position`, `status`) VALUES
('admin', 'admin@meeting-ai.com', '系统管理员', 'admin', '技术部', '系统管理员', 1),
('zhangxiaoming', 'zhangxiaoming@meeting-ai.com', '张晓明', 'user', '产品部', '产品设计师', 1);

-- 13. 创建数据库用户 (根据实际需要修改)
-- CREATE USER 'meeting_ai_user'@'%' IDENTIFIED BY 'StrongPassword123!';
-- GRANT ALL PRIVILEGES ON meeting_ai.* TO 'meeting_ai_user'@'%';
-- FLUSH PRIVILEGES;

-- 14. 完成消息
SELECT '数据库初始化完成!' AS message;