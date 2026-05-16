# Design System（前端设计规范）

## 📌 项目说明
本文档用于统一项目 UI 风格、组件规范与视觉规则，确保整体设计一致性、可维护性与高级感。

---

# 🎨 1. 设计原则

## 1.1 极简主义（Minimalism）
- 去除冗余装饰
- 保留核心信息
- 避免视觉噪音

## 1.2 信息层级清晰（Hierarchy）
页面必须分层：

- 主信息（标题 / 核心内容）
- 次级信息（说明 / 功能）
- 辅助信息（提示 / 标签）

## 1.3 一致性（Consistency）
全局必须统一：

- 字体
- 颜色
- 间距
- 圆角
- 组件风格

---

# 📏 2. 间距系统（Spacing System）

统一使用 8px 基准体系：
4px / 8px / 12px / 16px / 24px / 32px / 48px


## ❌ 禁止
- 13px / 17px / 21px 等随机值

---

# 🧱 3. 布局规范

## 3.1 页面结构标准

推荐结构：
Header（导航）
Hero（主视觉）
Features（功能区）
Content（内容区）
Footer（底部）

---

## 3.2 容器宽度

```css
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 16px;
}
```

## 3.3 卡片布局（Card System）
```css
.card {
  background: #ffffff;
  border-radius: 12px;
  padding: 16px;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.06);
  transition: all 0.2s ease;
}

.card:hover {
  transform: translateY(-2px);
}
```

# 🎨 4. 颜色系统（Color System）

## 4.1 主色（Primary）
```
#3B82F6
```

## 4.2 背景色（Background）
```
#3B82F6
```

## 4.3 文字颜色
```
Primary:   #111827
Secondary: #6B7280
Disabled:  #9CA3AF
```

## 4.4 功能色
```
Success: #10B981
Warning: #F59E0B
Error:   #EF4444
Info:    #3B82F6
```

## 4.5 使用规则

主色用于按钮 / 高亮 / 交互
不超过 3 个主色体系
禁止高饱和撞色
禁止彩虹色滥用

# 5. 字体系统（Typography）

## 5.1 推荐字体
中文：PingFang SC / Noto Sans SC
英文：Inter / SF Pro

## 5.2 字体层级

```css
h1 { font-size: 32px; font-weight: 700; }
h2 { font-size: 24px; font-weight: 600; }
h3 { font-size: 20px; font-weight: 600; }
p  { font-size: 14-16px; font-weight: 400; }
small { font-size: 12px; }
```

## 5.3 行高规范
```css
1.4 - 1.8
```

# 6. 组件规范（Components）
## 6.1 按钮 Button
Primary
.btn-primary {
  background: #3B82F6;
  color: #fff;
  padding: 10px 16px;
  border-radius: 8px;
  border: none;
}
Secondary
.btn-secondary {
  background: #E5E7EB;
  color: #111827;
  padding: 10px 16px;
  border-radius: 8px;
}
Ghost
.btn-ghost {
  background: transparent;
  border: 1px solid #E5E7EB;
  padding: 10px 16px;
  border-radius: 8px;
}

## 6.2 输入框 Input

.input {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #E5E7EB;
  border-radius: 8px;
  outline: none;
}

.input:focus {
  border-color: #3B82F6;
}

## 6.3 标签 Tag
.tag {
  padding: 4px 8px;
  border-radius: 6px;
  font-size: 12px;
  background: #EEF2FF;
  color: #3B82F6;
}

# 7. 动效系统（Motion）

## 7.1 动效原则
轻
快
自然

## 7.2 标准动画
transition: all 0.2s ease-out;

## 7.3 hover 反馈

.card:hover {
  transform: translateY(-2px);
}

## 7.4 禁止动效

过度旋转
高频闪烁
大幅度缩放

# 8. 响应式设计（Responsive）

## 8.1 断点

Mobile:  <768px
Tablet:  768px - 1024px
Desktop: >1024px

## 8.2 移动端规则
单列布局
更大按钮（≥44px）
减少信息密度
优先纵向滚动

# 9. UI 禁止项
❌ 不统一字体
❌ 使用随机间距
❌ 阴影过重叠加
❌ 多种主色混用
❌ 动效过度
❌ 信息堆叠

# 10. 设计目标

本设计系统的核心目标：

清晰（Clear）
克制（Minimal）
一致（Consistent）
可扩展（Scalable）
高级感（Modern & Premium）

# 11. 维护规则

新组件必须遵循本规范
新颜色需提前定义
不允许临时样式污染系统
UI 修改需同步更新 design.md