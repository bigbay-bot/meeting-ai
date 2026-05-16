<script setup lang="ts">
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import { Check, ArrowLeft } from '@element-plus/icons-vue'

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

const getStatusType = (status: string) => {
  return status === 'completed' ? 'success' : 'warning'
}

const getStatusLabel = (status: string) => {
  return status === 'completed' ? '已完成' : '待处理'
}
</script>

<template>
  <div class="meeting-detail page-container">
    <div class="page-header">
      <el-button text class="back-btn" @click="$router.push('/meetings')">
        <el-icon :size="18"><ArrowLeft /></el-icon>
        返回
      </el-button>
      <h1 class="page-title">会议详情</h1>
    </div>

    <div class="detail-card ds-card">
      <div class="meeting-header">
        <h2 class="h2">{{ meeting.title }}</h2>
        <span class="ds-tag" :class="{ 'ds-tag--success': meeting.status === 'completed' }">
          {{ meeting.status === 'completed' ? '已完成' : '进行中' }}
        </span>
      </div>

      <div class="meta-grid">
        <div class="meta-item">
          <span class="meta-label">组织者</span>
          <span class="meta-value">{{ meeting.organizer }}</span>
        </div>
        <div class="meta-item">
          <span class="meta-label">会议时间</span>
          <span class="meta-value">{{ meeting.time }}</span>
        </div>
        <div class="meta-item">
          <span class="meta-label">时长</span>
          <span class="meta-value">{{ meeting.duration }}</span>
        </div>
        <div class="meta-item">
          <span class="meta-label">地点</span>
          <span class="meta-value">{{ meeting.location }}</span>
        </div>
        <div class="meta-item meta-item--wide">
          <span class="meta-label">参会人员</span>
          <div class="participants">
            <span v-for="p in meeting.participants" :key="p" class="participant-tag">
              {{ p }}
            </span>
          </div>
        </div>
      </div>

      <el-tabs v-model="activeTab" class="detail-tabs">
        <el-tab-pane label="会议摘要" name="summary">
          <p class="summary-text">{{ meeting.summary }}</p>
        </el-tab-pane>

        <el-tab-pane label="决策项" name="decisions">
          <div class="decision-list">
            <div v-for="(decision, index) in meeting.decisions" :key="index" class="decision-item">
              <span class="decision-index">{{ index + 1 }}</span>
              <span class="decision-text">{{ decision }}</span>
            </div>
          </div>
        </el-tab-pane>

        <el-tab-pane label="行动项" name="actions">
          <div class="action-list">
            <div
              v-for="item in meeting.actionItems"
              :key="item.content"
              class="action-item"
              :class="{ 'action-item--done': item.status === 'completed' }"
            >
              <div class="action-main">
                <el-icon :size="18" class="action-check">
                  <Check />
                </el-icon>
                <span class="action-content">{{ item.content }}</span>
              </div>
              <div class="action-meta">
                <span class="action-owner">{{ item.owner }}</span>
                <span class="action-deadline">{{ item.deadline }}</span>
                <span class="ds-tag" :class="`ds-tag--${getStatusType(item.status)}`">
                  {{ getStatusLabel(item.status) }}
                </span>
              </div>
            </div>
          </div>
        </el-tab-pane>

        <el-tab-pane label="关键讨论" name="keypoints">
          <div class="keypoint-list">
            <div
              v-for="(point, index) in meeting.keyPoints"
              :key="index"
              class="keypoint-item"
            >
              <span class="keypoint-dot" />
              <span class="keypoint-text">{{ point }}</span>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<style scoped lang="scss">
.page-header {
  display: flex;
  align-items: center;
  gap: $space-3;
  margin-bottom: $space-5;

  .back-btn {
    padding: 0;
    height: auto;
    font-size: $font-size-base;
    color: $text-secondary;

    &:hover {
      color: $primary-color;
    }
  }

  .page-title {
    font-size: $font-size-xl;
    font-weight: $font-weight-bold;
    color: $text-primary;
    margin: 0;
  }
}

.detail-card {
  padding: $space-5;

  @include respond-to(sm) {
    padding: $space-4;
  }
}

.meeting-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: $space-3;
  margin-bottom: $space-5;
  flex-wrap: wrap;
}

.meta-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: $space-4;
  padding-bottom: $space-5;
  border-bottom: 1px solid $border-light;
  margin-bottom: $space-5;

  @include respond-to(md) {
    grid-template-columns: repeat(2, 1fr);
  }

  @include respond-to(sm) {
    grid-template-columns: 1fr;
    gap: $space-3;
  }
}

.meta-item {
  display: flex;
  flex-direction: column;
  gap: $space-1;

  &--wide {
    grid-column: 1 / -1;
  }

  .meta-label {
    font-size: $font-size-xs;
    color: $text-secondary;
    font-weight: $font-weight-medium;
  }

  .meta-value {
    font-size: $font-size-base;
    color: $text-primary;
    font-weight: $font-weight-medium;
  }
}

.participants {
  display: flex;
  flex-wrap: wrap;
  gap: $space-2;
}

.participant-tag {
  display: inline-flex;
  align-items: center;
  padding: 4px 10px;
  border-radius: $radius-md;
  background: $primary-light;
  color: $primary-color;
  font-size: $font-size-sm;
  font-weight: $font-weight-medium;
}

.detail-tabs {
  :deep(.el-tabs__header) {
    margin-bottom: $space-4;
  }
}

.summary-text {
  font-size: $font-size-base;
  line-height: $line-height-relaxed;
  color: $text-regular;
}

.decision-list {
  display: flex;
  flex-direction: column;
  gap: $space-3;
}

.decision-item {
  display: flex;
  align-items: flex-start;
  gap: $space-3;
  padding: $space-3 $space-4;
  border-radius: $radius-md;
  background: $bg-hover;

  .decision-index {
    width: 24px;
    height: 24px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 50%;
    background: $primary-color;
    color: #fff;
    font-size: $font-size-xs;
    font-weight: $font-weight-bold;
    flex-shrink: 0;
  }

  .decision-text {
    font-size: $font-size-base;
    color: $text-primary;
    line-height: $line-height-base;
    padding-top: 2px;
  }
}

.action-list {
  display: flex;
  flex-direction: column;
  gap: $space-3;
}

.action-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: $space-4;
  padding: $space-3 $space-4;
  border-radius: $radius-md;
  background: $bg-hover;
  transition: $transition-base;

  @include respond-to(md) {
    flex-direction: column;
    align-items: flex-start;
    gap: $space-2;
  }

  &--done {
    opacity: 0.7;
  }

  .action-main {
    display: flex;
    align-items: center;
    gap: $space-2;
    flex: 1;
    min-width: 0;
  }

  .action-check {
    color: $success-color;
    flex-shrink: 0;
  }

  .action-content {
    font-size: $font-size-base;
    color: $text-primary;
    @include text-ellipsis;
  }

  .action-meta {
    display: flex;
    align-items: center;
    gap: $space-3;
    flex-shrink: 0;

    @include respond-to(md) {
      width: 100%;
      justify-content: space-between;
    }
  }

  .action-owner {
    font-size: $font-size-sm;
    color: $text-secondary;
    font-weight: $font-weight-medium;
  }

  .action-deadline {
    font-size: $font-size-xs;
    color: $text-disabled;
  }
}

.keypoint-list {
  display: flex;
  flex-direction: column;
  gap: $space-3;
}

.keypoint-item {
  display: flex;
  align-items: flex-start;
  gap: $space-3;
  padding: $space-3 $space-4;
  border-radius: $radius-md;
  background: $bg-hover;

  .keypoint-dot {
    width: 8px;
    height: 8px;
    border-radius: 50%;
    background: $primary-color;
    margin-top: 7px;
    flex-shrink: 0;
  }

  .keypoint-text {
    font-size: $font-size-base;
    color: $text-primary;
    line-height: $line-height-base;
  }
}
</style>
