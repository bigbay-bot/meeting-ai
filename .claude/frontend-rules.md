# 前端开发规范（Vue 3）

## 技术版本锁定
- Vue: ^3.4.x
- TypeScript: ^5.3.x
- Vite: ^5.x
- Pinia: ^2.1.x
- Vue Router: ^4.2.x
- Element Plus: ^2.5.x
- Axios: ^1.6.x

## 目录结构
```
src/
├── api/              # API 接口定义（按模块分子目录）
│   ├── user.ts
│   ├── meeting.ts
│   ├── minutes.ts
│   └── ai.ts
├── assets/           # 静态资源
├── components/       # 全局通用组件
│   ├── BaseButton.vue
│   └── BaseModal.vue
├── composables/      # 组合式函数
│   ├── useMeeting.ts
│   └── useMinutes.ts
├── layouts/          # 布局组件
├── router/           # 路由配置
├── stores/           # Pinia 状态管理（按模块）
│   ├── user.ts
│   ├── meeting.ts
│   └── minutes.ts
├── styles/           # 全局样式、变量、混入
├── types/            # 全局 TypeScript 类型
├── utils/            # 工具函数
│   ├── request.ts    # Axios 封装
│   └── format.ts     # 日期/文本格式化
└── views/            # 页面视图（按模块分子目录）
    ├── login/
    ├── meeting/
    └── minutes/
```

## 组件规范
- 统一使用 `<script setup lang="ts">` + 组合式 API
- 组件名：大驼峰（`MeetingList.vue`），文件名与组件名一致
- Props 定义必须带类型和默认值：
  ```ts
  interface Props {
    meetingId: string
    readonly?: boolean
  }
  const props = withDefaults(defineProps<Props>(), {
    readonly: false
  })
  ```
- Emits 明确定义事件名：
  ```ts
  const emit = defineEmits<{
    submit: [data: MinutesForm]
    cancel: []
  }>()
  ```
- 组件逻辑超过 200 行必须拆分成 composable

## API 规范
- 所有 HTTP 请求走 `utils/request.ts` 封装的 Axios 实例
- 统一响应结构：`{ code: number, data: T, message: string }`
- API 函数命名：`动词+名词`，如 `createMeeting`、`getMinutesById`
- 请求/响应类型必须定义 interface，禁止用 `any`
- 分页接口统一参数：`{ page: number, size: number, ...filters }`

## 状态管理（Pinia）
- Store 按业务模块划分，禁止一个万能 store
- Store 中只存跨组件共享的状态，局部状态用 `ref/reactive`
- Action 中处理异步请求，统一错误处理
- Getter 用于派生状态，复杂计算用 `computed` 在组件中

## 样式规范
- 使用 SCSS，变量定义在 `styles/variables.scss`
- Element Plus 主题色通过 CSS 变量覆盖，不直接写死颜色值
- 组件样式加 `scoped`，需要穿透用 `:deep()`
- 布局统一使用 flex/grid，禁止用 float

## 路由规范
- 路由配置集中管理，动态导入页面组件：`component: () => import('@/views/...')`
- 路由守卫统一处理：登录校验、权限校验、标题设置
- 路由路径使用 kebab-case：`/meeting-list`、`/minutes-detail/:id`
