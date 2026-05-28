# 前端 UI 设计规范

> 本文档是项目 UI 的唯一标准。所有前端页面必须遵循。新增页面、修改样式前必须先对照本规范。

---

## 1 间距体系

基于 4px 网格，所有间距必须使用 `$space-*` 变量，禁止硬编码数值。

| 变量 | 值 | 用途 |
|------|----|------|
| `$space-1` | 4px | 图标与文字间距、紧凑内边距 |
| `$space-2` | 8px | 小元素间距、标签间距 |
| `$space-3` | 12px | 表单项间距、按钮组间距 |
| `$space-4` | 16px | 卡片默认内边距、列表项间距 |
| `$space-5` | 24px | 卡片大内边距、区块间距、页面容器间距 |
| `$space-6` | 32px | 大区块间距 |
| `$space-7` | 48px | 页面级间距 |

### 间距使用规则

- **卡片内边距**: 默认 `ds-card--pad-lg` = `$space-5`(24px)
- **卡片之间间距**: `$space-5`(24px)
- **表单项间距**: 使用 el-form 默认间距，不做额外覆盖
- **页面标题与内容**: `$space-5`(24px)，由 `.page-header` 的 `margin-bottom` 控制
- **section-head 与内容**: `$space-5`(24px)，由 `.section-head` 的 `margin-bottom` 控制
- **列表行间距**: `$space-4`(16px) 行间 + `$border-light` 分割线
- **标签组间距**: `$space-2`(8px)

---

## 2 字体与排版

### 字号

| 变量 | 值 | 用途 |
|------|----|------|
| `$font-size-xs` | 12px | 辅助信息、时间戳、标签 |
| `$font-size-sm` | 13px | 次要文字、标签页文字、元数据 |
| `$font-size-base` | 14px | 正文、输入框、表格内容 |
| `$font-size-md` | 16px | 列表项标题 |
| `$font-size-lg` | 20px | 卡片标题 (h3) |
| `$font-size-xl` | 24px | 页面标题 (page-title)、区域标题 (h2) |
| `$font-size-2xl` | 32px | 首页问候语、仪表盘数值 |

### 字重

| 变量 | 值 | 用途 |
|------|----|------|
| `$font-weight-regular` | 400 | 正文 |
| `$font-weight-medium` | 500 | 列表项标题、标签、输入框 |
| `$font-weight-semibold` | 600 | 卡片标题、选中态标签页 |
| `$font-weight-bold` | 700 | 页面标题 |

### 行高

| 变量 | 值 | 用途 |
|------|----|------|
| `$line-height-tight` | 1.4 | 标题、按钮、标签 |
| `$line-height-base` | 1.6 | 正文、表单、表格 |
| `$line-height-relaxed` | 1.75 | 长文本、摘要 |

### 字号使用规则

- 页面标题 → `.page-title` class（xl + bold）
- 卡片/区域标题 → `h2`/`.h2`（xl + semibold）或 `h3`/`.h3`（lg + semibold）
- 禁止在组件中硬编码 font-size，必须使用变量

---

## 3 圆角

| 变量 | 值 | 用途 |
|------|----|------|
| `$radius-sm` | 6px | 标签 (ds-tag)、小徽章 |
| `$radius-md` | 8px | 按钮、输入框、卡片内小组件、头像、tab按钮 |
| `$radius-lg` | 12px | 卡片 (ds-card)、上传区域、弹窗 |

### 圆角规则

- 卡片 → `$radius-lg`
- 按钮/输入框/tab按钮 → `$radius-md`
- 标签 → `$radius-sm`
- 禁止出现 4px、10px 等非标准圆角值

---

## 4 颜色

### 主色

| 用途 | 变量 | 值 |
|------|------|----|
| 主色 | `$primary-color` | #3b82f6 |
| 主色浅底 | `$primary-light` | #eef2ff |
| 主色悬停 | `$primary-hover` | #2563eb |

### 功能色

| 用途 | 变量 | 值 |
|------|------|----|
| 成功 | `$success-color` | #10b981 |
| 警告 | `$warning-color` | #f59e0b |
| 危险 | `$danger-color` | #ef4444 |

### 文字色

| 用途 | 变量 | 值 |
|------|------|----|
| 标题/强调 | `$text-primary` | #111827 |
| 正文 | `$text-regular` | #374151 |
| 次要信息 | `$text-secondary` | #6b7280 |
| 禁用/占位 | `$text-disabled` | #9ca3af |

### 背景

| 用途 | 变量 | 值 |
|------|------|----|
| 页面底色 | `$bg-base` | #f3f4f6 |
| 卡片 | `$bg-card` / `$bg-white` | #ffffff |
| 悬停/斑马纹 | `$bg-hover` | #f9fafb |
| 侧边栏 | `$bg-sidebar` | #ffffff |

### 边框

| 用途 | 变量 | 值 |
|------|------|----|
| 默认边框 | `$border-color` | #e5e7eb |
| 浅色分割 | `$border-light` | #f3f4f6 |

---

## 5 组件标准

### 5.1 卡片 (ds-card)

```
标准卡片:   ds-card ds-card--pad-lg ds-card--flat
表格卡片:   ds-card ds-card--flat（内 padding:0，表格外包一层）
特殊卡片:   ds-card ds-card--pad-lg ds-card--flat + 自定义背景渐变
```

- **所有内容卡片必须使用 `ds-card--flat`** 禁止 hover 浮起
- **所有内容卡片必须使用 `ds-card--pad-lg`** 统一 24px 内边距
- 表格卡片 `padding: 0`，分页区域自行加 `$space-3 $space-4` 内边距

### 5.2 按钮 (ds-btn)

| 场景 | class |
|------|-------|
| 主要操作 | `ds-btn ds-btn--primary` |
| 次要操作 | `ds-btn ds-btn--ghost` |
| 小按钮 | `ds-btn ds-btn--primary ds-btn--sm` |
| 图标按钮 | `ds-btn ds-btn--ghost ds-btn--icon` |

- **禁止** 使用 `el-button` 作为页面主要按钮，仅 el-table 操作列允许用 `el-button link`
- 表单提交/取消 → `ds-btn--primary` + `ds-btn--ghost`
- 页面头部"新建"按钮 → `ds-btn ds-btn--primary`
- 返回按钮 → `ds-btn ds-btn--ghost ds-btn--sm`

### 5.3 标签 (ds-tag)

| 场景 | class |
|------|-------|
| 默认(蓝色) | `ds-tag` |
| 成功/已完成 | `ds-tag ds-tag--success` |
| 进行中/警告 | `ds-tag ds-tag--warning` |
| 中性/类型 | `ds-tag ds-tag--neutral` |

- **禁止** 使用 `el-tag`，全部替换为 `ds-tag`
- 表格状态列、列表状态 → `ds-tag` + 对应变体
- 参会人名单 → `ds-tag`（默认蓝色）

### 5.4 标签切换 (tab-bar)

```
<section-head>
  <h2 class="section-head__title">标题</h2>
  <nav class="tab-bar">
    <button class="tab-bar__btn is-active">标签1</button>
    <button class="tab-bar__btn">标签2</button>
  </nav>
</section-head>
```

- **禁止** 使用 `el-tabs`，全部使用自定义 `tab-bar`
- 选中态: `is-active` class，蓝色文字 + 蓝色浅底
- 内容区使用 `v-if` 切换

### 5.5 表单

- 使用 `el-form` + `el-form-item`，label-width 统一 `100px`
- 输入框圆角: 全局已覆盖 `$radius-md`
- 表单整体放在 `ds-card ds-card--pad-lg ds-card--flat` 中
- 底部操作按钮: `ds-btn--primary` + `ds-btn--ghost`，右对齐或居中

### 5.6 分页

- 使用 `el-pagination` + `background` 属性
- 分页行样式:
  ```scss
  .pagination {
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: $space-3;
    padding: $space-3 $space-4;
    border-top: 1px solid $border-light;
  }
  ```
- 左侧显示总数文字（`$font-size-sm` + `$text-secondary`），右侧放分页器

### 5.7 表格（数据列表）

**禁止使用 el-table**，统一使用自定义列表行，视觉与首页一致，间距呼吸感更好。

#### 模板结构

```html
<div class="ds-card ds-card--pad-lg ds-card--flat">
  <!-- 表头 -->
  <div class="list-header">
    <span class="col-title">名称</span>
    <span class="col-type">类型</span>
    <span class="col-time">时间</span>
    <span class="col-status">状态</span>
    <span class="col-action">操作</span>
  </div>

  <!-- 列表行 -->
  <ul class="meeting-rows">
    <li v-for="item in list" :key="item.id" class="meeting-row" @click="handleView(item.id)">
      <div class="col-title">
        <div class="row-icon"><el-icon :size="20"><Document /></el-icon></div>
        <span class="row-name">{{ item.title }}</span>
      </div>
      <div class="col-type"><span class="ds-tag ds-tag--neutral">{{ item.type }}</span></div>
      <div class="col-time">{{ item.time }}</div>
      <div class="col-status"><span class="ds-tag ds-tag--success">{{ item.status }}</span></div>
      <div class="col-action" @click.stop>
        <button class="ds-btn ds-btn--ghost ds-btn--sm">查看</button>
        <button class="star-btn" :class="{ 'is-active': item.starred }">★</button>
      </div>
    </li>
  </ul>

  <!-- 分页 -->
  <div class="pagination">
    <span class="total-text">共 {{ total }} 条</span>
    <el-pagination background layout="prev, pager, next, sizes" :total="total"
      prev-text="上一页" next-text="下一页" />
  </div>
</div>
```

#### 列宽定义

用 CSS Grid 定义列宽，表头与行必须使用相同的 `grid-template-columns`：

```scss
// 根据实际业务调整宽度，名称列用 minmax(0, 1fr) 自适应
grid-template-columns: minmax(0, 1fr) 100px 160px 90px 140px;
gap: $space-4;
```

#### 表头样式

```scss
.list-header {
  display: grid;
  grid-template-columns: minmax(0, 1fr) 100px 160px 90px 140px;  // 与行一致
  gap: $space-4;
  align-items: center;
  padding: 0 $space-5;
  height: 44px;
  font-size: $font-size-sm;
  font-weight: $font-weight-medium;
  color: $text-secondary;
  border-bottom: 1px solid $border-color;

  @include respond-to(md) {
    display: none;  // 移动端隐藏表头
  }
}
```

#### 列表行样式

```scss
.meeting-rows {
  list-style: none;
  margin: 0;
  padding: 0;
}

.meeting-row {
  display: grid;
  grid-template-columns: minmax(0, 1fr) 100px 160px 90px 140px;  // 与表头一致
  gap: $space-4;
  align-items: center;
  padding: $space-4 $space-5;
  border-bottom: 1px solid $border-light;
  cursor: pointer;
  transition: $transition-base;

  &:last-child { border-bottom: none; }

  &:hover {
    background: $bg-hover;
    border-radius: $radius-md;
    margin: 0 (-$space-4);                      // 向外扩展实现圆角效果
    padding-left: $space-5 + $space-4;
    padding-right: $space-5 + $space-4;
  }

  @include respond-to(md) {
    grid-template-columns: 1fr;                 // 移动端单列堆叠
    gap: $space-2;
    padding: $space-4;

    &:hover { margin: 0; padding: $space-4; }   // 移动端无外扩
  }
}
```

#### 名称列（左侧图标+文字）

```scss
.col-title {
  display: flex;
  align-items: center;
  gap: $space-3;
  min-width: 0;

  .row-icon {
    width: 40px;
    height: 40px;
    flex-shrink: 0;
    border-radius: $radius-md;
    background: $primary-light;
    color: $primary-color;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .row-name {
    font-size: $font-size-base;
    font-weight: $font-weight-medium;
    color: $text-title;
    line-height: $line-height-tight;
    @include text-ellipsis;
  }
}
```

#### 时间列

```scss
.col-time {
  font-size: $font-size-sm;
  color: $text-secondary;
  white-space: nowrap;
}
```

#### 移动端标签前缀

移动端单列时，用 `::before` 显示列名前缀：

```scss
.col-type  { @include respond-to(md) { &::before { content: '类型：'; } } }
.col-time  { @include respond-to(md) { &::before { content: '时间：'; } } }
.col-status { @include respond-to(md) { &::before { content: '状态：'; } } }
```

#### 收藏/操作按钮

```scss
.star-btn {
  width: 32px;
  height: 32px;
  padding: 0;
  border: none;
  background: transparent;
  color: $text-disabled;
  cursor: pointer;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  border-radius: $radius-md;
  transition: $transition-base;

  &.is-active { color: $warning-color; }
  &:hover { background: $bg-hover; }
}
```

#### 头像组

```scss
.avatar-group {
  display: flex;
  align-items: center;

  .avatar-item {
    margin-left: -$space-2;
    border: 2px solid $bg-white;
    font-size: $font-size-xs;
    background: $primary-color;
    color: $bg-white;
    &:first-child { margin-left: 0; }
  }

  .avatar-more {
    margin-left: $space-1;
    font-size: $font-size-xs;
    color: $text-secondary;
    font-weight: $font-weight-medium;
  }
}
```

#### 分页

```scss
.pagination {
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: $space-3;
  padding: $space-4 $space-5 0;
  border-top: 1px solid $border-light;
  margin-top: $space-3;

  .total-text {
    font-size: $font-size-sm;
    color: $text-secondary;
  }
}
```

- 分页器必须配置中文: `prev-text="上一页"` `next-text="下一页"`
- Element Plus 全局已配置中文 locale（`main.ts`）

#### 筛选栏

```scss
.filter-card {
  display: grid;
  grid-template-columns: 1fr;
  gap: $space-3;
  margin-bottom: $space-5;

  @include respond-to(min-md) {
    grid-template-columns: 1fr repeat(3, minmax(120px, 160px));
    align-items: center;
  }
}
```

- 搜索框: `ds-input-wrap` > `ds-input`
- 下拉筛选: `el-select`

### 5.8 输入框/搜索

- 独立输入框: `ds-input` class
- 带图标的搜索框: `ds-input-wrap` > `ds-input`
- Element Plus 输入框: 全局已覆盖圆角为 `$radius-md`

### 5.9 页面头部

```
<div class="page-header">
  <h1 class="page-title">页面标题</h1>
  <button class="ds-btn ds-btn--primary">主操作</button>
</div>
```

- 有返回按钮时: `ds-btn ds-btn--ghost ds-btn--sm` 放在 page-title 前面

### 5.10 内容区标题行

```
<div class="section-head">
  <h2 class="section-head__title">区域标题</h2>
  <nav class="tab-bar">...</nav>
</div>
```

- 标题行下方有 `$border-light` 分割线
- 右侧放 tab-bar 或操作按钮

---

## 6 页面容器

```
<div class="xxx-page page-container">
  ...
</div>
```

- 每个页面根元素必须有 `page-container` class
- 页面特定宽度覆盖:
  ```scss
  .xxx-page.page-container {
    max-width: 90% !important;
    margin: 0 auto;
    width: 100%;
    @media (max-width: #{$bp-md - 1px}) {
      max-width: 100% !important;
    }
  }
  ```
- 所有页面统一 `max-width: 90%`，移动端 `100%`

---

## 7 禁止清单

| 禁止 | 替代 |
|------|------|
| 硬编码 `font-size: 14px` | `$font-size-base` |
| 硬编码 `margin: 16px` | `$space-4` |
| 硬编码 `border-radius: 8px` | `$radius-md` |
| 硬编码 `color: #6b7280` | `$text-secondary` |
| `el-tabs` | `section-head` + `tab-bar` |
| `el-table` | 自定义列表行（list-header + meeting-rows） |
| `el-tag` | `ds-tag` |
| `el-button` 作为主按钮 | `ds-btn` |
| `ds-card` 不加 `--flat` | `ds-card ds-card--flat` |
| `ds-card` 不加 `--pad-lg` | `ds-card ds-card--pad-lg` |
| 行内 style 写颜色/间距 | 用 class 或变量 |

---

## 8 阴影

| 变量 | 值 | 用途 |
|------|----|------|
| `$shadow-card` | 0 6px 20px rgba(0,0,0,0.06) | 卡片默认阴影 |
| `$shadow-sm` | 0 1px 3px rgba(0,0,0,0.04) | 小组件阴影 |
| `$shadow-hover` | 0 8px 24px rgba(59,130,246,0.12) | 卡片选中态阴影 |

---

## 9 动效

- 统一过渡: `$transition-base` = `all 0.2s ease-out`
- 卡片 hover: `translateY(-2px)`（仅非 flat 卡片）
- 标签/按钮 hover: 颜色变化 + 背景色变化，不做位移

---

## 10 断点

| 变量 | 值 | 用途 |
|------|----|------|
| `$bp-sm` | 576px | 小屏手机 |
| `$bp-md` | 768px | 平板竖屏 |
| `$bp-lg` | 1024px | 平板横屏/小桌面 |
| `$bp-xl` | 1280px | 大桌面 |

使用 mixin: `@include respond-to(sm)` / `@include respond-to(min-lg)` 等。
