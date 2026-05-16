<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { Search, Plus } from '@element-plus/icons-vue'

const router = useRouter()

const searchQuery = ref('')
const statusFilter = ref('')
const dateRange = ref([])

const statusOptions = [
  { label: '全部', value: '' },
  { label: '待开始', value: 'pending' },
  { label: '进行中', value: 'ongoing' },
  { label: '待生成纪要', value: 'transcribed' },
  { label: '已完成', value: 'completed' }
]

const meetingList = ref([
  { id: '1', title: '产品需求评审会议', organizer: '张三', time: '2026-05-15 14:00', duration: '2小时', participants: 8, status: 'completed' },
  { id: '2', title: '技术架构讨论会', organizer: '李四', time: '2026-05-14 10:00', duration: '1.5小时', participants: 5, status: 'transcribed' },
  { id: '3', title: '月度总结会议', organizer: '王五', time: '2026-05-10 09:30', duration: '3小时', participants: 20, status: 'completed' },
  { id: '4', title: 'Sprint 规划会议', organizer: '赵六', time: '2026-05-08 14:00', duration: '1小时', participants: 6, status: 'pending' },
  { id: '5', title: '客户沟通会议', organizer: '张三', time: '2026-05-05 16:00', duration: '1小时', participants: 4, status: 'completed' }
])

const getStatusTag = (status: string) => {
  const map: Record<string, { type: string; label: string }> = {
    pending: { type: 'info', label: '待开始' },
    ongoing: { type: 'primary', label: '进行中' },
    transcribed: { type: 'warning', label: '待生成纪要' },
    completed: { type: 'success', label: '已完成' }
  }
  return map[status] || { type: 'info', label: status }
}

const handleCreate = () => {
  router.push('/meetings/create')
}

const handleView = (row: any) => {
  router.push(`/meetings/${row.id}`)
}

const handleGenerate = (row: any) => {
  console.log('生成纪要', row.id)
}
</script>

<template>
  <div class="meeting-list">
    <el-card shadow="never">
      <div class="filter-bar">
        <el-input
          v-model="searchQuery"
          placeholder="搜索会议标题"
          :prefix-icon="Search"
          clearable
          style="width: 240px"
        />
        <el-select v-model="statusFilter" placeholder="会议状态" clearable style="width: 160px">
          <el-option
            v-for="opt in statusOptions"
            :key="opt.value"
            :label="opt.label"
            :value="opt.value"
          />
        </el-select>
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          style="width: 260px"
        />
        <el-button type="primary" :icon="Plus" @click="handleCreate">新建会议</el-button>
      </div>

      <el-table :data="meetingList" style="width: 100%">
        <el-table-column prop="title" label="会议标题" min-width="200" />
        <el-table-column prop="organizer" label="组织者" width="120" />
        <el-table-column prop="time" label="会议时间" width="180" />
        <el-table-column prop="duration" label="时长" width="100" />
        <el-table-column prop="participants" label="参会人数" width="100" />
        <el-table-column prop="status" label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="getStatusTag(row.status).type" size="small">
              {{ getStatusTag(row.status).label }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleView(row)">查看</el-button>
            <el-button v-if="row.status === 'transcribed'" link type="success" @click="handleGenerate(row)">
              生成纪要
            </el-button>
            <el-button link type="primary">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
          background
          layout="total, prev, pager, next"
          :total="50"
          :page-size="10"
        />
      </div>
    </el-card>
  </div>
</template>

<style scoped lang="scss">
.meeting-list {
  .filter-bar {
    display: flex;
    gap: 12px;
    margin-bottom: 20px;
    flex-wrap: wrap;
  }

  .pagination {
    display: flex;
    justify-content: flex-end;
    margin-top: 20px;
  }
}
</style>
