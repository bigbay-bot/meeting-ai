# 会议纪要AI服务 - 数据库设计文档

## 概述

本数据库设计用于支持"会议纪要AI服务"项目，包含用户管理、会议管理、纪要生成、AI分析等核心功能。数据库采用MySQL 8.0+，使用InnoDB存储引擎，字符集为utf8mb4。

## 版本信息

- **版本**: 1.0.0
- **最后更新**: 2026-05-17
- **设计者**: Claude Code
- **适用环境**: 开发、测试、生产

## 数据库架构

### 数据库名称
- `meeting_ai` (开发/测试环境)
- `meeting_ai_prod` (生产环境，建议使用不同名称)

### 字符集与排序规则
- 字符集: `utf8mb4`
- 排序规则: `utf8mb4_unicode_ci`
- 支持: 完整Unicode字符集，包括Emoji

### 存储引擎
- 所有表使用 `InnoDB` 引擎
- 支持事务、行级锁、外键约束

## 数据表详细说明

### 1. 用户表 (`user`)

#### 设计目的
存储系统用户信息，包括管理员和普通用户。

#### 字段说明
| 字段名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| id | bigint | 是 | AUTO_INCREMENT | 用户ID，主键 |
| username | varchar(50) | 是 | - | 用户名，唯一 |
| email | varchar(100) | 是 | - | 邮箱，唯一 |
| phone | varchar(20) | 否 | NULL | 手机号 |
| real_name | varchar(50) | 否 | NULL | 真实姓名 |
| avatar | varchar(255) | 否 | NULL | 头像URL |
| role | varchar(20) | 是 | 'user' | 角色: admin/user |
| department | varchar(100) | 否 | NULL | 部门 |
| position | varchar(100) | 否 | NULL | 职位 |
| status | tinyint | 是 | 1 | 状态: 0-禁用,1-启用 |
| created_at | datetime | 是 | CURRENT_TIMESTAMP | 创建时间 |
| updated_at | datetime | 是 | CURRENT_TIMESTAMP | 更新时间 |

#### 索引
- 主键: `id`
- 唯一索引: `uk_username` (username), `uk_email` (email)
- 普通索引: `idx_status` (status), `idx_department` (department)

#### 业务规则
1. 用户名和邮箱必须唯一
2. 角色只有两种: admin (管理员) 和 user (普通用户)
3. 禁用状态的用户无法登录系统

### 2. 会议表 (`meeting`)

#### 设计目的
存储会议的基本信息和状态。

#### 字段说明
| 字段名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| id | bigint | 是 | AUTO_INCREMENT | 会议ID，主键 |
| title | varchar(200) | 是 | - | 会议标题 |
| organizer_id | bigint | 是 | - | 组织者ID |
| type | varchar(20) | 是 | 'project' | 类型: project/review/summary/other |
| status | varchar(20) | 是 | 'scheduled' | 状态: scheduled/ongoing/completed/cancelled |
| progress_percent | int | 否 | 0 | 生成进度百分比 |
| start_time | datetime | 是 | - | 开始时间 |
| end_time | datetime | 是 | - | 结束时间 |
| duration | varchar(20) | 否 | NULL | 持续时间描述 |
| location | varchar(200) | 否 | NULL | 地点 |
| description | text | 否 | NULL | 会议描述 |
| tags | json | 否 | NULL | 标签数组，如 ["项目管理", "周例会"] |
| is_starred | tinyint | 否 | 0 | 是否收藏 |
| ai_processing_status | varchar(20) | 否 | 'pending' | AI处理状态: pending/processing/completed/failed |
| estimated_remaining_time | int | 否 | NULL | 预计剩余时间(秒) |
| created_by | bigint | 是 | - | 创建人 |
| created_at | datetime | 是 | CURRENT_TIMESTAMP | 创建时间 |
| updated_at | datetime | 是 | CURRENT_TIMESTAMP | 更新时间 |

#### 索引
- 主键: `id`
- 外键: 无外键约束 (组织者、创建人引用user表)
- 普通索引: `idx_organizer`, `idx_type`, `idx_status`, `idx_start_time`, `idx_created_by`, `idx_ai_status`

#### 业务规则
1. 会议类型与前端aiTabs对应: project(项目会议), review(评审会议), summary(总结汇报), other(其他)
2. AI处理状态用于跟踪纪要生成进度
3. tags字段存储JSON数组，便于灵活扩展

### 3. 会议参与者表 (`meeting_participant`)

#### 设计目的
存储会议与参与者的多对多关系。

#### 字段说明
| 字段名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| id | bigint | 是 | AUTO_INCREMENT | 参与者记录ID |
| meeting_id | bigint | 是 | - | 会议ID |
| user_id | bigint | 是 | - | 用户ID |
| role | varchar(20) | 否 | 'participant' | 角色: organizer/participant/observer |
| attendance_status | varchar(20) | 否 | 'invited' | 出席状态: invited/accepted/declined/attended/absent |
| is_responsible | tinyint | 否 | 0 | 是否为主要负责人 |
| created_at | datetime | 是 | CURRENT_TIMESTAMP | 创建时间 |

#### 索引
- 主键: `id`
- 唯一索引: `uk_meeting_user` (meeting_id, user_id)
- 普通索引: `idx_user_meetings`, `idx_attendance_status`
- 外键: `fk_meeting_participant_meeting`, `fk_meeting_participant_user`

#### 业务规则
1. 同一用户不能重复参加同一会议
2. 一个会议可以有多个organizer (组织者)
3. 出席状态用于跟踪邀请和实际出席情况

### 4. 会议纪要表 (`meeting_minutes`)

#### 设计目的
存储会议生成的纪要和AI分析结果。

#### 字段说明
| 字段名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| id | bigint | 是 | AUTO_INCREMENT | 纪要ID |
| meeting_id | bigint | 是 | - | 会议ID |
| version | int | 是 | 1 | 版本号 |
| content_type | varchar(20) | 是 | 'structured' | 内容类型: structured/markdown/raw |
| title | varchar(200) | 否 | NULL | 纪要标题 |
| summary | text | 否 | NULL | 会议摘要 |
| key_decisions | json | 否 | NULL | 关键决策，JSON数组 |
| action_items | json | 否 | NULL | 行动项，JSON数组 |
| controversies | json | 否 | NULL | 争议点，JSON数组 |
| consensus | json | 否 | NULL | 共识点，JSON数组 |
| full_content | longtext | 否 | NULL | 完整纪要内容 |
| generated_by_ai | tinyint | 否 | 1 | 是否AI生成 |
| ai_model_used | varchar(50) | 否 | NULL | 使用的AI模型 |
| ai_prompt_version | varchar(50) | 否 | NULL | 提示词版本 |
| generation_time | int | 否 | NULL | 生成耗时(秒) |
| editor_id | bigint | 否 | NULL | 最后编辑人 |
| is_finalized | tinyint | 否 | 0 | 是否定稿 |
| finalized_at | datetime | 否 | NULL | 定稿时间 |
| created_at | datetime | 是 | CURRENT_TIMESTAMP | 创建时间 |
| updated_at | datetime | 是 | CURRENT_TIMESTAMP | 更新时间 |

#### 索引
- 主键: `id`
- 唯一索引: `uk_meeting_version` (meeting_id, version)
- 普通索引: `idx_meeting`, `idx_editor`, `idx_is_finalized`
- 外键: `fk_meeting_minutes_meeting`

#### 业务规则
1. 支持版本控制，同一会议可以有多个版本的纪要
2. 结构化字段(key_decisions, action_items等)便于前端展示和分析
3. 定稿后不可再修改 (业务逻辑控制)

### 5. 待办事项表 (`todo_item`)

#### 设计目的
存储从会议中提取的待办事项。

#### 字段说明
| 字段名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| id | bigint | 是 | AUTO_INCREMENT | 待办ID |
| meeting_id | bigint | 否 | NULL | 关联的会议ID |
| minutes_id | bigint | 否 | NULL | 关联的纪要ID |
| title | varchar(200) | 是 | - | 待办标题 |
| description | text | 否 | NULL | 详细描述 |
| assignee_id | bigint | 是 | - | 负责人ID |
| assigner_id | bigint | 否 | NULL | 分配人ID |
| status | varchar(20) | 是 | 'todo' | 状态: todo/in_progress/done/cancelled |
| priority | varchar(20) | 否 | 'medium' | 优先级: high/medium/low |
| due_date | date | 否 | NULL | 截止日期 |
| completion_percent | int | 否 | 0 | 完成百分比 |
| tags | json | 否 | NULL | 标签 |
| is_private | tinyint | 否 | 0 | 是否私密 |
| created_at | datetime | 是 | CURRENT_TIMESTAMP | 创建时间 |
| updated_at | datetime | 是 | CURRENT_TIMESTAMP | 更新时间 |
| completed_at | datetime | 否 | NULL | 完成时间 |

#### 索引
- 主键: `id`
- 普通索引: `idx_meeting`, `idx_minutes`, `idx_assignee`, `idx_assigner`, `idx_status_due_date`, `idx_priority`
- 外键: `fk_todo_item_meeting`, `fk_todo_item_minutes`, `fk_todo_item_assignee`, `fk_todo_item_assigner`

#### 业务规则
1. 待办可以独立存在，也可以关联到会议或纪要
2. 支持完成百分比跟踪
3. 私密待办仅对负责人和分配人可见

### 6. 会议源材料表 (`meeting_source`)

#### 设计目的
存储会议的原始材料，如录音、转写文本等。

#### 字段说明
| 字段名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| id | bigint | 是 | AUTO_INCREMENT | 源材料ID |
| meeting_id | bigint | 是 | - | 会议ID |
| source_type | varchar(20) | 是 | - | 源类型: audio/recording/text/transcript |
| file_name | varchar(255) | 是 | - | 文件名 |
| file_path | varchar(500) | 是 | - | 文件存储路径 |
| file_size | bigint | 否 | NULL | 文件大小(字节) |
| duration | int | 否 | NULL | 音频时长(秒) |
| transcript_text | longtext | 否 | NULL | 转写文本 |
| transcript_status | varchar(20) | 否 | 'pending' | 转写状态: pending/processing/completed/failed |
| speaker_labels | json | 否 | NULL | 说话人标签 |
| ai_extracted_info | json | 否 | NULL | AI提取的信息 |
| uploaded_by | bigint | 是 | - | 上传人 |
| uploaded_at | datetime | 是 | CURRENT_TIMESTAMP | 上传时间 |

#### 索引
- 主键: `id`
- 普通索引: `idx_meeting`, `idx_source_type`, `idx_transcript_status`, `idx_uploaded_by`
- 外键: `fk_meeting_source_meeting`, `fk_meeting_source_uploader`

#### 业务规则
1. 一个会议可以有多个源材料
2. 支持音频转写状态跟踪
3. speaker_labels存储说话人识别信息

### 7. AI分析会话表 (`ai_analysis_session`)

#### 设计目的
存储用户的AI分析请求和结果。

#### 字段说明
| 字段名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| id | bigint | 是 | AUTO_INCREMENT | 分析记录ID |
| session_id | varchar(50) | 是 | - | 会话ID，唯一 |
| user_id | bigint | 是 | - | 用户ID |
| analysis_type | varchar(20) | 是 | - | 分析类型: cross_meeting/comparison/trend/summary |
| meeting_ids | json | 否 | NULL | 关联的会议ID数组 |
| question | text | 是 | - | 分析问题 |
| answer | longtext | 否 | NULL | AI回答 |
| insights | json | 否 | NULL | 洞察结果 |
| fastgpt_session_id | varchar(100) | 否 | NULL | FastGPT会话ID |
| knowledge_base_used | varchar(100) | 否 | NULL | 使用的知识库 |
| model_used | varchar(50) | 否 | NULL | 使用的模型 |
| status | varchar(20) | 是 | 'processing' | 状态: processing/completed/failed |
| processing_time | int | 否 | NULL | 处理耗时(毫秒) |
| tokens_used | int | 否 | NULL | 使用的token数 |
| created_at | datetime | 是 | CURRENT_TIMESTAMP | 创建时间 |
| updated_at | datetime | 是 | CURRENT_TIMESTAMP | 更新时间 |

#### 索引
- 主键: `id`
- 唯一索引: `uk_session_id` (session_id)
- 普通索引: `idx_user`, `idx_analysis_type`, `idx_status`, `idx_created_at`
- 外键: `fk_ai_analysis_user`

#### 业务规则
- 支持跨会议分析、比较分析、趋势分析等
- 存储AI调用元数据，便于计费和监控
- session_id可用于跟踪异步处理状态

### 8. 知识库条目表 (`knowledge_entry`)

#### 设计目的
存储AI学习和积累的知识条目。

#### 字段说明
| 字段名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| id | bigint | 是 | AUTO_INCREMENT | 知识条目ID |
| knowledge_base_id | varchar(50) | 是 | - | 知识库ID |
| entry_type | varchar(20) | 是 | - | 条目类型: meeting_pattern/decision_template/action_template/topic_insight |
| title | varchar(200) | 是 | - | 标题 |
| content | longtext | 是 | - | 内容 |
| metadata | json | 否 | NULL | 元数据 |
| source_meeting_ids | json | 否 | NULL | 来源会议ID |
| tags | json | 否 | NULL | 标签 |
| relevance_score | int | 否 | 100 | 相关性分数 |
| usage_count | int | 否 | 0 | 使用次数 |
| is_active | tinyint | 否 | 1 | 是否激活 |
| created_by | bigint | 否 | NULL | 创建人 |
| created_at | datetime | 是 | CURRENT_TIMESTAMP | 创建时间 |
| updated_at | datetime | 是 | CURRENT_TIMESTAMP | 更新时间 |

#### 索引
- 主键: `id`
- 普通索引: `idx_knowledge_base`, `idx_entry_type`, `idx_is_active`, `idx_relevance_score`, `idx_created_by`
- 外键: `fk_knowledge_entry_creator`

#### 业务规则
1. 支持多种类型的知识条目
2. 相关性分数用于排序和推荐
3. 使用次数跟踪热门知识

### 9. 系统配置表 (`system_config`)

#### 设计目的
存储系统配置参数。

#### 字段说明
| 字段名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| id | bigint | 是 | AUTO_INCREMENT | 配置ID |
| config_key | varchar(100) | 是 | - | 配置键，唯一 |
| config_value | text | 是 | - | 配置值 |
| config_type | varchar(20) | 否 | 'string' | 配置类型: string/number/boolean/json |
| description | varchar(500) | 否 | NULL | 描述 |
| module | varchar(50) | 否 | NULL | 所属模块 |
| is_public | tinyint | 否 | 0 | 是否公开 |
| created_at | datetime | 是 | CURRENT_TIMESTAMP | 创建时间 |
| updated_at | datetime | 是 | CURRENT_TIMESTAMP | 更新时间 |

#### 索引
- 主键: `id`
- 唯一索引: `uk_config_key` (config_key)
- 普通索引: `idx_module`, `idx_is_public`

#### 业务规则
1. 配置键全局唯一
2. 支持多种数据类型的配置值
3. 公开配置可供前端读取

## 表关系图

```
用户(user)
    ↑ 1
    | n
会议(meeting) 1:n 参与者(meeting_participant) n:1 用户(user)
    | 1
    | n
会议纪要(meeting_minutes) 1:n 待办事项(todo_item)
    ↑ 1                      ↑ 1
    | n                      | 1
会议源材料(meeting_source)   用户(user) (assignee/assigner)
    
用户(user) 1:n AI分析(ai_analysis_session)
用户(user) 1:n 知识条目(knowledge_entry) (created_by)
```

## 初始化数据

### 默认系统配置
脚本中包含了以下默认配置：
- 系统名称和版本
- AI相关配置
- 文件上传限制
- 会议自动生成设置
- 通知设置

### 默认用户
- `admin` / `admin@meeting-ai.com` (管理员)
- `zhangxiaoming` / `zhangxiaoming@meeting-ai.com` (普通用户，对应前端"张晓明")

**注意**: 生产环境务必修改默认密码！

## 索引策略

### 主键索引
- 所有表使用自增bigint主键
- 支持高并发插入

### 唯一索引
- 业务唯一性约束: username, email, meeting+user组合等
- 防止数据重复

### 普通索引
- 高频查询字段: status, type, created_at等
- 联合索引: status+due_date等组合查询
- 外键字段自动建立索引

### JSON字段索引
- MySQL 8.0+支持JSON字段索引
- 对于频繁查询的JSON路径可考虑建立函数索引

## 性能优化建议

### 1. 分表策略
- 会议表和纪要表可考虑按时间分区
- 每月或每季度一个分区

### 2. 读写分离
- 主库处理写操作和实时查询
- 从库处理报表和分析查询

### 3. 缓存策略
- 用户信息、配置信息可使用Redis缓存
- 会议列表缓存，设置适当过期时间

### 4. 归档策略
- 完成超过6个月的会议可归档到历史表
- 归档表使用压缩存储

## 备份与恢复

### 备份策略
```sql
-- 全量备份
mysqldump -u root -p meeting_ai > meeting_ai_full_backup_$(date +%Y%m%d).sql

-- 增量备份 (配合binlog)
-- 需要开启二进制日志
```

### 恢复步骤
1. 创建空数据库
2. 执行初始化脚本
3. 导入备份数据
4. 应用增量日志

## 监控与维护

### 监控指标
- 表空间使用情况
- 索引命中率
- 慢查询日志
- 连接数监控

### 维护任务
- 每周优化表碎片
- 每月分析索引使用情况
- 定期清理过期会话数据

## 安全考虑

### 1. 权限控制
- 应用使用专用数据库用户
- 按最小权限原则授权
- 生产环境禁止root用户远程访问

### 2. 数据加密
- 敏感信息(如密码)使用bcrypt加密
- 传输层使用SSL/TLS加密

### 3. 审计日志
- 关键操作记录审计日志
- 定期检查异常访问

## 版本升级

### 升级流程
1. 备份当前数据库
2. 执行升级脚本 (ALTER TABLE语句)
3. 验证数据完整性
4. 更新应用配置

### 版本兼容性
- 保持向后兼容的字段修改
- 废弃字段标记为deprecated，一段时间后删除

## 常见问题

### Q1: 如何扩展用户角色？
A: 可以修改`user.role`字段的枚举值，或创建角色表实现RBAC。

### Q2: 如何支持更多会议类型？
A: 修改`meeting.type`字段的枚举值，前端同步更新。

### Q3: JSON字段如何查询？
A: 使用MySQL的JSON函数，如`JSON_CONTAINS(tags, '"项目管理"')`。

### Q4: 如何优化大文本字段查询？
A: 对于`longtext`字段，避免在WHERE条件中使用，必要时使用全文索引。

## 附录

### 数据库用户创建 (生产环境)
```sql
CREATE USER 'meeting_ai_app'@'%' IDENTIFIED BY 'StrongPassword123!';
GRANT SELECT, INSERT, UPDATE, DELETE ON meeting_ai.* TO 'meeting_ai_app'@'%';
FLUSH PRIVILEGES;
```

### 连接字符串示例
```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/meeting_ai?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.username=meeting_ai_app
spring.datasource.password=StrongPassword123!
```

---

**文档版本控制**
| 版本 | 日期 | 修改说明 | 修改人 |
|------|------|----------|--------|
| 1.0.0 | 2026-05-17 | 初始版本 | Claude Code |
