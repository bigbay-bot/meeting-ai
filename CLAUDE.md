# 会议纪要服务（Meeting Minutes AI）

## 项目愿景
自动化生成高质量会议纪要。支持语音/文本输入，通过 AI 提炼关键信息、决策项、行动项，输出结构化纪要，并支持与历史会议知识关联。

## 技术栈
- **前端**: Vue 3 + TypeScript + Vite + Pinia + Vue Router + Element Plus
- **后端**: Spring Boot 3.x + Java 17 + Spring Security + MyBatis-Plus + MySQL + Redis
- **AI 服务**: FastGPT API（自带知识集）
- **基础设施**: Docker Compose（开发环境）

## 模块划分
```
meeting-ai
├── meeting-ui          # Vue 前端
├── meeting-server      # Spring Boot 后端
│   ├── user-module     # 用户/权限
│   ├── meeting-module  # 会议管理（创建、录音、转录）
│   ├── minutes-module  # 纪要生成、编辑、导出
│   └── ai-module       # FastGPT 调用封装、提示词管理
```

## 核心数据流
1. 用户创建会议 → 上传录音/输入文本 → 后端存储
2. 后端调用语音转写服务（可选）→ 生成原始文本
3. AI 模块将原始文本 + 会议上下文 → FastGPT（关联知识集）
4. FastGPT 返回结构化 JSON → 后端解析 → 存入 minutes 表
5. 前端实时展示/编辑纪要 → 支持人工修正 → 定稿导出（PDF/Markdown）

## 开发流程
- **分支管理**: `main`（生产）、`develop`（集成）、`feature/*`、`fix/*`
- **提交规范**: Angular 提交规范（`feat:`, `fix:`, `docs:`, `refactor:`, `test:`）
- **PR 要求**: 必须关联 issue/任务，至少 1 人 review，CI 通过方可合并
- **API 先行**: 前后端约定接口后，后端先出 Swagger，前端基于 mock 开发
- **测试要求**: 后端核心 service 层必须有单元测试；前端关键组件有 vitest 测试

## 关键架构决策
- **为什么 FastGPT**: 自带知识集管理，支持工作流编排，对中文场景优化好，私有化部署成本低
- **为什么 MyBatis-Plus**: 团队熟悉，代码生成能力强，复杂 SQL 可控
- **为什么 Pinia**: Vue3 官方推荐，TypeScript 支持好，比 Vuex 更轻量
