<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { DocumentCopy, Timer, Check, Warning } from '@element-plus/icons-vue'

const router = useRouter()

const greeting = ref('下午好，张晓明')
const dateStr = ref('2026年05月16日 星期五')

const stats = ref([
  { label: '本周会议', value: 12, icon: DocumentCopy, color: '#4f46e5' },
  { label: '待处理纪要', value: 5, icon: Timer, color: '#f59e0b' },
  { label: '已完成纪要', value: 28, icon: Check, color: '#10b981' },
  { label: '待确认任务', value: 3, icon: Warning, color: '#ef4444' }
])

const recentMeetings = ref([
  { id: '1', title: '产品需求评审会议', time: '2026-05-15 14:00', status: '已完成', type: '需求评审' },
  { id: '2', title: '技术架构讨论会', time: '2026-05-14 10:00', status: '待生成纪要', type: '技术讨论' },
  { id: '3', title: '月度总结会议', time: '2026-05-10 09:30', status: '已完成', type: '总结汇报' },
  { id: '4', title: 'Sprint 规划会议', time: '2026-05-08 14:00', status: '待确认', type: '敏捷开发' }
])

const todoList = ref([
  { id: '1', title: '确认技术架构讨论会议纪要', priority: 'high', deadline: '2026-05-16' },
  { id: '2', title: '补充产品需求评审的决策项', priority: 'medium', deadline: '2026-05-17' },
  { id: '3', title: '分配Sprint规划会议行动项负责人', priority: 'high', deadline: '2026-05-16' }
])

const goToMeeting = (id: string) => {
  router.push(`/meetings/${id}`)
}

const getPriorityType = (priority: string) => {
  const map: Record<string, string> = { high: 'danger', medium: 'warning', low: 'info' }
  return map[priority] || 'info'
}
</script>

<template>
  <div class="workplace">
    <div class="welcome-card">
      <div class="welcome-info">
        <h2>{{ greeting }}</h2>
        <p>{{ dateStr }}</p>
      </div>
      <el-button type="primary" size="large" @click="router.push('/meetings/create')">
        <el-icon><CirclePlusFilled /></el-icon>
        新建会议
      </el-button>
    </div>

    <el-row :gutter="16" class="stats-row">
      <el-col :span="6" v-for="item in stats" :key="item.label">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-content">
            <el-icon :size="32" :color="item.color"><component :is="item.icon" /></el-icon>
            <div class="stat-text">
              <div class="stat-value">{{ item.value }}</div>
              <div class="stat-label">{{ item.label }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16" class="content-row">
      <el-col :span="16">
        <el-card class="meeting-card" shadow="never">
          <template #header>
            <div class="card-header">
              <span>最近会议</span>
              <el-link type="primary" @click="router.push('/meetings')">查看全部</el-link>
            </div>
          </template>
          <el-table :data="recentMeetings" style="width: 100%" @row-click="(row: any) => goToMeeting(row.id)">
            <el-table-column prop="title" label="会议标题" />
            <el-table-column prop="time" label="时间" width="180" />
            <el-table-column prop="type" label="类型" width="120">
              <template #default="{ row }">
                <el-tag size="small">{{ row.type }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="120">
              <template #default="{ row }">
                <el-tag :type="row.status === '已完成' ? 'success' : 'warning'" size="small">
                  {{ row.status }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card class="todo-card" shadow="never">
          <template #header>
            <div class="card-header">
              <span>待处理任务</span>
            </div>
          </template>
          <div class="todo-list">
            <div v-for="item in todoList" :key="item.id" class="todo-item">
              <div class="todo-title">{{ item.title }}</div>
              <div class="todo-meta">
                <el-tag :type="getPriorityType(item.priority)" size="small">
                  {{ item.priority === 'high' ? '高' : item.priority === 'medium' ? '中' : '低' }}
                </el-tag>
                <span class="todo-deadline">截止: {{ item.deadline }}</span>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped lang="scss">
.workplace {
  .welcome-card {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: linear-gradient(135deg, #4f46e5 0%, #7c3aed 100%);
    color: #fff;
    padding: 24px 32px;
    border-radius: 12px;
    margin-bottom: 20px;

    h2 {
      margin: 0 0 8px;
      font-size: 24px;
    }

    p {
      margin: 0;
      opacity: 0.9;
    }
  }

  .stats-row {
    margin-bottom: 20px;

    .stat-card {
      .stat-content {
        display: flex;
        align-items: center;
        gap: 16px;
      }

      .stat-value {
        font-size: 28px;
        font-weight: 600;
        color: #333;
      }

      .stat-label {
        font-size: 14px;
        color: #999;
        margin-top: 4px;
      }
    }
  }

  .content-row {
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-weight: 600;
    }

    .todo-list {
      .todo-item {
        padding: 12px 0;
        border-bottom: 1px solid #f0f0f0;

        &:last-child {
          border-bottom: none;
        }

        .todo-title {
          font-size: 14px;
          color: #333;
          margin-bottom: 8px;
        }

        .todo-meta {
          display: flex;
          align-items: center;
          gap: 12px;

          .todo-deadline {
            font-size: 12px;
            color: #999;
          }
        }
      }
    }
  }
}
</style>
