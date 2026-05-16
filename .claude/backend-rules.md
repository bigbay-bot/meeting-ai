# 后端开发规范（Spring Boot）

## 技术版本锁定
- Spring Boot: ^3.2.x
- Java: 17
- MyBatis-Plus: ^3.5.x
- MySQL: ^8.0
- Redis: ^7.0
- Spring Security: ^6.x
- JWT: jjwt ^0.12.x

## 目录结构
```
meeting-server/
├── src/main/java/com/meeting/ai/
│   ├── config/           # 配置类（Security、Redis、WebMvc、FastGPT）
│   ├── controller/       # 控制层，仅处理 HTTP 请求/响应
│   ├── service/          # 业务层
│   │   ├── impl/         # 业务实现
│   ├── mapper/           # MyBatis-Plus Mapper 接口
│   ├── entity/           # 数据库实体（与表一一对应）
│   ├── dto/              # 数据传输对象（入参/出参）
│   │   ├── request/      # 请求 DTO
│   │   └── response/     # 响应 DTO/VO
│   ├── vo/               # 视图对象（复杂返回结构）
│   ├── enums/            # 枚举类
│   ├── exception/        # 自定义异常 + 全局异常处理器
│   ├── utils/            # 工具类
│   └── aspect/           # AOP（日志、权限校验）
└── src/main/resources/
    ├── mapper/           # XML 映射文件（复杂 SQL）
    └── prompts/          # AI 提示词模板（*.txt / *.md）
```

## 分层规范
- **Controller**: 只负责接收请求、调用 Service、返回结果。禁止写业务逻辑。
- **Service**: 处理业务逻辑、事务管理。一个 Service 只操作一个主实体，跨实体调用其他 Service。
- **Mapper**: 只做数据访问，复杂 SQL 写 XML，简单 CRUD 用 MyBatis-Plus 默认方法。
- **Entity**: 禁止直接作为接口入参或出参，必须转 DTO/VO。

## API 设计规范
- RESTful 风格：
  - GET /api/meetings — 列表/分页
  - GET /api/meetings/{id} — 详情
  - POST /api/meetings — 创建
  - PUT /api/meetings/{id} — 更新
  - DELETE /api/meetings/{id} — 删除
- 统一响应体：`Result<T>`（code、data、message、timestamp）
- 分页响应：`PageResult<T>`（records、total、page、size）
- 接口版本：通过 URL 路径 `/api/v1/...`

## 数据库规范
- 表名：小写 + 下划线（`meeting_record`、`minutes_detail`）
- 字段名：小写 + 下划线，逻辑删除字段 `deleted`（tinyint），乐观锁 `version`
- 主键：自增 bigint 或雪花 ID，统一命名 `id`
- 时间字段：`create_time`、`update_time`，自动填充
- 索引命名：`idx_表名_字段名`
- 枚举值：数据库用 tinyint，代码用枚举类映射

## 异常处理
- 统一异常处理器 `@RestControllerAdvice`
- 业务异常用 `BusinessException`（继承 RuntimeException）
- 错误码枚举：`ErrorCode`（如 MEETING_NOT_FOUND、AI_TIMEOUT）
- 禁止在 Controller/Service 中直接 `try-catch` 吞异常，统一由 AOP/全局处理器处理

## 安全规范
- JWT Token 认证，请求头 `Authorization: Bearer <token>`
- 敏感接口加 `@PreAuthorize` 权限注解
- 密码加密：BCrypt
- 接口防刷：Redis + 注解限流（如 `@RateLimit`）
- 所有接口入参必须 `@Valid` 校验

## 日志规范
- 使用 SLF4J + Logback
- Controller 层统一记录请求日志（AOP）
- 敏感信息（密码、Token）禁止打印
- 异常必须记录完整堆栈
