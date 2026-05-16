# AI 与知识集集成规范（FastGPT）

## 集成架构
```
后端 ai-module
    ├── FastGPTClient          # HTTP 客户端封装
    ├── PromptTemplateLoader   # 提示词模板加载器
    ├── MinutesGenerator       # 纪要生成器（核心编排）
    ├── KnowledgeSelector      # 知识集选择器
    └── ChatSessionManager     # 会话上下文管理
```

## FastGPT API 封装规范
- 统一通过 `FastGPTClient` 调用，禁止在 Service 中直接写 HTTP 请求
- 必须实现重试机制：最多 3 次，指数退避（1s、2s、4s）
- 支持流式（SSE）和非流式两种调用模式
- 统一处理超时：连接超时 10s，读取超时 120s（纪要生成耗时长）
- API 返回异常时，转换为 `AiServiceException`，带错误码 `AI_GENERATE_ERROR`

## 提示词管理
- 提示词模板存放于 `resources/prompts/` 目录，按场景分文件：
  - `minutes_summary.txt` — 会议纪要总结
  - `action_items_extract.txt` — 行动项提取
  - `meeting_classify.txt` — 会议类型分类
- 模板使用占位符 `{{variable}}`，由 `PromptTemplateLoader` 替换
- 提示词版本变更需记录变更原因，重大调整需回归测试
- 禁止在代码中硬编码长提示词，必须走模板加载

## 知识集使用规范
- **知识集选择**: 根据会议类型/标签自动选择 FastGPT 中对应的知识库
  - 技术评审会议 → 关联技术规范知识库
  - 产品需求会议 → 关联产品文档知识库
  - 通用会议 → 不关联，仅用大模型能力
- **知识集配置**: 在 `application.yml` 中维护知识库 ID 映射，禁止代码写死
- **回退策略**: 知识库检索不到相关内容时，降级为纯模型生成，不报错
- **上下文注入**: 知识库检索结果作为 system prompt 的一部分注入，明确标记来源

## 纪要生成流程
```
1. 接收原始文本（语音转写或手动输入）
2. 文本预处理：分段、去噪、发言人标注（如已有）
3. 选择知识库（根据会议 metadata）
4. 构建 prompt = 系统指令 + 知识库上下文 + 原始文本
5. 调用 FastGPT → 返回结构化 JSON
6. JSON 后处理：校验字段完整性、日期格式统一、去重
7. 存入 minutes 表（status = GENERATED，待人工确认）
```

## 上下文与会话管理
- 单次纪要生成视为一个独立会话，不保留长期上下文
- 用户在前端对 AI 生成的纪要进行追问/修改时，复用同一会话 ID
- 会话上下文保留 24 小时，Redis 存储，过期自动清理
- 上下文长度超过模型上限时，优先保留最近的交互，丢弃早期内容

## 输出格式规范
- FastGPT 返回必须是可解析的 JSON，prompt 中明确要求：
  ```json
  {
    "title": "会议标题",
    "summary": "会议摘要",
    "participants": ["姓名"],
    "decisions": ["决策项"],
    "actionItems": [
      { "content": "事项", "owner": "负责人", "deadline": "2024-01-01" }
    ],
    "keyPoints": ["关键讨论点"]
  }
  ```
- 后端用 Jackson 解析，字段缺失时填充默认值，解析失败时标记为 `PARSE_FAILED`，保留原始文本供人工处理

## 成本控制与限流
- 单次纪要生成文本上限 2 万字，超限时分段处理
- 按用户级别设置每日调用配额（普通用户 20 次/天，VIP 100 次/天）
- 记录每次调用的 token 消耗，用于后续成本分析
