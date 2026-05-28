<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { Search, Plus, Document, Star, StarFilled, ArrowRight } from '@element-plus/icons-vue'

const router = useRouter()

const searchQuery = ref('')
const typeFilter = ref('')
const statusFilter = ref('')
const timeFilter = ref('')

const typeOptions = [
  { label: '全部类型', value: '' },
  { label: '需求评审', value: 'review' },
  { label: '技术讨论', value: 'tech' },
  { label: '总结汇报', value: 'summary' },
  { label: '敏捷开发', value: 'agile' }
]

const statusOptions = [
  { label: '全部状态', value: '' },
  { label: '已完成', value: 'completed' },
  { label: '生成中', value: 'generating' },
  { label: '待开始', value: 'pending' }
]

const timeOptions = [
  { label: '最近一周', value: 'week' },
  { label: '最近一月', value: 'month' },
  { label: '最近三月', value: 'quarter' }
]

const meetings = ref([
  {
    id: '1',
    title: '产品需求评审会议',
    type: '需求评审',
    time: '2026-05-15 14:00',
    avatars: ['张', '李', '王', '赵'],
    extraCount: 4,
    status: 'completed',
    statusLabel: '已完成',
    starred: true
  },
  {
    id: '2',
    title: '技术架构讨论会',
    type: '技术讨论',
    time: '2026-05-14 10:00',
    avatars: ['李', '王', '赵'],
    extraCount: 2,
    status: 'generating',
    statusLabel: '生成中',
    starred: false
  },
  {
    id: '3',
    title: '月度总结会议',
    type: '总结汇报',
    time: '2026-05-10 09:30',
    avatars: ['王', '赵', '钱', '孙', '周'],
    extraCount: 15,
    status: 'completed',
    statusLabel: '已完成',
    starred: false
  },
  {
    id: '4',
    title: 'Sprint 规划会议',
    type: '敏捷开发',
    time: '2026-05-08 14:00',
    avatars: ['赵', '钱', '孙'],
    extraCount: 3,
    status: 'completed',
    statusLabel: '已完成',
    starred: true
  },
  {
    id: '5',
    title: '客户沟通会议',
    type: '需求评审',
    time: '2026-05-05 16:00',
    avatars: ['张', '李'],
    extraCount: 2,
    status: 'pending',
    statusLabel: '待开始',
    starred: false
  }
])

const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(50)

const getStatusTagClass = (status: string) => {
  const map: Record<string, string> = {
    completed: 'ds-tag--success',
    generating: 'ds-tag--warning',
    pending: 'ds-tag--neutral'
  }
  return map[status] || ''
}

const handleCreate = () => router.push('/meetings/create')
const handleView = (id: string) => router.push(`/meetings/${id}`)
const toggleStar = (meeting: any) => {
  meeting.starred = !meeting.starred
}
</script>

<template>
  <div class="meeting-list-page page-container">
    <div class="page-header">
      <h1 class="page-title">历史会议</h1>
      <button type="button" class="ds-btn ds-btn--primary" @click="handleCreate">
        <el-icon :size="16"><Plus /></el-icon>
        新建会议
      </button>
    </div>

    <!-- 筛选栏 -->
    <div class="ds-card ds-card--pad-lg ds-card--flat filter-card">
      <div class="ds-input-wrap filter-search">
        <el-icon :size="16" class="text-secondary"><Search /></el-icon>
        <input v-model="searchQuery" class="ds-input" type="search" placeholder="搜索会议名称..." />
      </div>
      <el-select v-model="typeFilter" placeholder="会议类型" clearable>
        <el-option v-for="opt in typeOptions" :key="opt.value" :label="opt.label" :value="opt.value" />
      </el-select>
      <el-select v-model="statusFilter" placeholder="会议状态" clearable>
        <el-option v-for="opt in statusOptions" :key="opt.value" :label="opt.label" :value="opt.value" />
      </el-select>
      <el-select v-model="timeFilter" placeholder="时间范围" clearable>
        <el-option v-for="opt in timeOptions" :key="opt.value" :label="opt.label" :value="opt.value" />
      </el-select>
    </div>

    <!-- 会议列表 -->
    <div class="ds-card ds-card--pad-lg ds-card--flat">
      <!-- 表头 -->
      <div class="list-header">
        <span class="col-title">会议名称</span>
        <span class="col-type">类型</span>
        <span class="col-time">时间</span>
        <span class="col-people">参会人</span>
        <span class="col-status">状态</span>
        <span class="col-action">操作</span>
      </div>

      <!-- 列表行 -->
      <ul class="meeting-rows">
        <li
          v-for="item in meetings"
          :key="item.id"
          class="meeting-row"
          @click="handleView(item.id)"
        >
          <div class="col-title">
            <div class="row-icon">
              <el-icon :size="20"><Document /></el-icon>
            </div>
            <span class="row-name">{{ item.title }}</span>
          </div>
          <div class="col-type">
            <span class="ds-tag ds-tag--neutral">{{ item.type }}</span>
          </div>
          <div class="col-time">
            <span>{{ item.time }}</span>
          </div>
          <div class="col-people">
            <div class="avatar-group">
              <el-avatar
                v-for="(name, i) in item.avatars.slice(0, 3)"
                :key="i"
                :size="26"
                class="avatar-item"
              >
                {{ name }}
              </el-avatar>
              <span v-if="item.extraCount > 0" class="avatar-more">+{{ item.extraCount }}</span>
            </div>
          </div>
          <div class="col-status">
            <span class="ds-tag" :class="getStatusTagClass(item.status)">{{ item.statusLabel }}</span>
          </div>
          <div class="col-action" @click.stop>
            <button type="button" class="ds-btn ds-btn--ghost ds-btn--sm" @click="handleView(item.id)">
              查看
              <el-icon :size="14"><ArrowRight /></el-icon>
            </button>
            <button
              type="button"
              class="star-btn"
              :class="{ 'is-active': item.starred }"
              aria-label="收藏"
              @click="toggleStar(item)"
            >
              <el-icon :size="16">
                <StarFilled v-if="item.starred" />
                <Star v-else />
              </el-icon>
            </button>
          </div>
        </li>
      </ul>

      <!-- 分页 -->
      <div class="pagination">
        <span class="total-text">共 {{ total }} 条</span>
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          background
          layout="prev, pager, next, sizes"
          :total="total"
          :page-sizes="[10, 20, 50]"
          prev-text="上一页"
          next-text="下一页"
        />
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.filter-card {
  display: grid;
  grid-template-columns: 1fr;
  gap: $space-3;
  margin-bottom: $space-5;

  @include respond-to(min-md) {
    grid-template-columns: 1fr repeat(3, minmax(120px, 160px));
    align-items: center;
  }

  .filter-search {
    width: 100%;
  }

  .el-select {
    width: 100%;
  }
}

// ---- 表头 ----
.list-header {
  display: grid;
  grid-template-columns: minmax(0, 1fr) 100px 160px 140px 90px 140px;
  gap: $space-4;
  align-items: center;
  padding: 0 $space-5;
  height: 44px;
  font-size: $font-size-sm;
  font-weight: $font-weight-medium;
  color: $text-secondary;
  border-bottom: 1px solid $border-color;

  @include respond-to(md) {
    display: none;
  }
}

// ---- 列表行 ----
.meeting-rows {
  list-style: none;
  margin: 0;
  padding: 0;
}

.meeting-row {
  display: grid;
  grid-template-columns: minmax(0, 1fr) 100px 160px 140px 90px 140px;
  gap: $space-4;
  align-items: center;
  padding: $space-4 $space-5;
  border-bottom: 1px solid $border-light;
  cursor: pointer;
  transition: $transition-base;

  &:last-child {
    border-bottom: none;
  }

  &:hover {
    background: $bg-hover;
    border-radius: $radius-md;
    margin: 0 (-$space-4);
    padding-left: $space-5 + $space-4;
    padding-right: $space-5 + $space-4;
  }

  @include respond-to(md) {
    grid-template-columns: 1fr;
    gap: $space-2;
    padding: $space-4;

    &:hover {
      margin: 0;
      padding-left: $space-4;
      padding-right: $space-4;
    }
  }
}

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

.col-type,
.col-status {
  @include respond-to(md) {
    display: flex;
    gap: $space-2;
    align-items: center;

    &::before {
      font-size: $font-size-xs;
      color: $text-secondary;
    }
  }
}

.col-type {
  @include respond-to(md) {
    &::before { content: '类型：'; }
  }
}

.col-status {
  @include respond-to(md) {
    &::before { content: '状态：'; }
  }
}

.col-time {
  font-size: $font-size-sm;
  color: $text-secondary;
  white-space: nowrap;

  @include respond-to(md) {
    &::before { content: '时间：'; font-size: $font-size-xs; }
  }
}

.col-people {
  @include respond-to(md) {
    &::before { content: '参会人：'; font-size: $font-size-xs; color: $text-secondary; }
    display: flex;
    align-items: center;
    gap: $space-2;
  }
}

.col-action {
  display: flex;
  align-items: center;
  gap: $space-2;

  @include respond-to(md) {
    margin-top: $space-2;
  }
}

// ---- 头像组 ----
.avatar-group {
  display: flex;
  align-items: center;

  .avatar-item {
    margin-left: -$space-2;
    border: 2px solid $bg-white;
    font-size: $font-size-xs;
    background: $primary-color;
    color: $bg-white;

    &:first-child {
      margin-left: 0;
    }
  }

  .avatar-more {
    margin-left: $space-1;
    font-size: $font-size-xs;
    color: $text-secondary;
    font-weight: $font-weight-medium;
  }
}

// ---- 收藏按钮 ----
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

  &.is-active {
    color: $warning-color;
  }

  &:hover {
    background: $bg-hover;
  }
}

// ---- 分页 ----
.pagination {
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: $space-3;
  padding: $space-4 $space-5 0;
  border-top: 1px solid $border-light;
  margin-top: $space-3;

  @include respond-to(md) {
    flex-direction: column;
    align-items: stretch;

    :deep(.el-pagination) {
      justify-content: center;
      flex-wrap: wrap;
    }
  }

  .total-text {
    font-size: $font-size-sm;
    color: $text-secondary;
  }
}

.meeting-list-page.page-container {
  max-width: 90% !important;
  margin: 0 auto;
  width: 100%;

  @media (max-width: #{$bp-md - 1px}) {
    max-width: 100% !important;
  }
}
</style>
