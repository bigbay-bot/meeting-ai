<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import {
  Search,
  Bell,
  Plus,
  Document,
  Star,
  StarFilled,
  ChatDotRound,
  ArrowRight
} from '@element-plus/icons-vue'

const router = useRouter()

const meetingTab = ref('all')
const aiTab = ref('all')
const progressPercent = ref(50)

const meetingTabs = [
  { label: '全部', name: 'all' },
  { label: '我创建的', name: 'created' },
  { label: '我参与的', name: 'joined' },
  { label: '收藏', name: 'starred' }
]

const aiTabs = [
  { label: '全部', name: 'all' },
  { label: '项目会议', name: 'project' },
  { label: '评审会议', name: 'review' },
  { label: '总结汇报', name: 'summary' }
]

const todoCategories = ref([
  { label: '我负责的', count: 5 },
  { label: '我发起的', count: 3 },
  { label: '抄送我的', count: 4 }
])

const recentMeetings = ref([
  {
    id: '1',
    title: '校园网安全专项工作会',
    time: '2026-05-15 14:00 - 16:00',
    participants: 8,
    tags: ['项目管理', '周例会'],
    status: '已完成',
    statusType: 'success',
    action: '查看纪要',
    starred: true
  },
  {
    id: '2',
    title: '技术架构讨论会',
    time: '2026-05-14 10:00 - 11:30',
    participants: 5,
    tags: ['技术讨论'],
    status: '生成中 50%',
    statusType: 'warning',
    action: '查看进度',
    starred: false
  },
  {
    id: '3',
    title: '月度总结会议',
    time: '2026-05-10 09:30 - 12:00',
    participants: 20,
    tags: ['总结汇报'],
    status: '已完成',
    statusType: 'success',
    action: '查看纪要',
    starred: false
  },
  {
    id: '4',
    title: 'Sprint 规划会议',
    time: '2026-05-08 14:00 - 15:00',
    participants: 6,
    tags: ['敏捷开发'],
    status: '已完成',
    statusType: 'success',
    action: '查看纪要',
    starred: true
  }
])

const aiPrompts = ref([
  '本次会议的关键决策有哪些？',
  '列出所有待办行动项及负责人',
  '总结会议中的争议点和共识',
  '对比上次同类会议的进展'
])

const goToMeeting = (id: string) => {
  router.push(`/meetings/${id}`)
}
</script>

<template>
  <div class="workplace ds-container">
    <!-- Header -->
    <header class="workplace__header">
      <div class="workplace__header-copy">
        <h1 class="workplace__title">上午好，张晓明 👋</h1>
        <p class="workplace__subtitle">AI 助理已为你准备好，让每一次会议都有价值</p>
      </div>
      <div class="workplace__header-actions">
        <div class="ds-input-wrap workplace__search">
          <el-icon class="text-secondary" :size="16"><Search /></el-icon>
          <input class="ds-input" type="search" placeholder="搜索会议、纪要..." />
        </div>
        <button type="button" class="ds-btn ds-btn--ghost ds-btn--icon" aria-label="通知">
          <el-badge :value="3" :max="99">
            <el-icon :size="18"><Bell /></el-icon>
          </el-badge>
        </button>
        <button type="button" class="ds-btn ds-btn--primary" @click="router.push('/meetings/create')">
          <el-icon :size="16"><Plus /></el-icon>
          新建会议
        </button>
      </div>
    </header>

    <!-- Hero：生成中 + 待办 -->
    <section class="workplace__hero">
      <article class="ds-card ds-card--pad-lg ds-card--flat gen-card">
        <p class="gen-card__label">
          <span class="gen-card__dot" />
          正在生成会议纪要
        </p>
        <h3 class="gen-card__name">校园网安全专项工作会</h3>
        <p class="text-secondary gen-card__meta">2026-05-15 14:00 · 8 人参会</p>
        <div class="gen-card__progress">
          <div class="gen-card__track">
            <span class="gen-card__bar" :style="{ width: `${progressPercent}%` }" />
          </div>
          <div class="gen-card__progress-foot">
            <span class="text-small text-secondary">生成中… 预计剩余 1 分 32 秒</span>
            <span class="gen-card__percent">{{ progressPercent }}%</span>
          </div>
        </div>
        <button type="button" class="ds-btn ds-btn--ghost ds-btn--sm">取消整理</button>
      </article>

      <article class="ds-card ds-card--pad-lg ds-card--flat todo-card">
        <div class="todo-card__head">
          <h2 class="h3 todo-card__title">
            待办事项
            <span class="todo-card__count">12</span>
          </h2>
        </div>
        <ul class="todo-card__list">
          <li v-for="item in todoCategories" :key="item.label" class="todo-card__item">
            <span>{{ item.label }}</span>
            <span class="todo-card__badge">{{ item.count }}</span>
          </li>
        </ul>
        <button type="button" class="todo-card__link">
          查看全部待办
          <el-icon :size="14"><ArrowRight /></el-icon>
        </button>
      </article>
    </section>

    <!-- 最近会议 -->
    <section class="ds-card ds-card--pad-lg ds-card--flat">
      <div class="section-head">
        <h2 class="h2 section-head__title">最近会议</h2>
        <nav class="tab-bar">
          <button
            v-for="tab in meetingTabs"
            :key="tab.name"
            type="button"
            class="tab-bar__btn"
            :class="{ 'is-active': meetingTab === tab.name }"
            @click="meetingTab = tab.name"
          >
            {{ tab.label }}
          </button>
        </nav>
      </div>

      <ul class="meeting-rows">
        <li
          v-for="item in recentMeetings"
          :key="item.id"
          class="meeting-rows__item"
          @click="goToMeeting(item.id)"
        >
          <div class="meeting-rows__icon">
            <el-icon :size="22"><Document /></el-icon>
          </div>
          <div class="meeting-rows__body">
            <div class="meeting-rows__top">
              <span class="meeting-rows__name">{{ item.title }}</span>

            </div>
            <p class="text-small text-secondary">{{ item.time }} · {{ item.participants }} 人参会</p>
            <div class="meeting-rows__tags">
              <span class="ds-tag" :class="`ds-tag--${item.statusType}`">{{ item.status }}</span>
              <span v-for="tag in item.tags" :key="tag" class="ds-tag ds-tag--neutral">{{ tag }}</span>
            </div>
          </div>
          <div class="meeting-rows__actions" @click.stop>
            <button type="button" class="ds-btn ds-btn--ghost ds-btn--sm">{{ item.action }}</button>
            <button
              type="button"
              class="meeting-rows__star"
              :class="{ 'is-active': item.starred }"
              aria-label="收藏"
            >
              <el-icon :size="18">
                <StarFilled v-if="item.starred" />
                <Star v-else />
              </el-icon>
            </button>
          </div>
        </li>
      </ul>
    </section>

    <!-- AI 复盘 -->
    <section class="ds-card ds-card--pad-lg ds-card--flat ai-panel">
      <div class="section-head">
        <h2 class="h2 section-head__title section-head__title--ai">
          <el-icon :size="20" color="#3B82F6"><ChatDotRound /></el-icon>
          AI 复盘助手
        </h2>
        <nav class="tab-bar">
          <button
            v-for="tab in aiTabs"
            :key="tab.name"
            type="button"
            class="tab-bar__btn"
            :class="{ 'is-active': aiTab === tab.name }"
            @click="aiTab = tab.name"
          >
            {{ tab.label }}
          </button>
        </nav>
      </div>

      <div class="ai-panel__prompts">
        <button
          v-for="(prompt, i) in aiPrompts"
          :key="i"
          type="button"
          class="ai-panel__chip"
        >
          {{ prompt }}
        </button>
      </div>

      <div class="ai-panel__compose">
        <input
          class="ds-input"
          type="text"
          placeholder="输入你的问题，AI 将为你解答…"
        />
        <button type="button" class="ds-btn ds-btn--primary">发送</button>
      </div>
    </section>
  </div>
</template>

<style scoped lang="scss">
.workplace {
  display: flex;
  flex-direction: column;
  gap: $space-5;
  flex: 1;
  height: 100%;
  padding-top: $space-2;
  padding-bottom: $space-6;

  &__header {
    display: flex;
    align-items: flex-start;
    justify-content: space-between;
    gap: $space-4;
    flex-wrap: wrap;
  }

  &__header-copy {
    flex: 1;
    min-width: 200px;
  }

  &__title {
    margin: 0 0 $space-2;
    font-size: $font-size-2xl;
    font-weight: $font-weight-bold;
    line-height: $line-height-tight;
    color: $text-primary;

    @media (max-width: #{$bp-md - 1px}) {
      font-size: $font-size-xl;
    }
  }

  &__subtitle {
    margin: 0;
    font-size: $font-size-md;
    font-weight: $font-weight-regular;
    line-height: $line-height-base;
    color: $text-secondary;
  }

  &__header-actions {
    display: flex;
    align-items: center;
    gap: $space-3;
    flex-wrap: wrap;
    width: 100%;

    @media (min-width: $bp-md) {
      width: auto;
    }
  }

  &__search {
    flex: 1;
    min-width: 0;

    @media (min-width: $bp-md) {
      width: 260px;
      flex: none;
    }
  }

  &__hero {
    display: grid;
    grid-template-columns: 1fr;
    gap: $space-4;
    align-items: stretch;
    flex: 1;
    min-height: 0;

    @media (min-width: $bp-lg) {
      grid-template-columns: minmax(0, 1.62fr) minmax(0, 1fr);
      gap: $space-5;
    }
  }
}

// 生成中卡片：浅蓝强调，无渐变
.gen-card {
  display: flex;
  flex-direction: column;
  height: 100%;
  min-height: 240px;
  background: linear-gradient(180deg, $primary-light 0%, $bg-white 48%) !important;

  &__label {
    display: flex;
    align-items: center;
    gap: $space-2;
    margin: 0 0 $space-4;
    font-size: $font-size-sm;
    font-weight: $font-weight-medium;
    color: $primary-color;
  }

  &__dot {
    width: 8px;
    height: 8px;
    border-radius: 50%;
    background: $primary-color;
    animation: pulse 1.6s ease-in-out infinite;
  }

  &__name {
    margin: 0 0 $space-2;
    font-size: $font-size-lg;
    font-weight: $font-weight-semibold;
    color: $text-primary;
    line-height: $line-height-tight;
  }

  &__meta {
    margin: 0 0 $space-5;
    font-size: $font-size-sm;
  }

  &__progress {
    margin-top: auto;
    margin-bottom: $space-4;

    @media (max-width: #{$bp-md - 1px}) {
      margin-top: $space-4;
    }
  }

  &__track {
    height: 8px;
    background: $border-color;
    border-radius: $radius-sm;
    overflow: hidden;
  }

  &__bar {
    display: block;
    height: 100%;
    background: $primary-color;
    border-radius: $radius-sm;
    transition: width 0.2s ease-out;
  }

  &__progress-foot {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: $space-3;
    gap: $space-4;
  }

  &__percent {
    font-size: $font-size-sm;
    font-weight: $font-weight-semibold;
    font-family: $font-family-num;
    color: $primary-color;
  }
}

.todo-card {
  display: flex;
  flex-direction: column;
  height: 100%;
  min-height: 240px;

  &__head {
    margin-bottom: $space-5;
  }

  &__title {
    margin: 0;
    display: flex;
    align-items: baseline;
    gap: $space-2;
  }

  &__count {
    font-size: $font-size-lg;
    font-weight: $font-weight-semibold;
    font-family: $font-family-num;
    color: $primary-color;
  }

  &__list {
    list-style: none;
    margin: 0;
    padding: 0;
    flex: 1;
  }

  &__item {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: $space-4 0;
    border-bottom: 1px solid $border-light;
    font-size: $font-size-base;
    color: $text-regular;
    cursor: pointer;
    transition: $transition-base;

    &:last-child {
      border-bottom: none;
    }

    &:hover {
      color: $text-primary;
    }
  }

  &__badge {
    min-width: 28px;
    height: 24px;
    padding: 0 $space-2;
    border-radius: $radius-sm;
    background: #fef3c7;
    color: $warning-color;
    font-size: $font-size-xs;
    font-weight: $font-weight-semibold;
    font-family: $font-family-num;
    display: inline-flex;
    align-items: center;
    justify-content: center;
  }

  &__link {
    margin-top: $space-4;
    padding: 0;
    border: none;
    background: none;
    font-size: $font-size-sm;
    font-weight: $font-weight-medium;
    color: $primary-color;
    cursor: pointer;
    display: inline-flex;
    align-items: center;
    gap: $space-1;
    align-self: flex-start;
    transition: $transition-base;

    &:hover {
      gap: $space-2;
    }
  }
}

.section-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: $space-3 $space-4;
  margin-bottom: $space-5;
  padding-bottom: $space-4;
  border-bottom: 1px solid $border-light;

  &__title {
    margin: 0;
    display: flex;
    align-items: center;
    gap: $space-2;

    &--ai {
      font-size: $font-size-xl;
    }
  }
}

.tab-bar {
  display: flex;
  flex-wrap: wrap;
  gap: $space-1;

  &__btn {
    padding: $space-2 $space-4;
    border: none;
    border-radius: $radius-md;
    background: transparent;
    font-family: $font-family;
    font-size: $font-size-base;
    font-weight: $font-weight-medium;
    color: $text-secondary;
    cursor: pointer;
    transition: $transition-base;
    line-height: $line-height-tight;

    &:hover {
      color: $text-primary;
      background: $bg-hover;
    }

    &.is-active {
      color: $primary-color;
      background: $primary-light;
    }
  }
}

.meeting-rows {
  list-style: none;
  margin: 0;
  padding: 0;

  &__item {
    display: flex;
    align-items: center;
    gap: $space-4;
    padding: $space-4 0;
    border-bottom: 1px solid $border-light;
    cursor: pointer;
    transition: $transition-base;

    &:first-child {
      padding-top: 0;
    }

    &:last-child {
      border-bottom: none;
      padding-bottom: 0;
    }

    &:hover {
      background: $bg-hover;
      margin: 0 (-$space-4);
      padding-left: $space-4;
      padding-right: $space-4;
      border-radius: $radius-md;
    }
  }

  &__icon {
    width: 48px;
    height: 48px;
    flex-shrink: 0;
    border-radius: $radius-md;
    background: $primary-light;
    color: $primary-color;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  &__body {
    flex: 1;
    min-width: 0;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }

  &__top {
    display: flex;
    align-items: center;
    gap: $space-3;
    margin-bottom: $space-2;
    flex-wrap: wrap;

    .ds-tag {
      align-self: center;
      flex-shrink: 0;
      line-height: 1;
    }
  }

  &__name {
    font-size: $font-size-md;
    font-weight: $font-weight-medium;
    color: $text-primary;
    line-height: 1.4;
    @include text-ellipsis;
    flex: 1;
    min-width: 120px;
    align-self: center;
  }

  &__tags {
    display: flex;
    flex-wrap: wrap;
    gap: $space-2;
    margin-top: $space-2;
    align-items: center;
  }

  &__actions {
    display: flex;
    align-items: center;
    gap: $space-3;
    flex-shrink: 0;
  }

  &__star {
    width: 36px;
    height: 36px;
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

    &.is-active {
      color: $warning-color;
    }

    &:hover {
      background: $bg-hover;
    }
  }
}

.ai-panel {
  &__prompts {
    display: grid;
    grid-template-columns: 1fr;
    gap: $space-3;
    margin-bottom: $space-5;

    @media (min-width: $bp-md) {
      grid-template-columns: repeat(2, 1fr);
    }

    @media (min-width: $bp-lg) {
      grid-template-columns: repeat(4, 1fr);
    }
  }

  &__chip {
    padding: $space-4;
    text-align: left;
    border: 1px solid $border-color;
    border-radius: $radius-lg;
    background: $bg-hover;
    font-family: $font-family;
    font-size: $font-size-sm;
    line-height: $line-height-relaxed;
    color: $text-regular;
    cursor: pointer;
    transition: $transition-base;

    &:hover {
      border-color: $primary-color;
      background: $primary-light;
      color: $primary-color;
      transform: translateY(-2px);
    }
  }

  &__compose {
    display: flex;
    gap: $space-3;
    align-items: stretch;
    flex-wrap: wrap;

    .ds-input {
      flex: 1;
      min-width: 200px;
    }

    .ds-btn--primary {
      flex-shrink: 0;
    }

    @media (max-width: #{$bp-md - 1px}) {
      flex-direction: column;

      .ds-btn--primary {
        width: 100%;
      }
    }
  }
}

@keyframes pulse {
  0%,
  100% {
    opacity: 1;
  }
  50% {
    opacity: 0.45;
  }
}

@media (max-width: #{$bp-md - 1px}) {
  .meeting-rows__item {
    flex-wrap: wrap;
  }

  .meeting-rows__actions {
    width: 100%;
    justify-content: flex-end;
    padding-left: 64px;
  }
}

@media (max-width: #{$bp-sm - 1px}) {
  .meeting-rows__actions {
    padding-left: 0;
  }
}

.workplace.ds-container {
  max-width: 80% !important;
  margin: 0 auto;
  width: 100%;

  @media (max-width: #{$bp-md - 1px}) {
    max-width: 100% !important;
  }
}
</style>
