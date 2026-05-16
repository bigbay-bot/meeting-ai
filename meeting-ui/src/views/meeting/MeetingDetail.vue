<script setup lang="ts">
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import { Check } from '@element-plus/icons-vue'

const route = useRoute()
const meetingId = route.params.id as string

const meeting = ref({
  id: meetingId,
  title: '产品需求评审会议',
  organizer: '张三',
  time: '2026-05-15 14:00',
  duration: '2小时',
  location: '会议室A / 线上腾讯会议',
  participants: ['张三', '李四', '王五', '赵六', '钱七', '孙八', '周九', '吴十'],
  status: 'completed',
  summary: '本次会议主要评审了Q3产品需求，确定了核心功能优先级和排期。',
  decisions: [
    '优先开发智能会议纪要功能，预计6月15日上线',
    '接入FastGPT作为AI引擎，使用自带知识集',
    '前端采用Vue3 + Element Plus技术栈'
  ],
  actionItems: [
    { content: '完成技术方案设计文档', owner: '李四', deadline: '2026-05-20', status: 'pending' },
    { content: '搭建前端项目框架', owner: '王五', deadline: '2026-05-18', status: 'completed' },
    { content: '申请FastGPT API密钥', owner: '张三', deadline: '2026-05-17', status: 'pending' }
  ],
  keyPoints: [
    '讨论了AI生成纪要的准确率问题，目标达到85%以上',
    '知识集需要按会议类型分类管理',
    '考虑支持多语言会议纪要生成'
  ]
})

const activeTab = ref('summary')
</script>

<template>
  <div class="meeting-detail">
    <el-page-header @back="$router.push('/meetings')" title="会议详情" />

    <el-card class="detail-card" shadow="never">
      <div class="meeting-header">
        <h2>{{ meeting.title }}</h2>
        <el-tag type="success" size="large">{{ meeting.status === 'completed' ? '已完成' : '进行中' }}</el-tag>
      </div>

      <el-descriptions :column="3" border>
        <el-descriptions-item label="组织者">{{ meeting.organizer }}</el-descriptions-item>
        <el-descriptions-item label="会议时间">{{ meeting.time }}</el-descriptions-item>
        <el-descriptions-item label="时长">{{ meeting.duration }}</el-descriptions-item>
        <el-descriptions-item label="地点">{{ meeting.location }}</el-descriptions-item>
        <el-descriptions-item label="参会人员" :span="2">
          <el-tag v-for="p in meeting.participants" :key="p" size="small" style="margin-right: 8px;">
            {{ p }}
          </el-tag>
        </el-descriptions-item>
      </el-descriptions>

      <el-tabs v-model="activeTab" class="detail-tabs">
        <el-tab-pane label="会议摘要" name="summary">
          <p class="summary-text">{{ meeting.summary }}</p>
        </el-tab-pane>

        <el-tab-pane label="决策项" name="decisions">
          <el-timeline>
            <el-timeline-item
              v-for="(decision, index) in meeting.decisions"
              :key="index"
              type="primary"
              :icon="Check"
            >
              {{ decision }}
            </el-timeline-item>
          </el-timeline>
        </el-tab-pane>

        <el-tab-pane label="行动项" name="actions">
          <el-table :data="meeting.actionItems" style="width: 100%">
            <el-table-column prop="content" label="事项" />
            <el-table-column prop="owner" label="负责人" width="120" />
            <el-table-column prop="deadline" label="截止日期" width="140" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="{ row }">
                <el-tag :type="row.status === 'completed' ? 'success' : 'warning'" size="small">
                  {{ row.status === 'completed' ? '已完成' : '待处理' }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <el-tab-pane label="关键讨论" name="keypoints">
          <el-collapse>
            <el-collapse-item
              v-for="(point, index) in meeting.keyPoints"
              :key="index"
              :title="`讨论点 ${index + 1}`"
            >
              {{ point }}
            </el-collapse-item>
          </el-collapse>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<style scoped lang="scss">
.meeting-detail {
  .detail-card {
    margin-top: 20px;

    .meeting-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;

      h2 {
        margin: 0;
      }
    }

    .detail-tabs {
      margin-top: 20px;
    }

    .summary-text {
      font-size: 14px;
      line-height: 1.8;
      color: #333;
    }
  }
}
</style>
