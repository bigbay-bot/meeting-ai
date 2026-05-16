<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { Search, Plus, MoreFilled } from '@element-plus/icons-vue'

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

const meetingList = ref([
  {
    id: '1',
    title: '产品需求评审会议',
    type: '需求评审',
    time: '2026-05-15 14:00',
    avatars: ['张', '李', '王', '赵'],
    extraCount: 4,
    status: 'completed',
    statusLabel: '已完成'
  },
  {
    id: '2',
    title: '技术架构讨论会',
    type: '技术讨论',
    time: '2026-05-14 10:00',
    avatars: ['李', '王', '赵'],
    extraCount: 2,
    status: 'generating',
    statusLabel: '生成中'
  },
  {
    id: '3',
    title: '月度总结会议',
    type: '总结汇报',
    time: '2026-05-10 09:30',
    avatars: ['王', '赵', '钱', '孙', '周'],
    extraCount: 15,
    status: 'completed',
    statusLabel: '已完成'
  },
  {
    id: '4',
    title: 'Sprint 规划会议',
    type: '敏捷开发',
    time: '2026-05-08 14:00',
    avatars: ['赵', '钱', '孙'],
    extraCount: 3,
    status: 'completed',
    statusLabel: '已完成'
  },
  {
    id: '5',
    title: '客户沟通会议',
    type: '需求评审',
    time: '2026-05-05 16:00',
    avatars: ['张', '李'],
    extraCount: 2,
    status: 'pending',
    statusLabel: '待开始'
  }
])

const getStatusClass = (status: string) => {
  const map: Record<string, string> = {
    completed: 'status-done',
    generating: 'status-progress',
    pending: 'status-pending'
  }
  return map[status] || ''
}

const handleCreate = () => router.push('/meetings/create')
const handleView = (row: { id: string }) => router.push(`/meetings/${row.id}`)
</script>

<template>
  <div class="meeting-list-page page-container">
    <div class="page-header">
      <h1 class="page-title">历史会议</h1>
      <el-button type="primary" :icon="Plus" @click="handleCreate">新建会议</el-button>
    </div>

    <div class="filter-card page-card">
      <el-input
        v-model="searchQuery"
        placeholder="搜索会议名称"
        :prefix-icon="Search"
        clearable
        class="filter-search"
      />
      <el-select v-model="typeFilter" placeholder="会议类型" clearable>
        <el-option v-for="opt in typeOptions" :key="opt.value" :label="opt.label" :value="opt.value" />
      </el-select>
      <el-select v-model="statusFilter" placeholder="会议状态" clearable>
        <el-option v-for="opt in statusOptions" :key="opt.value" :label="opt.label" :value="opt.value" />
      </el-select>
      <el-select v-model="timeFilter" placeholder="时间范围" clearable>
        <el-option label="最近一周" value="week" />
        <el-option label="最近一月" value="month" />
        <el-option label="最近三月" value="quarter" />
      </el-select>
    </div>

    <div class="table-card page-card">
      <div class="table-scroll-wrap">
      <el-table :data="meetingList" style="width: 100%; min-width: 720px">
        <el-table-column prop="title" label="会议名称" min-width="220">
          <template #default="{ row }">
            <span class="meeting-name-cell">{{ row.title }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="type" label="会议类型" width="120">
          <template #default="{ row }">
            <el-tag size="small" type="info">{{ row.type }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="time" label="时间" width="160" />
        <el-table-column label="参会人" width="160">
          <template #default="{ row }">
            <div class="avatar-group">
              <el-avatar
                v-for="(name, i) in row.avatars.slice(0, 4)"
                :key="i"
                :size="28"
                class="avatar-item"
              >
                {{ name }}
              </el-avatar>
              <span v-if="row.extraCount > 0" class="avatar-more">+{{ row.extraCount }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <span class="status-text" :class="getStatusClass(row.status)">
              {{ row.statusLabel }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleView(row)">查看</el-button>
            <el-button link>
              <el-icon><MoreFilled /></el-icon>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      </div>

      <div class="pagination">
        <span class="total-text">共 50 条</span>
        <el-pagination background layout="prev, pager, next, sizes" :total="50" :page-size="10" />
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.filter-card {
  display: grid;
  grid-template-columns: 1fr;
  gap: 10px;
  padding: 14px 16px;
  margin-bottom: $spacing-md;

  @include respond-to(min-md) {
    grid-template-columns: 1fr repeat(3, minmax(120px, 160px));
    padding: 16px 20px;
    align-items: center;
  }

  .filter-search {
    width: 100%;
  }

  .el-select {
    width: 100%;
  }
}

.table-card {
  padding: 0;
  overflow: hidden;

  :deep(.el-table) {
    --el-table-border-color: #{$border-lighter};

    th.el-table__cell {
      background: #f8fafc;
    }
  }

  .meeting-name-cell {
    font-weight: $font-weight-medium;
    font-size: $font-size-base;
    color: $text-title;
    line-height: $line-height-base;
  }

  .avatar-group {
    display: flex;
    align-items: center;

    .avatar-item {
      margin-left: -8px;
      border: 2px solid #fff;
      font-size: 11px;
      background: $primary-color;
      color: #fff;

      &:first-child {
        margin-left: 0;
      }
    }

    .avatar-more {
      margin-left: 6px;
      font-size: 12px;
      color: $text-secondary;
    }
  }

  .status-text {
    font-size: 13px;
    font-weight: 500;

    &.status-done {
      color: #16a34a;
    }

    &.status-progress {
      color: #d97706;
    }

    &.status-pending {
      color: $text-secondary;
    }
  }

  .pagination {
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: 12px;
    padding: 14px 16px;
    border-top: 1px solid $border-light;

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
}
</style>
